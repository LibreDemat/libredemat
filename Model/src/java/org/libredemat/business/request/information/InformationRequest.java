
package org.libredemat.business.request.information;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalTime;

import net.sf.oval.constraint.AssertValid;
import org.apache.xmlbeans.XmlOptions;
import org.libredemat.business.authority.*;
import org.libredemat.business.payment.*;
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.information.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class InformationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = InformationRequestData.conditions;

    @AssertValid(message = "")
    private InformationRequestData informationRequestData;

    public InformationRequest(RequestData requestData, InformationRequestData informationRequestData) {
        super(requestData);
        this.informationRequestData = informationRequestData;
    }

    public InformationRequest() {
        super();
        this.informationRequestData = new InformationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("reason", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("validation", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public InformationRequestData getSpecificData() {
        return informationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(InformationRequestData informationRequestData) {
        this.informationRequestData = informationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        InformationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final InformationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        InformationRequestDocument informationRequestDoc = InformationRequestDocument.Factory.newInstance();
        InformationRequestDocument.InformationRequest informationRequest = informationRequestDoc.addNewInformationRequest();
        super.fillCommonXmlInfo(informationRequest);
        int i = 0;
        
        informationRequest.setMessage(getMessage());
      
        i = 0;
        if (getMotive() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] motiveTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getMotive().size()];
            for (LocalReferentialData object : getMotive()) {
              motiveTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            informationRequest.setMotiveArray(motiveTypeTab);
        }
      
        return informationRequestDoc;
    }

    @Override
    public final InformationRequestDocument.InformationRequest modelToXmlRequest() {
        return modelToXml().getInformationRequest();
    }

    public static InformationRequest xmlToModel(InformationRequestDocument informationRequestDoc) {
        InformationRequestDocument.InformationRequest informationRequestXml = informationRequestDoc.getInformationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        InformationRequest informationRequest = new InformationRequest();
        informationRequest.fillCommonModelInfo(informationRequest, informationRequestXml);
        
        informationRequest.setMessage(informationRequestXml.getMessage());
      
        List<org.libredemat.business.request.LocalReferentialData> motiveList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(informationRequestXml.sizeOfMotiveArray());
        for (LocalReferentialDataType object : informationRequestXml.getMotiveArray()) {
            motiveList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        informationRequest.setMotive(motiveList);
      
        return informationRequest;
    }

    @Override
    public InformationRequest clone() {
        InformationRequest clone = new InformationRequest(getRequestData().clone(), informationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("reason", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("validation", stepState);
        
        return clone;
    }

  
    public final void setMessage(final String message) {
        informationRequestData.setMessage(message);
    }

    
    public final String getMessage() {
        return informationRequestData.getMessage();
    }
  
    public final void setMotive(final List<org.libredemat.business.request.LocalReferentialData> motive) {
        informationRequestData.setMotive(motive);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getMotive() {
        return informationRequestData.getMotive();
    }
  
}
