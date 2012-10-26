package fr.cg95.cvq.service.users.job;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import fr.cg95.cvq.business.authority.LocalAuthorityResource.Type;
import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.GlobalHomeFolderConfiguration;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.business.users.UserState;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry;
import fr.cg95.cvq.service.users.IUserSearchService;
import fr.cg95.cvq.service.users.IUserService;
import fr.cg95.cvq.service.users.IUserWorkflowService;
import fr.cg95.cvq.util.Critere;
import fr.cg95.cvq.util.DateUtils;
import fr.cg95.cvq.util.UserUtils;
import fr.cg95.cvq.util.mail.IMailService;
import fr.cg95.cvq.util.translation.ITranslationService;

public class UserPendingManagementJob {
    private static final Logger logger = Logger.getLogger(UserPendingManagementJob.class);

    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;
    private IUserSearchService userSearchService;
    private IUserService userService;
    private ITranslationService translationService;
    private IUserWorkflowService userWorkflowService;

    public void launchNotificationJob() {
        localAuthorityRegistry.browseAndCallback(this, "sendNotifications", null);
    }

    public void launchRemovalJob() {
        localAuthorityRegistry.browseAndCallback(this, "deleteExpiredAccounts", null);
    }

    public void deleteExpiredAccounts() {
        Integer nbDaysBeforePurge = userService.getGlobalHomeFolderConfiguration().getPendingUsersLiveDuration();
        Set<Critere> criterias = querySearchUsersToDelete(nbDaysBeforePurge);
        List<Individual> users = userSearchService.get(criterias, null, null, null, false);

        logger.info("Purge user accounts after "+nbDaysBeforePurge+" days : " + users.size());

        for (Individual indiv : users) {
            try {
                logger.info("Purge : " + indiv.getLastName() + " " + indiv.getFirstName() +" #"+indiv.getId());
                userWorkflowService.delete(indiv.getHomeFolder());
            } catch (Exception ex) {
                logger.error("Error during deleting user #" + indiv.getId(), ex);
            }
        }
    }

    /**
     * Send drafts expiration notification to ecitizens.
     *
     * @return the number of email notifications sent.
     */
    public Integer sendNotifications() {
        Integer counter = 0;
        GlobalHomeFolderConfiguration config = userService.getGlobalHomeFolderConfiguration();
        Integer limit = config.getPendingUsersLiveDuration() - config.getPendingUserNotificationPurge();

        Set<Critere> criterias = querySearchUsersToNotify(limit);

        List<Individual> users = userSearchService.get(criterias, null, null, null, false);
        logger.info("Notify user accounts "+config.getPendingUserNotificationPurge()+" days before deletion : " + users.size());

        for (Individual indiv : users) {
            logger.info("sendUserPendingNotifications() for user : " + indiv.getLastName() + " " + indiv.getFirstName() +" #"+indiv.getId());

            try {
                String from = SecurityContext.getCurrentSite().getAdminEmail();
                String mailBody = buildMailTemplate((Adult)indiv, config.getPendingUsersLiveDuration());
                if (mailBody == null) throw new CvqException("Unabled to retrieve email body for password purge notification");

                mailService.send(
                    from,
                    ((Adult)indiv).getEmail(),
                    null,
                    translationService.translate("homeFolder.adult.notification.purgeAccount.subject",
                            new Object[] { SecurityContext.getCurrentSite().getDisplayTitle() }),
                    mailBody);

                logger.debug("sendUserPendingNotifications() sent for user " + indiv.getId());
                counter ++;
            } catch (CvqException e) {
                logger.error("sendUserPendingNotifications() " + e.getMessage());
            }
        }

        return counter;
    }

    protected String buildMailTemplate(Adult adult, Integer liveDuration) {
        String template =
            this.localAuthorityRegistry.getBufferedLocalAuthorityResource(
                Type.TXT, "NotificationBeforeUserAccountDelete", false);

        if (template == null) return null;

        template = template.replace("${lien}", UserUtils.getUrlValidation(adult));
        template = template.replace("${site}", SecurityContext.getCurrentSite().getDisplayTitle());
        template = template.replace("${creationDate}", DateUtils.format(adult.getCreationDate()));
        template = template.replace("${expirationDate}",
            DateUtils.format(DateUtils.getShiftedDate(adult.getCreationDate(),
                    Calendar.DAY_OF_YEAR, liveDuration + 1))
        );

        return template;
    }

    protected Set<Critere> querySearchUsersToDelete(Integer dateInterval) {
        Set<Critere> criterias = new HashSet<Critere>();

        criterias.add(new Critere(
                Adult.SEARCH_BY_CREATION_DATE,
                DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, -dateInterval),
                Critere.LTE));

        criterias.add(new Critere(
                Adult.SEARCH_BY_USER_STATE,
                UserState.PENDING.name(),
                Critere.EQUALS));

        return criterias;
    }

    protected Set<Critere> querySearchUsersToNotify(Integer dateInterval) {
        Set<Critere> criterias = querySearchUsersToDelete(dateInterval);

        criterias.add(new Critere(
                Adult.SEARCH_BY_CREATION_DATE,
                DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, -(dateInterval+1)),
                Critere.GT));

        return criterias;
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setUserWorkflowService(IUserWorkflowService userWorkflowService) {
        this.userWorkflowService = userWorkflowService;
    }
}
