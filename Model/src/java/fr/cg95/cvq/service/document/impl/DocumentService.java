package fr.cg95.cvq.service.document.impl;

import java.util.*;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;

import fr.cg95.cvq.business.authority.LocalAuthority;
import fr.cg95.cvq.business.document.ContentType;
import fr.cg95.cvq.business.document.Document;
import fr.cg95.cvq.business.document.DocumentAction;
import fr.cg95.cvq.business.document.DocumentBinary;
import fr.cg95.cvq.business.document.DocumentState;
import fr.cg95.cvq.business.document.DocumentType;
import fr.cg95.cvq.business.document.DocumentTypeValidity;
import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.UsersEvent;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.dao.document.IDocumentDAO;
import fr.cg95.cvq.dao.document.IDocumentTypeDAO;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.exception.CvqDisabledFunctionalityException;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqInvalidTransitionException;
import fr.cg95.cvq.exception.CvqModelException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry;
import fr.cg95.cvq.service.document.IDocumentService;
import fr.cg95.cvq.util.translation.ITranslationService;

/**
 * Implementation of the {@link IDocumentService} service.
 *
 * @author bor@zenexity.fr
 */
public class DocumentService implements IDocumentService, ApplicationListener<UsersEvent> {

    static Logger logger = Logger.getLogger(DocumentService.class);

    protected ILocalAuthorityRegistry localAuthorityRegistry;
    
    protected IDocumentDAO documentDAO;
    protected IDocumentTypeDAO documentTypeDAO;
    private ITranslationService translationService;
    
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.READ)
    public Document getById(final Long id)
        throws CvqException, CvqObjectNotFoundException {
        return (Document) documentDAO.findById(Document.class, id);
    }

    /**
     * Compute a default end validity date for the given document, according to its
     * type.
     */
    private void computeEndValidityDate(Document document) {

        DocumentType docType = document.getDocumentType();
        DocumentTypeValidity docTypeValidity = docType.getValidityDurationType();

        Calendar calendar = new GregorianCalendar();
        Date currentDate = new Date();
        calendar.setTime(currentDate);

        if (docTypeValidity.equals(DocumentTypeValidity.UNLIMITED)) {
            document.setEndValidityDate(null);
        } else if (docTypeValidity.equals(DocumentTypeValidity.YEAR)) {
            Integer duration = docType.getValidityDuration();
            calendar.add(Calendar.YEAR, duration.intValue());
            document.setEndValidityDate(calendar.getTime());
            logger.debug("Set default end validity date to "
                         + document.getEndValidityDate());
        } else if (docTypeValidity.equals(DocumentTypeValidity.MONTH)) {
            Integer duration = docType.getValidityDuration();
            calendar.add(Calendar.MONTH, duration.intValue());
            document.setEndValidityDate(calendar.getTime());
            logger.debug("Set default end validity date to "
                         + document.getEndValidityDate());
        } else if (docTypeValidity.equals(DocumentTypeValidity.END_YEAR)) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            document.setEndValidityDate(calendar.getTime());
            logger.debug("Set default end validity date to "
                         + document.getEndValidityDate());
        } else if (docTypeValidity.equals(DocumentTypeValidity.END_SCHOOL_YEAR)) {
            if (calendar.get(Calendar.MONTH) > Calendar.JUNE)
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            calendar.set(Calendar.MONTH, Calendar.JUNE);
            calendar.set(Calendar.DAY_OF_MONTH, 30);
            document.setEndValidityDate(calendar.getTime());
            logger.debug("Set default end validity date to "
                         + document.getEndValidityDate());
        }
    }

    public void checkDocumentsValidity()
        throws CvqException {

        localAuthorityRegistry.browseAndCallback(this, "checkLocalAuthDocumentsValidity", null);
    }
    
    @Context(types = {ContextType.SUPER_ADMIN})
    public void checkLocalAuthDocumentsValidity()
        throws CvqException {

        logger.debug("checkLocalAuthDocumentsValidity() dealing with " 
            + SecurityContext.getCurrentSite().getName());
        
        Date currentDate = new Date();
        List<Document> wholeList = new ArrayList<Document>();

        // get all documents whose state is PENDING, CHECKED or VALIDATED
        wholeList.addAll(documentDAO.listByState(DocumentState.PENDING));
        wholeList.addAll(documentDAO.listByState(DocumentState.CHECKED));
        wholeList.addAll(documentDAO.listByState(DocumentState.VALIDATED));

        // if end validity date is reached, set them outofdate
        for (Document doc : wholeList) {
            if (doc.getEndValidityDate() != null
                && doc.getEndValidityDate().before(currentDate)) {
                logger.debug("checkLocalAuthDocumentsValidity() document " + doc.getId() 
                        + " has reached its end validity date (" 
                        + doc.getEndValidityDate() + ") !");
                doc.setState(DocumentState.OUTDATED);
                documentDAO.update(doc);

                addActionTrace(STATE_CHANGE_ACTION, DocumentState.OUTDATED, doc);
            }
        }
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public Long create(Document document)
        throws CvqException, CvqObjectNotFoundException {

        if (document == null)
            throw new CvqException("No document object provided");
        if (document.getDocumentType() == null)
            throw new CvqException("You must provide a type for your document");

        document.setCreationDate(new Date());
        document.setDepositId(SecurityContext.getCurrentUserId());

        computeEndValidityDate(document);

        Long documentId = documentDAO.create(document);

        logger.debug("Created document object with id : " + documentId);

        addActionTrace(CREATION_ACTION, DocumentState.PENDING, document);

        // when creating a new document in FO, we need it to be persisted before rendering the view
        HibernateUtil.getSession().flush();

        return documentId;
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void modify(final Document document)
        throws CvqException {

        if (document == null)
            return;
        documentDAO.update(document);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void delete(final Long id)
        throws CvqException, CvqObjectNotFoundException {

        Document document = getById(id);
        documentDAO.delete(document);

        // when deleting a new document in FO, we need it to be removed from DB before rendering the view
        HibernateUtil.getSession().flush();
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void addPage(final Long documentId, final DocumentBinary documentBinary)
        throws  CvqException {

        checkDocumentDigitalizationIsEnabled();

        try {
            String mimeType = checkNewBinaryData(documentId, documentBinary.getData());
            documentBinary.setContentType(ContentType.forString(mimeType));
            Document document = getById(documentId);
            if(document.getDatas() == null) {
                List<DocumentBinary> dataList = new ArrayList<DocumentBinary>();
                dataList.add(documentBinary);
                document.setDatas(dataList);
            } else
                document.getDatas().add(documentBinary);
            
            documentDAO.update(document);
            addActionTrace(PAGE_ADD_ACTION, null, document);
        } catch (CvqModelException cme) {
            throw new CvqModelException(cme.getI18nKey());
        }
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void modifyPage(final Long documentId, final DocumentBinary documentBinary)
        throws CvqException {

        checkDocumentDigitalizationIsEnabled();
        
        Document document = getById(documentId);
        
            if (document.getDatas().size() == 1) {
                documentDAO.update(documentBinary);
                if (document.getState().equals(DocumentState.OUTDATED)) {
                    document.setState(DocumentState.PENDING);
                    document.setValidationDate(null);
                    documentDAO.update(document);
                    addActionTrace(STATE_CHANGE_ACTION, DocumentState.PENDING, document);
                }
            }        
        addActionTrace(PAGE_EDIT_ACTION, null, document);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void deletePage(final Long documentId, final Integer pageId)
        throws CvqException, CvqObjectNotFoundException {

        checkDocumentDigitalizationIsEnabled();
        
        Document document = getById(documentId);
        DocumentBinary documentBinary = document.getDatas().get(pageId);
        document.getDatas().remove(documentBinary);
        documentDAO.delete(documentBinary);
        documentDAO.update(document);
        
        addActionTrace(PAGE_DELETE_ACTION, null, document);
    }
    
    public  String checkNewBinaryData(final Long documentId, byte[] data)
        throws CvqObjectNotFoundException, CvqException {
        String mimeType = "";
        Document document = getById(documentId);
        try {
            mimeType = Magic.getMagicMatch(data).getMimeType();
        } catch (MagicParseException mpe) {
                throw new CvqModelException("document.file.error.isNotValid");
        } catch (MagicMatchNotFoundException mmnfe) {
                throw new CvqModelException("document.file.error.isNotValid");
        } catch (MagicException me) {
                throw new CvqModelException("document.file.error.isNotValid");
        }
        
        if (ContentType.isAllowContentType(mimeType)) {
            if (document.getDatas() != null && !document.getDatas().isEmpty()) {
                        if (!document.getDatas().get(0).getContentType().equals(ContentType.forString(mimeType)))
                            throw new CvqModelException("document.file.error.contentTypeIsNotSameCompareToOtherPage");
            }
        }
        return mimeType;
    }

    private void checkDocumentDigitalizationIsEnabled() 
        throws CvqDisabledFunctionalityException {
        
        LocalAuthority la = SecurityContext.getCurrentSite();
        if (!la.isDocumentDigitalizationEnabled()) {
            logger.error("checkDocumentDigitalizationIsEnabled() document digitalization is not enabled for site "
                         + la.getName());
            throw new CvqDisabledFunctionalityException();
        }
    }
    
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.READ)
    public Set<DocumentBinary> getAllPages(final Long documentId)
        throws CvqException {

        Document document = getById(documentId);

        if (document.getDatas() == null)
            return new LinkedHashSet<DocumentBinary>();
        else
            return new LinkedHashSet<DocumentBinary>(document.getDatas());
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    private void deleteHomeFolderDocuments(Long homeFolderId) {
        List<Document> documents = getHomeFolderDocuments(homeFolderId, -1);
        for (Document document : documents)
            documentDAO.delete(document);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    private void deleteIndividualDocuments(Long individualId) {
        List<Document> documents = getIndividualDocuments(individualId);
        logger.debug("deleteIndividualDocuments() deleting " + documents.size() + " document(s)");
        for (Document document : documents)
            documentDAO.delete(document);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Document> getProvidedDocuments(final DocumentType docType,
            final Long homeFolderId, final Long individualId)
        throws CvqException {

        if (docType == null)
            throw new CvqException("No document type provided");
        if (homeFolderId == null)
            throw new CvqException("No home folder id provided");

        return documentDAO.listProvidedDocuments(docType.getId(),
                homeFolderId, individualId);
    }
    
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Document> getHomeFolderDocuments(final Long homeFolderId, int maxResults) {

        return documentDAO.listByHomeFolder(homeFolderId, maxResults);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Document> getIndividualDocuments(final Long individualId) {

        return documentDAO.listByIndividual(individualId);
    }
    
    public List<Document> getBySessionUuid(final String sessionUuid) {
        return documentDAO.findBySimpleProperty(Document.class, "sessionUuid", sessionUuid);
    }
    
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.NONE)
    public Integer searchCount(Hashtable<String,Object> searchParams) {
        return documentDAO.searchCount(this.prepareSearchParams(searchParams));
    }
    
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.NONE)
    public List<Document> search(Hashtable<String,Object> searchParams,int max,int offset) {
        return documentDAO.search(this.prepareSearchParams(searchParams),max,offset);
    }
    
    protected Hashtable<String,Object> prepareSearchParams(Hashtable<String,Object> searchParams) {
        
        if (searchParams == null)
            searchParams = new Hashtable<String, Object>();
        
        if (!searchParams.containsKey("homeFolderId") && !searchParams.containsKey("individualId")) {
            Adult user = SecurityContext.getCurrentEcitizen();
            List<Long> individuals = new ArrayList<Long>();
            for(Individual i : user.getHomeFolder().getIndividuals())
                individuals.add(i.getId());
            
            searchParams.put("homeFolderId",user.getHomeFolder().getId());
            searchParams.put("individualId",individuals);
        }
        
        return searchParams;
    }
    
    // Document Workflow related methods
    // TODO : make workflow method private - migrate unit tests
    //////////////////////////////////////////////////////////
    
    @Context(types = {ContextType.AGENT})
    public void updateDocumentState(final Long id, final DocumentState ds, final String message, 
            final Date validityDate)
        throws CvqException, CvqInvalidTransitionException, CvqObjectNotFoundException {
        if (ds.equals(DocumentState.VALIDATED))
            validate(id, validityDate, message);
        else if (ds.equals(DocumentState.CHECKED))
            check(id,message);
        else if (ds.equals(DocumentState.REFUSED))
            refuse(id, message);
        else if (ds.equals(DocumentState.OUTDATED))
            outDated(id);
    }

    @Context(types = {ContextType.AGENT})
    public void validate(final Long id, final Date validityDate, final String message)
        throws CvqException, CvqObjectNotFoundException, CvqInvalidTransitionException {

        Document document = getById(id);
        if (document.getState().equals(DocumentState.VALIDATED))
            return;

        if (document.getState().equals(DocumentState.PENDING)
            || document.getState().equals(DocumentState.CHECKED)) {
            try {
                document.setState(DocumentState.VALIDATED);
                document.setEndValidityDate(validityDate);
                document.setValidationDate(new Date());
                documentDAO.update(document);
            } catch (RuntimeException e) {
                throw new CvqException("Could not validate document " + e.toString());
            }
        } else {
            throw new CvqInvalidTransitionException(
                translationService.translate("document.state."
                    + document.getState().toString().toLowerCase()),
                translationService.translate("document.state."
                    + DocumentState.VALIDATED.toString().toLowerCase()));
        }

        addActionTrace(STATE_CHANGE_ACTION, DocumentState.VALIDATED, document);
    }

    @Context(types = {ContextType.AGENT})
    public void check(final Long id, final String message)
        throws CvqException, CvqObjectNotFoundException, CvqInvalidTransitionException {

        Document document = getById(id);
        if (document.getState().equals(DocumentState.CHECKED))
            return;

        if (!document.getState().equals(DocumentState.PENDING))
            throw new CvqInvalidTransitionException();

        document.setState(DocumentState.CHECKED);
        documentDAO.update(document);
        addActionTrace(STATE_CHANGE_ACTION, DocumentState.CHECKED, document);
    }

    @Context(types = {ContextType.AGENT})
    public void refuse(final Long id, final String message)
        throws CvqException, CvqObjectNotFoundException, CvqInvalidTransitionException {

        Document document = getById(id);
        if (document.getState().equals(DocumentState.REFUSED))
            return;

        if (!document.getState().equals(DocumentState.CHECKED)
            && !document.getState().equals(DocumentState.PENDING))
            throw new CvqInvalidTransitionException();

        document.setState(DocumentState.REFUSED);
        documentDAO.update(document);
    
        addActionTrace(STATE_CHANGE_ACTION, DocumentState.REFUSED, document);
    }

    @Context(types = {ContextType.AGENT})
    public void outDated(final Long id)
        throws CvqException, CvqObjectNotFoundException, CvqInvalidTransitionException {

        Document document = getById(id);
        if (document.getState().equals(DocumentState.OUTDATED))
            return;

        if (document.getState().equals(DocumentState.REFUSED))
            throw new CvqInvalidTransitionException();

        document.setState(DocumentState.OUTDATED);
        documentDAO.update(document);

        addActionTrace(STATE_CHANGE_ACTION, DocumentState.OUTDATED, document);
    }

    @Context(types = {ContextType.AGENT})
    public DocumentState[] getPossibleTransitions(DocumentState ds)
        throws CvqException {

        ArrayList<DocumentState> documentStateList = new ArrayList<DocumentState>();

        if (ds.equals(DocumentState.PENDING)) {
            documentStateList.add(DocumentState.VALIDATED);
            documentStateList.add(DocumentState.CHECKED);
            documentStateList.add(DocumentState.REFUSED);
            documentStateList.add(DocumentState.OUTDATED);
        } else if (ds.equals(DocumentState.VALIDATED)) {
            documentStateList.add(DocumentState.OUTDATED);
        } else if (ds.equals(DocumentState.CHECKED)) {
            documentStateList.add(DocumentState.VALIDATED);
            documentStateList.add(DocumentState.REFUSED);
            documentStateList.add(DocumentState.OUTDATED);
        } else if (ds.equals(DocumentState.REFUSED)) {
            // no more transitions available
        } else if (ds.equals(DocumentState.OUTDATED)) {
            documentStateList.add(DocumentState.PENDING);
        }

        return documentStateList.toArray(new DocumentState[documentStateList.size()]);
    }

    public void addActionTrace(final String label, final DocumentState resultingState,
            final Document document)
        throws CvqException {

        DocumentAction documentAction = new DocumentAction();
        documentAction.setAgentId(SecurityContext.getCurrentUserId());
        documentAction.setLabel(label);
        documentAction.setDate(new Date());
        documentAction.setResultingState(resultingState);

        if (document.getActions() == null) {
            Set<DocumentAction> actionsSet = new HashSet<DocumentAction>();
            actionsSet.add(documentAction);
            document.setActions(actionsSet);
        } else {
            document.getActions().add(documentAction);
        }
        
        documentDAO.update(document);
    }

    public List<DocumentState> getEditableStates() {
        List<DocumentState> result = new ArrayList<DocumentState>();
        
        result.add(DocumentState.PENDING);
        result.add(DocumentState.OUTDATED);
        
        return result;
    }
    
    @Override
    public void onApplicationEvent(UsersEvent homeFolderEvent) {
        logger.debug("onApplicationEvent() got an home folder event of type " + homeFolderEvent.getEvent());
        if (homeFolderEvent.getEvent().equals(UsersEvent.EVENT_TYPE.HOME_FOLDER_DELETE)) {
            logger.debug("onApplicationEvent() gonna delete home folder "
                    + homeFolderEvent.getHomeFolderId());
            deleteHomeFolderDocuments(homeFolderEvent.getHomeFolderId());
        } else if (homeFolderEvent.getEvent().equals(UsersEvent.EVENT_TYPE.INDIVIDUAL_DELETE)) {
            logger.debug("onApplicationEvent() gonna delete individual "
                    + homeFolderEvent.getIndividualId());
            deleteIndividualDocuments(homeFolderEvent.getIndividualId());
        }
    }

    public void deleteUnpersistedSessionDocuments(final String sessionUuid) {
        List<Document> sessionDocuments = getBySessionUuid(sessionUuid);
        for (Document document : sessionDocuments)
            documentDAO.delete(document);
    }

    public void setDocumentDAO(final IDocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public void setDocumentTypeDAO(final IDocumentTypeDAO documentTypeDAO) {
        this.documentTypeDAO = documentTypeDAO;
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }
    
    public void createPreview(DocumentBinary page) {
        
    }
}

