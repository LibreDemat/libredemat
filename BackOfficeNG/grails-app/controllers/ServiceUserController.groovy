import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.users.IUserSearchService
import fr.cg95.cvq.authentication.IAuthenticationService
import fr.cg95.cvq.exception.CvqAuthenticationFailedException
import fr.cg95.cvq.exception.CvqDisabledAccountException
import fr.cg95.cvq.exception.CvqUnknownUserException
import grails.converters.JSON
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.oauth2.IOAuth2Service
import fr.cg95.cvq.oauth2.model.AccessToken
import fr.cg95.cvq.oauth2.OAuth2Exception
import fr.cg95.cvq.service.authority.IAgentService;
import fr.cg95.cvq.service.request.ICategoryService

import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang.StringUtils

class ServiceUserController {
    IUserSearchService userSearchService
    IAuthenticationService authenticationService
    ILocalAuthorityRegistry localAuthorityRegistry
    IOAuth2Service oauth2Service
    IAgentService agentService
    ICategoryService categoryService

    def beforeInterceptor = [action:this.&authenticate, only:['auth','authAgent']]
    def authenticate() {
        def authorization = request.getHeader('Authorization')
        if (!authorization) {
            render(text: 'Authorization required', status: 403)
            return false
        }
        def method = authorization.subSequence(0, 6)
        if (!(method.equals('Basic '))){
            render(text: '"' + method + '" unavailable. Use "Basic" instead.', status: 403)
            return false
        }
        def credentials = StringUtils.split(
            new String(Base64.decodeBase64(authorization.substring(6).bytes),
            "UTF-8"),
            ":")
        if (credentials?.length < 2) {
            render(text: '"login" and "password" required', status: 403)
            return false
        }
        def password = localAuthorityRegistry
            .getLocalAuthorityBeanByName(SecurityContext.getCurrentConfigurationBean().getName())
            .getAuthorizations()
            .get(credentials[0])
        if (!password && !(password.equals(credentials[1]))){
            render(text: '"login"/"password" don\'t match', status: 403)
            return false
        }
        return true
    }
    /**
     * Send back a JSON object {"connected": false}
     *                      or {"connected": true, "firstname": "Jean", "lastname": "DUPONT"}
     *
     * By default, the JSON object is wrapped in JSONP.
     * But it can be sent as it if we're asked explicitly for JSON (http://…/login.json)
     *
     * See http://jsfiddle.net/HjCc2/6/ for usage.
     */
    def login = {
        def user
        try {
            SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT)
            if (session.currentEcitizenId) {
                SecurityContext.setCurrentEcitizen(session.currentEcitizenId)
                user = userSearchService.getById(session.currentEcitizenId)
            }
        } catch (Exception e) {
            log.error e.message
            render status: 500
        }
        def map
        if (user)
            map = [connected:true, firstname:user.firstName, lastName:user.lastName]
        else
            map = [connected:false]

        withFormat {
            js {
                render text: (params.callback ?: 'callback') + '(' + (map as JSON) + ')',
                       contentType: 'text/javascript',
                       status: 200
            }
            json {
                // Note: '*' can't be used with credentials.
                response.setHeader 'Access-Control-Allow-Origin', request.getHeader('Origin')
                response.setHeader 'Access-Control-Allow-Credentials', 'true'
                render text: map as JSON,
                       contentType: 'application/json',
                       status: 200
            }
        }
    }
    def loginAgent = {
        def agent = null
        def authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          log.debug("OAuth2 token is missing.");
          throw new OAuth2Exception();
        }

        String [] bearer = authHeader.split(" ", 2);
        AccessToken token = oauth2Service.valide(bearer[1]);
        try {
          agent = agentService.getByLogin(token.resourceOwnerName)
        } catch (Exception e) {
            log.error e.message
            render status: 500
        }
        if (agent == null) {
          render( 
            text: (['error': 'No agent found'] as JSON),
            contentType: 'application/json',
            status: 200)
        } else {
          def role = (agentService.isAdmin(agent)) ? 'admin' :
              (categoryService.hasWriteProfile(agent)) ? 'manager' : 'agent'

          render text:  ([
                        'firstname': agent.firstName,
                        'lastname': agent.lastName,
                        'login': agent.login,
                        'email': agent.email,
                        'role' : role
                      ] as JSON),
               contentType: 'application/json',
               status: 200
       }
    }
    def auth = {
      def error
        try {
          def res = authenticationService.authenticate(params.login,params.password)
          render text: "",
                contentType: "application/json",
                status: 200
          return true;
        } catch (CvqUnknownUserException e) {
          error = "account.error.invalidLogin"
        } catch (CvqAuthenticationFailedException e) {
          error = "account.error.authenticationFailed"
        } catch (CvqDisabledAccountException e) {
          error = "account.error.disabledAccount"
        }
        render text: "{ error: \""+error+"\", error_description: \""+message(code : error)+"\"}",
               contentType: 'application/json',
               status: (error == "account.error.authenticationFailed") ? 500 :
                         (error == "account.error.invalidLogin") ? 404 :
                         401
    }
    def authAgent = {
      def error
        try {
          def res = authenticationService.authenticateAgent(params.login,params.password)
          render text: "",
                contentType: "application/json",
                status: 200
          return true;
        } catch (CvqAuthenticationFailedException e) {
          error = "account.error.authenticationFailed"
        } catch (CvqDisabledAccountException e) {
          error = "account.error.disabledAccount"
        }
        render text: "{ error: \""+error+"\", error_description: \""+message(code : error)+"\"}",
               contentType: 'application/json',
               status: (error == "account.error.authenticationFailed") ? 500 :
                         (error == "account.error.invalidLogin") ? 404 :
                         401
    }
}
