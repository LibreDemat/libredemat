import java.util.Hashtable

import org.libredemat.business.authority.Agent
import org.libredemat.business.request.Request
import org.libredemat.business.request.RequestState
import org.libredemat.business.request.RequestType
import org.libredemat.service.authority.IAgentService
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestStatisticsService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.util.Critere
import org.libredemat.util.translation.impl.TranslationService;
import org.libredemat.security.SecurityContext

class BackofficeRequestController {

    IAgentService agentService
    ICategoryService categoryService
    IRequestSearchService requestSearchService
    IRequestStatisticsService requestStatisticsService
    IRequestTypeService requestTypeService
    
    def translationService
    def requestAdaptorService
    def localAuthorityRegistry
    def requestCsvAdaptorService
    
    def defaultAction = 'initSearch'
    // keys supported in advanced search screen : match with keys defined in Request.java
    def supportedKeys = ['requesterLastName', 'subjectLastName', 'id', 'homeFolderId',
                         'creationDateFrom', 'creationDateTo']
    def longKeys = ['id', 'homeFolderId']
    def dateKeys = ['creationDateFrom', 'creationDateTo']
    def defaultSortBy = 'creationDate'
    def defaultSortDir = 'desc'
    def resultsPerPage = 15
    def beforeInterceptor = {
        session["currentMenu"] = "requests"
    }
    
    
    /**
     * Called when first entering the search screen
     */
    def initSearch = {
        if(session['filterBy'] || session['sortBy'] || session['sortDir']) {
            redirect(action:search,params:['filterBy':session['filterBy']])
        }
        else {
        render(view:'search', 
            model:['inSearch':false, 'sortBy':defaultSortBy, 'dir': defaultSortDir,
                   'filters':[:]].plus(initSearchReferential()))
        }
    }

    /**
     * Called (synchronously) when performing a search
     */
    def search = {

        def (criteria, parsedFilters, sortBy, sortDir) = prepareSearch(request)

        def requestSeasons = null
        if (parsedFilters.filters.get("requestTypeIdFilter") != null) {
            requestSeasons = requestTypeService.getRequestSeasons(Long.valueOf(parsedFilters.filters.get("requestTypeIdFilter")))
        }

        // deal with pagination settings
        def results = params.results == null ? resultsPerPage : Integer.valueOf(params.results)
        def recordOffset =
            (params.recordOffset == "" || params.recordOffset == null) ? 0 : Integer.valueOf(params.recordOffset)

        // now, perform the search request
        def requests = requestSearchService.get(criteria, sortBy, sortDir, results, recordOffset, false)
        def recordsList = requests.collect { requestAdaptorService.prepareRecordForSummaryView(it) }

        session['filterBy'] = parsedFilters.filterBy
        session['sortBy'] = sortBy

        render(view: 'search',
            model:['records': recordsList,
                   'recordsReturned': requests.size(),
                   'totalRecords': requestSearchService.getCount(criteria),
                   'filters': parsedFilters.filters,
                   'filterBy': parsedFilters.filterBy,
                   'recordOffset': recordOffset,
                   'sortBy': sortBy,
                   'dir': sortDir,
                   'inSearch': true,
                   'allRequestSeasons': requestSeasons,
                   'requestTypeFilterFilled': checkIfRequestTypeFilterIsFilled(parsedFilters),
                   'results': results].plus(initSearchReferential()))
    }

    def exportCsv = {

        def (criteria, parsedFilters, sortBy, sortDir) = prepareSearch(request)
        def requests = requestSearchService.get(criteria, sortBy, sortDir, -1, 0, true)

        if (requests.empty) {
            flash.errorMessage = message("code": "request.exportCsv.error.noresult")
            search()
        } else if (!checkIfRequestTypeFilterIsFilled(parsedFilters)) {
            flash.errorMessage = message("code": "request.exportCsv.error.nofilter")
            search()
        } else {
            response.setHeader("Content-disposition", "attachment; filename=export-demandes.csv")
            response.contentType = "text/csv"
            response.outputStream.withWriter('ISO-8859-1') { writer ->
                requestCsvAdaptorService.exportRequestAsCsv(requests, writer)
            }
        }
    }

    def prepareSearch(request) {

        // deal with search criteria
        Set<Critere> criteria = new HashSet<Critere>()
        params.each { key,value ->
            if (supportedKeys.contains(key) && value != "") {
                Critere critere = new Critere()
                critere.attribut = key
                critere.comparatif = Critere.EQUALS
                if (longKeys.contains(key)) {
                    critere.value = LongUtils.stringToLong(value)
                } else if (dateKeys.contains(key)) {
                    critere.value = DateUtils.stringToDate(value)
                    if (key == 'creationDateFrom') {
                        critere.attribut = 'creationDate'
                        critere.comparatif = Critere.GTE
                    } else {
                        critere.attribut = 'creationDate'
                        critere.comparatif = Critere.LTE
                    }
                } else {
                    critere.comparatif = Critere.STARTSWITH
                    critere.value = value
                }
                criteria.add(critere)
            }
        }

        // deal with dynamic filters
        def hasStateFilter = false
        def parsedFilters = SearchUtils.parseFilters(params.filterBy)
        parsedFilters.filters.each { key, value ->
            Critere critere = new Critere()
            critere.attribut = key.replaceAll('Filter','')
            critere.comparatif = Critere.EQUALS
            if (key == 'stateFilter') {
                critere.value = RequestState.forString(value)
                hasStateFilter = true
            }
            else if (key == 'qualityFilter') {
                critere.attribut = 'qualityType'
                critere.value = "qualityType"+value
            } else
                critere.value = Long.valueOf(value)
            criteria.add(critere)
        }
        if (!hasStateFilter) {
            Critere critere = new Critere()
            critere.attribut = Request.SEARCH_BY_STATE
            critere.comparatif = Critere.NEQUALS
            critere.value = RequestState.ARCHIVED
            criteria.add(critere)
        }
        // in all cases, do not display draft requests
        criteria.add(new Critere(Request.SEARCH_BY_STATE, RequestState.DRAFT, Critere.NEQUALS))

        // deal with dynamic sorts
        def sortBy = defaultSortBy
        if(params.sortBy)
            sortBy = params.sortBy
        else if(session['sortBy'])
            sortBy = session['sortBy']

        def sortDir = params.dir ? params.dir : 'desc'

        return [criteria, parsedFilters, sortBy, sortDir]
    }

    def initSearchReferential() {
        def subMenuEntries = ["request.search"]
        if (categoryService.hasManagerProfile(SecurityContext.currentAgent)) {
            subMenuEntries.add("requestType.list")
            def urlBooker = SecurityContext.getCurrentConfigurationBean().getExternalApplicationProperty("booker.url")
            if (!urlBooker.isEmpty()) {
                subMenuEntries.add(translationService.translate("submenu.booker") + "|" + urlBooker + "admin")
            }
        }

        return ['allStates':RequestState.allRequestStates.findAll { it != RequestState.DRAFT },
                'allAgents':agentService.getAll(),
                'allCategories':categoryService.getAll(),
                'allRequestTypes':requestAdaptorService.translateAndSortRequestTypes(),
                'subMenuEntries' : subMenuEntries]
    }

    def listTasks = {
        def recordsList = []
        requestSearchService.listTasks(params.qoS, defaultSortBy, 0).each {
            def record = requestAdaptorService.prepareRecordForSummaryView(it)
            recordsList.add(record)
        }

        session['filterBy'] = [:]
        session['sortBy'] = defaultSortBy
        session['sortDir'] = defaultSortDir

        // TODO deal with pagination
        render(view : 'search', model:['records' : recordsList,
                   'recordsReturned' : recordsList.size(),
                   'totalRecords' : recordsList.size(),
                   'filters' : [:],
                   'filterBy' : [:],
                   'recordOffset' : 0,
                   'sortBy' : defaultSortBy,
                   'dir': defaultSortDir,
                   'inSearch' : true,
                   'results' : 100
                   ].plus(initSearchReferential()))
    }

    private def checkIfRequestTypeFilterIsFilled(parsedFilters) {
        parsedFilters.get("filters") != null && parsedFilters.get("filters").containsKey("requestTypeIdFilter")
    }
}
