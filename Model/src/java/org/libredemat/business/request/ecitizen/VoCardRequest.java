package org.libredemat.business.request.ecitizen;

import java.io.Serializable;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestData;
import org.libredemat.service.request.condition.IConditionChecker;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.request.ecitizen.VoCardRequestDocument;


/**
 * @author bor@zenexity.fr
 */
@Deprecated
public class VoCardRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = VoCardRequestData.conditions;

    private VoCardRequestData voCardRequestData;

    public VoCardRequest(RequestData requestData, VoCardRequestData voCardRequestData) {
        super(requestData);
        this.voCardRequestData = voCardRequestData;
    }

    public VoCardRequest() {
        super();
        voCardRequestData = new VoCardRequestData();
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public VoCardRequestData getSpecificData() {
        return voCardRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(VoCardRequestData voCardRequestData) {
        this.voCardRequestData = voCardRequestData;
    }

    @Override
    public String modelToXmlString() {
        VoCardRequestDocument object = (VoCardRequestDocument) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public XmlObject modelToXml() {
        VoCardRequestDocument voCardRequestDoc = VoCardRequestDocument.Factory.newInstance();
        VoCardRequestDocument.VoCardRequest voCardRequest = voCardRequestDoc.addNewVoCardRequest();
        super.fillCommonXmlInfo(voCardRequest);
        return voCardRequestDoc;
    }

    public static VoCardRequest xmlToModel(VoCardRequestDocument voCardRequestDoc) {
        VoCardRequestDocument.VoCardRequest voCardRequestXml = voCardRequestDoc.getVoCardRequest();
        VoCardRequest voCardRequest = new VoCardRequest();
        voCardRequest.fillCommonModelInfo(voCardRequest,voCardRequestXml);
        return voCardRequest;
    }

    @Override
    public RequestType modelToXmlRequest() {
        VoCardRequestDocument voCardRequestDocument = 
            (VoCardRequestDocument) modelToXml();
        return voCardRequestDocument.getVoCardRequest();
    }
}
