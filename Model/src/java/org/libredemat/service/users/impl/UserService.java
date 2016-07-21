package org.libredemat.service.users.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.GlobalHomeFolderConfiguration;
import org.libredemat.business.users.GlobalUserConfiguration;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.IndividualRole;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.UserState;
import org.libredemat.business.users.ChildInformationSheet;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.users.IAdultDAO;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.IUserService;
import org.libredemat.util.DateUtils;
import org.libredemat.util.ValidationUtils;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

public class UserService implements IUserService, ILocalAuthorityLifecycleAware {
    private static Logger logger = Logger.getLogger(UserService.class);

    private IUserSearchService userSearchService;
    private IGenericDAO genericDAO;
    private IAdultDAO adultDAO;

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.READ)
    public boolean hasHomeFolderRole(Long ownerId, Long homeFolderId, RoleType role) {
        for (IndividualRole individualRole : userSearchService.getById(ownerId).getIndividualRoles()) {
            if (individualRole.getRole().equals(role)
                && homeFolderId.equals(individualRole.getHomeFolderId()))
                return true;
        }
        return false;
    }

    @Override
    public List<String> validate(Adult adult, boolean login)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Validator validator = new Validator();
        validator.disableAllProfiles();
        validator.enableProfile("default");
        if (login) {
            validator.enableProfile("login");
        }
        Map<String, List<String>> invalidFields = new LinkedHashMap<String, List<String>>();
        for (ConstraintViolation violation : validator.validate(adult)) {
            ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
        }
        List<String> result = new ArrayList<String>();
        for (String profile : new String[] {"", "login"}) {
            if (invalidFields.get(profile) != null) {
                result.addAll(invalidFields.get(profile));
            }
        }
        return result;
    }

    @Override
    public List<String> validate(Individual individual)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Validator validator = new Validator();
        validator.disableAllProfiles();
        validator.enableProfile("default");
        Map<String, List<String>> invalidFields = new LinkedHashMap<String, List<String>>();
        for (ConstraintViolation violation : validator.validate(individual)) {
            ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
        }
        return invalidFields.get("") != null ? invalidFields.get("") : Collections.<String>emptyList();
    }

    @Override
    public List<String> validate(ChildInformationSheet childInformationSheet, boolean informationSheetRequired)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        
    Map<String, List<String>> invalidFields = new LinkedHashMap<String, List<String>>();
    
    Validator validatorPattern = new Validator();
        validatorPattern.disableAllProfiles();
        // Pattern à vérifier dans tous les cas
        validatorPattern.enableProfile("pattern"); 
        for (ConstraintViolation violation : validatorPattern.validate(childInformationSheet)) {
            ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
        }
        
        if (informationSheetRequired) {
            // Pattern à vérifier seulement si la fiche de renseignement est obligatoire
            Validator validatorInformationSheetRequired = new Validator();
            validatorInformationSheetRequired.disableAllProfiles();
            validatorInformationSheetRequired.enableProfile("informationSheetRequired");
            for (ConstraintViolation violation : validatorInformationSheetRequired.validate(childInformationSheet)) {
                ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
            }
        }

        List<String> result = new ArrayList<String>();
        for (String profile : new String[] {"", "informationSheetRequired", "pattern"}) {
            if (invalidFields.get(profile) != null) {
                result.addAll(invalidFields.get(profile));
            }
        }

        if (SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            // Pattern à vérifier seulement si la fiche de renseignement est obligatoire
            Validator validatorInformationSheetRequired = new Validator();
            validatorInformationSheetRequired.disableAllProfiles();
            validatorInformationSheetRequired.enableProfile("informationSheetRequiredFieldsActived");
            for (ConstraintViolation violation : validatorInformationSheetRequired.validate(childInformationSheet)) {
                ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
            }
        }

        for (String profile : new String[] {"", "informationSheetRequiredFieldsActived", "pattern"}) {
            if (invalidFields.get(profile) != null) {
                result.addAll(invalidFields.get(profile));
            }
        }
        return result;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public GlobalHomeFolderConfiguration getGlobalHomeFolderConfiguration() {
        GlobalHomeFolderConfiguration conf =
            genericDAO.simpleSelect(GlobalHomeFolderConfiguration.class).unique();
        if (conf == null) {
            conf = new GlobalHomeFolderConfiguration();
            genericDAO.update(conf);
        }
        return conf;
    }

    @Override
    public Boolean homeFolderIndependentCreationEnabled() {
        return getGlobalHomeFolderConfiguration().getIndependentCreation();
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    public void enableHomeFolderIndependentCreation() {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();
        conf.setIndependentCreation(true);
        genericDAO.update(conf);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    public void disableHomeFolderIndependentCreation() {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();
        conf.setIndependentCreation(false);
        genericDAO.update(conf);
    }

    @Override
    public Adult activateAccount(String login, String code) {
        Adult adult = adultDAO.findByLogin(login);
        if (isCodeVerificationValid(adult, code) && adult.getState() == UserState.PENDING) {
            adult.setState(UserState.NEW);
            adult.setValidationCode("");
            adultDAO.update(adult);
            return adult;
        }
        return null;
    }

    @Override
    public boolean checkResetPasswordLink(String login, String key) {
        Adult adult = adultDAO.findByLogin(login);
        return isCodeVerificationValid(adult, key) && adult.getValidationCodeExpiration().after(new Date());
    }

    @Override
    public boolean hasValidEmail(Adult adult) {
        return adult.getEmail() != null && !adult.getEmail().equals(SecurityContext.getCurrentConfigurationBean().getDefaultEmail());
    }

    @Override
    public void prepareResetPassword(Adult adult) {
        adult.assignRandomValidationCode();
        adult.setValidationCodeExpiration(DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, 3));
        genericDAO.update(adult);
    }

    @Override
    public boolean isChildInformationSheetFilled(Child child) {
        boolean retour = false;
        try {
            boolean informationSheetRequired = SecurityContext.getCurrentConfigurationBean().isInformationSheetRequired();
            if (!informationSheetRequired) {
                retour = true;
            }
            else {
                if (child != null & child.getChildInformationSheet() != null) {
                    if (child.getChildInformationSheet().getValidationDate() != null && 
                        this.validate(child.getChildInformationSheet(), informationSheetRequired).isEmpty()) {
                    // La date de validation de la fiche de renseignement est renseignée
                    // et
                    // tous les champs de la fiche de renseignement sont bien remplis
                        retour = true;
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return retour;
    }

    private boolean isCodeVerificationValid(Adult adult, String code) {
        return adult != null && code != null && code.equals(adult.getValidationCode());
    }

    public void setAdultDAO(IAdultDAO adultDAO) {
        this.adultDAO = adultDAO;
    }

    @Override
    public Boolean blockDuplicateCreationEnabled() {
        return getGlobalHomeFolderConfiguration().getBlockDuplicateCreation();
    }

    @Override
    public void enableBlockDuplicateCreation() {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();
        conf.setBlockDuplicateCreation(true);
        genericDAO.update(conf);
    }

    @Override
    public void disableBlockDuplicateCreation() {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();
        conf.setBlockDuplicateCreation(false);
        genericDAO.update(conf);
    }

    public GlobalUserConfiguration getGlobalUserConfiguration() {
        return adultDAO.getGlobalUserConfiguration();
    }

    @Override
    public void addLocalAuthority(String localAuthorityName) {
        if ( getGlobalUserConfiguration() == null) {
            genericDAO.saveOrUpdate(new GlobalUserConfiguration());
            HibernateUtil.getSession().flush();
        }
    }

    @Override
    public void removeLocalAuthority(String localAuthorityName) {
        // nothing to do
    }
}
