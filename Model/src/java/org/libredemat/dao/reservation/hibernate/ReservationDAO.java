/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.libredemat.dao.reservation.hibernate;

import org.libredemat.business.reservation.ReservationItem;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.reservation.IReservationDAO;
import org.libredemat.util.Critere;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 *
 * @author seb
 */
public class ReservationDAO extends JpaTemplate<ReservationItem, Long> implements IReservationDAO{

    /** Logs */
    private static Logger logger = Logger.getLogger(ReservationDAO.class);

    @Override
    public ReservationItem findById(String session, Long id) {
        Criteria crit = HibernateUtil.getSession().createCriteria(ReservationItem.class);
        crit.add(Critere.compose("session", session, Critere.EQUALS));
        crit.add(Critere.compose("id", id, Critere.EQUALS));
        return (ReservationItem) crit.uniqueResult();
    }

    @Override
    public ReservationItem findTheItem(String session, Long childId, String activityCode, String activityServiceCode, Date day) {
        Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session and ri.childId = :childId "
                + "and ri.activityCode = :activityCode and ri.activityServiceCode = :activityServiceCode and "
                + "ri.day = :day" )
            .setString("session", session)
            .setLong("childId", childId)
            .setString("activityCode", activityCode)
            .setString("activityServiceCode", activityServiceCode )
            .setDate("day", day);                
        return (ReservationItem) query.uniqueResult(); 
    }
    
    @Override
    public List<ReservationItem> findAll(String session) {
       Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session ")
            .setString("session", session);                
        return (List<ReservationItem>) query.list(); 
    }

    @Override
    public List<ReservationItem> findByHomeFolder(String session, Long homeFolder) {
        Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session and ri.homeFolderId = :homeFolderId")
            .setString("session", session)
            .setLong("homeFolderId", homeFolder);                
        return (List<ReservationItem>) query.list();
    }

    @Override
    public List<ReservationItem> findByChildId(String session, Long childId) {
        Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session and ri.childId = :childId")
            .setString("session", session)
            .setLong("childId", childId);            
        return (List<ReservationItem>) query.list();
    }

    @Override
    public List<ReservationItem> findByActivityCode(String session, String activityCode) {
        Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session and ri.activityCode = :activityCode ")
            .setString("session", session)
            .setString("activityCode", activityCode);
        return (List<ReservationItem>) query.list();
    }

    @Override
    public List<ReservationItem> findByActivityServiceCode(String session, String activityServiceCode) {
        Query query = HibernateUtil.getSession()
            .createQuery("from ReservationItem as ri where ri.session = :session and ri.activityServiceCode = :activityServiceCode " )
            .setString("session", session)
            .setString("activityServiceCode", activityServiceCode );
        return (List<ReservationItem>) query.list();
    }
    
    
}
