package fr.cg95.cvq.service.users.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;

import com.google.gson.JsonObject;

import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.business.users.MeansOfContactEnum;
import fr.cg95.cvq.business.users.UserAction;
import fr.cg95.cvq.business.users.UserEvent;
import fr.cg95.cvq.dao.jpa.IGenericDAO;
import fr.cg95.cvq.dao.users.IHomeFolderDAO;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqModelException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.users.IMeansOfContactService;
import fr.cg95.cvq.service.users.IUserNotificationService;
import fr.cg95.cvq.service.users.IUserSearchService;
import fr.cg95.cvq.service.users.IUserService;
import fr.cg95.cvq.service.users.job.UserNotificationJob.NotificationType;
import fr.cg95.cvq.util.JSONUtils;
import fr.cg95.cvq.util.mail.IMailService;
import fr.cg95.cvq.util.sms.ISmsService;
import fr.cg95.cvq.util.translation.ITranslationService;

public class UserNotificationService implements IUserNotificationService, ApplicationListener<UserEvent> {

    private IUserSearchService userSearchService;
    private IMeansOfContactService meansOfContactService;
    private IMailService mailService;
    private ISmsService smsService;
    private ITranslationService translationService;
    private IHomeFolderDAO homeFolderDAO;
    private IUserService userService;
    private IGenericDAO genericDAO;

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void contact(Adult adult, MeansOfContactEnum type, String recipient, String message, String note)
        throws CvqException {
        if (!meansOfContactService.isAvailable(type, adult)) throw new CvqModelException("");
        if (MeansOfContactEnum.EMAIL.equals(type)) {
            notifyByEmail(SecurityContext.getCurrentAgent().getEmail(), recipient,
                translationService.translate("mail.ecitizenContact.subject"),
                message, null, null);
        } else if (MeansOfContactEnum.SMS.equals(type)) {
            notifyBySms(recipient, message);
        }
        JsonObject payload = new JsonObject();
        JsonObject contact = new JsonObject();
        contact.addProperty("meansOfContact", type.toString());
        contact.addProperty("message", message);
        if (!StringUtils.isBlank(recipient)) contact.addProperty("recipient", recipient);
        payload.add("contact", contact);
        UserAction action = new UserAction(UserAction.Type.CONTACT, adult.getId(), payload);
        action.setNote(note);
        adult.getHomeFolder().getActions().add(action);
        homeFolderDAO.update(adult.getHomeFolder());
    }

    @Override
    public void notifyByEmail(String from, String to, String subject,
        String body, byte[] data, String attachmentName)
        throws CvqException {
        String fullSubject =
            "[" + SecurityContext.getCurrentSite().getDisplayTitle() + "] "
            + subject;
        mailService.send(from, to, null, fullSubject, body, data, attachmentName);
    }

    @Override
    public void notifyBySms(String to, String body)
        throws CvqException {
        if (smsService.isEnabled()) {
            smsService.send(to, body);
        } else {
            throw new CvqException("sms_service.not.enabled");
        }
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        Adult adult = userSearchService.getAdultById(event.getAction().getTargetId());
        UserAction.Type action = event.getAction().getType();

        if (adult != null && adult.getLogin() != null && !adult.getLogin().trim().isEmpty()) {
            if (UserAction.Type.CREATION == action && hasValidEmail(adult)) {
                // From BO : Send new-password link
                if (SecurityContext.isBackOfficeContext()) {
                    userService.prepareResetPassword(adult);
                    sendNotification(adult, NotificationType.NEW_ACCOUNT_BO,
                            translationService.translate("homeFolder.notification.note.newAccountBO"));
                // From FO : Send validation link
                } else {
                    sendNotification(adult, NotificationType.NEW_ACCOUNT,
                            translationService.translate("homeFolder.notification.note.newAccount"));
                }
            } else if (UserAction.Type.MODIFICATION == action && isLoginModified(event, adult) && hasValidEmail(adult)) {
                sendNotification(adult, NotificationType.LOGIN_ASSIGNED,
                        translationService.translate("homeFolder.notification.note.loginAssigned"));
            }
        }
    }

    private boolean hasValidEmail(Adult adult) {
        return adult != null
            && adult.getEmail() != null
            && !adult.getEmail().equals(SecurityContext.getCurrentConfigurationBean().getDefaultEmail());
    }

    private boolean isLoginModified(UserEvent event, Adult adult) {
        JsonObject payload = JSONUtils.deserialize(event.getAction().getData());
        return adult != null
            && !StringUtils.isEmpty(adult.getLogin())
            && payload != null
            && payload.has("atom")
            && payload.get("atom").getAsJsonObject().get("fields").getAsJsonObject().has("login")
            && !payload.get("atom").getAsJsonObject().get("fields").getAsJsonObject().get("login").getAsJsonObject().get("to").isJsonNull();
    }

    @Override
    public void sendNotification(Adult adult, NotificationType type, String note) {
        JsonObject data = new JsonObject();
        data.addProperty("login", adult.getLogin());
        data.addProperty("email", adult.getEmail());
        data.addProperty("action", type.toString());
        UserAction action = new UserAction(UserAction.Type.WAITING_NOTIFICATION, adult.getId(), data);
        action.setNote(note);
        adult.getHomeFolder().getActions().add(action);
        genericDAO.update(adult.getHomeFolder());
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public void setMeansOfContactService(IMeansOfContactService meansOfContactService) {
        this.meansOfContactService = meansOfContactService;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
