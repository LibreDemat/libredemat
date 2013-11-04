package org.libredemat.business.payment;

import org.libredemat.business.LibreDematEvent;

public class PaymentEvent extends LibreDematEvent {

    private static final long serialVersionUID = 1L;

    public static enum EVENT_TYPE { PAYMENT_VALIDATED, PAYMENT_REFUSED,
        PAYMENT_CANCELLED, PAYMENT_DELETED};
    
    private EVENT_TYPE event;
    private Payment payment;

    public PaymentEvent(Object source, EVENT_TYPE event, Payment payment) {
        super(source);
        this.event = event;
        this.payment = payment;
    }

    public EVENT_TYPE getEvent() {
        return this.event;
    }

    public Payment getPayment() {
        return payment;
    }
}
