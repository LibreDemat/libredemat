package org.libredemat.service.request.permit.impl;

import java.util.Map;
import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.permit.ParkingPermitTemporaryRelocationRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.IXENoBrokerFindException;
import org.libredemat.exception.IXEPaymentAllReadyExistException;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.payment.IRequestPaymentService;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestLockService;
import org.libredemat.service.request.impl.RequestService;
import org.libredemat.service.users.IUserSearchService;

public class ParkingPermitTemporaryRelocationRequestService extends RequestService implements IRequestPaymentService {

    protected IPaymentService paymentService;
    protected IRequestActionService requestActionService;
    protected IRequestLockService requestLockService;
    protected IUserSearchService userSearchService;

    @Override
    public boolean accept(Request request)
    {
        return request instanceof ParkingPermitTemporaryRelocationRequest;
    }

    @Override
    public boolean acceptPayment(Request request)
    {
        return true;
    }

    @Override
    public Request getSkeletonRequest()
    {
        return new ParkingPermitTemporaryRelocationRequest();
    }

    @Override
    public boolean onPaymentValidated(Request request, String paymentReference) throws CvqException
    {
        requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_VALIDATED, "Le paiement a été validé", "", null, null);
        requestLockService.release(request.getId());
        return false;
    }

    @Override
    public boolean onPaymentRefused(Request request) throws CvqException
    {
        requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_REFUSED, "Le paiement a été refusé", "", null, null);
        requestLockService.release(request.getId());
        return false;
    }

    @Override
    public boolean onPaymentCancelled(Request request) throws CvqException
    {
        requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_CANCELLED, "Le paiement a été annulé", "", null, null);
        requestLockService.release(request.getId());
        return false;
    }

    @Override
    public Payment buildPayment(Request request, Double amount) throws CvqException, IXEPaymentAllReadyExistException, IXENoBrokerFindException
    {
        ParkingPermitTemporaryRelocationRequest pptrr = (ParkingPermitTemporaryRelocationRequest) request;
        Payment payment = paymentService.getByRequestIdOnly(request.getId());
        if (payment != null)
        {
            return payment;
        }
        else
        {
            try
            {
                Map<String, String> brokers = paymentService.getAllBrokersByType(this.getLabel());
                if (brokers.isEmpty()) throw new IXENoBrokerFindException("error.broker.notexist");
                String broker = "";
                for (String keyBroker : brokers.keySet())
                {
                    broker = keyBroker;
                }
                InternalInvoiceItem internalInvoiceItem =
                        new InternalInvoiceItem("Demande de stationnement temporaire pour déménagement", amount,
                            request.getId().toString(), // key
                            "libredemat", // keyOwner
                            broker, // nom du broker
                            1, // quantité
                            amount); // unit price
                return paymentService.createPaymentContainerForRequest(internalInvoiceItem, PaymentMode.INTERNET,
                        userSearchService.getById(pptrr.getRequesterId()));
            }
            catch (Exception ex)
            {
                throw new CvqException(ex.getMessage());
            }
        }
    }

    public void setPaymentService(IPaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    public void setRequestActionService(IRequestActionService requestActionService)
    {
        this.requestActionService = requestActionService;
    }

    public void setRequestLockService(IRequestLockService requestLockService)
    {
        this.requestLockService = requestLockService;
    }

    public void setUserSearchService(IUserSearchService userSearchService)
    {
        this.userSearchService = userSearchService;
    }

    @Override
    public Payment getPayment(Request request)
    {
        return ((ParkingPermitTemporaryRelocationRequest) request).getPayment();
    }

    @Override
    public void setPayment(Request request, Payment paiement)
    {
        ((ParkingPermitTemporaryRelocationRequest) request).setPayment(paiement);
    }
}
