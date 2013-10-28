package org.libredemat.service.request.leisure.music.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.leisure.music.MusicSchoolRegistrationRequest;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the music school registration request service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class MusicSchoolRegistrationRequestService extends RequestService {

    @Override
    public boolean accept(Request request) {
        return request instanceof MusicSchoolRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new MusicSchoolRegistrationRequest();
    }
}
