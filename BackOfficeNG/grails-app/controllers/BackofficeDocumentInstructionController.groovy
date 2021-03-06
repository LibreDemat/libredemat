import org.libredemat.exception.CvqModelException;

import grails.converters.JSON
import org.libredemat.business.document.Document
import org.libredemat.business.document.ContentType
import org.libredemat.service.document.IDocumentTypeService
import org.libredemat.service.document.IDocumentService
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.request.IRequestDocumentService
import org.libredemat.service.request.IRequestLockService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.business.document.DocumentBinary
import org.libredemat.business.document.DocumentState
import org.libredemat.business.document.DepositOrigin
import org.libredemat.business.document.DepositType
import org.libredemat.business.document.DocumentType
import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestDocument
import org.libredemat.business.document.DocumentAction
import org.libredemat.security.SecurityContext
import org.libredemat.business.authority.Agent

import javax.servlet.http.HttpServletResponse

class BackofficeDocumentInstructionController {
    
    def defaultAction = "edit"
    
    DocumentAdaptorService documentAdaptorService
    
    IDocumentService documentService
    IDocumentTypeService documentTypeService
    IRequestDocumentService requestDocumentService
    IRequestLockService requestLockService
    IRequestTypeService requestTypeService
    IRequestSearchService requestSearchService
    ICategoryService categoryService

    def beforeInterceptor = {
        if (params.requestId)
            requestLockService.tryToLock(params.long('requestId'))
    }

    //FIXME: refactor 'edit' action by using DocumentService API 
    def edit = {
        def document = [actions:[],documentType:[:]]
        Request request = requestSearchService.getById(params.long('requestId'), false)
        Agent agent = SecurityContext.currentAgent;
        def messageLink = message(code:"document.message.showImage")

        if (!params.id || Integer.valueOf(params.id) == 0) {
            def documentType = documentTypeService.getDocumentTypeById(params.long('dtid'))
            document.documentType.name = documentType.name
            document.state = DocumentState.PENDING
            document.depositOrigin = DepositOrigin.AGENT
            document.depositType = DepositType.P_C
            document.datas = []
        } else {
            document = documentService.getById(params.long('id'))
            if (!document.datas.isEmpty() && document.datas.get(0).getContentType() == ContentType.PDF)
                messageLink = message(code: "document.message.downloadDocument")
        }

        def actions = []
        for (DocumentAction action : document.actions) {
            actions.add(0, documentAdaptorService.adaptDocumentAction(action))
        }
        def agentCanWrite =
            categoryService.hasWriteProfileOnCategory(agent, request.requestType.category.id)

        return ([
            "uuid" : UUID.randomUUID().toString(),
            "document": [
                "id": document.id,
                "actions": actions,
                "editable": documentService.getEditableStates().contains(document.state) && agentCanWrite,
                "state": LibredematUtils.adaptLibredematEnum(document.state, "document.state"),
                "name": message(code:LibredematUtils.adaptDocumentTypeName(document.documentType.name)),
                "depositType": LibredematUtils.adaptLibredematEnum(document.depositType, "document.depositType"),
                "depositOrigin": LibredematUtils.adaptLibredematEnum(document.depositOrigin, "document.depositOrigin"),
                "endValidityDate": document.endValidityDate,
                "ecitizenNote": document.ecitizenNote,
                "agentNote": document.agentNote,
                "messageLink": messageLink,
                "pageNumber": document.datas.size(),
                "pages": document.id ? documentAdaptorService.getDocument(document.id).datas : []
            ]
        ])
    }
    
    def addPage = {
        def result = [:], file = request.getFile('pageFile')
        
            Document document = null
            if (params.documentId) {
            	document = documentService.getById(params.long('documentId'))
            } else {
                document = new Document()
                document.state = DocumentState.PENDING // Hack to byPass DRAFT state
                Request req = requestSearchService.getById(params.long('requestId'), false)
                document.documentType = documentTypeService.getDocumentTypeById(params.long('documentTypeId'))
                document.homeFolderId = req.homeFolderId
                document.depositOrigin = DepositOrigin.AGENT
                
                documentService.create(document)
                requestDocumentService.addDocument(req, document.id)
                result.newDocumentId = document.id
            }
            
            try {
                documentService.addPage(Long.valueOf(document.id), file.bytes)
                result.status = 'success'
                result.documentId = params?.documentId ? '' : document.id
                result.message = message(code:"message.addDone")
                result.pageNumber = document.datas.size() - 1
            } catch (CvqModelException cme) {
                result.status = 'warning'
                result.message = message(code : cme.i18nKey)
            }
        
        response.contentType = 'text/html; charset=utf-8'
        render((new JSON(result)).toString())
    }
    
    def deletePage = {
    	documentService.deletePage(params.long('documentId'), Integer.valueOf(params.pageNumber))
        render ([status:"success", message:message(code:"message.deleteDone")] as JSON)
    }
    
    def modifyPage = {
        def result = [:], file = request.getFile('pageFile')
        
        def document = documentService.getById(params.long('documentId'))
        try {
            documentService.modifyPage(params.long('documentId'),
                Integer.valueOf(params.pageNumber), file.bytes)
            result.message = message(code:"message.updateDone")
            result.status = 'success'
            result.pageNumber = params.pageNumber
        } catch (CvqModelException cme) {
            result.status = 'warning'
            result.message = message(code: cme.i18nKey)
        }
        
        response.contentType = 'text/html; charset=utf-8'
        render((new JSON(result)).toString())
    }

    def rotate = {
        documentService.rotate(params.long("id"), params.int("index"), params.boolean("trigonometric"))
        render ([status : "success", message : message(code : "message.updateDone")] as JSON)
    }

    def documentPage = {
        def document = documentService.getById(params.long('id'))
        def page = document.datas[Integer.valueOf(params.pageNumber)]
        
        if (page.contentType.equals(ContentType.PDF))
            response.contentType="application/pdf"
            
        else
            response.contentType = "image/png"
        response.outputStream << page.data
    }
    
    def documentPreview = {
        def document = documentService.getById(params.long('id'))
        def page = document.datas[Integer.valueOf(params.pageNumber)]
        if (page.preview == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
        response.contentType = "image/png"
        response.outputStream << page.preview
    }

    def states = {
        def result = [:]
        def document = documentService.getById(params.long('id'));

        def states = documentService.getPossibleTransitions(DocumentState.forString(document.state.toString()))
        
        result.states = []
        for (String s : states) result.states.add(LibredematUtils.adaptLibredematEnum(s, "document.state"))
        
        result.endValidityDate = document.endValidityDate
        result.stateType = document.documentType.name
        result.documentId = document.id
        
        return result;
    }
    
    def documentsList = {
        
        def documents = [], types = [], result = [:], agent = SecurityContext.currentAgent
        Request request = requestSearchService.getById(params.long('requestId'), false)
        Set docs = requestDocumentService.getAssociatedDocuments(params.long('requestId'))

        for (RequestDocument rd: docs) {
            def d = documentService.getById(rd.documentId);
            types.add(d.documentType.id)
            documents.add([
                "id": d.id,
                "documentTypeId": d.documentType.id,
                "name": message(code: LibredematUtils.adaptDocumentTypeName(d.documentType.name)),
                "endValidityDate": d.endValidityDate,
                "pageNumber": d.datas.size(),
                "state": LibredematUtils.adaptLibredematEnum(d.state, "document.state")
            ])
        }
        
        for (DocumentType t : requestTypeService.getAllowedDocuments(request.requestType.id)) {
            if (!types.contains(t.id)) documents.add([
                "id": 0,"documentTypeId": t.id,
                "name": message(code: LibredematUtils.adaptDocumentTypeName(t.name)),
                "state": ["cssClass": "tag-not_provided", "i18nKey": "document.state.notProvided"]
            ])
        }
        
        result.agentCanWrite = categoryService.hasWriteProfileOnCategory(agent, request.requestType.category.id)
        result.documents = documents
        result.requestId = params.requestId
        result.shortMode = params.shortMode

        return result
    }
    
    def changeState = {
        documentService.updateDocumentState(params.long('documentId'),
        		DocumentState.forString(params.state), null, 
        		DateUtils.stringToDate(params.endValidityDate))
        render ([status:"success", message:message(code:"message.updateDone")] as JSON)
    }
    
    def agentNote = {
        def document = documentService.getById(params.long('documentId'));
        bind(document)
        documentService.modify(document)
        render([status:"success", message:message(code:"message.updateDone")] as JSON)
    }

    def removeDocument = {
        if (request.getMethod().toLowerCase() == "delete") {
            requestDocumentService.removeDocument(
                requestSearchService.getById(Long.valueOf(params.requestId), false),
                Long.valueOf(params.documentId))
            render ([status:"ok",
                success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }
}
