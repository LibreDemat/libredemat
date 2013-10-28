package org.libredemat.service.payment;

import javax.annotation.Resource;

import org.libredemat.service.payment.IPaymentProviderService;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.testtool.ServiceTestCase;
import org.springframework.beans.factory.annotation.Autowired;


public class PaymentTestCase extends ServiceTestCase {

    @Autowired
    protected IPaymentService paymentService;
    @Resource(name="fakePaymentProviderService")
    protected IPaymentProviderService fakePaymentProviderService;
}
