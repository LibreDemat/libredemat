package org.libredemat.util.sms;

import org.libredemat.exception.CvqException;

/**
 * A service that provides SMS sending facilities.
 *
 * @author bor@zenexity.fr
 */
public interface ISmsProviderService {

    void send(final String number, final String message)
        throws CvqException;

    boolean isEnabled();
}
