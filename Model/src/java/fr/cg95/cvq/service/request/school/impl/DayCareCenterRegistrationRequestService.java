package fr.cg95.cvq.service.request.school.impl;

import java.util.Arrays;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;

import fr.cg95.cvq.business.request.Request;
import fr.cg95.cvq.business.request.school.DayCareCenterRegistrationRequest;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.request.condition.EqualityChecker;
import fr.cg95.cvq.service.request.condition.EqualityListChecker;
import fr.cg95.cvq.service.request.impl.RequestService;
import fr.cg95.cvq.service.users.IIndividualService;

/**
 *
 * @author vsi@zenexity.fr
 */
public final class DayCareCenterRegistrationRequestService extends RequestService {

    @Autowired
    IIndividualService individualService;
    
    @Override
    public void init() {
        DayCareCenterRegistrationRequest.conditions.put("situationActuelleMere", new EqualityChecker("Autre"));
        DayCareCenterRegistrationRequest.conditions.put("situationActuellePere", new EqualityChecker("Autre"));
        DayCareCenterRegistrationRequest.conditions.put("estHorairesReguliersMere", new EqualityChecker("true"));
        DayCareCenterRegistrationRequest.conditions.put("estHorairesReguliersPere", new EqualityChecker("true"));
        DayCareCenterRegistrationRequest.conditions.put("choixTypeDatePlacementAccueilRegulier", new EqualityChecker("Connue"));
        DayCareCenterRegistrationRequest.conditions.put("modeAccueil", new EqualityChecker("false"));
        DayCareCenterRegistrationRequest.conditions.put("choixHorairesAccueil",
                new EqualityListChecker(Arrays.asList("estHorairesAccueilRegulier=Regulier","estHorairesAccueilIrregulier=Irregulier")));
    }
    
    @Override
    public boolean accept(final Request request) {
        return request instanceof DayCareCenterRegistrationRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        DayCareCenterRegistrationRequest dccrr = new DayCareCenterRegistrationRequest();
        if (SecurityContext.getCurrentEcitizen() != null) {
            if (SecurityContext.getCurrentEcitizen().getMobilePhone() != null)
                dccrr.setTelephoneContact(SecurityContext.getCurrentEcitizen().getMobilePhone());
            else if (SecurityContext.getCurrentEcitizen().getHomePhone() != null)
                dccrr.setTelephoneContact(SecurityContext.getCurrentEcitizen().getHomePhone());
            else
                dccrr.setTelephoneContact(SecurityContext.getCurrentEcitizen().getOfficePhone());
        }
        return dccrr;
    }
    
    @Override
    public void onRequestCreated(final Request request)
        throws CvqException {
        
        DayCareCenterRegistrationRequest dccrr = (DayCareCenterRegistrationRequest) request;
        // get the 18 months date of the subject
        Individual subject = individualService.getById(dccrr.getSubjectId());
        Calendar cal = Calendar.getInstance();
        cal.setTime(subject.getBirthDate());
        cal.add(Calendar.MONTH, 18);
        dccrr.setDixHuitMoisEnfant(cal.getTime());
    }

    public void setIndividualService(IIndividualService individualService) {
        this.individualService = individualService;
    }
}
