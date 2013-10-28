package org.libredemat.plugins.paymentproviders.spplus.service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.PaymentResultStatus;
import org.libredemat.service.payment.PaymentTestCase;

import junit.framework.Assert;


public class SpplusServiceTest extends PaymentTestCase {

    public void testAll() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);
        
        InternalInvoiceItem invoice1 =
            new InternalInvoiceItem("Spplus Invoice 1", Double.valueOf("300"),
                    "key", "keyOwner", "Spplus", Integer.valueOf(1), 
                    Double.valueOf(2));
        Payment payment = paymentService.createPaymentContainer(invoice1, PaymentMode.INTERNET);
        InternalInvoiceItem invoice2 =
            new InternalInvoiceItem("Spplus Invoice 2", Double.valueOf("600"),
                    "key", "keyOwner", "Spplus", Integer.valueOf(1), 
                    Double.valueOf(2));
        paymentService.addPurchaseItemToPayment(payment, invoice2);

        URL url = paymentService.initPayment(payment);
        Assert.assertNotNull(url);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("cvqReference", payment.getCvqReference());
        parameters.put("bankReference", "BANK-1234567890");
        parameters.put("refsfp", "BANK-1234567890");
        parameters.put("etat", "10");
        PaymentResultStatus returnStatus = paymentService.commitPayment(parameters);
        Assert.assertEquals(PaymentResultStatus.OK, returnStatus);
   }
}
