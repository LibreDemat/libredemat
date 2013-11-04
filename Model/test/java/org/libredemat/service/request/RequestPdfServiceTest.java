package org.libredemat.service.request;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.libredemat.business.request.RequestDocument;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.document.DocumentTestCase;
import org.libredemat.service.request.IRequestPdfService;
import org.springframework.beans.factory.annotation.Autowired;


public class RequestPdfServiceTest extends DocumentTestCase {
    @Autowired
    private IRequestPdfService requestPdfService;
    
    @Test
    public void testGenerateDocumentsArchive()
        throws CvqException, IOException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);
        
        List<RequestDocument> documents = new ArrayList<RequestDocument>();
        
        // with pdf documents
        RequestDocument rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmePdfDocument());
        documents.add(rqtDoc);
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmePdfDocument());
        documents.add(rqtDoc);
        continueWithNewTransaction();
        
        byte[] result = requestPdfService.generateDocumentsArchive(documents);
        
        // test
        assertNotNull("Error", result);
        
        // with image documents
        continueWithNewTransaction();
        
        documents.clear();
        
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmeImageDocument());
        documents.add(rqtDoc);
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmeImageDocument());
        documents.add(rqtDoc);
        
        result = requestPdfService.generateDocumentsArchive(documents);
        
        continueWithNewTransaction();
        
        // test
        assertNotNull("Error", result);
        
        // with both : image then pdf documents
        continueWithNewTransaction();
        
        documents.clear();
        
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmeImageDocument());
        documents.add(rqtDoc);
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmePdfDocument());
        documents.add(rqtDoc);
        
        result = requestPdfService.generateDocumentsArchive(documents);
        
        continueWithNewTransaction();
        
         //test
        assertNotNull("Error", result);
        
     // with both : pdf then image documents
        continueWithNewTransaction();
        
        documents.clear();
        
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmePdfDocument());
        documents.add(rqtDoc);
        rqtDoc = new RequestDocument();
        rqtDoc.setDocumentId(gimmeImageDocument());
        documents.add(rqtDoc);
        
        result = requestPdfService.generateDocumentsArchive(documents);
        
        continueWithNewTransaction();
        
         //test
        assertNotNull("Error", result);
    }
}
