

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
      
    }

    @Override
    public ParkingPermitTemporaryRelocationRequestData clone() {
        ParkingPermitTemporaryRelocationRequestData result = new ParkingPermitTemporaryRelocationRequestData();
        
          
            
        result.setAcceptationReglementInterieur(acceptationReglementInterieur);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> equipmentUsedList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setEquipmentUsed(equipmentUsedList);
      
          
        
          
            
        if (heureEnd != null)
            result.setHeureEnd(heureEnd);
        else
            result.setHeureEnd(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
          
        
          
            
        if (heureStart != null)
            result.setHeureStart(heureStart);
        else
            result.setHeureStart(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
          
        
          
            
        result.setImmatriculation(immatriculation);
      
          
        
          
            
        result.setLargeur(largeur);
      
          
        
          
            
        result.setLongeur(longeur);
      
          
        
          
            
        result.setMarque(marque);
      
          
        
          
            
        if (payment != null)
            result.setPayment(payment.clone());
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> performChoiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setPerformChoice(performChoiceList);
      
          
        
          
            
        result.setPeriodeEnd(periodeEnd);
      
          
        
          
            
        result.setPeriodeStart(periodeStart);
      
          
        
          
            
        if (requesterAddress != null)
            result.setRequesterAddress(requesterAddress.clone());
      
          
        
          
            
        result.setTonnage(tonnage);
      
          
        
          
            
        result.setVolume(volume);
      
          
        
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

  
    
      @AssertTrue(
        
        
        profiles = {"reglements"},
        message = "acceptationReglementInterieur"
      )
    
      @NotNull(
        
        
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
  
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"relocation"},
        message = "equipmentUsed"
      )
    
      @LocalReferential(
        
        
        profiles = {"relocation"},
        message = "equipmentUsed"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> equipmentUsed;

    public void setEquipmentUsed(final List<org.libredemat.business.request.LocalReferentialData> equipmentUsed) {
        this.equipmentUsed = equipmentUsed;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="parking_permit_temporary_relocation_request_equipment_used",
            joinColumns=
                @JoinColumn(name="parking_permit_temporary_relocation_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="equipment_used_id"))
    @OrderColumn(name="equipment_used_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getEquipmentUsed() {
        return this.equipmentUsed;
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
  
    
      @NotBlank(
        
        
        profiles = {"relocation"},
        message = "immatriculation"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
        profiles = {"relocation"},
        message = "immatriculation"
      )
    
      @MaxLength(
        
          value = 255,
        
        
        profiles = {"relocation"},
        message = "immatriculation"
      )
    
      @NotNull(
        
        
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
        message = "largeur"
      )
    
    private java.math.BigInteger largeur;

    public void setLargeur(final java.math.BigInteger largeur) {
        this.largeur = largeur;
    }

 
    @Column(name="largeur" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getLargeur() {
        return this.largeur;
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
  
    
      @NotBlank(
        
        
        profiles = {"relocation"},
        message = "marque"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
        profiles = {"relocation"},
        message = "marque"
      )
    
      @MaxLength(
        
          value = 255,
        
        
        profiles = {"relocation"},
        message = "marque"
      )
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "marque"
      )
    
    private String marque;

    public void setMarque(final String marque) {
        this.marque = marque;
    }

 
    @Column(name="marque" , length=255 )
      
    public String getMarque() {
        return this.marque;
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
  
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"relocation"},
        message = "performChoice"
      )
    
      @LocalReferential(
        
        
        profiles = {"relocation"},
        message = "performChoice"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> performChoice;

    public void setPerformChoice(final List<org.libredemat.business.request.LocalReferentialData> performChoice) {
        this.performChoice = performChoice;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="parking_permit_temporary_relocation_request_perform_choice",
            joinColumns=
                @JoinColumn(name="parking_permit_temporary_relocation_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="perform_choice_id"))
    @OrderColumn(name="perform_choice_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getPerformChoice() {
        return this.performChoice;
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
  
    
      @AssertValid(
        
        
        profiles = {"relocation"},
        message = "requesterAddress"
      )
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "requesterAddress"
      )
    
    private org.libredemat.business.users.Address requesterAddress;

    public void setRequesterAddress(final org.libredemat.business.users.Address requesterAddress) {
        this.requesterAddress = requesterAddress;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="requester_address_id")
      
    public org.libredemat.business.users.Address getRequesterAddress() {
        return this.requesterAddress;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "tonnage"
      )
    
    private java.math.BigInteger tonnage;

    public void setTonnage(final java.math.BigInteger tonnage) {
        this.tonnage = tonnage;
    }

 
    @Column(name="tonnage" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getTonnage() {
        return this.tonnage;
    }
  
    
      @NotNull(
        
        
        profiles = {"relocation"},
        message = "volume"
      )
    
    private java.math.BigInteger volume;

    public void setVolume(final java.math.BigInteger volume) {
        this.volume = volume;
    }

 
    @Column(name="volume" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getVolume() {
        return this.volume;
    }
  
}
