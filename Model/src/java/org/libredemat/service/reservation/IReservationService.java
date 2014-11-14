/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libredemat.service.reservation;

import org.libredemat.business.reservation.ReservationItem;
import org.libredemat.exception.CvqException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author seb
 */
public interface IReservationService {
    
    /**
     * Reservation item creation
     * @param ri
     * @return
     * @throws CvqException 
     */
    Long create(ReservationItem ri) throws CvqException;
    
    /**
     * Reservation item modification
     * @param ri
     * @throws CvqException 
     */
    void modify(ReservationItem ri) throws CvqException;
    
    /**
     * Reservation item delete
     * @param id 
     * @param session
     */
    void delete(final String session, final Long id);
    
    /**
     * get all reservation items from session
     * @param session
     * @return
     * @throws CvqException 
     */
    List<ReservationItem> getAll(final String session) throws CvqException;
    
    /**
     * get reservation items by home folder
     * @param session
     * @param homeFolderId
     * @return
     * @throws CvqException 
     */
    List<ReservationItem> getByHomeFolder(final String session, final Long homeFolderId) throws CvqException;
    
    /**
     * get reservation items by child id
     * @param session
     * @param childId
     * @return
     * @throws CvqException 
     */
    List<ReservationItem> getByChild(final String session, final Long childId) throws CvqException;
    
    /**
     * get reservation items by activity code
     * @param session
     * @param activityCode
     * @return
     * @throws CvqException 
     */
    List<ReservationItem> getByActivityCode(final String session, final String activityCode) throws CvqException;
    
    /**
     * get reservation items by activity service code
     * @param session
     * @param activityServiceCode
     * @return
     * @throws CvqException 
     */
    List<ReservationItem> getByActivityServiceCode(final String session, final String activityServiceCode) throws CvqException;
    
    /**
     * get item from id
     * @param session
     * @param id
     * @return 
     */
    ReservationItem getById(final String session, final Long id);
    
    /**
     * Get item from multiple param
     * @param session
     * @param childId
     * @param activityCode
     * @param activityServiceCode
     * @param day
     * @return 
     */
    ReservationItem getByParams(final String session, final Long childId, final String activityCode, final String activityServiceCode, final Date day);
            
}
