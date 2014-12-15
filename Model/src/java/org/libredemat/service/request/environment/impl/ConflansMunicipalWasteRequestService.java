package org.libredemat.service.request.environment.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.environment.CmwrProfilDemandeurType;
import org.libredemat.business.request.environment.ConflansMunicipalWasteRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class ConflansMunicipalWasteRequestService extends RequestService {

    @Override
    public void init() {
        ConflansMunicipalWasteRequest.conditions.put("profilDemandeur", new EqualityChecker(CmwrProfilDemandeurType.PARTICULIER.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof ConflansMunicipalWasteRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new ConflansMunicipalWasteRequest();
    }

}
