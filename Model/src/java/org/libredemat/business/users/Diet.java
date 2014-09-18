/**
 * 
 */
package org.libredemat.business.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.libredemat.xml.common.DietEnumType;
import org.libredemat.xml.common.DietType;


/**
 * Régime alimentaire
 * 
 * @author Inexine : Frederic Fabre
 *
 */
@Entity
@Table(name="diet")
public class Diet implements Serializable, Comparable<Diet>  {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    /**  */
    @Enumerated(EnumType.STRING)
    // length=16
    @Column(name="diet_type")
    private DietEnum type;
    
    /** Constructor */
    public Diet() {}
    
    @Override
    public String toString()
    {
    	if (type == null) return "NC";
    	return DietEnum.toDietLibelle(type);
    }

    /**
     * Constructor
     * @param type type de régime 
     *
     */
    public Diet(DietEnum type) {
    	this.type = type;
    }
    
    public Diet(String type) {
        this.type = DietEnum.valueOf(type);
    }

    /**
     * xmlToModel
     *
     * @param dietType
     * @return 
     *
     */
    public static Diet xmlToModel(DietEnumType dietEnumType) {
        Diet diet = new Diet(DietEnum.valueOf(dietEnumType.getStringValue()));
        return diet;
    }

    /**
     * modelToXml
     *
     * @param diet
     * @return 
     *
     */
    public static DietEnumType modelToXml(Diet diet) {
	DietEnumType dietEnumType = DietEnumType.Factory.newInstance();
	
        if (diet.getType() != null) {
//            dietType.setType(DietEnumType.Enum.forString((diet.getType().toString())));
            dietEnumType.setStringValue(diet.getType().toString());
        }
        return dietEnumType;
    }
    
    /* (non-Javadoc)
     * @param o
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Diet o) {
        if (o == null || o.getType() == null) {
            return 1;
        }
        return this.type.toString().compareTo(o.getType().toString());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the type
     */
    public DietEnum getType() {
        return type;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param type the type to set
     */
    public void setType(DietEnum type) {
        this.type = type;
    }

}
