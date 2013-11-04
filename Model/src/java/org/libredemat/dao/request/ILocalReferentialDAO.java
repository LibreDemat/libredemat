package org.libredemat.dao.request;


import java.util.Set;

import org.libredemat.business.request.LocalReferentialType;
import org.libredemat.exception.CvqException;

/**
 * Handles access to local referentials.
 * @author julien
 */
public interface ILocalReferentialDAO {
    
    /**
     * Find the local referentials associated to a given request type label
     * @param requestTypeLabel
     * @return The list of local referentials or null if the request type does not have a local referential
     */
    Set<LocalReferentialType> listByRequestType(String requestTypeLabel);
    
    /**
     * @param requestTypeLabel
     * @param typeName
     * @return The local referential named typeName for the request type labelled requestTypeLabel, or null if not found
     */
    LocalReferentialType getByRequestTypeAndName(String requestTypeLabel, String typeName);
    
    /**
     * Try to save a given local referential of a request type.
     * @param requestTypeLabel
     * @param lrt 
     */
    void save(String requestTypeLabel, LocalReferentialType lrt) throws CvqException;
}
