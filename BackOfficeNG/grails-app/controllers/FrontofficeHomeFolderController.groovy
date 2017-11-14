import groovy.text.SimpleTemplateEngine;

import org.libredemat.authentication.IAuthenticationService
import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestState
import org.libredemat.business.users.*
import org.libredemat.business.authority.LocalAuthorityResource
import org.libredemat.exception.CvqAuthenticationFailedException
import org.libredemat.exception.CvqBadPasswordException
import org.libredemat.exception.CvqValidationException
import org.libredemat.exception.CvqException
import org.libredemat.security.SecurityContext
import org.libredemat.service.request.IRequestServiceRegistry
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.users.IUserService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.users.IUserWorkflowService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.users.IUserDeduplicationService
import org.libredemat.util.Critere
import org.libredemat.exception.CvqModelException
import com.octo.captcha.service.CaptchaServiceException
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import net.sf.oval.constraint.EmailCheck

import java.util.Map
import java.util.List

class FrontofficeHomeFolderController {

    IAuthenticationService authenticationService
    IRequestServiceRegistry requestServiceRegistry
    IUserService userService
    IUserSearchService userSearchService
    IUserWorkflowService userWorkflowService
    IRequestSearchService requestSearchService
    ILocalAuthorityRegistry localAuthorityRegistry
    IUserDeduplicationService userDeduplicationService

    def homeFolderAdaptorService
    def individualAdaptorService
    def jcaptchaService
    def securityService
    def documentAdaptorService

    Adult currentEcitizen

    def beforeInterceptor = {
        currentEcitizen = SecurityContext.getCurrentEcitizen()
        Map.metaClass.reduce << { List keys ->
            def reducedMap = [:]
            keys.each {
                if (delegate.get(it) != null)
                    reducedMap.put(it, delegate.get(it))
            }
            return reducedMap
        }
        Individual.metaClass.homeFolderResponsible = {
            def role = null
            delegate.individualRoles.each {
                if (it.homeFolderId) role = it.role
            }
            return role
        }


        if(session["inRQID"])
        {
            def referer = request.getHeader("Referer")
            if(referer)
            if(!referer.contains("/homeFolder"))
            {
                session["inRQID"] = false;
            }
        }

        if(params.inRequestId)
        {
            session["inRQID"] = params.inRequestId
        }

        if(session["inRQID"]) {
            flash.inRequestId = session["inRQID"]
            flash.inRequestLabel = requestSearchService.getById(Long.valueOf(session["inRQID"]), false).getRequestType().getLabel()
        }

    }

    def index = {
        def homeFolder = currentEcitizen.homeFolder
        def children = userSearchService.getChildren(homeFolder.id)
        // FIXME : Poor implementation : db request on Role are crappy
        def childResponsibles = [:]
        children.each {
            childResponsibles.put(it.id, userSearchService.listBySubjectRoles(it.id, RoleType.childRoleTypes))
        }

        def actions = homeFolder.actions.findAll {
            it.type == UserAction.Type.CONTACT
        }.sort {
            it.date
        }.reverse()
        def lastMessage = homeFolderAdaptorService.prepareAction(actions[0])?.contact?.message

        def unarchivableIndividuals = []
        Critere critere = new Critere(Request.SEARCH_BY_HOME_FOLDER_ID, homeFolder.id, Critere.EQUALS)
        Set<Critere> criteria = new HashSet<Critere>();
        criteria.add(critere)
        List<Request> homeFolderRequests = requestSearchService.get(criteria, null, null, -1, 0, false)
        for (Request request : homeFolderRequests) {
            if (request.state != RequestState.ARCHIVED && request.subjectId != null)
                unarchivableIndividuals.add(request.subjectId)
        }

        if (params.idToDelete)
            flash.idToDelete = Long.valueOf(params.idToDelete)

        flash.deletionError = params.deletionError

        return ['homeFolder': homeFolder,
                'adults' : userSearchService.getAdults(homeFolder.id),
                'children': children,
                'childResponsibles' : childResponsibles,
                'documentsByTypes' : documentAdaptorService.homeFolderDocumentsByType(homeFolder.id),
                'lastMessage' : lastMessage,
                'unarchivableIndividuals' : unarchivableIndividuals]
    }

    def create = {
        /*
         * Initialize model
         */
        def model = ['findDuplicate' : '', 'callback' : callback() ]
        def flow = flow()
        if (flow == 'onTheFly') {
                def temporary = requestServiceRegistry
                    .getRequestService(params.requestTypeLabel)?.supportUnregisteredCreation()
                model = model.plus('temporary' : temporary)
        }

        // Force la saisie d'un compte définitif même si la demande à la base permet de créer un compte temporaire
        model = model.plus('createOnlyTemporaryAccountInTS' : SecurityContext.getCurrentConfigurationBean().isCreateOnlyTemporaryAccountInTS())

        /*
         * GET
         */
        if (request.get) {
            switch (flow) {
                case 'standalone' :
                    if (!userService.homeFolderIndependentCreationEnabled())
                        render(text : 'Independent home folder creation is disabled.', status : 403)
                    else
                        render(view : 'createStandalone', model : model)
                    break

                case 'onTheFly' :
                    render(view : 'createOnTheFly', model : model)
                    break
            }

        /*
         * POST
         */
        } else if (request.post) {
            handleResponsiblePost(model)
            if (flash.invalidFields.any()) {
                def parameters = model.callback.params
                parameters.put("callback", params.callback)
                redirect(controller : 'frontofficeHomeFolder', action : 'create', params : parameters)
                return
            } else {

                def authMethod = SecurityContext.getCurrentConfigurationBean().getAuthenticationMethodFront()

                if (SecurityContext.getCurrentConfigurationBean().isAccountValidationRequired() && !params.boolean("temporary")) {
                    render(view: "registerConfirmation", model: model)
                }
                else 
                {
                    if(authMethod.equals("builtin")) {
                        if(params.callback)
                        {
                            redirect(url : params.callback)
                            return
                        }
                        else
                        {
                            redirect(
                                controller : model.callback.controller,
                                action : model.callback.action,
                                params : model.callback.params)
                            return
                        }
                    } else if (authMethod.equals("oauth2")) {
                        def logincallback = params.callback ?: createLink( controller:model.callback.controller
                                , action:model.callback.action
                                , params:model.callback.params).toString()
                        redirect( controller:'OAuth2'
                                , action:'askLogin'
                                , params:['callback':logincallback])
                        return
                    }
                }

            }
        }
        else {
            render(view: '/system/error', model:["i18nKey":"homeFolder.action.confirmation.error"])
        }
    }

    def validation = {
        if (!params.login || !params.key) {
            render(text: 'Lien de validation invalide', status: 400)
            return
        }

        Adult adult = userService.activateAccount(params.login, params.key)
        if (adult != null) {
            def authMethod = SecurityContext.getCurrentConfigurationBean().getAuthenticationMethodFront()
            def model = [ 'callback' : callback() ]

            if(authMethod.equals("builtin")) {
                securityService.setEcitizenSessionInformation(adult, session)
                if(params.callback)
                {
                    redirect(url: params.callback+"?validation=success")
                        return
                }
                else
                {
                    flash.successMessage = message("code": "homeFolder.action.confirmation.validationSuccess")
                    redirect(
                            controller : model.callback.controller,
                            action : model.callback.action,
                            params : model.callback.params)
                        return
                }
            } else if (authMethod.equals("oauth2")) {
                def logincallback = params.callback ?: createLink( controller:model.callback.controller
                        , action:model.callback.action
                        , params:model.callback.params).toString()
                    redirect( controller:'OAuth2'
                            , action:'askLogin'
                            , params:['callback':logincallback])
                    return
            }
        } else {
            render(view: '/system/error', model:["i18nKey":"homeFolder.action.confirmation.error"])
        }
    }

    /**
     * The "flow" variable is used to determine which creation form to display, depending on the "params" map.
     * @return
     *      'onTheFly': if the creation is done on the fly for starting a request,
     *      'standalone': if the creation is standalone.
     */
    private flow() {
        def requestKeys = ['requestTypeLabel', 'requestSeasonId']

        if (params.reduce(requestKeys).any())
            return 'onTheFly'
        else
            return 'standalone'
    }

    /**
     * Guess a callback url using the "params" map.
     * @return an object [ controller:… action:… params:… ] to redirect to after the home folder creation.
     */
    private callback() {
        //Callback defaults to the home folder index.
        def result = [
            'controller' : 'frontofficeHomeFolder',
            'action' : 'index',
            'params' : [:] ]

        def requestKeys = ['requestTypeLabel', 'requestSeasonId']

        if(params.redirectionParams)
          requestKeys = requestKeys + params.findAll{ params.redirectionParams.contains(it.key)}.collect { key, value -> key }

        if (params.reduce(requestKeys).any()) {
            result.controller = 'frontofficeRequestType'
            result.action = 'start'
            result.params = params.reduce(requestKeys)
        }

        return result
    }

    /**
     * Try to create the home folder. If successing, login the user, else set flash variables:
     * - flash.adult
     * - flash.invalidFields
     */
    private handleResponsiblePost(model) throws CvqException {
        Adult adult = new Adult()
        DataBindingUtils.initBind(adult, params)
        bind(adult)
        flash['adult'] = adult
        flash['invalidFields'] = userService.validate(adult, !model.temporary || !params.boolean('temporary'))
        boolean captchaIsValid = false
        try {
            captchaIsValid = jcaptchaService.validateResponse('captchaImage', session.id, params.captchaText)
        } catch (CaptchaServiceException e) {
            //No need to throw an exception when the captcha has expired…
        }
        if (!ServiceAutocompletionController.wayExistAddressReferential(params.address?.cityInseeCode, params.address?.city, params.address?.streetName)) {
          flash.invalidFields.add('address.streetName')
        }
        if (!captchaIsValid) {
            flash.invalidFields.add('captchaText')
        }
        if (userService.blockDuplicateCreationEnabled() && userDeduplicationService.findResponsibleDuplicatesWithoutHomeFolder(adult)) {
            flash.invalidFields.add('user.duplicate.finding')
        }
        if (flash.invalidFields.isEmpty()) {
            session["registerCallback"] = params.callback;
            userWorkflowService.create(adult, model.temporary && params.boolean('temporary'), params.callback)
            if (!SecurityContext.getCurrentConfigurationBean().isAccountValidationRequired() || params.boolean("temporary")) {
                securityService.setEcitizenSessionInformation(adult, session)
            }
        }
    }

    def deleteIndividual = {
        def user = userSearchService.getById(params.long("id"))
        try {
            userWorkflowService.changeState(user, UserState.ARCHIVED)
            redirect(action: 'index')
        } catch (CvqModelException cme) {
            redirect(action: 'index', params: ['deletionError': cme.message, 'idToDelete': user.id])
        }
    }

    def adult = {
        def model = [:]
        def individual
        def children = userSearchService.getChildren(currentEcitizen.homeFolder.id)
        model['children'] = children
        if (params.id) {
            individual = userSearchService.getAdultById(Long.valueOf(params.id))
        } else {
            individual = new Adult()
            // hack : WTF is an unknown title ?
            individual.title = null
            individual.address = currentEcitizen.address
        }
        if (request.post) {
            try {
                def creation = false
                if (individual.id) {
                    if (params.fragment == 'address' && !ServiceAutocompletionController.wayExistAddressReferential(params.cityInseeCode, params.city, params.streetName)) {
                        throw new CvqValidationException(['address.streetName'])
                    }
                    if (params.fragment == 'responsibles') {
                        linkChildrenByAdult(individual, children, params)
                    }
                    historize(params.fragment, individual)
                    if (individual.id == currentEcitizen.id && params.fragment == 'identity') {
                        securityService.setEcitizenSessionInformation(individual, session)
                    }
                } else {
                    creation = true
                    addAdult(individual)
                    linkChildrenByAdult(individual, children, params)
                }
                redirect(action : "adult", params : ["id" : individual.id, 'creation' : creation])
                return false
            } catch (CvqValidationException e) {
                model["invalidFields"] = e.invalidFields
                session.doRollback = true
            }
        }
        model.hideAddressFields = true
        Adult.metaClass.fragmentMode = { name ->
            def template = '/adult' + StringUtils.firstCase(name,'Upper')
            return (name == params.fragment  ? 'edit' : 'static') + template
        }
        // hack inexine
        if (params.warningMessage != null) model['warningMessage'] = params.warningMessage
        model['adult'] = individual
        if (individual.id) {
            model['ownerRoles'] = homeFolderAdaptorService.prepareOwnerRoles(individual)
            def childrenModfied = []
            for(Child child : children) {
                def roleOnChild = individual.individualRoles.find{ it.individualId == child.id}
                if (roleOnChild) {
                    childrenModfied.add([
                        'id': child.id,
                        'fullName': child.fullName,
                        'role': roleOnChild.role])
                } else {
                childrenModfied.add([
                    'id': child.id,
                    'fullName': child.fullName,
                    'role': false])
                }
            }
            model['childrenModfied'] = childrenModfied
        }
        return model
    }

    def child = {
        def model = [:]
        def individual
        if (params.id) {
            individual = userSearchService.getChildById(Long.valueOf(params.id))
        } else {
            individual = new Child()
            // hack : WTF is an unknown sex ?
            individual.sex = null
        }
        boolean failedCreation = false
        if (request.post) {
            try {
                def creation = false
                if (params.fragment == 'informationSheet') {
                    // Fiche de renseignement enfant
                    if (((Child)individual).childInformationSheet == null) {
                        addChildInformationSheet((Child)individual, new ChildInformationSheet())
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
                }
                if (individual.id && params.roleOwnerId) {
                    if (!params.roleType)
                        throw new CvqValidationException(['legalResponsibles'])
                    def owner = userSearchService.getById(Long.valueOf(params.roleOwnerId))
                    link(owner, individual, [RoleType.forString(params.roleType)])
                    redirect(url:createLink(action:'child', params:['id':individual.id, 'fragment':params.fragment]) + '#' + params.fragment)
                    return false
                } else if (individual.id) {
                    historize(params.fragment, individual)
                } else {
                    creation = true
                    addChild(individual)
                }
                if (params.fragment == 'informationSheet') {
                    // Fiche de renseignement enfant
                    // Vérification de la fiche de renseignement enfant
                    // On ne vérifie les annotations NotNull que si la fiche est paramétrée à obligatoire dans l'asset
                    // Dans tous les cas, on vérifie les autres types d'annotations
                    // (utilisation de profil)
                    def invalidFieldsChildInformationSheet = userService.validate(((Child)individual).childInformationSheet,
                        SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired())
                    if (!invalidFieldsChildInformationSheet.isEmpty()) {
                        throw new CvqValidationException(invalidFieldsChildInformationSheet)
                    }
                    else {
                        // La fiche de renseignement a été validée correctement
                        // Cette date permet de savoir que le formulaire a été validé correctement
                        // On peut effacer périodiquement cette date pour tous les comptes pour obliger les citoyens à revalider
                        // leur fiche de renseignements
                        ((Child)individual).childInformationSheet.validationDate = new Date()
                    }
                }
                redirect(action : 'child', params : ['id' : individual.id, 'creation' : creation])
                return false
            } catch (CvqValidationException e) {
                e.printStackTrace()
                if (!params.id) failedCreation = true
                flash['invalidFields'] = e.invalidFields
                session.doRollback = true
            }
        }
        Child.metaClass.fragmentMode = { name ->
            def template = '/child' + StringUtils.firstCase(name,'Upper')
            return (name == params.fragment  ? 'edit' : 'static') + template
        }
        model["child"] = individual
        model["roles"] = params.roles
        if (individual.id && !failedCreation) {
            model['roleOwners'] = homeFolderAdaptorService.roleOwners(individual.id)
            model['currentEcitizen'] = currentEcitizen
            model['currentRoleOwnerId'] = params.roleOwnerId ? Long.valueOf(params.roleOwnerId) : 0
        } else {
            model['adults'] = userSearchService.getAdults(currentEcitizen.homeFolder.id)
        }

        // Fred Fabre - Inexine Hack : Fiche de renseignement et de sécurité enfant
        File infoFile = localAuthorityRegistry.getLocalAuthorityResourceFile(
            LocalAuthorityResource.INFORMATION_MESSAGE_INFORMATION_SHEET_FO.id)

        if (infoFile != null && infoFile.exists() && !infoFile.text.isEmpty()) {
            model['informationSheetFo'] = infoFile.text
        }

        if (SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired()) {
            model['informationSheetRequired'] = 'required'
        }
        else {
            model['informationSheetRequired'] = ''
        }
        model['informationSheetDisplayed'] = SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed()
        model['informationSheetRequiredFieldsActived'] = SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()
        model['availableRules'] = localAuthorityRegistry.getLocalAuthorityRules('childInformationSheetRequest')

        def dietsList = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration()
        def dietsListKey = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().keySet()
        def dietsListLibelle = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration().values()
        model['dietsList'] = dietsList
        model['dietsListKey'] = dietsListKey
        model['dietsListLibelle'] = dietsListLibelle

        // if creation failed, set id to null to force switch in edition mode in views
        // id is set to null lastly because JPA does not like tampered ids
        if (failedCreation) individual.id = null

        return model
    }

    private linkChildrenByAdult(individual, children, params) {
        for(Child child : children) {
            if(params.get('roleType_'+child.id) != null) {
                link(individual, child, [RoleType.forString(params.get('roleType_'+child.id))])
            }
        }
    }

    private link(owner, individual, roleType) {
        userWorkflowService.link(owner, individual, roleType)
        def invalidFields = userService.validate(individual)
        flash['invalidFields'] = invalidFields
        if (!invalidFields.isEmpty())
            throw new CvqValidationException(invalidFields)
    }

    def unlink = {
        def child = userSearchService.getById(Long.valueOf(params.id))
        def owner = userSearchService.getById(Long.valueOf(params.roleOwnerId))

        userWorkflowService.unlink(owner, child)

        def invalidFields = userService.validate(child)

        if (!invalidFields.isEmpty()) {
            flash['invalidFields'] = invalidFields
            session.doRollback = true
        }

        if(params.page == 'adult') {
            redirect(url:createLink(action:'adult', params:['id':params.roleOwnerId, 'fragment':params.fragment]) + '#' + params.fragment)
        } else {
            redirect(url:createLink(action:'child', params:['id':params.id, 'fragment':params.fragment]) + '#' + params.fragment)
        }
    }

    private addAdult(individual) throws CvqValidationException {
        DataBindingUtils.initBind(individual, params)
        bind(individual)
        def invalidFields = userService.validate(individual)
        if (!invalidFields.isEmpty())
            throw new CvqValidationException(invalidFields)
        userWorkflowService.add(currentEcitizen.homeFolder, individual, false)
    }

    private addChild(individual) throws CvqValidationException {
        bind(individual)
        userWorkflowService.add(currentEcitizen.homeFolder, individual)
        params.roles.each {
            if (it.value instanceof GrailsParameterMap && it.value.owner != '' && it.value.type != '') {
                userWorkflowService.link(
                    userSearchService.getById(Long.valueOf(it.value.owner)),
                    individual, [RoleType.forString(it.value.type)])
            }
        }
        def invalidFields = userService.validate(individual)
        if (!invalidFields.isEmpty())
            throw new CvqValidationException(invalidFields)
    }

    private addChildInformationSheet(child, childInformationSheet) throws CvqValidationException {
        bind(childInformationSheet)
        userWorkflowService.addChildInformationSheet(child, childInformationSheet)
        def invalidFields = userService.validate(childInformationSheet,
            SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired())
        if (!invalidFields.isEmpty())
            throw new CvqValidationException(invalidFields)
    }
    // FIXME :
    private historize(fragment, individual, boolean validate = true) throws CvqValidationException {
        def fields, dto
        if (fragment == 'identity') {
            dto = individual instanceof Adult ? new Adult() : new Child()
            fields = individual instanceof Adult ?
                ["title", "familyStatus", "lastName", "maidenName", "firstName", "firstName2", "firstName3", "profession", "cfbn"] :
                ["born", "lastName", "firstName", "firstName2", "firstName3", "sex", "birthDate", "birthPostalCode", "birthCity", "birthCountry"]
        }

        // Fred Fabre - Inexine Hack : Fiche de renseignement et de sécurité enfant
        if (fragment == 'informationSheet') {
            dto = new ChildInformationSheet()
            fields = [
                "telephonePortable",
                "emailEnfant",
                "nomOrganismeAssurance",
                "numeroOrganismeAssurance",
                "nomMedecinTraitant",
                "telephoneMedecinTraitant",
                "allergie",
                "vaccinBcg",
                "vaccinDtPolio",
                "vaccinInjectionSerum",
                "vaccinRor",
                "vaccinTetracoqPentacoq",
                "vaccinAutre",
                "recommandationParent",
                "difficulteSante",
                "projetAccueilIndividualise",
                "autorisationDroitImage",
                "autorisationMaquillage",
                "autorisationTransporterVehiculeMunicipal",
                "autorisationTransporterTransportCommun",
                "autorisationHospitalisation",
                "autorisationRentrerSeul",
                "diets",
                "personneAutoriseNom1",
                "personneAutoriseNom2",
                "personneAutoriseNom3",
                "personneAutorisePrenom1",
                "personneAutorisePrenom2",
                "personneAutorisePrenom3",
                "personneAutoriseTelephone1",
                "personneAutoriseTelephone2",
                "personneAutoriseTelephone3"
            ]
        }
        if (fragment == 'contact') {
            dto = new Adult()
            fields = ["email", "homePhone", "mobilePhone", "officePhone", "smsPermission"]
        }
        if (fragment == 'connexion') {
            dto = new Adult()
            fields = ["question", "answer"]
        }
        if (fragment == 'address') {
            dto = new Address()
            fields = ["additionalDeliveryInformation", "additionalGeographicalInformation", "city", "cityInseeCode", "countryName", "placeNameOrService", "postalCode", "streetMatriculation", "streetName", "streetNumber", "streetRivoliCode"]
        }
        bind(dto)
        def bean
        if (fragment == 'address')
        {
            bean = individual.address
        }
        else if (fragment == 'informationSheet')
        {
            bean = individual.childInformationSheet
        }
        else
        {
            bean = individual
        }
        individualAdaptorService.historize(individual, bean, dto, fragment, fields, validate)
    }

    def editPassword = {
        def model = ["passwordMinLength" : authenticationService.passwordMinLength]
        if (request.get) {
            return model
        } else if (request.post) {
            if (params.cancel == null) {
                if (!checkPasswords()) {
                    return model;
                }
                try {
                    userWorkflowService.modifyPassword(currentEcitizen, params.oldPassword, params.newPassword)
                } catch (CvqBadPasswordException e) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.oldPassword.validationError")
                    return model
                }
                flash.successMessage = message("code":"homeFolder.adult.property.password.changeSuccess")
            }
            redirect(controller : "frontofficeHomeFolder")
        }
    }

    private def checkPasswords = {
        if (params.newPassword != params.newPasswordConfirmation) {
            flash.errorMessage = message("code":"homeFolder.adult.property.newPasswordConfirmation.validationError")
            return false
        } else if (params.newPassword == null || params.newPassword.length() < authenticationService.passwordMinLength) {
            flash.errorMessage = message("code":"homeFolder.adult.property.newPassword.validationError", "args":[authenticationService.passwordMinLength])
            return false
        }
        return true
    }

    def resetPassword = {
        if (request.get) {
            render(view : "answerLogin", model : [])
            return false
        } else if (request.post) {
            def adult = userSearchService.getByLogin(params.login)
            if (adult == null || adult.state.equals(UserState.ARCHIVED)) {
                flash.errorMessage = message("code":"account.error.invalidLogin")
                render(view : "answerLogin", model : [])
                return false
            }
            if (adult.answer != null && (adult.answer == params.answer)) {
                userWorkflowService.launchResetPasswordProcess(adult)
                flash.successMessage = message("code": "homeFolder.action.resetPwd.alert", "args": [adult.getEmail()])
                redirect(controller : "frontofficeHome", action : "login")
                return false
            } else {
                if (!params.comesFromLoginStep) {
                    flash.errorMessage = message("code":"account.error.invalidAnswer")
                }
                render(view : "answerQuestion", model : ["question" : adult.question, "login" : params.login])
                return false
            }
        }
    }

    def editQuestion = {
        def model = ["question" : currentEcitizen.question, "answer" : currentEcitizen.answer]
        if (request.get) {
            return model
        } else if (request.post) {
            if (params.cancel == null) {
                try {
                    authenticationService.authenticate(currentEcitizen.login, params.password)
                } catch (CvqAuthenticationFailedException e) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.oldPassword.validationError")
                    return model
                }

                if (!checkQuestion()) {
                    return model;
                }

                historize("connexion", currentEcitizen)
                flash.successMessage = message("code":"homeFolder.adult.property.question.changeSuccess")
            }
            redirect(controller : "frontofficeHomeFolder")
        }
    }

    private def checkQuestion = {
        if (params.question != message("code":"homeFolder.adult.question.q1")
            && params.question != message("code":"homeFolder.adult.question.q2")
            && params.question != message("code":"homeFolder.adult.question.q3")
            && params.question != message("code":"homeFolder.adult.question.q4")) {
             flash.errorMessage = message("code":"homeFolder.adult.property.question.validationError")
             return false
         }
         if (params.answer == null || params.answer.trim().isEmpty()) {
             flash.errorMessage = message("code":"homeFolder.adult.property.answer.validationError")
             return false
         }
         return true
    }

    /**
     * After password reset, user send its new password/Question/answer
     */
    def newPassword = {
        if (!params.login || !params.key) {
            render(view: '/system/error', model:["i18nKey":"homeFolder.action.resetPwd.badLink"])
            return
        }
        if (currentEcitizen) {
            render(view: '/system/error', model:["i18nKey": "homeFolder.action.resetPwd.alreadyLogged"])
            return
        }

        def linkIsValid = userService.checkResetPasswordLink(params.login, params.key)
        if (linkIsValid) {
            def model = ["passwordMinLength" : authenticationService.passwordMinLength]
            if (request.get) {
                return model
            } else if (request.post) {
                if (!checkPasswords() || !checkQuestion()) { // Error in form
                    return model
                } else {
                    def adult = userSearchService.getByLogin(params.login)
                    adult.homeFolder.isImportedAndNotInitialized = false
                    userWorkflowService.modifyConnection(adult, params.newPassword, params.question, params.answer)
                    securityService.setEcitizenSessionInformation(adult, session)
                    flash.successMessage = message("code": "homeFolder.action.resetPwd.success")
                    redirect(action: 'index')
                }
            }
        } else {
            render(view: '/system/error', model:["i18nKey":"homeFolder.action.resetPwd.error"])
            return
        }
    }

    def editImportedAccount = {
        def model = ["adult": currentEcitizen,
            "passwordMinLength" : authenticationService.passwordMinLength]
        if (request.get) {
            return model
        } else if (request.post) {
            if (params.cancel == null) {
                try {
                    authenticationService.authenticate(currentEcitizen.login, params.oldPassword)
                } catch (CvqAuthenticationFailedException e) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.oldPassword.validationError")
                    return model
                }

                if (params.email == null || params.email.trim().isEmpty() ||
                    ! new EmailCheck().isSatisfied(null, params.email, null, null)) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.email.validationError")
                    return model
                }

                if (params.question != message("code":"homeFolder.adult.question.q1")
                       && params.question != message("code":"homeFolder.adult.question.q2")
                       && params.question != message("code":"homeFolder.adult.question.q3")
                       && params.question != message("code":"homeFolder.adult.question.q4")
                   ) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.question.validationError")
                    return model
                }
                if (params.answer == null || params.answer.trim().isEmpty()) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.answer.validationError")
                    return model
                }

                if (params.newPassword != params.newPasswordConfirmation) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.newPasswordConfirmation.validationError")
                    return model
                } else if (params.newPassword == null || params.newPassword.length() < authenticationService.passwordMinLength) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.newPassword.validationError", "args":[authenticationService.passwordMinLength])
                    return model
                }
                try {
                    userWorkflowService.modifyPassword(currentEcitizen, params.oldPassword, params.newPassword)
                } catch (CvqBadPasswordException e) {
                    flash.errorMessage = message("code":"homeFolder.adult.property.oldPassword.validationError")
                    return model
                }

                userWorkflowService.setHomeFolderAsInitialized(currentEcitizen.homeFolder)

                historize("contact", currentEcitizen, false)
                historize("connexion", currentEcitizen, false)
            }
            redirect(controller : "frontofficeHomeFolder")
        }
    }
}
