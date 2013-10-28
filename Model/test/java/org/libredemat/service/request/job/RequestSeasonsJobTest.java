package org.libredemat.service.request.job;

import static org.junit.Assert.*;

import org.junit.Test;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestSeason;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.RequestType;
import org.libredemat.business.request.school.SchoolRegistrationRequest;
import org.libredemat.business.users.MeansOfContact;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.business.users.SectionType;
import org.libredemat.dao.hibernate.GenericDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.RequestTestCase;
import org.libredemat.service.request.job.RequestSeasonsJob;
import org.libredemat.util.development.BusinessObjectsFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class RequestSeasonsJobTest extends RequestTestCase {
 
    @Autowired
    protected RequestSeasonsJob requestSeasonsJob;
    
    /**
     * Bypass service business rules
     * (like "request.season.registration_started")
     * Add month's offset to registration and effect dates
     */
    private void daoUpdateSeason(RequestSeason requestSeason,
        int registrationStartOffset, int registrationEndOffset,
        int effectStartOffset, int effectEndOffset) {
        requestSeason.setRegistrationStart(
            requestSeason.getRegistrationStart()
                .plusMonths(registrationStartOffset));
        requestSeason.setRegistrationEnd(
            requestSeason.getRegistrationEnd()
                .plusMonths(registrationEndOffset));
        requestSeason.setEffectStart(
            requestSeason.getEffectStart()
                .plusMonths(effectStartOffset));
        requestSeason.setEffectEnd(
            requestSeason.getEffectEnd()
                .plusMonths(effectEndOffset));
        GenericDAO genericDAO = super.getApplicationBean("genericDAO");
        genericDAO.update(requestSeason);
        continueWithNewTransaction();
    }

    @Test
    public void testJob() throws CvqException {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithManageRoles);

        RequestType requestType = 
            requestTypeService.getRequestTypeByLabel("School Registration");
        assertNotNull(requestType);
        
        /* Create a season */
        RequestSeason season = BusinessObjectsFactory.gimmeRequestSeason("Saison 1235", 1, 2, 3, 5);
        requestTypeService.addRequestSeason(requestType.getId(), season);
        continueWithNewTransaction();
        season = requestTypeService.getRequestSeasons(requestType.getId()).iterator().next();
        /* Make season registration start */
        daoUpdateSeason(season, -2, 0, 0, 0); // season =[-1, 2, 3, 5];

        /* Request for a school registration (in FrontOffice) */
        SecurityContext.setCurrentSite(localAuthorityName,
            SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        SchoolRegistrationRequest request = new SchoolRegistrationRequest();
        request.setRequestSeason(season);
        request.setSection(SectionType.BEFORE_FIRST_SECTION);
        request.setRulesAndRegulationsAcceptance(Boolean.valueOf(true));
        request.setSchool(schoolService.getAll().iterator().next());
        request.setUrgencyPhone("0101010101");
        request.setCurrentSection(SectionType.BEFORE_FIRST_SECTION);
        request.setCurrentSchoolAddress("CurrentSchoolAddress");
        request.setCurrentSchoolName("CurrentSchoolName");
        request.setRequesterId(fake.responsibleId);
        request.setSubjectId(fake.childId);
        MeansOfContact meansOfContact =
            meansOfContactService.getMeansOfContactByType(MeansOfContactEnum.EMAIL);
        request.setMeansOfContact(meansOfContact);
        Long requestId = requestWorkflowService.create(request, null);

        continueWithNewTransaction();
        
        /* Treat the school registration request (in BackOffice) */
        SecurityContext.setCurrentSite(localAuthorityName,
            SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);
        
        requestWorkflowService.updateRequestState(requestId, RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(requestId, RequestState.VALIDATED, null);
        requestWorkflowService.updateRequestState(requestId, RequestState.NOTIFIED, "Bon pour inscription");

        continueWithNewTransaction();
        
        /* Must not change requestState (season's effect isn't started) */
        requestSeasonsJob.launchJob();
        
        startTransaction();
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        Request requestFromDb = requestSearchService.getById(requestId, false);
        assertEquals(RequestState.NOTIFIED, requestFromDb.getState());

        continueWithNewTransaction();

        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithSiteRoles);

        /* Make season effect end */
        daoUpdateSeason(season, -5, -6, -6, -6); // season =[-6, -4, -3, -1];
        
        continueWithNewTransaction();
        
        /* Must archive all the requests */
        requestSeasonsJob.launchJob();
        
        // the job clauses the current transaction, so re-open a new one
        startTransaction();
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        requestFromDb = requestSearchService.getById(requestId, false);
        assertEquals(RequestState.CLOSED, requestFromDb.getState());
        requestWorkflowService.delete(requestId);

        continueWithNewTransaction();
        
        SecurityContext.setCurrentAgent(agentNameWithManageRoles);
        requestTypeService.removeRequestSeason(requestType.getId(), season.getId());
    }
}
