package org.libredemat.business.authority;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.UUID;

/**
 * @author bor@zenexity.fr
 */
@Entity
@Table(name="agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="active")
    private Boolean active = true;

    @Column(name="email")
    private String email;

    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(
          name="agent_site_roles",
          joinColumns=@JoinColumn(name="agent_id")
    )
    private Set<SiteRoles> sitesRoles;

    @Column(name="preferences")
    private Hashtable<String, Hashtable<String, String>> preferences; 

    @Column(name="validation_code", length=100)
    private String validationCode;

    @Column(name="validation_code_expiration")
    private Date validationCodeExpiration;

    @Column(name="is_view_payment", nullable=false, columnDefinition = "bool default false")
    private boolean isViewPayment;

    @Column(name="is_sanitaire", nullable=false, columnDefinition = "bool default false")
    private boolean isSanitaire;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<SiteRoles> getSitesRoles() {
        return this.sitesRoles;
    }

    public void setSitesRoles(Set<SiteRoles> sitesRoles) {
        this.sitesRoles = sitesRoles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Hashtable<String, Hashtable<String, String>> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Hashtable<String, Hashtable<String, String>> preferences) {
        this.preferences = preferences;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public Date getValidationCodeExpiration() {
        return validationCodeExpiration;
    }

    public void setValidationCodeExpiration(Date validationCodeExpiration) {
        this.validationCodeExpiration = validationCodeExpiration;
    }

    public void assignRandomValidationCode() {
        setValidationCode(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("login", getLogin())
            .append("id", getId())
            .toString();
    }

    public boolean isViewPayment() {
        return isViewPayment;
    }

    public void setViewPayment(boolean isViewPayment) {
        this.isViewPayment = isViewPayment;
    }

    public boolean getIsSanitaire() {
        return isSanitaire;
    }

    public void setIsSanitaire(boolean isSanitaire) {
        this.isSanitaire = isSanitaire;
    }
}
