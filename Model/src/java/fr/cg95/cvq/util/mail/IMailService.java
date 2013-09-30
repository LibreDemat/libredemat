package fr.cg95.cvq.util.mail;

import java.util.Map;

import fr.cg95.cvq.exception.CvqException;

/**
 * A service that provides simple mail sending facilities.
 *
 * @author bor@zenexity.fr
 */
public interface IMailService {

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body,
            final boolean html)
        throws CvqException;

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body)
        throws CvqException;

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body,
            final byte[] attachment, final String attachmentName,
            final boolean html)
        throws CvqException;

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body,
            final byte[] attachment, final String attachmentName)
      throws CvqException;

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body,
            Map<String, byte[]> attachments,
            final boolean html)
        throws CvqException;

    void send(final String from, final String to, final String[] cc,
            final String subject,
            final String body,
            Map<String, byte[]> attachments)
        throws CvqException;

    String prepareBodyFromAsset(final String fileName, final Map<String, String> variables);

}
