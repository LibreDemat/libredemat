package org.libredemat.service.request.parking;

import org.libredemat.business.request.parking.ParkResidentType;
import org.libredemat.exception.CvqException;
import org.libredemat.service.payment.IRequestPaymentService;
import org.libredemat.service.request.IRequestService;

public interface IParkCardRequestService extends IRequestService, IRequestPaymentService {
	ParkResidentType getResidentType(Long individualId) throws CvqException;

	int getLimiteCartByParkResident(String parkRessident);

	int getTarifCartByParkResident(String parkRessident, int number) throws CvqException;

	int getNumberOfImmatAllReadyPayed(Long individualId, Long requestId);

}
