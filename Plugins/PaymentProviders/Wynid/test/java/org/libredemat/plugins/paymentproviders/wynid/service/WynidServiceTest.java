package org.libredemat.plugins.paymentproviders.wynid.service;

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


public class WynidServiceTest extends PaymentTestCase {

    public void testAll() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        InternalInvoiceItem invoice1 =
            new InternalInvoiceItem("Wynid Invoice 1", Double.valueOf("300"),
                    "key", "keyOwner", "Wynid", Integer.valueOf(1), 
                    Double.valueOf(2));
        Payment payment = paymentService.createPaymentContainer(invoice1, PaymentMode.CARD);
        InternalInvoiceItem invoice2 =
            new InternalInvoiceItem("Wynid Invoice 2", Double.valueOf("600"),
                    "key", "keyOwner", "Wynid", Integer.valueOf(1), 
                    Double.valueOf(2));
        paymentService.addPurchaseItemToPayment(payment, invoice2);
        payment.addPaymentSpecificData("terminal", "Dummy-borne1");
        
        URL url = paymentService.initPayment(payment);
        String urlString = url.toString();
        System.err.println("urlString : " + urlString);
        int beginIndex = urlString.indexOf("transaction=");
        int endIndex = urlString.indexOf("&", beginIndex + 12);
        String reference = urlString.substring(beginIndex + 12, endIndex);
        System.err.println("reference : " + reference);

        Assert.assertNotNull(url);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("wynidid", payment.getCvqReference() + "/ACCEPTED");
        PaymentResultStatus returnStatus = paymentService.commitPayment(parameters);
        Assert.assertEquals(PaymentResultStatus.OK, returnStatus);
    }
}
