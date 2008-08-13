package fr.cg95.cvq.bo.social;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import fr.cg95.cvq.wizard.*;
import fr.cg95.cvq.bo.record.BaseRecord;
import fr.cg95.cvq.bo.record.RequestRecord;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.business.social.*;

public class HandicapAllowanceRequestRecord extends RequestRecord {

  	private String legalRepresentativeAddressAdditionalDeliveryInformation;
	private String legalRepresentativeAddressAdditionalGeographicalInformation;
	private String legalRepresentativeAddressStreetNumber;
	private String legalRepresentativeAddressStreetName;
	private String legalRepresentativeAddressPlaceNameOrService;
	private String legalRepresentativeAddressPostalCode;
	private String legalRepresentativeAddressCity;
	private String subjectIndividualLastName;
  	private String subjectIndividualAddressAdditionalDeliveryInformation;
	private String subjectIndividualAddressAdditionalGeographicalInformation;
	private String subjectIndividualAddressStreetNumber;
	private String subjectIndividualAddressStreetName;
	private String subjectIndividualAddressPlaceNameOrService;
	private String subjectIndividualAddressPostalCode;
	private String subjectIndividualAddressCity;
	private String needs;
	private boolean writingHelp;
	private boolean legalRepresentative;
	private boolean hopesAndNeeds;
	private String helperResponsability;
	private String legalRepresentativeFamilyRelationship;
	private String helperName;
	private String legalRepresentativeName;
	private String subjectIndividualFirstName;
	private String comments;
	private String legalRepresentativeFirstame;
	private String hopes;

	public HandicapAllowanceRequestRecord() {
		super();
	}
	
	protected Object clone() throws CloneNotSupportedException {
		HandicapAllowanceRequestRecord clonedRecord = (HandicapAllowanceRequestRecord)super.clone();
		return clonedRecord;
	}
	
	public BaseRecord getDataRecord(Long id) {

		if (id.equals(this.getId()))
			return this;
			
		return super.getDataRecord(id);
	}

    public void load(Object xmlRequest) {
        if ((xmlRequest != null) && (xmlRequest instanceof HandicapAllowanceRequest)) {
            HandicapAllowanceRequest request = (HandicapAllowanceRequest)xmlRequest; 

			if (request.getLegalRepresentativeAddress() != null) {
				this.legalRepresentativeAddressAdditionalDeliveryInformation = request.getLegalRepresentativeAddress().getAdditionalDeliveryInformation();
				this.legalRepresentativeAddressAdditionalGeographicalInformation = request.getLegalRepresentativeAddress().getAdditionalGeographicalInformation();
				this.legalRepresentativeAddressStreetNumber = request.getLegalRepresentativeAddress().getStreetNumber();
				this.legalRepresentativeAddressStreetName = request.getLegalRepresentativeAddress().getStreetName();
				this.legalRepresentativeAddressPlaceNameOrService = request.getLegalRepresentativeAddress().getPlaceNameOrService();
				this.legalRepresentativeAddressPostalCode = request.getLegalRepresentativeAddress().getPostalCode();
				this.legalRepresentativeAddressCity = request.getLegalRepresentativeAddress().getCity();
			}
			this.subjectIndividualLastName = ((Individual)request.getSubject()).getLastName();
			if (((Individual)request.getSubject()).getAdress() != null) {
				this.subjectIndividualAddressAdditionalDeliveryInformation = ((Individual)request.getSubject()).getAdress().getAdditionalDeliveryInformation();
				this.subjectIndividualAddressAdditionalGeographicalInformation = ((Individual)request.getSubject()).getAdress().getAdditionalGeographicalInformation();
				this.subjectIndividualAddressStreetNumber = ((Individual)request.getSubject()).getAdress().getStreetNumber();
				this.subjectIndividualAddressStreetName = ((Individual)request.getSubject()).getAdress().getStreetName();
				this.subjectIndividualAddressPlaceNameOrService = ((Individual)request.getSubject()).getAdress().getPlaceNameOrService();
				this.subjectIndividualAddressPostalCode = ((Individual)request.getSubject()).getAdress().getPostalCode();
				this.subjectIndividualAddressCity = ((Individual)request.getSubject()).getAdress().getCity();
			}
			this.needs = request.getNeeds();
            if ((request.getWritingHelp() != null))
			this.writingHelp = request.getWritingHelp();
            if ((request.getLegalRepresentative() != null))
			this.legalRepresentative = request.getLegalRepresentative();
            if ((request.getHopesAndNeeds() != null))
			this.hopesAndNeeds = request.getHopesAndNeeds();
			this.helperResponsability = request.getHelperResponsability();
			this.legalRepresentativeFamilyRelationship = request.getLegalRepresentativeFamilyRelationship();
			this.helperName = request.getHelperName();
			this.legalRepresentativeName = request.getLegalRepresentativeName();
			this.subjectIndividualFirstName = ((Individual)request.getSubject()).getFirstName();
			this.comments = request.getComments();
			this.legalRepresentativeFirstame = request.getLegalRepresentativeFirstame();
			this.hopes = request.getHopes();
        }
    }
    
    public void saveRequest(Object xmlRequest) {
        if ((xmlRequest != null) && (xmlRequest instanceof HandicapAllowanceRequest)) {
            HandicapAllowanceRequest request = (HandicapAllowanceRequest)xmlRequest; 
        }
    }
    
    public void saveData(Object xmlRequest) {
        if ((xmlRequest != null) && (xmlRequest instanceof HandicapAllowanceRequest)) {
            HandicapAllowanceRequest request = (HandicapAllowanceRequest)xmlRequest; 

  			if (request.getLegalRepresentativeAddress() != null) {
				request.getLegalRepresentativeAddress().setAdditionalDeliveryInformation(this.legalRepresentativeAddressAdditionalDeliveryInformation);
				request.getLegalRepresentativeAddress().setAdditionalGeographicalInformation(this.legalRepresentativeAddressAdditionalGeographicalInformation);
				request.getLegalRepresentativeAddress().setStreetNumber(this.legalRepresentativeAddressStreetNumber);
				request.getLegalRepresentativeAddress().setStreetName(this.legalRepresentativeAddressStreetName);
				request.getLegalRepresentativeAddress().setPlaceNameOrService(this.legalRepresentativeAddressPlaceNameOrService);
				request.getLegalRepresentativeAddress().setPostalCode(this.legalRepresentativeAddressPostalCode);
				request.getLegalRepresentativeAddress().setCity(this.legalRepresentativeAddressCity);
			}
			request.setNeeds(this.needs);
            if ((request.getWritingHelp() != null))
			request.setWritingHelp(this.writingHelp);
            if ((request.getLegalRepresentative() != null))
			request.setLegalRepresentative(this.legalRepresentative);
            if ((request.getHopesAndNeeds() != null))
			request.setHopesAndNeeds(this.hopesAndNeeds);
			request.setHelperResponsability(this.helperResponsability);
			request.setLegalRepresentativeFamilyRelationship(this.legalRepresentativeFamilyRelationship);
			request.setHelperName(this.helperName);
			request.setLegalRepresentativeName(this.legalRepresentativeName);
			request.setComments(this.comments);
			request.setLegalRepresentativeFirstame(this.legalRepresentativeFirstame);
			request.setHopes(this.hopes);
        }
    }
    
	public void setLegalRepresentativeAddressAdditionalDeliveryInformation(String legalRepresentativeAddressAdditionalDeliveryInformation) {
		this.legalRepresentativeAddressAdditionalDeliveryInformation = legalRepresentativeAddressAdditionalDeliveryInformation;
	}
	
	public String getLegalRepresentativeAddressAdditionalDeliveryInformation() {
		return this.legalRepresentativeAddressAdditionalDeliveryInformation;
	}

	public void setLegalRepresentativeAddressAdditionalGeographicalInformation(String legalRepresentativeAddressAdditionalGeographicalInformation) {
		this.legalRepresentativeAddressAdditionalGeographicalInformation = legalRepresentativeAddressAdditionalGeographicalInformation;
	}
	
	public String getLegalRepresentativeAddressAdditionalGeographicalInformation() {
		return this.legalRepresentativeAddressAdditionalGeographicalInformation;
	}

	public void setLegalRepresentativeAddressStreetNumber(String legalRepresentativeAddressStreetNumber) {
		this.legalRepresentativeAddressStreetNumber = legalRepresentativeAddressStreetNumber;
	}
	
	public String getLegalRepresentativeAddressStreetNumber() {
		return this.legalRepresentativeAddressStreetNumber;
	}

	public void setLegalRepresentativeAddressStreetName(String legalRepresentativeAddressStreetName) {
		this.legalRepresentativeAddressStreetName = legalRepresentativeAddressStreetName;
	}
	
	public String getLegalRepresentativeAddressStreetName() {
		return this.legalRepresentativeAddressStreetName;
	}

	public void setLegalRepresentativeAddressPlaceNameOrService(String legalRepresentativeAddressPlaceNameOrService) {
		this.legalRepresentativeAddressPlaceNameOrService = legalRepresentativeAddressPlaceNameOrService;
	}
	
	public String getLegalRepresentativeAddressPlaceNameOrService() {
		return this.legalRepresentativeAddressPlaceNameOrService;
	}

	public void setLegalRepresentativeAddressPostalCode(String legalRepresentativeAddressPostalCode) {
		this.legalRepresentativeAddressPostalCode = legalRepresentativeAddressPostalCode;
	}
	
	public String getLegalRepresentativeAddressPostalCode() {
		return this.legalRepresentativeAddressPostalCode;
	}

	public void setLegalRepresentativeAddressCity(String legalRepresentativeAddressCity) {
		this.legalRepresentativeAddressCity = legalRepresentativeAddressCity;
	}
	
	public String getLegalRepresentativeAddressCity() {
		return this.legalRepresentativeAddressCity;
	}

	public void setSubjectIndividualLastName(String subjectIndividualLastName) {
		this.subjectIndividualLastName = subjectIndividualLastName;
	}
	
	public String getSubjectIndividualLastName() {
		return this.subjectIndividualLastName;
	}

	public void setSubjectIndividualAddressAdditionalDeliveryInformation(String subjectIndividualAddressAdditionalDeliveryInformation) {
		this.subjectIndividualAddressAdditionalDeliveryInformation = subjectIndividualAddressAdditionalDeliveryInformation;
	}
	
	public String getSubjectIndividualAddressAdditionalDeliveryInformation() {
		return this.subjectIndividualAddressAdditionalDeliveryInformation;
	}

	public void setSubjectIndividualAddressAdditionalGeographicalInformation(String subjectIndividualAddressAdditionalGeographicalInformation) {
		this.subjectIndividualAddressAdditionalGeographicalInformation = subjectIndividualAddressAdditionalGeographicalInformation;
	}
	
	public String getSubjectIndividualAddressAdditionalGeographicalInformation() {
		return this.subjectIndividualAddressAdditionalGeographicalInformation;
	}

	public void setSubjectIndividualAddressStreetNumber(String subjectIndividualAddressStreetNumber) {
		this.subjectIndividualAddressStreetNumber = subjectIndividualAddressStreetNumber;
	}
	
	public String getSubjectIndividualAddressStreetNumber() {
		return this.subjectIndividualAddressStreetNumber;
	}

	public void setSubjectIndividualAddressStreetName(String subjectIndividualAddressStreetName) {
		this.subjectIndividualAddressStreetName = subjectIndividualAddressStreetName;
	}
	
	public String getSubjectIndividualAddressStreetName() {
		return this.subjectIndividualAddressStreetName;
	}

	public void setSubjectIndividualAddressPlaceNameOrService(String subjectIndividualAddressPlaceNameOrService) {
		this.subjectIndividualAddressPlaceNameOrService = subjectIndividualAddressPlaceNameOrService;
	}
	
	public String getSubjectIndividualAddressPlaceNameOrService() {
		return this.subjectIndividualAddressPlaceNameOrService;
	}

	public void setSubjectIndividualAddressPostalCode(String subjectIndividualAddressPostalCode) {
		this.subjectIndividualAddressPostalCode = subjectIndividualAddressPostalCode;
	}
	
	public String getSubjectIndividualAddressPostalCode() {
		return this.subjectIndividualAddressPostalCode;
	}

	public void setSubjectIndividualAddressCity(String subjectIndividualAddressCity) {
		this.subjectIndividualAddressCity = subjectIndividualAddressCity;
	}
	
	public String getSubjectIndividualAddressCity() {
		return this.subjectIndividualAddressCity;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}
	
	public String getNeeds() {
		return this.needs;
	}

	public void setWritingHelp(boolean writingHelp) {
		this.writingHelp = writingHelp;
	}
	
	public boolean getWritingHelp() {
		return this.writingHelp;
	}

	public void setLegalRepresentative(boolean legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	public boolean getLegalRepresentative() {
		return this.legalRepresentative;
	}

	public void setHopesAndNeeds(boolean hopesAndNeeds) {
		this.hopesAndNeeds = hopesAndNeeds;
	}
	
	public boolean getHopesAndNeeds() {
		return this.hopesAndNeeds;
	}

	public void setHelperResponsability(String helperResponsability) {
		this.helperResponsability = helperResponsability;
	}
	
	public String getHelperResponsability() {
		return this.helperResponsability;
	}

	public void setLegalRepresentativeFamilyRelationship(String legalRepresentativeFamilyRelationship) {
		this.legalRepresentativeFamilyRelationship = legalRepresentativeFamilyRelationship;
	}
	
	public String getLegalRepresentativeFamilyRelationship() {
		return this.legalRepresentativeFamilyRelationship;
	}

	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}
	
	public String getHelperName() {
		return this.helperName;
	}

	public void setLegalRepresentativeName(String legalRepresentativeName) {
		this.legalRepresentativeName = legalRepresentativeName;
	}
	
	public String getLegalRepresentativeName() {
		return this.legalRepresentativeName;
	}

	public void setSubjectIndividualFirstName(String subjectIndividualFirstName) {
		this.subjectIndividualFirstName = subjectIndividualFirstName;
	}
	
	public String getSubjectIndividualFirstName() {
		return this.subjectIndividualFirstName;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getComments() {
		return this.comments;
	}

	public void setLegalRepresentativeFirstame(String legalRepresentativeFirstame) {
		this.legalRepresentativeFirstame = legalRepresentativeFirstame;
	}
	
	public String getLegalRepresentativeFirstame() {
		return this.legalRepresentativeFirstame;
	}

	public void setHopes(String hopes) {
		this.hopes = hopes;
	}
	
	public String getHopes() {
		return this.hopes;
	}

}

