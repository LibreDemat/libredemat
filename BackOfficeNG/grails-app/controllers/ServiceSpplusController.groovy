import java.util.HashMap
import java.util.Map

import org.libredemat.service.payment.IPaymentService
import org.libredemat.security.SecurityContext

class ServiceSpplusController {

    IPaymentService paymentService

    def index = {

        Map<String, String> parametersMap = new HashMap<String, String>()
        parametersMap.put("cvqReference", params.reference)
        parametersMap.put("bankReference", params.refsfp)
        parametersMap.put("refsfp", params.refsfp)
        parametersMap.put("etat", params.etat)

        try {
            SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
            paymentService.commitPayment(parametersMap)
            render 'spcheckok'
        } catch (Exception e) {
            log.error "Error while commiting payment ${e}"
            render 'spcheckok'
        }
    }
}
