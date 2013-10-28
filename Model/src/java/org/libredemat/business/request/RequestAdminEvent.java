package org.libredemat.business.request;

import org.libredemat.business.LibreDematEvent;

public class RequestAdminEvent extends LibreDematEvent {

    private static final long serialVersionUID = 1L;

    private RequestAdminAction action;

    public RequestAdminEvent(Object source, RequestAdminAction action) {
        super(source);
        this.action = action;
    }

    public RequestAdminAction getAction() {
        return action;
    }

    public void setAction(RequestAdminAction action) {
        this.action = action;
    }
}
