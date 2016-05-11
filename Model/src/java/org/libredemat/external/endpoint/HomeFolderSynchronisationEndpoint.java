/**
 * HomeFolder synchronisation endpoint
 */
package org.libredemat.external.endpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.libredemat.authentication.IAuthenticationService;
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
import org.libredemat.util.logging.impl.Log;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

import fr.capwebct.capdemat.homeFolderSynchronisation.AdultType;
import fr.capwebct.capdemat.homeFolderSynchronisation.ChildInformationSheetType;
import fr.capwebct.capdemat.homeFolderSynchronisation.ChildType;
import fr.capwebct.capdemat.homeFolderSynchronisation.FamilyStatusType.Enum;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderMappingType;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationRequestDocument;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationRequestDocument.HomeFolderSynchronisationRequest;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationResponseDocument;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderSynchronisationResponseDocument.HomeFolderSynchronisationResponse;
import fr.capwebct.capdemat.homeFolderSynchronisation.HomeFolderType;
import fr.capwebct.capdemat.homeFolderSynchronisation.IndividualMappingType;
import fr.capwebct.capdemat.homeFolderSynchronisation.IndividualRoleType;
import fr.capwebct.capdemat.homeFolderSynchronisation.SubjectType;

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
    IUserSearchService userSearchService;
    IUserWorkflowService userWorkflowService;
    IExternalHomeFolderService externalHomeFolderService;
    IAuthenticationService authenticationService;

    /**
     * Constructor
     */
    public HomeFolderSynchronisationEndpoint(Marshaller marshaller)
    {
        super(marshaller);
    }

    String xqNamespace = "declare namespace hom='http://www.libredemat.org/homeFolderSynchronisation';";
    
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
        HomeFolderType[] homeFolderTypes = homeFolderSynchronisationRequest.getHomeFolderArray();

        HomeFolderSynchronisationResponseDocument homeFolderSynchronisationResponseDocument = HomeFolderSynchronisationResponseDocument.Factory
                .newInstance();
        HomeFolderSynchronisationResponse homeFolderSynchronisationResponse = homeFolderSynchronisationResponseDocument
                .addNewHomeFolderSynchronisationResponse();

        try
        {
            for (HomeFolderType homeFolderType : homeFolderTypes)
            {
                homeFolderCapdematId = homeFolderType.getHomeFolderCapdematId();
                homeFolderExternalId = homeFolderType.getHomeFolderExternalId();
                boolean isNewHomeFolder = false;

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
                List<Individual> individuals = populateHomeFolderIndividuals(homeFolderType);
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
                        populateFoundHomeFolder(homeFolderType, homefolderFound);
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
                    isNewHomeFolder = true;
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
                        if (!UserState.ARCHIVED.equals(individual.getState()) && isMatchIndividuByName(individual, individualSend))
                        {
                            // This is the same individual
                            this.externalHomeFolderService.setExternalId("CirilNetEnfance", homefolderFound.getId(),
                                    individual.getId(), individualSend.getExternalId());
                        }
                    }
                }
                homeFolders.add(homefolderFound);
                if (isNewHomeFolder) {
                    Adult homeFolderResponsible = userSearchService.getHomeFolderResponsible(homefolderFound.getId());
                    String externalId =
                            externalHomeFolderService.getIndividualMapping(homeFolderResponsible, "CirilNetEnfance").getExternalId();
                    String newPassword = "bienvenue" + homeFolderExternalId;
                    homeFolderResponsible.setPassword(authenticationService.encryptPassword(newPassword));
                    Log.importedHomeFolderToCsv(homeFolderResponsible, newPassword, externalId);
                }
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
            logger.error("Got an exception");
            logger.error("Failure", e);
            logger.fatal(e.getMessage());
            e.printStackTrace();
            homeFolderSynchronisationResponse.setResponseCode("ERROR01");
            homeFolderSynchronisationResponse.setResponseDetail("Erreur fatale lors de la synchronisation du compte : "
                    + e.getMessage());
        }
        logger.info("Response : " + homeFolderSynchronisationResponse.xmlText());
        return homeFolderSynchronisationResponse;
    }

    private List<Individual> populateHomeFolderIndividuals(HomeFolderType homeFolder) throws CvqException
    {
        logger.debug("HomeFolder creation : " + homeFolder);
        // Individuals list returned
        List<Individual> individuals = new ArrayList<Individual>();
        if (homeFolder != null)
        {
            List<Adult> adults = new ArrayList<Adult>();
            List<Child> children = new ArrayList<Child>();
            
            // Look into individuals from the homefolder
            for (SubjectType individual : homeFolder.getIndividualArray())
            {
                if (individual.isSetAdult())
                {
                    Adult adult = new Adult();
                    adults.add(this.populateAdult(adult, individual.getAdult()));
                    adult.setIndividualRoles(this.populateIndividualRoles(individual));
                }
                else if (individual.isSetChild())
                {
                    Child child = new Child();
                    children.add(this.populateChild(child, individual.getChild()));
                    child.setIndividualRoles(this.populateIndividualRoles(individual));
                }
            }
            individuals.addAll(children);
            individuals.addAll(adults);
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
    private List<Individual> populateFoundHomeFolder(HomeFolderType xmlHomeFolder, HomeFolder homeFolder)
            throws CvqException
    {
        logger.debug("HomeFolder modification : " + xmlHomeFolder);
        //List<Individual> individualsCreated = new ArrayList<Individual>();
        List<Individual> individuals = new ArrayList<Individual>();
        if (xmlHomeFolder != null && homeFolder != null)
        {
            //XmlObject[] xmlIndividuals = xmlHomeFolder.selectPath(xqNamespace+"./hom:Individual");
            // Look into individuals from the homefolder
            for (SubjectType xmlIndividual : xmlHomeFolder.getIndividualArray())
            {
                if (xmlIndividual.isSetAdult())
                {
                    Adult adult = null;
                    AdultType xmlAdult = xmlIndividual.getAdult();
                    String individualCapdematId = xmlAdult.getIndividualCapdematId();
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
                        String individualExternalId = xmlAdult.getIndividualExternalId();
                        if (StringUtils.isNotBlank(individualExternalId))
                        {
                            HomeFolderMapping homeFolderMappingCirilNetEnfance = this.externalHomeFolderService
                                    .getHomeFolderMapping("CirilNetEnfance", homeFolder.getId());
                            for (IndividualMapping individualMapping : homeFolderMappingCirilNetEnfance.getIndividualsMappings())
                            {
                                if (adult == null && individualExternalId.equals(individualMapping.getExternalId()))
                                {
                                    adult = (Adult) this.userSearchService.getAdultById(individualMapping.getIndividualId());
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
                else if (xmlIndividual.isSetChild())
                {
                    ChildType xmlChild = xmlIndividual.getChild();
                    Child child = null;
                    String individualCapdematId = xmlChild.getIndividualCapdematId();
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
                        String individualExternalId = xmlChild.getIndividualExternalId();
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
        }
        return individuals;
    }

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
                homeFolderNew.setImportedAndNotInitialized(true);
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
    private Adult populateAdult(Adult adult, AdultType xmlAdult) throws CvqException
    {
        if (xmlAdult != null)
        {
            adult.setLastName(xmlAdult.getLastName());
            adult.setFirstName(xmlAdult.getFirstName());
            if(xmlAdult.getFirstName2() != null && xmlAdult.getFirstName2() != "") adult.setFirstName2(xmlAdult.getFirstName2());
            if(xmlAdult.getFirstName3() != null && xmlAdult.getFirstName3() != "") adult.setFirstName3(xmlAdult.getFirstName3());

            adult.setCreationDate(xmlAdult.getCreationDate().getTime());

            adult.setTitle(TitleType.forString(StringUtils.upperCase(xmlAdult.getTitle().toString())));
            
            String maidenName = xmlAdult.getMaidenName();
            if (maidenName != null && maidenName.trim().equals("")) maidenName = null;
            adult.setMaidenName(maidenName);
            
            Enum fstatus = xmlAdult.getFamilyStatus();
            if(fstatus != null)
                adult.setFamilyStatus(FamilyStatusType.forString(StringUtils.upperCase(xmlAdult.getFamilyStatus().toString())));

            String homePhone = xmlAdult.getHomePhone();
            if (homePhone != null && homePhone.trim().equals("")) homePhone = null;
            if (homePhone != null && !homePhone.matches("^0[1-9][0-9]{8}$")) throw new CvqException(
                    "Le format du téléphone personnel ne correspond pas au format attendu : 0[1-9][0-9]{8}");
            adult.setHomePhone(homePhone);
            
            String officePhone = xmlAdult.getOfficePhone();
            if (officePhone != null && officePhone.trim().equals("")) officePhone = null;
            if (officePhone != null && !officePhone.matches("^0[1-9][0-9]{8}$")) throw new CvqException(
                    "Le format du téléphone de bureau ne correspond pas au format attendu : 0[1-9][0-9]{8}");
            adult.setOfficePhone(officePhone);
            
            String mobilePhone = xmlAdult.getMobilePhone();
            if (mobilePhone != null && mobilePhone.trim().equals("")) mobilePhone = null;
            if (mobilePhone != null && !mobilePhone.matches("^0[67][0-9]{8}$")) throw new CvqException(
                    "Le format du téléphone mobile ne correspond pas au format attendu : 0[67][0-9]{8}");
            adult.setMobilePhone(mobilePhone);
            
            String email = xmlAdult.getEmail();
            if (email != null && email.trim().equals("")) email = null;
            if (email != null && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) throw new CvqException(
                    "Le format du mail ne correspond pas au format attendu : ^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
            adult.setEmail(email);
            
            String cfbn = xmlAdult.getCfbn();
            if (cfbn != null && cfbn.trim().equals("")) cfbn = null;
            if (cfbn != null && !cfbn.matches("^[0-9A-Za-z-_. ]{0,20}$")) throw new CvqException(
                    "Le format du numéro allocataire CAF ne correspond pas au format attendu : ^[0-9A-Za-z-_. ]{0,20}$");
            adult.setCfbn(cfbn);
            
            String profession = xmlAdult.getProfession();
            if (profession != null && profession.trim().equals("")) profession = null;
            adult.setProfession(profession);
            adult.setExternalId(xmlAdult.getIndividualExternalId());
            adult.setExternalServiceLabel("CirilNetEnfance");
            
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

            Address address = new Address();
            address.setAdditionalDeliveryInformation(xmlAdult.getAddress().getAdditionalDeliveryInformation());
            address.setAdditionalGeographicalInformation(xmlAdult.getAddress().getAdditionalGeographicalInformation());
            address.setStreetNumber(xmlAdult.getAddress().getStreetNumber());
            address.setStreetName(xmlAdult.getAddress().getStreetName());
            address.setPostalCode(xmlAdult.getAddress().getPostalCode());
            address.setCity(xmlAdult.getAddress().getCity());
            adult.setAddress(address);
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
    private Child populateChild(Child child, ChildType xmlChild)
    {
        if (xmlChild != null)
        {
            child.setLastName(xmlChild.getLastName());
            child.setFirstName(xmlChild.getFirstName());

            if(xmlChild.getFirstName2() != null && xmlChild.getFirstName2() != "") child.setFirstName2(xmlChild.getFirstName2());
            if(xmlChild.getFirstName3() != null && xmlChild.getFirstName3() != "") child.setFirstName3(xmlChild.getFirstName3());

            child.setCreationDate(xmlChild.getCreationDate().getTime());
            child.setBirthDate(xmlChild.getBirthDate().getTime());

            if(xmlChild.getBirthPlace() != null) child.setBirthCity(xmlChild.getBirthPlace().getCity());
            if(xmlChild.getBirthPlace() != null) child.setBirthPostalCode(xmlChild.getBirthPlace().getPostalCode());
            
            child.setBorn(xmlChild.getBorn());
            child.setSex(SexType.forString(xmlChild.getSex().toString()));
            child.setExternalId(xmlChild.getIndividualExternalId());
            child.setExternalServiceLabel("CirilNetEnfance");

            if (xmlChild.getChildInformationSheet() != null)
            {
                ChildInformationSheet childInformationSheet = child.getChildInformationSheet();
                child.setChildInformationSheet(this.populateChildInformationSheet(childInformationSheet, xmlChild.getChildInformationSheet()));
            }
        }
        return child;
    }


    /**
     * Populate individual roles
     * 
     * @param xmlIndividual
     * @return individualRoles updated
     */
    private Set<IndividualRole> populateIndividualRoles(SubjectType xmlIndividual)
    {
        Set<IndividualRole> individualRoles = new HashSet<IndividualRole>();
        if (xmlIndividual != null)
        {
            IndividualRoleType[] roles = (xmlIndividual.isSetAdult() ? xmlIndividual.getAdult() : xmlIndividual.getChild()).getRoleArray();
            for (IndividualRoleType individualRoleXmlObject : roles)
            {
                IndividualRole individualRole = new IndividualRole();
                individualRole.setIndividualName(individualRoleXmlObject.getIndividualName());
                individualRole.setIndividualId(individualRoleXmlObject.getIndividualExternalId());
                individualRole.setRole(RoleType.valueOf(StringUtils.upperCase(individualRoleXmlObject.getRoleName().toString())));

                individualRoles.add(individualRole);
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
            ChildInformationSheetType xmlChildInformationSheet)
    {
        if (childInformationSheet == null)
        {
            childInformationSheet = new ChildInformationSheet();
        }
        
        if (xmlChildInformationSheet.getDiets() != null)
        {
            HashSet<Diet> dietSet = new HashSet<Diet>();
            for (fr.capwebct.capdemat.homeFolderSynchronisation.DietEnumType.Enum xmlDiet : xmlChildInformationSheet.getDiets().getTypeArray())
            {
                if (DietEnum.valueOf(xmlDiet.toString()) != null)
                    dietSet.add(new Diet(DietEnum.valueOf(xmlDiet.toString())));
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

        childInformationSheet.setAllergie(xmlChildInformationSheet.getAllergie());
        childInformationSheet.setAutorisationDroitImage(xmlChildInformationSheet.getAuthorisationDroitImage());
        childInformationSheet.setAutorisationHospitalisation(xmlChildInformationSheet.getAutorisationHospitalisation());
        
        childInformationSheet.setAutorisationMaquillage(xmlChildInformationSheet.getAutorisationMaquillage());
        childInformationSheet.setAutorisationRentrerSeul(xmlChildInformationSheet.getAuthorisationRentrerSeul());
        childInformationSheet.setAutorisationTransporterTransportCommun(xmlChildInformationSheet.getAutorisationTransporterTransportCommun());
        childInformationSheet.setAutorisationTransporterVehiculeMunicipal(xmlChildInformationSheet.getAutorisationTransporterVehiculeMunicipal());
        childInformationSheet.setDifficulteSante(xmlChildInformationSheet.getDifficulteSante());
        childInformationSheet.setEmailEnfant(xmlChildInformationSheet.getEmailEnfant());
        childInformationSheet.setNomMedecinTraitant(xmlChildInformationSheet.getNomMedecinTraitant());
        childInformationSheet.setNomOrganismeAssurance(xmlChildInformationSheet.getNomOrganismeAssurance());
        childInformationSheet.setNumeroOrganismeAssurance(xmlChildInformationSheet.getNumeroOrganismeAssurance());
        childInformationSheet.setProjetAccueilIndividualise(xmlChildInformationSheet.getProjetAccueilIndividualise());
        childInformationSheet.setRecommandationParent(xmlChildInformationSheet.getRecommandationParent());
        childInformationSheet.setTelephoneMedecinTraitant(xmlChildInformationSheet.getTelephoneMedecinTraitant());
        childInformationSheet.setTelephonePortable(xmlChildInformationSheet.getTelephonePortable());
        childInformationSheet.setVaccinAutre(xmlChildInformationSheet.getVaccinAutre());
        

        try {
            childInformationSheet.setVaccinBcg(xmlChildInformationSheet.getVaccinBcg().getTime());
        } catch (Exception e) { logger.error("Error parsing VaccinBcg : " + e.getMessage()); }
        try {
            childInformationSheet.setVaccinDtPolio(xmlChildInformationSheet.getVaccinDtPolio().getTime());
        } catch (Exception e) { logger.error("Error parsing VaccinDTPOLIO : " + e.getMessage() + e.getCause()); }
        try {
            childInformationSheet.setVaccinInjectionSerum(xmlChildInformationSheet.getVaccinInjectionSerum().getTime());
        } catch (Exception e) { logger.error("Error parsing VaccinInjectionSerum : " + e.getMessage()); }
        try {
            childInformationSheet.setVaccinRor(xmlChildInformationSheet.getVaccinRor().getTime());
        } catch (Exception e) { logger.error("Error parsing VaccinRor : " + e.getMessage()); }
        try {
            childInformationSheet.setVaccinTetracoqPentacoq(xmlChildInformationSheet.getVaccinTetracoqPentacoq().getTime());
        } catch (Exception e) { logger.error("Error parsing VaccinTetracoqPentacoq : " + e.getMessage()); }
        
        return childInformationSheet;
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
    private Individual populateBirthPlace(Individual individual, SubjectType xmlIndividual)
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
                if (individual != null && !UserState.ARCHIVED.equals(individual.getState()))
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
                Individual ind = this.userSearchService.getById(id);
                if(ind.getHomeFolder().getIndividualResponsable().getId().equals(ind.getId())) {
                    logger.warn("Cannot delete individual "+ind+" beacause it's the home folder responsible");
                } else {
                    this.userWorkflowService.changeState(ind, UserState.ARCHIVED);
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return individualsDeleted != null && individualsDeleted.size() > 0;
    }

    private Child ultimateChildMatchWithHomeFolder(ChildType xmlChild, HomeFolder homeFolder)
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

    private Child isMatchIndividuByName(ChildType xmlChild, Individual individualCapDemat)
    {
        Child child = new Child();
        if (isMatchIndividuByName(populateChild(child, xmlChild), individualCapDemat))
        {
            child.setId(individualCapDemat.getId());
            return child;
        }
        return null;
    }

    private Adult ultimateAdultMatchWithHomeFolder(AdultType xmlAdult, HomeFolder homeFolder) throws CvqException
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

    private Adult isMatchAdultByName(AdultType xmlAdult, Individual individualCapDemat) throws CvqException
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

    public void setAuthenticationService(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
