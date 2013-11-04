package org.libredemat.service.request.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.ISODateTimeFormat;
import org.libredemat.business.authority.LocalAuthorityResource.Type;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestAdminAction;
import org.libredemat.business.request.RequestAdminEvent;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IRequestActionDAO;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.request.external.IRequestExternalActionDAO;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.request.IRequestPdfService;
import org.libredemat.service.request.IRequestServiceRegistry;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.util.Critere;
import org.libredemat.util.translation.ITranslationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class RequestArchivingJob implements ApplicationContextAware, ILocalAuthorityLifecycleAware {

    public static class Result implements Serializable {
        private static final long serialVersionUID = 1L;
        public int numberOfSuccesses = 0;
        public Map<Request, Throwable> failures = new HashMap<Request, Throwable>();
    }

    private static final Set<RequestState> states = new HashSet<RequestState>(5);
    static {
        states.add(RequestState.VALIDATED);
        states.add(RequestState.CLOSED);
        states.add(RequestState.REJECTED);
        states.add(RequestState.CANCELLED);
    }

    private ApplicationContext applicationContext;
    private IRequestExternalActionService requestExternalActionService;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IRequestDAO requestDAO;
    private IRequestExternalActionDAO requestExternalActionDAO;
    private IGenericDAO genericDAO;
    private IRequestPdfService requestPdfService;
    private IRequestServiceRegistry requestServiceRegistry;
    private IRequestWorkflowService requestWorkflowService;
    private ITranslationService translationService;
    private IRequestActionDAO requestActionDAO;
    
    @Context(types = {ContextType.SUPER_ADMIN})
    public void launch() {
        localAuthorityRegistry.browseAndCallback(this, "archive", null);
    }

    @Context(types = {ContextType.SUPER_ADMIN})
    public void archive() {
        Result result = new Result();
        DateMidnight today = new DateMidnight();
        for (Request r : requestDAO.listByStates(states, true)) {
            try {
                Request request = requestDAO.findById(r.getId(), true);
                int filingDelay = requestServiceRegistry.getRequestService(request).getFilingDelay();
                DateMidnight lastModificationDay = new DateMidnight(request.getLastModificationDate());
                if (Months.monthsBetween(lastModificationDay, today).getMonths() >= filingDelay) {
                    String motive = translationService
                        .translate("requestArchive.motive", new Object[]{filingDelay});
                    if (RequestState.VALIDATED.equals(request.getState()))
                        requestWorkflowService.updateRequestState(request.getId(),
                            RequestState.CLOSED, motive);
                    requestWorkflowService.updateRequestState(request.getId(),
                        RequestState.ARCHIVED, motive);
                    byte archive[] = requestPdfService.generateArchive(request.getId());
                    requestDAO.empty(request);
                    Set<Critere> criteriaSet = new HashSet<Critere>(1);
                    criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
                        request.getId().toString(), Critere.EQUALS));
                    for (RequestExternalAction trace :
                        requestExternalActionService.getTraces(criteriaSet, null, null, 0, 0)) {
                        requestExternalActionDAO.delete(trace);
                    }
                    String filename = translationService.translate("requestArchive.filename",
                        new Object[] {
                            translationService
                                .translateRequestTypeLabel(request.getRequestType().getLabel()),
                            new DateTime(request.getCreationDate()).toString(ISODateTimeFormat.date()),
                            request.getId().toString()
                    });
                    localAuthorityRegistry.saveLocalAuthorityResource(Type.REQUEST_ARCHIVE,
                        filename, archive);
                    try {
                        JpaUtil.closeAndReOpen(false);
                        result.numberOfSuccesses++;
                    } catch (Throwable t) {
                        localAuthorityRegistry.getLocalAuthorityResourceFile(Type.REQUEST_ARCHIVE,
                            filename, false).delete();
                        throw t;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
                result.failures.put(r, t);
                JpaUtil.closeAndReOpen(true);
            }
        }
        RequestAdminAction action =
            new RequestAdminAction(RequestAdminAction.Type.REQUESTS_ARCHIVED);
        action.getComplementaryData().put(RequestAdminAction.Data.ARCHIVING_RESULT, result);
        if (result.numberOfSuccesses > 0 || result.failures.size() > 0) {
            genericDAO.saveOrUpdate(action);
        }
        RequestAdminEvent event = new RequestAdminEvent(this, action);
        applicationContext.publishEvent(event);
    }

    @Context(types = {ContextType.SUPER_ADMIN})
    public void migrate() {
        Result result = new Result();
        Set<RequestState> archivedStates = new HashSet<RequestState>(1);
        archivedStates.add(RequestState.ARCHIVED);
        for (Request r : requestDAO.listByStates(archivedStates, true)) {
            try {
                Request request = requestDAO.findById(r.getId(), true);
                byte archive[] = requestPdfService.generateArchive(request.getId());
                requestDAO.empty(request);
                Set<Critere> criteriaSet = new HashSet<Critere>(1);
                criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
                    request.getId().toString(), Critere.EQUALS));
                for (RequestExternalAction trace :
                    requestExternalActionService.getTraces(criteriaSet, null, null, 0, 0)) {
                    requestExternalActionDAO.delete(trace);
                }
                String filename = translationService.translate("requestArchive.filename",
                    new Object[] {
                        translationService
                            .translateRequestTypeLabel(request.getRequestType().getLabel()),
                        new DateTime(request.getCreationDate()).toString(ISODateTimeFormat.date()),
                        request.getId().toString()
                });
                localAuthorityRegistry.saveLocalAuthorityResource(Type.REQUEST_ARCHIVE,
                    filename, archive);
                try {
                    JpaUtil.closeAndReOpen(false);
                    result.numberOfSuccesses++;
                } catch (Throwable t) {
                    localAuthorityRegistry.getLocalAuthorityResourceFile(Type.REQUEST_ARCHIVE,
                        filename, false).delete();
                    throw t;
                }
            } catch (Throwable t) {
                t.printStackTrace();
                result.failures.put(r, t);
                JpaUtil.closeAndReOpen(true);
            }
        }
        RequestAdminAction action =
            new RequestAdminAction(RequestAdminAction.Type.ARCHIVES_MIGRATED);
        action.getComplementaryData().put(RequestAdminAction.Data.ARCHIVING_RESULT, result);
        genericDAO.saveOrUpdate(action);
        RequestAdminEvent event = new RequestAdminEvent(this, action);
        applicationContext.publishEvent(event);
    }

    @Override
    public void addLocalAuthority(String localAuthorityName) {
        if (!requestActionDAO.hasArchivesMigrationAction()) {
            migrate();
            archive();
        }
    }

    @Override
    public void removeLocalAuthority(String localAuthorityName) {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setRequestExternalActionService(
            IRequestExternalActionService requestExternalActionService) {
        this.requestExternalActionService = requestExternalActionService;
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setRequestExternalActionDAO(IRequestExternalActionDAO requestExternalActionDAO) {
        this.requestExternalActionDAO = requestExternalActionDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public void setRequestPdfService(IRequestPdfService requestPdfService) {
        this.requestPdfService = requestPdfService;
    }

    public void setRequestServiceRegistry(IRequestServiceRegistry requestServiceRegistry) {
        this.requestServiceRegistry = requestServiceRegistry;
    }

    public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        this.requestWorkflowService = requestWorkflowService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public IRequestActionDAO getRequestActionDAO() {
        return requestActionDAO;
    }

    public void setRequestActionDAO(IRequestActionDAO requestActionDAO) {
        this.requestActionDAO = requestActionDAO;
    }
}
