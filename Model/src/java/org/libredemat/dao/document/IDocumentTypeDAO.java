package org.libredemat.dao.document;

import java.util.List;

import org.libredemat.business.document.DocumentType;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author bor@zenexity.fr
 */
public interface IDocumentTypeDAO extends IJpaTemplate<DocumentType, Long> {

    /**
     * Lookup a document type by type.
     */
    DocumentType findByType(final Integer typeId);

    /**
     * Return the list of all known documents types.
     */
    List<DocumentType> listAll();
    Integer getNextTypeId();

    Boolean isRequiredByARequest(Long documentTypeId);
    Boolean isUsedInARequest(Long documentTypeId);
    Boolean nameAlreadyInUse(String name);
}
