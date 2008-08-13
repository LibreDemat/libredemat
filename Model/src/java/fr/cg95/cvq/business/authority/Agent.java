package fr.cg95.cvq.business.authority;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @hibernate.class
 *  table="agent"
 *  lazy="false"
 *
 * @author bor@zenexity.fr
 */
public class Agent implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Long id;

    private String login;
    private String lastName;
    private String firstName;
    private Boolean active;

    private Set categoriesRoles;
    private Set sitesRoles;

    /** full constructor */
    public Agent(String login, String lastName, String firstName, 
            Set categoriesRoles, Set sitesRoles) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.categoriesRoles = categoriesRoles;
        this.sitesRoles = sitesRoles;
    }

    /** default constructor */
    public Agent() {
    }

    /**
     * @hibernate.id
     *  generator-class="sequence"
     *  column="id"
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @hibernate.property
     *  column="login"
     */
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @hibernate.property
     *  column="last_name"
     */
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @hibernate.property
     *  column="first_name"
     */
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @hibernate.set
     *  lazy="true"
     *  table="agent_category_roles"
     * @hibernate.key
     *  column="agent_id"
     * @hibernate.composite-element
     *  class="fr.cg95.cvq.business.authority.CategoryRoles"
     */
    public Set getCategoriesRoles() {
        return this.categoriesRoles;
    }

    public void setCategoriesRoles(Set categoriesRoles) {
        this.categoriesRoles = categoriesRoles;
    }

    /**
     * @hibernate.set
     *  lazy="true"
     *  table="agent_site_roles"
     * @hibernate.key
     *  column="agent_id"
     * @hibernate.composite-element
     *  class="fr.cg95.cvq.business.authority.SiteRoles"
     */
    public Set getSitesRoles() {
        return this.sitesRoles;
    }

    public void setSitesRoles(Set sitesRoles) {
        this.sitesRoles = sitesRoles;
    }

    /**
     * @hibernate.property
     *  column="active"
     */
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }
}
