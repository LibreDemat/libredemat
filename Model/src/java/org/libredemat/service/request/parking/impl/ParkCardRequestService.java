package org.libredemat.service.request.parking.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.libredemat.business.payment.InternalInvoiceItem;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.payment.PaymentMode;
import org.libredemat.business.payment.PaymentState;
import org.libredemat.business.request.LocalReferentialEntryData;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.parking.ParkCardRequest;
import org.libredemat.business.request.parking.ParkImmatriculation;
import org.libredemat.business.request.parking.ParkResidentType;
import org.libredemat.business.request.parking.config.StreetBorderReferential;
import org.libredemat.business.users.Individual;
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.IXENoBrokerFindException;
import org.libredemat.exception.IXEPaymentAllReadyExistException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.IPaymentService;
import org.libredemat.service.request.ILocalReferentialService;
import org.libredemat.service.request.IParkCardService;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.service.request.IRequestLockService;
import org.libredemat.service.request.impl.RequestService;
import org.libredemat.service.request.parking.IParkCardRequestService;

public class ParkCardRequestService extends RequestService implements IParkCardRequestService
{
	private static Logger logger = Logger.getLogger(ParkCardRequestService.class);
	private IPaymentDAO paymentDAO;
	private IRequestDAO requestDAO;
	private IIndividualDAO individualDAO;
	private IPaymentService paymentService;
	private IParkCardService parkCardService;
	protected IRequestActionService requestActionService;
	private IRequestLockService requestLockService;
	protected ILocalReferentialService localReferentialService;

	@Override
	public boolean isPayableAtValidateTS(Request request)
	{
		Payment payment = paymentDAO.getByRequestIdOnly(request.getId());
		if (payment == null) return true;
		return false;
	}
	
	@Override
	public boolean acceptPayment(Request request)
	{
		return true;
	}
	
	@Override
	public boolean accept(Request request)
	{
		return request instanceof ParkCardRequest;
	}

	@Override
	public Request getSkeletonRequest()
	{
		return new ParkCardRequest();
	}

	@Override
	public boolean onPaymentValidated(Request request, String paymentReference) throws CvqException
	{
		((ParkCardRequest) request).setPaymentReference(paymentReference);
		requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_VALIDATED,
				"Le paiement a été validé", "", null, null);
		requestLockService.release(request.getId());
		return false;
	}

	@Override
	public boolean onPaymentRefused(Request request) throws CvqException
	{
		requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_REFUSED, "Le paiement a été refusé, la demande reste à payer",
				"", null, null);
		Payment payment = paymentDAO.getByRequestIdOnly(request.getId());
		if (payment != null) 
		{
			payment.setState(PaymentState.TOPAY);
			paymentDAO.update(payment);
		}
		requestLockService.release(request.getId());
		return false;
	}

	@Override
	public boolean onPaymentCancelled(Request request) throws CvqException
	{
		requestActionService.addAction(request.getId(), RequestActionType.PAYMENT_CANCELLED,
				"le paiement a été annulé par le citoyen, la demande reste à payer", "", null, null);
		Payment payment = paymentDAO.getByRequestIdOnly(request.getId());
		if (payment != null) 
		{
			payment.setState(PaymentState.TOPAY);
			paymentDAO.update(payment);
		}
		requestLockService.release(request.getId());
		return false;
	}

	@Override
	public Payment buildPayment(Request request) throws CvqException, IXEPaymentAllReadyExistException,
			IXENoBrokerFindException
	{
		Payment payment = paymentDAO.getByRequestIdOnly(request.getId());
		if (payment != null) return payment;
		ParkCardRequest pkRqt = ((ParkCardRequest) request);
		List<ParkImmatriculation> immatriculations = pkRqt.getParkImatriculation();
		String immats = "";
		Double totalAmount = 0.0;
		List<ParkImmatriculation> allImmatAllReadyPayed = getAllImmatAllReadyPayed(pkRqt.getSubjectId(), request.getId());
		int sizePlus = allImmatAllReadyPayed.size();
		for (int i = 0; i < immatriculations.size(); i++)
		{
			ParkImmatriculation immat = immatriculations.get(i);
			immats += immat.getImmatriculation();
			if (i != immatriculations.size() - 1) immats += ", ";
			try
			{
				totalAmount += Double.valueOf(getTarifCartByParkResident(pkRqt.getParkResident(), (i + 1 + sizePlus)));
			}
			catch (Exception ex)
			{
				throw new CvqException("ParseDouble Exception : Erreur de format du prix des cartes." + ex);
			}
		}
		Map<String, String> brokers = paymentService.getAllBrokersByType("Park Card");
		if (brokers.isEmpty()) throw new IXENoBrokerFindException("error.broker.notexist");
		String broker = "";
		for (String keyBroker : brokers.keySet())
		{
			broker = keyBroker;
		}
		InternalInvoiceItem internalInvoiceItem = new InternalInvoiceItem(
				"Demande de carte de stationnement (" + immats + ")", 
				totalAmount, 
				request.getId().toString(), // key
				"capdemat", // keyOwner
				broker, // nom du broker
				1, // quantité
				totalAmount); // remontant
		return paymentService.createPaymentContainer(internalInvoiceItem, PaymentMode.INTERNET);
	}

	@Override
	public boolean isSubjectPolicySpecific(Long individualId, Long requestId)
	{
		int limiteCartByParkResident = getLimiteCartByParkResident(getResidentType(individualId).getLegacyLabel());
		return getNumberOfImmatAllReadyPayed(individualId, requestId) < limiteCartByParkResident;
	}
	
	@Override
	public int getNumberOfImmatAllReadyPayed(Long individualId, Long requestId)
	{
		List<ParkImmatriculation> allImmatAllReadyPayed = getAllImmatAllReadyPayed(individualId, requestId);
		return allImmatAllReadyPayed.size();
	}

	private List<ParkImmatriculation> getAllImmatAllReadyPayed(Long subjectId, Long requestId)
	{
		/**
		 * Si la date du jour est supérieur à la date du 1/8 de cette année: on
		 * prend dateStart : 1/8 de cette année jusqu'au 1/8 de l'année prochaine
		 * 
		 * Si la date du jour est inférieur à la date du 1/8 de cette année: on
		 * prend dateStart : 1/8 de l'année dernière - dateEnd : 1/8 de cette
		 * année
		 */
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(date.getYear() - 1 + 1900, 7, 1);
		Date dateStart = cal.getTime();
		cal.set(date.getYear() + 1900, 7, 1);
		Date dateEnd = cal.getTime();
		if (date.after(dateEnd))
		{
			cal.set(date.getYear() + 1900, 7, 1);
			dateStart = cal.getTime();
			cal.set(date.getYear() + 1 + 1900, 7, 1);
			dateEnd = cal.getTime();
		}
		List<Request> listBySubjectAndLabel = requestDAO.listBySubjectAndLabelAndDate(requestId, subjectId, getLabel(), dateStart,
				dateEnd, true);
		/**
		 * Pour chaque demande effectuées par cette individu, il faut évaluer
		 * s'il lui reste des cartes à acheter. Si a effectué une demande d'une
		 * carte ET qu'il la payée ET qu'il est résident il peut commander 2
		 * cartes de plus. Si a effectué une demande d'une carte ET qu'il la
		 * payée ET qu'il n'est pas résident il ne peut pas commander plus de
		 * carte. Si a effectué une demande d'une carte ET qu'il la payée ET
		 * qu'il est borderRésident il peut prendre une carte encore.
		 */
		List<ParkImmatriculation> parkImatriculation = new ArrayList<ParkImmatriculation>();
		if (listBySubjectAndLabel.isEmpty()) return parkImatriculation;
		for (Request rqt : listBySubjectAndLabel)
		{
			ParkCardRequest rqtPark = ((ParkCardRequest) rqt);
			Payment payment = paymentDAO.getByRequestIdOnly(rqtPark.getId());
			if (payment != null)
			{
				List<ParkImmatriculation> imat = rqtPark.getParkImatriculation();
				parkImatriculation.addAll(imat);
			}
		}
		return parkImatriculation;
	}

	@Override
	public ParkResidentType getResidentType(Long individualId)
	{
		try
		{
			List<StreetBorderReferential> allStreets = parkCardService.getAllStreets();
			Individual individual = individualDAO.findById(individualId);
			for (StreetBorderReferential streetBorderReferential : allStreets)
			{
				if (streetBorderReferential.getStreetLabel().toLowerCase().trim()
						.equals(individual.getAddress().getStreetName().toLowerCase().trim())
						&& streetBorderReferential.getPostalCode().trim().equals(individual.getAddress().getPostalCode().trim())) return ParkResidentType.BORDERRESIDENT;
			}
			if (SecurityContext.getCurrentSite().getPostalCode().trim().equals(individual.getAddress().getPostalCode().trim())) return ParkResidentType.RESIDENT;
		}
		catch (CvqException e)
		{
			logger.debug("Une erreur s'est produite à la récupération des rues : " + e.getMessage());
		}
		return ParkResidentType.FOREIGNER;
	}

	@Override
	public int getLimiteCartByParkResident(String parkRessident)
	{
		try
		{
			LocalReferentialEntryData localReferentialEntry = localReferentialService.getLocalReferentialEntry(
					getLabel(), parkRessident + "LimitNumber", "limit" + parkRessident);
			return Integer.parseInt(localReferentialEntry.getLabel());
		}
		catch (Exception ex)
		{
			return 0;
		}
	}

	@Override
	public int getTarifCartByParkResident(String parkRessident, int number) throws CvqException
	{
		try
		{
			LocalReferentialEntryData localReferentialEntry = localReferentialService.getLocalReferentialEntry(
					getLabel(), parkRessident + "Tarification", "price" + (number-1));
			return Integer.parseInt(localReferentialEntry.getLabel());
		}
		catch (Exception ex)
		{
			throw new CvqException(ex.getMessage());
		}
	}

	@Override
	public Payment getPayment(Request request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPayment(Request request, Payment paiement)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean supportUnregisteredCreation()
	{
		return false;
	}

	public IPaymentService getPaymentService()
	{
		return paymentService;
	}

	public void setPaymentService(IPaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	public void setPaymentDAO(IPaymentDAO paymentDAO)
	{
		this.paymentDAO = paymentDAO;
	}

	public void setRequestActionService(IRequestActionService requestActionService)
	{
		this.requestActionService = requestActionService;
	}

	public void setRequestLockService(IRequestLockService requestLockService)
	{
		this.requestLockService = requestLockService;
	}

	public void setRequestDAO(IRequestDAO requestDAO)
	{
		this.requestDAO = requestDAO;
	}

	public void setLocalReferentialService(ILocalReferentialService localReferentialService)
	{
		this.localReferentialService = localReferentialService;
	}

	public void setIndividualDAO(IIndividualDAO individualDAO)
	{
		this.individualDAO = individualDAO;
	}

	public void setParkCardService(IParkCardService parkCardService)
	{
		this.parkCardService = parkCardService;
	}
}
