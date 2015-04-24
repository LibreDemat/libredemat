

package org.libredemat.business.request.technical;

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
@Table(name="technical_intervention_request")
public class TechnicalInterventionRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public TechnicalInterventionRequestData() {
      
    }

    @Override
    public TechnicalInterventionRequestData clone() {
        TechnicalInterventionRequestData result = new TechnicalInterventionRequestData();
        
          
            
        result.setInterventionDescription(interventionDescription);
      
          
        
          
            
        if (interventionPlace != null)
            result.setInterventionPlace(interventionPlace.clone());
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> interventionTypeList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setInterventionType(interventionTypeList);
      
          
        
          
            
        result.setOtherInterventionLabel(otherInterventionLabel);
      
          
        
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
        
        
        profiles = {"intervention"},
        message = "interventionDescription"
      )
    
      @NotBlank(
        
        
        profiles = {"intervention"},
        message = "interventionDescription"
      )
    
    private String interventionDescription;

    public void setInterventionDescription(final String interventionDescription) {
        this.interventionDescription = interventionDescription;
    }

 
    @Column(name="intervention_description"  )
      
    public String getInterventionDescription() {
        return this.interventionDescription;
    }
  
    
      @NotNull(
        
        
        profiles = {"intervention"},
        message = "interventionPlace"
      )
    
      @AssertValid(
        
        
        profiles = {"intervention"},
        message = "interventionPlace"
      )
    
    private org.libredemat.business.users.Address interventionPlace;

    public void setInterventionPlace(final org.libredemat.business.users.Address interventionPlace) {
        this.interventionPlace = interventionPlace;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="intervention_place_id")
      
    public org.libredemat.business.users.Address getInterventionPlace() {
        return this.interventionPlace;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"intervention"},
        message = "interventionType"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"intervention"},
        message = "interventionType"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> interventionType;

    public void setInterventionType(final List<org.libredemat.business.request.LocalReferentialData> interventionType) {
        this.interventionType = interventionType;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="technical_intervention_request_intervention_type",
            joinColumns=
                @JoinColumn(name="technical_intervention_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="intervention_type_id"))
    @OrderColumn(name="intervention_type_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getInterventionType() {
        return this.interventionType;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "if (_this.interventionType == null || _this.interventionType.isEmpty()) return false; _this.interventionType.each { active &= _this.conditions['interventionType'].test(it.name) };" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"intervention"},
        message = "otherInterventionLabel"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "if (_this.interventionType == null || _this.interventionType.isEmpty()) return false; _this.interventionType.each { active &= _this.conditions['interventionType'].test(it.name) };" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"intervention"},
        message = "otherInterventionLabel"
      )
    
    private String otherInterventionLabel;

    public void setOtherInterventionLabel(final String otherInterventionLabel) {
        this.otherInterventionLabel = otherInterventionLabel;
    }

 
    @Column(name="other_intervention_label"  )
      
    public String getOtherInterventionLabel() {
        return this.otherInterventionLabel;
    }
  
}
