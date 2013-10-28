import org.libredemat.security.SecurityContext
import org.libredemat.oauth2.InsufficientScopeException

import grails.converters.JSON

class ServiceLocalAuthorityController {

    def defaultAction = 'localAuthorityInfos'

    def beforeInterceptor = {
        def token = request.getAttribute("accessToken")
        if (!token?.sufficientScope("localauthority")) {
            forward(controller: 'OAuth2', action: 'invalidScope')
            return false;
        }
    }

    def localAuthorityInfos = {
        def localAuthority = SecurityContext.getCurrentSite()
        render([
            name: localAuthority.name,
            cp: localAuthority.postalCode,
            title: localAuthority.displayTitle,
            email: localAuthority.adminEmail
            ] as JSON)
    }

}
