import fr.cg95.cvq.authentication.IAuthenticationService

import fr.cg95.cvq.business.authority.Agent
import fr.cg95.cvq.business.authority.RecreationCenter
import fr.cg95.cvq.business.authority.School
import fr.cg95.cvq.business.authority.SiteProfile

import fr.cg95.cvq.service.authority.IAgentService
import fr.cg95.cvq.service.authority.IRecreationCenterService
import fr.cg95.cvq.service.authority.ISchoolService

import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class BackofficeReferentialController {

    IAgentService agentService
    IAuthenticationService authenticationService
    IRecreationCenterService recreationCenterService
    ISchoolService schoolService

    def beforeInterceptor = {
        session["currentMenu"] = "referential"
    }

    def afterInterceptor = { model ->
        model["subMenuEntries"] = []
        if (authenticationService.getAuthenticationMethod() == "builtin")
            model["subMenuEntries"] += "referential.agents"
        model["subMenuEntries"] += [
            "referential.schools",
            "referential.recreationCenters"
        ]
    }
	
    def index = {
    	if (authenticationService.getAuthenticationMethod() == "builtin")
            redirect(action:'agents')
        else
            redirect(action:'schools')
	}
    
    def agents = {
        if (request.xhr)
            render(template : "agents",
                model : ["agents" : agentService.getAll()])
        else
            render(view : "index", model : ["type" : "agent"])
    }

    def agent = {
        def agent
        if (params.id) {
            agent = agentService.getById(Long.valueOf(params.id))
        }
        if (request.get) {
            render(template : "agent",
                   model : [
                       "agent" : agent,
                       "passwordMinLength" : agentService.passwordMinLength,
                       "password" : params.password,
                       "profile" : params.profile,
                       "isAgent" : agentService.isAgent(agent),
                       "isAdmin" : agentService.isAdmin(agent)
                   ]
            )
        } else if (request.post) {
            def codeString
            if (params.id) {
                def selfLoginChange = false
                if (params.profile) {
                    def currentAgent = agentService.getByLogin(session.currentUser)
                    selfLoginChange = (Long.valueOf(params.id) == currentAgent.id && currentAgent.login != params.login)
                }
                bind(agent)
                if (selfLoginChange) {
                    session.currentUser = params.login
                }
                agentService.modify(agent)
                codeString = "message.updateDone"
            } else {
                agent = new Agent()
                bind(agent)
                agentService.create(agent)
                codeString = "message.creationDone"
            }
            if (params.profile) {
                agentService.setProfiles(agent, [SiteProfile.forString(params.siteProfile)])
            }
            if (params.password) {
                agent.password = authenticationService.encryptPassword(params.password);
            }
            render([status:"ok", success_msg:message(code:codeString)] as JSON)
        } else if (request.getMethod().toLowerCase() == "delete") {
            agentService.delete(agent.login)
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }

    def recreationCenters = {
        if (request.xhr)
            render(template : "recreationCenters",
                model : ["recreationCenters" : recreationCenterService.getAll()])
        else
            render(view : "index", model : ["type" : "recreationCenter"])
    }

    def recreationCenter = {
        def recreationCenter
        if (params.id) {
            recreationCenter = recreationCenterService.getById(Long.valueOf(params.id))
        }
        if (request.get) {
            render(template : "recreationCenter", model : ["recreationCenter" : recreationCenter])
        } else if (request.post) {
            def codeString
            if (params.id) {
                bind(recreationCenter)
                recreationCenterService.modify(recreationCenter)
                codeString = "message.updateDone"
            } else {
                recreationCenter = new RecreationCenter()
                bind(recreationCenter)
                recreationCenterService.create(recreationCenter)
                codeString = "message.creationDone"
            }
            render([status:"ok", success_msg:message(code:codeString)] as JSON)
        } else if (request.getMethod().toLowerCase() == "delete") {
            recreationCenterService.delete(recreationCenter)
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }

    def schools = {
        if (request.xhr)
            render(template : "schools",
                model : ["schools" : schoolService.getAll()])
        else
            render(view : "index", model : ["type" : "school"])
    }

    def school = {
        def school
        if (params.id) {
            school = schoolService.getById(Long.valueOf(params.id))
        }
        if (request.get) {
            render(template : "school", model : ["school" : school])
        } else if (request.post) {
            def codeString
            if (params.id) {
                bind(school)
                schoolService.modify(school)
                codeString = "message.updateDone"
            } else {
                school = new School()
                bind(school)
                schoolService.create(school)
                codeString = "message.creationDone"
            }
            render([status:"ok", success_msg:message(code:codeString)] as JSON)
        } else if (request.getMethod().toLowerCase() == "delete") {
            schoolService.delete(school)
            render([status:"ok", success_msg:message(code:"message.deleteDone")] as JSON)
        }
    }
}
