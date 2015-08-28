import java.util.Hashtable

import org.libredemat.business.QoS
import org.libredemat.business.users.Individual
import org.libredemat.business.request.Request
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.util.Critere

import grails.converters.JSON

class BackofficeTasksController {

    IRequestSearchService requestSearchService
    IUserSearchService userSearchService

    def defaultAction = 'tasks'

    // Default number of tasks to show per type
    def tasksShownNb = 5
    def beforeInterceptor = {
        session['currentMenu'] = 'tasks'
    }

    def tasks = {
        def taskMap = [:]

        taskMap.aboutRequests = [
            'late' : [
                'count' : requestSearchService.countTasks(Request.QUALITY_TYPE_RED),
                'all' : requestSearchService.listTasks(Request.QUALITY_TYPE_RED,
                        Request.SEARCH_BY_CREATION_DATE,
                        tasksShownNb)
            ],
            'urgent' : [
                'count' : requestSearchService.countTasks(Request.QUALITY_TYPE_ORANGE),
                'all' : requestSearchService.listTasks(Request.QUALITY_TYPE_ORANGE,
                        Request.SEARCH_BY_CREATION_DATE,
                        tasksShownNb)
            ],
            'good' : [
                'count' : requestSearchService.countTasks(Request.QUALITY_TYPE_OK),
                'all' : requestSearchService.listTasks(Request.QUALITY_TYPE_OK,
                        Request.SEARCH_BY_CREATION_DATE,
                        tasksShownNb)
            ]
        ]

        def sortParams = [ 'lastModificationDate': 'asc']
        taskMap.aboutIndividuals = [
            'late' : [
                'count' : userSearchService.countTasks(QoS.LATE),
                'all' : userSearchService.listTasks(QoS.LATE, sortParams, tasksShownNb, 0)
            ],
            'urgent' : [
                'count' : userSearchService.countTasks(QoS.URGENT),
                'all' : userSearchService.listTasks(QoS.URGENT, sortParams, tasksShownNb, 0)
            ],
            'good' : [
                'count' : userSearchService.countTasks(QoS.GOOD),
                'all' : userSearchService.listTasks(QoS.GOOD, sortParams, tasksShownNb, 0)
            ],
            'duplicates' : [
                'count' : userSearchService.countDuplicates(),
                'all' : userSearchService.listDuplicates(tasksShownNb)
            ]
        ]

        return ['taskMap' : taskMap]
    }
}
