package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.PessacAnimationRequest;
import org.libredemat.service.request.impl.RequestService;

public class PessacAnimationRequestService extends RequestService {

    @Override
    public boolean accept(final Request request) {
        return request instanceof PessacAnimationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new PessacAnimationRequest();
    }
}
