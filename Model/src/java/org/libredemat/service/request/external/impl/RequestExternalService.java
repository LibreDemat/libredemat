package org.libredemat.service.request.external.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestAction;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestSeason;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.business.request.workflow.event.impl.WorkflowGenericEvent;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.UserAction;
import org.libredemat.business.users.UserEvent;
import org.libredemat.business.users.UserState;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.business.users.external.UserExternalAction;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.ExternalServiceUtils;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.impl.ExternalService;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.payment.external.ExternalApplicationProviderService;
import org.libredemat.service.request.IRequestExportService;
import org.libredemat.service.request.IRequestSearchService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.annotation.RequestFilter;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.UserUtils;
import org.libredemat.util.Critere;
import org.libredemat.util.JSONUtils;
import org.libredemat.util.mail.IMailService;
import org.libredemat.util.translation.ITranslationService;
import org.libredemat.xml.common.HomeFolderType;
import org.libredemat.xml.common.IndividualType;
import org.libredemat.xml.common.RequestStateType;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.request.ecitizen.HomeFolderModificationRequestDocument;
import org.libredemat.xml.request.ecitizen.VoCardRequestDocument;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.libredemat.HomeFolderMergeRequestDocument;
import org.libredemat.MergedIndividualType;
import org.libredemat.HomeFolderMergeRequestDocument.HomeFolderMergeRequest;

public class RequestExternalService extends ExternalService implements IRequestExternalService,
    ILocalAuthorityLifecycleAware, ApplicationListener<UserEvent> {

    private static Logger logger = Logger.getLogger(RequestExternalService.class);

    private IRequestDAO requestDAO;
    private IGenericDAO genericDAO;

    private IRequestExternalActionService requestExternalActionService;

    private IRequestExportService requestExportService;

    private IUserSearchService userSearchService;

    private IExternalHomeFolderService externalHomeFolderService;

    private ITranslationService translationService;

    private IMailService mailService;

    private IRequestSearchService requestSearchService;

    private IRequestTypeService requestTypeService;

    private IHomeFolderDAO homeFolderDAO;

    private static final Set<RequestExternalAction.Status> finalExternalStatuses =
        new HashSet<RequestExternalAction.Status>(2);

    static {
        finalExternalStatuses.add(RequestExternalAction.Status.ACCEPTED);
        finalExternalStatuses.add(RequestExternalAction.Status.REJECTED);
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
            SecurityContext.getCurrentConfigurationBean().getBeanForExternalService(externalServiceLabel);
        return esb == null ? Collections.<String>emptyList() : esb.getRequestTypes();
    }

    @Deprecated
    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.NONE)
    public List<Request> getSendableRequests(String externalServiceLabel) {
        Set<RequestState> set = new HashSet<RequestState>(1);
        set.add(RequestState.COMPLETE);
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
    @Context(types = {ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.READ)
    public List<String> checkExternalReferential(Request request) throws CvqException {

        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return Collections.<String>emptyList();

        XmlObject xmlRequest = 
            ExternalServiceUtils.getRequestTypeFromXmlObject(requestExportService.fillRequestXml(request));

        List<String> errors = new ArrayList<String>();
        for (IExternalProviderService eps :
                getExternalServicesByRequestType(request.getRequestType().getLabel())) {
            errors.addAll(eps.checkExternalReferential(xmlRequest));
        }
        return errors;
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.WRITE)
    public void sendRequest(Request request) throws CvqException {
        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return;
        for (IExternalProviderService externalProviderService :
                getExternalServicesByRequestType(request.getRequestType().getLabel())) {
            if (externalProviderService instanceof ExternalApplicationProviderService)
                continue;
            externalProviderService.sendRequest(request);
        }
    }

    @Override
    @Async
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    @RequestFilter(privilege = ContextPrivilege.MANAGE)
    public void sendRequests(Set<Critere> ids, String email) throws CvqException {
        EntityManagerFactory emf = SecurityContext.getCurrentConfigurationBean().getEntityManagerFactory();
        JpaUtil.init(emf);
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
        JpaUtil.close(false);
    }

    @Override
    public RequestType getRequestPayload(Request request, IExternalProviderService externalProviderService) throws CvqException {
        RequestType xmlRequest = getRequestType(request);
        HomeFolderMapping mapping = getOrCreateHomeFolderMapping(xmlRequest, externalProviderService);
        fillRequestWithMapping(xmlRequest, mapping);
        if(isAgentCreatorRequest(request)){
            xmlRequest.setIsAgent(true);
        }
        return xmlRequest;
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    @RequestFilter(privilege = ContextPrivilege.MANAGE)
    public List<String> getKeys(Set<Critere> criterias) {
        return requestExternalActionService.getKeys(criterias);
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.WRITE)
    public RequestType getRequestType(Request request) throws CvqException {
        return ExternalServiceUtils.getRequestTypeFromXmlObject(
                requestExportService.fillRequestXml(request));
    }

    private HomeFolderMapping getOrCreateHomeFolderMapping(
            RequestType xmlRequest, IExternalProviderService externalProviderService) throws CvqModelException {
        HomeFolderType xmlHomeFolder = xmlRequest.getHomeFolder();
        HomeFolderMapping mapping = externalHomeFolderService.getHomeFolderMapping(
                externalProviderService.getLabel(), xmlHomeFolder.getId());
        if (mapping == null) {
            // no existing external service mapping : create a new one to store
            // the LibreDemat external identifier
            mapping = new HomeFolderMapping(externalProviderService.getLabel(), xmlHomeFolder.getId(), null);
            for (IndividualType xmlIndividual : xmlHomeFolder.getIndividualsArray()) {
                mapping.getIndividualsMappings()
                        .add(new IndividualMapping(xmlIndividual.getId(), null, mapping));
            }
            externalHomeFolderService.createHomeFolderMapping(mapping);
        }
        return mapping;
    }

    private void fillRequestWithMapping(RequestType xmlRequest, HomeFolderMapping mapping) throws CvqModelException {

        HomeFolderType xmlHomeFolder = xmlRequest.getHomeFolder();
        xmlHomeFolder.setExternalId(mapping.getExternalId());
        xmlHomeFolder.setExternalLibredematId(mapping.getExternalLibreDematId());

        for (IndividualType individual : xmlHomeFolder.getIndividualsArray()) {
            boolean hasMapping = false;
            for (IndividualMapping iMapping : mapping.getIndividualsMappings()) {
                if (iMapping.getIndividualId().equals(individual.getId()))
                    hasMapping = true;
            }
            if (!hasMapping) {
                mapping.getIndividualsMappings()
                    .add(new IndividualMapping(individual.getId(), null, mapping));
                externalHomeFolderService.modifyHomeFolderMapping(mapping);
            }
        }

        for (IndividualMapping iMapping : mapping.getIndividualsMappings()) {
            if (iMapping.getIndividualId() == null) {
                logger.warn("fillRequestWithMapping() Got an individual mapping without individual id " + iMapping.getExternalLibreDematId());
                continue;
            }

            if (iMapping.getIndividualId().equals(xmlRequest.getRequester().getId())) {
                xmlRequest.getRequester().setExternalLibredematId(iMapping.getExternalLibreDematId());
                xmlRequest.getRequester().setExternalId(iMapping.getExternalId());
            }

            if (xmlRequest.getSubject() != null) {
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
                    individualType.setExternalLibredematId(iMapping.getExternalLibreDematId());
                    individualType.setExternalId(iMapping.getExternalId());
                }
            }

            for (IndividualType xmlIndividual : xmlHomeFolder.getIndividualsArray()) {
                if (iMapping.getIndividualId().equals(xmlIndividual.getId())) {
                    xmlIndividual.setExternalId(iMapping.getExternalId());
                    xmlIndividual.setExternalLibredematId(iMapping.getExternalLibreDematId());
                }
            }
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Map<String, Object> loadExternalInformations(Request request) throws CvqException {

        if (!hasMatchingExternalService(request.getRequestType().getLabel()))
            return Collections.emptyMap();

        RequestType xmlRequest = getRequestType(request);
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

        Request request = (Request) requestDAO.findById(requestId);
        RequestSeason requestSeason = request.getRequestSeason();
        if (request.getState().equals(RequestState.ARCHIVED) || (requestSeason != null && requestSeason.getEffectEnd().isBeforeNow())) {
            logger.debug("getConsumptionsByRequest() Filtering request (archived or season finished) : " + requestId);
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

    @Override
    @Async
    @Context(types = {ContextType.SUPER_ADMIN})
    public void addLocalAuthority(String localAuthorityName) {
        EntityManagerFactory emf = SecurityContext.getCurrentConfigurationBean().getEntityManagerFactory();
        JpaUtil.init(emf);
        for (org.libredemat.business.request.RequestType rt : requestTypeService.getAllRequestTypes()) {
            for (IExternalProviderService service : getExternalServicesByRequestType(rt.getLabel())) {
                for (Long id : requestExternalActionService.getRequestsWithoutExternalAction(
                        rt.getId(), service.getLabel())) {
                    requestExternalActionService.addTrace(new RequestExternalAction(
                        new Date(), id, "libredemat",
                        translationService.translate("requestExternalAction.message.addTraceOnStartup"),
                        service.getLabel(), RequestExternalAction.Status.NOT_SENT));
                }
            }
        }
        JpaUtil.close(false);
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN})
    public void removeLocalAuthority(String localAuthorityName) {
        // nothing to do yet
    }

    @Override
    public boolean isAgentCreatorRequest(Request request){
        if(request.getActions() != null){
            for(RequestAction requestAction : request.getActions()){
                if(requestAction.getType().equals(RequestActionType.CREATION) &&
                        requestAction.getResultingState().equals(RequestState.PENDING)){
                    return !(request.getRequesterId().equals(requestAction.getAgentId()));
                }
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(UserEvent event) {

        HomeFolder homeFolder = userSearchService.getHomeFolderById(event.getAction().getTargetId());
        if (homeFolder == null)
            homeFolder = userSearchService.getById(event.getAction().getTargetId()).getHomeFolder();
        if (homeFolder == null) {
            logger.warn("onApplicationEvent() got an event for a non-existent user ID : "
                    + event.getAction().getTargetId());
            return;
        }

        if (UserAction.Type.MODIFICATION.equals(event.getAction().getType())
            || UserAction.Type.STATE_CHANGE.equals(event.getAction().getType())
            || UserAction.Type.SYNCHRONISE.equals(event.getAction().getType())) {

            if (UserState.VALID.equals(homeFolder.getState())) {

                List<String> newMappings = new ArrayList<String>();
                for (Entry<IExternalProviderService, ExternalServiceBean> entry : 
                    SecurityContext.getCurrentConfigurationBean().getExternalServices().entrySet()) {
                    if (entry.getValue().getProperty("sendHomeFolderCreation") != null
                            && externalHomeFolderService.getHomeFolderMapping(entry.getKey().getLabel(), homeFolder.getId()) == null) {
                        externalHomeFolderService.addHomeFolderMapping(
                                entry.getKey().getLabel(), homeFolder.getId(), null);
                        newMappings.add(entry.getKey().getLabel());
                    }
                }
                for (HomeFolderMapping mapping :
                    externalHomeFolderService.getHomeFolderMappings(homeFolder.getId())) {

                    String message = "La synchronisation s'est effectuée avec succès";
                    String status = "Sent";

                    String externalServiceLabel = mapping.getExternalServiceLabel();
                    try {

                        IExternalProviderService externalProviderService =
                            getExternalServiceByLabel(externalServiceLabel);
                        if (externalProviderService == null) {
                            logger.warn("onApplicationEvent() External service " 
                                    + mapping.getExternalServiceLabel() + " is no longer existing");
                            continue;
                        }
                        if (externalProviderService instanceof ExternalApplicationProviderService)
                            continue;

                        // to force user action's saving (which implies it will have an id we can use below)
                        JpaUtil.getEntityManager().flush();

                        RequestType xmlRequest = null;
                        if (newMappings.contains(externalServiceLabel)) {
                            VoCardRequestDocument doc = VoCardRequestDocument.Factory.newInstance();
                            xmlRequest = doc.addNewVoCardRequest();
                            xmlRequest.setRequestTypeLabel("VO Card");
                        } else {
                            HomeFolderModificationRequestDocument doc =
                                HomeFolderModificationRequestDocument.Factory.newInstance();
                            xmlRequest = doc.addNewHomeFolderModificationRequest();
                            xmlRequest.setRequestTypeLabel("Home Folder Modification");
                        }
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(event.getAction().getDate());
                        xmlRequest.setCreationDate(calendar);
                        // set request id to the trigerring action's id to ensure uniqueness and a minimum of coherence
                        if (UserAction.Type.SYNCHRONISE.equals(event.getAction().getType())) {
                            xmlRequest.setId(event.getAction().getDate().getTime());
                        } else {
                            xmlRequest.setId(event.getAction().getId());
                        }
                        xmlRequest.setLastModificationDate(calendar);
                        xmlRequest.setState(RequestStateType.Enum.forString(RequestState.VALIDATED.toString()));
                        xmlRequest.setValidationDate(calendar);
                        xmlRequest.addNewHomeFolder().set(homeFolder.modelToXml());
                        xmlRequest.addNewRequester().set(userSearchService.getHomeFolderResponsible(homeFolder.getId()).modelToXml());
                        fillRequestWithMapping(xmlRequest, mapping);
                        String externalId = externalProviderService.sendHomeFolderModification(xmlRequest);
                        if (externalId != null && !externalId.equals("")) {
                            mapping.setExternalId(externalId);
                            externalHomeFolderService.modifyHomeFolderMapping(mapping);
                        }

                        if (!externalProviderService.handlesTraces()) {
                        genericDAO.create(new UserExternalAction(
                            homeFolder.getId().toString(), externalServiceLabel, "Sent"));
                        }

                    } catch (Exception ex) {
                        logger.error(ex);
                        message = "Erreur interne : " + ex.getMessage();
                        status = "ErrorInterne";
                    }

                    //Trace on HF validation
                    if ((UserAction.Type.STATE_CHANGE.equals(event.getAction().getType())
                            || UserAction.Type.MODIFICATION.equals(event.getAction().getType()))
                          && externalServiceLabel.equals("CirilNetEnfance")) {

                        UserAction action = new UserAction(UserAction.Type.SYNCHRONISE, homeFolder.getId());
                        JsonObject payload = UserUtils.getPayloadForUserAction(-1L, "Système", -1L, "CirilNetEnfance");
                        payload.addProperty("state", status);
                        payload.addProperty("message", message);
                        action.setData(new Gson().toJson(payload));
                        action = (UserAction) genericDAO.create(action);
                        homeFolder.getActions().add(action);
                        homeFolderDAO.update(homeFolder);
                    }

                    if (UserAction.Type.SYNCHRONISE.equals(event.getAction().getType())) {
                        JsonObject payload = UserUtils.getPayloadForUserAction(SecurityContext.getCurrentUserId(),
                                    UserUtils.getDisplayName(SecurityContext.getCurrentUserId()), -1L,
                                    externalServiceLabel);
                        payload.addProperty("state", status);
                        payload.addProperty("message", message);
                        UserAction action = new UserAction(UserAction.Type.SYNCHRONISE, homeFolder.getId());
                        action.setData(new Gson().toJson(payload));
                        action = (UserAction) genericDAO.create(action);
                        homeFolder.getActions().add(action);
                        homeFolderDAO.update(homeFolder);
                    }
                }
            }
        } else if (UserAction.Type.MERGE.equals(event.getAction().getType())) {
            
            HomeFolder originHomeFolder = 
                userSearchService.getHomeFolderById(event.getAction().getTargetId());
            if (originHomeFolder == null) {
                logger.debug("onApplicationEvent() ignoring MERGE event on individual, waiting for home folder");
                return;
            }

            List<HomeFolderMapping> mappings = 
              externalHomeFolderService.getHomeFolderMappings(originHomeFolder.getId());
            if (mappings == null || mappings.isEmpty()) {
                logger.debug("onApplicationEvent() merged home folder has no mapping, returning");
                return;
            }

            JsonObject jsonObject = JSONUtils.deserialize(event.getAction().getData());
            Gson gson = new Gson();
            Type type = new TypeToken<Map<Long, Long>>() {}.getType();
            Map<Long, Long> merged = gson.fromJson(jsonObject.get("mergedIndividuals").getAsString(), type);

            Long targetHomeFolderId = jsonObject.get("merge").getAsLong();
            for (HomeFolderMapping originMapping : mappings) {

                String externalServiceLabel = originMapping.getExternalServiceLabel();
                HomeFolderMapping targetMapping = 
                    externalHomeFolderService.getHomeFolderMapping(externalServiceLabel, targetHomeFolderId);
                if (targetMapping == null) {
                    logger.debug("onApplicationEvent() creating a new HF mapping for " + externalServiceLabel
                            + " and " + targetHomeFolderId);
                    targetMapping = new HomeFolderMapping(externalServiceLabel, targetHomeFolderId, null);
                    targetMapping.setExternalLibreDematId(originMapping.getExternalLibreDematId());
                    for (IndividualMapping originIndMapping : originMapping.getIndividualsMappings()) {
                        Long targetIndividualId = merged.get(originIndMapping.getIndividualId()) != null ? 
                                merged.get(originIndMapping.getIndividualId()) : originIndMapping.getIndividualId();
                        IndividualMapping targetIndMapping = 
                            new IndividualMapping(targetIndividualId, null, targetMapping);
                        targetIndMapping.setExternalLibreDematId(originIndMapping.getExternalLibreDematId());
                        targetMapping.getIndividualsMappings().add(targetIndMapping);
                    }

                    externalHomeFolderService.createHomeFolderMapping(targetMapping);

                } else {
                    HomeFolderMergeRequestDocument hfmrDocument = 
                        HomeFolderMergeRequestDocument.Factory.newInstance();
                    HomeFolderMergeRequest hfmrRequest = hfmrDocument.addNewHomeFolderMergeRequest();
                    hfmrRequest.setOriginHomeFolderExternalLibreDematId(originMapping.getExternalLibreDematId());
                    hfmrRequest.setTargetHomeFolderExternalLibreDematId(targetMapping.getExternalLibreDematId());

                    for (IndividualMapping originIndMapping : originMapping.getIndividualsMappings()) {
                        MergedIndividualType mergedIndividual = hfmrRequest.addNewMergedIndividual();
                        mergedIndividual.setOriginIndividualExternalLibreDematId(originIndMapping.getExternalLibreDematId());
                        Long targetIndividualId = merged.get(originIndMapping.getIndividualId()) != null ? 
                                merged.get(originIndMapping.getIndividualId()) : originIndMapping.getIndividualId();
                        IndividualMapping targetIndMapping = 
                            externalHomeFolderService.getIndividualMapping(targetMapping, targetIndividualId);
                        if (targetIndMapping != null) {
                            logger.debug("onApplicationEvent() already a mapping for " + targetIndividualId 
                                    + " in " + targetHomeFolderId);
                            mergedIndividual.setTargetIndividualExternalLibreDematId(targetIndMapping.getExternalLibreDematId());
                            continue;
                            
                        }

                        targetIndMapping = new IndividualMapping(targetIndividualId, null, targetMapping);
                        targetIndMapping.setExternalLibreDematId(originIndMapping.getExternalLibreDematId());
                        targetMapping.getIndividualsMappings().add(targetIndMapping);

                        // even if no change in externalLibreDematId, notify anyway
                        mergedIndividual.setTargetIndividualExternalLibreDematId(originIndMapping.getExternalLibreDematId());
                    }

                    externalHomeFolderService.modifyHomeFolderMapping(targetMapping);
                    
                    IExternalProviderService externalProviderService =
                        getExternalServiceByLabel(externalServiceLabel);
                    try {
                        externalProviderService.sendMergedHomeFolder(hfmrDocument);
                    } catch (CvqException e) {
                        throw new RuntimeException();
                    }
                    
                }
            }
        } else if (UserAction.Type.SERVICE_EXTERNE.equals(event.getAction().getType())) {
            UserAction action = new UserAction(UserAction.Type.SYNCHRONISE, homeFolder.getId());
            JsonObject payload = UserUtils.getPayloadForUserAction(-1L, "CirilNetEnfance", -1L, "LibreDémat");
            payload.addProperty("state", "receive");
            payload.addProperty("message", "Démarrage de la synchronisation");
            action.setData(new Gson().toJson(payload));
            action = (UserAction) genericDAO.create(action);
            homeFolder.getActions().add(action);
            homeFolderDAO.update(homeFolder);
        }
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public void setRequestExportService(IRequestExportService requestExportService) {
        this.requestExportService = requestExportService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
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

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    public void publish(WorkflowGenericEvent wfEvent) throws CvqException {

        SecurityContext.pushContext();
        SecurityContext.setCurrentContext(SecurityContext.EXTERNAL_SERVICE_CONTEXT);

        for (IExternalProviderService extProviderService : getExternalServicesByRequestType(wfEvent.getRequest().getRequestType().getLabel())) {
            SecurityContext.setCurrentExternalService(extProviderService.getLabel());
            wfEvent.accept(extProviderService);
        }

        SecurityContext.popContext();
    }
}
