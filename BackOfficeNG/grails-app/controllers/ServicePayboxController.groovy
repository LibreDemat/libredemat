import enfanceServicesEnfance.ReturnType;

import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment
import org.libredemat.business.payment.PaymentState;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.external.IExternalProviderService
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.request.school.external.IActivityReservationProviderService
import org.libredemat.service.reservation.IReservationService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere
import org.libredemat.security.SecurityContext

class ServicePayboxController {
  IPaymentService paymentService
  IExternalHomeFolderService externalHomeFolderService
  IReservationService reservationService

  def index = {

    Map<String, String> parametersMap = new HashMap<String, String>()
    parametersMap.put("ref", params.ref);
    parametersMap.put("transNum", params.transNum);
    parametersMap.put("refsfp", params.transNum);
    parametersMap.put("erreur", params.erreur);

    SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
      log.debug("The error code is : " + params.erreur)
      try {

        paymentService.commitPayment(parametersMap)

        Set<Critere> criteriaSet = new HashSet<Critere>()
        Critere critere = new Critere()
        critere.comparatif = Critere.EQUALS
        critere.attribut = Payment.SEARCH_BY_CVQ_REFERENCE
        critere.value = params.ref
        criteriaSet.add(critere)
        def lisPayment = paymentService.get(criteriaSet, null, null, 1, 0)
        if(!lisPayment.isEmpty()){
          Payment payment = (Payment)lisPayment[0]
          def returnType

          if(payment.getState() == PaymentState.VALIDATED){
            returnType = ReturnType.SUCCESS.toString()
          } else if(payment.getState() == PaymentState.CANCELLED){
            returnType = ReturnType.CANCELED.toString()
          } else if(payment.getState() == PaymentState.REFUSED){
            returnType = ReturnType.REFUSED.toString()
          }

          Set<PurchaseItem> pi = payment.getPurchaseItems()
          Iterator iter = pi.iterator()
          if(iter.hasNext() && !(iter.next() instanceof InternalInvoiceItem)) {
              iter = pi.iterator()
              def sessionId
              while(iter.hasNext()){
                ExternalInvoiceItem eii = (ExternalInvoiceItem) iter.next()
                  sessionId = eii.getExternalItemId()
              }
              def hfm = externalHomeFolderService.getHomeFolderMapping("CirilNetEnfance", payment.getHomeFolderId())
                if(hfm != null){
                  IExternalProviderService service =
                    SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel("CirilNetEnfance")
                    if (service instanceof IActivityReservationProviderService) {
                      ((IActivityReservationProviderService)service).getPaymentValidation(String.valueOf(hfm.homeFolderId),
                        hfm.externalId, returnType, sessionId);

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
        }

        render 'spcheckok'
      } catch (Exception e) {
        log.error "Error while commiting payment ${e}"
          render 'spcheckok'
      }
  }

  def errorStatus = {
    if(params.NUMERR == "16") {
      render "le responsable du compte n'a pas d'adresse email"
    }
  }
}
