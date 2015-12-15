package org.libredemat.service.payment.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.libredemat.business.authority.LocalAuthorityResource.Type;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentEvent;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.business.payment.PaymentState;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.payment.external.ExternalHomeFolder;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.UserAction;
import org.libredemat.business.users.UserEvent;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.authority.LocalAuthorityConfigurationBean;
import org.libredemat.service.payment.CvqInvalidBrokerException;
import org.libredemat.service.payment.IPaymentProviderService;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.payment.PaymentResultBean;
import org.libredemat.service.payment.PaymentResultStatus;
import org.libredemat.service.payment.PaymentServiceBean;
import org.libredemat.service.payment.annotation.PaymentFilter;
import org.libredemat.service.payment.external.IExternalApplicationService;
import org.libredemat.service.payment.external.IPaymentExternalService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere;
import org.libredemat.util.DateUtils;
import org.libredemat.util.JSONUtils;
import org.libredemat.util.logging.impl.Log;
import org.libredemat.util.mail.IMailService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import au.com.bytecode.opencsv.CSVWriter;


public final class PaymentService implements IPaymentService, 
    ApplicationListener<UserEvent>, ApplicationContextAware {

    private static Logger logger = Logger.getLogger(PaymentService.class);

    private IGenericDAO genericDAO;
    private IPaymentDAO paymentDAO;
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;
    private IUserSearchService userSearchService;
    private IExternalHomeFolderService externalHomeFolderService;
    private IPaymentExternalService paymentExternalService;
    private IExternalApplicationService externalApplicationService;

    private ApplicationContext applicationContext;

    public Map<String, String> getAllBrokers() {
        
        Map<IPaymentProviderService, PaymentServiceBean> paymentProviders = 
            SecurityContext.getCurrentConfigurationBean().getPaymentServices();
        if (paymentProviders == null || paymentProviders.isEmpty())
            return null;
        Map<String, String> brokers = new HashMap<String, String>();
        for (IPaymentProviderService paymentProviderService : paymentProviders.keySet()) {
                PaymentServiceBean psb = paymentProviders.get(paymentProviderService);
                brokers.put(psb.getBroker(), psb.getFriendlyLabel());
        }

        return brokers;
    }

    @Override
    public final Payment createPaymentContainer(PurchaseItem purchaseItem, PaymentMode paymentMode) 
        throws CvqModelException, CvqInvalidBrokerException, CvqException {

        checkPurchaseItem(purchaseItem);
        
        Payment payment = new Payment();
        String broker = purchaseItem.getSupportedBroker();

        // Damn quick hack to link an external service item to a broker
        if (broker == null && getAllBrokers() != null && getAllBrokers().size() == 1)
            broker = getAllBrokers().keySet().iterator().next();

        if (broker == null || broker.equals(""))
            throw new CvqInvalidBrokerException("payment.missing_broker");
        else if (payment.getPurchaseItems() == null || payment.getPurchaseItems().isEmpty())
            payment.setBroker(broker);
        else if (!broker.equals(payment.getBroker()))
            throw new CvqInvalidBrokerException("payment.incompatible_broker");
        payment.setBroker(broker);
        processPurchaseItemAmount(purchaseItem);
        payment.setAmount(purchaseItem.getAmount());
        payment.setHomeFolderId(SecurityContext.getCurrentEcitizen().getHomeFolder().getId());
        payment.setRequesterId(SecurityContext.getCurrentEcitizen().getId());
        payment.setRequesterLastName(SecurityContext.getCurrentEcitizen().getLastName());
        payment.setRequesterFirstName(SecurityContext.getCurrentEcitizen().getFirstName());
        Set<PurchaseItem> purchaseItems = new HashSet<PurchaseItem>();
        purchaseItems.add(purchaseItem);
        payment.setPurchaseItems(purchaseItems);
        payment.setPaymentMode(paymentMode);

        payment.addPaymentSpecificData(Payment.SPECIFIC_DATA_EMAIL, 
                SecurityContext.getCurrentEcitizen().getEmail());
        return payment;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public final void addPurchaseItemToPayment(Payment payment, PurchaseItem purchaseItem)
        throws CvqInvalidBrokerException, CvqModelException, CvqException, 
            CvqObjectNotFoundException {

        checkPurchaseItem(purchaseItem);

        String broker = purchaseItem.getSupportedBroker();

        // Damn quick hack to link an external service item to a broker
        if (broker == null && getAllBrokers() != null && getAllBrokers().size() == 1)
            broker = getAllBrokers().keySet().iterator().next();

        if (broker == null || broker.equals(""))
            throw new CvqInvalidBrokerException("payment.missing_broker");
        if (payment.getBroker().equals(""))
            payment.setBroker(broker);
        else if (!broker.equals(payment.getBroker()))
            throw new CvqInvalidBrokerException("payment.incompatible_broker");

        if(payment.getPurchaseItems().size() > 0) {
            IPaymentProviderService paymentProviderService =
                    getPaymentServiceByBrokerAndMode(payment.getBroker(), payment.getPaymentMode());
            if(!paymentProviderService.allowMultiplePurchaseItems()) {
                throw new CvqException("payment.broker_does_not_allow_multiple_items");
            }
        }
        processPurchaseItemAmount(purchaseItem);
        payment.getPurchaseItems().add(purchaseItem);
        double newAmount = payment.getAmount().doubleValue()
            + purchaseItem.getAmount().doubleValue();
        payment.setAmount(Double.valueOf(newAmount));
    }

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public final void removePurchaseItemFromPayment(Payment payment, PurchaseItem purchaseItem) {

        payment.getPurchaseItems().remove(purchaseItem);
        double newAmount = 0;
        for(PurchaseItem pit : payment.getPurchaseItems())
            newAmount = newAmount + pit.getAmount();

        payment.setAmount(Double.valueOf(newAmount));
        if (payment.getPurchaseItems().isEmpty()) 
            payment.setBroker("");
    }

    /**
     * Perform business checking of a purchase item.
     */
    private void checkPurchaseItem(PurchaseItem purchaseItem) 
        throws CvqModelException {
        
        if (purchaseItem instanceof ExternalInvoiceItem) {
            ExternalInvoiceItem eii = (ExternalInvoiceItem) purchaseItem;
            if (eii.getIsPaid()) 
                throw new CvqModelException("payment.item_not_buyable");
        }
    }
    
    /**
     * Process a given purchase item to add some business data to it (eg amount).
     */
    private void processPurchaseItemAmount(PurchaseItem purchaseItem) {
        if (purchaseItem instanceof ExternalTicketingContractItem) {
            ExternalTicketingContractItem etci =
                (ExternalTicketingContractItem) purchaseItem;
            Double amount = Double.valueOf(etci.getQuantity().intValue() 
                    * etci.getUnitPrice().intValue());
                
            purchaseItem.setAmount(amount);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public final URL initPayment(Payment payment)
        throws CvqException {

        IPaymentProviderService paymentProviderService =
            getPaymentServiceByBrokerAndMode(payment.getBroker(), payment.getPaymentMode());
        if (paymentProviderService == null) {
            throw new CvqException("payment.provider_not_found");
        }
        PaymentServiceBean psb = getPaymentServiceBean(paymentProviderService);
        URL url = paymentProviderService.doInitPayment(payment, psb);
      
        payment.setState(PaymentState.INITIALIZED);
        payment.setInitializationDate(new Date());

        Log.paymentToCsv(payment);

        Set<PurchaseItem> purchaseItems = payment.getPurchaseItems();
        if (purchaseItems != null){
            Set<PurchaseItem> persistedPurchaseItems = new HashSet<PurchaseItem>();
            for (PurchaseItem purchaseItem : purchaseItems) {
                PurchaseItem item = JpaUtil.getEntityManager().merge(purchaseItem);
                persistedPurchaseItems.add(item);
            }
            payment.setPurchaseItems(persistedPurchaseItems);
        }
        paymentDAO.saveOrUpdate(payment);
        JpaUtil.getEntityManager().flush();

        return url;
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.NONE)
    public final PaymentResultStatus commitPayment(final Map<String, String> parameters)
        throws CvqException {
        
        logger.error("commitPayment() got a commit order in " + SecurityContext.getCurrentSite().getName());
        for (String key : parameters.keySet()) {
            logger.error("commitPayment() " + key + " : " + parameters.get(key));
        }

        IPaymentProviderService paymentProviderService = 
            getPaymentProviderFromParameters(parameters);
        if (paymentProviderService == null) {
            throw new CvqException("payment.provider_not_found");
        }

        logger.debug("commitPayment() redirecting to " + paymentProviderService);
        PaymentServiceBean psb = getPaymentServiceBean(paymentProviderService);
        PaymentResultBean paymentResultBean = 
            paymentProviderService.doCommitPayment(parameters, psb);
        
        PaymentResultStatus paymentStatus = paymentResultBean.getStatus();
        if (paymentStatus.equals(PaymentResultStatus.UNKNOWN) 
                || paymentStatus.equals(PaymentResultStatus.OTHER))
            return paymentStatus;
        
        logger.debug("commitPayment() looking for CVQ reference : " 
                + paymentResultBean.getCvqReference());
        Payment payment = paymentDAO.findByCvqReference(paymentResultBean.getCvqReference());
        String bankReference = paymentResultBean.getBankReference();
        // Hack retour TIPI
        if(payment == null) {
            logger.error(" Unable to find payment with cvq_reference : " + paymentResultBean.getCvqReference());
            logger.error(" Search payment by refdet : " + parameters.get("refdet"));
            payment = paymentDAO.findByCvqReference(parameters.get("refdet"));
        }
        if (bankReference == null || bankReference.equals("XXXXXX")) {
            bankReference = payment.getCvqReference();
        }
        payment.setBankReference(bankReference);
        payment.setCommitDate(new Date());
        PaymentEvent.EVENT_TYPE event = null;
        if (paymentStatus.equals(PaymentResultStatus.OK)) {
            payment.setState(PaymentState.VALIDATED);
            event = PaymentEvent.EVENT_TYPE.PAYMENT_VALIDATED;
        } else if (paymentStatus.equals(PaymentResultStatus.CANCELLED) && !PaymentState.VALIDATED.equals(payment.getState())) {
            payment.setState(PaymentState.CANCELLED);
            event = PaymentEvent.EVENT_TYPE.PAYMENT_CANCELLED;
        } else if (paymentStatus.equals(PaymentResultStatus.REFUSED) && !PaymentState.VALIDATED.equals(payment.getState())) {
            payment.setState(PaymentState.REFUSED);
            event = PaymentEvent.EVENT_TYPE.PAYMENT_REFUSED;
        }

        if(event != null) {
            paymentDAO.update(payment);

            PaymentEvent paymentEvent = new PaymentEvent(this, event, payment);
            applicationContext.publishEvent(paymentEvent);

            if (paymentStatus.equals(PaymentResultStatus.OK))
                notifyPaymentByMail(payment);

        }
        return paymentStatus;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public PaymentResultStatus getStateFromParameters(Map<String, String> parameters) 
        throws CvqException {

        IPaymentProviderService paymentProviderService = 
            getPaymentProviderFromParameters(parameters);
        if (paymentProviderService == null) {
            throw new CvqException("payment.provider_not_found");
        }

        PaymentServiceBean psb = getPaymentServiceBean(paymentProviderService);
        
        return paymentProviderService.getStateFromParameters(parameters, psb);
    }

    private IPaymentProviderService getPaymentProviderFromParameters(Map<String, String> parameters) {

        if (parameters.get("cvqReference") != null) {
            Payment payment = paymentDAO.findByCvqReference(parameters.get("cvqReference"));
            return getPaymentServiceByBrokerAndMode(payment.getBroker(), payment.getPaymentMode());
        } else {
            
            // search the payment provider that will recognize the parameters
            // sounds dirty but don't have anything better for the moment ...
            Set<IPaymentProviderService> paymentProviderServices =
                SecurityContext.getCurrentConfigurationBean().getPaymentServicesObjects();
            for (IPaymentProviderService tempPps : paymentProviderServices) {
                if (tempPps.handleParameters(parameters))
                    return tempPps;
            }
        }

        return null;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT,  ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public final List<Payment> getByHomeFolder(final Long homeFolderId) {
        return paymentDAO.findByHomeFolder(homeFolderId);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public final Payment getById(final Long id) {
        return paymentDAO.findById(id);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public final Payment getByCvqReference(final String reference) {
        return paymentDAO.findByCvqReference(reference);
    }

    @Override
    @PaymentFilter
    public List<Payment> get(Set<Critere> criteriaSet, final String sort, final String dir,
            final int recordsReturned, final int startIndex) {

        if (criteriaSet == null)
            criteriaSet = new HashSet<Critere>();

        return paymentDAO.search(criteriaSet, sort, dir, recordsReturned, startIndex);
    }    

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public List<Long> getIds(Set<Critere> criteriaSet) {
        List<Payment> payments = paymentDAO.search(criteriaSet, null, null, -1, 0);
        if (payments == null || payments.isEmpty())
            return Collections.emptyList();

        List<Long> paymentsIds = new ArrayList<Long>();
        for (Payment payment : payments)
            paymentsIds.add(payment.getId());
        return paymentsIds;
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public File exportPayments(List<Long> paymentsIds) throws CvqException {
        List<Payment> payments = paymentDAO.findByIds(paymentsIds);
        String[] header = new String[15];
        header[0] = "Identifiant LibreDémat";
        header[1] = "Référence bancaire";
        header[2] = "Régie de paiement";
        header[3] = "Date de validation";
        header[4] = "Identifiant compte LibreDémat";
        header[5] = "Nom du payeur";
        header[6] = "Prénom du payeur";
        header[7] = "Montant total";
        header[8] = "État";
        header[9] = "Identifiant compte externe";
        header[10] = "Nom compte externe";
        header[11] = "Prénom compte externe";
        header[12] = "Identifiant item";
        header[13] = "Label item";
        header[14] = "Montant item";
        List<String[]> data = new ArrayList<String[]>();
        data.add(header);
        for (Payment payment : payments) {
            for (PurchaseItem purchaseItem : payment.getPurchaseItems()) {
                String[] line = new String[15];
                line[0] = String.valueOf(payment.getId());
                line[1] = payment.getBankReference();
                line[2] = payment.getBroker();
                line[3] = DateUtils.formatDate(payment.getCommitDate());
                line[4] = String.valueOf(payment.getHomeFolderId());
                line[5] = payment.getRequesterLastName();
                line[6] = payment.getRequesterFirstName();
                line[7] = String.valueOf(payment.getAmount());
                line[8] = payment.getState().toString();
                if (purchaseItem instanceof ExternalAccountItem) {
                    ExternalAccountItem externalAccountItem = (ExternalAccountItem) purchaseItem;
                    ExternalHomeFolder ehf = 
                        externalApplicationService.getHomeFolder(Long.valueOf(externalAccountItem.getExternalApplicationId()), 
                                externalAccountItem.getExternalHomeFolderId());
                    line[9] = externalAccountItem.getExternalHomeFolderId();
                    if (ehf != null) {
                        line[10] = ehf.getResponsible().getLastName();
                        line[11] = ehf.getResponsible().getFirstName();
                    } else {
                        line[10] = "";
                        line[11] = "";
                    }
                    line[12] = externalAccountItem.getExternalItemId();
                    line[13] = externalAccountItem.getLabel();
                    line[14] = String.valueOf(externalAccountItem.getAmount());
                } else {
                    line[9] = "";
                    line[10] = "";
                    line[11] = "";
                    line[12] = "";
                    line[13] = "";
                    line[14] = "";
                }
                data.add(line);
            }
        }
        File file = null;
        try {
            file = File.createTempFile("paiements_", ".csv");
            FileWriter fw = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fw);
            writer.writeAll(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new CvqException("payment.error.failedExport");
        }

        return file;
    }

    // TODO : Improve externalHomeFolder mapping strategy
    // introducing compositId in externalHomeFolderIs is a very bad idea
    private Set<Critere> homeFolderIdToExternalHomeFolderIds(Set<Critere> criteres) {
        if (criteres == null)
            return new HashSet<Critere>();
        for (Critere critere : criteres) {
            if (!critere.getAttribut().equals(Payment.SEARCH_BY_HOME_FOLDER_ID))
                continue;
            String externalIds = "";
            List<HomeFolderMapping> mappings = externalHomeFolderService.getHomeFolderMappings(critere.getLongValue());
            if (mappings != null && mappings.size() > 0) {
                for (HomeFolderMapping mapping : mappings) {
                    if (mapping.getExternalId() == null || mapping.getExternalId().isEmpty() || mapping.getExternalId().indexOf(':') < 0)
                        continue;
                    externalIds += "'" + mapping.getExternalId().split(":")[1] + "',";
                }
                if (!externalIds.isEmpty())
                    externalIds = externalIds.substring(0, externalIds.length() - 1);
            }
            critere.setAttribut(ExternalAccountItem.SEARCH_BY_EXTERNAL_HOME_FOLDER);
            if (externalIds.isEmpty())
                externalIds += "'#'"; //hack
            critere.setValue("(" + externalIds + ")");
        }
        return criteres;
    }

    @Override
    public List<ExternalAccountItem> getInvoices(Set<Critere> criteres, 
            final String sort, final String dir, final int recordsReturned, final int startIndex) {
        return paymentDAO.searchInvoices(
                homeFolderIdToExternalHomeFolderIds(criteres), sort, dir, recordsReturned, startIndex);
    }

    @Override
    public List<ExternalAccountItem> getDepositAccounts(Set<Critere> criteres,
            final String sort, final String dir, final int recordsReturned, final int startIndex) {
        return paymentDAO.searchDepositAccounts(
                homeFolderIdToExternalHomeFolderIds(criteres), sort, dir, recordsReturned, startIndex);
    }

    @Override
    public List<ExternalAccountItem> getTicketingContracts(Set<Critere> criteres,
            final String sort, final String dir, final int recordsReturned, final int startIndex) {
        return paymentDAO.searchTicketingContracts(
                homeFolderIdToExternalHomeFolderIds(criteres), sort, dir, recordsReturned, startIndex);
    }

    @Override
    public List<ExternalAccountItem> getAllExternalAccountItems() {

        List<ExternalAccountItem> all = new ArrayList<ExternalAccountItem>();
        all.addAll(getInvoices(new HashSet<Critere>(), null, null, -1, 0));
        all.addAll(getDepositAccounts(new HashSet<Critere>(), null, null, -1, 0));
        all.addAll(getTicketingContracts(new HashSet<Critere>(), null, null, -1, 0));
        return all;
    }

    @Override
    @PaymentFilter
    public Long getCount(Set<Critere> criteriaSet) {

        if (criteriaSet == null)
            criteriaSet = new HashSet<Critere>();

        return paymentDAO.count(criteriaSet);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.READ)
    public List<ExternalInvoiceItem> getInvoicesPaid(final Long homeFolderId) 
        throws CvqException {
        List<ExternalInvoiceItem> invoicesPaid = new ArrayList<ExternalInvoiceItem>();
        Set<ExternalAccountItem> invoices = paymentExternalService.getExternalAccounts(homeFolderId, IPaymentService.EXTERNAL_INVOICES);

        for (ExternalAccountItem item : invoices) {
            if (((ExternalInvoiceItem) item).getIsPaid()) {
                invoicesPaid.add((ExternalInvoiceItem) item);
            }
        }
        return invoicesPaid;
    }

    @Override
    public Long getInvoicesCount(Set<Critere> criteres) {
        return paymentDAO.invoicesCount(homeFolderIdToExternalHomeFolderIds(criteres));
    }

    @Override
    public Long getDepositAccountsCount(Set<Critere> criteres) {
        return paymentDAO.depositAccountsCount(homeFolderIdToExternalHomeFolderIds(criteres));
    }

    @Override
    public Long getTicketingContractsCount(Set<Critere> criteres) {
        return paymentDAO.ticketingContractsCount(homeFolderIdToExternalHomeFolderIds(criteres));
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void delete(Long id) {
        Payment payment = getById(id);
        delete(payment);
    }

    private void delete(Payment payment) {
        paymentDAO.delete(payment);
    }

    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    private void deleteHomeFolderPayments(final Long homeFolderId) {
        List<Payment> homeFolderPayments = getByHomeFolder(homeFolderId);
        for (Payment payment : homeFolderPayments)
            delete(payment);
    }
    
    /**
     * Return the service bean associated to the given payment service. 
     */
    private PaymentServiceBean getPaymentServiceBean(IPaymentProviderService paymentProviderService) {

        Map<IPaymentProviderService, PaymentServiceBean> paymentServices = 
            SecurityContext.getCurrentConfigurationBean().getPaymentServices();
        if (paymentServices == null || paymentServices.isEmpty())
            return null;
        
        return paymentServices.get(paymentProviderService);
    }
    
    /**
     * Return the payment service associated to the given broker and payment mode.
     */
    private IPaymentProviderService getPaymentServiceByBrokerAndMode(String broker, 
            PaymentMode paymentMode) {

        Map<IPaymentProviderService, PaymentServiceBean>  paymentServices = 
            SecurityContext.getCurrentConfigurationBean().getPaymentServices();
        if (paymentServices == null || paymentServices.isEmpty())
            return null;

        for (IPaymentProviderService service : paymentServices.keySet()) {
            if (service.getPaymentMode().equals(paymentMode)) {
                PaymentServiceBean psb = paymentServices.get(service);
                if (broker.equals(psb.getBroker()))
                    return service;
            }
        }

        return null;
    }

    private void notifyPaymentByMail(Payment payment) throws CvqException {
        
        Adult requester = userSearchService.getAdultById(payment.getRequesterId());
        String mailSendTo = requester.getEmail();
        if (mailSendTo == null || mailSendTo.equals("")) {
            logger.warn("notifyPaymentByMail() e-citizen has no email address, returning");
            return;
        }
        
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        Map<String, String> mailMap = 
            lacb.getMailAsMap("hasPaymentNotification", "getPaymentNotificationData", 
                    "CommitPaymentConfirmation");
        
        if (mailMap != null) {
            String mailSubject = mailMap.get("mailSubject") != null ? mailMap.get("mailSubject") : "";
            String mailBodyFilename = mailMap.get("mailData") != null ? mailMap.get("mailData") : "";
            String mailBody = 
                localAuthorityRegistry.getBufferedLocalAuthorityResource(
                        Type.TXT, mailBodyFilename, false);
            
            if (mailBody == null) {
                logger.warn("notifyPaymentByMail() did not find mail template "
                        + mailBodyFilename + " for local authority " 
                        + SecurityContext.getCurrentSite().getName());
                return;
            }
            
            //  Mail body variable
            mailBody = mailBody.replace("${broker}",
                    payment.getBroker() != null ? payment.getBroker() : "" );
            mailBody = mailBody.replace("${cvqReference}",
                    payment.getCvqReference() != null ? payment.getCvqReference() : "" );
            mailBody = mailBody.replace("${paymentMode}",
                    payment.getPaymentMode() !=  null ? payment.getPaymentMode().toString() : "");
            mailBody = mailBody.replace("${commitDate}",
                    payment.getCommitDate().toString());
            
            mailService.send(null, mailSendTo, null, mailSubject, mailBody);
        }
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        logger.debug("onApplicationEvent() got a user event of type " + event.getAction().getType());
        if (UserAction.Type.DELETION.equals(event.getAction().getType())) {
            if (userSearchService.getById(event.getAction().getTargetId()) != null) {
                logger.debug("onApplicationEvent() nothing to delete for individual "
                    + event.getAction().getTargetId());
            } else {
                logger.debug("onApplicationEvent() deleting payments of home folder "
                    + event.getAction().getTargetId());
                deleteHomeFolderPayments(event.getAction().getTargetId());
            }
        } else if (UserAction.Type.MERGE.equals(event.getAction().getType())) {
            if (userSearchService.getById(event.getAction().getTargetId()) != null) {
                Long individualId = event.getAction().getTargetId();
                Long homeFolderId = userSearchService.getById(individualId).getHomeFolder().getId();
                Long targetIndividualId = JSONUtils.deserialize(event.getAction().getData()).get("merge").getAsLong();
                logger.debug("onApplicationEvent() moving payment from individual " + individualId
                    + " to " + targetIndividualId);
                for (Payment payment : getByHomeFolder(homeFolderId)) {
                    payment.setRequesterId(targetIndividualId);
                    paymentDAO.update(payment);
                }
            } else {
                logger.debug("onApplicationEvent() deleting payments of home folder "
                    + event.getAction().getTargetId());
                Long homeFolderId = event.getAction().getTargetId();
                Long targetHomeFolderId = JSONUtils.deserialize(event.getAction().getData()).get("merge").getAsLong();
                for (Payment payment : getByHomeFolder(homeFolderId)) {
                    payment.setHomeFolderId(targetHomeFolderId);
                    paymentDAO.update(payment);
                }
            }
        }
    }

    public final void setPaymentDAO(IPaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }

    public void setPaymentExternalService(IPaymentExternalService paymentExternalService) {
        this.paymentExternalService = paymentExternalService;
    }

    public void setExternalApplicationService(IExternalApplicationService externalApplicationService) {
        this.externalApplicationService = externalApplicationService;
    }

    public IGenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    /**
     * Hack inexine, méthode pour être sur de récupérer les bons brokers
     * 
     * @param type
     *            de request, exemple : Park Card
     * @return la liste de brokers
     */
    public Map<String, String> getAllBrokersByType(String type)
    {
        Map<IPaymentProviderService, PaymentServiceBean> paymentProviders = SecurityContext
            .getCurrentConfigurationBean().getPaymentServices();
        if (paymentProviders == null || paymentProviders.isEmpty()) return null;
        Map<String, String> brokers = new HashMap<String, String>();
        for (IPaymentProviderService paymentProviderService : paymentProviders.keySet())
        {
            PaymentServiceBean psb = paymentProviders.get(paymentProviderService);
            for (String requestTyp : psb.getRequestTypes())
            {
                if (requestTyp.equals(type)) brokers.put(psb.getBroker(), psb.getFriendlyLabel());
            }
        }
        return brokers;
    }

    @Override
    public boolean isPaymnetInPopup(Payment payment) throws CvqException {
        IPaymentProviderService paymentProviderService = getPaymentServiceByBrokerAndMode(payment.getBroker(),
                payment.getPaymentMode());
        if (paymentProviderService == null) { throw new CvqException("payment.provider_not_found"); }
        PaymentServiceBean psb = getPaymentServiceBean(paymentProviderService);
        return psb.isPopupPayment();
    }

    @Override
    @Deprecated
    public List<ExternalAccountItem> getExternalAccountItemByExternalItemId(String externalItemId) {
        List<ExternalAccountItem> all = new ArrayList<ExternalAccountItem>();
        if (externalItemId != null && !externalItemId.trim().equals("")) {
            HashSet<Critere> criteres = new HashSet<Critere>();
            Critere critere = new Critere(ExternalAccountItem.SEARCH_BY_EXTERNAL_ITEM_ID, externalItemId,
                    Critere.EQUALS);
            criteres.add(critere);
            all.addAll(getInvoices(criteres, null, null, -1, 0));
        }
        return all;
    }

    @Override
    public Payment getByRequestIdOnly(Long requestId) {
        return paymentDAO.getByRequestIdOnly(requestId);
    }

    @Override
    public final Payment createPaymentContainerForRequest(PurchaseItem purchaseItem, PaymentMode paymentMode,
            Individual requester) throws CvqModelException, CvqInvalidBrokerException, CvqException
    {
        checkPurchaseItem(purchaseItem);
        Payment payment = new Payment();
        String broker = purchaseItem.getSupportedBroker();
        // Damn quick hack to link an external service item to a broker
        if (broker == null && getAllBrokers() != null && getAllBrokers().size() == 1)
            broker = getAllBrokers().keySet().iterator().next();
        if (broker == null || broker.equals(""))
            throw new CvqInvalidBrokerException("payment.missing_broker");
        else if (payment.getPurchaseItems() == null || payment.getPurchaseItems().isEmpty())
            payment.setBroker(broker);
        else if (!broker.equals(payment.getBroker()))
            throw new CvqInvalidBrokerException("payment.incompatible_broker", new String[] { broker, payment.getBroker() });
        payment.setBroker(broker);
        processPurchaseItemAmount(purchaseItem);
        payment.setAmount(purchaseItem.getAmount());
        payment.setHomeFolderId(requester.getHomeFolder().getId());
        payment.setRequesterId(requester.getId());
        payment.setRequesterLastName(requester.getLastName());
        payment.setRequesterFirstName(requester.getFirstName());
        Set<PurchaseItem> purchaseItems = new HashSet<PurchaseItem>();
        purchaseItems.add(purchaseItem);
        payment.setPurchaseItems(purchaseItems);
        payment.setPaymentMode(paymentMode);
        payment.addPaymentSpecificData(Payment.SPECIFIC_DATA_EMAIL, ((Adult) requester).getEmail());
        return payment;
    }

    @Override
    public String notifyPaymentByMailWithPDF(Payment payment, byte[] pdf) throws CvqException
    {
        return sendMailByType(payment, pdf, "facture.pdf", "AskPaymentNotification");
    }

    @Override
    public String notifyAnnulationPaymentByMailWithPDF(Payment payment, byte[] pdf) throws CvqException
    {
        return sendMailByType(payment, pdf, "facture.pdf", "CancelPaymentNotification");
    }

    private String sendMailByType(Payment payment, byte[] pdf, String attachedName, String modelMail)
            throws CvqException {

        Adult requester = userSearchService.getAdultById(payment.getRequesterId());
        String mailSendTo = requester.getEmail();
        if (mailSendTo == null || mailSendTo.equals(""))
        {
            logger.warn("notifyPaymentByMail() e-citizen has no email address, returning");
            return "";
        }
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        Map<String, String> mailMap = lacb.getMailAsMap("hasPaymentNotification", "getPaymentNotificationData",
                modelMail);
        if (mailMap == null) throw new CvqException("payment.model.template.noexist");
        String mailSubject = mailMap.get("mailSubject") != null ? mailMap.get("mailSubject") : "";
        String mailBodyFilename = mailMap.get("mailData") != null ? mailMap.get("mailData") : "";
        String mailBody = localAuthorityRegistry.getBufferedLocalAuthorityResource(Type.TXT, mailBodyFilename, false);
        if (mailBody == null)
        {
            logger.warn("notifyPaymentByMail() did not find mail template " + mailBodyFilename
                    + " for local authority " + SecurityContext.getCurrentSite().getName());
            return "";
        }
        // Mail body variable
        mailBody = mailBody.replace("${broker}", payment.getBroker() != null ? payment.getBroker() : "");
        mailBody = mailBody.replace("${url}", lacb.getDefaultServerName() != null ? lacb.getDefaultServerName() : "");
        mailBody = mailBody.replace("${cvqReference}", payment.getCvqReference() != null ? payment.getCvqReference()
                : "");
        mailBody = mailBody.replace("${paymentMode}", payment.getPaymentMode() != null ? payment.getPaymentMode()
                .toString() : "");
        mailBody = mailBody.replace("${commitDate}", payment.getCommitDate() != null ? payment.getCommitDate()
                .toString() : "");
        mailService.send(null, mailSendTo, null, mailSubject, mailBody, pdf, pdf != null ? attachedName : "");
        return mailBody;
    }
}
