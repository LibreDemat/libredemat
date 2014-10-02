package org.libredemat.service.users.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.libredemat.authentication.IAuthenticationService;
import org.libredemat.business.request.Request;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.dao.users.IHomeFolderMappingDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.service.users.IUserCoherenceService;
import org.libredemat.service.users.IUserWorkflowService;
import org.libredemat.service.users.external.IExternalHomeFolderService;

public class UserCoherenceService implements IUserCoherenceService
{
	private static Logger logger = Logger.getLogger(UserCoherenceService.class);
	private IHomeFolderDAO homeFolderDAO;
	private IIndividualDAO individualDAO;
	private IHomeFolderMappingDAO homeFolderMappingDAO;
	private IExternalHomeFolderService externalHomeFolderService;
	private IRequestDAO requestDAO;
	private IAuthenticationService authenticationService;
	private IUserWorkflowService userWorkflowService;

	/**
	 * Vérifi la cohérence des individus sur les demandes de l'ensemble des
	 * comptes.
	 */
	@Override
	public void verifyRequestOfAllHomeFolders()
	{
		logger.info("verifyRequestOfAllHomeFolders() START");
		List<Request> listByHomeFolder = requestDAO.findAllWithoutArchived();
		Map<Long, List<Long>> modifications = new HashMap<Long, List<Long>>();
		int i = 0;
		for (Request request : listByHomeFolder)
		{
			Long homeFolderId = request.getHomeFolderId();
			HomeFolder homeFolder = homeFolderDAO.findById(homeFolderId);
			Individual responsable = homeFolder.getIndividualResponsable();
			try
			{
				boolean modif = false;
				if (!isIndividuInHomeFolder(request.getRequesterId(), homeFolder))
				{
					Long id = findIndividuByNameInHomeFolder(request.getRequesterLastName(),
							request.getRequesterFirstName(), homeFolder);
					if (id != null) request.setRequesterId(id);
					else
					{
						request.setRequesterId(responsable.getId());
						request.setRequesterLastName(responsable.getLastName());
						request.setRequesterFirstName(responsable.getFirstName());
					}
					modif = true;
				}
				if (request.getSubjectId() != null)
				{
					if (!isIndividuInHomeFolder(request.getSubjectId(), homeFolder))
					{
						Long id = findIndividuByNameInHomeFolder(request.getSubjectLastName(),
								request.getSubjectFirstName(), homeFolder);
						if (id != null) request.setSubjectId(id);
						else
						{
							request.setSubjectId(responsable.getId());
							request.setSubjectFirstName(responsable.getFirstName());
							request.setSubjectLastName(responsable.getLastName());
						}
						modif = true;
					}
					else
					{
						if (request.getSubjectLastName() == null)
						{
							request.setSubjectLastName(getLastName(request.getSubjectId(), homeFolder));
							modif = true;
						}
						if (request.getSubjectFirstName() == null)
						{
							request.setSubjectFirstName(getFirstName(request.getSubjectId(), homeFolder));
							modif = true;
						}
					}
				}
				if (modif)
				{
					requestDAO.update(request);
					List<Long> list = modifications.get(homeFolderId);
					if (list == null) list = new ArrayList<Long>();
					list.add(request.getId());
					modifications.put(homeFolderId, list);
				}
			}
			catch (Exception ex)
			{
				String message = ex.getMessage();
				if (ex.getCause() != null) message = ex.getCause().getMessage();
				logger.info("verifyRequestOfAllHomeFolders() Exception on " + homeFolderId + " : " + message);
			}
			i++;
			if (i % 500 == 0) logger.info("verifyRequestOfAllHomeFolders() current is : " + i + "/"
					+ listByHomeFolder.size());
		}
		StringBuffer modifToPrint = new StringBuffer();
		modifToPrint.append(" Résultat des modifications effectuées sur la cohérence des demandes par compte :\n");
		modifToPrint.append(" HOMEFOLDERID ; REQUESTIDS \n");
		for (Long homeFolderId : modifications.keySet())
		{
			List<Long> list = modifications.get(homeFolderId);
			modifToPrint.append(homeFolderId);
			for (Long requestId : list)
			{
				modifToPrint.append(";" + requestId);
			}
			modifToPrint.append("\n");
		}
		logger.info(modifToPrint);
		logger.info("verifyRequestOfAllHomeFolders() END");
	}

	private String getLastName(Long subjectId, HomeFolder homeFolder)
	{
		for (Individual individu : homeFolder.getIndividuals())
		{
			if (individu.getId().compareTo(subjectId) == 0) return individu.getLastName();
		}
		return "NC";
	}

	private String getFirstName(Long subjectId, HomeFolder homeFolder)
	{
		for (Individual individu : homeFolder.getIndividuals())
		{
			if (individu.getId().compareTo(subjectId) == 0) return individu.getFirstName();
		}
		return "NC";
	}

	private boolean isIndividuInHomeFolder(Long subjectId, HomeFolder homeFolder)
	{
		for (Individual indiv : homeFolder.getIndividuals())
		{
			if (indiv.getId().compareTo(subjectId) == 0) return true;
		}
		return false;
	}

	private Long findIndividuByNameInHomeFolder(String requesterLastName, String requesterFirstName,
			HomeFolder homeFolder)
	{
		if (requesterFirstName == null) return null;
		if (requesterLastName == null) return null;
		for (Individual indiv : homeFolder.getIndividuals())
		{
			if (indiv.getLastName().toLowerCase().equals(requesterLastName.toLowerCase())
					&& indiv.getFirstName().toLowerCase().equals(requesterFirstName.toLowerCase())) return indiv
					.getId();
		}
		return null;
	}

	/**
	 * Cette méthode vérifi la cohérence des données des roles pour tous les
	 * homefolders de la base de données
	 * 
	 */
	@Override
	public void verifyRolesOfAllHomeFolders()
	{
		logger.info("verifyRolesOfAllHomeFolders() START");
		List<HomeFolder> findAll = homeFolderDAO.listAll(true, true);
		int i = 0;
		for (HomeFolder homeFolderTarget : findAll)
		{
			verifyConcordenceRoles(homeFolderTarget);
			i++;
			if (i % 500 == 0) logger.info("verifyRolesOfAllHomeFolders() current is : " + i + "/" + findAll.size());
		}
		logger.info("verifyRolesOfAllHomeFolders() END");
	}

	private void verifyConcordenceRoles(HomeFolder homeFolderTarget)
	{
		// vérification qu'un responsable existe sur le compte
		if (homeFolderTarget.getIndividualResponsable() == null)
		{
			try
			{
				List<Individual> electionWithLogin = new ArrayList<Individual>();
				List<Individual> electionWithoutLogin = new ArrayList<Individual>();
				for (Individual indiv : homeFolderTarget.getIndividuals())
				{
					if (indiv instanceof Adult)
					{
						if (((Adult) indiv).getLogin() != null && !((Adult) indiv).getLogin().trim().isEmpty()) electionWithLogin
								.add(indiv);
						else electionWithoutLogin.add(indiv);
					}
				}
				Individual lelu = null;
				if (!electionWithLogin.isEmpty())
				{
					lelu = electionWithLogin.get(0);
				}
				else if (!electionWithoutLogin.isEmpty())
				{
					lelu = electionWithoutLogin.get(0);
					String generateLogin = authenticationService.generateLogin((Adult) lelu);
					((Adult) lelu).setLogin(generateLogin);
					individualDAO.update(lelu);
				}
				else
				{
					// dernier cas pas d'adulte en clair, on archive le compte.
					for (Individual individual : homeFolderTarget.getIndividuals())
					{
						individual.setState("ARCHIVED");
						individualDAO.update(individual);
					}
					homeFolderTarget.setState("ARCHIVED");
					homeFolderDAO.update(homeFolderTarget);
				}
				if (lelu != null) userWorkflowService.link(lelu, homeFolderTarget,
						Collections.singleton(RoleType.HOME_FOLDER_RESPONSIBLE));
			}
			catch (Exception ex)
			{
				String message = ex.getMessage();
				if (ex.getCause() != null) message = ex.getCause().getMessage();
				logger.info("verifyConcordenceRoles() Exception on " + homeFolderTarget.getId() + " : " + message);
			}
		}
	}

	/**
	 * Cette méthode vérifi la cohérence des données des paiements pour tous les
	 * homefolders de la base de données
	 * 
	 */
	@Override
	public void verifyPaymentsOfAllHomeFolders()
	{
		logger.info("verifyPaymentsOfAllHomeFolders() START");
		List<HomeFolder> findAll = homeFolderDAO.findAll();
		for (HomeFolder homeFolderTarget : findAll)
		{
			verifyConcordencePayments(homeFolderTarget);
		}
		logger.info("verifyPaymentsOfAllHomeFolders() END");
	}

	private void verifyConcordencePayments(HomeFolder homeFolderTarget)
	{
		// TODO
	}

	/**
	 * Cette méthode vérifi la cohérence des données de mapping pour tous les
	 * homefolders de la base de données
	 * 
	 */
	@Override
	public void verifyMappingOfAllHomeFolders()
	{
		logger.info("verifyMappingOfAllHomeFolders() START");
		List<HomeFolder> findAll = homeFolderDAO.findAll();
		for (HomeFolder homeFolderTarget : findAll)
		{
			verifyConcordenceMappings(homeFolderTarget);
		}
		logger.info("verifyMappingOfAllHomeFolders() END");
	}

	/**
	 * Cohérence du mapping des individus et homefolders Attention cette méthode
	 * est également utilisée pour la fusion.
	 * 
	 * @param homeFolderTarget
	 */
	@Override
	public void verifyConcordenceMappings(HomeFolder homeFolderTarget)
	{
		verifyConcordenceMappingAdd(homeFolderTarget);
		verifyConcordenceMappingRemove(homeFolderTarget);
	}

	protected void verifyConcordenceMappingRemove(HomeFolder homeFolderTarget)
	{
		List<HomeFolderMapping> findByHomeFolderTargetId = homeFolderMappingDAO.findByHomeFolderId(homeFolderTarget
				.getId());
		Map<HomeFolderMapping, List<IndividualMapping>> toDelete = new HashMap<HomeFolderMapping, List<IndividualMapping>>();
		for (HomeFolderMapping homeFolderMapping : findByHomeFolderTargetId)
		{
			boolean findIndiv = false;
			for (IndividualMapping iM : homeFolderMapping.getIndividualsMappings())
			{
				for (Individual i : homeFolderTarget.getIndividuals())
				{
					if (i.getId().compareTo(iM.getIndividualId()) == 0)
					{
						findIndiv = true;
					}
				}
				if (!findIndiv)
				{
					// list to remove
					List<IndividualMapping> list = toDelete.get(homeFolderMapping);
					if (list == null) list = new ArrayList<IndividualMapping>();
					list.add(iM);
					toDelete.put(homeFolderMapping, list);
				}
			}
		}
		for (HomeFolderMapping hfmK : toDelete.keySet())
		{
			boolean isModify = false;
			List<IndividualMapping> individualMapping = toDelete.get(hfmK);
			for (IndividualMapping deleteId : individualMapping)
			{
				hfmK.getIndividualsMappings().remove(deleteId);
				isModify = true;
			}
			if (isModify) homeFolderMappingDAO.update(hfmK);
		}
	}

	protected void verifyConcordenceMappingAdd(HomeFolder homeFolderTarget)
	{
		List<HomeFolderMapping> findByHomeFolderTargetId = homeFolderMappingDAO.findByHomeFolderId(homeFolderTarget
				.getId());
		for (HomeFolderMapping homeFolderMapping : findByHomeFolderTargetId)
		{
			List<Long> individualIds = new ArrayList<Long>();
			for (Individual i : homeFolderTarget.getIndividuals())
			{
				boolean findIndiv = false;
				for (IndividualMapping iM : homeFolderMapping.getIndividualsMappings())
				{
					if (i.getId().compareTo(iM.getIndividualId()) == 0)
					{
						findIndiv = true;
					}
				}
				if (!findIndiv) individualIds.add(i.getId());
			}
			for (Long individualId : individualIds)
			{
				IndividualMapping mm = new IndividualMapping(individualId, null, homeFolderMapping);
				homeFolderMapping.getIndividualsMappings().add(mm);
				homeFolderMappingDAO.update(homeFolderMapping);
				externalHomeFolderService.getHomeFolderMappings(homeFolderMapping.getId());
			}
		}
	}

	@Override
	public boolean existHomeFolderMappingByExternalLabel(List<HomeFolderMapping> homeFolderMappings,
			String externalServiceLabel)
	{
		if (homeFolderMappings == null) return false;
		for (HomeFolderMapping homeFolderMapping : homeFolderMappings)
		{
			if (homeFolderMapping.getExternalServiceLabel().equals(externalServiceLabel)) return true;
		}
		return false;
	}

	@Override
	public boolean existHomeFolderMappingByExternalLabel(HomeFolder homeFolder, String externalServiceLabel)
	{
		return (existHomeFolderMappingByExternalLabel(homeFolderMappingDAO.findByHomeFolderId(homeFolder.getId()),
				externalServiceLabel));
	}

	public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO)
	{
		this.homeFolderDAO = homeFolderDAO;
	}

	public void setHomeFolderMappingDAO(IHomeFolderMappingDAO homeFolderMappingDAO)
	{
		this.homeFolderMappingDAO = homeFolderMappingDAO;
	}

	public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService)
	{
		this.externalHomeFolderService = externalHomeFolderService;
	}

	public void setRequestDAO(IRequestDAO requestDAO)
	{
		this.requestDAO = requestDAO;
	}

	public void setIndividualDAO(IIndividualDAO individualDAO)
	{
		this.individualDAO = individualDAO;
	}

	public void setAuthenticationService(IAuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}

	public void setUserWorkflowService(IUserWorkflowService userWorkflowService)
	{
		this.userWorkflowService = userWorkflowService;
	}
}
