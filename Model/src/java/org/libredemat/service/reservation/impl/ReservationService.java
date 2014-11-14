/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libredemat.service.reservation.impl;

import org.libredemat.business.reservation.ReservationItem;
import org.libredemat.dao.reservation.IReservationDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.service.reservation.IReservationService;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author seb
 */
public class ReservationService implements IReservationService{
    
    /** Logs */
    private static Logger logger = Logger.getLogger(ReservationService.class);
    private IReservationDAO reservationDAO;

    @Override
    public Long create(ReservationItem ri) throws CvqException {
        if(ri == null)
            throw new CvqException("There is no reservation item !");
        Long reservationId = reservationDAO.create(ri).getId();
        logger.debug("Create reservation item with id : " + reservationId);
        return reservationId;
    }

    @Override
    public void modify(ReservationItem ri) throws CvqException {
        if(ri != null)
            reservationDAO.update(ri);
    }

    @Override
    public void delete(String session, Long id) {
        ReservationItem ri = reservationDAO.findById(session, id);
        if(ri != null)
            reservationDAO.delete(ri);
    }

    @Override
    public List<ReservationItem> getAll(String session) throws CvqException {
        return reservationDAO.findAll(session);
    }

    @Override
    public List<ReservationItem> getByHomeFolder(String session, Long homeFolderId) throws CvqException {
        return reservationDAO.findByHomeFolder(session, homeFolderId);
    }

    @Override
    public List<ReservationItem> getByChild(String session, Long childId) throws CvqException {
        return reservationDAO.findByChildId(session, childId);
    }
    
    @Override
    public List<ReservationItem> getByActivityCode(String session, String activityCode) throws CvqException {
        return reservationDAO.findByActivityCode(session, activityCode);
    }

    @Override
    public List<ReservationItem> getByActivityServiceCode(String session, String activityServiceCode) throws CvqException {
        return reservationDAO.findByActivityServiceCode(session, activityServiceCode);
    }

    @Override
    public ReservationItem getById(String session, Long id) {
        return reservationDAO.findById(session, id);
    }

    @Override
    public ReservationItem getByParams(String session, Long childId, String activityCode, String activityServiceCode, Date day) {
        return reservationDAO.findTheItem(session, childId, activityCode, activityServiceCode, day);
    }
        
    public void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }
    
}
