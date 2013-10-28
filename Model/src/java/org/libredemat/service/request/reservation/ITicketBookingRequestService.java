package org.libredemat.service.request.reservation;

import org.libredemat.business.request.reservation.TicketBookingRequest;
import org.libredemat.exception.CvqTicketBookingException;
import org.libredemat.service.payment.IRequestPaymentService;
import org.libredemat.service.request.IRequestService;

public interface ITicketBookingRequestService extends IRequestService, IRequestPaymentService {

    void reserve(TicketBookingRequest request, String placeNumber, Long fareId,
            Long placeCategoryId, Long eventId) throws CvqTicketBookingException;
    
    void free(TicketBookingRequest request, int ticketIndex);

    void switchSubscriberMode(TicketBookingRequest request, boolean isSubscriber, String number, 
            String firstName, String lastName) throws CvqTicketBookingException;

    void freeAllBookedPlaces(String placeCategoriesAndBookedPlaces);
}
