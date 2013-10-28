package org.libredemat.security.aspect;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.libredemat.business.authority.SiteProfile;
import org.libredemat.business.authority.SiteRoles;
import org.libredemat.security.PermissionException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextType;
import org.springframework.core.Ordered;


@Aspect
public class ContextAspect implements Ordered {

    private Logger logger = Logger.getLogger(ContextAspect.class);

    @Before("org.libredemat.SystemArchitecture.businessService() && @annotation(context)")
    public void contextAnnotatedMethod(JoinPoint joinPoint, Context context) {

        ContextType[] contextTypes = context.types();

        logger.debug("contextAnnotatedMethod() checking access types " + contextTypes
                + " with privilege " + context.privilege() 
                + " for " + SecurityContext.getCurrentUserLogin()
                + " (" + SecurityContext.getCurrentUserId() + ")"
                + " on resource " + joinPoint.getSignature().getDeclaringType() + "." 
                + joinPoint.getSignature().getName()
        );

        String securityContext = SecurityContext.getCurrentContext();

        if (securityContext.equals(SecurityContext.ADMIN_CONTEXT)) {
            logger.info("contextAnnotatedMethod() letting super adminstrator go through the process");
            return;
        }

        Map<ContextType, String> denials =
            new LinkedHashMap<ContextType, String>(contextTypes.length);
        for (ContextType contextType : contextTypes) {
            check : switch (contextType) {
                case UNAUTH_ECITIZEN :
                    if (!securityContext.equals(SecurityContext.FRONT_OFFICE_CONTEXT)) {
                        denials.put(contextType, "requires FrontOffice context");
                    } else if (SecurityContext.getCurrentEcitizen() != null) {
                        denials.put(contextType, "can only be called by unauthenticated ecitizens");
                    }
                    break;
                case ECITIZEN :
                    if (!securityContext.equals(SecurityContext.FRONT_OFFICE_CONTEXT)) {
                        denials.put(contextType, "requires FrontOffice context");
                    } else if (SecurityContext.getCurrentEcitizen() == null) {
                        denials.put(contextType, "can only be called by authenticated ecitizens");
                    }
                    break;
                case AGENT :
                    if (!securityContext.equals(SecurityContext.BACK_OFFICE_CONTEXT)) {
                        denials.put(contextType, "can only be called in BackOffice context");
                    } else {
                        if (SecurityContext.getCurrentAgent() == null) {
                            denials.put(contextType, "no agent found in security context");
                        } else {
                            for (SiteRoles siteRole :
                                SecurityContext.getCurrentCredentialBean().getSiteRoles()) {
                                if (siteRole.getProfile().equals(SiteProfile.AGENT))
                                    break check;
                            }
                            denials.put(contextType,
                                "current agent does not have an AGENT profile on the site");
                        }
                    }
                    break;
                case ADMIN :
                    if (!securityContext.equals(SecurityContext.BACK_OFFICE_CONTEXT)) {
                        denials.put(contextType, "can only be called in BackOffice context");
                    } else {
                        if (SecurityContext.getCurrentAgent() == null) {
                            denials.put(contextType, "no agent found in security context");
                        } else {
                            for (SiteRoles siteRole :
                                SecurityContext.getCurrentCredentialBean().getSiteRoles()) {
                                if (siteRole.getProfile().equals(SiteProfile.ADMIN))
                                    break check;
                            }
                            denials.put(contextType,
                                "current agent does not have an ADMIN profile on the site");
                        }
                    }
                    break;
                case SUPER_ADMIN :
                    if (!securityContext.equals(SecurityContext.ADMIN_CONTEXT)) {
                        denials.put(contextType, "can only be called in Admin context");
                    }
                    break;
                case EXTERNAL_SERVICE :
                    if (SecurityContext.getCurrentExternalService() == null)
                        denials.put(contextType, "can only be called by an external service");
                    break;
            }
        }
        if (denials.size() == contextTypes.length) {
            String message =
                "\n\tThe following context types have been checked and denied access :\n";
            for (Map.Entry<ContextType, String> denial : denials.entrySet()) {
                message += "\t\t" + denial.getKey() + " : " + denial.getValue() + "\n";
            }
            throw new PermissionException(joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), context.types(), context.privilege(), message);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
