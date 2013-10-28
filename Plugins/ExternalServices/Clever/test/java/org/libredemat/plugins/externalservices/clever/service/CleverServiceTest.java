package org.libredemat.plugins.externalservices.clever.service;

import java.util.ArrayList;
import java.util.List;

import org.libredemat.business.request.LocalReferentialData;
import org.libredemat.business.request.leisure.SmsNotificationRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.IRequestExportService;
import org.libredemat.service.request.RequestTestCase;

import static org.junit.Assert.*;


/**
 * Unit/integration test for Clever external service.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public class CleverServiceTest extends RequestTestCase {

    protected IExternalProviderService externalProviderService;
    protected IRequestExportService requestExportService;

    @Override
    public void onSetUp() throws Exception {
        super.onSetUp();
    }

    protected SmsNotificationRequest gimmeRequest() throws CvqException {

        SecurityContext.setCurrentAgent(this.agentNameWithCategoriesRoles);
        SmsNotificationRequest request = new SmsNotificationRequest();
        request.setRequestType(requestTypeService.getRequestTypeByLabel("Sms Notification"));
        request.setSubjectId(fake.responsibleId);
        request.setHomeFolderId(fake.id);
        // Subscription
        request.setSubscription(Boolean.valueOf(true));
        // Interest
        LocalReferentialData lrd = new LocalReferentialData();
        lrd.setName("Interest-1");
        List<LocalReferentialData> interestsList = new ArrayList<LocalReferentialData>();
        interestsList.add(lrd);
        request.setInterests(interestsList);
        // CleverSmsContact ID

        return request;
    }

    public void testSendRequest() throws CvqException {
        SmsNotificationRequest snr = gimmeRequest();
        
        // Create Clever SMS Contact
        String sendRequestResult = 
            externalProviderService.sendRequest(requestExportService.fillRequestXml(snr));
        assertNotNull(sendRequestResult);
        
        // Update Clever SMS Contact
        snr.setCleverSmsContactId(sendRequestResult);
        sendRequestResult = 
            externalProviderService.sendRequest(requestExportService.fillRequestXml(snr));
        assertNotNull(sendRequestResult);
        
        // Remove Clever SMS Contact
        snr.setSubscription(false);
        sendRequestResult = 
            externalProviderService.sendRequest(requestExportService.fillRequestXml(snr));
        assertNull(sendRequestResult);
    }

    public void setExternalProviderService(IExternalProviderService externalProviderService) {
        this.externalProviderService = externalProviderService;
    }

    public void setRequestExportService(IRequestExportService requestExportService) {
        this.requestExportService = requestExportService;
    }
}
