import org.libredemat.business.authority.SiteProfile;
import org.libredemat.service.authority.IAgentService

import grails.converters.JSON

class BackofficeAgentController {

    IAgentService agentService

    def defaultAction = "list"

    def beforeInterceptor = { session["currentMenu"] = "requestAdmin" }

    def subMenuEntries = [
      "agent.list",
      "homeFolder.importHomeFolders"
    ]

    def list = {
        def agents = agentService.getAll()
        // hack to load sitesRoles
        agents.each { it.sitesRoles.each {} }        
        
        return ["agents" : agents, "subMenuEntries" : subMenuEntries]
    }

    def edit = {
        if (params.agentId != null && params.agentId != "")
           params.id = params.agentId

        def agent = agentService.getById(Long.valueOf(params.id))
        if (request.get) {
            def agents = agentService.getAll()
            // hack to load sitesRoles
            agent.sitesRoles.each {}
            return [agents:agents, agent:agent,
                    siteProfiles : SiteProfile.allSiteProfiles, scope:"Agent"]
        } else if (request.post) {
            agentService.setProfiles(agent,
                [SiteProfile.forString(params.siteProfile)])
            render([status:"ok", success_msg:message(code:"message.updateDone")] as JSON)
        }
    }
}
