package fr.cg95.cvq.dao.users.hibernate;

import java.util.List;

import org.hibernate.Query;

import fr.cg95.cvq.business.users.HomeFolder;
import fr.cg95.cvq.business.users.UserAction;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.dao.jpa.JpaTemplate;
import fr.cg95.cvq.dao.users.IHomeFolderDAO;

/**
 * Implementation of the {@link IHomeFolderDAO} interface.
 * 
 * @author bor@zenexity.fr
 */
public class HomeFolderDAO extends JpaTemplate<HomeFolder,Long> implements IHomeFolderDAO {

    @SuppressWarnings("unchecked")
    public List<HomeFolder> listAll(boolean filterArchived, boolean filterTemporary) {
        StringBuffer sb = new StringBuffer();
        sb.append("from HomeFolder as homeFolder where 1 = 1 ");
        if (filterArchived)
            sb.append(" and homeFolder.state != 'Archived'");
        if (filterTemporary)
            sb.append(" and homeFolder.temporary = 'false'");
        Query query = HibernateUtil.getSession().createQuery(sb.toString());
        
        return query.list();    
    }

    @SuppressWarnings("unchecked")
    public List<UserAction> waitingNotification() {
        return getEntityManager().createQuery("select u from " + UserAction.class.getName() + " u " +
                "where u.type = :type")
                .setParameter("type", UserAction.Type.WAITING_NOTIFICATION)
                .getResultList();
    }

}
