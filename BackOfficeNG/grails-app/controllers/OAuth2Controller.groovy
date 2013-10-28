import org.libredemat.business.authority.LocalAuthorityResource.Type;
import org.libredemat.exception.CvqAuthenticationFailedException
import org.libredemat.exception.CvqDisabledAccountException
import org.libredemat.exception.CvqUnknownUserException
import org.libredemat.oauth2.IOAuth2Service
import org.libredemat.oauth2.OAuth2Exception;
import org.libredemat.oauth2.model.Token
import org.libredemat.security.SecurityContext
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.util.translation.ITranslationService;

import grails.converters.JSON

class OAuth2Controller {

    def securityService
    def localAuthorityRegistry

    IOAuth2Service oauth2Service
    ITranslationService translationService

    def askLogin = {
        if (session?.currentEcitizenId == null) {
            def callback = params.callback ?: createLink(controller:'frontofficeHome').toString()
            def url = oauth2Service.authorizationRequestUri(callback)
            if (url != null) {
                redirect(url:url)
                return false
            }
        }
        // TODO add flash error
        redirect(controller:'frontofficeHome')
        return false
    }

    def login = {
        def error = ''
        if (params.error != null) {
            log.error(params.error)
            log.error(params.error_description)
            error = "account.error.authenticationFailed"
        } else if (params.code != null) {
            Token t = oauth2Service.authorizationCodeGrant(params.code)
            if (t != null) {
                try {
                  if(t.getScope() == "agent") {
                    def agent = oauth2Service.authenticateAgent(t.getAccessToken())
                    if (agent == null) {
                        session.redirectToBo = true
                        redirect(action : 'logout')
                        return false;
                    }
                    securityService.setAgentSessionInformation(agent, session)
                    params.state ? redirect(uri:(params.state - request.contextPath)) : redirect(controller:"backofficeHome")
                  } else {
                    securityService.setEcitizenSessionInformation(
                        oauth2Service.authenticate(t.getAccessToken()), session)
                    params.state ? redirect(uri:(params.state - request.contextPath)) : redirect(controller:"frontofficeHome")
                  }
                  return false
                } catch (CvqUnknownUserException e) {
                    error = "account.error.authenticationFailed"
                } catch (CvqAuthenticationFailedException e) {
                    error = "account.error.authenticationFailed"
                } catch (CvqDisabledAccountException e) {
                    error = "account.error.disabledAccount"
                }
            }
        }
        // TODO add flash error
        redirect(controller:'frontofficeHome')
        return false
    }


    /**
     * Logout in chain from CapDémat, from Swarm and from Booker (if needed).
     */
    def logout = {
        def lacbname = SecurityContext.getCurrentConfigurationBean().getName()
        def lacb = localAuthorityRegistry.getLocalAuthorityBeanByName(lacbname)

        def home = request.getRequestURL().toString() - request.getRequestURI() + createLink(
            controller: (session.redirectToBo ? 'backofficeHome' : 'frontofficeHome')).toString()

        def callback
        if (lacb.getAdditionalTabs().contains('Planning')) {
            callback = URLEncoder.encode( lacb.getExternalApplicationProperty('booker.logouturl') + '?callback=' + home
                    , 'UTF-8'
                    )
        } else {
            callback = URLEncoder.encode(home, 'UTF-8')
        }

        securityService.logout(session)
        try {
            redirect(url:oauth2Service.getOAuth2Configuration().getLogoutUri() + "?callback=" + callback)
        } catch (OAuth2Exception e) {
            log.error(e.errorCode)
            redirect(home)
        }
        return false
    }

    def invalidScope = {
        log.info("Insufficient scope : the request requires higher privileges than provided by the access token.");
        response.setStatus(403);
        render([error : "insufficient_scope",
            error_description : "The request requires higher privileges than provided by the access token."] as JSON);
        return false;
    }

    def css = {

        def scope = params.scope

        def agentScope = oauth2Service.getOAuth2Configuration().getAgentScope()
        if (scope != null && scope.contains(agentScope)) {
            def cssNameBack = oauth2Service.getOAuth2Configuration().getCssNameBack()
            def contentFile = localAuthorityRegistry.getFileContent(localAuthorityRegistry.getLocalAuthorityResourceFile(Type.CSS, cssNameBack, true))
            render(text: contentFile, contentType:"text/css", status: 200)
            return false;
        }

        def cssNameFront = oauth2Service.getOAuth2Configuration().getCssNameFront()
        def contentFile = localAuthorityRegistry.getFileContent(localAuthorityRegistry.getLocalAuthorityResourceFile(Type.CSS, cssNameFront, true))
        render(text: contentFile, contentType:"text/css", status: 200)
        return false;
    }

    def i18n = {
        def scope = params.scope

        def agentScope = oauth2Service.getOAuth2Configuration().getAgentScope()
        if (scope != null && scope.contains(agentScope) ) {
            def i18nMap = ["auth.connexion" : translationService.translate("back.auth.connexion"),
                "auth.connexionTo" : translationService.translate("back.auth.connexionTo"),
                "auth.identifiant" : translationService.translate("back.auth.identifiant"),
                "auth.password" : translationService.translate("back.auth.password"),
                "auth.badPassword" : translationService.translate("back.auth.badPassword"),
                "auth.signup" : translationService.translate("back.auth.signup"),
                "auth.signup.url" : translationService.translate("back.auth.signup.url"),
                "auth.forgotpassword" : translationService.translate("back.auth.forgotpassword"),
                "auth.forgotpassword.url" : translationService.translate("back.auth.forgotpassword.url"),
                "auth.appName" : translationService.translate("back.auth.appName")
                ]
            render( i18nMap as JSON)
            return false;
        }
        def i18nMap = ["auth.connexion" : translationService.translate("front.auth.connexion"),
            "auth.connexionTo" : translationService.translate("front.auth.connexionTo"),
            "auth.identifiant" : translationService.translate("front.auth.identifiant"),
            "auth.password" : translationService.translate("front.auth.password"),
            "auth.badPassword" : translationService.translate("front.auth.badPassword"),
            "auth.signup" : translationService.translate("front.auth.signup"),
            "auth.signup.url" : translationService.translate("front.auth.signup.url"),
            "auth.forgotpassword" : translationService.translate("front.auth.forgotpassword"),
            "auth.forgotpassword.url" : translationService.translate("front.auth.forgotpassword.url"),
            "auth.appName" : translationService.translate("front.auth.appName")
            ]
        render( i18nMap as JSON)
        return false;

    }

}
