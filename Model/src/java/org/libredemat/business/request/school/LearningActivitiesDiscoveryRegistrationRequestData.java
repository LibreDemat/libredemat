

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
@Table(name="learning_activities_discovery_registration_request")
public class LearningActivitiesDiscoveryRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public LearningActivitiesDiscoveryRegistrationRequestData() {
      
    }

    @Override
    public LearningActivitiesDiscoveryRegistrationRequestData clone() {
        LearningActivitiesDiscoveryRegistrationRequestData result = new LearningActivitiesDiscoveryRegistrationRequestData();
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> atelierEveilList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setAtelierEveil(atelierEveilList);
      
          
        
          
            
        result.setAtelierEveilPrecisionChoix(atelierEveilPrecisionChoix);
      
          
        
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
        
        
        profiles = {"subject"},
        message = "atelierEveil"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"subject"},
        message = "atelierEveil"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> atelierEveil;

    public void setAtelierEveil(final List<org.libredemat.business.request.LocalReferentialData> atelierEveil) {
        this.atelierEveil = atelierEveil;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="learning_activities_discovery_registration_request_atelier_eveil",
            joinColumns=
                @JoinColumn(name="learning_activities_discovery_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="atelier_eveil_id"))
    @OrderColumn(name="atelier_eveil_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getAtelierEveil() {
        return this.atelierEveil;
    }
  
    
      @MaxLength(
        
          value = 255,
        
        
        profiles = {"subject"},
        message = "atelierEveilPrecisionChoix"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
        profiles = {"subject"},
        message = "atelierEveilPrecisionChoix"
      )
    
    private String atelierEveilPrecisionChoix;

    public void setAtelierEveilPrecisionChoix(final String atelierEveilPrecisionChoix) {
        this.atelierEveilPrecisionChoix = atelierEveilPrecisionChoix;
    }

 
    @Column(name="atelier_eveil_precision_choix" , length=255 )
      
    public String getAtelierEveilPrecisionChoix() {
        return this.atelierEveilPrecisionChoix;
    }
  
}
