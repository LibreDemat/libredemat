package org.libredemat.service.request;


import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.school.SchoolRegistrationRequest;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.util.Critere;
import org.libredemat.util.DateUtils;

import static org.junit.Assert.*;

/**
 * Request service draft test case
 *
 * @author Victor Bartel (vba@zenexity.fr)
 */
public class RequestDraftTest extends RequestTestCase {

    @Test
    public void testDrafts() throws CvqException {

        this.createDrafts(2);
        
        List<Request> drafts = this.getDrafts();
        assertEquals(2,drafts.size());
        
        requestWorkflowService.delete(drafts.get(0).getId());
        continueWithNewTransaction();
        
        drafts = getDrafts();
        assertEquals(1, drafts.size());
        
        Request draft2 = drafts.get(0);
        draft2.setState(RequestState.PENDING);
        requestWorkflowService.modify(draft2);

        continueWithNewTransaction();
        try {
            this.getDraftById(draft2.getId());
            fail();
        } catch (IndexOutOfBoundsException e) {}
        requestWorkflowService.delete(draft2.getId());

        continueWithNewTransaction();

        assertEquals(0, getDrafts().size());
    }
    
    List<Request> getDrafts() throws CvqException {
        Set<Critere> criterias = new HashSet<Critere>();
        
        Critere criteria = new Critere();
        criteria.setAttribut(Request.SEARCH_BY_STATE);
        criteria.setComparatif(Critere.EQUALS);
        criteria.setValue(RequestState.DRAFT);
        criterias.add(criteria);

        return requestSearchService.get(criterias, null, null, 0, 0, true);
    }
    
    Request getDraftById(Long id) throws CvqException {
        Set<Critere> criterias = new HashSet<Critere>();
        
        Critere criteria = new Critere();
        criteria.setAttribut(Request.SEARCH_BY_STATE);
        criteria.setComparatif(Critere.EQUALS);
        criteria.setValue(RequestState.DRAFT);
        criterias.add(criteria);
        
        criteria = new Critere();
        criteria.setAttribut(Request.SEARCH_BY_REQUEST_ID);
        criteria.setComparatif(Critere.EQUALS);
        criteria.setValue(id);
        criterias.add(criteria);
        
        List<Request> reqs = requestSearchService.get(criterias, null, null, 0, 0, false);
        
        return reqs.get(0);
    }
    
    void createDrafts(int step) throws CvqException {
        continueWithNewTransaction();
        
        for(int i = 1;i<=step;i++) {
            SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT);
            SecurityContext.setCurrentEcitizen(fake.responsibleId);
            
            Request request = new SchoolRegistrationRequest();
            request.setRequesterId(SecurityContext.getCurrentEcitizen().getId());
            request.setSubjectId(fake.childId);
            request.setState(RequestState.DRAFT);
            Long id = requestWorkflowService.create(request, null);
            continueWithNewTransaction();
            
            request = requestSearchService.getById(id, true);
            request.setCreationDate(DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR,i*(-1)));
            
            SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            requestWorkflowService.modify(request);
            continueWithNewTransaction();
        }
    }
}
