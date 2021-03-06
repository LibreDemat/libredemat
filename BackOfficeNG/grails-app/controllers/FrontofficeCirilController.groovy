import java.util.HashMap
import java.util.Map

import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.external.IRequestExternalService
import org.libredemat.service.request.school.external.IScholarBusinessProviderService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.request.school.external.IRemoteCirilSchoolsProvider;

import grails.converters.JSON

class FrontofficeCirilController {
    def IRequestExternalService requestExternalService
    def IUserSearchService userSearchService
    def IRequestSearchService requestSearchService

    def schools = {
        def service = requestExternalService.getExternalServiceByRequestType(params.requestTypeLabel)
        def child = userSearchService.getChildById(params.subjectIdContainer.toLong())
        def rqt = requestSearchService.getById(Long.parseLong(params.requestId), true)

        def parameters = [:]
        parameters['subjectIdContainer'] = params.subjectIdContainer
        parameters['sectionContainer'] = params.sectionContainer
        parameters['requestId'] = params.requestId

        try {
            def result = (service instanceof IRemoteCirilSchoolsProvider ) ? ((IRemoteCirilSchoolsProvider ) service).loadChildSchools(parameters) : new HashMap<String,String>()
            render(result as JSON)
        } catch(Exception ex) {
            render(contentType:"text/json", text: ["error": ex.getMessage()] as JSON)
        }
    }

}
