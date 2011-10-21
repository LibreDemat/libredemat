
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
@Table(name="hccr_family_assistance_member")
public class HccrFamilyAssistanceMember implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        HandicapCompensationChildRequest.conditions;

    public HccrFamilyAssistanceMember() {
        super();
      
    }

    public final String modelToXmlString() {
        HccrFamilyAssistanceMemberType object = (HccrFamilyAssistanceMemberType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final HccrFamilyAssistanceMemberType modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        HccrFamilyAssistanceMemberType hccrFamilyAssistanceMember = HccrFamilyAssistanceMemberType.Factory.newInstance();
        int i = 0;
    
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberLastName(this.familyAssistanceMemberLastName);
      
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberRelationship(this.familyAssistanceMemberRelationship);
      
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberFirstName(this.familyAssistanceMemberFirstName);
      
        return hccrFamilyAssistanceMember;
    }

    public static HccrFamilyAssistanceMember xmlToModel(HccrFamilyAssistanceMemberType hccrFamilyAssistanceMemberDoc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        HccrFamilyAssistanceMember hccrFamilyAssistanceMember = new HccrFamilyAssistanceMember();
    
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberLastName(hccrFamilyAssistanceMemberDoc.getFamilyAssistanceMemberLastName());
      
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberRelationship(hccrFamilyAssistanceMemberDoc.getFamilyAssistanceMemberRelationship());
      
        hccrFamilyAssistanceMember.setFamilyAssistanceMemberFirstName(hccrFamilyAssistanceMemberDoc.getFamilyAssistanceMemberFirstName());
      
        return hccrFamilyAssistanceMember;
    }

    @Override
    public HccrFamilyAssistanceMember clone() {
        HccrFamilyAssistanceMember result = new HccrFamilyAssistanceMember();
        
          
            
        result.setFamilyAssistanceMemberLastName(familyAssistanceMemberLastName);
      
          
        
          
            
        result.setFamilyAssistanceMemberRelationship(familyAssistanceMemberRelationship);
      
          
        
          
            
        result.setFamilyAssistanceMemberFirstName(familyAssistanceMemberFirstName);
      
          
        
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

  
    
      @MaxLength(
        
          value = 38,
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberLastName"
      )
    
      @NotNull(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberLastName"
      )
    
      @NotBlank(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberLastName"
      )
    
    private String familyAssistanceMemberLastName;

    public void setFamilyAssistanceMemberLastName(final String familyAssistanceMemberLastName) {
        this.familyAssistanceMemberLastName = familyAssistanceMemberLastName;
    }


    @Column(name="family_assistance_member_last_name" , length=38 )
      
    public String getFamilyAssistanceMemberLastName() {
        return this.familyAssistanceMemberLastName;
    }
  
    
      @MaxLength(
        
          value = 60,
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberRelationship"
      )
    
      @NotNull(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberRelationship"
      )
    
      @NotBlank(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberRelationship"
      )
    
    private String familyAssistanceMemberRelationship;

    public void setFamilyAssistanceMemberRelationship(final String familyAssistanceMemberRelationship) {
        this.familyAssistanceMemberRelationship = familyAssistanceMemberRelationship;
    }


    @Column(name="family_assistance_member_relationship" , length=60 )
      
    public String getFamilyAssistanceMemberRelationship() {
        return this.familyAssistanceMemberRelationship;
    }
  
    
      @MaxLength(
        
          value = 38,
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberFirstName"
      )
    
      @NotNull(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberFirstName"
      )
    
      @NotBlank(
        
        
        profiles = {"aid"},
        message = "familyAssistanceMemberFirstName"
      )
    
    private String familyAssistanceMemberFirstName;

    public void setFamilyAssistanceMemberFirstName(final String familyAssistanceMemberFirstName) {
        this.familyAssistanceMemberFirstName = familyAssistanceMemberFirstName;
    }


    @Column(name="family_assistance_member_first_name" , length=38 )
      
    public String getFamilyAssistanceMemberFirstName() {
        return this.familyAssistanceMemberFirstName;
    }
  
}
