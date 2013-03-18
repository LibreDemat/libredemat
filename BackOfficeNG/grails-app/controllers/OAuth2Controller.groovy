import fr.cg95.cvq.exception.CvqAuthenticationFailedException
import fr.cg95.cvq.exception.CvqDisabledAccountException
import fr.cg95.cvq.exception.CvqUnknownUserException
import fr.cg95.cvq.oauth2.IOAuth2Service
import fr.cg95.cvq.oauth2.OAuth2Exception;
import fr.cg95.cvq.oauth2.model.Token
import fr.cg95.cvq.security.SecurityContext

import grails.converters.JSON

class OAuth2Controller {

    def securityService
    def localAuthorityRegistry

    IOAuth2Service oauth2Service

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
        if (params.code != null) {
            Token t = oauth2Service.authorizationCodeGrant(params.code)
            if (t != null) {
                try {
                  if(t.getScope() == "agent") {
                    securityService.setAgentSessionInformation(
                        oauth2Service.authenticateAgent(t.getAccessToken()), session)

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

        def home = request.getRequestURL().toString() \
        - request.getRequestURI() \
        + createLink(controller:'frontofficeHome').toString()
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

}
