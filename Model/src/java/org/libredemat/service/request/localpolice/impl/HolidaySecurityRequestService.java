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

        // Hack Inexine
        // frederic fabre - 20/07/2012
        // Suite cahier des charges pour Yerres :
        // YERRES - E-Administration Cahier des Charges N°2012-05 - Sécurité vacances.pdf
        // Ajout de nouveaux champs dans la demande
        HolidaySecurityRequest.conditions.put("isAnimalOwner",
                new EqualityChecker("true"));
        HolidaySecurityRequest.conditions.put("isSecurityCompany",
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
