package org.libredemat.service.request.leisure.culture.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.leisure.culture.LibraryRegistrationRequest;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the library registration request service.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class LibraryRegistrationRequestService extends RequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof LibraryRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new LibraryRegistrationRequest();
    }
}
