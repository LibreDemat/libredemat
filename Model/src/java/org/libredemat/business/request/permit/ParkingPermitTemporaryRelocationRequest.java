
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
public class ParkingPermitTemporaryRelocationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = ParkingPermitTemporaryRelocationRequestData.conditions;

    @AssertValid(message = "")
    private ParkingPermitTemporaryRelocationRequestData parkingPermitTemporaryRelocationRequestData;

    public ParkingPermitTemporaryRelocationRequest(RequestData requestData, ParkingPermitTemporaryRelocationRequestData parkingPermitTemporaryRelocationRequestData) {
        super(requestData);
        this.parkingPermitTemporaryRelocationRequestData = parkingPermitTemporaryRelocationRequestData;
    }

    public ParkingPermitTemporaryRelocationRequest() {
        super();
        this.parkingPermitTemporaryRelocationRequestData = new ParkingPermitTemporaryRelocationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("relocation", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("reglements", stepState);
        
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
    public ParkingPermitTemporaryRelocationRequestData getSpecificData() {
        return parkingPermitTemporaryRelocationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(ParkingPermitTemporaryRelocationRequestData parkingPermitTemporaryRelocationRequestData) {
        this.parkingPermitTemporaryRelocationRequestData = parkingPermitTemporaryRelocationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        ParkingPermitTemporaryRelocationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final ParkingPermitTemporaryRelocationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        ParkingPermitTemporaryRelocationRequestDocument parkingPermitTemporaryRelocationRequestDoc = ParkingPermitTemporaryRelocationRequestDocument.Factory.newInstance();
        ParkingPermitTemporaryRelocationRequestDocument.ParkingPermitTemporaryRelocationRequest parkingPermitTemporaryRelocationRequest = parkingPermitTemporaryRelocationRequestDoc.addNewParkingPermitTemporaryRelocationRequest();
        super.fillCommonXmlInfo(parkingPermitTemporaryRelocationRequest);
        int i = 0;
        
        if (getAcceptationReglementInterieur() != null)
            parkingPermitTemporaryRelocationRequest.setAcceptationReglementInterieur(getAcceptationReglementInterieur().booleanValue());
        PptrrCompanyInformationType pptrrCompanyInformationTypeCompanyInformation = parkingPermitTemporaryRelocationRequest.addNewCompanyInformation();
        pptrrCompanyInformationTypeCompanyInformation.setApeCode(getApeCode());
      
        i = 0;
        if (getDesiredService() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] desiredServiceTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getDesiredService().size()];
            for (LocalReferentialData object : getDesiredService()) {
              desiredServiceTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            parkingPermitTemporaryRelocationRequest.setDesiredServiceArray(desiredServiceTypeTab);
        }
        PptrrEquipementUsedType pptrrEquipementUsedTypeEquipmentUsed = parkingPermitTemporaryRelocationRequest.addNewEquipmentUsed();
        if (getFurnitureLifting() != null)
            pptrrEquipementUsedTypeEquipmentUsed.setFurnitureLifting(getFurnitureLifting().booleanValue());
      
        if (getHeureEnd() != null)
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.xml.request.permit.HeuresType.Enum.forString(getHeureEnd().getLegacyLabel()));
      
        if (getHeureStart() != null)
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.xml.request.permit.HeuresType.Enum.forString(getHeureStart().getLegacyLabel()));
      
        pptrrEquipementUsedTypeEquipmentUsed.setImmatriculation(getImmatriculation());
      
        if (getIsCompany() != null)
            parkingPermitTemporaryRelocationRequest.setIsCompany(getIsCompany().booleanValue());
      
        if (getLongeur() != null)
            pptrrEquipementUsedTypeEquipmentUsed.setLongeur(new BigInteger(getLongeur().toString()));
      
        parkingPermitTemporaryRelocationRequest.setObservations(getObservations());
      
        parkingPermitTemporaryRelocationRequest.setObservationsReglement(getObservationsReglement());
      
        pptrrEquipementUsedTypeEquipmentUsed.setOther(getOther());
      
        if (getPayment() != null)
            parkingPermitTemporaryRelocationRequest.setPayment(Payment.modelToXml(getPayment()));
      
        date = getPeriodeEnd();
        if (date != null) {
            calendar.setTime(date);
            parkingPermitTemporaryRelocationRequest.setPeriodeEnd(calendar);
        }
      
        date = getPeriodeStart();
        if (date != null) {
            calendar.setTime(date);
            parkingPermitTemporaryRelocationRequest.setPeriodeStart(calendar);
        }
      
        parkingPermitTemporaryRelocationRequest.setRequesterAddress(getRequesterAddress());
      
        pptrrCompanyInformationTypeCompanyInformation.setSiretNumber(getSiretNumber());
      
        pptrrEquipementUsedTypeEquipmentUsed.setVehicleType(getVehicleType());
      
        return parkingPermitTemporaryRelocationRequestDoc;
    }

    @Override
    public final ParkingPermitTemporaryRelocationRequestDocument.ParkingPermitTemporaryRelocationRequest modelToXmlRequest() {
        return modelToXml().getParkingPermitTemporaryRelocationRequest();
    }

    public static ParkingPermitTemporaryRelocationRequest xmlToModel(ParkingPermitTemporaryRelocationRequestDocument parkingPermitTemporaryRelocationRequestDoc) {
        ParkingPermitTemporaryRelocationRequestDocument.ParkingPermitTemporaryRelocationRequest parkingPermitTemporaryRelocationRequestXml = parkingPermitTemporaryRelocationRequestDoc.getParkingPermitTemporaryRelocationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ParkingPermitTemporaryRelocationRequest parkingPermitTemporaryRelocationRequest = new ParkingPermitTemporaryRelocationRequest();
        parkingPermitTemporaryRelocationRequest.fillCommonModelInfo(parkingPermitTemporaryRelocationRequest, parkingPermitTemporaryRelocationRequestXml);
        
        parkingPermitTemporaryRelocationRequest.setAcceptationReglementInterieur(Boolean.valueOf(parkingPermitTemporaryRelocationRequestXml.getAcceptationReglementInterieur()));
      
        parkingPermitTemporaryRelocationRequest.setApeCode(parkingPermitTemporaryRelocationRequestXml.getCompanyInformation().getApeCode());
      
        List<org.libredemat.business.request.LocalReferentialData> desiredServiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(parkingPermitTemporaryRelocationRequestXml.sizeOfDesiredServiceArray());
        for (LocalReferentialDataType object : parkingPermitTemporaryRelocationRequestXml.getDesiredServiceArray()) {
            desiredServiceList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        parkingPermitTemporaryRelocationRequest.setDesiredService(desiredServiceList);
      
        parkingPermitTemporaryRelocationRequest.setFurnitureLifting(Boolean.valueOf(parkingPermitTemporaryRelocationRequestXml.getEquipmentUsed().getFurnitureLifting()));
      
        if (parkingPermitTemporaryRelocationRequestXml.getHeureEnd() != null)
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.business.request.permit.HeuresType.forString(parkingPermitTemporaryRelocationRequestXml.getHeureEnd().toString()));
        else
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
        if (parkingPermitTemporaryRelocationRequestXml.getHeureStart() != null)
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.business.request.permit.HeuresType.forString(parkingPermitTemporaryRelocationRequestXml.getHeureStart().toString()));
        else
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
        parkingPermitTemporaryRelocationRequest.setImmatriculation(parkingPermitTemporaryRelocationRequestXml.getEquipmentUsed().getImmatriculation());
      
        parkingPermitTemporaryRelocationRequest.setIsCompany(Boolean.valueOf(parkingPermitTemporaryRelocationRequestXml.getIsCompany()));
      
        parkingPermitTemporaryRelocationRequest.setLongeur(parkingPermitTemporaryRelocationRequestXml.getEquipmentUsed().getLongeur());
      
        parkingPermitTemporaryRelocationRequest.setObservations(parkingPermitTemporaryRelocationRequestXml.getObservations());
      
        parkingPermitTemporaryRelocationRequest.setObservationsReglement(parkingPermitTemporaryRelocationRequestXml.getObservationsReglement());
      
        parkingPermitTemporaryRelocationRequest.setOther(parkingPermitTemporaryRelocationRequestXml.getEquipmentUsed().getOther());
      
        if (parkingPermitTemporaryRelocationRequestXml.getPayment() != null)
            parkingPermitTemporaryRelocationRequest.setPayment(Payment.xmlToModel(parkingPermitTemporaryRelocationRequestXml.getPayment()));
      
        calendar = parkingPermitTemporaryRelocationRequestXml.getPeriodeEnd();
        if (calendar != null) {
            parkingPermitTemporaryRelocationRequest.setPeriodeEnd(calendar.getTime());
        }
      
        calendar = parkingPermitTemporaryRelocationRequestXml.getPeriodeStart();
        if (calendar != null) {
            parkingPermitTemporaryRelocationRequest.setPeriodeStart(calendar.getTime());
        }
      
        parkingPermitTemporaryRelocationRequest.setRequesterAddress(parkingPermitTemporaryRelocationRequestXml.getRequesterAddress());
      
        parkingPermitTemporaryRelocationRequest.setSiretNumber(parkingPermitTemporaryRelocationRequestXml.getCompanyInformation().getSiretNumber());
      
        parkingPermitTemporaryRelocationRequest.setVehicleType(parkingPermitTemporaryRelocationRequestXml.getEquipmentUsed().getVehicleType());
      
        return parkingPermitTemporaryRelocationRequest;
    }

    @Override
    public ParkingPermitTemporaryRelocationRequest clone() {
        ParkingPermitTemporaryRelocationRequest clone = new ParkingPermitTemporaryRelocationRequest(getRequestData().clone(), parkingPermitTemporaryRelocationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("relocation", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("reglements", stepState);
        
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
        parkingPermitTemporaryRelocationRequestData.setAcceptationReglementInterieur(acceptationReglementInterieur);
    }

    @IsRulesAcceptance
    public final Boolean getAcceptationReglementInterieur() {
        return parkingPermitTemporaryRelocationRequestData.getAcceptationReglementInterieur();
    }
  
    public final void setApeCode(final String apeCode) {
        parkingPermitTemporaryRelocationRequestData.setApeCode(apeCode);
    }

    
    public final String getApeCode() {
        return parkingPermitTemporaryRelocationRequestData.getApeCode();
    }
  
    public final void setDesiredService(final List<org.libredemat.business.request.LocalReferentialData> desiredService) {
        parkingPermitTemporaryRelocationRequestData.setDesiredService(desiredService);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getDesiredService() {
        return parkingPermitTemporaryRelocationRequestData.getDesiredService();
    }
  
    public final void setFurnitureLifting(final Boolean furnitureLifting) {
        parkingPermitTemporaryRelocationRequestData.setFurnitureLifting(furnitureLifting);
    }

    
    public final Boolean getFurnitureLifting() {
        return parkingPermitTemporaryRelocationRequestData.getFurnitureLifting();
    }
  
    public final void setHeureEnd(final org.libredemat.business.request.permit.HeuresType heureEnd) {
        parkingPermitTemporaryRelocationRequestData.setHeureEnd(heureEnd);
    }

    
    public final org.libredemat.business.request.permit.HeuresType getHeureEnd() {
        return parkingPermitTemporaryRelocationRequestData.getHeureEnd();
    }
  
    public final void setHeureStart(final org.libredemat.business.request.permit.HeuresType heureStart) {
        parkingPermitTemporaryRelocationRequestData.setHeureStart(heureStart);
    }

    
    public final org.libredemat.business.request.permit.HeuresType getHeureStart() {
        return parkingPermitTemporaryRelocationRequestData.getHeureStart();
    }
  
    public final void setImmatriculation(final String immatriculation) {
        parkingPermitTemporaryRelocationRequestData.setImmatriculation(immatriculation);
    }

    
    public final String getImmatriculation() {
        return parkingPermitTemporaryRelocationRequestData.getImmatriculation();
    }
  
    public final void setIsCompany(final Boolean isCompany) {
        parkingPermitTemporaryRelocationRequestData.setIsCompany(isCompany);
    }

    
    public final Boolean getIsCompany() {
        return parkingPermitTemporaryRelocationRequestData.getIsCompany();
    }
  
    public final void setLongeur(final java.math.BigInteger longeur) {
        parkingPermitTemporaryRelocationRequestData.setLongeur(longeur);
    }

    
    public final java.math.BigInteger getLongeur() {
        return parkingPermitTemporaryRelocationRequestData.getLongeur();
    }
  
    public final void setObservations(final String observations) {
        parkingPermitTemporaryRelocationRequestData.setObservations(observations);
    }

    
    public final String getObservations() {
        return parkingPermitTemporaryRelocationRequestData.getObservations();
    }
  
    public final void setObservationsReglement(final String observationsReglement) {
        parkingPermitTemporaryRelocationRequestData.setObservationsReglement(observationsReglement);
    }

    
    public final String getObservationsReglement() {
        return parkingPermitTemporaryRelocationRequestData.getObservationsReglement();
    }
  
    public final void setOther(final String other) {
        parkingPermitTemporaryRelocationRequestData.setOther(other);
    }

    
    public final String getOther() {
        return parkingPermitTemporaryRelocationRequestData.getOther();
    }
  
    public final void setPayment(final org.libredemat.business.payment.Payment payment) {
        parkingPermitTemporaryRelocationRequestData.setPayment(payment);
    }

    
    public final org.libredemat.business.payment.Payment getPayment() {
        return parkingPermitTemporaryRelocationRequestData.getPayment();
    }
  
    public final void setPeriodeEnd(final java.util.Date periodeEnd) {
        parkingPermitTemporaryRelocationRequestData.setPeriodeEnd(periodeEnd);
    }

    
    public final java.util.Date getPeriodeEnd() {
        return parkingPermitTemporaryRelocationRequestData.getPeriodeEnd();
    }
  
    public final void setPeriodeStart(final java.util.Date periodeStart) {
        parkingPermitTemporaryRelocationRequestData.setPeriodeStart(periodeStart);
    }

    
    public final java.util.Date getPeriodeStart() {
        return parkingPermitTemporaryRelocationRequestData.getPeriodeStart();
    }
  
    public final void setRequesterAddress(final String requesterAddress) {
        parkingPermitTemporaryRelocationRequestData.setRequesterAddress(requesterAddress);
    }

    
    public final String getRequesterAddress() {
        return parkingPermitTemporaryRelocationRequestData.getRequesterAddress();
    }
  
    public final void setSiretNumber(final String siretNumber) {
        parkingPermitTemporaryRelocationRequestData.setSiretNumber(siretNumber);
    }

    
    public final String getSiretNumber() {
        return parkingPermitTemporaryRelocationRequestData.getSiretNumber();
    }
  
    public final void setVehicleType(final String vehicleType) {
        parkingPermitTemporaryRelocationRequestData.setVehicleType(vehicleType);
    }

    
    public final String getVehicleType() {
        return parkingPermitTemporaryRelocationRequestData.getVehicleType();
    }
  
}
