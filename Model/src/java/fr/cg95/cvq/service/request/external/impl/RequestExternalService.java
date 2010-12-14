package fr.cg95.cvq.service.request.external.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.scheduling.annotation.Async;

import fr.cg95.cvq.business.request.Request;
import fr.cg95.cvq.business.request.RequestState;
import fr.cg95.cvq.business.request.external.RequestExternalAction;
import fr.cg95.cvq.business.request.external.RequestExternalActionState;
import fr.cg95.cvq.business.users.external.HomeFolderMapping;
import fr.cg95.cvq.business.users.external.IndividualMapping;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.dao.request.IRequestDAO;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.external.ExternalServiceBean;
import fr.cg95.cvq.external.ExternalServiceUtils;
import fr.cg95.cvq.external.IExternalProviderService;
import fr.cg95.cvq.external.impl.ExternalService;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.request.IRequestExportService;
import fr.cg95.cvq.service.request.IRequestSearchService;
import fr.cg95.cvq.service.request.annotation.RequestFilter;
import fr.cg95.cvq.service.request.external.IRequestExternalActionService;
import fr.cg95.cvq.service.request.external.IRequestExternalService;
import fr.cg95.cvq.service.users.external.IExternalHomeFolderService;
import fr.cg95.cvq.util.Critere;
import fr.cg95.cvq.util.mail.IMailService;
import fr.cg95.cvq.util.translation.ITranslationService;
import fr.cg95.cvq.xml.common.HomeFolderType;
import fr.cg95.cvq.xml.common.IndividualType;
import fr.cg95.cvq.xml.common.RequestType;

public class RequestExternalService extends ExternalService implements IRequestExternalService {

    private static Logger logger = Logger.getLogger(RequestExternalService.class);

    private IRequestDAO requestDAO;
    private IRequestExternalActionService requestExternalActionService;

    private IRequestExportService requestExportService;

    private IExternalHomeFolderService externalHomeFolderService;

    private ITranslationService translationService;

    private IMailService mailService;

    private IRequestSearchService requestSearchService;

    private static final Set<RequestExternalActionState> finalExternalStatuses =
        new HashSet<RequestExternalActionState>(2);

    static {
        finalExternalStatuses.add(RequestExternalActionState.ACCEPTED);
        finalExternalStatuses.add(RequestExternalActionState.REJECTED);
    }

    @Override
    public IExternalProviderService getExternalServiceByRequestType(String requestTypeLabel) {
        Set<IExternalProviderService> externalProviderServices =
            getExternalServicesByRequestType(requestTypeLabel);
        if (!externalProviderServices.isEmpty())
            return getExternalServicesByRequestType(requestTypeLabel).toArray(new IExternalProviderService[1])[0];

        return null;
    }

    @Override
    public Set<IExternalProviderService> getExternalServicesByRequestType(String requestTypeLabel) {
        Set<String> requestTypesLabels = new HashSet<String>();
        requestTypesLabels.add(requestTypeLabel);
        return getExternalServicesByRequestTypes(requestTypesLabels);

    }

    /**
     * Get the list of external services objects for the current local authority
     * interested in events about the given request types.
     * 
     * @return an empty set if none found
     */
    private Set<IExternalProviderService>
        getExternalServicesByRequestTypes(final Set<String> requestTypesLabels) {
        Set<IExternalProviderService> resultSet = new HashSet<IExternalProviderService>();
        for (Map.Entry<IExternalProviderService, ExternalServiceBean> entry :
            SecurityContext.getCurrentConfigurationBean().getExternalServices().entrySet()) {
            for (String requestTypeLabel : requestTypesLabels) {
                if (entry.getValue().supportRequestType(requestTypeLabel)) {
                    resultSet.add(entry.getKey());
                }
            }
        }
        return resultSet;
    }

    @Override
    public boolean hasMatchingExternalService(String requestLabel) {
        Set<IExternalProviderService> externalProviderServices =
            getExternalServicesByRequestType(requestLabel);
        return !externalProviderServices.isEmpty();
    }

    @Override
    public Collection<String> getRequestTypesForExternalService(String externalServiceLabel) {
        ExternalServiceBean esb =
           getBeanForExternalService(getExternalServiceByLabel(externalServiceLabel));
        return esb == null ? Collections.<String>emptyList() : esb.getRequestTypes();
    }

    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.NONE)
    public List<Request> getSendableRequests(String externalServiceLabel) {
        Set<RequestState> set = new HashSet<RequestState>(1);
        set.add(RequestState.VALIDATED);
        List<Request> result = new ArrayList<Request>();
        for (String rt : getRequestTypesForExternalService(externalServiceLabel)) {
            for (Request req : requestDAO.listByStatesAndType(set, rt, true)) {
                Set<Critere> criteriaSet = new HashSet<Critere>(3);
                criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
                    String.valueOf(req.getId()), Critere.EQUALS));
                criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
                    externalServiceLabel, Critere.EQUALS));
                criteriaSet.add(new Critere(
                    RequestExternalAction.SEARCH_BY_STATUS, finalExternalStatuses,
                    Critere.IN));
                if (requestExternalActionService.getTracesCount(criteriaSet) == 0) {
                    result.add(req);
                }
            }
        }
        return result;
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.NONE)
    public Set<String> getGenerableRequestTypes() {
        Set<String> result = new HashSet<String>();
        for (ExternalServiceBean esb :
            SecurityContext.getCurrentConfigurationBean().getExternalServices().values()) {
            if (esb.getGenerateTracedRequest())
                result.addAll(esb.getRequestTypes());
        }
        return result;
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<String> checkExternalReferential(Request request) throws CvqException {

        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return Collections.<String>emptyList();

        XmlObject xmlRequest = requestExportService.fillRequestXml(request);

        List<String> errors = new ArrayList<String>();
        for (IExternalProviderService eps :
                getExternalServicesByRequestType(request.getRequestType().getLabel())) {
            errors.addAll(eps.checkExternalReferential(xmlRequest));
        }
        return errors;
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void sendRequest(Request request) throws CvqException {

        if (!request.getState().equals(RequestState.VALIDATED))
            throw new CvqException("plugins.externalservices.error.requestMustBeValidated");

        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return;

        RequestType xmlRequest = ExternalServiceUtils.getRequestTypeFromXmlObject(
                requestExportService.fillRequestXml(request));
        HomeFolderType xmlHomeFolder = xmlRequest.getHomeFolder();
        for (IExternalProviderService externalProviderService :
                getExternalServicesByRequestType(request.getRequestType().getLabel())) {
            // before sending the request to the external service, eventually set 
            // the external identifiers if they are known ...
            String externalServiceLabel = externalProviderService.getLabel();
            HomeFolderMapping mapping = 
                externalHomeFolderService.getHomeFolderMapping(externalServiceLabel, xmlHomeFolder.getId());
            if (mapping != null) {
                fillRequestWithMapping(xmlRequest, mapping);
            } else {
                // no existing external service mapping : create a new one to store
                // the CapDemat external identifier
                mapping = new HomeFolderMapping(externalServiceLabel, xmlHomeFolder.getId(), null);
                for (IndividualType xmlIndividual : xmlHomeFolder.getIndividualsArray()) {
                    mapping.getIndividualsMappings()
                            .add(new IndividualMapping(xmlIndividual.getId(), null, mapping));
                }
                externalHomeFolderService.createHomeFolderMapping(mapping);

                fillRequestWithMapping(xmlRequest, mapping);
            }

            RequestExternalAction trace = null;
            if (!externalProviderService.handlesTraces()) {
                trace = new RequestExternalAction(new Date(), String.valueOf(xmlRequest.getId()),
                        null, "capdemat", null, externalServiceLabel, null);
            }
            try {
                logger.debug("sendRequest() routing request to external service " 
                        + externalServiceLabel);
                String externalId = externalProviderService.sendRequest(xmlRequest);
                if (externalId != null && !externalId.equals("")) {
                    mapping.setExternalId(externalId);
                    externalHomeFolderService.modifyHomeFolderMapping(mapping);
                }
                if (!externalProviderService.handlesTraces()) {
                    trace.setStatus(RequestExternalActionState.SENT);
                }
            } catch (CvqException ce) {
                logger.error("sendRequest() error while sending request to " 
                        + externalServiceLabel);
                if (!externalProviderService.handlesTraces()) {
                    trace.setStatus(RequestExternalActionState.NOT_SENT);
                }
            }
            if (!externalProviderService.handlesTraces()) {
                requestExternalActionService.addTrace(trace);
            }
        }
    }

    @Override
    @Async
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    @RequestFilter(privilege = ContextPrivilege.MANAGE)
    public void sendRequests(Set<Critere> ids, String email) throws CvqException {
        HibernateUtil.beginTransaction();
        Set<Long> successes = new HashSet<Long>();
        Map<Long, String> failures = new HashMap<Long, String>();
        for (Request request : requestSearchService.get(ids, null, null, 0, 0, true)) {
            try {
                sendRequest(request);
                successes.add(request.getId());
            } catch (Exception e) {
                failures.put(request.getId(), e.getMessage());
            }
        }
        String body = "";
        if (!successes.isEmpty()) {
            body += translationService.translate(
                "externalService.batchRequestResend.notification.body.success",
                new Object[]{successes.size()}) + "\n\t* "
                    + StringUtils.join(successes, "\n\t* ") + "\n\n";
        }
        if (!failures.isEmpty()) {
            String failuresChunk = "";
            for (Map.Entry<Long, String> failure : failures.entrySet()) {
                failuresChunk += "\t* " + failure.getKey() + " : " + failure.getValue() + "\n";
            }
            body += translationService.translate(
                "externalService.batchRequestResend.notification.body.failure",
                new Object[]{failures.size()}) + "\n" + failuresChunk;
        }
        mailService.send(null, email, null,
            translationService.translate("externalService.batchRequestResend.notification.subject"),
            body);
        HibernateUtil.commitTransaction();
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    @RequestFilter(privilege = ContextPrivilege.MANAGE)
    public List<String> getKeys(Set<Critere> criterias) {
        return requestExternalActionService.getKeys(criterias);
    }

    private void fillRequestWithMapping(RequestType xmlRequest, HomeFolderMapping mapping) {

        HomeFolderType xmlHomeFolder = xmlRequest.getHomeFolder();
        xmlHomeFolder.setExternalId(mapping.getExternalId());
        xmlHomeFolder.setExternalCapdematId(mapping.getExternalCapDematId());

        for (IndividualMapping iMapping : mapping.getIndividualsMappings()) {
            if (iMapping.getIndividualId() == null) {
                logger.warn("fillRequestWithMapping() Got an individual mapping without individual id " + iMapping.getExternalCapDematId());
                continue;
            }

            if (iMapping.getIndividualId().equals(xmlRequest.getRequester().getId())) {
                xmlRequest.getRequester().setExternalCapdematId(iMapping.getExternalCapDematId());
                xmlRequest.getRequester().setExternalId(iMapping.getExternalId());
            }

            if (xmlRequest.getSubject() != null && xmlRequest.getSubject().getChild() != null) {
                IndividualType individualType = null;
                if (xmlRequest.getSubject().getChild() != null)
                    individualType = xmlRequest.getSubject().getChild();
                else if (xmlRequest.getSubject().getAdult() != null)
                    individualType = xmlRequest.getSubject().getAdult();
                else if (xmlRequest.getSubject().getIndividual() != null)
                    individualType = xmlRequest.getSubject().getIndividual();

                if (individualType == null) {
                    logger.warn("fillRequestWithMapping() Unable to extract individual from requets " + xmlRequest.getId());
                } else if (iMapping.getIndividualId().equals(individualType.getId())) {
                    individualType.setExternalCapdematId(iMapping.getExternalCapDematId());
                    individualType.setExternalId(iMapping.getExternalId());
                }
            }

            for (IndividualType xmlIndividual : xmlHomeFolder.getIndividualsArray()) {
                if (iMapping.getIndividualId().equals(xmlIndividual.getId())) {
                    xmlIndividual.setExternalId(iMapping.getExternalId());
                    xmlIndividual.setExternalCapdematId(iMapping.getExternalCapDematId());
                }
            }
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Map<String, Object> loadExternalInformations(Request request) throws CvqException {

        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return Collections.emptyMap();

        RequestType xmlRequest = ExternalServiceUtils.getRequestTypeFromXmlObject(
                requestExportService.fillRequestXml(request));
        List<HomeFolderMapping> mappings = 
            externalHomeFolderService.getHomeFolderMappings(xmlRequest.getHomeFolder().getId());
        if (mappings == null || mappings.isEmpty())
            return Collections.emptyMap();

        Map<String, Object> informations = new TreeMap<String, Object>();
        for (HomeFolderMapping mapping : mappings) {
            IExternalProviderService externalProviderService = 
                getExternalServiceByLabel(mapping.getExternalServiceLabel());
            if (externalProviderService == null) {
                logger.warn("loadExternalInformations() External service " + mapping.getExternalServiceLabel() 
                        + " is no longer existing");
                continue;
            }
            fillRequestWithMapping(xmlRequest, mapping);
            try {
                informations.putAll(externalProviderService.loadExternalInformations(xmlRequest));
            } catch (CvqException e) {
                logger.warn("loadExternalInformations()" +
                    "Failed to retrieve information for " + externalProviderService.getLabel());
            }
        }

        return informations;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Map<Date, String> getConsumptions(final Long requestId, Date dateFrom, Date dateTo)
            throws CvqException {

        Request request = (Request) requestDAO.findById(Request.class, requestId);
        if (request.getState().equals(RequestState.ARCHIVED)) {
            logger.debug("getConsumptions() Filtering archived request : " + requestId);
            return null;
        }

        Set<IExternalProviderService> externalProviderServices =
            getExternalServicesByRequestType(request.getRequestType().getLabel());
        if (externalProviderServices == null || externalProviderServices.isEmpty())
            return Collections.emptyMap();

        Map<Date, String> resultMap = new HashMap<Date, String>();
        for (IExternalProviderService externalProviderService : externalProviderServices) {
            Map<Date, String> serviceMap = 
                externalProviderService.getConsumptions(request.getId(), dateFrom, dateTo);
            if (serviceMap != null && !serviceMap.isEmpty())
                resultMap.putAll(serviceMap);
        }

        return resultMap;
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setRequestExportService(IRequestExportService requestExportService) {
        this.requestExportService = requestExportService;
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }

    public void setRequestExternalActionService(
            IRequestExternalActionService requestExternalActionService) {
        this.requestExternalActionService = requestExternalActionService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setRequestSearchService(IRequestSearchService requestSearchService) {
        this.requestSearchService = requestSearchService;
    }

}
