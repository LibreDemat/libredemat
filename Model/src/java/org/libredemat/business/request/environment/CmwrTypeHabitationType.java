package org.libredemat.business.request.environment;

/**
 * Generated class file, do not edit !
 */
public enum CmwrTypeHabitationType {

    PAVILLON("Pavillon"),
    COMMERCE("Commerce"),
    IMMEUBLE("Immeuble"),
    INDUSTRIEL("Industriel");


    /**
     * only for backward use CmwrTypeHabitationType.values() instead
     * @deprecated only for backward
     */
    @Deprecated 
    public static CmwrTypeHabitationType[] allCmwrTypeHabitationTypes = CmwrTypeHabitationType.values();

    private String legacyLabel;

    private CmwrTypeHabitationType(String legacyLabel){
        this.legacyLabel = legacyLabel;
    }

    public String getLegacyLabel() {
        return legacyLabel;
    }

    public static CmwrTypeHabitationType getDefaultCmwrTypeHabitationType() {
        return PAVILLON;
    }

    /**
     * @deprecated use valueOf instead. Watchout! you must provid something of CmwrTypeHabitationType.something
     * not the value of the name attribut.
     */
    public static CmwrTypeHabitationType forString(final String enumAsString) {
        for (CmwrTypeHabitationType value : values())
            if (value.toString().equals(enumAsString))
                return value;
        return getDefaultCmwrTypeHabitationType();
    }
}
