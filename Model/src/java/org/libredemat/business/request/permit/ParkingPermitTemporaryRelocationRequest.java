
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
      
        i = 0;
        if (getEquipmentUsed() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] equipmentUsedTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getEquipmentUsed().size()];
            for (LocalReferentialData object : getEquipmentUsed()) {
              equipmentUsedTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            parkingPermitTemporaryRelocationRequest.setEquipmentUsedArray(equipmentUsedTypeTab);
        }
      
        if (getHeureEnd() != null)
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.xml.request.permit.HeuresType.Enum.forString(getHeureEnd().getLegacyLabel()));
      
        if (getHeureStart() != null)
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.xml.request.permit.HeuresType.Enum.forString(getHeureStart().getLegacyLabel()));
      
        parkingPermitTemporaryRelocationRequest.setImmatriculation(getImmatriculation());
      
        if (getLargeur() != null)
            parkingPermitTemporaryRelocationRequest.setLargeur(new BigInteger(getLargeur().toString()));
      
        if (getLongeur() != null)
            parkingPermitTemporaryRelocationRequest.setLongeur(new BigInteger(getLongeur().toString()));
      
        parkingPermitTemporaryRelocationRequest.setMarque(getMarque());
      
        if (getPayment() != null)
            parkingPermitTemporaryRelocationRequest.setPayment(Payment.modelToXml(getPayment()));
      
        i = 0;
        if (getPerformChoice() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] performChoiceTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getPerformChoice().size()];
            for (LocalReferentialData object : getPerformChoice()) {
              performChoiceTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            parkingPermitTemporaryRelocationRequest.setPerformChoiceArray(performChoiceTypeTab);
        }
      
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
      
        if (getRequesterAddress() != null)
            parkingPermitTemporaryRelocationRequest.setRequesterAddress(Address.modelToXml(getRequesterAddress()));
      
        if (getTonnage() != null)
            parkingPermitTemporaryRelocationRequest.setTonnage(new BigInteger(getTonnage().toString()));
      
        if (getVolume() != null)
            parkingPermitTemporaryRelocationRequest.setVolume(new BigInteger(getVolume().toString()));
      
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
      
        List<org.libredemat.business.request.LocalReferentialData> equipmentUsedList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(parkingPermitTemporaryRelocationRequestXml.sizeOfEquipmentUsedArray());
        for (LocalReferentialDataType object : parkingPermitTemporaryRelocationRequestXml.getEquipmentUsedArray()) {
            equipmentUsedList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        parkingPermitTemporaryRelocationRequest.setEquipmentUsed(equipmentUsedList);
      
        if (parkingPermitTemporaryRelocationRequestXml.getHeureEnd() != null)
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.business.request.permit.HeuresType.forString(parkingPermitTemporaryRelocationRequestXml.getHeureEnd().toString()));
        else
            parkingPermitTemporaryRelocationRequest.setHeureEnd(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
        if (parkingPermitTemporaryRelocationRequestXml.getHeureStart() != null)
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.business.request.permit.HeuresType.forString(parkingPermitTemporaryRelocationRequestXml.getHeureStart().toString()));
        else
            parkingPermitTemporaryRelocationRequest.setHeureStart(org.libredemat.business.request.permit.HeuresType.getDefaultHeuresType());
      
        parkingPermitTemporaryRelocationRequest.setImmatriculation(parkingPermitTemporaryRelocationRequestXml.getImmatriculation());
      
        parkingPermitTemporaryRelocationRequest.setLargeur(parkingPermitTemporaryRelocationRequestXml.getLargeur());
      
        parkingPermitTemporaryRelocationRequest.setLongeur(parkingPermitTemporaryRelocationRequestXml.getLongeur());
      
        parkingPermitTemporaryRelocationRequest.setMarque(parkingPermitTemporaryRelocationRequestXml.getMarque());
      
        if (parkingPermitTemporaryRelocationRequestXml.getPayment() != null)
            parkingPermitTemporaryRelocationRequest.setPayment(Payment.xmlToModel(parkingPermitTemporaryRelocationRequestXml.getPayment()));
      
        List<org.libredemat.business.request.LocalReferentialData> performChoiceList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(parkingPermitTemporaryRelocationRequestXml.sizeOfPerformChoiceArray());
        for (LocalReferentialDataType object : parkingPermitTemporaryRelocationRequestXml.getPerformChoiceArray()) {
            performChoiceList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        parkingPermitTemporaryRelocationRequest.setPerformChoice(performChoiceList);
      
        calendar = parkingPermitTemporaryRelocationRequestXml.getPeriodeEnd();
        if (calendar != null) {
            parkingPermitTemporaryRelocationRequest.setPeriodeEnd(calendar.getTime());
        }
      
        calendar = parkingPermitTemporaryRelocationRequestXml.getPeriodeStart();
        if (calendar != null) {
            parkingPermitTemporaryRelocationRequest.setPeriodeStart(calendar.getTime());
        }
      
        if (parkingPermitTemporaryRelocationRequestXml.getRequesterAddress() != null)
            parkingPermitTemporaryRelocationRequest.setRequesterAddress(Address.xmlToModel(parkingPermitTemporaryRelocationRequestXml.getRequesterAddress()));
      
        parkingPermitTemporaryRelocationRequest.setTonnage(parkingPermitTemporaryRelocationRequestXml.getTonnage());
      
        parkingPermitTemporaryRelocationRequest.setVolume(parkingPermitTemporaryRelocationRequestXml.getVolume());
      
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
  
    public final void setEquipmentUsed(final List<org.libredemat.business.request.LocalReferentialData> equipmentUsed) {
        parkingPermitTemporaryRelocationRequestData.setEquipmentUsed(equipmentUsed);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getEquipmentUsed() {
        return parkingPermitTemporaryRelocationRequestData.getEquipmentUsed();
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
  
    public final void setLargeur(final java.math.BigInteger largeur) {
        parkingPermitTemporaryRelocationRequestData.setLargeur(largeur);
    }

    
    public final java.math.BigInteger getLargeur() {
        return parkingPermitTemporaryRelocationRequestData.getLargeur();
    }
  
    public final void setLongeur(final java.math.BigInteger longeur) {
        parkingPermitTemporaryRelocationRequestData.setLongeur(longeur);
    }

    
    public final java.math.BigInteger getLongeur() {
        return parkingPermitTemporaryRelocationRequestData.getLongeur();
    }
  
    public final void setMarque(final String marque) {
        parkingPermitTemporaryRelocationRequestData.setMarque(marque);
    }

    
    public final String getMarque() {
        return parkingPermitTemporaryRelocationRequestData.getMarque();
    }
  
    public final void setPayment(final org.libredemat.business.payment.Payment payment) {
        parkingPermitTemporaryRelocationRequestData.setPayment(payment);
    }

    
    public final org.libredemat.business.payment.Payment getPayment() {
        return parkingPermitTemporaryRelocationRequestData.getPayment();
    }
  
    public final void setPerformChoice(final List<org.libredemat.business.request.LocalReferentialData> performChoice) {
        parkingPermitTemporaryRelocationRequestData.setPerformChoice(performChoice);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getPerformChoice() {
        return parkingPermitTemporaryRelocationRequestData.getPerformChoice();
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
  
    public final void setRequesterAddress(final org.libredemat.business.users.Address requesterAddress) {
        parkingPermitTemporaryRelocationRequestData.setRequesterAddress(requesterAddress);
    }

    
    public final org.libredemat.business.users.Address getRequesterAddress() {
        return parkingPermitTemporaryRelocationRequestData.getRequesterAddress();
    }
  
    public final void setTonnage(final java.math.BigInteger tonnage) {
        parkingPermitTemporaryRelocationRequestData.setTonnage(tonnage);
    }

    
    public final java.math.BigInteger getTonnage() {
        return parkingPermitTemporaryRelocationRequestData.getTonnage();
    }
  
    public final void setVolume(final java.math.BigInteger volume) {
        parkingPermitTemporaryRelocationRequestData.setVolume(volume);
    }

    
    public final java.math.BigInteger getVolume() {
        return parkingPermitTemporaryRelocationRequestData.getVolume();
    }
  
}
