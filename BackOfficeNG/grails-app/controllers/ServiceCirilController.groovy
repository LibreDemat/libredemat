import enfanceServicesEnfance.ReturnType;
import org.libredemat.business.payment.ExternalAccountItem
import org.libredemat.business.payment.Payment
import org.libredemat.business.payment.PurchaseItem
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.external.IExternalProviderService
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.request.school.external.IActivityReservationProviderService
import org.libredemat.service.reservation.IReservationService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere
import org.libredemat.security.SecurityContext

class ServiceCirilController {
	
	IPaymentService paymentService
	IExternalHomeFolderService externalHomeFolderService
	IPaymentDAO paymentDAO
	IReservationService reservationService
	
	def negativePayment = { // call when amount to pay is negative or equal to zero
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("ref", params.ref);
		parametersMap.put("transNum", params.transNum);
		parametersMap.put("refsfp", params.transNum);
		parametersMap.put("erreur", params.erreur);
		parametersMap.put("etat", params.etat);
		
		try {
			SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
			
			def ref = params.ref
			Set<Critere> criteriaSet = new HashSet<Critere>()
			Critere critere = new Critere()
			critere.comparatif = Critere.EQUALS
			critere.attribut = Payment.SEARCH_BY_CVQ_REFERENCE
			critere.value = params.ref
			criteriaSet.add(critere)
			def lisPayment = paymentService.get(criteriaSet, null, null, 1, 0)
			if(!lisPayment.isEmpty()){
				Payment payment = (Payment)lisPayment[0]
				def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance",	payment.getHomeFolderId())
				if(hfm != null){
					IExternalProviderService service =
							SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
					if (service instanceof IActivityReservationProviderService) {
						if(params.erreur == "00001" || params.etat == "12") {
							((IActivityReservationProviderService)service).getPaymentValidation(String.valueOf(hfm.homeFolderId), hfm.externalId, 
                                                        ReturnType.CANCELED.toString(), session.activityId);
						} else {
							((IActivityReservationProviderService)service).getPaymentValidation(String.valueOf(hfm.homeFolderId), hfm.externalId,
														ReturnType.SUCCESS.toString(), session.activityId);
						}
						
						def reservationItems = reservationService.getByHomeFolder(params.transNum, hfm.homeFolderId)
							reservationItems.each{res ->
								reservationService.delete(params.transNum, res.id);
						}
						
						// Hack inexine 
						//((IActivityReservationProviderService)service).getCancelReservation(session.activityId);
					}
				}
				parametersMap.put("withoutMail", true);
				paymentService.commitPayment(parametersMap);
			}
			
			redirect(controller:"frontofficePayment", action:"status",params:parametersMap);
			
		} 
		catch (Exception e) 
		{
            log.error "Error while commiting payment ${e}"
            redirect(controller:"frontofficePayment", action:"status",params:parametersMap)
        }
	}
}
