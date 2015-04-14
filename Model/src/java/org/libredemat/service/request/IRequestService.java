package org.libredemat.service.request;

import java.util.Map;

import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;
import org.springframework.context.ApplicationListener;


/**
 * High level service interface to deal with requests.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public interface IRequestService extends ApplicationListener<LibreDematEvent> {

    /////////////////////////////////////
    // Methods handled by the base class
    /////////////////////////////////////

    /**
     * Return a string used to uniquely identify the service.
     */
    String getLabel();

    /**
     * Return the file name of local referential data specific to this request type (or null if
     * none defined).
     */
    String getLocalReferentialFilename();

    /**
     * Return the file name of external referential data specific to this request type (or null
     * if not defined)
     */
    String getExternalReferentialFilename();

    /**
     * Number of months before requests are filed (between 1 and 36 months)
     */
    int getFilingDelay();

    /**
     * Whether documents must be included in archives
     */
    boolean isArchiveDocuments();

    /**
     * Whether the request type handled by current service authorizes creation operation without 
     * having already an account.
     */
    boolean supportUnregisteredCreation();
    
    /**
     * Return the subject policy supported by the current service, one of
     * {@link IRequestWorkflowService#SUBJECT_POLICY_NONE}, {@link IRequestWorkflowService#SUBJECT_POLICY_INDIVIDUAL},
     * {@link IRequestWorkflowService#SUBJECT_POLICY_ADULT} or {@link IRequestWorkflowService#SUBJECT_POLICY_CHILD}.
     * 
     * If not overrided in the service configuration, defaults to
     * {@link IRequestWorkflowService#SUBJECT_POLICY_NONE}.
     *   
     */
    String getSubjectPolicy();
    
    /**
     * Whether the request type handled by current service is of registration
     * kind.
     */
    boolean isOfRegistrationKind();
    
    /**
     *  Return true if the request is restartable for the same subject, 
     *  without waiting the next season.
     */
    boolean getSupportMultiple();

    String getDefaultDisplayGroup();

    ///////////////////////////////////////////////////////////
    // Methods that must be overridden by implementing services
    ///////////////////////////////////////////////////////////

    /**
     * Chain of responsabilities pattern.
     */
    boolean accept(Request request);

    /**
     * Return a fresh new request object of the type managed by the implementing class.
     * This method must be implemented by classes implementing this interface.
     */
    Request getSkeletonRequest();
    
    ///////////////////////////////////////////////////////////
    // Methods that may be overridden by implementing services
    ///////////////////////////////////////////////////////////

    /**
     * Hook called after common business checks and before persisting the request,
     * when the ecitizen submits it.
     * 
     * Can be used to perform specific business checks or logic.
     */
    void onRequestIssued(Request request) throws CvqException;

    void onRequestModified(Request request) throws CvqException;

    void onRequestCompleted(Request request) throws CvqException;

    /**
     * Hook called before validating the request.
     * 
     * Can be used to perform specific business checks or logic.
     */
    void onRequestValidated(Request request) throws CvqException;

    void onRequestCancelled(Request request) throws CvqException;
    
    void onRequestRejected(Request request) throws CvqException;

    /**
     * Hook called after a validated payment is received.
     * 
     * @return true if associated request has to be validated.
     */
    boolean onPaymentValidated(Request request, String paymentReference) throws CvqException;
    
    /**
     * Hook called after a refused payment is received.
     * 
     * @return true if associated request has to be rejected.
     */
    boolean onPaymentRefused(Request request) throws CvqException;

    /**
     * Hook called after a cancelled payment is received.
     * 
     * @return true if associated request has to be cancelled.
     */
    boolean onPaymentCancelled(Request request) throws CvqException;

    /**
     * Hook called just after the request has been sent to an external service.
     * 
     * @param sendRequestResult the result returned by the external service.
     */
    void onExternalServiceSendRequest(Request request, String sendRequestResult) 
        throws CvqException;

    /**
     * Fetch an external business referential.
     * Must be used only for simple use case.
     */
    Map<String,Object> getBusinessReferential() throws CvqException;

    public boolean isPayableAtValidateTS(Request request);

    public boolean acceptPayment(Request request);

    boolean isSubjectPolicySpecific(Long individualId, Long requestId);
}
