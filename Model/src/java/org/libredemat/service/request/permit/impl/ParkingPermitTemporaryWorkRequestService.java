package org.libredemat.service.request.permit.impl;

import org.libredemat.business.payment.Payment;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.permit.ParkingPermitTemporaryWorkRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.IXENoBrokerFindException;
import org.libredemat.exception.IXEPaymentAllReadyExistException;
import org.libredemat.service.payment.IRequestPaymentService;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

public class ParkingPermitTemporaryWorkRequestService  extends RequestService implements IRequestPaymentService {

    @Override
    public void init() {
        ParkingPermitTemporaryWorkRequest.conditions.put("isCompany", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("desiredService", new EqualityChecker("PARKING_PERMIT_FOR_WORK"));
        ParkingPermitTemporaryWorkRequest.conditions.put("workOnBuilding", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("scaffolding", new EqualityChecker("true"));
        ParkingPermitTemporaryWorkRequest.conditions.put("vehicleParkingOrFloorOccupation", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof ParkingPermitTemporaryWorkRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new ParkingPermitTemporaryWorkRequest();
    }

    @Override
    public Payment buildPayment(Request request, Double amount) throws CvqException, IXEPaymentAllReadyExistException, IXENoBrokerFindException {
        return null;
    }

    @Override
    public boolean acceptPayment(Request request)
    {
        return true;
    }

    @Override
    public Payment getPayment(Request request) {
        return null;
    }

    @Override
    public void setPayment(Request request, Payment paiement) {

    }
}
