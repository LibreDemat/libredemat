package org.libredemat.service.users.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.libredemat.business.QoS;
import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.business.users.GlobalUserConfiguration;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.UserAction;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.users.IUserService;
import org.libredemat.util.DateUtils;
import org.libredemat.util.mail.IMailService;

import com.google.gson.JsonObject;


public class UserInstructionDurationCheckerJob {

    private static Logger logger = Logger.getLogger(UserInstructionDurationCheckerJob.class);

    private ILocalAuthorityRegistry localAuthorityRegistry;

    private IMailService mailService;

    private IIndividualDAO individualDAO;

    private IHomeFolderDAO homeFolderDAO;

    private IUserService userService;

    @Context(types = {ContextType.SUPER_ADMIN})
    public void launchJob() {
        localAuthorityRegistry.browseAndCallback(this, "check", null);
    }

    @Context(types = {ContextType.SUPER_ADMIN})
    public void check() {
        LocalAuthority la = SecurityContext.getCurrentSite();
        // FIXME : we're still relying on request configuration
        GlobalUserConfiguration config = userService.getGlobalUserConfiguration();
        logger.info("UserInstructionDurationCheckerJob : dealing with " + la.getName());

        Date now = new Date();
        List<Individual> individuals = individualDAO.searchTasks(now);
        List<Individual> urgent = new ArrayList<Individual>();
        List<Individual> late = new ArrayList<Individual>();
        List<Individual> tasks = new ArrayList<Individual>();
        for (Individual individual : individuals) {
            int delay = DateUtils.getWorkDaysBetweenDates(individual.getLastModificationDate(), now);
            if (config.getInstructionMaxDelay() > 0 && delay >= config.getInstructionMaxDelay()
                && !QoS.LATE.equals(individual.getQoS())) {
                individual.setQoS(QoS.LATE);
                late.add(individual);
                tasks.add(individual);
            } else if (config.getInstructionAlertDelay() > 0
                && delay >= (config.getInstructionMaxDelay() - config.getInstructionAlertDelay())
                && QoS.GOOD.equals(individual.getQoS())) {
                individual.setQoS(QoS.URGENT);
                urgent.add(individual);
                tasks.add(individual);
            }
        }
        boolean notified = false;
        if (config.isInstructionAlertsEnabled()) {
            if (!StringUtils.isBlank(la.getAdminEmail()) && !tasks.isEmpty()) {
                StringBuffer body = new StringBuffer();
                body.append("Bonjour,\n\n")
                    .append("Voici le récapitulatif des alertes sur les usagers :\n")
                    .append("\tAlertes oranges : ").append(urgent.size()).append("\n")
                    .append("\tAlertes rouges : ").append(late.size()).append("\n");
                if (config.isInstructionAlertsDetailed()) {
                    if (!urgent.isEmpty()) {
                        body.append("\n").append("Détail des alertes oranges :\n");
                        for (Individual individual : urgent) {
                            body.append("\t").append(individual.getFullName())
                                .append(" (").append(individual.getId()).append(")\n");
                        }
                    }
                    if (!late.isEmpty()) {
                        body.append("\n\n").append("Détail des alertes rouges :\n");
                        for (Individual individual : late) {
                            body.append("\t").append(individual.getFullName())
                                .append(" (").append(individual.getId()).append(")\n");
                        }
                    }
                }
                try {
                    mailService.send(null, la.getAdminEmail(), null,
                        "[LibreDémat] Alerte traitement comptes", body.toString());
                    notified = true;
                } catch (CvqException e) {
                    logger.error("checkLocalAuthRequestsInstructionDelay() got an error while "
                            + "sending email alert : " + e.getMessage());
                }
            }
        }
        for (Individual i : tasks) {
            JsonObject payload = new JsonObject();
            payload.addProperty("quality", i.getQoS().toString());
            payload.addProperty("notified", notified);
            i.getHomeFolder().getActions().add(new UserAction(UserAction.Type.QoS, i.getId(), payload));
            homeFolderDAO.update(i.getHomeFolder());
        }
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }

    public void setIndividualDAO(IIndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
