package org.libredemat.business.users;

import org.libredemat.business.LibreDematEvent;

public class UserEvent extends LibreDematEvent {

    private static final long serialVersionUID = 1L;

    private UserAction action;

    public UserEvent(Object source, UserAction action) {
        super(source);
        this.action = action;
    }

    public UserAction getAction() {
        return action;
    }
}
