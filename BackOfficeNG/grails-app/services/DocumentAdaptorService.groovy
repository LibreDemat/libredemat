import fr.cg95.cvq.business.document.DocumentState;

import fr.cg95.cvq.service.users.IHomeFolderDocumentService;

import fr.cg95.cvq.exception.CvqModelException;

import fr.cg95.cvq.business.request.Request
import fr.cg95.cvq.business.document.Document
import fr.cg95.cvq.business.document.ContentType
import fr.cg95.cvq.business.document.DocumentAction;
import fr.cg95.cvq.business.document.DocumentBinary
import fr.cg95.cvq.business.document.DocumentType
import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.request.IRequestDocumentService
import fr.cg95.cvq.service.request.IRequestTypeService
import fr.cg95.cvq.service.document.IDocumentService
import fr.cg95.cvq.service.document.IDocumentTypeService
import fr.cg95.cvq.util.UserUtils

public class DocumentAdaptorService {

    def messageSource
    RequestAdaptorService requestAdaptorService

    IRequestTypeService requestTypeService
    IRequestDocumentService requestDocumentService
    IDocumentService documentService
    IDocumentTypeService documentTypeService
    IHomeFolderDocumentService homeFolderDocumentService

    def getDocumentTypes(Request rqt) {
        return getDocumentTypes(rqt.requestType.id)
    }

    def getDocumentTypes(Long requestTypeId) {
        def documentTypes = requestTypeService.getAllowedDocuments(requestTypeId)
        def result = []
        documentTypes.each {
            def docType = [
                'id':it.id,
                'name': messageSource.getMessage(CapdematUtils.adaptDocumentTypeName(it.name),null,SecurityContext.currentLocale),
            ]
            result.add(docType)
        }
        result = result.sort { it.name }
        return result
    }

    def getDocumentsByType(Request rqt) {
        def documentTypes = requestTypeService.getAllowedDocuments(rqt.requestType.id)
        def result = [:]
        def documentTypeList = []
        documentTypes.each {
            def docType = [
                'id':it.id,
                'name': messageSource.getMessage(CapdematUtils.adaptDocumentTypeName(it.name),null,SecurityContext.currentLocale),
                'associated': requestDocumentService.getAssociatedDocumentsByType(rqt.id, it.id),
                'provided': SecurityContext.currentEcitizen ? requestDocumentService.getProvidedNotAssociatedDocumentsByType(rqt.id, it.id) : []
            ]
            documentTypeList.add(docType)
        }
        documentTypeList = documentTypeList.sort { it.name }
        documentTypeList.each {
            result[it.id] = it
            if (it.associated.isEmpty() && rqt.stepStates.get('document') != null) {
                rqt.stepStates.document.state = "uncomplete"
                rqt.stepStates.document.errorMsg = null
            }
        }
        return result
    }

    def getDocument(id) {
        def doc = documentService.getById(id)
        return adaptDocument(doc)
    }

    def adaptDocument(Document doc) {
        def result = [
            'id' : doc.id,
            'ecitizenNote' : doc.ecitizenNote,
            'datas' : [],
            'documentType' : doc.documentType
        ]
        def pageNumber = 0
        doc.datas.each { it ->
            result['datas'].add(['id': it.id, 'pageNumber': pageNumber++, 'extension': it.contentType.extension])
        }
        return result
    }

    def adaptDocumentAction(DocumentAction action) {
        def resultingState = null
        if (DocumentAction.Type.STATE_CHANGE.equals(action.type))
            resultingState = CapdematUtils.adaptCapdematEnum(action.resultingState, "document.state")
        return [
            'id': action.id,
            'note': action.note,
            'date': action.date,
            'type' : CapdematUtils.adaptCapdematEnum(action.type, "documentAction.type"),
            'username' : UserUtils.getDisplayName(action.userId),
            'resultingState': resultingState
        ]
    }

    def adaptDocumentType(Long id) {
        def docType = documentTypeService.getDocumentTypeById(id)
        return ['id':docType.id, 'i18nKey':CapdematUtils.adaptDocumentTypeName(docType.name)]
    }

    /**
     * For a given document type, return documents linked to the home folder and documents which could be linked.
     *
     * @return [
     *      'linked' : documents linked to the home folder.
     *      'available' : documents which could be linked.
     * ]
     */
    def homeFolderDocumentsForType(Long homeFolderId, Long documentTypeId) {
        def documentType = documentTypeService.getDocumentTypeById(documentTypeId)
        def linked
        def all
        if (homeFolderId) {
            linked = homeFolderDocumentService.documentsLinkedToHomeFolder(homeFolderId, documentType)
            all = documentService.getProvidedDocuments(documentType, homeFolderId, null)
        }
        return [
            'linked' : linked,
            'available' : all?.minus(linked)
        ]
    }

    def homeFolderDocumentsByType(Long homeFolderId) {
        def wished = homeFolderDocumentService.wishedDocumentTypes()
        def documentTypeList = []
        wished.each {
            def documents = homeFolderDocumentsForType(homeFolderId, it.id)
            def type = [
                'id':it.id,
                'name': messageSource.getMessage(CapdematUtils.adaptDocumentTypeName(it.name),null,SecurityContext.currentLocale),
                'linked': documents.linked,
                'linkedAndInvalid': documents.linked.findAll { [DocumentState.OUTDATED, DocumentState.REFUSED].contains(it.state) },
                'available': documents.available
            ]
            documentTypeList.add(type)
        }
        def result = [:]
        documentTypeList = documentTypeList.sort { it.name }
        documentTypeList.each { result[it.id] = it }
        return result
    }
}
