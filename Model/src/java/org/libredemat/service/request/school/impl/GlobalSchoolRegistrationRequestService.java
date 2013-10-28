package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.GlobalSchoolRegistrationRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public final class GlobalSchoolRegistrationRequestService extends RequestService {

    @Override
    public void init() {
        GlobalSchoolRegistrationRequest.conditions.put("estDerogation", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof GlobalSchoolRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new GlobalSchoolRegistrationRequest();
    }
}
