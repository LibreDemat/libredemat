package org.libredemat.plugins.externalservices.datacomsys.service;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import org.libredemat.plugins.externalservices.datacomsys.ws.IDataComSysClient;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalInvoiceItemDetail;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.xml.request.ecitizen.HomeFolderModificationRequestDocument.HomeFolderModificationRequest;
import org.libredemat.xml.request.school.PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest;
import org.libredemat.xml.request.school.RecreationActivityRegistrationRequestDocument.RecreationActivityRegistrationRequest;
import org.libredemat.xml.request.school.SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest;
import org.libredemat.xml.request.school.SchoolRegistrationRequestDocument.SchoolRegistrationRequest;
import gethello.GetFactureDetailRequestResponseDocument;
import gethello.GetFactureRequestResponseDocument;

public class DataComSysService extends ExternalProviderServiceAdapter {
    
    private static Logger logger = Logger.getLogger(DataComSysService.class);
    private static final String END_POINT = "EndPoint";
    private String endPoint;
    private String label;
    
    private IDataComSysClient dataComSysClient;
    private IExternalHomeFolderService externalHomeFolderService;

    @Override
    public String sendRequest(XmlObject requestXml) throws CvqException {
        HashMap<String, String> registration = new HashMap<String, String>();
        try {
            if(requestXml instanceof SchoolRegistrationRequest){
                SchoolRegistrationRequest srr = (SchoolRegistrationRequest) requestXml;
                logger.debug("SendRequest() : " + srr.getRequestTypeLabel());
                registration = dataComSysClient.getRegistrationCreation(endPoint, srr);
                if(registration.containsKey("error")){
                    String message = registration.get("error");
                    throw new CvqModelException("Retour du logiciel metier" + message);
                } else {
                    externalHomeFolderService.addHomeFolderMapping(label, srr.getHomeFolder().getId(), null);
                }                
            }
            if(requestXml instanceof SchoolCanteenRegistrationRequest){
                SchoolCanteenRegistrationRequest scrr = (SchoolCanteenRegistrationRequest) requestXml;
                logger.debug("SendRequest() : " + scrr.getRequestTypeLabel());
                registration = dataComSysClient.getRegistrationCreation(endPoint, scrr);
                if(registration.containsKey("error")){
                    String message = registration.get("error");
                    throw new CvqModelException("Retour du logiciel metier" + message);
                } else {
                    externalHomeFolderService.addHomeFolderMapping(label, scrr.getHomeFolder().getId(), null);
                }  
            }
            if(requestXml instanceof RecreationActivityRegistrationRequest){
                RecreationActivityRegistrationRequest rarr = (RecreationActivityRegistrationRequest) requestXml;
                logger.debug("SendRequest() : " + rarr.getRequestTypeLabel());
                registration = dataComSysClient.getRegistrationCreation(endPoint, rarr);
                if(registration.containsKey("error")){
                    String message = registration.get("error");
                    throw new CvqModelException("Retour du logiciel metier" + message);
                } else {
                    externalHomeFolderService.addHomeFolderMapping(label, rarr.getHomeFolder().getId(), null);
                }  
            }
            if(requestXml instanceof PerischoolActivityRegistrationRequest){
                PerischoolActivityRegistrationRequest parr = (PerischoolActivityRegistrationRequest) requestXml;
                logger.debug("SendRequest() : " + parr.getRequestTypeLabel());
                registration = dataComSysClient.getRegistrationCreation(endPoint, parr);
                if(registration.containsKey("error")){
                    String message = registration.get("error");
                    throw new CvqModelException("Retour du logiciel metier" + message);
                } else {
                    externalHomeFolderService.addHomeFolderMapping(label, parr.getHomeFolder().getId(), null);
                }  
            }
            if(requestXml instanceof HomeFolderModificationRequest){
                HomeFolderModificationRequest hfmr = (HomeFolderModificationRequest) requestXml;
                if(externalHomeFolderService.getHomeFolderMapping(label, hfmr.getHomeFolder().getId()) != null){
                    logger.debug("SendRequest() : " + hfmr.getRequestTypeLabel());
                    registration = dataComSysClient.getRegistrationModification(endPoint, hfmr);
                    if(registration.containsKey("error")){
                        String message = registration.get("error");
                        throw new CvqModelException("Retour du logiciel metier" + message);
                    }
                }
            }
        } catch (ParseException e) {
            logger.fatal(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
            throws CvqException {
        return null;
    }

    @Override
    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId,
            String externalHomeFolderId, String externalId) throws CvqException {
        
        Map<String, List<ExternalAccountItem> > results = 
                new LinkedHashMap<String, List<ExternalAccountItem> >();
        try {
            GetFactureRequestResponseDocument gfrrd = dataComSysClient.getHomeFolderAccountsDocument(endPoint, homeFolderId);
            Reader xmlRequest = new StringReader("<?xml version='1.0' encoding='UTF-8'?>" + gfrrd.xmlText());
            SAXBuilder saxb = new SAXBuilder();
            Document invoiceXMLDocument = saxb.build(xmlRequest);
            
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd");
                
                XPath xpath = XPath.newInstance("//bill");
                List billElements = (List) xpath.selectNodes(invoiceXMLDocument);
                List<ExternalAccountItem> bills = new ArrayList<ExternalAccountItem>();
                Iterator<Element> iter = billElements.iterator();
                
                while (iter.hasNext()) {
                    Element element = (Element) iter.next();
                    String  billId = element.getChild("bill-id").getValue();
                    
                    String billLabel = element.getChild("bill-label").getValue();
                    String billAmount = element.getChild("bill-value").getValue();
                    Date billIssueDate = null;
                    if(element.getChild("bill-date").getValue() != null ||
                            element.getChild("bill-date").getValue() !="" || 
                            element.getChild("bill-date").getValue().length() > 9)
                        billIssueDate = 
                            simpleDateFormat.parse(element.getChild("bill-date").getValue());
                    Date billExpirationDate = null;
                   /* if(element.getChild("bill-date-expiration").getValue() != null || 
                            element.getChild("bill-date-expiration").getValue() != "" || 
                            element.getChild("bill-date-expiration").getValue().length() > 9)
                            billExpirationDate = 
                                simpleDateFormat.parse(element.getChild("bill-date-expiration").getValue()); */
                    Date billPaymentDate = null;                
                    if(billIssueDate != null)
                        billPaymentDate = billIssueDate;
                        /*
                    if (element.getChild("bill-date-payment").getValue() != null ||
                            element.getChild("bill-date-payment").getValue() != "" ||
                            element.getChild("bill-date-payment").getValue().length() > 9)
                        billPaymentDate = 
                            simpleDateFormat.parse(element.getChild("bill-date-payment").getValue());
                            */
                    String billPayed = element.getChild("bill-payed").getValue();
                    Boolean isPayed = Boolean.FALSE;
                    if (billPayed.equals("1"))
                        isPayed = true;
                    Double amount = Double.valueOf(billAmount);
                    /*ExternalInvoiceItem eii = new ExternalInvoiceItem(billLabel, amount, amount, getLabel(), billId, billIssueDate, 
                            billExpirationDate, billPaymentDate, isPayed, null);*/ 
                    ExternalInvoiceItem eii = new ExternalInvoiceItem(billLabel, amount, amount, getLabel(), billId, billIssueDate, 
                            billExpirationDate, billPaymentDate, isPayed, "Régie Paybox des services scolaire",null);
                    
                    bills.add(eii);
                }
                results.put(IPaymentService.EXTERNAL_INVOICES, bills);
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
        return results;
    }

    @Override
    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {

    }

    @Override
    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
        try {            
        
            GetFactureDetailRequestResponseDocument gfdrrd = 
                    dataComSysClient.getDetailInvoice(endPoint, eii.getExternalHomeFolderId(), eii.getExternalItemId());
            Reader xmlRequest = new StringReader("<?xml version='1.0' encoding='UTF-8'?>" + gfdrrd.xmlText());
            SAXBuilder saxb = new SAXBuilder();
            Document invoiceDetailXMLDocument = saxb.build(xmlRequest);
            XPath xpath = XPath.newInstance("//invoiceDetail");
            List listDetail = (List) xpath.selectNodes(invoiceDetailXMLDocument);
            Iterator<Element> iterDetail = listDetail.iterator();
            while (iterDetail.hasNext()) {
                Element element = (Element) iterDetail.next();
                
                String labelDetail = element.getChild("label").getValue();
                String ChildName = element.getChild("child-name").getValue();
                String ChildSurName = element.getChild("child-surname").getValue();
                int value = 0;
                int quantity = 0;
                int unitPrice = 0;
                if(Integer.valueOf(element.getChild("value").getValue().trim()) != null)
                    value = Integer.valueOf(element.getChild("value").getValue().trim());
                if(Integer.valueOf(element.getChild("quantity").getValue().trim()) != null)
                    quantity = Integer.valueOf(element.getChild("quantity").getValue().trim());
                if(Integer.valueOf(element.getChild("unit-price").getValue().trim()) != null)
                    unitPrice = Integer.valueOf(element.getChild("unit-price").getValue().trim());
                      
                ExternalInvoiceItemDetail eiiDetail = new ExternalInvoiceItemDetail();
                eiiDetail.setLabel(labelDetail);
                eiiDetail.setSubjectName(ChildName);
                eiiDetail.setSubjectSurname(ChildSurName);
                eiiDetail.setValue(value);
                eiiDetail.setQuantity(new BigDecimal(quantity));
                eiiDetail.setUnitPrice(unitPrice);
                
                eii.addInvoiceDetail(eiiDetail);
            }
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
        
    }

    @Override
    public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems,
            String cvqReference, String bankReference, Long homeFolderId,
            String externalHomeFolderId, String externalId, Date validationDate)
            throws CvqException {
        try {
            dataComSysClient.getCreditAccount(endPoint, purchaseItems, cvqReference, 
                    bankReference, homeFolderId, externalHomeFolderId, externalId, validationDate);
        } catch (ParseException e) {
            logger.fatal(e.getMessage());
        }

    }

    @Override
    public void checkConfiguration(ExternalServiceBean externalServiceBean,
            String localAuthorityName) throws CvqConfigurationException {
        
        setEndPoint((String) externalServiceBean.getProperty(END_POINT));
        if (endPoint==null)
            throw new CvqConfigurationException("Missing " + END_POINT
                    + " configuration parameter");

    }

    @Override
    public String helloWorld() throws CvqException {
        return null;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
    public List<String> checkExternalReferential(XmlObject requestXml) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> loadExternalInformations(XmlObject requestXml) throws CvqException {
        return Collections.emptyMap();
    }
    
    
    
    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        super.setExternalHomeFolderService(externalHomeFolderService);
    }
    
    public void setDataComSysClient(IDataComSysClient dataComSysClient) {
        this.dataComSysClient = dataComSysClient;
    }
}
