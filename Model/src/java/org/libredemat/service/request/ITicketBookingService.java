package org.libredemat.service.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.libredemat.business.request.ticket.Entertainment;
import org.libredemat.business.request.ticket.Event;
import org.libredemat.business.request.ticket.Fare;
import org.libredemat.business.request.ticket.FareType;
import org.libredemat.business.request.ticket.PlaceCategory;
import org.libredemat.business.request.ticket.Subscriber;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.exception.CvqTicketBookingException;
import org.libredemat.util.Critere;


public interface ITicketBookingService {

    Long createEntertainment(Entertainment entertainment) throws CvqException;

    void updateEntertainment(Entertainment entertainment);

    void deleteEntertainment(Long id);

    Entertainment getEntertainmentById(final Long id);

    List<Entertainment> getEntertainments(Set<Critere> criteriaSet, final String sort,
            final String dir, final int recordsReturned, final int startIndex) throws CvqException;

    List<Entertainment> getAllEntertainments() throws CvqException;

    Event getEventById(final Long id);

    List<Event> getEvents(Set<Critere> criteriaSet, final String sort,
            final String dir, final int recordsReturned, final int startIndex) throws CvqException;

    void updateEvent(Event event);

    void deleteEvent(Long id);

    PlaceCategory getPlaceCategoryById(final Long id);

    void deletePlaceCategory(Long id, Long eventId);

    Fare getFareById(final Long id);

    void updateFare(Fare fare) throws CvqObjectNotFoundException;

    void deleteFare(Long id, Long eventId);

    Subscriber getSubscriberById(final Long id);

    List<Subscriber> getSubscribers(Set<Critere> criteriaSet, final String sort,
            final String dir, final int recordsReturned, final int startIndex) throws CvqException;

    void updateSubscriber(Subscriber subscriber);

    void deleteSubscriber(Long id);

    Map<String,Integer> importEntertainments(byte[] entertainments) throws CvqTicketBookingException;

    Map<String,Integer> importSubscribers(byte[] subscribers) throws CvqTicketBookingException;

    boolean isSubscriber(String number, String firstName, String lastName) throws CvqTicketBookingException;

    Integer getSubscriberLimit(String subscriberNumber, FareType fareType);

    int getMaxCartSize();
}
