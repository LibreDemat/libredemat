package org.libredemat.service.request.urbanism.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.urbanism.AlignmentNumberingConnectionRequest;
import org.libredemat.business.request.urbanism.AncrRequesterQualityType;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * @author jsb@zenexity.fr
 */
public class AlignmentNumberingConnectionRequestService extends RequestService {

    @Override
    public void init() {
        AlignmentNumberingConnectionRequest.conditions.put("isAccountAddress",
            new EqualityChecker("true"));
        AlignmentNumberingConnectionRequest.conditions.put("requesterQuality",
            new EqualityChecker(AncrRequesterQualityType.OWNER.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof AlignmentNumberingConnectionRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new AlignmentNumberingConnectionRequest();
    }
}
