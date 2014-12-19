package org.libredemat.external.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.ExternalServiceConfigurationBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.external.IExternalService;
import org.libredemat.security.SecurityContext;


public class ExternalService implements IExternalService {

    private static Logger logger = Logger.getLogger(ExternalService.class);

    @Override
    public boolean authenticate(String login, String password) {
        ExternalServiceConfigurationBean escb = 
            SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean();

        ExternalServiceBean esb = escb.getExternalServiceBeanByLogin(login);
        if (esb == null){
            logger.warn("authenticate() unable to find a matching service for with login " + login);
            return false;
        }

        if (esb.getPassword().equals(password))
            return true;

        logger.warn("authenticate() authentication failed for service with login " + login);
        return false;
    }
    
    @Override
    public IExternalProviderService getExternalServiceByLabel(String externalServiceLabel) {
        return SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLabel(externalServiceLabel);
    }

    @Override
    public IExternalProviderService getExternalServiceByLogin(String login) {
        return SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceByLogin(login);
    }

    @Override
    public List<String> getExternalServiceByLabels() {
        return SecurityContext.getCurrentConfigurationBean().getExternalServiceConfigurationBean().getExternalServiceLabels();
    }
}
