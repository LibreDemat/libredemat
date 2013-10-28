package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.LeisureCenterRegistrationRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the leisure center registration request service.
 * 
 * @author vsi@zenexity.com
 */
public class LeisureCenterRegistrationRequestService extends RequestService {

    @Override
    public void init() {
        LeisureCenterRegistrationRequest.conditions.put("estDerogation", new EqualityChecker("true"));
        LeisureCenterRegistrationRequest.conditions.put("estTransport", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof LeisureCenterRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new LeisureCenterRegistrationRequest();
    }

}
