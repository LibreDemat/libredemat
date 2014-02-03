package org.libredemat.service.users.job;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.libredemat.business.authority.LocalAuthorityResource.Type;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.UserAction;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.util.JSONUtils;
import org.libredemat.util.UserUtils;
import org.libredemat.util.mail.IMailService;
import org.libredemat.util.translation.ITranslationService;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class UserNotificationJob {
    private static Logger logger = Logger.getLogger(UserNotificationJob.class);

    private IHomeFolderDAO homeFolderDAO;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;
    private ITranslationService translationService;
    private IGenericDAO genericDAO;

    public static enum NotificationType {
        LOGIN_ASSIGNED,
        RESET_PASSWORD,
        NEW_ACCOUNT,
        NEW_ACCOUNT_BO
    }

    public void launch() {
        localAuthorityRegistry.browseAndCallback(this, "sendNotifications", null);
    }

    public Integer sendNotifications() {
        int sendedEmails = 0;
        for (UserAction userAction: homeFolderDAO.waitingNotification()) {
            JsonObject json = JSONUtils.deserialize(userAction.getData());
            Adult adult = (Adult) genericDAO.findById(Adult.class, json.getAsJsonObject("target").get("id").getAsLong());

            try {
                switch(NotificationType.valueOf(json.get("action").getAsString())) {
                    case NEW_ACCOUNT:
                        sendNewAccountFOEmail(adult, json);
                    break;
                    case NEW_ACCOUNT_BO:
                        sendNewAccountBOEmail(adult, json);
                    break;
                    case LOGIN_ASSIGNED:
                        sendLoginAssignedEmail(adult, json);
                    break;
                    case RESET_PASSWORD:
                        sendResetPasswordEmail(adult, json);
                    break;
                }

                userAction.setType(UserAction.Type.NOTIFIED);
                sendedEmails++;
            } catch (MailException me) {
                if (me instanceof MailAuthenticationException) {
                    logger.error("User notification error : authentication failure", me);
                } else if (me instanceof MailSendException) {
                    logger.error("User notification error : sending message failure", me);
                } else {
                    logger.error("User notification email sending error.", me);
                    userAction.setType(UserAction.Type.NOTIFICATION_ERROR);
                }
            } catch (Exception e) {
                logger.error("User notification email sending error.", e);
                userAction.setType(UserAction.Type.NOTIFICATION_ERROR);
            } finally {
                genericDAO.update(userAction);
            }
        }
        return sendedEmails;
    }

    private void sendResetPasswordEmail(Adult adult, JsonObject json) throws CvqException {
        HashMap<String, String> variables = getGenericVariables(adult);
        variables.put("link", UserUtils.getUrlResetPassword(adult));

        sendNotificationMail(
                json.get("courriel").getAsString(),
                SecurityContext.isFrontOfficeContext()?
                        "homeFolder.adult.notification.resetPassword.subject":
                         "homeFolder.adult.notification.bo.resetPassword.subject",
                loadEmailBodyFromAssets(SecurityContext.isFrontOfficeContext() ? "ResetPassword" : "BoResetPassword", variables));
    }

    private void sendLoginAssignedEmail(Adult adult, JsonObject json) throws CvqException {
        sendNotificationMail(
                json.get("courriel").getAsString(),
                "homeFolder.adult.notification.loginAssigned.subject",
                loadEmailBodyFromAssets("LoginAssigned", getGenericVariables(adult)));
    }

    private void sendNewAccountFOEmail(Adult adult, JsonObject json) throws CvqException {
        String urlValidation =  UserUtils.getUrlValidation(adult);

        // User create account on the fly -> redirect to TS after login
        JsonElement callback = json.getAsJsonObject("user").get("callbackUrl");
        if (callback != null && callback.getAsString() != null && !callback.getAsString().isEmpty()) {
            try {
                urlValidation += "&callback=" + URLEncoder.encode(callback.getAsString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Unable to encode URL "+callback);
            }
        }

        HashMap<String, String> variables = getGenericVariables(adult);
        variables.put("link", urlValidation);

        sendNotificationMail(
                json.get("courriel").getAsString(),
                "homeFolder.adult.notification.newAccount.subject",
                loadEmailBodyFromAssets("NewAccountFO", variables));
    }

    private void sendNewAccountBOEmail(Adult adult, JsonObject json) throws CvqException {
        HashMap<String, String> variables = getGenericVariables(adult);
        variables.put("link", UserUtils.getUrlResetPassword(adult));

        sendNotificationMail(
                json.get("courriel").getAsString(),
                "homeFolder.adult.notification.newAccount.bo.subject",
                loadEmailBodyFromAssets("NewAccountBO", variables));
    }

    /**
     * Send notification to user with custom subjet and body
     */
    private void sendNotificationMail(String email, String subject, String body) throws CvqException {
        mailService.send(
                SecurityContext.getCurrentSite().getAdminEmail(),
                email,
                null,
                translationService.translate(subject,
                    new Object[]{ SecurityContext.getCurrentSite().getDisplayTitle() }),
                body,
                null
            );
    }

    /**
     * Generic variables sent to each emails
     * @param adult
     * @return
     */
    private HashMap<String, String> getGenericVariables(Adult adult) {
        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("lastName", adult.getLastName());
        variables.put("firstName", adult.getFirstName());
        variables.put("login", adult.getLogin());
        variables.put("city", SecurityContext.getCurrentSite().getDisplayTitle());
        variables.put("homefolderId", adult.getHomeFolder().getId().toString());
        return variables;
    }

    /**
     * Load email body from TXT asset, and replace variables
     * @param nomFichier
     * @param variables
     * @return
     */
    private String loadEmailBodyFromAssets(String nomFichier, HashMap<String, String> variables) {
        String template = localAuthorityRegistry.getBufferedLocalAuthorityResource(Type.TXT, nomFichier, true);
        if (template == null) return null;

        for(Entry<String, String> variable : variables.entrySet()) {
            if (variable.getKey() == null || variable.getValue() == null) continue;
            template = template.replace("${" + variable.getKey() + "}", variable.getValue());
        }

        return template;
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

}
