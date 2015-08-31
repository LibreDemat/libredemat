package org.libredemat.dao.users.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.UserState;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.users.IAdultDAO;
import org.libredemat.util.Critere;


/**
 * The "Adult" service Hibernate implementation. This class is responsible for
 * data access logic functions
 * 
 * @author bor@zenexity.fr
 */
public class AdultDAO extends IndividualDAO implements IAdultDAO {

    public AdultDAO() {
        super();
    }

    @Override
    public Adult findByLogin(String login) {
        Criteria crit = HibernateUtil.getSession().createCriteria(Adult.class);
        crit.add(Critere.compose("login", login, Critere.EQUALS));
        return (Adult)crit.uniqueResult();
    }

    public List<Adult> listAdultsByHomeFolder(final Long homeFolderId, UserState... states) {
        String hql = "from Adult as adult"
            + " where adult.homeFolder.id = :homeFolderId"
            + " and adult.state in (:states)";
        return HibernateUtil.getSession()
            .createQuery(hql)
            .setParameter("homeFolderId", homeFolderId.longValue())
            .setParameterList("states", states.length > 0 ? states : UserState.activeStates)
            .list();
    }

    @Override
    public List<Adult> matchAdults (Map<String,String> parameters) {
        Query q = HibernateUtil.getSession().createQuery(
                "from Adult a where" +
                    " ((lower(a.firstName) = lower(:firstName) and lower(a.lastName) = lower(:lastName))" +
                    " or lower(a.email) = lower(:email) or lower(a.homePhone) = lower(:homePhone)" +
                    " or lower(:address) like '%'|| lower(a.address.streetName) || '%')" +
                    " and a.homeFolder.temporary is false"
        );
        return q.setProperties(parameters).list();
    }

    @Override
    public List<Adult> findDuplicates(Map<String,String> parameters) {
        Query q = HibernateUtil.getSession().createQuery(
                "from Adult a where" +
                    " (REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ltrim(rtrim(lower(a.firstName), ' '), ' '), 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'ë', 'e'), 'à', 'a'), 'â', 'a'), 'ä', 'a'), 'î', 'i'), 'ï', 'i') = lower(:firstName)" +
                    " and REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ltrim(rtrim(lower(a.lastName), ' '), ' '), 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'ë', 'e'), 'à', 'a'), 'â', 'a'), 'ä', 'a'), 'î', 'i'), 'ï', 'i') = lower(:lastName))" +
                    " and ( lower(a.email) = lower(:email) " +
                    " or lower(:address) like '%'|| lower(a.address.streetName) || '%' )" +
                    " and a.state NOT IN ( '" + UserState.ARCHIVED.name() + "',  '" + UserState.PENDING.name() + "' )" +
                    " and a.homeFolder.temporary is false"
        );

        return q.setProperties(parameters).list();
    }

    @Override
    public Long countDuplicates() {
        return (Long)HibernateUtil.getSession()
            .createQuery("select count(*) from Adult a where a.duplicateAlert is true and a.homeFolder.temporary is false and homeFolder != null and a.state NOT IN ( '" + UserState.ARCHIVED.name() + "',  '" + UserState.PENDING.name() + "' )")
            .iterate().next();
    }

    @Override
    public List<Adult> listDuplicates(Map<String,String> sortParams, Integer max, Integer offset) {
        StringBuffer sb = new StringBuffer();
        sb.append("from Individual individual where individual.duplicateAlert is true and homeFolder != null ")
                .append("and homeFolder.temporary is false and individual.state NOT IN ( '")
                    .append(UserState.ARCHIVED.name()).append("', '")
                    .append(UserState.PENDING.name()).append("') ");
        HibernateUtil hUtil = new HibernateUtil();
        hUtil.buildSort(sortParams, sb);

        Query query = HibernateUtil.getSession().createQuery(sb.toString());
        if (max > 0)
            query.setMaxResults(max);
        query.setFirstResult(offset != null ? offset : 0);
        return query.list();
    }
    @Override
    public List<Adult> findResponsibleDuplicates(Map<String, String> parameters)
    {
        Query q = HibernateUtil
                .getSession()
                .createQuery(
                        "from Adult a where"
                                + " (REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ltrim(rtrim(lower(a.firstName), ' '), ' '), 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'ë', 'e'), 'à', 'a'), 'â', 'a'), 'ä', 'a'), 'î', 'i'), 'ï', 'i') = :firstName "
                                + " and REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ltrim(rtrim(lower(a.lastName), ' '), ' '), 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'ë', 'e'), 'à', 'a'), 'â', 'a'), 'ä', 'a'), 'î', 'i'), 'ï', 'i') = :lastName) "
                                + " and (REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ltrim(rtrim(lower(a.email), ' '), ' '), 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'ë', 'e'), 'à', 'a'), 'â', 'a'), 'ä', 'a'), 'î', 'i'), 'ï', 'i') = :email "
                                + " or lower(:address) like '%'||lower(a.address.streetName)||'%')" + " and a.state != '"
                                + UserState.ARCHIVED.name() + "'" + " and a.homeFolder.temporary is false"
                                + " and a.homeFolder.id != " + Long.valueOf(parameters.get("homeFolderId")) // HACK
                                                                                                            // INEXINE
                );
        return q.setProperties(parameters).list();
    }
}
