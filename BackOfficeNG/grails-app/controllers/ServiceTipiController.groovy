import org.libredemat.security.SecurityContext
import org.libredemat.service.payment.IPaymentService
import org.libredemat.service.payment.PaymentResultStatus

class ServiceTipiController {

    IPaymentService paymentService

    def index = {
        Map<String, String> parametersMap = new HashMap<String, String>()
        parametersMap.put("refdet", params.refdet)
        parametersMap.put("bankReference", params.numauto)
        parametersMap.put("resultrans", params.resultrans)
        parametersMap.put("objet", params.objet)
        parametersMap.put("dattrans", params.dattrans)
        parametersMap.put("heurtrans", params.heurtrans)

        try {
            SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
            paymentService.commitPayment(parametersMap)
            render ''
        } catch (Exception e) {
            log.error "Error while commiting payment ${e}"
            render ''
        }
    }
}
