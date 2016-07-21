import com.google.gson.JsonObject
import grails.converters.JSON
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Collections

import org.libredemat.schema.ximport.HomeFolderImportDocument
import org.libredemat.service.users.IHomeFolderDocumentService
import org.libredemat.service.users.IUserService
import org.libredemat.service.users.IUserSynchronisationService;
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.users.IUserWorkflowService
import org.libredemat.service.users.IUserSecurityService
import org.libredemat.service.users.IUserDeduplicationService
import org.libredemat.util.Critere
import org.libredemat.business.users.*
import org.libredemat.business.QoS
import org.libredemat.security.SecurityContext
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.payment.IPaymentService
import org.libredemat.service.users.IMeansOfContactService
import org.libredemat.service.users.external.IExternalHomeFolderService
import org.libredemat.service.users.impl.UserSearchService;
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.business.payment.Payment
import org.libredemat.service.document.IDocumentTypeService
import org.libredemat.security.PermissionException
import org.libredemat.security.annotation.ContextPrivilege

import org.libredemat.exception.CvqModelException
import org.libredemat.exception.CvqValidationException

import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestState
import org.libredemat.util.Critere

import org.apache.xmlbeans.XmlError
import org.apache.xmlbeans.XmlException
import org.apache.xmlbeans.XmlOptions

import org.libredemat.service.request.ICategoryService

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject


import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.springframework.web.context.request.RequestContextHolder
import org.libredemat.business.authority.LocalAuthorityResource.Type
import org.libredemat.business.request.RequestFormType
import org.libredemat.business.request.RequestType
import org.libredemat.business.request.RequestForm
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.users.IUserNotificationService
import org.libredemat.service.users.IUserSynchronisationService

class BackofficeHomeFolderController {

    IExternalHomeFolderService externalHomeFolderService
    IUserService userService
    IUserSearchService userSearchService
    IUserWorkflowService userWorkflowService
    IUserDeduplicationService userDeduplicationService
    IRequestSearchService requestSearchService
    IPaymentService paymentService
    IMeansOfContactService meansOfContactService
    IUserSecurityService userSecurityService
    IDocumentTypeService documentTypeService
    IHomeFolderDocumentService homeFolderDocumentService
    IRequestTypeService requestTypeService
    ICategoryService categoryService
    GroovyPagesTemplateEngine groovyPagesTemplateEngine
    ILocalAuthorityRegistry localAuthorityRegistry
    IUserNotificationService userNotificationService
    IUserSynchronisationService userSynchronisationService

    def translationService
    def homeFolderAdaptorService
    def requestAdaptorService
    def requestTypeAdaptorService
    def individualAdaptorService

    def defaultAction = 'search'
    def defaultMax = 15
    def subMenuEntries

    def beforeInterceptor = {
        session["currentMenu"] = "users"
        if (SecurityContext.currentCredentialBean.hasSiteAdminRole()) {
            subMenuEntries = ["userAdmin.index", "userSecurity.index", "homeFolder.meansOfContact", "homeFolder.importHomeFolders", "homeFolder.childInformationSheetDateInitialisation", "homeFolder.synchronisation"]
        } else {
            if (userSecurityService.can(SecurityContext.getCurrentAgent(), ContextPrivilege.MANAGE))
                subMenuEntries = ["homeFolder.search", "homeFolder.configure", "homeFolder.create"]
            else
                if (userSecurityService.can(SecurityContext.getCurrentAgent(), ContextPrivilege.WRITE))
                    subMenuEntries = ["homeFolder.search", "homeFolder.create"]
                else
                    subMenuEntries = ["homeFolder.search"]
        }
    }

    def help = {}

    def findDuplicates = {
        Adult adult = userSearchService.getAdultById(params.id.toLong())
        userDeduplicationService.findHomeFolderDuplicates(adult.homeFolder.id)
        redirect(action:'details', params:['id': adult.homeFolder.id])
    }

    def search = {
        def state = [:], records = [], count = 0
        if (params.pageState) state = JSON.parse(params.pageState)
        
        if(!request.get) {
            records = this.doSearch(state)
            count = userSearchService.getCount(this.prepareCriterias(state), true)
        }
        
        return ([
            'agentCanWrite': userSecurityService.canWrite(SecurityContext.currentAgent.id),
            'state': state,
            'records': records,
            'count' : count,
            'max': this.defaultMax,
            'homeFolderStates': this.buildHomeFolderStateFilter(),
            'currentSiteName': SecurityContext.currentSite.name,
            'homeFolderStatus' : this.buildHomeFolderStatusFilter(),
            'pageState' : (new JSON(state)).toString().encodeAsHTML(),
            'offset' : params.currentOffset ? params.currentOffset : 0,
            'subMenuEntries': subMenuEntries,
            'formActionLink': createLink(action:'search')
        ]);
    }
    
    def details = {
        def homeFolder = userSearchService.getHomeFolderById(Long.parseLong(params.id))
        if (homeFolder.temporary)
            render(text: "", status: 403)
        def adults, children
        if (params.viewArchived != null) {
            adults = userSearchService.getAdults(homeFolder.id, UserState.allUserStates)
            children = userSearchService.getChildren(homeFolder.id, UserState.allUserStates)
        } else {
            adults = userSearchService.getAdults(homeFolder.id)
            children = userSearchService.getChildren(homeFolder.id)
        }

        def unarchivableIndividuals = []
            Critere critere = new Critere(Request.SEARCH_BY_HOME_FOLDER_ID, homeFolder.id, Critere.EQUALS)
            Set<Critere> criteria = new HashSet<Critere>();
            criteria.add(critere)
            List<Request> homeFolderRequests = requestSearchService.get(criteria, null, null, -1, 0, false)
            for (Request request : homeFolderRequests) {
                if (request.state != RequestState.ARCHIVED && request.subjectId != null)
                    unarchivableIndividuals.add(request.subjectId)
            }

        def result = [:]
        result.homeFolder = homeFolder
        result.homeFolderResponsible = userSearchService.getHomeFolderResponsible(homeFolder.id)
        if (result.homeFolderResponsible.duplicateAlert) {
            result.homeFolderDuplicates = [:]
            def duplicates = JSON.parse(result.homeFolderResponsible.duplicateData)
            duplicates.each { homeFolderId, values ->
                result.homeFolderDuplicates[homeFolderId] = [:]
                values.each { 
                    if (it.key == 'rank')
                        result.homeFolderDuplicates[homeFolderId][it.key] = Long.valueOf(it.value)
                    else
                        result.homeFolderDuplicates[homeFolderId][it.key] = it.value
                }
                result.homeFolderDuplicates[homeFolderId]['otherDuplicates'] = 
                    userDeduplicationService.getHomeFolderDuplicates(homeFolder.id, Long.valueOf(homeFolderId))
                result.homeFolderDuplicates[homeFolderId]['otherDuplicates'].remove(result.homeFolderResponsible.fullName)
                result.homeFolderDuplicates[homeFolderId]['duplicateResponsibleData'] = userSearchService.getHomeFolderResponsible(Long.valueOf(homeFolderId))
            }
        } else {
            String adultDuplicates = adults.inject("") { acc, a ->
                if (a.duplicateAlert && !a.duplicateData.trim().isEmpty()) {
                    acc + ", " + a.duplicateData
                } else {
                    acc
                }
            }
            String childrenDuplicates = children.inject("") { acc, c ->
                if (c.duplicateAlert && !c.duplicateData.trim().isEmpty()) {
                    acc + ", " + c.duplicateData
                } else {
                    acc
                }
            }
            def duplicates = JSON.parse(((adultDuplicates + childrenDuplicates).isEmpty()) ?
                "[]" : "[" + (adultDuplicates + childrenDuplicates).substring(1) + "]")
            result.individualDuplicates = [:]
            def dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            duplicates.each { duplicate ->
                duplicate.each { homeFolderId, values ->
                    def individualId = values.get("id")
                    result.individualDuplicates[homeFolderId] = [:]
                    result.individualDuplicates[homeFolderId][individualId] = [:]
                    values.each {
                        if (it.key == 'rank')
                            result.individualDuplicates[homeFolderId][individualId][it.key] = Long.valueOf(it.value)
                        else if (it.key == 'birthDate')
                            result.individualDuplicates[homeFolderId][individualId][it.key] = dateFormat.parse(it.value)
                        else
                            result.individualDuplicates[homeFolderId][individualId][it.key] = it.value
                    }
                }
            }
        }
        result.externalProviders = SecurityContext.getCurrentConfigurationBean().getExternalServices().entrySet().collect{ it.getKey().label }
        result.adults = adults.findAll{it.id != result.homeFolderResponsible.id}
        result.children = children
        result.adultsRoles = getRolesByAdults(adults)
        result.childsRoles = getRolesByChildren(adults)
        result.homeFolderState = homeFolder.state.toString().toLowerCase()
        result.homeFolderStatus = homeFolder.enabled ? 'enable' : 'disable'
        def isValidable = false
        def agentCanWrite = userSecurityService.can(SecurityContext.getCurrentAgent(), ContextPrivilege.WRITE)
        if ((homeFolder.state.equals(UserState.NEW) || homeFolder.state.equals(UserState.MODIFIED)) &&
            (agentCanWrite)) {
            isValidable=true
        }
        result.isValidable=isValidable

        result.responsibles = [:]
        for(Child child : result.children)
            result.responsibles.put(child.id, userSearchService.listBySubjectRoles(child.id, RoleType.childRoleTypes))
        result.homeMappings = externalHomeFolderService.getHomeFolderMappings(Long.valueOf(params.id))

        result.agentCanWrite = agentCanWrite
        result.informationSheetDisplayed = SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed()

        result.dietsList = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration()
        result.dietsListKey = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().keySet()
        result.dietsListLibelle = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().values()
        result.informationSheetRequiredFieldsActived = SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()

        result.groups = requestTypeAdaptorService.getActiveRequestTypeByDisplayGroup(homeFolder)

        if(!SecurityContext.getCurrentConfigurationBean().getExternalApplicationProperty("booker.url").isEmpty()
          && categoryService.hasWriteProfile(SecurityContext.getCurrentAgent())
          && userSearchService.hasExternalLibredematId(result.homeFolderResponsible.id)) {
            result.groups["Other"] = ['label': "Autre",requests:[['label':"Planning",'id':'booking']]]
        }

        result.unarchivableIndividuals = unarchivableIndividuals

        result.canResetPassword = isAllowedToResetPassword(homeFolder, result.homeFolderResponsible)
        result.isAccessSanitaire = SecurityContext.getCurrentAgent().getIsSanitaire()
        return result
    }

    private getRolesByChildren(adults) {
        def roles = []
        for(Adult adult : adults) {
            roles.add(getRolesByChild(adult))
        }
        return roles
    }

    private getRolesByChild(adult) {
        def children = userSearchService.getChildren(adult.homeFolder.id)
        def roles = []
        for(Child child : children) {
            def roleOnChild = adult.individualRoles.find{ it.individualId == child.id}
            if (roleOnChild) {
                roles.add(['role': roleOnChild.role,
                           'childId': child.id,
                           'adultId': adult.id,
                           'adultFullName': adult.fullName])
            } else {
                roles.add(['role': false,
                           'adultFullName': adult.fullName,
                           'childId': child.id,
                           'adultId': adult.id])
            }
        }
        return roles
    }

    private getRolesByAdults(adults) {
        def roles = [:]
        for(Adult adult : adults) {
            roles.put(adult.id,getRolesByAdult(adult))
        }
        return roles
    }

    private getRolesByAdult(adult) {
        def children = userSearchService.getChildren(adult.homeFolder.id)
        def roles = []
        for(Child child : children) {
            def roleOnChild = adult.individualRoles.find{ it.individualId == child.id}
            if (roleOnChild) {
                roles.add(['role': roleOnChild.role,
                           'subjectName': child.fullName,
                           'childId': child.id,
                           'adultId': adult.id])
            } else {
                roles.add(['role': false,
                           'subjectName': child.fullName,
                           'childId': child.id,
                           'adultId': adult.id])
            }
        }
        return roles
    }

    def create = {
        if (request.post) {
            def adult = new Adult()
            DataBindingUtils.initBind(adult, params)
            bind(adult)
            def invalidFields = userService.validate(adult, false)
            if (!invalidFields.isEmpty()) {
                session.doRollback = true
                render (['invalidFields': invalidFields] as JSON)
                return false
            }
            userWorkflowService.create(adult, false, null)
            render (['id' : adult.homeFolder.id] as JSON)
            return false
        }
        return (['subMenuEntries': subMenuEntries,
                 'defaultEmail': SecurityContext.getCurrentConfigurationBean().getDefaultEmail()])
    }

    def adult = { 
        def adult, template
        def mode = params.mode
        def children = userSearchService.getChildren(Long.valueOf(params.homeFolderId))
        if (!params.id) {
            adult =  new Adult()
            template = 'adult'
            flash.homeFolderId = params.homeFolderId
        } else {
            adult = userSearchService.getAdultById(Long.valueOf(params.id))
            template = params.template ? params.template : 'adult'
        }

        if (request.post && !adult.id) {
            mode = 'static'
            DataBindingUtils.initBind(adult, params)
            bind(adult)
            def homefolder = userSearchService.getHomeFolderById(Long.valueOf(params.homeFolderId))
            adult.address = homefolder.address
            def invalidFields = userService.validate(adult)
            if (!invalidFields.isEmpty()) {
                session.doRollback = true
                render (['invalidFields': invalidFields] as JSON)
                return false
            }

            userWorkflowService.add(homefolder, adult, false)
            for(Child child : children) {
                if (params.get('type_'+child.id)) {
                    userWorkflowService.link(adult,child, [RoleType.forString(params.get('type_'+child.id))])
                    if (!userService.validate(child).isEmpty())  {
                        session.doRollback = true
                        def errors = []
                        errors.add("type_${child.id}")
                        render (['invalidFields': errors] as JSON)
                        return false
                    }
                }
            }
            render (['status': 'success', 'type':'adult', 'id': adult.id] as JSON)
            return false
        }
        def homeFolderResponsible = userSearchService.getHomeFolderResponsible(Long.valueOf(params.homeFolderId))
        def adults = userSearchService.getAdults(Long.valueOf(params.homeFolderId), UserState.activeStates).findAll{it.id != homeFolderResponsible.id}
        def adultsRoles = getRolesByAdults(adults)
        render(template: mode + '/' + template, model:['adult': adult, 'children': children, 'adultsRoles': adultsRoles])
    }

    def child = {
        def child, template, homeFolderId
        def mode = params.mode
        if (!params.id) {
            child =  new Child()
            template = 'child'
            homeFolderId = Long.valueOf(params.homeFolderId)
            flash.homeFolderId = params.homeFolderId
        } else {
            child = userSearchService.getChildById(Long.valueOf(params.id))
            template = params.template ? params.template : 'child'
            homeFolderId = child.homeFolder.id
        }
        if (request.post && !child.id) {
            mode = 'static'
            bind(child)
            userWorkflowService.add(userSearchService.getHomeFolderById(homeFolderId), child)
            params.roles.each {
                if (it.value instanceof GrailsParameterMap && it.value.owner != '' && it.value.type != '') {
                    userWorkflowService.link(
                        userSearchService.getById(Long.valueOf(it.value.owner)),
                        child, [RoleType.valueOf(it.value.type)])
                }
            }
            def invalidFields = userService.validate(child)
            if (!invalidFields.isEmpty()) {
                session.doRollback = true
                if (invalidFields.contains('legalResponsibles')) {
                    userSearchService.getAdults(child.homeFolder.id).eachWithIndex { adult, index ->
                        invalidFields.add("roles.${index}.type")
                    }
                }
                render (['invalidFields': invalidFields] as JSON)
                return false
            }
            render (['status': 'success', 'type':'child', 'id': child.id] as JSON)
            return false
        }
        def models = ['child': child]
        models['adults'] = userSearchService.getAdults(homeFolderId).findAll{ it.state != UserState.ARCHIVED }
        if (child.id) {
            models['roleOwners'] = userSearchService.listBySubjectRoles(child.id, RoleType.childRoleTypes)
        }
        render(template: mode + '/' + template, model: models)
    }

    def removeIndividual = {
        def user = userSearchService.getById(params.long("id"))
        try {
            userWorkflowService.changeState(user, UserState.ARCHIVED)
            render(['status':'success', 'message':message(code:'homeFolder.message.individualRemoveSuccess')] as JSON)
        } catch (CvqModelException cme) {
            render(['status':'error', 'message':cme.message] as JSON)
        }
    }

    def resetPassword = {
        def adult = userSearchService.getHomeFolderResponsible(params.long("id"))
        userWorkflowService.launchResetPasswordProcess(adult)
        render(['status': 'success', 'message': message(code:'homeFolder.action.resetPwd.btnFeedback')] as JSON)
    }

    def state = {
        def user
        try {
            user = userSearchService.getById(params.long("id"))
        } catch (CvqObjectNotFoundException) {
            user = userSearchService.getHomeFolderById(params.long("id"))
        }
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            userWorkflowService.changeState(user, UserState.forString(params.state))
        }
        render(template : mode + "/state", model : [
            "user" : user, "states" : userWorkflowService.getPossibleTransitions(user)])
    }
    
    def validateHomeFolder = {
            def homeFolder = userSearchService.getHomeFolderById(Long.parseLong(params.id));
            userWorkflowService.validateHomeFolder(homeFolder);
            redirect(action: 'details',id: params.id)
    }

    def processDuplicate = {
        if (params.ignore) {
            userDeduplicationService.removeDuplicate(params.long('homeFolderId'), params.long('targetHomeFolderId'))
        } else if (params.merge) {
            userDeduplicationService.mergeDuplicate(params.long('homeFolderId'), params.long('targetHomeFolderId'))
        }
        redirect(action:'details', params:['id': params.homeFolderId])
    }

    def realizeRequest = {
      def requestTypeLabel = params.requestTypeId != null && !params.requestTypeId.isEmpty() ?
                               params.requestTypeId.equals("booking") ?
                                 "booking" : requestTypeService.getRequestTypeById(Long.valueOf(params.requestTypeId)).label : null

      session.startAgentSpoofEcitizenProcess = true
      redirect(controller: 'frontofficeHome', action:'loginAgent', params:['id' : params.id, 'requestTypeLabel' : requestTypeLabel])
    }

    def address = {
        def adult = userSearchService.getAdultById(params.long("id"))
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            try {
                def temp = new Address()
                bind(temp)
                individualAdaptorService.historize(
                    adult, adult.address, temp, "address",
                    ["additionalDeliveryInformation", "additionalGeographicalInformation", "city",
                        "cityInseeCode", "countryName", "placeNameOrService", "postalCode",
                        "streetMatriculation", "streetName", "streetNumber", "streetRivoliCode"])
            } catch (CvqValidationException e) {
                session.doRollback = true
                def invalidFields = []
                e.invalidFields.each{ invalidFields.add(it = it.replace('address.',''))}
                render (['invalidFields': invalidFields] as JSON)
                return false
            }
        }
        render(template : mode + "/address", model : ["user" : adult])
    }

    def contact = {
        def adult = userSearchService.getAdultById(params.long("id"))
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            try {
                def temp = new Adult()
                bind(temp)
                individualAdaptorService.historize(
                    adult, adult, temp, "contact", ["email", "homePhone", "mobilePhone", "officePhone", "smsPermission"])
            } catch (CvqValidationException e) {
                session.doRollback = true
                render (['invalidFields': e.invalidFields] as JSON)
                return false
            }
        }
        render(template : mode + "/contact", model : ["adult" : adult])
    }

    def childInformationSheet = {
      def child = userSearchService.getChildById(params.long("id"))
        def mode = request.get ? params.mode : "static"
        if (request.post) {
          try {
            // Fiche de renseignement enfant
            if (child.childInformationSheet == null) 
            {
              addChildInformationSheet(child, new ChildInformationSheet())
            }
            // Régimes alimentaires
            def dietSet = new LinkedHashSet<Diet>()
              def dietsListKey = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().keySet()		 
              for (String diet : dietsListKey)
              {
                if (params.getAt(diet) != null)
                {
                  dietSet.add(new Diet(diet));
                }
              }
            params.diets = dietSet;

            def temp = new ChildInformationSheet()
              def fields = ["telephonePortable", "emailEnfant", "nomOrganismeAssurance", "numeroOrganismeAssurance",
                  "nomMedecinTraitant", "telephoneMedecinTraitant", "allergie", "vaccinBcg", "vaccinDtPolio",
                  "vaccinInjectionSerum", "vaccinRor", "vaccinTetracoqPentacoq", "vaccinAutre",
                  "recommandationParent", "difficulteSante", "projetAccueilIndividualise", "autorisationDroitImage",
                  "autorisationMaquillage", "autorisationTransporterVehiculeMunicipal",
                  "autorisationTransporterTransportCommun", "autorisationHospitalisation", 
                  "autorisationRentrerSeul", "diets", 
                  "personneAutoriseNom1", "personneAutoriseNom2", "personneAutoriseNom3", 
                  "personneAutorisePrenom1", "personneAutorisePrenom2", "personneAutorisePrenom3", 
                  "personneAutoriseTelephone1", "personneAutoriseTelephone2", "personneAutoriseTelephone3"]

                    bind(temp)

                    individualAdaptorService.historize(
                        child, child.childInformationSheet, temp, "childInformationSheet", fields)

                    // Fiche de renseignement enfant
                    // Vérification de la fiche de renseignement enfant
                    // On ne vérifie les annotations NotNull que si la fiche est paramétrée à obligatoire dans l'asset
                    // Dans tous les cas, on vérifie les autres types d'annotations
                    // (utilisation de profil)
                    def invalidFieldsChildInformationSheet = userService.validate(child.childInformationSheet,
                        SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired())
                    if (!invalidFieldsChildInformationSheet.isEmpty()) {
                      throw new CvqValidationException(invalidFieldsChildInformationSheet)
                    }
                    else {
                      // La fiche de renseignement a été validée correctement
                      // Cette date permet de savoir que le formulaire a été validé correctement
                      // On peut effacer périodiquement cette date pour tous les comptes pour obliger les citoyens à revalider
                      // leur fiche de renseignements
                      child.childInformationSheet.validationDate = new Date()
                    }

          } 
          catch (CvqValidationException e) {
            session.doRollback = true
              render (['invalidFields': e.invalidFields] as JSON)
              return false
          }
        }
      // Filtrage des régimes alimentaires
      def dietsList = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration()
        def dietsListKey = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().keySet()
        def dietsListLibelle = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().values()
        def informationSheetRequiredFieldsActived = SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()
        render(template : mode + "/childInformationSheet", model : ["child" : child, "dietsList" : dietsList, "dietsListKey" : dietsListKey, "dietsListLibelle" : dietsListLibelle, "informationSheetRequiredFieldsActived" : informationSheetRequiredFieldsActived])
    }
    private addChildInformationSheet(child, childInformationSheet) throws CvqValidationException {
      bind(childInformationSheet)
        userWorkflowService.addChildInformationSheet(child, childInformationSheet)
        def invalidFields = userService.validate(childInformationSheet,
            SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired())
        if (!invalidFields.isEmpty())
          throw new CvqValidationException(invalidFields)
    }
    def identity = {
        def individual = userSearchService.getById(params.long("id"))
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            try {
                def temp = individual instanceof Adult ? new Adult() : new Child()
                bind(temp)
                individualAdaptorService.historize(
                    individual, individual, temp, "identity",
                    individual instanceof Adult ?
                        ["title", "familyStatus", "lastName", "maidenName", "firstName", "firstName2", "firstName3", "profession", "cfbn"] :
                        ["born", "lastName", "firstName", "firstName2", "firstName3", "sex", "birthDate", "birthPostalCode", "birthCity", "birthCountry"])
            } catch (CvqValidationException e) {
                session.doRollback = true 
                render (['invalidFields': e.invalidFields] as JSON)
                return false
            }
        }
        render(template : mode + "/" + individual.class.simpleName.toLowerCase() + "Identity",
            model : ["individual" : individual])
    }

    
    def mapping = {
    	if (request.post) {
    	  externalHomeFolderService.setExternalId(params.externalServiceLabel, Long.valueOf(params.homeFolderId), Long.valueOf(params.id), params.externalId)
    	}
        def individual = userSearchService.getById(params.long("id"))
        def mapping = externalHomeFolderService.getIndividualMapping(individual, params.externalServiceLabel)
        def mode = request.get ? params.mode : "static"
        render(template : mode + "/mapping", model : ["mapping" : mapping])
    }

    def homeFolderMapping =
    {
      if (request.post)
      {
        def homeFolderMappingObject = externalHomeFolderService.getHomeFolderMapping(params.externalServiceLabel, Long.valueOf(params.homeFolderId))
          homeFolderMappingObject.externalId = params.externalId
          externalHomeFolderService.modifyHomeFolderMapping(homeFolderMappingObject)
          //externalHomeFolderService.setExternalId(params.externalServiceLabel, Long.valueOf(params.homeFolderId), Long.valueOf(params.mappingId), params.externalId)
      }
      def individual = userSearchService.getById(params.long("id"))
        //def mapping = externalHomeFolderService.getIndividualMapping(individual, params.externalServiceLabel)
        def mapping = externalHomeFolderService.getHomeFolderMapping(params.externalServiceLabel, individual.homeFolder.id)
        def mode = request.get ? params.mode : "static"
        render(template : mode + "/homeFolderMapping", model : ["mapping" : mapping, 'individualId':individual.id])
    }

    def responsibles = {
        def child = userSearchService.getChildById(Long.valueOf(params.id))
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            params.roles.each {
                if (it.value instanceof GrailsParameterMap && it.value.owner != '') {
                    def owner = userSearchService.getById(Long.valueOf(it.value.owner))
                    if (it.value.type == '')
                        userWorkflowService.unlink(owner,child)
                    else
                        userWorkflowService.link(owner,child, [RoleType.forString(it.value.type)])
                }
            }
            if (!userService.validate(child).isEmpty())  {
                session.doRollback = true
                def errors = []
                userSearchService.getAdults(child.homeFolder.id).eachWithIndex { adult, index ->
                    errors.add("roles.${index}.type")
                }
                render (['invalidFields': errors] as JSON)
                return false
            } 
        }
        def adults = userSearchService.getAdults(child.homeFolder.id)
        def model = [
            "child" : child,
            "adults" : adults,
            "roles" : mode == 'static' ?
            getRolesByChildren(adults)
            : homeFolderAdaptorService.roleOwners(child.id),
            "roleOwners" : mode == 'static' ? 
                getRolesByChildren(adults)
                : homeFolderAdaptorService.roleOwners(child.id)
        ]
        render(template : mode + "/responsibles", model : model)
    }
    
//    def adultResponsibles = {
//        def individual = userSearchService.getById(params.long("id"))
//        def mode = request.get ? params.mode : "static"
//        if (request.post) {
//            try {
//                println("\n\n\n\n"+((GrailsParameterMap)params.roles))
//                params.roles.keySet().each { index ->
//                    def role = params.roles.get(index)
//                    //if (role.value instanceof GrailsParameterMap && role.value.owner != '') {
//                        println("\n\n\n\n"+index)
//                        println("\n\n\n\n"+params.long("child_"+index))
//                        def child = userSearchService.getChildById(params.long("child_"+index))
//                        if (role.type == '')
//                            userWorkflowService.unlink(individual,child)
//                        else
//                            userWorkflowService.link(individual,child, [RoleType.forString(role.type)])
//
//                        if (!userService.validate(child).isEmpty())  {
//                            session.doRollback = true
//                            def errors = []
//                            userSearchService.getAdults(child.homeFolder.id).eachWithIndex { adult, i ->
//                                errors.add("roles.${i}.type")
//                            }
//                            render (['invalidFields': errors] as JSON)
//                            return false
//                        }
//                    //}
//                }
//            } catch (CvqValidationException e) {
//                session.doRollback = true
//                render (['invalidFields': e.invalidFields] as JSON)
//                return false
//            }
//        }
//        def roles = getRolesByAdult(individual)
//        render(template : mode + "/adultResponsibles",
//            model : ["adult" : individual, "roles": roles])
//    }

    def adultResponsibles = {
        def individual = userSearchService.getById(params.long("id"))
        def children = userSearchService.getChildren(individual.homeFolder.id)
        def mode = request.get ? params.mode : "static"
        if (request.post) {
            try {
                for(Child child : children) {
                    if (params.get('type_'+child.id) == '') {
                        userWorkflowService.unlink(individual,child)
                    }
                    else {
                        userWorkflowService.link(individual,child, [RoleType.forString(params.get('type_'+child.id))])
                    }

                    if (!userService.validate(child).isEmpty())  {
                        session.doRollback = true
                        def errors = []
                        errors.add("type_${child.id}")
                        render (['invalidFields': errors] as JSON)
                        return false
                    }
                }
            } catch (CvqValidationException e) {
                session.doRollback = true
                render (['invalidFields': e.invalidFields] as JSON)
                return false
            }
        }
        def roles = getRolesByAdult(individual)
        render(template : mode + "/adultResponsibles",
            model : ["adult" : individual, "roles": roles])
    }

    def actions = {
        def list = new ArrayList(userSearchService.getHomeFolderById(Long.valueOf(params.id)).actions)
        Collections.reverse(list)
        return ["actions" : homeFolderAdaptorService.prepareActions(list),
                "archived" : params.boolean("archived")]
    }

    def view = {
        if (!request.get) return false
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition",
            "attachment; filename=historique.pdf")
        def actionId = Long.valueOf(params.requestActionId); //% 512;
        def action = userSearchService.getById(Long.valueOf(params.id)).homeFolder.actions.find { a -> a.id == actionId}

        response.contentLength = action.file.length
        response.outputStream << action.file
        response.outputStream.flush();
    }

    def currentHomeFolderState = {
        def result = [:];
        def homeFolder = userSearchService.getHomeFolderById(Long.parseLong(params.id));
        result.homeFolderState = homeFolder.state.toString().toLowerCase();
        def isValidable=false;
        if(homeFolder.state.equals(UserState.NEW) || homeFolder.state.equals(UserState.MODIFIED)) {
            isValidable=true;
        }
        result.isValidable=isValidable;
        result.homeFolder=homeFolder;
        return result;
    }

    def currentResetPasswordBox = {
        def homeFolder = userSearchService.getHomeFolderById(Long.parseLong(params.id))
        def responsible = userSearchService.getHomeFolderResponsible(homeFolder.id)
        def model = [
            canResetPassword : isAllowedToResetPassword(homeFolder, responsible)
        ]
        render(template: "currentResetPasswordBox", model: model)
    }

    // TODO : move in request module
    def requests = {
        def result = [requests:[]]
        def homeFolderRequests =
            requestSearchService.getByHomeFolderId(Long.valueOf(params.id), false);

        homeFolderRequests.each {
          def record = requestAdaptorService.prepareRecordForSummaryView(it)
          result.requests.add(record)
        }
        return result
    }
    
    // TODO : move in payment module
    def payments = {
        def result = [payments:[]]
        
        for (Payment payment : this.paymentService.getByHomeFolder(Long.parseLong(params.id))) {
            result.payments.add([
                'id' : payment.id,
                'initializationDate' : payment.initializationDate,
                'state' : payment.state.toString(),
                'bankReference' : payment.bankReference,
                'amount' : payment.amount,
                'paymentMode' : message(code:"payment.mode."+payment.paymentMode.toString().toLowerCase())
            ])
        }
        
        return result
    }

    def meansOfContact = {
        return ["subMenuEntries" : subMenuEntries]
    }

    def moCs = {
        render(template : "moCs",
               model : ["moCs" : meansOfContactService.availableMeansOfContact])
    }

    def moC = {
        if (request.post) {
            def moc = meansOfContactService.getById(Long.valueOf(params.id))
            if (params.enabled == 'true') meansOfContactService.disableMeansOfContact(moc)
            else if (params.enabled == 'false') meansOfContactService.enableMeansOfContact(moc)
            render ([status : "success", success_msg : message(code : "message.updateDone")] as JSON)
        }
    }

    def importHomeFolders = {
        if (request.get) {
            render(view : "import", model : [
                "subMenuEntries" : subMenuEntries,
                "hasAdminEmail" : SecurityContext.currentSite.adminEmail
            ])
            return false
        } else if (request.post) {
            if (!SecurityContext.currentSite.adminEmail) {
                render (new JSON([status : "error",
                    error_msg : message(code : "homeFolder.import.error.noAdminEmail")]).toString())
                return false
            }
            def file = request.getFile("document")
            def doc
            try {
                doc = HomeFolderImportDocument.Factory.parse(file.inputStream)
                // first validate the data
                List<XmlError> errors = new ArrayList<XmlError>()
                XmlOptions options = new XmlOptions()
                options.setErrorListener(errors)
                doc.validate(options)
                if (!errors.isEmpty()) {
                    log.error "Got validation errors for current file"
                    for (XmlError error : errors) {
                        log.error "Message: ${error.getMessage()}"
                        log.error "Location of invalid XML: ${error.getCursorLocation().xmlText()}"
                    }
                    render (new JSON([status : "error",
                    error_msg : message(code : "homeFolder.import.error.invalidFile")]).toString())
                    return false
                }
            } catch (XmlException e) {
                render (new JSON([status : "error",
                    error_msg : message(code : "homeFolder.import.error.invalidFile")]).toString())
                return false
            } catch (IOException e) {
                render (new JSON([status : "error",
                    error_msg : message(code : "homeFolder.import.error.invalidFile")]).toString())
                return false
            }
            userWorkflowService.importHomeFolders(doc)
            render (new JSON([status : "ok", success_msg :
                message(code : "homeFolder.import.message.started",
                    args : [SecurityContext.currentSite.adminEmail])]).toString())
            return false
        }
    }

    def saveRule = {
        if(request.post) {
            def requestTypeLabelAsDir = "childInformationSheetRequest"
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
                requestTypeLabelAsDir + '/acceptationReglementInterieur', file.bytes)
            render (new JSON([ 'status':'success',
                               'message':message(code:'message.updateDone')]).toString())
        } else {
            render (new JSON(['status':'error', 'message':message(code:'requestType.message.noPdfRulesFile')]).toString())
            return false
        }
    }

        def childInformationSheetDateInitialisation = {
        	if (request.get) 
			{
                def rulesField = localAuthorityRegistry.getLocalAuthorityResourceFile(Type.PDF,
                "childInformationSheetRequest/acceptationReglementInterieur", false)
        	    render(view : "childInformationSheetDateInitialisation", model : [
        			"subMenuEntries" : subMenuEntries,
        			"isInformationSheetDisplayed" : SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed(),
                     "informationSheetRequiredFieldsActived" : SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived(),
                     "rulesField" : rulesField != null && rulesField.exists()
        		    ])
        	    return false
        	} 
			else if (request.post) 
			{
				try 
				{
				    userWorkflowService.childInformationSheetDateInitialisation()
				    render (new JSON([status : "ok", success_msg :
					message(code : "homeFolder.childInformationSheetDateInitialisation.message.performed",
					args : [
						SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed()
					])]).toString())
				}
				catch (Exception e) 
				{
				    logger.error e.getMessage()
				    render (new JSON([status : "error",
					error_msg : message(code : "homeFolder.childInformationSheetDateInitialisation.error.initialisation")]).toString())
				    	return false
				}
        		return false
        	}
        }

    def synchronisation = {
        if(request.get) {
            def externalProvider = SecurityContext.getCurrentConfigurationBean().getExternalServices().entrySet().collect{ it.getKey().label }

            render(view : "synchronisation", model : [
                        "subMenuEntries" : subMenuEntries,
                        "isInformationSheetDisplayed" : SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed(),
                        "posted" : false,
                        "externalProviders" : externalProvider,
                        "defaultEmail" : SecurityContext.getCurrentAgent().email
                        ])
            return false
        } else if(request.post) {
            try { 
                userSynchronisationService.synchroniseAll(params.list("services"), params.email)
            } catch (Exception e) {
                render (new JSON([status : "error", msg : e.message]).toString())
                return false
            }
            render (new JSON([status : "success", msg :message(code:"homeFolder.synchronisation.notification.subject")]).toString())
            return false
        }
    }
    protected List doSearch(state) {
        return userSearchService.get(prepareCriterias(state), prepareSort(state), defaultMax,
            params.currentOffset ? Integer.parseInt(params.currentOffset) : 0, true)
    }
    
    protected Set<Critere> prepareCriterias(state) {
        def mapper =[:]
        mapper.lastName = Critere.STARTSWITH
        mapper.firstName = Critere.STARTSWITH
        mapper.email = Critere.STARTSWITH
        mapper.homeFolderId = Critere.EQUALS
        mapper.homeFolderState = Critere.EQUALS 
        mapper.isHomeFolderResponsible = Critere.EQUALS
        mapper.isDuplicateAlert = Critere.EQUALS
        mapper.userState = Critere.EQUALS
        Set<Critere> criterias = new LinkedHashSet<Critere>()
        
        for(String key : state.keySet()){
            if(mapper.keySet().contains(key) && state."$key") {
                Critere criteria = new Critere()
                criteria.setAttribut(key)
                criteria.setComparatif(mapper[key].toString())
                if (key.equals('homeFolderId'))
                    criteria.setValue(LongUtils.stringToLong(state[key]))
                else
                    criteria.setValue(state[key])
                criterias.add(criteria)
            }
        }
        Critere criteria = new Critere()
        criteria.setAttribut(Individual.SEARCH_IS_TEMPORARY)
        criteria.setComparatif(Critere.EQUALS)
        criteria.setValue(false)
        criterias.add(criteria)

        if (!UserState.ARCHIVED.toString().equals(state['homeFolderState']) &&
            !state['userState']) {
          Critere notArchived = new Critere()
          notArchived.setAttribut(Individual.SEARCH_BY_USER_STATE)
          notArchived.setComparatif(Critere.NEQUALS)
          notArchived.setValue(UserState.ARCHIVED.toString())
          criterias.add(notArchived)
        }

        return criterias
    }
    
    protected Map<String,String> prepareSort(state) {
        if(!state?.orderBy) state.orderBy = 'creationDate'
        Map<String,String> result = new HashMap<String,String>();
        def orderBy = 'asc'
        if (state.orderBy.contains('Date'))
            orderBy = 'desc'
        result.put("individual." + state.orderBy, orderBy)
        return result
    }
    
    protected List buildHomeFolderStatusFilter() {
        def result = []
        result.add(['name':'true','i18nKey': message(code:'property.active')])
        result.add(['name':'false','i18nKey':message(code:'property.inactive')])
        return result
    }
    
    protected List buildHomeFolderStateFilter() {
        def result = []

        for(UserState state : UserState.filtersStates) {
            result.add([
                'name':state.toString(),
                'i18nKey': message(code:"user.state.${state.toString().toLowerCase()}")
            ])
        }
        return result;
    }

    def listTasks = {
        def state = [:]
        if (params.pageState) state = JSON.parse(params.pageState)

        render(view : 'search', model: [
            'agentCanWrite': userSecurityService.canWrite(SecurityContext.currentAgent.id),
            'state': state,
            'records' : userSearchService.listTasks(QoS.forString(params.qoS), prepareSort(state), defaultMax,
                    params.currentOffset ? Integer.parseInt(params.currentOffset) : 0),
            'count' : userSearchService.countTasks(QoS.forString(params.qoS)),
            'max': this.defaultMax,
            'homeFolderStates': buildHomeFolderStateFilter(),
            'currentSiteName': SecurityContext.currentSite.name,
            'homeFolderStatus' : buildHomeFolderStatusFilter(),
            'pageState' : (new JSON(state)).toString().encodeAsHTML(),
            'offset' : params.currentOffset ? params.currentOffset : 0,
            'subMenuEntries' : subMenuEntries,
            'formActionLink': createLink(action:'listTasks')
        ]);
    }

    def listDuplicates = {
        def state = [:]
        if (params.pageState) state = JSON.parse(params.pageState)
        state = state.plus(['isDuplicateAlert': true])

        render(view : 'search', model: [
            'agentCanWrite': userSecurityService.canWrite(SecurityContext.currentAgent.id),
            'state': state,
            'records' : userSearchService.listDuplicates(prepareSort(state), defaultMax,
                    params.currentOffset ? Integer.parseInt(params.currentOffset) : 0),
            'count' : userSearchService.countDuplicates(),
            'max': this.defaultMax,
            'homeFolderStates': buildHomeFolderStateFilter(),
            'currentSiteName': SecurityContext.currentSite.name,
            'homeFolderStatus' : buildHomeFolderStatusFilter(),
            'pageState' : (new JSON(state)).toString().encodeAsHTML(),
            'offset' : params.currentOffset ? params.currentOffset : 0,
            'subMenuEntries' : subMenuEntries,
            'formActionLink': createLink(action:'listDuplicates')
        ]);
    }
    /**
     * Home folders configuration:
     * Enable/disable home folder creation without starting a request.
     * Set document types wished at home folder creation time.
     */
    def configure = {
        try {
            def bool = userService.homeFolderIndependentCreationEnabled();
            def blockDuplicateCreation = userService.blockDuplicateCreationEnabled();
            return (['subMenuEntries': subMenuEntries, 'independentCreationEnabled': bool,
                'blockDuplicateCreationEnabled': blockDuplicateCreation])
        } catch (PermissionException pe) {
            render(text: message(code: pe.message), status: 403)
        }
    }

    def setBlockDuplicateCreation = {
        try {
            if (params.blockDuplicateCreation == "1")
                userService.enableBlockDuplicateCreation()
            else
                userService.disableBlockDuplicateCreation()
            render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
        } catch (PermissionException pe) {
            render(text: message(code: pe.message), status: 403)
        }
    }

    def setIndependentCreation = {
        try {
            if (params.independentCreation == "1")
                userService.enableHomeFolderIndependentCreation()
            else
                userService.disableHomeFolderIndependentCreation()
            render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
        } catch (PermissionException pe) {
            render(text: message(code: pe.message), status: 403)
        }
    }

    def documentList = {
        def list = []
        def wishedDocumentTypes = homeFolderDocumentService.wishedDocumentTypes()
        documentTypeService.getAllDocumentTypes().each{ d ->
            list.add([
                'documentId' : d.id,
                'name' : message(code:LibredematUtils.adaptDocumentTypeName(d.name)),
                'bound' : wishedDocumentTypes.contains(d),
                'class' : wishedDocumentTypes.contains(d) ? '' : 'notBelong'
            ])
        }
        list = list.sort{ it.name }
        render(template:"documentList", model:["documents":list])
    }

    def associateDocument = {
        try {
            homeFolderDocumentService.wish(Long.valueOf(params.dtid))
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        } catch (PermissionException pe) {
            render(text: message(code: pe.message), status: 403)
        }
    }

    def unassociateDocument = {
        try {
            homeFolderDocumentService.unwish(Long.valueOf(params.dtid))
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        } catch (PermissionException pe) {
            render(text: message(code: pe.message), status: 403)
        }
    }


    /*def note = {
        def hf = userSearchService.getHomeFolderById(Long.valueOf(params.id))
        render(template: 'note', model: ['hf': hf])
    }

    def saveNote = {
        def hf = userSearchService.getHomeFolderById(Long.valueOf(params.id))
        if (hf && params.note) {
            def action = new UserAction(UserAction.Type.INTERNAL_NOTE, hf.id)
            action.userId = SecurityContext.getCurrentUserId()
            action.note = params.note
            hf.actions.add(action)
            homeFolderDAO.update(hf)

            render(text:['message':message(code:'homeFolder.note.added')] as JSON)
        } else {
            render(status:403, text:['message':message(code:'homeFolder.note.add.failed')] as JSON)
        }
    }*/

    private isAllowedToResetPassword(homeFolder, homeFolderResponsible) {
        return userService.hasValidEmail(homeFolderResponsible) && homeFolder.state != UserState.ARCHIVED
    }

    def synchronise = {
        def individual = userSearchService.getById(params.id.toLong());
        try {
          userSynchronisationService.synchronise(params.list("services"), individual)
        }
        catch (Exception ex)
        {
            render (new JSON([status : "error", msg : message('code' : 'homeFolder.synchronisation.notification.fail.nomessage')]).toString())
            return false
        }
        render (new JSON([status : "success", msg : message('code' : 'homeFolder.synchronisation.notification.success')]).toString())
        return false
    }

    /**
     * retrieves request form list using passed request type id
     */
    def formList = {
        def id = Long.valueOf(params.id)
        def mailType = RequestFormType.HOMEFOLDER_MAIL_TEMPLATE
        def forms = requestTypeService.getRequestFormsByRequestFormType(mailType)
        render(template:"formList",model:["requestForms":forms])
    }

    /**
     * Hack Inexine
     */
    def form = {
        def method = request.getMethod().toLowerCase(), id
        if(method == "post" && params?.requestTypeId) {
            RequestForm form = new RequestForm()
            if(params.requestFormId) {
                form = requestTypeService.getRequestFormById(Long.valueOf(params.requestFormId))
            }
            form.setType(RequestFormType.HOMEFOLDER_MAIL_TEMPLATE)
            form.setLabel(params.label)
            form.setTemplateName(params.templateName)
            form.setShortLabel(params.shortLabel)
            id = requestTypeService.modifyHomeFolderRequestForm(form)

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
            requestTypeService.removeRequestTypeForm(Long.valueOf(params.id))
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }

    /**
     * Hack Inexine
     */
    def mailTemplate = {
        if(request.post) {
            if(params.editor != "") {
                RequestForm form = requestTypeService
                        .getRequestFormById(Long.valueOf(params.requestFormId))
                form.setType(RequestFormType.HOMEFOLDER_MAIL_TEMPLATE)
                form.setPersonalizedData(params.editor.getBytes())

                requestTypeService.modifyHomeFolderRequestForm(form)
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

    /**
     * Hack Inexine
     */
    def loadMailTemplate = {
        def fileName = params.file
        File templateFile = localAuthorityRegistry
                .getLocalAuthorityResourceFile(Type.MAIL_TEMPLATES, fileName, false)

        if(templateFile.exists()) {
            response.contentType = 'text/html; charset=utf-8'

            def forms = []
            forms.add(requestTypeService.getRequestFormById(Long.valueOf(params.formId)))

            def content = templateFile.text
            def template = groovyPagesTemplateEngine.createTemplate(content,'page1')

            def requestAttributes = RequestContextHolder.currentRequestAttributes()
            def out = new StringWriter()
            def originalOut = requestAttributes.getOut()
            requestAttributes.setOut(out)
            template.make(['name':fileName,'forms':forms]).writeTo(out)
            requestAttributes.setOut(originalOut)

            render out.toString()
        } else {
            render message(code:'message.templateDoesNotExist')
        }
    }

}
