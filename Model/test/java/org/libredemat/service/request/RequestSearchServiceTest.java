package org.libredemat.service.request;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.util.Critere;

import static org.junit.Assert.*;


public class RequestSearchServiceTest extends RequestTestCase {

    @Test
    public void testSearch() throws CvqException {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        Set<Critere> criteriaSet = new HashSet<Critere>();
        Critere crit = new Critere();
        crit.setAttribut(Request.SEARCH_BY_REQUESTER_LASTNAME);
        crit.setComparatif(Critere.EQUALS);
        crit.setValue(userSearchService.getById(fake.responsibleId).getLastName());
        criteriaSet.add(crit);
        Critere crit2 = new Critere();
        crit2.setAttribut(Request.SEARCH_BY_CREATION_DATE);
        crit2.setComparatif(Critere.LT);
        crit2.setValue(new Date());
        criteriaSet.add(crit2);
        Critere crit3 = new Critere();
        crit3.setAttribut(Request.SEARCH_BY_HOME_FOLDER_ID);
        crit3.setComparatif(Critere.EQUALS);
        crit3.setValue(fake.id);
        criteriaSet.add(crit3);
        List<Request> carteVoList = requestSearchService.get(criteriaSet, null, null, -1, 0, false);
        assertEquals(carteVoList.size(),1);

        crit = new Critere();
        crit.setAttribut(Request.SEARCH_BY_REQUESTER_LASTNAME);
        crit.setComparatif(Critere.EQUALS);
        crit.setValue("connaispascegarsla!");
        criteriaSet = new HashSet<Critere>();
        criteriaSet.add(crit);
        carteVoList = requestSearchService.get(criteriaSet, null, null, -1, 0, false);
        assertEquals(carteVoList.size(),0);

        crit = new Critere();
        crit.setAttribut(Request.SEARCH_BY_REQUEST_ID);
        crit.setComparatif(Critere.EQUALS);
        crit.setValue(request.getId());
        criteriaSet = new HashSet<Critere>();
        criteriaSet.add(crit);
        carteVoList = requestSearchService.get(criteriaSet, null, null, -1, 0, false);
        assertEquals(carteVoList.size(), 1);

        crit = new Critere();
        crit.setAttribut(Request.SEARCH_BY_HOME_FOLDER_ID);
        crit.setComparatif(Critere.NEQUALS);
        crit.setValue(String.valueOf(fake.id));
        criteriaSet = new HashSet<Critere>();
        criteriaSet.add(crit);
        carteVoList = requestSearchService.get(criteriaSet, null, null, -1, 0, false);
        for (Request req : carteVoList) {
            assertFalse(req.getId() == fake.id);
        }
    }
}
