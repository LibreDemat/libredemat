import org.libredemat.service.request.IRequestLockService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IRequestWorkflowService
import org.libredemat.business.request.RequestState
import org.libredemat.business.payment.InternalInvoiceItem

import org.libredemat.security.SecurityContext

import org.libredemat.exception.CvqModelException
import org.libredemat.exception.CvqTicketBookingException

import org.libredemat.service.request.reservation.ITicketBookingRequestService
import org.libredemat.service.request.ITicketBookingService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.business.request.ticket.Event
import org.libredemat.util.Critere
import org.libredemat.business.authority.LocalAuthorityResource

class FrontofficeTicketBookingController {

    IRequestLockService requestLockService
    IRequestSearchService requestSearchService
    IRequestWorkflowService requestWorkflowService
    ITicketBookingRequestService ticketBookingRequestService
    ITicketBookingService ticketBookingService
    ILocalAuthorityRegistry localAuthorityRegistry

    DocumentAdaptorService documentAdaptorService
    RequestTypeAdaptorService requestTypeAdaptorService

    def getAndLockRequest() {
        def requestId = Long.valueOf(params.requestId != null ? params.requestId : params.id)
        requestLockService.lock(requestId)
        return requestSearchService.getById(requestId, true)
    }

    def edit = {
        def rqt = getAndLockRequest()
        def requestTypeResources = requestTypeAdaptorService.requestTypeResources(rqt.requestType.label)
        render(view: "/frontofficeRequestType/${requestTypeResources.requestTypeLabelAsDir}/edit", model: [
            'rqt': rqt,
            'hasHomeFolder': SecurityContext.currentEcitizen ? true : false,
            'currentStep': 'entertainments',
            'documentTypes': documentAdaptorService.getDocumentTypes(rqt),
            'returnUrl' : (params.returnUrl != null ? params.returnUrl : ""),
            'isEdition' : !RequestState.DRAFT.equals(rqt.state),
            'customReferential' : [
                'businessStep': 'entertainments',
                'ticketEvents': ticketBookingService.getEvents(new HashSet<Critere>(), session.ticketBooking.sortBy, null, session.ticketBooking.recordsReturns, session.ticketBooking.startsIndex),
                'maxCartSize': ticketBookingService.getMaxCartSize()
            ]
        ].plus(requestTypeResources))
    }

    def entertainmentLogo = {
        def entertainment = ticketBookingService.getEntertainmentById(Long.valueOf(params.id))
        def logo = entertainment.logo
        if (logo == null) {
            def noLogoResource = localAuthorityRegistry.getLocalAuthorityResourceFile(
                LocalAuthorityResource.Type.IMAGE, 'ticketBookingNoLogo',
                LocalAuthorityResource.Version.CURRENT, true)
            logo = noLogoResource.readBytes()
        }
        response.contentType = "image/png"
        response.outputStream << logo
    }

    def previousEvents = {
        session.ticketBooking.startsIndex -= session.ticketBooking.recordsReturns
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def nextEvents = {
        session.ticketBooking.startsIndex += session.ticketBooking.recordsReturns
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def sortEvents = {
        session.ticketBooking.sortBy = params.sortBy
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def selectEvent = {
        session.ticketBooking.selectedEventId = Long.valueOf(params.eventId)
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def askUpdateSubscriberMode = {
        flash.tbrAskUpdateSubscriberMode = true;
        flash.isSubscriber = new Boolean(params.isSubscriber);
        flash.subscriberNumber = params.subscriberNumber
        flash.subscriberFirstName = params.subscriberFirstName
        flash.subscriberLastName = params.subscriberLastName
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def updateSubscriberMode = {
        if (!params.cancel) {
            def rqt = getAndLockRequest()
            try {
                ticketBookingRequestService.switchSubscriberMode(rqt, new Boolean(params.isSubscriber),
                    params.subscriberNumber, params.subscriberFirstName, params.subscriberLastName)
            } catch (CvqTicketBookingException ctbe) {
                flash.tbrErrorSubscriber = true
                flash.tbrMessageError = message(code: ctbe.i18nKey)
            }
        }
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def bookPlaces = {
        try {
            ticketBookingRequestService.reserve(
                getAndLockRequest(), 
                params.placeNumber,
                Long.valueOf(params.fareId),
                Long.valueOf(params.categoryId),
                Long.valueOf(params.eventId))
        } catch (CvqTicketBookingException e) {
            flash.tbrErrorFareId = Long.valueOf(params.fareId)
            flash.tbrMessageError = message(code: e.i18nKey, args:e.i18nArgs as List)
        }
        redirect (action:'edit', params:['requestId':params.requestId])
    }

    def freePlaces = {
        ticketBookingRequestService.free(getAndLockRequest(), Integer.valueOf(params.ticketIndex))
        redirect (action:'edit', params:['requestId':params.requestId])
    }

}
