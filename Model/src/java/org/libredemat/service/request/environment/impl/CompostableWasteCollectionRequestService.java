package org.libredemat.service.request.environment.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.environment.CompostableWasteCollectionRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

public class CompostableWasteCollectionRequestService extends RequestService {

    public boolean accept(Request request) {
        return request instanceof CompostableWasteCollectionRequest;
    }

    public Request getSkeletonRequest() {
        CompostableWasteCollectionRequest request =
            new CompostableWasteCollectionRequest();
        if (SecurityContext.getCurrentEcitizen() != null) {
            request.setCollectionAddress(SecurityContext.getCurrentEcitizen()
                .getHomeFolder().getAddress().clone());
        }
        return request;
    }
}
