package fr.cg95.cvq.service.users.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;

import com.google.gson.JsonObject;

import fr.cg95.cvq.business.users.UserAction;
import fr.cg95.cvq.dao.jpa.IGenericDAO;
import fr.cg95.cvq.dao.users.IHomeFolderDAO;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry;
import fr.cg95.cvq.util.JSONUtils;
import fr.cg95.cvq.util.mail.IMailService;
import fr.cg95.cvq.util.translation.ITranslationService;

public class UserNotificationJob {
    private static Logger logger = Logger.getLogger(UserNotificationJob.class);

    private IHomeFolderDAO homeFolderDAO;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;
    private ITranslationService translationService;
    private IGenericDAO genericDAO;

    public void launch() {
        localAuthorityRegistry.browseAndCallback(this, "sendNotifications", null);
    }

    public Integer sendNotifications() {
        List<UserAction> waiting = homeFolderDAO.waitingNotification();
        int sendedmails = 0;
        for (UserAction userAction: waiting) {
            JsonObject json = JSONUtils.deserialize(userAction.getData());
            try {
                mailService.send(
                    SecurityContext.getCurrentSite().getAdminEmail(),
                    json.get("email").getAsString(),
                    null,
                    translationService.translate("homeFolder.adult.notification.loginAssigned.subject",
                        new Object[]{SecurityContext.getCurrentSite().getDisplayTitle()}),
                    translationService.translate("homeFolder.adult.notification.loginAssigned.body",
                        new Object[]{SecurityContext.getCurrentSite().getDisplayTitle(),
                            json.get("login").getAsString()}), null);
                userAction.setType(UserAction.Type.NOTIFIED);
                sendedmails++;
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
        return sendedmails;
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
