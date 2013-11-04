package org.libredemat.exception;

/**
 * Exception raised when an object is not found in DB and was expected to.
 *
 * @author bor@zenexity.fr
 */
public class CvqObjectNotFoundException extends CvqException {

    private static final long serialVersionUID = 1L;

    public CvqObjectNotFoundException() {
        super();
    }

    public CvqObjectNotFoundException(final String key) {
        super(key);
    }
}
