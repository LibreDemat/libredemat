package org.libredemat.service.request.election.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.election.ElectoralMotiveType;
import org.libredemat.business.request.election.ElectoralRollRegistrationRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the electoral roll registration request service.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class ElectoralRollRegistrationRequestService extends RequestService {

    @Override
    public void init() {
        ElectoralRollRegistrationRequest.conditions.put("motive",
            new EqualityChecker(ElectoralMotiveType.DIRECT_CITY_CONTRIBUTION.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof ElectoralRollRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new ElectoralRollRegistrationRequest();
    }
}
