import org.libredemat.business.authority.LocalAuthorityResource
import org.libredemat.business.authority.LocalAuthorityResource.Type
import org.libredemat.business.users.MeansOfContactEnum
import org.libredemat.business.request.RequestActionType
import org.libredemat.business.request.RequestFormType
import org.libredemat.service.authority.IAgentService
import org.libredemat.service.request.IRequestLockService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.users.IUserNotificationService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.security.SecurityContext
import org.libredemat.util.UserUtils

import grails.converters.JSON

import org.springframework.web.context.request.RequestContextHolder
import org.xhtmlrenderer.pdf.ITextRenderer
import org.libredemat.business.users.Individual
import org.libredemat.business.users.Adult
import org.libredemat.business.users.UserAction
import org.libredemat.service.users.IUserWorkflowService


// mostly taken from RequestInstructionController,
// so still dependent on a request
// TODO request decoupling
class BackofficeContactController {

    IRequestLockService requestLockService
    IRequestSearchService requestSearchService
    IRequestTypeService requestTypeService
    IUserNotificationService userNotificationService
    IUserSearchService userSearchService
    IAgentService agentService
    IUserWorkflowService userWorkflowService

    def groovyPagesTemplateEngine
    def individualAdaptorService
    def localAuthorityRegistry
    def meansOfContactService
    def messageSource
    def requestActionService
	def translationService

    def beforeInterceptor = {
        if (params.requestId) requestLockService.tryToLock(Long.valueOf(params.requestId))
    }

    // directly taken from RequestInstructionController
    // TODO request decoupling
    def panel = {
        if (!request.get) return false
        def rqt
        if (params.requestId)
            rqt = requestSearchService.getById(Long.valueOf(params.requestId), false)
        def user
        if (rqt) {
            if (rqt.requesterId)
                user = userSearchService.getById(rqt.requesterId)
            else
                user = userSearchService.getHomeFolderResponsible(rqt.homeFolderId)
        } else {
            user = userSearchService.getHomeFolderResponsible(Long.valueOf(params.homeFolderId))
        }
        def meansOfContacts = []
        meansOfContactService.getAdultEnabledMeansOfContact(user).each {
            if (!MeansOfContactEnum.EMAIL.equals(it.type) || !user.email.equals(
                SecurityContext.getCurrentConfigurationBean().getDefaultEmail())) {
            meansOfContacts.add(
                LibredematUtils.adaptLibredematEnum(it.type, "meansOfContact"))
            }
        }
        meansOfContacts.each() {
            it.i18nKey = message(code:it.i18nKey)
        }
        def defaultMeansOfContact = rqt ?
            LibredematUtils.adaptLibredematEnum(rqt.meansOfContact?.type, "meansOfContact") :
            LibredematUtils.adaptLibredematEnum(MeansOfContactEnum.EMAIL, "meansOfContact")
        def requestForms = []
        if (rqt) {
            requestTypeService.getRequestTypeForms(rqt.requestType.id,
                RequestFormType.REQUEST_MAIL_TEMPLATE).each {
                String data = ""
                if (it.personalizedData) data = new String(it.personalizedData)
                requestForms.add([
                    "id": it.id,
                    "shortLabel": it.shortLabel,
                    "type": LibredematUtils.adaptLibredematEnum(it.type, "meansOfContact")
                ])
            }
            rqt = [
                "id" : rqt.id,
                "state": LibredematUtils.adaptLibredematEnum(rqt.state, "request.state")
            ]
        } else {
           // Hack Inexine
           // Courrier type pour sur les comptes donc pas de demande
           requestTypeService.getRequestFormsByRequestFormType(RequestFormType.HOMEFOLDER_MAIL_TEMPLATE).each {
           String data = ""
           if (it.personalizedData) data = new String(it.personalizedData)
           requestForms.add([
               "id": it.id,
               "shortLabel": it.shortLabel,
               "type": LibredematUtils.adaptLibredematEnum(it.type, "meansOfContact")
           ])
           }
       }
        return [
            "meansOfContacts": meansOfContacts,
            "defaultMeansOfContact" : defaultMeansOfContact,
            "requestForms": requestForms,
            "user": user,
            "rqt": rqt
        ]
    }

    def preview = {
        if (!request.post) return false
        if (params.previewFormat == "HTML") {
            response.contentType = "text/html; charset=utf-8"
            render prepareTemplate(params.requestId, params.id, params.requestFormId,
                params.templateMessage?.encodeAsHTML(), params.meansOfContact,
                params.previewFormat)
        } else if (params.previewFormat == "PDF") {
            def b = preparePdf(params.requestId, params.id,     params.requestFormId,
                params.templateMessage, params.meansOfContact)
            response.contentType = "application/pdf"
            response.setHeader("Content-disposition",
                "attachment; filename=letter.pdf")
            response.contentLength = b.length
            response.outputStream << b
            response.outputStream.flush()
        }
    }

    def view = {
        if (!request.get) return false
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition",
            "attachment; filename=letter.pdf")
        def data = requestActionService.getAction(Long.valueOf(params.requestId),
            Long.valueOf(params.requestActionId)).file
        response.contentLength = data.length
        response.outputStream << data
        response.outputStream.flush()
    }

    def contact = {
        if (!request.post) return false
        def notification

        def user
        if (params.id != null) {
            user = userSearchService.getById(params.long("id"))
        }

        if (params.requestId) {
        // FIXME : no indentation to avoid fake rewriting of this action from git's POV
        def requestId = Long.valueOf(params.requestId)
        def requestFormId
        def requestFormLabel = null
        if (params.requestFormId) {
            requestFormId = Long.valueOf(params.requestFormId)
            def requestForm = requestTypeService.getRequestFormById(requestFormId)
            requestFormLabel = requestForm.getLabel()
        }
        switch (MeansOfContactEnum.forString(params.meansOfContact)) {
            case MeansOfContactEnum.MAIL :
                requestActionService.addAction(
                    requestId,
                    RequestActionType.CONTACT_CITIZEN,
                    params.templateMessage, params.note,
                    params.requestFormId ?
                        preparePdf(params.requestId, params.id, params.requestFormId,
                            params.templateMessage, params.meansOfContact) : null, requestFormLabel)
                notification = [
                    status : "ok",
                    success_msg : message(code : "message.actionTraced")
                ]
                break;
            case MeansOfContactEnum.EMAIL :
                def pdf
                if (params.requestFormId)
                    pdf = preparePdf(requestId, params.id, requestFormId,
                        params.templateMessage, params.meansOfContact)
                requestActionService.addAction(
                    requestId,
                    RequestActionType.CONTACT_CITIZEN,
                    params.templateMessage, params.note, pdf, requestFormLabel)
                userNotificationService.notifyByEmail(
                    requestSearchService.getById(requestId, false).requestType
                        .category.primaryEmail,
                    params.email,
                    message(code:"mail.ecitizenContact.subject"),
                    params.requestFormId ?
                        message(code:"mail.ecitizenContact.body") :
                        params.templateMessage,
                    pdf,
                    params.requestFormId ?
                        "${requestTypeService.getRequestFormById(requestFormId).label}.pdf"
                        : null)
                notification = [
                    status : "ok",
                    success_msg : message(code : "message.emailSent")
                ]
                break;
            case MeansOfContactEnum.HOME_PHONE :
            case MeansOfContactEnum.OFFICE_PHONE :
            case MeansOfContactEnum.MOBILE_PHONE :
                requestActionService.addAction(
                    requestId,
                    RequestActionType.CONTACT_CITIZEN,
                    null, params.note, null,null)
                notification = [
                    status : "ok",
                    success_msg : message(code : "message.actionTraced")
                ]
                break;
            case MeansOfContactEnum.SMS :
                requestActionService.addAction(
                    requestId,
                    RequestActionType.CONTACT_CITIZEN,
                    params.smsMessage, params.note, null,null)
                userNotificationService.notifyBySms(params.mobilePhone, params.smsMessage)
                notification = [
                    status : "ok",
                    success_msg : message(code : "message.smsSent")
                ]
                break;
            case MeansOfContactEnum.LOCAL_AUTHORITY_OFFICE :
                requestActionService.addAction(
                    requestId,
                    RequestActionType.CONTACT_CITIZEN,
                    params.templateMessage, params.note,
                    params.requestFormId ?
                        preparePdf(params.requestId, params.id, params.requestFormId,
                            params.templateMessage, params.meansOfContact) : null, requestFormLabel)
                notification = [
                    status : "ok",
                    success_msg : message(code : "message.actionTraced")
                ]
                break;
        }
        } 
        // Contact du citoyen à partir du compte en choisissant 1 courrier type
        else if (params.requestFormId) {

            def requestId = null
            def requestFormId
            def requestFormLabel = null
            if (params.requestFormId) {
            requestFormId = Long.valueOf(params.requestFormId)
            def requestForm = requestTypeService.getRequestFormById(requestFormId)
            requestFormLabel = requestForm.getLabel()
            }
            switch (MeansOfContactEnum.forString(params.meansOfContact)) {
            case MeansOfContactEnum.MAIL :
                userWorkflowService.addHomeFolderAction(user, params.note, UserAction.Type.CONTACT,
                params.templateMessage, MeansOfContactEnum.MAIL, params.email)

                notification = [
                status : "ok",
                success_msg : message(code : "message.actionTraced")
                ]
                break;
            case MeansOfContactEnum.EMAIL :
                def pdf
                if (params.requestFormId) {
                pdf = preparePdf(requestId, params.id, requestFormId, params.templateMessage, params.meansOfContact)
                }
                userNotificationService.notifyByEmail(
                null,
                params.email,
                message(code:"mail.ecitizenContact.subject"),
                params.requestFormId ?
                    message(code:"mail.ecitizenContact.body") :
                    params.templateMessage,
                pdf,
                params.requestFormId ?
                    "${requestTypeService.getRequestFormById(requestFormId).label}.pdf"
                    : null)

                userWorkflowService.addHomeFolderAction(user, params.note, UserAction.Type.CONTACT,
                params.templateMessage, MeansOfContactEnum.EMAIL, params.email, pdf)

                notification = [
                status : "ok",
                success_msg : message(code : "message.emailSent")
                ]
                break;
            case MeansOfContactEnum.HOME_PHONE :
            case MeansOfContactEnum.OFFICE_PHONE :
            case MeansOfContactEnum.MOBILE_PHONE :

                userWorkflowService.addHomeFolderAction(user, params.note, UserAction.Type.CONTACT,
                params.templateMessage, MeansOfContactEnum.MOBILE_PHONE, params.email)

                notification = [
                status : "ok",
                success_msg : message(code : "message.actionTraced")
                ]
                break;
            case MeansOfContactEnum.SMS :
                userWorkflowService.addHomeFolderAction(user, params.note, UserAction.Type.CONTACT,
                params.templateMessage, MeansOfContactEnum.SMS, params.email)

                notification = [
                status : "ok",
                success_msg : message(code : "message.smsSent")
                ]
                break;
            case MeansOfContactEnum.LOCAL_AUTHORITY_OFFICE :
                userWorkflowService.addHomeFolderAction(user, params.note, UserAction.Type.CONTACT,
                params.templateMessage, MeansOfContactEnum.LOCAL_AUTHORITY_OFFICE, params.email)

                notification = [
                status : "ok",
                success_msg : message(code : "message.actionTraced")
                ]
                break;
            }
        } else {
            def moc = MeansOfContactEnum.forString(params.meansOfContact)
            switch (moc) {
                case MeansOfContactEnum.LOCAL_AUTHORITY_OFFICE :
                case MeansOfContactEnum.MAIL :
                    userNotificationService.contact(user, moc, null, params.templateMessage, params.note)
                    notification = [
                        status : "ok",
                        success_msg : message(code : "message.actionTraced")
                    ]
                    break
                case MeansOfContactEnum.EMAIL :
                    userNotificationService.contact(user, moc, params.email, params.templateMessage, params.note)
                    notification = [
                        status : "ok",
                        success_msg : message(code : "message.emailSent")
                    ]
                    break
                case MeansOfContactEnum.HOME_PHONE :
                case MeansOfContactEnum.OFFICE_PHONE :
                case MeansOfContactEnum.MOBILE_PHONE :
                    userNotificationService.contact(user, moc, null, null, params.note)
                    notification = [
                        status : "ok",
                        success_msg : message(code : "message.actionTraced")
                    ]
                    break
                case MeansOfContactEnum.SMS :
                    userNotificationService.contact(user, moc, params.mobilePhone, params.smsMessage, params.note)
                    notification = [
                        status : "ok",
                        success_msg : message(code : "message.smsSent")
                    ]
                    break
            }
        }
        render(notification as JSON)
    }

    private preparePdf(requestId, homeFolderResponsibleId, requestFormId, templateMessage, meansOfContact) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ITextRenderer renderer = new ITextRenderer()
        renderer.setDocumentFromString(prepareTemplate(
            requestId, homeFolderResponsibleId, requestFormId,
            templateMessage?.encodeAsXML().replaceAll(/\n/, "<br />"),
            meansOfContact, "PDF"))
        renderer.layout()
        renderer.createPDF(baos)
        return baos.toByteArray()
    }

    // directly taken from RequestInstructionController
    // TODO request decoupling
    //      and use RequestNotificationService.evaluate() for templating.
    private prepareTemplate(requestId, homeFolderResponsibleId, formId,observations,meansOfContact,type) {

        def requestAttributes = RequestContextHolder.currentRequestAttributes()
        def form = requestTypeService.getRequestFormById(Long.valueOf(formId))

        def requester = null
        def rqt = null
        def subjectObject = null
        def subject = null

        // Demandeur
        // FIXME RDJ - if no requester use homefolder responsible
        if (requestId != null) {
            rqt = requestSearchService.getById(Long.valueOf(requestId), false)
            if (rqt.requesterId != null) {
                requester = userSearchService.getById(rqt.requesterId)
            }
            else {
                requester = userSearchService.getHomeFolderResponsible(rqt.homeFolderId)
            }
            if (rqt.subjectId != null) {
                subjectObject = userSearchService.getById(rqt.subjectId)
                subject = individualAdaptorService.getIndividualDescription(subjectObject)
            }
        }
        else {
            requester = userSearchService.getAdultById(Long.valueOf(homeFolderResponsibleId))
        }

        def address = requester.getHomeFolder().getAddress()

        def forms = []
        forms.add(form)

        def templateFile = localAuthorityRegistry
            .getLocalAuthorityResourceFile(Type.MAIL_TEMPLATES,
                form.getTemplateName(), false)
        if (templateFile.exists()) {

            // FIXME BOR : is there a better way to do this ?
            def logoLink = ""
            def footerLink = ""
            if (type == "PDF") {
                try {
                    logoLink =
                        localAuthorityRegistry.getLocalAuthorityResourceFile(
                            LocalAuthorityResource.LOGO_PDF.id)
                            .absolutePath
                } catch (Exception e) {
                    log.error("Exception while looking for JPEG logo : ",
                 e.getMessage())
                }
                try {
                    footerLink =
                        localAuthorityRegistry.getLocalAuthorityResourceFile(
                            LocalAuthorityResource.FOOTER_PDF.getId())
                            .absolutePath
                } catch (Exception e) {
                    log.error("Exception while looking for JPEG footer : ",
                 e.getMessage())
                }
            }

            def template = groovyPagesTemplateEngine.createTemplate(templateFile);
            def out = new StringWriter();
            def originalOut = requestAttributes.getOut()
            requestAttributes.setOut(out)
            template.make([
                "forms" : forms, "type" : type, "logoLink" : logoLink,
                "footerLink" : footerLink
            ]).writeTo(out);
            requestAttributes.setOut(originalOut)

            String content = out.toString().replace('#{','${')

            String tpLabel = ''
            String lastAgentName = ''
            String lastAgentEmail = ''
            String requestValidationDate = ''
            String requestDate = ''
            String requestCategory = ''
            String requestCategoryEmail = ''

            if (rqt != null) {
                tpLabel = type == "HTML" ?
                    translationService.translateRequestTypeDescription(rqt?.requestType.label).toLowerCase().encodeAsHTML() :
                    translationService.translateRequestTypeDescription(rqt?.requestType.label).toLowerCase()

                lastAgentName = rqt ? UserUtils.getDisplayName(rqt?.lastInterveningUserId) : ''
                lastAgentEmail = rqt ? agentService.exists(rqt?.lastInterveningUserId) ? agentService.getById(rqt?.lastInterveningUserId).email : '' : ''
                requestValidationDate = rqt?.validationDate ? DateUtils.dateToFullString(rqt?.validationDate) : ''
                requestDate = DateUtils.dateToFullString(rqt?.creationDate)
                requestCategory = rqt?.requestType.category.name
                requestCategoryEmail = rqt?.requestType.category.primaryEmail
            }

            String title = ''
            if (subject != null) {
                title = subject?.firstName == "" ? "" :
                    messageSource.getMessage("homeFolder.adult.title.${subject?.title.toString().toLowerCase()}",
                    null, SecurityContext.currentLocale)
            }
            String meansOfContactMessage = ''
            if (meansOfContact != null) {
                meansOfContactMessage = message(code : "meansOfContact." + StringUtils.pascalToCamelCase(meansOfContact))
            }

            def model = [
                "DATE" : DateUtils.dateToFullString(new Date()),
                "LAST_AGENT_NAME" : lastAgentName,
                "LAST_AGENT_EMAIL" : lastAgentEmail,
                "MOC" : meansOfContactMessage,
                "RQ_ID" : rqt?.id,
                "RQ_CAT" : requestCategory,
                "RQ_CAT_EMAIL" : requestCategoryEmail,
                "RQ_TP_LABEL" : tpLabel,
                "RQ_CDATE" : requestDate,
                "RQ_DVAL" : requestValidationDate,
                "RQ_OBSERV" : observations,
                "HF_ID" : requester.homeFolder.id,
                "RR_FNAME" : requester.firstName,
                "RR_LNAME" : requester.lastName,
                "RR_TITLE" :
                    messageSource.getMessage(
                        "homeFolder.adult.title.${requester.title.toString().toLowerCase()}",
                        null, SecurityContext.currentLocale),
                "RR_LOGIN" : requester.login,
                "RR_QUESTION" : requester.question,
                "RR_ANSWER" : requester.answer,
                "SU_FNAME" : subject?.firstName,
                "SU_LNAME" : subject?.lastName,
                "SU_TITLE" : title,
                "HF_ADDRESS_ADI" : address.additionalDeliveryInformation,
                "HF_ADDRESS_AGI" : address.additionalGeographicalInformation,
                "HF_ADDRESS_SNAME" : address.streetName,
                "HF_ADDRESS_SNUM" : address.streetNumber,
                "HF_ADDRESS_PNS" : address.placeNameOrService,
                "HF_ADDRESS_ZIP" : address.postalCode,
                "HF_ADDRESS_TOWN" : address.city,
                "HF_ADDRESS_CN" : address.countryName,
                "LA_NAME" : localAuthorityRegistry.getLocalAuthorityByName(SecurityContext.getCurrentSite().getName()).getDisplayTitle()
            ]
            model.each { k, v -> if (v == null) model[k] = "" }

            template = groovyPagesTemplateEngine.createTemplate(content, "tmp")
            out = new StringWriter();
            originalOut = requestAttributes.getOut()
            requestAttributes.setOut(out)
            template.make(model).writeTo(out);
            requestAttributes.setOut(originalOut)
            return out.toString()
        } else {
            return ""
        }
    }
}
