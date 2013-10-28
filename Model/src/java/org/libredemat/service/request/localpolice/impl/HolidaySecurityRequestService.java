package org.libredemat.service.request.localpolice.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.localpolice.HolidaySecurityRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class HolidaySecurityRequestService extends RequestService {

    @Override
    public void init() {
        HolidaySecurityRequest.conditions.put("otherContact",
                new EqualityChecker("true"));
    }
    
    @Override
    public boolean accept(Request request) {
        return request instanceof HolidaySecurityRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new HolidaySecurityRequest();
    }
}
