package fr.cg95.cvq.business.users;

public enum UserState {

    /* Warn : the order off declaration is important! */
    PENDING("Pending"),
    NEW("New"),
    VALID("Valid"),
    MODIFIED("Modified"),
    INVALID("Invalid"),
    ARCHIVED("Archived");

    private String name;

    private UserState(final String state) {
        this.name = state;
    }

    /**
     * @deprecated only for backward, use values() instead
     */
    public static final UserState[] allUserStates = UserState.values();

    public static final UserState[] activeStates = { NEW, VALID, MODIFIED, INVALID };

    /**
     * States shown in user search
     */
    public static final UserState[] filtersStates = { NEW, VALID, MODIFIED, INVALID, ARCHIVED };

    public static UserState forString(final String enumAsString) {
        for (UserState state : values()) {
            if (state.toString().equals(enumAsString)) return state;
        }
        return NEW;
    }

    @Override
    public String toString() {
        return name;
    }
}
