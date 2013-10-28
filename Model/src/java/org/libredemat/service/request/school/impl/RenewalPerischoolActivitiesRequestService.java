package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.RenewalPerischoolActivitiesRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class RenewalPerischoolActivitiesRequestService extends RequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof RenewalPerischoolActivitiesRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new RenewalPerischoolActivitiesRequest();
    }

}
