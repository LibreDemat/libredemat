package org.libredemat.plugins.paymentproviders.tipi.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.IPaymentProviderService;
import org.libredemat.service.payment.PaymentResultBean;
import org.libredemat.service.payment.PaymentResultStatus;
import org.libredemat.service.payment.PaymentServiceBean;
import org.libredemat.dao.payment.IPaymentDAO;


public class TipiService implements IPaymentProviderService {
    
    private String paymentUrl;
    private String callbackUrl;
    private IPaymentDAO paymentDAO;
    
    private String label;
    private static Logger logger = Logger.getLogger(TipiService.class);
    private static final String TIPI_NUMCLI = "numcli";
    private static final String TIPI_SAISIE = "saisie";

    private static final Random random = new Random();

    public void init() throws CvqException {
        if(paymentUrl == null || callbackUrl == null)
            throw new CvqException("No payment URL provided!");
    }

    @Override
    public URL doInitPayment(Payment payment, PaymentServiceBean paymentServiceBean)
            throws CvqException {
        
        StringBuffer urlParameters = new StringBuffer();
        
        String tipiNumCli = (String) paymentServiceBean.getProperty(TIPI_NUMCLI);
        urlParameters.append("&numcli="+tipiNumCli);
        
        String tipiExer = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        urlParameters.append("&exer="+tipiExer);
        
        if(!(payment.getPurchaseItems().iterator().next() instanceof ExternalAccountItem))
            throw new CvqException("PurchaseItem is not an ExternalAccountItem and cannot be paid with Tipi");
        String reference = ((ExternalAccountItem) payment.getPurchaseItems().iterator().next()).getExternalItemId();
        urlParameters.append("&refdet="+reference);

        String ref_complement = Integer.toString(random.nextInt(100));
        String tipiObjet = ref_complement;

        payment.setCvqReference(reference+ref_complement);
        paymentDAO.saveOrUpdate(payment);

        urlParameters.append("&objet="+tipiObjet);
        
        String tipiMontant = String.valueOf(payment.getAmount().intValue());
        urlParameters.append("&montant="+tipiMontant);
        
        String homeFolderResponsibleEmail = payment.getPaymentSpecificDataByKey(Payment.SPECIFIC_DATA_EMAIL);
        if (homeFolderResponsibleEmail != null && !homeFolderResponsibleEmail.equals("")) {
            urlParameters.append("&mel=").append(homeFolderResponsibleEmail);
        }

        String tipiUrlCl = "https://"+SecurityContext.getCurrentConfigurationBean().getDefaultServerName()+this.callbackUrl;
        urlParameters.append("&urlcl="+tipiUrlCl);
        
        urlParameters.append("&openInPopUp=true");

        String tipiSaisie = (String) paymentServiceBean.getProperty(TIPI_SAISIE);
        urlParameters.append("&saisie="+tipiSaisie);
        
        String url2 = paymentUrl + urlParameters;
        
        URL url = null;
        try {
            url = new URL(url2);
        } catch (MalformedURLException mue) {
            logger.error("initPayment() Error while creating URL object");
            mue.printStackTrace();
            throw new CvqException();
        }
        
        logger.debug("initPayment() Returning URL : "+ url.toString());
        return url;
    }

    @Override
    public PaymentResultBean doCommitPayment(Map<String, String> parameters,
            PaymentServiceBean paymentServiceBean) throws CvqException {
        PaymentResultStatus returnStatus = getStateFromParameters(parameters, paymentServiceBean);
        String cvq_reference = parameters.get("refdet")+(parameters.get("objet"));
        return new PaymentResultBean(returnStatus, cvq_reference, parameters.get("bankReference"));
    }

    @Override
    public void checkConfiguration(PaymentServiceBean paymentServiceBean)
            throws CvqConfigurationException {
        String tipiCli = (String) paymentServiceBean.getProperty(TIPI_NUMCLI);
        if(tipiCli == null)
            throw new CvqConfigurationException("Missing Tipi client number");
        
    }

    @Override
    public PaymentMode getPaymentMode() {
        return PaymentMode.INTERNET;
    }

    @Override
    public boolean handleParameters(Map<String, String> parameters) {
        return parameters.containsKey("resultrans");
    }

    @Override
    public PaymentResultStatus getStateFromParameters(Map<String, String> parameters,
            PaymentServiceBean paymentServiceBean) throws CvqException {
        if(!handleParameters(parameters))
            return PaymentResultStatus.UNKNOWN;
        
        String bankTransactionStatus = parameters.get("resultrans");
        
        if (bankTransactionStatus.equals("P")) {
            return PaymentResultStatus.OK;
        } else if (bankTransactionStatus.equals("R")) {
            return PaymentResultStatus.REFUSED;
        } else {
            return PaymentResultStatus.UNKNOWN;
        }
    }
    public void setPaymentUrl(String pu) { this.paymentUrl = pu; }
    public void setCallbackUrl(String cu) { this.callbackUrl = cu; }
    public void setLabel(String label) { this.label = label; }
    public void setPaymentDAO(IPaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }


    @Override
    public boolean allowMultiplePurchaseItems() {
        return false;
    }
}