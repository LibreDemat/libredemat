package org.libredemat.service.request.school.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.HomeEmergencyRegistrationRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

/**
 *
 * @author vsi@zenexity.fr
 */
public final class HomeEmergencyRegistrationRequestService extends RequestService {

    @Override
    public boolean accept(final Request request) {
        return request instanceof HomeEmergencyRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        HomeEmergencyRegistrationRequest herr = new HomeEmergencyRegistrationRequest();
        if (SecurityContext.getCurrentEcitizen() != null) {
            if (SecurityContext.getCurrentEcitizen().getMobilePhone() != null)
                herr.setTelephone(SecurityContext.getCurrentEcitizen().getMobilePhone());
            else if (SecurityContext.getCurrentEcitizen().getHomePhone() != null)
                herr.setTelephone(SecurityContext.getCurrentEcitizen().getHomePhone());
            else
                herr.setTelephone(SecurityContext.getCurrentEcitizen().getOfficePhone());
        }
        return herr;
    }
}
