
package org.libredemat.business.request.permit;

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
import org.libredemat.business.payment.*;
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.permit.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class ParkingPermitTemporaryWorkRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = ParkingPermitTemporaryWorkRequestData.conditions;

    @AssertValid(message = "")
    private ParkingPermitTemporaryWorkRequestData parkingPermitTemporaryWorkRequestData;

    public ParkingPermitTemporaryWorkRequest(RequestData requestData, ParkingPermitTemporaryWorkRequestData parkingPermitTemporaryWorkRequestData) {
        super(requestData);
        this.parkingPermitTemporaryWorkRequestData = parkingPermitTemporaryWorkRequestData;
    }

    public ParkingPermitTemporaryWorkRequest() {
        super();
        this.parkingPermitTemporaryWorkRequestData = new ParkingPermitTemporaryWorkRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("work", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("reglements", stepState);
        
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
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("paiement", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public ParkingPermitTemporaryWorkRequestData getSpecificData() {
        return parkingPermitTemporaryWorkRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(ParkingPermitTemporaryWorkRequestData parkingPermitTemporaryWorkRequestData) {
        this.parkingPermitTemporaryWorkRequestData = parkingPermitTemporaryWorkRequestData;
    }

    @Override
    public final String modelToXmlString() {
        ParkingPermitTemporaryWorkRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final ParkingPermitTemporaryWorkRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        ParkingPermitTemporaryWorkRequestDocument parkingPermitTemporaryWorkRequestDoc = ParkingPermitTemporaryWorkRequestDocument.Factory.newInstance();
        ParkingPermitTemporaryWorkRequestDocument.ParkingPermitTemporaryWorkRequest parkingPermitTemporaryWorkRequest = parkingPermitTemporaryWorkRequestDoc.addNewParkingPermitTemporaryWorkRequest();
        super.fillCommonXmlInfo(parkingPermitTemporaryWorkRequest);
        int i = 0;
        
        if (getAcceptationReglementInterieur() != null)
            parkingPermitTemporaryWorkRequest.setAcceptationReglementInterieur(getAcceptationReglementInterieur().booleanValue());
        PptwrCompanyInformationType pptwrCompanyInformationTypeCompanyInformation = parkingPermitTemporaryWorkRequest.addNewCompanyInformation();
        pptwrCompanyInformationTypeCompanyInformation.setApeCode(getApeCode());
        ParkingPermitForWorkInformationType parkingPermitForWorkInformationTypeParkingPermitForWorkInformation = parkingPermitTemporaryWorkRequest.addNewParkingPermitForWorkInformation();
        parkingPermitForWorkInformationTypeParkingPermitForWorkInformation.setConstructLicenseNumber(getConstructLicenseNumber());
      
        i = 0;
        if (getDesiredService() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] desiredServiceTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getDesiredService().size()];
            for (LocalReferentialData object : getDesiredService()) {
              desiredServiceTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            parkingPermitTemporaryWorkRequest.setDesiredServiceArray(desiredServiceTypeTab);
        }
      
        if (getIsCompany() != null)
            parkingPermitTemporaryWorkRequest.setIsCompany(getIsCompany().booleanValue());
      
        parkingPermitTemporaryWorkRequest.setObservations(getObservations());
      
        parkingPermitTemporaryWorkRequest.setObservationsReglement(getObservationsReglement());
        VehicleParkingOrFloorOccupationInformationType vehicleParkingOrFloorOccupationInformationTypeVehicleParkingOrFloorOccupationInformation = parkingPermitTemporaryWorkRequest.addNewVehicleParkingOrFloorOccupationInformation();
        if (getOccupation() != null)
            vehicleParkingOrFloorOccupationInformationTypeVehicleParkingOrFloorOccupationInformation.setOccupation(getOccupation().doubleValue());
      
        date = getOccupationEndDate();
        if (date != null) {
            calendar.setTime(date);
            vehicleParkingOrFloorOccupationInformationTypeVehicleParkingOrFloorOccupationInformation.setOccupationEndDate(calendar);
        }
      
        vehicleParkingOrFloorOccupationInformationTypeVehicleParkingOrFloorOccupationInformation.setOccupationOtherAddress(getOccupationOtherAddress());
      
        date = getOccupationStartDate();
        if (date != null) {
            calendar.setTime(date);
            vehicleParkingOrFloorOccupationInformationTypeVehicleParkingOrFloorOccupationInformation.setOccupationStartDate(calendar);
        }
      
        if (getPayment() != null)
            parkingPermitTemporaryWorkRequest.setPayment(Payment.modelToXml(getPayment()));
      
        parkingPermitTemporaryWorkRequest.setPaymentIndicativeAmount(getPaymentIndicativeAmount());
        ExistingLicenseExtensionInformationType existingLicenseExtensionInformationTypeExistingLicenseExtensionInformation = parkingPermitTemporaryWorkRequest.addNewExistingLicenseExtensionInformation();
        existingLicenseExtensionInformationTypeExistingLicenseExtensionInformation.setReferenceNumber(getReferenceNumber());
      
        if (getScaffolding() != null)
            parkingPermitTemporaryWorkRequest.setScaffolding(getScaffolding().booleanValue());
        ScaffoldingInformationType scaffoldingInformationTypeScaffoldingInformation = parkingPermitTemporaryWorkRequest.addNewScaffoldingInformation();
        date = getScaffoldingEndDate();
        if (date != null) {
            calendar.setTime(date);
            scaffoldingInformationTypeScaffoldingInformation.setScaffoldingEndDate(calendar);
        }
      
        if (getScaffoldingLength() != null)
            scaffoldingInformationTypeScaffoldingInformation.setScaffoldingLength(getScaffoldingLength().doubleValue());
      
        date = getScaffoldingStartDate();
        if (date != null) {
            calendar.setTime(date);
            scaffoldingInformationTypeScaffoldingInformation.setScaffoldingStartDate(calendar);
        }
      
        pptwrCompanyInformationTypeCompanyInformation.setSiretNumber(getSiretNumber());
      
        parkingPermitForWorkInformationTypeParkingPermitForWorkInformation.setSiteAddress(getSiteAddress());
      
        parkingPermitForWorkInformationTypeParkingPermitForWorkInformation.setUsedVehicles(getUsedVehicles());
      
        if (getVehicleParkingOrFloorOccupation() != null)
            parkingPermitTemporaryWorkRequest.setVehicleParkingOrFloorOccupation(getVehicleParkingOrFloorOccupation().booleanValue());
      
        parkingPermitForWorkInformationTypeParkingPermitForWorkInformation.setWorkNature(getWorkNature());
      
        if (getWorkOnBuilding() != null)
            parkingPermitForWorkInformationTypeParkingPermitForWorkInformation.setWorkOnBuilding(getWorkOnBuilding().booleanValue());
      
        return parkingPermitTemporaryWorkRequestDoc;
    }

    @Override
    public final ParkingPermitTemporaryWorkRequestDocument.ParkingPermitTemporaryWorkRequest modelToXmlRequest() {
        return modelToXml().getParkingPermitTemporaryWorkRequest();
    }

    public static ParkingPermitTemporaryWorkRequest xmlToModel(ParkingPermitTemporaryWorkRequestDocument parkingPermitTemporaryWorkRequestDoc) {
        ParkingPermitTemporaryWorkRequestDocument.ParkingPermitTemporaryWorkRequest parkingPermitTemporaryWorkRequestXml = parkingPermitTemporaryWorkRequestDoc.getParkingPermitTemporaryWorkRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ParkingPermitTemporaryWorkRequest parkingPermitTemporaryWorkRequest = new ParkingPermitTemporaryWorkRequest();
        parkingPermitTemporaryWorkRequest.fillCommonModelInfo(parkingPermitTemporaryWorkRequest, parkingPermitTemporaryWorkRequestXml);
        
        parkingPermitTemporaryWorkRequest.setAcceptationReglementInterieur(Boolean.valueOf(parkingPermitTemporaryWorkRequestXml.getAcceptationReglementInterieur()));
      
        parkingPermitTemporaryWorkRequest.setApeCode(parkingPermitTemporaryWorkRequestXml.getCompanyInformation().getApeCode());
      
        parkingPermitTemporaryWorkRequest.setConstructLicenseNumber(parkingPermitTemporaryWorkRequestXml.getParkingPermitForWorkInformation().getConstructLicenseNumber());
      
        List<org.libredemat.business.request.LocalReferentialData> desiredServiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(parkingPermitTemporaryWorkRequestXml.sizeOfDesiredServiceArray());
        for (LocalReferentialDataType object : parkingPermitTemporaryWorkRequestXml.getDesiredServiceArray()) {
            desiredServiceList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        parkingPermitTemporaryWorkRequest.setDesiredService(desiredServiceList);
      
        parkingPermitTemporaryWorkRequest.setIsCompany(Boolean.valueOf(parkingPermitTemporaryWorkRequestXml.getIsCompany()));
      
        parkingPermitTemporaryWorkRequest.setObservations(parkingPermitTemporaryWorkRequestXml.getObservations());
      
        parkingPermitTemporaryWorkRequest.setObservationsReglement(parkingPermitTemporaryWorkRequestXml.getObservationsReglement());
      
        parkingPermitTemporaryWorkRequest.setOccupation(new Double(parkingPermitTemporaryWorkRequestXml.getVehicleParkingOrFloorOccupationInformation().getOccupation()));
      
        calendar = parkingPermitTemporaryWorkRequestXml.getVehicleParkingOrFloorOccupationInformation().getOccupationEndDate();
        if (calendar != null) {
            parkingPermitTemporaryWorkRequest.setOccupationEndDate(calendar.getTime());
        }
      
        parkingPermitTemporaryWorkRequest.setOccupationOtherAddress(parkingPermitTemporaryWorkRequestXml.getVehicleParkingOrFloorOccupationInformation().getOccupationOtherAddress());
      
        calendar = parkingPermitTemporaryWorkRequestXml.getVehicleParkingOrFloorOccupationInformation().getOccupationStartDate();
        if (calendar != null) {
            parkingPermitTemporaryWorkRequest.setOccupationStartDate(calendar.getTime());
        }
      
        if (parkingPermitTemporaryWorkRequestXml.getPayment() != null)
            parkingPermitTemporaryWorkRequest.setPayment(Payment.xmlToModel(parkingPermitTemporaryWorkRequestXml.getPayment()));
      
        parkingPermitTemporaryWorkRequest.setPaymentIndicativeAmount(parkingPermitTemporaryWorkRequestXml.getPaymentIndicativeAmount());
      
        parkingPermitTemporaryWorkRequest.setReferenceNumber(parkingPermitTemporaryWorkRequestXml.getExistingLicenseExtensionInformation().getReferenceNumber());
      
        parkingPermitTemporaryWorkRequest.setScaffolding(Boolean.valueOf(parkingPermitTemporaryWorkRequestXml.getScaffolding()));
      
        calendar = parkingPermitTemporaryWorkRequestXml.getScaffoldingInformation().getScaffoldingEndDate();
        if (calendar != null) {
            parkingPermitTemporaryWorkRequest.setScaffoldingEndDate(calendar.getTime());
        }
      
        parkingPermitTemporaryWorkRequest.setScaffoldingLength(new Double(parkingPermitTemporaryWorkRequestXml.getScaffoldingInformation().getScaffoldingLength()));
      
        calendar = parkingPermitTemporaryWorkRequestXml.getScaffoldingInformation().getScaffoldingStartDate();
        if (calendar != null) {
            parkingPermitTemporaryWorkRequest.setScaffoldingStartDate(calendar.getTime());
        }
      
        parkingPermitTemporaryWorkRequest.setSiretNumber(parkingPermitTemporaryWorkRequestXml.getCompanyInformation().getSiretNumber());
      
        parkingPermitTemporaryWorkRequest.setSiteAddress(parkingPermitTemporaryWorkRequestXml.getParkingPermitForWorkInformation().getSiteAddress());
      
        parkingPermitTemporaryWorkRequest.setUsedVehicles(parkingPermitTemporaryWorkRequestXml.getParkingPermitForWorkInformation().getUsedVehicles());
      
        parkingPermitTemporaryWorkRequest.setVehicleParkingOrFloorOccupation(Boolean.valueOf(parkingPermitTemporaryWorkRequestXml.getVehicleParkingOrFloorOccupation()));
      
        parkingPermitTemporaryWorkRequest.setWorkNature(parkingPermitTemporaryWorkRequestXml.getParkingPermitForWorkInformation().getWorkNature());
      
        parkingPermitTemporaryWorkRequest.setWorkOnBuilding(Boolean.valueOf(parkingPermitTemporaryWorkRequestXml.getParkingPermitForWorkInformation().getWorkOnBuilding()));
      
        return parkingPermitTemporaryWorkRequest;
    }

    @Override
    public ParkingPermitTemporaryWorkRequest clone() {
        ParkingPermitTemporaryWorkRequest clone = new ParkingPermitTemporaryWorkRequest(getRequestData().clone(), parkingPermitTemporaryWorkRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("work", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("reglements", stepState);
        
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
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("paiement", stepState);
        
        return clone;
    }

  
    public final void setAcceptationReglementInterieur(final Boolean acceptationReglementInterieur) {
        parkingPermitTemporaryWorkRequestData.setAcceptationReglementInterieur(acceptationReglementInterieur);
    }

    @IsRulesAcceptance
    public final Boolean getAcceptationReglementInterieur() {
        return parkingPermitTemporaryWorkRequestData.getAcceptationReglementInterieur();
    }
  
    public final void setApeCode(final String apeCode) {
        parkingPermitTemporaryWorkRequestData.setApeCode(apeCode);
    }

    
    public final String getApeCode() {
        return parkingPermitTemporaryWorkRequestData.getApeCode();
    }
  
    public final void setConstructLicenseNumber(final String constructLicenseNumber) {
        parkingPermitTemporaryWorkRequestData.setConstructLicenseNumber(constructLicenseNumber);
    }

    
    public final String getConstructLicenseNumber() {
        return parkingPermitTemporaryWorkRequestData.getConstructLicenseNumber();
    }
  
    public final void setDesiredService(final List<org.libredemat.business.request.LocalReferentialData> desiredService) {
        parkingPermitTemporaryWorkRequestData.setDesiredService(desiredService);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getDesiredService() {
        return parkingPermitTemporaryWorkRequestData.getDesiredService();
    }
  
    public final void setIsCompany(final Boolean isCompany) {
        parkingPermitTemporaryWorkRequestData.setIsCompany(isCompany);
    }

    
    public final Boolean getIsCompany() {
        return parkingPermitTemporaryWorkRequestData.getIsCompany();
    }
  
    public final void setObservations(final String observations) {
        parkingPermitTemporaryWorkRequestData.setObservations(observations);
    }

    
    public final String getObservations() {
        return parkingPermitTemporaryWorkRequestData.getObservations();
    }
  
    public final void setObservationsReglement(final String observationsReglement) {
        parkingPermitTemporaryWorkRequestData.setObservationsReglement(observationsReglement);
    }

    
    public final String getObservationsReglement() {
        return parkingPermitTemporaryWorkRequestData.getObservationsReglement();
    }
  
    public final void setOccupation(final Double occupation) {
        parkingPermitTemporaryWorkRequestData.setOccupation(occupation);
    }

    
    public final Double getOccupation() {
        return parkingPermitTemporaryWorkRequestData.getOccupation();
    }
  
    public final void setOccupationEndDate(final java.util.Date occupationEndDate) {
        parkingPermitTemporaryWorkRequestData.setOccupationEndDate(occupationEndDate);
    }

    
    public final java.util.Date getOccupationEndDate() {
        return parkingPermitTemporaryWorkRequestData.getOccupationEndDate();
    }
  
    public final void setOccupationOtherAddress(final String occupationOtherAddress) {
        parkingPermitTemporaryWorkRequestData.setOccupationOtherAddress(occupationOtherAddress);
    }

    
    public final String getOccupationOtherAddress() {
        return parkingPermitTemporaryWorkRequestData.getOccupationOtherAddress();
    }
  
    public final void setOccupationStartDate(final java.util.Date occupationStartDate) {
        parkingPermitTemporaryWorkRequestData.setOccupationStartDate(occupationStartDate);
    }

    
    public final java.util.Date getOccupationStartDate() {
        return parkingPermitTemporaryWorkRequestData.getOccupationStartDate();
    }
  
    public final void setPayment(final org.libredemat.business.payment.Payment payment) {
        parkingPermitTemporaryWorkRequestData.setPayment(payment);
    }

    
    public final org.libredemat.business.payment.Payment getPayment() {
        return parkingPermitTemporaryWorkRequestData.getPayment();
    }
  
    public final void setPaymentIndicativeAmount(final String paymentIndicativeAmount) {
        parkingPermitTemporaryWorkRequestData.setPaymentIndicativeAmount(paymentIndicativeAmount);
    }

    
    public final String getPaymentIndicativeAmount() {
        return parkingPermitTemporaryWorkRequestData.getPaymentIndicativeAmount();
    }
  
    public final void setReferenceNumber(final String referenceNumber) {
        parkingPermitTemporaryWorkRequestData.setReferenceNumber(referenceNumber);
    }

    
    public final String getReferenceNumber() {
        return parkingPermitTemporaryWorkRequestData.getReferenceNumber();
    }
  
    public final void setScaffolding(final Boolean scaffolding) {
        parkingPermitTemporaryWorkRequestData.setScaffolding(scaffolding);
    }

    
    public final Boolean getScaffolding() {
        return parkingPermitTemporaryWorkRequestData.getScaffolding();
    }
  
    public final void setScaffoldingEndDate(final java.util.Date scaffoldingEndDate) {
        parkingPermitTemporaryWorkRequestData.setScaffoldingEndDate(scaffoldingEndDate);
    }

    
    public final java.util.Date getScaffoldingEndDate() {
        return parkingPermitTemporaryWorkRequestData.getScaffoldingEndDate();
    }
  
    public final void setScaffoldingLength(final Double scaffoldingLength) {
        parkingPermitTemporaryWorkRequestData.setScaffoldingLength(scaffoldingLength);
    }

    
    public final Double getScaffoldingLength() {
        return parkingPermitTemporaryWorkRequestData.getScaffoldingLength();
    }
  
    public final void setScaffoldingStartDate(final java.util.Date scaffoldingStartDate) {
        parkingPermitTemporaryWorkRequestData.setScaffoldingStartDate(scaffoldingStartDate);
    }

    
    public final java.util.Date getScaffoldingStartDate() {
        return parkingPermitTemporaryWorkRequestData.getScaffoldingStartDate();
    }
  
    public final void setSiretNumber(final String siretNumber) {
        parkingPermitTemporaryWorkRequestData.setSiretNumber(siretNumber);
    }

    
    public final String getSiretNumber() {
        return parkingPermitTemporaryWorkRequestData.getSiretNumber();
    }
  
    public final void setSiteAddress(final String siteAddress) {
        parkingPermitTemporaryWorkRequestData.setSiteAddress(siteAddress);
    }

    
    public final String getSiteAddress() {
        return parkingPermitTemporaryWorkRequestData.getSiteAddress();
    }
  
    public final void setUsedVehicles(final String usedVehicles) {
        parkingPermitTemporaryWorkRequestData.setUsedVehicles(usedVehicles);
    }

    
    public final String getUsedVehicles() {
        return parkingPermitTemporaryWorkRequestData.getUsedVehicles();
    }
  
    public final void setVehicleParkingOrFloorOccupation(final Boolean vehicleParkingOrFloorOccupation) {
        parkingPermitTemporaryWorkRequestData.setVehicleParkingOrFloorOccupation(vehicleParkingOrFloorOccupation);
    }

    
    public final Boolean getVehicleParkingOrFloorOccupation() {
        return parkingPermitTemporaryWorkRequestData.getVehicleParkingOrFloorOccupation();
    }
  
    public final void setWorkNature(final String workNature) {
        parkingPermitTemporaryWorkRequestData.setWorkNature(workNature);
    }

    
    public final String getWorkNature() {
        return parkingPermitTemporaryWorkRequestData.getWorkNature();
    }
  
    public final void setWorkOnBuilding(final Boolean workOnBuilding) {
        parkingPermitTemporaryWorkRequestData.setWorkOnBuilding(workOnBuilding);
    }

    
    public final Boolean getWorkOnBuilding() {
        return parkingPermitTemporaryWorkRequestData.getWorkOnBuilding();
    }
  
}
