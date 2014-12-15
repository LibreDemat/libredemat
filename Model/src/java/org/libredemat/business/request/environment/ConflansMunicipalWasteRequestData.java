

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
@Table(name="conflans_municipal_waste_request")
public class ConflansMunicipalWasteRequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ConflansMunicipalWasteRequestData() {
      
        profilDemandeur = org.libredemat.business.request.environment.CmwrProfilDemandeurType.PARTICULIER;
      
        typeHabitation = org.libredemat.business.request.environment.CmwrTypeHabitationType.PAVILLON;
      
    }

    @Override
    public ConflansMunicipalWasteRequestData clone() {
        ConflansMunicipalWasteRequestData result = new ConflansMunicipalWasteRequestData();
        
          
            
        if (adresseOrganisation != null)
            result.setAdresseOrganisation(adresseOrganisation.clone());
      
          
        
          
            
        result.setComposteurGrand(composteurGrand);
      
          
        
          
            
        result.setComposteurPetit(composteurPetit);
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> conteneurList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setConteneur(conteneurList);
      
          
        
          
            
        result.setNomOrganisation(nomOrganisation);
      
          
        
          
            
        result.setNombreResidants(nombreResidants);
      
          
        
          
            
        result.setOmCentVingtLitres(omCentVingtLitres);
      
          
        
          
            
        result.setOmDeuxCentQuaranteLitres(omDeuxCentQuaranteLitres);
      
          
        
          
            
        result.setOmSixCentSoixanteLitres(omSixCentSoixanteLitres);
      
          
        
          
            
        result.setOmTroisCentQuaranteLitres(omTroisCentQuaranteLitres);
      
          
        
          
            
        result.setPrecisionsReparation(precisionsReparation);
      
          
        
          
            
        if (profilDemandeur != null)
            result.setProfilDemandeur(profilDemandeur);
        else
            result.setProfilDemandeur(org.libredemat.business.request.environment.CmwrProfilDemandeurType.getDefaultCmwrProfilDemandeurType());
      
          
        
          
            
        List<org.libredemat.business.request.LocalReferentialData> quartierList = new ArrayList<org.libredemat.business.request.LocalReferentialData>();
        result.setQuartier(quartierList);
      
          
        
          
            
        result.setTriCentVingtLitres(triCentVingtLitres);
      
          
        
          
            
        result.setTriDeuxCentQuaranteLitres(triDeuxCentQuaranteLitres);
      
          
        
          
            
        result.setTriSixCentSoixanteLitres(triSixCentSoixanteLitres);
      
          
        
          
            
        result.setTriTroisCentQuaranteLitres(triTroisCentQuaranteLitres);
      
          
        
          
            
        if (typeHabitation != null)
            result.setTypeHabitation(typeHabitation);
        else
            result.setTypeHabitation(org.libredemat.business.request.environment.CmwrTypeHabitationType.getDefaultCmwrTypeHabitationType());
      
          
        
          
            
        result.setVerreCentVingtLitres(verreCentVingtLitres);
      
          
        
          
            
        result.setVerreDeuxCentQuaranteLitres(verreDeuxCentQuaranteLitres);
      
          
        
          
            
        result.setVerreTrenteCinqLitres(verreTrenteCinqLitres);
      
          
        
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
        
        
          when = "groovy:def active = true;" +
          
                "active &= !_this.conditions['profilDemandeur'].test(_this.profilDemandeur.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"informations"},
        message = "adresseOrganisation"
      )
    
      @AssertValid(
        
        
          when = "groovy:def active = true;" +
          
                "active &= !_this.conditions['profilDemandeur'].test(_this.profilDemandeur.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"informations"},
        message = "adresseOrganisation"
      )
    
    private org.libredemat.business.users.Address adresseOrganisation;

    public void setAdresseOrganisation(final org.libredemat.business.users.Address adresseOrganisation) {
        this.adresseOrganisation = adresseOrganisation;
    }

 
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="adresse_organisation_id")
      
    public org.libredemat.business.users.Address getAdresseOrganisation() {
        return this.adresseOrganisation;
    }
  
    
    private java.math.BigInteger composteurGrand;

    public void setComposteurGrand(final java.math.BigInteger composteurGrand) {
        this.composteurGrand = composteurGrand;
    }

 
    @Column(name="composteur_grand" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getComposteurGrand() {
        return this.composteurGrand;
    }
  
    
    private java.math.BigInteger composteurPetit;

    public void setComposteurPetit(final java.math.BigInteger composteurPetit) {
        this.composteurPetit = composteurPetit;
    }

 
    @Column(name="composteur_petit" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getComposteurPetit() {
        return this.composteurPetit;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"informations"},
        message = "conteneur"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"informations"},
        message = "conteneur"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> conteneur;

    public void setConteneur(final List<org.libredemat.business.request.LocalReferentialData> conteneur) {
        this.conteneur = conteneur;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="conflans_municipal_waste_request_conteneur",
            joinColumns=
                @JoinColumn(name="conflans_municipal_waste_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="conteneur_id"))
    @OrderColumn(name="conteneur_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getConteneur() {
        return this.conteneur;
    }
  
    
      @NotNull(
        
        
          when = "groovy:def active = true;" +
          
                "active &= !_this.conditions['profilDemandeur'].test(_this.profilDemandeur.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"informations"},
        message = "nomOrganisation"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
          when = "groovy:def active = true;" +
          
                "active &= !_this.conditions['profilDemandeur'].test(_this.profilDemandeur.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"informations"},
        message = "nomOrganisation"
      )
    
      @NotBlank(
        
        
          when = "groovy:def active = true;" +
          
                "active &= !_this.conditions['profilDemandeur'].test(_this.profilDemandeur.toString());" +
                    
                  
              
            
            
            "return active",
        
        profiles = {"informations"},
        message = "nomOrganisation"
      )
    
    private String nomOrganisation;

    public void setNomOrganisation(final String nomOrganisation) {
        this.nomOrganisation = nomOrganisation;
    }

 
    @Column(name="nom_organisation" , length=255 )
      
    public String getNomOrganisation() {
        return this.nomOrganisation;
    }
  
    
      @NotNull(
        
        
        profiles = {"informations"},
        message = "nombreResidants"
      )
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,255}$",
        
        
        profiles = {"informations"},
        message = "nombreResidants"
      )
    
      @NotBlank(
        
        
        profiles = {"informations"},
        message = "nombreResidants"
      )
    
    private String nombreResidants;

    public void setNombreResidants(final String nombreResidants) {
        this.nombreResidants = nombreResidants;
    }

 
    @Column(name="nombre_residants" , length=255 )
      
    public String getNombreResidants() {
        return this.nombreResidants;
    }
  
    
    private java.math.BigInteger omCentVingtLitres;

    public void setOmCentVingtLitres(final java.math.BigInteger omCentVingtLitres) {
        this.omCentVingtLitres = omCentVingtLitres;
    }

 
    @Column(name="om_cent_vingt_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getOmCentVingtLitres() {
        return this.omCentVingtLitres;
    }
  
    
    private java.math.BigInteger omDeuxCentQuaranteLitres;

    public void setOmDeuxCentQuaranteLitres(final java.math.BigInteger omDeuxCentQuaranteLitres) {
        this.omDeuxCentQuaranteLitres = omDeuxCentQuaranteLitres;
    }

 
    @Column(name="om_deux_cent_quarante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getOmDeuxCentQuaranteLitres() {
        return this.omDeuxCentQuaranteLitres;
    }
  
    
    private java.math.BigInteger omSixCentSoixanteLitres;

    public void setOmSixCentSoixanteLitres(final java.math.BigInteger omSixCentSoixanteLitres) {
        this.omSixCentSoixanteLitres = omSixCentSoixanteLitres;
    }

 
    @Column(name="om_six_cent_soixante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getOmSixCentSoixanteLitres() {
        return this.omSixCentSoixanteLitres;
    }
  
    
    private java.math.BigInteger omTroisCentQuaranteLitres;

    public void setOmTroisCentQuaranteLitres(final java.math.BigInteger omTroisCentQuaranteLitres) {
        this.omTroisCentQuaranteLitres = omTroisCentQuaranteLitres;
    }

 
    @Column(name="om_trois_cent_quarante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getOmTroisCentQuaranteLitres() {
        return this.omTroisCentQuaranteLitres;
    }
  
    
      @MatchPattern(
        
          pattern = "^[\\w\\W]{0,1024}$",
        
        
        profiles = {"informations"},
        message = "precisionsReparation"
      )
    
    private String precisionsReparation;

    public void setPrecisionsReparation(final String precisionsReparation) {
        this.precisionsReparation = precisionsReparation;
    }

 
    @Column(name="precisions_reparation" , length=1024 )
      
    public String getPrecisionsReparation() {
        return this.precisionsReparation;
    }
  
    
      @NotNull(
        
        
        profiles = {"informations"},
        message = "profilDemandeur"
      )
    
    private org.libredemat.business.request.environment.CmwrProfilDemandeurType profilDemandeur;

    public void setProfilDemandeur(final org.libredemat.business.request.environment.CmwrProfilDemandeurType profilDemandeur) {
        this.profilDemandeur = profilDemandeur;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="profil_demandeur"  )
      
    public org.libredemat.business.request.environment.CmwrProfilDemandeurType getProfilDemandeur() {
        return this.profilDemandeur;
    }
  
    
      @LocalReferential(
        
        
        profiles = {"administration"},
        message = "quartier"
      )
    
      @MinSize(
        
          value = 1,
        
        
        profiles = {"administration"},
        message = "quartier"
      )
    
    private List<org.libredemat.business.request.LocalReferentialData> quartier;

    public void setQuartier(final List<org.libredemat.business.request.LocalReferentialData> quartier) {
        this.quartier = quartier;
    }

 
    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="conflans_municipal_waste_request_quartier",
            joinColumns=
                @JoinColumn(name="conflans_municipal_waste_request_id"),
            inverseJoinColumns=
                @JoinColumn(name="quartier_id"))
    @OrderColumn(name="quartier_index")
      
    public List<org.libredemat.business.request.LocalReferentialData> getQuartier() {
        return this.quartier;
    }
  
    
    private java.math.BigInteger triCentVingtLitres;

    public void setTriCentVingtLitres(final java.math.BigInteger triCentVingtLitres) {
        this.triCentVingtLitres = triCentVingtLitres;
    }

 
    @Column(name="tri_cent_vingt_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getTriCentVingtLitres() {
        return this.triCentVingtLitres;
    }
  
    
    private java.math.BigInteger triDeuxCentQuaranteLitres;

    public void setTriDeuxCentQuaranteLitres(final java.math.BigInteger triDeuxCentQuaranteLitres) {
        this.triDeuxCentQuaranteLitres = triDeuxCentQuaranteLitres;
    }

 
    @Column(name="tri_deux_cent_quarante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getTriDeuxCentQuaranteLitres() {
        return this.triDeuxCentQuaranteLitres;
    }
  
    
    private java.math.BigInteger triSixCentSoixanteLitres;

    public void setTriSixCentSoixanteLitres(final java.math.BigInteger triSixCentSoixanteLitres) {
        this.triSixCentSoixanteLitres = triSixCentSoixanteLitres;
    }

 
    @Column(name="tri_six_cent_soixante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getTriSixCentSoixanteLitres() {
        return this.triSixCentSoixanteLitres;
    }
  
    
    private java.math.BigInteger triTroisCentQuaranteLitres;

    public void setTriTroisCentQuaranteLitres(final java.math.BigInteger triTroisCentQuaranteLitres) {
        this.triTroisCentQuaranteLitres = triTroisCentQuaranteLitres;
    }

 
    @Column(name="tri_trois_cent_quarante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getTriTroisCentQuaranteLitres() {
        return this.triTroisCentQuaranteLitres;
    }
  
    
      @NotNull(
        
        
        profiles = {"informations"},
        message = "typeHabitation"
      )
    
    private org.libredemat.business.request.environment.CmwrTypeHabitationType typeHabitation;

    public void setTypeHabitation(final org.libredemat.business.request.environment.CmwrTypeHabitationType typeHabitation) {
        this.typeHabitation = typeHabitation;
    }

 
    @Enumerated(EnumType.STRING)
    @Column(name="type_habitation"  )
      
    public org.libredemat.business.request.environment.CmwrTypeHabitationType getTypeHabitation() {
        return this.typeHabitation;
    }
  
    
    private java.math.BigInteger verreCentVingtLitres;

    public void setVerreCentVingtLitres(final java.math.BigInteger verreCentVingtLitres) {
        this.verreCentVingtLitres = verreCentVingtLitres;
    }

 
    @Column(name="verre_cent_vingt_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getVerreCentVingtLitres() {
        return this.verreCentVingtLitres;
    }
  
    
    private java.math.BigInteger verreDeuxCentQuaranteLitres;

    public void setVerreDeuxCentQuaranteLitres(final java.math.BigInteger verreDeuxCentQuaranteLitres) {
        this.verreDeuxCentQuaranteLitres = verreDeuxCentQuaranteLitres;
    }

 
    @Column(name="verre_deux_cent_quarante_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getVerreDeuxCentQuaranteLitres() {
        return this.verreDeuxCentQuaranteLitres;
    }
  
    
    private java.math.BigInteger verreTrenteCinqLitres;

    public void setVerreTrenteCinqLitres(final java.math.BigInteger verreTrenteCinqLitres) {
        this.verreTrenteCinqLitres = verreTrenteCinqLitres;
    }

 
    @Column(name="verre_trente_cinq_litres" , columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://libredemat.capwebct.fr/ticket/338
      
    public java.math.BigInteger getVerreTrenteCinqLitres() {
        return this.verreTrenteCinqLitres;
    }
  
}
