
package org.libredemat.business.request.leisure;

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
import org.libredemat.xml.request.leisure.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class YouthCenterRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = YouthCenterRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private YouthCenterRegistrationRequestData youthCenterRegistrationRequestData;

    public YouthCenterRegistrationRequest(RequestData requestData, YouthCenterRegistrationRequestData youthCenterRegistrationRequestData) {
        super(requestData);
        this.youthCenterRegistrationRequestData = youthCenterRegistrationRequestData;
    }

    public YouthCenterRegistrationRequest() {
        super();
        this.youthCenterRegistrationRequestData = new YouthCenterRegistrationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("registration", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("rules", stepState);
        
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
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("administration", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public YouthCenterRegistrationRequestData getSpecificData() {
        return youthCenterRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(YouthCenterRegistrationRequestData youthCenterRegistrationRequestData) {
        this.youthCenterRegistrationRequestData = youthCenterRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        YouthCenterRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final YouthCenterRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        YouthCenterRegistrationRequestDocument youthCenterRegistrationRequestDoc = YouthCenterRegistrationRequestDocument.Factory.newInstance();
        YouthCenterRegistrationRequestDocument.YouthCenterRegistrationRequest youthCenterRegistrationRequest = youthCenterRegistrationRequestDoc.addNewYouthCenterRegistrationRequest();
        super.fillCommonXmlInfo(youthCenterRegistrationRequest);
        int i = 0;
        
        if (getChildAlone() != null)
            youthCenterRegistrationRequest.setChildAlone(getChildAlone().booleanValue());
      
        date = getFirstRegistrationDate();
        if (date != null) {
            calendar.setTime(date);
            youthCenterRegistrationRequest.setFirstRegistrationDate(calendar);
        }
      
        youthCenterRegistrationRequest.setFirstRegistrationNumeroAdherent(getFirstRegistrationNumeroAdherent());
      
        if (getIsFirstRegistration() != null)
            youthCenterRegistrationRequest.setIsFirstRegistration(getIsFirstRegistration().booleanValue());
      
        if (getMultiActivities() != null)
            youthCenterRegistrationRequest.setMultiActivities(getMultiActivities().booleanValue());
      
        if (getRulesAcceptance() != null)
            youthCenterRegistrationRequest.setRulesAcceptance(getRulesAcceptance().booleanValue());
      
        date = getSubjectChoiceBirthDate();
        if (date != null) {
            calendar.setTime(date);
            youthCenterRegistrationRequest.setSubjectChoiceBirthDate(calendar);
        }
      
        youthCenterRegistrationRequest.setSubjectChoiceEmail(getSubjectChoiceEmail());
      
        youthCenterRegistrationRequest.setSubjectChoiceMobilePhone(getSubjectChoiceMobilePhone());
      
        return youthCenterRegistrationRequestDoc;
    }

    @Override
    public final YouthCenterRegistrationRequestDocument.YouthCenterRegistrationRequest modelToXmlRequest() {
        return modelToXml().getYouthCenterRegistrationRequest();
    }

    public static YouthCenterRegistrationRequest xmlToModel(YouthCenterRegistrationRequestDocument youthCenterRegistrationRequestDoc) {
        YouthCenterRegistrationRequestDocument.YouthCenterRegistrationRequest youthCenterRegistrationRequestXml = youthCenterRegistrationRequestDoc.getYouthCenterRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        YouthCenterRegistrationRequest youthCenterRegistrationRequest = new YouthCenterRegistrationRequest();
        youthCenterRegistrationRequest.fillCommonModelInfo(youthCenterRegistrationRequest, youthCenterRegistrationRequestXml);
        
        youthCenterRegistrationRequest.setChildAlone(Boolean.valueOf(youthCenterRegistrationRequestXml.getChildAlone()));
      
        calendar = youthCenterRegistrationRequestXml.getFirstRegistrationDate();
        if (calendar != null) {
            youthCenterRegistrationRequest.setFirstRegistrationDate(calendar.getTime());
        }
      
        youthCenterRegistrationRequest.setFirstRegistrationNumeroAdherent(youthCenterRegistrationRequestXml.getFirstRegistrationNumeroAdherent());
      
        youthCenterRegistrationRequest.setIsFirstRegistration(Boolean.valueOf(youthCenterRegistrationRequestXml.getIsFirstRegistration()));
      
        youthCenterRegistrationRequest.setMultiActivities(Boolean.valueOf(youthCenterRegistrationRequestXml.getMultiActivities()));
      
        youthCenterRegistrationRequest.setRulesAcceptance(Boolean.valueOf(youthCenterRegistrationRequestXml.getRulesAcceptance()));
      
        calendar = youthCenterRegistrationRequestXml.getSubjectChoiceBirthDate();
        if (calendar != null) {
            youthCenterRegistrationRequest.setSubjectChoiceBirthDate(calendar.getTime());
        }
      
        youthCenterRegistrationRequest.setSubjectChoiceEmail(youthCenterRegistrationRequestXml.getSubjectChoiceEmail());
      
        youthCenterRegistrationRequest.setSubjectChoiceMobilePhone(youthCenterRegistrationRequestXml.getSubjectChoiceMobilePhone());
      
        return youthCenterRegistrationRequest;
    }

    @Override
    public YouthCenterRegistrationRequest clone() {
        YouthCenterRegistrationRequest clone = new YouthCenterRegistrationRequest(getRequestData().clone(), youthCenterRegistrationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("registration", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("rules", stepState);
        
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
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("administration", stepState);
        
        return clone;
    }

  
    public final void setChildAlone(final Boolean childAlone) {
        youthCenterRegistrationRequestData.setChildAlone(childAlone);
    }

    
    public final Boolean getChildAlone() {
        return youthCenterRegistrationRequestData.getChildAlone();
    }
  
    public final void setFirstRegistrationDate(final java.util.Date firstRegistrationDate) {
        youthCenterRegistrationRequestData.setFirstRegistrationDate(firstRegistrationDate);
    }

    
    public final java.util.Date getFirstRegistrationDate() {
        return youthCenterRegistrationRequestData.getFirstRegistrationDate();
    }
  
    public final void setFirstRegistrationNumeroAdherent(final String firstRegistrationNumeroAdherent) {
        youthCenterRegistrationRequestData.setFirstRegistrationNumeroAdherent(firstRegistrationNumeroAdherent);
    }

    
    public final String getFirstRegistrationNumeroAdherent() {
        return youthCenterRegistrationRequestData.getFirstRegistrationNumeroAdherent();
    }
  
    public final void setIsFirstRegistration(final Boolean isFirstRegistration) {
        youthCenterRegistrationRequestData.setIsFirstRegistration(isFirstRegistration);
    }

    
    public final Boolean getIsFirstRegistration() {
        return youthCenterRegistrationRequestData.getIsFirstRegistration();
    }
  
    public final void setMultiActivities(final Boolean multiActivities) {
        youthCenterRegistrationRequestData.setMultiActivities(multiActivities);
    }

    
    public final Boolean getMultiActivities() {
        return youthCenterRegistrationRequestData.getMultiActivities();
    }
  
    public final void setRulesAcceptance(final Boolean rulesAcceptance) {
        youthCenterRegistrationRequestData.setRulesAcceptance(rulesAcceptance);
    }

    
    public final Boolean getRulesAcceptance() {
        return youthCenterRegistrationRequestData.getRulesAcceptance();
    }
  
    public final void setSubjectChoiceBirthDate(final java.util.Date subjectChoiceBirthDate) {
        youthCenterRegistrationRequestData.setSubjectChoiceBirthDate(subjectChoiceBirthDate);
    }

    
    public final java.util.Date getSubjectChoiceBirthDate() {
        return youthCenterRegistrationRequestData.getSubjectChoiceBirthDate();
    }
  
    public final void setSubjectChoiceEmail(final String subjectChoiceEmail) {
        youthCenterRegistrationRequestData.setSubjectChoiceEmail(subjectChoiceEmail);
    }

    
    public final String getSubjectChoiceEmail() {
        return youthCenterRegistrationRequestData.getSubjectChoiceEmail();
    }
  
    public final void setSubjectChoiceMobilePhone(final String subjectChoiceMobilePhone) {
        youthCenterRegistrationRequestData.setSubjectChoiceMobilePhone(subjectChoiceMobilePhone);
    }

    
    public final String getSubjectChoiceMobilePhone() {
        return youthCenterRegistrationRequestData.getSubjectChoiceMobilePhone();
    }
  
}
