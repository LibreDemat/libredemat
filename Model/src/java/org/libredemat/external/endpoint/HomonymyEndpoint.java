/**
 * 
 */
package org.libredemat.external.endpoint;

import java.util.List;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

import fr.capwebct.capdemat.homonymy.CapdematHomonymType;
import fr.capwebct.capdemat.homonymy.ExternalIndividualType;
import fr.capwebct.capdemat.homonymy.HomonymIndividualType;
import fr.capwebct.capdemat.homonymy.HomonymyRequestDocument;
import fr.capwebct.capdemat.homonymy.HomonymyRequestDocument.HomonymyRequest;
import fr.capwebct.capdemat.homonymy.HomonymyResponseDocument;
import fr.capwebct.capdemat.homonymy.HomonymyResponseDocument.HomonymyResponse;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Individual;
import org.libredemat.service.users.IUserSearchService;

/**
 * Homonymy endpoint
 * Search homonymes in Capdemat when an external application sends individual names
 * Return homonymes's homefolder
 * 
 * cf Homonymy.xsd
 * 
 * @author Inexine : Frederic Fabre
 * Hack Inexine
 *
 */
public class HomonymyEndpoint extends AbstractMarshallingPayloadEndpoint {
    /** */
    private IUserSearchService userSearchService;
    
    /*
     * Constructor
     */
    public HomonymyEndpoint(Marshaller marshaller) {
        super(marshaller);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint#invokeInternal(java.lang.Object)
     */
    @Override
    protected Object invokeInternal(Object request) throws Exception {
        logger.debug("request : " + request);
        
        // Request
        HomonymyRequest homonymyRequest = ((HomonymyRequestDocument)request).getHomonymyRequest();
            
        // Response
        HomonymyResponseDocument homonymyResponseDocument = HomonymyResponseDocument.Factory.newInstance();
        // <HomonymyResponse>
        HomonymyResponse homonymyResponse = homonymyResponseDocument.addNewHomonymyResponse();
        ExternalIndividualType[] xmlExternalIndividuals = homonymyRequest.getExternalIndividualArray();//selectPath("./ExternalIndividual");  
        
        // For each external individuals send by external appplication
        for (ExternalIndividualType externalIndividual : xmlExternalIndividuals) {
            //XmlCursor monCursorExternalId = xmlExternalIndividual.newCursor();
            //XmlCursor monCursorFirstName = xmlExternalIndividual.newCursor();
            //XmlCursor monCursorLastName = xmlExternalIndividual.newCursor();
            
            //monCursorExternalId.toChild("ExternalId");
            String externalId = externalIndividual.getExternalId();//monCursorExternalId.getTextValue();
            
            //monCursorFirstName.toChild("FirstName");
            String firstName = externalIndividual.getFirstName();
            
            //monCursorLastName.toChild("LastName");
            String lastName = externalIndividual.getLastName();

            // <CapdematHomonym>
            CapdematHomonymType capdematHomonym = homonymyResponse.addNewCapdematHomonym();
            // <ExternalId>
            capdematHomonym.setExternalId(externalId);
            
            // Search individuals in database
            List<Individual> individuals = this.userSearchService.getIndividualsByFirstnameAndLastname(firstName, lastName);
            
            for (Individual individual : individuals) {
                // <HomonymIndividual>
                HomonymIndividualType homonymIndividual = capdematHomonym.addNewHomonymIndividual();
                // <HomeFolderId>
                homonymIndividual.setHomeFolderId(individual.getHomeFolder().getId().toString());
                // <IndividualId>
                homonymIndividual.setIndividualId(individual.getId().toString());
                // <FirstName>
                homonymIndividual.setFirstName(individual.getFirstName());
                // <LastName>
                homonymIndividual.setLastName(individual.getLastName());
                
                if(individual instanceof Adult) {
                    Adult adult = Adult.class.cast(individual);
                    // <Email>                
                    homonymIndividual.setEmail(adult.getEmail());
                    // <HomePhone>
                    homonymIndividual.setHomePhone(adult.getHomePhone());
                    // <OfficePhone>
                    homonymIndividual.setOfficePhone(adult.getOfficePhone());
                    // <MobilePhone>
                    homonymIndividual.setMobilePhone(adult.getMobilePhone());
                    // <StreetNumber>
                    homonymIndividual.setStreetNumber(individual.getAddress().getStreetNumber());
                    // <StreetName>
                    homonymIndividual.setStreetName(individual.getAddress().getStreetName());
                    // <PostalCode>
                    homonymIndividual.setPostalCode(individual.getAddress().getPostalCode());
                    // <City>
                    homonymIndividual.setCity(individual.getAddress().getCity());
                }
            }
        }
        logger.debug("Response : " + homonymyResponse.xmlText());
        return homonymyResponse;
    }

    /**
     * @return the userSearchService
     */
    public IUserSearchService getUserSearchService() {
        return this.userSearchService;
    }

    /**
     * @param userSearchService the userSearchService to set
     */
    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }
    
}
