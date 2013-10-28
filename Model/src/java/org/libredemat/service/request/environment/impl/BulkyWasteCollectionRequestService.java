package org.libredemat.service.request.environment.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.environment.BulkyWasteCollectionRequest;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.impl.RequestService;

public class BulkyWasteCollectionRequestService extends RequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof BulkyWasteCollectionRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        BulkyWasteCollectionRequest request = new BulkyWasteCollectionRequest();
        if (SecurityContext.getCurrentEcitizen() != null) {
            request.setCollectionAddress(SecurityContext.getCurrentEcitizen()
                .getHomeFolder().getAddress().clone());
        }
        return request;
    }

}
