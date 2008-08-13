package fr.cg95.cvq.service.technical.impl;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.w3c.dom.Node;

import fr.cg95.cvq.business.technical.TechnicalInterventionRequest;
import fr.cg95.cvq.business.users.HomeFolder;
import fr.cg95.cvq.business.users.Request;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.service.technical.ITechnicalInterventionRequestService;
import fr.cg95.cvq.service.users.impl.RequestService;
import fr.cg95.cvq.xml.technical.TechnicalInterventionRequestDocument;

public class TechnicalInterventionRequestService extends RequestService implements
        ITechnicalInterventionRequestService {

    static Logger logger = Logger.getLogger(TechnicalInterventionRequestService.class);

    public boolean accept(Request request) {
        return request instanceof TechnicalInterventionRequest;
    }

    public Long create(Node node) throws CvqException {
        TechnicalInterventionRequestDocument requestDocument = null;
        try {
            requestDocument = TechnicalInterventionRequestDocument.Factory.parse(node);
        } catch (XmlException xe) {
            logger.error("create() Error while parsing received data");
            xe.printStackTrace();
        }
        
        TechnicalInterventionRequest tir = TechnicalInterventionRequest.xmlToModel(requestDocument);
        HomeFolder homeFolder = super.createOrSynchronizeHomeFolder(tir);

        initializeCommonAttributes(tir);

        Long requestId = super.create(tir);
        if(homeFolder != null) {
            homeFolder.setBoundToRequest(Boolean.valueOf(true));
            homeFolder.setOriginRequestId(requestId);
        }

        return requestId;
    }

    public Request getSkeletonRequest() throws CvqException {

        return new TechnicalInterventionRequest();
    }



}
