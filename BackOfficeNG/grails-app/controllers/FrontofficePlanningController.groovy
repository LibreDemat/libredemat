import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.security.SecurityContext
import org.libredemat.security.annotation.ContextType
 
class FrontofficePlanningController {
    def localAuthorityRegistry
 
    def index = {
        def name = SecurityContext.getCurrentConfigurationBean()
                                  .getName()
        def url = localAuthorityRegistry.getLocalAuthorityBeanByName(name)
                    .getExternalApplicationProperty('booker.url') + (
                    ((session.frontContext == ContextType.AGENT) ? "?as="+session.currentEcitizenId : ""))
        return [url: url]
    }
}
