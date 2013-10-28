

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
@Table(name="school_registration_request")
public class SchoolRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public SchoolRegistrationRequestData() {
      
        currentSection = org.libredemat.business.users.SectionType.UNKNOWN;
      
        rulesAndRegulationsAcceptance = Boolean.valueOf(false);
      
        section = org.libredemat.business.users.SectionType.UNKNOWN;
      
    }

    @Override
    public SchoolRegistrationRequestData clone() {
        SchoolRegistrationRequestData result = new SchoolRegistrationRequestData();
        
          
            
        result.setCurrentSchoolAddress(currentSchoolAddress);
      
          
        
          
            
        result.setCurrentSchoolName(currentSchoolName);
      
          
        
          
            
        if (currentSection != null)
            result.setCurrentSection(currentSection);
        else
            result.setCurrentSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
          
        
          
            
        result.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
      
          
        
          
            result.setSchool(school);
          
        
          
            
        if (section != null)
            result.setSection(section);
        else
            result.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
          
        
          
            
        result.setUrgencyPhone(urgencyPhone);
      
          
        
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

  
    
    private String currentSchoolAddress;

    public void setCurrentSchoolAddress(final String currentSchoolAddress) {
        this.currentSchoolAddress = currentSchoolAddress;
    }

 
    @Column(name="current_school_address"  )
      
    public String getCurrentSchoolAddress() {
        return this.currentSchoolAddress;
    }
  
    
    private String currentSchoolName;

    public void setCurrentSchoolName(final String currentSchoolName) {
        this.currentSchoolName = currentSchoolName;
    }

 
    @Column(name="current_school_name"  )
      
    public String getCurrentSchoolName() {
        return this.currentSchoolName;
    }
  
    
    private org.libredemat.business.users.SectionType currentSection;

    public void setCurrentSection(final org.libredemat.business.users.SectionType currentSection) {
        this.currentSection = currentSection;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="current_section" , length=32 )
      
    public org.libredemat.business.users.SectionType getCurrentSection() {
        return this.currentSection;
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
        
        
        profiles = {"registration"},
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
  
}
