import org.libredemat.business.request.RequestState
import org.libredemat.business.request.RequestNote;
import org.libredemat.security.SecurityContext
import org.libredemat.service.request.ICategoryService
import org.libredemat.service.request.IRequestDocumentService
import org.libredemat.service.request.IRequestLockService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.request.IRequestWorkflowService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.util.UserUtils

import org.joda.time.DateTime;
import org.joda.time.Minutes;

class RequestAdaptorService {

    IRequestDocumentService requestDocumentService
    IRequestLockService requestLockService
    IRequestSearchService requestSearchService
    IRequestTypeService requestTypeService
    IRequestWorkflowService requestWorkflowService
    IUserSearchService userSearchService
    ICategoryService categoryService

    def translationService
    def agentService

    public translateAndSortRequestTypes(onlyManaged = false) {
        def allRequestTypes =
            onlyManaged ? requestTypeService.getManagedRequestTypes() : requestTypeService.getAllRequestTypes()
        def allRequestTypesTranslated =  []
        allRequestTypes.each {
            allRequestTypesTranslated.add([
                id:it.id,
                label:translationService.translateRequestTypeLabel(it.label),
                categoryId:it.category?.id
            ])
        }
        return allRequestTypesTranslated.sort{it.label}
    }

    public prepareRecord(request) {
        return ['id':request.id,
                'requestTypeLabel':request.requestType.label,
                'label':translationService
                    .translateRequestTypeLabel(request.requestType.label).encodeAsHTML(),
                'creationDate':request.creationDate,
                'requesterName':UserUtils.getDisplayName(request.requesterId),
                'subjectName':UserUtils.getDisplayName(request.subjectId),
                'state':request.state.toString(),
                'lastModificationDate':request.lastModificationDate,
                'lastInterveningUserId': UserUtils.getDisplayName(request.lastInterveningUserId),
                'isEditable' : requestWorkflowService.isEditable(request.id)
        ]
    }

    public prepareRecords(requests) {
        if (!requests.records) requests.records = []
        requests.all.each {
            requests.records.add(prepareRecord(it))
        }
        
        return requests
    }

    public prepareRecordForSummaryView(request) {
        def homeFolder = userSearchService.getHomeFolderById(request.homeFolderId)
        def quality = 'green'
        if (request.redAlert)
            quality = 'red'
        else if (request.orangeAlert)
            quality = 'orange'
        def record = [
              'id':request.id,
              'label':translationService.translateRequestTypeLabel(request.requestType.label).encodeAsHTML(),
              'creationDate':request.creationDate,
              'requesterLastName': UserUtils.getDisplayName(request.requesterId),
              'subjectLastName': UserUtils.getDisplayName(request.subjectId),
              'homeFolderId':request.homeFolderId,
              'state':request.state.toString(),
              'lastModificationDate':request.lastModificationDate,
              'lastInterveningUserId': UserUtils.getDisplayName(request.lastInterveningUserId),
              'temporary':homeFolder.temporary,
              'quality':quality,
              'isViewable' : !RequestState.ARCHIVED.equals(request.state)
                  && categoryService.hasProfileOnCategory(SecurityContext.currentAgent,
                      request.requestType.category?.id),
              'hasAllDocuments' : requestDocumentService.hasAllDocuments(request),
              'season': request.requestSeason ? request.requestSeason.label : null
        ]

        return record
    }

    public prepareNote(requestNote) {
        if (!requestNote) return null
        return [
            'id':requestNote.id,
            'user':UserUtils.getUserDetails(requestNote.userId),
            'type':LibredematUtils.adaptLibredematEnum(requestNote.type, "request.note.type"),
            'note':requestNote.note,
            'date':requestNote.date,
            'template' : 'requestNote',
            'attachment' : requestNote.attachment,
            'attachmentName' : requestNote.attachmentName,
            'replies' : requestNote.children.collect{ prepareNote(it) }
        ]
    }

    public prepareActionToNote(requestAction) {
        if (!requestAction) return null
        return [
            'id': requestAction.id,
            'user': UserUtils.getUserDetails(requestAction.agentId),
            'type': LibredematUtils.adaptLibredematEnum(requestAction.type, "request.action.type"),
            'note': requestAction.note,
            'message' : requestAction.message,
            'date': requestAction.date,
            'template' : 'requestNote',
            'css' : 'reverse',
            'attachment' : requestAction.attachment,
            'attachmentName' : requestAction.attachmentName,
            'file' : requestAction.file,
            'fileName' : requestAction.filename,
            'replies' : []
        ]
    }
    
    public prepareNotes(requestNotes) {
        if (!requestNotes) requestNotes = []
        return requestNotes.collect{ prepareNote(it) }
    }

    public prepareNotes(requestNotes, repliesActions) {
        if (!requestNotes) requestNotes = []
        return requestNotes.collect{
            def note = prepareNote(it)
            def repActions = repliesActions.findAll{t -> it.id.equals(t.replyParentId)}
            if(repActions && !repActions.empty){
               repActions.each{
                   note.replies.add(prepareActionToNote(it))
                   }
            }
            note.replies = note.replies.sort{ t -> t.date}
            note
        }
    }

    public stepState(step, state, errorMsg, invalidFields = []) {
        step.state = state
        step.cssClass = 'tag-' + state
        step.i18nKey = 'request.step.state.' + state
        step.errorMsg = errorMsg
        step.invalidFields = invalidFields
    }

    public prepareLock(requestId) {
        def result = [:]
        result.locked = requestLockService.isLocked(requestId)
        result.lockedByCurrentUser =
            requestLockService.isLockedByCurrentUser(requestId)
        if (result.lockedByCurrentUser) result.cssClass = "lockacquired"
        else if (result.locked) result.cssClass = "locked"
        else result.cssClass = "free"
        if (result.lockedByCurrentUser || result.locked) result.i18nKey = "locked"
        else result.i18nKey = "free"
        def requestLock = requestLockService.getRequestLock(requestId)
        if (requestLock != null) {
            result.age =
                Minutes.minutesBetween(new DateTime(requestLock.getDate()),
                    new DateTime()).minutes
            result.lifetime =
                requestTypeService.globalRequestTypeConfiguration.requestLockMaxDelay - result.age
            if (result.lockedByCurrentUser)
                result.lockerName = translationService.translate("you")
            else {
                if (agentService.exists(requestLock.userId)) {
                    def agent = agentService.getById(requestLock.userId)
                    result.lockerName = "$agent.firstName $agent.lastName"
                } else {
                    result.lockerName =
                        """${translationService.translate("layout.theMale")} ${translationService.translate("eCitizen")}"""
                }
            }
        }
        return result
    }

    public prepareTraces(traces) {
        if (!traces) traces = []
        return traces.collect { prepareTrace(it) }
    }

    public prepareTrace(trace) {
        if (!trace) return null
        return [
            "key" : trace.key,
            "date" : trace.date,
            "status" : trace.status,
            "message" : trace.message,
            "externalServiceLabel" : trace.name,
            "request" :
                prepareRecordForSummaryView(
                    requestSearchService.getById(Long.valueOf(trace.key), false))
        ]
    }
}
