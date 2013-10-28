package org.libredemat.service.request.school.impl;

import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.school.SchoolRegistrationRequest;
import org.libredemat.business.users.SectionType;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the school registration request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class SchoolRegistrationRequestService extends RequestService {

    @Override
    public void onRequestCompleted(final Request request)
        throws CvqModelException {

        // check school association has been done before validating request
        SchoolRegistrationRequest srr = (SchoolRegistrationRequest) request;
        if (srr.getSchool() == null)
            throw new CvqModelException("srr.property.school.validationError");
        if (srr.getSection().equals(SectionType.UNKNOWN))
            throw new CvqModelException("srr.property.section.validationError");
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof SchoolRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        SchoolRegistrationRequest request =
            new SchoolRegistrationRequest();
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
                ((SchoolRegistrationRequest)event.getRequest()).setSection(null);
            }
        }
    }
}
