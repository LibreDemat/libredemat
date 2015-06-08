
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
public class RecreationActivityPolyRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = RecreationActivityPolyRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private RecreationActivityPolyRegistrationRequestData recreationActivityPolyRegistrationRequestData;

    public RecreationActivityPolyRegistrationRequest(RequestData requestData, RecreationActivityPolyRegistrationRequestData recreationActivityPolyRegistrationRequestData) {
        super(requestData);
        this.recreationActivityPolyRegistrationRequestData = recreationActivityPolyRegistrationRequestData;
    }

    public RecreationActivityPolyRegistrationRequest() {
        super();
        this.recreationActivityPolyRegistrationRequestData = new RecreationActivityPolyRegistrationRequestData();
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
          getStepStates().put("requester", stepState);
        
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
    public RecreationActivityPolyRegistrationRequestData getSpecificData() {
        return recreationActivityPolyRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(RecreationActivityPolyRegistrationRequestData recreationActivityPolyRegistrationRequestData) {
        this.recreationActivityPolyRegistrationRequestData = recreationActivityPolyRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        RecreationActivityPolyRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final RecreationActivityPolyRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        RecreationActivityPolyRegistrationRequestDocument recreationActivityPolyRegistrationRequestDoc = RecreationActivityPolyRegistrationRequestDocument.Factory.newInstance();
        RecreationActivityPolyRegistrationRequestDocument.RecreationActivityPolyRegistrationRequest recreationActivityPolyRegistrationRequest = recreationActivityPolyRegistrationRequestDoc.addNewRecreationActivityPolyRegistrationRequest();
        super.fillCommonXmlInfo(recreationActivityPolyRegistrationRequest);
        int i = 0;
        
        i = 0;
        if (getAuthorizedPolyIndividuals() != null) {
            org.libredemat.xml.request.school.RecreationAuthorizedPolyIndividualType[] authorizedPolyIndividualsTypeTab = new org.libredemat.xml.request.school.RecreationAuthorizedPolyIndividualType[getAuthorizedPolyIndividuals().size()];
            for (RecreationAuthorizedPolyIndividual object : getAuthorizedPolyIndividuals()) {
              authorizedPolyIndividualsTypeTab[i++] = object.modelToXml();
            }
            recreationActivityPolyRegistrationRequest.setAuthorizedPolyIndividualsArray(authorizedPolyIndividualsTypeTab);
        }
      
        if (getChildPhotoExploitationPolyPermission() != null)
            recreationActivityPolyRegistrationRequest.setChildPhotoExploitationPolyPermission(getChildPhotoExploitationPolyPermission().booleanValue());
      
        if (getClassTripPolyPermission() != null)
            recreationActivityPolyRegistrationRequest.setClassTripPolyPermission(getClassTripPolyPermission().booleanValue());
      
        i = 0;
        if (getContactPolyIndividuals() != null) {
            org.libredemat.xml.request.school.RecreationContactPolyIndividualType[] contactPolyIndividualsTypeTab = new org.libredemat.xml.request.school.RecreationContactPolyIndividualType[getContactPolyIndividuals().size()];
            for (RecreationContactPolyIndividual object : getContactPolyIndividuals()) {
              contactPolyIndividualsTypeTab[i++] = object.modelToXml();
            }
            recreationActivityPolyRegistrationRequest.setContactPolyIndividualsArray(contactPolyIndividualsTypeTab);
        }
      
        if (getHospitalizationPolyPermission() != null)
            recreationActivityPolyRegistrationRequest.setHospitalizationPolyPermission(getHospitalizationPolyPermission().booleanValue());
      
        i = 0;
        if (getRecreationPolyActivity() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] recreationPolyActivityTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getRecreationPolyActivity().size()];
            for (LocalReferentialData object : getRecreationPolyActivity()) {
              recreationPolyActivityTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            recreationActivityPolyRegistrationRequest.setRecreationPolyActivityArray(recreationPolyActivityTypeTab);
        }
      
        if (getRecreationPolyCenter() != null)
            recreationActivityPolyRegistrationRequest.setRecreationPolyCenter(RecreationCenter.modelToXml(getRecreationPolyCenter()));
      
        if (getRulesAndRegulationsPolyAcceptance() != null)
            recreationActivityPolyRegistrationRequest.setRulesAndRegulationsPolyAcceptance(getRulesAndRegulationsPolyAcceptance().booleanValue());
      
        recreationActivityPolyRegistrationRequest.setUrgencyPolyPhone(getUrgencyPolyPhone());
      
        return recreationActivityPolyRegistrationRequestDoc;
    }

    @Override
    public final RecreationActivityPolyRegistrationRequestDocument.RecreationActivityPolyRegistrationRequest modelToXmlRequest() {
        return modelToXml().getRecreationActivityPolyRegistrationRequest();
    }

    public static RecreationActivityPolyRegistrationRequest xmlToModel(RecreationActivityPolyRegistrationRequestDocument recreationActivityPolyRegistrationRequestDoc) {
        RecreationActivityPolyRegistrationRequestDocument.RecreationActivityPolyRegistrationRequest recreationActivityPolyRegistrationRequestXml = recreationActivityPolyRegistrationRequestDoc.getRecreationActivityPolyRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        RecreationActivityPolyRegistrationRequest recreationActivityPolyRegistrationRequest = new RecreationActivityPolyRegistrationRequest();
        recreationActivityPolyRegistrationRequest.fillCommonModelInfo(recreationActivityPolyRegistrationRequest, recreationActivityPolyRegistrationRequestXml);
        
        List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> authorizedPolyIndividualsList = new ArrayList<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual>(recreationActivityPolyRegistrationRequestXml.sizeOfAuthorizedPolyIndividualsArray());
        for (RecreationAuthorizedPolyIndividualType object : recreationActivityPolyRegistrationRequestXml.getAuthorizedPolyIndividualsArray()) {
            authorizedPolyIndividualsList.add(org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual.xmlToModel(object));
        }
        recreationActivityPolyRegistrationRequest.setAuthorizedPolyIndividuals(authorizedPolyIndividualsList);
      
        recreationActivityPolyRegistrationRequest.setChildPhotoExploitationPolyPermission(Boolean.valueOf(recreationActivityPolyRegistrationRequestXml.getChildPhotoExploitationPolyPermission()));
      
        recreationActivityPolyRegistrationRequest.setClassTripPolyPermission(Boolean.valueOf(recreationActivityPolyRegistrationRequestXml.getClassTripPolyPermission()));
      
        List<org.libredemat.business.request.school.RecreationContactPolyIndividual> contactPolyIndividualsList = new ArrayList<org.libredemat.business.request.school.RecreationContactPolyIndividual>(recreationActivityPolyRegistrationRequestXml.sizeOfContactPolyIndividualsArray());
        for (RecreationContactPolyIndividualType object : recreationActivityPolyRegistrationRequestXml.getContactPolyIndividualsArray()) {
            contactPolyIndividualsList.add(org.libredemat.business.request.school.RecreationContactPolyIndividual.xmlToModel(object));
        }
        recreationActivityPolyRegistrationRequest.setContactPolyIndividuals(contactPolyIndividualsList);
      
        recreationActivityPolyRegistrationRequest.setHospitalizationPolyPermission(Boolean.valueOf(recreationActivityPolyRegistrationRequestXml.getHospitalizationPolyPermission()));
      
        List<org.libredemat.business.request.LocalReferentialData> recreationPolyActivityList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(recreationActivityPolyRegistrationRequestXml.sizeOfRecreationPolyActivityArray());
        for (LocalReferentialDataType object : recreationActivityPolyRegistrationRequestXml.getRecreationPolyActivityArray()) {
            recreationPolyActivityList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        recreationActivityPolyRegistrationRequest.setRecreationPolyActivity(recreationPolyActivityList);
      
        if (recreationActivityPolyRegistrationRequestXml.getRecreationPolyCenter() != null)
            recreationActivityPolyRegistrationRequest.setRecreationPolyCenter(RecreationCenter.xmlToModel(recreationActivityPolyRegistrationRequestXml.getRecreationPolyCenter()));
      
        recreationActivityPolyRegistrationRequest.setRulesAndRegulationsPolyAcceptance(Boolean.valueOf(recreationActivityPolyRegistrationRequestXml.getRulesAndRegulationsPolyAcceptance()));
      
        recreationActivityPolyRegistrationRequest.setUrgencyPolyPhone(recreationActivityPolyRegistrationRequestXml.getUrgencyPolyPhone());
      
        return recreationActivityPolyRegistrationRequest;
    }

    @Override
    public RecreationActivityPolyRegistrationRequest clone() {
        RecreationActivityPolyRegistrationRequest clone = new RecreationActivityPolyRegistrationRequest(getRequestData().clone(), recreationActivityPolyRegistrationRequestData.clone());
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
          clone.getStepStates().put("requester", stepState);
        
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

  
    public final void setAuthorizedPolyIndividuals(final List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> authorizedPolyIndividuals) {
        recreationActivityPolyRegistrationRequestData.setAuthorizedPolyIndividuals(authorizedPolyIndividuals);
    }

    
    public final List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> getAuthorizedPolyIndividuals() {
        return recreationActivityPolyRegistrationRequestData.getAuthorizedPolyIndividuals();
    }
  
    public final void setChildPhotoExploitationPolyPermission(final Boolean childPhotoExploitationPolyPermission) {
        recreationActivityPolyRegistrationRequestData.setChildPhotoExploitationPolyPermission(childPhotoExploitationPolyPermission);
    }

    @IsRulesAcceptance
    public final Boolean getChildPhotoExploitationPolyPermission() {
        return recreationActivityPolyRegistrationRequestData.getChildPhotoExploitationPolyPermission();
    }
  
    public final void setClassTripPolyPermission(final Boolean classTripPolyPermission) {
        recreationActivityPolyRegistrationRequestData.setClassTripPolyPermission(classTripPolyPermission);
    }

    @IsRulesAcceptance
    public final Boolean getClassTripPolyPermission() {
        return recreationActivityPolyRegistrationRequestData.getClassTripPolyPermission();
    }
  
    public final void setContactPolyIndividuals(final List<org.libredemat.business.request.school.RecreationContactPolyIndividual> contactPolyIndividuals) {
        recreationActivityPolyRegistrationRequestData.setContactPolyIndividuals(contactPolyIndividuals);
    }

    
    public final List<org.libredemat.business.request.school.RecreationContactPolyIndividual> getContactPolyIndividuals() {
        return recreationActivityPolyRegistrationRequestData.getContactPolyIndividuals();
    }
  
    public final void setHospitalizationPolyPermission(final Boolean hospitalizationPolyPermission) {
        recreationActivityPolyRegistrationRequestData.setHospitalizationPolyPermission(hospitalizationPolyPermission);
    }

    @IsRulesAcceptance
    public final Boolean getHospitalizationPolyPermission() {
        return recreationActivityPolyRegistrationRequestData.getHospitalizationPolyPermission();
    }
  
    public final void setRecreationPolyActivity(final List<org.libredemat.business.request.LocalReferentialData> recreationPolyActivity) {
        recreationActivityPolyRegistrationRequestData.setRecreationPolyActivity(recreationPolyActivity);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getRecreationPolyActivity() {
        return recreationActivityPolyRegistrationRequestData.getRecreationPolyActivity();
    }
  
    public final void setRecreationPolyCenter(final org.libredemat.business.authority.RecreationCenter recreationPolyCenter) {
        recreationActivityPolyRegistrationRequestData.setRecreationPolyCenter(recreationPolyCenter);
    }

    
    public final org.libredemat.business.authority.RecreationCenter getRecreationPolyCenter() {
        return recreationActivityPolyRegistrationRequestData.getRecreationPolyCenter();
    }
  
    public final void setRulesAndRegulationsPolyAcceptance(final Boolean rulesAndRegulationsPolyAcceptance) {
        recreationActivityPolyRegistrationRequestData.setRulesAndRegulationsPolyAcceptance(rulesAndRegulationsPolyAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsPolyAcceptance() {
        return recreationActivityPolyRegistrationRequestData.getRulesAndRegulationsPolyAcceptance();
    }
  
    public final void setUrgencyPolyPhone(final String urgencyPolyPhone) {
        recreationActivityPolyRegistrationRequestData.setUrgencyPolyPhone(urgencyPolyPhone);
    }

    
    public final String getUrgencyPolyPhone() {
        return recreationActivityPolyRegistrationRequestData.getUrgencyPolyPhone();
    }
  
}
