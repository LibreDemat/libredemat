
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
import org.libredemat.business.payment.*;
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.school.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class SchoolRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = SchoolRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private SchoolRegistrationRequestData schoolRegistrationRequestData;

    public SchoolRegistrationRequest(RequestData requestData, SchoolRegistrationRequestData schoolRegistrationRequestData) {
        super(requestData);
        this.schoolRegistrationRequestData = schoolRegistrationRequestData;
    }

    public SchoolRegistrationRequest() {
        super();
        this.schoolRegistrationRequestData = new SchoolRegistrationRequestData();
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
    public SchoolRegistrationRequestData getSpecificData() {
        return schoolRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(SchoolRegistrationRequestData schoolRegistrationRequestData) {
        this.schoolRegistrationRequestData = schoolRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        SchoolRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final SchoolRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        SchoolRegistrationRequestDocument schoolRegistrationRequestDoc = SchoolRegistrationRequestDocument.Factory.newInstance();
        SchoolRegistrationRequestDocument.SchoolRegistrationRequest schoolRegistrationRequest = schoolRegistrationRequestDoc.addNewSchoolRegistrationRequest();
        super.fillCommonXmlInfo(schoolRegistrationRequest);
        int i = 0;
          CurrentSchoolType currentSchoolTypeCurrentSchool = schoolRegistrationRequest.addNewCurrentSchool();
        currentSchoolTypeCurrentSchool.setCurrentSchoolAddress(getCurrentSchoolAddress());
      
        currentSchoolTypeCurrentSchool.setCurrentSchoolName(getCurrentSchoolName());
      
        if (getCurrentSection() != null)
            currentSchoolTypeCurrentSchool.setCurrentSection(org.libredemat.xml.common.SectionType.Enum.forString(getCurrentSection().getLegacyLabel()));
      
        if (getRulesAndRegulationsAcceptance() != null)
            schoolRegistrationRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        if (getSchool() != null)
            schoolRegistrationRequest.setSchool(School.modelToXml(getSchool()));
      
        if (getSection() != null)
            schoolRegistrationRequest.setSection(org.libredemat.xml.common.SectionType.Enum.forString(getSection().getLegacyLabel()));
      
        schoolRegistrationRequest.setUrgencyPhone(getUrgencyPhone());
      
        return schoolRegistrationRequestDoc;
    }

    @Override
    public final SchoolRegistrationRequestDocument.SchoolRegistrationRequest modelToXmlRequest() {
        return modelToXml().getSchoolRegistrationRequest();
    }

    public static SchoolRegistrationRequest xmlToModel(SchoolRegistrationRequestDocument schoolRegistrationRequestDoc) {
        SchoolRegistrationRequestDocument.SchoolRegistrationRequest schoolRegistrationRequestXml = schoolRegistrationRequestDoc.getSchoolRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        SchoolRegistrationRequest schoolRegistrationRequest = new SchoolRegistrationRequest();
        schoolRegistrationRequest.fillCommonModelInfo(schoolRegistrationRequest, schoolRegistrationRequestXml);
        
        schoolRegistrationRequest.setCurrentSchoolAddress(schoolRegistrationRequestXml.getCurrentSchool().getCurrentSchoolAddress());
      
        schoolRegistrationRequest.setCurrentSchoolName(schoolRegistrationRequestXml.getCurrentSchool().getCurrentSchoolName());
      
        if (schoolRegistrationRequestXml.getCurrentSchool().getCurrentSection() != null)
            schoolRegistrationRequest.setCurrentSection(org.libredemat.business.users.SectionType.forString(schoolRegistrationRequestXml.getCurrentSchool().getCurrentSection().toString()));
        else
            schoolRegistrationRequest.setCurrentSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
        schoolRegistrationRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(schoolRegistrationRequestXml.getRulesAndRegulationsAcceptance()));
      
        if (schoolRegistrationRequestXml.getSchool() != null)
            schoolRegistrationRequest.setSchool(School.xmlToModel(schoolRegistrationRequestXml.getSchool()));
      
        if (schoolRegistrationRequestXml.getSection() != null)
            schoolRegistrationRequest.setSection(org.libredemat.business.users.SectionType.forString(schoolRegistrationRequestXml.getSection().toString()));
        else
            schoolRegistrationRequest.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
        schoolRegistrationRequest.setUrgencyPhone(schoolRegistrationRequestXml.getUrgencyPhone());
      
        return schoolRegistrationRequest;
    }

    @Override
    public SchoolRegistrationRequest clone() {
        SchoolRegistrationRequest clone = new SchoolRegistrationRequest(getRequestData().clone(), schoolRegistrationRequestData.clone());
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

  
    public final void setCurrentSchoolAddress(final String currentSchoolAddress) {
        schoolRegistrationRequestData.setCurrentSchoolAddress(currentSchoolAddress);
    }

    
    public final String getCurrentSchoolAddress() {
        return schoolRegistrationRequestData.getCurrentSchoolAddress();
    }
  
    public final void setCurrentSchoolName(final String currentSchoolName) {
        schoolRegistrationRequestData.setCurrentSchoolName(currentSchoolName);
    }

    
    public final String getCurrentSchoolName() {
        return schoolRegistrationRequestData.getCurrentSchoolName();
    }
  
    public final void setCurrentSection(final org.libredemat.business.users.SectionType currentSection) {
        schoolRegistrationRequestData.setCurrentSection(currentSection);
    }

    
    public final org.libredemat.business.users.SectionType getCurrentSection() {
        return schoolRegistrationRequestData.getCurrentSection();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        schoolRegistrationRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return schoolRegistrationRequestData.getRulesAndRegulationsAcceptance();
    }
  
    public final void setSchool(final org.libredemat.business.authority.School school) {
        schoolRegistrationRequestData.setSchool(school);
    }

    
    public final org.libredemat.business.authority.School getSchool() {
        return schoolRegistrationRequestData.getSchool();
    }
  
    public final void setSection(final org.libredemat.business.users.SectionType section) {
        schoolRegistrationRequestData.setSection(section);
    }

    
    public final org.libredemat.business.users.SectionType getSection() {
        return schoolRegistrationRequestData.getSection();
    }
  
    public final void setUrgencyPhone(final String urgencyPhone) {
        schoolRegistrationRequestData.setUrgencyPhone(urgencyPhone);
    }

    
    public final String getUrgencyPhone() {
        return schoolRegistrationRequestData.getUrgencyPhone();
    }
  
}
