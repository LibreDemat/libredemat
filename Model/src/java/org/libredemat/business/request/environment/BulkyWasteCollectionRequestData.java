

package org.libredemat.business.request.environment;

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
@Table(name="bulky_waste_collection_request")
public class BulkyWasteCollectionRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public BulkyWasteCollectionRequestData() {
      
    }

    @Override
    public BulkyWasteCollectionRequestData clone() {
        BulkyWasteCollectionRequestData result = new BulkyWasteCollectionRequestData();
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> bulkyWasteTypeList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setBulkyWasteType(bulkyWasteTypeList);
      
          
        
          
            
        if (collectionAddress != null)
            result.setCollectionAddress(collectionAddress.clone());
      
          
        
          
            
        result.setOtherWaste(otherWaste);
      
          
        
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

  
    
      @LocalReferential(
        
        
        profiles = {"waste"},
        message = "bulkyWasteType"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"waste"},
        message = "bulkyWasteType"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> bulkyWasteType;

    public void setBulkyWasteType(final List<org.libredemat.business.request.LocalReferentialData> bulkyWasteType) {
        this.bulkyWasteType = bulkyWasteType;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="bulky_waste_collection_request_bulky_waste_type",
            joinColumns=
                @JoinColumn(name="bulky_waste_collection_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="bulky_waste_type_id"))
    @OrderColumn(name="bulky_waste_type_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getBulkyWasteType() {
        return this.bulkyWasteType;
    }
  
    
      @AssertValid(
        
        
        profiles = {"waste"},
        message = "collectionAddress"
      )
    
    private org.libredemat.business.users.Address collectionAddress;

    public void setCollectionAddress(final org.libredemat.business.users.Address collectionAddress) {
        this.collectionAddress = collectionAddress;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="collection_address_id")
      
    public org.libredemat.business.users.Address getCollectionAddress() {
        return this.collectionAddress;
    }
  
    
    private String otherWaste;

    public void setOtherWaste(final String otherWaste) {
        this.otherWaste = otherWaste;
    }

 
    @Column(name="other_waste"  )
      
    public String getOtherWaste() {
        return this.otherWaste;
    }
  
}
