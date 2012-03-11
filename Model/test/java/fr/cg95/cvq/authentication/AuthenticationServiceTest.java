package fr.cg95.cvq.authentication;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.authority.LocalAuthorityConfigurationBean;
import fr.cg95.cvq.testtool.ServiceTestCase;

public class AuthenticationServiceTest extends ServiceTestCase {

    @Test
    public void testAuthenticationMethod() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);

        // by default, local auth has no specific auth method
        // and the platform default is builtin
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        String defaultAuthenticationMethod = lacb.getAuthenticationMethod();
        assertNull(defaultAuthenticationMethod);
        assertEquals("builtin", authenticationService.getAuthenticationMethod());
        
        // the new setting should override the default one
        lacb.setAuthenticationMethod("cas");
        assertEquals("cas", authenticationService.getAuthenticationMethod());
    }
}
