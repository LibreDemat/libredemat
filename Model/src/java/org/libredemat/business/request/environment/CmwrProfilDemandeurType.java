package org.libredemat.business.request.environment;

/**
 * Generated class file, do not edit !
 */
public enum CmwrProfilDemandeurType {

    PARTICULIER("Particulier"),
    BAILLEUR("Bailleur"),
    SYNDIC("Syndic");


    /**
     * only for backward use CmwrProfilDemandeurType.values() instead
     * @deprecated only for backward
     */
    @Deprecated 
    public static CmwrProfilDemandeurType[] allCmwrProfilDemandeurTypes = CmwrProfilDemandeurType.values();

    private String legacyLabel;

    private CmwrProfilDemandeurType(String legacyLabel){
        this.legacyLabel = legacyLabel;
    }

    public String getLegacyLabel() {
        return legacyLabel;
    }

    public static CmwrProfilDemandeurType getDefaultCmwrProfilDemandeurType() {
        return PARTICULIER;
    }

    /**
     * @deprecated use valueOf instead. Watchout! you must provid something of CmwrProfilDemandeurType.something
     * not the value of the name attribut.
     */
    public static CmwrProfilDemandeurType forString(final String enumAsString) {
        for (CmwrProfilDemandeurType value : values())
            if (value.toString().equals(enumAsString))
                return value;
        return getDefaultCmwrProfilDemandeurType();
    }
}
