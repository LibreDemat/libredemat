package org.libredemat.service.request.civil.impl;

import java.util.Arrays;

import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.civil.BirthCertificateFormatType;
import org.libredemat.business.request.civil.BirthDetailsRequest;
import org.libredemat.business.request.civil.BirthRequesterQualityType;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.condition.EqualityListChecker;
import org.libredemat.service.request.impl.RequestService;


/**
 * Implementation of the {@link IBirthDetailsRequestService birth details request service}.
 * 
 * @author bor@zenexity.fr
 */
public final class BirthDetailsRequestService extends RequestService {

    @Override
    public void init() {
        BirthDetailsRequest.conditions.put("requesterQuality", new EqualityChecker(BirthRequesterQualityType.OTHER.name()));
        BirthDetailsRequest.conditions.put("format",
            new EqualityListChecker(Arrays.asList(BirthCertificateFormatType.FULL_COPY.name(), BirthCertificateFormatType.EXTRACT_WITH_RELATIONSHIP.name())));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof BirthDetailsRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        BirthDetailsRequest request = new BirthDetailsRequest();
        // this test is here only because, on frontoffice homepage, requests are created
        // to display their translated labels, while the currentSite has already been reset by
        // the "after" in openSessionInViewFilter
        if (SecurityContext.getCurrentSite() != null) {
            LocalAuthority localAuthority = SecurityContext.getCurrentSite();
            request.setBirthCity(localAuthority.getDisplayTitle());
            request.setBirthPostalCode(localAuthority.getPostalCode().substring(0,2));
        }
        return request;
    }
}
