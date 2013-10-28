package org.libredemat.service.request.job;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.business.request.Category;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.RequestUtils;
import org.libredemat.util.mail.IMailService;
import org.libredemat.util.translation.ITranslationService;


/**
 * A job that sends email notifications of the newly created requests to category managers.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public class RequestCreationNotificationJob {

    private static Logger logger = Logger.getLogger(RequestCreationNotificationJob.class);

    private IRequestActionService requestActionService;
    private IMailService mailService;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private ITranslationService translationService;
    private IRequestTypeService requestTypeService;

    private IRequestDAO requestDAO;

    public void launchJob() {
        localAuthorityRegistry.browseAndCallback(this, "notifyLocalAuthRequestsCreation", null);
    }

    public void notifyLocalAuthRequestsCreation() {

        LocalAuthority la = SecurityContext.getCurrentSite();
        logger.info("notifyLocalAuthRequestsCreation() dealing with " + la.getName());
        if (!requestTypeService.getGlobalRequestTypeConfiguration()
            .isRequestsCreationNotificationEnabled()) {
            logger.info("notifyLocalAuthRequestsCreation() requests creation notification are disabled for "
                    + la.getName() + ", returning");
            return;
        }

        List<Request> requestsToNotify = 
            requestDAO.issuedAndNotYetNotified();
        logger.debug("notifyLocalAuthRequestsCreation() got "
                + requestsToNotify.size() + " requests to notify");
        Map<Category, List<Request>> requestsByService =
            RequestUtils.groupByCategory(requestsToNotify);
        for (Category category : requestsByService.keySet()) {
            if (category.getPrimaryEmail() != null && !category.getPrimaryEmail().equals("")) {
                List<Request> requestList = requestsByService.get(category);
                RequestUtils.groupByRequestType(requestList);
                StringBuffer body = new StringBuffer();
                body.append("Bonjour,\n\n")
                    .append("Voici la liste des derniers télé-services créés :\n");
                for (Request request : requestList) {
                    String requestTypeLabel = 
                        translationService.translateRequestTypeLabel(request.getRequestType().getLabel());
                    body.append("\t").append(requestTypeLabel).append(" : ")
                        .append(request.getId()).append("\n");
                }

                boolean alertSent = true;
                try {
                    mailService.send(null, category.getPrimaryEmail(), 
                            category.getEmails() == null ? null : (String[]) category
                            .getEmails().toArray(new String[category.getEmails().size()]),
                            "[CapDémat] Alerte nouveaux téléservices", body.toString());
                } catch (CvqException e) {
                    logger.error("notifyLocalAuthRequestsCreation() got an error while sending email alert "
                            + e.getMessage());
                    alertSent = false;
                }
                if (alertSent) {
                    // email alert successfully sent, update request accordingly
                    for (Request request : requestList) {
                        requestActionService.addSystemAction(request.getId(),
                            RequestActionType.CREATION_NOTIFICATION);
                    }
                }
            } else {
                logger.warn("notifyLocalAuthRequestsCreation() category "
                        + category.getName() + " has no email address configured");
            }
        }
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setRequestActionService(IRequestActionService requestActionService) {
        this.requestActionService = requestActionService;
    }

    public void setLocalAuthorityRegistry(
            ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }
}
