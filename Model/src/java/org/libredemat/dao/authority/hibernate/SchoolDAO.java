package org.libredemat.dao.authority.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.libredemat.business.authority.School;
import org.libredemat.dao.authority.ISchoolDAO;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.util.Critere;


/**
 * The "School" service Hibernate implementation. This class is responsible for
 * data access logic functions
 * 
 * @author bor@zenexity.fr
 */
public class SchoolDAO extends JpaTemplate<School,Long> implements ISchoolDAO {

    public School findByName(final String name) {
        Criteria crit = HibernateUtil.getSession().createCriteria(School.class);
        crit.add(Critere.compose("name", name, Critere.EQUALS));
        return (School) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<School> listAll() {
        return (List<School>)HibernateUtil.getSession()
            .createQuery("from School as school").list();
    }

    @Override
    public List<School> getActive() {
        return (List<School>)HibernateUtil.getSession().createQuery("from School as s where s.active = true").list();
    }

    public boolean exists(String name, Long id) {
        String request = "from School school where school.name = :name";
        if (id != null) {
            request += " and not school.id = :id";
        }
        Query query = HibernateUtil.getSession().createQuery(request)
            .setString("name", name);
        if (id != null) {
            query.setLong("id", id.longValue());
        }
        if (query.uniqueResult() == null)
            return false;
        else
            return true;
    }
}
