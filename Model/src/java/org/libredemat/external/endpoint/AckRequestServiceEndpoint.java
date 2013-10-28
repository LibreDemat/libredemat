package org.libredemat.external.endpoint;

import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.springframework.oxm.Marshaller;

import org.libredemat.AckRequestType;
import org.libredemat.AckRequestsRequestDocument;
import org.libredemat.AckRequestsResponseDocument;
import org.libredemat.AckRequestsRequestDocument.AckRequestsRequest;
import org.libredemat.AckRequestsResponseDocument.AckRequestsResponse;

/**
 * @author vba@zenexity.fr
 */
public class AckRequestServiceEndpoint extends SecuredServiceEndpoint {

    private IRequestExternalActionService requestExternalActionService;
    public AckRequestServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected Object invokeInternal(Object o) throws Exception {

        AckRequestsResponseDocument responseDocument = AckRequestsResponseDocument.Factory.newInstance();
        AckRequestsResponse response = responseDocument.addNewAckRequestsResponse();
        AckRequestsRequest request = ((AckRequestsRequestDocument)o).getAckRequestsRequest();

        try {
            for (int i=0; i<request.getAckElementsArray().length; i++) {
                AckRequestType type = request.getAckElementsArray()[i];

                RequestExternalAction trace = new RequestExternalAction();
                trace.setKey(type.getRequestId());
                trace.setKeyOwner("libredemat");
                trace.setName(SecurityContext.getCurrentExternalService());

                if (type.getErroneous())
                    trace.setStatus(RequestExternalAction.Status.ERROR);
                else
                    trace.setStatus(RequestExternalAction.Status.ACKNOWLEDGED);

                requestExternalActionService.addTrace(trace);
                response.setAccomplished(true);
            }
        } catch (RuntimeException e) {
            response.setAccomplished(false);
        }
        

        return response;
    }

    public void setRequestExternalActionService(
            IRequestExternalActionService requestExternalActionService) {
        this.requestExternalActionService = requestExternalActionService;
    }
}
