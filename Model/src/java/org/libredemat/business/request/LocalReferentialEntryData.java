package org.libredemat.business.request;

/**
 * Simple logic-less and read-only class holding the content of a local
 * referential type entry
 * @author julien
 */
public class LocalReferentialEntryData {
    /// Entry unique key
    protected String key;
    /// Human readable label for this entry
    protected String label;
    /// (Optional) Additionnal message
    protected String message;
    /*
     * Hack inexine to add external code field
     */
    protected String externalCode;
    /*
     * end of hack
     */

    public final String getKey() {
        return key;
    }
    
    public String getLabel() {
        return label;
    }

    public String getMessage() {
        return message;
    }
    
    // hack inexine
    public String getExternalCode() {
        return externalCode;
    }
    // end of hack
}
