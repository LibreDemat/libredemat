package org.libredemat.service.request.school.impl;

import org.apache.log4j.Logger;
import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.school.PerischoolActivityRegistrationRequest;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;


/**
 * Implementation of the perischool activity registration request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class PerischoolActivityRegistrationRequestService extends RequestService {

    private static Logger logger =
        Logger.getLogger(PerischoolActivityRegistrationRequestService.class);

    @Override
    public void onRequestCompleted(final Request request)
        throws CvqModelException {
    
        PerischoolActivityRegistrationRequest parr = (PerischoolActivityRegistrationRequest) request;
    
        // ensure school information has been filled
        if (parr.getSchool() == null) {
            logger.error("validate() registration has not been associated to a school !");
            throw new CvqModelException("parr.property.school.validationError");
        }
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof PerischoolActivityRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        PerischoolActivityRegistrationRequest request =
            new PerischoolActivityRegistrationRequest();
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
                ((PerischoolActivityRegistrationRequest)event.getRequest()).setSection(null);
            }
        }
    }
}
