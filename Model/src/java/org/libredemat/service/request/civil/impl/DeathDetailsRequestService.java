package org.libredemat.service.request.civil.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.civil.DeathDetailsRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the {@link IDeathDetailsRequestService death details request service}.
 * 
 * @author bor@zenexity.fr
 */
public final class DeathDetailsRequestService extends RequestService {
    
    @Override
	public boolean accept(Request request) {
		return request instanceof DeathDetailsRequest;
	}

    @Override
	public Request getSkeletonRequest() {
        DeathDetailsRequest request = new DeathDetailsRequest();
        //FIXME see Birth
        if (SecurityContext.getCurrentSite() != null) {
            request.setDeathCity(SecurityContext.getCurrentSite().getDisplayTitle());
            request.setDeathPostalCode(SecurityContext.getCurrentSite().getPostalCode().substring(0,2));
        }
        return request;
    }
}
