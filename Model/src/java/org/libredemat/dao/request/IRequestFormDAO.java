package org.libredemat.dao.request;

import java.util.List;

import org.libredemat.business.request.RequestForm;
import org.libredemat.business.request.RequestFormType;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author bor@zenexity.fr
 */
public interface IRequestFormDAO extends IJpaTemplate<RequestForm,Long> {

    /**
     * Look up a request form by form type and request label.
     */
    RequestForm findByTypeAndRequest(final RequestFormType requestFormType, 
            final String requestLabel);
    
    /**
     * Look up a request form list by form type and request type id.
     */
    List<RequestForm> findByTypeAndRequestTypeId(final RequestFormType requestFormType,
            final long requestTypeId);

    /**
     * Look up a request form list by form type.
     * Hack Inexine
     * Frederic Fabre - 20/11/2012
     *
     * @param requestFormType request form type searched
     *
     * @return requestforms list
     *
     */
    List<RequestForm> findByType(final RequestFormType requestFormType);
}
