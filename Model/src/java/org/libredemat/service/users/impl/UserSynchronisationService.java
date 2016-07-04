package org.libredemat.service.users.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.users.IUserDeduplicationService;
import org.libredemat.service.users.IUserNotificationService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.IUserSynchronisationService;
import org.libredemat.service.users.IUserWorkflowService;
import org.libredemat.util.translation.ITranslationService;

public class UserSynchronisationService implements IUserSynchronisationService{

    private static Logger logger = Logger.getLogger(UserSynchronisationService.class);

    private IUserSearchService userSearchService;
    private IUserDeduplicationService userDeduplicationService;
    private IUserWorkflowService userWorkflowService;
    private ITranslationService translationService;
    private IUserNotificationService userNotificationService;

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void synchroniseAll(List<String> servicesLabel, String email) throws CvqException, IOException {
        List<HomeFolder> homeFolders = userSearchService.getAll(true, true);
        StringBuilder fileString = new StringBuilder();
        for(String serviceLabel : servicesLabel) {
            List<List<String>> informations = new ArrayList<List<String>>();
            fileString.append(serviceLabel+ " :\n");
            fileString.append("--------------------------------\n\n");

            for(HomeFolder homeFolder : homeFolders) {
                List<String> message = new ArrayList<String>();
                message.add(homeFolder.getId().toString());
                try {
                    userDeduplicationService.createCirilMapping(homeFolder);
                    userWorkflowService.synchronise(homeFolder, serviceLabel);
                    message.add(translationService.translate("homeFolder.synchronisation.notification.success"));
                } catch (Exception ex) {
                    if (ex.getMessage() == null || ex.getMessage().isEmpty()) {
                        message.add(translationService.translate("homeFolder.synchronisation.notification.fail.nomessage"));
                    }
                    else {
                        message.add(ex.getMessage());
                    }
                }
                informations.add(message);
            }
            for(List<String> info : informations) {
                fileString.append(translationService.translate("homeFolder.synchronisation.notification.info", new Object[] {info.get(0), info.get(1)})+"\n");
            }
            fileString.append("\n\n\n");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(fileString.toString().getBytes("UTF-8"));

        String subject = translationService.translate("homeFolder.synchronisation.notification.subject");
        String body = translationService.translate("homeFolder.synchronisation.notification.body");;
        userNotificationService.notifyByEmail(SecurityContext.getCurrentSite().getAdminEmail() , email, subject, body, bos.toByteArray(), "rapport synchronisation.txt");
    }

    @Override
    public void synchronise(List<String> servicesLabel, Individual individual) throws CvqException{
        for(String serviceLabel : servicesLabel) {
            userDeduplicationService.createCirilMapping(individual.getHomeFolder());
            userWorkflowService.synchronise(individual, serviceLabel);
        }
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public void setUserDeduplicationService(IUserDeduplicationService userDeduplicationService) {
        this.userDeduplicationService = userDeduplicationService;
    }

    public void setUserWorkflowService(IUserWorkflowService userWorkflowService) {
        this.userWorkflowService = userWorkflowService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setUserNotificationService(IUserNotificationService userNotificationService) {
        this.userNotificationService = userNotificationService;
    }
}
