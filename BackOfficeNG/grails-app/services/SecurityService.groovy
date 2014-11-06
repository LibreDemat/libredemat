import org.libredemat.security.SecurityContext
import org.libredemat.security.annotation.ContextType

public class SecurityService {

    /**
     * Define allowed controller/action pairs according to application
     * and user context (agent, unauthenticated ecitizen, ...).
     */
    private permissions = [
        (ContextType.ADMIN) : [
            (SecurityContext.BACK_OFFICE_CONTEXT) : [
                "backofficeAgent" : /.*/,
                "backofficeCategory" : /.*/,
                "backofficeDisplayGroup" : /.*/,
                "backofficeExternal" : /.*/,
                "backofficeExternalApplication" : /.*/,
                "backofficeHomeFolder" : [/importHomeFolders/,/meansOfContact/,/childInformationSheetDateInitialisation/, /moCs/,/moC/],
                "backofficeLocalAuthority" : /.*/,
                "backofficeLogin" : /.*/,
                "backofficeReferential" : /.*/,
                "backofficePayment" : /.*/,
                "backofficeRequestAdmin" : /.*/,
                "backofficeRequestArchives" : /.*/,
                "backofficeDocumentType" : /.*/,
                "backofficeUserSecurity" : /.*/,
                "backofficeEmail" : /.*/,
                "backofficeUserAdmin" : /.*/,
                "system" : /error/
            ]
        ],
        (ContextType.AGENT) : [
            (SecurityContext.BACK_OFFICE_CONTEXT) : [
                "backofficeContact" : /.*/,
                "backofficeDocumentInstruction" : /.*/,
                "backofficeUserDocumentInstruction" : /.*/,
                "backofficeExternal" : /.*/,
                "backofficeHomeFolder" : /^(?:(?!importHomeFolders|meansOfContact|childInformationSheetDateInitialisation|moCs|moC).)*$/,
                "backofficeLogin" : /.*/,
                "backofficeRequest" : /.*/,
                "backofficeRequestInstruction" : /.*/,
                "backofficeRequestType" : /.*/,
                "backofficeStatistic" : /.*/,
                "backofficeTasks" : /.*/,
                "backofficeTicketBooking" : /.*/,
                "backofficeEmail" : /.*/,
                "system" : /error/
            ],
            (SecurityContext.FRONT_OFFICE_CONTEXT) : [
                "frontofficeTechnocarte" : /.*/,
                "frontofficeRequest" : /.*/,
                "frontofficeRequestDocument" : /.*/,
                "frontofficeRequestType" : /.*/,
                "frontofficePlanning" : /.*/,
                "frontofficeDocument" : [/details/,/binary/,/preview/],
                "frontofficeHome" : [/loginAgent/,/logoutAgent/],
                "serviceAutocomplete" : /.*/,
                "system" : /error/
            ]
        ],
        (ContextType.ECITIZEN) : [
            (SecurityContext.FRONT_OFFICE_CONTEXT) : /.*/
        ],
        (ContextType.UNAUTH_ECITIZEN) : [
            (SecurityContext.FRONT_OFFICE_CONTEXT) : [
                "frontofficeRequestType" : [/login/, /start/],
                "frontofficeHomeFolder" : [/create/, /resetPassword/, /validation/, /newPassword/],
                "frontofficeHome" : [/loginAgent/, /login/, /browsers/, /legal/, /test/],
                "frontofficeDocument" : [/details/, /binary/, /preview/],
                "system" : /error/
            ]
        ]
    ]

    /**
     * Define default entry point (controller/action)
     * according to application and user context.
     */
    private defaultPoints = [
        (ContextType.ADMIN) :
            [controller : "backofficeLocalAuthority"],
        (ContextType.AGENT) : [
            (SecurityContext.BACK_OFFICE_CONTEXT) :
                [controller : "backofficeTasks"],
            (SecurityContext.FRONT_OFFICE_CONTEXT) :
                [controller : "frontofficeRequestType", action : "index"]
        ],
        (ContextType.ECITIZEN) :
            [controller : "frontofficeHome", action : "index"],
        (ContextType.UNAUTH_ECITIZEN) :
            [controller : "frontofficeHome", action : "login"]
    ]

    public Map defaultAccessPoint(ContextType contextType, String securityContext) {
        def contextTypePoint = defaultPoints[contextType]
        if (contextTypePoint[securityContext] == null)
            return contextTypePoint
        else
            return contextTypePoint[securityContext]
    }

    /**
     * According to application and user context,
     * check whether requested controller and action are authorized.
     *
     * Return given controller and action if they are authorized,
     * default entry point otherwise.
     */
    public Map defineAccessPoint(ContextType contextType,
        String securityContext, String controller, String action) {
        if(!contextType) contextType = ContextType.UNAUTH_ECITIZEN
        if (!securityContext)
            securityContext = SecurityContext.FRONT_OFFICE_CONTEXT
        def current = [action : action, controller : controller]
        def contextPermissions = permissions[contextType][securityContext]
        if (contextPermissions == null) {
            return defaultPoints[contextType]
        }
        if (!(contextPermissions instanceof Map)
            && (controller =~ contextPermissions).matches()) {
            return current
        }
        def authorized = false
        if (contextPermissions[controller]) {
            def controllerPermissions = contextPermissions[controller]
            if (!(controllerPermissions instanceof List))
                controllerPermissions = [controllerPermissions]
            for(String regex : controllerPermissions) {
                if ((action =~ regex).matches()) {
                    authorized = true
                    break
                }
            }
        }
        if (authorized) return current
        else {
            def contextDefaultPoints = defaultPoints[contextType][securityContext]
            if (contextDefaultPoints == null)
                return defaultPoints[contextType]
            return contextDefaultPoints
        }
    }

    public void setEcitizenSessionInformation(adult, session) {
        session.currentEcitizenId = adult.id
        if (session.frontContext != ContextType.AGENT)
            session.frontContext = ContextType.ECITIZEN
        SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT)
        SecurityContext.setCurrentEcitizen(adult)
        session.currentEcitizenName = adult.firstName + " " + adult.lastName

        def sessionActivityId= java.util.UUID.randomUUID()
        adult.setSessionActivityId(sessionActivityId.toString())
    }

    public void setAgentSessionInformation(agent, session) {
        session.currentUser = agent.login
        session.currentAgentId = agent.id
        session.frontContext = ContextType.AGENT
        SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
        SecurityContext.setCurrentAgent(agent)
        session.currentAgentName = agent.firstName + " " + agent.lastName
    }

    public void logout(session) {
        session.frontContext = null
        session.currentEcitizenId = null
        session.currentEcitizenName = null
        session.currentCredentialBean = null
        session.currentAgentName = null;
        session.currentAgentId = null;
    }
}
