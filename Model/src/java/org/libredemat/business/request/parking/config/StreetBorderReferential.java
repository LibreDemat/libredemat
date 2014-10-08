package org.libredemat.business.request.parking.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Inexine : Frederic Fabre
 *
 */
@Entity
@Table(name="street_border_referential")
public class StreetBorderReferential implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public static final String SEARCh_BY_LABEL = "streetLabel";
    
    private Long id;
    private String streetLabel;
    private String city;
    private String postalCode;
    
    public StreetBorderReferential() {
        // TODO Auto-generated constructor stub
    }

    public StreetBorderReferential(String streetLabel, String city, String postalCode) {
        this.streetLabel = streetLabel;
        this.city = city;
        this.postalCode = postalCode;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="street_label")
    public String getStreetLabel() {
        return streetLabel;
    }

    public void setStreetLabel(String streetLabel) {
        this.streetLabel = streetLabel;
    }

    @Column(name="city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    
    

}
