package org.libredemat.external.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.users.Individual;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.IExternalProviderService;


/**
 * Empty implementation of the {@link IExternalProviderService external provider service interface}
 * that is meant to be used by external services who wish to retrieve requests from
 * LibreDemat (instead of waiting for LibreDemat to send them).
 *  
 */
public class EntryPointExternalService extends ExternalProviderServiceAdapter {
    private String label;
    
    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
            throws CvqConfigurationException {
    }
    
    public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems,
            String cvqReference, String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, Date validationDate)
            throws CvqException {
    }

    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId, String externalHomeFolderId, String externalId)
            throws CvqException {
        return null;
    }
    
    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
            throws CvqException {
        return null;
    }
    
    public Map<Individual, Map<String, String>> getIndividualAccountsInformation(Long homeFolderId, String externalHomeFolderId, String externalId)
            throws CvqException {
        return null;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String helloWorld() throws CvqException {
        return null;
    }
    
    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
    }
    
    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
    }
    
    public String sendRequest(XmlObject requestXml) throws CvqException {
        return null;
    }

    public boolean supportsConsumptions() {
        return false;
    }

    public boolean handlesTraces() {
        return true;
    }

    public List<String> checkExternalReferential(final XmlObject requestXml) {
        return new ArrayList<String>(0);
    }

    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        return Collections.emptyMap();
    }
}
