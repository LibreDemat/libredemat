package org.libredemat.plugins.externalservices.clever.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import cleversms.services.soap.*;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.request.LocalReferentialEntry;
import org.libredemat.business.request.LocalReferentialType;
import org.libredemat.business.users.Adult;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.service.request.ILocalReferentialService;
import org.libredemat.service.request.IRequestService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.util.sms.ISmsProviderService;
import org.libredemat.xml.common.LocalReferentialDataType;
import org.libredemat.xml.request.leisure.SmsNotificationRequestDocument;

import cleversms.services.CleverSMSServiceProvider;

public class CleverService extends ExternalProviderServiceAdapter implements ISmsProviderService{
    private static Logger logger = Logger.getLogger(CleverService.class);
    
    private static final String YES_LABEL = "oui";
    private static final String NO_LABEL = "non";
    private String label;

    private ILocalReferentialService localReferentialService;
    private IRequestService smsNotificationRequestService;
    private IUserSearchService userSearchService;

    private String endportpath;
    private String username;
    private String password;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void send(String number, String message) throws CvqException {
        try {
            CleverSMSServiceProvider provider = new CleverSMSServiceProvider(
                    endportpath, username, password);

            CleverSMSMessageSEI smsMessageService = provider.getMessageService();
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.setMessage(message);
            // Hack to send internationalized phone numbers
            if (number.startsWith("06")) {
                smsMessage.setNumber("+33" + number.substring(1));
            } else {
                smsMessage.setNumber(number);
            }

            logger.debug("send() gonna send message " + message 
                    + " to " + number);
            smsMessageService.sendSMSMessage(smsMessage);

        } catch (ServiceException se) {
            logger.error("send() service exception : " + se.getMessage());
            se.printStackTrace();
            throw new CvqException("sms.technical_problem");
        } catch (InvalidNumberException ine) {
            logger.error("send() invalid number exception : " + ine.getMessage());
            ine.printStackTrace();            
            throw new CvqException("sms.invalid_number");
        } catch (NotEnoughCreditException nece) {
            logger.error("send() not enough credit exception : " + nece.getMessage());
            nece.printStackTrace();
            throw new CvqException("sms.not_enough_credit");
        } catch (RemoteException re) {
            logger.error("send() remote exception : " + re.getMessage());
            re.printStackTrace();
            throw new CvqException("sms.technical_problem");
        }
    }

    public String sendRequest(XmlObject requestXml) throws CvqException {
        try {

            if (!(requestXml instanceof SmsNotificationRequestDocument)) {
                logger.warn("sendRequest() received an un-managed request type, ignoring it");
                return null;
            }

            // CleverSMS Service Provider
            CleverSMSServiceProvider provider = new CleverSMSServiceProvider(endportpath, username, password);
            CleverSMSContactSEI contactService = provider.getContactService();

            SmsNotificationRequestDocument snr = (SmsNotificationRequestDocument) requestXml;
            Adult adult = userSearchService.getAdultById(snr.getSmsNotificationRequest().getSubject().getIndividual().getId());

            Integer cleverSmsContactId = null;
            if (snr.getSmsNotificationRequest().getCleverSmsContactId() != null && snr.getSmsNotificationRequest().getCleverSmsContactId().length() > 0)
                cleverSmsContactId = new Integer(snr.getSmsNotificationRequest().getCleverSmsContactId());

            // Build a CleverSMS Contact from the SmsNotificationRequest's
            // subject
            Contact cleverSmsContact = new Contact();
            cleverSmsContact.setName(adult.getLastName());
            cleverSmsContact.setFirstname(adult.getFirstName());
            cleverSmsContact.setGsm(adult.getMobilePhone());
            // Subscriber's interests
            List<LocalReferentialDataType> interests = Arrays.asList(snr.getSmsNotificationRequest().getInterestsArray());
            List<ExtendValue> values = new ArrayList<ExtendValue>();
            for (LocalReferentialDataType interest : interests) {
                ExtendValue value = new ExtendValue();
                value.setKey(interest.getName());
                value.setValue(YES_LABEL);
                values.add(value);
            }
            // Not Subscribers's interests
            LocalReferentialType lrt = localReferentialService.getLocalReferentialType(
                smsNotificationRequestService.getLabel(), "Interests");
            Set<LocalReferentialEntry> lrtEntries = lrt.getEntries();
            for (LocalReferentialEntry lrtEntry : lrtEntries) {
                String lrtEntryKey = lrtEntry.getKey();
                ExtendValue value = new ExtendValue();
                value.setKey(lrtEntryKey);
                value.setValue(YES_LABEL);
                if (!values.contains(value)) {
                    value.setValue(NO_LABEL);
                    values.add(value);
                }
            }
            cleverSmsContact.setValues(values.toArray(new ExtendValue[values.size()]));
            
            // Create Contact
            if (cleverSmsContactId == null) {
                logger.debug("sendRequest() calling CleverSMSContactSEI.createContact()");
                int id = contactService.createContact(cleverSmsContact);
                return (new Integer(id)).toString();
            }
            // Update Contact
            else if (snr.getSmsNotificationRequest().getSubscription()) {
                cleverSmsContact.setId(cleverSmsContactId);
                try {
                    logger.debug("sendRequest() calling CleverSMSContactSEI.updateContact("
                            + cleverSmsContactId + ")");
                    contactService.updateContact(cleverSmsContact);
                    return snr.getSmsNotificationRequest().getCleverSmsContactId();
                } catch (ContactNotFoundException e) {
                    // Create a new CleverSMS Contact to bind to the
                    // SmsNotificationRequest's subject
                    int id = contactService.createContact(cleverSmsContact);
                    return (new Integer(id)).toString();
                }
            }
            // Remove Contact
            else {
                cleverSmsContact.setId(cleverSmsContactId);
                try {
                    logger.debug("sendRequest() calling CleverSMSContactSEI.removeContact("
                            + cleverSmsContactId + ")");
                    contactService.removeContact(cleverSmsContact);
                    return null;
                } catch (ContactNotFoundException e) {
                    // CleverSMS Contact has been already removed ...
                    return null;
                }
            }
        } catch (RemoteException e) {
            logger.error("sendRequest() SOAP access error : " + e.getMessage());
            throw new CvqException(e.getMessage());
        } catch (ServiceException e) {
            logger.error("sendRequest() service exception : " + e.getMessage());
            throw new CvqException(e.getMessage());
        }
    }

    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
            throws CvqConfigurationException {
        try {
            this.username = externalServiceBean.getProperty("username").toString();
            this.password = externalServiceBean.getProperty("password").toString();
            this.endportpath = externalServiceBean.getProperty("endportpath").toString();
        } catch (NullPointerException npe) {
            throw new CvqConfigurationException("NPE - CleverSmsService should be configured with : [username, password, endportpath]");
        }
    }

    public void creditHomeFolderAccounts(Collection purchaseItems, String cvqReference,
            String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, Date validationDate) throws CvqException {
        logger.info("creditHomeFolderAccounts() no action associated");
    }

    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId, String externalHomeFolderId, String externalId)
            throws CvqException {
        logger.info("getAccountsByHomeFolder() no action associated");
        return null;
    }

    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
            throws CvqException {
        logger.info("getConsumptionsByRequest() no action associated");
        return null;
    }

    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
        logger.info("loadDepositAccountDetails() no action associated");
    }

    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
        logger.info("loadInvoiceDetails() no action associated");
    }

    public String helloWorld() throws CvqException {
        return null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLocalReferentialService(ILocalReferentialService localReferentialService) {
        this.localReferentialService = localReferentialService;
    }

    public void setSmsNotificationRequestService(IRequestService smsNotificationRequestService) {
        this.smsNotificationRequestService = smsNotificationRequestService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public boolean supportsConsumptions() {
        return false;
    }

    public boolean handlesTraces() {
        return false;
    }

    public List<String> checkExternalReferential(final XmlObject requestXml) {
        return new ArrayList<String>(0);
    }

    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        return Collections.emptyMap();
    }
}
