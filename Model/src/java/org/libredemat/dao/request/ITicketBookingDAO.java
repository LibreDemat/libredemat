package org.libredemat.dao.request;

import java.util.List;
import java.util.Set;

import org.libredemat.business.request.ticket.Entertainment;
import org.libredemat.business.request.ticket.Event;
import org.libredemat.business.request.ticket.Fare;
import org.libredemat.business.request.ticket.PlaceCategory;
import org.libredemat.business.request.ticket.Subscriber;
import org.libredemat.util.Critere;


public interface ITicketBookingDAO {

    List<Entertainment> searchEntertainment(final Set<Critere> criteria, final String sort, String dir, 
            int recordsReturned, int startIndex);

    List<Event> searchEvent(final Set<Critere> criteria, final String sort, String dir, 
            int recordsReturned, int startIndex);

    Entertainment findEntertainmentByExternalId(String externalId);

    Event findEventByExternalId(String externalId);

    PlaceCategory findPlaceCategoryByExternalId(String externalId);

    Fare findFareByExternalId(String externalId);

    List<Subscriber> searchSubscriber(final Set<Critere> criteria, final String sort, String dir, 
            int recordsReturned, int startIndex);

    Subscriber findSubscriberByNumber(String number);
}
