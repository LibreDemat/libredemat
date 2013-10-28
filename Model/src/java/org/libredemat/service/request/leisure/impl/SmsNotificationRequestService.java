package org.libredemat.service.request.leisure.impl;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.leisure.SmsNotificationRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.impl.RequestService;

/**
 * Implementation of the sms notification request service.
 * 
 * @author Rafik Djedjig (rdj@zenexity.fr)
 */
public class SmsNotificationRequestService extends RequestService {

    // Manage the binding between the request's subject and the CleverSms's contact.
    @Override
    public void onExternalServiceSendRequest(Request request, String sendRequestResult)
        throws CvqException {
        
        SmsNotificationRequest snr = (SmsNotificationRequest)request;
        // Bind Clever SMS contact
        if (sendRequestResult != null)
            snr.setCleverSmsContactId(sendRequestResult);
        // Unbind Clever SMS contact
        else if (!snr.getSubscription())
            snr.setCleverSmsContactId(null);
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof SmsNotificationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new SmsNotificationRequest();
    }
}
