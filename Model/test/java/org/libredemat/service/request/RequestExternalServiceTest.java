package org.libredemat.service.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.LocalAuthorityConfigurationBean;
import org.libredemat.service.request.external.IRequestExternalService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class RequestExternalServiceTest extends RequestTestCase {

    @Autowired
    private IRequestExternalService requestExternalService;
    @Resource(name="fakeExternalService")
    private IExternalProviderService fakeExternalService;
    
    @Test
    public void testHasMatchingExternalService() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);

        ExternalServiceBean esb = new ExternalServiceBean();
        List<String> requestTypes = new ArrayList<String>();
        requestTypes.add("School Registration");
        esb.setRequestTypes(requestTypes);
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        lacb.registerExternalService(fakeExternalService, esb);

        boolean matched = requestExternalService.hasMatchingExternalService("School Registration");
        if (!matched)
            fail("should have matched");
        
        matched = requestExternalService.hasMatchingExternalService("Ticket Booking");
        if (matched)
            fail("should have not matched");

        lacb.unregisterExternalService(fakeExternalService);
    }
    
    @Test
    public void testGetRequestTypesForExternalService() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);

        ExternalServiceBean esb = new ExternalServiceBean();
        List<String> requestTypes = new ArrayList<String>();
        requestTypes.add("School Registration");
        esb.setRequestTypes(requestTypes);
        
        // register the mock external provider service with the LACB
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        lacb.registerExternalService(fakeExternalService, esb);

        Collection<String> requestTypesFromService =
            requestExternalService.getRequestTypesForExternalService(fakeExternalService.getLabel());
        assertNotNull(requestTypesFromService);
        assertEquals(1, requestTypesFromService.size());
        assertEquals("School Registration", requestTypesFromService.iterator().next());

        lacb.unregisterExternalService(fakeExternalService);
    }
}
