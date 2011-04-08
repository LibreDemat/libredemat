package fr.cg95.cvq.authentication;

import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.exception.CvqAuthenticationFailedException;
import fr.cg95.cvq.exception.CvqDisabledAccountException;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqUnknownUserException;

/**
 * An authentication service aimed at authenticating e-citizens only.
 *
 * @author bor@zenexity.fr
 */
public interface IAuthenticationService {

    /** default minimal password length for all account types **/
    int passwordMinLength = 8;

    Adult authenticate(final String login, final String passwd)
        throws CvqException, CvqUnknownUserException,
               CvqAuthenticationFailedException, CvqDisabledAccountException;

    String encryptPassword(final String clearPassword)
        throws CvqException;

    String generateLogin(Adult adult);

    /**
     * Generate a new random password.
     */
    String generatePassword();

    /**
     * Set a new password for the given adult.
     */
    void resetAdultPassword(final Adult adult, final String newPassword)
        throws CvqException;
}
