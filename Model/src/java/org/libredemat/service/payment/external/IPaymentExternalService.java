package org.libredemat.service.payment.external;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;
import org.libredemat.security.annotation.IsUser;


public interface IPaymentExternalService {
    /**
     * Get external accounts information and state for the given home folder. Designed
     * to be called by an ecitizen from the Front Office.
     *
     * @param type the "account type" for which we want information (one of
     *        {@link org.libredemat.service.payment.IPaymentService#EXTERNAL_INVOICES},
     *        {@link org.libredemat.service.payment.IPaymentService#EXTERNAL_DEPOSIT_ACCOUNTS},
     *        {@link org.libredemat.service.payment.IPaymentService#EXTERNAL_TICKETING_ACCOUNTS}
     *
     */
    Set<ExternalAccountItem> getExternalAccounts(@IsUser final Long homeFolderId,
            final String type)
        throws CvqException;

    /**
     * Load details of operations performed on given deposit account. Details
     * are directly loaded into the provided object.
     */
    void loadDepositAccountDetails(ExternalDepositAccountItem edai)
        throws CvqException;

    /**
     * Load details of items paid along given external invoice. Details
     * are directly loaded into the provided object.
     */
    void loadInvoiceDetails(ExternalInvoiceItem eii)
        throws CvqException;

    /**
     * Find externals services to notify from payment.
     * @param payment
     * @throws CvqException
     */
    Map<String, List<PurchaseItem>> externalServicesToNotify(Payment payment);
}
