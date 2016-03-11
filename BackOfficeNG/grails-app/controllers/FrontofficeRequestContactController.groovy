import org.libredemat.business.request.RequestNoteType;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.service.request.IRequestNoteService;

class FrontofficeRequestContactController {

    IRequestNoteService requestNoteService
    RequestAdaptorService requestAdaptorService

    def reply = {
        def model = ['requestId':params.id, 'parentId':params.long('parentId')]
        def requestId = Long.valueOf(params.id)
        def parentId = params.parentId && params.parentId.length() > 0 ? Long.valueOf(params.parentId) : null
        if (request.get) {
            def parentReply =
            requestAdaptorService.prepareNotes(
                    requestNoteService.getNotes(requestId,null).findAll{ it.id == parentId })
            model.parentReply = parentReply
            return model
        } else if (request.post) {
            if (params.message.length() == 0) {
                flash.errorMessage = message("code":"request.conversation.messageMustNotBeEmpty")
                return model
            }
            def attach = request.getFile("attachment")
            def attachName
            if(!attach.isEmpty()) {
                attachName = attach.getOriginalFilename()
                requestNoteService.addNote(params.long('id'),
                        RequestNoteType.PUBLIC, params.message,
                        attach.getBytes(), attachName, params.long('parentId'))
            } else {
                requestNoteService.addNote(params.long('id'),
                        RequestNoteType.PUBLIC, params.message,null,null, params.long('parentId'))
            }
            flash.successMessage = message("code":"request.conversation.replySendSuccess")
            redirect(controller:'frontofficeRequest', action:'summary', id:params.id)
        }
    }

    def viewAttachment = {
        if (!request.get) return false

        def document = requestNoteService.getNote(Long.valueOf(params.id),
            Long.valueOf(params.requestNoteId))
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition",
            "attachment; filename=${document.attachmentName == null ? 'letter.pdf' : document.attachmentName}")
        def data = document.attachment
        response.contentLength = data.length
        response.outputStream << data
        response.outputStream.flush()
    }

}