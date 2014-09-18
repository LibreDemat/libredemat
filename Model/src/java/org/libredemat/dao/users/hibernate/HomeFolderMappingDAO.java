/**
 * 
 */
package org.libredemat.dao.users.hibernate;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.dao.CvqDaoException;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.users.IHomeFolderMappingDAO;
import org.libredemat.service.users.impl.UserSearchService;
import org.libredemat.util.Critere;

/**
 * HomeFolderMapping DAO class
 * 
 * Hack Inexine : new DAO class
 * 
 * @author Inexine : Frederic Fabre
 * 
 */
public class HomeFolderMappingDAO extends JpaTemplate<HomeFolderMapping, Long> implements IHomeFolderMappingDAO
{
	/** Logger */
	private static Logger logger = Logger.getLogger(HomeFolderMappingDAO.class);

	/**
	 * Search for HomeFolderMappings list with an externalId
	 * 
	 * Hack Inexine
	 */
	@Override
	public List<HomeFolderMapping> findByExternalId(String externalServiceLabel, String externalId)
	{
		try
		{
			Criteria crit = HibernateUtil.getSession().createCriteria(HomeFolderMapping.class);
			crit.add(Critere.compose("externalId", externalId, Critere.EQUALS));
			crit.add(Critere.compose("externalServiceLabel", externalServiceLabel, Critere.EQUALS));
			return (List<HomeFolderMapping>) crit.list();
		}
		catch (CvqDaoException cvqDaoException)
		{
			logger.error(cvqDaoException);
			return null;
		}
	}

	@Override
	public List<HomeFolderMapping> findByhomeFolderIdAndLabel(String externalServiceLabel, Long homeFolderId)
	{
		return JpaUtil
				.getEntityManager()
				.createQuery(
						"from HomeFolderMapping where" + " homeFolderId = :homeFolderId "
								+ " and externalServiceLabel = :externalServiceLabel", HomeFolderMapping.class)
				.setParameter("homeFolderId", homeFolderId).setParameter("externalServiceLabel", externalServiceLabel)
				.getResultList();
	}

	/**
	 * Search for HomeFolderMappings list with an externalId
	 * 
	 * Hack Inexine
	 */
	@Override
	public List<HomeFolderMapping> findByHomeFolderId(Long homeFolderId)
	{
		try
		{
			Criteria crit = HibernateUtil.getSession().createCriteria(HomeFolderMapping.class);
			crit.add(Critere.compose("homeFolderId", homeFolderId, Critere.EQUALS));
			return ((List<HomeFolderMapping>) crit.list());
		}
		catch (CvqDaoException cvqDaoException)
		{
			logger.error(cvqDaoException);
			return null;
		}
	}
}
