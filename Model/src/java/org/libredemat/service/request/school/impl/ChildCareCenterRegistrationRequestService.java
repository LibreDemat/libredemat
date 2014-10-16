package org.libredemat.service.request.school.impl;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.school.ChildCareCenterRegistrationRequest;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.SexType;
import org.libredemat.business.users.UserState;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.IUserWorkflowService;

/**
 * Child Care Center Registration Request Pré inscription petite enfance
 * 
 * @author Inexine : Frederic Fabre
 * @Modif pit@tor					
 * 	
 *     TS reporté de la version 4.2
 * 
 */
public class ChildCareCenterRegistrationRequestService extends RequestService
{
	private static Logger logger = Logger.getLogger(ChildCareCenterRegistrationRequestService.class);
	private IUserWorkflowService userWorkflowService;
	private IUserSearchService userSearchService;

	@Override
	public void onRequestIssued(final Request request) throws CvqException
	{
		logger.debug("Child care center Request is created !!");
		ChildCareCenterRegistrationRequest cccrr = (ChildCareCenterRegistrationRequest) request;
		Child child = (Child)userSearchService.getById(cccrr.getSubjectId());
		cccrr.setSubjectChoiceBirthDate(child.getBirthDate());
		cccrr.setSubjectChoiceGender(child.getSex());
				
		/* 
		 * Hack inexine - plus aucun intérêt FOR ALL METHOD
		 * 
		 */
		/*Individual requester = userSearchService.getById(cccrr.getRequesterId());
		if (child.getFirstName().equals("A NAITRE") && child.getLastName().equals("NOUVEL ENFANT"))
		{
			child.setLastName(cccrr.getRequesterLastName());
			if (!cccrr.getSubjectChoiceBirthDate().equals(null))
			{
				child.setBirthDate(new Date());
				Child.class.cast(child).setSex(cccrr.getSubjectChoiceGender());
			}
			else
			{
				child.setBirthDate(new Date()); // a way to ovoid forgotten
												// field
			}
			List<RoleType> roles = new ArrayList<RoleType>();
			if (Adult.class.cast(requester).getTitle().equals(SexType.MALE))
			{
				roles.add(RoleType.CLR_FATHER);
			}
			else if (Adult.class.cast(requester).getTitle().equals(SexType.FEMALE))
			{
				roles.add(RoleType.CLR_MOTHER);
			}
			else
			{
				roles.add(RoleType.CLR_TUTOR);
			}
			this.userWorkflowService.modify(child.getHomeFolder());
			this.userWorkflowService.link(requester, child, roles);
		}
		else
		{
			for (Individual childToDel : this.userSearchService.getChildren(cccrr.getHomeFolderId()))
			{
				if (childToDel.getLastName().equals("NOUVEL ENFANT"))
				{
					this.userWorkflowService.delete(childToDel);
				}
			}
		}*/
	}

	@Override
	public void onRequestCancelled(final Request request) throws CvqException
	{
		/*
		ChildCareCenterRegistrationRequest cccrr = (ChildCareCenterRegistrationRequest) request;
		Child child = (Child) this.userSearchService.getById(cccrr.getSubjectId());
		if (child.getFirstName().equals("A NAITRE"))
		{
			this.userWorkflowService.delete(child);
		}
		*/
	}

	@Override
	public void onRequestRejected(final Request request) throws CvqException
	{
		/*
		ChildCareCenterRegistrationRequest cccrr = (ChildCareCenterRegistrationRequest) request;
		Child child = (Child) this.userSearchService.getById(cccrr.getSubjectId());
		if (child.getFirstName().equals("A NAITRE"))
		{
			this.userWorkflowService.delete(child);
		}*/
	}

	@Override
	public void onRequestValidated(Request request) throws CvqException
	{
		super.onRequestValidated(request);
		/*ChildCareCenterRegistrationRequest cccrr = (ChildCareCenterRegistrationRequest) request;
		Child child = (Child) this.userSearchService.getById(cccrr.getSubjectId());
		if (child.getFirstName().equals("A NAITRE"))
		{
			child.setState(UserState.VALID);
			this.userWorkflowService.modify(child.getHomeFolder());
		}*/
	}

	@Override
	public boolean accept(Request request)
	{
		return request instanceof ChildCareCenterRegistrationRequest;
	}

	@Override
	public Request getSkeletonRequest()
	{
		return new ChildCareCenterRegistrationRequest();
	}

	@Override
	public void init()
	{
		ChildCareCenterRegistrationRequest.conditions.put("mondayPeriod", new EqualityChecker("HALF_DAY"));
		ChildCareCenterRegistrationRequest.conditions.put("fridayPeriod", new EqualityChecker("HALF_DAY"));
		ChildCareCenterRegistrationRequest.conditions.put("thursdayPeriod", new EqualityChecker("HALF_DAY"));
		ChildCareCenterRegistrationRequest.conditions.put("tuesdayPeriod", new EqualityChecker("HALF_DAY"));
		ChildCareCenterRegistrationRequest.conditions.put("wednesdayPeriod", new EqualityChecker("HALF_DAY"));
	}

	/**
	 * @param userWorkflowService
	 *            the userWorkflowService to set
	 */
	public void setUserWorkflowService(IUserWorkflowService userWorkflowService)
	{
		this.userWorkflowService = userWorkflowService;
	}

	/**
	 * @param userSearchService
	 *            the userSearchService to set
	 */
	public void setUserSearchService(IUserSearchService userSearchService)
	{
		this.userSearchService = userSearchService;
	}
}
