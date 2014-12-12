package org.libredemat.plugins.externalservices.cirilelections.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.ws.client.core.WebServiceTemplate;

import org.libredemat.modules.payment.schema.sre.SendRequestRequestDocument;
import org.libredemat.modules.payment.schema.sre.SendRequestRequestDocument.SendRequestRequest;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.security.SecurityContext;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.request.election.StandardElectoralRollRegistrationRequestDocument.StandardElectoralRollRegistrationRequest;

public class CirilElectionsService extends ExternalProviderServiceAdapter {

    private static Logger logger = Logger.getLogger(CirilElectionsService.class);
    
    private String label;

    private WebServiceTemplate webServiceTemplate;

    private Map<String, ExternalServiceBean> localAuthoritySpecificConfiguration =
            new HashMap<String, ExternalServiceBean>();

    @Override
    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId, 
            String externalHomeFolderId, String externalId)
        throws CvqException {
        return Collections.emptyMap();
    }

    @Override
    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
    }

    @Override
    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
    }

    @Override
    public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems, String cvqReference,
            String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, 
            Date validationDate) throws CvqException {
        
    }

    @Override
    public String sendRequest(XmlObject requestXml) throws CvqException {

        SendRequestRequestDocument sendRequestRequestDocument =
            SendRequestRequestDocument.Factory.newInstance();
        SendRequestRequest sendRequestRequest =
            sendRequestRequestDocument.addNewSendRequestRequest();

        RequestType request = (RequestType) requestXml;

        if (request instanceof StandardElectoralRollRegistrationRequest)
            sendRequestRequest.setStandardElectoralRollRegistrationRequest((StandardElectoralRollRegistrationRequest) request);
        else
            sendRequestRequest.setRequest(request);
        sendRequestRequest.setRequestTypeLabel(request.getRequestTypeLabel());
        logger.debug("sendRequest() sending " + sendRequestRequestDocument.xmlText());
        webServiceTemplate.marshalSendAndReceive(getConfigurationProperty("EndPoint"), sendRequestRequestDocument);
        return "";
    }

    @Override
    public boolean supportsConsumptions() {
        return false;
    }

    @Override
    public boolean handlesTraces() {
        return false;
    }

    @Override
    public List<String> checkExternalReferential(final XmlObject requestXml) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        return Collections.emptyMap();
    }

    @Override
    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
        throws CvqException {
        return Collections.emptyMap();
    }

    @Override
    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
        throws CvqConfigurationException {
        localAuthoritySpecificConfiguration.put(localAuthorityName, externalServiceBean);
    }

    private String getConfigurationProperty(String propertyName) {
        String propertySpecificValue = (String)
            localAuthoritySpecificConfiguration.get(SecurityContext.getCurrentSite().getName()).getProperty(propertyName); 
        return propertySpecificValue;
    }

    /** ***** Not Implemented methods ****** */
    /** *********************************** */

    @Override
    public String helloWorld() throws CvqException {
        return null;
    }

    /** ******************************* */

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}
