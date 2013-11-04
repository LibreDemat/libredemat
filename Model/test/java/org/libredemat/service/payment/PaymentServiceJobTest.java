package org.libredemat.service.payment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.business.payment.PaymentState;
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.job.PaymentInitializationDateCheckerJob;
import org.libredemat.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class PaymentServiceJobTest extends PaymentTestCase {

    @Autowired
	private IPaymentDAO paymentDAO;
    @Autowired
	private PaymentInitializationDateCheckerJob paymentInitilizationDateCheckerJob;
	
	private Payment createPayment(int timeShifting, PaymentState paymentState, boolean commitAlert, 
			String broker, String cvqReference , PaymentMode paymentMode) throws CvqException {
       
		SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);
		
		Payment payment = new Payment();
		
		Date dateTest = DateUtils.getShiftedDate(Calendar.HOUR, timeShifting);
		
		payment.setInitializationDate(dateTest);
		payment.setState(paymentState);
		payment.setCommitAlert(commitAlert);
		payment.setBroker(broker);
		payment.setCvqReference(cvqReference);
		payment.setPaymentMode(paymentMode);
		
		payment.setHomeFolderId(SecurityContext.getCurrentEcitizen().getHomeFolder().getId());
		payment.setRequesterId(SecurityContext.getCurrentUserId());
		payment.setRequesterLastName(SecurityContext.getCurrentEcitizen().getLastName());
		payment.setRequesterFirstName(SecurityContext.getCurrentEcitizen().getFirstName());
		
		paymentDAO.create(payment);
		
		return payment;
    }
	
	@Test
	public void testPaymentDAOSearchNotCommited() throws CvqException {
    	
    	Payment payment = 
    	    createPayment(-4, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment2 = 
    	    createPayment(-5, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment3 = 
    	    createPayment(-6, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	
    	continueWithNewTransaction();
    	
    	List<Payment> listPayment = paymentDAO.searchNotCommited();
    	assertEquals(3, listPayment.size());
    	
    	// Drop testing datas
        paymentDAO.delete(paymentDAO.findById(payment.getId()));
        paymentDAO.delete(paymentDAO.findById(payment2.getId()));
        paymentDAO.delete(paymentDAO.findById(payment3.getId()));
    	
        continueWithNewTransaction();

        listPayment = paymentDAO.searchNotCommited();
        assertEquals(0, listPayment.size());
    }

	@Test
    public void testPaymentInitialization() throws CvqException {
    	
    	Payment payment = 
    	    createPayment(0, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment2 = 
    	    createPayment(-4, PaymentState.CANCELLED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment3 = 
    	    createPayment(-4, PaymentState.CANCELLED, true, "BOKER", "CVQR", PaymentMode.CARD);
    	
    	Payment payment4 = 
    	    createPayment(-4, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment5 = 
    	    createPayment(-5, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	Payment payment6 = 
    	    createPayment(-6, PaymentState.INITIALIZED, false, "BOKER", "CVQR", PaymentMode.CARD);
    	
    	continueWithNewTransaction();
    	
    	// Do not forget that the job comits the transaction and closed the session
    	paymentInitilizationDateCheckerJob.launchJob();
    	
    	startTransaction();
        
        // set the current site and citizen after launching the job since it resets them after running
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        SecurityContext.setCurrentEcitizen(fake.responsibleId);

    	List<Payment> listPayment = paymentDAO.searchNotCommited();
    	assertTrue(listPayment.isEmpty());
    	
    	// Drop testing datas
        paymentDAO.delete(paymentDAO.findById(payment.getId()));
        paymentDAO.delete(paymentDAO.findById(payment2.getId()));
        paymentDAO.delete(paymentDAO.findById(payment3.getId()));
        paymentDAO.delete(paymentDAO.findById(payment4.getId()));
        paymentDAO.delete(paymentDAO.findById(payment5.getId()));
        paymentDAO.delete(paymentDAO.findById(payment6.getId()));
    	
        continueWithNewTransaction();

        listPayment = paymentDAO.searchNotCommited();
        assertTrue(listPayment.isEmpty());
    }
}
