import java.text.SimpleDateFormat
import java.util.Date
import java.util.Map
import java.util.Locale

import org.apache.commons.lang.StringUtils;

import enfanceServicesEnfance.Activities
import enfanceServicesEnfance.ActivitiesResp
import enfanceServicesEnfance.ActivityServices
import enfanceServicesEnfance.ActivityServicesDateResp
import enfanceServicesEnfance.ActivityServicesPlanning
import enfanceServicesEnfance.ActivityServicesResp
import enfanceServicesEnfance.ChildResp
import enfanceServicesEnfance.Day
import org.libredemat.business.authority.LocalAuthorityResource
import org.libredemat.business.payment.ExternalAccountItem
import org.libredemat.business.payment.ExternalInvoiceItem
import org.libredemat.business.payment.ExternalInvoiceItemDetail
import org.libredemat.business.payment.PaymentMode
import org.libredemat.business.reservation.ReservationItem
import org.libredemat.business.users.Adult
import org.libredemat.business.users.Child
import org.libredemat.business.users.Individual
import org.libredemat.business.users.external.IndividualMapping
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean
import org.libredemat.external.IExternalProviderService
import org.libredemat.security.SecurityContext
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.payment.IPaymentService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.external.IRequestExternalService
import org.libredemat.service.request.school.external.IActivityReservationProviderService
import org.libredemat.service.reservation.IReservationService
import org.libredemat.service.users.IUserNotificationService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.users.IUserService
import org.libredemat.service.users.external.IExternalHomeFolderService
import grails.converters.JSON


class FrontofficeReservationController
{
	
	IRequestSearchService requestSearchService
	IRequestExternalService requestExternalService
	IExternalHomeFolderService externalHomeFolderService
	IUserSearchService userSearchService
	IUserService userService
	IReservationService reservationService
	IPaymentService paymentService
	ILocalAuthorityRegistry localAuthorityRegistry
	IUserNotificationService userNotificationService
	def requestActionService
	
	Adult ecitizen
	
	def afterInterceptor =
	{ result ->
		result.month = params.month
		result.year = params.year
		
		def calendar = Calendar.instance
		
		result.currentYear = calendar.get(Calendar.YEAR)
		result.currentMonth = calendar.get(Calendar.MONTH) + 1
		
		/*result.monthsNames = [:]
		String[] months = new DateFormatSymbols().getMonths()
		(0..11).each
		{ it ->
			result.monthsNames[it+1] = months[it]
		}*/
		result.monthsNames = [1:"Janvier",2:"Février",3:"Mars",4:"Avril",5:"Mai",6:"Juin",7:"Juillet",8:"Août",9:"Septembre",10:"Octobre",11:"Novembre",12:"Décembre"]
	}
	
	def beforeInterceptor =
	{
		this.ecitizen = SecurityContext.getCurrentEcitizen()
	}
	
	def index =
	{
		def result = [:]
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	ecitizen.homeFolder.id) // get the mapping for external id
		result.reservations = [:]
		if(hfm != null){																					// if ther is an external id
			IExternalProviderService service =																// get teh externalService CirilNetEnfance in context
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			def from
			def to
			def dates
			if (params.year && params.month)
				dates = buildDate(params.month.toInteger(), params.year.toInteger())
			else
				dates = buildDate(Calendar.instance.get(Calendar.MONTH) + 1,
					Calendar.instance.get(Calendar.YEAR))
			from = dates.from.time
			to = dates.to.time
			if (service instanceof IActivityReservationProviderService) { 									// test of service is existe or not
				LinkedHashMap<Child, Activities[]> getResumee = 											// call the method from external service
					((IActivityReservationProviderService)service).getReservationResume(ecitizen.homeFolder.id, from, to, session.activityId)
				
				Iterator<Individual> iter = getResumee.newKeyIterator()
				while (iter.hasNext()) {										// parse result map
					Individual child = (Individual) iter.next()
					def childId = child.id.toString()
					if(!result.reservations[childId]) result.reservations[childId] = [:]
					result.reservations[childId].ch = child
					
					result.reservations[childId].childInformationSheetFilled = (child instanceof Adult) ? true : userService.isChildInformationSheetFilled(child)
					
					result.reservations[childId].activities = [:]
					Activities[] activities = getResumee.get(child)
					for(Activities acts : activities){							// parse activity for each child
						def ca = acts.getActivityCode()
						if(!result.reservations[childId].activities[ca]) result.reservations[childId].activities[ca] = [:]
						result.reservations[childId].activities[ca].code = acts.getActivityCode()
						if(acts.getActivitylabel()) {
							result.reservations[childId].activities[ca].label = acts.getActivitylabel()
						} else {
							result.reservations[childId].activities[ca].label = null
						}
						result.reservations[childId].activities[ca].activityServices = [:]
						
						def actis = acts.getActivityServicesArray()
						
						for(ActivityServices ass : actis){						// parse service for each activity
							if(ass.getActivityServiceGroupCode()){
								
								def groupType = ass.getActivityServiceGroupCode()
								if(!result.reservations[childId].activities[ca].activityServices[groupType]) result.reservations[childId].activities[ca].activityServices[groupType] = [:]
								result.reservations[childId].activities[ca].activityServices[groupType].type = groupType
								result.reservations[childId].activities[ca].activityServices[groupType].goupCode = ass.getActivityServiceGroupCode()
								result.reservations[childId].activities[ca].activityServices[groupType].groupLabel = ass.getActivityServiceGroupLabel()
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd) result.reservations[childId].activities[ca].activityServices[groupType].asd = [:]
								def cs = ass.getActivityServiceGroupCode()
								def css = ass.getActivityServiceCode()
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd[cs]) result.reservations[childId].activities[ca].activityServices[groupType].asd[cs] = [:]
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css]) result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css] = [:]
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].code = ass.getActivityServiceCode()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].label = ass.getActivityServiceLabel()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].counting = ass.getActivityServiceCount()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].color = ass.getActivityServiceColor()
								
							} else {
								def groupType = "single"
								if(!result.reservations[childId].activities[ca].activityServices[groupType]) result.reservations[childId].activities[ca].activityServices[groupType] = [:]
								result.reservations[childId].activities[ca].activityServices[groupType].type = groupType
								result.reservations[childId].activities[ca].activityServices[groupType].goupCode = "none"
								result.reservations[childId].activities[ca].activityServices[groupType].groupLabel = "none"
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd) result.reservations[childId].activities[ca].activityServices[groupType].asd = [:]
								def cs = ass.getActivityServiceCode()
								def css = ass.getActivityServiceCode()
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd[cs]) result.reservations[childId].activities[ca].activityServices[groupType].asd[cs] = [:]
								if(!result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css]) result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css] = [:]
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].code = ass.getActivityServiceCode()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].label = ass.getActivityServiceLabel()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].counting = ass.getActivityServiceCount()
								result.reservations[childId].activities[ca].activityServices[groupType].asd[cs][css].color = ass.getActivityServiceColor()
							}
						}
					}
				}
			}
		}
		return result
	}
	
	def details = 
	{
		// planning detail for a group of activities
		def result = [:]
		def month = params.month
		def year = params.year
		def activityCode = params.activityCode.decodeURL()
		def childId = params.childId
		
		result = getActivity(month, year, childId, activityCode)		// retreive information for an activity for a child during a month
		if (result == 'deadLine' || result == 'planning')
		{
			redirect(action : "index", params : ["warningMessage" : message("code":"reservation.error.planning." + result)])
			return false;
		}
		result = getModify(result)										// modify information among reservation are being process
		
		result.monthPre = (month.toLong() > 1)?(month.toLong() - 1):(12)	// build month for the next month
		result.monthSui = (month.toLong() < 12)?(month.toLong() + 1):(1)	// build month for the preview month
		result.yearPre = (result.monthPre == 12)?(year.toLong() - 1):(year.toLong()) // build year for the next year
		result.yearSui = (result.monthSui == 1)?(year.toLong() + 1):(year.toLong()) // build year for the preview month
		
		File infoFile = localAuthorityRegistry.getLocalAuthorityResourceFile(  	//  get the information file
			LocalAuthorityResource.INFORMATION_MESSAGE_RESERVATION_FO.id)
		
		if(infoFile.exists() && !infoFile.text.isEmpty()) result.commonInfo = infoFile.text
		
		render(view:'details', model:result)
	}
	
	def getDeleteItemReservation = {
		// delete one item of reservation  from table and response to ajax call
		def itemId = params.itemId.toLong()
		def reservationItem = reservationService.getById(session.activityId, itemId)
		def activityCode =  reservationItem.activityCode
		def childId = reservationItem.childId
		reservationService.delete(session.activityId, itemId)
		render(['result':'ok', 'activityCode':activityCode, 'childId':childId] as JSON) // return map as JSON format
	}
	
	def getDetailShow = {
		// to show the detail of cart
		def homeFolderid = params.homeFolderId.toLong()
		def reservationItems = reservationService.getByHomeFolder(session.activityId, params?.homeFolderId?.toLong())
		def mapFirstName = [:]
		reservationItems.each{
			Individual child = userSearchService.getById(it.childId.toLong())
			mapFirstName."${it.childId}" = child.firstName
		}
		render(template: 'detailShow', model:['items': reservationItems, 'mapFirstName': mapFirstName])
	}
	
	def getPlanningCart = {
		// method to retreive the activity on planning during session
    	def reservationItems
    	def mapFirstName = [:]
    	try 
		{
			reservationItems = reservationService.getByHomeFolder(session.activityId, params.homeFolderId.toLong())
			reservationItems.each
			{
				Individual child = userSearchService.getById(it.childId.toLong())
				mapFirstName."${it.childId}" = child.firstName
			}
    	}
		catch (NumberFormatException ex) 
		{
			log.error("getPlanningCart - " + params.homeFolderId + " : " + ex.getMessage())
		}
		render(template: 'planningCart', model:['items': reservationItems, 'mapFirstName': mapFirstName])
	}
	
	def getPlanningCartToJson = {
		//get the car in JSON format
		def result = []
		try
		{
			def reservationItems = reservationService.getByHomeFolder(session.activityId, params.homeFolderId.toLong())
			def calendar =  Calendar.instance
			reservationItems.each{
				def item = [:]
				item.color = it.color
				item.dayType = it.dayType
				item.childId = it.childId
				item.activityCode = it.activityCode
				calendar.setTime(it.day)
				item.day = String.format(Locale.FRANCE, '%tY/%<tm/%<td', calendar)
				result.add(item)
			}
		}
		catch (NumberFormatException ex) 
		{
			log.error("getPlanningCartToJson - " + params.homeFolderId + " : " + ex.getMessage())
		}
		render(['result':result] as JSON)
	}
	
	def getUpdateReservation = {
		// send and received update reservation
		def homeFolderId = params.homeFolderId
		def reservationItems = reservationService.getByHomeFolder(session.activityId, homeFolderId.toLong())
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	homeFolderId.toLong())
		
		// hack inexine - ATTENTION SI MODIF ALORS AUSSI getPayReservation
		def resume = "\n" + message(code : "homeFolder.adult.user.inquestions") + " |     "+ message(code : "activity.title") + "	|    " + message(code : "event.property.dates") + "	|    " + message(code : "reservation.type") + "\n";
		
		def resaList = [:]
		def resultResponse = [:]
		reservationItems.each{
			Individual child = userSearchService.getById(it.childId)
			def ifm = externalHomeFolderService.getIndividualMapping(hfm, it.childId)
			if(!resaList."${ifm.getExternalId()}") resaList."${ifm.getExternalId()}" = [:]
			if(!resaList."${ifm.getExternalId()}"."${it.activityCode}") resaList."${ifm.getExternalId()}"."${it.activityCode}" = [:]
			if(!resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}") resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}"=[]
			resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}".add(["day":it.day,"dayType":it.dayType])
			
			// hack inexine
			def fullName =  child?.lastName + " " + child?.firstName;			
			def day = "NC"
			try
			{
				day = new SimpleDateFormat("dd-MM-yyyy").format(it.day);
			}
			catch (Exception ex)
			{
			}
			resume += fullName + " | " + it.activityServiceLabel + " | " + day + " | " + message(code : "reservation.type." + it.getDayType()) + "\n";
			// fin hack inexine
		}
		
		HashMap<String, Object> getUpdateReservation = new HashMap<String, Object>()
		IExternalProviderService service = null;
		if(hfm != null){
			service =
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			if (service instanceof IActivityReservationProviderService) {
				getUpdateReservation =
					((IActivityReservationProviderService)service).getUpdateReservation(homeFolderId, hfm.getExternalId(), resaList, session.activityId)
			}
		}
		def result = [:]
		resultResponse.amountNegative = getUpdateReservation.get("amountNegativeInCent")
		resultResponse.amount =getUpdateReservation.get("amoutnToPay")
		ChildResp[] childResp = getUpdateReservation.get("childsItem")
		
		def messageError = []
		result.children = [:]
		childResp.each{	child ->
			if(!result.children."${child.getExternalChildId()}") result.children."${child.getExternalChildId()}" = [:]
			ActivitiesResp[] actiResp = child.getActivitiesRespArray()
			actiResp.each{ acti ->
				if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}")
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}" = [:]
				ActivityServicesResp[] actiServResp = acti.getActivityServicesRespArray()
				actiServResp.each{actiServ ->
					if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}")
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}" = [:]
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}".label = actiServ.getActivityServiceLabel()
					ActivityServicesDateResp[] days = actiServ.getActivityServicesDateRespArray()
					days.each{
						if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}")
							result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}" = [:]
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".date = it.getDate()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".dayAmount = it.getDayAmountInCent()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".erroCode = it.getErrorCode()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorLabel = it.getErrorLabel()
						if(it.getErrorCode() != null){
							Date thisDate = new SimpleDateFormat("yyyy-MM-dd").parse(result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".date.toString())
							def calendar = new GregorianCalendar()
							calendar.setTime(thisDate)
							def ligne = String.format('%te %<tb %<tY', calendar)  + " "
							ligne += result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}".label + " "
							ligne += result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorLabel +"<br/>"
							messageError.add(ligne)
						}
					}
				}
			}
		}
		resultResponse.message = "<div class='list'>"
		if(messageError.size() > 0){
			resultResponse.message += messageError.join()
		} else {
			resultResponse.message += message(code:"reservation.validate");
			// send a mail if is active in configuration.
			ExternalServiceBean externalBean = SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getBeanForExternalService("CirilNetEnfance");
			if (Boolean.valueOf(externalBean.getProperty("isActiveSendMailInValidNoPay")))
			{
				// send Mail
				def from = externalBean.getProperty("mailFrom");
				Adult responsible = userSearchService.getHomeFolderResponsible(homeFolderId.toLong());
				if (responsible != null)
				{
					def to = responsible.getEmail();
					userNotificationService.notifyByEmail(from,	to, message(code:"mail.civilnetenfance.senMailInValidNoPay.subject"),
						message(code:"mail.civilnetenfance.senMailInValidNoPay.body", args:[resume]), null,	null)
				}
				else
				{
					resultResponse.message += message(code:"nomail.nomessage");
				}
			}
		}
		resultResponse.message += "</div>"
		render(['result':resultResponse] as JSON)
	}
	
	/**
	 * Affichage de la boite de dialogue qui résume le contenu du panier avec le montant à payer
	 * 2 Choix : Payer OU Retour planning. @see reservation.js 
	 * Payer renvoi vers /frontoffice/reservation/getPayReservation
	 */
	def getUpdateReservationToPay = {
		// send and received update reservation to pay
		def homeFolderId = params.homeFolderId
		def reservationItems = reservationService.getByHomeFolder(session.activityId, homeFolderId.toLong())
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	homeFolderId.toLong())
		def resaList = [:]
		def resultResponse = [:]
		reservationItems.each{
			Individual child = userSearchService.getById(it.childId)
			def ifm = externalHomeFolderService.getIndividualMapping(hfm, it.childId)
			if(!resaList."${ifm.getExternalId()}") resaList."${ifm.getExternalId()}" = [:]
			if(!resaList."${ifm.getExternalId()}"."${it.activityCode}") resaList."${ifm.getExternalId()}"."${it.activityCode}" = [:]
			if(!resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}") resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}"=[]
			resaList."${ifm.getExternalId()}"."${it.activityCode}"."${it.activityServiceCode}".add(["day":it.day,"dayType":it.dayType])
		}
		
		HashMap<String, Object> getUpdateReservation = new HashMap<String, Object>()
		if(hfm != null){
			IExternalProviderService service =
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			if (service instanceof IActivityReservationProviderService) {
				getUpdateReservation =
					((IActivityReservationProviderService)service).getUpdateReservation(homeFolderId, hfm.getExternalId(), resaList, session.activityId)
			}
		}
		def result = [:]
		result.amount = getUpdateReservation.get("amoutnToPay")
		result.amountNegative = getUpdateReservation.get("amountNegativeInCent")
		ChildResp[] childResp = getUpdateReservation.get("childsItem")
		
		def messageError = [:]
		def messageLine = [:]
		result.children = [:]
		childResp.each{	child ->
			if(!result.children."${child.getExternalChildId()}") result.children."${child.getExternalChildId()}" = [:]
			ActivitiesResp[] actiResp = child.getActivitiesRespArray()
			actiResp.each{ acti ->
				if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}")
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}" = [:]
				ActivityServicesResp[] actiServResp = acti.getActivityServicesRespArray()
				actiServResp.each{actiServ ->
					if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}")
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}" = [:]
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}".label = actiServ.getActivityServiceLabel()
					ActivityServicesDateResp[] days = actiServ.getActivityServicesDateRespArray()
					days.each{
						if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}")
							result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}" = [:]
						
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".date = it.getDate()
						
						Date thisDate = new SimpleDateFormat("yyyy-MM-dd").parse(it.getDate().toString())
						def calendar = new GregorianCalendar()
						calendar.setTime(thisDate)
						
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".dateFormat = String.format('%te %<tb %<tY', calendar)
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".dayAmount = it.getDayAmountInCent()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorCode = it.getErrorCode()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorLabel = it.getErrorLabel()
						IndividualMapping im = externalHomeFolderService.getIndividualMappingByExternalId(child.getExternalChildId(), "CirilNetEnfance", ecitizen.homeFolder.id)
						def thisChild = userSearchService.getById(im.individualId)
						def getResaFromTable = reservationService.getByParams(session.activityId, thisChild.id,acti.getActivityCode() , actiServ.getActivityServiceCode(), thisDate)
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".firstName = thisChild.firstName
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".childId = thisChild.id
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".type = getResaFromTable.dayType

					}
				}
			}
		}
		
		result.homeFolderId = homeFolderId;
		render(template:'payForm', model:result)
	}
	
	def getAmountVerification = {
		// get the amount verifcation result
		
		def amount = params.amount
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	ecitizen.homeFolder.id)
		HashMap<String, Object> getVerification = new HashMap<String, Object>()
		if(hfm != null){
			IExternalProviderService service =
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			if (service instanceof IActivityReservationProviderService) {
				getVerification = ((IActivityReservationProviderService)service).getAmountVerification(hfm.homeFolderId.toString(), hfm.externalId, amount.toInteger(), session.activityId)
			}
		}
		def resultVerif = [:]
		if(!getVerification.get('returnCode').equals("OK")){
			resultVerif.label = getVerification.get('verificationResponse')
			resultVerif.amountExpected = getVerification.get('amountWanted')
		} else {
			resultVerif.label = getVerification.get('returnCode')
		}
		render(["result":resultVerif] as JSON)
	}
	
	def getPayReservation = {
		// feed payment table and redirect to payment systeme
		def result=[:]
		def datas = []
		
		if(params.line instanceof String[]) {
			datas = params.line
		} else {
			datas << params.line
		}
		def lines = []
		def resume = "\n" + message(code : "homeFolder.adult.user.inquestions") + " |     "+ message(code : "activity.title") + "	|    " + message(code : "event.property.dates") + "    |    " + message(code : "payment.header.values") + "\n";
		datas.each{
			if (it != null) {
				def elements = it.tokenize("-")
				Individual child = userSearchService.getById(elements[0].toLong())
				def invoiceLine = [:]
				invoiceLine.labelService = elements[5]
				invoiceLine.lastName = child.lastName
				invoiceLine.fisrtName = child.firstName
				invoiceLine.dayAmount = elements[3]
				lines.add(invoiceLine)
				
				double amount = -0.00;
				def day = "NC"
				def isNegatif = false;
				try
				{
					amount = Double.parseDouble(invoiceLine.dayAmount) / 100;
					day = new SimpleDateFormat("yyyy/MM/dd").parse(elements[4]);
					day = day.format("dd-MM-yyyy");
					isNegatif = Boolean.valueOf(elements[6]);
				}
				catch (Exception ex)
				{
				}
				resume += invoiceLine.lastName + " " + invoiceLine.fisrtName + " | " + invoiceLine.labelService + " | " + day + " | " + (isNegatif?"-":"") + amount + "€\n"; 
				// Hack inexine - le euro ne saffiche pas dans le properties
			}
		}
		try
		{
			result = setInvoice(params.amount.toInteger(), lines);
		}
		catch (CvqException ex)
		{
			redirect(action : "index", params : ["warningMessage" : message("code":"error.broker.notexist")]);
			return false;
		}
		log.debug("Amount pass to setInvoice : " + params.amount)
		ExternalAccountItem eai = (ExternalAccountItem) session.invoice[0]
		
		session.payment = paymentService.createPaymentContainer(eai, PaymentMode.INTERNET)
		if(params.amount.toInteger() <= 0)
		{
			def payment = session.payment
			payment.addPaymentSpecificData('scheme',request.scheme)
			payment.addPaymentSpecificData('domainName',request.serverName)
			payment.addPaymentSpecificData('port',request.serverPort.toString())
			
			// Hack inexine - send a mail if is active in configuration.
			ExternalServiceBean externalBean = SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getBeanForExternalService("CirilNetEnfance");
			if (Boolean.valueOf(externalBean.getProperty("isActiveSendMailInValidNoPay")))
			{
				// send Mail
				def from = externalBean.getProperty("mailFrom");
				Adult responsible = userSearchService.getHomeFolderResponsible(params.homeFolderId.toLong());
				if (responsible != null)
				{
					def to = responsible.getEmail();
					userNotificationService.notifyByEmail(from,	to, message(code:"mail.civilnetenfance.senMailInValidWithPay.subject"),
						message(code:"mail.civilnetenfance.senMailInValidWithPay.body", args:[resume]), null,	null)
				}
				else
				{
					resultResponse.message += message(code:"nomail.nomessage");
				}
			}
			// fin hack inexine
			
			def paymentUrl = paymentService.initPayment(payment).toString()
			session.payment = null;
			redirect(controller:"serviceCiril", action:'negativePayment', params:["ref":payment.getCvqReference(),
					"transNum":session.activityId, "refsfp":session.activityId, "erreur":"00000"])
		} 
		else 
		{
			def payment = session.payment
			payment.addPaymentSpecificData('scheme',request.scheme)
			payment.addPaymentSpecificData('domainName',request.serverName)
			payment.addPaymentSpecificData('port',request.serverPort.toString())
			
			// Hack inexine - send a mail if is active in configuration.
			ExternalServiceBean externalBean = SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getBeanForExternalService("CirilNetEnfance");
			if (Boolean.valueOf(externalBean.getProperty("isActiveSendMailInValidNoPay")))
			{
				// send Mail
				def from = externalBean.getProperty("mailFrom");
				Adult responsible = userSearchService.getHomeFolderResponsible(params.homeFolderId.toLong());
				if (responsible != null)
				{
					def to = responsible.getEmail();
					userNotificationService.notifyByEmail(from,	to, message(code:"mail.civilnetenfance.senMailInValidWithPay.subject"),
						message(code:"mail.civilnetenfance.senMailInValidWithPay.body", args:[resume]), null,	null)
				}
				else
				{
					resultResponse.message += message(code:"nomail.nomessage");
				}
			}
			// fin hack inexine
			
			def paymentUrl = paymentService.initPayment(payment).toString();
			session.payment = null;
			getCleanReservationWithourRender();
			if (paymentService.isPaymnetInPopup(payment))
			{
				render(view: '/frontofficePayment/newPayment', model : ['url' : paymentUrl])
				return
			}
			else
			{
				redirect(url:paymentUrl)
				return false
			}
		}
	}
	
	def getDeleteRefuse = {
		// delete from table reservation item which is refuse by ciril
		def parametter = params.parametters.decodeURL().tokenize('-')
		def day = new SimpleDateFormat("yyyy-MM-dd").parse(parametter[3].replaceAll('/','-'))
		def resa = reservationService.getByParams(session.activityId, parametter[0].toLong(), parametter[1],
			parametter[2], day)
		reservationService.delete(session.activityId, resa.id)
		render(['activityCode':resa.activityCode,'childId':resa.childId] as JSON)
	}
	
	def getCancelReservation = {
		// call cancel reservation web service to reset Ciril reservation temporary table
		def reservationItems = reservationService.getByHomeFolder(session.activityId, ecitizen.homeFolder.id)
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	ecitizen.homeFolder.id)
		if(hfm != null){
			IExternalProviderService service =
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			if (service instanceof IActivityReservationProviderService) {
				((IActivityReservationProviderService)service).getCancelReservation(session.activityId)
			}
		}
		render(['result':'ok'] as JSON)
	}
	
	def getCleanReservation = {
			// clean database table
			def homeFolderId = params.homeFolderId
					def reservationItems = reservationService.getByHomeFolder(session.activityId, homeFolderId.toLong())
					reservationItems.each{res ->
					reservationService.delete(session.activityId, res.id)
			}
			render(['result':'ok'] as JSON)
	}
	
	def getCleanReservationWithourRender = {
		// clean database table
		def homeFolderId = params.homeFolderId
		def reservationItems = reservationService.getByHomeFolder(session.activityId, homeFolderId.toLong())
		reservationItems.each{res ->
			reservationService.delete(session.activityId, res.id)
		}
		session.amountInCent = 0;
	}
	
	def getDayActivityService = {
		// method to display services of a day
		def day = params.day
		def month = params.month
		def year = params.year
		def activityCode = params.activityCode.decodeURL()
		def childId = params.childId
		def result = getActivity(month, year, childId, activityCode)
		result = getModify(result)
		def datOfTheDay = year + "/" + month + "/" + day
		def keyDay = year + "-" + month + "-" + day
		def dayActivities = [:]
		
		result.data.each{
			if(it.value.days[keyDay] != null){
				dayActivities."${it.value.code}" = [:]
				dayActivities."${it.value.code}".reservation = it.value.reservation
				dayActivities."${it.value.code}".label = it.value.label
				dayActivities."${it.value.code}".typeOfTheDay = it.value.days[keyDay]
				dayActivities."${it.value.code}".association = getAssociationType(it.value.days[keyDay])
				dayActivities."${it.value.code}".date = datOfTheDay
				dayActivities."${it.value.code}".color = it.value.color
				dayActivities."${it.value.code}".incompatible = it.value.incompatibleServiceCode.join('µ')
			}
		}
		render(template:'dayActivityService', model:['childId':childId, 'activityCode': activityCode, 'dayActivities': dayActivities])
	}
	
	
	def getItemInStock = {
		// method to stock in table modification for a day
		def datas = []
		if(params.data instanceof String[]) {
			datas = params.data
		} else {
			datas << params.data
		}
		def hf
		def ac
		def ci
		datas.each{
			def dayDatas = it.tokenize('-')
			ReservationItem ri = new ReservationItem()
			ri.session = session.activityId
			Individual child = userSearchService.getById(dayDatas[0].toLong())
			ri.childId = child.getId()
			ci = child.getId()
			ri.homeFolderId = child.getHomeFolder().getId()
			ri.activityCode = dayDatas[1]
			ri.activityServiceCode = dayDatas[2]
			ri.activityServiceLabel = dayDatas[6]
			ri.day = new SimpleDateFormat("yyyy/MM/dd").parse(dayDatas[3])
			ri.dayType = dayDatas[4]
			ri.color = dayDatas[5]
			hf = child.getHomeFolder().getId()
			ac = dayDatas[1]
			if(dayDatas[4] != 'Ouvert') {
				if(dayDatas[4].toString().toLowerCase().equals('reservationencours') ||
				dayDatas[4].toString().toLowerCase().equals('reportencours')) {
					def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
						ri.activityServiceCode, ri.day)
					if(resa==null){
						reservationService.create(ri)
					}
				} else {
					def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
						ri.activityServiceCode, ri.day)
					if(resa!=null){
						reservationService.delete(session.activityId, resa.id)
					}
				}
				
			} else {
				def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
					ri.activityServiceCode, ri.day)
				
				if(resa!=null){
					reservationService.delete(session.activityId, resa.id)
				}
			}
		}
		render(['homeFolder': hf, 'activityCode': ac, 'childId': ci] as JSON)
	}
	
	
	def getAllReservation = {
		
		def payment =  params.payment
		def map = [:]
		if (params.ch_lu != null)  
			map.lu = true
		else
			map.lu = false
			
		if (params.ch_ma != null)  
			map.ma = true
		else
			map.ma = false
		
		if (params.ch_me != null)
			map.me = true
		else
			map.me = false
			
		if (params.ch_je != null)
			map.je = true
		else
			map.je = false
			
		if (params.ch_ve != null)
			map.ve = true
		else
			map.ve = false
			
		if (params.ch_sa != null)
			map.sa = true
		else
			map.sa = false
			
		if (params.ch_di != null)
			map.di = true
		else
			map.di = false
			
		map.sessionId = session.activityId
			
		map.folderId = session.homeFolderId
		def homeFolderId = session.homeFolderId
		map.childId = params.childId
			
		//def reservationItems = reservationService.getByHomeFolder(session.activityId, homeFolderId.toLong())
	
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	session.homeFolderId.toLong())
		map.externalFolderId = hfm.externalId
			
		def ifm = externalHomeFolderService.getIndividualMapping(hfm, params.childId.toLong())
		map.externalChildId = ifm.externalId
			
		map.activityServiceCode = params.activityServiceCode
	

		if (params.startingDate != null)
			map.startingDate = params.startingDate	
		if (params.endingDate != null)
			map.endingDate = params.endingDate
			
			
		// hack inexine - ATTENTION SI MODIF ALORS AUSSI getPayReservation
		def resume = "\n" + message(code : "homeFolder.adult.user.inquestions") + " |     "+ message(code : "activity.title") + "	|    " + message(code : "event.property.dates") + "	|    " + message(code : "reservation.type") + "\n";
			
		HashMap<String, Object> getUpdateReservation = new HashMap<String, Object>()
		IExternalProviderService service =																// get teh externalService CirilNetEnfance in context
		SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
		getUpdateReservation = ((IActivityReservationProviderService)service).getAllReservation(map)
		
		
		def result = [:]
		result.amountNegative = getUpdateReservation.get("amountNegativeInCent")
		result.amount =getUpdateReservation.get("amoutnToPay")
		ChildResp[] childResp = getUpdateReservation.get("childsItem")
		
		def messageError = [:]
		def messageLine = [:]
		result.children = [:]
		childResp.each{	child ->
			if(!result.children."${child.getExternalChildId()}") result.children."${child.getExternalChildId()}" = [:]
			ActivitiesResp[] actiResp = child.getActivitiesRespArray()
			actiResp.each{ acti ->
				if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}")
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}" = [:]
				ActivityServicesResp[] actiServResp = acti.getActivityServicesRespArray()
				actiServResp.each{actiServ ->
					if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}")
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}" = [:]
					result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}".label = actiServ.getActivityServiceLabel()
					ActivityServicesDateResp[] days = actiServ.getActivityServicesDateRespArray()
					days.each{
						if(!result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}")
							result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}" = [:]
						
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".date = it.getDate()
						
						Date thisDate = new SimpleDateFormat("yyyy-MM-dd").parse(it.getDate().toString())
						def calendar = new GregorianCalendar()
						calendar.setTime(thisDate)

						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".dateFormat = String.format('%tA %<td %<tb %<tY', calendar) 						
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".dayAmount = it.getDayAmountInCent()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorCode = it.getErrorCode()
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".errorLabel = it.getErrorLabel()
						IndividualMapping im = externalHomeFolderService.getIndividualMappingByExternalId(child.getExternalChildId(), "CirilNetEnfance", ecitizen.homeFolder.id)
						def thisChild = userSearchService.getById(im.individualId)
	
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".type = "reservationencours"
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".firstName = thisChild.firstName
						result.children."${child.getExternalChildId()}"."${acti.getActivityCode()}"."${actiServ.getActivityServiceCode()}"."${it.getDate()}".childId = thisChild.id

					}
				}
			}
		}
		result.homeFolderId = homeFolderId;
		
		if (Boolean.parseBoolean(payment))
		{
			render(template:'payForm', model:result)
		}
		else
		{
			render(template:'resumeAllReservation', model:result)
		}
		
	}
	
	def getItemInStockMonth = {
		// method to stock in table modif ication for a day
		def datas = params.data
		if (datas == null) render(['errordata': 'nodata'] as JSON)
		
		def map = [:];
		if (datas instanceof String) map = extractData(datas)
		else
		{		
			datas.each
			{
				map = extractData(it)
			}
		}
		def hf = map.hf;
		def ac = map.ac;
		def ci = map.ci;
		render(['homeFolder': hf, 'activityCode': ac, 'childId': ci] as JSON)
	}
	
	private extractData(it)
	{
		def dayDatas = it.tokenize('-');
		ReservationItem ri = new ReservationItem()
		ri.session = session.activityId
		Individual child = userSearchService.getById(dayDatas[0].toLong())
		ri.childId = child.getId()
		def ci = child.getId()
		ri.homeFolderId = child.getHomeFolder().getId()
		ri.activityCode = dayDatas[1]
		ri.activityServiceCode = dayDatas[2]
		ri.activityServiceLabel = dayDatas[6]
		ri.day = new SimpleDateFormat("yyyy/MM/dd").parse(dayDatas[3])
		ri.dayType = dayDatas[4]
		ri.color = dayDatas[5]
		def hf = child.getHomeFolder().getId()
		def ac = dayDatas[1]
		
		if(dayDatas[4] != 'Ouvert') {
			if(dayDatas[4].toString().toLowerCase().equals('reservationencours') ||
			dayDatas[4].toString().toLowerCase().equals('reportencours')) { // case reservationEnCours and reportEnCours
				if(dayDatas[4].toString().toLowerCase().equals('reservationencours')){
					def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
						ri.activityServiceCode, ri.day)
					if(resa==null){
						reservationService.create(ri)
						
					}
					if(dayDatas[7] != null){
						def incompatible = []
						if(dayDatas[7].contains('µ')){
							incompatible = dayDatas[7].tokenize('µ')
						} else {
							incompatible.add(dayDatas[7])
						}
						
						incompatible.each{
							def inc = it.tokenize("|")
							ReservationItem ri2 = new ReservationItem()
							ri2.session = session.activityId
							ri2.childId = child.getId()
							ri2.homeFolderId = child.getHomeFolder().getId()
							ri2.activityCode = dayDatas[1]
							ri2.day = new SimpleDateFormat("yyyy/MM/dd").parse(dayDatas[3])
							ri2.dayType = 'ReportEnCours'
							ri2.activityServiceCode = inc[0]
							ri2.activityServiceLabel = inc[2]
							ri2.color = inc[1]
							def resac = reservationService.getByParams(ri2.session, ri2.childId, ri2.activityCode,
								ri2.activityServiceCode, ri2.day)
							if(resac==null){
								reservationService.create(ri2)
							} else {
								reservationService.delete(session.activityId, resac.id)
							}
						}
					}
				}else {
					def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
						ri.activityServiceCode, ri.day)
					if(resa==null){
						reservationService.create(ri)
					}
					
				}
			} else { // resa is "ReservationValide"
				def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
					ri.activityServiceCode, ri.day)
				if(resa!=null){
					reservationService.delete(session.activityId, resa.id)
				}
				if(dayDatas[7] != null){ // if reservation incompatible service is not null for this resa
					def incompatible = []
					if(dayDatas[7].contains('µ')){
						incompatible = dayDatas[7].tokenize('µ')
					} else {
						incompatible.add(dayDatas[7])
					}
					
					incompatible.each{
						def inc = it.tokenize("|")
						ReservationItem ri2 = new ReservationItem()
						ri2.session = session.activityId
						ri2.childId = child.getId()
						ri2.homeFolderId = child.getHomeFolder().getId()
						ri2.activityCode = dayDatas[1]
						ri2.day = new SimpleDateFormat("yyyy/MM/dd").parse(dayDatas[3])
						ri2.dayType = 'ReportEnCours'
						ri2.activityServiceCode = inc[0]
						ri2.activityServiceLabel = inc[2]
						ri2.color = inc[1]
						def resac = reservationService.getByParams(ri2.session, ri2.childId, ri2.activityCode,
							ri2.activityServiceCode, ri2.day)
						if(resac==null){
							reservationService.create(ri2)
						} else {
							reservationService.delete(session.activityId, resac.id)
						}
					}
				}
			}
			
		} else { // if the day is "Ouvert"
			def resa = reservationService.getByParams(ri.session, ri.childId, ri.activityCode,
				ri.activityServiceCode, ri.day)
			if(resa != null){
				reservationService.delete(session.activityId, resa.id)
			}
		}
		def map = [:];
		map.hf = hf;
		map.ac = ac;
		map.ci = ci;
		return map;
	}
	
	private getAssociationType(type){ // method to define association among the type of the day
		def result = []
		if(type.toString().toLowerCase().equals('reservationvalide')){
			result.add('associate')
			result.add('')
		} else if(type.toString().toLowerCase().equals('reservationencours')) {
			result.add('associate')
			result.add('-R')
		} else if(type.toString().toLowerCase().equals('reportencours')) {
			result.add('unassociate')
			result.add('-A')
		} else {
			result.add('unassociate')
			result.add('')
		}
		return result
	}
	
	def getMonthlyActivityServices = {
		// method to return monthly activity services
		def month = params.month
		def year = params.year
		def activityServiceCode = params.activityServiceCode.decodeURL()
		def activityCode = params.activityCode.decodeURL()
		def childId = params.childId
		def monthActivities = [:]
		def result = getActivity(month, year, childId, activityCode)
		def payment = params.payment
		/* modify among the catch cart */
		result = getModify(result)
		monthActivities = result.data[activityServiceCode]
		def incompatibility = [:]
		result.data.each{
			println(it.value.incompatibleServiceCode.size())
			if(!(it.value.incompatibleServiceCode.size() < 1)){
				incompatibility."${it.key}" = [:]
				incompatibility."${it.key}".incompatible = [:]
				//println(it.value.incompatibleServiceCode)
				it.value.incompatibleServiceCode.each{ code ->
					if(result.data[code]){
						incompatibility."${it.key}".incompatible."${code}" = [:]
						incompatibility."${it.key}".incompatible."${code}".color = result.data[code].color
						incompatibility."${it.key}".incompatible."${code}".label = result.data[code].label
					}
				}
			}
		}
		
		Date endDateFormat = new Date();
		if (monthActivities.endDateRegistration != null)
		{
			endDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(monthActivities.endDateRegistration.toString());
		}
		
		
		
		render(template:'monthActivityService', model:['childId':result.childId, 'activityCode': activityCode, 'activityServiceCode': activityServiceCode,  
			'isActiveCocheAllInReservationPlaningMonth': SecurityContext.getCurrentConfigurationBean().isActiveCocheAllInReservationPlaningMonth(),
			'isActiveGlobalReservation': SecurityContext.getCurrentConfigurationBean().isActiveGlobalReservation(),
			'endDateRegistration': endDateFormat.format("dd-MM-yyyy"),'weeklySchedule': monthActivities.weeklySchedule, 
				'month': month,'year':year,'deadLine': result.deadLine, 'monthActivities': monthActivities, 'incompatibility':incompatibility, 'payment':payment])
	}
	
	private getModify(map){
		def result = map
		def reservationItems = reservationService.getByHomeFolder(session.activityId, session.homeFolderId.toLong())
		def reservationItemsByChild = reservationItems.findAll{it.childId == result.childId}
		result.data.each{ data ->
			reservationItemsByChild.each{
				if(it.activityServiceCode == data.key){
					def dateFormatString = new SimpleDateFormat("yyyy-MM-dd").format(it.day)
					result.data[data.key].days[dateFormatString] = it.dayType
				}
			}
		}
		return result
	}
	
	
	private getActivity(month, year, childId, activityCode){
		def result = [:]
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	ecitizen.homeFolder.id)
		def ifm = externalHomeFolderService.getIndividualMapping(hfm, childId.toLong())
		Individual child = userSearchService.getById(childId.toLong())
		if(hfm != null){
			IExternalProviderService service =
				SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
			def from
			def to
			def dates
			if (year && month)
				dates = buildDate(month.toInteger(), year.toInteger())
			else
				dates = buildDate(Calendar.instance.get(Calendar.MONTH) + 1,
					Calendar.instance.get(Calendar.YEAR))
			from = dates.from.time
			to = dates.to.time
			if (service instanceof IActivityReservationProviderService) 
			{
				HashMap<String, Object> getPlanning = ((IActivityReservationProviderService)service).getReservationActivity( activityCode,
					ecitizen.homeFolder.id.toString(), hfm.getExternalId(), childId,  ifm.getExternalId(), from, to,  session.activityId)
				// Initialisation
				result.data = [:]
				if (!getPlanning)
				{
					return "planning";
				}
				// HACK INEXINE - PASSER EN INSTANT PAIEMENT - getPlanning.get("instantPayment") OU false
				session.setAttribute("instantPayment", getPlanning.get("instantPayment"))
				session.setAttribute("amountInCent", getPlanning.get("amountInCent"))
				session.setAttribute("homeFolderId", ecitizen.homeFolder.id.toString())
				result.activityCode = getPlanning.get("activityCode")
				result.deadLine = getPlanning.get("deadLine").toString()
				if (!result.deadLine)
				{
					return "deadLine";
				}
				if(getPlanning.containsKey("activityLabel"))
					result.activityLabel = getPlanning.get("activityLabel")
				
				result.childId = child.id
				result.individual = child.firstName
				ActivityServicesPlanning[] aspArray = getPlanning.get("activityServicesPlanning")
				def i = 0
				for(ActivityServicesPlanning asp : aspArray){
					result.data."${asp.activityServiceCode}" = [:]
					
					if(asp.activityServiceColor.size() > 0)
						result.data."${asp.activityServiceCode}".color = asp.activityServiceColor
					else
						result.data."${asp.activityServiceCode}".color = getColor(i)
					result.data."${asp.activityServiceCode}".code = asp.activityServiceCode
					result.data."${asp.activityServiceCode}".label = asp.activityServiceLabel
					result.data."${asp.activityServiceCode}".postPonement = asp.possiblePostPonement.toString()
					result.data."${asp.activityServiceCode}".reservation = asp.possibleReservation.toString()
					result.data."${asp.activityServiceCode}".serviceLinked = asp.activityServiceLinked.toString()
					result.data."${asp.activityServiceCode}".groupCode = asp.activityServiceGroupCode
					result.data."${asp.activityServiceCode}".groupLabel = asp.activityServiceGroupLabel
					result.data."${asp.activityServiceCode}".incompatibleServiceCode = asp.incompatibleActivityServiceCodeArray
					result.data."${asp.activityServiceCode}".days = [:]
					Day[] days = asp.dayArray
					for(Day day : days){
						result.data."${asp.activityServiceCode}".days."${day.date}" = day.dayType
					}
					
					result.data."${asp.activityServiceCode}".endDateRegistration = asp.endDateRegistration
					
					result.data."${asp.activityServiceCode}".weeklySchedule = [:] 
					if(asp.weeklySchedule)
					{
						result.data."${asp.activityServiceCode}".weeklySchedule.monday = asp.weeklySchedule.monday;
						result.data."${asp.activityServiceCode}".weeklySchedule.tuesday = asp.weeklySchedule.tuesday;
						result.data."${asp.activityServiceCode}".weeklySchedule.wednesday = asp.weeklySchedule.wednesday;
						result.data."${asp.activityServiceCode}".weeklySchedule.thursday = asp.weeklySchedule.thursday;
						result.data."${asp.activityServiceCode}".weeklySchedule.friday = asp.weeklySchedule.friday;
						result.data."${asp.activityServiceCode}".weeklySchedule.saturday = asp.weeklySchedule.saturday;
						result.data."${asp.activityServiceCode}".weeklySchedule.sunday = asp.weeklySchedule.sunday;
					}
					
					
					i++
				}
			}
		}
		return result
	}
	
	protected Map buildDate(int month, int year) {
		def result = [
				from : new GregorianCalendar(),
				to: new GregorianCalendar()
			]
		
		result.from.set( Calendar.YEAR, year)
		result.from.set( Calendar.MONTH, month - 1 )
		result.from.set( Calendar.DAY_OF_MONTH, 1 )
		
		result.to.set( Calendar.YEAR, year)
		result.to.set( Calendar.MONTH, month - 1 )
		result.to.set( Calendar.DAY_OF_MONTH, result.from.getActualMaximum(Calendar.DAY_OF_MONTH) )
		
		return result
	}
	
	protected String getColor(int value) { // method to define color when is not set by external software
		def colors = [
			'FF0000',
			'0000FF',
			'66CC33',
			'996666',
			'CCCC00',
			'00FFCC',
			'333300',
			'006666',
			'FF6666',
			'CCFF99',
			'FFFF00',
			'FF9900',
			'999999',
			'3399CC',
			'660000',
			'000000'
		]
		return colors.get(value)
	}
	
	protected setInvoice(Integer amount, List invoiceLine)
	{
		session.invoice =  []
		session.invoiceDetail = []
		def result = [:]
		result.invoices = []
		result.invoiceDetail =[]
		def mapBroker = paymentService.getAllBrokers()
		if (mapBroker == null) 
		{ 
			throw new CvqException("error.broker.notexist");
		}
		def broker = [:]
		if(!mapBroker.isEmpty())
		{
			mapBroker.each{
				if(it.key=='FACTURATION SCOLAIRE'){
					broker.broker= it.key 
					broker.label = it.value
				}
			}
		} else {
			broker.broker = 'FACTURATION SCOLAIRE'
			broker.label = 'Services scolaires'
		}
		
		def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	ecitizen.homeFolder.id)
		if(hfm != null){
			ExternalInvoiceItem invoice = new ExternalInvoiceItem()
			invoice.setLabel("Reservation en ligne")
			invoice.setExternalServiceLabel(hfm.externalServiceLabel)
			invoice.setExternalItemId(session.activityId)
			invoice.setIssueDate(new Date())
			invoice.setExpirationDate(new Date())
			invoice.setPaymentDate(new Date())
			invoice.setIsPaid(false)
			invoice.setSupportedBroker(broker.broker)
			
			def invoiceDetailTab = []
			def totalFacture = 0
			invoiceLine.each{
				ExternalInvoiceItemDetail invoiceDetail = new ExternalInvoiceItemDetail()
				invoiceDetail.setLabel(it.labelService)
				invoiceDetail.setQuantity(new BigDecimal(1))
				invoiceDetail.setSubjectName(it.lastName)
				invoiceDetail.setSubjectSurname(it.fisrtName)
				invoiceDetail.setUnitPrice(it.dayAmount.toInteger())
				invoiceDetail.setValue(it.dayAmount.toInteger())
				totalFacture = totalFacture + it.dayAmount.toInteger()
				invoice.getInvoiceDetails().add(invoiceDetail)
				invoiceDetailTab.add(invoiceDetail)
			}
			if(amount.toInteger() < 0){
				invoice.setAmount(new Double(totalFacture.toString()))
				invoice.setTotalValue(new Double(totalFacture.toString()))
			} else {
				invoice.setAmount(new Double(amount.toString()))
				invoice.setTotalValue(new Double(amount.toString()))
			}
			
			session.invoice.add(invoice)
			session.invoiceDetail.add(invoiceDetailTab)
			result.invoices.add(invoice)
			result.invoiceDetail.add(invoiceDetailTab)
		}
		return result
	}
}

