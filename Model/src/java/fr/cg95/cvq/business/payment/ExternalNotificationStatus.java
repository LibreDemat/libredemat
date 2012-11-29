package fr.cg95.cvq.business.payment;

public enum ExternalNotificationStatus {

    ITEM_SENT("ItemSent"),
    ITEM_NOT_SENT("ItemNotSent"),
    ITEM_SENT_ERROR("ItemSentError");

    private String name;

    private ExternalNotificationStatus(String type) {
        this.name = type;
    }

    public static ExternalNotificationStatus forString(String enumAsString) {
        for (ExternalNotificationStatus type : values()) {
            if (type.toString().equals(enumAsString)) return type;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
