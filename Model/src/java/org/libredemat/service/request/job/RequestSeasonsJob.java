package org.libredemat.service.request.job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.RequestSeason;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.RequestType;
import org.libredemat.business.request.RequestEvent.EVENT_TYPE;
import org.libredemat.exception.CvqException;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestSearchService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.util.Critere;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * A job that automatically archives notified requests which are associated
 * with a finished season.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public class RequestSeasonsJob implements ApplicationContextAware{

    private static Logger logger = Logger.getLogger(RequestSeasonsJob.class);
    
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IRequestWorkflowService requestWorkflowService;
    private IRequestTypeService requestTypeService;
    private IRequestSearchService requestSearchService;
    private ApplicationContext applicationContext;
    private IRequestActionService requestActionService;

    public void launchJob() {
        localAuthorityRegistry.browseAndCallback(this, "checkRequestsSeasons", null);
    }

    public void checkRequestsSeasons()
        throws CvqException {
    
        List<RequestType> requestTypes = requestTypeService.getAllRequestTypes();
        for (RequestType requestType : requestTypes) {
            if (requestType.getSeasons() == null || requestType.getSeasons().isEmpty()) {
                logger.debug("checkRequestsSeasons() no seasons defined for request type " 
                        + requestType.getLabel());
                continue;
            }
                
            logger.debug("checkRequestsSeasons() looking at seasons for request type " 
                    + requestType.getLabel());
            Set<RequestSeason> requestSeasons = requestType.getSeasons();
            for (RequestSeason requestSeason : requestSeasons) {
                logger.debug("checkRequestsSeasons() looking at season " 
                        + requestSeason.getLabel());
                if (requestSeason.getEffectEnd().isBeforeNow()) {
                    Set<Critere> criterias = new HashSet<Critere>(2);
                    Critere stateCriteria =
                        new Critere(Request.SEARCH_BY_STATE,
                            RequestState.VALIDATED, Critere.EQUALS);
                    criterias.add(stateCriteria);
                    criterias.add(new Critere(Request.SEARCH_BY_SEASON_ID,
                        requestSeason.getId(), Critere.EQUALS));
                    List<Request> requests = 
                        requestSearchService.get(criterias, null, null, 0, 0, false);
                    for (Request request : requests) {
                        requestWorkflowService.updateRequestState(request.getId(),
                            RequestState.CLOSED, null);
                    }
                }

                if (requestSeason.getRegistrationEnd().isBeforeNow()) {
                    Set<Critere> criterias = new HashSet<Critere>(2);
                    Critere stateCriteria =
                        new Critere(Request.SEARCH_BY_STATE,
                            RequestState.DRAFT, Critere.EQUALS);
                    criterias.add(stateCriteria);
                    criterias.add(new Critere(Request.SEARCH_BY_SEASON_ID,
                        requestSeason.getId(), Critere.EQUALS));
                    List<Request> requests =
                            requestSearchService.get(criterias, null, null, 0, 0, false);
                    for (Request request : requests) {
                        requestWorkflowService.delete(request, false);
                    }
                }
                Integer seasonDraftDeletionLimit = requestTypeService.getGlobalRequestTypeConfiguration().getSeasonDraftNotificationBeforeDelete();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(requestSeason.getRegistrationEnd().toDate());
                calendar.add(Calendar.DAY_OF_YEAR, - seasonDraftDeletionLimit);
                if ((new Date()).after(calendar.getTime())) {
                    Set<Critere> criterias = new HashSet<Critere>(2);
                    Critere stateCriteria =
                        new Critere(Request.SEARCH_BY_STATE,
                            RequestState.DRAFT, Critere.EQUALS);
                    criterias.add(stateCriteria);
                    criterias.add(new Critere(Request.SEARCH_BY_SEASON_ID,
                        requestSeason.getId(), Critere.EQUALS));
                    List<Request> requests =
                            requestSearchService.get(criterias, null, null, 0, 0, false);
                    for (Request request : requests) {
                        boolean sent = false;
                        RequestEvent requestEvent =
                            new RequestEvent(this, EVENT_TYPE.SEASONS_DRAFT_EXPIRATION, request);
                        applicationContext.publishEvent(requestEvent);
                        sent = true;
                        if (sent)
                            requestActionService.addSystemAction(request.getId(), RequestActionType.DRAFT_DELETE_NOTIFICATION);
                    }
                }

            }
        }
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setRequestSearchService(IRequestSearchService requestSearchService) {
        this.requestSearchService = requestSearchService;
    }

    public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        this.requestWorkflowService = requestWorkflowService;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    public void setRequestActionService(IRequestActionService requestActionService) {
        this.requestActionService = requestActionService;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }
}
