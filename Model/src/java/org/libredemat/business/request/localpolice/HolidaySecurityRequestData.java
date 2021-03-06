

package org.libredemat.business.request.localpolice;

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
@Table(name="holiday_security_request")
public class HolidaySecurityRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public HolidaySecurityRequestData() {
      
        alarm = Boolean.valueOf(false);
      
        isAnimalOwner = Boolean.valueOf(false);
      
        isSecurityCompany = Boolean.valueOf(false);
      
        light = Boolean.valueOf(false);
      
        otherContact = Boolean.valueOf(false);
      
        otherContactDuplicateKey = Boolean.valueOf(false);
      
        rulesAndRegulationsAcceptance = Boolean.valueOf(false);
      
    }

    @Override
    public HolidaySecurityRequestData clone() {
        HolidaySecurityRequestData result = new HolidaySecurityRequestData();
        
          
            
        result.setAbsenceEndDate(absenceEndDate);
      
          
        
          
            
        result.setAbsenceStartDate(absenceStartDate);
      
          
        
          
            
        result.setAlarm(alarm);
      
          
        
          
            
        result.setAlertPhone(alertPhone);
      
          
        
          
            
        result.setAnimalInformation(animalInformation);
      
          
        
          
            
        result.setIsAnimalOwner(isAnimalOwner);
      
          
        
          
            
        result.setIsSecurityCompany(isSecurityCompany);
      
          
        
          
            
        result.setLight(light);
      
          
        
          
            
        result.setOtherContact(otherContact);
      
          
        
          
            
        if (otherContactAddress != null)
            result.setOtherContactAddress(otherContactAddress.clone());
      
          
        
          
            
        result.setOtherContactDuplicateKey(otherContactDuplicateKey);
      
          
        
          
            
        result.setOtherContactFirstName(otherContactFirstName);
      
          
        
          
            
        result.setOtherContactLastName(otherContactLastName);
      
          
        
          
            
        result.setOtherContactPhone(otherContactPhone);
      
          
        
          
            
        result.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
      
          
        
          
            
        result.setSecurityCompanyAddress(securityCompanyAddress);
      
          
        
          
            
        result.setSecurityCompanyName(securityCompanyName);
      
          
        
          
            
        result.setSecurityCompanyTelephone(securityCompanyTelephone);
      
          
        
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
        
        
        profiles = {"registration"},
        message = "absenceEndDate"
      )
    
    private java.util.Date absenceEndDate;

    public void setAbsenceEndDate(final java.util.Date absenceEndDate) {
        this.absenceEndDate = absenceEndDate;
    }

 
    @Column(name="absence_end_date"  )
      
    public java.util.Date getAbsenceEndDate() {
        return this.absenceEndDate;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "absenceStartDate"
      )
    
    private java.util.Date absenceStartDate;

    public void setAbsenceStartDate(final java.util.Date absenceStartDate) {
        this.absenceStartDate = absenceStartDate;
    }

 
    @Column(name="absence_start_date"  )
      
    public java.util.Date getAbsenceStartDate() {
        return this.absenceStartDate;
    }
  
    
      @NotNull(
        
        
        profiles = {"additional"},
        message = "alarm"
      )
    
    private Boolean alarm;

    public void setAlarm(final Boolean alarm) {
        this.alarm = alarm;
    }

 
    @Column(name="alarm"  )
      
    public Boolean getAlarm() {
        return this.alarm;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"contactphone"},
        message = "alertPhone"
      )
    
      @NotNull(
        
        
        profiles = {"contactphone"},
        message = "alertPhone"
      )
    
      @NotBlank(
        
        
        profiles = {"contactphone"},
        message = "alertPhone"
      )
    
    private String alertPhone;

    public void setAlertPhone(final String alertPhone) {
        this.alertPhone = alertPhone;
    }

 
    @Column(name="alert_phone" , length=10 )
      
    public String getAlertPhone() {
        return this.alertPhone;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isAnimalOwner'].test(_this.isAnimalOwner.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "animalInformation"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isAnimalOwner'].test(_this.isAnimalOwner.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "animalInformation"
      )
    
    private String animalInformation;

    public void setAnimalInformation(final String animalInformation) {
        this.animalInformation = animalInformation;
    }

 
    @Column(name="animal_information"  )
      
    public String getAnimalInformation() {
        return this.animalInformation;
    }
  
    
      @NotNull(
        
        
        profiles = {"additional"},
        message = "isAnimalOwner"
      )
    
    private Boolean isAnimalOwner;

    public void setIsAnimalOwner(final Boolean isAnimalOwner) {
        this.isAnimalOwner = isAnimalOwner;
    }

 
    @Column(name="is_animal_owner"  )
      
    public Boolean getIsAnimalOwner() {
        return this.isAnimalOwner;
    }
  
    
      @NotNull(
        
        
        profiles = {"additional"},
        message = "isSecurityCompany"
      )
    
    private Boolean isSecurityCompany;

    public void setIsSecurityCompany(final Boolean isSecurityCompany) {
        this.isSecurityCompany = isSecurityCompany;
    }

 
    @Column(name="is_security_company"  )
      
    public Boolean getIsSecurityCompany() {
        return this.isSecurityCompany;
    }
  
    
      @NotNull(
        
        
        profiles = {"additional"},
        message = "light"
      )
    
    private Boolean light;

    public void setLight(final Boolean light) {
        this.light = light;
    }

 
    @Column(name="light"  )
      
    public Boolean getLight() {
        return this.light;
    }
  
    
      @NotNull(
        
        
        profiles = {"contact"},
        message = "otherContact"
      )
    
    private Boolean otherContact;

    public void setOtherContact(final Boolean otherContact) {
        this.otherContact = otherContact;
    }

 
    @Column(name="other_contact"  )
      
    public Boolean getOtherContact() {
        return this.otherContact;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactAddress"
      )
    
      @AssertValid(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactAddress"
      )
    
    private org.libredemat.business.users.Address otherContactAddress;

    public void setOtherContactAddress(final org.libredemat.business.users.Address otherContactAddress) {
        this.otherContactAddress = otherContactAddress;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="other_contact_address_id")
      
    public org.libredemat.business.users.Address getOtherContactAddress() {
        return this.otherContactAddress;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactDuplicateKey"
      )
    
    private Boolean otherContactDuplicateKey;

    public void setOtherContactDuplicateKey(final Boolean otherContactDuplicateKey) {
        this.otherContactDuplicateKey = otherContactDuplicateKey;
    }

 
    @Column(name="other_contact_duplicate_key"  )
      
    public Boolean getOtherContactDuplicateKey() {
        return this.otherContactDuplicateKey;
    }
  
    
      @MaxLength(
        
          value = 38,
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactFirstName"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactFirstName"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactFirstName"
      )
    
    private String otherContactFirstName;

    public void setOtherContactFirstName(final String otherContactFirstName) {
        this.otherContactFirstName = otherContactFirstName;
    }

 
    @Column(name="other_contact_first_name" , length=38 )
      
    public String getOtherContactFirstName() {
        return this.otherContactFirstName;
    }
  
    
      @MaxLength(
        
          value = 38,
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactLastName"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactLastName"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactLastName"
      )
    
    private String otherContactLastName;

    public void setOtherContactLastName(final String otherContactLastName) {
        this.otherContactLastName = otherContactLastName;
    }

 
    @Column(name="other_contact_last_name" , length=38 )
      
    public String getOtherContactLastName() {
        return this.otherContactLastName;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactPhone"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactPhone"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['otherContact'].test(_this.otherContact.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"contact"},
        message = "otherContactPhone"
      )
    
    private String otherContactPhone;

    public void setOtherContactPhone(final String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

 
    @Column(name="other_contact_phone" , length=10 )
      
    public String getOtherContactPhone() {
        return this.otherContactPhone;
    }
  
    
      @NotNull(
        
        
        profiles = {"rules"},
        message = "rulesAndRegulationsAcceptance"
      )
    
      @AssertTrue(
        
        
        profiles = {"rules"},
        message = "rulesAndRegulationsAcceptance"
      )
    
    private Boolean rulesAndRegulationsAcceptance;

    public void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        this.rulesAndRegulationsAcceptance = rulesAndRegulationsAcceptance;
    }

 
    @Column(name="rules_and_regulations_acceptance"  )
      
    public Boolean getRulesAndRegulationsAcceptance() {
        return this.rulesAndRegulationsAcceptance;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyAddress"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyAddress"
      )
    
    private String securityCompanyAddress;

    public void setSecurityCompanyAddress(final String securityCompanyAddress) {
        this.securityCompanyAddress = securityCompanyAddress;
    }

 
    @Column(name="security_company_address"  )
      
    public String getSecurityCompanyAddress() {
        return this.securityCompanyAddress;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyName"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyName"
      )
    
    private String securityCompanyName;

    public void setSecurityCompanyName(final String securityCompanyName) {
        this.securityCompanyName = securityCompanyName;
    }

 
    @Column(name="security_company_name"  )
      
    public String getSecurityCompanyName() {
        return this.securityCompanyName;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyTelephone"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyTelephone"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isSecurityCompany'].test(_this.isSecurityCompany.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"additional"},
        message = "securityCompanyTelephone"
      )
    
    private String securityCompanyTelephone;

    public void setSecurityCompanyTelephone(final String securityCompanyTelephone) {
        this.securityCompanyTelephone = securityCompanyTelephone;
    }

 
    @Column(name="security_company_telephone" , length=10 )
      
    public String getSecurityCompanyTelephone() {
        return this.securityCompanyTelephone;
    }
  
}
