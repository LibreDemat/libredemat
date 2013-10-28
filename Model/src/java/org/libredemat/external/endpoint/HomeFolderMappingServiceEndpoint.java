package org.libredemat.external.endpoint;

import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.springframework.oxm.Marshaller;

import org.libredemat.HomeFolderMappingRequestDocument;
import org.libredemat.HomeFolderMappingType;
import org.libredemat.IndividualMappingType;
import org.libredemat.HomeFolderMappingRequestDocument.HomeFolderMappingRequest;

public class HomeFolderMappingServiceEndpoint extends SecuredServiceEndpoint {

    private IExternalHomeFolderService externalHomeFolderService;

    public HomeFolderMappingServiceEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected Object invokeInternal(Object arg0) throws Exception {

        HomeFolderMappingRequest homeFolderMappingRequest =
            ((HomeFolderMappingRequestDocument) arg0).getHomeFolderMappingRequest();

        String currentExternalService = SecurityContext.getCurrentExternalService();

        HomeFolderMappingType homeFolderMappingType = homeFolderMappingRequest.getHomeFolderMapping();
        HomeFolderMapping esim =
            externalHomeFolderService.getHomeFolderMapping(currentExternalService, homeFolderMappingType.getExternalLibreDematId());
        externalHomeFolderService.addHomeFolderMapping(currentExternalService, esim.getHomeFolderId(),
                homeFolderMappingType.getExternalId());
        IndividualMappingType[] individualMappingTypes = homeFolderMappingRequest.getIndividualMappingArray();
        for (int i = 0; i < individualMappingTypes.length; i++) {
            for (IndividualMapping indMapping : esim.getIndividualsMappings()) {
                if (indMapping.getExternalLibreDematId().equals(individualMappingTypes[i].getExternalLibreDematId()))
                    externalHomeFolderService.setExternalId(currentExternalService, esim.getHomeFolderId(), 
                            indMapping.getIndividualId(),
                            individualMappingTypes[i].getExternalId());
            }
        }

        return null;
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }
}
