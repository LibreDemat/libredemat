package fr.cg95.cvq.fo.leisure.music.form;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpSession;
import fr.cg95.cvq.wizard.*;
import fr.cg95.cvq.xml.common.*;
import fr.cg95.cvq.xml.leisure.music.*;
import fr.cg95.cvq.xml.leisure.music.MusicSchoolRegistrationRequestDocument.MusicSchoolRegistrationRequest;

public class Validation extends IStageForm {

	private Calendar subjectIndividualBirthDate;
	private String subjectIndividualFirstName2;
	private String subjectIndividualLastName;
  	private String subjectIndividualAddressAdditionalDeliveryInformation;
	private String subjectIndividualAddressAdditionalGeographicalInformation;
	private String subjectIndividualAddressStreetNumber;
	private String subjectIndividualAddressStreetName;
	private String subjectIndividualAddressPlaceNameOrService;
	private String subjectIndividualAddressPostalCode;
	private String subjectIndividualAddressCity;
	private boolean rulesAndRegulationsAcceptance;
	private String subjectIndividualFirstName;
	private String subjectIndividualFirstName3;
	private String subjectIndividualSex;

	public Validation() {
		super();
	}
	
	public void reset(String state) {
		if (state.equals("summary")) {
		}
	}
	
	public void load(HttpSession session, Object xmlbRequest) {
		if ((xmlbRequest != null) && (xmlbRequest instanceof MusicSchoolRegistrationRequest)) {
			MusicSchoolRegistrationRequest request = (MusicSchoolRegistrationRequest)xmlbRequest;
			this.subjectIndividualBirthDate = request.getSubject().getIndividual().getBirthDate();
			this.subjectIndividualFirstName2 = request.getSubject().getIndividual().getFirstName2();
			this.subjectIndividualLastName = request.getSubject().getIndividual().getLastName();
  			this.subjectIndividualAddressAdditionalDeliveryInformation = request.getSubject().getIndividual().getAddress().getAdditionalDeliveryInformation();
			this.subjectIndividualAddressAdditionalGeographicalInformation = request.getSubject().getIndividual().getAddress().getAdditionalGeographicalInformation();
			this.subjectIndividualAddressStreetNumber = request.getSubject().getIndividual().getAddress().getStreetNumber();
			this.subjectIndividualAddressStreetName = request.getSubject().getIndividual().getAddress().getStreetName();
			this.subjectIndividualAddressPlaceNameOrService = request.getSubject().getIndividual().getAddress().getPlaceNameOrService();
			this.subjectIndividualAddressPostalCode = request.getSubject().getIndividual().getAddress().getPostalCode();
			this.subjectIndividualAddressCity = request.getSubject().getIndividual().getAddress().getCity();
			this.rulesAndRegulationsAcceptance = request.getRulesAndRegulationsAcceptance();
			this.subjectIndividualFirstName = request.getSubject().getIndividual().getFirstName();
			this.subjectIndividualFirstName3 = request.getSubject().getIndividual().getFirstName3();
			if (request.getSubject().getIndividual().getSex() != null)
			this.subjectIndividualSex = request.getSubject().getIndividual().getSex().toString();
		}
	}
	
	public void save(HttpSession session, Object xmlbRequest) {
		if ((xmlbRequest != null) && (xmlbRequest instanceof MusicSchoolRegistrationRequest)) {
			MusicSchoolRegistrationRequest request = (MusicSchoolRegistrationRequest)xmlbRequest;
			request.getSubject().getIndividual().setBirthDate(this.subjectIndividualBirthDate);
			request.getSubject().getIndividual().setFirstName2(this.subjectIndividualFirstName2);
			request.setActivityArray(saveForm((ReferentialData)session.getAttribute("Activity")));
			request.getSubject().getIndividual().setLastName(this.subjectIndividualLastName);
  			request.getSubject().getIndividual().getAddress().setAdditionalDeliveryInformation(this.subjectIndividualAddressAdditionalDeliveryInformation);
			request.getSubject().getIndividual().getAddress().setAdditionalGeographicalInformation(this.subjectIndividualAddressAdditionalGeographicalInformation);
			request.getSubject().getIndividual().getAddress().setStreetNumber(this.subjectIndividualAddressStreetNumber);
			request.getSubject().getIndividual().getAddress().setStreetName(this.subjectIndividualAddressStreetName);
			request.getSubject().getIndividual().getAddress().setPlaceNameOrService(this.subjectIndividualAddressPlaceNameOrService);
			request.getSubject().getIndividual().getAddress().setPostalCode(this.subjectIndividualAddressPostalCode);
			request.getSubject().getIndividual().getAddress().setCity(this.subjectIndividualAddressCity);
			request.setRulesAndRegulationsAcceptance(this.rulesAndRegulationsAcceptance);
			request.getSubject().getIndividual().setFirstName(this.subjectIndividualFirstName);
			request.getSubject().getIndividual().setFirstName3(this.subjectIndividualFirstName3);
			request.getSubject().getIndividual().setSex(SexType.Enum.forString(this.subjectIndividualSex));
		}
	}
	
	public boolean isComplete() {
		if (this.checkSubjectIndividualLastName() &&
			((this.subjectIndividualLastName == null) || (this.subjectIndividualLastName.length() == 0)))
			return false;
  		if (this.checkSubjectIndividualAddressStreetName() &&
			((this.subjectIndividualAddressStreetName == null) || (this.subjectIndividualAddressStreetName.length() == 0)))
			return false;
		if (this.checkSubjectIndividualAddressPostalCode() &&
			((this.subjectIndividualAddressPostalCode == null) || (this.subjectIndividualAddressPostalCode.length() == 0)))
			return false;
		if (this.checkSubjectIndividualAddressCity() &&
			((this.subjectIndividualAddressCity == null) || (this.subjectIndividualAddressCity.length() == 0)))
			return false;
		if (this.checkSubjectIndividualFirstName() &&
			((this.subjectIndividualFirstName == null) || (this.subjectIndividualFirstName.length() == 0)))
			return false;
		return true;
	}
	
	public void setSubjectIndividualBirthDate(Calendar subjectIndividualBirthDate) {
		this.subjectIndividualBirthDate = subjectIndividualBirthDate;
	}
	
	public Calendar getSubjectIndividualBirthDate() {
		return this.subjectIndividualBirthDate;
	}
	
	public boolean checkSubjectIndividualBirthDate() {
		return true;
	}

	public void setSubjectIndividualFirstName2(String subjectIndividualFirstName2) {
		this.subjectIndividualFirstName2 = subjectIndividualFirstName2;
	}
	
	public String getSubjectIndividualFirstName2() {
		return this.subjectIndividualFirstName2;
	}
	
	public boolean checkSubjectIndividualFirstName2() {
		return true;
	}

	public void setSubjectIndividualLastName(String subjectIndividualLastName) {
		this.subjectIndividualLastName = subjectIndividualLastName;
	}
	
	public String getSubjectIndividualLastName() {
		return this.subjectIndividualLastName;
	}
	
	public boolean checkSubjectIndividualLastName() {
		return true;
	}

  	public void setSubjectIndividualAddressAdditionalDeliveryInformation(String subjectIndividualAddressAdditionalDeliveryInformation) {
		this.subjectIndividualAddressAdditionalDeliveryInformation = subjectIndividualAddressAdditionalDeliveryInformation;
	}
	
	public String getSubjectIndividualAddressAdditionalDeliveryInformation() {
		return this.subjectIndividualAddressAdditionalDeliveryInformation;
	}
	
	public boolean checkSubjectIndividualAddressAdditionalDeliveryInformation() {
		return true;
	}

	public void setSubjectIndividualAddressAdditionalGeographicalInformation(String subjectIndividualAddressAdditionalGeographicalInformation) {
		this.subjectIndividualAddressAdditionalGeographicalInformation = subjectIndividualAddressAdditionalGeographicalInformation;
	}
	
	public String getSubjectIndividualAddressAdditionalGeographicalInformation() {
		return this.subjectIndividualAddressAdditionalGeographicalInformation;
	}
	
	public boolean checkSubjectIndividualAddressAdditionalGeographicalInformation() {
		return true;
	}

	public void setSubjectIndividualAddressStreetNumber(String subjectIndividualAddressStreetNumber) {
		this.subjectIndividualAddressStreetNumber = subjectIndividualAddressStreetNumber;
	}
	
	public String getSubjectIndividualAddressStreetNumber() {
		return this.subjectIndividualAddressStreetNumber;
	}
	
	public boolean checkSubjectIndividualAddressStreetNumber() {
		return true;
	}

	public void setSubjectIndividualAddressStreetName(String subjectIndividualAddressStreetName) {
		this.subjectIndividualAddressStreetName = subjectIndividualAddressStreetName;
	}
	
	public String getSubjectIndividualAddressStreetName() {
		return this.subjectIndividualAddressStreetName;
	}
	
	public boolean checkSubjectIndividualAddressStreetName() {
		return true;
	}

	public void setSubjectIndividualAddressPlaceNameOrService(String subjectIndividualAddressPlaceNameOrService) {
		this.subjectIndividualAddressPlaceNameOrService = subjectIndividualAddressPlaceNameOrService;
	}
	
	public String getSubjectIndividualAddressPlaceNameOrService() {
		return this.subjectIndividualAddressPlaceNameOrService;
	}
	
	public boolean checkSubjectIndividualAddressPlaceNameOrService() {
		return true;
	}

	public void setSubjectIndividualAddressPostalCode(String subjectIndividualAddressPostalCode) {
		this.subjectIndividualAddressPostalCode = subjectIndividualAddressPostalCode;
	}
	
	public String getSubjectIndividualAddressPostalCode() {
		return this.subjectIndividualAddressPostalCode;
	}
	
	public boolean checkSubjectIndividualAddressPostalCode() {
		return true;
	}

	public void setSubjectIndividualAddressCity(String subjectIndividualAddressCity) {
		this.subjectIndividualAddressCity = subjectIndividualAddressCity;
	}
	
	public String getSubjectIndividualAddressCity() {
		return this.subjectIndividualAddressCity;
	}
	
	public boolean checkSubjectIndividualAddressCity() {
		return true;
	}

	public void setRulesAndRegulationsAcceptance(boolean rulesAndRegulationsAcceptance) {
		this.rulesAndRegulationsAcceptance = rulesAndRegulationsAcceptance;
	}
	
	public boolean getRulesAndRegulationsAcceptance() {
		return this.rulesAndRegulationsAcceptance;
	}
	
	public boolean checkRulesAndRegulationsAcceptance() {
		return true;
	}

	public void setSubjectIndividualFirstName(String subjectIndividualFirstName) {
		this.subjectIndividualFirstName = subjectIndividualFirstName;
	}
	
	public String getSubjectIndividualFirstName() {
		return this.subjectIndividualFirstName;
	}
	
	public boolean checkSubjectIndividualFirstName() {
		return true;
	}

	public void setSubjectIndividualFirstName3(String subjectIndividualFirstName3) {
		this.subjectIndividualFirstName3 = subjectIndividualFirstName3;
	}
	
	public String getSubjectIndividualFirstName3() {
		return this.subjectIndividualFirstName3;
	}
	
	public boolean checkSubjectIndividualFirstName3() {
		return true;
	}

	public void setSubjectIndividualSex(String subjectIndividualSex) {
		this.subjectIndividualSex = subjectIndividualSex;
	}
	
	public String getSubjectIndividualSex() {
		return this.subjectIndividualSex;
	}
	
	public boolean checkSubjectIndividualSex() {
		return true;
	}

}
