package org.libredemat.external.endpoint;

import java.util.List;

import org.libredemat.business.users.Address;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.IUserService;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

import org.libredemat.GetHomeFoldersResponseDocument;
import org.libredemat.HomeFolderType;
import org.libredemat.IndividualType;
import org.libredemat.GetHomeFoldersResponseDocument.GetHomeFoldersResponse;


public class HomeFolderServiceEndpoint extends AbstractMarshallingPayloadEndpoint {

    private IUserService userService;

    private IUserSearchService userSearchService;

    public HomeFolderServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected Object invokeInternal(Object request) throws Exception {

       GetHomeFoldersResponseDocument responseDocument =
            GetHomeFoldersResponseDocument.Factory.newInstance();
       GetHomeFoldersResponse response =
            responseDocument.addNewGetHomeFoldersResponse();

        List<HomeFolder> homeFolders = userSearchService.getAll(true, true);
        for (HomeFolder homeFolder : homeFolders) {
            HomeFolderType homeFolderType = response.addNewHomeFolder();
            homeFolderType.setId(homeFolder.getId());
            Address address = homeFolder.getAddress();
            homeFolderType.setAddress(address.getStreetNumber() + " " + address.getStreetName());
            for (Object object : homeFolder.getIndividuals()) {
                Individual individual = (Individual) object;
                IndividualType individualType = homeFolderType.addNewIndividual();
                individualType.setId(individual.getId());
                individualType.setFirstName(individual.getFirstName());
                individualType.setLastName(individual.getLastName());
                if (individual instanceof Adult) {
                    Adult adult = (Adult) individual;
                    if (userService.hasHomeFolderRole(adult.getId(), homeFolder.getId(),
                            RoleType.HOME_FOLDER_RESPONSIBLE))
                        individualType.setIsHomeFolderResponsible(true);
                } else if (individual instanceof Child) {
                    individualType.setIsChild(true);
                }
            }
        }

       return response;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }
}
