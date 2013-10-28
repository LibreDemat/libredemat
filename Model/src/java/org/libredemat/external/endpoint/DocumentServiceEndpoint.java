package org.libredemat.external.endpoint;

import org.libredemat.security.PermissionException;
import org.libredemat.service.request.IRequestDocumentService;
import org.springframework.oxm.Marshaller;

import org.libredemat.GetDocumentResponseDocument.GetDocumentResponse;
import org.libredemat.GetDocumentRequestDocument.GetDocumentRequest;
import org.libredemat.GetDocumentRequestDocument;
import org.libredemat.GetDocumentResponseDocument;

public class DocumentServiceEndpoint extends SecuredServiceEndpoint {

    private IRequestDocumentService requestDocumentService;

    public static final String noPermissions = "Access denied! No permissions granted";
    public static final String notFound = "Request or document not found"; 

    public DocumentServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected Object invokeInternal(Object request) throws Exception {

        GetDocumentRequest getDocumentRequest =
            ((GetDocumentRequestDocument)request).getGetDocumentRequest();

        try {

            GetDocumentResponseDocument getDocumentResponseDocument = 
                requestDocumentService.getAssociatedDocument(getDocumentRequest.getRequestId(), 
                    getDocumentRequest.isSetDocumentId() ? getDocumentRequest.getDocumentId() : null, 
                    getDocumentRequest.getMergeDocument());
            if (getDocumentResponseDocument != null) {
                return getDocumentResponseDocument.getGetDocumentResponse();
            } else {
                getDocumentResponseDocument =
                    GetDocumentResponseDocument.Factory.newInstance();
                GetDocumentResponse getDocumentResponse =
                    getDocumentResponseDocument.addNewGetDocumentResponse();
                  getDocumentResponse.setError(notFound);

                  return getDocumentResponse;
            }
        } catch (PermissionException pe) {
          GetDocumentResponseDocument getDocumentResponseDocument =
              GetDocumentResponseDocument.Factory.newInstance();
          GetDocumentResponse getDocumentResponse = 
              getDocumentResponseDocument.addNewGetDocumentResponse();
            getDocumentResponse.setError(noPermissions);

            return getDocumentResponse;
        }
    }

    public void setRequestDocumentService(IRequestDocumentService requestDocumentService) {
        this.requestDocumentService = requestDocumentService;
    }
}
