package org.libredemat.service.request.external;

import java.util.List;
import java.util.Set;

import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.service.request.annotation.IsRequest;
import org.libredemat.util.Critere;


public interface IRequestExternalActionService {

    Long addTrace(@IsRequest RequestExternalAction trace);

    List<RequestExternalAction> getTraces(Set<Critere> criteriaSet, String sort,
        String dir, int count, int offset);

    Long getTracesCount(Set<Critere> criteriaSet);

    List<RequestExternalAction> getLastTraces(Set<Critere> criteriaSet, String sort,
            String dir, int count, int offset);

    Long getLastTracesCount(Set<Critere> criteriaSet);

    List<Long> getRequestsWithoutExternalAction(Long requestTypeId, String externalServiceLabel);

    List<String> getKeys(Set<Critere> criterias);
    
    boolean isAcknowledged(@IsRequest Long requestId, String externalServiceLabel);
}
