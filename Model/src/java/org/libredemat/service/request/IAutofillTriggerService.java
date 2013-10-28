package org.libredemat.service.request;

import org.libredemat.exception.CvqObjectNotFoundException;

/**
 * @author jsb@zenexity.fr
 *
 */
public interface IAutofillTriggerService {

    /**
     * Retrieve the trigger object whose fields will be used to autofill listeners
     */
    Object getById(final Long id)
        throws CvqObjectNotFoundException;

}
