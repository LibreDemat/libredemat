package org.libredemat.service.request.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.document.Document;
import org.libredemat.business.document.DocumentAction;
import org.libredemat.business.document.DocumentState;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestDocument;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.security.PermissionException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.document.IDocumentService;
import org.libredemat.service.document.IDocumentTypeService;
import org.libredemat.service.request.IRequestDocumentService;
import org.libredemat.service.request.IRequestSearchService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.util.translation.ITranslationService;
import org.springframework.context.ApplicationListener;

import org.libredemat.DocumentType;
import org.libredemat.GetDocumentListResponseDocument;
import org.libredemat.GetDocumentResponseDocument;
import org.libredemat.GetDocumentListResponseDocument.GetDocumentListResponse;
import org.libredemat.GetDocumentResponseDocument.GetDocumentResponse;

public class RequestDocumentService implements IRequestDocumentService, ApplicationListener<RequestEvent> {

    private static Logger logger = Logger.getLogger(RequestDocumentService.class);
    
    private IRequestDAO requestDAO;

    private IRequestExternalService requestExternalService;
    private IRequestSearchService requestSearchService;
    private IRequestTypeService requestTypeService;
    private IRequestExternalActionService requestExternalActionService;
    private IDocumentService documentService;
    private ITranslationService translationService;
    private IDocumentTypeService documentTypeService;

    @Deprecated
    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void addDocuments(final Long requestId, final Set<Long> documentsId) {

        Request request = requestDAO.findById(requestId);
        for (Long documentId : documentsId)
            request.getDocuments().add(new RequestDocument(documentId));

        updateLastModificationInformation(request);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void addDocument(final Long requestId, final Long documentId) {
        addDocument(requestDAO.findById(requestId), documentId);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void addDocument(Request request, final Long documentId) {
        if (request != null) {
            request.getDocuments().add(new RequestDocument(documentId));
            updateLastModificationInformation(request);
        }
    }
    
    @Deprecated
    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void addDocuments(Request request, List<Document> documents)
        throws CvqException {

        if (documents == null)
            return;

        for (Document document : documents) {
            document.setHomeFolderId(request.getHomeFolderId());
            document.setDepositId(request.getRequesterId());
            for (DocumentAction documentAction : document.getActions()) {
                documentAction.setUserId(request.getRequesterId());
            }
            addDocument(request, document.getId());
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void removeDocument(Request request, final Long documentId)
        throws CvqException, CvqObjectNotFoundException {
        Iterator<RequestDocument> it = request.getDocuments().iterator();
        while (it.hasNext()) {
            RequestDocument rd = it.next();
            if (rd.getDocumentId().equals(documentId)){
                it.remove();
            }
        }
        updateLastModificationInformation(request);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Set<RequestDocument> getAssociatedDocuments(final Long requestId) {
        Request request = requestDAO.findById(requestId);
        return request.getDocuments();
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.READ)
    public Set<Document> getAssociatedDocumentsByType(final Long requestId, final Long documentTypeId) {
        Request request = requestDAO.findById(requestId);
        if (request.getDocuments() == null)
            return Collections.emptySet();
        Set<Document> result = new HashSet<Document>();
        for (RequestDocument requestDocument : request.getDocuments()) {
            Document document = documentService.getById(requestDocument.getDocumentId());
            if (document.getDocumentType().getId().equals(documentTypeId))
                result.add(document);
        }
        return result;
    }

    @Override
    @Context(types = {ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.READ)
    public GetDocumentListResponseDocument getAssociatedFullDocuments(final Long requestId)
        throws CvqException, PermissionException {
        Request request = requestDAO.findById(requestId);

        GetDocumentListResponseDocument getDocumentListResponseDocument =
            GetDocumentListResponseDocument.Factory.newInstance();
        GetDocumentListResponse getDocumentListResponse =
            getDocumentListResponseDocument.addNewGetDocumentListResponse();

        Collection<String> authorizedRequestTypesLabels =
            requestExternalService.getRequestTypesForExternalService(SecurityContext.getCurrentExternalService());

        // FIXME move it in DocumentContextCheckAspect
        // Check external service permissions wrt configured request types labels
        String requestTypeLabel = request.getRequestType().getLabel();
        if (!authorizedRequestTypesLabels.contains(requestTypeLabel)) {
            throw new PermissionException(this.getClass(), "getAssociatedFullDocuments",
                    new ContextType[] {ContextType.EXTERNAL_SERVICE}, ContextPrivilege.READ,
                    "");
        }

        if (request.getDocuments().isEmpty()) {
            return getDocumentListResponseDocument;
        }

        for (RequestDocument rd : request.getDocuments()) {
            Document doc = documentService.getById(rd.getDocumentId());
            DocumentType documentType = getDocumentListResponse.addNewDocument();
            documentType.setDocumentId(doc.getId());
            documentType.setType(doc.getDocumentType().getName());
            documentType.setState(doc.getState().toString());
            Calendar calendar = new GregorianCalendar();
            if (doc.getValidationDate() != null) {
                calendar.setTime(doc.getValidationDate());
                documentType.setValidationDate(calendar);
            }
            if (doc.getEndValidityDate() != null) {
                calendar.setTime(doc.getEndValidityDate());
                documentType.setEndValidityDate(calendar);
            }
        }

        return getDocumentListResponseDocument;
    }

    @Override
    @Context(types = {ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.READ)
    public GetDocumentResponseDocument getAssociatedDocument(Long requestId, Long documentId,
            boolean mergeDocument) throws CvqException, PermissionException {

        Request request = requestDAO.findById(requestId);

        Collection<String> authorizedRequestTypesLabels =
            requestExternalService.getRequestTypesForExternalService(SecurityContext.getCurrentExternalService());

        // FIXME move it in DocumentContextCheckAspect
        // Check external service permissions wrt configured request types labels
        String requestTypeLabel = request.getRequestType().getLabel();
        if (!authorizedRequestTypesLabels.contains(requestTypeLabel)) {
            throw new PermissionException(this.getClass(), "getAssociatedFullDocuments",
                    new ContextType[] {ContextType.EXTERNAL_SERVICE}, ContextPrivilege.READ, "");
        }

        GetDocumentResponseDocument getDocumentResponseDocument =
            GetDocumentResponseDocument.Factory.newInstance();
        
        GetDocumentResponse getDocumentResponse = 
            getDocumentResponseDocument.addNewGetDocumentResponse();

        RequestExternalAction est = new RequestExternalAction(new Date(), requestId, "libredemat", 
                null, SecurityContext.getCurrentExternalService(), RequestExternalAction.Status.SENT);

        if (documentId != null) {
            // check the document is really associated to the request
            // someone could try to cheat with us
            boolean isDocumentReallyAssociated = false;
            for (RequestDocument requestDocument : request.getDocuments()) {
                if (requestDocument.getDocumentId().equals(documentId)) {
                    isDocumentReallyAssociated = true;
                    break;
                }
            }
            if (!isDocumentReallyAssociated)
                throw new PermissionException(this.getClass(), "getAssociatedDocument", 
                        new ContextType[] {ContextType.EXTERNAL_SERVICE}, ContextPrivilege.READ, "");

            Document document = documentService.getById(documentId);

            String message = 
                translationService.translate("externalservice.trace.sent.document", 
                        new Object[] { document.getId()}
                );
            est.setMessage(message);
            est.getComplementaryData().put("nature", "document");

            // Check if the document contains pages
            if (document.getDatas().isEmpty()) {
                requestExternalActionService.addTrace(est);
                return getDocumentResponseDocument;
            }

            if (mergeDocument) {
                documentService.mergeDocumentBinary(document);
                getDocumentResponse.addDocumentBinary(document.getDatas().get(0).getData());
            } else {
                for (int i = 0; i < document.getDatas().size(); i++) {
                    getDocumentResponse.addDocumentBinary(document.getDatas().get(i).getData());
                }
            }

        } else {
            byte[] pdf = requestSearchService.getCertificate(request.getId());
            getDocumentResponse.addDocumentBinary(pdf);

            String message = translationService.translate("externalservice.trace.sent.summary");
            est.setMessage(message);
            est.getComplementaryData().put("nature", "summary");
        }

        requestExternalActionService.addTrace(est);

        return getDocumentResponseDocument;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Document> getProvidedNotAssociatedDocumentsByType(final Long requestId, final Long documentTypeId)
            throws CvqException {
        Request request = requestDAO.findById(requestId);
        List<Document> result = documentService.getProvidedDocuments(
                documentTypeService.getDocumentTypeById(documentTypeId),
                SecurityContext.getCurrentEcitizen().getHomeFolder().getId(), null);
        Iterator<Document> it = result.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            for (RequestDocument requestDocument : request.getDocuments())
                if (doc.getId().equals(requestDocument.getDocumentId())) {
                    it.remove();
                    break;
                }
        }
        return result;
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Boolean hasAllDocuments(Request request)
        throws CvqException {
        Set<org.libredemat.business.document.DocumentType> docs = requestTypeService.getAllowedDocuments(request.getRequestType().getId());
        if (docs == null || docs.isEmpty()) return null;
        for (org.libredemat.business.document.DocumentType type : docs) {
            if (getAssociatedDocumentsByType(request.getId(), type.getId()).isEmpty()) return false;
        }
        return true;
    }

    private void updateLastModificationInformation(Request request) {
        request.setLastModificationDate(new Date());
        request.setLastInterveningUserId(SecurityContext.getCurrentUserId());
        requestDAO.update(request);
    }

    @Override
    public void onApplicationEvent(RequestEvent requestEvent) {
        logger.debug("onApplicationEvent() - " + requestEvent.getEvent() + " listen ");
        try {
            if (requestEvent.getEvent().equals(RequestEvent.EVENT_TYPE.REQUEST_CREATED))
                onRequestCreated(requestEvent.getRequest());
            else if (requestEvent.getEvent().equals(RequestEvent.EVENT_TYPE.REQUEST_DELETED))
                onRequestDeleted(requestEvent.getRequest());
        } catch (CvqException e) {
            logger.error("onApplicationEvent() : " + requestEvent.getEvent() + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onRequestCreated(Request request) throws CvqException {
        Iterator<RequestDocument> it = request.getDocuments().iterator();
        while (it.hasNext()) {
            RequestDocument rd = it.next();
            Document d = documentService.getById(rd.getDocumentId());
            if (d != null) {
                if (DocumentState.DRAFT.equals(d.getState())) {
                    documentService.pending(d.getId());
                }
            } else {
                it.remove();
            }
        }
    }

    private void onRequestDeleted(Request request) {
        Iterator<RequestDocument> it = request.getDocuments().iterator();
        while (it.hasNext()) {
            RequestDocument rd = it.next();
            Document d = documentService.getById(rd.getDocumentId());
            if (DocumentState.DRAFT.equals(d.getState())) {
                it.remove();
                documentService.delete(d.getId());
            }
        }
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setDocumentService(IDocumentService documentService) {
        this.documentService = documentService;
    }

    public void setRequestExternalService(IRequestExternalService requestExternalService) {
        this.requestExternalService = requestExternalService;
    }

    public void setRequestSearchService(IRequestSearchService requestSearchService) {
        this.requestSearchService = requestSearchService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setRequestExternalActionService(
            IRequestExternalActionService requestExternalActionService) {
        this.requestExternalActionService = requestExternalActionService;
    }

    public void setDocumentTypeService(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }
}
