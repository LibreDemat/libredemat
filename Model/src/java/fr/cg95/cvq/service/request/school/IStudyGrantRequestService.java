package fr.cg95.cvq.service.request.school;

import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.service.request.IRequestService;
import fr.cg95.cvq.service.request.annotation.IsRequest;

/**
 * @author Jean-Sébastien Bour (jsb@zenexity.fr)
 */
public interface IStudyGrantRequestService extends IRequestService {

    public final String SERVICE_NAME = "studyGrantRequestService";

    /**
     * Set the Edemande ID of this request
     */
    void setEdemandeId(@IsRequest final Long requestId, final String edemandeId)
        throws CvqException;

    /**
     * Set the Edemande ID of the account holder of this request
     */
    void setAccountHolderEdemandeId(@IsRequest final Long requestId, final String accountHolderEdemandeId)
        throws CvqException;
}
