package org.libredemat.service.users.job;

import org.apache.log4j.Logger;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.util.mail.IMailService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImportedHomeFoldersNotificationJob {

    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IMailService mailService;

    private static Logger logger = Logger.getLogger(ImportedHomeFoldersNotificationJob.class);

    public void launch() {
        localAuthorityRegistry.browseAndCallback(this, "sendNotification", null);
    }

    public void sendNotification() {
        String importedHomeFolders = localAuthorityRegistry.getLocalAuthorityImportedHomeFoldersFile(1);
        File importedHomeFoldersFile = new File(importedHomeFolders);
        if (importedHomeFoldersFile.exists() && importedHomeFoldersFile.length() > 0) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(importedHomeFoldersFile);
            } catch (FileNotFoundException e) {
                // not possible, juste checked above
                return;
            }
            byte[] data = new byte[(int) importedHomeFoldersFile.length()];
            try {
                fis.read(data, 0, data.length);
            } catch (IOException e) {
                logger.error("Unable to load imported home folders data");
                return;
            }
            String importedHomeFoldersFilename = "Rapport d'import Ciril";
            String to = SecurityContext.getCurrentSite().getAdminEmail();
            try {
                mailService.send(null, to, new String[] { "capdemat-dev@zenexity.com", "support-capdemat@zenexity.com" },
                        "Rapport d'import de comptes Civil Net Enfance", "Ci-joint", data, importedHomeFoldersFilename);
            } catch (CvqException e) {
                logger.error("Unable to send email with imported home folders data");
            }
        }
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }
}
