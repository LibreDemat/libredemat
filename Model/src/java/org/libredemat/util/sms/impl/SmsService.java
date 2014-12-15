package org.libredemat.util.sms.impl;

import java.util.Iterator;
import java.util.Map;

import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.security.SecurityContext;
import org.libredemat.util.sms.ISmsProviderService;
import org.libredemat.util.sms.ISmsService;

public class SmsService implements ISmsService {

    public void send(String number, String message) throws CvqException {
        ISmsProviderService smsProvider = getSmsService();
        if (smsProvider != null && smsProvider.isEnabled()) {
            smsProvider.send(number, message);
        } else {
            throw new CvqException("sms_service.not.enabled");
        }
    }

    public ISmsProviderService getSmsService() {
        Map<IExternalProviderService, ExternalServiceBean> extServices =
                SecurityContext.getCurrentConfigurationBean().getExternalServices();
        if (extServices == null || extServices.isEmpty())
            return null;
        Iterator<IExternalProviderService> it = extServices.keySet().iterator();
        while (it.hasNext()) {
            IExternalProviderService cur = it.next();
            if (cur instanceof ISmsProviderService) return (ISmsProviderService) cur;
        }
        return null;
    }
}
