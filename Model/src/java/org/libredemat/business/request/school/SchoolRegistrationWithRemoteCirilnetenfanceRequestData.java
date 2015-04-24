

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
@Table(name="school_registration_with_remote_cirilnetenfance_request")
public class SchoolRegistrationWithRemoteCirilnetenfanceRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public SchoolRegistrationWithRemoteCirilnetenfanceRequestData() {
      
        rulesAndRegulationsAcceptance = Boolean.valueOf(false);
      
    }

    @Override
    public SchoolRegistrationWithRemoteCirilnetenfanceRequestData clone() {
        SchoolRegistrationWithRemoteCirilnetenfanceRequestData result = new SchoolRegistrationWithRemoteCirilnetenfanceRequestData();
        
          
            
        result.setIdSchoolName(idSchoolName);
      
          
        
          
            
        result.setLabelSchoolName(labelSchoolName);
      
          
        
          
            
        result.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
      
          
        
          
            
        if (section != null)
            result.setSection(section);
        else
            result.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
          
        
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
        message = "idSchoolName"
      )
    
      @NotBlank(
        
        
        profiles = {"registration"},
        message = "idSchoolName"
      )
    
    private String idSchoolName;

    public void setIdSchoolName(final String idSchoolName) {
        this.idSchoolName = idSchoolName;
    }

 
    @Column(name="id_school_name"  )
      
    public String getIdSchoolName() {
        return this.idSchoolName;
    }
  
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "labelSchoolName"
      )
    
      @NotBlank(
        
        
        profiles = {"registration"},
        message = "labelSchoolName"
      )
    
    private String labelSchoolName;

    public void setLabelSchoolName(final String labelSchoolName) {
        this.labelSchoolName = labelSchoolName;
    }

 
    @Column(name="label_school_name"  )
      
    public String getLabelSchoolName() {
        return this.labelSchoolName;
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
  
}
