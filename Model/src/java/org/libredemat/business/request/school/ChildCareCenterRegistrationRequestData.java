

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

import net.sf.oval.constraint.*;
import org.libredemat.business.authority.*;
import org.libredemat.business.payment.*;
import org.libredemat.business.request.*;
import org.libredemat.business.users.*;
import org.libredemat.service.request.LocalReferential;
import org.libredemat.service.request.condition.IConditionChecker;

import javax.persistence.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * Generated class file, do not edit !
 */
@Entity
@Table(name="child_care_center_registration_request")
public class ChildCareCenterRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ChildCareCenterRegistrationRequestData() {
      
        fridayPeriod = org.libredemat.business.request.school.DayPeriodType.ALL_DAY;
      
        mondayPeriod = org.libredemat.business.request.school.DayPeriodType.ALL_DAY;
      
        thursdayPeriod = org.libredemat.business.request.school.DayPeriodType.ALL_DAY;
      
        tuesdayPeriod = org.libredemat.business.request.school.DayPeriodType.ALL_DAY;
      
        wednesdayPeriod = org.libredemat.business.request.school.DayPeriodType.ALL_DAY;
      
    }

    @Override
    public ChildCareCenterRegistrationRequestData clone() {
        ChildCareCenterRegistrationRequestData result = new ChildCareCenterRegistrationRequestData();
        
          
            
        result.setFridayFirstPeriodBegining(fridayFirstPeriodBegining);
      
          
        
          
            
        result.setFridayFirstPeriodEnding(fridayFirstPeriodEnding);
      
          
        
          
            
        if (fridayPeriod != null)
            result.setFridayPeriod(fridayPeriod);
        else
            result.setFridayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
          
        
          
            
        result.setFridaySecondPeriodBegining(fridaySecondPeriodBegining);
      
          
        
          
            
        result.setFridaySecondPeriodEnding(fridaySecondPeriodEnding);
      
          
        
          
            
        result.setMondayFirstPeriodBegining(mondayFirstPeriodBegining);
      
          
        
          
            
        result.setMondayFirstPeriodEnding(mondayFirstPeriodEnding);
      
          
        
          
            
        if (mondayPeriod != null)
            result.setMondayPeriod(mondayPeriod);
        else
            result.setMondayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
          
        
          
            
        result.setMondaySecondPeriodBegining(mondaySecondPeriodBegining);
      
          
        
          
            
        result.setMondaySecondPeriodEnding(mondaySecondPeriodEnding);
      
          
        
          
            
        result.setRegistrationDate(registrationDate);
      
          
        
          
            
        result.setSubjectChoiceBirthDate(subjectChoiceBirthDate);
      
          
        
          
            
        if (subjectChoiceGender != null)
            result.setSubjectChoiceGender(subjectChoiceGender);
        else
            result.setSubjectChoiceGender(org.libredemat.business.users.SexType.getDefaultSexType());
      
          
        
          
            
        result.setThursdayFirstPeriodBegining(thursdayFirstPeriodBegining);
      
          
        
          
            
        result.setThursdayFirstPeriodEnding(thursdayFirstPeriodEnding);
      
          
        
          
            
        if (thursdayPeriod != null)
            result.setThursdayPeriod(thursdayPeriod);
        else
            result.setThursdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
          
        
          
            
        result.setThursdaySecondPeriodBegining(thursdaySecondPeriodBegining);
      
          
        
          
            
        result.setThursdaySecondPeriodEnding(thursdaySecondPeriodEnding);
      
          
        
          
            
        result.setTuesdayFirstPeriodBegining(tuesdayFirstPeriodBegining);
      
          
        
          
            
        result.setTuesdayFirstPeriodEnding(tuesdayFirstPeriodEnding);
      
          
        
          
            
        if (tuesdayPeriod != null)
            result.setTuesdayPeriod(tuesdayPeriod);
        else
            result.setTuesdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
          
        
          
            
        result.setTuesdaySecondPeriodBegining(tuesdaySecondPeriodBegining);
      
          
        
          
            
        result.setTuesdaySecondPeriodEnding(tuesdaySecondPeriodEnding);
      
          
        
          
            
        result.setWednesdayFirstPeriodBegining(wednesdayFirstPeriodBegining);
      
          
        
          
            
        result.setWednesdayFirstPeriodEnding(wednesdayFirstPeriodEnding);
      
          
        
          
            
        if (wednesdayPeriod != null)
            result.setWednesdayPeriod(wednesdayPeriod);
        else
            result.setWednesdayPeriod(org.libredemat.business.request.school.DayPeriodType.getDefaultDayPeriodType());
      
          
        
          
            
        result.setWednesdaySecondPeriodBegining(wednesdaySecondPeriodBegining);
      
          
        
          
            
        result.setWednesdaySecondPeriodEnding(wednesdaySecondPeriodEnding);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> welcomingChoiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setWelcomingChoice(welcomingChoiceList);
      
          
        
        return result;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public final Long getId() {
        return this.id;
    }

  
    
    private String fridayFirstPeriodBegining;

    public void setFridayFirstPeriodBegining(final String fridayFirstPeriodBegining) {
        this.fridayFirstPeriodBegining = fridayFirstPeriodBegining;
    }

 
    @Column(name="friday_first_period_begining"  )
      
    public String getFridayFirstPeriodBegining() {
        return this.fridayFirstPeriodBegining;
    }
  
    
    private String fridayFirstPeriodEnding;

    public void setFridayFirstPeriodEnding(final String fridayFirstPeriodEnding) {
        this.fridayFirstPeriodEnding = fridayFirstPeriodEnding;
    }

 
    @Column(name="friday_first_period_ending"  )
      
    public String getFridayFirstPeriodEnding() {
        return this.fridayFirstPeriodEnding;
    }
  
    
    private org.libredemat.business.request.school.DayPeriodType fridayPeriod;

    public void setFridayPeriod(final org.libredemat.business.request.school.DayPeriodType fridayPeriod) {
        this.fridayPeriod = fridayPeriod;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="friday_period"  )
      
    public org.libredemat.business.request.school.DayPeriodType getFridayPeriod() {
        return this.fridayPeriod;
    }
  
    
    private String fridaySecondPeriodBegining;

    public void setFridaySecondPeriodBegining(final String fridaySecondPeriodBegining) {
        this.fridaySecondPeriodBegining = fridaySecondPeriodBegining;
    }

 
    @Column(name="friday_second_period_begining"  )
      
    public String getFridaySecondPeriodBegining() {
        return this.fridaySecondPeriodBegining;
    }
  
    
    private String fridaySecondPeriodEnding;

    public void setFridaySecondPeriodEnding(final String fridaySecondPeriodEnding) {
        this.fridaySecondPeriodEnding = fridaySecondPeriodEnding;
    }

 
    @Column(name="friday_second_period_ending"  )
      
    public String getFridaySecondPeriodEnding() {
        return this.fridaySecondPeriodEnding;
    }
  
    
    private String mondayFirstPeriodBegining;

    public void setMondayFirstPeriodBegining(final String mondayFirstPeriodBegining) {
        this.mondayFirstPeriodBegining = mondayFirstPeriodBegining;
    }

 
    @Column(name="monday_first_period_begining"  )
      
    public String getMondayFirstPeriodBegining() {
        return this.mondayFirstPeriodBegining;
    }
  
    
    private String mondayFirstPeriodEnding;

    public void setMondayFirstPeriodEnding(final String mondayFirstPeriodEnding) {
        this.mondayFirstPeriodEnding = mondayFirstPeriodEnding;
    }

 
    @Column(name="monday_first_period_ending"  )
      
    public String getMondayFirstPeriodEnding() {
        return this.mondayFirstPeriodEnding;
    }
  
    
    private org.libredemat.business.request.school.DayPeriodType mondayPeriod;

    public void setMondayPeriod(final org.libredemat.business.request.school.DayPeriodType mondayPeriod) {
        this.mondayPeriod = mondayPeriod;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="monday_period"  )
      
    public org.libredemat.business.request.school.DayPeriodType getMondayPeriod() {
        return this.mondayPeriod;
    }
  
    
    private String mondaySecondPeriodBegining;

    public void setMondaySecondPeriodBegining(final String mondaySecondPeriodBegining) {
        this.mondaySecondPeriodBegining = mondaySecondPeriodBegining;
    }

 
    @Column(name="monday_second_period_begining"  )
      
    public String getMondaySecondPeriodBegining() {
        return this.mondaySecondPeriodBegining;
    }
  
    
    private String mondaySecondPeriodEnding;

    public void setMondaySecondPeriodEnding(final String mondaySecondPeriodEnding) {
        this.mondaySecondPeriodEnding = mondaySecondPeriodEnding;
    }

 
    @Column(name="monday_second_period_ending"  )
      
    public String getMondaySecondPeriodEnding() {
        return this.mondaySecondPeriodEnding;
    }
  
    
      @NotNull(
        
        
        profiles = {"registrationParams"},
        message = "registrationDate"
      )
    
    private java.util.Date registrationDate;

    public void setRegistrationDate(final java.util.Date registrationDate) {
        this.registrationDate = registrationDate;
    }

 
    @Column(name="registration_date"  )
      
    public java.util.Date getRegistrationDate() {
        return this.registrationDate;
    }
  
    
    private java.util.Date subjectChoiceBirthDate;

    public void setSubjectChoiceBirthDate(final java.util.Date subjectChoiceBirthDate) {
        this.subjectChoiceBirthDate = subjectChoiceBirthDate;
    }

 
    @Column(name="subject_choice_birth_date"  )
      
    public java.util.Date getSubjectChoiceBirthDate() {
        return this.subjectChoiceBirthDate;
    }
  
    
    private org.libredemat.business.users.SexType subjectChoiceGender;

    public void setSubjectChoiceGender(final org.libredemat.business.users.SexType subjectChoiceGender) {
        this.subjectChoiceGender = subjectChoiceGender;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="subject_choice_gender"  )
      
    public org.libredemat.business.users.SexType getSubjectChoiceGender() {
        return this.subjectChoiceGender;
    }
  
    
    private String thursdayFirstPeriodBegining;

    public void setThursdayFirstPeriodBegining(final String thursdayFirstPeriodBegining) {
        this.thursdayFirstPeriodBegining = thursdayFirstPeriodBegining;
    }

 
    @Column(name="thursday_first_period_begining"  )
      
    public String getThursdayFirstPeriodBegining() {
        return this.thursdayFirstPeriodBegining;
    }
  
    
    private String thursdayFirstPeriodEnding;

    public void setThursdayFirstPeriodEnding(final String thursdayFirstPeriodEnding) {
        this.thursdayFirstPeriodEnding = thursdayFirstPeriodEnding;
    }

 
    @Column(name="thursday_first_period_ending"  )
      
    public String getThursdayFirstPeriodEnding() {
        return this.thursdayFirstPeriodEnding;
    }
  
    
    private org.libredemat.business.request.school.DayPeriodType thursdayPeriod;

    public void setThursdayPeriod(final org.libredemat.business.request.school.DayPeriodType thursdayPeriod) {
        this.thursdayPeriod = thursdayPeriod;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="thursday_period"  )
      
    public org.libredemat.business.request.school.DayPeriodType getThursdayPeriod() {
        return this.thursdayPeriod;
    }
  
    
    private String thursdaySecondPeriodBegining;

    public void setThursdaySecondPeriodBegining(final String thursdaySecondPeriodBegining) {
        this.thursdaySecondPeriodBegining = thursdaySecondPeriodBegining;
    }

 
    @Column(name="thursday_second_period_begining"  )
      
    public String getThursdaySecondPeriodBegining() {
        return this.thursdaySecondPeriodBegining;
    }
  
    
    private String thursdaySecondPeriodEnding;

    public void setThursdaySecondPeriodEnding(final String thursdaySecondPeriodEnding) {
        this.thursdaySecondPeriodEnding = thursdaySecondPeriodEnding;
    }

 
    @Column(name="thursday_second_period_ending"  )
      
    public String getThursdaySecondPeriodEnding() {
        return this.thursdaySecondPeriodEnding;
    }
  
    
    private String tuesdayFirstPeriodBegining;

    public void setTuesdayFirstPeriodBegining(final String tuesdayFirstPeriodBegining) {
        this.tuesdayFirstPeriodBegining = tuesdayFirstPeriodBegining;
    }

 
    @Column(name="tuesday_first_period_begining"  )
      
    public String getTuesdayFirstPeriodBegining() {
        return this.tuesdayFirstPeriodBegining;
    }
  
    
    private String tuesdayFirstPeriodEnding;

    public void setTuesdayFirstPeriodEnding(final String tuesdayFirstPeriodEnding) {
        this.tuesdayFirstPeriodEnding = tuesdayFirstPeriodEnding;
    }

 
    @Column(name="tuesday_first_period_ending"  )
      
    public String getTuesdayFirstPeriodEnding() {
        return this.tuesdayFirstPeriodEnding;
    }
  
    
    private org.libredemat.business.request.school.DayPeriodType tuesdayPeriod;

    public void setTuesdayPeriod(final org.libredemat.business.request.school.DayPeriodType tuesdayPeriod) {
        this.tuesdayPeriod = tuesdayPeriod;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="tuesday_period"  )
      
    public org.libredemat.business.request.school.DayPeriodType getTuesdayPeriod() {
        return this.tuesdayPeriod;
    }
  
    
    private String tuesdaySecondPeriodBegining;

    public void setTuesdaySecondPeriodBegining(final String tuesdaySecondPeriodBegining) {
        this.tuesdaySecondPeriodBegining = tuesdaySecondPeriodBegining;
    }

 
    @Column(name="tuesday_second_period_begining"  )
      
    public String getTuesdaySecondPeriodBegining() {
        return this.tuesdaySecondPeriodBegining;
    }
  
    
    private String tuesdaySecondPeriodEnding;

    public void setTuesdaySecondPeriodEnding(final String tuesdaySecondPeriodEnding) {
        this.tuesdaySecondPeriodEnding = tuesdaySecondPeriodEnding;
    }

 
    @Column(name="tuesday_second_period_ending"  )
      
    public String getTuesdaySecondPeriodEnding() {
        return this.tuesdaySecondPeriodEnding;
    }
  
    
    private String wednesdayFirstPeriodBegining;

    public void setWednesdayFirstPeriodBegining(final String wednesdayFirstPeriodBegining) {
        this.wednesdayFirstPeriodBegining = wednesdayFirstPeriodBegining;
    }

 
    @Column(name="wednesday_first_period_begining"  )
      
    public String getWednesdayFirstPeriodBegining() {
        return this.wednesdayFirstPeriodBegining;
    }
  
    
    private String wednesdayFirstPeriodEnding;

    public void setWednesdayFirstPeriodEnding(final String wednesdayFirstPeriodEnding) {
        this.wednesdayFirstPeriodEnding = wednesdayFirstPeriodEnding;
    }

 
    @Column(name="wednesday_first_period_ending"  )
      
    public String getWednesdayFirstPeriodEnding() {
        return this.wednesdayFirstPeriodEnding;
    }
  
    
    private org.libredemat.business.request.school.DayPeriodType wednesdayPeriod;

    public void setWednesdayPeriod(final org.libredemat.business.request.school.DayPeriodType wednesdayPeriod) {
        this.wednesdayPeriod = wednesdayPeriod;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="wednesday_period"  )
      
    public org.libredemat.business.request.school.DayPeriodType getWednesdayPeriod() {
        return this.wednesdayPeriod;
    }
  
    
    private String wednesdaySecondPeriodBegining;

    public void setWednesdaySecondPeriodBegining(final String wednesdaySecondPeriodBegining) {
        this.wednesdaySecondPeriodBegining = wednesdaySecondPeriodBegining;
    }

 
    @Column(name="wednesday_second_period_begining"  )
      
    public String getWednesdaySecondPeriodBegining() {
        return this.wednesdaySecondPeriodBegining;
    }
  
    
    private String wednesdaySecondPeriodEnding;

    public void setWednesdaySecondPeriodEnding(final String wednesdaySecondPeriodEnding) {
        this.wednesdaySecondPeriodEnding = wednesdaySecondPeriodEnding;
    }

 
    @Column(name="wednesday_second_period_ending"  )
      
    public String getWednesdaySecondPeriodEnding() {
        return this.wednesdaySecondPeriodEnding;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"welcoming"},
        message = "welcomingChoice"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"welcoming"},
        message = "welcomingChoice"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> welcomingChoice;

    public void setWelcomingChoice(final List<org.libredemat.business.request.LocalReferentialData> welcomingChoice) {
        this.welcomingChoice = welcomingChoice;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="child_care_center_registration_request_welcoming_choice",
            joinColumns=
                @JoinColumn(name="child_care_center_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="welcoming_choice_id"))
    @OrderColumn(name="welcoming_choice_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getWelcomingChoice() {
        return this.welcomingChoice;
    }
  
}
