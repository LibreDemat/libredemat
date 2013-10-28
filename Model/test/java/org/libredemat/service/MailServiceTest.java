package org.libredemat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.libredemat.exception.CvqException;
import org.libredemat.testtool.ServiceTestCase;
import org.libredemat.util.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The tests for the mail service.
 *
 * @author bor@zenexity.fr
 */
public class MailServiceTest extends ServiceTestCase {

    @Autowired
    protected IMailService mailService;
    
    @Test
    public void testMail() throws CvqException {

        String to = "bor@zenexity.fr";
        String from = "bobeal@zenexity.fr";
        String[] ccs = new String[] { "bobeal@gmail.com" };
        String subject = "Un email de votre administration";
        String body = "Veuillez trouver ci-joint le document que vous avez commandé ...";
        File file = getResourceFile("Referentiel General Interoperabilite Volet Technique V0.90.pdf");
        byte[] attachmentData = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(attachmentData);
        } catch (FileNotFoundException e) {
            // unlikely to happen since we already checked that
        } catch (IOException ioe) {
            throw new CvqException("error reading data from file " + file.getName());
        }

        mailService.send(from, to, ccs, subject, body, attachmentData, file.getName());
    }
}
