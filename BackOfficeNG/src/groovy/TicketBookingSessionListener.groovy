import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext

import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.request.reservation.ITicketBookingRequestService

public class TicketBookingSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    // TODO: Very dirty implementation to conform to LibreDémat callBack constraint
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        ApplicationContext ctx = (ApplicationContext) ApplicationHolder.getApplication().getMainContext()
        ITicketBookingRequestService ticketBookingRequestService = (ITicketBookingRequestService) ctx.getBean("ticketBookingRequestService")
        ILocalAuthorityRegistry localAuthorityRegistry = (ILocalAuthorityRegistry) ctx.getBean("localAuthorityRegistry")

        def session = httpSessionEvent.getSession()
        getSessionUUIDs(session).each {
            if (session[it].ticketBooking != null) {
                def toFreePlaces = ''
                if (session[it].cRequest.tbrTicket != null) {
                    session[it].cRequest.tbrTicket.each {
                        toFreePlaces += it.placeCategoryId + ',' + it.placeNumber + ','
                    }
                    localAuthorityRegistry.callback(session['currentSiteName'], ticketBookingRequestService,
                        'freeAllBookedPlaces', [toFreePlaces] as String[] )
                }
            }
        }
    }

    private Set getSessionUUIDs(session) {
        def uuidSet = [] as Set
        session.attributeNames.each {
            if (isUuid(it)) uuidSet.add(it)
        }
        return uuidSet
    }

    private boolean isUuid(uuidString) {
        try {
            UUID.fromString(uuidString)
            return true
        } catch (IllegalArgumentException e) {
            return false
        }
    }
}

