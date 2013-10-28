package org.libredemat.service.payment;

import org.libredemat.business.payment.Payment;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;

/**
 * Request that needs to redirect citizen to payment provider page
 * just after form's filling must implement this interface
 */
public interface IRequestPaymentService {

    Payment buildPayment(Request request) throws CvqException;

}
