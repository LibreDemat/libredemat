
package org.libredemat.business.request.social;

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
import org.libredemat.business.authority.*;
import org.libredemat.business.request.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.social.*;
import org.libredemat.service.request.LocalReferential;
import org.libredemat.service.request.condition.IConditionChecker;
import javax.persistence.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * Generated class file, do not edit !
 */
@Entity
@Table(name="hccr_home_intervenant")
public class HccrHomeIntervenant implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        HandicapCompensationChildRequest.conditions;

    public HccrHomeIntervenant() {
        super();
      
    }

    public final String modelToXmlString() {
        HccrHomeIntervenantType object = (HccrHomeIntervenantType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final HccrHomeIntervenantType modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        HccrHomeIntervenantType hccrHomeIntervenant = HccrHomeIntervenantType.Factory.newInstance();
        int i = 0;
    
        if (this.homeIntervenantKind != null)
            hccrHomeIntervenant.setHomeIntervenantKind(org.libredemat.xml.request.social.HccrHomeIntervenantKindType.Enum.forString(this.homeIntervenantKind.getLegacyLabel()));
      
        hccrHomeIntervenant.setHomeIntervenantDetails(this.homeIntervenantDetails);
      
        return hccrHomeIntervenant;
    }

    public static HccrHomeIntervenant xmlToModel(HccrHomeIntervenantType hccrHomeIntervenantDoc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        HccrHomeIntervenant hccrHomeIntervenant = new HccrHomeIntervenant();
    
        if (hccrHomeIntervenantDoc.getHomeIntervenantKind() != null)
            hccrHomeIntervenant.setHomeIntervenantKind(org.libredemat.business.request.social.HccrHomeIntervenantKindType.forString(hccrHomeIntervenantDoc.getHomeIntervenantKind().toString()));
        else
            hccrHomeIntervenant.setHomeIntervenantKind(org.libredemat.business.request.social.HccrHomeIntervenantKindType.getDefaultHccrHomeIntervenantKindType());
      
        hccrHomeIntervenant.setHomeIntervenantDetails(hccrHomeIntervenantDoc.getHomeIntervenantDetails());
      
        return hccrHomeIntervenant;
    }

    @Override
    public HccrHomeIntervenant clone() {
        HccrHomeIntervenant result = new HccrHomeIntervenant();
        
          
            
        if (homeIntervenantKind != null)
            result.setHomeIntervenantKind(homeIntervenantKind);
        else
            result.setHomeIntervenantKind(org.libredemat.business.request.social.HccrHomeIntervenantKindType.getDefaultHccrHomeIntervenantKindType());
      
          
        
          
            
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
    
    private org.libredemat.business.request.social.HccrHomeIntervenantKindType homeIntervenantKind;

    public void setHomeIntervenantKind(final org.libredemat.business.request.social.HccrHomeIntervenantKindType homeIntervenantKind) {
        this.homeIntervenantKind = homeIntervenantKind;
    }


    @Enumerated(EnumType.STRING)
    @Column(name="home_intervenant_kind"  )
      
    public org.libredemat.business.request.social.HccrHomeIntervenantKindType getHomeIntervenantKind() {
        return this.homeIntervenantKind;
    }
  
    
      @MaxLength(
        
          value = 60,
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hccrHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"aid"},
        message = "homeIntervenantDetails"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hccrHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
            "return active",
        
        profiles = {"aid"},
        message = "homeIntervenantDetails"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            "active &= _this.conditions['hccrHomeIntervenant.homeIntervenantKind'].test(_this.homeIntervenantKind.toString());" +
                
              
            
            
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
