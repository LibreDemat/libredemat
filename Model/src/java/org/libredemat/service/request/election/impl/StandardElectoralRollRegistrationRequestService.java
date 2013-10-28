package org.libredemat.service.request.election.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.election.SerrrNationaliteType;
import org.libredemat.business.request.election.SerrrSexeType;
import org.libredemat.business.request.election.SerrrSituationType;
import org.libredemat.business.request.election.SerrrTypeElectionType;
import org.libredemat.business.request.election.StandardElectoralRollRegistrationRequest;
import org.libredemat.business.users.SexType;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class StandardElectoralRollRegistrationRequestService extends RequestService {

    @Override
    public void init() {
        StandardElectoralRollRegistrationRequest.conditions.put("sexe",
                new EqualityChecker(SerrrSexeType.FEMININ.name()));

        StandardElectoralRollRegistrationRequest.conditions.put("nationalite",
            new EqualityChecker(SerrrNationaliteType.RESSORTISSANT_U_E.name()));

        StandardElectoralRollRegistrationRequest.conditions.put("typeElection",
                new EqualityChecker(SerrrTypeElectionType.ELECTION_EUROPEENNE.name()));

        StandardElectoralRollRegistrationRequest.conditions.put("situation",
                new EqualityChecker(SerrrSituationType.CHANGEMENT_COMMUNE.name()));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof StandardElectoralRollRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new StandardElectoralRollRegistrationRequest();
    }

}
