import org.libredemat.service.request.IRequestServiceRegistry

import java.util.Collections;
import java.lang.Math

import org.libredemat.business.request.external.RequestExternalAction
import org.libredemat.business.document.ContentType
import org.libredemat.business.request.DataState
import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestAction
import org.libredemat.business.request.RequestActionType
import org.libredemat.business.request.RequestNoteType
import org.libredemat.business.request.RequestState
import org.libredemat.business.users.Child;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType
import org.libredemat.business.payment.Payment
import org.libredemat.business.payment.PaymentState
import org.libredemat.exception.CvqException
import org.libredemat.exception.CvqModelException
import org.libredemat.security.SecurityContext
import org.libredemat.service.authority.IAgentService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.authority.IRecreationCenterService
import org.libredemat.service.authority.ISchoolService
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.request.IConditionService
import org.libredemat.service.request.ILocalReferentialService
import org.libredemat.service.users.IMeansOfContactService
import org.libredemat.service.request.IRequestLockService
import org.libredemat.service.request.IRequestNoteService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.request.IRequestWorkflowService
import org.libredemat.service.request.IRequestActionService
import org.libredemat.service.request.external.IRequestExternalService
import org.libredemat.service.request.external.IRequestExternalActionService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.util.Critere
import org.libredemat.util.UserUtils
import org.libredemat.util.UserUtils.UserDetails

import grails.converters.JSON

import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class BackofficeRequestInstructionController {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    IRequestActionService requestActionService
    IRequestLockService requestLockService
    IRequestSearchService requestSearchService
    IRequestNoteService requestNoteService
    IRequestTypeService requestTypeService
    IRequestWorkflowService requestWorkflowService
    IRequestServiceRegistry requestServiceRegistry
    IUserSearchService userSearchService
    IMeansOfContactService meansOfContactService
    IAgentService agentService
    ICategoryService categoryService
    ILocalReferentialService localReferentialService 
    IRecreationCenterService recreationCenterService
    ISchoolService schoolService
    IRequestExternalService requestExternalService
    IRequestExternalActionService requestExternalActionService
    IConditionService conditionService

    def translationService
    def homeFolderAdaptorService
    def requestAdaptorService
    def defaultAction = "edit"

    def beforeInterceptor = {
        session["currentMenu"] = "requests"
        if (params.action != "requestLock") {
            if (params.requestId)
                requestLockService.tryToLock(Long.valueOf(params.requestId))
            else if (params.id)
                requestLockService.tryToLock(Long.valueOf(params.id))
        }
    }

    def edit = {
        def rqt = requestSearchService.getById(Long.valueOf(params.id), true)
        def requester = rqt.requesterId != null ? userSearchService.getById(rqt.requesterId) : null

        // Auto-Transition from Pending to INProgress if enabled
        if (rqt.getState().equals(RequestState.PENDING) && SecurityContext.getCurrentConfigurationBean().isAutotransition()){
            requestWorkflowService.updateRequestState(rqt.id,RequestState.INPROGRESS, null);
        }

        def editableStates = []
        for (RequestState state : requestWorkflowService.getEditableStates())
            editableStates.add(state.toString())
        
        def externalProviderServiceLabel = null
        def externalTemplateName = null
        def lastTraceStatus = null
        if (requestExternalService.hasMatchingExternalService(rqt.requestType.label)) {
            externalProviderServiceLabel = requestExternalService
                    .getExternalServiceByRequestType(rqt.requestType.label).label
            def criteriaSet = new HashSet<Critere>(2)
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
                    String.valueOf(rqt.id), Critere.EQUALS))
            def traces = requestExternalActionService.getTraces(criteriaSet,
                    RequestExternalAction.SEARCH_BY_DATE, "desc", 1, 0)
            if (!traces.isEmpty()) {
                lastTraceStatus = LibredematUtils
                        .adaptLibredematEnum(traces.get(0).status, "externalservice.trace.status")
                if (traces.get(0).name) { // poor hack : mettre l'externalServiceLabel correct dans le cas de multiples services externes
                    externalProviderServiceLabel = traces.get(0).name
                }
            }
            externalTemplateName = ["/backofficeRequestInstruction/external",
                externalProviderServiceLabel, "_block"].join('/')
            def externalTemplate = groovyPagesTemplateEngine
                    .getResourceForUri(externalTemplateName)
            if (!externalTemplate || !externalTemplate.file
            || !externalTemplate.exists())
                externalTemplateName = null
            else
                externalTemplateName = ["/backofficeRequestInstruction/external",
                    externalProviderServiceLabel, "block"].join('/')
        }

        def lastActionNote
        rqt.actions.each {
            if (RequestActionType.STATE_CHANGE.equals(it.type)) {
                lastActionNote = it.note
            }
        }

        def subject = rqt.subjectId != null ? userSearchService.getById(rqt.subjectId) : null
        def hasHomeFolder = !userSearchService.getHomeFolderById(rqt.homeFolderId).temporary

        def subMenuEntries = ["request.search"]
        if (categoryService.hasManagerProfile(SecurityContext.currentAgent)) {
            subMenuEntries.add("requestType.list")
            def urlBooker = SecurityContext.getCurrentConfigurationBean().getExternalApplicationProperty("booker.url")
            if (!urlBooker.isEmpty()) {
                subMenuEntries.add(translationService.translate("submenu.booker") + "|" + urlBooker + "admin")
            }
        }
        if (hasHomeFolder) {
            subMenuEntries.add("homeFolder.details.id=" + rqt.homeFolderId)
        }

        return ([
            "rqt": rqt,
            "requestTypeLabel": rqt.requestType.label,
            "lrTypes": getLocalReferentialTypes(localReferentialService, rqt.requestType.label),
            "requester": requester,
            'hasHomeFolder': hasHomeFolder,
            "editableStates": (editableStates as JSON).toString(),
            "agentCanWrite": categoryService.hasWriteProfileOnCategory(SecurityContext.currentAgent, 
                rqt.requestType.category.id),
            "requestState": LibredematUtils.adaptLibredematEnum(rqt.state, "request.state"),
            "lastActionNote" : lastActionNote,
            "requestDataState": LibredematUtils.adaptLibredematEnum(rqt.dataState, "request.dataState"),
            "requestLabel": translationService.translateRequestTypeLabel(rqt.requestType.label).encodeAsHTML(),
            "requestTypeTemplate": LibredematUtils.requestTypeLabelAsDir(rqt.requestType.label),
            "externalProviderServiceLabel" : externalProviderServiceLabel,
            "externalTemplateName" : externalTemplateName,
            "lastTraceStatus" : lastTraceStatus,
            "subject" : subject,
            "subjectIsChild" : subject != null && subject instanceof Child ? true : false,
            "subMenuEntries" : subMenuEntries
        ])
    }
    
    // TODO - Mutualize with FrontOffice
    def getLocalReferentialTypes(lrService, requestTypeLabel) {
        def result = [:]
        if (requestTypeLabel == 'Ticket Booking')
            return result
        try {
            lrService.getLocalReferentialTypes(requestTypeLabel).each{
                result.put(StringUtils.firstCase(it.name,'Lower'), it)
            }
        } catch (CvqException ce) { /* No localReferentialData found ! */ }
        return result
    }
    
    def getLocalReferentialType(lrService, rtLabel, lrtName) {
        def lrt = null
        if (rtLabel != 'Ticket Booking') {
            try {
                lrt = lrService.getLocalReferentialType(rtLabel, StringUtils.firstCase(lrtName, 'Upper'))
            } catch (CvqException ce) {}
        }
        return lrt
    }
    
    // FIXME - Modify lazy initialization policy in JavaBean ?
    def lazyInit(rqt, listName) {
        if (rqt[listName] == null || rqt[listName].size() == 0) return false
        rqt[listName].get(0)
    }
    
    def localReferentialData = {
        def rqt = requestSearchService.getById(Long.valueOf(params.requestId), true)
        def lrt = getLocalReferentialType(localReferentialService, rqt.requestType.label, params.javaName)
        render( template: '/backofficeRequestInstruction/widget/localReferentialDataStatic',
                model: ['rqt':rqt,
                        'javaName':params.javaName, 
                        'lrEntries':lrt.entries,
                        'isMultiple':lrt.isMultiple(),
                        'depth':0 ])
    }

    /* request data inline edition managment
    * --------------------------------------------------------------------- */

    def widget = {
        def widgets = ['date','calendar','payment','time','address','bankAccount','frenchRIB','libredematEnum','boolean','textarea','localReferentialData','school','recreationCenter', 'recreationPolyCenter']
        
        def propertyTypes = JSON.parse(params.propertyType)
        def propertyType = propertyTypes.validate
        def widget = widgets.contains(propertyType) ?  propertyType : "string"
        if (propertyType == "acceptance") {
            widget = "boolean"
        }

        def model = ["requestId": Long.valueOf(params.id),
                     // the "fully qualifier" property name
                     "propertyName": params.propertyName,
                     "propertyPrefix": params.propertyName.replace(".","_").replace("[","_").replace("]","_"),
                     "propertyType": propertyType,
                     "required" : propertyTypes.required ? "required" : ""]
        
        // value init (by type)
        def propertyValue
        if (["address", "bankAccount", "frenchRIB"].contains(propertyType)) {
            propertyValue = JSON.parse(params.propertyValue)
        } 
        else if (propertyType == "libredematEnum") {
            def propertyJavaType = propertyTypes.javatype.tokenize(".")
            def allPropertyValue = Class.forName(propertyTypes.javatype).values()
            model["allPropertyValue"] = allPropertyValue
            
            // FIXME - normalize propertyValue class like class="value<MY_VAL> i18n-<MY_I18n>)"
            def propertyValueTokens = params.propertyValue.tokenize(" ")
            if (propertyValueTokens.size() == 1)
                propertyValueTokens = ['',propertyValueTokens[0]]
                
            propertyValue = [ "enumString": propertyValueTokens[0],
                              "i18nKeyPrefix": propertyValueTokens[1] ]
            // will contain the fully qualified class name of the "LibreDemat enum" class
            model["propertyValueType"] = propertyTypes.javatype
        }
        else if (propertyType == "localReferentialData") {
            def rqt = requestSearchService.getById(Long.valueOf(params.id), true)
            def lrt = getLocalReferentialType(localReferentialService, rqt.requestType.label, params.propertyName)
            model['lrType'] = lrt
            model['lrDatas'] = rqt[params.propertyName].collect { it.name }
            flash[params.propertyName + 'Index'] = 0
            model["htmlClass"] =
                (model["lrType"].isMultiple() ? "validate-localReferentialData " :
                    (model["required"] != "" ? "validate-not-first " : "")) +
                model["required"]
        }
        else if (propertyType == "school") {
            model.schools = schoolService.getActive()
            if (params.propertyValue != "null") {
                propertyValue = Long.valueOf(params.propertyValue)
            }
        }
        else if (propertyType == "recreationCenter" || propertyType == "recreationPolyCenter") {
            model.recreationCenters = recreationCenterService.getActive()
            if (params.propertyValue != "null") {
                propertyValue = Long.valueOf(params.propertyValue)
            }
        } else if (propertyType == "time") {
            propertyValue = [:]
            propertyValue.hour = params.propertyValue.split(':')[0].trim()
            propertyValue.minute = params.propertyValue.split(':')[1].trim()
        } else if (propertyType == "payment") {
            try {
                propertyValue = Double.valueOf(params.propertyValue);
            } catch (Exception ex) {
                propertyValue = 0;
            }
        } else {
            propertyValue = params.propertyValue
            model["minLength"] = propertyTypes.minLength
            model["maxLength"] = propertyTypes.maxLength
            model["i18nKeyPrefix"] = propertyTypes.i18n
            model["regex"] = params.propertyRegex
            if (params.propertyRegex != "") model["propertyType"] = "regex"
            if (propertyType == "textarea") model["rows"] = propertyTypes.rows
        }
        model["propertyValue"] = propertyValue
        render( template: "/backofficeRequestInstruction/widget/" + widget, model:model)
    }
    
    def modify = {
        if (params.requestId == null)
             return false
        def rqt = requestSearchService.getById(Long.valueOf(params.requestId), true)
        if (params.keySet().contains('_requester')) {
            def requester = userSearchService.getById(rqt.requesterId)
            bindRequester(requester, params)
        } else if (params.keySet().contains('schoolId')) {
            // TODO Move the logic in a business layer
            def school = schoolService.getById(Long.valueOf(params.schoolId))
            if (school?.active) {
                rqt.school = school;
            } else {
                throw new CvqModelException("request.error.inactiveSchool")
            }
        } else if (params.keySet().contains('recreationPolyCenterId')) {
            // Hack Inexine - Frederic Fabre - PP - raprr
            def recreationCenter = recreationCenterService.getById(Long.valueOf(params.recreationPolyCenterId))
                if (recreationCenter?.active) 
                {
                    rqt.recreationPolyCenter = recreationCenter
                } 
                else 
                {
                    throw new CvqModelException("request.error.inactiveRecreationCenter")
                }
        } else if (params.keySet().contains('recreationCenterId')) {
            // TODO move that in the business layer
            def recreationCenter = recreationCenterService.getById(Long.valueOf(params.recreationCenterId))
            if (recreationCenter?.active) { // Make sure the recreation center is active
                rqt.recreationCenter = recreationCenter;
            } else {
                throw new CvqModelException("request.error.inactiveRecreationCenter")
            }
        } else if (params.keySet().contains('payment') && params.payment == 'true') {
            def requestPaymentService = requestServiceRegistry.getRequestPaymentService(rqt);
            if (requestPaymentService != null)
            {
                def amount = (Math.round(Double.valueOf(params.amount.replace(',','.')) * 100 ) / 100) * 100
                if (amount > 0)
                {
                    Payment payment = requestPaymentService.buildPayment(rqt, amount.toDouble());
                    payment.setAmount(amount.toDouble());
                    payment.setInitializationDate(new Date());
                    payment.setState(PaymentState.TOPAY);
                    requestPaymentService.setPayment(rqt, payment);
                    requestWorkflowService.updateLastModificationInformation(rqt, null);
                    def pdf = requestWorkflowService.createInvoiceForRequest(rqt);
                    requestWorkflowService.mailCitizenForPayment(rqt, pdf);
                }
                else if (requestPaymentService.getPayment(rqt) != null)
                {
                    // Annulation du paiement
                    Payment payment = requestPaymentService.buildPayment(rqt, 0.0);
                    payment.setAmount(0.0);
                    payment.setState(PaymentState.CANCELLED);
                    requestPaymentService.setPayment(rqt, payment);
                    requestWorkflowService.updateLastModificationInformation(rqt, null);
                    def pdf = requestWorkflowService.createInvoiceForRequest(rqt);
                    requestWorkflowService.mailCitizenForAnnulationPayment(rqt, pdf);
                }
            }
        } else {
            DataBindingUtils.initBind(rqt, params)
            bind(rqt)
        }
        requestWorkflowService.modify(rqt)
//        log.debug("Binder custum editor PersistentStringEnum = " + getBinder(rqt).propertyEditorRegistry.findCustomEditor(org.libredemat.dao.hibernate.PersistentStringEnum.class, null))
        render ([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }
    
    def bindRequester(requester, params) {
        params.each { param ->
            if (param.value.getClass() == GrailsParameterMap.class && param.key == '_requester') {
                DataBindingUtils.initBind(requester, param.value)
                bindParam (requester, param.value)
            }
        }
    }
    
    def modifyList = {
        if (params.requestId == null || params.listAction == null )
             return
        
        def cRequest = requestSearchService.getById(Long.valueOf(params.requestId), true)
        def actionTokens = params.listAction.tokenize('_')

        def listElemTokens = actionTokens[1].tokenize('[]')
        def getterMethod =
            cRequest.class.getMethod('get' + StringUtils.firstCase(listElemTokens[0], 'Upper'))
        
        if (actionTokens[0] == 'delete') {                   
            getterMethod.invoke(cRequest, null).remove(Integer.valueOf(listElemTokens[1]).intValue())
        }
        else if (actionTokens[0] == 'add') {
            def listElemType = getterMethod.genericReturnType.actualTypeArguments[0]
            def listElem = listElemType.getConstructor(null).newInstance(null)
            getterMethod.invoke(cRequest, null).add(listElem)
        }
        render (template:'/backofficeRequestInstruction/requestType/'
                          + LibredematUtils.requestTypeLabelAsDir(cRequest.requestType.label) 
                          +'/' + listElemTokens[0]
               ,model: ['rqt': cRequest])
    }

    def condition = {
        
        def result = []
        
        if (params.requestTypeLabel == null)
            render ([status: 'error', error_msg:message(code:'error.unexpected')] as JSON)
        
        try {
            for (Map entry : (JSON.parse(params.conditionsContainer) as List)) {
                result.add([
                    success_msg: message(code:'message.conditionTested'),
                    test: conditionService.isConditionFilled(params.requestTypeLabel, entry),
                    status: 'ok'
                ])
            }
            render(result as JSON)
        } catch (CvqException ce) {
            render ([status: 'error', error_msg:message(code:'error.unexpected')] as JSON)
        }
    }

    /* request state workflow managment
    * --------------------------------------------------------------------- */

    def stateTransitions = {
        def stateAsString = StringUtils.toPascalCase(params.stateCssClass.replace("tag-", ""))
        def stateType = params.stateType

        def transitionStates = []
        def stateTypeI18nKey
        switch (stateType) {
            case "requestDataState":
                transitionStates =
                    requestWorkflowService.getPossibleTransitions(DataState.forString(stateAsString))
                stateTypeI18nKey = "request.dataState"
                break
            case "requestState":
                transitionStates =
                    requestWorkflowService.getPossibleTransitions(RequestState.forString(stateAsString))
                 stateTypeI18nKey = "request.state"
                 break
        }

        def states = []
        transitionStates.each { states.add(LibredematUtils.adaptLibredematEnum(it, stateTypeI18nKey)) }

        render( template: "possibleTransitionStates",
                model: ["states": states, "stateType": stateType, "id": params.id])
    }

    def changeState = {
        if (params.stateType == null || params.newState == null || params.id == null )
             return

        switch (params.stateType) {
            case "requestDataState":
                requestWorkflowService.updateRequestDataState(
                        Long.valueOf(params.id), DataState.forString(params.newState))
                break
            case "requestState":
                requestWorkflowService.updateRequestState(
                        Long.valueOf(params.id),
                        RequestState.forString(params.newState),
                        params.note)
               break
        }
        render ([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
    }

    /*  request information  managment
    * --------------------------------------------------------------------- */

    def homeFolder = {
        def result = ['adults':[], 'children':[]]
        def cRequest = requestSearchService.getById(Long.valueOf(params.id), false)
        def homeFolder = userSearchService.getHomeFolderById(cRequest.homeFolderId)
        userSearchService.getAdults(homeFolder.id).each { adult ->
            result.adults.add([
                'id' : adult.id,
                'title' : message('code':"homeFolder.adult.title.${adult.title.toString().toLowerCase()}"),
                'fullName' : "${adult.firstName} ${adult.lastName}",
                'email' : adult.email,
                'homePhone' : adult.homePhone,
                'mobilePhone' : adult.mobilePhone,
                'officePhone' : adult.officePhone,
                'birthDate' : adult.birthDate,
                'birthCountry' : adult.birthCountry,
                'birthPostalCode' : adult.birthPostalCode,
                'birthCity' : adult.birthCity,
                'ownerRoles' : homeFolderAdaptorService.prepareOwnerRoles(adult)
            ])
        }
        userSearchService.getChildren(homeFolder.id).each{ child ->
            result.children.add([
                'id' : child.id,
                'sex' : child.sex,
                'fullName' : "${child.firstName?.concat(' ')?: ''}${child.lastName}",
                'born' : child.isBorn(),
                'birthDate' : child.birthDate,
                'birthCountry' : child.birthCountry,
                'birthPostalCode' : child.birthPostalCode,
                'birthCity' : child.birthCity,
                'roleOwners' : userSearchService.listBySubjectRoles(child.id,
                    [RoleType.CLR_FATHER, RoleType.CLR_MOTHER, RoleType.CLR_TUTOR] as RoleType[])
            ])
        }
        result.info = [
            'id' : homeFolder.id,
            'temporary': homeFolder.temporary,
            'state' : homeFolder.state,
            'enabled' : homeFolder.enabled,
            'temporary' : homeFolder.temporary,
            'addressDetails' : "${homeFolder.address.additionalDeliveryInformation?.concat(', ')?: ''}" +
                "${homeFolder.address.additionalGeographicalInformation?.concat(', ')?: ''}" +
                "${homeFolder.address.streetNumber?.concat(' ')?: ''}${homeFolder.address.streetName}, " +
                "${homeFolder.address.placeNameOrService?.concat(', ')?: ''}" +
                "${homeFolder.address.postalCode + ', '}" +
                "${homeFolder.address.city}" +
                "${homeFolder.address.countryName ? ', ' + homeFolder.address.countryName : ''}" + "."
        ]
        render(template:'homeFolderData', model:['homeFolder':result])
    }

    def homeFolderRequests = {
        def rqt = requestSearchService.getById(Long.valueOf(params.id), false)
        def homeFolderRequests = requestSearchService.getByHomeFolderId(rqt.homeFolderId, false);

        def records = []
        homeFolderRequests.each {
            def record = requestAdaptorService.prepareRecordForSummaryView(it)
            records.add(record)
        }
        render(template:'homeFolderRequests', model: ['records': records])
    }

    def history = {
        def id = params.long("id")
        def actions = []
        def repliesActions = []
        requestSearchService.getById(id, false).actions.each {
            if (RequestState.DRAFT.equals(it.resultingState))
                return
            def resultingState = null
            if (it.type.equals(RequestActionType.STATE_CHANGE)) {
                resultingState = LibredematUtils.adaptLibredematEnum(it.resultingState, "request.state")
            }
            if(it.replyParentId == null) {
                def requestAction = [
                    'id':it.id,
                    "requestId" : id,
                    "user" : UserUtils.getUserDetails(it.agentId),
                    "type" : LibredematUtils.adaptLibredematEnum(it.type, "requestAction.type"),
                    'note':it.note,
                    "message" : it.message,
                    'date':it.date,
                    'resulting_state':resultingState,
                    "hasFile" : it.file != null,
                    "fileType" :
                        [RequestActionType.CREATION, RequestActionType.STATE_CHANGE]
                            .contains(it.type) ?
                            "requestAction.property.requestCertificate" : "requestAction.property.file",
                    "filename" : it.filename,
                    "template" : "requestAction",
                    "replyParentId" : it.replyParentId
                ]
                actions.add(requestAction)
            } else {
              repliesActions.add(it)
            }
        }
        def customTemplates = [:]
        Set criteriaSet = new HashSet<Critere>(1)
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY, params.id, Critere.EQUALS))
        requestExternalActionService.getTraces(criteriaSet, null, null, 0, 0).each {
            if (!customTemplates.containsKey(it.name)) {
                def customTemplate = groovyPagesTemplateEngine.getResourceForUri(
                    ["/backofficeRequestInstruction/external", it.name,
                        "_requestExternalActionComplementaryData"].join('/'))
                if (customTemplate && customTemplate.file && customTemplate.exists())
                    customTemplates[it.name] = ["/backofficeRequestInstruction/external", it.name,
                        "requestExternalActionComplementaryData"].join('/')
                else
                    customTemplates[it.name] = null
            }
            actions.add([
                "date" : it.date,
                "label" : it.name,
                "status" : LibredematUtils.adaptLibredematEnum(it.status, "externalservice.trace.status"),
                "message" : it.message,
                "complementaryData" : it.complementaryData,
                "template" : "requestExternalAction",
                "customTemplate" : customTemplates[it.name]
            ])
        }

        actions.addAll(requestAdaptorService.prepareNotes(requestNoteService.getNotes(id, null),repliesActions))
        render(template : "requestHistory", model : [
            "requestId" : params.id,
            "actions" : actions.sort { it.date }.reverse()
        ])
    }

    def requestNote = {
        if (request.get) {
            def rqt = requestSearchService.getById(params.long("id"), false)
            render(template : "requestNotePanel", model : ["rqt" : rqt])
            return false
        }
        if (params.requestId != null && params.note != null && params.requestNoteType != null) {
            requestNoteService.addNote(
                Long.valueOf(params.requestId),
                RequestNoteType.forString(params.requestNoteType), params.note)
            render([status:"ok", success_msg:message(code:"request.note.message.added")] as JSON)
        } else
            render ([status: "error", error_msg:message(code:"error.missingParmeter")] as JSON)
        
    }

    def external = {
        if (request.post) {
            Request rqt = requestSearchService.getById(Long.valueOf(params.id), true)
            if (!rqt.getState().equals(RequestState.VALIDATED))
                throw new CvqException("plugins.externalservices.error.requestMustBeValidated");
            requestExternalService.sendRequest(rqt)
            def criteriaSet = new HashSet<Critere>(2)
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
                params.id, Critere.EQUALS))
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
                params.label, Critere.EQUALS))
            def lastTraceStatus = LibredematUtils.adaptLibredematEnum(
                requestExternalActionService.getTraces(criteriaSet,
                RequestExternalAction.SEARCH_BY_DATE, "desc", 1, 0).get(0).status, "externalservice.trace.status")
            render(template : "/backofficeRequestInstruction/external/" + params.label + "/externalStatus",
                   model : ["externalProviderServiceLabel" : params.label,
                            "lastTraceStatus" : lastTraceStatus])
        }
    }

    def externalReferentialChecks = {
        def rqt = requestSearchService.getById(Long.valueOf(params.id), true)
        render(template : "/backofficeRequestInstruction/external/" + params.label + "/externalReferentialChecks",
               model : ["id" : params.id, "label" : params.label,
                        "externalReferentialCheckErrors" : requestExternalService
                            .checkExternalReferential(rqt)])
    }

    def requestLock = {
        def id = Long.valueOf(params.id)
        if (request.get) {
            if (params.part == "tag") {
                render(template : "requestLock",
                       model : requestAdaptorService.prepareLock(id))
            } else if (params.part == "panel") {
                render(template : "requestLockPanel",
                       model : requestAdaptorService.prepareLock(id))
            }
        } else if (request.post) {
            requestLockService.tryToLock(Long.valueOf(params.id))
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        } else if (request.method.toLowerCase() == "delete") {
            requestLockService.release(Long.valueOf(params.id))
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }
}
