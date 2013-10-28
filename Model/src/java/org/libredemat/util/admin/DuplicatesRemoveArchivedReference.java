package org.libredemat.util.admin;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.UserState;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.util.JSONUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;


public class DuplicatesRemoveArchivedReference {

    private static Logger logger = Logger.getLogger(DuplicatesRemoveArchivedReference.class);
    private ILocalAuthorityRegistry localAuthorityRegistry;
    private IIndividualDAO individualDAO;

    public static void main(final String[] args) {
        Logger.getRootLogger().setLevel(Level.OFF);
        logger.setLevel(Level.INFO);
        ClassPathXmlApplicationContext cpxa = SpringApplicationContextLoader.loadContext(args[0]);
        DuplicatesRemoveArchivedReference drar = new DuplicatesRemoveArchivedReference();
        drar.localAuthorityRegistry = cpxa.getBean("localAuthorityRegistry", ILocalAuthorityRegistry.class);
        drar.individualDAO = cpxa.getBean("individualDAO", IIndividualDAO.class);
        drar.localAuthorityRegistry.browseAndCallback(drar, "migrate", null);
        System.exit(0);
    }

    public void migrate() {
        List<Individual> individuals = JpaUtil.getEntityManager().createQuery(
                "from " + Individual.class.getSimpleName() + " individual " +
                "where individual.duplicateAlert is true", Individual.class)
                .getResultList();
        List<Long> archivedIndividualIds = JpaUtil.getEntityManager().createQuery(
                "select individual.id from " + Individual.class.getSimpleName() + " individual " +
                "where individual.state='" + UserState.ARCHIVED.name() + "'", Long.class)
                .getResultList();
        if (individuals != null && archivedIndividualIds != null && archivedIndividualIds.size() > 0) {
            for (Individual individual: individuals) {
                if (individual == null || individual.getDuplicateData() == null) {
                    continue;
                }
                Map<Long, Map<String, String>> similars =
                        JSONUtils.deserializeAsArray(individual.getDuplicateData());
                if (similars != null) {
                    Map<Long, Map<String, String>> duplicates =
                            JSONUtils.deserializeAsArray(individual.getDuplicateData());
                    for (Entry<Long, Map<String, String>> similar: similars.entrySet()) {
                        String individualId = similar.getValue().get("id");
                        if (individualId != null && !individualId.trim().isEmpty() &&
                                archivedIndividualIds.contains(Long.valueOf(individualId))) {
                            duplicates.remove(similar.getKey());
                        }
                    }
                    Gson gson = new Gson();
                    String jsonDuplicates = gson.toJson(duplicates);
                    if (!individual.getDuplicateData().equals(jsonDuplicates)) {
                        logger.info("Individual "+ individual.getId() + " : replace " +
                                individual.getDuplicateData() + " with " + jsonDuplicates);
                        if (duplicates.isEmpty()) {
                            individual.setDuplicateData(null);
                            individual.setDuplicateAlert(false);
                        } else {
                            individual.setDuplicateData(jsonDuplicates);
                        }
                        individualDAO.update(individual);
                        JpaUtil.closeAndReOpen(false);
                    }
                }
            }
        }
    }
}
