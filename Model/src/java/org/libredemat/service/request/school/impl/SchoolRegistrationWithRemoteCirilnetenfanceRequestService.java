package org.libredemat.service.request.school.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.SchoolRegistrationWithRemoteCirilnetenfanceRequest;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.SectionType;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.condition.NotEmptyValueChecker;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the school registration request service.
 * 
 * @author Pierre Pontfort
 */
public final class SchoolRegistrationWithRemoteCirilnetenfanceRequestService extends RequestService {

	@Override
	public void onRequestCompleted(final Request request) throws CvqModelException
	{
		// check school association has been done before validating request
		/*
		 * SchoolRegistrationWithRemoteCirilnetenfanceRequest srr =
		 * (SchoolRegistrationWithRemoteCirilnetenfanceRequest) request; if
		 * (srr.getSchool() == null) throw new
		 * CvqModelException("srr.property.school.validationError"); if
		 * (srr.getSection().equals(SectionType.UNKNOWN)) throw new
		 * CvqModelException( "srr.property.section.validationError");
		 */
	}

	@Override
	public void init()
	{
        SchoolRegistrationWithRemoteCirilnetenfanceRequest.conditions.put("subjectId", new NotEmptyValueChecker());
		SchoolRegistrationWithRemoteCirilnetenfanceRequest.conditions.put("section", new NotEmptyValueChecker());
        SchoolRegistrationWithRemoteCirilnetenfanceRequest.conditions.put("schoolName", new EqualityChecker("other"));

	}
	@Override
	public boolean accept(final Request request)
	{
		return request instanceof SchoolRegistrationWithRemoteCirilnetenfanceRequest;
	}

	@Override
	public Request getSkeletonRequest()
	{
		return new SchoolRegistrationWithRemoteCirilnetenfanceRequest();
	}

	@Override
	public void onApplicationEvent(LibreDematEvent e)
	{
		/*
		 * if (e instanceof RequestEvent) { RequestEvent event = (RequestEvent)
		 * e; if
		 * (RequestEvent.EVENT_TYPE.REQUEST_CLONED.equals(event.getEvent()) &&
		 * accept(event.getRequest())) {
		 * //((SchoolRegistrationWithRemoteCirilnetenfanceRequest)
		 * event.getRequest()).setSection(null); } }
		 */
	}

	public HashMap<Boolean, String> isAuthoriseAccessInTS(HashMap<String, String> map)
	{
		HashMap<Boolean, String> retour = new HashMap<Boolean, String>();
		String string = map.get("cirilServerStarted");
		boolean cirilServerStarted = Boolean.parseBoolean(string);
		if (!cirilServerStarted)
		{
			retour.put(false, "external.warning.server.notStarted");
			return retour;
		}
		/*Adult currentEcitizen = SecurityContext.getCurrentEcitizen();
		String displayTitle = SecurityContext.getCurrentSite().getDisplayTitle();
		if (!currentEcitizen.getAddress().getCity().trim().toLowerCase().equals(displayTitle.trim().toLowerCase()))
		{
			retour.put(false, "external.warning.address.notInCommune");
			return retour;
		}*/
		retour.put(true, "access.ok");
		return retour;
	}
}
