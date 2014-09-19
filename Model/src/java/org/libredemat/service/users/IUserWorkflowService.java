package org.libredemat.service.users;

import java.io.IOException;
import java.util.Collection;

import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.UserState;
import org.libredemat.business.users.UserAction;
import org.libredemat.business.users.UserAction.Type;
import org.libredemat.business.users.ChildInformationSheet;
import org.libredemat.exception.CvqBadPasswordException;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqInvalidTransitionException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.schema.ximport.HomeFolderImportDocument;
import org.libredemat.security.annotation.IsUser;

import com.google.gson.JsonObject;


public interface IUserWorkflowService {

    UserState[] getPossibleTransitions(UserState state);

    UserState[] getPossibleTransitions(@IsUser Individual individual);

    UserState[] getPossibleTransitions(@IsUser HomeFolder homeFolder);

    boolean isValidTransition(UserState from, UserState to);

    UserState[] getStatesBefore(UserState state);

    UserState[] getStatesWithProperty(String propertyName);

    HomeFolder create(Adult adult, boolean temporary, String callbackUrl)
        throws CvqException;

    Long add(@IsUser HomeFolder homeFolder, @IsUser Adult adult, boolean assignLogin)
        throws CvqException;

    Long add(@IsUser HomeFolder homeFolder, @IsUser Child child)
        throws CvqException;

    void modify(@IsUser HomeFolder homeFolder);

    void modify(@IsUser Individual individual, JsonObject atom)
        throws CvqException;

    void modifyPassword(@IsUser Adult adult, String oldPassword, String newPassword)
        throws CvqException, CvqBadPasswordException;

    void modifyConnection(@IsUser Adult adult, String password, String question, String answer);

    /**
     * Send the new password by email to the home folder's responsible,
     * or to the admin address if the responsible has no email address,
     * or output it in confirmation message if none have an address.
     *
     * @return the confirmation message
     */
    String generateAndSendNewPassword(Adult adult)
        throws CvqException;

    /**
     * Start the reset password process by sending email to user with reset link
     * @param adult
     */
    void launchResetPasswordProcess(Adult adult);

    void link(@IsUser Individual owner, @IsUser HomeFolder target, Collection<RoleType> types);

    void unlink(@IsUser Individual owner, @IsUser HomeFolder target);

    void link(@IsUser Individual owner, @IsUser Individual target, Collection<RoleType> roleTypes);

    void unlink(@IsUser Individual owner, @IsUser Individual target);

    void changeState(@IsUser HomeFolder homeFolder, UserState state)
        throws CvqModelException, CvqInvalidTransitionException;

    void changeState(@IsUser Individual individual, UserState state)
        throws CvqModelException, CvqInvalidTransitionException;

    void delete(@IsUser HomeFolder homeFolder);

    void delete(@IsUser Long id);

    void delete(@IsUser Individual individual);

    void importHomeFolders(HomeFolderImportDocument doc)
        throws CvqException, IOException;
    /*
     * Change state of homeFolder and all individual to valid
     * Only possible if current homeFolder state is New or Modify
     */
    void validateHomeFolder(@IsUser HomeFolder homeFolder)
        throws CvqModelException, CvqInvalidTransitionException;
    
    void historize(HomeFolder homeFolder, Type type, Long targetId, JsonObject payload);

    void historize(Adult adult, Type type, String note);

    HomeFolder createOnly(Adult adult) throws CvqException;

    public Long addChildInformationSheet(Child child, ChildInformationSheet childInformationSheet)
        throws CvqModelException, CvqInvalidTransitionException;

	/**
	 * Réinitialise la date de validation de l'ensemble des fiches de renseignement et de sécurité enfant de l'asset
	 * 
	 * @return nombre de fiches réinitialisées
	 */
	public int childInformationSheetDateInitialisation();
}
