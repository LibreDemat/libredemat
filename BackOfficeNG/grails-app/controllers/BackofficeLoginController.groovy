import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import fr.cg95.cvq.authentication.IAuthenticationService
import fr.cg95.cvq.exception.CvqAuthenticationFailedException
import fr.cg95.cvq.exception.CvqDisabledAccountException
import fr.cg95.cvq.exception.CvqUnknownUserException
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.authority.IAgentService
import fr.cg95.cvq.util.web.filter.CASFilter

class BackofficeLoginController {

    IAuthenticationService authenticationService
    IAgentService agentService
    SecurityService securityService

    def defaultAction = "login"

    def beforeInterceptor = {
        session["currentMenu"] = "login"
    }

    def login = {
        def error = null, agent = null
        if(request.get) {
            render(view : "login")
        }
        if(request.post) {
            try { agent = authenticationService.authenticateAgent(params.login,params.password) }
            catch (CvqUnknownUserException e) {error = e}
            catch (CvqAuthenticationFailedException e) {error = e}
            catch (CvqDisabledAccountException e) {error = e}

            if(agent) {
                SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
                SecurityContext.setCurrentAgent(agent)
                session.currentUser = agent.login
                session.currentCredentialBean = SecurityContext.currentCredentialBean

                def accessPoint = securityService.defaultAccessPoint(session.currentCredentialBean.hasSiteAdminRole() ?
                        ContextType.ADMIN : ContextType.AGENT, SecurityContext.BACK_OFFICE_CONTEXT)
                redirect(controller: accessPoint['controller'], action:accessPoint['action'])
            } else {
                render(view : "login", model : ["enteredLogin" : params.login, "error" : error.message])
            }
        }
    }

    def logout = {
        session.currentUser = null
        session.currentCredentialBean = null
        // FIXME requests coupling
        session.hasAccessToRequestArchives = false

        if (authenticationService.getAuthenticationMethod() == "builtin") {
        	redirect(controller:"backofficeLogin")
        } else if (authenticationService.getAuthenticationMethod() == "cas") { 
            session.removeAttribute(CASFilter.CAS_FILTER_USER)
            session.removeAttribute(CASFilter.CAS_FILTER_RECEIPT)
        	if (CH.config.cas_mocking == "true")
        		response.sendRedirect('/CapDemat/cas.gsp')
        	else
        		redirect(url:CH.config.cas_logout_url)
        }
        return false
    }
}
