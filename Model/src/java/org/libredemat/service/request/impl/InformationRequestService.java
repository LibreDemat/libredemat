package org.libredemat.service.request.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.information.InformationRequest;

public class InformationRequestService extends RequestService{

    @Override
    public boolean accept(Request request) {
        return request instanceof InformationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new InformationRequest();
    }

}
