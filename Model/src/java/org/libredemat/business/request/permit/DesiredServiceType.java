package org.libredemat.business.request.permit;

/**
 * Generated class file, do not edit !
 */
public enum DesiredServiceType {

    PARKING_PERMIT_FOR_WORK("ParkingPermitForWork"),
    EXISTING_LICENSE_EXTENSION("ExistingLicenseExtension");


    /**
     * only for backward use DesiredServiceType.values() instead
     * @deprecated only for backward
     */
    @Deprecated 
    public static DesiredServiceType[] allDesiredServiceTypes = DesiredServiceType.values();

    private String legacyLabel;

    private DesiredServiceType(String legacyLabel){
        this.legacyLabel = legacyLabel;
    }

    public String getLegacyLabel() {
        return legacyLabel;
    }

    public static DesiredServiceType getDefaultDesiredServiceType() {
        return PARKING_PERMIT_FOR_WORK;
    }

    /**
     * @deprecated use valueOf instead. Watchout! you must provid something of DesiredServiceType.something
     * not the value of the name attribut.
     */
    public static DesiredServiceType forString(final String enumAsString) {
        for (DesiredServiceType value : values())
            if (value.toString().equals(enumAsString))
                return value;
        return getDefaultDesiredServiceType();
    }
}
