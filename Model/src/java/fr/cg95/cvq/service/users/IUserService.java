package fr.cg95.cvq.service.users;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.GlobalHomeFolderConfiguration;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.business.users.RoleType;
import fr.cg95.cvq.security.annotation.IsUser;

public interface IUserService {

    boolean hasHomeFolderRole(@IsUser Long ownerId, @IsUser Long homeFolderId, RoleType role);

    /**
     * @param adult The {@link Adult} to validate
     * @param login Whether this {@link Adult} must be a login account
     *              (then we need to validate question/answer and password)
     * @return The list of invalid fields
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    List<String> validate(Adult adult, boolean login)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException;

    List<String> validate(Individual individual)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException;

    /**
     * @return Whether or not a home folder can be created without starting a request.
     */
     Boolean homeFolderIndependentCreationEnabled();

    /**
     * Enable home folder creation without starting a request.
     */
    void enableHomeFolderIndependentCreation();

    /**
     * Disable home folder creation without starting a request.
     */
    void disableHomeFolderIndependentCreation();

    /**
     * Activate user account after email verification
     * @param login
     * @param code
     * @return
     */
    Adult activateAccount(String login, String code);

    /**
     * Return true if this adult has an email different from default email
     * @param adult
     * @return
     */
    boolean hasValidEmail(Adult adult);

    /**
     * Prepare password resetting process by affecting a new validation code with an expiration to user
     * @param adult
     */
    void prepareResetPassword(Adult adult);

    /**
     * Check if validation code is correct for password reset
     * @param login
     * @param key
     * @return
     */
    boolean checkResetPasswordLink(String login, String key);

    /**
     * Return Homefolder configuration
     * @return
     */
    public GlobalHomeFolderConfiguration getGlobalHomeFolderConfiguration();
}
