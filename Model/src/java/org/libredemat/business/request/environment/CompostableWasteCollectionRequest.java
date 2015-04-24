
package org.libredemat.business.request.environment;

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
import org.libredemat.xml.request.environment.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class CompostableWasteCollectionRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = CompostableWasteCollectionRequestData.conditions;

    @AssertValid(message = "")
    private CompostableWasteCollectionRequestData compostableWasteCollectionRequestData;

    public CompostableWasteCollectionRequest(RequestData requestData, CompostableWasteCollectionRequestData compostableWasteCollectionRequestData) {
        super(requestData);
        this.compostableWasteCollectionRequestData = compostableWasteCollectionRequestData;
    }

    public CompostableWasteCollectionRequest() {
        super();
        this.compostableWasteCollectionRequestData = new CompostableWasteCollectionRequestData();
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
          getStepStates().put("waste", stepState);
        
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
    public CompostableWasteCollectionRequestData getSpecificData() {
        return compostableWasteCollectionRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(CompostableWasteCollectionRequestData compostableWasteCollectionRequestData) {
        this.compostableWasteCollectionRequestData = compostableWasteCollectionRequestData;
    }

    @Override
    public final String modelToXmlString() {
        CompostableWasteCollectionRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final CompostableWasteCollectionRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        CompostableWasteCollectionRequestDocument compostableWasteCollectionRequestDoc = CompostableWasteCollectionRequestDocument.Factory.newInstance();
        CompostableWasteCollectionRequestDocument.CompostableWasteCollectionRequest compostableWasteCollectionRequest = compostableWasteCollectionRequestDoc.addNewCompostableWasteCollectionRequest();
        super.fillCommonXmlInfo(compostableWasteCollectionRequest);
        int i = 0;
        
        if (getCollectionAddress() != null)
            compostableWasteCollectionRequest.setCollectionAddress(Address.modelToXml(getCollectionAddress()));
      
        i = 0;
        if (getCompostableWasteType() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] compostableWasteTypeTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getCompostableWasteType().size()];
            for (LocalReferentialData object : getCompostableWasteType()) {
              compostableWasteTypeTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            compostableWasteCollectionRequest.setCompostableWasteTypeArray(compostableWasteTypeTypeTab);
        }
      
        compostableWasteCollectionRequest.setOtherWaste(getOtherWaste());
      
        return compostableWasteCollectionRequestDoc;
    }

    @Override
    public final CompostableWasteCollectionRequestDocument.CompostableWasteCollectionRequest modelToXmlRequest() {
        return modelToXml().getCompostableWasteCollectionRequest();
    }

    public static CompostableWasteCollectionRequest xmlToModel(CompostableWasteCollectionRequestDocument compostableWasteCollectionRequestDoc) {
        CompostableWasteCollectionRequestDocument.CompostableWasteCollectionRequest compostableWasteCollectionRequestXml = compostableWasteCollectionRequestDoc.getCompostableWasteCollectionRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        CompostableWasteCollectionRequest compostableWasteCollectionRequest = new CompostableWasteCollectionRequest();
        compostableWasteCollectionRequest.fillCommonModelInfo(compostableWasteCollectionRequest, compostableWasteCollectionRequestXml);
        
        if (compostableWasteCollectionRequestXml.getCollectionAddress() != null)
            compostableWasteCollectionRequest.setCollectionAddress(Address.xmlToModel(compostableWasteCollectionRequestXml.getCollectionAddress()));
      
        List<org.libredemat.business.request.LocalReferentialData> compostableWasteTypeList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(compostableWasteCollectionRequestXml.sizeOfCompostableWasteTypeArray());
        for (LocalReferentialDataType object : compostableWasteCollectionRequestXml.getCompostableWasteTypeArray()) {
            compostableWasteTypeList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        compostableWasteCollectionRequest.setCompostableWasteType(compostableWasteTypeList);
      
        compostableWasteCollectionRequest.setOtherWaste(compostableWasteCollectionRequestXml.getOtherWaste());
      
        return compostableWasteCollectionRequest;
    }

    @Override
    public CompostableWasteCollectionRequest clone() {
        CompostableWasteCollectionRequest clone = new CompostableWasteCollectionRequest(getRequestData().clone(), compostableWasteCollectionRequestData.clone());
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
          clone.getStepStates().put("waste", stepState);
        
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

  
    public final void setCollectionAddress(final org.libredemat.business.users.Address collectionAddress) {
        compostableWasteCollectionRequestData.setCollectionAddress(collectionAddress);
    }

    
    public final org.libredemat.business.users.Address getCollectionAddress() {
        return compostableWasteCollectionRequestData.getCollectionAddress();
    }
  
    public final void setCompostableWasteType(final List<org.libredemat.business.request.LocalReferentialData> compostableWasteType) {
        compostableWasteCollectionRequestData.setCompostableWasteType(compostableWasteType);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getCompostableWasteType() {
        return compostableWasteCollectionRequestData.getCompostableWasteType();
    }
  
    public final void setOtherWaste(final String otherWaste) {
        compostableWasteCollectionRequestData.setOtherWaste(otherWaste);
    }

    
    public final String getOtherWaste() {
        return compostableWasteCollectionRequestData.getOtherWaste();
    }
  
}
