package org.libredemat.plugins.externalservices.digitech.ws;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdom.JDOMException;
import org.libredemat.plugins.externalservices.digitech.service.DigitechService;
import org.libredemat.business.request.external.RequestExternalAction.Status;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;

public interface IDigitechClient
{
	/**
	 * Birth request sent response
	 * 
	 * @param endPoint
	 * @param requestXml
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws XmlException
	 * @throws CvqModelException
	 */
	HashMap<String, String> birthMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException;

	/**
	 * Mariage request sent response
	 * 
	 * @param endPoint
	 * @param requestXml
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws XmlException
	 * @throws CvqModelException
	 */
	HashMap<String, String> mariageMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException;

	/**
	 * Death request sent response
	 * 
	 * @param endPoint
	 * @param requestXml
	 * @return
	 * @throws CvqException
	 * @throws ParseException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws XmlException
	 * @throws CvqModelException
	 */
	HashMap<String, String> deathMessageResponse(String endPoint, XmlObject requestXml) throws CvqException,
			ParseException, JDOMException, IOException, XmlException, CvqModelException;

	public void addTrace(long requestId, Status status, String message, String note,
			byte[] pdfData, String fileName);
}
