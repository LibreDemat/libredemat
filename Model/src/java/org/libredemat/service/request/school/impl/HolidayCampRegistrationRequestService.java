package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.HolidayCampRegistrationRequest;
import org.libredemat.service.request.impl.RequestService;

public final class HolidayCampRegistrationRequestService extends RequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof HolidayCampRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new HolidayCampRegistrationRequest();
    }

}
