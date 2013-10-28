package org.libredemat.service.request.urbanism.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.urbanism.AcrRequesterQualityType;
import org.libredemat.business.request.urbanism.AlignmentCertificateRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the alignment certificate request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class AlignmentCertificateRequestService extends RequestService {

    @Override
    public void init() {
        AlignmentCertificateRequest.conditions.put("requesterQuality", new EqualityChecker(AcrRequesterQualityType.TENANT.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof AlignmentCertificateRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new AlignmentCertificateRequest();
    }
}
