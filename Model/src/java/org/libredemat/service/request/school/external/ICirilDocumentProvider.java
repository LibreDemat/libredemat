package org.libredemat.service.request.school.external;

import java.util.Map;
import org.libredemat.exception.CvqException;

public interface ICirilDocumentProvider {
    public org.jdom.Document getDocumentsOfCirilNetEnfance(Long homeFolderId) throws CvqException;
}
