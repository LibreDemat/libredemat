package org.libredemat.business.request.permit;

/**
 * Generated class file, do not edit !
 */
public enum HeuresType {

    K0("K0"),
    K1("K1"),
    K2("K2"),
    K3("K3"),
    K4("K4"),
    K5("K5"),
    K6("K6"),
    K7("K7"),
    K8("K8"),
    K9("K9"),
    K10("K10"),
    K11("K11"),
    K12("K12"),
    K13("K13"),
    K14("K14"),
    K15("K15"),
    K16("K16"),
    K17("K17"),
    K18("K18"),
    K19("K19"),
    K20("K20"),
    K21("K21"),
    K22("K22"),
    K23("K23");


    /**
     * only for backward use HeuresType.values() instead
     * @deprecated only for backward
     */
    @Deprecated 
    public static HeuresType[] allHeuresTypes = HeuresType.values();

    private String legacyLabel;

    private HeuresType(String legacyLabel){
        this.legacyLabel = legacyLabel;
    }

    public String getLegacyLabel() {
        return legacyLabel;
    }

    public static HeuresType getDefaultHeuresType() {
        return K9;
    }

    /**
     * @deprecated use valueOf instead. Watchout! you must provid something of HeuresType.something
     * not the value of the name attribut.
     */
    public static HeuresType forString(final String enumAsString) {
        for (HeuresType value : values())
            if (value.toString().equals(enumAsString))
                return value;
        return getDefaultHeuresType();
    }
}
