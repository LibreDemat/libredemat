

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
@Table(name="perischool_activity_registration_request")
public class PerischoolActivityRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public PerischoolActivityRegistrationRequestData() {
      
        childPhotoExploitationPermission = Boolean.valueOf(false);
      
        classTripPermission = Boolean.valueOf(false);
      
        hospitalizationPermission = Boolean.valueOf(false);
      
        rulesAndRegulationsAcceptance = Boolean.valueOf(false);
      
        section = org.libredemat.business.users.SectionType.UNKNOWN;
      
    }

    @Override
    public PerischoolActivityRegistrationRequestData clone() {
        PerischoolActivityRegistrationRequestData result = new PerischoolActivityRegistrationRequestData();
        
          
            
        List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> authorizedIndividualsList = new ArrayList<org.libredemat.business.request.school.PerischoolAuthorizedIndividual>();
        result.setAuthorizedIndividuals(authorizedIndividualsList);
      
          
        
          
            
        result.setChildPhotoExploitationPermission(childPhotoExploitationPermission);
      
          
        
          
            
        result.setClassTripPermission(classTripPermission);
      
          
        
          
            
        List<org.libredemat.business.request.school.PerischoolContactIndividual> contactIndividualsList = new ArrayList<org.libredemat.business.request.school.PerischoolContactIndividual>();
        result.setContactIndividuals(contactIndividualsList);
      
          
        
          
            
        result.setHospitalizationPermission(hospitalizationPermission);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> perischoolActivityList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setPerischoolActivity(perischoolActivityList);
      
          
        
          
            
        result.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
      
          
        
          
            result.setSchool(school);
          
        
          
            
        if (section != null)
            result.setSection(section);
        else
            result.setSection(org.libredemat.business.users.SectionType.getDefaultSectionType());
      
          
        
          
            
        result.setUrgencyPhone(urgencyPhone);
      
          
        
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

  
    
      @AssertValid(
        
        
        profiles = {"authorization"},
        message = "authorizedIndividuals"
      )
    
    private List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> authorizedIndividuals;

    public void setAuthorizedIndividuals(final List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> authorizedIndividuals) {
        this.authorizedIndividuals = authorizedIndividuals;
    }

 
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="authorized_individuals_index")
    @JoinColumn(name="perischool_activity_registration_request_id")
      
    public List<org.libredemat.business.request.school.PerischoolAuthorizedIndividual> getAuthorizedIndividuals() {
        return this.authorizedIndividuals;
    }
  
    
    private Boolean childPhotoExploitationPermission;

    public void setChildPhotoExploitationPermission(final Boolean childPhotoExploitationPermission) {
        this.childPhotoExploitationPermission = childPhotoExploitationPermission;
    }

 
    @Column(name="child_photo_exploitation_permission"  )
      
    public Boolean getChildPhotoExploitationPermission() {
        return this.childPhotoExploitationPermission;
    }
  
    
    private Boolean classTripPermission;

    public void setClassTripPermission(final Boolean classTripPermission) {
        this.classTripPermission = classTripPermission;
    }

 
    @Column(name="class_trip_permission"  )
      
    public Boolean getClassTripPermission() {
        return this.classTripPermission;
    }
  
    
      @AssertValid(
        
        
        profiles = {"contact"},
        message = "contactIndividuals"
      )
    
    private List<org.libredemat.business.request.school.PerischoolContactIndividual> contactIndividuals;

    public void setContactIndividuals(final List<org.libredemat.business.request.school.PerischoolContactIndividual> contactIndividuals) {
        this.contactIndividuals = contactIndividuals;
    }

 
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="contact_individuals_index")
    @JoinColumn(name="perischool_activity_registration_request_id")
      
    public List<org.libredemat.business.request.school.PerischoolContactIndividual> getContactIndividuals() {
        return this.contactIndividuals;
    }
  
    
    private Boolean hospitalizationPermission;

    public void setHospitalizationPermission(final Boolean hospitalizationPermission) {
        this.hospitalizationPermission = hospitalizationPermission;
    }

 
    @Column(name="hospitalization_permission"  )
      
    public Boolean getHospitalizationPermission() {
        return this.hospitalizationPermission;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"registration"},
        message = "perischoolActivity"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"registration"},
        message = "perischoolActivity"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> perischoolActivity;

    public void setPerischoolActivity(final List<org.libredemat.business.request.LocalReferentialData> perischoolActivity) {
        this.perischoolActivity = perischoolActivity;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="perischool_activity_registration_request_perischool_activity",
            joinColumns=
                @JoinColumn(name="perischool_activity_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="perischool_activity_id"))
    @OrderColumn(name="perischool_activity_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getPerischoolActivity() {
        return this.perischoolActivity;
    }
  
    
    private Boolean rulesAndRegulationsAcceptance;

    public void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        this.rulesAndRegulationsAcceptance = rulesAndRegulationsAcceptance;
    }

 
    @Column(name="rules_and_regulations_acceptance"  )
      
    public Boolean getRulesAndRegulationsAcceptance() {
        return this.rulesAndRegulationsAcceptance;
    }
  
    
      @AssertValid(
        
        
        profiles = {"administration"},
        message = "school"
      )
    
    private org.libredemat.business.authority.School school;

    public void setSchool(final org.libredemat.business.authority.School school) {
        this.school = school;
    }

 
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="school_id")
      
    public org.libredemat.business.authority.School getSchool() {
        return this.school;
    }
  
    
      @NotNull(
        
        
        profiles = {"administration"},
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
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
      @NotNull(
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
      @NotBlank(
        
        
        profiles = {"registration"},
        message = "urgencyPhone"
      )
    
    private String urgencyPhone;

    public void setUrgencyPhone(final String urgencyPhone) {
        this.urgencyPhone = urgencyPhone;
    }

 
    @Column(name="urgency_phone" , length=10 )
      
    public String getUrgencyPhone() {
        return this.urgencyPhone;
    }
  
}
