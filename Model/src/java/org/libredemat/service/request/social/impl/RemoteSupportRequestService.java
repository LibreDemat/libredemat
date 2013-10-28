package org.libredemat.service.request.social.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.social.RemoteSupportRequest;
import org.libredemat.business.request.social.RsrContactKindType;
import org.libredemat.business.request.social.RsrRequestInformationRequestKindType;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the remote support request service.
 * 
 * @author Rafik Djedjig (rdj@zenexity.fr)
 */
public class RemoteSupportRequestService extends RequestService {

    @Override
    public void init() {
        RemoteSupportRequest.conditions.put("requestInformationRequestKind",
            new EqualityChecker(RsrRequestInformationRequestKindType.COUPLE.name()));
        RemoteSupportRequest.conditions.put("requestInformationEmergency",
            new EqualityChecker("true"));
        RemoteSupportRequest.conditions.put("contactKind", new EqualityChecker(RsrContactKindType.OTHER.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof RemoteSupportRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new RemoteSupportRequest();
    }
}
