package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.RecreationActivityRegistrationRequest;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the recreation activity registration request service.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class RecreationActivityRegistrationRequestService extends RequestService {

    @Override
    public void onRequestCompleted(final Request request)
        throws CvqModelException {

        // check recreation center association has been done before validating request
        RecreationActivityRegistrationRequest rarr = (RecreationActivityRegistrationRequest) request;
        if (rarr.getRecreationCenter() == null)
            throw new CvqModelException("rarr.property.recreationCenter.validationError");
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof RecreationActivityRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        RecreationActivityRegistrationRequest request =
            new RecreationActivityRegistrationRequest();
        if (SecurityContext.getCurrentEcitizen() != null)
            request.setUrgencyPhone(SecurityContext.getCurrentEcitizen().getOfficePhone());
        return request;
    }
}
