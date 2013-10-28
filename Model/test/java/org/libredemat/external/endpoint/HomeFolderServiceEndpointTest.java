package org.libredemat.external.endpoint;

import org.junit.Test;
import org.libredemat.external.endpoint.HomeFolderServiceEndpoint;
import org.libredemat.security.SecurityContext;
import org.libredemat.testtool.ServiceTestCase;
import org.springframework.oxm.xmlbeans.XmlBeansMarshaller;


public class HomeFolderServiceEndpointTest extends ServiceTestCase {

    @Test
    public void testHomeFolderServiceEndpoint() throws Exception {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);
        XmlBeansMarshaller xmlBeansMarshaller = new XmlBeansMarshaller();
        HomeFolderServiceEndpoint hfsEndpoint = new HomeFolderServiceEndpoint(xmlBeansMarshaller);
        hfsEndpoint.setUserService(userService);
        hfsEndpoint.setUserSearchService(userSearchService);
        hfsEndpoint.invokeInternal(null);
        SecurityContext.resetCurrentSite();
    }
}
