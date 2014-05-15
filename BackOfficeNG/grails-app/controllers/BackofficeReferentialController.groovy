import org.libredemat.authentication.IAuthenticationService

import org.libredemat.business.authority.Agent
import org.libredemat.business.authority.RecreationCenter
import org.libredemat.business.authority.School
import org.libredemat.business.authority.SiteProfile

import org.libredemat.service.authority.IAgentService
import org.libredemat.service.authority.IRecreationCenterService
import org.libredemat.service.authority.ISchoolService

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

    def static subMenuEntries = [
            "referential.agents",
            "referential.schools",
            "referential.recreationCenters",
            "documentType.list"
        ]

    def afterInterceptor = { model ->
        model["subMenuEntries"] = BackofficeReferentialController.subMenuEntries
    }
	
    def index = {
    	redirect(action:'agents')
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
