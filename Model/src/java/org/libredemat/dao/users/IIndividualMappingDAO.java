package org.libredemat.dao.users;

import java.util.List;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.dao.jpa.IJpaTemplate;

/**
 * IndividualMappingDAO interface
 * 
 * Hack Inexine : new DAO interface
 * 
 * @author pit@tor
 * 
 */
public interface IIndividualMappingDAO extends IJpaTemplate<IndividualMapping, Long>
{

	List<IndividualMapping> findByExternalId(String externalId);
	List<IndividualMapping> findByExternalLibredematId(String externalLibrdematId);
}
