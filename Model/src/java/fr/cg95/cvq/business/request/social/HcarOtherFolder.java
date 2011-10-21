
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
@Table(name="hcar_other_folder")
public class HcarOtherFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        HandicapCompensationAdultRequest.conditions;

    public HcarOtherFolder() {
        super();
      
    }

    public final String modelToXmlString() {
        HcarOtherFolderType object = (HcarOtherFolderType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final HcarOtherFolderType modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        HcarOtherFolderType hcarOtherFolder = HcarOtherFolderType.Factory.newInstance();
        int i = 0;
    
        hcarOtherFolder.setOtherFolderDepartment(this.otherFolderDepartment);
      
        hcarOtherFolder.setOtherFolderName(this.otherFolderName);
      
        hcarOtherFolder.setOtherFolderNumber(this.otherFolderNumber);
      
        return hcarOtherFolder;
    }

    public static HcarOtherFolder xmlToModel(HcarOtherFolderType hcarOtherFolderDoc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        HcarOtherFolder hcarOtherFolder = new HcarOtherFolder();
    
        hcarOtherFolder.setOtherFolderDepartment(hcarOtherFolderDoc.getOtherFolderDepartment());
      
        hcarOtherFolder.setOtherFolderName(hcarOtherFolderDoc.getOtherFolderName());
      
        hcarOtherFolder.setOtherFolderNumber(hcarOtherFolderDoc.getOtherFolderNumber());
      
        return hcarOtherFolder;
    }

    @Override
    public HcarOtherFolder clone() {
        HcarOtherFolder result = new HcarOtherFolder();
        
          
            
        result.setOtherFolderDepartment(otherFolderDepartment);
      
          
        
          
            
        result.setOtherFolderName(otherFolderName);
      
          
        
          
            
        result.setOtherFolderNumber(otherFolderNumber);
      
          
        
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
        
          value = 2,
        
        
        profiles = {"folders"},
        message = "otherFolderDepartment"
      )
    
    private String otherFolderDepartment;

    public void setOtherFolderDepartment(final String otherFolderDepartment) {
        this.otherFolderDepartment = otherFolderDepartment;
    }


    @Column(name="other_folder_department" , length=2 )
      
    public String getOtherFolderDepartment() {
        return this.otherFolderDepartment;
    }
  
    
      @MaxLength(
        
          value = 60,
        
        
        profiles = {"folders"},
        message = "otherFolderName"
      )
    
      @NotNull(
        
        
        profiles = {"folders"},
        message = "otherFolderName"
      )
    
      @NotBlank(
        
        
        profiles = {"folders"},
        message = "otherFolderName"
      )
    
    private String otherFolderName;

    public void setOtherFolderName(final String otherFolderName) {
        this.otherFolderName = otherFolderName;
    }


    @Column(name="other_folder_name" , length=60 )
      
    public String getOtherFolderName() {
        return this.otherFolderName;
    }
  
    
      @MaxLength(
        
          value = 30,
        
        
        profiles = {"folders"},
        message = "otherFolderNumber"
      )
    
    private String otherFolderNumber;

    public void setOtherFolderNumber(final String otherFolderNumber) {
        this.otherFolderNumber = otherFolderNumber;
    }


    @Column(name="other_folder_number" , length=30 )
      
    public String getOtherFolderNumber() {
        return this.otherFolderNumber;
    }
  
}
