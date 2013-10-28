package org.libredemat.plugins.externalservices.edemande.service;

import org.libredemat.business.document.Document;
import org.libredemat.business.request.school.StudyGrantRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.document.IDocumentTypeService;
import org.libredemat.service.request.RequestTestCase;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.service.request.school.impl.StudyGrantRequestService;
import org.springframework.beans.factory.annotation.Autowired;


public class EdemandeServiceTest extends RequestTestCase {

    private IRequestExternalService requestExternalService;

    @Autowired
    private StudyGrantRequestService studyGrantRequestService;

    @Override
    public void onSetUp() throws Exception {
        super.onSetUp();
        requestExternalService = getApplicationBean("requestExternalService");
    }

    public void testChargerTypeDemande() throws Exception {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        // fill and create the request
        //////////////////////////////

        StudyGrantRequest request = (StudyGrantRequest)requestWorkflowService.getSkeletonRequest(
                studyGrantRequestService.getLabel());
        request.setRequesterId(SecurityContext.getCurrentUserId());
        request.setHomeFolderId(fake.id);
        request.setSubjectId(fake.childId);
        Document document = new Document();
        document.setDocumentType(documentTypeService.getDocumentTypeByType(IDocumentTypeService.SCHOOL_CERTIFICATE_TYPE));
        document.setHomeFolderId(fake.id);
        document.setIndividualId(request.getSubjectId());
        Long documentId = documentService.create(document);
        //DocumentBinary documentBinary = new DocumentBinary();
        //File file = getResourceFile("zenexity.png");
        //byte[] data = new byte[(int) file.length()];
        //FileInputStream fis = new FileInputStream(file);
        //fis.read(data);
        //documentBinary.setData(data);
        //iDocumentService.addPage(documentId, documentBinary);
        //document.setDatas(null);
        Long requestId =
            requestWorkflowService.create(request, null);
        requestDocumentService.addDocument(requestId, documentId);

        try {
            requestExternalService.sendRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

