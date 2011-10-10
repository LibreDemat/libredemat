package fr.cg95.cvq.dao.document;

import java.util.Hashtable;
import java.util.List;

import fr.cg95.cvq.business.document.Document;
import fr.cg95.cvq.business.document.DocumentType;
import fr.cg95.cvq.business.users.HomeFolder;
import fr.cg95.cvq.dao.jpa.IJpaTemplate;

/**
 * @author bor@zenexity.fr
 */
public interface IDocumentDAO extends IJpaTemplate<Document, Long> {

    /**
     * Return the documents who belong to the given home folder with the specified type.
     */
    List<Document> listProvidedDocuments(final DocumentType documentType,
        final Long homeFolderId, final Long individualId);

    /**
     * Return the {@link Document documents} belonging to a given home folder.
     */
    List<Document> listByHomeFolder(final Long homeFolderId, int max);

    /**
     * Return the {@link Document documents} belonging to the given individual.
     */
    List<Document> listByIndividual(final Long individualId);

    /**
     * Return the IDs of outdated documents.
     */
    List<Long> listOutdated();

    /**
     * Return the {@link Document documents} that response to passed criterias.
     */
    List<Document> search(Hashtable<String,Object> searchParams,int max,int offset);
    
    Integer searchCount(Hashtable<String,Object> searchParams);

    /**
     * @return the IDs of documents which contentType or preview isn't set
     */
    List<Long> listByMissingComputedValues();

    /**
     * Return the list of documents linked to the home folder, filtered with the document type.
     * @param homeFolder
     * @param documentType
     */
    public List<Document> linkedToHomeFolder(HomeFolder homeFolder, DocumentType documentType);
}
