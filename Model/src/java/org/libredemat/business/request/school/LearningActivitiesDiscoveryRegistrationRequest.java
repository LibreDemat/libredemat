
package org.libredemat.business.request.school;

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
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.school.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class LearningActivitiesDiscoveryRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = LearningActivitiesDiscoveryRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private LearningActivitiesDiscoveryRegistrationRequestData learningActivitiesDiscoveryRegistrationRequestData;

    public LearningActivitiesDiscoveryRegistrationRequest(RequestData requestData, LearningActivitiesDiscoveryRegistrationRequestData learningActivitiesDiscoveryRegistrationRequestData) {
        super(requestData);
        this.learningActivitiesDiscoveryRegistrationRequestData = learningActivitiesDiscoveryRegistrationRequestData;
    }

    public LearningActivitiesDiscoveryRegistrationRequest() {
        super();
        this.learningActivitiesDiscoveryRegistrationRequestData = new LearningActivitiesDiscoveryRegistrationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("subject", stepState);
        
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
    public LearningActivitiesDiscoveryRegistrationRequestData getSpecificData() {
        return learningActivitiesDiscoveryRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(LearningActivitiesDiscoveryRegistrationRequestData learningActivitiesDiscoveryRegistrationRequestData) {
        this.learningActivitiesDiscoveryRegistrationRequestData = learningActivitiesDiscoveryRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        LearningActivitiesDiscoveryRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final LearningActivitiesDiscoveryRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        LearningActivitiesDiscoveryRegistrationRequestDocument learningActivitiesDiscoveryRegistrationRequestDoc = LearningActivitiesDiscoveryRegistrationRequestDocument.Factory.newInstance();
        LearningActivitiesDiscoveryRegistrationRequestDocument.LearningActivitiesDiscoveryRegistrationRequest learningActivitiesDiscoveryRegistrationRequest = learningActivitiesDiscoveryRegistrationRequestDoc.addNewLearningActivitiesDiscoveryRegistrationRequest();
        super.fillCommonXmlInfo(learningActivitiesDiscoveryRegistrationRequest);
        int i = 0;
        
        i = 0;
        if (getAtelierEveil() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] atelierEveilTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getAtelierEveil().size()];
            for (LocalReferentialData object : getAtelierEveil()) {
              atelierEveilTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            learningActivitiesDiscoveryRegistrationRequest.setAtelierEveilArray(atelierEveilTypeTab);
        }
      
        learningActivitiesDiscoveryRegistrationRequest.setAtelierEveilPrecisionChoix(getAtelierEveilPrecisionChoix());
      
        return learningActivitiesDiscoveryRegistrationRequestDoc;
    }

    @Override
    public final LearningActivitiesDiscoveryRegistrationRequestDocument.LearningActivitiesDiscoveryRegistrationRequest modelToXmlRequest() {
        return modelToXml().getLearningActivitiesDiscoveryRegistrationRequest();
    }

    public static LearningActivitiesDiscoveryRegistrationRequest xmlToModel(LearningActivitiesDiscoveryRegistrationRequestDocument learningActivitiesDiscoveryRegistrationRequestDoc) {
        LearningActivitiesDiscoveryRegistrationRequestDocument.LearningActivitiesDiscoveryRegistrationRequest learningActivitiesDiscoveryRegistrationRequestXml = learningActivitiesDiscoveryRegistrationRequestDoc.getLearningActivitiesDiscoveryRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        LearningActivitiesDiscoveryRegistrationRequest learningActivitiesDiscoveryRegistrationRequest = new LearningActivitiesDiscoveryRegistrationRequest();
        learningActivitiesDiscoveryRegistrationRequest.fillCommonModelInfo(learningActivitiesDiscoveryRegistrationRequest, learningActivitiesDiscoveryRegistrationRequestXml);
        
        List<org.libredemat.business.request.LocalReferentialData> atelierEveilList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(learningActivitiesDiscoveryRegistrationRequestXml.sizeOfAtelierEveilArray());
        for (LocalReferentialDataType object : learningActivitiesDiscoveryRegistrationRequestXml.getAtelierEveilArray()) {
            atelierEveilList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        learningActivitiesDiscoveryRegistrationRequest.setAtelierEveil(atelierEveilList);
      
        learningActivitiesDiscoveryRegistrationRequest.setAtelierEveilPrecisionChoix(learningActivitiesDiscoveryRegistrationRequestXml.getAtelierEveilPrecisionChoix());
      
        return learningActivitiesDiscoveryRegistrationRequest;
    }

    @Override
    public LearningActivitiesDiscoveryRegistrationRequest clone() {
        LearningActivitiesDiscoveryRegistrationRequest clone = new LearningActivitiesDiscoveryRegistrationRequest(getRequestData().clone(), learningActivitiesDiscoveryRegistrationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("subject", stepState);
        
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

  
    public final void setAtelierEveil(final List<org.libredemat.business.request.LocalReferentialData> atelierEveil) {
        learningActivitiesDiscoveryRegistrationRequestData.setAtelierEveil(atelierEveil);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getAtelierEveil() {
        return learningActivitiesDiscoveryRegistrationRequestData.getAtelierEveil();
    }
  
    public final void setAtelierEveilPrecisionChoix(final String atelierEveilPrecisionChoix) {
        learningActivitiesDiscoveryRegistrationRequestData.setAtelierEveilPrecisionChoix(atelierEveilPrecisionChoix);
    }

    
    public final String getAtelierEveilPrecisionChoix() {
        return learningActivitiesDiscoveryRegistrationRequestData.getAtelierEveilPrecisionChoix();
    }
  
}
