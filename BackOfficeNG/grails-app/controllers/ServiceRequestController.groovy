import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.users.Individual
import org.libredemat.dao.request.IRequestDAO
import org.libredemat.security.SecurityContext
import org.libredemat.service.request.impl.RequestActionService;
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.users.external.IExternalHomeFolderService
import org.libredemat.oauth2.InsufficientScopeException
import org.libredemat.service.request.IRequestActionService
import org.libredemat.business.users.TitleType

import grails.converters.JSON

class ServiceRequestController {

    IUserSearchService userSearchService
    IExternalHomeFolderService externalHomeFolderService
    IRequestDAO requestDAO
    IRequestActionService requestActionService

    def beforeInterceptor = {
        def token = request.getAttribute("accessToken")
        if (!token?.sufficientScope("request", params.requestTypeLabel)) {
            forward(controller: 'OAuth2', action: 'invalidScope')
            return false;
        }
    }

    def defaultAction = 'requestByIndividualAndType'

    def requestByIndividualAndType = {
        def token = request.getAttribute("accessToken")

        def user = userSearchService.getByLogin(token.resourceOwnerName)
        def individual = externalHomeFolderService.getIndividualMapping(params.individual)
        if (!user?.homeFolder?.id.equals(individual?.homeFolderMapping?.homeFolderId)) {
            response.status = 403
            render ([error: 'Invalid or unauthorized individual identifier.'] as JSON)
            return false
        }

        def requests = requestDAO.listBySubjectAndLabel(
            individual?.individualId, params.requestTypeLabel.replaceAll("_", " "), null, false)
        def requestList = new ArrayList()
        for (Request r: requests) {
            def map = new HashMap()
            map.put("id", r.id)
            map.put("state", r.state.name())
            map.put("creationDate", r.creationDate)
            map.put("lastModificationDate", r.lastModificationDate)
            map.put("subjectId", r.subjectId)
            requestList.add(map)
        }
        render(requestList as JSON)
    }
}

