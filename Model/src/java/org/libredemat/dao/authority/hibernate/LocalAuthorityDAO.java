package org.libredemat.dao.authority.hibernate;

import org.hibernate.Query;
import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.dao.authority.ILocalAuthorityDAO;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.GenericDAO;
import org.libredemat.dao.jpa.JpaTemplate;


/**
 * The "LocalAuthority" service Hibernate implementation. This class is
 * responsible for data access logic functions
 * 
 * @author bor@zenexity.fr
 */
public class LocalAuthorityDAO extends JpaTemplate<LocalAuthority,Long> implements ILocalAuthorityDAO {

    public LocalAuthority findByName(final String name) {
        Query query = HibernateUtil.getSession()
            .createQuery("from LocalAuthority la where la.name like lower(:name)")
            .setString("name", name);
        return (LocalAuthority) query.uniqueResult();
    }
}
