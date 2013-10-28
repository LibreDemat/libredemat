import org.libredemat.security.SecurityContext
import org.libredemat.service.payment.IPaymentService
import org.libredemat.service.payment.PaymentResultStatus

class ServiceFakePaymentController {

    IPaymentService paymentService

    def defaultAction = 'index'

    def index = {}

    def process = {

        def separator = params.callbackUrl.contains('?') ? '&' : '?'
        def url = "${params.callbackUrl}${separator}cvqReference=${params.cvqReference}"+
            "&bankReference=${params.cvqReference}&libreDematFake=true&status="

        def status = ''
        if (params.cardNumber == '0123456789') status = 'OK'
        else if (params.cardNumber == '0000000000') status = 'CANCELLED'
        else if (params.cardNumber == '9999999999') status = 'REFUSED'
        url = url + status

        // Commit payment in LibreDemat to know immediatly result in Fake context
        def paymentParams = [:]
        paymentParams.libreDematFake = 'libreDematFake'
        paymentParams.cvqReference = params.cvqReference
        paymentParams.status = status
        SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
        PaymentResultStatus paymentResultStatus = paymentService.commitPayment(paymentParams)

        redirect(url:url)
        return false
    }
}
