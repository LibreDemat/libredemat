
package org.libredemat.business.request.parking;

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
import org.libredemat.business.authority.*;
import org.libredemat.business.request.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.RequestType;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.parking.*;
import org.libredemat.service.request.LocalReferential;
import org.libredemat.service.request.condition.IConditionChecker;
import javax.persistence.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * Generated class file, do not edit !
 */
@Entity
@Table(name="park_immatriculation")
public class ParkImmatriculation implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        ParkCardRequest.conditions;

    public ParkImmatriculation() {
        super();
      
    }

    public final String modelToXmlString() {
        ParkImmatriculationType object = (ParkImmatriculationType) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final ParkImmatriculationType modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        ParkImmatriculationType parkImmatriculation = ParkImmatriculationType.Factory.newInstance();
        int i = 0;
    
        parkImmatriculation.setImmatriculation(this.immatriculation);
      
        parkImmatriculation.setTarif(this.tarif);
      
        return parkImmatriculation;
    }

    public static ParkImmatriculation xmlToModel(ParkImmatriculationType parkImmatriculationDoc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ParkImmatriculation parkImmatriculation = new ParkImmatriculation();
    
        parkImmatriculation.setImmatriculation(parkImmatriculationDoc.getImmatriculation());
      
        parkImmatriculation.setTarif(parkImmatriculationDoc.getTarif());
      
        return parkImmatriculation;
    }

    @Override
    public ParkImmatriculation clone() {
        ParkImmatriculation result = new ParkImmatriculation();
        
          
            
        result.setImmatriculation(immatriculation);
      
          
        
          
            
        result.setTarif(tarif);
      
          
        
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

  
    
      @NotNull(
        
        
        profiles = {"car"},
        message = "immatriculation"
      )
    
      @NotBlank(
        
        
        profiles = {"car"},
        message = "immatriculation"
      )
    
    private String immatriculation;

    public void setImmatriculation(final String immatriculation) {
        this.immatriculation = immatriculation;
    }


    @Column(name="immatriculation"  )
      
    public String getImmatriculation() {
        return this.immatriculation;
    }
  
    
    private String tarif;

    public void setTarif(final String tarif) {
        this.tarif = tarif;
    }


    @Column(name="tarif"  )
      
    public String getTarif() {
        return this.tarif;
    }
  
}
