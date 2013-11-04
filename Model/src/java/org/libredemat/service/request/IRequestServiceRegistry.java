package org.libredemat.service.request;

import java.util.Collection;
import java.util.List;

import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqObjectNotFoundException;


/**
 * Registry for registered request services.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr) bor@zenexity.fr
 */
public interface IRequestServiceRegistry {

    /**
     * Get all registered request services.
     */
    Collection<IRequestService> getAllRequestServices();
    
    /**
     * Get the service responsible for the management of the given request.
     */
    IRequestService getRequestService(Request request);

    /**
     * Get the service responsible for the management of the given request id.
     */
    IRequestService getRequestService(Long requestId);

    /**
     * Get the service responsible for the management of the request
     * with the given label.
     */
    IRequestService getRequestService(String requestLabel);
    
    /**
     * Get a list of request services supporting creation by un-registered
     * users.
     */
    List<IRequestService> getServicesSupportingUnregisteredCreation();

    /**
     * Get a list of request services supporting the notion of seasons 
     * (aka "registration services")
     */
    List<IRequestService> getServicesSupportingSeasons();
    
    /**
     * Register a new request service.
     */
    void registerService(IRequestService requestService);
}
