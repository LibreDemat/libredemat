package org.libredemat.service.request.permit.impl;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestType;
import org.libredemat.business.request.permit.ParkingPermitTemporaryWorkRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.IXENoBrokerFindException;
import org.libredemat.exception.IXEPaymentAllReadyExistException;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.payment.IRequestPaymentService;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestLockService;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;
import org.libredemat.service.users.IUserSearchService;

import java.util.Map;

public class ParkingPermitTemporaryWorkRequestService  extends RequestService implements IRequestPaymentService {

    protected IPaymentService paymentService;
    protected IRequestActionService requestActionService;
    protected IRequestLockService requestLockService;
    protected IUserSearchService userSearchService;

    @Override
    public void init() {
        ParkingPermitTemporaryWorkRequest.conditions.put("isCompany", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("desiredService", new EqualityChecker("Permis-de-stationnement-provisoire-pour-travaux"));
        ParkingPermitTemporaryWorkRequest.conditions.put("workOnBuilding", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("scaffolding", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("vehicleParkingOrFloorOccupation", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof ParkingPermitTemporaryWorkRequest;
    }

    @Override
    public boolean acceptPayment(Request request)
    {
        return true;
    }

    @Override
    public Request getSkeletonRequest() {
        return new ParkingPermitTemporaryWorkRequest();
    }

    private void setPaymentIndicativeAmount(Request request) throws CvqException {
        ParkingPermitTemporaryWorkRequest pptwRequest = (ParkingPermitTemporaryWorkRequest) request;
        RequestType rt = pptwRequest.getRequestType();

        float totalPrice = 0;
        if (pptwRequest.getScaffolding()) {
            int daysBetweenStartAndEnd = Days.daysBetween(new LocalDate(pptwRequest.getScaffoldingStartDate()),
                    new LocalDate(pptwRequest.getScaffoldingEndDate())).getDays() + 1;
            totalPrice += pptwRequest.getScaffoldingLength().floatValue() *
                    Float.valueOf(rt.getSpecificConfigurationDataValue("scaffoldingPrice").replace(',', '.')) *
                    daysBetweenStartAndEnd;
        }

        if (pptwRequest.getVehicleParkingOrFloorOccupation()) {
            int daysBetweenStartAndEnd = Days.daysBetween(new LocalDate(pptwRequest.getOccupationStartDate()),
                    new LocalDate(pptwRequest.getOccupationEndDate())).getDays() + 1;
            totalPrice += pptwRequest.getOccupation().floatValue() *
                    Float.valueOf(rt.getSpecificConfigurationDataValue("floorOccupationPrice").replace(',','.')) *
                    daysBetweenStartAndEnd;
        }

        totalPrice += Float.valueOf(rt.getSpecificConfigurationDataValue("fixedChargePrice").replace(',','.'));

        pptwRequest.setPaymentIndicativeAmount(String.valueOf(totalPrice));
    }

    @Override
    public void onRequestIssued(Request request) throws CvqException {
        setPaymentIndicativeAmount(request);
    }

    @Override
    public void onRequestModified(Request request) throws CvqException {
        setPaymentIndicativeAmount(request);
    }

    @Override
    public void onRequestRectified(Request request) throws CvqException {
        setPaymentIndicativeAmount(request);
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
    public Payment buildPayment(Request request, Double amount) throws CvqException, IXEPaymentAllReadyExistException, IXENoBrokerFindException {
        ParkingPermitTemporaryWorkRequest pptwr = (ParkingPermitTemporaryWorkRequest) request;
        Payment payment = paymentService.getByRequestIdOnly(request.getId());
        if (payment != null) {
            return payment;
        } else {
            try {
                Map<String, String> brokers = paymentService.getAllBrokersByType(this.getLabel());
                if (brokers.isEmpty()) throw new IXENoBrokerFindException("error.broker.notexist");
                String broker = brokers.keySet().iterator().next();

                InternalInvoiceItem internalInvoiceItem =
                        new InternalInvoiceItem("Demande numéro " + request.getId().toString() +" de stationnement temporaire pour travaux", amount,
                                request.getId().toString(), // key
                                "libredemat", // keyOwner
                                broker, // nom du broker
                                1, // quantité
                                amount); // unit price
                return paymentService.createPaymentContainerForRequest(internalInvoiceItem, PaymentMode.INTERNET,
                        userSearchService.getById(pptwr.getRequesterId()));
            } catch (Exception ex) {
                throw new CvqException(ex.getMessage());
            }
        }
    }

    public void setPaymentService(IPaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    public void setRequestActionService(IRequestActionService requestActionService) {
        this.requestActionService = requestActionService;
    }

    public void setRequestLockService(IRequestLockService requestLockService) {
        this.requestLockService = requestLockService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @Override
    public Payment getPayment(Request request) {
        return ((ParkingPermitTemporaryWorkRequest) request).getPayment();
    }

    @Override
    public void setPayment(Request request, Payment paiement) {
        ((ParkingPermitTemporaryWorkRequest) request).setPayment(paiement);
    }
}
