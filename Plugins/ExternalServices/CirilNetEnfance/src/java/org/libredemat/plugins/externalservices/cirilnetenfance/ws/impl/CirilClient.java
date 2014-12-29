package org.libredemat.plugins.externalservices.cirilnetenfance.ws.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;

import enfanceServicesEnfance.AccountDetailsDocument;
import enfanceServicesEnfance.AccountDetailsRequest;
import enfanceServicesEnfance.AccountDetailsResponseDocument;
import enfanceServicesEnfance.AccountUpdateType;
import enfanceServicesEnfance.ActivitiesReq;
import enfanceServicesEnfance.ActivityServicesDateReq;
import enfanceServicesEnfance.ActivityServicesReq;
import enfanceServicesEnfance.Adresse;
import enfanceServicesEnfance.AdresseFormatAfnor;
import enfanceServicesEnfance.AmountVerificationRequestDocument;
import enfanceServicesEnfance.AmountVerificationRequestDocument.AmountVerificationRequest;
import enfanceServicesEnfance.AmountVerificationResponseDocument;
import enfanceServicesEnfance.BankTransaction;
import enfanceServicesEnfance.BankTransactionAccounts;
import enfanceServicesEnfance.BankTransactionContracts;
import enfanceServicesEnfance.BankTransactionInvoices;
import enfanceServicesEnfance.CancelReservationRequestDocument;
import enfanceServicesEnfance.CancelReservationRequestDocument.CancelReservationRequest;
import enfanceServicesEnfance.CancelReservationResponseDocument;
import enfanceServicesEnfance.ChildCareCenterRegistrationDocument;
import enfanceServicesEnfance.ChildCareCenterRegistrationResponseDocument;
import enfanceServicesEnfance.ChildReq;
import enfanceServicesEnfance.ContractUpdateType;
import enfanceServicesEnfance.CreditAccountDocument;
import enfanceServicesEnfance.CreditAccountDocument.CreditAccount;
import enfanceServicesEnfance.DayType;
import enfanceServicesEnfance.ExternalDocumentReponseDocument;
import enfanceServicesEnfance.FamilyAccountsRequestDocument;
import enfanceServicesEnfance.FamilyAccountsRequestDocument.FamilyAccountsRequest;
import enfanceServicesEnfance.FamilyDocument;
import enfanceServicesEnfance.FamilyType;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeDocument;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeDocument.GetEcoleSecteurCalculee;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeDocument.GetEcoleSecteurCalculee.InfosScolaires;
import enfanceServicesEnfance.GetEcoleSecteurCalculeeResponseDocument;
import enfanceServicesEnfance.GetExternalDocumentDocument1;
import enfanceServicesEnfance.GetExternalDocumentDocument1.GetExternalDocument;
import enfanceServicesEnfance.GlobalSchoolServicesRegistrationDocument;
import enfanceServicesEnfance.GlobalSchoolServicesRegistrationResponseDocument;
import enfanceServicesEnfance.HomeFolderModificationDocument;
import enfanceServicesEnfance.HomeFolderModificationResponseDocument;
import enfanceServicesEnfance.InvoiceDetailsDocument;
import enfanceServicesEnfance.InvoiceDetailsRequest;
import enfanceServicesEnfance.InvoiceDetailsResponseDocument;
import enfanceServicesEnfance.InvoiceUpdateType;
import enfanceServicesEnfance.PaymentType;
import enfanceServicesEnfance.PerischoolActivityRegistrationDocument;
import enfanceServicesEnfance.PerischoolActivityRegistrationResponseDocument;
import enfanceServicesEnfance.PostPaymentReturnRequestDocument;
import enfanceServicesEnfance.PostPaymentReturnRequestDocument.PostPaymentReturnRequest;
import enfanceServicesEnfance.RecreationActivityRegistrationDocument;
import enfanceServicesEnfance.RecreationActivityRegistrationResponseDocument;
import enfanceServicesEnfance.ReservationActivityPlanningRequestDocument;
import enfanceServicesEnfance.ReservationActivityPlanningRequestDocument.ReservationActivityPlanningRequest;
import enfanceServicesEnfance.ReservationActivityPlanningResponseDocument;
import enfanceServicesEnfance.ReservationResumeRequestDocument;
import enfanceServicesEnfance.ReservationResumeRequestDocument.ReservationResumeRequest;
import enfanceServicesEnfance.ReservationResumeResponseDocument;
import enfanceServicesEnfance.ReturnType;
import enfanceServicesEnfance.SchoolCanteenRegistrationDocument;
import enfanceServicesEnfance.SchoolCanteenRegistrationResponseDocument;
import enfanceServicesEnfance.SchoolRegistrationDocument;
import enfanceServicesEnfance.SchoolRegistrationResponseDocument;
import enfanceServicesEnfance.SchoolRegistrationWithRemoteCirilnetenfanceDocument;
import enfanceServicesEnfance.SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument;
import enfanceServicesEnfance.UpdateGlobalReservationRequestDocument;
import enfanceServicesEnfance.UpdateGlobalReservationRequestDocument.UpdateGlobalReservationRequest;
import enfanceServicesEnfance.UpdateReservationRequestDocument;
import enfanceServicesEnfance.UpdateReservationRequestDocument.UpdateReservationRequest;
import enfanceServicesEnfance.UpdateReservationResponseDocument;
import enfanceServicesEnfance.WeeklySchedule;
import enfanceServicesEnfance.YouthCenterRegistrationDocument;
import enfanceServicesEnfance.YouthCenterRegistrationResponseDocument;
import org.libredemat.plugins.externalservices.cirilnetenfance.util.TimeOut;
import org.libredemat.plugins.externalservices.cirilnetenfance.ws.ICirilClient;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.exception.CvqException;

public class CirilClient implements ICirilClient
{
	private Logger logger = Logger.getLogger(CirilClient.class);
	private WebServiceTemplate cirilClientService;
	private Long timeOutServerStarted = 6000L;

	@Override
	public FamilyDocument getFamilyAccounts(String endPoint, String zipCode, Long homeFolderId,
			String externalCapdematId, String externalId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		FamilyAccountsRequestDocument fa = FamilyAccountsRequestDocument.Factory.newInstance();
		FamilyAccountsRequest far = (FamilyAccountsRequest) fa.addNewFamilyAccountsRequest();
		far.setLocalAuthority(zipCode);
		far.setExternalCapDematId(externalCapdematId);
		far.setExternalId(externalId);
		far.setHomeFolderId(homeFolderId);
		logger.info("getFamilyAccounts() got payload : " + fa.toString());
		FamilyDocument reqxml;
		try
		{
			reqxml = (FamilyDocument) cirilClientService.marshalSendAndReceive(fa);
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("Erreur lors de la récupération des familyAccounts : " + ew.getMessage());
			throw new CvqException("Cette page est momentanément indisponible. Merci de revenir dans quelques minutes.");
		}
		logger.info("getFamilyAccounts() got result : " + reqxml.toString());
		return reqxml;
	}

	@Override
	public InvoiceDetailsResponseDocument getInvoiceDetail(String endPoint, String zipCode, String externalCapdematId,
			String externalId, String invoiceId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		InvoiceDetailsDocument idd = InvoiceDetailsDocument.Factory.newInstance();
		InvoiceDetailsRequest idr = (InvoiceDetailsRequest) idd.addNewInvoiceDetails().addNewInvoiceDetailsRequest();
		idr.setExternalFamilyAccountId(externalId);
		idr.setLocalAuthority(zipCode);
		idr.setInvoiceId(invoiceId);
		idr.setExternalFamilyAccountId(externalCapdematId);
		logger.info("InvoiceDetails() request : " + idr.toString());
		InvoiceDetailsResponseDocument idrespd;
		try
		{
			idrespd = (InvoiceDetailsResponseDocument) cirilClientService.marshalSendAndReceive(idd);
			logger.info("InvoiceDetail() response : " + idrespd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex.getMessage());
			throw new CvqException("Erreur lors du traitement de la demande. " + ex.getMessage());
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew.getMessage());
			throw new CvqException(
					"Le connecteur a rencontré un problème. Le serveur Ciril est peut être indisponible. "
							+ ew.getMessage());
		}
		return idrespd;
	}

	@Override
	public AccountDetailsResponseDocument getAccountDetatil(String endPoint, String postalCode,
			String externalApplicationId, String externalId, String accountId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		AccountDetailsDocument add = AccountDetailsDocument.Factory.newInstance();
		AccountDetailsRequest adr = add.addNewAccountDetails().addNewAccountDetailsRequest();
		adr.setAccountId(accountId);
		adr.setLocalAuthority(postalCode);
		adr.setExternalFamilyAccountId(externalId);
		adr.setExternalApplicationId(Long.valueOf(externalApplicationId));
		logger.info("AccountDetails() request : " + adr.toString());
		AccountDetailsResponseDocument adrd;
		try
		{
			adrd = (AccountDetailsResponseDocument) cirilClientService.marshalSendAndReceive(add);
			logger.info("AccountDetails() response : " + adrd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
		return adrd;
	}

	@Override
	public void getCreditAccount(String endPoint, Collection<PurchaseItem> purchaseItems, String cvqReference,
			String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId,
			Date validationDate, String postalCode, String broker) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		int total = 0;
		for (PurchaseItem purchaseItem : purchaseItems)
		{
			total = total + purchaseItem.getAmount().intValue();
		}
		CreditAccountDocument cad = CreditAccountDocument.Factory.newInstance();
		CreditAccount ca = cad.addNewCreditAccount();
		BankTransaction bt = ca.addNewBankTransaction();
		bt.setVersion("1.0");
		PaymentType pt = bt.addNewPayment();
		FamilyType ft = bt.addNewFamily();
		BankTransactionAccounts ba = bt.addNewAccounts();
		BankTransactionInvoices bi = bt.addNewInvoices();
		BankTransactionContracts bc = bt.addNewContracts();
		pt.setPaymentAck(bankReference);
		pt.setCvqAck(cvqReference);
		pt.setPaymentAmount(total);
		pt.setPaymentBroker(broker);
		Calendar cal = Calendar.getInstance();
		cal.setTime(validationDate);
		pt.setPaymentDate(cal);
		pt.setPaymentType("internet");
		ft.setExternalCapDematId(externalHomeFolderId);
		ft.setExternalId(externalId);
		ft.setZip(postalCode);
		ft.setId(homeFolderId);
		for (PurchaseItem item : purchaseItems)
		{
			if (item instanceof ExternalDepositAccountItem)
			{
				ExternalDepositAccountItem depotItem = (ExternalDepositAccountItem) item;
				AccountUpdateType at = ba.addNewAccount();
				at.setAccountId(depotItem.getExternalItemId());
				at.setAccountNewValue(depotItem.getAmount().intValue());
				at.setAccountOldValue(depotItem.getOldValue());
				Calendar cali = Calendar.getInstance();
				cali.setTime(depotItem.getOldValueDate());
				at.setAccountOldValueDate(cali);
				at.setExternalApplicationId(0);
				at.setExternalFamilyAccountId(externalId);
			}
			if (item instanceof ExternalInvoiceItem)
			{
				ExternalInvoiceItem invoiceItem = (ExternalInvoiceItem) item;
				InvoiceUpdateType it = bi.addNewInvoice();
				it.setInvoiceId(invoiceItem.getExternalItemId());
				it.setAmount(invoiceItem.getAmount().intValue());
				it.setExternalApplicationId(0);
				it.setExternalFamilyAccountId(externalId);
			}
			if (item instanceof ExternalTicketingContractItem)
			{
				ExternalTicketingContractItem contractItem = (ExternalTicketingContractItem) item;
				ContractUpdateType ct = bc.addNewContract();
				ct.setContractId(contractItem.getExternalItemId());
				ct.setAmount(contractItem.getAmount().intValue());
				ct.setExternalApplicationId(0);
				ct.setExternalFamilyAccountId(externalId);
				ct.setCapwebctExternalIndividualId("");
				ct.setCapwebctIndividualId(contractItem.getSubjectId());
				ct.setPrice(contractItem.getUnitPrice().intValue());
				ct.setQuantity(contractItem.getQuantity());
			}
		}
		logger.info("creditAccount() request : " + ca.toString());
		try
		{
			cirilClientService.marshalSendAndReceive(cad);
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
	}

	@Override
	public HashMap<String, Object> getReturnRegistration(String endPoint, XmlObject modelToXml, String registrationType)
			throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		HashMap<String, Object> result = new HashMap<String, Object>();
		logger.info("Sending a registration to : " + endPoint);
		cirilClientService.setDefaultUri(endPoint);
		try
		{
			if (registrationType.equals("SchoolRegistration"))
			{
				SchoolRegistrationDocument srd = SchoolRegistrationDocument.Factory.newInstance();
				srd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + srd.toString());
				SchoolRegistrationResponseDocument srrd = (SchoolRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(srd);
				logger.info("receive from Ciril : " + srrd.toString());
				result.put("result", srrd);
			}
			if (registrationType.equals("SchoolRegistrationWithRemoteCirilnetenfance"))
			{
				SchoolRegistrationWithRemoteCirilnetenfanceDocument srd = SchoolRegistrationWithRemoteCirilnetenfanceDocument.Factory
						.newInstance();
				srd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + srd.toString());
				SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument srrd = (SchoolRegistrationWithRemoteCirilnetenfanceResponseDocument) cirilClientService
						.marshalSendAndReceive(srd);
				logger.info("receive from Ciril : " + srrd.toString());
				result.put("result", srrd);
			}
			else if (registrationType.equals("RecreationActivityRegistration"))
			{
				RecreationActivityRegistrationDocument rard = RecreationActivityRegistrationDocument.Factory
						.newInstance();
				rard.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + rard.toString());
				RecreationActivityRegistrationResponseDocument rarrd = (RecreationActivityRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(rard);
				logger.info("receive from Ciril : " + rarrd.toString());
				result.put("result", rarrd);
			}
			else if (registrationType.equals("GlobalSchoolServicesRegistration"))
			{
				GlobalSchoolServicesRegistrationDocument document = GlobalSchoolServicesRegistrationDocument.Factory
						.newInstance();
				document.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + document.toString());
				GlobalSchoolServicesRegistrationResponseDocument gssrrd = (GlobalSchoolServicesRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(document);
				logger.info("receive from Ciril : " + gssrrd.toString());
				result.put("result", gssrrd);
			}
			else if (registrationType.equals("PerischoolActivityRegistration"))
			{
				PerischoolActivityRegistrationDocument pard = PerischoolActivityRegistrationDocument.Factory
						.newInstance();
				pard.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + pard.toString());
				PerischoolActivityRegistrationResponseDocument parrd = (PerischoolActivityRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(pard);
				logger.info("receive from Ciril : " + parrd.toString());
				result.put("result", parrd);
			}
			else if (registrationType.equals("SchoolCanteenRegistration"))
			{
				SchoolCanteenRegistrationDocument scrd = SchoolCanteenRegistrationDocument.Factory.newInstance();
				scrd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + scrd.toString());
				SchoolCanteenRegistrationResponseDocument scrrd = (SchoolCanteenRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(scrd);
				logger.info("receive from Ciril : " + scrrd.toString());
				result.put("result", scrrd);
			}
			else if (registrationType.equals("ChildCareCenterRegistration"))
			{
				ChildCareCenterRegistrationDocument scrd = ChildCareCenterRegistrationDocument.Factory.newInstance();
				scrd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + scrd.toString());
				ChildCareCenterRegistrationResponseDocument scrrd = (ChildCareCenterRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(scrd);
				logger.info("receive from Ciril : " + scrrd.toString());
				result.put("result", scrrd);
			}
			else if (registrationType.equals("HomeFolderModification"))
			{
				HomeFolderModificationDocument hfmd = HomeFolderModificationDocument.Factory.newInstance();
				hfmd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + hfmd.toString());
				HomeFolderModificationResponseDocument hfmrd = (HomeFolderModificationResponseDocument) cirilClientService
						.marshalSendAndReceive(hfmd);
				logger.info("receive from Ciril : " + hfmrd.toString());
				result.put("result", hfmrd);
			}
			else if (registrationType.equals("YouthCenterRegistration"))
			{
				YouthCenterRegistrationDocument ycrrd = YouthCenterRegistrationDocument.Factory.newInstance();
				ycrrd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + ycrrd.toString());
				YouthCenterRegistrationResponseDocument ycrrdd = (YouthCenterRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(ycrrd);
				logger.info("receive from Ciril : " + ycrrdd.toString());
				result.put("result", ycrrdd);
			}
			/*else if (registrationType.equals("ChildMedicalForm"))
			{
				ChildMedicalFormDocument cmfd = ChildMedicalFormDocument.Factory.newInstance();
				cmfd.set(transform(modelToXml, registrationType));
				logger.info("Post to Ciril : " + cmfd.toString());
				ChildMedicalFormResponseDocument cmfrd = (ChildCareCenterRegistrationResponseDocument) cirilClientService
						.marshalSendAndReceive(cmfd);
				logger.info("receive from Ciril : " + cmfrd.toString());
				result.put("result", cmfrd);
			}*/
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException(
					"Le connecteur a rencontré un problème. Le serveur Ciril est peut être indisponible.");
		}/*
		 * catch (Exception e) { logger.debug("there is an error : " +
		 * e.getMessage()); throw new CvqException(e.getMessage()); }
		 */
		return result;
	}

	private XmlObject transform(XmlObject xmlObject, String registrationType)
	{
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		Reader xmlRequest = new StringReader("<?xml version='1.0' encoding='UTF-8'?>" + xmlObject.xmlText());
		SAXBuilder saxb = new SAXBuilder();
		XmlObject xmlModified = null;
		Document jdom;
		try
		{
			jdom = saxb.build(xmlRequest);
			Element racineInit = (Element) jdom.getRootElement();
			Document newDoc = new Document();
			Namespace ns = Namespace.getNamespace("Ciril:Enfance:ServicesEnfance");
			Element newRacine = new Element(registrationType, ns);
			newDoc.addContent(newRacine);
			Element sRacine = new Element(registrationType + "Request");
			newRacine.addContent(sRacine);
			listElements(racineInit, sRacine, registrationType);
			xmlModified = XmlObject.Factory.parse(out.outputString(newDoc));
		}
		catch (JDOMException e)
		{
			logger.fatal(e.getMessage());
		}
		catch (IOException e)
		{
			logger.fatal(e.getMessage());
		}
		catch (XmlException e)
		{
			logger.fatal(e.getMessage());
		}
		return xmlModified;
	}

	private void listElements(Element element, Element newElement, String registrationType)
	{
		List<Element> elements = (List<Element>) element.getChildren();
		Namespace ns2 = Namespace.getNamespace("com", "http://www.libredemat.org/schema/common");
		Namespace ns3 = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		for (Element elem : elements)
		{
			if (elem.getName().equals("Individuals"))
			{
				Element child = new Element(elem.getName());
				newElement.addContent(child);
				child.setText(elem.getText());
				List<Attribute> atts = elem.getAttributes();
				Element child1 = null;
				Attribute attResp = null;
				String value = "";
				for (Attribute at : atts)
				{
					if (at.getName().equals("type"))
					{
						if (at.getValue().equals("com:ChildType"))
						{
							child1 = new Element("Child");
						}
						else
						{
							child1 = new Element("Adult");
						}
					}
					if (at.getName().equals("isHomeFolderResponsible"))
					{
						attResp = new Attribute(at.getName(), at.getValue());
					}
				}
				child.addContent(child1);
				if (attResp != null)
				{
					child1.setAttribute(attResp);
				}
				listElements(elem, child1, registrationType);
			}
			else
			{
				if (elem.getName().equals("RecreationActivity") || elem.getName().equals("Activities")
						|| elem.getName().equals("PerischoolActivity") || elem.getName().equals("FoodDiet")
						|| elem.getName().equals("CanteenAttendingDays"))
				{
					if (elem.getChild("Name", ns2).getAttributeValue("nil", ns3) != null)
					{
						// do nothing
					}
					else
					{
						Element child = new Element(elem.getName());
						newElement.addContent(child);
						child.setText(elem.getText());
						listElements(elem, child, registrationType);
					}
				}
				else
				{
					Element child = new Element(elem.getName());
					newElement.addContent(child);
					child.setText(elem.getText());
					listElements(elem, child, registrationType);
				}
			}
		}
	}

	@Override
	public ReservationResumeResponseDocument getReservationResume(String endPoint, String externalFolderId,
			String FolderId, Date start, Date end, String sessionId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(start);
		String[] sd = startDate.split("-");
		String endDate = sdf.format(end);
		String[] ed = endDate.split("-");
		cirilClientService.setDefaultUri(endPoint);
		ReservationResumeRequestDocument rrrd = ReservationResumeRequestDocument.Factory.newInstance();
		ReservationResumeRequest rrr = rrrd.addNewReservationResumeRequest();
		Calendar calstart = new GregorianCalendar(Integer.valueOf(sd[0]), Integer.valueOf(sd[1]) - 1,
				Integer.valueOf(sd[2]));
		Calendar calend = new GregorianCalendar(Integer.valueOf(ed[0]), Integer.valueOf(ed[1]) - 1,
				Integer.valueOf(ed[2]));
		rrr.setExternalFolderId(externalFolderId);
		rrr.setFolderId(FolderId);
		rrr.setSessionId(sessionId);
		rrr.setStartDate(calstart);
		rrr.setEndDate(calend);
		logger.debug("Get the reservation resumé request : " + rrrd.toString());
		ReservationResumeResponseDocument rrrespd = null;
		try
		{
			rrrespd = (ReservationResumeResponseDocument) cirilClientService.marshalSendAndReceive(rrrd);
			logger.info("Get the reservation resumé response : " + rrrespd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
		return rrrespd;
	}

	@Override
	public ReservationActivityPlanningResponseDocument getReservationPlanning(String endPoint, String activityCode,
			String folderId, String externalFolderId, String childId, String externalChildId, Date start, Date end,
			String sessionId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(start);
		String[] sd = startDate.split("-");
		String endDate = sdf.format(end);
		String[] ed = endDate.split("-");
		Calendar calstart = new GregorianCalendar(Integer.valueOf(sd[0]), Integer.valueOf(sd[1]) - 1,
				Integer.valueOf(sd[2]));
		Calendar calend = new GregorianCalendar(Integer.valueOf(ed[0]), Integer.valueOf(ed[1]) - 1,
				Integer.valueOf(ed[2]));
		cirilClientService.setDefaultUri(endPoint);
		ReservationActivityPlanningRequestDocument raprd = ReservationActivityPlanningRequestDocument.Factory
				.newInstance();
		ReservationActivityPlanningRequest rapr = raprd.addNewReservationActivityPlanningRequest();
		rapr.setActivityCode(activityCode);
		rapr.setFolderId(folderId);
		rapr.setExternalFolderId(externalFolderId);
		rapr.setChildId(childId);
		rapr.setExternalChildId(externalChildId);
		rapr.setSessionId(sessionId);
		rapr.setStartDate(calstart);
		rapr.setEndDate(calend);
		logger.info("Get the reservation planning request : " + raprd.toString());
		ReservationActivityPlanningResponseDocument raprespd = null;
		try
		{
			raprespd = (ReservationActivityPlanningResponseDocument) cirilClientService.marshalSendAndReceive(raprd);
			logger.info("Get the reservation planning response : " + raprespd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
		return raprespd;
	}

	@Override
	public UpdateReservationResponseDocument getUpdateReservation(String endPoint, String folderId,
			String externalFolderId, String sessionId, Map<String, Map> reservationItems) throws CvqException,
			ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		UpdateReservationRequestDocument urrd = UpdateReservationRequestDocument.Factory.newInstance();
		UpdateReservationRequest urr = urrd.addNewUpdateReservationRequest();
		urr.setFolderId(folderId);
		urr.setExternalFolderId(externalFolderId);
		urr.setSessionId(sessionId);
		Calendar calendar = new GregorianCalendar();
		for (Map.Entry<String, Map> map : reservationItems.entrySet())
		{
			ChildReq cr = urr.addNewChildReq();
			cr.setExternalChildId((String) map.getKey());
			Map<String, Map> mapCast = map.getValue();
			for (Map.Entry<String, Map> mapActiCode : mapCast.entrySet())
			{
				ActivitiesReq ar = cr.addNewActivitiesReq();
				ar.setActivityCode(mapActiCode.getKey());
				Map<String, List<Map>> mapActiCodeCast = mapActiCode.getValue();
				for (Map.Entry<String, List<Map>> mapActiSerCode : mapActiCodeCast.entrySet())
				{
					ActivityServicesReq asr = ar.addNewActivityServicesReq();
					asr.setActivityServiceCode(mapActiSerCode.getKey());
					List<Map> mapActiSerCodeCast = mapActiSerCode.getValue();
					for (Map<String, Object> mapDay : mapActiSerCode.getValue())
					{
						ActivityServicesDateReq asdr = asr.addNewActivityServicesDateReq();
						Timestamp time = (Timestamp) mapDay.get("day");
						String timezone = time.toString();
						String[] withoutTime = timezone.split(" ");
						String[] finalDate = withoutTime[0].split("-");
						Calendar thisDate = new GregorianCalendar(Integer.valueOf(finalDate[0]),
								Integer.valueOf(finalDate[1]) - 1, Integer.valueOf(finalDate[2]));
						asdr.setDate(thisDate);
						asdr.setDayType(DayType.Enum.forString((String) mapDay.get("dayType")));
					}
				}
			}
		}
		logger.debug("Get the reservation Update request : " + urrd.toString());
		UpdateReservationResponseDocument urRespd = null;
		try
		{
			urRespd = (UpdateReservationResponseDocument) cirilClientService.marshalSendAndReceive(urrd);
			logger.info("Get the reservation update response : " + urRespd.toString());
		}
		catch (XmlMappingException xme)
		{
			logger.error("error treating request : " + xme);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
		catch (Exception e)
		{
			logger.error("error with the request : " + e);
		}
		return urRespd;
	}

	@Override
	public void getCancelReservation(String endPoint, String sessionId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		CancelReservationRequestDocument crrd = CancelReservationRequestDocument.Factory.newInstance();
		CancelReservationRequest crr = crrd.addNewCancelReservationRequest();
		crr.setSessionId(sessionId);
		logger.info("Get the reservation cancel request : " + crrd.toString());
		CancelReservationResponseDocument crrespd = null;
		try
		{
			crrespd = (CancelReservationResponseDocument) cirilClientService.marshalSendAndReceive(crrd);
			logger.info("Get the reservation cancel response : " + crrespd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
	}

	@Override
	public void getPaymentReturnValidation(String endPoint, String folderId, String externalFolderId,
			String returnType, String sessionId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		cirilClientService.setDefaultUri(endPoint);
		PostPaymentReturnRequestDocument pprrd = PostPaymentReturnRequestDocument.Factory.newInstance();
		PostPaymentReturnRequest pprr = pprrd.addNewPostPaymentReturnRequest();
		pprr.setFolderId(folderId);
		pprr.setExternalFolderId(externalFolderId);
		pprr.setReturnType(ReturnType.Enum.forString(returnType));
		pprr.setSessionId(sessionId);
		logger.info("Get the return payment validation request : " + pprrd.toString());
		try
		{
			cirilClientService.marshalSendAndReceive(pprrd);
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
	}

	@Override
	public AmountVerificationResponseDocument getAmountVerification(String endPoint, String folderId,
			String externalFolderId, Integer amountInCent, String sessionId) throws CvqException, ParseException
	{
		// if (!isCirilServerStarted(endPoint)) throw new
		// CvqException("Le serveur CirilNetEnfance n'est pas démarré");
		// Amount verification
		// create soap enveloppe, feed it and send it
		cirilClientService.setDefaultUri(endPoint);
		AmountVerificationRequestDocument avrd = AmountVerificationRequestDocument.Factory.newInstance();
		AmountVerificationRequest avr = avrd.addNewAmountVerificationRequest();
		avr.setAmountInCent(amountInCent);
		avr.setFolderId(folderId);
		avr.setExternalFolderId(externalFolderId);
		avr.setSessionId(sessionId);
		logger.debug("Get the amount verification request : " + avrd.toString());
		AmountVerificationResponseDocument avrespd = null;
		try
		{
			avrespd = (AmountVerificationResponseDocument) cirilClientService.marshalSendAndReceive(avrd);
			logger.info("Get the amount verification response : " + avrespd.toString());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Le connecteur a rencontré un problème. Le serveur Ciril est indisponible.");
		}
		return avrespd;
	}

	public void setCirilClientService(WebServiceTemplate cirilClientService)
	{
		this.cirilClientService = cirilClientService;
	}

	@Override
	public boolean isCirilServerStarted(String endPointReservation)
	{
		TimeOut t = new TimeOut(endPointReservation, cirilClientService, timeOutServerStarted, true);
		try
		{
			boolean sucess = t.execute(); // Will return false if this times out
			if (!sucess)
			{
				// This thread timed out
				logger.info("isCirilServerStarted : NON ");
				return false;
			}
			else
			{
				logger.info("isCirilServerStarted : OUI ");
				return true;
			}
		}
		catch (InterruptedException e)
		{
			logger.info("isCirilServerStarted : NON ");
			return false;
		}
	}

	/**
	 * WS ReservationClient de CapDemat 4.2
	 */
	@Override
	public ExternalDocumentReponseDocument getExternalDocument(String endPointReservation, Long homeFolderId,
			String externalId) throws CvqException, ParseException
	{
		logger.debug("proposed endPoint : " + endPointReservation);
		ExternalDocumentReponseDocument result = null;
		cirilClientService.setDefaultUri(endPointReservation);
		GetExternalDocumentDocument1 gedd = GetExternalDocumentDocument1.Factory.newInstance();
		GetExternalDocument ged = gedd.addNewGetExternalDocument();
		ged.setIdFolder(homeFolderId);
		ged.setExternalHomeFolderId(externalId);
		logger.debug("getExternalDocument() : " + gedd.xmlText());
		try
		{
			result = (ExternalDocumentReponseDocument) cirilClientService.marshalSendAndReceive(gedd);
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error sending request : " + ew);
			throw new CvqException("Erreur lors de l'envoi de la demande au service externe");
		}
		logger.debug("VerificationDocumentList() got result : " + result.xmlText());
		return result;
	}

	@Override
	/**
	 *  CodeCommune : Code INSEE de la commune (obligatoire dans le cas de communautés de communes)
		CodeFamille : Code de la famille dans Civil Net Enfance
		codeExterneFamille : Code CapDemat de la famille
		CodeEnfant : Code de l'enfant dans CNE.
		CodeExterneEnfant : Code CapDemat de l'enfant
		codeBaseEleveNiveau : Code base élève du niveau scolaire souhaité.
		dateReferenceAnneeScolaire : Date de référence pour la recherche de l'année scolaire
		dateNaissanceEnfant : Date de naissance de l'enfant pour la recherche du niveau scolaire en fonction de l'âge (si niveau non sélectionné)
		codeNiveau : Code Civil Net Enfance du niveau scolaire souhaité
		
	 */
	public GetEcoleSecteurCalculeeResponseDocument getChildSchool(String endPointSchool, String codeCommune,
			String codeEnfant, String codeExterneEnfant, String codeExterneFamille, String codeFamille,
			String codeBaseEleveNiveau, String dateReferenceAnneeScolaire, String dateNaissanceEnfant,
			String codeNiveau, String streetName, String city, String postalCode) throws CvqException, ParseException
	{
		logger.debug("proposed endPoint : " + endPointSchool);
		GetEcoleSecteurCalculeeResponseDocument schoolResponseDocument = null;
		cirilClientService.setDefaultUri(endPointSchool);
		GetEcoleSecteurCalculeeDocument getEcoleSecteurCalculeeDocument = GetEcoleSecteurCalculeeDocument.Factory
				.newInstance();
		GetEcoleSecteurCalculee getEcoleSecteurCalculee = getEcoleSecteurCalculeeDocument
				.addNewGetEcoleSecteurCalculee();
		getEcoleSecteurCalculee.setCodeApplication("CAPDEMAT");
		getEcoleSecteurCalculee.setCodeCommune(codeCommune);
		if (streetName != null && city != null && postalCode != null)
		{
			Adresse addr = Adresse.Factory.newInstance();
			AdresseFormatAfnor afnor = AdresseFormatAfnor.Factory.newInstance();
			afnor.setStreetName(streetName);
			afnor.setCity(city);
			afnor.setPostalCode(postalCode);
			addr.setAdresseFormatAfnor(afnor);
			getEcoleSecteurCalculee.setAdresse(addr);
		}
		getEcoleSecteurCalculee.setCodeEnfant(codeEnfant);
		getEcoleSecteurCalculee.setCodeExterneEnfant(codeExterneEnfant);
		getEcoleSecteurCalculee.setCodeExterneFamille(codeExterneFamille);
		getEcoleSecteurCalculee.setCodeFamille(codeFamille);
		InfosScolaires infosScolaires = GetEcoleSecteurCalculee.InfosScolaires.Factory.newInstance();
		infosScolaires.setDateNaissanceEnfant(dateNaissanceEnfant);
		infosScolaires.setCodeBaseEleveNiveau(codeBaseEleveNiveau);
		// infosScolaires.setCodeNiveau("");
		if (dateReferenceAnneeScolaire != null && !dateReferenceAnneeScolaire.equals("")) infosScolaires
				.setDateReferenceAnneeScolaire(dateReferenceAnneeScolaire);
		// codeBaseEleveNiveau
		getEcoleSecteurCalculee.setInfosScolaires(infosScolaires);
		try
		{
			logger.info("getChildSchool() : " + getEcoleSecteurCalculeeDocument.xmlText());
			schoolResponseDocument = (GetEcoleSecteurCalculeeResponseDocument) cirilClientService
					.marshalSendAndReceive(getEcoleSecteurCalculeeDocument);
		}
		catch (XmlMappingException ex)
		{
			logger.error("Erreur lors du traitement de la demande : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("Le nom de l'école de l'enfant n'a pas pu être récupéré : " + ew.getMessage());
			throw new CvqException("Le nom de l'école de l'enfant n'a pas put être récupéré");
		}
		catch (Exception e)
		{
			logger.error("Le nom de l'école de l'enfant n'a pas pu être récupéré : " + e.getMessage());
			throw new CvqException("Le nom de l'école de l'enfant n'a pas put être récupéré");
		}
		// TODO ws Ecole : Décommenter
		// logger.info("schoolResponseDocument : " +
		// schoolResponseDocument.toString());
		return schoolResponseDocument;
	}

	public void setTimeOutServerStarted(Long timeOutServerStarted)
	{
		this.timeOutServerStarted = timeOutServerStarted;
	}

	@Override
	public UpdateReservationResponseDocument getAllReservation(
			String endPointReservation, Map<String, Object> params) throws CvqException {
		logger.debug("proposed endPoint : " + endPointReservation);
		
		UpdateReservationResponseDocument updateReservationResponseDocument = null;
		cirilClientService.setDefaultUri(endPointReservation);
		UpdateGlobalReservationRequestDocument updateGlobalReservationRequestDocument = UpdateGlobalReservationRequestDocument.Factory
				.newInstance();	
		
		UpdateGlobalReservationRequest updateGlobalReservationRequest = updateGlobalReservationRequestDocument.addNewUpdateGlobalReservationRequest();     
		
		// setters
		updateGlobalReservationRequest.setSessionId((String)params.get("sessionId"));
		updateGlobalReservationRequest.setFolderId((String)params.get("folderId"));
		updateGlobalReservationRequest.setExternalFolderId((String)params.get("externalFolderId"));
		updateGlobalReservationRequest.setChildId((String)params.get("childId"));
		updateGlobalReservationRequest.setExternalChildId((String)params.get("externalChildId"));
		updateGlobalReservationRequest.setActivityServiceCode((String)params.get("activityServiceCode"));

		try
		{
			SimpleDateFormat spDate = new SimpleDateFormat("yyyy-MM-dd");
			
			Date dtFormat;
			Calendar cal = Calendar.getInstance();
			if (params.get("startingDate") != null)
			{
				dtFormat = spDate.parse((String)params.get("startingDate"));
				cal.setTime(dtFormat);		
				updateGlobalReservationRequest.setStartingDate(cal);
			}
			if (params.get("endingDate") != null)
			{
				dtFormat = spDate.parse((String)params.get("endingDate"));
				cal.setTime(dtFormat);		
				updateGlobalReservationRequest.setEndingDate(cal);
			}
		}
		catch(Exception e)
		{
			logger.error("Erreur de parse des dates : " + e);
		}
		
		WeeklySchedule weeklySchedule = WeeklySchedule.Factory.newInstance();
		
		weeklySchedule.setMonday((Boolean)params.get("lu"));
		weeklySchedule.setTuesday((Boolean)params.get("ma"));		
		weeklySchedule.setWednesday((Boolean)params.get("me"));
		weeklySchedule.setThursday((Boolean)params.get("je"));
		weeklySchedule.setFriday((Boolean)params.get("ve"));
		weeklySchedule.setSaturday((Boolean)params.get("sa"));
		weeklySchedule.setSunday((Boolean)params.get("di"));
		
		updateGlobalReservationRequest.setWeeklySchedule(weeklySchedule);
		
		try
		{
			logger.info("updateGlobalReservation() : " + updateGlobalReservationRequestDocument.xmlText());
			updateReservationResponseDocument = (UpdateReservationResponseDocument) cirilClientService
					.marshalSendAndReceive(updateGlobalReservationRequestDocument);
			logger.info("Get All reservation update response : " + updateReservationResponseDocument.toString());
			return updateReservationResponseDocument;
		}
		catch (XmlMappingException ex)
		{
			logger.error("Erreur lors du traitement de la demande : " + ex);
			throw new CvqException("Erreur lors du traitement de la demande");
		}
		catch (WebServiceClientException ew)
		{
			logger.error("Le nom de l'école de l'enfant n'a pas pu être récupéré : " + ew.getMessage());
			throw new CvqException("Le nom de l'école de l'enfant n'a pas put être récupéré");
		}
		catch (Exception e)
		{
			logger.error("Le nom de l'école de l'enfant n'a pas pu être récupéré : " + e.getMessage());
			throw new CvqException("Le nom de l'école de l'enfant n'a pas put être récupéré");
		}		
	}
}
