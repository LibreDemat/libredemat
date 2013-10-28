package org.libredemat.business;

import org.springframework.context.ApplicationEvent;

public abstract class LibreDematEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public LibreDematEvent(Object source) {
        super(source);
    }
}
