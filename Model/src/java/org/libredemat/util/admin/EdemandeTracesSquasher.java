package org.libredemat.util.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.exception.CvqException;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.service.authority.impl.LocalAuthorityRegistry;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.util.Critere;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EdemandeTracesSquasher {

    private IExternalProviderService edemandeService;

    private IGenericDAO genericDAO;

    private LocalAuthorityRegistry localAuthorityRegistry;

    private IRequestExternalActionService requestExternalActionService;

    private IRequestExternalService requestExternalService;

    public static void main(final String[] args) {
        ClassPathXmlApplicationContext context = SpringApplicationContextLoader.loadContext(null);
        EdemandeTracesSquasher ets = new EdemandeTracesSquasher();
        ets.edemandeService = context.getBean("edemandeExternalService", IExternalProviderService.class);
        ets.genericDAO = context.getBean("genericDAO", IGenericDAO.class);
        ets.localAuthorityRegistry = context.getBean("localAuthorityRegistry", LocalAuthorityRegistry.class);
        ets.requestExternalActionService = context.getBean("requestExternalActionService", IRequestExternalActionService.class);
        ets.requestExternalService = context.getBean("requestExternalService", IRequestExternalService.class);
        ets.localAuthorityRegistry.browseAndCallback(ets, "migrate", new String[0]);
        System.exit(0);
    }

    public void migrate()
        throws CvqException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Set<Critere> criterias = new HashSet<Critere>();
        criterias.add(new Critere(RequestExternalAction.SEARCH_BY_NAME, edemandeService.getLabel(),
            Critere.EQUALS));
        for (String key : requestExternalService.getKeys(criterias)) {
            RequestExternalAction previous = null;
            Set<Critere> criterias2 = new HashSet<Critere>(criterias);
            criterias2.add(new Critere(RequestExternalAction.SEARCH_BY_KEY, key, Critere.EQUALS));
            for (RequestExternalAction action : requestExternalActionService.getTraces(criterias2,
                RequestExternalAction.SEARCH_BY_DATE, "asc", 0, 0)) {
                if (previous != null && previous.getStatus().equals(action.getStatus()) &&
                    StringUtils.equals((String)action.getComplementaryData().get("individual"),
                        (String)action.getComplementaryData().get("individual"))) {
                    Integer count = (Integer)previous.getComplementaryData().get("count");
                    if (count == null) count = 2; else count++;
                    action.getComplementaryData().put("count", count);
                    genericDAO.delete(previous);
                    genericDAO.update(action);
                }
                previous = action;
            }
            JpaUtil.closeAndReOpen(false);
        }
    }
}
