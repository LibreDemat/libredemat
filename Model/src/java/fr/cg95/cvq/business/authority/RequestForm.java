package fr.cg95.cvq.business.authority;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @hibernate.class
 *  table="request_form"
 *  lazy="false"
 *
 * @author bor@zenexity.fr
 */
public class RequestForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Long id;

    private RequestFormType type;
    
    /**
     * user friendly name of the form inputed by an administrator
     */
    private String label;
    
    /**
     * Short name, used to display requestForm name in little space (like in a drop down list)
     */
    private String shortLabel;

    /**
     * the name of the XSL file used to generate certificates after some states transitions
     */
    private String xslFoFilename;

    /** the request types that use this form */
    private Set requestTypes;

    /** full constructor */
    public RequestForm(RequestFormType type, String xslFoFilename,
                       Set requestTypes) {
        this.type = type;
        this.xslFoFilename = xslFoFilename;
        this.requestTypes = requestTypes;
    }

    /** default constructor */
    public RequestForm() {
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
     *  column="type"
     *  not-null="true"
     */
    public RequestFormType getType() {
        return this.type;
    }

    public void setType(RequestFormType type) {
        this.type = type;
    }

    /**
     * @hibernate.property
     *  column="xsl_fo_filename"
     */
    public String getXslFoFilename() {
        return this.xslFoFilename;
    }

    public void setXslFoFilename(String xslFoFilename) {
        this.xslFoFilename = xslFoFilename;
    }

    /**
     * @hibernate.set
     *  lazy="true"
     *  table="forms"
     * @hibernate.key
     *  column="request_form_id"
     * @hibernate.many-to-many
     *  class="fr.cg95.cvq.business.authority.RequestType"
     *  column="request_type_id"
     */
    public Set getRequestTypes() {
        return this.requestTypes;
    }

    public void setRequestTypes(Set requestTypes) {
        this.requestTypes = requestTypes;
    }

    /**
     * @hibernate.property
     *  column="label"
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * @hibernate.property
     *  column="short_label"
     */
    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
