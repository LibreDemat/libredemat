package org.libredemat.service.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.business.document.Document;
import org.libredemat.exception.CvqDisabledFunctionalityException;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.document.IDocumentTypeService;

import static org.junit.Assert.*;


public class DocumentDigitalizationTest extends DocumentTestCase {

    @Test
    public void testDocumentDigitalization() throws CvqException, IOException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        // create a document
        Document doc = new Document();
        doc.setDocumentType(documentTypeService.getDocumentTypeByType(IDocumentTypeService.IDENTITY_RECEIPT_TYPE));
        doc.setHomeFolderId(fake.id);
        Long docId = documentService.create(doc);

        // add binary data
        File file = getResourceFile("health_notebook.jpg");
        byte[] data = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(data);
        documentService.addPage(docId, data);
        
        LocalAuthority la = SecurityContext.getCurrentSite();
        la.setDocumentDigitalizationEnabled(false);
        try {
            documentService.addPage(docId, data);
            fail("should have thrown an exception");
        } catch (CvqDisabledFunctionalityException cdfe) {
            // that was expected
        }
        la.setDocumentDigitalizationEnabled(true);
    }
}
