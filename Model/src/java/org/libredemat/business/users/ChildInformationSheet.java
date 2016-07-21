/**
 * 
 */
package org.libredemat.business.users;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Past;

import org.libredemat.security.SecurityContext;
import org.libredemat.xml.common.ChildInformationSheetType;
import org.libredemat.xml.common.DietEnumType;
import org.libredemat.xml.common.DietType;

/**
 * Fiche de renseignement et de sécurité enfant
 * 
 * Inexine Hack
 * 
 * @author Inexine : Frederic Fabre
 *
 */
@Entity
@Table(name="child_information_sheet")
public class ChildInformationSheet implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    /**  Date de validation de la fiche de renseignement */
    // Cette date permet de savoir quel a famille a renseigné la fiche correctement.
    // On pourra effacer prédioquement cette date pour tous les comptes pour focer les familles à revalider leur fiche de renseignement
    @Column(name="date_validation")
    private Date validationDate; 
    
    /* Téléphone portable de l'enfant */
    @MatchPattern(pattern = "^0[67][0-9]{8}$", message = "telephonePortable", profiles = {"pattern"})
    @Column(name="telephone_portable",length=32)
    private String telephonePortable;
    
    /** Email de l'enfant */
    @Email(message = "emailEnfant", profiles = {"pattern"})
    @Column(name="email_enfant",length=50)
    private String emailEnfant;
    
    /** Personnes autorisées */
    @NotNull(message = "personneAutoriseNom1", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="personne_autorise_nom1")
    private String personneAutoriseNom1;
    
    @Column(name="personne_autorise_nom2")
    private String personneAutoriseNom2;
    
    @Column(name="personne_autorise_nom3")
    private String personneAutoriseNom3;

    @NotNull(message = "personneAutorisePrenom1", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="personne_autorise_prenom1")
    private String personneAutorisePrenom1;
    
    @Column(name="personne_autorise_prenom2")
    private String personneAutorisePrenom2;
    
    @Column(name="personne_autorise_prenom3")
    private String personneAutorisePrenom3;

    @NotNull(message = "personneAutoriseTelephone1", profiles = {"informationSheetRequiredFieldsActived"})
    @MatchPattern(pattern = "^0[1-5679][0-9]{8}$", message = "personneAutoriseTelephone1", profiles = {"pattern"})
    @Column(name="personne_autorise_telephone1", length=32)
    private String personneAutoriseTelephone1;
    
    @MatchPattern(pattern = "^0[1-5679][0-9]{8}$", message = "personneAutoriseTelephone2", profiles = {"pattern"})
    @Column(name="personne_autorise_telephone2", length=32)
    private String personneAutoriseTelephone2;
    
    @MatchPattern(pattern = "^0[1-5679][0-9]{8}$", message = "personneAutoriseTelephone3", profiles = {"pattern"})
    @Column(name="personne_autorise_telephone3", length=32)
    private String personneAutoriseTelephone3;    
    
    /**  Autorisation de rentrer seul */
    @Column(name="autorisation_rentrer_seul")
    private Boolean autorisationRentrerSeul;
    
    /** Nom de l'organisme d'assurance de la fiche de renseignement et de sécurité enfant */
    @NotNull(message = "nomOrganismeAssurance", profiles = {"informationSheetRequired"})
    @Column(name="nom_organisme_assurance")
    private String nomOrganismeAssurance;
    
    /** Numéro de l'organisme d'assurance de la fiche de renseignement et de sécurité enfant */
    @NotNull(message = "numeroOrganismeAssurance", profiles = {"informationSheetRequired"})
    @Column(name="numero_organisme_assurance")
    private String numeroOrganismeAssurance;
    
    /** Nom du médecin traitant de la fiche de renseignement et de sécurité enfant */
    @Column(name="nom_medecin_traitant")
    private String nomMedecinTraitant;
    
    /** Numéro de téléphone du médecin traitant de la fiche de renseignement et de sécurité enfant */
    @MatchPattern(pattern = "^0[1-5679][0-9]{8}$", message = "telephoneMedecinTraitant", profiles = {"pattern"})
    @Column(name="telephone_medecin_traitant", length=32)
    private String telephoneMedecinTraitant;
    
    /** Allergies de la fiche de renseignement et de sécurité enfant */
    @Column(name="allergie")
    private String allergie;
    
    /** Régimes alimentaires */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="child_information_sheet_id")
    private Set<Diet> diets;
    
    /** Vaccin BCG de la fiche de renseignement et de sécurité enfant  */
    @Past(message = "vaccinBcg", profiles = {"pattern"})
    @Column(name="vaccin_bcg")
    private Date vaccinBcg;
    
    /** Vaccin DT polio de la fiche de renseignement et de sécurité enfant  */
    @NotNull(message = "vaccinDtPolio", profiles = {"informationSheetRequired"})
    @Past(message = "vaccinDtPolio", profiles = {"pattern"})
    @Column(name="vaccin_dt_polio")
    private Date vaccinDtPolio;
    
    /** Vaccin injection de sérum de la fiche de renseignement et de sécurité enfant  */
    @Column(name="vaccin_injection_serum")
    @Past(message = "vaccinInjectionSerum", profiles = {"pattern"})
    private Date vaccinInjectionSerum;
    
    /** Vaccin ROR de la fiche de renseignement et de sécurité enfant  */
    @Column(name="vaccin_ror")
    @Past(message = "vaccinRor", profiles = {"pattern"})
    private Date vaccinRor;
    
    /** Vaccin tetracoq pentacoq de la fiche de renseignement et de sécurité enfant  */
    @Column(name="vaccin_tetracoq_pentacoq")
    @Past(message = "vaccinTetracoqPentacoq", profiles = {"pattern"})
    private Date vaccinTetracoqPentacoq;
    
    /** Autre vaccin de la fiche de renseignement et de sécurité enfant  */
    @Column(name="vaccin_autre")
    private String vaccinAutre;
    
    /** Recommandations de parents de la fiche de renseignement et de sécurité enfant  */
    @Column(name="recommandation_parent")
    private String recommandationParent;
    
    /** Difficultés de santé de la fiche de renseignement et de sécurité enfant  */
    @Column(name="difficulte_sante")
    private String difficulteSante;
    
    /** pathologie de l'enfant de la fiche de renseignement et de sécurité enfant  */
    @Column(name="pathologie_enfant")
    private boolean projetAccueilIndividualise;

    /** Autorisation pour le droit à l'image de la fiche de renseignement et de sécurité enfant  */
    @NotNull(message = "autorisationDroitImage", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="autorisation_droit_image")
    private Boolean autorisationDroitImage;

    /** Autorisation de maquillage de la fiche de renseignement et de sécurité enfant  */
    @NotNull(message = "autorisationMaquillage", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="autorisation_maquillage")
    private Boolean autorisationMaquillage;

    @NotNull(message = "autorisationTransporterVehiculeMunicipal", profiles = {"informationSheetRequiredFieldsActived"})
    /** Autorisation de transporter l'enfant en véhicule municipal de la fiche de renseignement et de sécurité enfant  */
    @Column(name="autorisation_transporter_vehicule_municipal")
    private Boolean autorisationTransporterVehiculeMunicipal;

    /** Autorisation de transporter l'enfant en transports en commun de la fiche de renseignement et de sécurité enfant  */
    @NotNull(message = "autorisationTransporterTransportCommun", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="autorisation_transporter_transport_commun")
    private Boolean autorisationTransporterTransportCommun;
    
    /** Autorisation d'hospitalisation de la fiche de renseignement et de sécurité enfant  */
    @NotNull(message = "autorisationTransporterTransportCommun", profiles = {"informationSheetRequiredFieldsActived"})
    @Column(name="autorisation_hospitalisation")
    private Boolean autorisationHospitalisation; 

    /**
     * Transforme du modele en XML
     *
     * @return childInformationSheetType
     *
     */
    public static ChildInformationSheetType modelToXml(ChildInformationSheet childInformationSheet) {
        ChildInformationSheetType childInformationSheetType = ChildInformationSheetType.Factory.newInstance();
        Calendar calendar = Calendar.getInstance();

        if (childInformationSheet.getId() != null) {
            childInformationSheet.setId(childInformationSheet.getId().longValue());
        }
        childInformationSheetType.setAllergie(childInformationSheet.getAllergie());

        // Régimes alimentaires
        if (childInformationSheet.getDiets() != null) {
            DietType diets = DietType.Factory.newInstance();
            for (Diet diet : childInformationSheet.getDiets()) {
        	diets.addNewType().set(Diet.modelToXml(diet));
            }
            childInformationSheetType.setDiets(diets);
        }
        
        childInformationSheetType.setAutorisationDroitImage(childInformationSheet.isAutorisationDroitImage() == null ? false : childInformationSheet.isAutorisationDroitImage());
        // Cette info n'est pour l'instant pas synchronisée
        childInformationSheetType.setAutorisationHospitalisation(childInformationSheet.isAutorisationHospitalisation() == null ? false : childInformationSheet.isAutorisationHospitalisation());
        childInformationSheetType.setAutorisationMaquillage(childInformationSheet.isAutorisationMaquillage() == null ? false : childInformationSheet.isAutorisationMaquillage());
        childInformationSheetType.setAutorisationRentrerSeul(childInformationSheet.isAutorisationRentrerSeul() == null ? false : childInformationSheet.isAutorisationRentrerSeul());
        childInformationSheetType.setAutorisationTransporterTransportCommun(childInformationSheet.isAutorisationTransporterTransportCommun() == null ? false : childInformationSheet.isAutorisationTransporterTransportCommun());
        childInformationSheetType.setAutorisationTransporterVehiculeMunicipal(childInformationSheet.isAutorisationTransporterVehiculeMunicipal() == null ? false : childInformationSheet.isAutorisationTransporterVehiculeMunicipal());
        childInformationSheetType.setDifficulteSante(childInformationSheet.getDifficulteSante());
        childInformationSheetType.setRecommandationParent(childInformationSheet.getRecommandationParent());
        childInformationSheetType.setEmailEnfant(childInformationSheet.getEmailEnfant());
        childInformationSheetType.setNomMedecinTraitant(childInformationSheet.getNomMedecinTraitant());
        childInformationSheetType.setNomOrganismeAssurance(childInformationSheet.getNomOrganismeAssurance());
        childInformationSheetType.setNumeroOrganismeAssurance(childInformationSheet.getNumeroOrganismeAssurance());
        childInformationSheetType.setProjetAccueilIndividualise(childInformationSheet.isProjetAccueilIndividualise());
        childInformationSheetType.setTelephoneMedecinTraitant(childInformationSheet.getTelephoneMedecinTraitant());
        childInformationSheetType.setTelephonePortable(childInformationSheet.getTelephonePortable());
        
        // Cette info n'est pour l'instant pas synchronisée 
        childInformationSheetType.setVaccinAutre(childInformationSheet.getVaccinAutre());
        
        if (childInformationSheet.getVaccinBcg() != null) {
            calendar.setTime(childInformationSheet.getVaccinBcg());
            childInformationSheetType.setVaccinBcg(calendar);            
        }
        if (childInformationSheet.getVaccinDtPolio() != null) {
            calendar.setTime(childInformationSheet.getVaccinDtPolio());
            childInformationSheetType.setVaccinDtPolio(calendar);    
        }
        if (childInformationSheet.getVaccinInjectionSerum() != null) {
            calendar.setTime(childInformationSheet.getVaccinInjectionSerum());
            childInformationSheetType.setVaccinInjectionSerum(calendar);    
        }
        if (childInformationSheet.getVaccinRor() != null) {
            calendar.setTime(childInformationSheet.getVaccinRor());
            childInformationSheetType.setVaccinRor(calendar);    
        }
        if (childInformationSheet.getVaccinTetracoqPentacoq() != null) {
            calendar.setTime(childInformationSheet.getVaccinTetracoqPentacoq());
            childInformationSheetType.setVaccinTetracoqPentacoq(calendar);    
        }
        
        return childInformationSheetType;
    }

    /**
     * Transforme de l'XML en modele 
     *
     * @param ChildInformationSheet
     * @return childInformationSheet
     *
     */
    public static ChildInformationSheet xmlToModel(ChildInformationSheetType childInformationSheetType) {
        if (childInformationSheetType == null) return null;
        ChildInformationSheet childInformationSheet = new ChildInformationSheet();

        childInformationSheet.setAllergie(childInformationSheetType.getAllergie());

        // Régimes alimentaires
        HashSet<Diet> dietSet = new HashSet<Diet>();
        if (childInformationSheetType.getDiets().sizeOfTypeArray() > 0) {
            for (int i = 0; i < childInformationSheetType.getDiets().getTypeArray().length; i++) {
        	DietEnumType.Enum dietEnumType = childInformationSheetType.getDiets().getTypeArray(i);
        	Diet dietTemp = new Diet(DietEnum.valueOf(dietEnumType.toString()));
        	dietSet.add(dietTemp);
            }
        }
        childInformationSheet.setDiets(dietSet);

        childInformationSheet.setAutorisationDroitImage(childInformationSheetType.getAutorisationDroitImage());
        // Cette info n'est pour l'instant pas synchronisée
        childInformationSheet.setAutorisationMaquillage(childInformationSheetType.getAutorisationMaquillage());
        childInformationSheet.setAutorisationHospitalisation(childInformationSheetType.getAutorisationHospitalisation());
        childInformationSheet.setAutorisationRentrerSeul(childInformationSheetType.getAutorisationRentrerSeul());
        childInformationSheet.setAutorisationTransporterTransportCommun(childInformationSheetType.getAutorisationTransporterTransportCommun());
        childInformationSheet.setAutorisationTransporterVehiculeMunicipal(childInformationSheetType.getAutorisationTransporterVehiculeMunicipal());
        childInformationSheet.setDifficulteSante(childInformationSheetType.getDifficulteSante());
        childInformationSheet.setRecommandationParent(childInformationSheetType.getRecommandationParent());
        childInformationSheet.setEmailEnfant(childInformationSheetType.getEmailEnfant());
        childInformationSheet.setNomMedecinTraitant(childInformationSheetType.getNomMedecinTraitant());
        childInformationSheet.setNomOrganismeAssurance(childInformationSheetType.getNomOrganismeAssurance());
        childInformationSheet.setNumeroOrganismeAssurance(childInformationSheetType.getNumeroOrganismeAssurance());
        childInformationSheet.setProjetAccueilIndividualise(childInformationSheetType.getProjetAccueilIndividualise());
        childInformationSheet.setRecommandationParent(childInformationSheetType.getRecommandationParent());
        childInformationSheet.setTelephoneMedecinTraitant(childInformationSheetType.getTelephoneMedecinTraitant());
        childInformationSheet.setTelephonePortable(childInformationSheetType.getTelephonePortable());
        
        // Cette info n'est pour l'instant pas synchronisée 
        childInformationSheet.setVaccinAutre(childInformationSheetType.getVaccinAutre());
        
        if (childInformationSheetType.getVaccinBcg() != null) {
            childInformationSheet.setVaccinBcg(childInformationSheetType.getVaccinBcg().getTime());
        }
        if (childInformationSheetType.getVaccinDtPolio() != null) {
            childInformationSheet.setVaccinDtPolio(childInformationSheetType.getVaccinDtPolio().getTime()); 
        }
        if (childInformationSheetType.getVaccinInjectionSerum() != null) {
            childInformationSheet.setVaccinInjectionSerum(childInformationSheetType.getVaccinInjectionSerum().getTime());
        }
        if (childInformationSheetType.getVaccinRor() != null) {
            childInformationSheet.setVaccinRor(childInformationSheetType.getVaccinRor().getTime()); 
        }
        if (childInformationSheetType.getVaccinTetracoqPentacoq() != null) {
            childInformationSheet.setVaccinTetracoqPentacoq(childInformationSheetType.getVaccinTetracoqPentacoq().getTime());
        }
        
        return childInformationSheet;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the validationDate
     */
    public Date getValidationDate() {
        return validationDate;
    }

    /**
     * @return the telephonePortable
     */
    public String getTelephonePortable() {
        return telephonePortable;
    }

    /**
     * @return the emailEnfant
     */
    public String getEmailEnfant() {
        return emailEnfant;
    }

    /**
     * @return the autorisationRentrerSeul
     */
    public Boolean isAutorisationRentrerSeul() {
        if(autorisationRentrerSeul ==null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationRentrerSeul;
        }
    }

    /**
     * @return the nomOrganismeAssurance
     */
    public String getNomOrganismeAssurance() {
        return nomOrganismeAssurance;
    }

    /**
     * @return the numeroOrganismeAssurance
     */
    public String getNumeroOrganismeAssurance() {
        return numeroOrganismeAssurance;
    }

    /**
     * @return the nomMedecinTraitant
     */
    public String getNomMedecinTraitant() {
        return nomMedecinTraitant;
    }

    /**
     * @return the telephoneMedecinTraitant
     */
    public String getTelephoneMedecinTraitant() {
        return telephoneMedecinTraitant;
    }

    /**
     * @return the allergie
     */
    public String getAllergie() {
        return allergie;
    }

    /**
     * @return the diets
     */
    public Set<Diet> getDiets() {
        return diets;
    }

    /**
     * @return the vaccinBcg
     */
    public Date getVaccinBcg() {
        return vaccinBcg;
    }

    /**
     * @return the vaccinDtPolio
     */
    public Date getVaccinDtPolio() {
        return vaccinDtPolio;
    }

    /**
     * @return the vaccinInjectionSerum
     */
    public Date getVaccinInjectionSerum() {
        return vaccinInjectionSerum;
    }

    /**
     * @return the vaccinRor
     */
    public Date getVaccinRor() {
        return vaccinRor;
    }

    /**
     * @return the vaccinTetracoqPentacoq
     */
    public Date getVaccinTetracoqPentacoq() {
        return vaccinTetracoqPentacoq;
    }

    /**
     * @return the vaccinAutre
     */
    public String getVaccinAutre() {
        return vaccinAutre;
    }

    /**
     * @return the recommandationParent
     */
    public String getRecommandationParent() {
        return recommandationParent;
    }

    /**
     * @return the difficulteSante
     */
    public String getDifficulteSante() {
        return difficulteSante;
    }

    /**
     * @return the projetAccueilIndividualise
     */
    public boolean isProjetAccueilIndividualise() {
        return projetAccueilIndividualise;
    }

    /**
     * @return the autorisationDroitImage
     */
    public Boolean isAutorisationDroitImage() {
        if(autorisationDroitImage ==null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationDroitImage;
        }
    }

    /**
     * @return the autorisationMaquillage
     */
    public Boolean isAutorisationMaquillage() {
        if(autorisationMaquillage == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationMaquillage;
        }
    }

    /**
     * @return the autorisationTransporterVehiculeMunicipal
     */
    public Boolean isAutorisationTransporterVehiculeMunicipal() {
        if(autorisationTransporterVehiculeMunicipal == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationTransporterVehiculeMunicipal;
        }
    }

    /**
     * @return the autorisationTransporterTransportCommun
     */
    public Boolean isAutorisationTransporterTransportCommun() {
        if(autorisationTransporterTransportCommun == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationTransporterTransportCommun;
        }
    }

    /**
     * @return the autorisationHospitalisation
     */
    public Boolean isAutorisationHospitalisation() {
        if(autorisationHospitalisation ==null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            return false;
        } else {
            return autorisationHospitalisation;
        }
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param validationDate the validationDate to set
     */
    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    /**
     * @param telephonePortable the telephonePortable to set
     */
    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    /**
     * @param emailEnfant the emailEnfant to set
     */
    public void setEmailEnfant(String emailEnfant) {
        this.emailEnfant = emailEnfant;
    }

    /**
     * @param autorisationRentrerSeul the autorisationRentrerSeul to set
     */
    public void setAutorisationRentrerSeul(Boolean autorisationRentrerSeul) {
        if(autorisationRentrerSeul ==null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationRentrerSeul = false;
        } else {
            this.autorisationRentrerSeul = autorisationRentrerSeul;
        }
        
    }

    /**
     * @param nomOrganismeAssurance the nomOrganismeAssurance to set
     */
    public void setNomOrganismeAssurance(String nomOrganismeAssurance) {
        this.nomOrganismeAssurance = nomOrganismeAssurance;
    }

    /**
     * @param numeroOrganismeAssurance the numeroOrganismeAssurance to set
     */
    public void setNumeroOrganismeAssurance(String numeroOrganismeAssurance) {
        this.numeroOrganismeAssurance = numeroOrganismeAssurance;
    }

    /**
     * @param nomMedecinTraitant the nomMedecinTraitant to set
     */
    public void setNomMedecinTraitant(String nomMedecinTraitant) {
        this.nomMedecinTraitant = nomMedecinTraitant;
    }

    /**
     * @param telephoneMedecinTraitant the telephoneMedecinTraitant to set
     */
    public void setTelephoneMedecinTraitant(String telephoneMedecinTraitant) {
        this.telephoneMedecinTraitant = telephoneMedecinTraitant;
    }

    /**
     * @param allergie the allergie to set
     */
    public void setAllergie(String allergie) {
        this.allergie = allergie;
    }

    /**
     * @param diets the diets to set
     */
    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
    }

    /**
     * @param vaccinBcg the vaccinBcg to set
     */
    public void setVaccinBcg(Date vaccinBcg) {
        this.vaccinBcg = vaccinBcg;
    }

    /**
     * @param vaccinDtPolio the vaccinDtPolio to set
     */
    public void setVaccinDtPolio(Date vaccinDtPolio) {
        this.vaccinDtPolio = vaccinDtPolio;
    }

    /**
     * @param vaccinInjectionSerum the vaccinInjectionSerum to set
     */
    public void setVaccinInjectionSerum(Date vaccinInjectionSerum) {
        this.vaccinInjectionSerum = vaccinInjectionSerum;
    }

    /**
     * @param vaccinRor the vaccinRor to set
     */
    public void setVaccinRor(Date vaccinRor) {
        this.vaccinRor = vaccinRor;
    }

    /**
     * @param vaccinTetracoqPentacoq the vaccinTetracoqPentacoq to set
     */
    public void setVaccinTetracoqPentacoq(Date vaccinTetracoqPentacoq) {
        this.vaccinTetracoqPentacoq = vaccinTetracoqPentacoq;
    }

    /**
     * @param vaccinAutre the vaccinAutre to set
     */
    public void setVaccinAutre(String vaccinAutre) {
        this.vaccinAutre = vaccinAutre;
    }

    /**
     * @param recommandationParent the recommandationParent to set
     */
    public void setRecommandationParent(String recommandationParent) {
        this.recommandationParent = recommandationParent;
    }

    /**
     * @param difficulteSante the difficulteSante to set
     */
    public void setDifficulteSante(String difficulteSante) {
        this.difficulteSante = difficulteSante;
    }

    /**
     * @param projetAccueilIndividualise the projetAccueilIndividualise to set
     */
    public void setProjetAccueilIndividualise(boolean projetAccueilIndividualise) {
        this.projetAccueilIndividualise = projetAccueilIndividualise;
    }

    /**
     * @param autorisationDroitImage the autorisationDroitImage to set
     */
    public void setAutorisationDroitImage(Boolean autorisationDroitImage) {
        if(autorisationDroitImage == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationDroitImage = false;
        } else {
            this.autorisationDroitImage = autorisationDroitImage;
        }
    }

    /**
     * @param autorisationMaquillage the autorisationMaquillage to set
     */
    public void setAutorisationMaquillage(Boolean autorisationMaquillage) {
        if(autorisationMaquillage == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationMaquillage = false;
        } else {
            this.autorisationMaquillage = autorisationMaquillage;
        }
        
    }

    /**
     * @param autorisationTransporterVehiculeMunicipal the autorisationTransporterVehiculeMunicipal to set
     */
    public void setAutorisationTransporterVehiculeMunicipal(Boolean autorisationTransporterVehiculeMunicipal) {
        if(autorisationTransporterVehiculeMunicipal == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationTransporterVehiculeMunicipal = false;
        } else {
            this.autorisationTransporterVehiculeMunicipal = autorisationTransporterVehiculeMunicipal;
        }
    }

    /**
     * @param autorisationTransporterTransportCommun the autorisationTransporterTransportCommun to set
     */
    public void setAutorisationTransporterTransportCommun(Boolean autorisationTransporterTransportCommun) {
        if(autorisationTransporterTransportCommun == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationTransporterTransportCommun = false;
        } else {
            this.autorisationTransporterTransportCommun = autorisationTransporterTransportCommun;
        }
    }

    /**
     * @param autorisationHospitalisation the autorisationHospitalisation to set
     */
    public void setAutorisationHospitalisation(Boolean autorisationHospitalisation) {
        if(autorisationHospitalisation == null && !SecurityContext.getCurrentConfigurationBean().isInformationSheetRequiredFieldsActived()) {
            this.autorisationHospitalisation = false;
        } else {
            this.autorisationHospitalisation = autorisationHospitalisation;
        }
        
    }

    /**
     * @return the personneAutoriseNom1
     */
    public String getPersonneAutoriseNom1() {
        return personneAutoriseNom1;
    }

    /**
     * @return the personneAutoriseNom2
     */
    public String getPersonneAutoriseNom2() {
        return personneAutoriseNom2;
    }

    /**
     * @return the personneAutoriseNom3
     */
    public String getPersonneAutoriseNom3() {
        return personneAutoriseNom3;
    }

    /**
     * @return the personneAutorisePrenom1
     */
    public String getPersonneAutorisePrenom1() {
        return personneAutorisePrenom1;
    }

    /**
     * @return the personneAutorisePrenom2
     */
    public String getPersonneAutorisePrenom2() {
        return personneAutorisePrenom2;
    }

    /**
     * @return the personneAutorisePrenom3
     */
    public String getPersonneAutorisePrenom3() {
        return personneAutorisePrenom3;
    }

    /**
     * @return the personneAutoriseTelephone1
     */
    public String getPersonneAutoriseTelephone1() {
        return personneAutoriseTelephone1;
    }

    /**
     * @return the personneAutoriseTelephone2
     */
    public String getPersonneAutoriseTelephone2() {
        return personneAutoriseTelephone2;
    }

    /**
     * @return the personneAutoriseTelephone3
     */
    public String getPersonneAutoriseTelephone3() {
        return personneAutoriseTelephone3;
    }

    /**
     * @param personneAutoriseNom1 the personneAutoriseNom1 to set
     */
    public void setPersonneAutoriseNom1(String personneAutoriseNom1) {
        this.personneAutoriseNom1 = personneAutoriseNom1;
    }

    /**
     * @param personneAutoriseNom2 the personneAutoriseNom2 to set
     */
    public void setPersonneAutoriseNom2(String personneAutoriseNom2) {
        this.personneAutoriseNom2 = personneAutoriseNom2;
    }

    /**
     * @param personneAutoriseNom3 the personneAutoriseNom3 to set
     */
    public void setPersonneAutoriseNom3(String personneAutoriseNom3) {
        this.personneAutoriseNom3 = personneAutoriseNom3;
    }

    /**
     * @param personneAutorisePrenom1 the personneAutorisePrenom1 to set
     */
    public void setPersonneAutorisePrenom1(String personneAutorisePrenom1) {
        this.personneAutorisePrenom1 = personneAutorisePrenom1;
    }

    /**
     * @param personneAutorisePrenom2 the personneAutorisePrenom2 to set
     */
    public void setPersonneAutorisePrenom2(String personneAutorisePrenom2) {
        this.personneAutorisePrenom2 = personneAutorisePrenom2;
    }

    /**
     * @param personneAutorisePrenom3 the personneAutorisePrenom3 to set
     */
    public void setPersonneAutorisePrenom3(String personneAutorisePrenom3) {
        this.personneAutorisePrenom3 = personneAutorisePrenom3;
    }

    /**
     * @param personneAutoriseTelephone1 the personneAutoriseTelephone1 to set
     */
    public void setPersonneAutoriseTelephone1(String personneAutoriseTelephone1) {
        this.personneAutoriseTelephone1 = personneAutoriseTelephone1;
    }

    /**
     * @param personneAutoriseTelephone2 the personneAutoriseTelephone2 to set
     */
    public void setPersonneAutoriseTelephone2(String personneAutoriseTelephone2) {
        this.personneAutoriseTelephone2 = personneAutoriseTelephone2;
    }

    /**
     * @param personneAutoriseTelephone3 the personneAutoriseTelephone3 to set
     */
    public void setPersonneAutoriseTelephone3(String personneAutoriseTelephone3) {
        this.personneAutoriseTelephone3 = personneAutoriseTelephone3;
    }

 

}
