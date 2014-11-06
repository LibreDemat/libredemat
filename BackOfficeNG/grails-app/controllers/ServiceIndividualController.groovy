import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.oauth2.InsufficientScopeException;
import org.libredemat.exception.CvqObjectNotFoundException
import org.libredemat.service.authority.IAgentService

import org.libredemat.business.users.Adult

import grails.converters.JSON

class ServiceIndividualController {

    IUserSearchService userSearchService
    IExternalHomeFolderService externalHomeFolderService
    IAgentService agentService

    def beforeInterceptor = {
        def token = request.getAttribute("accessToken")
        if (!token?.sufficientScope("individual")) {
            forward(controller: 'OAuth2', action: 'invalidScope')
            return false;
        }
    }

    def defaultAction = 'userInfo'

    def userInfo = {
        def token = request.getAttribute("accessToken")
        def user

        try {
          if(params.eCitizenId != null && params.eCitizenId != "") {
            def agent = agentService.getByLogin(token.resourceOwnerName)
            if(agent) {
                user = userSearchService.getById(params.eCitizenId as Long)
            } else {
                render(status: 403)
            }
          } else {
            user = userSearchService.getByLogin(token.resourceOwnerName)
          }
        } catch (CvqObjectNotFoundException ex) {
          render(status: 404)
        }

        def individualMapping = externalHomeFolderService.
                getIndividualMapping(user, SecurityContext.getCurrentExternalService())
        if (individualMapping != null) {
            user.setExternalLibreDematId(individualMapping.getExternalLibreDematId())
            user.setExternalId(individualMapping.getExternalId())
        }

        def result = [externalLibredematId:user.externalLibreDematId, externalId:user.externalId,
               email: user.email, firstname: user.firstName, lastname: user.lastName]

        if(token.scope.contains("homefolderId")) {
          result += [homefolderId: user.getHomeFolder().getId()]
        }

        if(token.scope.contains("sessionActivityId")) {
          result += [sessionActivityId: ((Adult)user).getSessionActivityId()]
        }

        render (result as JSON)
    }

}
