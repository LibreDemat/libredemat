package org.libredemat.util.sms.impl;

import org.apache.log4j.Logger;
import org.libredemat.exception.CvqException;
import org.libredemat.util.sms.ISmsService;


public class FakeSmsService implements ISmsService {

    private Logger logger = Logger.getLogger(FakeSmsService.class);

    public boolean isEnabled() {
        return true;
    }

    public void send(String number, String message) throws CvqException {
        logger.debug("send() sending " + message + " to " + number);
    }
}
