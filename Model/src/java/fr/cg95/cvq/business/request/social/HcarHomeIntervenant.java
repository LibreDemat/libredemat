
package fr.cg95.cvq.business.request.social;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalTime;

import net.sf.oval.constraint.*;
import org.apache.xmlbeans.XmlOptions;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.request.*;
import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.xml.common.RequestType;
import fr.cg95.cvq.xml.common.*;
import fr.cg95.cvq.xml.request.social.*;
import fr.cg95.cvq.service.request.LocalReferential;
import fr.cg95.cvq.service.request.condition.IConditionChecker;
import javax.persistence.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * Generated class file, do not edit !
 */
@Entity
@Table(name="hcar_home_intervenant")
public class HcarHomeIntervenant implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        HandicapCompensationAdultRequest.conditions;

    public HcarHomeIntervenant() {
        super();
      
    }

    public final String modelToXmlString() {
        HcarHomeIntervenantType object = (HcarHomeIntervenantType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final HcarHomeIntervenantType modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        HcarHomeIntervenantType hcarHomeIntervenant = HcarHomeIntervenantType.Factory.newInstance();
        int i = 0;
    
        if (this.homeIntervenantKind != null)
            hcarHomeIntervenant.setHomeIntervenantKind(fr.cg95.cvq.xml.request.social.HcarHomeIntervenantKindType.Enum.forString(this.homeIntervenantKind.toString()));
      
        hcarHomeIntervenant.setHomeIntervenantDetails(this.homeIntervenantDetails);
      
        return hcarHomeIntervenant;
    }

    public static HcarHomeIntervenant xmlToModel(HcarHomeIntervenantType hcarHomeIntervenantDoc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        HcarHomeIntervenant hcarHomeIntervenant = new HcarHomeIntervenant();
    
        if (hcarHomeIntervenantDoc.getHomeIntervenantKind() != null)
            hcarHomeIntervenant.setHomeIntervenantKind(fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType.forString(hcarHomeIntervenantDoc.getHomeIntervenantKind().toString()));
        else
            hcarHomeIntervenant.setHomeIntervenantKind(fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType.getDefaultHcarHomeIntervenantKindType());
      
        hcarHomeIntervenant.setHomeIntervenantDetails(hcarHomeIntervenantDoc.getHomeIntervenantDetails());
      
        return hcarHomeIntervenant;
    }

    @Override
    public HcarHomeIntervenant clone() {
        HcarHomeIntervenant result = new HcarHomeIntervenant();
        
          
            
        if (homeIntervenantKind != null)
            result.setHomeIntervenantKind(homeIntervenantKind);
        else
            result.setHomeIntervenantKind(fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType.getDefaultHcarHomeIntervenantKindType());
      
          
        
          
            
        result.setHomeIntervenantDetails(homeIntervenantDetails);
      
          
        
        return result;
    }

    private Long id;

    public final void setId(final Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public final Long getId() {
        return this.id;
    }

  
    
      @NotNull(
        
        
        profiles = {"aid"},
        message = "homeIntervenantKind"
      )
    
    private fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType homeIntervenantKind;

    public void setHomeIntervenantKind(final fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType homeIntervenantKind) {
        this.homeIntervenantKind = homeIntervenantKind;
    }


    @Enumerated(EnumType.STRING)
    @Column(name="home_intervenant_kind"  )
      
    public fr.cg95.cvq.business.request.social.HcarHomeIntervenantKindType getHomeIntervenantKind() {
        return this.homeIntervenantKind;
    }
  
    
      @MaxLength(
        
          value = 60,
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hcarHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"aid"},
        message = "homeIntervenantDetails"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hcarHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"aid"},
        message = "homeIntervenantDetails"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hcarHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"aid"},
        message = "homeIntervenantDetails"
      )
    
    private String homeIntervenantDetails;

    public void setHomeIntervenantDetails(final String homeIntervenantDetails) {
        this.homeIntervenantDetails = homeIntervenantDetails;
    }


    @Column(name="home_intervenant_details" , length=60 )
      
    public String getHomeIntervenantDetails() {
        return this.homeIntervenantDetails;
    }
  
}
