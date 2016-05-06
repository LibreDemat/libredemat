import org.libredemat.security.SecurityContext;
import org.libredemat.service.users.IUserSecurityService
import org.libredemat.business.users.UserSecurityProfile
import org.libredemat.business.users.UserSecurityRule

import grails.converters.JSON

class BackofficeUserSecurityController {

    IUserSecurityService userSecurityService

    def beforeInterceptor = {
        session["currentMenu"] = "users"
    }

    def defaultAction = "index"
    def subMenuEntries = ["userAdmin.index", "userSecurity.index", "homeFolder.meansOfContact", "homeFolder.importHomeFolders", "homeFolder.childInformationSheetDateInitialisation", "homeFolder.synchronisation"]

    def index = {
        return [
            "subMenuEntries" : subMenuEntries,
            "view": "allowed",
            "agents": userSecurityService.listAgents(true),
            "agentsAccess" : userSecurityService.listAgents(false).toArray().findAll{ agent ->
                agent.getIsSanitaire() == true
            },
            "mapRules" : userSecurityService.mapRules(),
            "isInformationSheetDisplayed" : SecurityContext.getCurrentConfigurationBean().isInformationSheetDisplayed()
        ]
    }

    def filterAccessAgents = {
        def agents = []

        if ((request.post && params.scope == null) || params.scope == 'all') {
            agents = userSecurityService.listAgents(false)
        } else if (params.scope == 'bounded') {
            agents = userSecurityService.listAgents(false).toArray().findAll{ agent ->
                agent.getIsSanitaire() == true
            }
        }

        render( template:"agentsAccess",
                model:[ agents: agents, scope:params.scope ])
    }

    def unassociateAgent = {
        def agent = userSecurityService.changePermissionAccessAgent(Long.valueOf(params.agentId), false)
         render ([ agent:agent.getIsSanitaire(),
            status:'success', success_msg:message(code:"message.updateDone")] as JSON)
    }

    def associateAgent = {
        def agent = userSecurityService.changePermissionAccessAgent(Long.valueOf(params.agentId), true)
         render ([ agent:agent.getIsSanitaire(),
            status:'success', success_msg:message(code:"message.updateDone")] as JSON)
    }

    def agents = {
        render( template: "agents", model: [
            "view": params.view, 
            "agents": userSecurityService.listAgents(params.view == "allowed"),
            "mapRules" : userSecurityService.mapRules()
        ])
    }

    def agent = {
        def rule = userSecurityService.getRule(Long.valueOf(params.id))
        render(template: "agent", model: [
            "securityRule": rule != null ? rule : new UserSecurityRule(Long.valueOf(params.id), null),
            "profiles": UserSecurityProfile.writer
         ])
    }

    def allow = {
        userSecurityService.allow(Long.valueOf(params.id), UserSecurityProfile.forString(params.profile))
        render ([status:"success", "message":message(code:"message.updateDone")] as JSON)
    }

    def disallow = {
        userSecurityService.disallow(Long.valueOf(params.id))
        render ([status:"success", "message":message(code:"message.updateDone")] as JSON)
    }

}
