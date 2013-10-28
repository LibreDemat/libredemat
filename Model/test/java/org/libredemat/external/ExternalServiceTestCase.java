package org.libredemat.external;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.IExternalService;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.LocalAuthorityConfigurationBean;
import org.libredemat.service.payment.external.IPaymentExternalService;
import org.libredemat.service.request.RequestTestCase;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FIXME : dependency on request test case has to be fixed
 */
public class ExternalServiceTestCase extends RequestTestCase {

    @Autowired
    protected IExternalService externalService;
    @Autowired
    protected IRequestExternalService requestExternalService;
    @Autowired
    protected IRequestExternalActionService requestExternalActionService;
    @Autowired
    protected IPaymentExternalService paymentExternalService;
    @Autowired
    protected IExternalHomeFolderService externalHomeFolderService;
    
    @Resource(name="fakeExternalService")
    protected IExternalProviderService fakeExternalService;

    @Before
    public void registerFakeExternalService() throws CvqConfigurationException {
        ExternalServiceBean esb = new ExternalServiceBean();
        List<String> requestTypes = new ArrayList<String>();
        requestTypes.add(tirLabel);
        esb.setLogin(fakeExternalService.getLabel());
        esb.setRequestTypes(requestTypes);
        esb.setPassword("12345678");
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        lacb.registerExternalService(fakeExternalService, esb);
    }

    @After
    public void unregisterFakeExternalService() {
        LocalAuthorityConfigurationBean lacb = SecurityContext.getCurrentConfigurationBean();
        lacb.unregisterExternalService(fakeExternalService);
    }    
}
