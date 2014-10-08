package org.libredemat.service.request;

import java.util.List;
import java.util.Map;

import org.libredemat.business.request.parking.config.StreetBorderReferential;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.exception.CvqParkCardException;
import org.libredemat.exception.CvqTicketBookingException;

public interface IParkCardService {
    
    StreetBorderReferential createStreetBorderReferential(StreetBorderReferential streetBorderReferential) throws CvqException;
    
    void updateStreetBorderReferential(StreetBorderReferential streetBorderReferential);
    
    void deleteStreetBorderReferential(Long id) throws CvqObjectNotFoundException;
    
    List<StreetBorderReferential> getAllStreets() throws CvqException;
    
    StreetBorderReferential getById(Long id) throws CvqObjectNotFoundException;
    
    Map<String,Integer> importStreets(byte[] streets) throws CvqParkCardException;
    
    boolean isThereStreets()throws CvqException;
}
