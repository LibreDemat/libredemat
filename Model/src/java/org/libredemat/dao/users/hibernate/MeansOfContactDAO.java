package org.libredemat.dao.users.hibernate;

import java.util.List;

import org.libredemat.business.users.MeansOfContact;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.users.IMeansOfContactDAO;


public class MeansOfContactDAO extends JpaTemplate<MeansOfContact,Long> implements IMeansOfContactDAO {

    @Override
    public MeansOfContact findByType(MeansOfContactEnum type) {
        return (MeansOfContact) HibernateUtil.getSession()
            .createQuery("from MeansOfContact as meansOfContact where meansOfContact.type = :type")
            .setParameter("type", type)
            .uniqueResult();
    }

    @Override
    public List<MeansOfContact> listAll() {
        return HibernateUtil.getSession().createQuery("from MeansOfContact").list();
    }

    @Override
    public List<MeansOfContact> listAllEnabled() {
        return HibernateUtil.getSession()
            .createQuery("from MeansOfContact as meansOfContact where meansOfContact.enabled = true")
            .list();
    }
}
