

package org.libredemat.business.request.parking;

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
@Table(name="park_card_request")
public class ParkCardRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ParkCardRequestData() {
      
    }

    @Override
    public ParkCardRequestData clone() {
        ParkCardRequestData result = new ParkCardRequestData();
        
          
            
        result.setCardNumberLimit(cardNumberLimit);
      
          
        
          
            
        result.setCardOnePrice(cardOnePrice);
      
          
        
          
            
        result.setCardThreePrice(cardThreePrice);
      
          
        
          
            
        result.setCardTwoPrice(cardTwoPrice);
      
          
        
          
            
        result.setInformationCardLimitRest(informationCardLimitRest);
      
          
        
          
            
        List<org.libredemat.business.request.parking.ParkImmatriculation> parkImatriculationList = new ArrayList<org.libredemat.business.request.parking.ParkImmatriculation>();
        result.setParkImatriculation(parkImatriculationList);
      
          
        
          
            
        result.setParkResident(parkResident);
      
          
        
          
            
        result.setPaymentReference(paymentReference);
      
          
        
          
            
        result.setPaymentTotal(paymentTotal);
      
          
        
          
            
        if (subjectAddress != null)
            result.setSubjectAddress(subjectAddress.clone());
      
          
        
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
        
        
        profiles = {"car"},
        message = "cardNumberLimit"
      )
    
    private java.math.BigInteger cardNumberLimit;

    public void setCardNumberLimit(final java.math.BigInteger cardNumberLimit) {
        this.cardNumberLimit = cardNumberLimit;
    }

 
    @Column(name="card_number_limit" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getCardNumberLimit() {
        return this.cardNumberLimit;
    }
  
    
      @NotNull(
        
        
        profiles = {"car"},
        message = "cardOnePrice"
      )
    
    private java.math.BigDecimal cardOnePrice;

    public void setCardOnePrice(final java.math.BigDecimal cardOnePrice) {
        this.cardOnePrice = cardOnePrice;
    }

 
    @Column(name="card_one_price"  )
      
    public java.math.BigDecimal getCardOnePrice() {
        return this.cardOnePrice;
    }
  
    
      @NotNull(
        
        
        profiles = {"car"},
        message = "cardThreePrice"
      )
    
    private java.math.BigDecimal cardThreePrice;

    public void setCardThreePrice(final java.math.BigDecimal cardThreePrice) {
        this.cardThreePrice = cardThreePrice;
    }

 
    @Column(name="card_three_price"  )
      
    public java.math.BigDecimal getCardThreePrice() {
        return this.cardThreePrice;
    }
  
    
      @NotNull(
        
        
        profiles = {"car"},
        message = "cardTwoPrice"
      )
    
    private java.math.BigDecimal cardTwoPrice;

    public void setCardTwoPrice(final java.math.BigDecimal cardTwoPrice) {
        this.cardTwoPrice = cardTwoPrice;
    }

 
    @Column(name="card_two_price"  )
      
    public java.math.BigDecimal getCardTwoPrice() {
        return this.cardTwoPrice;
    }
  
    
    private String informationCardLimitRest;

    public void setInformationCardLimitRest(final String informationCardLimitRest) {
        this.informationCardLimitRest = informationCardLimitRest;
    }

 
    @Column(name="information_card_limit_rest"  )
      
    public String getInformationCardLimitRest() {
        return this.informationCardLimitRest;
    }
  
    
      @MaxSize(
        
          value = 10,
        
        
        profiles = {"car"},
        message = "parkImatriculation"
      )
    
      @AssertValid(
        
        
        profiles = {"car"},
        message = "parkImatriculation"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"car"},
        message = "parkImatriculation"
      )
    
    private List<org.libredemat.business.request.parking.ParkImmatriculation> parkImatriculation;

    public void setParkImatriculation(final List<org.libredemat.business.request.parking.ParkImmatriculation> parkImatriculation) {
        this.parkImatriculation = parkImatriculation;
    }

 
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="park_imatriculation_index")
    @JoinColumn(name="park_card_request_id")
      
    public List<org.libredemat.business.request.parking.ParkImmatriculation> getParkImatriculation() {
        return this.parkImatriculation;
    }
  
    
    private String parkResident;

    public void setParkResident(final String parkResident) {
        this.parkResident = parkResident;
    }

 
    @Column(name="park_resident"  )
      
    public String getParkResident() {
        return this.parkResident;
    }
  
    
    private String paymentReference;

    public void setPaymentReference(final String paymentReference) {
        this.paymentReference = paymentReference;
    }

 
    @Column(name="payment_reference"  )
      
    public String getPaymentReference() {
        return this.paymentReference;
    }
  
    
    private String paymentTotal;

    public void setPaymentTotal(final String paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

 
    @Column(name="payment_total"  )
      
    public String getPaymentTotal() {
        return this.paymentTotal;
    }
  
    
      @NotNull(
        
        
        profiles = {"subject"},
        message = "subjectAddress"
      )
    
      @AssertValid(
        
        
        profiles = {"subject"},
        message = "subjectAddress"
      )
    
    private org.libredemat.business.users.Address subjectAddress;

    public void setSubjectAddress(final org.libredemat.business.users.Address subjectAddress) {
        this.subjectAddress = subjectAddress;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="subject_address_id")
      
    public org.libredemat.business.users.Address getSubjectAddress() {
        return this.subjectAddress;
    }
  
}
