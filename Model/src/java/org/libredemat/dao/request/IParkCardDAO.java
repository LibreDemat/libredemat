package org.libredemat.dao.request;

import java.util.List;
import org.hibernate.Session;
import org.libredemat.business.request.parking.config.StreetBorderReferential;
import org.libredemat.dao.jpa.IJpaTemplate;

public interface IParkCardDAO extends IJpaTemplate<StreetBorderReferential,Long>  {
    
    StreetBorderReferential findByStreetLabel(String label);
    
    StreetBorderReferential findById(Long id);
    
    List<StreetBorderReferential> listAllStreets();
    
    public void deleteAll(Session session);
    
}
