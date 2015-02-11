package org.libredemat.service.payment;

import org.libredemat.business.payment.Payment;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.IXENoBrokerFindException;
import org.libredemat.exception.IXEPaymentAllReadyExistException;



/**
 * Request that needs to redirect citizen to payment provider page
 * just after form's filling must implement this interface
 */
public interface IRequestPaymentService {

    Payment buildPayment(Request request, Double amount) throws CvqException, IXEPaymentAllReadyExistException, IXENoBrokerFindException;

    boolean onPaymentValidated(Request request, String paymentReference) throws CvqException;

    boolean onPaymentRefused(Request request) throws CvqException;

    boolean onPaymentCancelled(Request request) throws CvqException;

    Payment getPayment(Request request);

    void setPayment(Request request, Payment paiement);
}
