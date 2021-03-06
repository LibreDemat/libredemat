import java.util.HashMap
import java.util.Map

import org.libredemat.service.payment.IPaymentService
import org.libredemat.security.SecurityContext

class ServiceSystemPayController {

    IPaymentService paymentService

    def index = {

        Map<String, String> parametersMap = new HashMap<String, String>()
        parametersMap.put("etat", params.vads_result)
        for (String key : params.keySet()){
            parametersMap.put(key, params.get(key))
        }
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
