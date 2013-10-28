package org.libredemat.exception;

public class CvqNotValidatedAccount extends CvqException {

    private static final long serialVersionUID = 1L;

    public CvqNotValidatedAccount() {
        super();
    }

    public CvqNotValidatedAccount(final String key) {
        super(key);
    }
}
