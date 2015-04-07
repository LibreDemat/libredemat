

package org.libredemat.business.request.permit;

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
@Table(name="parking_permit_temporary_relocation_request")
public class ParkingPermitTemporaryRelocationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ParkingPermitTemporaryRelocationRequestData() {
      
        acceptationReglementInterieur = Boolean.valueOf(false);
      
        heureEnd = org.libredemat.business.request.permit.HeuresType.K18;
      
        heureStart = org.libredemat.business.request.permit.HeuresType.K9;
      
        isCompany = Boolean.valueOf(true);
      
    }

    @Override
    public ParkingPermitTemporaryRelocationRequestData clone() {
        ParkingPermitTemporaryRelocationRequestData result = new ParkingPermitTemporaryRelocationRequestData();
        
          
            
        result.setAcceptationReglementInterieur(acceptationReglementInterieur);
      
          
        
          
            
        result.setApeCode(apeCode);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> desiredServiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setDesiredService(desiredServiceList);
      
          
        
          
            
        result.setFurnitureLifting(furnitureLifting);
      
          
        
          
            
        if (heureEnd != null)
            result.setHeureEnd(heureEnd);
        else
            result.setHeureEnd(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
          
        
          
            
        if (heureStart != null)
            result.setHeureStart(heureStart);
        else
            result.setHeureStart(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
          
        
          
            
        result.setImmatriculation(immatriculation);
      
          
        
          
            
        result.setIsCompany(isCompany);
      
          
        
          
            
        result.setLongeur(longeur);
      
          
        
          
            
        result.setObservations(observations);
      
          
        
          
            
        result.setObservationsReglement(observationsReglement);
      
          
        
          
            
        result.setOther(other);
      
          
        
          
            
        if (payment != null)
            result.setPayment(payment.clone());
      
          
        
          
            
        result.setPeriodeEnd(periodeEnd);
      
          
        
          
            
        result.setPeriodeStart(periodeStart);
      
          
        
          
            
        result.setRequesterAddress(requesterAddress);
      
          
        
          
            
        result.setSiretNumber(siretNumber);
      
          
        
          
            
        result.setVehicleType(vehicleType);
      
          
        
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
        
        
        profiles = {"reglements"},
        message = "acceptationReglementInterieur"
      )
    
      @AssertTrue(
        
        
        profiles = {"reglements"},
        message = "acceptationReglementInterieur"
      )
    
    private Boolean acceptationReglementInterieur;

    public void setAcceptationReglementInterieur(final Boolean acceptationReglementInterieur) {
        this.acceptationReglementInterieur = acceptationReglementInterieur;
    }

 
    @Column(name="acceptation_reglement_interieur"  )
      
    public Boolean getAcceptationReglementInterieur() {
        return this.acceptationReglementInterieur;
    }
  
    
      @MaxLength(
        
          value = 5,
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "apeCode"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "apeCode"
      )
    
      @MatchPattern(
        
          pattern = "^[0-9]{4}[a-zA-Z]{1}$",
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "apeCode"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "apeCode"
      )
    
    private String apeCode;

    public void setApeCode(final String apeCode) {
        this.apeCode = apeCode;
    }

 
    @Column(name="ape_code" , length=5 )
      
    public String getApeCode() {
        return this.apeCode;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"relocation"},
        message = "desiredService"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"relocation"},
        message = "desiredService"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> desiredService;

    public void setDesiredService(final List<org.libredemat.business.request.LocalReferentialData> desiredService) {
        this.desiredService = desiredService;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="parking_permit_temporary_relocation_request_desired_service",
            joinColumns=
                @JoinColumn(name="parking_permit_temporary_relocation_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="desired_service_id"))
    @OrderColumn(name="desired_service_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getDesiredService() {
        return this.desiredService;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "furnitureLifting"
      )
    
    private Boolean furnitureLifting;

    public void setFurnitureLifting(final Boolean furnitureLifting) {
        this.furnitureLifting = furnitureLifting;
    }

 
    @Column(name="furniture_lifting"  )
      
    public Boolean getFurnitureLifting() {
        return this.furnitureLifting;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "heureEnd"
      )
    
    private org.libredemat.business.request.permit.HeuresType heureEnd;

    public void setHeureEnd(final org.libredemat.business.request.permit.HeuresType heureEnd) {
        this.heureEnd = heureEnd;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="heure_end"  )
      
    public org.libredemat.business.request.permit.HeuresType getHeureEnd() {
        return this.heureEnd;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "heureStart"
      )
    
    private org.libredemat.business.request.permit.HeuresType heureStart;

    public void setHeureStart(final org.libredemat.business.request.permit.HeuresType heureStart) {
        this.heureStart = heureStart;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="heure_start"  )
      
    public org.libredemat.business.request.permit.HeuresType getHeureStart() {
        return this.heureStart;
    }
  
    
      @MaxLength(
        
          value = 255,
        
        
        profiles = {"relocation"},
        message = "immatriculation"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
        profiles = {"relocation"},
        message = "immatriculation"
      )
    
    private String immatriculation;

    public void setImmatriculation(final String immatriculation) {
        this.immatriculation = immatriculation;
    }

 
    @Column(name="immatriculation" , length=255 )
      
    public String getImmatriculation() {
        return this.immatriculation;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "isCompany"
      )
    
    private Boolean isCompany;

    public void setIsCompany(final Boolean isCompany) {
        this.isCompany = isCompany;
    }

 
    @Column(name="is_company"  )
      
    public Boolean getIsCompany() {
        return this.isCompany;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "longeur"
      )
    
    private java.math.BigInteger longeur;

    public void setLongeur(final java.math.BigInteger longeur) {
        this.longeur = longeur;
    }

 
    @Column(name="longeur" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getLongeur() {
        return this.longeur;
    }
  
    
    private String observations;

    public void setObservations(final String observations) {
        this.observations = observations;
    }

 
    @Column(name="observations"  )
      
    public String getObservations() {
        return this.observations;
    }
  
    
    private String observationsReglement;

    public void setObservationsReglement(final String observationsReglement) {
        this.observationsReglement = observationsReglement;
    }

 
    @Column(name="observations_reglement"  )
      
    public String getObservationsReglement() {
        return this.observationsReglement;
    }
  
    
    private String other;

    public void setOther(final String other) {
        this.other = other;
    }

 
    @Column(name="other"  )
      
    public String getOther() {
        return this.other;
    }
  
    
      @AssertValid(
        
        
        profiles = {"paiement"},
        message = "payment"
      )
    
    private org.libredemat.business.payment.Payment payment;

    public void setPayment(final org.libredemat.business.payment.Payment payment) {
        this.payment = payment;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="payment_id")
      
    public org.libredemat.business.payment.Payment getPayment() {
        return this.payment;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "periodeEnd"
      )
    
    private java.util.Date periodeEnd;

    public void setPeriodeEnd(final java.util.Date periodeEnd) {
        this.periodeEnd = periodeEnd;
    }

 
    @Column(name="periode_end"  )
      
    public java.util.Date getPeriodeEnd() {
        return this.periodeEnd;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "periodeStart"
      )
    
    private java.util.Date periodeStart;

    public void setPeriodeStart(final java.util.Date periodeStart) {
        this.periodeStart = periodeStart;
    }

 
    @Column(name="periode_start"  )
      
    public java.util.Date getPeriodeStart() {
        return this.periodeStart;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "requesterAddress"
      )
    
      @NotBlank(
        
        
        profiles = {"relocation"},
        message = "requesterAddress"
      )
    
    private String requesterAddress;

    public void setRequesterAddress(final String requesterAddress) {
        this.requesterAddress = requesterAddress;
    }

 
    @Column(name="requester_address"  )
      
    public String getRequesterAddress() {
        return this.requesterAddress;
    }
  
    
      @MaxLength(
        
          value = 14,
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "siretNumber"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "siretNumber"
      )
    
      @MatchPattern(
        
          pattern = "^[0-9]{14}$",
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "siretNumber"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"relocation"},
        message = "siretNumber"
      )
    
    private String siretNumber;

    public void setSiretNumber(final String siretNumber) {
        this.siretNumber = siretNumber;
    }

 
    @Column(name="siret_number" , length=14 )
      
    public String getSiretNumber() {
        return this.siretNumber;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "vehicleType"
      )
    
      @NotBlank(
        
        
        profiles = {"relocation"},
        message = "vehicleType"
      )
    
    private String vehicleType;

    public void setVehicleType(final String vehicleType) {
        this.vehicleType = vehicleType;
    }

 
    @Column(name="vehicle_type"  )
      
    public String getVehicleType() {
        return this.vehicleType;
    }
  
}
