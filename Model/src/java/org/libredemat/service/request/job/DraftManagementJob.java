package org.libredemat.service.request.job;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.authority.LocalAuthorityResource.Type;
import org.libredemat.business.request.GlobalRequestTypeConfiguration;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.users.Adult;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.util.Critere;
import org.libredemat.util.DateUtils;
import org.libredemat.util.mail.IMailService;
import org.libredemat.util.translation.ITranslationService;


/**
 * Job dedicated to the management of drafts.
 * 
 * Performs two tasks :
 * <ul>
 *   <li>Delete expired drafts</li>
 *   <li>Send a notification to e-citizens when one of their drafts is about to expire</li>
 * </ul>
 *
 * @author Victor Bartel (vba@zenexity.fr)
 */
public class DraftManagementJob {
    
    private static Logger logger = Logger.getLogger(DraftManagementJob.class);

    private IRequestDAO requestDAO;
    private IRequestActionService requestActionService;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;
    private IUserSearchService userSearchService;
    private ITranslationService translationService;
    private IRequestTypeService requestTypeService;
    private IRequestWorkflowService requestWorkflowService;

    public void launchNotificationJob() {
        localAuthorityRegistry.browseAndCallback(this, "sendNotifications", null);
    }
    
    public void launchRemovalJob() {
        localAuthorityRegistry.browseAndCallback(this, "deleteExpiredDrafts", null);
    }
    
    public void deleteExpiredDrafts() {
        Request request;
        while ((request = getNextDraftToDelete()) != null) {
            try {
                requestWorkflowService.delete(request, false);
            } catch (Exception e) {
                logger.error("Unable to delete draft : " + request.getId(), e);
            }
            JpaUtil.closeAndReOpen(false);
        }
    }

    /**
     * Send drafts expiration notification to ecitizens.
     * 
     * @return the number of email notifications sent.
     */
    public Integer sendNotifications() {
        Integer counter = 0; 
        GlobalRequestTypeConfiguration config = requestTypeService.getGlobalRequestTypeConfiguration();
        Integer limit = config.getDraftLiveDuration() - config.getDraftNotificationBeforeDelete();
        
        List<Request> requests = requestDAO.listDraftsToNotify(
            DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, -limit));
        
        String mailSubject = translationService.translate(
                "request.draftExpiration.subject",
                new Object[] {
                    SecurityContext.getCurrentSite().getDisplayTitle()
        });

        for (Request r : requests) {
            Adult adult = userSearchService.getAdultById(r.getRequesterId());
            String from = null;
            if (r.getRequestType().getCategory() != null)
                from = r.getRequestType().getCategory().getPrimaryEmail();
            else
                from = SecurityContext.getCurrentSite().getAdminEmail();
            boolean sent = false;
            
            try {
                String mailBody =
                    this.buildMailTemplate(r, config.getDraftLiveDuration());
                if (mailBody != null) {
                    mailService.send(from, adult.getEmail(), null, mailSubject, mailBody);
                }
                logger.debug("sendNotifications() sent for request " + r.getId());
                sent = true;
                counter ++;
            } catch (CvqException e) {
                logger.error("sendNotifications() " + e.getMessage());
            } finally {
                if (sent)
                    requestActionService.addSystemAction(r.getId(),
                        RequestActionType.DRAFT_DELETE_NOTIFICATION);
            }
        }
        return counter;
    }
    
    protected String buildMailTemplate(Request request, Integer liveDuration) {
        String template =
            this.localAuthorityRegistry.getBufferedLocalAuthorityResource(
                Type.TXT, "NotificationBeforeDraftDelete", false);

        if (template == null)
            return null;
        
        template = template.replace("${requestType}", 
            translationService.translateRequestTypeLabel(request.getRequestType().getLabel()));
        template = template.replace("${requestId}",request.getId().toString());
        template = template.replace("${creationDate}", 
                DateUtils.format(request.getCreationDate()));
        template = template.replace("${expirationDate}",
            DateUtils.format(DateUtils.getShiftedDate(request.getCreationDate(), 
                    Calendar.DAY_OF_YEAR, liveDuration + 1))
        );
        
        return template;
    }

    private Request getNextDraftToDelete() {
        Set<Critere> criterias = prepareQueryParams(
                requestTypeService.getGlobalRequestTypeConfiguration().getDraftLiveDuration());
        List<Request> requests = requestDAO.search(criterias,null,null,1,0, true);
        if (requests != null && !requests.isEmpty())
            return requests.get(0);
        else
            return null;
    }

    protected Set<Critere> prepareQueryParams(Integer dateInterval) {
        Set<Critere> criterias = new HashSet<Critere>();
        
        Critere criteria = new Critere();
        criteria.setAttribut(Request.SEARCH_BY_CREATION_DATE);
        criteria.setComparatif(Critere.LTE);
        criteria.setValue(DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, -dateInterval));
        criterias.add(criteria);
        
        criteria = new Critere();
        criteria.setAttribut(Request.SEARCH_BY_STATE);
        criteria.setComparatif(Critere.EQUALS);
        criteria.setValue(RequestState.DRAFT);
        criterias.add(criteria);
        
        return criterias;
    }

    public void setRequestActionService(IRequestActionService requestActionService) {
        this.requestActionService = requestActionService;
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

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        this.requestWorkflowService = requestWorkflowService;
    }
}
