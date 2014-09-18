package org.libredemat.service.users.external;

import java.util.List;

import org.libredemat.business.payment.external.ExternalApplication;
import org.libredemat.business.payment.external.ExternalHomeFolder;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.security.annotation.IsUser;


public interface IExternalHomeFolderService {

    List<HomeFolderMapping> getHomeFolderMappings(@IsUser Long homeFolderId);

    void createHomeFolderMapping(HomeFolderMapping homeFolderMapping);

    void modifyHomeFolderMapping(HomeFolderMapping homeFolderMapping);

    HomeFolderMapping getHomeFolderMapping(final String externalServiceLabel,
        @IsUser final Long homeFolderId);

    HomeFolderMapping getHomeFolderMapping(final String externalServiceLabel, 
            final String externalLibreDematId);

    HomeFolderMapping getHomeFolderMapping(String externalServiceLabel, ExternalHomeFolder eh);

    HomeFolderMapping getHomeFolderMapping(String externalServiceLabel,
            ExternalApplication externalApplication, String externalHomeFolderId);

    /**
     * Add a new mapping for the given object.
     *
     * If a mapping already exists for the given external service label and home folder id,
     * its external id will be replaced by the given one.
     */
    void addHomeFolderMapping(final String externalServiceLabel,
        @IsUser final Long homeFolderId, final String externalId);

    void deleteHomeFolderMapping(final String externalServiceLabel, ExternalHomeFolder eh);

    /**
     * Delete mappings for the given external service and home folder (included individual mappings).
     */
    void deleteHomeFolderMappings(final String externalServiceLabel,
        @IsUser final Long homeFolderId);

    /**
     * Set the external id of an individual for the given external service.
     * 
     * The mapping for the home folder must exist prior to this action.
     * To be used on external id retrieval from the external service.
     */
    void setExternalId(String externalServiceLabel,
        @IsUser Long homeFolderId, @IsUser Long individualId,
        String externalId);

    List<IndividualMapping> getIndividualMappings(@IsUser Long individualId);

    IndividualMapping getIndividualMapping(HomeFolderMapping homeFolderMapping, @IsUser Long individualId);

    void deleteIndividualMapping(HomeFolderMapping homeFolderMapping, @IsUser Long individualId);

    IndividualMapping getIndividualMapping(@IsUser Individual individual, String externalServiceLabel);

    /**
     * Search for homefolder by its home folder external id
     * If we find more than one HomeFolder, we keep the first found.
     * More than one HomeFolder is not possible
     * 
     * @author Inexine : Frederic Fabre
     * Hack Inexine
     * 
     * @param homeFolderExternalId Homefolder external Id 
     * @param externalServiceLabel External service label (cf HomeFolderMapping table)
     * @return homefolder
     */
    // Inexine Hack Frederic Fabre
    public HomeFolder getHomeFolderByHomeFolderExternalId(String externalServiceLabel, String homeFolderExternalId);
    
    // Inexine Hack Sébastien Faure 
    IndividualMapping getIndividualMappingByExternalId(String externalId, String externalServiceLabel, Long homeFolderId);

    // inexine @author pit@tor
    public void deleteIndividualMapping(IndividualMapping individualMapping);

    IndividualMapping getIndividualMapping(Long individualId, Long homeFolderId, String externalServiceLabel);

    IndividualMapping getIndividualMapping(String externalLibreDematId);

}
