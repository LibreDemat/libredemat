package fr.capwebct.capdemat.plugins.externalservices.edemande.adapters;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.capwebct.capdemat.plugins.externalservices.edemande.service.EdemandeService;
import fr.cg95.cvq.business.users.BankAccount;
import fr.cg95.cvq.business.users.FrenchRIB;
import fr.cg95.cvq.xml.common.AddressType;
import fr.cg95.cvq.xml.common.FrenchRIBType;
import fr.cg95.cvq.xml.common.TitleType;
import fr.cg95.cvq.xml.request.school.impl.StudyGrantRequestDocumentImpl.StudyGrantRequestImpl;

public class StudyGrantEdemandeRequest implements EdemandeRequest {

    private StudyGrantRequestImpl request;

    public StudyGrantEdemandeRequest(StudyGrantRequestImpl request) {
        this.request = request;
    }

    @Override
    public Calendar getAccountHolderBirthDate() {
        return request.getAccountHolderBirthDate();
    }

    @Override
    public String getAccountHolderEdemandeId() {
        return request.getAccountHolderEdemandeId();
    }

    @Override
    public String getAccountHolderFirstName() {
        return request.getAccountHolderFirstName();
    }

    @Override
    public String getAccountHolderLastName() {
        return request.getAccountHolderLastName();
    }

    @Override
    public TitleType.Enum getAccountHolderTitle() {
        return request.getAccountHolderTitle();
    }

    @Override
    public Calendar getCreationDate() {
        return request.getCreationDate();
    }

    @Override
    public String getEdemandeId() {
        return request.getEdemandeId();
    }

    @Override
    public FrenchRIBType getFrenchRIB() {
        if (request.getBankAccount() == null) return null;
        BankAccount bankAccount = BankAccount.xmlToModel(request.getBankAccount());
        if (bankAccount == null) return null;
        return FrenchRIB.modelToXml(bankAccount.toFrenchRIB());
    }

    @Override
    public Long getHomeFolderId() {
        return request.getHomeFolder().getId();
    }

    @Override
    public Long getId() {
        return request.getId();
    }

    @Override
    public AddressType getSubjectAddress() {
        return request.getSubject().getAdult().getAddress();
    }

    @Override
    public String getSubjectBirthCity() {
        return request.getSubject().getAdult().getBirthPlace() != null ?
            request.getSubject().getAdult().getBirthPlace().getCity() : null;
    }

    @Override
    public Calendar getSubjectBirthDate() {
        return request.getSubjectInformations().getSubjectBirthDate();
    }

    @Override
    public String getSubjectEdemandeId() {
        return request.getSubject().getAdult().getExternalId();
    }

    @Override
    public String getSubjectEmail() {
        return request.getSubject().getAdult().getEmail();
    }

    @Override
    public String getSubjectFirstName() {
        return request.getSubject().getAdult().getFirstName();
    }

    @Override
    public String getSubjectLastName() {
        return request.getSubject().getAdult().getLastName();
    }

    @Override
    public String getSubjectPhone() {
        if (!StringUtils.isBlank(request.getSubject().getAdult().getHomePhone())) {
            return request.getSubject().getAdult().getHomePhone();
        } else if (!StringUtils.isBlank(request.getSubject().getAdult().getMobilePhone())) {
            return request.getSubject().getAdult().getMobilePhone();
        } else if (!StringUtils.isBlank(request.getSubject().getAdult().getOfficePhone())) {
            return request.getSubject().getAdult().getOfficePhone();
        }
        return "";
    }

    @Override
    public TitleType.Enum getSubjectTitle() {
        return request.getSubject().getAdult().getTitle();
    }

    @Override
    public boolean isSubjectAccountHolder() {
        return request.getIsSubjectAccountHolder();
    }

    @Override
    public void setAccountHolderEdemandeId(String id) {
        request.setAccountHolderEdemandeId(id);
    }

    @Override
    public Long getSubjectId() {
        return request.getSubject().getAdult().getId();
    }

    @Override
    public void setSubjectEdemandeId(String id) {
        request.getSubject().getAdult().setExternalId(id);
    }

    @Override
    public void setEdemandeId(String id) {
        request.setEdemandeId(id);
    }

    @Override
    public Map<String, Object> getSpecificFields(EdemandeService service) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("firstRequest", request.getSubjectInformations().getSubjectFirstRequest());
        result.put("taxHouseholdCityCode", request.getTaxHouseholdCityArray().length == 0 ? "" :
            request.getTaxHouseholdCityArray(0).getName());
        result.put("taxHouseholdCityPrecision",
            StringUtils.defaultString(request.getTaxHouseholdCityPrecision()));
        result.put("taxHouseholdIncome", request.getTaxHouseholdIncome());
        result.put("hasCROUSHelp", request.getHasCROUSHelp());
        result.put("hasRegionalCouncilHelp", request.getHasRegionalCouncilHelp());
        result.put("hasEuropeHelp", request.getHasEuropeHelp());
        result.put("hasOtherHelp", request.getHasOtherHelp());
        result.put("AlevelsDate", request.getALevelsInformations().getAlevelsDate());
        result.put("AlevelsType", service.translate("sgr.property.alevels."
            + request.getALevelsInformations().getAlevels().toString().toLowerCase()));
        result.put("currentStudiesType", StringUtils.defaultIfEmpty(
            request.getCurrentStudiesInformations().getOtherStudiesLabel(),
            service.translate("sgr.property.currentStudiesDiploma."
            + request.getCurrentStudiesInformations().getCurrentStudiesDiploma().toString())));
        result.put("currentStudiesLevel", service.translate("sgr.property.currentStudiesLevel."
            + request.getCurrentStudiesInformations().getCurrentStudiesLevel().toString()));
        result.put("sandwichCourses", request.getCurrentStudiesInformations().getSandwichCourses());
        result.put("abroadInternship", request.getCurrentStudiesInformations().getAbroadInternship());
        result.put("abroadInternshipStartDate",
            service.formatDate(request.getCurrentStudiesInformations().getAbroadInternshipStartDate()));
        result.put("abroadInternshipEndDate", service.formatDate(
            request.getCurrentStudiesInformations().getAbroadInternshipEndDate()));
        result.put("currentSchoolName",
            StringUtils.defaultIfEmpty(request.getCurrentSchool().getCurrentSchoolNamePrecision(),
                request.getCurrentSchool().getCurrentSchoolNameArray().length == 0 ? "" :
                    request.getCurrentSchool().getCurrentSchoolNameArray(0).getName()));
        result.put("currentSchoolAddress", request.getCurrentSchool().getCurrentSchoolAddress());
        result.put("abroadInternshipSchoolName",
            request.getCurrentStudiesInformations().getAbroadInternship() ?
                request.getCurrentStudiesInformations().getAbroadInternshipSchoolName() : "");
        result.put("abroadInternshipSchoolCountry",
            request.getCurrentStudiesInformations().getAbroadInternship() ?
                service.translate("sgr.property.abroadInternshipSchoolCountry."
                    + request.getCurrentStudiesInformations().getAbroadInternshipSchoolCountry()) : "");
        result.put("distance", service.translate(
            "sgr.property.distance." + request.getDistance().toString()));
        return result;
    }

    @Override
    public Config getConfig() {
        return Config.SGR;
    }
}
