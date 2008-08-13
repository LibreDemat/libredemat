package fr.cg95.cvq.business.ecitizen;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import fr.cg95.cvq.business.users.Request;
import fr.cg95.cvq.xml.ecitizen.VoCardRequestDocument;

/**
 * @hibernate.joined-subclass
 *  table="vo_card_request"
 *  lazy="true"
 * @hibernate.joined-subclass-key
 *  column="id"
 *
 * @author bor@zenexity.fr
 */
public class VoCardRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	public VoCardRequest() {
        super();
    }


    public String modelToXmlString() {

        VoCardRequestDocument object = (VoCardRequestDocument) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public XmlObject modelToXml() {

        Calendar calendar = Calendar.getInstance();
        Date date = null;
        VoCardRequestDocument voCardRequestDoc = VoCardRequestDocument.Factory.newInstance();
        VoCardRequestDocument.VoCardRequest voCardRequest = voCardRequestDoc.addNewVoCardRequest();
        super.fillCommonXmlInfo(voCardRequest);
        return voCardRequestDoc;
    }

    public static VoCardRequest xmlToModel(VoCardRequestDocument voCardRequestDoc) {

        VoCardRequestDocument.VoCardRequest voCardRequestXml = voCardRequestDoc.getVoCardRequest();
        Calendar calendar = Calendar.getInstance();
        VoCardRequest voCardRequest = new VoCardRequest();
        voCardRequest.fillCommonModelInfo(voCardRequest,voCardRequestXml);
        return voCardRequest;
    }

}
