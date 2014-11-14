package org.libredemat.dao.reservation;

import org.libredemat.business.reservation.ReservationItem;
import org.libredemat.dao.jpa.IJpaTemplate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author seb
 */
public interface IReservationDAO extends IJpaTemplate<ReservationItem, Long> {
    
    
    /**
     * Find one reservation item by id
     * @param id
     * @param session
     * @return 
     */
    ReservationItem findById(String session, Long id);
    
    /**
     * Find one reservation item by multiple params
     * @param session
     * @param childId
     * @param activityCode
     * @param ActivityServiceCode
     * @param day
     * @return 
     */
    ReservationItem findTheItem(String session, Long childId, String activityCode, String activityServiceCode, Date day);    
    
    /**
     *  List of all reservation item from session
     * @param session
     * @return List<ReservationItem>
     */
    List<ReservationItem> findAll(String session);
    
    /**
     * List of reservation item from session filter by child id
     * @param session
     * @param childId
     * @return List<ReservationItem>
     */
    List<ReservationItem> findByChildId(String session, Long childId);
    
    /**
     * List of reservation item from session filter by home folder
     * @param Session
     * @param homeFolder
     * @return List<ReservationItem> 
     */
    List<ReservationItem> findByHomeFolder(String session, Long homeFolder);
    
    /**
     * List of reservation item from session filter by activity code
     * @param Session
     * @param ActivityCode
     * @return List<ReservationItem>
     */
    List<ReservationItem> findByActivityCode(String session, String activityCode);
    
    /**
     * List of reservation item from session filter by activity service code
     * @param Session
     * @param ActivityServiceCode
     * @return List<ReservationItem>
     */
    List<ReservationItem> findByActivityServiceCode(String session, String activityServiceCode);
}
