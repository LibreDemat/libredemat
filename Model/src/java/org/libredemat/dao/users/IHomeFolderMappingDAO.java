package org.libredemat.dao.users;

import java.util.List;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.dao.jpa.IJpaTemplate;

/**
 * HomeFolderMapping interface
 * 
 * Hack Inexine : new DAO interface
 * 
 * @author Inexine : Frederic Fabre
 * 
 */
public interface IHomeFolderMappingDAO extends IJpaTemplate<HomeFolderMapping, Long>
{
	/**
	 * Search for HomeFolderMappings list with an externalId
	 * 
	 * @param externalId
	 *            id to find
	 * @param externalServiceLabel
	 *            External service label
	 * @return HomeFolderMappings list
	 */
	// Inexine Hack Frederic Fabre
	public List<HomeFolderMapping> findByExternalId(String EexternalServiceLabel, String externalId);
	
	/**
	 * @author pit@tor
	 * 
	 * Elle doit renvoyer une list avec un seul élément. Pour être sur de ne pas se prendre un NExceptionNotOnlyResult
	 * Je renvoie une liste ;-)
	 * 
	 * @param homeFolderId
	 * @return List<HomeFolderMapping> 
	 */
	public List<HomeFolderMapping> findByHomeFolderId(Long homeFolderId);

	List<HomeFolderMapping> findByhomeFolderIdAndLabel(String externalServiceLabel, Long homeFolderId);
}
