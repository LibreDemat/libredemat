package org.libredemat.plugins.externalservices.digitech.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.springframework.ws.client.WebServiceClientException;
import org.libredemat.plugins.externalservices.digitech.ws.IDigitechClient;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.request.external.RequestExternalAction.Status;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.xml.request.civil.BirthDetailsRequestDocument.BirthDetailsRequest;
import org.libredemat.xml.request.civil.DeathDetailsRequestDocument.DeathDetailsRequest;
import org.libredemat.xml.request.civil.MarriageDetailsRequestDocument.MarriageDetailsRequest;

public class DigitechService extends ExternalProviderServiceAdapter
{
	// private static Logger logger = Logger.getLogger(DigitechService.class);
	private String label;
	private IDigitechClient digitechClient;
	private static final String END_POINT = "EndPoint";
	private String endPoint;

	@Override
	public String sendRequest(XmlObject requestXml) throws CvqException
	{
		if (requestXml instanceof BirthDetailsRequest)
		{
			BirthDetailsRequest birthDetailsRequest = (BirthDetailsRequest) requestXml;
			try
			{
				HashMap<String, String> resultBirth = digitechClient.birthMessageResponse(endPoint, requestXml);
				if (resultBirth.containsKey("WARN"))
				{
					addTrace(birthDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultBirth.get("WARN"));
					throw new CvqModelException("Retour du logiciel métier : " + resultBirth.get("WARN"));
				}
				else if (resultBirth.containsKey("ERROR"))
				{
					addTrace(birthDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultBirth.get("ERROR"));
					throw new CvqModelException("Retour du logiciel métier : " + resultBirth.get("ERROR"));
				}
				else if (resultBirth.containsKey("FATAL"))
				{
					addTrace(birthDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultBirth.get("FATAL"));
					throw new CvqModelException("Retour du logiciel métier : " + resultBirth.get("FATAL"));
				}
				else
				{
					addTrace(birthDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultBirth.get("INFO"));
				}
			}
			catch (ParseException e)
			{
				addTrace(birthDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (JDOMException e)
			{
				addTrace(birthDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (IOException e)
			{
				addTrace(birthDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (XmlException exml)
			{
				addTrace(birthDetailsRequest.getId(), "", Status.ERROR, exml.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + exml.getMessage());
			}
			catch (WebServiceClientException ew)
			{
				addTrace(birthDetailsRequest.getId(), "", Status.ERROR, ew.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + ew.getMessage());
			}
		}
		else if (requestXml instanceof MarriageDetailsRequest)
		{
			MarriageDetailsRequest marriageDetailsRequest = (MarriageDetailsRequest) requestXml;
			try
			{
				HashMap<String, String> resultMariage = digitechClient.mariageMessageResponse(endPoint, requestXml);
				if (resultMariage.containsKey("WARN"))
				{
					addTrace(marriageDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultMariage.get("WARN"));
					throw new CvqModelException("Retour du logiciel métier : " + resultMariage.get("WARN"));
				}
				else if (resultMariage.containsKey("ERROR"))
				{
					addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultMariage.get("ERROR"));
					throw new CvqModelException("Retour du logiciel métier : " + resultMariage.get("ERROR"));
				}
				else if (resultMariage.containsKey("FATAL"))
				{
					addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultMariage.get("FATAL"));
					throw new CvqModelException("Retour du logiciel métier : " + resultMariage.get("FATAL"));
				}
				else
				{
					addTrace(marriageDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultMariage.get("INFO"));
				}
			}
			catch (ParseException e)
			{
				addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (JDOMException e)
			{
				addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (IOException e)
			{
				addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (XmlException exml)
			{
				addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, exml.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + exml.getMessage());
			}
			catch (WebServiceClientException ew)
			{
				addTrace(marriageDetailsRequest.getId(), "", Status.ERROR, ew.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + ew.getMessage());
			}
		}
		else if (requestXml instanceof DeathDetailsRequest)
		{
			DeathDetailsRequest deathDetailsRequest = (DeathDetailsRequest) requestXml;
			try
			{
				HashMap<String, String> resultDeath = digitechClient.deathMessageResponse(endPoint, requestXml);
				if (resultDeath.containsKey("WARN"))
				{
					addTrace(deathDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultDeath.get("WARN"));
					throw new CvqModelException("Retour du logiciel métier : " + resultDeath.get("WARN"));
				}
				else if (resultDeath.containsKey("ERROR"))
				{
					addTrace(deathDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultDeath.get("ERROR"));
					throw new CvqModelException("Retour du logiciel métier : " + resultDeath.get("ERROR"));
				}
				else if (resultDeath.containsKey("FATAL"))
				{
					addTrace(deathDetailsRequest.getId(), "", Status.ERROR, (String) "Retour du logiciel métier : "
							+ resultDeath.get("FATAL"));
					throw new CvqModelException("Retour du logiciel métier : " + resultDeath.get("FATAL"));
				}
				else
				{
					addTrace(deathDetailsRequest.getId(), "", Status.SENT, (String) "Retour du logiciel métier : "
							+ resultDeath.get("INFO"));
				}
			}
			catch (ParseException e)
			{
				addTrace(deathDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (JDOMException e)
			{
				addTrace(deathDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (IOException e)
			{
				addTrace(deathDetailsRequest.getId(), "", Status.ERROR, e.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + e.getMessage());
			}
			catch (XmlException exml)
			{
				addTrace(deathDetailsRequest.getId(), "", Status.ERROR, exml.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + exml.getMessage());
			}
			catch (WebServiceClientException ew)
			{
				addTrace(deathDetailsRequest.getId(), "", Status.ERROR, ew.getMessage());
				throw new CvqModelException("impossible de joindre le logiciel metier : " + ew.getMessage());
			}
		}
		return null;
	}

	private void addTrace(Long requestId, String subkey, Status status, String message)
	{
		digitechClient.addTrace(requestId, status, message, subkey, null, null);
	}

	@Override
	public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo) throws CvqException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId,
			String externalHomeFolderId, String externalId) throws CvqException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems, String cvqReference,
			String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, Date validationDate)
			throws CvqException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
			throws CvqConfigurationException
	{
		setEndPoint((String) externalServiceBean.getProperty(END_POINT));
		if (endPoint == null) throw new CvqConfigurationException("Missing " + END_POINT + " configuration parameter");
	}

	@Override
	public String helloWorld() throws CvqException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	@Override
	public boolean supportsConsumptions()
	{
		return false;
	}

	@Override
	public boolean handlesTraces()
	{
		return true;
	}

	@Override
	public List<String> checkExternalReferential(XmlObject requestXml)
	{
		return Collections.EMPTY_LIST;
	}

	@Override
	public Map<String, Object> loadExternalInformations(XmlObject requestXml) throws CvqException
	{
		return Collections.EMPTY_MAP;
	}

	public void setDigitechClient(IDigitechClient digitechClient)
	{
		this.digitechClient = digitechClient;
	}

	public void setEndPoint(String endPoint)
	{
		this.endPoint = endPoint;
	}
}
