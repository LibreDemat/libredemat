package fr.cg95.cvq.exception;

public class CvqNotValidatedAccount extends CvqException {

    private static final long serialVersionUID = 1L;

    public CvqNotValidatedAccount() {
        super();
    }

    public CvqNotValidatedAccount(final String key) {
        super(key);
    }
}
