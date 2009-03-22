package fr.cg95.cvq.service.request.technical.impl;

import fr.cg95.cvq.business.request.Request;
import fr.cg95.cvq.business.request.technical.TechnicalInterventionRequest;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.service.request.impl.RequestService;
import fr.cg95.cvq.service.request.technical.ITechnicalInterventionRequestService;

public class TechnicalInterventionRequestService extends RequestService 
    implements ITechnicalInterventionRequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof TechnicalInterventionRequest;
    }

    @Override
    public Request getSkeletonRequest() throws CvqException {
        return new TechnicalInterventionRequest();
    }
}
