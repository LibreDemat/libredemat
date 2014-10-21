package org.libredemat.plugins.externalservices.datacomsys.ws.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;

import fr.capwebct.capdemat.plugins.externalservices.datacomsys.ws.IDataComSysClient;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.xml.request.ecitizen.HomeFolderModificationRequestDocument.HomeFolderModificationRequest;
import gethello.GetFactureDetailRequestDocument;
import gethello.GetFactureDetailRequestDocument.GetFactureDetailRequest;
import gethello.GetFactureRequestDocument;
import gethello.GetFactureRequestDocument.GetFactureRequest;
import gethello.GetFactureDetailRequestResponseDocument;
import gethello.GetFactureRequestResponseDocument;
import gethello.GetFamilyCreationRequestDocument;
import gethello.GetFamilyCreationRequestDocument.GetFamilyCreationRequest;
import gethello.GetFamilyCreationRequestResponseDocument;
import gethello.GetFamilyModificationRequestDocument;
import gethello.GetFamilyModificationRequestDocument.GetFamilyModificationRequest;
import gethello.GetFamilyModificationRequestResponseDocument;
import gethello.GetPaiementRequestDocument;
import gethello.GetPaiementRequestDocument.GetPaiementRequest;
import gethello.GetPaiementRequestResponseDocument;

public class DataComSysClient implements IDataComSysClient {
    private Logger logger = Logger.getLogger(DataComSysClient.class);
    private WebServiceTemplate dataComSysClientService;
    
    @Override
    public void getCreditAccount(String endPoint, Collection<PurchaseItem> purchaseItems, String cvqReference,
            String bankReference, Long homeFolderId, String externalHomeFolderId,
            String externalId, Date validationDate) throws CvqException, ParseException {
        dataComSysClientService.setDefaultUri(endPoint);
        
        GetPaiementRequestDocument gprd = GetPaiementRequestDocument.Factory.newInstance();
        GetPaiementRequest gpr = gprd.addNewGetPaiementRequest();
        int total = 0;
        for (PurchaseItem purchaseItem : purchaseItems) {
            total = total + purchaseItem.getAmount().intValue();
        }        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");        
        String xmlEnveloppe = "<bank-transaction version=\"1.0\">";
        xmlEnveloppe += "<payment payment-date=\"" + simpleDateFormat.format(validationDate) + "\" ";
        xmlEnveloppe += "payment-amount=\""+ total +"\" payment-ack=\""+ bankReference +"\" ";
        xmlEnveloppe += "cvq-ack=\"" + cvqReference +"\" />";
        xmlEnveloppe += "<family id=\"" + homeFolderId + "\" ";
        //xmlEnveloppe += "<family id=\"" + numCompte + "\" ";
        xmlEnveloppe += "zip=\"" + SecurityContext.getCurrentSite().getPostalCode() + "\" />";
        xmlEnveloppe += "<invoices>";
        Iterator<PurchaseItem> iter = purchaseItems.iterator();
        while (iter.hasNext()) {
            ExternalInvoiceItem purchaseItem = (ExternalInvoiceItem) iter.next();
            xmlEnveloppe += "<invoice amount=\" " + purchaseItem.getAmount().intValue() + "\" ";
            xmlEnveloppe += "external-application-id=\"0\" ";
            xmlEnveloppe += "external-family-account-id=\"\" ";
            xmlEnveloppe += "invoice-id=\""+ purchaseItem.getExternalItemId() +"\"";
            xmlEnveloppe += "/>";
        }       
        xmlEnveloppe += "</invoices></bank-transaction>";
        gpr.setMonPaiementRecu(xmlEnveloppe);
        GetPaiementRequestResponseDocument gprrd;
        logger.debug("Credit account soap enveloppe : " + gprd.toString());
        try {
            gprrd = (GetPaiementRequestResponseDocument) dataComSysClientService.marshalSendAndReceive(gprd);
        } catch (XmlMappingException ex) {
            logger.error("error treating request : " + ex);
            throw new CvqException("Erreur lors du traitement de la demande");
        } catch (WebServiceClientException ew) {
            logger.error("error sending request : " + ew);
            throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
        }
    }
    
    @Override
    public GetFactureRequestResponseDocument getHomeFolderAccountsDocument(String endPoint,
            Long homeFolderId) throws CvqException, ParseException {
        dataComSysClientService.setDefaultUri(endPoint);
        GetFactureRequestDocument gfrd = GetFactureRequestDocument.Factory.newInstance();
        GetFactureRequest gfr = gfrd.addNewGetFactureRequest();
        gfr.setIdFamily(String.valueOf(homeFolderId));
        logger.debug("getAccount information request : " + gfrd.toString());
        GetFactureRequestResponseDocument gfrrd;
        try {
            gfrrd = (GetFactureRequestResponseDocument) dataComSysClientService.marshalSendAndReceive(gfrd);
            logger.debug("getAccount information reponse : " + gfrrd.toString());
        } catch (XmlMappingException ex) {
            logger.error("error treating request : " + ex);
            throw new CvqException("Erreur lors du traitement de la demande");
        } catch (WebServiceClientException ew) {
            logger.error("error sending request : " + ew);
            throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
        }
        return gfrrd;
    }
    
    @Override
    public GetFactureDetailRequestResponseDocument getDetailInvoice(String endPoint,
            String homeFolderId, String itemId) throws CvqException, ParseException {
        dataComSysClientService.setDefaultUri(endPoint);
        GetFactureDetailRequestDocument gfdrd = GetFactureDetailRequestDocument.Factory.newInstance();
        GetFactureDetailRequest gfdr = gfdrd.addNewGetFactureDetailRequest();
        gfdr.setIdFamily(String.valueOf(homeFolderId));
        gfdr.setIdFacture(itemId);
        logger.debug("get detail facture request ; " + gfdrd.toString());
        GetFactureDetailRequestResponseDocument gfdrrd;
        try {
            gfdrrd = (GetFactureDetailRequestResponseDocument) dataComSysClientService.marshalSendAndReceive(gfdrd);
            logger.debug("get detail facture response ; " + gfdrrd.toString());        
        } catch (XmlMappingException ex) {
            logger.error("error treating request : " + ex);
            throw new CvqException("Erreur lors du traitement de la demande");
        } catch (WebServiceClientException ew) {
            logger.error("error sending request : " + ew);
            throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
        }
        
        return gfdrrd;
    }
    
    @Override
    public HashMap<String, String> getRegistrationCreation(String endPoint, XmlObject xmlRequest
            ) throws CvqException, ParseException {
        dataComSysClientService.setDefaultUri(endPoint);
        HashMap<String, String> registrationResponse = new HashMap<String, String>();
        GetFamilyCreationRequestDocument gfcrd = GetFamilyCreationRequestDocument.Factory.newInstance();
        GetFamilyCreationRequest gfcr = gfcrd.addNewGetFamilyCreationRequest();
        gfcr.setMaFamilleCreeRecu(xmlRequest.xmlText());
        GetFamilyCreationRequestResponseDocument gfcrrd;
        logger.debug("Registration request : " + gfcrd.toString());
        try {
            gfcrrd = (GetFamilyCreationRequestResponseDocument) dataComSysClientService.marshalSendAndReceive(gfcrd);            
            logger.debug("Registration response : "+ gfcrrd.toString());
            registrationResponse.put("indMapp", "ok");
        } catch (XmlMappingException ex) {
            logger.error("error treating request : " + ex);
            registrationResponse.put("error", ex.getMessage());
            //throw new CvqException("Erreur lors du traitement de la demande");
        } catch (WebServiceClientException ew) {
            logger.error("error sending request : " + ew);
            registrationResponse.put("error", ew.getMessage());
            //throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
        }
        return registrationResponse;
    }
    
    @Override
    public HashMap<String, String> getRegistrationModification(String endPoint, XmlObject xmlRequest)
            throws CvqException, ParseException {
        HashMap<String, String> registrationResponse = new HashMap<String, String>();
        GetFamilyModificationRequestDocument gfmrd = GetFamilyModificationRequestDocument.Factory.newInstance();
        GetFamilyModificationRequest gfmr = gfmrd.addNewGetFamilyModificationRequest();
        gfmr.setMaFamilleModifRecu(xmlRequest.xmlText());
        logger.debug("Modification request : " + gfmrd.toString());
        GetFamilyModificationRequestResponseDocument gfmrrd;
        try {
            gfmrrd = (GetFamilyModificationRequestResponseDocument) dataComSysClientService.marshalSendAndReceive(gfmrd);
            logger.debug("Modification response : "+ gfmrrd.toString());
            registrationResponse.put("indMapp", "ok");
        } catch (XmlMappingException ex) {
            logger.error("error treating request : " + ex);
            registrationResponse.put("error", ex.getMessage());
            //throw new CvqException("Erreur lors du traitement de la demande");
        } catch (WebServiceClientException ew) {
            logger.error("error sending request : " + ew);
            registrationResponse.put("error", ew.getMessage());
            //throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
        }        
        return registrationResponse;
    }
    
   public void setDataComSysClientService(WebServiceTemplate dataComSysClientService) {
    this.dataComSysClientService = dataComSysClientService;
   } 
}
