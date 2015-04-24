

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
@Table(name="school_canteen_registration_request")
public class SchoolCanteenRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public SchoolCanteenRegistrationRequestData() {
      
        foodAllergy = Boolean.valueOf(false);
      
        hospitalizationPermission = Boolean.valueOf(false);
      
        rulesAndRegulationsAcceptance = Boolean.valueOf(false);
      
        section = org.libredemat.business.users.SectionType.UNKNOWN;
      
    }

    @Override
    public SchoolCanteenRegistrationRequestData clone() {
        SchoolCanteenRegistrationRequestData result = new SchoolCanteenRegistrationRequestData();
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> canteenAttendingDaysList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setCanteenAttendingDays(canteenAttendingDaysList);
      
          
        
          
            
        result.setDoctorName(doctorName);
      
          
        
          
            
        result.setDoctorPhone(doctorPhone);
      
          
        
          
            
        result.setFoodAllergy(foodAllergy);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> foodDietList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setFoodDiet(foodDietList);
      
          
        
          
            
        result.setHospitalizationPermission(hospitalizationPermission);
      
          
        
          
            
        result.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
      
          
        
          
            result.setSchool(school);
          
        
          
            
        if (section != null)
            result.setSection(section);
        else
            result.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
          
        
          
            
        result.setUrgencyPhone(urgencyPhone);
      
          
        
          
            
        result.setWhichFoodAllergy(whichFoodAllergy);
      
          
        
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

  
    
      @LocalReferential(
        
        
        profiles = {"registration"},
        message = "canteenAttendingDays"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"registration"},
        message = "canteenAttendingDays"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> canteenAttendingDays;

    public void setCanteenAttendingDays(final List<org.libredemat.business.request.LocalReferentialData> canteenAttendingDays) {
        this.canteenAttendingDays = canteenAttendingDays;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="school_canteen_registration_request_canteen_attending_days",
            joinColumns=
                @JoinColumn(name="school_canteen_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="canteen_attending_days_id"))
    @OrderColumn(name="canteen_attending_days_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getCanteenAttendingDays() {
        return this.canteenAttendingDays;
    }
  
    
    private String doctorName;

    public void setDoctorName(final String doctorName) {
        this.doctorName = doctorName;
    }

 
    @Column(name="doctor_name"  )
      
    public String getDoctorName() {
        return this.doctorName;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"registration"},
        message = "doctorPhone"
      )
    
    private String doctorPhone;

    public void setDoctorPhone(final String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

 
    @Column(name="doctor_phone" , length=10 )
      
    public String getDoctorPhone() {
        return this.doctorPhone;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "foodAllergy"
      )
    
    private Boolean foodAllergy;

    public void setFoodAllergy(final Boolean foodAllergy) {
        this.foodAllergy = foodAllergy;
    }

 
    @Column(name="food_allergy"  )
      
    public Boolean getFoodAllergy() {
        return this.foodAllergy;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"registration"},
        message = "foodDiet"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"registration"},
        message = "foodDiet"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> foodDiet;

    public void setFoodDiet(final List<org.libredemat.business.request.LocalReferentialData> foodDiet) {
        this.foodDiet = foodDiet;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="school_canteen_registration_request_food_diet",
            joinColumns=
                @JoinColumn(name="school_canteen_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="food_diet_id"))
    @OrderColumn(name="food_diet_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getFoodDiet() {
        return this.foodDiet;
    }
  
    
    private Boolean hospitalizationPermission;

    public void setHospitalizationPermission(final Boolean hospitalizationPermission) {
        this.hospitalizationPermission = hospitalizationPermission;
    }

 
    @Column(name="hospitalization_permission"  )
      
    public Boolean getHospitalizationPermission() {
        return this.hospitalizationPermission;
    }
  
    
    private Boolean rulesAndRegulationsAcceptance;

    public void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        this.rulesAndRegulationsAcceptance = rulesAndRegulationsAcceptance;
    }

 
    @Column(name="rules_and_regulations_acceptance"  )
      
    public Boolean getRulesAndRegulationsAcceptance() {
        return this.rulesAndRegulationsAcceptance;
    }
  
    
      @AssertValid(
        
        
        profiles = {"administration"},
        message = "school"
      )
    
    private org.libredemat.business.authority.School school;

    public void setSchool(final org.libredemat.business.authority.School school) {
        this.school = school;
    }

 
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="school_id")
      
    public org.libredemat.business.authority.School getSchool() {
        return this.school;
    }
  
    
      @NotNull(
        
        
        profiles = {"administration"},
        message = "section"
      )
    
    private org.libredemat.business.users.SectionType section;

    public void setSection(final org.libredemat.business.users.SectionType section) {
        this.section = section;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="section" , length=32 )
      
    public org.libredemat.business.users.SectionType getSection() {
        return this.section;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
      @NotBlank(
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
    private String urgencyPhone;

    public void setUrgencyPhone(final String urgencyPhone) {
        this.urgencyPhone = urgencyPhone;
    }

 
    @Column(name="urgency_phone" , length=10 )
      
    public String getUrgencyPhone() {
        return this.urgencyPhone;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['foodAllergy'].test(_this.foodAllergy.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "whichFoodAllergy"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['foodAllergy'].test(_this.foodAllergy.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "whichFoodAllergy"
      )
    
    private String whichFoodAllergy;

    public void setWhichFoodAllergy(final String whichFoodAllergy) {
        this.whichFoodAllergy = whichFoodAllergy;
    }

 
    @Column(name="which_food_allergy"  )
      
    public String getWhichFoodAllergy() {
        return this.whichFoodAllergy;
    }
  
}
