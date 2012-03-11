package fr.cg95.cvq.dao.authority.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import fr.cg95.cvq.business.authority.RecreationCenter;
import fr.cg95.cvq.dao.authority.IRecreationCenterDAO;
import fr.cg95.cvq.dao.jpa.JpaTemplate;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.util.Critere;

/**
 * The "RecreationCenter" service Hibernate implementation. This class is
 * responsible for data access logic functions
 * 
 * @author bor@zenexity.fr
 */
public class RecreationCenterDAO extends JpaTemplate<RecreationCenter,Long> implements IRecreationCenterDAO {

    public RecreationCenter findByName(final String name) {
        Criteria crit = HibernateUtil.getSession()
            .createCriteria(RecreationCenter.class);
        crit.add(Critere.compose("name", name, Critere.EQUALS));
        return (RecreationCenter) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<RecreationCenter> listAll() {
        return (List<RecreationCenter>)HibernateUtil.getSession()
            .createQuery("from RecreationCenter as recreationCenter").list();
    }

    @Override
    public List<RecreationCenter> getActive() {
        return (List<RecreationCenter>)HibernateUtil.getSession()
                .createQuery("from RecreationCenter as r where r.active = true").list();
    }

    public boolean exists(String name, Long id) {
        String request = "from RecreationCenter recreationCenter where recreationCenter.name = :name";
        if (id != null) {
            request += " and not recreationCenter.id = :id";
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
