package org.libredemat.service.request.urbanism.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.urbanism.ScrRequesterQualityType;
import org.libredemat.business.request.urbanism.SewerConnectionRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the sewer connection request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class SewerConnectionRequestService extends RequestService {

    @Override
    public void init() {
        SewerConnectionRequest.conditions.put("requesterQuality", new EqualityChecker(ScrRequesterQualityType.TENANT.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof SewerConnectionRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new SewerConnectionRequest();
    }
}
