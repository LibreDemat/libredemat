package org.libredemat.dao.request.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.libredemat.business.request.RequestForm;
import org.libredemat.business.request.RequestFormType;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.request.IRequestFormDAO;
import org.libredemat.util.Critere;


/**
 * Hibernate implementation of the {@link IRequestFormDAO} interface.
 *
 * @author bor@zenexity.fr
 */
public class RequestFormDAO extends JpaTemplate<RequestForm,Long> implements IRequestFormDAO {

    public RequestFormDAO() {
        super();
    }

    public RequestForm findByTypeAndRequest(final RequestFormType requestFormType,
            final String requestLabel) {
        Criteria crit = HibernateUtil.getSession().createCriteria(RequestForm.class);
        crit.add(Critere.compose("type", requestFormType, Critere.EQUALS));
        crit.createCriteria("requestTypes")
            .add(Critere.compose("label", requestLabel, Critere.EQUALS));
        return (RequestForm) crit.uniqueResult();
    }
    
    public List<RequestForm> findByTypeAndRequestTypeId(final RequestFormType requestFormType,
            final long requestTypeId) {
        Criteria crit = HibernateUtil.getSession().createCriteria(RequestForm.class);
        crit.add(Critere.compose("type", requestFormType, Critere.EQUALS))
            .addOrder(Order.desc("id"));
        crit.createCriteria("requestTypes")
            .add(Critere.compose("id", requestTypeId, Critere.EQUALS));
        return crit.list();
    }

    /*
     * (non-Javadoc)
     *
     * @param requestFormType
     *
     * @return
     *
     * @see
     * org.libredemat.dao.request.IRequestFormDAO#findByType(org.libredemat.business
     * .request.RequestFormType)
     */
    @Override
    public List<RequestForm> findByType(RequestFormType requestFormType) {
        Criteria crit = HibernateUtil.getSession().createCriteria(RequestForm.class);
        crit.add(Critere.compose("type", requestFormType, Critere.EQUALS)).addOrder(
                Order.desc("id"));
        return crit.list();
    }
}
