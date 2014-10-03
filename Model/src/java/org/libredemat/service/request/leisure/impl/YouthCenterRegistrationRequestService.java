package org.libredemat.service.request.leisure.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.leisure.YouthCenterRegistrationRequest;
import org.libredemat.service.request.impl.RequestService;
import org.libredemat.service.request.condition.EqualityChecker;

public class YouthCenterRegistrationRequestService extends RequestService {

    @Override
    public void init() {
        YouthCenterRegistrationRequest.conditions.put("isFirstRegistration", new EqualityChecker("false"));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof YouthCenterRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new YouthCenterRegistrationRequest();
    }
}
