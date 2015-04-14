package org.libredemat.service.request;

import java.util.Set;

import org.libredemat.business.request.LocalReferentialEntryData;
import org.libredemat.business.request.LocalReferentialType;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.annotation.IsRequestType;


public interface ILocalReferentialService {

    /**
     * Get a list of all local referential type names belonging to a given request type.
     * Warning: Any modification on the local referential types will be lost. Use the dedicated methods of this service for that.
     */
    Set<LocalReferentialType> getLocalReferentialTypes(final String requestTypeLabel)
            throws CvqException;

    /**
     * @param requestTypeLabel
     * @param typeName
     * @return
     * @throws CvqException
     * Warning: Any modification on the local referential type will be lost. Use the dedicated methods of this service for that.
     */
    LocalReferentialType getLocalReferentialType(final String requestTypeLabel, final String typeName) throws CvqException;

    /**
     * Sounds like this method name is too long…
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param typeName Name of the local referential type
     * @return true if the local referential type allows multiple choices
     * @throws CvqException 
     */
    public boolean isLocalReferentialTypeAllowingMultipleChoices(final String requestTypeLabel, final String typeName) throws CvqException;
    
    public void setLocalReferentialTypeAllowingMultipleChoices(final String requestTypeLabel, final String typeName, boolean multiple) throws CvqException;

    /**
     * 
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param typeName Name of the local referential type
     * @param key
     * @return
     * @throws CvqException 
     */
    public LocalReferentialEntryData getLocalReferentialEntry(String requestTypeLabel, String typeName, String key) throws CvqException;
    
    /**
     * 
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param typeName Name of the local referential type
     * @param parentKey
     * @param label
     * @param message
     * @returns The key of the added entry
     * @throws CvqException 
     */
    public String addLocalReferentialEntry(String requestTypeLabel, String typeName, String parentKey, String label, String message, String externalCode) throws CvqException;
    public String addLocalReferentialEntry(String requestTypeLabel, String typeName, String parentKey, String key, String label, String message, String externalCode) throws CvqException;

    /**
     * 
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param typeName Name of the local referential type
     * @param entryKey
     * @throws CvqException 
     */
    public void removeLocalReferentialEntry(String requestTypeLabel, String typeName, String entryKey) throws CvqException;

    /**
     * 
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param typeName Name of the local referential type
     * @param key
     * @param label
     * @param message
     * @throws CvqException 
     */
    public void editLocalReferentialEntry(String requestTypeLabel, String typeName, String key, String label, String message, String externalCode) throws CvqException;

    /**
     * Return whether local referential is configured for the given request type.
     * 
     * @return false if at least one localReferentialType has null or empty entries. 
     *  Otherwise return true.
     */
    public boolean isLocalReferentialConfigured(final String requestTypeLabel) throws CvqException;

    /**
     * @param requestTypeLabel Label of the request type the local referential type belongs to
     * @param newLrt New local referential type
     * @throws CvqException
     */
    public void saveLocalReferentialType(@IsRequestType final String requestTypeLabel, LocalReferentialType newLrt)
            throws CvqException;
}
