package org.libredemat.plugins.externalservices.digitech.ws.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdom.JDOMException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.libredemat.plugins.externalservices.digitech.ws.IDigitechClient;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.business.request.external.RequestExternalAction.Status;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.xml.request.civil.BirthDetailsRequestDocument.BirthDetailsRequest;
import org.libredemat.xml.request.civil.DeathDetailsRequestDocument.DeathDetailsRequest;
import org.libredemat.xml.request.civil.MarriageDetailsRequestDocument.MarriageDetailsRequest;
import fr.digitech.cityws.messageResponse.MessageResponseDocument;

public class DigitechClient implements IDigitechClient
{
	private static Logger logger = Logger.getLogger(DigitechClient.class);
	private WebServiceTemplate digitechWebClientService;
	private IRequestExternalActionService requestExternalActionService;

	@Override
	public HashMap<String, String> birthMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException
	{
		digitechWebClientService.setDefaultUri(endPoint);
		HashMap<String, String> returnResult = new HashMap<String, String>();
		BirthDetailsRequest bdr = (BirthDetailsRequest) requestXml;
		logger.info("Digitech birth resquet got payload : " + bdr.toString());
		try
		{
			MessageResponseDocument bmr = (MessageResponseDocument) digitechWebClientService.marshalSendAndReceive(bdr);
			logger.debug("Digitech birth response got result : " + bmr.toString());
			returnResult.put(bmr.getMessageResponse().getSeverity().toString(), bmr.getMessageResponse()
					.getDescription());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			returnResult.put("saopError", ex.getMessage());
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error treating request : " + ew);
			returnResult.put("saopError", ew.getMessage());
		}
		return returnResult;
	}

	@Override
	public HashMap<String, String> deathMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException
	{
		digitechWebClientService.setDefaultUri(endPoint);
		HashMap<String, String> returnResult = new HashMap<String, String>();
		DeathDetailsRequest ddr = (DeathDetailsRequest) requestXml;
		logger.debug("Digitech death resquet got payload : " + ddr.toString());
		try
		{
			MessageResponseDocument dmr = (MessageResponseDocument) digitechWebClientService.marshalSendAndReceive(ddr);
			logger.debug("Digitech death response got result : " + dmr.toString());
			returnResult.put(dmr.getMessageResponse().getSeverity().toString(), dmr.getMessageResponse()
					.getDescription());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			returnResult.put("saopError", ex.getMessage());
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error treating request : " + ew);
			returnResult.put("saopError", ew.getMessage());
		}
		return returnResult;
	}

	@Override
	public HashMap<String, String> mariageMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException
	{
		digitechWebClientService.setDefaultUri(endPoint);
		HashMap<String, String> returnResult = new HashMap<String, String>();
		MarriageDetailsRequest mdr = (MarriageDetailsRequest) requestXml;
		logger.debug("Digitech marriage resquet got payload : " + mdr.toString());
		try
		{
			MessageResponseDocument mmr = (MessageResponseDocument) digitechWebClientService.marshalSendAndReceive(mdr);
			logger.debug("Digitech marriage response got result : " + mmr.toString());
			returnResult.put(mmr.getMessageResponse().getSeverity().toString(), mmr.getMessageResponse()
					.getDescription());
		}
		catch (XmlMappingException ex)
		{
			logger.error("error treating request : " + ex);
			returnResult.put("saopError", ex.getMessage());
		}
		catch (WebServiceClientException ew)
		{
			logger.error("error treating request : " + ew);
			returnResult.put("saopError", ew.getMessage());
		}
		return returnResult;
	}

	public void setDigitechWebClientService(WebServiceTemplate digitechWebClientService)
	{
		this.digitechWebClientService = digitechWebClientService;
	}

	@Override
	public void addTrace(long requestId, Status status, String message, String note, byte[] pdfData, String fileName)
	{
		requestExternalActionService.addTrace(new RequestExternalAction(new Date(), requestId, "CapDemat", message,
				"Digitech", status));
	}

	public void setRequestExternalActionService(IRequestExternalActionService requestExternalActionService)
	{
		this.requestExternalActionService = requestExternalActionService;
	}
}
