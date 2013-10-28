package org.libredemat.dao.request.hibernate;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.libredemat.business.request.RequestAction;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestAdminAction;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.RequestAdminAction.Type;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IRequestActionDAO;


/**
 * Implementation of the {@link IRequestActionDAO} interface.
 * 
 * @author jsb@zenexity.fr
 */
public class RequestActionDAO extends JpaTemplate<RequestAction, Long> implements IRequestActionDAO {

    @Override
    public boolean hasAction(final Long requestId, final RequestActionType type) {
        return !Long.valueOf(0).equals(HibernateUtil.getSession()
            .createQuery("select count(*) from RequestAction where request_id = :requestId and type = :type")
                .setLong("requestId", requestId).setString("type", type.name()).uniqueResult());
    }

    @Override
    public RequestAction getAction(final Long requestId, final RequestActionType type,
            final RequestState state, boolean first) {
        return (RequestAction) JpaUtil.getEntityManager()
            .createQuery("select ra from RequestAction ra where " +
                    "request_id = :requestId and type = :type and resultingState = :state " +
                    "order by date " + (first ? "ASC" : "DESC"))
                .setParameter("requestId", requestId)
                .setParameter("type", type)
                .setParameter("state", state)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RequestAdminAction> getAdminActions() {
        return HibernateUtil.getSession().createCriteria(RequestAdminAction.class)
            .addOrder(Order.desc("date")).list();
    }

    @Override
    public boolean hasArchivesMigrationAction() {
        return HibernateUtil.getSession().createCriteria(RequestAdminAction.class)
            .add(Restrictions.eq("type", Type.ARCHIVES_MIGRATED)).uniqueResult() != null;
    }
}
