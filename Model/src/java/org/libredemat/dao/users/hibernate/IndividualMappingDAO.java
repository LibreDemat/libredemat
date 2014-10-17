/**
 * 
 */
package org.libredemat.dao.users.hibernate;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.dao.CvqDaoException;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.users.IIndividualMappingDAO;
import org.libredemat.util.Critere;

/**
 * IIndividualMappingDAO DAO class
 * 
 * Hack Inexine : new DAO class
 * 
 * @author pit@tor
 * 
 */
public class IndividualMappingDAO extends JpaTemplate<IndividualMapping, Long> implements IIndividualMappingDAO
{
	/** Logger */
	private static Logger logger = Logger.getLogger(IndividualMappingDAO.class);

	@Override
	public List<IndividualMapping> findByExternalId(String externalId)
	{
		try
		{
			Criteria crit = HibernateUtil.getSession().createCriteria(IndividualMapping.class);
			crit.add(Critere.compose("externalId", externalId, Critere.EQUALS));
			return (List<IndividualMapping>) crit.list();
		}
		catch (CvqDaoException cvqDaoException)
		{
			logger.error(cvqDaoException);
			return null;
		}
	}
}
