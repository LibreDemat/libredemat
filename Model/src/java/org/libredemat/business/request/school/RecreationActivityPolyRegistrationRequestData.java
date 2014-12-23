

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
@Table(name="recreation_activity_poly_registration_request")
public class RecreationActivityPolyRegistrationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public RecreationActivityPolyRegistrationRequestData() {
      
        childPhotoExploitationPolyPermission = Boolean.valueOf(false);
      
        classTripPolyPermission = Boolean.valueOf(false);
      
        hospitalizationPolyPermission = Boolean.valueOf(false);
      
        rulesAndRegulationsPolyAcceptance = Boolean.valueOf(false);
      
    }

    @Override
    public RecreationActivityPolyRegistrationRequestData clone() {
        RecreationActivityPolyRegistrationRequestData result = new RecreationActivityPolyRegistrationRequestData();
        
          
            
        List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> authorizedPolyIndividualsList = new ArrayList<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual>();
        result.setAuthorizedPolyIndividuals(authorizedPolyIndividualsList);
      
          
        
          
            
        result.setChildPhotoExploitationPolyPermission(childPhotoExploitationPolyPermission);
      
          
        
          
            
        result.setClassTripPolyPermission(classTripPolyPermission);
      
          
        
          
            
        List<org.libredemat.business.request.school.RecreationContactPolyIndividual> contactPolyIndividualsList = new ArrayList<org.libredemat.business.request.school.RecreationContactPolyIndividual>();
        result.setContactPolyIndividuals(contactPolyIndividualsList);
      
          
        
          
            
        result.setHospitalizationPolyPermission(hospitalizationPolyPermission);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> recreationPolyActivityList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setRecreationPolyActivity(recreationPolyActivityList);
      
          
        
          
            result.setRecreationPolyCenter(recreationPolyCenter);
          
        
          
            
        result.setRulesAndRegulationsPolyAcceptance(rulesAndRegulationsPolyAcceptance);
      
          
        
          
            
        result.setUrgencyPolyPhone(urgencyPolyPhone);
      
          
        
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
        message = "authorizedPolyIndividuals"
      )
    
    private List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> authorizedPolyIndividuals;

    public void setAuthorizedPolyIndividuals(final List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> authorizedPolyIndividuals) {
        this.authorizedPolyIndividuals = authorizedPolyIndividuals;
    }

 
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="authorized_poly_individuals_index")
    @JoinColumn(name="recreation_activity_poly_registration_request_id")
      
    public List<org.libredemat.business.request.school.RecreationAuthorizedPolyIndividual> getAuthorizedPolyIndividuals() {
        return this.authorizedPolyIndividuals;
    }
  
    
    private Boolean childPhotoExploitationPolyPermission;

    public void setChildPhotoExploitationPolyPermission(final Boolean childPhotoExploitationPolyPermission) {
        this.childPhotoExploitationPolyPermission = childPhotoExploitationPolyPermission;
    }

 
    @Column(name="child_photo_exploitation_poly_permission"  )
      
    public Boolean getChildPhotoExploitationPolyPermission() {
        return this.childPhotoExploitationPolyPermission;
    }
  
    
    private Boolean classTripPolyPermission;

    public void setClassTripPolyPermission(final Boolean classTripPolyPermission) {
        this.classTripPolyPermission = classTripPolyPermission;
    }

 
    @Column(name="class_trip_poly_permission"  )
      
    public Boolean getClassTripPolyPermission() {
        return this.classTripPolyPermission;
    }
  
    
      @AssertValid(
        
        
        profiles = {"contact"},
        message = "contactPolyIndividuals"
      )
    
    private List<org.libredemat.business.request.school.RecreationContactPolyIndividual> contactPolyIndividuals;

    public void setContactPolyIndividuals(final List<org.libredemat.business.request.school.RecreationContactPolyIndividual> contactPolyIndividuals) {
        this.contactPolyIndividuals = contactPolyIndividuals;
    }

 
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="contact_poly_individuals_index")
    @JoinColumn(name="recreation_activity_poly_registration_request_id")
      
    public List<org.libredemat.business.request.school.RecreationContactPolyIndividual> getContactPolyIndividuals() {
        return this.contactPolyIndividuals;
    }
  
    
    private Boolean hospitalizationPolyPermission;

    public void setHospitalizationPolyPermission(final Boolean hospitalizationPolyPermission) {
        this.hospitalizationPolyPermission = hospitalizationPolyPermission;
    }

 
    @Column(name="hospitalization_poly_permission"  )
      
    public Boolean getHospitalizationPolyPermission() {
        return this.hospitalizationPolyPermission;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"requester"},
        message = "recreationPolyActivity"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"requester"},
        message = "recreationPolyActivity"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> recreationPolyActivity;

    public void setRecreationPolyActivity(final List<org.libredemat.business.request.LocalReferentialData> recreationPolyActivity) {
        this.recreationPolyActivity = recreationPolyActivity;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="recreation_activity_poly_registration_request_recreation_poly_activity",
            joinColumns=
                @JoinColumn(name="recreation_activity_poly_registration_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="recreation_poly_activity_id"))
    @OrderColumn(name="recreation_poly_activity_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getRecreationPolyActivity() {
        return this.recreationPolyActivity;
    }
  
    
      @AssertValid(
        
        
        profiles = {"requester"},
        message = "recreationPolyCenter"
      )
    
    private org.libredemat.business.authority.RecreationCenter recreationPolyCenter;

    public void setRecreationPolyCenter(final org.libredemat.business.authority.RecreationCenter recreationPolyCenter) {
        this.recreationPolyCenter = recreationPolyCenter;
    }

 
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="recreation_poly_center_id")
      
    public org.libredemat.business.authority.RecreationCenter getRecreationPolyCenter() {
        return this.recreationPolyCenter;
    }
  
    
      @NotNull(
        
        
        profiles = {"rules"},
        message = "rulesAndRegulationsPolyAcceptance"
      )
    
      @AssertTrue(
        
        
        profiles = {"rules"},
        message = "rulesAndRegulationsPolyAcceptance"
      )
    
    private Boolean rulesAndRegulationsPolyAcceptance;

    public void setRulesAndRegulationsPolyAcceptance(final Boolean rulesAndRegulationsPolyAcceptance) {
        this.rulesAndRegulationsPolyAcceptance = rulesAndRegulationsPolyAcceptance;
    }

 
    @Column(name="rules_and_regulations_poly_acceptance"  )
      
    public Boolean getRulesAndRegulationsPolyAcceptance() {
        return this.rulesAndRegulationsPolyAcceptance;
    }
  
    
      @MaxLength(
        
          value = 10,
        
        
        profiles = {"requester"},
        message = "urgencyPolyPhone"
      )
    
      @NotNull(
        
        
        profiles = {"requester"},
        message = "urgencyPolyPhone"
      )
    
      @NotBlank(
        
        
        profiles = {"requester"},
        message = "urgencyPolyPhone"
      )
    
    private String urgencyPolyPhone;

    public void setUrgencyPolyPhone(final String urgencyPolyPhone) {
        this.urgencyPolyPhone = urgencyPolyPhone;
    }

 
    @Column(name="urgency_poly_phone" , length=10 )
      
    public String getUrgencyPolyPhone() {
        return this.urgencyPolyPhone;
    }
  
}
