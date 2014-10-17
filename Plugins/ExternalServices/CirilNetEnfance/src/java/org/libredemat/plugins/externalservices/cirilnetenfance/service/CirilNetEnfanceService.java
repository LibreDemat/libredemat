package org.libredemat.plugins.externalservices.cirilnetenfance.service;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import enfanceServicesEnfance.AccountDetailType;
import enfanceServicesEnfance.AccountDetailsResponseDocument;
import enfanceServicesEnfance.AccountDetailsResponseDocument.AccountDetailsResponse;
import enfanceServicesEnfance.AccountType;
import enfanceServicesEnfance.Activities;
import enfanceServicesEnfance.AmountVerificationResponseDocument;
import enfanceServicesEnfance.ChildCareCenterRegistrationResponseDocument;
import enfanceServicesEnfance.ChildCareCenterRegistrationResponseDocument.ChildCareCenterRegistrationResponse;
import enfanceServicesEnfance.ContractType;
import enfanceServicesEnfance.ExternalDocumentReponseDocument;
import enfanceServicesEnfance.FamilyAccounts;
import enfanceServicesEnfance.FamilyContracts;
import enfanceServicesEnfance.FamilyDocument;
import enfanceServicesEnfance.FamilyInvoices;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeResponseDocument;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeResponseDocument.GetEcoleSecteurCalculeeResponse.GestionErreur;
import enfanceServicesEnfance.GlobalSchoolServicesRegistrationResponseDocument;
import enfanceServicesEnfance.GlobalSchoolServicesRegistrationResponseDocument.GlobalSchoolServicesRegistrationResponse;
import enfanceServicesEnfance.HomeFolderModificationResponseDocument;
import enfanceServicesEnfance.HomeFolderModificationResponseDocument.HomeFolderModificationResponse;
import enfanceServicesEnfance.IndividualContractType;
import enfanceServicesEnfance.IndividualMappingType;
import enfanceServicesEnfance.InvoiceDetailType;
import enfanceServicesEnfance.InvoiceDetailsResponseDocument;
import enfanceServicesEnfance.InvoiceDetailsResponseDocument.InvoiceDetailsResponse;
import enfanceServicesEnfance.InvoiceType;
import enfanceServicesEnfance.PerischoolActivityRegistrationResponseDocument;
import enfanceServicesEnfance.PerischoolActivityRegistrationResponseDocument.PerischoolActivityRegistrationResponse;
//import enfanceServicesEnfance.RecreationActivityPolyRegistrationResponseDocument;
//import enfanceServicesEnfance.RecreationActivityPolyRegistrationResponseDocument.RecreationActivityPolyRegistrationResponse;
import enfanceServicesEnfance.RecreationActivityRegistrationResponseDocument;
import enfanceServicesEnfance.RecreationActivityRegistrationResponseDocument.RecreationActivityRegistrationResponse;
import enfanceServicesEnfance.ReservationActivityPlanningResponseDocument;
import enfanceServicesEnfance.ReservationResumeResponseDocument;
import enfanceServicesEnfance.SchoolCanteenRegistrationResponseDocument;
import enfanceServicesEnfance.SchoolCanteenRegistrationResponseDocument.SchoolCanteenRegistrationResponse;
import enfanceServicesEnfance.SchoolRegistrationResponseDocument;
import enfanceServicesEnfance.SchoolRegistrationResponseDocument.SchoolRegistrationResponse;
import enfanceServicesEnfance.SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument;
import enfanceServicesEnfance.SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument.SchoolRegistrationWithRemoteCirilnetenfanceResponse;
import enfanceServicesEnfance.UpdateReservationResponseDocument;

import org.libredemat.plugins.externalservices.cirilnetenfance.ws.ICirilClient;
import org.libredemat.business.authority.School;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItemDetail;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalInvoiceItemDetail;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.SectionType;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.dao.authority.ISchoolDAO;
import org.libredemat.dao.jpa.IJpaTemplate;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.exception.CvqRemoteException;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.service.request.school.external.IActivityReservationProviderService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere;
import org.libredemat.service.request.school.external.IRemoteCirilSchoolsProvider;
import org.libredemat.service.request.school.external.ICirilDocumentProvider;
import org.libredemat.xml.common.LocalReferentialDataType;
import org.libredemat.xml.request.ecitizen.HomeFolderModificationRequestDocument.HomeFolderModificationRequest;
import org.libredemat.xml.request.leisure.YouthCenterRegistrationRequestDocument.YouthCenterRegistrationRequest;
//import org.libredemat.xml.request.school.ChildCareCenterRegistrationRequestDocument.ChildCareCenterRegistrationRequest;
//import org.libredemat.xml.request.school.ChildMedicalFormRequestDocument.ChildMedicalFormRequest;
//import org.libredemat.xml.request.school.GlobalSchoolServicesRegistrationRequestDocument.GlobalSchoolServicesRegistrationRequest;
import org.libredemat.xml.request.school.PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest;
//import org.libredemat.xml.request.school.RecreationActivityPolyRegistrationRequestDocument.RecreationActivityPolyRegistrationRequest;
import org.libredemat.xml.request.school.RecreationActivityRegistrationRequestDocument.RecreationActivityRegistrationRequest;
import org.libredemat.xml.request.school.SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest;
import org.libredemat.xml.request.school.SchoolRegistrationRequestDocument.SchoolRegistrationRequest;
import org.libredemat.xml.request.school.SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument.SchoolRegistrationWithRemoteCirilnetenfanceRequest;

public class CirilNetEnfanceService extends ExternalProviderServiceAdapter implements
		IActivityReservationProviderService, IRemoteCirilSchoolsProvider, ICirilDocumentProvider
{
	private static Logger logger = Logger.getLogger(CirilNetEnfanceService.class);
	private static final String END_POINT_REGISTRATION = "EndPointRegistration";
	private static final String END_POINT_RESERVATION = "EndPointReservation";
	private static final String END_POINT_SCHOOL = "EndPointSchool";
	private String endPointReservation;
	private String endPointRegistration;
	private String endPointSchool;
	private String label;
	private String externalId;
	private IExternalHomeFolderService externalHomeFolderService;
	private IUserSearchService userSearchService;
	private IRequestWorkflowService requestWorkflowService;
	private ICirilClient cirilClient;
	private ISchoolDAO schoolDAO;
	private IPaymentService paymentService;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private IIndividualDAO individualDAO;
    private IRequestDAO requestDAO;
	
	public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
			throws CvqConfigurationException
	{
		setEndPointRegistration((String) externalServiceBean.getProperty(END_POINT_REGISTRATION));
		setEndPointReservation((String) externalServiceBean.getProperty(END_POINT_RESERVATION));
		setEndPointSchool((String) externalServiceBean.getProperty(END_POINT_SCHOOL));
		/*
		 * Est-ce que c'est vraiment utile ?! if (endPointRegistration == null)
		 * throw new CvqConfigurationException("Missing " +
		 * END_POINT_REGISTRATION + " configuration parameter"); if
		 * (endPointReservation == null) throw new
		 * CvqConfigurationException("Missing " + END_POINT_RESERVATION +
		 * " configuration parameter"); if (endPointSchool == null) throw new
		 * CvqConfigurationException("Missing " + END_POINT_SCHOOL +
		 * " configuration parameter");
		 */
	}

	public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems, String cvqReference,
			String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, Date validationDate)
			throws CvqException
	{
		try
		{
			Set<Critere> criteriaSet = new HashSet<Critere>();
			Critere critere = new Critere();
			critere.setComparatif(Critere.EQUALS);
			critere.setAttribut(Payment.SEARCH_BY_CVQ_REFERENCE);
			critere.setValue(cvqReference);
			criteriaSet.add(critere);
			List<Payment> lisPayment = paymentService.get(criteriaSet, null, null, 1, 0);
			String broker = "null";
			if (!lisPayment.isEmpty())
			{
				Payment payment = (Payment) lisPayment.get(0);
				broker = payment.getBroker();
			}
			cirilClient.getCreditAccount(endPointRegistration, purchaseItems, cvqReference, bankReference,
					homeFolderId, externalHomeFolderId, externalId, validationDate, SecurityContext.getCurrentSite()
							.getPostalCode(), broker);
		}
		catch (ParseException e)
		{
			logger.fatal(e.getMessage());
		}
	}

	public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId,
			String externalHomeFolderId, String externalId) throws CvqException
	{
		Map<String, List<ExternalAccountItem>> results = new LinkedHashMap<String, List<ExternalAccountItem>>();
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		setExternalId(externalId);
		try
		{
			FamilyDocument family = cirilClient.getFamilyAccounts(endPointRegistration, SecurityContext
					.getCurrentSite().getPostalCode(), homeFolderId, null, externalId);
			logger.debug("getAccount() : " + family.toString());
			List<ExternalAccountItem> depositAcount = new ArrayList<ExternalAccountItem>();
			FamilyAccounts fa = family.getFamily().getAccounts();
			if (fa != null)
			{
				for (AccountType at : fa.getAccountArray())
				{
					ExternalDepositAccountItem edai = new ExternalDepositAccountItem(at.getAccountLabel(), new Double(
							at.getAccountValue()), getLabel(), at.getAccountId(), at.getAccountDate().getTime(),
							new Double(at.getAccountValue()), null);
					depositAcount.add(edai);
				}
				results.put(IPaymentService.EXTERNAL_DEPOSIT_ACCOUNTS, depositAcount);
			}
			List<ExternalAccountItem> invoices = new ArrayList<ExternalAccountItem>();
			FamilyInvoices fi = family.getFamily().getInvoices();
			if (fi != null)
			{
				for (InvoiceType it : fi.getInvoiceArray())
				{
					String url = null;
					if (it.getInvoiceUrl() != null) url = it.getInvoiceUrl();
					Date expirationDate = null;
					if (it.getInvoiceExpirationDate() != null) expirationDate = it.getInvoiceExpirationDate().getTime();
					ExternalInvoiceItem eii = new ExternalInvoiceItem(it.getInvoiceLabel(), new Double(
							it.getInvoiceValue()), new Double(it.getInvoiceValue()), getLabel(), it.getInvoiceId(), it
							.getInvoiceDate().getTime(), expirationDate, null, it.getInvoicePaid(), it.getBroker(), url);
					invoices.add(eii);
				}
				results.put(IPaymentService.EXTERNAL_INVOICES, invoices);
			}
			List<ExternalAccountItem> contracts = new ArrayList<ExternalAccountItem>();
			FamilyContracts fc = family.getFamily().getContracts();
			if (fc != null)
			{
				for (IndividualContractType ict : fc.getContractArray())
				{
					for (ContractType ct : ict.getContractArray())
					{
						ExternalTicketingContractItem etci = new ExternalTicketingContractItem(ct.getContractLabel(),
								null, getLabel(), ct.getContractId(), null, new Double(ct.getBuyPrice()),
								ct.getMinBuy(), ct.getMaxBuy(), ct.getContractDate().getTime(), ct.getBroker());
						etci.addExternalServiceSpecificData("child-csn", String.valueOf(ict.getCapwebctIndividualId()));
						contracts.add(etci);
					}
				}
				results.put(IPaymentService.EXTERNAL_TICKETING_ACCOUNTS, contracts);
			}
		}
		catch (ParseException e)
		{
			logger.fatal(e.getMessage());
		}
		return results;
	}

	public Map<Date, String> getConsumptionsByRequest(Request request, Date dateFrom, Date dateTo)
	{
		return null;
	}

	public Map<Individual, Map<String, String>> getIndividualAccountsInformation(Long homeFolderId,
			String externalHomeFolderId, String externalId)
	{
		return null;
	}

	public String helloWorld() throws CvqException
	{
		return null;
	}

	public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException
	{
		try
		{
			AccountDetailsResponseDocument adrd = cirilClient.getAccountDetatil(endPointRegistration, SecurityContext
					.getCurrentSite().getPostalCode(), edai.getExternalApplicationId(), getExternalId(), edai
					.getExternalItemId());
			AccountDetailsResponse adr = adrd.getAccountDetailsResponse();
			for (AccountDetailType adt : adr.getAccountDetailArray())
			{
				ExternalDepositAccountItemDetail edaid = new ExternalDepositAccountItemDetail();
				edaid.setDate(adt.getDate().getTime());
				edaid.setHolderName(adt.getHolderName());
				edaid.setHolderSurname(adt.getHolderSurname());
				edaid.setValue(adt.getValue());
				if (adt.getPaymentAck() != "CONSO" && adt.getCvqAck() != "CONSO")
				{
					edaid.setBankReference(adt.getPaymentAck());
					edaid.setPaymentType(adt.getPaymentType());
					edaid.setPaymentId(adt.getCvqAck());
				}
				else
				{
					edaid.setPaymentType(adt.getPaymentType());
				}
				edai.addAccountDetail(edaid);
			}
		}
		catch (ParseException e)
		{
			logger.error("loadAccountDetail() parseException : " + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("loadAccountDetail() : " + e.getMessage());
		}
	}

	public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException
	{
		try
		{
			InvoiceDetailsResponseDocument idrd = cirilClient.getInvoiceDetail(endPointRegistration, SecurityContext
					.getCurrentSite().getPostalCode(), eii.getExternalHomeFolderId(), getExternalId(), eii
					.getExternalItemId());
			InvoiceDetailsResponse idr = idrd.getInvoiceDetailsResponse();
			for (InvoiceDetailType idt : idr.getInvoiceDetailArray())
			{
				ExternalInvoiceItemDetail eiid = new ExternalInvoiceItemDetail(idt.getChildName(),
						idt.getChildSurname(), idt.getLabel(), idt.getUnitPrice(),
						BigDecimal.valueOf(idt.getQuantity()), idt.getValue());
				eii.addInvoiceDetail(eiid);
			}
		}
		catch (ParseException e)
		{
			logger.error("loadInvoiceDetail() parseException : " + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("loadInvoiceDetail() : " + e.getMessage());
		}
	}

	public String sendRequest(final XmlObject xmlRequest) throws CvqException
	{
		logger.debug("CiriNetEnfance send request");
		HashMap<String, Object> repDoc = new HashMap<String, Object>();
		String businessError = "La connexion au serveur a echouée";
		try
		{
			if (xmlRequest instanceof SchoolRegistrationRequest)
			{
				SchoolRegistrationRequest srrd = (SchoolRegistrationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, srrd, "SchoolRegistration");
				businessError = getResult(repDoc.get("result"), srrd.getHomeFolder().getId(), srrd.getId());
			}
			if (xmlRequest instanceof SchoolRegistrationWithRemoteCirilnetenfanceRequest)
			{
				SchoolRegistrationWithRemoteCirilnetenfanceRequest srrd = (SchoolRegistrationWithRemoteCirilnetenfanceRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, srrd,
						"SchoolRegistrationWithRemoteCirilnetenfance");
				businessError = getResult(repDoc.get("result"), srrd.getHomeFolder().getId(), srrd.getId());
			}
			else if (xmlRequest instanceof HomeFolderModificationRequest)
			{
				HomeFolderModificationRequest hfmr = (HomeFolderModificationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, hfmr, "HomeFolderModification");
				businessError = getResult(repDoc.get("result"), hfmr.getHomeFolder().getId(), hfmr.getId());
			}
			else if (xmlRequest instanceof SchoolCanteenRegistrationRequest)
			{
				SchoolCanteenRegistrationRequest scrr = (SchoolCanteenRegistrationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, scrr, "SchoolCanteenRegistration");
				businessError = getResult(repDoc.get("result"), scrr.getHomeFolder().getId(), scrr.getId());
			}
			else if (xmlRequest instanceof PerischoolActivityRegistrationRequest)
			{
				PerischoolActivityRegistrationRequest parr = (PerischoolActivityRegistrationRequest) xmlRequest;
				repDoc = cirilClient
						.getReturnRegistration(endPointRegistration, parr, "PerischoolActivityRegistration");
				businessError = getResult(repDoc.get("result"), parr.getHomeFolder().getId(), parr.getId());
			}
			else if (xmlRequest instanceof RecreationActivityRegistrationRequest)
			{
				RecreationActivityRegistrationRequest rarr = (RecreationActivityRegistrationRequest) xmlRequest;
				repDoc = cirilClient
						.getReturnRegistration(endPointRegistration, rarr, "RecreationActivityRegistration");
				businessError = getResult(repDoc.get("result"), rarr.getHomeFolder().getId(), rarr.getId());
			}
			/*
			else if (xmlRequest instanceof RecreationActivityPolyRegistrationRequest)
			{
				RecreationActivityPolyRegistrationRequest rarr = (RecreationActivityPolyRegistrationRequest) xmlRequest;
				List<LocalReferentialDataType> list = new ArrayList<LocalReferentialDataType>();
				for (LocalReferentialDataType rpa : rarr.getRecreationPolyActivityArray())
				{
					if (rpa.getName() != null && !rpa.getName().equals(""))
					{
						list.add(rpa);
					}
				}
				LocalReferentialDataType[] tab = new LocalReferentialDataType[list.size()];
				int i = 0;
				for (LocalReferentialDataType rpa : list)
				{
					tab[i] = rpa;
					i++;
				}
				rarr.setRecreationPolyActivityArray(tab);
				repDoc = cirilClient
						.getReturnRegistration(endPointRegistration, rarr, "RecreationActivityRegistration");
				businessError = getResult(repDoc.get("result"), rarr.getHomeFolder().getId(), rarr.getId());
			}
			*/
			/*
			else if (xmlRequest instanceof GlobalSchoolServicesRegistrationRequest)
			{
				GlobalSchoolServicesRegistrationRequest gssrr = (GlobalSchoolServicesRegistrationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, gssrr,
						"GlobalSchoolServicesRegistration");
				businessError = getResult(repDoc.get("result"), gssrr.getHomeFolder().getId(), gssrr.getId());
			}
			*/
			/*
			else if (xmlRequest instanceof ChildCareCenterRegistrationRequest)
			{
				ChildCareCenterRegistrationRequest gssrr = (ChildCareCenterRegistrationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, gssrr, "ChildCareCenterRegistration");
				businessError = getResult(repDoc.get("result"), gssrr.getHomeFolder().getId(), gssrr.getId());
			}
			*/
			else if (xmlRequest instanceof YouthCenterRegistrationRequest)
			{
				YouthCenterRegistrationRequest ycrr = (YouthCenterRegistrationRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, ycrr, "YouthCenterRegistration");
				businessError = getResult(repDoc.get("result"), ycrr.getHomeFolder().getId(), ycrr.getId());
			}
			/*
			else if (xmlRequest instanceof ChildMedicalFormRequest)
			{
				ChildMedicalFormRequest cmfr = (ChildMedicalFormRequest) xmlRequest;
				repDoc = cirilClient.getReturnRegistration(endPointRegistration, cmfr, "ChildMedicalForm");
				businessError = getResult(repDoc.get("result"), cmfr.getHomeFolder().getId(), cmfr.getId());
			}
			*/
		}
		catch (ParseException ep)
		{
			throw new CvqModelException("Une erreur s'est produite dans l'analyse du xml");
		}
		catch (XmlException exml)
		{
			throw new CvqModelException("Une erreur s'est produite dans le xml");
		}
		return null;
	}

	private String getResult(Object object, Long hid, Long requestId) throws CvqException
	{
		String error = "";
		String hfextid = "";
		HashMap<Long, String> accountMember = new HashMap<Long, String>();
		boolean isHomeFolderModification = object instanceof HomeFolderModificationResponseDocument;
		try
		{
			if (object instanceof SchoolRegistrationResponseDocument)
			{
				SchoolRegistrationResponseDocument srrd = (SchoolRegistrationResponseDocument) object;
				SchoolRegistrationResponse srr = srrd.getSchoolRegistrationResponse();
				if (srr.getMessage().getError() != null)
				{
					error = srr.getMessage().getError().getMessage();
				}
				if (srr.getNotifications() != null)
				{
					hfextid = srr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = srr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			if (object instanceof SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument)
			{
				SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument srrd = (SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument) object;
				SchoolRegistrationWithRemoteCirilnetenfanceResponse srr = srrd
						.getSchoolRegistrationWithRemoteCirilnetenfanceResponse();
				if (srr.getMessage().getError() != null)
				{
					error = srr.getMessage().getError().getMessage();
				}
				if (srr.getNotifications() != null)
				{
					hfextid = srr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = srr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			else if (isHomeFolderModification)
			{
				HomeFolderModificationResponseDocument hfmrd = (HomeFolderModificationResponseDocument) object;
				HomeFolderModificationResponse hfmr = hfmrd.getHomeFolderModificationResponse();
				if (hfmr.getMessage().getError() != null)
				{
					error = hfmr.getMessage().getError().getMessage();
				}
				if (hfmr.getNotifications() != null)
				{
					hfextid = hfmr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = hfmr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			else if (object instanceof SchoolCanteenRegistrationResponseDocument)
			{
				SchoolCanteenRegistrationResponseDocument scrrd = (SchoolCanteenRegistrationResponseDocument) object;
				SchoolCanteenRegistrationResponse scrr = scrrd.getSchoolCanteenRegistrationResponse();
				if (scrr.getMessage().getError() != null)
				{
					error = scrr.getMessage().getError().getMessage();
				}
				if (scrr.getNotifications() != null)
				{
					hfextid = scrr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = scrr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			else if (object instanceof PerischoolActivityRegistrationResponseDocument)
			{
				PerischoolActivityRegistrationResponseDocument parrd = (PerischoolActivityRegistrationResponseDocument) object;
				PerischoolActivityRegistrationResponse parr = parrd.getPerischoolActivityRegistrationResponse();
				if (parr.getMessage().getError() != null)
				{
					error = parr.getMessage().getError().getMessage();
				}
				if (parr.getNotifications() != null)
				{
					hfextid = parr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = parr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			/*
			else if (object instanceof RecreationActivityPolyRegistrationResponseDocument)
			{
				RecreationActivityPolyRegistrationResponseDocument rarrd = (RecreationActivityPolyRegistrationResponseDocument) object;
				RecreationActivityPolyRegistrationResponse rarr = rarrd.getRecreationActivityPolyRegistrationResponse();
				if (rarr.getMessage().getError() != null)
				{
					error = rarr.getMessage().getError().getMessage();
				}
				if (rarr.getNotifications() != null)
				{
					hfextid = rarr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = rarr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			*/
			else if (object instanceof RecreationActivityRegistrationResponseDocument)
			{
				RecreationActivityRegistrationResponseDocument rarrd = (RecreationActivityRegistrationResponseDocument) object;
				RecreationActivityRegistrationResponse rarr = rarrd.getRecreationActivityRegistrationResponse();
				if (rarr.getMessage().getError() != null)
				{
					error = rarr.getMessage().getError().getMessage();
				}
				if (rarr.getNotifications() != null)
				{
					hfextid = rarr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = rarr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			else if (object instanceof GlobalSchoolServicesRegistrationResponseDocument)
			{
				GlobalSchoolServicesRegistrationResponseDocument rarrd = (GlobalSchoolServicesRegistrationResponseDocument) object;
				GlobalSchoolServicesRegistrationResponse rarr = rarrd.getGlobalSchoolServicesRegistrationResponse();
				if (rarr.getMessage().getError() != null)
				{
					error = rarr.getMessage().getError().getMessage();
				}
				if (rarr.getNotifications() != null)
				{
					hfextid = rarr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = rarr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			else if (object instanceof ChildCareCenterRegistrationResponseDocument)
			{
				ChildCareCenterRegistrationResponseDocument rarrd = (ChildCareCenterRegistrationResponseDocument) object;
				ChildCareCenterRegistrationResponse rarr = rarrd.getChildCareCenterRegistrationResponse();
				if (rarr.getMessage().getError() != null)
				{
					error = rarr.getMessage().getError().getMessage();
				}
				if (rarr.getNotifications() != null)
				{
					hfextid = rarr.getNotifications().getHomeFolderMapping().getExternalId();
					IndividualMappingType[] individus = rarr.getNotifications().getIndividualMappingArray();
					for (IndividualMappingType idmt : individus)
					{
						accountMember.put(idmt.getCapDematId(), idmt.getExternalId());
					}
				}
			}
			if (StringUtils.isNotBlank(hfextid))
			{
				try
				{
					// Long hfextidLong = Long.parseLong(hfextid);
					externalHomeFolderService.addHomeFolderMapping(label, hid, hfextid);
					for (Long iid : accountMember.keySet())
					{
						externalHomeFolderService.setExternalId(label, hid, iid, accountMember.get(iid));
					}
				}
				catch (NumberFormatException ex)
				{
					throw new CvqModelException("Le numéro de dossier Civil n'est pas un chiffre (Long) : " + hfextid);
				}
				catch (Exception ex)
				{
					// hack inexine - s'il y a une erreur plus importante, il
					// faut continuer le traitement...
				}
			}
			if (!error.isEmpty())
			{
				// if () TODO correctif suite modification compte
				if (!isHomeFolderModification) requestWorkflowService.updateRequestState(requestId,
						RequestState.UNCOMPLETE, null);
				throw new CvqModelException(error);
			}
		}
		catch (CvqModelException ex)
		{
			logger.error("HACK INEXINE - CVQMODELException : " + ex.getMessage());
			throw new CvqModelException("Retour du logiciel métier : " + ex.getMessage());
		}
		catch (Exception e)
		{
			logger.error("HACK INEXINE - NEXCEPTION : " + e.getMessage());
			throw new CvqModelException("Retour du logiciel métier : " + e.getMessage());
		}
		return error;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}

	public void setEndPointRegistration(String endPointRegistration)
	{
		this.endPointRegistration = endPointRegistration;
	}

	public void setEndPointReservation(String endPointReservation)
	{
		this.endPointReservation = endPointReservation;
	}

	public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService)
	{
		this.externalHomeFolderService = externalHomeFolderService;
	}

	public void setSchoolDAO(ISchoolDAO schoolDAO)
	{
		this.schoolDAO = schoolDAO;
	}

	@Override
	public List<String> checkExternalReferential(XmlObject requestXml)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean handlesTraces()
	{
		return false;
	}

	@Override
	public Map<String, Object> loadExternalInformations(XmlObject requestXml) throws CvqException
	{
		return Collections.emptyMap();
	}

	@Override
	public boolean supportsConsumptions()
	{
		return true;
	}

	public void setCirilClient(ICirilClient cirilClient)
	{
		this.cirilClient = cirilClient;
	}

	@Override
	public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo) throws CvqException
	{
		return Collections.emptyMap();
	}

	/**
	 * From here method from interface IActivityReservationProviderService
	 * concerning Reservation getReservationResume => retreive information for
	 * 'pivot' page getReservationActivity => retreive information for one
	 * activity of one child during a month getUpdateReservation => send
	 * reservation to validate them in business soft getAmountVerification =>
	 * validate amount to pay with the business soft getPaymentValidation =>
	 * return to the business soft state of payment getCancelReservation =>
	 * delete reservation on business soft
	 */
	@Override
	public LinkedHashMap<Child, Activities[]> getReservationResume(Long homeFolderId, Date start, Date end,
			String sessionId) throws CvqException, ParseException
	{
		// retreive information for 'pivot' page
		LinkedHashMap<Child, Activities[]> getRes = new LinkedHashMap<Child, Activities[]>();
		// ordering map among the id of child
		HomeFolderMapping hfm = externalHomeFolderService.getHomeFolderMapping(label, homeFolderId);
		List<IndividualMapping> individuals = hfm.getIndividualsMappings();
		try
		{
			ReservationResumeResponseDocument rrrespdoc = cirilClient.getReservationResume(endPointReservation,
					hfm.getExternalId(), String.valueOf(homeFolderId), start, end, sessionId);
			// for each child put an array only for child with external idof
			// information
			for (enfanceServicesEnfance.Child child : rrrespdoc.getReservationResumeResponse().getChildArray())
			{
				for (IndividualMapping im : individuals)
				{
					// only for child with external id
					if (im.getExternalId() != null && im.getExternalId().equals(child.getExternalChildId()))
					{
						Child capChild = userSearchService.getChildById(im.getIndividualId());
						getRes.put(capChild, child.getActivitiesArray());
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("La connexion avec le connecteur Ciril a rencontré un pb - leur serveur est peut-être indisponible. "
					+ e.getMessage());
			throw new CvqRemoteException("error.cirilConnectionFoReservationError");
		}
		return getRes;
	}

	/*
	 * method to convert soap detail planning of activity response to map object
	 */
	@Override
	public Map<String, Object> getReservationActivity(String activityCode, String folderId, String externalFolderId,
			String childId, String externalChildId, Date start, Date end, String sessionId) throws CvqException,
			ParseException
	{ // retreive information for ona activity of one child during a month
		Map<String, Object> getRes = new HashMap<String, Object>();
		ReservationActivityPlanningResponseDocument raprd = cirilClient.getReservationPlanning(endPointReservation,
				activityCode, folderId, externalFolderId, childId, externalChildId, start, end, sessionId);
		/**
		 * define information we need in the map to easily get them in
		 * controller
		 */
		if (raprd == null || raprd.getReservationActivityPlanningResponse() == null) return null;
		getRes.put("amountInCent", raprd.getReservationActivityPlanningResponse().getAmountInCent());
		getRes.put("instantPayment", raprd.getReservationActivityPlanningResponse().getInstantPayment());
		getRes.put("reservationDeadLine", raprd.getReservationActivityPlanningResponse().getReservationDeadline());
		getRes.put("activityCode", raprd.getReservationActivityPlanningResponse().getActivityCode());
		getRes.put("deadLine", raprd.getReservationActivityPlanningResponse().getReservationDeadline());
		if (raprd.getReservationActivityPlanningResponse().getActivityLabel() != null)
		{
			getRes.put("activityLabel", raprd.getReservationActivityPlanningResponse().getActivityLabel());
		}
		getRes.put("activityServicesPlanning", raprd.getReservationActivityPlanningResponse()
				.getActivityServicesPlanningArray());
		return getRes;
	}

	@Override
	public Map<String, Object> getAllReservation(Map<String, Object> params) throws CvqException, ParseException
	{
		Map<String, Object> getRes = new HashMap<String, Object>();		
		UpdateReservationResponseDocument urrespd = cirilClient.getAllReservation(endPointReservation, params);
		
		if (urrespd == null || urrespd.getUpdateReservationResponse() == null) return null;
		getRes.put("amountNegativeInCent", urrespd.getUpdateReservationResponse().getAccountNegativeInCent());
		getRes.put("amoutnToPay", urrespd.getUpdateReservationResponse().getAmountToPay());
		getRes.put("childsItem", urrespd.getUpdateReservationResponse().getChildRespArray());	
		
		return getRes;
	}
	
    @Override
    public Map<String, String> loadChildSchools(Map<String, Object> params) throws CvqException {
        Long subjectIdContainer = Long.valueOf(params.get("subjectIdContainer").toString());
        String sectionContainer = params.get("sectionContainer").toString();
        SectionType section = SectionType.forString(sectionContainer);
        sectionContainer = section.getLegacyLabel();
        Long requestId = Long.valueOf(params.get("requestId").toString());
        Request request = requestDAO.findById(requestId.longValue());
        String year = "";
        if (request.getRequestSeason() != null && request.getRequestSeason().getEffectStart() != null)
        {
            Date date = request.getRequestSeason().getEffectStart().toDate();
            year = new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        Map<String, String> map = new HashMap<String, String>();
        Adult currentEcitizen = SecurityContext.getCurrentEcitizen();
        Individual child = individualDAO.findById(subjectIdContainer.longValue());
        Document schoolsOfCirilNetEnfance = getSchoolsOfCirilNetEnfance(subjectIdContainer
                .longValue(), currentEcitizen.getHomeFolder().getId(), currentEcitizen.getAddress().getCityInseeCode(),
                currentEcitizen.getAddress().getStreetName(), currentEcitizen.getAddress().getCity(), currentEcitizen
                .getAddress().getPostalCode(), sectionContainer, year, new SimpleDateFormat("yyyy-MM-dd")
                .format(child.getBirthDate()));
        Element rootElement = schoolsOfCirilNetEnfance.getRootElement();
        List<Object> children = rootElement.getChildren("ecolesSecteurHabitation");
        Iterator<Object> iterator = children.iterator();
        while (iterator.hasNext())
        {
            Element el = (Element) iterator.next();
            List<Element> childrens = el.getChildren("ecoleSecteurHabitation");
            for (Element el2 : childrens)
            {
                Element code = el2.getChild("code");
                Element libelle = el2.getChild("libelle");
                map.put(code.getText(), libelle.getText());
            }
        }
        return map;
    }

	@Override
	public Map<String, Object> getUpdateReservation(String folderId, String externalFolderId, Map reservationItems,
			String sessionId) throws CvqException, ParseException
	{ // validate reservation in the business soft
		Map<String, Object> getRes = new HashMap<String, Object>();
		UpdateReservationResponseDocument urrespd = cirilClient.getUpdateReservation(endPointReservation, folderId,
				externalFolderId, sessionId, reservationItems);
		/**
		 * define information we need in the map to easily get them in
		 * controller
		 */
		if (urrespd == null || urrespd.getUpdateReservationResponse() == null) return null;
		getRes.put("amountNegativeInCent", urrespd.getUpdateReservationResponse().getAccountNegativeInCent());
		getRes.put("amoutnToPay", urrespd.getUpdateReservationResponse().getAmountToPay());
		getRes.put("childsItem", urrespd.getUpdateReservationResponse().getChildRespArray());
		return getRes;
	}

	@Override
	public Map<String, Object> getAmountVerification(String folderId, String externalFolderId, Integer amountInCent,
			String sessionId) throws CvqException, ParseException
	{
		Map<String, Object> getRes = new HashMap<String, Object>();
		AmountVerificationResponseDocument avrespd = cirilClient.getAmountVerification(endPointReservation, folderId,
				externalFolderId, amountInCent, sessionId);
		/**
		 * define information we need in the map to easily get them in
		 * controller
		 */
		getRes.put("returnCode", avrespd.getAmountVerificationResponse().getReturnCode());
		if (!getRes.get("returnCode").equals("OK"))
		{ // if there is an error define information to display it to the
			// citizen
			getRes.put("verificationResponse", avrespd.getAmountVerificationResponse().getErrorLabel());
			getRes.put("amountWanted", avrespd.getAmountVerificationResponse().getExpectedAmount());
		}
		return getRes;
	}


    /**
     * *  individualId : id de l'enfant
     * *  
     * *  CodeCommune : Code INSEE de la commune (obligatoire dans le cas de communautés de communes)
     * CodeFamille : Code de la famille dans Civil Net Enfance
     * codeExterneFamille : Code CapDemat de la famille
     * CodeEnfant : Code de l'enfant CNE.
     * CodeExterneEnfant : Code CapDemat de l'enfant
     * codeBaseEleveNiveau : Code base élève du niveau scolaire souhaité.
     * dateReferenceAnneeScolaire : Date de référence pour la recherche de l'année scolaire
     * dateNaissanceEnfant : Date de naissance de l'enfant pour la recherche du niveau scolaire en fonction de l'âge (si niveau non sélectionné)
     * codeNiveau : Code Civil Net Enfance du niveau scolaire souhaité
     *                                                                                       */
    public org.jdom.Document getSchoolsOfCirilNetEnfance(Long childId, Long homeFolderId, String codeCommune, String streetName, String city, String postalCode, String codeBaseEleveNiveau, String dateReferenceAnneeScolaire, String dateNaissanceEnfant) throws CvqException {
        IndividualMapping mappingChild = externalHomeFolderService.getIndividualMapping(childId, homeFolderId, this.label);
        HomeFolderMapping mappingHomeFolder = externalHomeFolderService.getHomeFolderMapping(this.label, homeFolderId);
        Map<String, Object> parameter = new HashMap<String, Object>();
        if (mappingChild == null || mappingChild.getExternalId() == null || mappingHomeFolder == null
                || mappingHomeFolder.getExternalId() == null)
        {
            parameter.put("streetName", streetName);
            parameter.put("city", city);
            parameter.put("postalCode", postalCode);
        }
        else
        {
            parameter.put("codeEnfant", mappingChild.getExternalId());
            parameter.put("codeFamille", mappingHomeFolder.getExternalId());
        }
        parameter.put("codeCommune", codeCommune);
        parameter.put("codeExterneEnfant", childId + "");
        parameter.put("codeExterneFamille", homeFolderId + "");
        parameter.put("codeBaseEleveNiveau", codeBaseEleveNiveau);
        parameter.put("dateReferenceAnneeScolaire", dateReferenceAnneeScolaire);
        parameter.put("dateNaissanceEnfant", dateNaissanceEnfant);
        parameter.put("codeNiveau", codeBaseEleveNiveau);
        org.jdom.Document doc = getSchoolsOfChild(parameter);
        if (doc != null && doc.hasRootElement() && doc.getContent() != null && !doc.getContent().isEmpty()) {
            return doc;
        } else {
            throw new CvqException("external.warning.noschool");
        }
    };


	@Override
	public void getPaymentValidation(String folderId, String externalFolderId, String returnType, String sessionId)
			throws CvqException, ParseException
	{ // return to the business soft the payment state
		cirilClient.getPaymentReturnValidation(endPointReservation, folderId, externalFolderId, returnType, sessionId);
	}

	@Override
	public void getCancelReservation(String sessionId) throws CvqException, ParseException
	{ // cancel reservation on the busines soft
		cirilClient.getCancelReservation(endPointReservation, sessionId);
	}

	@Override
	public Document getInterractionReservation(Map<String, Object> params) throws CvqException
	{
		Long homeFolderId = (Long) params.get("homeFolderId");
		String externalId = (String) params.get("externalId");
		logger.debug("Home folder id : " + homeFolderId);
		logger.debug("external id : " + externalId);
		logger.debug("params table size : " + params.size());
		logger.debug("params endpoint_resa : " + endPointReservation);
		logger.info("call : " + endPointReservation + " for : " + homeFolderId + " with : " + externalId);
		Document docx = new Document();
		SAXBuilder sax = new SAXBuilder();
		if (params.containsKey("documentList"))
		{
			logger.debug("Here in document list call");
			ExternalDocumentReponseDocument edrd;
			try
			{
				edrd = cirilClient.getExternalDocument(endPointReservation, homeFolderId, externalId);
				Reader in = new StringReader(edrd.toString());
				docx = sax.build(in);
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage());
			}
		}
		return docx;
	}

	@Override
	/**
	 * Le params peut contenir toutes ces informations :
	 * 
	 * 
	 * String codeCommune = (String) params.get("codeCommune"); 
		Long codeEnfant = (String) params.get("codeEnfant");
		String codeExterneEnfant = (String) params.get("codeExterneEnfant"); 
		String codeExterneFamille = (String) params.get("codeExterneFamille"); 
		Long codeFamille = (String) params.get("codeFamille"); 
		String codeBaseEleveNiveau = (String) params.get("codeBaseEleveNiveau");
		String dateReferenceAnneeScolaire = (String) params.get("dateReferenceAnneeScolaire"); 
		String dateNaissanceEnfant = (String) params.get("dateNaissanceEnfant"); 
		String codeNiveau = (String) params.get("codeNiveau"); 
	 */
	public Document getSchoolsOfChild(Map<String, Object> params) throws CvqException
	{
		String codeEnfant = (String) params.get("codeEnfant");
		String codeFamille = (String) params.get("codeFamille");
		String codeCommune = (String) params.get("codeCommune");
		String codeExterneEnfant = (String) params.get("codeExterneEnfant");
		String codeExterneFamille = (String) params.get("codeExterneFamille");
		String codeBaseEleveNiveau = (String) params.get("codeBaseEleveNiveau");
		String dateReferenceAnneeScolaire = (String) params.get("dateReferenceAnneeScolaire");
		String dateNaissanceEnfant = (String) params.get("dateNaissanceEnfant");
		String codeNiveau = (String) params.get("codeNiveau");
		String streetName = (String) params.get("streetName");
		String city = (String) params.get("city");
		String postalCode = (String) params.get("postalCode");
		logger.info("call : " + endPointSchool + " for childId : " + codeEnfant + " with externalChildId : "
				+ codeExterneEnfant);
		Document docx = new Document();
		SAXBuilder sax = new SAXBuilder();
		GetEcoleSecteurCalculeeResponseDocument edrd = null;
		try
		{
			edrd = cirilClient.getChildSchool(endPointSchool, codeCommune, codeEnfant, codeExterneEnfant,
					codeExterneFamille, codeFamille, codeBaseEleveNiveau, dateReferenceAnneeScolaire,
					dateNaissanceEnfant, codeNiveau, streetName, city, postalCode);
			logger.info("getChildSchool() : " + edrd.getGetEcoleSecteurCalculeeResponse());
			GestionErreur gestionErreur = edrd.getGetEcoleSecteurCalculeeResponse().getGestionErreur();
			if (gestionErreur != null && gestionErreur.getCodeErreur() > -1)
			{
				// une erreur s'est produite on va essayer de la gérer
				logger.warn("getSchoolsOfChild - CVQMODELException : " + gestionErreur.getMessageFront());
				throw new CvqModelException(gestionErreur.getMessageFront());
			}
			else
			{
				Reader in = new StringReader(edrd.toString());
				docx = sax.build(in);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			throw new CvqModelException(e.getMessage());
		}
		return docx;
	}

    public org.jdom.Document getDocumentsOfCirilNetEnfance(Long homeFolderId) throws CvqException
    {
        // List<Request> rs =
        // requestSearchService.getByHomeFolderIdWithoutStateClosed(homeFolderId,
        // false);
        HomeFolderMapping mapping = externalHomeFolderService.getHomeFolderMapping(getLabel(), homeFolderId);
        if (mapping != null && mapping.getExternalId() != null)
        {
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("documentList", "ok");
            parameter.put("homeFolderId", homeFolderId);
            parameter.put("externalId", mapping.getExternalId());
            Document doc = getInterractionReservation(parameter);
            if (doc != null && doc.hasRootElement() && doc.getContent() != null && !doc.getContent().isEmpty()) {
                return doc;
            }
        }
        return null;
    };

	@Override
	public boolean isServerStarted()
	{
		return cirilClient.isCirilServerStarted(endPointReservation);
	}

	public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService)
	{
		this.requestWorkflowService = requestWorkflowService;
	}

	public void setUserSearchService(IUserSearchService userSearchService)
	{
		this.userSearchService = userSearchService;
	}

	public void setPaymentService(IPaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public void setEndPointSchool(String endPointSchool)
	{
		this.endPointSchool = endPointSchool;
	}

    public void setIndividualDAO(IIndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }
}
