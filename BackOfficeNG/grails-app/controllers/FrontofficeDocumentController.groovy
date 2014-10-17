import org.libredemat.business.document.Document
import org.libredemat.business.document.ContentType
import org.libredemat.business.document.DocumentState
import org.libredemat.business.document.DocumentBinary
import org.libredemat.business.users.Adult
import org.libredemat.business.users.Individual
import org.libredemat.security.SecurityContext
import org.libredemat.service.document.IDocumentService
import org.libredemat.service.document.IDocumentTypeService
import org.libredemat.service.request.school.external.ICirilDocumentProvider;
import org.libredemat.util.UserUtils

import java.util.Hashtable
import javax.servlet.http.HttpServletResponse
import grails.converters.JSON

import org.jdom.output.*
import org.jdom.*

class FrontofficeDocumentController {
    
    IDocumentService documentService
    IDocumentTypeService documentTypeService
    
    DocumentAdaptorService documentAdaptorService
    
    Adult currentEcitizen
    int maxRows = 10
    
    def beforeInterceptor = {
        this.currentEcitizen = SecurityContext.getCurrentEcitizen();
    }

    def details = {
        def result = [:], prevPage = null, nextPage = null, index = 0
        Document document = documentService.getById(params.long('id'))

        result.page = params.pn ? Integer.parseInt(params.pn) : 0
        result.actions = this.getActions(document)
        result.sessionUuid = params.sessionUuid
        
        def pages =  document.datas
        prevPage = result.page > 0 ? result.page - 1 : null
        nextPage = result.page < (pages.size() - 1) ? result.page + 1 : null
                
        def extension = ""
        if (!pages.isEmpty()) {
            extension = pages[0].contentType.extension
        }
        
        def messageLink = message(code:"document.message.showImage")
        if(pages[0].getContentType() == ContentType.PDF)
            messageLink = message(code: "document.message.downloadDocument")
        
        result.doc = [
            "id": document.id,
            "name": document.documentType,
            "title" : message(code: LibredematUtils.adaptDocumentTypeName(document.documentType.name)),
            "state": document.state,
            "depositType": document.depositType,
            "depositOrigin": document.depositOrigin,
            'depositor' : UserUtils.getDisplayName(document.depositId),
            "creationDate" : document.creationDate,
            "validationDate": document.validationDate,
            "endValidityDate": document.endValidityDate,
            "ecitizenNote": document.ecitizenNote,
            "agentNote": document.agentNote,
            'certified' : document.certified,
            'preview' : pages.get(result.page).getPreview(),
            'messageLink': messageLink,
            'numberOfPages': pages.size(),
            'extension': extension,
            'nextPage' : nextPage,
            'prevPage' : prevPage,
            'pagesTitle': StringUtils.firstCase(message(code:'property.page'),'')
        ]
        return result
    }
    
    def index = {
        def state = [:]
        
        if (params.ps) state = JSON.parse(params.ps)
        if (params.df != null) state.df = params.df;
        if (params.nf != null) state.nf = params.nf;
        if (params.sf != null) state.sf = params.sf;
        
        return ([
            'state': state,
            'documents' : this.getDocuments(state,params),
            'pageState' : (new JSON(state)).toString(),
            'individuals' : this.getIndividuals(),
            'states': DocumentState.allDocumentStates.findAll{DocumentState.DRAFT != it}.collect{it.toString().toLowerCase()},
            'types' : this.getTypes(),
            'maxRows': maxRows,
            'certificates': this.getCertificates()
        ])
    }
    
    def binary = {
        Document document = documentService.getById(params.long('id'))
        DocumentBinary binary = document.datas.get(params.pn ? Integer.valueOf(params.pn) : 0)
        if (binary.contentType.equals(ContentType.PDF))
            response.contentType = "application/pdf"
        else
            response.contentType = "image/png"
        response.outputStream << binary.data
    }
    
    def preview = {
            Document document = documentService.getById(params.long('id'))
            DocumentBinary binary = document.datas.get(params.pn ? Integer.valueOf(params.pn) : 0)
            if (binary.preview == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND)
            }
            response.contentType = "image/png"
            response.outputStream << binary.preview
    }
    
    def protected getActions(document) {
        def actions = []
        document.actions.each {
            actions.add(documentAdaptorService.adaptDocumentAction(it))
        }
        
        return actions
    }

    def protected getCertificates(params) {
        // méthode de récupération des certificats
        def result = [:]                                                                            // on crée une map
        result.familyDoc = []                                                                       // on crée deux sous map pour différencier les catégories de doc
        result.individualDoc = [:]
        def message = "message.noDocuments"

        def homeFolderId = currentEcitizen.homeFolder.id;
        def service = SecurityContext.currentConfigurationBean.getExternalServices().find { it.getKey() instanceof ICirilDocumentProvider }

        if(service != null) {
          def doc = service.getKey().getDocumentsOfCirilNetEnfance(homeFolderId)                // on fait passer les parametres au connecteur qui renvoit un doc xml org.jdom.Document
          if (doc != null) {
              def writer = new StringWriter()
              // on prepare un flux d'écriture de String
              new XMLOutputter().output(doc, writer)                                              // on y écrit le xml proprement
              def certificates = new XmlSlurper().parseText(writer.toString())                        // on parse le xml de réponse Ciril
              def family = certificates.FamilyAccountDocument.FamilyDocuments                         // on collecte les infos selon les catégorie
              certificates = new XmlSlurper().parseText(writer.toString())                        // on parse le xml de réponse Ciril
              def individual = certificates.IndividualAccountDocument.Individual

              result.familyDoc = family.collect{[it.Label, it.Url]}
              result.individualDoc = individual.collect{
                [
                  this.getNameByExternalId(homeFolderId, it.IndividualExternalId), it.IndividualDocument.collect{[it.Label, it.Url]}
                ]}
          }
        }

        return [
            'family' : result.familyDoc,
            'individual': result.individualDoc,
            'count' : result.familyDoc.size() + result.individualDoc.size(),
            'message' : message
        ]
    }

    def protected getNameByExternalId(homeFolderId, externalId) {
      return userSearchService.getNameForExternalIdCirilNetEnfance(homeFolderId, externalId.toLong());
    }

    def protected getDocuments(state,params) {
        def result = []
        def criterias = new Hashtable<String,Object>();
        int offset = !params?.offset ? 0 : Integer.parseInt(params.offset)
                
        if (state?.df) criterias.put("documentType.id",Long.valueOf(state.df))
        if (state?.sf) criterias.put("state",DocumentState.forString(StringUtils.firstCase(state.sf,'')))
        if (state?.nf == currentEcitizen.homeFolder.id.toString()) 
            criterias.put('homeFolderId',Long.valueOf(state.nf))
        else if (state?.nf)
            criterias.put('individualId',Long.valueOf(state.nf))
        
        documentService.search(criterias,maxRows,offset).each {
            result.add([
                'id' : it.id,
                'creationDate' : it.creationDate,
                'certified' : it.certified,
                'endValidityDate' : it.endValidityDate,
                'state' : it.state.toString(),
                'subject' : UserUtils.getDisplayName(it.individualId),
                'depositType' : it.depositType,
                'depositOrigin' : it.depositOrigin,
                'depositor' : UserUtils.getDisplayName(it.depositId),
                'title' : message(code: LibredematUtils.adaptDocumentTypeName(it.documentType.name))
            ]);
        }
        
        return [
            'all' : result,
            'count' : documentService.searchCount(criterias)
        ]
    }
    
    def protected getTypes() {
        def result = []
        this.documentTypeService.allDocumentTypes.each{
            result.add([
                id: it.id,
                name: message(code:LibredematUtils.adaptDocumentTypeName(it.name))
            ])
        }
        return result.sort {it.name}
    }
    
    def protected getIndividuals() {
        def individuals = []
        
        currentEcitizen.homeFolder.individuals.each{
            individuals.add([
                id : it.id,
                fullName : UserUtils.getDisplayName(it.id)
            ])
        }
        individuals = individuals.sort {it.fullName}
        individuals.add([
            id:currentEcitizen.homeFolder.id,
            fullName:message(code:'document.property.commonDocuments')
        ])
        return individuals
    }

    protected List getOrderedDocumentPages(document) {
        def result = []
        documentService.getAllPages(document.id).each{result.add(it.pageNumber)}
        return result.sort()
    }
}
