
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
public class ChildCareCenterRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = ChildCareCenterRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private ChildCareCenterRegistrationRequestData childCareCenterRegistrationRequestData;

    public ChildCareCenterRegistrationRequest(RequestData requestData, ChildCareCenterRegistrationRequestData childCareCenterRegistrationRequestData) {
        super(requestData);
        this.childCareCenterRegistrationRequestData = childCareCenterRegistrationRequestData;
    }

    public ChildCareCenterRegistrationRequest() {
        super();
        this.childCareCenterRegistrationRequestData = new ChildCareCenterRegistrationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("registrationSubject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("registrationParams", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("welcoming", stepState);
        
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
    public ChildCareCenterRegistrationRequestData getSpecificData() {
        return childCareCenterRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(ChildCareCenterRegistrationRequestData childCareCenterRegistrationRequestData) {
        this.childCareCenterRegistrationRequestData = childCareCenterRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        ChildCareCenterRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final ChildCareCenterRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        ChildCareCenterRegistrationRequestDocument childCareCenterRegistrationRequestDoc = ChildCareCenterRegistrationRequestDocument.Factory.newInstance();
        ChildCareCenterRegistrationRequestDocument.ChildCareCenterRegistrationRequest childCareCenterRegistrationRequest = childCareCenterRegistrationRequestDoc.addNewChildCareCenterRegistrationRequest();
        super.fillCommonXmlInfo(childCareCenterRegistrationRequest);
        int i = 0;
          ChildFridayType childFridayTypeFridayRegistrationParam = childCareCenterRegistrationRequest.addNewFridayRegistrationParam();
        childFridayTypeFridayRegistrationParam.setFridayFirstPeriodBegining(getFridayFirstPeriodBegining());
      
        childFridayTypeFridayRegistrationParam.setFridayFirstPeriodEnding(getFridayFirstPeriodEnding());
      
        if (getFridayPeriod() != null)
            childFridayTypeFridayRegistrationParam.setFridayPeriod(org.libredemat.xml.request.school.DayPeriodType.Enum.forString(getFridayPeriod().getLegacyLabel()));
      
        childFridayTypeFridayRegistrationParam.setFridaySecondPeriodBegining(getFridaySecondPeriodBegining());
      
        childFridayTypeFridayRegistrationParam.setFridaySecondPeriodEnding(getFridaySecondPeriodEnding());
        ChildMonDayType childMonDayTypeMondayRegistrationParam = childCareCenterRegistrationRequest.addNewMondayRegistrationParam();
        childMonDayTypeMondayRegistrationParam.setMondayFirstPeriodBegining(getMondayFirstPeriodBegining());
      
        childMonDayTypeMondayRegistrationParam.setMondayFirstPeriodEnding(getMondayFirstPeriodEnding());
      
        if (getMondayPeriod() != null)
            childMonDayTypeMondayRegistrationParam.setMondayPeriod(org.libredemat.xml.request.school.DayPeriodType.Enum.forString(getMondayPeriod().getLegacyLabel()));
      
        childMonDayTypeMondayRegistrationParam.setMondaySecondPeriodBegining(getMondaySecondPeriodBegining());
      
        childMonDayTypeMondayRegistrationParam.setMondaySecondPeriodEnding(getMondaySecondPeriodEnding());
      
        date = getRegistrationDate();
        if (date != null) {
            calendar.setTime(date);
            childCareCenterRegistrationRequest.setRegistrationDate(calendar);
        }
      
        date = getSubjectChoiceBirthDate();
        if (date != null) {
            calendar.setTime(date);
            childCareCenterRegistrationRequest.setSubjectChoiceBirthDate(calendar);
        }
      
        if (getSubjectChoiceGender() != null)
            childCareCenterRegistrationRequest.setSubjectChoiceGender(org.libredemat.xml.common.SexType.Enum.forString(getSubjectChoiceGender().getLegacyLabel()));
        ChildThursdayType childThursdayTypeThursdayRegistrationParam = childCareCenterRegistrationRequest.addNewThursdayRegistrationParam();
        childThursdayTypeThursdayRegistrationParam.setThursdayFirstPeriodBegining(getThursdayFirstPeriodBegining());
      
        childThursdayTypeThursdayRegistrationParam.setThursdayFirstPeriodEnding(getThursdayFirstPeriodEnding());
      
        if (getThursdayPeriod() != null)
            childThursdayTypeThursdayRegistrationParam.setThursdayPeriod(org.libredemat.xml.request.school.DayPeriodType.Enum.forString(getThursdayPeriod().getLegacyLabel()));
      
        childThursdayTypeThursdayRegistrationParam.setThursdaySecondPeriodBegining(getThursdaySecondPeriodBegining());
      
        childThursdayTypeThursdayRegistrationParam.setThursdaySecondPeriodEnding(getThursdaySecondPeriodEnding());
        ChildTuesdayType childTuesdayTypeTuesdayRegistrationParam = childCareCenterRegistrationRequest.addNewTuesdayRegistrationParam();
        childTuesdayTypeTuesdayRegistrationParam.setTuesdayFirstPeriodBegining(getTuesdayFirstPeriodBegining());
      
        childTuesdayTypeTuesdayRegistrationParam.setTuesdayFirstPeriodEnding(getTuesdayFirstPeriodEnding());
      
        if (getTuesdayPeriod() != null)
            childTuesdayTypeTuesdayRegistrationParam.setTuesdayPeriod(org.libredemat.xml.request.school.DayPeriodType.Enum.forString(getTuesdayPeriod().getLegacyLabel()));
      
        childTuesdayTypeTuesdayRegistrationParam.setTuesdaySecondPeriodBegining(getTuesdaySecondPeriodBegining());
      
        childTuesdayTypeTuesdayRegistrationParam.setTuesdaySecondPeriodEnding(getTuesdaySecondPeriodEnding());
        ChildWednesdayType childWednesdayTypeWednesdayRegistrationParam = childCareCenterRegistrationRequest.addNewWednesdayRegistrationParam();
        childWednesdayTypeWednesdayRegistrationParam.setWednesdayFirstPeriodBegining(getWednesdayFirstPeriodBegining());
      
        childWednesdayTypeWednesdayRegistrationParam.setWednesdayFirstPeriodEnding(getWednesdayFirstPeriodEnding());
      
        if (getWednesdayPeriod() != null)
            childWednesdayTypeWednesdayRegistrationParam.setWednesdayPeriod(org.libredemat.xml.request.school.DayPeriodType.Enum.forString(getWednesdayPeriod().getLegacyLabel()));
      
        childWednesdayTypeWednesdayRegistrationParam.setWednesdaySecondPeriodBegining(getWednesdaySecondPeriodBegining());
      
        childWednesdayTypeWednesdayRegistrationParam.setWednesdaySecondPeriodEnding(getWednesdaySecondPeriodEnding());
      
        i = 0;
        if (getWelcomingChoice() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] welcomingChoiceTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getWelcomingChoice().size()];
            for (LocalReferentialData object : getWelcomingChoice()) {
              welcomingChoiceTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            childCareCenterRegistrationRequest.setWelcomingChoiceArray(welcomingChoiceTypeTab);
        }
      
        return childCareCenterRegistrationRequestDoc;
    }

    @Override
    public final ChildCareCenterRegistrationRequestDocument.ChildCareCenterRegistrationRequest modelToXmlRequest() {
        return modelToXml().getChildCareCenterRegistrationRequest();
    }

    public static ChildCareCenterRegistrationRequest xmlToModel(ChildCareCenterRegistrationRequestDocument childCareCenterRegistrationRequestDoc) {
        ChildCareCenterRegistrationRequestDocument.ChildCareCenterRegistrationRequest childCareCenterRegistrationRequestXml = childCareCenterRegistrationRequestDoc.getChildCareCenterRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ChildCareCenterRegistrationRequest childCareCenterRegistrationRequest = new ChildCareCenterRegistrationRequest();
        childCareCenterRegistrationRequest.fillCommonModelInfo(childCareCenterRegistrationRequest, childCareCenterRegistrationRequestXml);
        
        childCareCenterRegistrationRequest.setFridayFirstPeriodBegining(childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridayFirstPeriodBegining());
      
        childCareCenterRegistrationRequest.setFridayFirstPeriodEnding(childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridayFirstPeriodEnding());
      
        if (childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridayPeriod() != null)
            childCareCenterRegistrationRequest.setFridayPeriod(org.libredemat.business.request.school.DayPeriodType.forString(childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridayPeriod().toString()));
        else
            childCareCenterRegistrationRequest.setFridayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
        childCareCenterRegistrationRequest.setFridaySecondPeriodBegining(childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridaySecondPeriodBegining());
      
        childCareCenterRegistrationRequest.setFridaySecondPeriodEnding(childCareCenterRegistrationRequestXml.getFridayRegistrationParam().getFridaySecondPeriodEnding());
      
        childCareCenterRegistrationRequest.setMondayFirstPeriodBegining(childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondayFirstPeriodBegining());
      
        childCareCenterRegistrationRequest.setMondayFirstPeriodEnding(childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondayFirstPeriodEnding());
      
        if (childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondayPeriod() != null)
            childCareCenterRegistrationRequest.setMondayPeriod(org.libredemat.business.request.school.DayPeriodType.forString(childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondayPeriod().toString()));
        else
            childCareCenterRegistrationRequest.setMondayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
        childCareCenterRegistrationRequest.setMondaySecondPeriodBegining(childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondaySecondPeriodBegining());
      
        childCareCenterRegistrationRequest.setMondaySecondPeriodEnding(childCareCenterRegistrationRequestXml.getMondayRegistrationParam().getMondaySecondPeriodEnding());
      
        calendar = childCareCenterRegistrationRequestXml.getRegistrationDate();
        if (calendar != null) {
            childCareCenterRegistrationRequest.setRegistrationDate(calendar.getTime());
        }
      
        calendar = childCareCenterRegistrationRequestXml.getSubjectChoiceBirthDate();
        if (calendar != null) {
            childCareCenterRegistrationRequest.setSubjectChoiceBirthDate(calendar.getTime());
        }
      
        if (childCareCenterRegistrationRequestXml.getSubjectChoiceGender() != null)
            childCareCenterRegistrationRequest.setSubjectChoiceGender(org.libredemat.business.users.SexType.forString(childCareCenterRegistrationRequestXml.getSubjectChoiceGender().toString()));
        else
            childCareCenterRegistrationRequest.setSubjectChoiceGender(org.libredemat.business.users.SexType.getDefaultSexType());
      
        childCareCenterRegistrationRequest.setThursdayFirstPeriodBegining(childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdayFirstPeriodBegining());
      
        childCareCenterRegistrationRequest.setThursdayFirstPeriodEnding(childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdayFirstPeriodEnding());
      
        if (childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdayPeriod() != null)
            childCareCenterRegistrationRequest.setThursdayPeriod(org.libredemat.business.request.school.DayPeriodType.forString(childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdayPeriod().toString()));
        else
            childCareCenterRegistrationRequest.setThursdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
        childCareCenterRegistrationRequest.setThursdaySecondPeriodBegining(childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdaySecondPeriodBegining());
      
        childCareCenterRegistrationRequest.setThursdaySecondPeriodEnding(childCareCenterRegistrationRequestXml.getThursdayRegistrationParam().getThursdaySecondPeriodEnding());
      
        childCareCenterRegistrationRequest.setTuesdayFirstPeriodBegining(childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdayFirstPeriodBegining());
      
        childCareCenterRegistrationRequest.setTuesdayFirstPeriodEnding(childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdayFirstPeriodEnding());
      
        if (childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdayPeriod() != null)
            childCareCenterRegistrationRequest.setTuesdayPeriod(org.libredemat.business.request.school.DayPeriodType.forString(childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdayPeriod().toString()));
        else
            childCareCenterRegistrationRequest.setTuesdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
        childCareCenterRegistrationRequest.setTuesdaySecondPeriodBegining(childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdaySecondPeriodBegining());
      
        childCareCenterRegistrationRequest.setTuesdaySecondPeriodEnding(childCareCenterRegistrationRequestXml.getTuesdayRegistrationParam().getTuesdaySecondPeriodEnding());
      
        childCareCenterRegistrationRequest.setWednesdayFirstPeriodBegining(childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdayFirstPeriodBegining());
      
        childCareCenterRegistrationRequest.setWednesdayFirstPeriodEnding(childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdayFirstPeriodEnding());
      
        if (childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdayPeriod() != null)
            childCareCenterRegistrationRequest.setWednesdayPeriod(org.libredemat.business.request.school.DayPeriodType.forString(childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdayPeriod().toString()));
        else
            childCareCenterRegistrationRequest.setWednesdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
        childCareCenterRegistrationRequest.setWednesdaySecondPeriodBegining(childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdaySecondPeriodBegining());
      
        childCareCenterRegistrationRequest.setWednesdaySecondPeriodEnding(childCareCenterRegistrationRequestXml.getWednesdayRegistrationParam().getWednesdaySecondPeriodEnding());
      
        List<org.libredemat.business.request.LocalReferentialData> welcomingChoiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(childCareCenterRegistrationRequestXml.sizeOfWelcomingChoiceArray());
        for (LocalReferentialDataType object : childCareCenterRegistrationRequestXml.getWelcomingChoiceArray()) {
            welcomingChoiceList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        childCareCenterRegistrationRequest.setWelcomingChoice(welcomingChoiceList);
      
        return childCareCenterRegistrationRequest;
    }

    @Override
    public ChildCareCenterRegistrationRequest clone() {
        ChildCareCenterRegistrationRequest clone = new ChildCareCenterRegistrationRequest(getRequestData().clone(), childCareCenterRegistrationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("registrationSubject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("registrationParams", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("welcoming", stepState);
        
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

  
    public final void setFridayFirstPeriodBegining(final String fridayFirstPeriodBegining) {
        childCareCenterRegistrationRequestData.setFridayFirstPeriodBegining(fridayFirstPeriodBegining);
    }

    
    public final String getFridayFirstPeriodBegining() {
        return childCareCenterRegistrationRequestData.getFridayFirstPeriodBegining();
    }
  
    public final void setFridayFirstPeriodEnding(final String fridayFirstPeriodEnding) {
        childCareCenterRegistrationRequestData.setFridayFirstPeriodEnding(fridayFirstPeriodEnding);
    }

    
    public final String getFridayFirstPeriodEnding() {
        return childCareCenterRegistrationRequestData.getFridayFirstPeriodEnding();
    }
  
    public final void setFridayPeriod(final org.libredemat.business.request.school.DayPeriodType fridayPeriod) {
        childCareCenterRegistrationRequestData.setFridayPeriod(fridayPeriod);
    }

    
    public final org.libredemat.business.request.school.DayPeriodType getFridayPeriod() {
        return childCareCenterRegistrationRequestData.getFridayPeriod();
    }
  
    public final void setFridaySecondPeriodBegining(final String fridaySecondPeriodBegining) {
        childCareCenterRegistrationRequestData.setFridaySecondPeriodBegining(fridaySecondPeriodBegining);
    }

    
    public final String getFridaySecondPeriodBegining() {
        return childCareCenterRegistrationRequestData.getFridaySecondPeriodBegining();
    }
  
    public final void setFridaySecondPeriodEnding(final String fridaySecondPeriodEnding) {
        childCareCenterRegistrationRequestData.setFridaySecondPeriodEnding(fridaySecondPeriodEnding);
    }

    
    public final String getFridaySecondPeriodEnding() {
        return childCareCenterRegistrationRequestData.getFridaySecondPeriodEnding();
    }
  
    public final void setMondayFirstPeriodBegining(final String mondayFirstPeriodBegining) {
        childCareCenterRegistrationRequestData.setMondayFirstPeriodBegining(mondayFirstPeriodBegining);
    }

    
    public final String getMondayFirstPeriodBegining() {
        return childCareCenterRegistrationRequestData.getMondayFirstPeriodBegining();
    }
  
    public final void setMondayFirstPeriodEnding(final String mondayFirstPeriodEnding) {
        childCareCenterRegistrationRequestData.setMondayFirstPeriodEnding(mondayFirstPeriodEnding);
    }

    
    public final String getMondayFirstPeriodEnding() {
        return childCareCenterRegistrationRequestData.getMondayFirstPeriodEnding();
    }
  
    public final void setMondayPeriod(final org.libredemat.business.request.school.DayPeriodType mondayPeriod) {
        childCareCenterRegistrationRequestData.setMondayPeriod(mondayPeriod);
    }

    
    public final org.libredemat.business.request.school.DayPeriodType getMondayPeriod() {
        return childCareCenterRegistrationRequestData.getMondayPeriod();
    }
  
    public final void setMondaySecondPeriodBegining(final String mondaySecondPeriodBegining) {
        childCareCenterRegistrationRequestData.setMondaySecondPeriodBegining(mondaySecondPeriodBegining);
    }

    
    public final String getMondaySecondPeriodBegining() {
        return childCareCenterRegistrationRequestData.getMondaySecondPeriodBegining();
    }
  
    public final void setMondaySecondPeriodEnding(final String mondaySecondPeriodEnding) {
        childCareCenterRegistrationRequestData.setMondaySecondPeriodEnding(mondaySecondPeriodEnding);
    }

    
    public final String getMondaySecondPeriodEnding() {
        return childCareCenterRegistrationRequestData.getMondaySecondPeriodEnding();
    }
  
    public final void setRegistrationDate(final java.util.Date registrationDate) {
        childCareCenterRegistrationRequestData.setRegistrationDate(registrationDate);
    }

    
    public final java.util.Date getRegistrationDate() {
        return childCareCenterRegistrationRequestData.getRegistrationDate();
    }
  
    public final void setSubjectChoiceBirthDate(final java.util.Date subjectChoiceBirthDate) {
        childCareCenterRegistrationRequestData.setSubjectChoiceBirthDate(subjectChoiceBirthDate);
    }

    
    public final java.util.Date getSubjectChoiceBirthDate() {
        return childCareCenterRegistrationRequestData.getSubjectChoiceBirthDate();
    }
  
    public final void setSubjectChoiceGender(final org.libredemat.business.users.SexType subjectChoiceGender) {
        childCareCenterRegistrationRequestData.setSubjectChoiceGender(subjectChoiceGender);
    }

    
    public final org.libredemat.business.users.SexType getSubjectChoiceGender() {
        return childCareCenterRegistrationRequestData.getSubjectChoiceGender();
    }
  
    public final void setThursdayFirstPeriodBegining(final String thursdayFirstPeriodBegining) {
        childCareCenterRegistrationRequestData.setThursdayFirstPeriodBegining(thursdayFirstPeriodBegining);
    }

    
    public final String getThursdayFirstPeriodBegining() {
        return childCareCenterRegistrationRequestData.getThursdayFirstPeriodBegining();
    }
  
    public final void setThursdayFirstPeriodEnding(final String thursdayFirstPeriodEnding) {
        childCareCenterRegistrationRequestData.setThursdayFirstPeriodEnding(thursdayFirstPeriodEnding);
    }

    
    public final String getThursdayFirstPeriodEnding() {
        return childCareCenterRegistrationRequestData.getThursdayFirstPeriodEnding();
    }
  
    public final void setThursdayPeriod(final org.libredemat.business.request.school.DayPeriodType thursdayPeriod) {
        childCareCenterRegistrationRequestData.setThursdayPeriod(thursdayPeriod);
    }

    
    public final org.libredemat.business.request.school.DayPeriodType getThursdayPeriod() {
        return childCareCenterRegistrationRequestData.getThursdayPeriod();
    }
  
    public final void setThursdaySecondPeriodBegining(final String thursdaySecondPeriodBegining) {
        childCareCenterRegistrationRequestData.setThursdaySecondPeriodBegining(thursdaySecondPeriodBegining);
    }

    
    public final String getThursdaySecondPeriodBegining() {
        return childCareCenterRegistrationRequestData.getThursdaySecondPeriodBegining();
    }
  
    public final void setThursdaySecondPeriodEnding(final String thursdaySecondPeriodEnding) {
        childCareCenterRegistrationRequestData.setThursdaySecondPeriodEnding(thursdaySecondPeriodEnding);
    }

    
    public final String getThursdaySecondPeriodEnding() {
        return childCareCenterRegistrationRequestData.getThursdaySecondPeriodEnding();
    }
  
    public final void setTuesdayFirstPeriodBegining(final String tuesdayFirstPeriodBegining) {
        childCareCenterRegistrationRequestData.setTuesdayFirstPeriodBegining(tuesdayFirstPeriodBegining);
    }

    
    public final String getTuesdayFirstPeriodBegining() {
        return childCareCenterRegistrationRequestData.getTuesdayFirstPeriodBegining();
    }
  
    public final void setTuesdayFirstPeriodEnding(final String tuesdayFirstPeriodEnding) {
        childCareCenterRegistrationRequestData.setTuesdayFirstPeriodEnding(tuesdayFirstPeriodEnding);
    }

    
    public final String getTuesdayFirstPeriodEnding() {
        return childCareCenterRegistrationRequestData.getTuesdayFirstPeriodEnding();
    }
  
    public final void setTuesdayPeriod(final org.libredemat.business.request.school.DayPeriodType tuesdayPeriod) {
        childCareCenterRegistrationRequestData.setTuesdayPeriod(tuesdayPeriod);
    }

    
    public final org.libredemat.business.request.school.DayPeriodType getTuesdayPeriod() {
        return childCareCenterRegistrationRequestData.getTuesdayPeriod();
    }
  
    public final void setTuesdaySecondPeriodBegining(final String tuesdaySecondPeriodBegining) {
        childCareCenterRegistrationRequestData.setTuesdaySecondPeriodBegining(tuesdaySecondPeriodBegining);
    }

    
    public final String getTuesdaySecondPeriodBegining() {
        return childCareCenterRegistrationRequestData.getTuesdaySecondPeriodBegining();
    }
  
    public final void setTuesdaySecondPeriodEnding(final String tuesdaySecondPeriodEnding) {
        childCareCenterRegistrationRequestData.setTuesdaySecondPeriodEnding(tuesdaySecondPeriodEnding);
    }

    
    public final String getTuesdaySecondPeriodEnding() {
        return childCareCenterRegistrationRequestData.getTuesdaySecondPeriodEnding();
    }
  
    public final void setWednesdayFirstPeriodBegining(final String wednesdayFirstPeriodBegining) {
        childCareCenterRegistrationRequestData.setWednesdayFirstPeriodBegining(wednesdayFirstPeriodBegining);
    }

    
    public final String getWednesdayFirstPeriodBegining() {
        return childCareCenterRegistrationRequestData.getWednesdayFirstPeriodBegining();
    }
  
    public final void setWednesdayFirstPeriodEnding(final String wednesdayFirstPeriodEnding) {
        childCareCenterRegistrationRequestData.setWednesdayFirstPeriodEnding(wednesdayFirstPeriodEnding);
    }

    
    public final String getWednesdayFirstPeriodEnding() {
        return childCareCenterRegistrationRequestData.getWednesdayFirstPeriodEnding();
    }
  
    public final void setWednesdayPeriod(final org.libredemat.business.request.school.DayPeriodType wednesdayPeriod) {
        childCareCenterRegistrationRequestData.setWednesdayPeriod(wednesdayPeriod);
    }

    
    public final org.libredemat.business.request.school.DayPeriodType getWednesdayPeriod() {
        return childCareCenterRegistrationRequestData.getWednesdayPeriod();
    }
  
    public final void setWednesdaySecondPeriodBegining(final String wednesdaySecondPeriodBegining) {
        childCareCenterRegistrationRequestData.setWednesdaySecondPeriodBegining(wednesdaySecondPeriodBegining);
    }

    
    public final String getWednesdaySecondPeriodBegining() {
        return childCareCenterRegistrationRequestData.getWednesdaySecondPeriodBegining();
    }
  
    public final void setWednesdaySecondPeriodEnding(final String wednesdaySecondPeriodEnding) {
        childCareCenterRegistrationRequestData.setWednesdaySecondPeriodEnding(wednesdaySecondPeriodEnding);
    }

    
    public final String getWednesdaySecondPeriodEnding() {
        return childCareCenterRegistrationRequestData.getWednesdaySecondPeriodEnding();
    }
  
    public final void setWelcomingChoice(final List<org.libredemat.business.request.LocalReferentialData> welcomingChoice) {
        childCareCenterRegistrationRequestData.setWelcomingChoice(welcomingChoice);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getWelcomingChoice() {
        return childCareCenterRegistrationRequestData.getWelcomingChoice();
    }
  
}
