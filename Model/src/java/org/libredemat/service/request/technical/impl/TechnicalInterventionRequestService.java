package org.libredemat.service.request.technical.impl;

import java.util.Arrays;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.technical.TechnicalInterventionRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.condition.EqualityListChecker;
import org.libredemat.service.request.impl.RequestService;


public class TechnicalInterventionRequestService extends RequestService {

    @Override
    public void init() {
        TechnicalInterventionRequest.conditions.put("interventionType",
            new EqualityListChecker(Arrays.asList("other", "Other", "Autre", "autre")));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof TechnicalInterventionRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        TechnicalInterventionRequest request = new TechnicalInterventionRequest();
        if (SecurityContext.getCurrentEcitizen() != null) {
            request.setInterventionPlace(SecurityContext.getCurrentEcitizen().getHomeFolder().getAddress().clone());
        }
        return request;
    }
}
