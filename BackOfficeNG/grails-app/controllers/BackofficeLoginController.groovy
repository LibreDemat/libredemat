import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.libredemat.authentication.IAuthenticationService
import org.libredemat.exception.CvqAuthenticationFailedException
import org.libredemat.exception.CvqDisabledAccountException
import org.libredemat.exception.CvqUnknownUserException
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.IAgentService
import org.libredemat.util.web.filter.CASFilter
import org.libredemat.exception.CvqObjectNotFoundException
import org.libredemat.dao.authority.IAgentDAO

class BackofficeLoginController {

    IAuthenticationService authenticationService
    IAgentService agentService
    IAgentDAO agentDAO;
    SecurityService securityService

    def defaultAction = "login"

    def beforeInterceptor = {
        session["currentMenu"] = "login"
    }

    def login = {
        def error = null
        if(request.get) {
            render(view : "login")
        }
        if(request.post) {
            try {
                def agent = authenticationService.authenticateAgent(params.login, params.password)
                return loginAndRedirectAgent(agent)
            }
            catch (CvqUnknownUserException e) {error = e}
            catch (CvqAuthenticationFailedException e) {error = e}
            catch (CvqDisabledAccountException e) {error = e}

            render(view: "login", model : ["enteredLogin": params.login, "error": error.message])
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
        		response.sendRedirect('/LibreDemat/cas.gsp')
        	else
        		redirect(url:CH.config.cas_logout_url)
        } else if (authenticationService.getAuthenticationMethod() == "oauth2") {
          redirect(controller:"OAuth2", action:"logout")
        }
        return false
    }

    def resetPassword = {
        if (SecurityContext.getCurrentAgent()) {
            return render(view: '/system/error', model: [i18nKey: 'agent.reset.error.alreadyLogged', context: 'bo'])
        }
        if (request.post) {
            try {
                def agent = agentService.getByLogin(params.login)
                def emailNotMatch = agent.email == null || agent.email.empty || agent.email != params.email
                if (emailNotMatch) {
                    flash.errorMessage = message(code: 'agent.reset.error.badEmail')
                } else {
                    agentService.sendResetPasswordEmail(agent);
                    flash.successMessage = message(code: 'agent.reset.message.confirmation', args:[agent.email])
                    if (authenticationService.getAuthenticationMethod() == "oauth2") {
                      return render(view: 'resetPasswordSuccess', model: [ message: flash.successMessage ])
                    } else {
                      return redirect(action: 'login')
                    }
                }
            } catch (CvqObjectNotFoundException ex) {
                flash.errorMessage = message(code: 'agent.reset.error.badEmail')
                log.error(ex);
            } catch (Exception ex) {
                flash.errorMessage = message(code: 'error.unexpected')
                log.error(ex);
            }
        }
        render(view: 'resetPasswordForm', model: [])
    }

    def activateAccount = {
        if (SecurityContext.getCurrentAgent()) {
            return render(view: '/system/error', model: [i18nKey: 'agent.reset.error.alreadyLogged', context: 'bo'])
        }
        render(view: 'activateAccount', model: [])
    }


    def newPassword = {
        if (!params.login || !params.key) {
            return render(view: '/system/error', model: [i18nKey: 'agent.reset.error.badLink', context: 'bo'])
        }
        if (SecurityContext.getCurrentAgent()) {
            return render(view: '/system/error', model: [i18nKey: 'agent.reset.error.alreadyLogged', context: 'bo'])
        }

        def linkIsValid = agentService.checkResetPasswordLink(params.login, params.key)
        if (linkIsValid) {
            def agent = agentDAO.findByLogin(params.login)
            def model = [
                passwordMinLength: authenticationService.passwordMinLength,
                agent: agent ]

            if (request.get) {
                return model
            } else if (request.post) {
                if (!checkPasswords()) {
                    return model
                } else {
                    agentService.assignNewPassword(agent, params.newPassword);
                    flash.successMessage = message(code: 'agent.reset.success.resetPwd')
                    return loginAndRedirectAgent(agent)
                }
            }
        } else {
            return render(view: '/system/error', model: [i18nKey: 'agent.reset.error.linkExpired', context: 'bo'])
        }
    }

    private def checkPasswords = {
        if (params.newPassword != params.newPasswordConfirmation) {
            flash.errorMessage = message(code: 'homeFolder.adult.property.newPasswordConfirmation.validationError')
            return false
        } else if (params.newPassword == null || params.newPassword.length() < authenticationService.passwordMinLength) {
            flash.errorMessage = message(code: 'homeFolder.adult.property.newPassword.validationError', args: [authenticationService.passwordMinLength])
            return false
        }
        return true
    }

    private loginAndRedirectAgent(agent) {
        SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
        SecurityContext.setCurrentAgent(agent)
        session.currentUser = agent.login
        session.currentCredentialBean = SecurityContext.currentCredentialBean
        session.isViewPayment = agent.isViewPayment

        def accessPoint = securityService.defaultAccessPoint(
            session.currentCredentialBean.hasSiteAdminRole() ? ContextType.ADMIN : ContextType.AGENT,
            SecurityContext.BACK_OFFICE_CONTEXT)

        redirect(controller: accessPoint['controller'], action: accessPoint['action'])
    }

}
