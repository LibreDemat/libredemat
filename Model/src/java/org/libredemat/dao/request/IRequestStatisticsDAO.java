package org.libredemat.dao.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.libredemat.business.request.RequestState;


/**
 *
 * @author bor@zenexity.fr
 */
public interface IRequestStatisticsDAO {

    Long countByQuality(final Date startDate, final Date endDate,
        final List<RequestState> resultingStates, final String qualityType,
        final List<Long> requestTypesId);

    Map<Long,Long> countByQualityAndType(final Date startDate, final Date endDate,
        final List<RequestState> resultingStates, final String qualityType,
        final List<Long> requestTypesId);

    Long countByResultingState(final String resultingState,
        final Date startDate, final Date endDate,
        final List<Long> requestTypesId);

    Map<Long, Long> countByType(final Date startDate, final Date endDate,
        final List<Long> requestTypeIds);

    Long countByPeriod(final Date startDate, final Date endDate,
        final List<Long> requestTypeIds);

    Date getOldestRequest();
}
