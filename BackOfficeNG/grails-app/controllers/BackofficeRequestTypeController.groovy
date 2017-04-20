import org.libredemat.business.request.LocalReferentialEntry
import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestFormType
import org.libredemat.business.request.RequestSeason
import org.libredemat.business.request.RequestType
import org.libredemat.business.request.Requirement
import org.libredemat.business.request.RequestForm
import org.libredemat.business.request.RequestState
import org.libredemat.business.authority.LocalAuthorityResource.Type
import org.libredemat.business.authority.LocalAuthorityResource.Version
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.document.IDocumentTypeService
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.request.ILocalReferentialService
import org.libredemat.service.request.IParkCardService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.request.IRequestServiceRegistry
import org.libredemat.service.request.impl.RequestServiceRegistry;
import org.libredemat.util.Critere
import org.libredemat.exception.CvqParkCardException

import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.apache.commons.lang.StringUtils

import java.io.File
import java.util.Collections

import grails.converters.JSON

class BackofficeRequestTypeController {

    IRequestTypeService requestTypeService
    IDocumentTypeService documentTypeService
    ICategoryService categoryService
    ILocalReferentialService localReferentialService
    IRequestServiceRegistry requestServiceRegistry
    ILocalAuthorityRegistry localAuthorityRegistry
    IParkCardService parkCardService

    GroovyPagesTemplateEngine groovyPagesTemplateEngine
    
    def translationService
    def requestAdaptorService
    def RequestTypeAdaptorService
    def emailNotificationAdaptorService
    
    def defaultAction = 'list'
    
    def beforeInterceptor = {
        session["currentMenu"] = "requests"
    }

    def afterInterceptor = { model ->
        def subMenuEntries = ["request.search"]
        if (categoryService.hasManagerProfile(SecurityContext.currentAgent)) {
            subMenuEntries.add("requestType.list")
            def urlBooker = SecurityContext.getCurrentConfigurationBean().getExternalApplicationProperty("booker.url")
            if (!urlBooker.isEmpty()) {
                subMenuEntries.add(translationService.translate("submenu.booker") + "|" + urlBooker + "admin")
            }
        }
        model["subMenuEntries"] = subMenuEntries
    }

    def list = {
        def requestTypes = []

        // deal with dynamic filters
        def parsedFilters = SearchUtils.parseFilters(params.filterBy)
        if (parsedFilters.filters.size() > 0) {
            def categoryId = 
            	parsedFilters.filters['categoryIdFilter'] == null ? null : Long.valueOf(parsedFilters.filters['categoryIdFilter'])
            def state = 
            	parsedFilters.filters['stateFilter'] == null ? null : Boolean.valueOf(parsedFilters.filters['stateFilter'])
            Set<Critere> criteriaSet = new HashSet<Critere>()
            if (categoryId != null) {
                Critere critere = new Critere()
                critere.attribut = RequestType.SEARCH_BY_CATEGORY_ID
                critere.value = categoryId
                criteriaSet.add(critere)
            }
            if (state != null) {
                Critere critere = new Critere()
                critere.attribut = RequestType.SEARCH_BY_STATE
                critere.value = state
                criteriaSet.add(critere)
            }
            requestTypes = 
                requestTypeService.getRequestTypes(criteriaSet)
        } else {
        	requestTypes = requestTypeService.getAllRequestTypes()
        }

        def adaptedRequestTypes = []
        requestTypes.each{ 
        	adaptedRequestTypes.add(LibredematUtils.adaptRequestType(translationService, it)) 
        }
        adaptedRequestTypes = adaptedRequestTypes.sort{ it.label.toLowerCase() }
        
        ["requestTypes":adaptedRequestTypes, "allCategories":categoryService.getAll(),
            "filters":parsedFilters.filters,"filterBy":parsedFilters.filterBy]
    }

    private getCommonModel(requestType) {
        def result
        def requestService = requestServiceRegistry.getRequestService(requestType.label)
        boolean isMandatoryDocumentStep = requestTypeService.getRequestTypeMandatoryDocumentStep(requestType.id)
        if(isMandatoryDocumentStep){
            result = [
                "requestType" : requestType,
                "requestTypeLabel" :
                    translationService.translateRequestTypeLabel(requestType.label).encodeAsHTML(),
                "requestTypes" : requestAdaptorService.translateAndSortRequestTypes(),
                "isMandatoryDocumentStep" : isMandatoryDocumentStep
            ]
        }
        else{
            result = [
                "requestType" : requestType,
                "requestTypeLabel" :
                    translationService.translateRequestTypeLabel(requestType.label).encodeAsHTML(),
                "requestTypes" : requestAdaptorService.translateAndSortRequestTypes()
            ]
        }
        result["configurationItems"] = [
            "requestProperty" : ["requestType.configuration.requestProperty", false],
            "forms" : ["requestType.configuration.forms", false],
            "delays" : ["requestType.configuration.delays", false],
            "documents" : ["requestType.configuration.documentType", false],
            "steps" : ["requestType.configuration.steps", false],
            "emails" : ["requestType.configuration.emails", false],
            "texts" : ["requestType.configuration.texts", false]
        ]
        if (requestTypeService.getRulesAcceptanceFieldNames(requestType.id).size() > 0) {
            result["configurationItems"]["rules"] = ["requestType.configuration.rules", false]
        }
        if (requestService.getLocalReferentialFilename() != null) {
            result["configurationItems"]["localReferential"] =
                ["requestType.configuration.localReferential", true]
        }
        if (requestService.isOfRegistrationKind()) {
            result["configurationItems"]["seasons"] = ["requestType.configuration.seasons", false]
        }
        if (requestType.label == 'Ticket Booking') {
            result["configurationItems"]["ticketBooking"] =
                ["requestType.configuration.ticketBooking", false]
        } else if (requestType.label == 'Parking Permit Temporary Relocation') {
            result["configurationItems"]["parkingPermitTemporaryRelocation"] =
                    ["requestType.configuration.parkingPermitTemporaryRelocation", true]
        } else if (requestType.label == 'Parking Permit Temporary Work') {
            result["configurationItems"]["parkingPermitTemporaryWork"] =
                    ["requestType.configuration.parkingPermitTemporaryWork", true]
        }

        // Inexine Hack - Frederic Fabre && PP
        if (requestType.label == 'Park Card')
        {
            result["configurationItems"]["localReferential"] =
                [
                    "requestType.configuration.localReferential",
                    true
                ]
            result["configurationItems"]["streetBorderReferentials"] =
                [
                    "requestType.configuration.streetBorderReferential",
                    true
                ]
        }

        return result
    }

    /**
     * START - Hack inexine park card request
     */

    def streetBorderReferentials =
    {
        def id = Long.valueOf(params.id)
        def addresses = parkCardService.getAllStreets()
        render(view : "configure", model : ['streetBorderReferentials' : addresses, 'id' : id].plus(getCommonModel(requestTypeService.getRequestTypeById(id))))
    }

    def importStreet =
    {
        def file = request.getFile("csvFile")
        def id = Long.valueOf(params.id)
        if (file.empty)
        {
            render (new JSON(['status':'warning', 'message':message(code:'requestType.configuration.parkCard.message.noFile')]).toString())
            return false
        }
        try
        {
            parkCardService.importStreets(file.bytes)
        }
        catch (CvqParkCardException ex)
        {
            render (new JSON([ 'status':'error', 'message':message(code:ex.i18nKey)]).toString())
            return false
        }
        redirect(controller:"backofficeRequestType",action:"streetBorderReferentials", params:['id':id])
    }
    /**
     * END - Hack inexine park card request end
     */

    def forms = {
        def id = Long.valueOf(params.id)
        render(
            view : "configure",
            model : [
                "requestForms" :
                    requestTypeService.getRequestTypeForms(id, RequestFormType.REQUEST_MAIL_TEMPLATE)
            ].plus(getCommonModel(requestTypeService.getRequestTypeById(id))))
    }

    def delays = {
        def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.id))
        if (request.get) {
            render(
                view : "configure",
                model : [
                    "defaultConfig" : requestTypeService.globalRequestTypeConfiguration
                ].plus(getCommonModel(requestType))
            )
        } else if (request.post) {
            bind(requestType)
            requestTypeService.modifyRequestType(requestType)
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        }
    }

    def seasons = {
        render(view : "configure",
            model : getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(params.id))))
    }

    def loadSeasonsArea = {
        render(template : "listSeasons", model : ["seasons" : requestTypeService.getRequestTypeById(Long.valueOf(params.id)).seasons, "requestTypeId" : params.id])
    }

    def editSeason = {
        if (request.get) {
            def season
            if (params.id)
                season = requestTypeService.getRequestSeason(
                    Long.valueOf(params.requestTypeId), Long.valueOf(params.id))
            render(template : "editSeason", model : ["season" : season, "requestTypeId" : params.requestTypeId])
            return false
        } else if (request.post) {
            def season = new RequestSeason()
            bind(season)
            def codeString
            if (params.id == null || params.id.trim().isEmpty()) {
                requestTypeService.addRequestSeason(
                    Long.valueOf(params.requestTypeId), season)
                codeString = "message.creationDone"
            } else {
                requestTypeService.modifyRequestSeason(
                    Long.valueOf(params.requestTypeId), season)
                codeString = "message.updateDone"
            }
            render([status:"ok", success_msg:message(code : codeString)] as JSON)
            return false
        } else if (request.getMethod().toLowerCase() == "delete") {
            requestTypeService.removeRequestSeason(
                Long.valueOf(params.requestTypeId), Long.valueOf(params.id))
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
            return false
        }
    }

    def documents = {
        render(
            view : "configure",
            model: getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(params.id)))
        )
    }

    def documentList = {
        def list = []
        def reqs = []
        def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.id))
        requestType.requirements.each { r -> reqs.add(r.documentType.id)}
        documentTypeService.getAllDocumentTypes().each{ d ->
            list.add([
                'documentId' : d.id,
                'name' : message(code:LibredematUtils.adaptDocumentTypeName(d.name)),
                'bound' : reqs.contains(d.id),
                'class' : reqs.contains(d.id) ? '' : 'notBelong'
            ])
        }
        list = list.sort{it.name}
        render(template:"documentList",model:["documents":list])
    }
    
    def associateDocument = {
        requestTypeService.addRequestTypeRequirement(
            Long.valueOf(params.rtid),Long.valueOf(params.dtid)
        )
        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }
    
    def unassociateDocument = {
        requestTypeService.removeRequestTypeRequirement(
            Long.valueOf(params.rtid),Long.valueOf(params.dtid)
        )
        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }
    
    def state = {
        def requestType = requestTypeService.getRequestTypeById(Long.parseLong(params.id))
        if (request.get) {
            def result = [:]
            result.active = requestType.active
            result.requestTypeId = params.id
            result.state = requestType.active ? 'active':'inactive'
            return result
        } 
        else if (request.post) {
            def requestService = requestServiceRegistry.getRequestService(requestType.label)
            if (requestService.getLocalReferentialFilename() != null
               && !localReferentialService.isLocalReferentialConfigured(requestType.label)
               && !requestType.active) {
                render ([
                  'label': message(code: "property.${requestType.active ? 'active' : 'inactive'}"),
                  'state': requestType.active ? 'enable' : 'disable',
                  'message':message(code:"localReferential.error.isNotConfigure"),
                  'status':"warning"
                ] as JSON)
            }
            else {
                requestType.active = params.requestState == 'active'
                requestTypeService.modifyRequestType(requestType)
                render ([
                    'label': message(code: "property.${requestType.active ? 'active' : 'inactive'}"),
                    'state': requestType.active ? 'enable' : 'disable',
                    'message':message(code:"message.updateDone"),
                    'status':"success"
                ] as JSON)
            }
        }
    }

    // retrieves request form list using passed request type id
    def formList = {
        def id = Long.valueOf(params.id)
        def mailType = RequestFormType.REQUEST_MAIL_TEMPLATE
        def forms = requestTypeService.getRequestTypeForms(id, mailType)
        render(template:"formList",model:["requestForms":forms])
    }
    
    def form = {
        def method = request.getMethod().toLowerCase(), id
        if(method == "post" && params?.requestTypeId) {
            RequestForm form = new RequestForm()
            if(params.requestFormId) {
                form = requestTypeService.getRequestFormById(Long.valueOf(params.requestFormId))
            }
            form.setType(RequestFormType.REQUEST_MAIL_TEMPLATE)
            form.setLabel(params.label)
            form.setTemplateName(params.templateName)
            form.setShortLabel(params.shortLabel)
            id = requestTypeService.modifyRequestTypeForm(Long.valueOf(params.requestTypeId),form)
            
            render(['id':id,status:"ok",success_msg:message(code:"message.updateDone")] as JSON)
        } else if(method=="get") {
            def requestForm = null
            def templates = localAuthorityRegistry
                .getLocalAuthorityResourceFileNames(Type.MAIL_TEMPLATES,
                ".*\\" + Type.MAIL_TEMPLATES.extension)
            if(params.id) 
                requestForm = requestTypeService
                    .getRequestFormById(Long.valueOf(params.id))
            render(template:"form",model:["requestForm":requestForm,
                                               "templates":templates])
        } else if(method=="delete") {
            requestTypeService.removeRequestTypeForm(Long.valueOf(params.id));
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }
    
    def mailTemplate = {
        if(request.post) {
            if(params.editor != "") {
                RequestForm form = requestTypeService
                    .getRequestFormById(Long.valueOf(params.requestFormId))
                form.setType(RequestFormType.REQUEST_MAIL_TEMPLATE)
                form.setPersonalizedData(params.editor.getBytes())
                
                requestTypeService.modifyRequestTypeForm(Long.valueOf(params.requestTypeId),form)
                render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
            } else {
                throw new Exception("mail_templates.some_of_mandatory_fields_is_empty")
            }
        } 
        else {
            def templates = localAuthorityRegistry
                .getLocalAuthorityResourceFileNames(Type.MAIL_TEMPLATES, "*")
            render (view: 'mailTemplate', model:['name':params.id,'templates':templates])
        }
    }
    
    def loadMailTemplate = {
        def fileName = params.file        
        File templateFile = localAuthorityRegistry
            .getLocalAuthorityResourceFile(Type.MAIL_TEMPLATES, fileName, false)
        
        if(templateFile.exists()) {
            response.contentType = 'text/html; charset=utf-8'
			
            def forms = [];
            forms.add(requestTypeService.getRequestFormById(Long.valueOf(params.formId)))

            def content = templateFile.text
            def template = groovyPagesTemplateEngine.createTemplate(content,'page1');

            def requestAttributes = RequestContextHolder.currentRequestAttributes()
            def out = new StringWriter();
            def originalOut = requestAttributes.getOut()
            requestAttributes.setOut(out)
            template.make(['name':fileName,'forms':forms]).writeTo(out);
            requestAttributes.setOut(originalOut)

            render out.toString()
        } else { 
            render message(code:'message.templateDoesNotExist')
        }
    }

    def steps = {
        def id = Long.valueOf(params.id)
        def requestType = requestTypeService.getRequestTypeById(id)
        def requestService = requestServiceRegistry.getRequestService(requestType.label)

        render( view: 'configure'
              , model: [ 'id': params.id
                       , 'supportUnregisteredCreation': requestService.supportUnregisteredCreation()
                       ].plus(getCommonModel(requestTypeService.getRequestTypeById(id)))
              )
    }

    def saveSteps = {
        def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.id))
        def requestService = requestServiceRegistry.getRequestService(requestType.label)
        def active = params.stepAccountCompletion.equals("active")

        if (requestService.supportUnregisteredCreation() && active) {
            render( [ status: 'error'
                    , msg: message(code: 'requestType.configuration.steps.supportUnregisteredCreationError')
                    ] as JSON
                  )
        } else {
            requestType.setStepAccountCompletion(active)
            requestTypeService.modifyRequestType(requestType)
            render([status: 'success', msg: message(code: 'message.updateDone')] as JSON)
        }
    }

    def requestProperty = {
        def id = Long.valueOf(params.id)
        def rqt = requestTypeService.getRequestTypeById(id)
        def requestService = requestServiceRegistry.getRequestService(rqt.label)
        render(
            view : "configure",
            model : ["unregisteredCreation" : requestService.supportUnregisteredCreation(),
                "isOfRegistrationKind" : requestService.isOfRegistrationKind(),
                "supportMultiple" : requestService.getSupportMultiple(),
                "subjectPolicy" : requestService.getSubjectPolicy(),
                "filingDelay" : requestService.getFilingDelay(),
                "supportNoValidAccount" : requestService.getSupportNoValidAccount(),
                "id" : params.id].plus(getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(id))))
        )
    }

    def saveRequestProperty = {
        def rqtType = requestTypeService.getRequestTypeById(Long.valueOf(params.id))
        rqtType.setSupportUnregisteredCreation(params.supportUnregisteredCreation.equals("active"))
        rqtType.setSupportMultiple(params.supportMultiple.equals("active"))
        rqtType.setIsOfRegistrationKind(params.isOfRegistrationKind.equals("active"))
        rqtType.setSubjectPolicy(params.subjectPolicy)
        rqtType.setSupportNoValidAccount(params.supportNoValidAccount.equals("active"))
        try {
            rqtType.setFilingDelay(Integer.valueOf(params.filingDelay))
            if(Integer.valueOf(params.filingDelay) <= 0) {
                render([status:"error", error_msg:message(code : "requestType.property.filingDelay.badformat", args : [params.filingDelay])] as JSON)
                return;
            }
        } catch (NumberFormatException nfe) {
            render([status:"error", error_msg:message(code : "requestType.property.filingDelay.badformat", args : [params.filingDelay])] as JSON)
            return;
        }
        requestTypeService.modifyRequestType(rqtType)
        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    /* Local referential related action
     * ------------------------------------------------------------------------------------------ */
    def localReferential = {
        render(
            view : "configure",
            model : getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(params.id)))
        )
    }

    def localReferentialList = {
        render(
            template : "localReferentialList",
            model : [
                "lrTypes" : localReferentialService.getLocalReferentialTypes(
                    requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label),
                "requestTypeId" : params.id
            ]
        )
    }
    
    def localReferentialType = {
        def lrType = localReferentialService.getLocalReferentialType(requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label, params.dataName)
        render(template:"localReferentialEntries", 
               model:['lrEntries': lrType.entries, 'areReadOnly': lrType.getManager() != "LibreDémat", 'parentEntry': lrType.name,
                       'isMultiple': lrType.isMultiple(), 'isRadio': lrType.isRadio(), 'depth': 0])
    }

    def saveLocalReferentialType = {
        def allowMultipleChoices = false
        def allowRadioChoice = false
        if (params.allowMultipleChoices == 'radio') {
            allowRadioChoice = true
        } else {
            allowMultipleChoices = Boolean.valueOf(params.allowMultipleChoices)
        }
        localReferentialService.setLocalReferentialTypeAllowingMultipleChoices(
                requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label,
                params.lrtDataName, allowMultipleChoices, allowRadioChoice)
        render(['status': 'success', 'message': message(code: "message.updateDone")] as JSON)
    }

    def localReferentialEntry = {
       def lrType = localReferentialService.getLocalReferentialType(requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label, params.dataName)
       def lre
       if (params.isNew != null) {
          lre = new LocalReferentialEntry()
          lre.key = params.parentEntryKey
       } else 
          lre = lrType.getEntryByKey(params.entryKey)
       
       render(template:"localReferentialEntryFrom", 
              model:['entry':lre,
                     'parentEntryKey':params.parentEntryKey,
                     'dataName':params.dataName,
                     'isNewSubEntry':params.isNew != null ? true : false,
                     'requestTypeId':params.id])
    }
    
    def saveLocalReferentialEntry = {
        def isNew = false
        def rtLabel = requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label
        def parentKey = params.parentEntryKey != params.dataName ? params.parentEntryKey : null
        def newLabel = params.label
        def newMessage = params.message
        // hack inexine to add external code field
        def newExternalCode = params.externalCode
        if (params.'entry.key' == params.parentEntryKey) {
            localReferentialService.addLocalReferentialEntry(rtLabel, params.dataName, parentKey, newLabel, newMessage, newExternalCode)
            isNew = true
        } else {
            localReferentialService.editLocalReferentialEntry(rtLabel, params.dataName, params.'entry.key', newLabel, newMessage, newExternalCode)
        }
        render (['isNew': isNew, 'entryLabel': newLabel, 'entryKey': params.'entry.key',
                 'status':'success', 'message':message(code:"message.updateDone")] as JSON)
    }
    
    def removeLocalReferentialEntry = {
        def rtLabel = requestTypeService.getRequestTypeById(Long.valueOf(params.id)).label
        def label = localReferentialService.getLocalReferentialEntry(rtLabel, params.dataName, params.entryKey).label
        localReferentialService.removeLocalReferentialEntry(rtLabel, params.dataName, params.entryKey)
        render (['entryLabel': label,
                  'status':'success', 'message':message(code:"message.updateDone")] as JSON)
    }

    /* Rules related action
     * ------------------------------------------------------------------------------------------ */

    def rules = {
        render(
            view : "configure",
            model : getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(params.id)))
        )
    }

    def loadRules = {
        def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.id))
        def requestTypeLabelAsDir = LibredematUtils.requestTypeLabelAsDir(requestType.label)

        def rulesFieldNames = [:]
        requestTypeService.getRulesAcceptanceFieldNames(Long.valueOf(params.id))?.each {
            File ruleFile = localAuthorityRegistry.getLocalAuthorityResourceFile(
                Type.PDF, requestTypeLabelAsDir + '/' + it, Version.CURRENT, false)
            rulesFieldNames[it] = ruleFile.exists()
        }

        render(template:"ruleList",
                model:['id': params.id,
                    'requestTypeAcronym': RequestTypeAdaptorService.generateAcronym(requestType.label),
                    'requestTypeLabelAsDir': requestTypeLabelAsDir,
                    'rulesFieldNames': rulesFieldNames ])
    }

    // TODO: Manage in LocalAuthorityRegistry the requestType ressource dir persistence
    def saveRule = {
        def requestType = requestTypeService.getRequestTypeById(Long.valueOf(params.requestTypeId))
        def requestTypeLabelAsDir = LibredematUtils.requestTypeLabelAsDir(requestType.label)
        def file = request.getFile('rulesFile')
        response.contentType = 'text/html; charset=utf-8'
        if (file.empty) {
            render (new JSON(['status':'warning', 'message':message(code:'requestType.message.noRulesFile')]).toString())
            return false
        }
        if (!file.getContentType().equals("application/pdf")) {
            render (new JSON(['status':'warning', 'message':message(code:'requestType.message.noPdfRulesFile')]).toString())
            return false
        }
        def rulesDir = new File (localAuthorityRegistry.getAssetsBase() + '/' + session.currentSiteName + '/' + Type.PDF.folder + '/' + requestTypeLabelAsDir)
        if (!rulesDir.exists()) rulesDir.mkdir()

        localAuthorityRegistry.saveLocalAuthorityResource(Type.PDF, 
            requestTypeLabelAsDir + '/' + params.rulesField, file.bytes)
        render (new JSON([ 'rand' : UUID.randomUUID().toString(),
                           'status':'success', 
                           'message':message(code:'message.updateDone')]).toString())

    }

    /* Email notifications
     * --------------------------------------------------------------------- */

    def emails = {
        def id = Long.valueOf(params.id)
        def requestType = requestTypeService.getRequestTypeById(id)
        def dir = LibredematUtils.requestTypeLabelAsDir(requestType.label)
        def states = emailNotificationAdaptorService.states('templates/mails/notification/' + dir)
        def platformStates = [:]
        for(n in emailNotificationAdaptorService.states('templates/mails/notification'))
          platformStates[n.code] = n

        render(
            view:'configure',
            model:[
              'states':states,
              'platformStates':platformStates,
            ].plus(getCommonModel(requestType))
        )
    }

    /* Texts & helps related actions
     * --------------------------------------------------------------------- */

    def texts = {
        def id = Long.valueOf(params.id)
        def requestType = requestTypeService.getRequestTypeById(id)
        def keys = requestServiceRegistry.getRequestService(requestType.getLabel())
                                         .getSkeletonRequest()
                                         .getStepStates().keySet()
        def acronym = RequestTypeAdaptorService.generateAcronym(requestType.getLabel())

        def steps = ['introduction':message(code:'requestType.configuration.texts.intro')]
        steps = keys.inject(steps, { map, step ->
            if (step == 'homeFolder' || step == 'validation') {
              return map
            }
            def code = acronym + '.step.' + step + '.label'
            map[step] = message(code:'requestType.configuration.texts.help') \
                      + ' ' \
                      + message(code:code, default:StringUtils.capitalize(step))
            return map
        })

        render(
            view:'configure',
            model:['steps':steps].plus(getCommonModel(requestType))
        )
    }

    def helpFile = {
        def id = Long.valueOf(params.id)
        def requestType = requestTypeService.getRequestTypeById(id)

        def step = params.step
        def dir = LibredematUtils.requestTypeLabelAsDir(requestType.label)
        File file = localAuthorityRegistry.getLocalAuthorityResourceFile(
            Type.HTML,
            'request/' + dir + '/' + step,
            false)
        def text
        try {
            text = file.getText('utf-8')
        } catch(FileNotFoundException fnfe) {
            text = ''
        } finally {
            render(text:text)
        }
    }

    def saveHelpFile = {
        def id = Long.valueOf(params.id)
        def requestType = requestTypeService.getRequestTypeById(id)

        def step = params.step
        if (!requestType) {
            render(status:404, text:['message':message(code:'requestType.configuration.unknownRequestType')] as JSON)
            return
        }
        def dir = LibredematUtils.requestTypeLabelAsDir(requestType.label)

        def html = params.html ?: ''

        if (html) {
          localAuthorityRegistry.saveLocalAuthorityResource(
              Type.HTML,
              'request/' + dir + '/' + step,
              html.getBytes('UTF-8'))
          render message(code:'message.updateDone')
        } else {
          localAuthorityRegistry.removeLocalAuthorityResource(
              Type.HTML,
              'request/' + dir + '/' + step)
          render message(code:'message.updateDone')
        }
    }

    def ticketBooking = {
        render(
            view : "configure",
            model : getCommonModel(requestTypeService.getRequestTypeById(Long.valueOf(params.id)))
        )
    }

    /* Parking Permit Temporary Relocation
      * --------------------------------------------------------------------- */

    def parkingPermitTemporaryRelocation = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        def specificConfigurationData = requestType.getSpecificConfigurationDataAsJson()

        render(view: 'configure',
               model: ['authorizationWithoutPrestation': specificConfigurationData.get('authorizationWithoutPrestation'),
                       'relocationWithPrestation': specificConfigurationData.get('relocationWithPrestation'),
                       'minDaysBeforeRelocation': specificConfigurationData.get('minDaysBeforeRelocation')
                      ].plus(getCommonModel(requestType)))
    }

    def savePptrrPrices = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        requestType.addSpecificConfigurationData('authorizationWithoutPrestation', params.authorizationWithoutPrestation)
        requestType.addSpecificConfigurationData('relocationWithPrestation', params.relocationWithPrestation)
        requestTypeService.modifyRequestType(requestType)

        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    def savePptrrDates = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        requestType.addSpecificConfigurationData('minDaysBeforeRelocation', params.minDaysBeforeRelocation)
        requestTypeService.modifyRequestType(requestType)

        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    /* Parking Permit Temporary Work
      * --------------------------------------------------------------------- */

    def parkingPermitTemporaryWork = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        def specificConfigurationData = requestType.getSpecificConfigurationDataAsJson()

        render(view: 'configure',
                model: ['scaffoldingPrice': specificConfigurationData.get('scaffoldingPrice'),
                        'floorOccupationPrice': specificConfigurationData.get('floorOccupationPrice'),
                        'fixedChargePrice': specificConfigurationData.get('fixedChargePrice'),
                        'exceedingPrice': specificConfigurationData.get('exceedingPrice'),
                        'minDaysBeforeScaffolding': specificConfigurationData.get('minDaysBeforeScaffolding'),
                        'minDaysBeforeFloorOccupation': specificConfigurationData.get('minDaysBeforeFloorOccupation')
                ].plus(getCommonModel(requestType)))
    }

    def savePptwrPrices = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        requestType.addSpecificConfigurationData('scaffoldingPrice', params.scaffoldingPrice)
        requestType.addSpecificConfigurationData('floorOccupationPrice', params.floorOccupationPrice)
        requestType.addSpecificConfigurationData('fixedChargePrice', params.fixedChargePrice)
        requestType.addSpecificConfigurationData('exceedingPrice', params.exceedingPrice)
        requestTypeService.modifyRequestType(requestType)

        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    def savePptwrDates = {
        def requestType = requestTypeService.getRequestTypeById(params.long('id'))
        requestType.addSpecificConfigurationData('minDaysBeforeScaffolding', params.minDaysBeforeScaffolding)
        requestType.addSpecificConfigurationData('minDaysBeforeFloorOccupation', params.minDaysBeforeFloorOccupation)
        requestTypeService.modifyRequestType(requestType)

        render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    def setMandatoryDocumentStep = {
    if (params.mandatoryDocumentStep == "1"){
        requestTypeService.setRequestTypeMandatoryDocumentStep(Long.valueOf(params.id),true);
    }
    else{
        requestTypeService.setRequestTypeMandatoryDocumentStep(Long.valueOf(params.id),false);
    }
    render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
}
}
