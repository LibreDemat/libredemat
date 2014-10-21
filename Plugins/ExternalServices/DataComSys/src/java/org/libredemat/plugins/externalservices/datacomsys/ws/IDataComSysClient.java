package org.libredemat.plugins.externalservices.datacomsys.ws;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.xmlbeans.XmlObject;

import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;
import gethello.GetFactureDetailRequestResponseDocument;
import gethello.GetFactureRequestResponseDocument;

public interface IDataComSysClient {
    /**
     * 
     * @param endPoint
     * @param purchaseItems
     * @param cvqReference
     * @param bankReference
     * @param homeFolderId
     * @param externalHomeFolderId
     * @param externalId
     * @param validationDate
     * @throws CvqException
     * @throws ParseException
     */
    public void getCreditAccount(String endPoint, Collection<PurchaseItem> purchaseItems,
            String cvqReference, String bankReference, Long homeFolderId,
            String externalHomeFolderId, String externalId, Date validationDate) throws CvqException, ParseException;

    /**
     * 
     * @param endPoint
     * @param homeFolderId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    public GetFactureRequestResponseDocument getHomeFolderAccountsDocument(String endPoint, Long homeFolderId)
            throws CvqException, ParseException;
    /**
     * 
     * @param endPoint
     * @param homeFolderId
     * @param itemId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    public GetFactureDetailRequestResponseDocument getDetailInvoice(String endPoint, String homeFolderId, String itemId) 
            throws CvqException, ParseException;
    
    /**
     * 
     * @param endPoint
     * @param xmlRequest
     * @param type
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    public HashMap<String, String> getRegistrationCreation(String endPoint, XmlObject xmlRequest) 
           throws CvqException, ParseException;
    /**
     * 
     * @param endPoint
     * @param xmlRequest
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    public HashMap<String, String> getRegistrationModification(String endPoint, XmlObject xmlRequest) 
            throws CvqException, ParseException;
}
