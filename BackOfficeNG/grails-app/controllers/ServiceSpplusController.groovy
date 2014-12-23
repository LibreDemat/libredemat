import enfanceServicesEnfance.ReturnType
import org.libredemat.business.payment.ExternalInvoiceItem
import org.libredemat.business.payment.Payment
import org.libredemat.business.payment.PaymentState
import org.libredemat.business.payment.PurchaseItem
import org.libredemat.external.IExternalProviderService
import org.libredemat.service.request.school.external.IActivityReservationProviderService
import org.libredemat.service.reservation.IReservationService
import org.libredemat.service.users.external.IExternalHomeFolderService

import org.libredemat.service.payment.IPaymentService
import org.libredemat.security.SecurityContext

class ServiceSpplusController {

    IPaymentService paymentService
    IExternalHomeFolderService externalHomeFolderService
    IReservationService reservationService

    def index = {

        Map<String, String> parametersMap = new HashMap<String, String>()
        parametersMap.put("cvqReference", params.reference)
        parametersMap.put("bankReference", params.refsfp)
        parametersMap.put("refsfp", params.refsfp)
        parametersMap.put("etat", params.etat)

        try {
            SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
            paymentService.commitPayment(parametersMap)

            // inexine hack to get return from reservation payment to ciril
            Payment payment = paymentService.getByCvqReference(params.ref)
            if (payment != null) {
                Set<PurchaseItem> pi = payment.getPurchaseItems()
                Iterator iter = pi.iterator()
                def sessionId
                def returnType
                while(iter.hasNext()){
                    ExternalInvoiceItem eii = (ExternalInvoiceItem) iter.next()
                    sessionId = eii.getExternalItemId()
                }
                if(payment.getState() == PaymentState.VALIDATED){
                    returnType = ReturnType.SUCCESS.toString()
                } else if(payment.getState() == PaymentState.CANCELLED){
                    returnType = ReturnType.CANCELED.toString()
                } else if(payment.getState() == PaymentState.REFUSED){
                    returnType = ReturnType.REFUSED.toString()
                }
                def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance", payment.getHomeFolderId())
                if(hfm != null){
                    IExternalProviderService service =
                            SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
                    if (service instanceof IActivityReservationProviderService) {
                        ((IActivityReservationProviderService)service).getPaymentValidation(String.valueOf(hfm.homeFolderId), hfm.externalId,
                                returnType, sessionId);
                        //if(returnType = ReturnType.REFUSED || returnType = ReturnType.SUCCESS){
                        def reservationItems = reservationService.getByHomeFolder(sessionId, hfm.homeFolderId)
                        if(returnType == ReturnType.SUCCESS.toString()){
                            reservationItems.each{res ->
                                reservationService.delete(sessionId, res.id);
                            }
                            ((IActivityReservationProviderService)service).getCancelReservation(sessionId);
                        }
                    }
                }
            }

            render 'spcheckok'
        } catch (Exception e) {
            log.error "Error while commiting payment ${e}"
            render 'spcheckok'
        }
    }
}
