package org.libredemat.service.request;

import java.io.IOException;
import java.util.Collection;

import org.codehaus.groovy.control.CompilationFailedException;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestDocument;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.annotation.IsRequest;

import com.lowagie.text.DocumentException;


/**
 * Service dedicated to various pdf files generation.
 *
 * @author bor@zenexity.fr
 */
public interface IRequestPdfService {

    /**
     * Generate a PDF representation of a request.
     */
    byte[] generateCertificate(Request request)
        throws CvqException;

    byte[] generateArchive(@IsRequest Long requestId)
        throws CvqException, CompilationFailedException, ClassNotFoundException, IOException,
            DocumentException;

    public byte[] generateDocumentsArchive(Collection<RequestDocument> requestDocuments)
        throws CvqException;
}
