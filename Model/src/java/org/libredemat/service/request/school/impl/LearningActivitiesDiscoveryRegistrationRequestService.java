package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.LearningActivitiesDiscoveryRegistrationRequest;
import org.libredemat.service.request.impl.RequestService;

/**
 *
 * @author vsi@zenexity.fr
 */
public final class LearningActivitiesDiscoveryRegistrationRequestService extends RequestService {

    @Override
    public boolean accept(final Request request) {
        return request instanceof LearningActivitiesDiscoveryRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new LearningActivitiesDiscoveryRegistrationRequest();
    }
}
