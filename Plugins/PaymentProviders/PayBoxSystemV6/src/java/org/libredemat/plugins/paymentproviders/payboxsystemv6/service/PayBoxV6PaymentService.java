package org.libredemat.plugins.paymentproviders.payboxsystemv6.service;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.service.payment.IPaymentProviderService;
import org.libredemat.service.payment.PaymentResultBean;
import org.libredemat.service.payment.PaymentResultStatus;
import org.libredemat.service.payment.PaymentServiceBean;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Map;
import java.util.Random;

public class PayBoxV6PaymentService implements IPaymentProviderService {

    private static Logger logger = Logger.getLogger(PayBoxV6PaymentService.class);

    private static String PBX_SITE = "pbxSite";
    private static String PBX_RANG = "pbxRang";
    private static String PBX_IDENTIFIANT = "pbxIdentifiant";

    private static String PBX_RETOUR = "pbxRetour";
    private static String PBX_HMAC = "pbxHmac";

    private static String PBX_EFFECTUE = "pbxEffectue";
    private static String PBX_REFUSE = "pbxRefuse";
    private static String PBX_ANNULE = "pbxAnnule";
    private static String PBX_ATTENTE = "pbxAttente";

    private static String PBX_REPONDRE_A = "pbxRepondreA";

    private String label;
    private String paymentUrl;

    private static final Random random = new Random();
    private NumberFormat numberFormat = new DecimalFormat("###000");

    public void init() throws CvqException {

        if (paymentUrl == null)
            throw new CvqException("No payment URL provided !");
    }

    public void checkConfiguration(PaymentServiceBean paymentServiceBean)
            throws CvqConfigurationException {
        String pbxSite = (String) paymentServiceBean.getProperty(PBX_SITE);
        String pbxRang = (String) paymentServiceBean.getProperty(PBX_RANG);
        String pbxIdentifiant = (String) paymentServiceBean.getProperty(PBX_IDENTIFIANT);

        if (pbxSite == null ||
                pbxRang == null ||
                pbxIdentifiant == null)
            throw new CvqConfigurationException("Missing (at least) one of site, rang or identifiant");
    }

    public URL doInitPayment(Payment payment, PaymentServiceBean paymentServiceBean)
            throws CvqException {

        String pbxSite = (String) paymentServiceBean.getProperty(PBX_SITE);
        String pbxRang = (String) paymentServiceBean.getProperty(PBX_RANG);
        String pbxIdentifiant = (String) paymentServiceBean.getProperty(PBX_IDENTIFIANT);

        String pbxRetour = (String) paymentServiceBean.getProperty(PBX_RETOUR);
        String pbxHmac = (String) paymentServiceBean.getProperty(PBX_HMAC);

        String pbxEffectue = (String) paymentServiceBean.getProperty(PBX_EFFECTUE);
        String pbxRefuse = (String) paymentServiceBean.getProperty(PBX_REFUSE);
        String pbxAnnule = (String) paymentServiceBean.getProperty(PBX_ANNULE);
        String pbxAttente = (String) paymentServiceBean.getProperty(PBX_ATTENTE);

        String pbxRepondreA = (String) paymentServiceBean.getProperty(PBX_REPONDRE_A);

        StringBuffer urlParameters = new StringBuffer();

        urlParameters.append("PBX_SITE=").append(pbxSite);
        urlParameters.append("&PBX_RANG=").append(pbxRang);
        urlParameters.append("&PBX_IDENTIFIANT=").append(pbxIdentifiant);

        String montantTotal = numberFormat.format(payment.getAmount().doubleValue());
        urlParameters.append("&PBX_TOTAL=").append(montantTotal);
        urlParameters.append("&PBX_DEVISE=978");
        urlParameters.append("&PBX_TYPECARTE=CB");

        String reference = payment.getHomeFolderId() + "S" + random.nextInt();
        urlParameters.append("&PBX_CMD=").append(reference);
        payment.setCvqReference(reference);
        String homeFolderEmail = payment.getPaymentSpecificDataByKey(Payment.SPECIFIC_DATA_EMAIL);
        urlParameters.append("&PBX_PORTEUR=").append(homeFolderEmail);

        urlParameters.append("&PBX_RETOUR=").append(pbxRetour);

        urlParameters.append("&PBX_HASH=").append("Sha512");
        urlParameters.append("&PBX_TIME=").append(ISODateTimeFormat.dateHourMinuteSecond().print(new DateTime()));

        urlParameters.append("&PBX_EFFECTUE=").append(pbxEffectue);
        urlParameters.append("&PBX_REFUSE=").append(pbxRefuse);
        urlParameters.append("&PBX_ANNULE=").append(pbxAnnule);
        urlParameters.append("&PBX_ATTENTE=").append(pbxAttente);

        urlParameters.append("&PBX_REPONDRE_A=").append(pbxRepondreA);

        try {

            final byte[] bytesKey = DatatypeConverter.parseHexBinary(pbxHmac);
            final SecretKeySpec secretKey = new SecretKeySpec(bytesKey, "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKey);
            final byte[] macData = mac.doFinal(urlParameters.toString().getBytes("utf-8"));
            final byte[] hex = new Hex().encode(macData);
            String result = new String(hex, "utf-8");
            urlParameters.append("&PBX_HMAC=").append(result.toUpperCase());

            String url = paymentUrl + '?' + urlParameters.toString();
            logger.debug("initPayment() Returning URL : " + url);
            return new URL(url);

        } catch (NoSuchAlgorithmException nsae) {
            logger.error("initPayment() Algorith not found", nsae);
            throw new CvqException();
        } catch (MalformedURLException mue) {
            logger.error("initPayment() Error while creating URL object", mue);
            throw new CvqException();
        } catch (UnsupportedEncodingException uee) {
            logger.error("initPayment() Error while encoding parameters in UTF-8", uee);
            throw new CvqException();
        } catch (InvalidKeyException e) {
            logger.error("initPayment() Invalid key while hashing parameters", e);
            throw new CvqException();
        }
    }

    public PaymentMode getPaymentMode() {
        return PaymentMode.INTERNET;
    }

    public PaymentResultBean doCommitPayment(Map<String, String> parameters,
            PaymentServiceBean paymentServiceBean) throws CvqException {

        PaymentResultStatus returnStatus = getStateFromParameters(parameters, paymentServiceBean);
        return new PaymentResultBean(returnStatus, parameters.get("ref"),
                parameters.get("transNum"));
    }

    public PaymentResultStatus getStateFromParameters(Map<String, String> parameters,
            PaymentServiceBean paymentServiceBean) throws CvqException {

        if (!handleParameters(parameters))
            return PaymentResultStatus.UNKNOWN;

        String bankTransactionStatus = parameters.get("erreur");

        logger.debug("getStateFromParameters() Dealing with status : " + bankTransactionStatus);

        // compute return status according to bank transaction status
        if (bankTransactionStatus.equals("00000")) {
            return PaymentResultStatus.OK;
        } else if (bankTransactionStatus.startsWith("001") ||
                bankTransactionStatus.equals("00003") ||
                bankTransactionStatus.equals("00004") ||
                bankTransactionStatus.equals("00006") ||
                bankTransactionStatus.equals("00008") ||
                bankTransactionStatus.equals("00011") ||
                bankTransactionStatus.equals("00015") ||
                bankTransactionStatus.equals("00021") ||
                bankTransactionStatus.equals("00029") ||
                bankTransactionStatus.equals("00033") ||
                bankTransactionStatus.equals("00040")) {
            return PaymentResultStatus.REFUSED;
        } else if (bankTransactionStatus.equals("00001") ||
                bankTransactionStatus.equals("00030")) {
            return PaymentResultStatus.CANCELLED;
        } else {
            logger.warn("getStateFromParameters() Received unknown return status " + bankTransactionStatus);
            return PaymentResultStatus.UNKNOWN;
        }
    }

    public boolean handleParameters(Map<String, String> parameters) {
        if (parameters.get("transNum") != null)
            return true;
        else
            return false;
    }

    public final void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean allowMultiplePurchaseItems() {
        return true;
    }

}
