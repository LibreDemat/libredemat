package org.libredemat.external.endpoint;

import org.libredemat.security.PermissionException;
import org.libredemat.service.request.IRequestDocumentService;
import org.springframework.oxm.Marshaller;

import org.libredemat.GetDocumentListRequestDocument;
import org.libredemat.GetDocumentListResponseDocument;
import org.libredemat.GetDocumentListRequestDocument.GetDocumentListRequest;
import org.libredemat.GetDocumentListResponseDocument.GetDocumentListResponse;

public class DocumentListServiceEndpoint extends SecuredServiceEndpoint {

    private IRequestDocumentService requestDocumentService;

    private final String noPermissions = "Access denied! No permissions granted";
    private final String notFound = "Request not found";

    public DocumentListServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected Object invokeInternal(Object request) throws Exception {

        GetDocumentListRequest getDocumentListRequest =
            ((GetDocumentListRequestDocument)request).getGetDocumentListRequest();

        try {
            GetDocumentListResponseDocument getDocumentListResponseDocument =
                requestDocumentService.getAssociatedFullDocuments(getDocumentListRequest.getRequestId());
            if (getDocumentListResponseDocument != null) {
                return getDocumentListResponseDocument.getGetDocumentListResponse();
            } else {
                getDocumentListResponseDocument =
                    GetDocumentListResponseDocument.Factory.newInstance();
                GetDocumentListResponse getDocumentListResponse =
                    getDocumentListResponseDocument.addNewGetDocumentListResponse();
                  getDocumentListResponse.setError(notFound);

                  return getDocumentListResponse;
            }
        } catch (PermissionException pe) {
          GetDocumentListResponseDocument getDocumentListResponseDocument =
              GetDocumentListResponseDocument.Factory.newInstance();
          GetDocumentListResponse getDocumentListResponse =
              getDocumentListResponseDocument.addNewGetDocumentListResponse();
            getDocumentListResponse.setError(noPermissions);

            return getDocumentListResponse;
        }
    }

    public void setRequestDocumentService(IRequestDocumentService requestDocumentService) {
        this.requestDocumentService = requestDocumentService;
    }
}
