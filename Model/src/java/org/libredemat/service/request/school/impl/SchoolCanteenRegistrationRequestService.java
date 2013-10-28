package org.libredemat.service.request.school.impl;

import org.apache.log4j.Logger;
import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.authority.School;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.school.SchoolCanteenRegistrationRequest;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;


/**
 * Implementation of the school canteen registration request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class SchoolCanteenRegistrationRequestService extends RequestService {

    private static Logger logger = Logger.getLogger(SchoolCanteenRegistrationRequestService.class);


    @Override
    public void init() {
        SchoolCanteenRegistrationRequest.conditions.put("foodAllergy", new EqualityChecker("true"));
    }

    @Override
    public void onRequestIssued(final Request request) {

        SchoolCanteenRegistrationRequest scrr = (SchoolCanteenRegistrationRequest) request;
        School school = scrr.getSchool();
        if (school != null) {
            School syncSchool = (School) genericDAO.findById(School.class, school.getId());
            scrr.setSchool(syncSchool);
        }
    }

    @Override
    public void onRequestCompleted(final Request request)
        throws CvqModelException {
        
        SchoolCanteenRegistrationRequest scrr = (SchoolCanteenRegistrationRequest) request;
        
        // ensure school information has been filled
        if (scrr.getSchool() == null) {
            logger.error("validate() registration has not been associated to a school !");
            throw new CvqModelException("scrr.property.school.validationError");
        }
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof SchoolCanteenRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        SchoolCanteenRegistrationRequest request =
            new SchoolCanteenRegistrationRequest();
        if (SecurityContext.getCurrentEcitizen() != null)
            request.setUrgencyPhone(SecurityContext.getCurrentEcitizen().getOfficePhone());
        return request;
    }

    @Override
    public void onApplicationEvent(LibreDematEvent e) {
        if (e instanceof RequestEvent) {
            RequestEvent event = (RequestEvent)e;
            if (RequestEvent.EVENT_TYPE.REQUEST_CLONED.equals(event.getEvent())
                && accept(event.getRequest())) {
                ((SchoolCanteenRegistrationRequest)event.getRequest()).setSection(null);
            }
        }
    }
}
