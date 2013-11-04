

package org.libredemat.business.request.urbanism;

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
@Table(name="sewer_connection_request")
public class SewerConnectionRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public SewerConnectionRequestData() {
      
        moreThanTwoYears = Boolean.valueOf(false);
      
        requesterQuality = org.libredemat.business.request.urbanism.ScrRequesterQualityType.OWNER;
      
    }

    @Override
    public SewerConnectionRequestData clone() {
        SewerConnectionRequestData result = new SewerConnectionRequestData();
        
          
            
        result.setLocality(locality);
      
          
        
          
            
        result.setMoreThanTwoYears(moreThanTwoYears);
      
          
        
          
            
        result.setNumber(number);
      
          
        
          
            
        if (ownerAddress != null)
            result.setOwnerAddress(ownerAddress.clone());
      
          
        
          
            
        result.setOwnerFirstNames(ownerFirstNames);
      
          
        
          
            
        result.setOwnerLastName(ownerLastName);
      
          
        
          
            
        if (requesterQuality != null)
            result.setRequesterQuality(requesterQuality);
        else
            result.setRequesterQuality(org.libredemat.business.request.urbanism.ScrRequesterQualityType.getDefaultScrRequesterQualityType());
      
          
        
          
            
        result.setSection(section);
      
          
        
          
            
        result.setTransportationRoute(transportationRoute);
      
          
        
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

  
    
    private String locality;

    public void setLocality(final String locality) {
        this.locality = locality;
    }

 
    @Column(name="locality"  )
      
    public String getLocality() {
        return this.locality;
    }
  
    
      @NotNull(
        
        
        profiles = {"cadastre"},
        message = "moreThanTwoYears"
      )
    
    private Boolean moreThanTwoYears;

    public void setMoreThanTwoYears(final Boolean moreThanTwoYears) {
        this.moreThanTwoYears = moreThanTwoYears;
    }

 
    @Column(name="more_than_two_years"  )
      
    public Boolean getMoreThanTwoYears() {
        return this.moreThanTwoYears;
    }
  
    
      @NotNull(
        
        
        profiles = {"cadastre"},
        message = "number"
      )
    
    private java.math.BigInteger number;

    public void setNumber(final java.math.BigInteger number) {
        this.number = number;
    }

 
    @Column(name="number" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getNumber() {
        return this.number;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerAddress"
      )
    
      @AssertValid(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerAddress"
      )
    
    private org.libredemat.business.users.Address ownerAddress;

    public void setOwnerAddress(final org.libredemat.business.users.Address ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="owner_address_id")
      
    public org.libredemat.business.users.Address getOwnerAddress() {
        return this.ownerAddress;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerFirstNames"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerFirstNames"
      )
    
    private String ownerFirstNames;

    public void setOwnerFirstNames(final String ownerFirstNames) {
        this.ownerFirstNames = ownerFirstNames;
    }

 
    @Column(name="owner_first_names"  )
      
    public String getOwnerFirstNames() {
        return this.ownerFirstNames;
    }
  
    
      @MaxLength(
        
          value = 38,
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerLastName"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerLastName"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['requesterQuality'].test(_this.requesterQuality.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"cadastre"},
        message = "ownerLastName"
      )
    
    private String ownerLastName;

    public void setOwnerLastName(final String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

 
    @Column(name="owner_last_name" , length=38 )
      
    public String getOwnerLastName() {
        return this.ownerLastName;
    }
  
    
      @NotNull(
        
        
        profiles = {"cadastre"},
        message = "requesterQuality"
      )
    
    private org.libredemat.business.request.urbanism.ScrRequesterQualityType requesterQuality;

    public void setRequesterQuality(final org.libredemat.business.request.urbanism.ScrRequesterQualityType requesterQuality) {
        this.requesterQuality = requesterQuality;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="requester_quality"  )
      
    public org.libredemat.business.request.urbanism.ScrRequesterQualityType getRequesterQuality() {
        return this.requesterQuality;
    }
  
    
      @NotNull(
        
        
        profiles = {"cadastre"},
        message = "section"
      )
    
      @NotBlank(
        
        
        profiles = {"cadastre"},
        message = "section"
      )
    
    private String section;

    public void setSection(final String section) {
        this.section = section;
    }

 
    @Column(name="section"  )
      
    public String getSection() {
        return this.section;
    }
  
    
    private String transportationRoute;

    public void setTransportationRoute(final String transportationRoute) {
        this.transportationRoute = transportationRoute;
    }

 
    @Column(name="transportation_route"  )
      
    public String getTransportationRoute() {
        return this.transportationRoute;
    }
  
}
