import org.libredemat.business.authority.LocalAuthority
import org.libredemat.business.authority.LocalAuthorityResource
import org.libredemat.security.SecurityContext
import org.libredemat.service.authority.ILocalAuthorityRegistry
import grails.converters.JSON

class BackofficeLocalAuthorityController {
    
    ILocalAuthorityRegistry localAuthorityRegistry

    def localAuthorityResourceAdaptorService

    def defaultAction = 'aspect'

    def subMenuEntries = [
      'localAuthority.aspect',
      'localAuthority.pdf', 
      'localAuthority.information',
      'localAuthority.identity',
      'localAuthority.addressesReferential'
    ]

    def beforeInterceptor = { 
        session['currentMenu'] = 'localAuthority'
    }

    def aspect = {
        def localAuthorityResources = localAuthorityResourceAdaptorService.getLocalAuthorityResources()
        if (request.get) {
            if (params.id) {
                render(template : localAuthorityResources[params.id].template,
                       model : [
                           "subMenuEntries" : subMenuEntries,
                           "id" : params.id,
                           "rand" : UUID.randomUUID().toString(),
                           "hasCurrent" : localAuthorityRegistry.hasLocalAuthorityResource(
                               params.id, LocalAuthorityResource.Version.CURRENT),
                           "currentCode" : LocalAuthorityResource.Version.CURRENT.name(),
                           "hasOld" : localAuthorityRegistry.hasLocalAuthorityResource(
                               params.id, LocalAuthorityResource.Version.OLD),
                           "oldCode" : LocalAuthorityResource.Version.OLD.name()])
                return false
            }
        } else if (request.post) {
            localAuthorityRegistry.replaceLocalAuthorityResource(params.id, request.getFile("content").getBytes())
            response.contentType = 'text/html; charset=utf-8'
            render (new JSON([status:"success", success_msg:message(code:"message.updateDone")]).toString())
            return false
        }
    }

    def pdf = {
        def localAuthorityResources = localAuthorityResourceAdaptorService.getLocalAuthorityResources()
        if (request.get) {
            if (params.id) {
                render(template : localAuthorityResources[params.id].template,
                       model : [
                           "subMenuEntries" : subMenuEntries,
                           "id" : params.id,
                           "hasCurrent" : localAuthorityRegistry.hasLocalAuthorityResource(
                               params.id, LocalAuthorityResource.Version.CURRENT),
                           "currentCode" : LocalAuthorityResource.Version.CURRENT.name(),
                           "hasOld" : localAuthorityRegistry.hasLocalAuthorityResource(
                               params.id, LocalAuthorityResource.Version.OLD),
                           "oldCode" : LocalAuthorityResource.Version.OLD.name()])
                return false
            }
        } else if (request.post) {
            localAuthorityRegistry.replaceLocalAuthorityResource(params.id, request.getFile("content").getBytes())
            response.contentType = 'text/html; charset=utf-8'
            render (new JSON([status:"success", success_msg:message(code:"message.updateDone")]).toString())
            return false
        }
    }

    def information = {
        if (request.get) {
            def managedMessages = [
                LocalAuthorityResource.INFORMATION_MESSAGE_FO,
                LocalAuthorityResource.INFORMATION_MESSAGE_FO_UNAUTHENTICATED,
                LocalAuthorityResource.INFORMATION_MESSAGE_INFORMATION_SHEET_FO,
                LocalAuthorityResource.INFORMATION_MESSAGE_RESERVATION_FO
            ]
            def messages = []
            managedMessages.each {
                def file = localAuthorityRegistry.getLocalAuthorityResourceFile(it.id)
                if (file == null || !file.exists()) {
                    localAuthorityRegistry.saveLocalAuthorityResource(it.id, "".bytes)
                    file = localAuthorityRegistry.getLocalAuthorityResourceFile(it.id)
                }
                messages.add(["id" : it.id, "text" : file.text])
            }
            render(view : "information", model : [
                "subMenuEntries" : subMenuEntries, "messages" : messages
            ])
        } else if (request.post) {
            localAuthorityRegistry.saveLocalAuthorityResource(params.id,
                params.editor.toString().getBytes("UTF-8"));
            render message(code : "message.updateDone")
        }
    }

    def identity = {
        if (request.get) {
            def serverNames = SecurityContext.getCurrentSite().serverNames.join("\n")
            return ["subMenuEntries" : subMenuEntries,
                    "postalCode" : SecurityContext.getCurrentSite().postalCode,
                    "displayTitle" : SecurityContext.getCurrentSite().displayTitle,
                    "adminEmail" : SecurityContext.getCurrentSite().adminEmail,
                    "googleAnalyticsId" : SecurityContext.getCurrentSite().googleAnalyticsId,
                    "serverNames" : serverNames]
        } else if (request.post) {
            bind(SecurityContext.getCurrentSite())
            def serverNames = new TreeSet()
            params.serverNames.split(["\n"]).each{
                it = it.trim()
                if (!it.isEmpty()) {
                    serverNames.add(it)
                }
            }
            localAuthorityRegistry.setLocalAuthorityServerNames(serverNames)
            render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
            return false
        }
    }

    def addressesReferential = {
        if(request.get) {
            return [
                "subMenuEntries": subMenuEntries,
                "token": SecurityContext.getCurrentSite().token,
                "adressesReferentialUrl" : SecurityContext.getCurrentSite().adressesReferentialUrl]
        } else if (request.post) {
            bind(SecurityContext.getCurrentSite())
            localAuthorityRegistry.saveLocalAuthority(SecurityContext.getCurrentSite())
            render ([status:"success", success_msg:message(code:"localAuthority.message.addressesReferentialConfigSuccess")] as JSON)
            return false
        }
    }

    def rollback = {
        localAuthorityRegistry.rollbackLocalAuthorityResource(params.id)
        render ([status:"success", success_msg:message(code:"message.updateDone")] as JSON)
        return false
    }
}
