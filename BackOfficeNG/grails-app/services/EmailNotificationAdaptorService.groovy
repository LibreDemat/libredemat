import fr.cg95.cvq.business.request.RequestState
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.business.authority.LocalAuthorityResource.Type
import fr.cg95.cvq.security.SecurityContext

public class EmailNotificationAdaptorService {

    def messageSource
    ILocalAuthorityRegistry localAuthorityRegistry

    /**
     * Return the "states" model suitable for rendering.
     *
     * @param dir the directory where to look for configured states
     */
    def states(dir) {
        def states, file, code, enabled

        states = RequestState.values().inject([], { array, state ->
            file = localAuthorityRegistry.getLocalAuthorityResourceFile(
                Type.HTML,
                dir + '/' + state.toString().toUpperCase(),
                false)
            enabled = false
            try {
                enabled = file.exists()
            } catch (SecurityException se) {
                log.error "No read rights on file " + state.toString().toUpperCase() + ".html"
            }
            code = 'request.state.' + state.toString().toLowerCase()

            array.add([
                code:code,
                label:messageSource.getMessage(code, null,SecurityContext.currentLocale),
                enabled:enabled])
            return array
        })

        return states
    }

}