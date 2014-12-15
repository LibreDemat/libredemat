package fr.capwebct.capdemat.plugins.externalservices.ovhsms.service;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;

import java.util.Locale;

import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.util.sms.ISmsProviderService;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;

import com.ovh.soapi.manager.ManagerPortType;
import com.ovh.soapi.manager.ManagerService;

public class OvhSmsService extends ExternalProviderServiceAdapter implements ISmsProviderService {
    private static Logger logger = Logger.getLogger(OvhSmsService.class);

    private String smsAccount;
    private String login;
    private String password;
    private String numberFrom;

    private String label;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void send(String number, String message) throws CvqException {
        ManagerPortType manager = new ManagerService().getManagerPort();

        if (number.startsWith("06"))
            number = "+33" + number.substring(1);

        logger.debug("SMScontent:"+message+",destination:"+number);
        int result = manager.telephonySmsUserSend(
                this.login,
                this.password,
                this.smsAccount,
                this.numberFrom,
                number,
                message,
                10, //the maximum time -in minute(s)- before the message is dropped, defaut is 10 minutes
                1, //the sms class: flash(0),phone display(1),SIM(2),toolkit(3), default is 1
                0, //the time -in minute(s)- to wait before sending the message, default is 0
                3, //the priority of the message (0 to 3), default is 3
                1, //the sms coding : 1 for 7 bit or 2 for unicode, default is 1
                "", // an optional tag
                true //do not display STOP clause in the message, this requires that this is not an advertising message
                );

        logger.debug("OvhSms response is : " + result);
    }

    public void init() {}

    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
            throws CvqConfigurationException {
        try {
            this.smsAccount = externalServiceBean.getProperty("smsAccount").toString();
            this.login = externalServiceBean.getProperty("login").toString();
            this.password = externalServiceBean.getProperty("password").toString();
            this.numberFrom = externalServiceBean.getProperty("numberFrom").toString();
        } catch (NullPointerException npe) {
            throw new CvqConfigurationException("NPE - OvhSmsService should be configured with : [username, password, endportpath]");
        }
    }

    public String sendRequest(XmlObject requestXml) throws CvqException {
        return "";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String helloWorld() throws CvqException {
        return null;
    }

    public boolean supportsConsumptions() {
        return false;
    }

    public boolean handlesTraces() {
        return true;
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


    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        return Collections.emptyMap();
    }

    public List<String> checkExternalReferential(final XmlObject requestXml) {
        return new ArrayList<String>(0);
    }
}
