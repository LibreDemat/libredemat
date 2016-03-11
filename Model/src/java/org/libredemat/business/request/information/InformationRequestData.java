

package org.libredemat.business.request.information;

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
@Table(name="information_request")
public class InformationRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public InformationRequestData() {
      
    }

    @Override
    public InformationRequestData clone() {
        InformationRequestData result = new InformationRequestData();
        
          
            
        result.setMessage(message);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> motiveList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setMotive(motiveList);
      
          
        
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

  
    
      @MaxLength(
        
          value = 255,
        
        
        profiles = {"reason"},
        message = "message"
      )
    
      @NotNull(
        
        
        profiles = {"reason"},
        message = "message"
      )
    
      @MatchPattern(
        
          pattern = "^.{0,255}$",
        
        
        profiles = {"reason"},
        message = "message"
      )
    
      @NotBlank(
        
        
        profiles = {"reason"},
        message = "message"
      )
    
    private String message;

    public void setMessage(final String message) {
        this.message = message;
    }

 
    @Column(name="message" , length=255 )
      
    public String getMessage() {
        return this.message;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"reason"},
        message = "motive"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"reason"},
        message = "motive"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> motive;

    public void setMotive(final List<org.libredemat.business.request.LocalReferentialData> motive) {
        this.motive = motive;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="information_request_motive",
            joinColumns=
                @JoinColumn(name="information_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="motive_id"))
    @OrderColumn(name="motive_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getMotive() {
        return this.motive;
    }
  
}
