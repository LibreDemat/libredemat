package org.libredemat.plugins.paymentproviders.paylinev4.service;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.PaymentTestCase;
import org.libredemat.util.Critere;

import static org.junit.Assert.*;


public class PaylineV4ServiceTest extends PaymentTestCase {

    public void testAll() throws CvqException {

        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        InternalInvoiceItem invoice1 =
            new InternalInvoiceItem("PaylineV4 Invoice 1", Double.valueOf("300"),
                    "key", "keyOwner", "PaylineV4", Integer.valueOf(1), 
                    Double.valueOf(2));
        Payment payment = paymentService.createPaymentContainer(invoice1, PaymentMode.INTERNET);
        InternalInvoiceItem invoice2 =
            new InternalInvoiceItem("PaylineV4 Invoice 2", Double.valueOf("600"),
                    "key", "keyOwner", "PaylineV4", Integer.valueOf(1), 
                    Double.valueOf(2));
        paymentService.addPurchaseItemToPayment(payment, invoice2);

        payment.addPaymentSpecificData("domainName", "localhost");

        URL url = paymentService.initPayment(payment);
        assertNotNull(url);

        continueWithNewTransaction();

        Set<Critere> criterias = new HashSet<Critere>();
        criterias.add(new Critere(Payment.SEARCH_BY_HOME_FOLDER_ID, fake.id, Critere.EQUALS));
        List<Payment> payments =
            paymentService.get(criterias, null, null, 0, 0);
        assertEquals(1, payments.size());
        Payment finalPayment = payments.get(0);
        assertNotNull(finalPayment.getBankReference());
        assertNotNull(finalPayment.getCvqReference());

//        Map<String, String> parameters = new HashMap<String, String>();
//        parameters.put("token", payment.getBankReference());
//        PaymentResultStatus returnStatus = iPaymentService.commitPayment(parameters);
//        Assert.assertEquals(PaymentResultStatus.OK, returnStatus);
   }
}
