import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.security.SecurityContext
 
class FrontofficePlanningController {
    def localAuthorityRegistry
 
    def index = {
        def name = SecurityContext.getCurrentConfigurationBean()
                                  .getName()
        def url = localAuthorityRegistry.getLocalAuthorityBeanByName(name)
                                        .getExternalApplicationProperty('booker.url')
        return [url: url]
    }
}
