package org.libredemat.external.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItemDetail;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalInvoiceItemDetail;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.ExternalServiceUtils;
import org.libredemat.security.SecurityContext;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.request.civil.BirthDetailsRequestDocument.BirthDetailsRequest;
import org.libredemat.xml.request.school.PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest;
import org.libredemat.xml.request.school.RecreationActivityRegistrationRequestDocument.RecreationActivityRegistrationRequest;
import org.libredemat.xml.request.school.SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest;
import org.libredemat.xml.request.school.SchoolRegistrationRequestDocument.SchoolRegistrationRequest;

import org.libredemat.modules.payment.schema.acc.AccountDetailType;
import org.libredemat.modules.payment.schema.acc.AccountDetailsRequestDocument;
import org.libredemat.modules.payment.schema.acc.AccountDetailsResponseDocument;
import org.libredemat.modules.payment.schema.acc.AccountDetailsRequestDocument.AccountDetailsRequest;
import org.libredemat.modules.payment.schema.ban.AccountUpdateType;
import org.libredemat.modules.payment.schema.ban.ContractUpdateType;
import org.libredemat.modules.payment.schema.ban.CreditAccountRequestDocument;
import org.libredemat.modules.payment.schema.ban.FamilyType;
import org.libredemat.modules.payment.schema.ban.InvoiceUpdateType;
import org.libredemat.modules.payment.schema.ban.PaymentType;
import org.libredemat.modules.payment.schema.ban.CreditAccountRequestDocument.CreditAccountRequest;
import org.libredemat.modules.payment.schema.cer.CheckExternalReferentialRequestDocument;
import org.libredemat.modules.payment.schema.cer.CheckExternalReferentialResponseDocument;
import org.libredemat.modules.payment.schema.cer.CheckExternalReferentialRequestDocument.CheckExternalReferentialRequest;
import org.libredemat.modules.payment.schema.cns.ConsumptionType;
import org.libredemat.modules.payment.schema.cns.GetConsumptionsRequestDocument;
import org.libredemat.modules.payment.schema.cns.GetConsumptionsResponseDocument;
import org.libredemat.modules.payment.schema.cns.GetConsumptionsRequestDocument.GetConsumptionsRequest;
import org.libredemat.modules.payment.schema.fam.FamilyAccountsRequestDocument;
import org.libredemat.modules.payment.schema.fam.FamilyAccountsResponseDocument;
import org.libredemat.modules.payment.schema.fam.FamilyAccountsRequestDocument.FamilyAccountsRequest;
import org.libredemat.modules.payment.schema.inv.InvoiceDetailType;
import org.libredemat.modules.payment.schema.inv.InvoiceDetailsRequestDocument;
import org.libredemat.modules.payment.schema.inv.InvoiceDetailsResponseDocument;
import org.libredemat.modules.payment.schema.inv.InvoiceDetailsRequestDocument.InvoiceDetailsRequest;
import org.libredemat.modules.payment.schema.rei.ExternalInformationType;
import org.libredemat.modules.payment.schema.rei.GetExternalInformationRequestDocument;
import org.libredemat.modules.payment.schema.rei.GetExternalInformationResponseDocument;
import org.libredemat.modules.payment.schema.rei.GetExternalInformationRequestDocument.GetExternalInformationRequest;
import org.libredemat.modules.payment.schema.sre.SendRequestRequestDocument;
import org.libredemat.modules.payment.schema.sre.SendRequestRequestDocument.SendRequestRequest;

public class SoapExternalService extends ExternalProviderServiceAdapter {

    private static Logger logger = Logger.getLogger(SoapExternalService.class);
    
    private String label;

    private SoapExternalServiceClient soapExternalServiceClient;
    
    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId, 
            String externalHomeFolderId, String externalId)
        throws CvqException {
        
        FamilyAccountsRequestDocument farDocument = 
            FamilyAccountsRequestDocument.Factory.newInstance();
        FamilyAccountsRequest far = farDocument.addNewFamilyAccountsRequest();
        far.setLocalAuthority(SecurityContext.getCurrentSite().getName());
        far.setHomeFolderId(homeFolderId);

        FamilyAccountsResponseDocument familyAccountsResponseDocument = 
            (FamilyAccountsResponseDocument) soapExternalServiceClient.getFamilyAccounts(farDocument);

        return ExternalServiceUtils.parseFamilyDocument(familyAccountsResponseDocument, getLabel());
    }

    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
        if (edai.getExternalItemId() == null
                || edai.getExternalApplicationId() == null
                || edai.getExternalHomeFolderId() == null) {
            edai = null;
            logger.debug("loadDepositAccountDetails() Received un-handled deposit account, returning");
            return;
        }
        
        AccountDetailsRequestDocument accountDetailsRequestDocument =
            AccountDetailsRequestDocument.Factory.newInstance();
        AccountDetailsRequest accountDetailsRequest = 
            accountDetailsRequestDocument.addNewAccountDetailsRequest();
        accountDetailsRequest.setAccountId(edai.getExternalItemId());
        accountDetailsRequest.setExternalApplicationId(Long.valueOf(edai.getExternalApplicationId()));
        accountDetailsRequest.setExternalFamilyAccountId(edai.getExternalHomeFolderId());

        // FIXME : hard-coded 3 months range
        Date dateTo = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateTo);
        accountDetailsRequest.setEndSearch(calendar);
        calendar.add(Calendar.MONTH, -3);
        accountDetailsRequest.setStartSearch(calendar);
        
        // Calls webservice
        AccountDetailsResponseDocument accountDetailsDocument = (AccountDetailsResponseDocument) 
            soapExternalServiceClient.loadAccountDetails(accountDetailsRequestDocument);

        AccountDetailType[] accountDetailTypes = 
            accountDetailsDocument.getAccountDetailsResponse().getAccountDetailArray();
        for (int i = 0; i < accountDetailTypes.length; i++) {
            AccountDetailType accountDetailType = accountDetailTypes[i];
            ExternalDepositAccountItemDetail edaiDetail = new ExternalDepositAccountItemDetail();
            edaiDetail.setDate(accountDetailType.getDate().getTime());
            edaiDetail.setHolderName(accountDetailType.getHolderName());
            edaiDetail.setHolderSurname(accountDetailType.getHolderSurname());
            edaiDetail.setPaymentId(accountDetailType.getPaymentAck());
            edaiDetail.setPaymentType(accountDetailType.getPaymentType());
            edaiDetail.setValue(accountDetailType.getValue());
            if (edai.getAccountDetails() == null)
                edai.setAccountDetails(new HashSet<ExternalDepositAccountItemDetail>());

            edai.addAccountDetail(edaiDetail);
        }
    }

    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
        if (eii.getExternalItemId() == null
                || eii.getExternalApplicationId() == null
                || eii.getExternalHomeFolderId() == null) {
            eii = null;
            return;
        }
        
        InvoiceDetailsRequestDocument invoiceDetailsRequestDocument =
            InvoiceDetailsRequestDocument.Factory.newInstance();
        InvoiceDetailsRequest invoiceDetailsRequest =
            invoiceDetailsRequestDocument.addNewInvoiceDetailsRequest();
        invoiceDetailsRequest.setInvoiceId(eii.getExternalItemId());
        invoiceDetailsRequest.setExternalApplicationId(Long.valueOf(eii.getExternalApplicationId()));
        invoiceDetailsRequest.setExternalFamilyAccountId(eii.getExternalHomeFolderId());

        // Calls webservice
        InvoiceDetailsResponseDocument invoiceDetailsResponseDocument = (InvoiceDetailsResponseDocument) 
            soapExternalServiceClient.loadInvoiceDetails(invoiceDetailsRequestDocument);

        InvoiceDetailType[] invoiceDetailTypes = 
            invoiceDetailsResponseDocument.getInvoiceDetailsResponse().getInvoiceDetailArray();
        for (int i = 0; i < invoiceDetailTypes.length; i++) {
            ExternalInvoiceItemDetail eiiDetail = new ExternalInvoiceItemDetail();
            InvoiceDetailType invoiceDetailType = invoiceDetailTypes[i];
            eiiDetail.setSubjectName(invoiceDetailType.getChildName());
            eiiDetail.setSubjectSurname(invoiceDetailType.getChildSurname());
            eiiDetail.setLabel(invoiceDetailType.getLabel());
            eiiDetail.setQuantity(invoiceDetailType.getQuantity());
            eiiDetail.setUnitPrice(invoiceDetailType.getUnitPrice());
            eiiDetail.setValue(invoiceDetailType.getValue());
            if (eii.getInvoiceDetails() == null)
                eii.setInvoiceDetails(new HashSet<ExternalInvoiceItemDetail>());

            eii.getInvoiceDetails().add(eiiDetail);
        }
    }

    public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems, String cvqReference,
            String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, 
            Date validationDate) throws CvqException {
        
        CreditAccountRequestDocument creditAccountRequestDocument = 
            CreditAccountRequestDocument.Factory.newInstance();
        CreditAccountRequest creditAccountRequest = creditAccountRequestDocument.addNewCreditAccountRequest();

        FamilyType familyType = creditAccountRequest.addNewFamily();
        familyType.setId(homeFolderId);
        familyType.setZip(SecurityContext.getCurrentSite().getPostalCode());

        Calendar calendar = Calendar.getInstance();
        int totalAmount = 0;
        String broker = null;
        for (PurchaseItem purchaseItem : purchaseItems) {
            // purchase items in a payment transaction can not belong to more than one broker
            // so take the first we meet
            if (broker == null)
                broker = purchaseItem.getSupportedBroker();
            totalAmount += purchaseItem.getAmount().intValue();
        }
        PaymentType paymentType = creditAccountRequest.addNewPayment();
        paymentType.setPaymentBroker(broker);
        paymentType.setCvqAck(cvqReference);
        paymentType.setPaymentAck(bankReference);
        paymentType.setPaymentAmount(totalAmount);
        paymentType.setPaymentType("CB");
        calendar.setTime(validationDate);
        paymentType.setPaymentDate(calendar);

        List<AccountUpdateType> accountUpdateTypes = new ArrayList<AccountUpdateType>();
        List<ContractUpdateType> contractUpdateTypes = new ArrayList<ContractUpdateType>();
        List<InvoiceUpdateType> invoiceUpdateTypes = new ArrayList<InvoiceUpdateType>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            if (purchaseItem instanceof ExternalDepositAccountItem) {
                ExternalDepositAccountItem edai = (ExternalDepositAccountItem) purchaseItem;
                AccountUpdateType updateType = AccountUpdateType.Factory.newInstance();
                updateType.setAccountId(edai.getExternalItemId());
                updateType.setExternalApplicationId(Long.valueOf(edai.getExternalApplicationId()));
                updateType.setExternalFamilyAccountId(edai.getExternalHomeFolderId());
                updateType.setAccountNewValue(edai.getOldValue().intValue() + edai.getAmount().intValue());
                updateType.setAccountOldValue(edai.getOldValue().intValue());
                calendar.setTime(edai.getOldValueDate());
                updateType.setAccountOldValueDate(calendar);
                accountUpdateTypes.add(updateType);
            }

            if (purchaseItem instanceof ExternalInvoiceItem) {
                ExternalInvoiceItem eii = (ExternalInvoiceItem) purchaseItem;
                InvoiceUpdateType updateType = InvoiceUpdateType.Factory.newInstance();
                updateType.setInvoiceId(eii.getExternalItemId());
                updateType.setExternalApplicationId(Long.valueOf(eii.getExternalApplicationId()));
                updateType.setExternalFamilyAccountId(eii.getExternalHomeFolderId());
                updateType.setAmount(eii.getAmount().intValue());
                invoiceUpdateTypes.add(updateType);
            }

            if (purchaseItem instanceof ExternalTicketingContractItem) {
                ExternalTicketingContractItem etci = (ExternalTicketingContractItem) purchaseItem;
                ContractUpdateType updateType = ContractUpdateType.Factory.newInstance();
                updateType.setContractId(etci.getExternalItemId());
                updateType.setExternalApplicationId(Long.valueOf(etci.getExternalApplicationId()));
                updateType.setExternalFamilyAccountId(etci.getExternalHomeFolderId());
                updateType.setExternalIndividualId(etci.getExternalIndividualId());
                updateType.setCapwebctIndividualId(etci.getSubjectId());
                updateType.setPrice(etci.getUnitPrice().intValue());
                updateType.setQuantity(etci.getQuantity());
                updateType.setAmount(etci.getAmount().intValue());
                contractUpdateTypes.add(updateType);
            }
        }
        if (accountUpdateTypes.size() > 0)
            creditAccountRequest.addNewAccounts().setAccountArray(
                    accountUpdateTypes.toArray(new AccountUpdateType[]{}));
        if (contractUpdateTypes.size() > 0)
            creditAccountRequest.addNewContracts().setContractArray(
                    contractUpdateTypes.toArray(new ContractUpdateType[]{}));
        if (invoiceUpdateTypes.size() > 0)
            creditAccountRequest.addNewInvoices().setInvoiceArray(
                    invoiceUpdateTypes.toArray(new InvoiceUpdateType[]{}));
        
        soapExternalServiceClient.creditAccount(creditAccountRequestDocument);
    }

    public String sendRequest(XmlObject requestXml) throws CvqException {
        
        SendRequestRequestDocument sendRequestRequestDocument =
            SendRequestRequestDocument.Factory.newInstance();
        SendRequestRequest sendRequestRequest =
            sendRequestRequestDocument.addNewSendRequestRequest();

        RequestType request = (RequestType) requestXml;

        if (request instanceof SchoolRegistrationRequest)
            sendRequestRequest.setSchoolRegistrationRequest((SchoolRegistrationRequest) request);
        else if (request instanceof SchoolCanteenRegistrationRequest)
            sendRequestRequest.setSchoolCanteenRegistrationRequest((SchoolCanteenRegistrationRequest) request);
        else if (request instanceof PerischoolActivityRegistrationRequest)
            sendRequestRequest.setPerischoolActivityRegistrationRequest((PerischoolActivityRegistrationRequest) request);
        else if (request instanceof RecreationActivityRegistrationRequest)
            sendRequestRequest.setRecreationActivityRegistrationRequest((RecreationActivityRegistrationRequest) request);
        else if (request instanceof BirthDetailsRequest)
            sendRequestRequest.setBirthDetailsRequest((BirthDetailsRequest) request);
        else
            sendRequestRequest.setRequest(request);
        sendRequestRequest.setRequestTypeLabel(request.getRequestTypeLabel());
        soapExternalServiceClient.sendRequest(sendRequestRequestDocument);
        return "";
    }

    public boolean supportsConsumptions() {
        return false;
    }

    public boolean handlesTraces() {
        return false;
    }

    public List<String> checkExternalReferential(final XmlObject requestXml) {
        CheckExternalReferentialRequestDocument checkExternalReferentialRequestDocument =
            CheckExternalReferentialRequestDocument.Factory.newInstance();
        CheckExternalReferentialRequest checkExternalReferentialRequest =
            checkExternalReferentialRequestDocument.addNewCheckExternalReferentialRequest();
        checkExternalReferentialRequest.setRequest((RequestType) requestXml);
        
        CheckExternalReferentialResponseDocument checkExternalReferentialResponseDocument =
            (CheckExternalReferentialResponseDocument) soapExternalServiceClient.checkExternalReferential(checkExternalReferentialRequestDocument);
        String[] messages = 
            checkExternalReferentialResponseDocument.getCheckExternalReferentialResponse().getMessageArray();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < messages.length; i++) {
            result.add(messages[i]);
        }

        return result;
    }

    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        GetExternalInformationRequestDocument getExternalInformationRequestDocument =
            GetExternalInformationRequestDocument.Factory.newInstance();
        GetExternalInformationRequest getExternalInformationRequest =
            getExternalInformationRequestDocument.addNewGetExternalInformationRequest();
        getExternalInformationRequest.setLocalAuthority(SecurityContext.getCurrentSite().getName());
        RequestType requestType = (RequestType) requestXml;
        getExternalInformationRequest.setRequestId(requestType.getId());

        GetExternalInformationResponseDocument getExternalInformationResponseDocument =
            (GetExternalInformationResponseDocument) soapExternalServiceClient.loadExternalInformation(getExternalInformationRequestDocument);
        ExternalInformationType[] externalInformations = 
            getExternalInformationResponseDocument.getGetExternalInformationResponse().getExternalInformationArray();
        Map<String, Object> result = new HashMap<String, Object>();
        for (int i = 0; i < externalInformations.length; i++) {
            ExternalInformationType externalInformation = externalInformations[i];
            result.put(externalInformation.getKey(), externalInformation.getValue());
        }

        return result;
    }

    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
        throws CvqException {
        GetConsumptionsRequestDocument getConsumptionsRequestDocument =
            GetConsumptionsRequestDocument.Factory.newInstance();
        GetConsumptionsRequest getConsumptionsRequest =
            getConsumptionsRequestDocument.addNewGetConsumptionsRequest();
        getConsumptionsRequest.setLocalAuthority(SecurityContext.getCurrentSite().getName());
        getConsumptionsRequest.setRequestId(key);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFrom);
        getConsumptionsRequest.setDateFrom(calendar);
        calendar.setTime(dateTo);
        getConsumptionsRequest.setDateTo(calendar);

        GetConsumptionsResponseDocument getConsumptionsResponseDocument = 
            (GetConsumptionsResponseDocument) soapExternalServiceClient.getConsumptions(getConsumptionsRequestDocument);
        ConsumptionType[] consumptions = 
            getConsumptionsResponseDocument.getGetConsumptionsResponse().getConsumptionArray();
        Map<Date, String> result = new HashMap<Date, String>();
        for (int i = 0; i < consumptions.length; i++) {
            result.put(consumptions[i].getDate().getTime(), consumptions[i].getLabel());
        }
        
        return result;
    }

    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
        throws CvqConfigurationException {
    }

    /** ***** Not Implemented methods ****** */
    /** *********************************** */

    public String helloWorld() throws CvqException {
        return null;
    }

    /** ******************************* */

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSoapExternalServiceClient(SoapExternalServiceClient soapExternalServiceClient) {
        this.soapExternalServiceClient = soapExternalServiceClient;
    }
}
