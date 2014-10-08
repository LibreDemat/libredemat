package org.libredemat.exception;

public class CvqParkCardException extends CvqException {

    private static final long serialVersionUID = 1L;

    public CvqParkCardException(String reason) {
        super(reason);
    }

    public CvqParkCardException(String reason, String key) {
        super(key);
    }
    
    public CvqParkCardException(String key, String[] args) {
        super(key, args);
    }
}
