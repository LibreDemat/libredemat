import fr.cg95.cvq.business.external.ExternalServiceTrace
import fr.cg95.cvq.business.external.TraceStatusEnum
import fr.cg95.cvq.business.request.Request
import fr.cg95.cvq.business.request.RequestState
import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.request.IRequestExternalService
import fr.cg95.cvq.util.Critere

import grails.converters.JSON
import net.sf.oval.constraint.EmailCheck

class BackofficeExternalController {

    def externalService
    IRequestExternalService requestExternalService
    def requestAdaptorService

    def defaultSortBy = ExternalServiceTrace.SEARCH_BY_DATE
    def count = 15

    def search = {
        if (request.get) {
            return [
                "inSearch" : false,
                "sortBy" : defaultSortBy,
                "lastOnly" : true,
                "filters" : [:]
            ].plus(initSearchReferential())
        } else if (request.post) {
            Set<Critere> criteria = new HashSet<Critere>()
            if (params.key)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_KEY,
                    params.key, Critere.EQUALS))
            if (params.dateFrom)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_DATE,
                    DateUtils.stringToDate(params.dateFrom), Critere.GTE))
            if (params.dateTo)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_DATE,
                    DateUtils.stringToDate(params.dateTo), Critere.LTE))
            def parsedFilters = SearchUtils.parseFilters(params.filterBy)
            if (parsedFilters.filters.externalServiceLabelFilter)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_NAME,
                    parsedFilters.filters.externalServiceLabelFilter,
                    Critere.EQUALS))
            if (parsedFilters.filters.statusFilter)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_STATUS,
                    TraceStatusEnum.forString(parsedFilters.filters.statusFilter),
                    Critere.EQUALS))
            if (parsedFilters.filters.requestTypeFilter)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_REQUEST_TYPE,
                    parsedFilters.filters.requestTypeFilter, Critere.EQUALS))
            if (parsedFilters.filters.requestStateFilter)
                criteria.add(new Critere(ExternalServiceTrace.SEARCH_BY_REQUEST_STATE,
                    parsedFilters.filters.requestStateFilter, Critere.EQUALS))
            def sortBy = params.sortBy ? params.sortBy : defaultSortBy
            def offset
            try {
                offset = Integer.valueOf(params.offset)
            } catch (NumberFormatException e) {
                offset = 0
            }
            def traces
            def totalRecords
            if (params.lastOnly) {
                traces = externalService.getLastTraces(criteria, sortBy, "desc", count, offset)
                totalRecords = externalService.getLastTracesCount(criteria)
            } else {
                traces = externalService.getTraces(criteria, sortBy, "desc", count, offset)
                totalRecords = externalService.getTracesCount(criteria)
            }
            traces = requestAdaptorService.prepareTraces(traces)
            return [
                "key" : params.key,
                "dateFrom" : params.dateFrom,
                "dateTo" : params.dateTo,
                "traces" : traces,
                "totalRecords" : totalRecords,
                "keys" : requestExternalService.getKeys(criteria),
                "lastOnly" : params.lastOnly,
                "filters":parsedFilters.filters,
                "filterBy":parsedFilters.filterBy,
                "offset" : offset,
                "sortBy" : sortBy,
                "inSearch" : true
            ].plus(initSearchReferential())
        }
    }

    def sendRequests = {
        def email = SecurityContext.currentAgent.email
        if (!email) {
            if (params.email) {
                if (new EmailCheck().isSatisfied(null, params.email, null, null)) {
                    email = params.email
                } else {
                    render ([status : "error",
                        error_msg : message(code : "externalService.batchRequestResend.error.email.invalid")]
                            as JSON)
                    return false
                }
            } else {
                render ([status : "error",
                    error_msg : message(code : "externalService.batchRequestResend.error.email.required")]
                        as JSON)
                return false
            }
        }
        def ids = []
        if (params.ids instanceof String) ids.add(Long.valueOf(params.ids))
        else params.ids.each { ids.add(Long.valueOf(it)) }
        def criteria = new Critere(Request.SEARCH_BY_REQUEST_ID, ids, Critere.IN)
        def criterias = new HashSet<Critere>()
        criterias.add(criteria)
        requestExternalService.sendRequests(criterias, email)
        render ([status : "ok",
            success_msg : message(code : "externalService.batchRequestResend.message.success", args : [email])]
                as JSON)
    }

    private initSearchReferential() {
        return [
            "externalServiceLabels" : SecurityContext.currentConfigurationBean
                .externalProviderServices.collect { it.key.label },
            "traceStatuses" : TraceStatusEnum.allTraceStatuses,
            "requestStates" : RequestState.allRequestStates.findAll { it != RequestState.DRAFT },
            "requestTypes" : requestAdaptorService.translateAndSortRequestTypes()
        ]
    }
}
