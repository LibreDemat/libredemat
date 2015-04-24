
package org.libredemat.business.request.parking;

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
import org.libredemat.xml.request.parking.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class ParkCardRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = ParkCardRequestData.conditions;

    @AssertValid(message = "")
    private ParkCardRequestData parkCardRequestData;

    public ParkCardRequest(RequestData requestData, ParkCardRequestData parkCardRequestData) {
        super(requestData);
        this.parkCardRequestData = parkCardRequestData;
    }

    public ParkCardRequest() {
        super();
        this.parkCardRequestData = new ParkCardRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("subject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("car", stepState);
        
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
    public ParkCardRequestData getSpecificData() {
        return parkCardRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(ParkCardRequestData parkCardRequestData) {
        this.parkCardRequestData = parkCardRequestData;
    }

    @Override
    public final String modelToXmlString() {
        ParkCardRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final ParkCardRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        ParkCardRequestDocument parkCardRequestDoc = ParkCardRequestDocument.Factory.newInstance();
        ParkCardRequestDocument.ParkCardRequest parkCardRequest = parkCardRequestDoc.addNewParkCardRequest();
        super.fillCommonXmlInfo(parkCardRequest);
        int i = 0;
          ParkTarifDefinitionType parkTarifDefinitionTypeTarification = parkCardRequest.addNewTarification();
        if (getCardNumberLimit() != null)
            parkTarifDefinitionTypeTarification.setCardNumberLimit(new BigInteger(getCardNumberLimit().toString()));
      
        if (getCardOnePrice() != null)
            parkTarifDefinitionTypeTarification.setCardOnePrice(getCardOnePrice());
      
        if (getCardThreePrice() != null)
            parkTarifDefinitionTypeTarification.setCardThreePrice(getCardThreePrice());
      
        if (getCardTwoPrice() != null)
            parkTarifDefinitionTypeTarification.setCardTwoPrice(getCardTwoPrice());
      
        parkCardRequest.setInformationCardLimitRest(getInformationCardLimitRest());
      
        i = 0;
        if (getParkImatriculation() != null) {
            org.libredemat.xml.request.parking.ParkImmatriculationType[] parkImatriculationTypeTab = new org.libredemat.xml.request.parking.ParkImmatriculationType[getParkImatriculation().size()];
            for (ParkImmatriculation object : getParkImatriculation()) {
              parkImatriculationTypeTab[i++] = object.modelToXml();
            }
            parkCardRequest.setParkImatriculationArray(parkImatriculationTypeTab);
        }
      
        parkCardRequest.setParkResident(getParkResident());
      
        parkCardRequest.setPaymentReference(getPaymentReference());
      
        parkCardRequest.setPaymentTotal(getPaymentTotal());
      
        if (getSubjectAddress() != null)
            parkCardRequest.setSubjectAddress(Address.modelToXml(getSubjectAddress()));
      
        return parkCardRequestDoc;
    }

    @Override
    public final ParkCardRequestDocument.ParkCardRequest modelToXmlRequest() {
        return modelToXml().getParkCardRequest();
    }

    public static ParkCardRequest xmlToModel(ParkCardRequestDocument parkCardRequestDoc) {
        ParkCardRequestDocument.ParkCardRequest parkCardRequestXml = parkCardRequestDoc.getParkCardRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ParkCardRequest parkCardRequest = new ParkCardRequest();
        parkCardRequest.fillCommonModelInfo(parkCardRequest, parkCardRequestXml);
        
        parkCardRequest.setCardNumberLimit(parkCardRequestXml.getTarification().getCardNumberLimit());
      
        if (parkCardRequestXml.getTarification().getCardOnePrice() != null)
            parkCardRequest.setCardOnePrice(parkCardRequestXml.getTarification().getCardOnePrice());
      
        if (parkCardRequestXml.getTarification().getCardThreePrice() != null)
            parkCardRequest.setCardThreePrice(parkCardRequestXml.getTarification().getCardThreePrice());
      
        if (parkCardRequestXml.getTarification().getCardTwoPrice() != null)
            parkCardRequest.setCardTwoPrice(parkCardRequestXml.getTarification().getCardTwoPrice());
      
        parkCardRequest.setInformationCardLimitRest(parkCardRequestXml.getInformationCardLimitRest());
      
        List<org.libredemat.business.request.parking.ParkImmatriculation> parkImatriculationList = new ArrayList<org.libredemat.business.request.parking.ParkImmatriculation>(parkCardRequestXml.sizeOfParkImatriculationArray());
        for (ParkImmatriculationType object : parkCardRequestXml.getParkImatriculationArray()) {
            parkImatriculationList.add(org.libredemat.business.request.parking.ParkImmatriculation.xmlToModel(object));
        }
        parkCardRequest.setParkImatriculation(parkImatriculationList);
      
        parkCardRequest.setParkResident(parkCardRequestXml.getParkResident());
      
        parkCardRequest.setPaymentReference(parkCardRequestXml.getPaymentReference());
      
        parkCardRequest.setPaymentTotal(parkCardRequestXml.getPaymentTotal());
      
        if (parkCardRequestXml.getSubjectAddress() != null)
            parkCardRequest.setSubjectAddress(Address.xmlToModel(parkCardRequestXml.getSubjectAddress()));
      
        return parkCardRequest;
    }

    @Override
    public ParkCardRequest clone() {
        ParkCardRequest clone = new ParkCardRequest(getRequestData().clone(), parkCardRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("subject", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("car", stepState);
        
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

  
    public final void setCardNumberLimit(final java.math.BigInteger cardNumberLimit) {
        parkCardRequestData.setCardNumberLimit(cardNumberLimit);
    }

    
    public final java.math.BigInteger getCardNumberLimit() {
        return parkCardRequestData.getCardNumberLimit();
    }
  
    public final void setCardOnePrice(final java.math.BigDecimal cardOnePrice) {
        parkCardRequestData.setCardOnePrice(cardOnePrice);
    }

    
    public final java.math.BigDecimal getCardOnePrice() {
        return parkCardRequestData.getCardOnePrice();
    }
  
    public final void setCardThreePrice(final java.math.BigDecimal cardThreePrice) {
        parkCardRequestData.setCardThreePrice(cardThreePrice);
    }

    
    public final java.math.BigDecimal getCardThreePrice() {
        return parkCardRequestData.getCardThreePrice();
    }
  
    public final void setCardTwoPrice(final java.math.BigDecimal cardTwoPrice) {
        parkCardRequestData.setCardTwoPrice(cardTwoPrice);
    }

    
    public final java.math.BigDecimal getCardTwoPrice() {
        return parkCardRequestData.getCardTwoPrice();
    }
  
    public final void setInformationCardLimitRest(final String informationCardLimitRest) {
        parkCardRequestData.setInformationCardLimitRest(informationCardLimitRest);
    }

    
    public final String getInformationCardLimitRest() {
        return parkCardRequestData.getInformationCardLimitRest();
    }
  
    public final void setParkImatriculation(final List<org.libredemat.business.request.parking.ParkImmatriculation> parkImatriculation) {
        parkCardRequestData.setParkImatriculation(parkImatriculation);
    }

    
    public final List<org.libredemat.business.request.parking.ParkImmatriculation> getParkImatriculation() {
        return parkCardRequestData.getParkImatriculation();
    }
  
    public final void setParkResident(final String parkResident) {
        parkCardRequestData.setParkResident(parkResident);
    }

    
    public final String getParkResident() {
        return parkCardRequestData.getParkResident();
    }
  
    public final void setPaymentReference(final String paymentReference) {
        parkCardRequestData.setPaymentReference(paymentReference);
    }

    
    public final String getPaymentReference() {
        return parkCardRequestData.getPaymentReference();
    }
  
    public final void setPaymentTotal(final String paymentTotal) {
        parkCardRequestData.setPaymentTotal(paymentTotal);
    }

    
    public final String getPaymentTotal() {
        return parkCardRequestData.getPaymentTotal();
    }
  
    public final void setSubjectAddress(final org.libredemat.business.users.Address subjectAddress) {
        parkCardRequestData.setSubjectAddress(subjectAddress);
    }

    
    public final org.libredemat.business.users.Address getSubjectAddress() {
        return parkCardRequestData.getSubjectAddress();
    }
  
}
