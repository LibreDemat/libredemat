package org.libredemat.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItemDetail;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalInvoiceItemDetail;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.IPaymentService;

import static org.junit.Assert.*;


public class FakeExternalServiceTest extends ExternalServiceTestCase {

    @Test
    public void testContracts() throws CvqException {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        // retrieve all external accounts directly from fake external service
        Map<String, List<ExternalAccountItem>> completeAccount = 
            fakeExternalService.getAccountsByHomeFolder(fake.id, null, null);
        if (completeAccount == null) {
            logger.debug("testContracts() no contract found for home folder : " + fake.id);
            return;
        }
        assertEquals(2, completeAccount.get(IPaymentService.EXTERNAL_DEPOSIT_ACCOUNTS).size());
        assertEquals(4, completeAccount.get(IPaymentService.EXTERNAL_INVOICES).size());
        assertEquals(3, completeAccount.get(IPaymentService.EXTERNAL_TICKETING_ACCOUNTS).size());
        
        // retrieve external ticketing accounts from home folder service
        List<ExternalAccountItem> externalAccounts = 
            completeAccount.get(IPaymentService.EXTERNAL_TICKETING_ACCOUNTS);
        ExternalTicketingContractItem etciToPayOn = 
            (ExternalTicketingContractItem) externalAccounts.get(0);
        assertNotNull(etciToPayOn.getExternalApplicationId());

        // make a payment on choosen ticketing contract
        Collection<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
        etciToPayOn.setQuantity(Integer.valueOf(5));
        etciToPayOn.setAmount(etciToPayOn.getQuantity() * etciToPayOn.getUnitPrice());
        purchaseItems.add(etciToPayOn);
        fakeExternalService.creditHomeFolderAccounts(purchaseItems, "cvqReference", 
                "bankReference", fake.id, null, null, new Date());

        // retrieve external deposit accounts from home folder service
        externalAccounts = 
            completeAccount.get(IPaymentService.EXTERNAL_DEPOSIT_ACCOUNTS);
        for (ExternalAccountItem externalAccountItem : externalAccounts) {
            ExternalDepositAccountItem edai =
                (ExternalDepositAccountItem) externalAccountItem;
            logger.debug(edai.toString());
            logger.debug(edai.getExternalItemId());
            if (edai.getExternalItemId().equals("95999-3-1910782193")) {
                paymentExternalService.loadDepositAccountDetails(edai);
                assertEquals(2, edai.getAccountDetails().size());
                boolean foundCheque = false;
                for (ExternalDepositAccountItemDetail edaiDetail : edai.getAccountDetails()) {
                    assertEquals("ORIHUELA", edaiDetail.getHolderSurname());
                    assertEquals("Benoit", edaiDetail.getHolderName());
                    if (edaiDetail.getPaymentType().equals("Chèque")) {
                        foundCheque = true;
                        assertEquals(20345, edaiDetail.getValue().intValue());
                        assertEquals("0101566442", edaiDetail.getPaymentId());
                    }
                }
                if (!foundCheque)
                    fail("did not find cheque payment !");
            }
        }

        // retrieve external invoices from home folder service
        externalAccounts = 
            completeAccount.get(IPaymentService.EXTERNAL_INVOICES);
        for (ExternalAccountItem externalAccountItem : externalAccounts) {
            ExternalInvoiceItem eii =
                (ExternalInvoiceItem) externalAccountItem;
            if (eii.getExternalItemId().equals("95999-3-1910782195")) {
                assertEquals(Boolean.FALSE, eii.getIsPaid());
                paymentExternalService.loadInvoiceDetails(eii);
                assertEquals(2, eii.getInvoiceDetails().size());
                boolean foundPedro = false;
                for (ExternalInvoiceItemDetail eiiDetail : eii.getInvoiceDetails()) {
                    assertEquals("ORIHUELA", eiiDetail.getSubjectSurname());
                    if (eiiDetail.getSubjectName().equals("Pedro")) {
                        foundPedro = true;
                        assertEquals("Repas restauration scolaire", eiiDetail.getLabel());
                        assertEquals(2300, eiiDetail.getUnitPrice().intValue());
                        assertEquals(BigDecimal.valueOf(2.5), eiiDetail.getQuantity());
                        assertEquals(4600, eiiDetail.getValue().intValue());
                    }
                }
                if (!foundPedro)
                    fail("did not find Lolita !");
            }
        }
    }
}
