package org.libredemat.service.request.impl;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestType;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.IRequestService;
import org.libredemat.service.request.IRequestTypeService;
import org.libredemat.service.request.IRequestWorkflowService;


/**
 * Partial implementation of the {@link IRequestService}, only provides functionalities
 * common to all request types and default implementations of un-necessary extension points.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public abstract class RequestService implements IRequestService {

    private static Logger logger = Logger.getLogger(RequestService.class);

    protected String localReferentialFilename;
    protected String externalReferentialFilename;
    protected Boolean supportUnregisteredCreation = Boolean.FALSE;
    protected String subjectPolicy = IRequestWorkflowService.SUBJECT_POLICY_NONE;
    protected String label;
    protected Boolean isOfRegistrationKind;
    protected String defaultDisplayGroup;
    protected int filingDelay;
    protected boolean archiveDocuments;
    protected boolean supportMultiple = Boolean.FALSE;

    protected IGenericDAO genericDAO;
    private IRequestTypeService requestTypeService;

    public void init() {
        // empty method to avoid breaking specific services instantiation by spring
    }

    @Override
    public void onRequestIssued(Request request) throws CvqException {
    }

    @Override
    public void onRequestModified(Request request) throws CvqException {
    }

    @Override
    public void onRequestCompleted(Request request) throws CvqException {
    }

    @Override
    public void onRequestValidated(Request request)
        throws CvqException {
    }

    @Override
    public void onRequestCancelled(Request request) throws CvqException {
    }

    @Override
    public void onRequestRejected(Request request) throws CvqException {
    }

    @Override
    public boolean onPaymentValidated(Request request, String paymentReference)
        throws CvqException {
        return false;
    }

    @Override
    public boolean onPaymentRefused(Request request)
        throws CvqException {
        return false;
    }

    @Override
    public boolean onPaymentCancelled(Request request)
        throws CvqException {
        return false;
    }

    @Override
    public void onExternalServiceSendRequest(Request request, String sendRequestResult) throws CvqException {
    }

    @Override
    public Map<String,Object> getBusinessReferential() throws CvqException {
        return Collections.emptyMap();
    }

    @Override
    public void onApplicationEvent(LibreDematEvent e) {}

    @Override
    public String getLocalReferentialFilename() {
        return this.localReferentialFilename;
    }

    public void setLocalReferentialFilename(final String filename) {
        this.localReferentialFilename = filename;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setSupportUnregisteredCreation(String supportUnregisteredCreation) {
        this.supportUnregisteredCreation = Boolean.valueOf(supportUnregisteredCreation);
    }

    @Override
    public boolean supportUnregisteredCreation() {
        Boolean sUnregisteredCreation = null;
        try {
            RequestType rqtType = requestTypeService.getRequestTypeByLabel(label);
            sUnregisteredCreation = rqtType.getSupportUnregisteredCreation();
        } catch (CvqException e) {
            logger.error("Cannot retrieve requestTypeByLabel : " + label +
                    "\n Error: " + e.getMessage());
        }
        return sUnregisteredCreation == null ?
                (supportUnregisteredCreation == null ? false : supportUnregisteredCreation)
                : sUnregisteredCreation;
    }

    public void setExternalReferentialFilename(String externalReferentialFilename) {
        this.externalReferentialFilename = externalReferentialFilename;
    }

    @Override
    public String getExternalReferentialFilename() {
        return externalReferentialFilename;
    }

    @Override
    public String getSubjectPolicy() {
        String sPolicy = null;
        try {
            RequestType rqtType = requestTypeService.getRequestTypeByLabel(label);
            sPolicy = rqtType.getSubjectPolicy();
        } catch (CvqException e) {
            logger.error("Cannot retrieve requestTypeByLabel : " + label +
                    "\n Error: " + e.getMessage());
        }
        return sPolicy == null ? subjectPolicy : sPolicy;
    }

    public void setSubjectPolicy(final String subjectPolicy) {
        this.subjectPolicy = subjectPolicy;
    }

    @Override
    public boolean isOfRegistrationKind() {
        Boolean isOfRegKind = null;
        try {
            RequestType rqtType = requestTypeService.getRequestTypeByLabel(label);
            isOfRegKind = rqtType.getIsOfRegistrationKind();
        } catch (CvqException e) {
            logger.error("Cannot retrieve requestTypeByLabel : " + label +
                    "\n Error: " + e.getMessage());
        }
        return isOfRegKind == null ?
                (isOfRegistrationKind == null ? false : isOfRegistrationKind)
                : isOfRegKind;
    }

    public void setIsOfRegistrationKind(String isOfRegistrationKind) {
        this.isOfRegistrationKind = Boolean.valueOf(isOfRegistrationKind);
    }

    @Override
	public String getDefaultDisplayGroup() {
        return defaultDisplayGroup;
    }

    public void setDefaultDisplayGroup(String defaultDisplayGroup) {
        this.defaultDisplayGroup = defaultDisplayGroup;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    @Override
    public int getFilingDelay() {
        Integer fDelay = null;
        try {
            RequestType rqtType = requestTypeService.getRequestTypeByLabel(label);
            fDelay = rqtType.getFilingDelay();
        } catch (CvqException e) {
            logger.error("Cannot retrieve requestTypeByLabel : " + label +
                    "\n Error: " + e.getMessage());
        }
        return fDelay == null ? filingDelay : fDelay;
    }

    public void setFilingDelay(int filingDelay) {
        if (filingDelay < 1 || filingDelay > 36) {
            throw new RuntimeException("Filing delay must be between 1 and 36 months");
        }
        this.filingDelay = filingDelay;
    }

    @Override
    public boolean isArchiveDocuments() {
        return archiveDocuments;
    }

    public void setArchiveDocuments(boolean archiveDocuments) {
        this.archiveDocuments = archiveDocuments;
    }
    
    @Override
    public boolean getSupportMultiple() {
        Boolean sMultiple = null;
        try {
            RequestType rqtType = requestTypeService.getRequestTypeByLabel(label);
            sMultiple = rqtType.getSupportMultiple();
        } catch (CvqException e) {
            logger.error("Cannot retrieve requestTypeByLabel : " + label +
                    "\n Error: " + e.getMessage());
        }
        return sMultiple == null ? supportMultiple : sMultiple;
    }

    public void setSupportMultiple(boolean supportMultiple) {
        this.supportMultiple = supportMultiple;
    }

    public IRequestTypeService getRequestTypeService() {
        return requestTypeService;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }
}
