package org.libredemat.service.payment.external.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalNotificationStatus;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentEvent;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.impl.ExternalService;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.payment.external.IPaymentExternalService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.springframework.context.ApplicationListener;



public class PaymentExternalService extends ExternalService implements IPaymentExternalService , ApplicationListener<PaymentEvent>{

    private static Logger logger = Logger.getLogger(PaymentExternalService.class);

    private IExternalHomeFolderService externalHomeFolderService;
    private IPaymentDAO paymentDAO;

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Set<ExternalAccountItem> getExternalAccounts(Long homeFolderId, String type) 
        throws CvqException {

        // FIXME : this can cause backward compatibility problems
        //         not sure that all existing accounts have a mapping
        List<HomeFolderMapping> mappings = 
            externalHomeFolderService.getHomeFolderMappings(homeFolderId);
        if (mappings == null || mappings.isEmpty())
            return Collections.emptySet();

        Set<ExternalAccountItem> externalAccountItems = new HashSet<ExternalAccountItem>();
        for (HomeFolderMapping mapping : mappings) {
            IExternalProviderService externalProviderService =
                getExternalServiceByLabel(mapping.getExternalServiceLabel());
            if (externalProviderService == null) {
                logger.warn("getExternalAccounts() External service " + mapping.getExternalServiceLabel()
                        + " is no longer existing");
                continue;
            }
            Map<String, List<ExternalAccountItem>> mappingAccounts =
                externalProviderService.getAccountsByHomeFolder(homeFolderId,
                        mapping.getExternalLibreDematId(), mapping.getExternalId());
            if (mappingAccounts != null && mappingAccounts.get(type) != null) {
                externalAccountItems.addAll(mappingAccounts.get(type));
            }
        }
        return externalAccountItems;
    }

    @Override
    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
        IExternalProviderService externalProviderService =
            getExternalServiceByLabel(edai.getExternalServiceLabel());
        externalProviderService.loadDepositAccountDetails(edai);
    }

    @Override
    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
        IExternalProviderService externalProviderService =
            getExternalServiceByLabel(eii.getExternalServiceLabel());
        externalProviderService.loadInvoiceDetails(eii);
    }

    @Context(types = {ContextType.SUPER_ADMIN})
    private void creditHomeFolderAccounts(Payment payment) {
        Map<String, List<PurchaseItem>> externalServicesToNotify = externalServicesToNotify(payment);

        if (!externalServicesToNotify.isEmpty()) {
            for (List<PurchaseItem> items: externalServicesToNotify.values()) {
                for (PurchaseItem item: items) {
                    if (item != null && ((ExternalAccountItem)item)
                            .getExternalNotificationStatus() == null) {
                        ((ExternalAccountItem) item).setExternalNotificationStatus(
                                ExternalNotificationStatus.ITEM_NOT_SENT);
                    }
                }
            }
            paymentDAO.update(payment);
        }
    }

    @Override
    public Map<String, List<PurchaseItem>> externalServicesToNotify(Payment payment) {
        Map<String, List<PurchaseItem>> externalServicesToNotify =
            new HashMap<String, List<PurchaseItem>>();
        Set<PurchaseItem> purchaseItems = payment.getPurchaseItems();
        for (PurchaseItem purchaseItem : purchaseItems) {

            // if purchase item is managed by an external service, 
            // stack it for notification of the associated external service
            if (purchaseItem instanceof ExternalAccountItem) {
                logger.debug("creditHomeFolderAccounts() item managed by an external service : "
                        + purchaseItem.getInformativeFriendlyLabel());
                ExternalAccountItem externalAccountItem = (ExternalAccountItem) purchaseItem;
                externalAccountItem.setSupportedBroker(payment.getBroker());
                String externalServiceLabel = externalAccountItem.getExternalServiceLabel();
                if (externalServicesToNotify.get(externalServiceLabel) == null) {
                    externalServicesToNotify.put(externalServiceLabel,
                            new ArrayList<PurchaseItem>());
                }
                externalServicesToNotify.get(externalServiceLabel).add(purchaseItem);
            }
        }
        return externalServicesToNotify;
    }

    @Override
    public void onApplicationEvent(PaymentEvent paymentEvent) {
        logger.debug("onApplicationEvent() got a payment event of type " + paymentEvent.getEvent());
        if (paymentEvent.getEvent().equals(PaymentEvent.EVENT_TYPE.PAYMENT_VALIDATED)) {
            logger.info("Validated payment with id : " + paymentEvent.getPayment().getId());
            creditHomeFolderAccounts(paymentEvent.getPayment());
        }
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }

    public void setPaymentDAO(IPaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

}
