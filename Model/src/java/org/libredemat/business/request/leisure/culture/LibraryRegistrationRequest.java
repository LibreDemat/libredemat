
package org.libredemat.business.request.leisure.culture;

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
import org.libredemat.xml.request.leisure.culture.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class LibraryRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = LibraryRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private LibraryRegistrationRequestData libraryRegistrationRequestData;

    public LibraryRegistrationRequest(RequestData requestData, LibraryRegistrationRequestData libraryRegistrationRequestData) {
        super(requestData);
        this.libraryRegistrationRequestData = libraryRegistrationRequestData;
    }

    public LibraryRegistrationRequest() {
        super();
        this.libraryRegistrationRequestData = new LibraryRegistrationRequestData();
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
    public LibraryRegistrationRequestData getSpecificData() {
        return libraryRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(LibraryRegistrationRequestData libraryRegistrationRequestData) {
        this.libraryRegistrationRequestData = libraryRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        LibraryRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final LibraryRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        LibraryRegistrationRequestDocument libraryRegistrationRequestDoc = LibraryRegistrationRequestDocument.Factory.newInstance();
        LibraryRegistrationRequestDocument.LibraryRegistrationRequest libraryRegistrationRequest = libraryRegistrationRequestDoc.addNewLibraryRegistrationRequest();
        super.fillCommonXmlInfo(libraryRegistrationRequest);
        int i = 0;
        
        if (getAdultContentAuthorization() != null)
            libraryRegistrationRequest.setAdultContentAuthorization(getAdultContentAuthorization().booleanValue());
      
        if (getParentalAuthorization() != null)
            libraryRegistrationRequest.setParentalAuthorization(getParentalAuthorization().booleanValue());
      
        libraryRegistrationRequest.setRegistrationNumber(getRegistrationNumber());
      
        if (getRulesAndRegulationsAcceptance() != null)
            libraryRegistrationRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        i = 0;
        if (getSubscription() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] subscriptionTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getSubscription().size()];
            for (LocalReferentialData object : getSubscription()) {
              subscriptionTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            libraryRegistrationRequest.setSubscriptionArray(subscriptionTypeTab);
        }
      
        if (getSubscriptionPrice() != null)
            libraryRegistrationRequest.setSubscriptionPrice(getSubscriptionPrice());
      
        return libraryRegistrationRequestDoc;
    }

    @Override
    public final LibraryRegistrationRequestDocument.LibraryRegistrationRequest modelToXmlRequest() {
        return modelToXml().getLibraryRegistrationRequest();
    }

    public static LibraryRegistrationRequest xmlToModel(LibraryRegistrationRequestDocument libraryRegistrationRequestDoc) {
        LibraryRegistrationRequestDocument.LibraryRegistrationRequest libraryRegistrationRequestXml = libraryRegistrationRequestDoc.getLibraryRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        LibraryRegistrationRequest libraryRegistrationRequest = new LibraryRegistrationRequest();
        libraryRegistrationRequest.fillCommonModelInfo(libraryRegistrationRequest, libraryRegistrationRequestXml);
        
        libraryRegistrationRequest.setAdultContentAuthorization(Boolean.valueOf(libraryRegistrationRequestXml.getAdultContentAuthorization()));
      
        libraryRegistrationRequest.setParentalAuthorization(Boolean.valueOf(libraryRegistrationRequestXml.getParentalAuthorization()));
      
        libraryRegistrationRequest.setRegistrationNumber(libraryRegistrationRequestXml.getRegistrationNumber());
      
        libraryRegistrationRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(libraryRegistrationRequestXml.getRulesAndRegulationsAcceptance()));
      
        List<org.libredemat.business.request.LocalReferentialData> subscriptionList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(libraryRegistrationRequestXml.sizeOfSubscriptionArray());
        for (LocalReferentialDataType object : libraryRegistrationRequestXml.getSubscriptionArray()) {
            subscriptionList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        libraryRegistrationRequest.setSubscription(subscriptionList);
      
        if (libraryRegistrationRequestXml.getSubscriptionPrice() != null)
            libraryRegistrationRequest.setSubscriptionPrice(libraryRegistrationRequestXml.getSubscriptionPrice());
      
        return libraryRegistrationRequest;
    }

    @Override
    public LibraryRegistrationRequest clone() {
        LibraryRegistrationRequest clone = new LibraryRegistrationRequest(getRequestData().clone(), libraryRegistrationRequestData.clone());
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

  
    public final void setAdultContentAuthorization(final Boolean adultContentAuthorization) {
        libraryRegistrationRequestData.setAdultContentAuthorization(adultContentAuthorization);
    }

    @IsRulesAcceptance
    public final Boolean getAdultContentAuthorization() {
        return libraryRegistrationRequestData.getAdultContentAuthorization();
    }
  
    public final void setParentalAuthorization(final Boolean parentalAuthorization) {
        libraryRegistrationRequestData.setParentalAuthorization(parentalAuthorization);
    }

    @IsRulesAcceptance
    public final Boolean getParentalAuthorization() {
        return libraryRegistrationRequestData.getParentalAuthorization();
    }
  
    public final void setRegistrationNumber(final String registrationNumber) {
        libraryRegistrationRequestData.setRegistrationNumber(registrationNumber);
    }

    
    public final String getRegistrationNumber() {
        return libraryRegistrationRequestData.getRegistrationNumber();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        libraryRegistrationRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return libraryRegistrationRequestData.getRulesAndRegulationsAcceptance();
    }
  
    public final void setSubscription(final List<org.libredemat.business.request.LocalReferentialData> subscription) {
        libraryRegistrationRequestData.setSubscription(subscription);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getSubscription() {
        return libraryRegistrationRequestData.getSubscription();
    }
  
    public final void setSubscriptionPrice(final java.math.BigDecimal subscriptionPrice) {
        libraryRegistrationRequestData.setSubscriptionPrice(subscriptionPrice);
    }

    
    public final java.math.BigDecimal getSubscriptionPrice() {
        return libraryRegistrationRequestData.getSubscriptionPrice();
    }
  
}
