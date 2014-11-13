package org.libredemat.service.request.school.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.RecreationActivityPolyRegistrationRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

public final class RecreationActivityPolyRegistrationRequestService extends RequestService {

    private static Logger logger = Logger.getLogger(RecreationActivityPolyRegistrationRequestService.class);

    @Override
    public void onRequestCompleted(final Request request)
            throws CvqModelException {
        // check recreation center association has been done before completing request
        RecreationActivityPolyRegistrationRequest raprr = (RecreationActivityPolyRegistrationRequest) request;
        if(raprr.getRecreationPolyCenter() == null)
            throw new CvqModelException("rarr.property.recreationCenter.validationError");
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof RecreationActivityPolyRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        RecreationActivityPolyRegistrationRequest raprr = new  RecreationActivityPolyRegistrationRequest();
        if (SecurityContext.getCurrentEcitizen() != null)
            raprr.setUrgencyPolyPhone(SecurityContext.getCurrentEcitizen().getOfficePhone());
        return raprr;
    }
}
