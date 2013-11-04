package org.libredemat.exception;

/**
 * Exception thrown when one tries to perform an illegal state change.
 *
 * @author bor@zenexity.fr
 */
public class CvqInvalidTransitionException extends CvqException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor with default message.
     * <br />
     * Usage is discouraged, use
     * {@link #CvqInvalidTransitionException(String from, String to)}
     * when possible.
     */
    public CvqInvalidTransitionException() {
        super("message.invalidTransition");
    }

    /**
     * Display a parametered error message with both states.
     * <br />
     * TODO : Define a State superclass for all *State,
     * and use it here instead of String ?
     */
    public CvqInvalidTransitionException(String from, String to) {
        super("message.invalidTransition.parametered", new String[]{from, to});
    }
}
