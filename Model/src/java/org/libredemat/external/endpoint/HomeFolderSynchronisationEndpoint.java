/**
 * HomeFolder synchronisation endpoint
 */
package org.libredemat.external.endpoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderMappingType;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationRequestDocument;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationRequestDocument.HomeFolderSynchronisationRequest;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationResponseDocument;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationResponseDocument.HomeFolderSynchronisationResponse;
import fr.capwebct.capdemat.homeFolderSynchronisation.IndividualMappingType;
import org.libredemat.business.users.Address;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.ChildInformationSheet;
import org.libredemat.business.users.Diet;
import org.libredemat.business.users.DietEnum;
import org.libredemat.business.users.FamilyStatusType;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.IndividualRole;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.SexType;
import org.libredemat.business.users.TitleType;
import org.libredemat.business.users.UserAction;
import org.libredemat.business.users.UserState;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.exception.CvqException;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.IUserWorkflowService;
import org.libredemat.service.users.external.IExternalHomeFolderService;

/**
 * Homefolder synchronisation : Create or modify homefolder Return homefolder id
 * and individual ids, avec login, or error code
 * 
 * cf HomeFolderSynchronisation.xsd
 * 
 * @author Inexine : Frederic Fabre Hack Inexine
 * 
 */
public class HomeFolderSynchronisationEndpoint extends AbstractMarshallingPayloadEndpoint
{
	/** User search service */
	IUserSearchService userSearchService;
	/** User workflow service */
	IUserWorkflowService userWorkflowService;
	/** External homefolder service */
	IExternalHomeFolderService externalHomeFolderService;

	/**
	 * Constructor
	 */
	public HomeFolderSynchronisationEndpoint(Marshaller marshaller)
	{
		super(marshaller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint
	 * #invokeInternal(java.lang.Object)
	 */
	@Override
	protected Object invokeInternal(Object request)
	{
		logger.info("request : " + request);
		// Initialisations
		// Used to found if HomeFolder allready exists
		String homeFolderCapdematId = "";
		String homeFolderExternalId = "";
		HomeFolder homefolderFound = null;
		List<HomeFolder> homeFolders = new ArrayList<HomeFolder>();
		// Request
		HomeFolderSynchronisationRequest homeFolderSynchronisationRequest = ((HomeFolderSynchronisationRequestDocument) request)
				.getHomeFolderSynchronisationRequest();
		XmlObject[] xmlHomeFolders = homeFolderSynchronisationRequest.selectPath("./HomeFolder");
		// Response
		HomeFolderSynchronisationResponseDocument homeFolderSynchronisationResponseDocument = HomeFolderSynchronisationResponseDocument.Factory
				.newInstance();
		HomeFolderSynchronisationResponse homeFolderSynchronisationResponse = homeFolderSynchronisationResponseDocument
				.addNewHomeFolderSynchronisationResponse();
		// Analyse the request to build the response
		// Look into homefolders
		try
		{
			for (XmlObject xmlHomeFolder : xmlHomeFolders)
			{
				XmlCursor homeFolderCapdematIdCursor = xmlHomeFolder.newCursor();
				if (homeFolderCapdematIdCursor.toChild("HomeFolderCapdematId"))
				{
					homeFolderCapdematId = homeFolderCapdematIdCursor.getTextValue();
				}
				homeFolderCapdematIdCursor.dispose();
				XmlCursor homeFolderExternalIdCursor = xmlHomeFolder.newCursor();
				if (homeFolderExternalIdCursor.toChild("HomeFolderExternalId"))
				{
					homeFolderExternalId = homeFolderExternalIdCursor.getTextValue();
				}
				homeFolderExternalIdCursor.dispose();
				// Search for if the homefolder already exists : 2 ways
				if (StringUtils.isNotBlank(homeFolderCapdematId))
				{
					// homeFolderCapdematId check up
					homefolderFound = this.userSearchService.getHomeFolderById(Long.valueOf(homeFolderCapdematId));
				}
				else
				{
					// HomeFolderExternalId check up
					if (StringUtils.isNotBlank(homeFolderExternalId))
					{
						homefolderFound = this.externalHomeFolderService.getHomeFolderByHomeFolderExternalId(
								"CirilNetEnfance", homeFolderExternalId);
					}
				}
				// Individuals list send by ws
				List<Individual> individuals = populateHomeFolderIndividuals(xmlHomeFolder);
				if (homefolderFound != null)
				{
					if ((!homefolderFound.getState().equals(UserState.MODIFIED))
							&& (!homefolderFound.getState().equals(UserState.ARCHIVED))
							&& (!homefolderFound.getState().equals(UserState.INVALID)))
					{
						// historize la synchronisation externe
						userWorkflowService.historize(homefolderFound, UserAction.Type.SERVICE_EXTERNE,
								homefolderFound.getId(), null);
						// HomeFolder modification
						populateFoundHomeFolder(xmlHomeFolder, homefolderFound);
						this.deleteIndividualsFromHomeFolder(individuals, homefolderFound);
						// Create individual roles again
						this.upDateIndividualRole(individuals, homefolderFound);
						// Persist
						this.userWorkflowService.modify(homefolderFound);
					}
				}
				else
				{
					// HomeFolder creation
					homefolderFound = this.homeFolderCreation(individuals, homeFolderExternalId);
				}
				// Homefolder external id handling
				HomeFolderMapping homeFolderMappingCirilNetEnfance = this.externalHomeFolderService
						.getHomeFolderMapping("CirilNetEnfance", homefolderFound.getId());
				if (homeFolderMappingCirilNetEnfance == null)
				{
					// Homefolder mapping creation
					this.externalHomeFolderService.createHomeFolderMapping(new HomeFolderMapping("CirilNetEnfance",
							homefolderFound.getId(), homeFolderExternalId));
				}
				else
				{
					// Homefolder mapping modification
					homeFolderMappingCirilNetEnfance.setExternalId(homeFolderExternalId);
					homeFolderMappingCirilNetEnfance.setExternalServiceLabel("CirilNetEnfance");
					// Persist in DB
					this.externalHomeFolderService.modifyHomeFolderMapping(homeFolderMappingCirilNetEnfance);
				}
				// Individuals external id handling
				for (Individual individualSend : individuals)
				{
					for (Individual individual : homefolderFound.getIndividuals())
					{
						if (isMatchIndividuByName(individual, individualSend))
						{
							// This is the same individual
							this.externalHomeFolderService.setExternalId("CirilNetEnfance", homefolderFound.getId(),
									individual.getId(), individualSend.getExternalId());
						}
					}
				}
				homeFolders.add(homefolderFound);
			}
			// Response
			for (HomeFolder homeFolder : homeFolders)
			{
				String login = (this.userSearchService.getHomeFolderResponsible(homeFolder.getId())).getLogin();
				homeFolderSynchronisationResponse.setLogin(login);
				HomeFolderMappingType homeFolderMappingType = HomeFolderMappingType.Factory.newInstance();
				if (StringUtils.isNotBlank(homeFolderExternalId))
				{
					homeFolderMappingType.setExternalId(StringUtils.trim(homeFolderExternalId));
				}
				homeFolderMappingType.setCapDematId(homeFolder.getId());
				homeFolderSynchronisationResponse.setHomeFolderMapping(homeFolderMappingType);
				IndividualMappingType[] individualMappingTypes = new IndividualMappingType[homeFolder.getIndividuals()
						.size()];
				int i = 0;
				for (Individual individual : homeFolder.getIndividuals())
				{
					individualMappingTypes[i] = IndividualMappingType.Factory.newInstance();
					individualMappingTypes[i].setCapDematId(individual.getId());
					individualMappingTypes[i].setExternalId(individual.getExternalId());
					i++;
				}
				homeFolderSynchronisationResponse.setIndividualMappingArray(individualMappingTypes);
				if (homeFolder.getState().equals(UserState.MODIFIED))
				{
					homeFolderSynchronisationResponse.setResponseCode("ERROR01");
					homeFolderSynchronisationResponse
							.setResponseDetail("Le compte est déjà en état 'modifié'. "
									+ "Il doit être validé par un agent dans le backoffice de Capdemat avant de pouvoir être synchronisé.");
				}
				if (homeFolder.getState().equals(UserState.ARCHIVED))
				{
					homeFolderSynchronisationResponse.setResponseCode("ERROR02");
					homeFolderSynchronisationResponse
							.setResponseDetail("Le compte est en état 'Archivé'. Il ne peut être synchronisé.");
				}
				if (homeFolder.getState().equals(UserState.INVALID))
				{
					homeFolderSynchronisationResponse.setResponseCode("ERROR03");
					homeFolderSynchronisationResponse
							.setResponseDetail("Le compte est en état 'Invalide'. Le compte a quand même été synchronisé.");
				}
			}
		}
		catch (CvqException ex)
		{
			logger.error(ex.getMessage());
			ex.printStackTrace();
			homeFolderSynchronisationResponse.setResponseCode("ERROR01");
			homeFolderSynchronisationResponse.setResponseDetail("Erreur lors de la récupération des champs : "
					+ ex.getI18nKey());
		}
		catch (Exception e)
		{
			logger.fatal(e.getMessage());
			e.printStackTrace();
			homeFolderSynchronisationResponse.setResponseCode("ERROR01");
			homeFolderSynchronisationResponse.setResponseDetail("Erreur fatale lors de la synchronisation du compte : "
					+ e.getMessage());
		}
		logger.info("Response : " + homeFolderSynchronisationResponse.xmlText());
		return homeFolderSynchronisationResponse;
	}

	/**
	 * HomeFolder population
	 * 
	 * @param xmlHomeFolder
	 * @return List<Individual> Populated individuals
	 */
	private List<Individual> populateHomeFolderIndividuals(XmlObject xmlHomeFolder) throws CvqException
	{
		logger.debug("HomeFolder creation : " + xmlHomeFolder);
		// Individuals list returned
		List<Individual> individuals = new ArrayList<Individual>();
		if (xmlHomeFolder != null)
		{
			List<Adult> adults = new ArrayList<Adult>();
			List<Child> children = new ArrayList<Child>();
			XmlObject[] xmlIndividuals = xmlHomeFolder.selectPath("./Individual");
			// Look into individuals from the homefolder
			for (XmlObject xmlIndividual : xmlIndividuals)
			{
				XmlCursor adultChildCursor = xmlIndividual.newCursor();
				if (adultChildCursor.toChild("Adult"))
				{
					Adult adult = new Adult();
					XmlObject[] xmlAdults = xmlIndividual.selectPath("./Adult");
					// Look into adults from the <individual> (according to the
					// xsd file, we only have 1 adult in individual)
					for (XmlObject xmlAdult : xmlAdults)
					{
						adults.add(this.populateAdult(adult, xmlAdult));
						adult.setIndividualRoles(this.populateIndividualRoles(xmlAdult));
					}
				}
				else if (adultChildCursor.toChild("Child"))
				{
					Child child = new Child();
					XmlObject[] xmlChilds = xmlIndividual.selectPath("./Child");
					for (XmlObject xmlChild : xmlChilds)
					{
						children.add(this.populateChild(child, xmlChild));
						child.setIndividualRoles(this.populateIndividualRoles(xmlChild));
					}
				}
				adultChildCursor.dispose();
			}
			if (children != null)
			{
				individuals.addAll(children);
			}
			if (adults != null)
			{
				individuals.addAll(adults);
			}
		}
		return individuals;
	}

	/**
	 * Found HomeFolder population
	 * 
	 * @param xmlHomeFolder
	 * @param homeFolder
	 *            HomeFolder to modify
	 * @return List<Individual> Individuals created
	 */
	private List<Individual> populateFoundHomeFolder(XmlObject xmlHomeFolder, HomeFolder homeFolder)
			throws CvqException
	{
		logger.debug("HomeFolder modification : " + xmlHomeFolder);
		//List<Individual> individualsCreated = new ArrayList<Individual>();
		List<Individual> individuals = new ArrayList<Individual>();
		if (xmlHomeFolder != null && homeFolder != null)
		{
			XmlObject[] xmlIndividuals = xmlHomeFolder.selectPath("./Individual");
			// Look into individuals from the homefolder
			for (XmlObject xmlIndividual : xmlIndividuals)
			{
				XmlCursor adultChildCursor = xmlIndividual.newCursor();
				if (adultChildCursor.toChild("Adult"))
				{
					XmlObject[] xmlAdults = xmlIndividual.selectPath("./Adult");
					// Look into adults from the <individual> (according to the
					// xsd file, we only have 1 adult in individual)
					for (XmlObject xmlAdult : xmlAdults)
					{
						Adult adult = null;
						String individualCapdematId = this.getXmlTagTextValue(xmlAdult, "IndividualCapdematId");
						// Search for the adult in the HomeFolder by its
						// individualCapdematId
						if (StringUtils.isNotBlank(individualCapdematId))
						{
							Long id = Long.parseLong(individualCapdematId);
							if (id != null)
							{
								// Adult to modify
								adult = this.userSearchService.getAdultById(id);
							}
						}
						// Search for the adult in the HomeFolder by its
						// IndividualExternalId
						else
						{
							String individualExternalId = this.getXmlTagTextValue(xmlAdult, "IndividualExternalId");
							if (StringUtils.isNotBlank(individualExternalId))
							{
								HomeFolderMapping homeFolderMappingCirilNetEnfance = this.externalHomeFolderService
										.getHomeFolderMapping("CirilNetEnfance", homeFolder.getId());
								for (IndividualMapping individualMapping : homeFolderMappingCirilNetEnfance
										.getIndividualsMappings())
								{
									if (adult == null && individualExternalId.equals(individualMapping.getExternalId()))
									{
										adult = (Adult) this.userSearchService.getAdultById(individualMapping
												.getIndividualId());
									}
								}
							}
						}
						// cas particulier ni IndividualCapdematId côté CNE, ni
						// IndividualExternalId côté CapDemat
						if (adult == null || adult.getId() == null)
						{
							adult = ultimateAdultMatchWithHomeFolder(xmlAdult, homeFolder);
						}
						else
						{
							this.populateAdult(adult, xmlAdult);
						}
						if (adult == null || adult.getId() == null)
						{
							adult = new Adult();
							this.populateAdult(adult, xmlAdult);
							// Adult to create
							//individualsCreated.add(adult);
							try
							{
								this.userWorkflowService.add(homeFolder, adult, true);
							}
							catch (CvqException e)
							{
								logger.fatal(e.getMessage());
							}
						}
						individuals.add(adult);
					}
				}
				else if (adultChildCursor.toChild("Child"))
				{
					XmlObject[] xmlChilds = xmlIndividual.selectPath("./Child");
					for (XmlObject xmlChild : xmlChilds)
					{
						Child child = null;
						String individualCapdematId = this.getXmlTagTextValue(xmlChild, "IndividualCapdematId");
						// Search for the child in the HomeFolder by its
						// individualCapdematId
						if (individualCapdematId != null)
						{
							Long id = Long.parseLong(individualCapdematId);
							if (id != null)
							{
								// Child to modify
								child = this.userSearchService.getChildById(id);
							}
						}
						// Search for the adult in the HomeFolder by its
						// IndividualExternalId
						else
						{
							String individualExternalId = this.getXmlTagTextValue(xmlChild, "IndividualExternalId");
							if (StringUtils.isNotBlank(individualExternalId))
							{
								HomeFolderMapping homeFolderMappingCirilNetEnfance = this.externalHomeFolderService
										.getHomeFolderMapping("CirilNetEnfance", homeFolder.getId());
								for (IndividualMapping individualMapping : homeFolderMappingCirilNetEnfance
										.getIndividualsMappings())
								{
									if (child == null && individualExternalId.equals(individualMapping.getExternalId()))
									{
										child = (Child) this.userSearchService.getChildById(individualMapping
												.getIndividualId());
									}
								}
							}
						}
						// cas particulier ni IndividualCapdematId côté CNE, ni
						// IndividualExternalId côté CapDemat
						if (child == null || child.getId() == null)
						{
							child = ultimateChildMatchWithHomeFolder(xmlChild, homeFolder);
						}
						else
						{
							this.populateChild(child, xmlChild);
						}
						if (child == null || child.getId() == null)
						{
							child = new Child();
							this.populateChild(child, xmlChild);
							// Child to create
							//individualsCreated.add(child);
							try
							{
								this.userWorkflowService.add(homeFolder, child);
							}
							catch (CvqException e)
							{
								logger.fatal(e.getMessage());
							}
						}
						individuals.add(child);
					}
				}
				adultChildCursor.dispose();
			}
		}
		return individuals;
	}

	/**
	 * HomeFolder creation
	 * 
	 * @param individuals
	 * @return homeFolderNew HomeFolder created
	 */
	private HomeFolder homeFolderCreation(List<Individual> individuals, String homeFolderExternalId)
	{
		logger.debug("homeFolderCreation");
		Adult adultResponsible = this.findHomeFolderResponsible(individuals);
		HomeFolder homeFolderNew = new HomeFolder();
		homeFolderNew.setExternalId(homeFolderExternalId);
		// HomeFolder creation
		if (adultResponsible != null)
		{
			try
			{
				homeFolderNew = this.userWorkflowService.createOnly(adultResponsible);
			}
			catch (CvqException e)
			{
				logger.fatal("Error during HomeFolder and HomeFolder responsible creation : " + e.getMessage());
			}
		}
		else
		{
			logger.debug("No HomeFolder responsible found");
		}
		// Sort individuals in adult ou child lists
		List<Adult> adults = new ArrayList<Adult>();
		List<Child> children = new ArrayList<Child>();
		for (Individual individual : individuals)
		{
			if (Adult.class.isInstance(individual))
			{
				adults.add((Adult) individual);
			}
			else
			{
				children.add((Child) individual);
			}
		}
		// Add adults in HomeFolder
		for (Adult adult : adults)
		{
			try
			{
				if (adultResponsible != adult)
				{
					this.userWorkflowService.add(homeFolderNew, adult, false);
				}
			}
			catch (CvqException e)
			{
				logger.fatal("Error when adding Adult in HomeFolder : " + e.getMessage());
			}
		}
		// Add children in HomeFolder
		for (Child child : children)
		{
			try
			{
				Set<IndividualRole> individualRoles = new HashSet<IndividualRole>(child.getIndividualRoles());
				child.getIndividualRoles().clear();
				this.userWorkflowService.add(homeFolderNew, child);
				for (IndividualRole individualRole : individualRoles)
				{
					if (individualRole.getIndividualId() != null)
					{
						// Child has roles
						for (Adult adult : adults)
						{
							if (adult.getExternalId() != null)
							{
								// Adult is identifiable
								if (adult.getExternalId().equals(String.valueOf(individualRole.getIndividualId())))
								{
									// Build individual role
									this.userWorkflowService.link(adult, child,
											Collections.singleton(individualRole.getRole()));
								}
							}
						}
					}
				}
			}
			catch (CvqException e)
			{
				logger.fatal("Error when adding Child in HomeFolder : " + e.getMessage());
			}
		}
		return homeFolderNew;
	}

	/**
	 * Populate an Adult object with xml information
	 * 
	 * @param adult
	 *            object which has to be populated
	 * @param xmlAdult
	 *            information to populate
	 * @return adult
	 */
	private Adult populateAdult(Adult adult, XmlObject xmlAdult) throws CvqException
	{
		if (xmlAdult != null)
		{
			adult = (Adult) this.populateIndividual(adult, xmlAdult);
			adult.setTitle(TitleType.forString(StringUtils.upperCase(this.getXmlTagTextValue(xmlAdult, "Title"))));
			String maidenName = this.getXmlTagTextValue(xmlAdult, "MaidenName");
			if (maidenName != null && maidenName.trim().equals("")) maidenName = null;
			adult.setMaidenName(maidenName);
			adult.setFamilyStatus(FamilyStatusType.forString(StringUtils.upperCase(this.getXmlTagTextValue(xmlAdult,
					"FamilyStatus"))));
			String homePhone = this.getXmlTagTextValue(xmlAdult, "HomePhone");
			if (homePhone != null && homePhone.trim().equals("")) homePhone = null;
			if (homePhone != null && !homePhone.matches("^0[1-9][0-9]{8}$")) throw new CvqException(
					"Le format du téléphone personnel ne correspond pas au format attendu : 0[1-9][0-9]{8}");
			adult.setHomePhone(homePhone);
			String officePhone = this.getXmlTagTextValue(xmlAdult, "OfficePhone");
			if (officePhone != null && officePhone.trim().equals("")) officePhone = null;
			if (officePhone != null && !officePhone.matches("^0[1-9][0-9]{8}$")) throw new CvqException(
					"Le format du téléphone de bureau ne correspond pas au format attendu : 0[1-9][0-9]{8}");
			adult.setOfficePhone(officePhone);
			String mobilePhone = this.getXmlTagTextValue(xmlAdult, "MobilePhone");
			if (mobilePhone != null && mobilePhone.trim().equals("")) mobilePhone = null;
			if (mobilePhone != null && !mobilePhone.matches("^0[67][0-9]{8}$")) throw new CvqException(
					"Le format du téléphone mobile ne correspond pas au format attendu : 0[67][0-9]{8}");
			adult.setMobilePhone(mobilePhone);
			String email = this.getXmlTagTextValue(xmlAdult, "Email");
			if (email != null && email.trim().equals("")) email = null;
			if (email != null && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) throw new CvqException(
					"Le format du mail ne correspond pas au format attendu : ^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
			adult.setEmail(email);
			
			String cfbn = this.getXmlTagTextValue(xmlAdult, "Cfbn");
			if (cfbn != null && cfbn.trim().equals("")) cfbn = null;
			if (cfbn != null && !cfbn.matches("^[0-9A-Za-z-_. ]{0,20}$")) throw new CvqException(
					"Le format du numéro allocataire CAF ne correspond pas au format attendu : ^[0-9A-Za-z-_. ]{0,20}$");
			adult.setCfbn(cfbn);
			
			String profession = this.getXmlTagTextValue(xmlAdult, "Profession");
			if (profession != null && profession.trim().equals("")) profession = null;
			adult.setProfession(profession);
			adult.setExternalId(this.getXmlTagTextValue(xmlAdult, "IndividualExternalId"));
			// Default password, don't modify it
			// TODO : Enable to personnalized it
			if (StringUtils.isBlank(adult.getPassword()))
			{
				adult.setPassword("bienvenue");
			}
			if (StringUtils.isBlank(adult.getAnswer()))
			{
				adult.setAnswer("Non renseigné");
			}
			if (StringUtils.isBlank(adult.getQuestion()))
			{
				adult.setQuestion("Quel est votre loisir préféré ?");
			}
			XmlCursor xmlCursor = xmlAdult.newCursor();
			if (xmlCursor.toChild("Address"))
			{
				Address address = adult.getAddress();
				adult.setAddress(this.populateAdress(address, xmlCursor.getObject()));
			}
			xmlCursor.dispose();
		}
		return adult;
	}

	/**
	 * Populate a Child object with xml information
	 * 
	 * @param child
	 *            object which has to be populated
	 * @param xmlChild
	 *            information to populate
	 * @return child
	 */
	private Child populateChild(Child child, XmlObject xmlChild)
	{
		if (xmlChild != null)
		{
			try
			{
				child = (Child) this.populateIndividual(child, xmlChild);
				String born = this.getXmlTagTextValue(xmlChild, "Born");
				child.setBorn("true".equals(born));
				child.setSex(SexType.forString(this.getXmlTagTextValue(xmlChild, "Sex")));
				child.setExternalId(this.getXmlTagTextValue(xmlChild, "IndividualExternalId"));
				XmlCursor xmlCursor = xmlChild.newCursor();
				if (xmlCursor.toChild("ChildInformationSheet"))
				{
					ChildInformationSheet childInformationSheet = child.getChildInformationSheet();
					child.setChildInformationSheet(this.populateChildInformationSheet(childInformationSheet,
							xmlCursor.getObject()));
				}
				xmlCursor.dispose();
			}
			catch (Exception e)
			{
				logger.fatal(e.getMessage());
			}
		}
		return child;
	}

	/**
	 * Populate an individual object with xml information
	 * 
	 * @param individual
	 *            object which has to be populated
	 * @param xmlIndividual
	 *            information to populate
	 * @return individual populated
	 */
	private Individual populateIndividual(Individual individual, XmlObject xmlIndividual)
	{
		if (xmlIndividual != null)
		{
			try
			{
				String valeur = null;
				individual.setExternalId(this.getXmlTagTextValue(xmlIndividual, "IndividualExternalId"));
				individual.setFirstName(this.getXmlTagTextValue(xmlIndividual, "FirstName"));
				individual.setLastName(this.getXmlTagTextValue(xmlIndividual, "LastName"));
				valeur = this.getXmlTagTextValue(xmlIndividual, "FirstName2");
				if (StringUtils.isNotBlank(valeur))
				{
					individual.setFirstName2(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlIndividual, "FirstName3");
				if (StringUtils.isNotBlank(valeur))
				{
					individual.setFirstName3(valeur);
				}
				individual.setCreationDate(this.getXmlTagTextValue(xmlIndividual, "CreationDate"));
				// Birth date
				Date birthDate = null;
				try
				{
					String date = this.getXmlTagTextValue(xmlIndividual, "BirthDate");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						birthDate = (Date) formatter.parse(date);
					}
				}
				catch (ParseException e)
				{
					logger.error("Error with child birthdate : " + e.getMessage());
				}
				individual.setBirthDate(birthDate);
				XmlCursor birthPlaceXmlCursor = xmlIndividual.newCursor();
				if (birthPlaceXmlCursor.toChild("BirthPlace"))
				{
					this.populateBirthPlace(individual, birthPlaceXmlCursor.getObject());
				}
				birthPlaceXmlCursor.dispose();
			}
			catch (Exception e)
			{
				logger.fatal(e.getMessage());
			}
		}
		return individual;
	}

	/**
	 * Populate individual roles
	 * 
	 * @param xmlIndividual
	 * @return individualRoles updated
	 */
	private Set<IndividualRole> populateIndividualRoles(XmlObject xmlIndividual)
	{
		Set<IndividualRole> individualRoles = new HashSet<IndividualRole>();
		if (xmlIndividual != null)
		{
			XmlObject[] xmlRoles = xmlIndividual.selectPath("./Role");
			for (XmlObject individualRoleXmlObject : xmlRoles)
			{
				IndividualRole individualRole = new IndividualRole();
				individualRoles.add(this.populateIndividualRole(individualRole, individualRoleXmlObject));
			}
		}
		return individualRoles;
	}

	/**
	 * Populate an Address object with xml information
	 * 
	 * @param address
	 *            object which has to be populated
	 * @param xmlAddress
	 *            information to populate
	 * @return address populated
	 */
	private Address populateAdress(Address address, XmlObject xmlAddress)
	{
		if (xmlAddress != null)
		{
			try
			{
				if (address == null)
				{
					address = new Address();
				}
				address.setAdditionalDeliveryInformation(this.getXmlTagTextValue(xmlAddress,
						"AdditionalDeliveryInformation"));
				address.setAdditionalGeographicalInformation(this.getXmlTagTextValue(xmlAddress,
						"AdditionalGeographicalInformation"));
				address.setStreetNumber(this.getXmlTagTextValue(xmlAddress, "StreetNumber"));
				address.setStreetName(this.getXmlTagTextValue(xmlAddress, "StreetName"));
				address.setPostalCode(this.getXmlTagTextValue(xmlAddress, "PostalCode"));
				address.setCity(this.getXmlTagTextValue(xmlAddress, "City"));
			}
			catch (Exception e)
			{
				logger.fatal(e.getMessage());
			}
		}
		return address;
	}

	/**
	 * 
	 * 
	 * @param childInformationSheet
	 * @param xmlChildInformationSheet
	 * @return
	 * 
	 */
	private ChildInformationSheet populateChildInformationSheet(ChildInformationSheet childInformationSheet,
			XmlObject xmlChildInformationSheet)
	{
		if (xmlChildInformationSheet != null)
		{
			try
			{
				if (childInformationSheet == null)
				{
					childInformationSheet = new ChildInformationSheet();
				}
				// Régimes alimentaires
				XmlCursor dietsXmlCursor = xmlChildInformationSheet.newCursor();
				if (dietsXmlCursor.toChild("Diets"))
				{
					HashSet<Diet> dietSet = new HashSet<Diet>();
					XmlObject[] xmlDiets = dietsXmlCursor.getObject().selectPath("./Type");
					for (XmlObject xmlDiet : xmlDiets)
					{
						String valeur;
						XmlOptions opts = new XmlOptions();
						valeur = xmlDiet.xmlText(opts);
						if (valeur != null)
						{
							valeur = valeur.trim();
							valeur = valeur.replaceAll("<xml-fragment>", "");
							valeur = valeur.replaceAll("</xml-fragment>", "");
							if (DietEnum.valueOf(valeur) != null)
							{
								Diet diet = new Diet();
								diet.setType(DietEnum.valueOf(valeur));
								dietSet.add(diet);
							}
						}
					}
					if (!dietSet.isEmpty())
					{
						childInformationSheet.setDiets(dietSet);
					}
					else
					{
						childInformationSheet.setDiets(null);
					}
				}
				dietsXmlCursor.dispose();
				String valeur = null;
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "Allergie");
				if (valeur != null)
				{
					childInformationSheet.setAllergie(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationDroitImage");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationDroitImage("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationHospitalisation");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationHospitalisation("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationMaquillage");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationMaquillage("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationRentrerSeul");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationRentrerSeul("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationTransporterTransportCommun");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationTransporterTransportCommun("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "AutorisationTransporterVehiculeMunicipal");
				if (valeur != null)
				{
					childInformationSheet.setAutorisationTransporterVehiculeMunicipal("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "DifficulteSante");
				if (valeur != null)
				{
					childInformationSheet.setDifficulteSante(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "EmailEnfant");
				if (valeur != null)
				{
					childInformationSheet.setEmailEnfant(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "NomMedecinTraitant");
				if (valeur != null)
				{
					childInformationSheet.setNomMedecinTraitant(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "NomOrganismeAssurance");
				if (valeur != null)
				{
					childInformationSheet.setNomOrganismeAssurance(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "NumeroOrganismeAssurance");
				if (valeur != null)
				{
					childInformationSheet.setNumeroOrganismeAssurance(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "ProjetAccueilIndividualise");
				if (valeur != null)
				{
					childInformationSheet.setProjetAccueilIndividualise("true".equals(valeur));
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "RecommandationParent");
				if (valeur != null)
				{
					childInformationSheet.setRecommandationParent(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "TelephoneMedecinTraitant");
				if (valeur != null)
				{
					childInformationSheet.setTelephoneMedecinTraitant(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "TelephonePortable");
				if (valeur != null)
				{
					childInformationSheet.setTelephonePortable(valeur);
				}
				valeur = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinAutre");
				if (valeur != null)
				{
					childInformationSheet.setVaccinAutre(valeur);
				}
				// VaccinBcg
				Date dateVaccin = null;
				try
				{
					String date = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinBcg");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						dateVaccin = (Date) formatter.parse(date);
					}
					childInformationSheet.setVaccinBcg(dateVaccin);
					dateVaccin = null;
				}
				catch (ParseException e)
				{
					logger.error("Error with VaccinBcg : " + e.getMessage());
				}
				// VaccinDtPolio
				try
				{
					String date = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinDtPolio");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						dateVaccin = (Date) formatter.parse(date);
					}
					childInformationSheet.setVaccinDtPolio(dateVaccin);
					dateVaccin = null;
				}
				catch (ParseException e)
				{
					logger.error("Error with VaccinDtPolio : " + e.getMessage());
				}
				// VaccinInjectionSerum
				try
				{
					String date = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinInjectionSerum");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						dateVaccin = (Date) formatter.parse(date);
					}
					childInformationSheet.setVaccinInjectionSerum(dateVaccin);
					dateVaccin = null;
				}
				catch (ParseException e)
				{
					logger.error("Error with VaccinInjectionSerum : " + e.getMessage());
				}
				// VaccinRor
				try
				{
					String date = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinRor");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						dateVaccin = (Date) formatter.parse(date);
					}
					childInformationSheet.setVaccinRor(dateVaccin);
					dateVaccin = null;
				}
				catch (ParseException e)
				{
					logger.error("Error with VaccinRor : " + e.getMessage());
				}
				// VaccinTetracoqPentacoq
				try
				{
					String date = this.getXmlTagTextValue(xmlChildInformationSheet, "VaccinTetracoqPentacoq");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if (StringUtils.isNotBlank(date))
					{
						dateVaccin = (Date) formatter.parse(date);
					}
					childInformationSheet.setVaccinTetracoqPentacoq(dateVaccin);
					dateVaccin = null;
				}
				catch (ParseException e)
				{
					logger.error("Error with VaccinTetracoqPentacoq : " + e.getMessage());
				}
			}
			catch (Exception e)
			{
				logger.fatal(e.getMessage());
			}
		}
		return childInformationSheet;
	}

	/**
	 * Populate individual role
	 * 
	 * @param individualRole
	 *            to populate
	 * @param xmlIndividualRole
	 *            information to populate
	 * @return individual roles populated
	 */
	private IndividualRole populateIndividualRole(IndividualRole individualRole, XmlObject xmlIndividualRole)
	{
		try
		{
			if (xmlIndividualRole != null)
			{
				String individualId = this.getXmlTagTextValue(xmlIndividualRole, "IndividualExternalId");
				if (individualId != null)
				{
					individualRole.setIndividualId(Long.valueOf(individualId));
				}
				individualRole.setIndividualName(this.getXmlTagTextValue(xmlIndividualRole, "IndividualName"));
				individualRole.setRole(RoleType.valueOf(StringUtils.upperCase(this.getXmlTagTextValue(
						xmlIndividualRole, "RoleName"))));
			}
		}
		catch (Exception e)
		{
			logger.fatal(e.getMessage());
		}
		return individualRole;
	}

	/**
	 * Populate birth place
	 * 
	 * @param individual
	 *            to populate
	 * @param xmlIndividual
	 *            information to populate
	 * @return individual populated
	 */
	private Individual populateBirthPlace(Individual individual, XmlObject xmlIndividual)
	{
		try
		{
			if (xmlIndividual != null)
			{
				individual.setBirthCity(this.getXmlTagTextValue(xmlIndividual, "City"));
				individual.setBirthPostalCode(this.getXmlTagTextValue(xmlIndividual, "PostalCode"));
			}
		}
		catch (Exception e)
		{
			logger.fatal(e.getMessage());
		}
		return individual;
	}

	/**
	 * Delete from the HomeFolder, individuals who are not send by ws
	 * 
	 * @param individualsSendByWs
	 * @param homeFolder
	 * @return true if individuals deleted
	 */
	private boolean deleteIndividualsFromHomeFolder(List<Individual> individualsSendByWs, HomeFolder homeFolder)
	{
		List<Long> individualsDeleted = new ArrayList<Long>();
		List<Individual> individuals = homeFolder.getIndividuals();
		// collect individuals ids to not iterate on individuals directly
		// coz' it throws ConcurrentModificationException in called methods
		// (changeState)
		if (individuals != null)
		{
			List<Long> ids = new ArrayList<Long>();
			for (Individual i : individuals)
			{
				ids.add(i.getId());
			}
			for (Long id : ids)
			{
				Individual individual = this.userSearchService.getById(id);
				if (individual != null)
				{
					boolean found = false;
					for (Individual individualSendByWs : individualsSendByWs)
					{
						if (isMatchIndividuByName(individual, individualSendByWs))
						{
							found = true;
						}
					}
					// Individual not found in individuals list send by ws
					// Have to be deleted
					if (!found)
					{
						individualsDeleted.add(individual.getId());
					}
				}
			}
		}
		// Use a list because it's impossible to a for each with a list and to
		// delete element from this list in the same time
		// Exception : Server java.util.ConcurrentModificationException
		try
		{
			for (Long id : individualsDeleted)
			{
				this.userWorkflowService.delete(this.userSearchService.getById(id));
			}
		}
		catch (Exception e)
		{
			logger.fatal(e.getMessage());
		}
		return individualsDeleted != null && individualsDeleted.size() > 0;
	}

	private Child ultimateChildMatchWithHomeFolder(XmlObject xmlChild, HomeFolder homeFolder)
	{
		for (Individual individual : homeFolder.getIndividuals())
		{
			if (individual instanceof Child)
			{
				Child child = isMatchIndividuByName(xmlChild, individual);
				if (child != null) return child;
			}
		}
		return null;
	}

	private Child isMatchIndividuByName(XmlObject xmlChild, Individual individualCapDemat)
	{
		Child child = new Child();
		if (isMatchIndividuByName(populateChild(child, xmlChild), individualCapDemat))
		{
			child.setId(individualCapDemat.getId());
			return child;
		}
		return null;
	}

	private Adult ultimateAdultMatchWithHomeFolder(XmlObject xmlAdult, HomeFolder homeFolder) throws CvqException
	{
		for (Individual individual : homeFolder.getIndividuals())
		{
			if (individual instanceof Adult)
			{
				Adult adult = isMatchAdultByName(xmlAdult, individual);
				if (adult != null) return adult;
			}
		}
		return null;
	}

	private Adult isMatchAdultByName(XmlObject xmlAdult, Individual individualCapDemat) throws CvqException
	{
		Adult adult = new Adult();
		if (isMatchIndividuByName(populateAdult(adult, xmlAdult), individualCapDemat))
		{
			adult.setId(individualCapDemat.getId());
			return adult;
		}
		return null;
	}

	private boolean isMatchIndividuByName(Individual individual, Individual individualSendByWs)
	{
		if (individual instanceof Child && individualSendByWs instanceof Child)
		{
			// à naitre
			if (individual.getLastName().toLowerCase().trim()
					.equals(individualSendByWs.getLastName().toLowerCase().trim())
					&& !((Child) individual).isBorn()
					&& !((Child) individualSendByWs).isBorn()
					&& ((Child) individual).getBirthDate().compareTo(((Child) individualSendByWs).getBirthDate()) == 0) return true;
			else if (individual.getFirstName().toLowerCase().trim()
					.equals(individualSendByWs.getFirstName().toLowerCase().trim())
					&& individual.getLastName().toLowerCase().trim()
							.equals(individualSendByWs.getLastName().toLowerCase().trim())
					&& ((Child) individual).isBorn()
					&& ((Child) individualSendByWs).isBorn()
					&& ((Child) individual).getBirthDate().compareTo(((Child) individualSendByWs).getBirthDate()) == 0) return true;
		}
		else if ((individualSendByWs.getFirstName() != null && individual.getFirstName() != null && individual
				.getFirstName().toLowerCase().trim().equals(individualSendByWs.getFirstName().toLowerCase().trim()))
				&& (individual.getLastName().toLowerCase().trim().equals(individualSendByWs.getLastName().toLowerCase()
						.trim()))) return true;
		return false;
	}

	/**
	 * Update individual roles
	 * 
	 * @return true if individual roles has been updated
	 */
	private boolean upDateIndividualRole(List<Individual> individualsSendByWs, HomeFolder homeFolder)
	{
		boolean result = false;
		for (Child child : this.userSearchService.getChildren(homeFolder.getId()))
		{
			// For each child from the HomeFolder
			Child childSendByWs = null;
			// Search for the child in the individuals list send by the ws
			for (Individual individualSendByWs : individualsSendByWs)
			{
				if ((Child.class.isInstance(individualSendByWs)) && (child.getExternalId() != null)
						&& (child.getExternalId().equals(individualSendByWs.getExternalId())))
				{
					// Child found
					childSendByWs = (Child) individualSendByWs;
				}
			}
			if (childSendByWs != null && childSendByWs.getIndividualRoles() != null)
			{
				// Pour chaque rôle de l'enfant :
				for (IndividualRole individualRole : childSendByWs.getIndividualRoles())
				{
					// On recherche l'adulte qui a un role sur l'enfant
					if (individualRole.getIndividualId() != null)
					{
						for (Adult adult : this.userSearchService.getAdults(homeFolder.getId()))
						{
							if ((adult.getExternalId() != null)
									&& (adult.getExternalId().equals(String.valueOf(individualRole.getIndividualId()))))
							{
								// Build individual role
								try
								{
									this.userWorkflowService.link(adult, child,
											Collections.singleton(individualRole.getRole()));
									result = true;
								}
								catch (Exception e)
								{
									logger.fatal(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Find responsible from an individuals list
	 * 
	 * @param individuals
	 *            Individuals list
	 * @return HomeFolder responsible or null if not found
	 */
	private Adult findHomeFolderResponsible(List<Individual> individuals)
	{
		Adult adultFound = null;
		for (Individual individual : individuals)
		{
			for (IndividualRole individualRole : individual.getIndividualRoles())
			{
				if (individualRole.getRole() == RoleType.HOME_FOLDER_RESPONSIBLE)
				{
					logger.debug("HomeFolder responsible found");
					// Delete HomeFolder responsible role. It'll be created
					// later again
					individual.getIndividualRoles().remove(individualRole);
					adultFound = (Adult) individual;
				}
			}
		}
		return adultFound;
	}

	/**
	 * Return XML tag text
	 * 
	 * @param xmlObject
	 * @param name
	 *            XML tag name
	 * @return Xml tag text, or null if not found
	 */
	private String getXmlTagTextValue(XmlObject xmlObject, String name)
	{
		String returnedValue = null;
		if (xmlObject != null && StringUtils.isNotBlank(name))
		{
			XmlCursor xmlCursor = xmlObject.newCursor();
			if (xmlCursor.toChild(name))
			{
				returnedValue = "";
				if (StringUtils.isNotBlank(xmlCursor.getTextValue()))
				{
					returnedValue = xmlCursor.getTextValue();
				}
			}
			// XMLBeans doc : Call dispose() because simply letting a cursor go
			// out of scope and having
			// the garbage collector attempt to reclaim it may not produce
			// desirable performance.
			xmlCursor.dispose();
		}
		return returnedValue;
	}

	/**
	 * @return the userSearchService
	 */
	public IUserSearchService getUserSearchService()
	{
		return userSearchService;
	}

	/**
	 * @param userSearchService
	 *            the userSearchService to set
	 */
	public void setUserSearchService(IUserSearchService userSearchService)
	{
		this.userSearchService = userSearchService;
	}

	/**
	 * @return the userWorkflowService
	 */
	public IUserWorkflowService getUserWorkflowService()
	{
		return userWorkflowService;
	}

	/**
	 * @param userWorkflowService
	 *            the userWorkflowService to set
	 */
	public void setUserWorkflowService(IUserWorkflowService userWorkflowService)
	{
		this.userWorkflowService = userWorkflowService;
	}

	/**
	 * @return the externalHomeFolderService
	 */
	public IExternalHomeFolderService getExternalHomeFolderService()
	{
		return externalHomeFolderService;
	}

	/**
	 * @param externalHomeFolderService
	 *            the externalHomeFolderService to set
	 */
	public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService)
	{
		this.externalHomeFolderService = externalHomeFolderService;
	}
}
