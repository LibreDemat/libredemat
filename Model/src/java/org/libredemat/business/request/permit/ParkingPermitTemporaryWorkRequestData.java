

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
@Table(name="parking_permit_temporary_work_request")
public class ParkingPermitTemporaryWorkRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ParkingPermitTemporaryWorkRequestData() {
      
        acceptationReglementInterieur = Boolean.valueOf(false);
      
        desiredService = org.libredemat.business.request.permit.DesiredServiceType.PARKING_PERMIT_FOR_WORK;
      
        isCompany = Boolean.valueOf(true);
      
        scaffolding = Boolean.valueOf(true);
      
        vehicleParkingOrFloorOccupation = Boolean.valueOf(true);
      
        workOnBuilding = Boolean.valueOf(true);
      
    }

    @Override
    public ParkingPermitTemporaryWorkRequestData clone() {
        ParkingPermitTemporaryWorkRequestData result = new ParkingPermitTemporaryWorkRequestData();
        
          
            
        result.setAcceptationReglementInterieur(acceptationReglementInterieur);
      
          
        
          
            
        result.setApeCode(apeCode);
      
          
        
          
            
        result.setConstructLicenseNumber(constructLicenseNumber);
      
          
        
          
            
        if (desiredService != null)
            result.setDesiredService(desiredService);
        else
            result.setDesiredService(org.libredemat.business.request.permit.DesiredServiceType.getDefaultDesiredServiceType());
      
          
        
          
            
        result.setIsCompany(isCompany);
      
          
        
          
            
        result.setObservations(observations);
      
          
        
          
            
        result.setObservationsReglement(observationsReglement);
      
          
        
          
            
        result.setOccupation(occupation);
      
          
        
          
            
        result.setOccupationEndDate(occupationEndDate);
      
          
        
          
            
        result.setOccupationStartDate(occupationStartDate);
      
          
        
          
            
        if (payment != null)
            result.setPayment(payment.clone());
      
          
        
          
            
        result.setPaymentIndicativeAmount(paymentIndicativeAmount);
      
          
        
          
            
        result.setReferenceNumber(referenceNumber);
      
          
        
          
            
        result.setScaffolding(scaffolding);
      
          
        
          
            
        result.setScaffoldingEndDate(scaffoldingEndDate);
      
          
        
          
            
        result.setScaffoldingLength(scaffoldingLength);
      
          
        
          
            
        result.setScaffoldingStartDate(scaffoldingStartDate);
      
          
        
          
            
        result.setSiretNumber(siretNumber);
      
          
        
          
            
        result.setSiteAddress(siteAddress);
      
          
        
          
            
        result.setUsedVehicles(usedVehicles);
      
          
        
          
            
        result.setVehicleParkingOrFloorOccupation(vehicleParkingOrFloorOccupation);
      
          
        
          
            
        result.setWorkNature(workNature);
      
          
        
          
            
        result.setWorkOnBuilding(workOnBuilding);
      
          
        
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
        
        profiles = {"work"},
        message = "apeCode"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "apeCode"
      )
    
      @MatchPattern(
        
          pattern = "^[0-9]{4}[a-zA-Z]{1}$",
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "apeCode"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
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
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['workOnBuilding'].test(_this.workOnBuilding.toString());" +
                    
                  
              
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "constructLicenseNumber"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['workOnBuilding'].test(_this.workOnBuilding.toString());" +
                    
                  
              
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "constructLicenseNumber"
      )
    
    private String constructLicenseNumber;

    public void setConstructLicenseNumber(final String constructLicenseNumber) {
        this.constructLicenseNumber = constructLicenseNumber;
    }

 
    @Column(name="construct_license_number"  )
      
    public String getConstructLicenseNumber() {
        return this.constructLicenseNumber;
    }
  
    
      @NotNull(
        
        
        profiles = {"work"},
        message = "desiredService"
      )
    
    private org.libredemat.business.request.permit.DesiredServiceType desiredService;

    public void setDesiredService(final org.libredemat.business.request.permit.DesiredServiceType desiredService) {
        this.desiredService = desiredService;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="desired_service"  )
      
    public org.libredemat.business.request.permit.DesiredServiceType getDesiredService() {
        return this.desiredService;
    }
  
    
      @NotNull(
        
        
        profiles = {"work"},
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
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['vehicleParkingOrFloorOccupation'].test(_this.vehicleParkingOrFloorOccupation.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "occupation"
      )
    
    private Double occupation;

    public void setOccupation(final Double occupation) {
        this.occupation = occupation;
    }

 
    @Column(name="occupation"  )
      
    public Double getOccupation() {
        return this.occupation;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['vehicleParkingOrFloorOccupation'].test(_this.vehicleParkingOrFloorOccupation.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "occupationEndDate"
      )
    
    private java.util.Date occupationEndDate;

    public void setOccupationEndDate(final java.util.Date occupationEndDate) {
        this.occupationEndDate = occupationEndDate;
    }

 
    @Column(name="occupation_end_date"  )
      
    public java.util.Date getOccupationEndDate() {
        return this.occupationEndDate;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['vehicleParkingOrFloorOccupation'].test(_this.vehicleParkingOrFloorOccupation.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "occupationStartDate"
      )
    
    private java.util.Date occupationStartDate;

    public void setOccupationStartDate(final java.util.Date occupationStartDate) {
        this.occupationStartDate = occupationStartDate;
    }

 
    @Column(name="occupation_start_date"  )
      
    public java.util.Date getOccupationStartDate() {
        return this.occupationStartDate;
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
  
    
    private String paymentIndicativeAmount;

    public void setPaymentIndicativeAmount(final String paymentIndicativeAmount) {
        this.paymentIndicativeAmount = paymentIndicativeAmount;
    }

 
    @Column(name="payment_indicative_amount"  )
      
    public String getPaymentIndicativeAmount() {
        return this.paymentIndicativeAmount;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= !_this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "referenceNumber"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= !_this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "referenceNumber"
      )
    
    private String referenceNumber;

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

 
    @Column(name="reference_number"  )
      
    public String getReferenceNumber() {
        return this.referenceNumber;
    }
  
    
      @NotNull(
        
        
        profiles = {"work"},
        message = "scaffolding"
      )
    
    private Boolean scaffolding;

    public void setScaffolding(final Boolean scaffolding) {
        this.scaffolding = scaffolding;
    }

 
    @Column(name="scaffolding"  )
      
    public Boolean getScaffolding() {
        return this.scaffolding;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['scaffolding'].test(_this.scaffolding.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "scaffoldingEndDate"
      )
    
    private java.util.Date scaffoldingEndDate;

    public void setScaffoldingEndDate(final java.util.Date scaffoldingEndDate) {
        this.scaffoldingEndDate = scaffoldingEndDate;
    }

 
    @Column(name="scaffolding_end_date"  )
      
    public java.util.Date getScaffoldingEndDate() {
        return this.scaffoldingEndDate;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['scaffolding'].test(_this.scaffolding.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "scaffoldingLength"
      )
    
    private java.math.BigInteger scaffoldingLength;

    public void setScaffoldingLength(final java.math.BigInteger scaffoldingLength) {
        this.scaffoldingLength = scaffoldingLength;
    }

 
    @Column(name="scaffolding_length" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getScaffoldingLength() {
        return this.scaffoldingLength;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['scaffolding'].test(_this.scaffolding.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "scaffoldingStartDate"
      )
    
    private java.util.Date scaffoldingStartDate;

    public void setScaffoldingStartDate(final java.util.Date scaffoldingStartDate) {
        this.scaffoldingStartDate = scaffoldingStartDate;
    }

 
    @Column(name="scaffolding_start_date"  )
      
    public java.util.Date getScaffoldingStartDate() {
        return this.scaffoldingStartDate;
    }
  
    
      @MaxLength(
        
          value = 14,
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "siretNumber"
      )
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "siretNumber"
      )
    
      @MatchPattern(
        
          pattern = "^[0-9]{14}$",
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "siretNumber"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
                "active &= _this.conditions['isCompany'].test(_this.isCompany.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
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
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "siteAddress"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "siteAddress"
      )
    
    private String siteAddress;

    public void setSiteAddress(final String siteAddress) {
        this.siteAddress = siteAddress;
    }

 
    @Column(name="site_address"  )
      
    public String getSiteAddress() {
        return this.siteAddress;
    }
  
    
    private String usedVehicles;

    public void setUsedVehicles(final String usedVehicles) {
        this.usedVehicles = usedVehicles;
    }

 
    @Column(name="used_vehicles"  )
      
    public String getUsedVehicles() {
        return this.usedVehicles;
    }
  
    
      @NotNull(
        
        
        profiles = {"work"},
        message = "vehicleParkingOrFloorOccupation"
      )
    
    private Boolean vehicleParkingOrFloorOccupation;

    public void setVehicleParkingOrFloorOccupation(final Boolean vehicleParkingOrFloorOccupation) {
        this.vehicleParkingOrFloorOccupation = vehicleParkingOrFloorOccupation;
    }

 
    @Column(name="vehicle_parking_or_floor_occupation"  )
      
    public Boolean getVehicleParkingOrFloorOccupation() {
        return this.vehicleParkingOrFloorOccupation;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "workNature"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "workNature"
      )
    
    private String workNature;

    public void setWorkNature(final String workNature) {
        this.workNature = workNature;
    }

 
    @Column(name="work_nature"  )
      
    public String getWorkNature() {
        return this.workNature;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
            
                "active &= _this.conditions['desiredService'].test(_this.desiredService.toString());" +
                    
                  
              
            
            "return active",
        
        profiles = {"work"},
        message = "workOnBuilding"
      )
    
    private Boolean workOnBuilding;

    public void setWorkOnBuilding(final Boolean workOnBuilding) {
        this.workOnBuilding = workOnBuilding;
    }

 
    @Column(name="work_on_building"  )
      
    public Boolean getWorkOnBuilding() {
        return this.workOnBuilding;
    }
  
}
