package org.libredemat.service.request.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentEvent;
import org.libredemat.business.payment.PaymentState;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestState;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.IRequestService;
import org.libredemat.service.request.IRequestServiceRegistry;
import org.libredemat.service.request.IRequestWorkflowService;
import org.springframework.context.ApplicationListener;


public class RequestPaymentsListener implements ApplicationListener<PaymentEvent> {

    private static Logger logger = Logger.getLogger(RequestPaymentsListener.class);
    
    private IRequestDAO requestDAO;

    private IRequestServiceRegistry requestServiceRegistry;
    private IRequestWorkflowService requestWorkflowService;

    /**
     * Called by payment service on the reception of a payment operation status.
     *
     * If payment is successful, performs the following :
     * <ul>
     *  <li>Notify service associated to request type</li>
     *  <li>Notify external services</li>
     * </ul>
     */
    private final void notifyPaymentResult(final Payment payment)
        throws CvqException {

        // for each different request found in purchased items list, notify the associated
        // service of payment result status
        Set<Long> requests = new HashSet<Long>();
        Set<PurchaseItem> purchaseItems = payment.getPurchaseItems();
        for (PurchaseItem purchaseItem : purchaseItems) {
            // if purchase item came from us, notify the corresponding service
            if (purchaseItem instanceof InternalInvoiceItem) {
                InternalInvoiceItem internalInvoiceItem = (InternalInvoiceItem) purchaseItem;
                if (internalInvoiceItem.getKeyOwner().equals("libredemat"))
                    requests.add(Long.valueOf(internalInvoiceItem.getKey()));
            }
        }

        if (!requests.isEmpty()) {
            for (Long requestId : requests) {
                Request request = requestDAO.findById(requestId);
                IRequestService requestService = 
                    requestServiceRegistry.getRequestService(request);
                if (payment.getState().equals(PaymentState.VALIDATED)) {
                    if (requestService.onPaymentValidated(request, payment.getBankReference())) {
                        if (request.getState().equals(RequestState.PENDING))
                            requestWorkflowService.updateRequestState(request.getId(), 
                                    RequestState.COMPLETE, null);
                        requestWorkflowService.updateRequestState(request.getId(), 
                                RequestState.VALIDATED, "request.message.paymentValidated");
                    }
                } else if (payment.getState().equals(PaymentState.CANCELLED)) {
                    if (requestService.onPaymentCancelled(request)) {
                        requestWorkflowService.updateRequestState(request.getId(), 
                                RequestState.CANCELLED, "request.message.paymentCancelled");
                    }
                } else if (payment.getState().equals(PaymentState.REFUSED)) {
                    if (requestService.onPaymentRefused(request)) {
                        requestWorkflowService.updateRequestState(request.getId(), 
                                RequestState.REJECTED, "request.message.paymentRefused");                        
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(PaymentEvent paymentEvent) {
        logger.debug("onApplicationEvent() got a payment event of type " + paymentEvent.getEvent());
        try {
            notifyPaymentResult(paymentEvent.getPayment());
        } catch (CvqException e) {
            // TODO We have nothing to handle this
            logger.error("onApplicationEvent() got an error while notifying payment resutl");
            e.printStackTrace();
        }
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setRequestServiceRegistry(IRequestServiceRegistry requestServiceRegistry) {
        this.requestServiceRegistry = requestServiceRegistry;
    }

    public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        this.requestWorkflowService = requestWorkflowService;
    }
}
