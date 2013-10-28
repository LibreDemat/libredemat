

package org.libredemat.business.request.election;

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
@Table(name="electoral_roll_registration_request")
public class ElectoralRollRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ElectoralRollRegistrationRequestData() {
      
        motive = org.libredemat.business.request.election.ElectoralMotiveType.NEW_CITY_RESIDENT;
      
    }

    @Override
    public ElectoralRollRegistrationRequestData clone() {
        ElectoralRollRegistrationRequestData result = new ElectoralRollRegistrationRequestData();
        
          
            
        result.setElectoralNumber(electoralNumber);
      
          
        
          
            
        if (motive != null)
            result.setMotive(motive);
        else
            result.setMotive(org.libredemat.business.request.election.ElectoralMotiveType.getDefaultElectoralMotiveType());
      
          
        
          
            
        result.setPollingSchoolName(pollingSchoolName);
      
          
        
          
            
        result.setPollingStation(pollingStation);
      
          
        
          
            
        if (subjectAddressOutsideCity != null)
            result.setSubjectAddressOutsideCity(subjectAddressOutsideCity.clone());
      
          
        
          
            
        if (subjectNationality != null)
            result.setSubjectNationality(subjectNationality);
        else
            result.setSubjectNationality(org.libredemat.business.users.NationalityType.getDefaultNationalityType());
      
          
        
          
            
        result.setSubjectOldCity(subjectOldCity);
      
          
        
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

  
    
    private Long electoralNumber;

    public void setElectoralNumber(final Long electoralNumber) {
        this.electoralNumber = electoralNumber;
    }

 
    @Column(name="electoral_number"  )
      
    public Long getElectoralNumber() {
        return this.electoralNumber;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "motive"
      )
    
    private org.libredemat.business.request.election.ElectoralMotiveType motive;

    public void setMotive(final org.libredemat.business.request.election.ElectoralMotiveType motive) {
        this.motive = motive;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="motive"  )
      
    public org.libredemat.business.request.election.ElectoralMotiveType getMotive() {
        return this.motive;
    }
  
    
    private String pollingSchoolName;

    public void setPollingSchoolName(final String pollingSchoolName) {
        this.pollingSchoolName = pollingSchoolName;
    }

 
    @Column(name="polling_school_name"  )
      
    public String getPollingSchoolName() {
        return this.pollingSchoolName;
    }
  
    
    private Long pollingStation;

    public void setPollingStation(final Long pollingStation) {
        this.pollingStation = pollingStation;
    }

 
    @Column(name="polling_station"  )
      
    public Long getPollingStation() {
        return this.pollingStation;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['motive'].test(_this.motive.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "subjectAddressOutsideCity"
      )
    
      @AssertValid(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['motive'].test(_this.motive.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "subjectAddressOutsideCity"
      )
    
    private org.libredemat.business.users.Address subjectAddressOutsideCity;

    public void setSubjectAddressOutsideCity(final org.libredemat.business.users.Address subjectAddressOutsideCity) {
        this.subjectAddressOutsideCity = subjectAddressOutsideCity;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="subject_address_outside_city_id")
      
    public org.libredemat.business.users.Address getSubjectAddressOutsideCity() {
        return this.subjectAddressOutsideCity;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "subjectNationality"
      )
    
    private org.libredemat.business.users.NationalityType subjectNationality;

    public void setSubjectNationality(final org.libredemat.business.users.NationalityType subjectNationality) {
        this.subjectNationality = subjectNationality;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="subject_nationality" , length=32 )
      
    public org.libredemat.business.users.NationalityType getSubjectNationality() {
        return this.subjectNationality;
    }
  
    
      @MaxLength(
        
          value = 5,
        
        
          when = "groovy:def active = true;" +
          
            "active &= !_this.conditions['motive'].test(_this.motive.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "subjectOldCity"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= !_this.conditions['motive'].test(_this.motive.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "subjectOldCity"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            "active &= !_this.conditions['motive'].test(_this.motive.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"registration"},
        message = "subjectOldCity"
      )
    
    private String subjectOldCity;

    public void setSubjectOldCity(final String subjectOldCity) {
        this.subjectOldCity = subjectOldCity;
    }

 
    @Column(name="subject_old_city" , length=5 )
      
    public String getSubjectOldCity() {
        return this.subjectOldCity;
    }
  
}
