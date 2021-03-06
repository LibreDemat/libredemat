
package org.libredemat.business.request.technical;

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
import org.libredemat.xml.request.technical.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class TechnicalInterventionRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = TechnicalInterventionRequestData.conditions;

    @AssertValid(message = "")
    private TechnicalInterventionRequestData technicalInterventionRequestData;

    public TechnicalInterventionRequest(RequestData requestData, TechnicalInterventionRequestData technicalInterventionRequestData) {
        super(requestData);
        this.technicalInterventionRequestData = technicalInterventionRequestData;
    }

    public TechnicalInterventionRequest() {
        super();
        this.technicalInterventionRequestData = new TechnicalInterventionRequestData();
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
          getStepStates().put("intervention", stepState);
        
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
    public TechnicalInterventionRequestData getSpecificData() {
        return technicalInterventionRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(TechnicalInterventionRequestData technicalInterventionRequestData) {
        this.technicalInterventionRequestData = technicalInterventionRequestData;
    }

    @Override
    public final String modelToXmlString() {
        TechnicalInterventionRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final TechnicalInterventionRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        TechnicalInterventionRequestDocument technicalInterventionRequestDoc = TechnicalInterventionRequestDocument.Factory.newInstance();
        TechnicalInterventionRequestDocument.TechnicalInterventionRequest technicalInterventionRequest = technicalInterventionRequestDoc.addNewTechnicalInterventionRequest();
        super.fillCommonXmlInfo(technicalInterventionRequest);
        int i = 0;
        
        technicalInterventionRequest.setInterventionDescription(getInterventionDescription());
      
        if (getInterventionPlace() != null)
            technicalInterventionRequest.setInterventionPlace(Address.modelToXml(getInterventionPlace()));
      
        i = 0;
        if (getInterventionType() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] interventionTypeTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getInterventionType().size()];
            for (LocalReferentialData object : getInterventionType()) {
              interventionTypeTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            technicalInterventionRequest.setInterventionTypeArray(interventionTypeTypeTab);
        }
      
        technicalInterventionRequest.setOtherInterventionLabel(getOtherInterventionLabel());
      
        return technicalInterventionRequestDoc;
    }

    @Override
    public final TechnicalInterventionRequestDocument.TechnicalInterventionRequest modelToXmlRequest() {
        return modelToXml().getTechnicalInterventionRequest();
    }

    public static TechnicalInterventionRequest xmlToModel(TechnicalInterventionRequestDocument technicalInterventionRequestDoc) {
        TechnicalInterventionRequestDocument.TechnicalInterventionRequest technicalInterventionRequestXml = technicalInterventionRequestDoc.getTechnicalInterventionRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        TechnicalInterventionRequest technicalInterventionRequest = new TechnicalInterventionRequest();
        technicalInterventionRequest.fillCommonModelInfo(technicalInterventionRequest, technicalInterventionRequestXml);
        
        technicalInterventionRequest.setInterventionDescription(technicalInterventionRequestXml.getInterventionDescription());
      
        if (technicalInterventionRequestXml.getInterventionPlace() != null)
            technicalInterventionRequest.setInterventionPlace(Address.xmlToModel(technicalInterventionRequestXml.getInterventionPlace()));
      
        List<org.libredemat.business.request.LocalReferentialData> interventionTypeList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(technicalInterventionRequestXml.sizeOfInterventionTypeArray());
        for (LocalReferentialDataType object : technicalInterventionRequestXml.getInterventionTypeArray()) {
            interventionTypeList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        technicalInterventionRequest.setInterventionType(interventionTypeList);
      
        technicalInterventionRequest.setOtherInterventionLabel(technicalInterventionRequestXml.getOtherInterventionLabel());
      
        return technicalInterventionRequest;
    }

    @Override
    public TechnicalInterventionRequest clone() {
        TechnicalInterventionRequest clone = new TechnicalInterventionRequest(getRequestData().clone(), technicalInterventionRequestData.clone());
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
          clone.getStepStates().put("intervention", stepState);
        
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

  
    public final void setInterventionDescription(final String interventionDescription) {
        technicalInterventionRequestData.setInterventionDescription(interventionDescription);
    }

    
    public final String getInterventionDescription() {
        return technicalInterventionRequestData.getInterventionDescription();
    }
  
    public final void setInterventionPlace(final org.libredemat.business.users.Address interventionPlace) {
        technicalInterventionRequestData.setInterventionPlace(interventionPlace);
    }

    
    public final org.libredemat.business.users.Address getInterventionPlace() {
        return technicalInterventionRequestData.getInterventionPlace();
    }
  
    public final void setInterventionType(final List<org.libredemat.business.request.LocalReferentialData> interventionType) {
        technicalInterventionRequestData.setInterventionType(interventionType);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getInterventionType() {
        return technicalInterventionRequestData.getInterventionType();
    }
  
    public final void setOtherInterventionLabel(final String otherInterventionLabel) {
        technicalInterventionRequestData.setOtherInterventionLabel(otherInterventionLabel);
    }

    
    public final String getOtherInterventionLabel() {
        return technicalInterventionRequestData.getOtherInterventionLabel();
    }
  
}
