package org.libredemat.service.request.impl;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.request.LocalReferentialEntry;
import org.libredemat.business.request.LocalReferentialType;
import org.libredemat.business.request.ticket.Entertainment;
import org.libredemat.business.request.ticket.Event;
import org.libredemat.business.request.ticket.Fare;
import org.libredemat.business.request.ticket.FareType;
import org.libredemat.business.request.ticket.PlaceCategory;
import org.libredemat.business.request.ticket.Subscriber;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.request.ITicketBookingDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.exception.CvqTicketBookingException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.request.ILocalReferentialService;
import org.libredemat.service.request.ITicketBookingService;
import org.libredemat.util.Critere;
import org.libredemat.util.DateUtils;

import au.com.bytecode.opencsv.CSVReader;


public class TicketBookingService implements ITicketBookingService , ILocalAuthorityLifecycleAware {

    private static Logger logger = Logger.getLogger(TicketBookingService.class);

    private ITicketBookingDAO ticketBookingDAO;
    private IGenericDAO genericDAO;
    private ILocalReferentialService localReferentialService;

    @Override
    public Long createEntertainment(Entertainment entertainment) throws CvqException {
    // FIXME return Entertainment
        if (entertainment == null)
            throw new CvqException("No entertainment provided");
        return ((Entertainment)genericDAO.create(entertainment)).getId();
    }

    @Override
    public void updateEntertainment(Entertainment entertainment) {
        genericDAO.update(entertainment);
    }
    
    @Override
    public void deleteEntertainment(Long id) {
        genericDAO.delete(getEntertainmentById(id));
    }

    @Override
    public Entertainment getEntertainmentById(Long id) {
        return (Entertainment)genericDAO.findById(Entertainment.class, id);
    }

    @Override
    public List<Entertainment> getEntertainments(Set<Critere> criteriaSet, String sort, String dir,
            int recordsReturned, int startIndex) throws CvqException {
       return ticketBookingDAO.searchEntertainment(criteriaSet, sort, dir, recordsReturned, startIndex);
    }

    @Override
    public List<Entertainment> getAllEntertainments() throws CvqException {
        return getEntertainments(new HashSet<Critere>(), null, null, -1, 0);
    }

    @Override
    public Event getEventById(final Long id) {
        return (Event)genericDAO.findById(Event.class, id);
    }

    @Override
    public List<Event> getEvents(Set<Critere> criteriaSet, final String sort, 
            final String dir, final int recordsReturned, final int startIndex) throws CvqException {
        Date current = new Date();
        criteriaSet.add(new Critere(Event.SEARCH_BY_BOOKING_START, current, Critere.LT));
        criteriaSet.add(new Critere(Event.SEARCH_BY_BOOKING_END, current, Critere.GT));
        return ticketBookingDAO.searchEvent(criteriaSet, sort, dir, recordsReturned, startIndex);
    }

    @Override
    public void updateEvent(Event event) {
        genericDAO.saveOrUpdate(event);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        event.getEntertainment().getEvents().remove(event);
        genericDAO.delete(event);
    }

    @Override
    public PlaceCategory getPlaceCategoryById(final Long id) {
        return (PlaceCategory)genericDAO.findById(PlaceCategory.class, id);
    }

    @Override
    public void deletePlaceCategory(Long placeCategoryId, Long eventId) {
        Iterator<PlaceCategory> it = getEventById(eventId).getPlaceCategories().iterator();
        while (it.hasNext()) {
            PlaceCategory pc = it.next();
            if (pc.getId().equals(placeCategoryId))
                it.remove();
        }
        genericDAO.delete(getPlaceCategoryById(placeCategoryId));
    }

    @Override
    public Fare getFareById(final Long id) {
        return (Fare)genericDAO.findById(Fare.class, id);
    }

    @Override
    public void updateFare(Fare fare) throws CvqObjectNotFoundException {
        genericDAO.saveOrUpdate(fare);
    }

    @Override
    public void deleteFare(Long id, Long placeCategoryId) {
        Iterator<Fare> it = getPlaceCategoryById(placeCategoryId).getFares().iterator();
        while (it.hasNext()) {
            Fare f = it.next();
            if (f.getId().equals(id))
                it.remove();
        }
        genericDAO.delete(getFareById(id));
    }

    @Override
    public Subscriber getSubscriberById(Long id) {
        return (Subscriber)genericDAO.findById(Subscriber.class, id);
    }

    @Override
    public List<Subscriber> getSubscribers(Set<Critere> criteriaSet, String sort, String dir,
            int recordsReturned, int startIndex) throws CvqException {
        return ticketBookingDAO.searchSubscriber(criteriaSet, sort, dir, recordsReturned, startIndex);
    }

    @Override
    public void deleteSubscriber(Long id) {
        genericDAO.delete(getSubscriberById(id));
    }

    @Override
    public void updateSubscriber(Subscriber subscriber) {
        genericDAO.saveOrUpdate(subscriber);
    }

    /*
    "EntertainmentId","EntertainmentName","EntertainmentCategory","EntertainmentLink",
    "EventId","EventDate","EventPlace",
    "PlaceCategoryId","PlaceCategoryName","PlaceCategoryPlaceNumber",
    "FareId","FareName","FarePrice","FareWithSubscribtion"
     */
    @Override
    public Map<String,Integer> importEntertainments(byte[] entertainments) throws CvqTicketBookingException {
        Entertainment entertainment;
        Event event;
        PlaceCategory placeCategory;
        Fare fare;
        Map<String,Integer> report = new HashMap<String,Integer>();
        report.put("entertainment", 0);
        report.put("event", 0);
        report.put("placeCategory", 0);
        report.put("fare", 0);
        try {
            CSVReader csvReader = 
                new CSVReader(new StringReader(new String(entertainments)),',','"',1);

            for (Object o : csvReader.readAll()) {
                String[] line = (String[])o;

                entertainment = ticketBookingDAO.findEntertainmentByExternalId(line[0]);
                if (entertainment == null) {
                    entertainment = new Entertainment(line[0], line[1], line[3], line[2]);
                    createEntertainment(entertainment);
                    report.put("entertainment",report.get("entertainment") + 1);
                }

                event = ticketBookingDAO.findEventByExternalId(line[4]);
                if (event == null) {
                    event = new Event(line[4], DateUtils.parseDateTime(line[5]), line[6]);
                    event.setEntertainment(entertainment);
                    entertainment.getEvents().add(event);
                    report.put("event",report.get("event") + 1);
                }

                placeCategory = ticketBookingDAO.findPlaceCategoryByExternalId(line[7]);
                if (placeCategory == null){
                    placeCategory = new PlaceCategory(line[7], line[8], line[9]);
                    event.getPlaceCategories().add(placeCategory);
                    report.put("placeCategory",report.get("placeCategory") + 1);
                }

                fare = ticketBookingDAO.findFareByExternalId(line[10]);
                if (fare == null) {
                    fare = new Fare(line[10], line[11], line[12], line[13]);
                    placeCategory.getFares().add(fare);
                    report.put("fare",report.get("fare") + 1);
                }

                updateEntertainment(entertainment);
                HibernateUtil.getSession().flush();
            }
       } catch (IOException e) {
           logger.error(e.getMessage());
           HibernateUtil.getSession().getTransaction().rollback();
           throw  new CvqTicketBookingException("ticketBooking.error.importEntertainmentsFailed");
       } catch (NumberFormatException e){
           logger.error(e.getMessage());
           HibernateUtil.getSession().getTransaction().rollback();
           throw  new CvqTicketBookingException("ticketBooking.error.importEntertainmentsFailed");
       } catch (ParseException e) {
           logger.error(e.getMessage());
           HibernateUtil.getSession().getTransaction().rollback();
           throw  new CvqTicketBookingException("ticketBooking.error.importEntertainmentsFailed");
        } catch (CvqException e) {
            logger.error(e.getMessage());
            HibernateUtil.getSession().getTransaction().rollback();
            throw  new CvqTicketBookingException("ticketBooking.error.importEntertainmentsFailed");
        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage());
            HibernateUtil.getSession().getTransaction().rollback();
            throw  new CvqTicketBookingException("ticketBooking.error.importEntertainmentsFailed");
        }
        return report;
    }

    @Override
    public boolean isSubscriber(String number, String firstName, String lastName) 
            throws CvqTicketBookingException {
        Subscriber s = ticketBookingDAO.findSubscriberByNumber(number);
        if (s != null)
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName))
                return true;
        return false;
    }

    @Override
    public Map<String,Integer> importSubscribers(byte[] subscribers) throws CvqTicketBookingException {
        Subscriber subscriber;
        Map<String,Integer> report = new HashMap<String,Integer>();
        report.put("subscriber", 0);
        try {
            CSVReader csvReader = new CSVReader(new StringReader(new String(subscribers)),',','"');
            for (Object o : csvReader.readAll()) {
                String[] line = (String[])o;
                subscriber = ticketBookingDAO.findSubscriberByNumber(line[0]);
                if (subscriber == null) {
                    subscriber = new Subscriber(line[0],line[1],line[2],line[3],line[4]);
                    genericDAO.create(subscriber);
                    report.put("subscriber",report.get("subscriber") + 1);
                }
                HibernateUtil.getSession().flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            HibernateUtil.getSession().getTransaction().rollback();
            throw  new CvqTicketBookingException("ticketBooking.error.importSubscribersFailed");
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
            HibernateUtil.getSession().getTransaction().rollback();
            throw  new CvqTicketBookingException("ticketBooking.error.importSubscribersFailed");
        } catch (IndexOutOfBoundsException e) {
             logger.error(e.getMessage());
             HibernateUtil.getSession().getTransaction().rollback();
             throw  new CvqTicketBookingException("ticketBooking.error.importSubscribersFailed");
        }
        return report;
    }

    @Override
     public Integer getSubscriberLimit(String number, FareType fareType) {
         Subscriber s = ticketBookingDAO.findSubscriberByNumber(number);
         if (s != null)
             return s.getLimits().get(fareType);
         return null;
     }

    // FIXME : dirty implementation for global configuration parameter based on localReferential
    @Override
    public int getMaxCartSize() {
        int cartSize = -1;
        try {
            LocalReferentialType lrt =
                localReferentialService.getLocalReferentialType("Ticket Booking", "ticketBookingConfiguration");
            LocalReferentialEntry lre = lrt.getEntryByKey("Taille-du-panier");
            cartSize =  new Integer(lre.getMessage());
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        } catch (CvqException e) {
            logger.error(e.getMessage());
        }
        return cartSize;
    }

    // FIXME : dirty implementation for mandatory fares "Plein" et "Réduit"
    private void initMandadoryFares() {
        for (FareType fare : FareType.allFareTypes) {
            try {
                localReferentialService.removeLocalReferentialEntry("Ticket Booking", "rateTypes", fare.toString());
            } catch (CvqException e) {
                // the rate types probably weren't configured ; let's try and create them
            }
            try {
                localReferentialService.addLocalReferentialEntry("Ticket Booking", "rateTypes", null, fare.toString(), fare.getLabel(), null);
            } catch (CvqException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void addLocalAuthority(String localAuthorityName) {
        initMandadoryFares();
    }

    @Override
    public void removeLocalAuthority(String localAuthorityName) {}

    public void setTicketBookingDAO(ITicketBookingDAO ticketBookingDAO) {
        this.ticketBookingDAO = ticketBookingDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public void setLocalReferentialService(ILocalReferentialService localReferentialService) {
        this.localReferentialService = localReferentialService;
    }

}
