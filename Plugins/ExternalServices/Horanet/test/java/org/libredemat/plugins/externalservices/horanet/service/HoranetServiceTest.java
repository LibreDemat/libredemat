package org.libredemat.plugins.externalservices.horanet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItemDetail;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalInvoiceItemDetail;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.request.LocalReferentialData;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.school.PerischoolActivityRegistrationRequest;
import org.libredemat.business.request.school.SchoolCanteenRegistrationRequest;
import org.libredemat.business.request.school.SchoolRegistrationRequest;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.MeansOfContact;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.business.users.SectionType;
import org.libredemat.business.users.UserState;
import org.libredemat.exception.CvqException;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.request.RequestTestCase;

import static org.junit.Assert.*;


/**
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public class HoranetServiceTest extends RequestTestCase {

	private IExternalProviderService externalProviderService;
	
	private void setServices() throws CvqException{
	    externalProviderService = getApplicationBean("horanetExternalService");
    }

//	public void testHelloWorld() throws CvqException {
//
//		setServices();
//		
//		try {
//			String helloWorldResult = externalProviderService.helloWorld();
//			Assert.assertEquals(helloWorldResult, "Hello World");
//		} catch (CvqException e) {
//			e.printStackTrace();
//			fail("exception while sending hello world");
//		}
//	}
	
	public void testEcitizenFlow() throws Exception {
		
		setServices();
		
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        requestWorkflowService.updateRequestState(request.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(request.getId(), RequestState.VALIDATED, null);

        continueWithNewTransaction();
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);
        HomeFolder homeFolder = userSearchService.getHomeFolderById(fake.id);
        homeFolder.setFamilyQuotient("354,44");
        userWorkflowService.modify(homeFolder);
        
        // create and validate a school registration request to be able to issue
        // a school canteen registration request
        /////////////////////////////////////////////////////////////////////////
        
        SchoolRegistrationRequest srrRequest = new SchoolRegistrationRequest();
        srrRequest.setSection(SectionType.FIRST_SECTION);
        srrRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(true));
        srrRequest.setSchool(schoolService.getAll().get(0));
        srrRequest.setUrgencyPhone("0102030405");
        srrRequest.setCurrentSection(SectionType.FIRST_SECTION);
        srrRequest.setCurrentSchoolAddress("CurrentSchoolAddress");
        srrRequest.setCurrentSchoolName("CurrentSchoolName");
        srrRequest.setSubjectId(fake.childId);
        srrRequest.setSubjectLastName(userSearchService.getById(fake.childId).getLastName());
        MeansOfContact meansOfContact = meansOfContactService.getMeansOfContactByType(MeansOfContactEnum.MAIL);
        srrRequest.setMeansOfContact(meansOfContact);
        requestWorkflowService.create(srrRequest, null);
     
		continueWithNewTransaction();
		SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
		SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        requestWorkflowService.updateRequestState(srrRequest.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(srrRequest.getId(), RequestState.VALIDATED, null);

        // create and validate a school canteen registration request and check 
        // that data is correctly sent
        /////////////////////////////////////////////////////////////////////////

		continueWithNewTransaction();
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        SchoolCanteenRegistrationRequest scrrRequest = new SchoolCanteenRegistrationRequest();
        scrrRequest.setSection(SectionType.FIRST_SECTION);
        scrrRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(true));
        scrrRequest.setSchool(schoolService.getAll().get(0));
        scrrRequest.setUrgencyPhone("0102030405");
        scrrRequest.setDoctorPhone("0102030405");
        scrrRequest.setDoctorName("DoctorName");
        scrrRequest.setHospitalizationPermission(Boolean.valueOf(true));
        scrrRequest.setFoodAllergy(Boolean.valueOf(true));
        scrrRequest.setSubjectId(fake.childId);
        scrrRequest.setSubjectLastName(userSearchService.getById(fake.childId).getLastName());
        scrrRequest.setMeansOfContact(meansOfContact);
        LocalReferentialData foodDietLrd = new LocalReferentialData();
        foodDietLrd.setName("NoPork");
        List<LocalReferentialData> foodDiets = new ArrayList<LocalReferentialData>();
        foodDiets.add(foodDietLrd);
        scrrRequest.setFoodDiet(foodDiets);
        requestWorkflowService.create(scrrRequest, null);
        
		continueWithNewTransaction();
		SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
		SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        requestWorkflowService.updateRequestState(scrrRequest.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(scrrRequest.getId(), RequestState.VALIDATED, null);

        // create and validate a perischool activity registration request and check 
        // that data is correctly sent
        ///////////////////////////////////////////////////////////////////////////

		continueWithNewTransaction();
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        PerischoolActivityRegistrationRequest parrRequest = new PerischoolActivityRegistrationRequest();
        parrRequest.setClassTripPermission(Boolean.valueOf(true));
        parrRequest.setChildPhotoExploitationPermission(Boolean.valueOf(true));
        parrRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(true));
        parrRequest.setUrgencyPhone("0102030405");
        parrRequest.setHospitalizationPermission(Boolean.valueOf(true));
        parrRequest.setSubjectId(fake.childId);
        parrRequest.setSubjectLastName(userSearchService.getById(fake.childId).getLastName());
        parrRequest.setMeansOfContact(meansOfContact);
        LocalReferentialData activityLrd = new LocalReferentialData();
        activityLrd.setName("EveningNursery");
        List<LocalReferentialData> activities = new ArrayList<LocalReferentialData>();
        activities.add(activityLrd);
        parrRequest.setPerischoolActivity(activities);
        requestWorkflowService.create(parrRequest, null);

		continueWithNewTransaction();
		SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
		SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);

        requestWorkflowService.updateRequestState(parrRequest.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(parrRequest.getId(), RequestState.VALIDATED, null);

		continueWithNewTransaction();
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

        // get account details, perform a payment on a deposit account and on a 
        // ticketing contract then load details of those two accounts
        ///////////////////////////////////////////////////////////////////////////

        ExternalDepositAccountItem edai = null;
        ExternalTicketingContractItem etci = null;
        
        // Step 1 : load accounts and choose one deposit and one ticketing
        Map<String, List<ExternalAccountItem>> externalAccounts = 
        	externalProviderService.getAccountsByHomeFolder(homeFolder.getId(), null, null);
        for (String accountType : externalAccounts.keySet()) {
        	logger.debug("inspecting account type " + accountType);
        	List<ExternalAccountItem> accountsByType = externalAccounts.get(accountType);
        	for (ExternalAccountItem eai : accountsByType) {
        		logger.debug("got account " + eai.toString());
        		if (accountType.equals(IPaymentService.EXTERNAL_DEPOSIT_ACCOUNTS)) {
        			logger.debug("found a test deposit account");
        			edai = (ExternalDepositAccountItem) eai;

                    // load account details
                    externalProviderService.loadDepositAccountDetails(edai);
                    Set<ExternalDepositAccountItemDetail> accountDetails = edai.getAccountDetails();
                    if (accountDetails != null) {
                        for (ExternalDepositAccountItemDetail edaiDetail : accountDetails) {
                            logger.debug("detail - payment id : " + edaiDetail.getPaymentId());
                            logger.debug("detail - payment type : " + edaiDetail.getPaymentType());
                            logger.debug("detail - payment date : " + edaiDetail.getDate());
                            logger.debug("detail - payment value : " + edaiDetail.getValue());
                        }
                    }
        		} else if (accountType.equals(IPaymentService.EXTERNAL_TICKETING_ACCOUNTS)
        				&& etci == null) {
        			logger.debug("found a test ticketing contract account");
        			etci = (ExternalTicketingContractItem) eai;
        		} else if (accountType.equals(IPaymentService.EXTERNAL_INVOICES)) {
        		    logger.debug("found an invoice");
                    ExternalInvoiceItem eii = (ExternalInvoiceItem) eai;
                    
                    // load invoice details
                    externalProviderService.loadInvoiceDetails(eii);
                    Set<ExternalInvoiceItemDetail> eiiDetails = eii.getInvoiceDetails();
                    if (eiiDetails != null) {
                        for (ExternalInvoiceItemDetail eiiDetail : eiiDetails) {
                            logger.debug("detail - label : " + eiiDetail.getLabel());
                            logger.debug("detail - subject name : " + eiiDetail.getSubjectName());
                            logger.debug("detail - subject surname : " + eiiDetail.getSubjectSurname());
                            logger.debug("detail - quantity : " + eiiDetail.getQuantity());
                            logger.debug("detail - unit price : " + eiiDetail.getUnitPrice());
                            logger.debug("detail - value : " + eiiDetail.getValue());
                        }
                    }
                }
        	}
        }

        if (etci == null && edai == null)
        	fail("nothing to pay on");
        
        // Step 2 : perform a payment
        List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
//        if (etci != null) {
//        	etci.setQuantity(Integer.valueOf("13"));
//            etci.setAmount(etci.getQuantity() * etci.getUnitPrice());
//        	purchaseItems.add(etci);
//        }
        if (edai != null) {
        	edai.setAmount(Double.valueOf("2000"));
        	purchaseItems.add(edai);
        }
        externalProviderService.creditHomeFolderAccounts(purchaseItems, "CVQ_REF_1234", "BANK_REF_1234", 
        		homeFolder.getId(), null, null, new Date());
        
        // TODO : check account credit ... when horanet finished integration !

        // load account details
        externalProviderService.loadDepositAccountDetails(edai);
        Set<ExternalDepositAccountItemDetail> accountDetails = edai.getAccountDetails();
        if (accountDetails != null) {
            for (ExternalDepositAccountItemDetail edaiDetail : accountDetails) {
                logger.debug("detail - payment id : " + edaiDetail.getPaymentId());
                logger.debug("detail - payment type : " + edaiDetail.getPaymentType());
                logger.debug("detail - payment date : " + edaiDetail.getDate());
                logger.debug("detail - payment value : " + edaiDetail.getValue());
            }
        }
        
        // get consumptions for our two registrations
        /////////////////////////////////////////////
        
        Calendar calendar = new GregorianCalendar();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.MONTH, -3);
        Date dateFrom = calendar.getTime();
        Map<Date, String> consumptions = 
        	externalProviderService.getConsumptions(scrrRequest.getId(), dateFrom, dateTo);
        for (Date date : consumptions.keySet()) {
        	logger.debug("on date " + date + ", got consumption : " + consumptions.get(date));
        }

        // send an home folder modification request
        /////////////////////////////////////////////
        homeFolder = userSearchService.getHomeFolderById(fake.id);
        homeFolder.getAddress().setStreetName("Ma nouvelle adresse");
        userWorkflowService.modify(homeFolder);
        continueWithNewTransaction();
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);
        Individual homeFolderResponsible = userSearchService.getById(fake.responsibleId);
        userWorkflowService.changeState(homeFolderResponsible, UserState.INVALID);
        userWorkflowService.changeState(homeFolderResponsible, UserState.VALID);
	}
}
