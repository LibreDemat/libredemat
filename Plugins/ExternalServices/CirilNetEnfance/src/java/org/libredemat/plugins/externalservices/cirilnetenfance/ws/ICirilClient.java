package org.libredemat.plugins.externalservices.cirilnetenfance.ws;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import enfanceServicesEnfance.AccountDetailsResponseDocument;
import enfanceServicesEnfance.AmountVerificationResponseDocument;
import enfanceServicesEnfance.ExternalDocumentReponseDocument;
import enfanceServicesEnfance.FamilyDocument;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeResponseDocument;
import enfanceServicesEnfance.InvoiceDetailsResponseDocument;
import enfanceServicesEnfance.ReservationActivityPlanningResponseDocument;
import enfanceServicesEnfance.ReservationResumeResponseDocument;
import enfanceServicesEnfance.UpdateReservationResponseDocument;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;

public interface ICirilClient
{
	/**
	 * Get the family account information
	 * 
	 * @param endPoint
	 * @param zipCode
	 * @param homeFolderId
	 * @param externalCapdematId
	 * @param externalId
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public FamilyDocument getFamilyAccounts(String endPoint, String zipCode, Long homeFolderId,
			String externalCapdematId, String externalId) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param zipCode
	 * @param externalCapdematId
	 * @param externalId
	 * @param invoiceId
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public InvoiceDetailsResponseDocument getInvoiceDetail(String endPoint, String zipCode, String externalCapdematId,
			String externalId, String invoiceId) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param postalCode
	 * @param externalApplicationId
	 * @param externalId
	 * @param invoiceId
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public AccountDetailsResponseDocument getAccountDetatil(String endPoint, String postalCode,
			String externalApplicationId, String externalId, String accountId) throws CvqException, ParseException;

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
	 * @param postalCode
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public void getCreditAccount(String endPoint, Collection<PurchaseItem> purchaseItems, String cvqReference,
			String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId,
			Date validationDate, String postalCode, String broker) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param modelToXml
	 * @param registrationType
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 * @throws XmlException
	 */
	public HashMap<String, Object> getReturnRegistration(String endPoint, XmlObject modelToXml, String registrationType)
			throws CvqException, ParseException, XmlException;

	/**
	 * 
	 * @param endPoint
	 * @param externalFolderId
	 * @param FolderId
	 * @param start
	 * @param end
	 * @param sessionId
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public ReservationResumeResponseDocument getReservationResume(String endPoint, String externalFolderId,
			String FolderId, Date start, Date end, String sessionId) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
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
	public ReservationActivityPlanningResponseDocument getReservationPlanning(String endPoint, String activityCode,
			String folderId, String externalFolderId, String childId, String externalChildId, Date start, Date end,
			String sessionId) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param folderId
	 * @param externalFolderId
	 * @param childId
	 * @param externalChildId
	 * @param sessionId
	 * @param reservationItems
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 */
	public UpdateReservationResponseDocument getUpdateReservation(String endPoint, String folderId,
			String externalFolderId, String sessionId, Map<String, Map> reservationItems) throws CvqException,
			ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param sessionId
	 * @throws CvqException
	 * @throws ParseException
	 */
	public void getCancelReservation(String endPoint, String sessionId) throws CvqException, ParseException;

	/**
	 * 
	 * @param endPoint
	 * @param folderId
	 * @param externalFolderId
	 * @param returnType
	 * @param sessionId
	 * @throws CvqException
	 * @throws ParseException
	 */
	public void getPaymentReturnValidation(String endPoint, String folderId, String externalFolderId,
			String returnType, String sessionId) throws CvqException, ParseException;

	public AmountVerificationResponseDocument getAmountVerification(String endPoint, String folderId,
			String externalFolderId, Integer amountInCent, String sessionId) throws CvqException, ParseException;

	ExternalDocumentReponseDocument getExternalDocument(String endPointReservation, Long homeFolderId, String externalId)
			throws CvqException, ParseException;

	boolean isCirilServerStarted(String endPointReservation);

	GetEcoleSecteurCalculeeResponseDocument getChildSchool(String endPointSchool, String codeCommune,
			String codeEnfant, String codeExterneEnfant, String codeExterneFamille, String codeFamille,
			String codeBaseEleveNiveau, String dateReferenceAnneeScolaire, String dateNaissanceEnfant,
			String codeNiveau, String streetName, String city, String postalCode) throws CvqException, ParseException;

	public UpdateReservationResponseDocument getAllReservation(
			String endPointReservation, Map<String, Object> params) throws CvqException;			
}
