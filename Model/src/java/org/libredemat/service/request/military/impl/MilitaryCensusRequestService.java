package org.libredemat.service.request.military.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.military.MilitaryCensusRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class MilitaryCensusRequestService extends RequestService {

    @Override
    public void init() {
        MilitaryCensusRequest.conditions.put("prefectPupil", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(Request request) {
        return (request instanceof MilitaryCensusRequest);
    }

    @Override
    public Request getSkeletonRequest() {
        return new MilitaryCensusRequest();
    }
}
