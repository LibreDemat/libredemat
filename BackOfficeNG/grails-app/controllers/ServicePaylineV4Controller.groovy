import org.apache.commons.lang.StringUtils

import org.libredemat.security.SecurityContext
import org.libredemat.service.payment.IPaymentService

class ServicePaylineV4Controller {

    IPaymentService paymentService

    def commit = {
        if (log.isDebugEnabled()) {
            params.each {
                log.debug "Got parameter ${it.key} with value ${it.value}"
            }
        }
        SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
        paymentService.commitPayment(["token" : params.token])
        render ""
    }
}
