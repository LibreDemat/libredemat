package org.libredemat.external;

import java.util.List;

import org.libredemat.business.users.HomeFolder;


public interface IExternalService {

    /**
     * Authenticate an external service.
     */
    boolean authenticate(final String externalServiceLabel, final String password);
    
    IExternalProviderService getExternalServiceByLabel(String externalServiceLabel);

    IExternalProviderService getExternalServiceByLogin(String login);

    List<String> getExternalServiceByLabels();
}
