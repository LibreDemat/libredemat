package org.libredemat.service.request.social.impl;

import java.math.BigDecimal;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.social.SagrActiviteAssociation;
import org.libredemat.business.request.social.SagrFederationSportiveType;
import org.libredemat.business.request.social.SagrRoleAssociationType;
import org.libredemat.business.request.social.SagrSportPratiqueType;
import org.libredemat.business.request.social.SportsAssociationsGrantRequest;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;


public class SportsAssociationsGrantRequestService extends RequestService {

    @Override
    public void init() {
        SportsAssociationsGrantRequest.conditions.put("estAdresseCorrespondantPrincipal", new EqualityChecker("false"));
        SportsAssociationsGrantRequest.conditions.put("roleDemandeur", new EqualityChecker(SagrRoleAssociationType.PRESIDENT.name()));
        SportsAssociationsGrantRequest.conditions.put("sagrActiviteAssociation.sportPratique", new EqualityChecker(SagrSportPratiqueType.AUTRE.name()));
        SportsAssociationsGrantRequest.conditions.put("sagrActiviteAssociation.federationSportive", new EqualityChecker(SagrFederationSportiveType.AUTRE.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof SportsAssociationsGrantRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new SportsAssociationsGrantRequest();
    }

    @Override
    public void onRequestIssued(Request request) {
        SportsAssociationsGrantRequest sagr = (SportsAssociationsGrantRequest) request;
        BigDecimal totalSubvention = BigDecimal.ZERO;
        for (SagrActiviteAssociation as : sagr.getSagrActiviteAssociation()) {
            as.setTotalLicencieActivite(as.getNombreLicencieMajeurActivite() + as.getNombreLicencieMineurActivite());
            totalSubvention = totalSubvention.add(as.getSommeSolliciteeActivite());
        }
        sagr.setSubventionSolliciteConseilGeneral(totalSubvention);
    }

}
