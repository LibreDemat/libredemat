package org.libredemat.service.request;


import java.util.Date;
import java.util.Map;

import org.libredemat.business.request.RequestState;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.annotation.IsCategory;
import org.libredemat.service.request.annotation.IsRequestType;

/**
 * High level service interface to get requests related statistics.
 *
 * @author bor@zenexity.fr
 */
public interface IRequestStatisticsService {

    String QUALITY_TYPE_OK = "qualityTypeOk";
    String QUALITY_TYPE_ORANGE = "qualityTypeOrange";
    String QUALITY_TYPE_RED = "qualityTypeRed";

    /**
     * Supported intervals for request types stats display by period.
     *
     * They are automatically computed from start and end dates.
     *
     * @see #getTypeStatsIntervalType(java.util.Date, java.util.Date) 
     */
    enum TypeStatsIntervalType {
        HOUR, DAY, TWO_DAYS, WEEK, MONTH, YEAR
    }
    
    /**
     * Get quality of service statistics about requests.
     *
     * @param startDate interval start date
     * @param endDate interval end date
     * @param requestTypeId to restrict statistics to a specific request type
     * @param categoryId to restrict statistics to a specific category
     *
     * @return a map of quality indicator and counts
     * @see {@link #QUALITY_TYPE_OK}, {@link #QUALITY_TYPE_ORANGE}, {@link #QUALITY_TYPE_RED}
     */
    Map<String, Long> getQualityStats(final Date startDate, final Date endDate,
        @IsRequestType final Long requestTypeId, @IsCategory final Long categoryId);

    /**
     * The same as {@link #getQualityStats} but with results grouped by request type id.
     *
     * @param startDate interval start date
     * @param endDate interval end date
     * @param requestTypeId to restrict statistics to a specific request type
     * @param categoryId to restrict statistics to a specific category
     */
    Map<Long, Map<String, Long>> getQualityStatsByType(final Date startDate, final Date endDate,
        @IsRequestType final Long requestTypeId, @IsCategory final Long categoryId) throws CvqException;

    Map<RequestState, Long> getStateStats(final Date startDate, final Date endDate,
        @IsRequestType final Long requestTypeId, @IsCategory final Long categoryId);

    Map<Long, Long> getTypeStats(final Date startDate, final Date endDate,
        @IsRequestType final Long requestTypeId, @IsCategory final Long categoryId);

    Map<Date, Long> getTypeStatsByPeriod(final Date startDate, final Date endDate,
        @IsRequestType final Long requestTypeId, @IsCategory final Long categoryId);

    TypeStatsIntervalType getTypeStatsIntervalType(final Date startDate, final Date endDate);
}
