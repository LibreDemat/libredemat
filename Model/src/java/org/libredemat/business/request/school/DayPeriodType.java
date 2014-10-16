package org.libredemat.business.request.school;

/**
 * Generated class file, do not edit !
 */
public enum DayPeriodType {

    ALL_DAY("allDay"),
    HALF_DAY("halfDay");


    /**
     * only for backward use DayPeriodType.values() instead
     * @deprecated only for backward
     */
    @Deprecated 
    public static DayPeriodType[] allDayPeriodTypes = DayPeriodType.values();

    private String legacyLabel;

    private DayPeriodType(String legacyLabel){
        this.legacyLabel = legacyLabel;
    }

    public String getLegacyLabel() {
        return legacyLabel;
    }

    public static DayPeriodType getDefaultDayPeriodType() {
        return ALL_DAY;
    }

    /**
     * @deprecated use valueOf instead. Watchout! you must provid something of DayPeriodType.something
     * not the value of the name attribut.
     */
    public static DayPeriodType forString(final String enumAsString) {
        for (DayPeriodType value : values())
            if (value.toString().equals(enumAsString))
                return value;
        return getDefaultDayPeriodType();
    }
}
