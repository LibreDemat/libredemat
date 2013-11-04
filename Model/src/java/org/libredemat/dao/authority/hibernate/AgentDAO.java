package org.libredemat.dao.authority.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.libredemat.business.authority.Agent;
import org.libredemat.dao.authority.IAgentDAO;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;



/**
 * Implementation of the {@link IAgentDAO} interface.
 *
 * @author bor@zenexity.fr
 */
public class AgentDAO extends JpaTemplate<Agent,Long> implements IAgentDAO {

    public boolean exists(Long id) {
        Query query = HibernateUtil.getSession()
            .createQuery("from Agent agent where agent.id = :id ")
            .setLong("id", id.longValue());
        return query.uniqueResult() != null;
    }

    public boolean exists(String login, Long id) {
        String request = "from Agent agent where agent.login = :login";
        if (id != null) {
            request += " and not agent.id = :id";
        }
        Query query = HibernateUtil.getSession().createQuery(request)
            .setString("login", login);
        if (id != null) {
            query.setLong("id", id.longValue());
        }
        if (query.uniqueResult() == null)
            return false;
        else
            return true;
    }

    public Agent findByLogin(final String login) {
        Query query = HibernateUtil.getSession()
            .createQuery("from Agent agent where agent.login = :login ")
            .setString("login", login);
        return (Agent) query.uniqueResult(); 
    }
    
    public List listAll() {

        StringBuffer sb = new StringBuffer();
        sb.append("from Agent as agent order by agent.lastName asc");

        return HibernateUtil.getSession().createQuery(sb.toString()).list();
    }
}
