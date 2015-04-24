
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
public class PerischoolActivityRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = PerischoolActivityRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private PerischoolActivityRegistrationRequestData perischoolActivityRegistrationRequestData;

    public PerischoolActivityRegistrationRequest(RequestData requestData, PerischoolActivityRegistrationRequestData perischoolActivityRegistrationRequestData) {
        super(requestData);
        this.perischoolActivityRegistrationRequestData = perischoolActivityRegistrationRequestData;
    }

    public PerischoolActivityRegistrationRequest() {
        super();
        this.perischoolActivityRegistrationRequestData = new PerischoolActivityRegistrationRequestData();
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
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("contact", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("authorization", stepState);
        
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
    public PerischoolActivityRegistrationRequestData getSpecificData() {
        return perischoolActivityRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(PerischoolActivityRegistrationRequestData perischoolActivityRegistrationRequestData) {
        this.perischoolActivityRegistrationRequestData = perischoolActivityRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        PerischoolActivityRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final PerischoolActivityRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        PerischoolActivityRegistrationRequestDocument perischoolActivityRegistrationRequestDoc = PerischoolActivityRegistrationRequestDocument.Factory.newInstance();
        PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest perischoolActivityRegistrationRequest = perischoolActivityRegistrationRequestDoc.addNewPerischoolActivityRegistrationRequest();
        super.fillCommonXmlInfo(perischoolActivityRegistrationRequest);
        int i = 0;
        
        i = 0;
        if (getAuthorizedIndividuals() != null) {
            org.libredemat.xml.request.school.PerischoolAuthorizedIndividualType[] authorizedIndividualsTypeTab = new org.libredemat.xml.request.school.PerischoolAuthorizedIndividualType[getAuthorizedIndividuals().size()];
            for (PerischoolAuthorizedIndividual object : getAuthorizedIndividuals()) {
              authorizedIndividualsTypeTab[i++] = object.modelToXml();
            }
            perischoolActivityRegistrationRequest.setAuthorizedIndividualsArray(authorizedIndividualsTypeTab);
        }
      
        if (getChildPhotoExploitationPermission() != null)
            perischoolActivityRegistrationRequest.setChildPhotoExploitationPermission(getChildPhotoExploitationPermission().booleanValue());
      
        if (getClassTripPermission() != null)
            perischoolActivityRegistrationRequest.setClassTripPermission(getClassTripPermission().booleanValue());
      
        i = 0;
        if (getContactIndividuals() != null) {
            org.libredemat.xml.request.school.PerischoolContactIndividualType[] contactIndividualsTypeTab = new org.libredemat.xml.request.school.PerischoolContactIndividualType[getContactIndividuals().size()];
            for (PerischoolContactIndividual object : getContactIndividuals()) {
              contactIndividualsTypeTab[i++] = object.modelToXml();
            }
            perischoolActivityRegistrationRequest.setContactIndividualsArray(contactIndividualsTypeTab);
        }
      
        if (getHospitalizationPermission() != null)
            perischoolActivityRegistrationRequest.setHospitalizationPermission(getHospitalizationPermission().booleanValue());
      
        i = 0;
        if (getPerischoolActivity() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] perischoolActivityTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getPerischoolActivity().size()];
            for (LocalReferentialData object : getPerischoolActivity()) {
              perischoolActivityTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            perischoolActivityRegistrationRequest.setPerischoolActivityArray(perischoolActivityTypeTab);
        }
      
        if (getRulesAndRegulationsAcceptance() != null)
            perischoolActivityRegistrationRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        if (getSchool() != null)
            perischoolActivityRegistrationRequest.setSchool(School.modelToXml(getSchool()));
      
        if (getSection() != null)
            perischoolActivityRegistrationRequest.setSection(org.libredemat.xml.common.SectionType.Enum.forString(getSection().getLegacyLabel()));
      
        perischoolActivityRegistrationRequest.setUrgencyPhone(getUrgencyPhone());
      
        return perischoolActivityRegistrationRequestDoc;
    }

    @Override
    public final PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest modelToXmlRequest() {
        return modelToXml().getPerischoolActivityRegistrationRequest();
    }

    public static PerischoolActivityRegistrationRequest xmlToModel(PerischoolActivityRegistrationRequestDocument perischoolActivityRegistrationRequestDoc) {
        PerischoolActivityRegistrationRequestDocument.PerischoolActivityRegistrationRequest perischoolActivityRegistrationRequestXml = perischoolActivityRegistrationRequestDoc.getPerischoolActivityRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        PerischoolActivityRegistrationRequest perischoolActivityRegistrationRequest = new PerischoolActivityRegistrationRequest();
        perischoolActivityRegistrationRequest.fillCommonModelInfo(perischoolActivityRegistrationRequest, perischoolActivityRegistrationRequestXml);
        
        List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> authorizedIndividualsList = new ArrayList<org.libredemat.business.request.school.PerischoolAuthorizedIndividual>(perischoolActivityRegistrationRequestXml.sizeOfAuthorizedIndividualsArray());
        for (PerischoolAuthorizedIndividualType object : perischoolActivityRegistrationRequestXml.getAuthorizedIndividualsArray()) {
            authorizedIndividualsList.add(org.libredemat.business.request.school.PerischoolAuthorizedIndividual.xmlToModel(object));
        }
        perischoolActivityRegistrationRequest.setAuthorizedIndividuals(authorizedIndividualsList);
      
        perischoolActivityRegistrationRequest.setChildPhotoExploitationPermission(Boolean.valueOf(perischoolActivityRegistrationRequestXml.getChildPhotoExploitationPermission()));
      
        perischoolActivityRegistrationRequest.setClassTripPermission(Boolean.valueOf(perischoolActivityRegistrationRequestXml.getClassTripPermission()));
      
        List<org.libredemat.business.request.school.PerischoolContactIndividual> contactIndividualsList = new ArrayList<org.libredemat.business.request.school.PerischoolContactIndividual>(perischoolActivityRegistrationRequestXml.sizeOfContactIndividualsArray());
        for (PerischoolContactIndividualType object : perischoolActivityRegistrationRequestXml.getContactIndividualsArray()) {
            contactIndividualsList.add(org.libredemat.business.request.school.PerischoolContactIndividual.xmlToModel(object));
        }
        perischoolActivityRegistrationRequest.setContactIndividuals(contactIndividualsList);
      
        perischoolActivityRegistrationRequest.setHospitalizationPermission(Boolean.valueOf(perischoolActivityRegistrationRequestXml.getHospitalizationPermission()));
      
        List<org.libredemat.business.request.LocalReferentialData> perischoolActivityList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(perischoolActivityRegistrationRequestXml.sizeOfPerischoolActivityArray());
        for (LocalReferentialDataType object : perischoolActivityRegistrationRequestXml.getPerischoolActivityArray()) {
            perischoolActivityList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        perischoolActivityRegistrationRequest.setPerischoolActivity(perischoolActivityList);
      
        perischoolActivityRegistrationRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(perischoolActivityRegistrationRequestXml.getRulesAndRegulationsAcceptance()));
      
        if (perischoolActivityRegistrationRequestXml.getSchool() != null)
            perischoolActivityRegistrationRequest.setSchool(School.xmlToModel(perischoolActivityRegistrationRequestXml.getSchool()));
      
        if (perischoolActivityRegistrationRequestXml.getSection() != null)
            perischoolActivityRegistrationRequest.setSection(org.libredemat.business.users.SectionType.forString(perischoolActivityRegistrationRequestXml.getSection().toString()));
        else
            perischoolActivityRegistrationRequest.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
        perischoolActivityRegistrationRequest.setUrgencyPhone(perischoolActivityRegistrationRequestXml.getUrgencyPhone());
      
        return perischoolActivityRegistrationRequest;
    }

    @Override
    public PerischoolActivityRegistrationRequest clone() {
        PerischoolActivityRegistrationRequest clone = new PerischoolActivityRegistrationRequest(getRequestData().clone(), perischoolActivityRegistrationRequestData.clone());
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
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("contact", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("authorization", stepState);
        
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

  
    public final void setAuthorizedIndividuals(final List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> authorizedIndividuals) {
        perischoolActivityRegistrationRequestData.setAuthorizedIndividuals(authorizedIndividuals);
    }

    
    public final List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> getAuthorizedIndividuals() {
        return perischoolActivityRegistrationRequestData.getAuthorizedIndividuals();
    }
  
    public final void setChildPhotoExploitationPermission(final Boolean childPhotoExploitationPermission) {
        perischoolActivityRegistrationRequestData.setChildPhotoExploitationPermission(childPhotoExploitationPermission);
    }

    @IsRulesAcceptance
    public final Boolean getChildPhotoExploitationPermission() {
        return perischoolActivityRegistrationRequestData.getChildPhotoExploitationPermission();
    }
  
    public final void setClassTripPermission(final Boolean classTripPermission) {
        perischoolActivityRegistrationRequestData.setClassTripPermission(classTripPermission);
    }

    @IsRulesAcceptance
    public final Boolean getClassTripPermission() {
        return perischoolActivityRegistrationRequestData.getClassTripPermission();
    }
  
    public final void setContactIndividuals(final List<org.libredemat.business.request.school.PerischoolContactIndividual> contactIndividuals) {
        perischoolActivityRegistrationRequestData.setContactIndividuals(contactIndividuals);
    }

    
    public final List<org.libredemat.business.request.school.PerischoolContactIndividual> getContactIndividuals() {
        return perischoolActivityRegistrationRequestData.getContactIndividuals();
    }
  
    public final void setHospitalizationPermission(final Boolean hospitalizationPermission) {
        perischoolActivityRegistrationRequestData.setHospitalizationPermission(hospitalizationPermission);
    }

    @IsRulesAcceptance
    public final Boolean getHospitalizationPermission() {
        return perischoolActivityRegistrationRequestData.getHospitalizationPermission();
    }
  
    public final void setPerischoolActivity(final List<org.libredemat.business.request.LocalReferentialData> perischoolActivity) {
        perischoolActivityRegistrationRequestData.setPerischoolActivity(perischoolActivity);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getPerischoolActivity() {
        return perischoolActivityRegistrationRequestData.getPerischoolActivity();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        perischoolActivityRegistrationRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return perischoolActivityRegistrationRequestData.getRulesAndRegulationsAcceptance();
    }
  
    public final void setSchool(final org.libredemat.business.authority.School school) {
        perischoolActivityRegistrationRequestData.setSchool(school);
    }

    
    public final org.libredemat.business.authority.School getSchool() {
        return perischoolActivityRegistrationRequestData.getSchool();
    }
  
    public final void setSection(final org.libredemat.business.users.SectionType section) {
        perischoolActivityRegistrationRequestData.setSection(section);
    }

    
    public final org.libredemat.business.users.SectionType getSection() {
        return perischoolActivityRegistrationRequestData.getSection();
    }
  
    public final void setUrgencyPhone(final String urgencyPhone) {
        perischoolActivityRegistrationRequestData.setUrgencyPhone(urgencyPhone);
    }

    
    public final String getUrgencyPhone() {
        return perischoolActivityRegistrationRequestData.getUrgencyPhone();
    }
  
}
