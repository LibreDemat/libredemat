

package org.libredemat.business.request.leisure;

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
@Table(name="youth_center_registration_request")
public class YouthCenterRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public YouthCenterRegistrationRequestData() {
      
        childAlone = Boolean.valueOf(false);
      
        isFirstRegistration = Boolean.valueOf(true);
      
        multiActivities = Boolean.valueOf(false);
      
        rulesAcceptance = Boolean.valueOf(false);
      
    }

    @Override
    public YouthCenterRegistrationRequestData clone() {
        YouthCenterRegistrationRequestData result = new YouthCenterRegistrationRequestData();
        
          
            
        result.setChildAlone(childAlone);
      
          
        
          
            
        result.setFirstRegistrationDate(firstRegistrationDate);
      
          
        
          
            
        result.setFirstRegistrationNumeroAdherent(firstRegistrationNumeroAdherent);
      
          
        
          
            
        result.setIsFirstRegistration(isFirstRegistration);
      
          
        
          
            
        result.setMultiActivities(multiActivities);
      
          
        
          
            
        result.setRulesAcceptance(rulesAcceptance);
      
          
        
          
            
        result.setSubjectChoiceBirthDate(subjectChoiceBirthDate);
      
          
        
          
            
        result.setSubjectChoiceEmail(subjectChoiceEmail);
      
          
        
          
            
        result.setSubjectChoiceMobilePhone(subjectChoiceMobilePhone);
      
          
        
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

  
    
      @NotNull(
        
        
        profiles = {"rules"},
        message = "childAlone"
      )
    
    private Boolean childAlone;

    public void setChildAlone(final Boolean childAlone) {
        this.childAlone = childAlone;
    }

 
    @Column(name="child_alone"  )
      
    public Boolean getChildAlone() {
        return this.childAlone;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isFirstRegistration'].test(_this.isFirstRegistration.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "firstRegistrationDate"
      )
    
    private java.util.Date firstRegistrationDate;

    public void setFirstRegistrationDate(final java.util.Date firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

 
    @Column(name="first_registration_date"  )
      
    public java.util.Date getFirstRegistrationDate() {
        return this.firstRegistrationDate;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isFirstRegistration'].test(_this.isFirstRegistration.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "firstRegistrationNumeroAdherent"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isFirstRegistration'].test(_this.isFirstRegistration.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "firstRegistrationNumeroAdherent"
      )
    
    private String firstRegistrationNumeroAdherent;

    public void setFirstRegistrationNumeroAdherent(final String firstRegistrationNumeroAdherent) {
        this.firstRegistrationNumeroAdherent = firstRegistrationNumeroAdherent;
    }

 
    @Column(name="first_registration_numero_adherent"  )
      
    public String getFirstRegistrationNumeroAdherent() {
        return this.firstRegistrationNumeroAdherent;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "isFirstRegistration"
      )
    
    private Boolean isFirstRegistration;

    public void setIsFirstRegistration(final Boolean isFirstRegistration) {
        this.isFirstRegistration = isFirstRegistration;
    }

 
    @Column(name="is_first_registration"  )
      
    public Boolean getIsFirstRegistration() {
        return this.isFirstRegistration;
    }
  
    
      @NotNull(
        
        
        profiles = {"rules"},
        message = "multiActivities"
      )
    
    private Boolean multiActivities;

    public void setMultiActivities(final Boolean multiActivities) {
        this.multiActivities = multiActivities;
    }

 
    @Column(name="multi_activities"  )
      
    public Boolean getMultiActivities() {
        return this.multiActivities;
    }
  
    
      @NotNull(
        
        
        profiles = {"rules"},
        message = "rulesAcceptance"
      )
    
    private Boolean rulesAcceptance;

    public void setRulesAcceptance(final Boolean rulesAcceptance) {
        this.rulesAcceptance = rulesAcceptance;
    }

 
    @Column(name="rules_acceptance"  )
      
    public Boolean getRulesAcceptance() {
        return this.rulesAcceptance;
    }
  
    
    private java.util.Date subjectChoiceBirthDate;

    public void setSubjectChoiceBirthDate(final java.util.Date subjectChoiceBirthDate) {
        this.subjectChoiceBirthDate = subjectChoiceBirthDate;
    }

 
    @Column(name="subject_choice_birth_date"  )
      
    public java.util.Date getSubjectChoiceBirthDate() {
        return this.subjectChoiceBirthDate;
    }
  
    
    private String subjectChoiceEmail;

    public void setSubjectChoiceEmail(final String subjectChoiceEmail) {
        this.subjectChoiceEmail = subjectChoiceEmail;
    }

 
    @Column(name="subject_choice_email"  )
      
    public String getSubjectChoiceEmail() {
        return this.subjectChoiceEmail;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"registration"},
        message = "subjectChoiceMobilePhone"
      )
    
    private String subjectChoiceMobilePhone;

    public void setSubjectChoiceMobilePhone(final String subjectChoiceMobilePhone) {
        this.subjectChoiceMobilePhone = subjectChoiceMobilePhone;
    }

 
    @Column(name="subject_choice_mobile_phone" , length=10 )
      
    public String getSubjectChoiceMobilePhone() {
        return this.subjectChoiceMobilePhone;
    }
  
}
