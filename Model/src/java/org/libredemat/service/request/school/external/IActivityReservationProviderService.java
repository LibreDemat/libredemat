package org.libredemat.service.request.school.external;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import enfanceServicesEnfance.Activities;
import enfanceServicesEnfance.impl.ActivitiesImpl;

import org.jdom.Document;
import org.libredemat.business.users.Child;
import org.libredemat.exception.CvqException;

public interface IActivityReservationProviderService {
    /**
     * Retriev information for the 'pivot' page
     * @param homeFolderId
     * @param start
     * @param end
     * @param sessionId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    LinkedHashMap<Child,Activities[]> getReservationResume(Long homeFolderId, Date start, Date end, String sessionId) 
            throws CvqException, ParseException;
    /**
     * retreive information for one activity of one child during a month
     * @param activityCode
     * @param folderId
     * @param externalFolderId
     * @param childId
     * @param externalChildId
     * @param start
     * @param end
     * @param sessionId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    Map<String,Object> getReservationActivity(String activityCode, String folderId, String externalFolderId, 
            String childId, String externalChildId, Date start, Date end,  String sessionId) 
                    throws CvqException, ParseException;
    /**
     * Validate amount to pay with the business soft
     * @param folderId
     * @param externalFolderId
     * @param amountInCent
     * @param sessionId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    Map<String,Object> getAmountVerification(String folderId, String externalFolderId, 
            Integer amountInCent, String sessionId) 
            throws CvqException, ParseException;
    /**
     * Return to the business soft the payment state
     * @param folderId
     * @param externalFolderId
     * @param returnType
     * @param sessionId
     * @throws CvqException
     * @throws ParseException
     */
    void getPaymentValidation(String folderId, String externalFolderId, String returnType, String sessionId) 
            throws CvqException, ParseException;
    
    /**
     * Validate all reservation with the business soft
     * @param params
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    Map<String,Object> getAllReservation(Map<String,Object> params) throws CvqException, ParseException;
    
    /**
     * Validate reservation with the business soft
     * @param folderId
     * @param externalFolderId
     * @param reservationItems
     * @param sessionId
     * @return
     * @throws CvqException
     * @throws ParseException
     */
    Map<String,Object> getUpdateReservation(String folderId, String externalFolderId, 
            Map reservationItems, String sessionId) throws CvqException, ParseException;
    /**
     * Cancel reservation on the business soft
     * @param sessionId
     * @throws CvqException
     * @throws ParseException
     */
    void getCancelReservation(String sessionId) throws CvqException, ParseException;
    
    public Document getInterractionReservation(Map<String, Object> params) throws CvqException;

    public Document getSchoolsOfChild(Map<String, Object> params) throws CvqException;

    public boolean isServerStarted();
    
}
