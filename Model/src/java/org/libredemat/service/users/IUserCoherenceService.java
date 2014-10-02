package org.libredemat.service.users;

import java.util.List;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.external.HomeFolderMapping;

public interface IUserCoherenceService
{
	void verifyConcordenceMappings(HomeFolder homeFolderTarget);

	void verifyMappingOfAllHomeFolders();

	void verifyRequestOfAllHomeFolders();

	boolean existHomeFolderMappingByExternalLabel(List<HomeFolderMapping> homeFolderMappings,
			String externalServiceLabel);

	void verifyRolesOfAllHomeFolders();

	void verifyPaymentsOfAllHomeFolders();

	boolean existHomeFolderMappingByExternalLabel(HomeFolder homeFolder, String externalServiceLabel);
}
