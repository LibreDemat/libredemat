package org.libredemat.service.request.school.impl;

import java.util.Arrays;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.AutorisationType;
import org.libredemat.business.request.school.SchoolTransportRegistrationRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.condition.EqualityListChecker;
import org.libredemat.service.request.impl.RequestService;

public class SchoolTransportRegistrationRequestService extends RequestService{

    @Override
    public void init() {
        SchoolTransportRegistrationRequest.conditions.put("estMaternelleElementaireAutorisations", new EqualityChecker("true"));
        SchoolTransportRegistrationRequest.conditions.put("autorisation", new EqualityListChecker(Arrays.asList(
            "autoriseTiers=" + AutorisationType.AVEC_TIERS.name(),
            "autoriseFrereOuSoeur=" + AutorisationType.AVEC_FRERE_SOEUR)));
    }

    @Override
    public boolean accept(final Request request) {
        return request instanceof SchoolTransportRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new SchoolTransportRegistrationRequest();
    }

}
