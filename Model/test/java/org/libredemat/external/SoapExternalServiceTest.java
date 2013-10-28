package org.libredemat.external;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.libredemat.business.request.RequestState;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.impl.SoapExternalServiceClient;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.LocalAuthorityConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;


public class SoapExternalServiceTest extends ExternalServiceTestCase {

    @Resource(name="soapExternalService")
    private IExternalProviderService soapExternalService;
    @Autowired
    private SoapExternalServiceClient soapExternalServiceClient;
    
    @Test
    public void testInteractions() throws CvqException {

        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        lacb.unregisterExternalService(fakeExternalService);
        ExternalServiceBean esb = new ExternalServiceBean();
        esb.setPassword("12345678");
        List<String> requestTypes = new ArrayList<String>();
        requestTypes.add(tirLabel);
        esb.setRequestTypes(requestTypes);
        lacb.registerExternalService(soapExternalService, esb);
        soapExternalServiceClient.setFake(true);
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithManageRoles);
        
        List<String> messages = requestExternalService.checkExternalReferential(request);
        Assert.assertEquals(2, messages.size());
        
        requestWorkflowService.updateRequestState(request.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(request.getId(), RequestState.VALIDATED, null);
        
        continueWithNewTransaction();
        
        Map<String, Object> externalInformations = requestExternalService.loadExternalInformations(request);
        Assert.assertEquals(2, externalInformations.size());

        Map<Date, String> consumptions = 
            requestExternalService.getConsumptions(request.getId(), new Date(), new Date());
        Assert.assertEquals(2, consumptions.size());

        lacb.unregisterExternalService(soapExternalService);
        lacb.registerExternalService(fakeExternalService, esb);
    }
}
