
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
public class SchoolRegistrationWithRemoteCirilnetenfanceRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = SchoolRegistrationWithRemoteCirilnetenfanceRequestData.conditions;

    @AssertValid(message = "")
    private SchoolRegistrationWithRemoteCirilnetenfanceRequestData schoolRegistrationWithRemoteCirilnetenfanceRequestData;

    public SchoolRegistrationWithRemoteCirilnetenfanceRequest(RequestData requestData, SchoolRegistrationWithRemoteCirilnetenfanceRequestData schoolRegistrationWithRemoteCirilnetenfanceRequestData) {
        super(requestData);
        this.schoolRegistrationWithRemoteCirilnetenfanceRequestData = schoolRegistrationWithRemoteCirilnetenfanceRequestData;
    }

    public SchoolRegistrationWithRemoteCirilnetenfanceRequest() {
        super();
        this.schoolRegistrationWithRemoteCirilnetenfanceRequestData = new SchoolRegistrationWithRemoteCirilnetenfanceRequestData();
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
    public SchoolRegistrationWithRemoteCirilnetenfanceRequestData getSpecificData() {
        return schoolRegistrationWithRemoteCirilnetenfanceRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(SchoolRegistrationWithRemoteCirilnetenfanceRequestData schoolRegistrationWithRemoteCirilnetenfanceRequestData) {
        this.schoolRegistrationWithRemoteCirilnetenfanceRequestData = schoolRegistrationWithRemoteCirilnetenfanceRequestData;
    }

    @Override
    public final String modelToXmlString() {
        SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument schoolRegistrationWithRemoteCirilnetenfanceRequestDoc = SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument.Factory.newInstance();
        SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument.SchoolRegistrationWithRemoteCirilnetenfanceRequest schoolRegistrationWithRemoteCirilnetenfanceRequest = schoolRegistrationWithRemoteCirilnetenfanceRequestDoc.addNewSchoolRegistrationWithRemoteCirilnetenfanceRequest();
        super.fillCommonXmlInfo(schoolRegistrationWithRemoteCirilnetenfanceRequest);
        int i = 0;
          SchoolSelected schoolSelectedTheSchool = schoolRegistrationWithRemoteCirilnetenfanceRequest.addNewTheSchool();
        schoolSelectedTheSchool.setIdSchoolName(getIdSchoolName());
      
        schoolSelectedTheSchool.setLabelSchoolName(getLabelSchoolName());
      
        if (getRulesAndRegulationsAcceptance() != null)
            schoolRegistrationWithRemoteCirilnetenfanceRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        if (getSection() != null)
            schoolRegistrationWithRemoteCirilnetenfanceRequest.setSection(org.libredemat.xml.common.SectionType.Enum.forString(getSection().getLegacyLabel()));
      
        return schoolRegistrationWithRemoteCirilnetenfanceRequestDoc;
    }

    @Override
    public final SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument.SchoolRegistrationWithRemoteCirilnetenfanceRequest modelToXmlRequest() {
        return modelToXml().getSchoolRegistrationWithRemoteCirilnetenfanceRequest();
    }

    public static SchoolRegistrationWithRemoteCirilnetenfanceRequest xmlToModel(SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument schoolRegistrationWithRemoteCirilnetenfanceRequestDoc) {
        SchoolRegistrationWithRemoteCirilnetenfanceRequestDocument.SchoolRegistrationWithRemoteCirilnetenfanceRequest schoolRegistrationWithRemoteCirilnetenfanceRequestXml = schoolRegistrationWithRemoteCirilnetenfanceRequestDoc.getSchoolRegistrationWithRemoteCirilnetenfanceRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        SchoolRegistrationWithRemoteCirilnetenfanceRequest schoolRegistrationWithRemoteCirilnetenfanceRequest = new SchoolRegistrationWithRemoteCirilnetenfanceRequest();
        schoolRegistrationWithRemoteCirilnetenfanceRequest.fillCommonModelInfo(schoolRegistrationWithRemoteCirilnetenfanceRequest, schoolRegistrationWithRemoteCirilnetenfanceRequestXml);
        
        schoolRegistrationWithRemoteCirilnetenfanceRequest.setIdSchoolName(schoolRegistrationWithRemoteCirilnetenfanceRequestXml.getTheSchool().getIdSchoolName());
      
        schoolRegistrationWithRemoteCirilnetenfanceRequest.setLabelSchoolName(schoolRegistrationWithRemoteCirilnetenfanceRequestXml.getTheSchool().getLabelSchoolName());
      
        schoolRegistrationWithRemoteCirilnetenfanceRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(schoolRegistrationWithRemoteCirilnetenfanceRequestXml.getRulesAndRegulationsAcceptance()));
      
        if (schoolRegistrationWithRemoteCirilnetenfanceRequestXml.getSection() != null)
            schoolRegistrationWithRemoteCirilnetenfanceRequest.setSection(org.libredemat.business.users.SectionType.forString(schoolRegistrationWithRemoteCirilnetenfanceRequestXml.getSection().toString()));
        else
            schoolRegistrationWithRemoteCirilnetenfanceRequest.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
        return schoolRegistrationWithRemoteCirilnetenfanceRequest;
    }

    @Override
    public SchoolRegistrationWithRemoteCirilnetenfanceRequest clone() {
        SchoolRegistrationWithRemoteCirilnetenfanceRequest clone = new SchoolRegistrationWithRemoteCirilnetenfanceRequest(getRequestData().clone(), schoolRegistrationWithRemoteCirilnetenfanceRequestData.clone());
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

  
    public final void setIdSchoolName(final String idSchoolName) {
        schoolRegistrationWithRemoteCirilnetenfanceRequestData.setIdSchoolName(idSchoolName);
    }

    
    public final String getIdSchoolName() {
        return schoolRegistrationWithRemoteCirilnetenfanceRequestData.getIdSchoolName();
    }
  
    public final void setLabelSchoolName(final String labelSchoolName) {
        schoolRegistrationWithRemoteCirilnetenfanceRequestData.setLabelSchoolName(labelSchoolName);
    }

    
    public final String getLabelSchoolName() {
        return schoolRegistrationWithRemoteCirilnetenfanceRequestData.getLabelSchoolName();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        schoolRegistrationWithRemoteCirilnetenfanceRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return schoolRegistrationWithRemoteCirilnetenfanceRequestData.getRulesAndRegulationsAcceptance();
    }
  
    public final void setSection(final org.libredemat.business.users.SectionType section) {
        schoolRegistrationWithRemoteCirilnetenfanceRequestData.setSection(section);
    }

    
    public final org.libredemat.business.users.SectionType getSection() {
        return schoolRegistrationWithRemoteCirilnetenfanceRequestData.getSection();
    }
  
}
