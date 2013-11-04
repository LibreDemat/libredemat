package org.libredemat.external.endpoint;

import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;


/**
 * Extends base payload endpoint functionalities. Verifies endpoint authentication status.
 *
 * @author vba@zenexity.fr
 */
public abstract class SecuredServiceEndpoint extends AbstractMarshallingPayloadEndpoint {

    public SecuredServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void onMarshalResponse(MessageContext messageContext, Object requestObject,
            Object responseObject) {
        
        super.onMarshalResponse(messageContext, requestObject, responseObject);
    }

    @Override
    protected boolean onUnmarshalRequest(MessageContext messageContext, Object requestObject)
            throws Exception {
        
        if (SecurityContext.getCurrentExternalService() == null)
            throw new CvqException("It's necessary to be logged to perform this task");
        
        return super.onUnmarshalRequest(messageContext, requestObject);
    }
}
