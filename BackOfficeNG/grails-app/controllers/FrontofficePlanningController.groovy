import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.security.annotation.ContextType
 
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
