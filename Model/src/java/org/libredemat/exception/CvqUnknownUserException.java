package org.libredemat.exception;

/**
 * Exception raised when an unknown citizen tries to log in.
 * 
 * @author bor@zenexity.fr
 */
public class CvqUnknownUserException extends CvqException {

    private static final long serialVersionUID = 1L;

    public CvqUnknownUserException() {
        super();
    }

    public CvqUnknownUserException(final String key) {
        super(key);
    }
}
