
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
public class SchoolCanteenRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = SchoolCanteenRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private SchoolCanteenRegistrationRequestData schoolCanteenRegistrationRequestData;

    public SchoolCanteenRegistrationRequest(RequestData requestData, SchoolCanteenRegistrationRequestData schoolCanteenRegistrationRequestData) {
        super(requestData);
        this.schoolCanteenRegistrationRequestData = schoolCanteenRegistrationRequestData;
    }

    public SchoolCanteenRegistrationRequest() {
        super();
        this.schoolCanteenRegistrationRequestData = new SchoolCanteenRegistrationRequestData();
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
    public SchoolCanteenRegistrationRequestData getSpecificData() {
        return schoolCanteenRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(SchoolCanteenRegistrationRequestData schoolCanteenRegistrationRequestData) {
        this.schoolCanteenRegistrationRequestData = schoolCanteenRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        SchoolCanteenRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final SchoolCanteenRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        SchoolCanteenRegistrationRequestDocument schoolCanteenRegistrationRequestDoc = SchoolCanteenRegistrationRequestDocument.Factory.newInstance();
        SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest schoolCanteenRegistrationRequest = schoolCanteenRegistrationRequestDoc.addNewSchoolCanteenRegistrationRequest();
        super.fillCommonXmlInfo(schoolCanteenRegistrationRequest);
        int i = 0;
        
        i = 0;
        if (getCanteenAttendingDays() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] canteenAttendingDaysTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getCanteenAttendingDays().size()];
            for (LocalReferentialData object : getCanteenAttendingDays()) {
              canteenAttendingDaysTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            schoolCanteenRegistrationRequest.setCanteenAttendingDaysArray(canteenAttendingDaysTypeTab);
        }
      
        schoolCanteenRegistrationRequest.setDoctorName(getDoctorName());
      
        schoolCanteenRegistrationRequest.setDoctorPhone(getDoctorPhone());
      
        if (getFoodAllergy() != null)
            schoolCanteenRegistrationRequest.setFoodAllergy(getFoodAllergy().booleanValue());
      
        i = 0;
        if (getFoodDiet() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] foodDietTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getFoodDiet().size()];
            for (LocalReferentialData object : getFoodDiet()) {
              foodDietTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            schoolCanteenRegistrationRequest.setFoodDietArray(foodDietTypeTab);
        }
      
        if (getHospitalizationPermission() != null)
            schoolCanteenRegistrationRequest.setHospitalizationPermission(getHospitalizationPermission().booleanValue());
      
        if (getRulesAndRegulationsAcceptance() != null)
            schoolCanteenRegistrationRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        if (getSchool() != null)
            schoolCanteenRegistrationRequest.setSchool(School.modelToXml(getSchool()));
      
        if (getSection() != null)
            schoolCanteenRegistrationRequest.setSection(org.libredemat.xml.common.SectionType.Enum.forString(getSection().getLegacyLabel()));
      
        schoolCanteenRegistrationRequest.setUrgencyPhone(getUrgencyPhone());
      
        schoolCanteenRegistrationRequest.setWhichFoodAllergy(getWhichFoodAllergy());
      
        return schoolCanteenRegistrationRequestDoc;
    }

    @Override
    public final SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest modelToXmlRequest() {
        return modelToXml().getSchoolCanteenRegistrationRequest();
    }

    public static SchoolCanteenRegistrationRequest xmlToModel(SchoolCanteenRegistrationRequestDocument schoolCanteenRegistrationRequestDoc) {
        SchoolCanteenRegistrationRequestDocument.SchoolCanteenRegistrationRequest schoolCanteenRegistrationRequestXml = schoolCanteenRegistrationRequestDoc.getSchoolCanteenRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        SchoolCanteenRegistrationRequest schoolCanteenRegistrationRequest = new SchoolCanteenRegistrationRequest();
        schoolCanteenRegistrationRequest.fillCommonModelInfo(schoolCanteenRegistrationRequest, schoolCanteenRegistrationRequestXml);
        
        List<org.libredemat.business.request.LocalReferentialData> canteenAttendingDaysList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(schoolCanteenRegistrationRequestXml.sizeOfCanteenAttendingDaysArray());
        for (LocalReferentialDataType object : schoolCanteenRegistrationRequestXml.getCanteenAttendingDaysArray()) {
            canteenAttendingDaysList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        schoolCanteenRegistrationRequest.setCanteenAttendingDays(canteenAttendingDaysList);
      
        schoolCanteenRegistrationRequest.setDoctorName(schoolCanteenRegistrationRequestXml.getDoctorName());
      
        schoolCanteenRegistrationRequest.setDoctorPhone(schoolCanteenRegistrationRequestXml.getDoctorPhone());
      
        schoolCanteenRegistrationRequest.setFoodAllergy(Boolean.valueOf(schoolCanteenRegistrationRequestXml.getFoodAllergy()));
      
        List<org.libredemat.business.request.LocalReferentialData> foodDietList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(schoolCanteenRegistrationRequestXml.sizeOfFoodDietArray());
        for (LocalReferentialDataType object : schoolCanteenRegistrationRequestXml.getFoodDietArray()) {
            foodDietList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        schoolCanteenRegistrationRequest.setFoodDiet(foodDietList);
      
        schoolCanteenRegistrationRequest.setHospitalizationPermission(Boolean.valueOf(schoolCanteenRegistrationRequestXml.getHospitalizationPermission()));
      
        schoolCanteenRegistrationRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(schoolCanteenRegistrationRequestXml.getRulesAndRegulationsAcceptance()));
      
        if (schoolCanteenRegistrationRequestXml.getSchool() != null)
            schoolCanteenRegistrationRequest.setSchool(School.xmlToModel(schoolCanteenRegistrationRequestXml.getSchool()));
      
        if (schoolCanteenRegistrationRequestXml.getSection() != null)
            schoolCanteenRegistrationRequest.setSection(org.libredemat.business.users.SectionType.forString(schoolCanteenRegistrationRequestXml.getSection().toString()));
        else
            schoolCanteenRegistrationRequest.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
        schoolCanteenRegistrationRequest.setUrgencyPhone(schoolCanteenRegistrationRequestXml.getUrgencyPhone());
      
        schoolCanteenRegistrationRequest.setWhichFoodAllergy(schoolCanteenRegistrationRequestXml.getWhichFoodAllergy());
      
        return schoolCanteenRegistrationRequest;
    }

    @Override
    public SchoolCanteenRegistrationRequest clone() {
        SchoolCanteenRegistrationRequest clone = new SchoolCanteenRegistrationRequest(getRequestData().clone(), schoolCanteenRegistrationRequestData.clone());
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

  
    public final void setCanteenAttendingDays(final List<org.libredemat.business.request.LocalReferentialData> canteenAttendingDays) {
        schoolCanteenRegistrationRequestData.setCanteenAttendingDays(canteenAttendingDays);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getCanteenAttendingDays() {
        return schoolCanteenRegistrationRequestData.getCanteenAttendingDays();
    }
  
    public final void setDoctorName(final String doctorName) {
        schoolCanteenRegistrationRequestData.setDoctorName(doctorName);
    }

    
    public final String getDoctorName() {
        return schoolCanteenRegistrationRequestData.getDoctorName();
    }
  
    public final void setDoctorPhone(final String doctorPhone) {
        schoolCanteenRegistrationRequestData.setDoctorPhone(doctorPhone);
    }

    
    public final String getDoctorPhone() {
        return schoolCanteenRegistrationRequestData.getDoctorPhone();
    }
  
    public final void setFoodAllergy(final Boolean foodAllergy) {
        schoolCanteenRegistrationRequestData.setFoodAllergy(foodAllergy);
    }

    
    public final Boolean getFoodAllergy() {
        return schoolCanteenRegistrationRequestData.getFoodAllergy();
    }
  
    public final void setFoodDiet(final List<org.libredemat.business.request.LocalReferentialData> foodDiet) {
        schoolCanteenRegistrationRequestData.setFoodDiet(foodDiet);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getFoodDiet() {
        return schoolCanteenRegistrationRequestData.getFoodDiet();
    }
  
    public final void setHospitalizationPermission(final Boolean hospitalizationPermission) {
        schoolCanteenRegistrationRequestData.setHospitalizationPermission(hospitalizationPermission);
    }

    @IsRulesAcceptance
    public final Boolean getHospitalizationPermission() {
        return schoolCanteenRegistrationRequestData.getHospitalizationPermission();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        schoolCanteenRegistrationRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return schoolCanteenRegistrationRequestData.getRulesAndRegulationsAcceptance();
    }
  
    public final void setSchool(final org.libredemat.business.authority.School school) {
        schoolCanteenRegistrationRequestData.setSchool(school);
    }

    
    public final org.libredemat.business.authority.School getSchool() {
        return schoolCanteenRegistrationRequestData.getSchool();
    }
  
    public final void setSection(final org.libredemat.business.users.SectionType section) {
        schoolCanteenRegistrationRequestData.setSection(section);
    }

    
    public final org.libredemat.business.users.SectionType getSection() {
        return schoolCanteenRegistrationRequestData.getSection();
    }
  
    public final void setUrgencyPhone(final String urgencyPhone) {
        schoolCanteenRegistrationRequestData.setUrgencyPhone(urgencyPhone);
    }

    
    public final String getUrgencyPhone() {
        return schoolCanteenRegistrationRequestData.getUrgencyPhone();
    }
  
    public final void setWhichFoodAllergy(final String whichFoodAllergy) {
        schoolCanteenRegistrationRequestData.setWhichFoodAllergy(whichFoodAllergy);
    }

    
    public final String getWhichFoodAllergy() {
        return schoolCanteenRegistrationRequestData.getWhichFoodAllergy();
    }
  
}
