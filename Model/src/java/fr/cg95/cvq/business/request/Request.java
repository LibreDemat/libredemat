package fr.cg95.cvq.business.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.xmlbeans.XmlObject;

import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.Child;
import fr.cg95.cvq.business.users.HomeFolder;
import fr.cg95.cvq.business.users.Individual;


/**
 * Represent a request issued by an e-citizen.
 *
 * @hibernate.class
 *  table="request"
 *  lazy="false"
 *
 * @author bor@zenexity.fr
 */
public abstract class Request implements Serializable {

    private static final long serialVersionUID = 1L;
	
    public static final String SEARCH_BY_REQUEST_ID = "id";
    public static final String SEARCH_BY_HOME_FOLDER_ID = "homeFolderId";
    public static final String SEARCH_BY_REQUESTER_LASTNAME = "requesterLastName";
    public static final String SEARCH_BY_REQUESTER_FIRSTNAME = "requesterFirstName";
    public static final String SEARCH_BY_CATEGORY_NAME = "categoryName";
    public static final String SEARCH_BY_STATE = "state";
    public static final String SEARCH_BY_CREATION_DATE = "creationDate";
    public static final String SEARCH_BY_REQUEST_TYPE_LABEL = "requestTypeLabel";
    public static final String SEARCH_BY_LAST_INTERVENING_AGENT_ID = "lastInterveningAgentId";
    public static final String SEARCH_BY_LAST_MODIFICATION_DATE = "lastModificationDate";
    public static final String SEARCH_BY_QUALITY_TYPE = "qualityType";
	
	public static final String SEARCH_BY_RESULTING_STATE = "resultingState";
	public static final String SEARCH_BY_MODIFICATION_DATE = "modificationDate";

	public static final String QUALITY_TYPE_OK = "qualityTypeOk";
	public static final String QUALITY_TYPE_ORANGE = "qualityTypeOrange";
	public static final String QUALITY_TYPE_RED = "qualityTypeRed";
    
	/** identifier field */
    private Long id;
    private HomeFolder homeFolder;
    private Date creationDate;
    private Date lastModificationDate;
    private Date validationDate;
    private Long lastInterveningAgentId;
    private RequestState state;
    private DataState dataState;
    private MeansOfContact meansOfContact;
    /** the request's step, instruction or delivery, set by the model */
    private RequestStep step;
    private Adult requester;
    private Object subject;
    private RequestType requestType;
    /** for request types that have seasons, keep the season uuid */
    private String seasonUuid;
    /** QoS level 1 : instruction delay is expiring soon. */
    private Boolean orangeAlert;
    /** QoS level 2 : instruction delay has expired. */
    private Boolean redAlert;
    
    private Set documents;
    private Set actions;
    private Set notes;

    public Request() {
    }

    public abstract String modelToXmlString() ;

    public abstract XmlObject modelToXml() ;

    public abstract fr.cg95.cvq.xml.common.RequestType modelToXmlRequest();
    
    public void fillCommonXmlInfo(fr.cg95.cvq.xml.common.RequestType requestType) {
        Calendar calendar = Calendar.getInstance();

        if (this.id != null)
            requestType.setId(this.id.longValue());
        // creation date can't be null, this is certified by the model :-)
        // hmm ... it can in fact be null when we generate test empty PDFs ...
        if (this.creationDate != null) {
            calendar.setTime(this.creationDate);
            requestType.setCreationDate(calendar);
        }
        if (this.lastModificationDate != null) {
            calendar.setTime(this.lastModificationDate);
            requestType.setLastModificationDate(calendar);
        }
        if (this.validationDate != null) {
            calendar.setTime(this.validationDate);
            requestType.setValidationDate(calendar);
        }
        if (this.lastInterveningAgentId != null)
            requestType.setLastInterveningAgentId(this.lastInterveningAgentId.longValue());

        if (this.state != null)
            requestType.setState(fr.cg95.cvq.xml.common.RequestStateType.Enum.forString(this.state.toString()));
        if (this.dataState != null)
            requestType.setDataState(fr.cg95.cvq.xml.common.DataStateType.Enum.forString(this.dataState.toString()));
        
        if (meansOfContact != null)
            requestType.setMeansOfContact(MeansOfContact.modelToXml(meansOfContact));
        
        if (this.step != null)
            requestType.setStep(fr.cg95.cvq.xml.common.RequestType.Step.Enum.forString(this.step.toString()));
        if (requester != null) {
            requestType.setRequester(Adult.modelToXml(requester));
        }
        if (homeFolder != null) {
            requestType.setHomeFolder(homeFolder.modelToXml());
        }
        if (subject != null) {
            if (subject instanceof Adult) {
                requestType.addNewSubject().setAdult(Adult.modelToXml((Adult) subject));
            } else if (subject instanceof Child) {
                requestType.addNewSubject().setChild(Child.modelToXml((Child) subject));
            } else if (subject instanceof Individual) {
                requestType.addNewSubject().setIndividual(Individual.modelToXml((Individual) subject));
            }
        }
    }

    public void fillCommonModelInfo(Request request,
            fr.cg95.cvq.xml.common.RequestType requestType) {
        
        Calendar calendar = Calendar.getInstance();

        request.setId(new Long(requestType.getId()));

        // creation date can be null, this is certified by the model :-)
        calendar = requestType.getCreationDate();
        if (calendar != null)
            request.setCreationDate(calendar.getTime());
        calendar = requestType.getLastModificationDate();
        if (calendar != null)
            request.setLastModificationDate(calendar.getTime());
        calendar = requestType.getValidationDate();
        if (calendar != null)
            request.setValidationDate(calendar.getTime());
        request.setLastInterveningAgentId(new Long(requestType.getLastInterveningAgentId()));
        if (requestType.getState() != null)
            request.setState(RequestState.forString(requestType.getState().toString()));
        
        if (requestType.getMeansOfContact() != null)
            request.setMeansOfContact(MeansOfContact.xmlToModel(requestType.getMeansOfContact()));
        
        if (requestType.getStep() != null)
            request.setStep(RequestStep.forString(requestType.getStep().toString()));
        if (requestType.getRequester() != null)
            request.setRequester(Adult.xmlToModel(requestType.getRequester()));
        if (requestType.getHomeFolder() != null)
            request.setHomeFolder(HomeFolder.xmlToModel(requestType.getHomeFolder()));
        if (requestType.getSubject() != null) {
            if (requestType.getSubject().isSetIndividual()) {
                request.setSubject(Individual.xmlToModel(requestType.getSubject().getIndividual()));
            } else if (requestType.getSubject().isSetAdult()) {
                request.setSubject(Adult.xmlToModel(requestType.getSubject().getAdult()));
            } else if (requestType.getSubject().isSetChild()) {
                request.setSubject(Child.xmlToModel(requestType.getSubject().getChild()));
            }
        }
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
     * @hibernate.many-to-one
     *  class="fr.cg95.cvq.business.users.HomeFolder"
     *  column="home_folder_id"
     */
    public HomeFolder getHomeFolder() {
        return this.homeFolder;
    }

    public void setHomeFolder(HomeFolder homeFolder) {
        this.homeFolder = homeFolder;
    }

    /**
     * @hibernate.property
     *  column="creation_date"
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @hibernate.property
     *  column="last_modification_date"
     */
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**
     * @hibernate.property
     *  column="last_intervening_agent_id"
     */
    public Long getLastInterveningAgentId() {
        return this.lastInterveningAgentId;
    }

    public void setLastInterveningAgentId(Long lastInterveningAgentId) {
        this.lastInterveningAgentId = lastInterveningAgentId;
    }

    /**
     * @hibernate.property
     *  column="state"
     *  not-null="true"
     *  length="16"
     */
    public RequestState getState() {
        return this.state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    /**
     * @hibernate.property
     *  column="data_state"
     *  not-null="true"
     *  length="16"
     */
    public DataState getDataState() {
        return this.dataState;
    }

    public void setDataState(DataState dataState) {
        this.dataState = dataState;
    }

    /** 
     * @hibernate.many-to-one
     *  class="fr.cg95.cvq.business.request.MeansOfContact"
     *  column="means_of_contact_id"
     */
    public MeansOfContact getMeansOfContact() {
        return this.meansOfContact;
    }

    public void setMeansOfContact(MeansOfContact meansOfContact) {
        this.meansOfContact = meansOfContact;
    }

    /**
     * @hibernate.property
     *  column="request_step"
     *  length="16"
     */
    public RequestStep getStep() {
        return this.step;
    }

    public void setStep(RequestStep step) {
        this.step = step;
    }

    /**
     * @hibernate.many-to-one
     *  class="fr.cg95.cvq.business.users.Adult"
     *  column="requester_id"
     */
    public Adult getRequester() {
        return this.requester;
    }

    public void setRequester(Adult requester) {
        this.requester = requester;
    }

    /**
     * @hibernate.many-to-one
     *  class="fr.cg95.cvq.business.request.RequestType"
     *  column="request_type_id"
     */
    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * @hibernate.property
     *  column="season_uuid"
     */
    public String getSeasonUuid() {
        return seasonUuid;
    }

    public void setSeasonUuid(String seasonUuid) {
        this.seasonUuid = seasonUuid;
    }

    /**
     * @hibernate.set
     *  lazy="true"
     *  table="request_document_map"
     * @hibernate.key
     *  column="request_id"
     * @hibernate.many-to-many
     *  class="fr.cg95.cvq.business.document.Document"
     *  column="document_id"
     */
    public Set getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set documents) {
        this.documents = documents;
    }

    /**
     * @hibernate.set
     *  inverse="true"
     *  lazy="true"
     *  cascade="all-delete-orphan"
     *  order-by="id asc"
     * @hibernate.key
     *  column="request_id"
     * @hibernate.one-to-many
     *  class="fr.cg95.cvq.business.request.RequestAction"
     */
    public Set getActions() {
        return this.actions;
    }

    public void setActions(Set actions) {
        this.actions = actions;
    }

    /**
     * @hibernate.set
     *  inverse="true"
     *  lazy="true"
     *  cascade="delete"
     *  order-by="id asc"
     * @hibernate.key
     *  column="request_id"
     * @hibernate.one-to-many
     *  class="fr.cg95.cvq.business.request.RequestNote"
     */
    public Set getNotes() {
        return this.notes;
    }

    public void setNotes(Set notes) {
        this.notes = notes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    /**
     * @hibernate.property
     *  column="orange_alert"
     */
	public Boolean getOrangeAlert() {
		return orangeAlert;
	}

	public void setOrangeAlert(Boolean orangeAlert) {
		this.orangeAlert = orangeAlert;
	}

    /**
     * @hibernate.property
     *  column="red_alert"
     */
	public Boolean getRedAlert() {
		return redAlert;
	}

	public void setRedAlert(Boolean redAlert) {
		this.redAlert = redAlert;
	}

    /**
     * @hibernate.property
     *  column="validation_date"
     */
    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    /**
     * @hibernate.any
     *  id-type="long"
     *  lazy="true"
     * @hibernate.any-column
     *  name="subject_table_name"
     * @hibernate.any-column
     *  name="subject_id"
     *  
     */
    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }
}
