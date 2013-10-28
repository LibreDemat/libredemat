package org.libredemat.authentication;

import static org.junit.Assert.*;

import org.junit.Test;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.LocalAuthorityConfigurationBean;
import org.libredemat.testtool.ServiceTestCase;


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
