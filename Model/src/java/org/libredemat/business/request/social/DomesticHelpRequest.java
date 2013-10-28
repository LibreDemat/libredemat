
package org.libredemat.business.request.social;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalTime;

import net.sf.oval.constraint.AssertValid;
import org.apache.xmlbeans.XmlOptions;
import org.libredemat.business.authority.*;
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.social.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class DomesticHelpRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = DomesticHelpRequestData.conditions;

    @AssertValid(message = "")
    private DomesticHelpRequestData domesticHelpRequestData;

    public DomesticHelpRequest(RequestData requestData, DomesticHelpRequestData domesticHelpRequestData) {
        super(requestData);
        this.domesticHelpRequestData = domesticHelpRequestData;
    }

    public DomesticHelpRequest() {
        super();
        this.domesticHelpRequestData = new DomesticHelpRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("subject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("familyReferent", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("spouse", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("dwelling", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("resources", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("taxes", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("validation", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public DomesticHelpRequestData getSpecificData() {
        return domesticHelpRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(DomesticHelpRequestData domesticHelpRequestData) {
        this.domesticHelpRequestData = domesticHelpRequestData;
    }

    @Override
    public final String modelToXmlString() {
        DomesticHelpRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final DomesticHelpRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        DomesticHelpRequestDocument domesticHelpRequestDoc = DomesticHelpRequestDocument.Factory.newInstance();
        DomesticHelpRequestDocument.DomesticHelpRequest domesticHelpRequest = domesticHelpRequestDoc.addNewDomesticHelpRequest();
        super.fillCommonXmlInfo(domesticHelpRequest);
        int i = 0;
          DhrIncomesType dhrIncomesTypeDhrRequesterIncomes = domesticHelpRequest.addNewDhrRequesterIncomes();
        if (getDhrAllowances() != null)
            dhrIncomesTypeDhrRequesterIncomes.setDhrAllowances(new BigInteger(getDhrAllowances().toString()));
        DhrRequesterPensionPlanType dhrRequesterPensionPlanTypeDhrRequesterPensionPlan = domesticHelpRequest.addNewDhrRequesterPensionPlan();
        dhrRequesterPensionPlanTypeDhrRequesterPensionPlan.setDhrComplementaryPensionPlan(getDhrComplementaryPensionPlan());
        DhrCurrentDwellingType dhrCurrentDwellingTypeDhrCurrentDwelling = domesticHelpRequest.addNewDhrCurrentDwelling();
        if (getDhrCurrentDwellingAddress() != null)
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingAddress(Address.modelToXml(getDhrCurrentDwellingAddress()));
      
        date = getDhrCurrentDwellingArrivalDate();
        if (date != null) {
            calendar.setTime(date);
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingArrivalDate(calendar);
        }
      
        if (getDhrCurrentDwellingKind() != null)
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingKind(org.libredemat.xml.request.social.DhrDwellingKindType.Enum.forString(getDhrCurrentDwellingKind().getLegacyLabel()));
      
        if (getDhrCurrentDwellingNetArea() != null)
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingNetArea(getDhrCurrentDwellingNetArea());
      
        if (getDhrCurrentDwellingNumberOfRoom() != null)
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingNumberOfRoom(getDhrCurrentDwellingNumberOfRoom());
      
        dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingPhone(getDhrCurrentDwellingPhone());
      
        if (getDhrCurrentDwellingStatus() != null)
            dhrCurrentDwellingTypeDhrCurrentDwelling.setDhrCurrentDwellingStatus(org.libredemat.xml.request.social.DhrDwellingStatusType.Enum.forString(getDhrCurrentDwellingStatus().getLegacyLabel()));
      
        if (getDhrFurnitureInvestmentIncome() != null)
            dhrIncomesTypeDhrRequesterIncomes.setDhrFurnitureInvestmentIncome(new BigInteger(getDhrFurnitureInvestmentIncome().toString()));
        DhrRequesterGuardianType dhrRequesterGuardianTypeDhrRequesterGuardian = domesticHelpRequest.addNewDhrRequesterGuardian();
        if (getDhrGuardianAddress() != null)
            dhrRequesterGuardianTypeDhrRequesterGuardian.setDhrGuardianAddress(Address.modelToXml(getDhrGuardianAddress()));
      
        if (getDhrGuardianMeasure() != null)
            dhrRequesterGuardianTypeDhrRequesterGuardian.setDhrGuardianMeasure(org.libredemat.xml.request.social.DhrGuardianMeasureType.Enum.forString(getDhrGuardianMeasure().getLegacyLabel()));
      
        dhrRequesterGuardianTypeDhrRequesterGuardian.setDhrGuardianName(getDhrGuardianName());
        DhrFamilyReferentType dhrFamilyReferentTypeDhrFamilyReferent = domesticHelpRequest.addNewDhrFamilyReferent();
        if (getDhrHaveFamilyReferent() != null)
            dhrFamilyReferentTypeDhrFamilyReferent.setDhrHaveFamilyReferent(getDhrHaveFamilyReferent().booleanValue());
        DhrTaxesAmountType dhrTaxesAmountTypeDhrTaxesAmount = domesticHelpRequest.addNewDhrTaxesAmount();
        if (getDhrIncomeTax() != null)
            dhrTaxesAmountTypeDhrTaxesAmount.setDhrIncomeTax(new BigInteger(getDhrIncomeTax().toString()));
      
        if (getDhrIncomesAnnualTotal() != null)
            dhrIncomesTypeDhrRequesterIncomes.setDhrIncomesAnnualTotal(new BigInteger(getDhrIncomesAnnualTotal().toString()));
        DhrSpouseStatusType dhrSpouseStatusTypeDhrSpouseStatus = domesticHelpRequest.addNewDhrSpouseStatus();
        if (getDhrIsSpouseRetired() != null)
            dhrSpouseStatusTypeDhrSpouseStatus.setDhrIsSpouseRetired(getDhrIsSpouseRetired().booleanValue());
      
        if (getDhrNetIncome() != null)
            dhrIncomesTypeDhrRequesterIncomes.setDhrNetIncome(new BigInteger(getDhrNetIncome().toString()));
      
        i = 0;
        if (getDhrNotRealAsset() != null) {
            org.libredemat.xml.request.social.DhrNotRealAssetType[] dhrNotRealAssetTypeTab = new org.libredemat.xml.request.social.DhrNotRealAssetType[getDhrNotRealAsset().size()];
            for (DhrNotRealAsset object : getDhrNotRealAsset()) {
              dhrNotRealAssetTypeTab[i++] = object.modelToXml();
            }
            domesticHelpRequest.setDhrNotRealAssetArray(dhrNotRealAssetTypeTab);
        }
      
        dhrRequesterPensionPlanTypeDhrRequesterPensionPlan.setDhrPensionPlanDetail(getDhrPensionPlanDetail());
      
        i = 0;
        if (getDhrPreviousDwelling() != null) {
            org.libredemat.xml.request.social.DhrPreviousDwellingType[] dhrPreviousDwellingTypeTab = new org.libredemat.xml.request.social.DhrPreviousDwellingType[getDhrPreviousDwelling().size()];
            for (DhrPreviousDwelling object : getDhrPreviousDwelling()) {
              dhrPreviousDwellingTypeTab[i++] = object.modelToXml();
            }
            domesticHelpRequest.setDhrPreviousDwellingArray(dhrPreviousDwellingTypeTab);
        }
      
        if (getDhrPrincipalPensionPlan() != null)
            dhrRequesterPensionPlanTypeDhrRequesterPensionPlan.setDhrPrincipalPensionPlan(org.libredemat.xml.request.social.DhrPrincipalPensionPlanType.Enum.forString(getDhrPrincipalPensionPlan().getLegacyLabel()));
      
        i = 0;
        if (getDhrRealAsset() != null) {
            org.libredemat.xml.request.social.DhrRealAssetType[] dhrRealAssetTypeTab = new org.libredemat.xml.request.social.DhrRealAssetType[getDhrRealAsset().size()];
            for (DhrRealAsset object : getDhrRealAsset()) {
              dhrRealAssetTypeTab[i++] = object.modelToXml();
            }
            domesticHelpRequest.setDhrRealAssetArray(dhrRealAssetTypeTab);
        }
      
        if (getDhrRealEstateInvestmentIncome() != null)
            dhrIncomesTypeDhrRequesterIncomes.setDhrRealEstateInvestmentIncome(new BigInteger(getDhrRealEstateInvestmentIncome().toString()));
      
        if (getDhrReferentAddress() != null)
            dhrFamilyReferentTypeDhrFamilyReferent.setDhrReferentAddress(Address.modelToXml(getDhrReferentAddress()));
      
        dhrFamilyReferentTypeDhrFamilyReferent.setDhrReferentFirstName(getDhrReferentFirstName());
      
        dhrFamilyReferentTypeDhrFamilyReferent.setDhrReferentName(getDhrReferentName());
        DhrSpouseType dhrSpouseTypeDhrSpouse = domesticHelpRequest.addNewDhrSpouse();
        if (getDhrRequestKind() != null)
            dhrSpouseTypeDhrSpouse.setDhrRequestKind(org.libredemat.xml.request.social.DhrRequestKindType.Enum.forString(getDhrRequestKind().getLegacyLabel()));
        DhrRequesterType dhrRequesterTypeDhrRequester = domesticHelpRequest.addNewDhrRequester();
        date = getDhrRequesterBirthDate();
        if (date != null) {
            calendar.setTime(date);
            dhrRequesterTypeDhrRequester.setDhrRequesterBirthDate(calendar);
        }
      
        dhrRequesterTypeDhrRequester.setDhrRequesterBirthPlace(getDhrRequesterBirthPlace());
      
        date = getDhrRequesterFranceArrivalDate();
        if (date != null) {
            calendar.setTime(date);
            dhrRequesterTypeDhrRequester.setDhrRequesterFranceArrivalDate(calendar);
        }
      
        if (getDhrRequesterHaveGuardian() != null)
            dhrRequesterGuardianTypeDhrRequesterGuardian.setDhrRequesterHaveGuardian(getDhrRequesterHaveGuardian().booleanValue());
      
        if (getDhrRequesterIsFrenchResident() != null)
            dhrRequesterTypeDhrRequester.setDhrRequesterIsFrenchResident(getDhrRequesterIsFrenchResident().booleanValue());
      
        if (getDhrRequesterNationality() != null)
            dhrRequesterTypeDhrRequester.setDhrRequesterNationality(org.libredemat.xml.common.NationalityType.Enum.forString(getDhrRequesterNationality().getLegacyLabel()));
      
        if (getDhrSpouseAddress() != null)
            dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpouseAddress(Address.modelToXml(getDhrSpouseAddress()));
      
        date = getDhrSpouseBirthDate();
        if (date != null) {
            calendar.setTime(date);
            dhrSpouseTypeDhrSpouse.setDhrSpouseBirthDate(calendar);
        }
      
        dhrSpouseTypeDhrSpouse.setDhrSpouseBirthPlace(getDhrSpouseBirthPlace());
      
        dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpouseComplementaryPensionPlan(getDhrSpouseComplementaryPensionPlan());
      
        dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpouseEmployer(getDhrSpouseEmployer());
      
        if (getDhrSpouseFamilyStatus() != null)
            dhrSpouseTypeDhrSpouse.setDhrSpouseFamilyStatus(org.libredemat.xml.common.FamilyStatusType.Enum.forString(getDhrSpouseFamilyStatus().getLegacyLabel()));
      
        dhrSpouseTypeDhrSpouse.setDhrSpouseFirstName(getDhrSpouseFirstName());
      
        date = getDhrSpouseFranceArrivalDate();
        if (date != null) {
            calendar.setTime(date);
            dhrSpouseTypeDhrSpouse.setDhrSpouseFranceArrivalDate(calendar);
        }
      
        if (getDhrSpouseIsFrenchResident() != null)
            dhrSpouseTypeDhrSpouse.setDhrSpouseIsFrenchResident(getDhrSpouseIsFrenchResident().booleanValue());
      
        dhrSpouseTypeDhrSpouse.setDhrSpouseMaidenName(getDhrSpouseMaidenName());
      
        dhrSpouseTypeDhrSpouse.setDhrSpouseName(getDhrSpouseName());
      
        if (getDhrSpouseNationality() != null)
            dhrSpouseTypeDhrSpouse.setDhrSpouseNationality(org.libredemat.xml.common.NationalityType.Enum.forString(getDhrSpouseNationality().getLegacyLabel()));
      
        dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpousePensionPlanDetail(getDhrSpousePensionPlanDetail());
      
        if (getDhrSpousePrincipalPensionPlan() != null)
            dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpousePrincipalPensionPlan(org.libredemat.xml.request.social.DhrPrincipalPensionPlanType.Enum.forString(getDhrSpousePrincipalPensionPlan().getLegacyLabel()));
      
        dhrSpouseStatusTypeDhrSpouseStatus.setDhrSpouseProfession(getDhrSpouseProfession());
      
        if (getDhrSpouseTitle() != null)
            dhrSpouseTypeDhrSpouse.setDhrSpouseTitle(org.libredemat.xml.common.TitleType.Enum.forString(getDhrSpouseTitle().getLegacyLabel()));
      
        if (getLocalRate() != null)
            dhrTaxesAmountTypeDhrTaxesAmount.setLocalRate(new BigInteger(getLocalRate().toString()));
      
        if (getPensions() != null)
            dhrIncomesTypeDhrRequesterIncomes.setPensions(new BigInteger(getPensions().toString()));
      
        if (getProfessionalTaxes() != null)
            dhrTaxesAmountTypeDhrTaxesAmount.setProfessionalTaxes(new BigInteger(getProfessionalTaxes().toString()));
      
        if (getPropertyTaxes() != null)
            dhrTaxesAmountTypeDhrTaxesAmount.setPropertyTaxes(new BigInteger(getPropertyTaxes().toString()));
      
        return domesticHelpRequestDoc;
    }

    @Override
    public final DomesticHelpRequestDocument.DomesticHelpRequest modelToXmlRequest() {
        return modelToXml().getDomesticHelpRequest();
    }

    public static DomesticHelpRequest xmlToModel(DomesticHelpRequestDocument domesticHelpRequestDoc) {
        DomesticHelpRequestDocument.DomesticHelpRequest domesticHelpRequestXml = domesticHelpRequestDoc.getDomesticHelpRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        DomesticHelpRequest domesticHelpRequest = new DomesticHelpRequest();
        domesticHelpRequest.fillCommonModelInfo(domesticHelpRequest, domesticHelpRequestXml);
        
        domesticHelpRequest.setDhrAllowances(domesticHelpRequestXml.getDhrRequesterIncomes().getDhrAllowances());
      
        domesticHelpRequest.setDhrComplementaryPensionPlan(domesticHelpRequestXml.getDhrRequesterPensionPlan().getDhrComplementaryPensionPlan());
      
        if (domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingAddress() != null)
            domesticHelpRequest.setDhrCurrentDwellingAddress(Address.xmlToModel(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingAddress()));
      
        calendar = domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingArrivalDate();
        if (calendar != null) {
            domesticHelpRequest.setDhrCurrentDwellingArrivalDate(calendar.getTime());
        }
      
        if (domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingKind() != null)
            domesticHelpRequest.setDhrCurrentDwellingKind(org.libredemat.business.request.social.DhrDwellingKindType.forString(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingKind().toString()));
        else
            domesticHelpRequest.setDhrCurrentDwellingKind(org.libredemat.business.request.social.DhrDwellingKindType.getDefaultDhrDwellingKindType());
      
        if (domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingNetArea() != null)
            domesticHelpRequest.setDhrCurrentDwellingNetArea(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingNetArea());
      
        if (domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingNumberOfRoom() != null)
            domesticHelpRequest.setDhrCurrentDwellingNumberOfRoom(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingNumberOfRoom());
      
        domesticHelpRequest.setDhrCurrentDwellingPhone(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingPhone());
      
        if (domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingStatus() != null)
            domesticHelpRequest.setDhrCurrentDwellingStatus(org.libredemat.business.request.social.DhrDwellingStatusType.forString(domesticHelpRequestXml.getDhrCurrentDwelling().getDhrCurrentDwellingStatus().toString()));
        else
            domesticHelpRequest.setDhrCurrentDwellingStatus(org.libredemat.business.request.social.DhrDwellingStatusType.getDefaultDhrDwellingStatusType());
      
        domesticHelpRequest.setDhrFurnitureInvestmentIncome(domesticHelpRequestXml.getDhrRequesterIncomes().getDhrFurnitureInvestmentIncome());
      
        if (domesticHelpRequestXml.getDhrRequesterGuardian().getDhrGuardianAddress() != null)
            domesticHelpRequest.setDhrGuardianAddress(Address.xmlToModel(domesticHelpRequestXml.getDhrRequesterGuardian().getDhrGuardianAddress()));
      
        if (domesticHelpRequestXml.getDhrRequesterGuardian().getDhrGuardianMeasure() != null)
            domesticHelpRequest.setDhrGuardianMeasure(org.libredemat.business.request.social.DhrGuardianMeasureType.forString(domesticHelpRequestXml.getDhrRequesterGuardian().getDhrGuardianMeasure().toString()));
        else
            domesticHelpRequest.setDhrGuardianMeasure(org.libredemat.business.request.social.DhrGuardianMeasureType.getDefaultDhrGuardianMeasureType());
      
        domesticHelpRequest.setDhrGuardianName(domesticHelpRequestXml.getDhrRequesterGuardian().getDhrGuardianName());
      
        domesticHelpRequest.setDhrHaveFamilyReferent(Boolean.valueOf(domesticHelpRequestXml.getDhrFamilyReferent().getDhrHaveFamilyReferent()));
      
        domesticHelpRequest.setDhrIncomeTax(domesticHelpRequestXml.getDhrTaxesAmount().getDhrIncomeTax());
      
        domesticHelpRequest.setDhrIncomesAnnualTotal(domesticHelpRequestXml.getDhrRequesterIncomes().getDhrIncomesAnnualTotal());
      
        domesticHelpRequest.setDhrIsSpouseRetired(Boolean.valueOf(domesticHelpRequestXml.getDhrSpouseStatus().getDhrIsSpouseRetired()));
      
        domesticHelpRequest.setDhrNetIncome(domesticHelpRequestXml.getDhrRequesterIncomes().getDhrNetIncome());
      
        List<org.libredemat.business.request.social.DhrNotRealAsset> dhrNotRealAssetList = new ArrayList<org.libredemat.business.request.social.DhrNotRealAsset>(domesticHelpRequestXml.sizeOfDhrNotRealAssetArray());
        for (DhrNotRealAssetType object : domesticHelpRequestXml.getDhrNotRealAssetArray()) {
            dhrNotRealAssetList.add(org.libredemat.business.request.social.DhrNotRealAsset.xmlToModel(object));
        }
        domesticHelpRequest.setDhrNotRealAsset(dhrNotRealAssetList);
      
        domesticHelpRequest.setDhrPensionPlanDetail(domesticHelpRequestXml.getDhrRequesterPensionPlan().getDhrPensionPlanDetail());
      
        List<org.libredemat.business.request.social.DhrPreviousDwelling> dhrPreviousDwellingList = new ArrayList<org.libredemat.business.request.social.DhrPreviousDwelling>(domesticHelpRequestXml.sizeOfDhrPreviousDwellingArray());
        for (DhrPreviousDwellingType object : domesticHelpRequestXml.getDhrPreviousDwellingArray()) {
            dhrPreviousDwellingList.add(org.libredemat.business.request.social.DhrPreviousDwelling.xmlToModel(object));
        }
        domesticHelpRequest.setDhrPreviousDwelling(dhrPreviousDwellingList);
      
        if (domesticHelpRequestXml.getDhrRequesterPensionPlan().getDhrPrincipalPensionPlan() != null)
            domesticHelpRequest.setDhrPrincipalPensionPlan(org.libredemat.business.request.social.DhrPrincipalPensionPlanType.forString(domesticHelpRequestXml.getDhrRequesterPensionPlan().getDhrPrincipalPensionPlan().toString()));
        else
            domesticHelpRequest.setDhrPrincipalPensionPlan(org.libredemat.business.request.social.DhrPrincipalPensionPlanType.getDefaultDhrPrincipalPensionPlanType());
      
        List<org.libredemat.business.request.social.DhrRealAsset> dhrRealAssetList = new ArrayList<org.libredemat.business.request.social.DhrRealAsset>(domesticHelpRequestXml.sizeOfDhrRealAssetArray());
        for (DhrRealAssetType object : domesticHelpRequestXml.getDhrRealAssetArray()) {
            dhrRealAssetList.add(org.libredemat.business.request.social.DhrRealAsset.xmlToModel(object));
        }
        domesticHelpRequest.setDhrRealAsset(dhrRealAssetList);
      
        domesticHelpRequest.setDhrRealEstateInvestmentIncome(domesticHelpRequestXml.getDhrRequesterIncomes().getDhrRealEstateInvestmentIncome());
      
        if (domesticHelpRequestXml.getDhrFamilyReferent().getDhrReferentAddress() != null)
            domesticHelpRequest.setDhrReferentAddress(Address.xmlToModel(domesticHelpRequestXml.getDhrFamilyReferent().getDhrReferentAddress()));
      
        domesticHelpRequest.setDhrReferentFirstName(domesticHelpRequestXml.getDhrFamilyReferent().getDhrReferentFirstName());
      
        domesticHelpRequest.setDhrReferentName(domesticHelpRequestXml.getDhrFamilyReferent().getDhrReferentName());
      
        if (domesticHelpRequestXml.getDhrSpouse().getDhrRequestKind() != null)
            domesticHelpRequest.setDhrRequestKind(org.libredemat.business.request.social.DhrRequestKindType.forString(domesticHelpRequestXml.getDhrSpouse().getDhrRequestKind().toString()));
        else
            domesticHelpRequest.setDhrRequestKind(org.libredemat.business.request.social.DhrRequestKindType.getDefaultDhrRequestKindType());
      
        calendar = domesticHelpRequestXml.getDhrRequester().getDhrRequesterBirthDate();
        if (calendar != null) {
            domesticHelpRequest.setDhrRequesterBirthDate(calendar.getTime());
        }
      
        domesticHelpRequest.setDhrRequesterBirthPlace(domesticHelpRequestXml.getDhrRequester().getDhrRequesterBirthPlace());
      
        calendar = domesticHelpRequestXml.getDhrRequester().getDhrRequesterFranceArrivalDate();
        if (calendar != null) {
            domesticHelpRequest.setDhrRequesterFranceArrivalDate(calendar.getTime());
        }
      
        domesticHelpRequest.setDhrRequesterHaveGuardian(Boolean.valueOf(domesticHelpRequestXml.getDhrRequesterGuardian().getDhrRequesterHaveGuardian()));
      
        domesticHelpRequest.setDhrRequesterIsFrenchResident(Boolean.valueOf(domesticHelpRequestXml.getDhrRequester().getDhrRequesterIsFrenchResident()));
      
        if (domesticHelpRequestXml.getDhrRequester().getDhrRequesterNationality() != null)
            domesticHelpRequest.setDhrRequesterNationality(org.libredemat.business.users.NationalityType.forString(domesticHelpRequestXml.getDhrRequester().getDhrRequesterNationality().toString()));
        else
            domesticHelpRequest.setDhrRequesterNationality(org.libredemat.business.users.NationalityType.getDefaultNationalityType());
      
        if (domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpouseAddress() != null)
            domesticHelpRequest.setDhrSpouseAddress(Address.xmlToModel(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpouseAddress()));
      
        calendar = domesticHelpRequestXml.getDhrSpouse().getDhrSpouseBirthDate();
        if (calendar != null) {
            domesticHelpRequest.setDhrSpouseBirthDate(calendar.getTime());
        }
      
        domesticHelpRequest.setDhrSpouseBirthPlace(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseBirthPlace());
      
        domesticHelpRequest.setDhrSpouseComplementaryPensionPlan(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpouseComplementaryPensionPlan());
      
        domesticHelpRequest.setDhrSpouseEmployer(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpouseEmployer());
      
        if (domesticHelpRequestXml.getDhrSpouse().getDhrSpouseFamilyStatus() != null)
            domesticHelpRequest.setDhrSpouseFamilyStatus(org.libredemat.business.users.FamilyStatusType.forString(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseFamilyStatus().toString()));
        else
            domesticHelpRequest.setDhrSpouseFamilyStatus(org.libredemat.business.users.FamilyStatusType.getDefaultFamilyStatusType());
      
        domesticHelpRequest.setDhrSpouseFirstName(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseFirstName());
      
        calendar = domesticHelpRequestXml.getDhrSpouse().getDhrSpouseFranceArrivalDate();
        if (calendar != null) {
            domesticHelpRequest.setDhrSpouseFranceArrivalDate(calendar.getTime());
        }
      
        domesticHelpRequest.setDhrSpouseIsFrenchResident(Boolean.valueOf(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseIsFrenchResident()));
      
        domesticHelpRequest.setDhrSpouseMaidenName(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseMaidenName());
      
        domesticHelpRequest.setDhrSpouseName(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseName());
      
        if (domesticHelpRequestXml.getDhrSpouse().getDhrSpouseNationality() != null)
            domesticHelpRequest.setDhrSpouseNationality(org.libredemat.business.users.NationalityType.forString(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseNationality().toString()));
        else
            domesticHelpRequest.setDhrSpouseNationality(org.libredemat.business.users.NationalityType.getDefaultNationalityType());
      
        domesticHelpRequest.setDhrSpousePensionPlanDetail(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpousePensionPlanDetail());
      
        if (domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpousePrincipalPensionPlan() != null)
            domesticHelpRequest.setDhrSpousePrincipalPensionPlan(org.libredemat.business.request.social.DhrPrincipalPensionPlanType.forString(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpousePrincipalPensionPlan().toString()));
        else
            domesticHelpRequest.setDhrSpousePrincipalPensionPlan(org.libredemat.business.request.social.DhrPrincipalPensionPlanType.getDefaultDhrPrincipalPensionPlanType());
      
        domesticHelpRequest.setDhrSpouseProfession(domesticHelpRequestXml.getDhrSpouseStatus().getDhrSpouseProfession());
      
        if (domesticHelpRequestXml.getDhrSpouse().getDhrSpouseTitle() != null)
            domesticHelpRequest.setDhrSpouseTitle(org.libredemat.business.users.TitleType.forString(domesticHelpRequestXml.getDhrSpouse().getDhrSpouseTitle().toString()));
        else
            domesticHelpRequest.setDhrSpouseTitle(org.libredemat.business.users.TitleType.getDefaultTitleType());
      
        domesticHelpRequest.setLocalRate(domesticHelpRequestXml.getDhrTaxesAmount().getLocalRate());
      
        domesticHelpRequest.setPensions(domesticHelpRequestXml.getDhrRequesterIncomes().getPensions());
      
        domesticHelpRequest.setProfessionalTaxes(domesticHelpRequestXml.getDhrTaxesAmount().getProfessionalTaxes());
      
        domesticHelpRequest.setPropertyTaxes(domesticHelpRequestXml.getDhrTaxesAmount().getPropertyTaxes());
      
        return domesticHelpRequest;
    }

    @Override
    public DomesticHelpRequest clone() {
        DomesticHelpRequest clone = new DomesticHelpRequest(getRequestData().clone(), domesticHelpRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("subject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("familyReferent", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("spouse", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("dwelling", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("resources", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("taxes", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("validation", stepState);
        
        return clone;
    }

  
    public final void setDhrAllowances(final java.math.BigInteger dhrAllowances) {
        domesticHelpRequestData.setDhrAllowances(dhrAllowances);
    }

    
    public final java.math.BigInteger getDhrAllowances() {
        return domesticHelpRequestData.getDhrAllowances();
    }
  
    public final void setDhrComplementaryPensionPlan(final String dhrComplementaryPensionPlan) {
        domesticHelpRequestData.setDhrComplementaryPensionPlan(dhrComplementaryPensionPlan);
    }

    
    public final String getDhrComplementaryPensionPlan() {
        return domesticHelpRequestData.getDhrComplementaryPensionPlan();
    }
  
    public final void setDhrCurrentDwellingAddress(final org.libredemat.business.users.Address dhrCurrentDwellingAddress) {
        domesticHelpRequestData.setDhrCurrentDwellingAddress(dhrCurrentDwellingAddress);
    }

    
    public final org.libredemat.business.users.Address getDhrCurrentDwellingAddress() {
        return domesticHelpRequestData.getDhrCurrentDwellingAddress();
    }
  
    public final void setDhrCurrentDwellingArrivalDate(final java.util.Date dhrCurrentDwellingArrivalDate) {
        domesticHelpRequestData.setDhrCurrentDwellingArrivalDate(dhrCurrentDwellingArrivalDate);
    }

    
    public final java.util.Date getDhrCurrentDwellingArrivalDate() {
        return domesticHelpRequestData.getDhrCurrentDwellingArrivalDate();
    }
  
    public final void setDhrCurrentDwellingKind(final org.libredemat.business.request.social.DhrDwellingKindType dhrCurrentDwellingKind) {
        domesticHelpRequestData.setDhrCurrentDwellingKind(dhrCurrentDwellingKind);
    }

    
    public final org.libredemat.business.request.social.DhrDwellingKindType getDhrCurrentDwellingKind() {
        return domesticHelpRequestData.getDhrCurrentDwellingKind();
    }
  
    public final void setDhrCurrentDwellingNetArea(final java.math.BigDecimal dhrCurrentDwellingNetArea) {
        domesticHelpRequestData.setDhrCurrentDwellingNetArea(dhrCurrentDwellingNetArea);
    }

    
    public final java.math.BigDecimal getDhrCurrentDwellingNetArea() {
        return domesticHelpRequestData.getDhrCurrentDwellingNetArea();
    }
  
    public final void setDhrCurrentDwellingNumberOfRoom(final java.math.BigDecimal dhrCurrentDwellingNumberOfRoom) {
        domesticHelpRequestData.setDhrCurrentDwellingNumberOfRoom(dhrCurrentDwellingNumberOfRoom);
    }

    
    public final java.math.BigDecimal getDhrCurrentDwellingNumberOfRoom() {
        return domesticHelpRequestData.getDhrCurrentDwellingNumberOfRoom();
    }
  
    public final void setDhrCurrentDwellingPhone(final String dhrCurrentDwellingPhone) {
        domesticHelpRequestData.setDhrCurrentDwellingPhone(dhrCurrentDwellingPhone);
    }

    
    public final String getDhrCurrentDwellingPhone() {
        return domesticHelpRequestData.getDhrCurrentDwellingPhone();
    }
  
    public final void setDhrCurrentDwellingStatus(final org.libredemat.business.request.social.DhrDwellingStatusType dhrCurrentDwellingStatus) {
        domesticHelpRequestData.setDhrCurrentDwellingStatus(dhrCurrentDwellingStatus);
    }

    
    public final org.libredemat.business.request.social.DhrDwellingStatusType getDhrCurrentDwellingStatus() {
        return domesticHelpRequestData.getDhrCurrentDwellingStatus();
    }
  
    public final void setDhrFurnitureInvestmentIncome(final java.math.BigInteger dhrFurnitureInvestmentIncome) {
        domesticHelpRequestData.setDhrFurnitureInvestmentIncome(dhrFurnitureInvestmentIncome);
    }

    
    public final java.math.BigInteger getDhrFurnitureInvestmentIncome() {
        return domesticHelpRequestData.getDhrFurnitureInvestmentIncome();
    }
  
    public final void setDhrGuardianAddress(final org.libredemat.business.users.Address dhrGuardianAddress) {
        domesticHelpRequestData.setDhrGuardianAddress(dhrGuardianAddress);
    }

    
    public final org.libredemat.business.users.Address getDhrGuardianAddress() {
        return domesticHelpRequestData.getDhrGuardianAddress();
    }
  
    public final void setDhrGuardianMeasure(final org.libredemat.business.request.social.DhrGuardianMeasureType dhrGuardianMeasure) {
        domesticHelpRequestData.setDhrGuardianMeasure(dhrGuardianMeasure);
    }

    
    public final org.libredemat.business.request.social.DhrGuardianMeasureType getDhrGuardianMeasure() {
        return domesticHelpRequestData.getDhrGuardianMeasure();
    }
  
    public final void setDhrGuardianName(final String dhrGuardianName) {
        domesticHelpRequestData.setDhrGuardianName(dhrGuardianName);
    }

    
    public final String getDhrGuardianName() {
        return domesticHelpRequestData.getDhrGuardianName();
    }
  
    public final void setDhrHaveFamilyReferent(final Boolean dhrHaveFamilyReferent) {
        domesticHelpRequestData.setDhrHaveFamilyReferent(dhrHaveFamilyReferent);
    }

    
    public final Boolean getDhrHaveFamilyReferent() {
        return domesticHelpRequestData.getDhrHaveFamilyReferent();
    }
  
    public final void setDhrIncomeTax(final java.math.BigInteger dhrIncomeTax) {
        domesticHelpRequestData.setDhrIncomeTax(dhrIncomeTax);
    }

    
    public final java.math.BigInteger getDhrIncomeTax() {
        return domesticHelpRequestData.getDhrIncomeTax();
    }
  
    public final void setDhrIncomesAnnualTotal(final java.math.BigInteger dhrIncomesAnnualTotal) {
        domesticHelpRequestData.setDhrIncomesAnnualTotal(dhrIncomesAnnualTotal);
    }

    
    public final java.math.BigInteger getDhrIncomesAnnualTotal() {
        return domesticHelpRequestData.getDhrIncomesAnnualTotal();
    }
  
    public final void setDhrIsSpouseRetired(final Boolean dhrIsSpouseRetired) {
        domesticHelpRequestData.setDhrIsSpouseRetired(dhrIsSpouseRetired);
    }

    
    public final Boolean getDhrIsSpouseRetired() {
        return domesticHelpRequestData.getDhrIsSpouseRetired();
    }
  
    public final void setDhrNetIncome(final java.math.BigInteger dhrNetIncome) {
        domesticHelpRequestData.setDhrNetIncome(dhrNetIncome);
    }

    
    public final java.math.BigInteger getDhrNetIncome() {
        return domesticHelpRequestData.getDhrNetIncome();
    }
  
    public final void setDhrNotRealAsset(final List<org.libredemat.business.request.social.DhrNotRealAsset> dhrNotRealAsset) {
        domesticHelpRequestData.setDhrNotRealAsset(dhrNotRealAsset);
    }

    
    public final List<org.libredemat.business.request.social.DhrNotRealAsset> getDhrNotRealAsset() {
        return domesticHelpRequestData.getDhrNotRealAsset();
    }
  
    public final void setDhrPensionPlanDetail(final String dhrPensionPlanDetail) {
        domesticHelpRequestData.setDhrPensionPlanDetail(dhrPensionPlanDetail);
    }

    
    public final String getDhrPensionPlanDetail() {
        return domesticHelpRequestData.getDhrPensionPlanDetail();
    }
  
    public final void setDhrPreviousDwelling(final List<org.libredemat.business.request.social.DhrPreviousDwelling> dhrPreviousDwelling) {
        domesticHelpRequestData.setDhrPreviousDwelling(dhrPreviousDwelling);
    }

    
    public final List<org.libredemat.business.request.social.DhrPreviousDwelling> getDhrPreviousDwelling() {
        return domesticHelpRequestData.getDhrPreviousDwelling();
    }
  
    public final void setDhrPrincipalPensionPlan(final org.libredemat.business.request.social.DhrPrincipalPensionPlanType dhrPrincipalPensionPlan) {
        domesticHelpRequestData.setDhrPrincipalPensionPlan(dhrPrincipalPensionPlan);
    }

    
    public final org.libredemat.business.request.social.DhrPrincipalPensionPlanType getDhrPrincipalPensionPlan() {
        return domesticHelpRequestData.getDhrPrincipalPensionPlan();
    }
  
    public final void setDhrRealAsset(final List<org.libredemat.business.request.social.DhrRealAsset> dhrRealAsset) {
        domesticHelpRequestData.setDhrRealAsset(dhrRealAsset);
    }

    
    public final List<org.libredemat.business.request.social.DhrRealAsset> getDhrRealAsset() {
        return domesticHelpRequestData.getDhrRealAsset();
    }
  
    public final void setDhrRealEstateInvestmentIncome(final java.math.BigInteger dhrRealEstateInvestmentIncome) {
        domesticHelpRequestData.setDhrRealEstateInvestmentIncome(dhrRealEstateInvestmentIncome);
    }

    
    public final java.math.BigInteger getDhrRealEstateInvestmentIncome() {
        return domesticHelpRequestData.getDhrRealEstateInvestmentIncome();
    }
  
    public final void setDhrReferentAddress(final org.libredemat.business.users.Address dhrReferentAddress) {
        domesticHelpRequestData.setDhrReferentAddress(dhrReferentAddress);
    }

    
    public final org.libredemat.business.users.Address getDhrReferentAddress() {
        return domesticHelpRequestData.getDhrReferentAddress();
    }
  
    public final void setDhrReferentFirstName(final String dhrReferentFirstName) {
        domesticHelpRequestData.setDhrReferentFirstName(dhrReferentFirstName);
    }

    
    public final String getDhrReferentFirstName() {
        return domesticHelpRequestData.getDhrReferentFirstName();
    }
  
    public final void setDhrReferentName(final String dhrReferentName) {
        domesticHelpRequestData.setDhrReferentName(dhrReferentName);
    }

    
    public final String getDhrReferentName() {
        return domesticHelpRequestData.getDhrReferentName();
    }
  
    public final void setDhrRequestKind(final org.libredemat.business.request.social.DhrRequestKindType dhrRequestKind) {
        domesticHelpRequestData.setDhrRequestKind(dhrRequestKind);
    }

    
    public final org.libredemat.business.request.social.DhrRequestKindType getDhrRequestKind() {
        return domesticHelpRequestData.getDhrRequestKind();
    }
  
    public final void setDhrRequesterBirthDate(final java.util.Date dhrRequesterBirthDate) {
        domesticHelpRequestData.setDhrRequesterBirthDate(dhrRequesterBirthDate);
    }

    
    public final java.util.Date getDhrRequesterBirthDate() {
        return domesticHelpRequestData.getDhrRequesterBirthDate();
    }
  
    public final void setDhrRequesterBirthPlace(final String dhrRequesterBirthPlace) {
        domesticHelpRequestData.setDhrRequesterBirthPlace(dhrRequesterBirthPlace);
    }

    
    public final String getDhrRequesterBirthPlace() {
        return domesticHelpRequestData.getDhrRequesterBirthPlace();
    }
  
    public final void setDhrRequesterFranceArrivalDate(final java.util.Date dhrRequesterFranceArrivalDate) {
        domesticHelpRequestData.setDhrRequesterFranceArrivalDate(dhrRequesterFranceArrivalDate);
    }

    
    public final java.util.Date getDhrRequesterFranceArrivalDate() {
        return domesticHelpRequestData.getDhrRequesterFranceArrivalDate();
    }
  
    public final void setDhrRequesterHaveGuardian(final Boolean dhrRequesterHaveGuardian) {
        domesticHelpRequestData.setDhrRequesterHaveGuardian(dhrRequesterHaveGuardian);
    }

    
    public final Boolean getDhrRequesterHaveGuardian() {
        return domesticHelpRequestData.getDhrRequesterHaveGuardian();
    }
  
    public final void setDhrRequesterIsFrenchResident(final Boolean dhrRequesterIsFrenchResident) {
        domesticHelpRequestData.setDhrRequesterIsFrenchResident(dhrRequesterIsFrenchResident);
    }

    
    public final Boolean getDhrRequesterIsFrenchResident() {
        return domesticHelpRequestData.getDhrRequesterIsFrenchResident();
    }
  
    public final void setDhrRequesterNationality(final org.libredemat.business.users.NationalityType dhrRequesterNationality) {
        domesticHelpRequestData.setDhrRequesterNationality(dhrRequesterNationality);
    }

    
    public final org.libredemat.business.users.NationalityType getDhrRequesterNationality() {
        return domesticHelpRequestData.getDhrRequesterNationality();
    }
  
    public final void setDhrSpouseAddress(final org.libredemat.business.users.Address dhrSpouseAddress) {
        domesticHelpRequestData.setDhrSpouseAddress(dhrSpouseAddress);
    }

    
    public final org.libredemat.business.users.Address getDhrSpouseAddress() {
        return domesticHelpRequestData.getDhrSpouseAddress();
    }
  
    public final void setDhrSpouseBirthDate(final java.util.Date dhrSpouseBirthDate) {
        domesticHelpRequestData.setDhrSpouseBirthDate(dhrSpouseBirthDate);
    }

    
    public final java.util.Date getDhrSpouseBirthDate() {
        return domesticHelpRequestData.getDhrSpouseBirthDate();
    }
  
    public final void setDhrSpouseBirthPlace(final String dhrSpouseBirthPlace) {
        domesticHelpRequestData.setDhrSpouseBirthPlace(dhrSpouseBirthPlace);
    }

    
    public final String getDhrSpouseBirthPlace() {
        return domesticHelpRequestData.getDhrSpouseBirthPlace();
    }
  
    public final void setDhrSpouseComplementaryPensionPlan(final String dhrSpouseComplementaryPensionPlan) {
        domesticHelpRequestData.setDhrSpouseComplementaryPensionPlan(dhrSpouseComplementaryPensionPlan);
    }

    
    public final String getDhrSpouseComplementaryPensionPlan() {
        return domesticHelpRequestData.getDhrSpouseComplementaryPensionPlan();
    }
  
    public final void setDhrSpouseEmployer(final String dhrSpouseEmployer) {
        domesticHelpRequestData.setDhrSpouseEmployer(dhrSpouseEmployer);
    }

    
    public final String getDhrSpouseEmployer() {
        return domesticHelpRequestData.getDhrSpouseEmployer();
    }
  
    public final void setDhrSpouseFamilyStatus(final org.libredemat.business.users.FamilyStatusType dhrSpouseFamilyStatus) {
        domesticHelpRequestData.setDhrSpouseFamilyStatus(dhrSpouseFamilyStatus);
    }

    
    public final org.libredemat.business.users.FamilyStatusType getDhrSpouseFamilyStatus() {
        return domesticHelpRequestData.getDhrSpouseFamilyStatus();
    }
  
    public final void setDhrSpouseFirstName(final String dhrSpouseFirstName) {
        domesticHelpRequestData.setDhrSpouseFirstName(dhrSpouseFirstName);
    }

    
    public final String getDhrSpouseFirstName() {
        return domesticHelpRequestData.getDhrSpouseFirstName();
    }
  
    public final void setDhrSpouseFranceArrivalDate(final java.util.Date dhrSpouseFranceArrivalDate) {
        domesticHelpRequestData.setDhrSpouseFranceArrivalDate(dhrSpouseFranceArrivalDate);
    }

    
    public final java.util.Date getDhrSpouseFranceArrivalDate() {
        return domesticHelpRequestData.getDhrSpouseFranceArrivalDate();
    }
  
    public final void setDhrSpouseIsFrenchResident(final Boolean dhrSpouseIsFrenchResident) {
        domesticHelpRequestData.setDhrSpouseIsFrenchResident(dhrSpouseIsFrenchResident);
    }

    
    public final Boolean getDhrSpouseIsFrenchResident() {
        return domesticHelpRequestData.getDhrSpouseIsFrenchResident();
    }
  
    public final void setDhrSpouseMaidenName(final String dhrSpouseMaidenName) {
        domesticHelpRequestData.setDhrSpouseMaidenName(dhrSpouseMaidenName);
    }

    
    public final String getDhrSpouseMaidenName() {
        return domesticHelpRequestData.getDhrSpouseMaidenName();
    }
  
    public final void setDhrSpouseName(final String dhrSpouseName) {
        domesticHelpRequestData.setDhrSpouseName(dhrSpouseName);
    }

    
    public final String getDhrSpouseName() {
        return domesticHelpRequestData.getDhrSpouseName();
    }
  
    public final void setDhrSpouseNationality(final org.libredemat.business.users.NationalityType dhrSpouseNationality) {
        domesticHelpRequestData.setDhrSpouseNationality(dhrSpouseNationality);
    }

    
    public final org.libredemat.business.users.NationalityType getDhrSpouseNationality() {
        return domesticHelpRequestData.getDhrSpouseNationality();
    }
  
    public final void setDhrSpousePensionPlanDetail(final String dhrSpousePensionPlanDetail) {
        domesticHelpRequestData.setDhrSpousePensionPlanDetail(dhrSpousePensionPlanDetail);
    }

    
    public final String getDhrSpousePensionPlanDetail() {
        return domesticHelpRequestData.getDhrSpousePensionPlanDetail();
    }
  
    public final void setDhrSpousePrincipalPensionPlan(final org.libredemat.business.request.social.DhrPrincipalPensionPlanType dhrSpousePrincipalPensionPlan) {
        domesticHelpRequestData.setDhrSpousePrincipalPensionPlan(dhrSpousePrincipalPensionPlan);
    }

    
    public final org.libredemat.business.request.social.DhrPrincipalPensionPlanType getDhrSpousePrincipalPensionPlan() {
        return domesticHelpRequestData.getDhrSpousePrincipalPensionPlan();
    }
  
    public final void setDhrSpouseProfession(final String dhrSpouseProfession) {
        domesticHelpRequestData.setDhrSpouseProfession(dhrSpouseProfession);
    }

    
    public final String getDhrSpouseProfession() {
        return domesticHelpRequestData.getDhrSpouseProfession();
    }
  
    public final void setDhrSpouseTitle(final org.libredemat.business.users.TitleType dhrSpouseTitle) {
        domesticHelpRequestData.setDhrSpouseTitle(dhrSpouseTitle);
    }

    
    public final org.libredemat.business.users.TitleType getDhrSpouseTitle() {
        return domesticHelpRequestData.getDhrSpouseTitle();
    }
  
    public final void setLocalRate(final java.math.BigInteger localRate) {
        domesticHelpRequestData.setLocalRate(localRate);
    }

    
    public final java.math.BigInteger getLocalRate() {
        return domesticHelpRequestData.getLocalRate();
    }
  
    public final void setPensions(final java.math.BigInteger pensions) {
        domesticHelpRequestData.setPensions(pensions);
    }

    
    public final java.math.BigInteger getPensions() {
        return domesticHelpRequestData.getPensions();
    }
  
    public final void setProfessionalTaxes(final java.math.BigInteger professionalTaxes) {
        domesticHelpRequestData.setProfessionalTaxes(professionalTaxes);
    }

    
    public final java.math.BigInteger getProfessionalTaxes() {
        return domesticHelpRequestData.getProfessionalTaxes();
    }
  
    public final void setPropertyTaxes(final java.math.BigInteger propertyTaxes) {
        domesticHelpRequestData.setPropertyTaxes(propertyTaxes);
    }

    
    public final java.math.BigInteger getPropertyTaxes() {
        return domesticHelpRequestData.getPropertyTaxes();
    }
  
}
