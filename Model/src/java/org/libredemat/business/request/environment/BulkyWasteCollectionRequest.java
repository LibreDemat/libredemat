
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
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.environment.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class BulkyWasteCollectionRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = BulkyWasteCollectionRequestData.conditions;

    @AssertValid(message = "")
    private BulkyWasteCollectionRequestData bulkyWasteCollectionRequestData;

    public BulkyWasteCollectionRequest(RequestData requestData, BulkyWasteCollectionRequestData bulkyWasteCollectionRequestData) {
        super(requestData);
        this.bulkyWasteCollectionRequestData = bulkyWasteCollectionRequestData;
    }

    public BulkyWasteCollectionRequest() {
        super();
        this.bulkyWasteCollectionRequestData = new BulkyWasteCollectionRequestData();
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
    public BulkyWasteCollectionRequestData getSpecificData() {
        return bulkyWasteCollectionRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(BulkyWasteCollectionRequestData bulkyWasteCollectionRequestData) {
        this.bulkyWasteCollectionRequestData = bulkyWasteCollectionRequestData;
    }

    @Override
    public final String modelToXmlString() {
        BulkyWasteCollectionRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final BulkyWasteCollectionRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        BulkyWasteCollectionRequestDocument bulkyWasteCollectionRequestDoc = BulkyWasteCollectionRequestDocument.Factory.newInstance();
        BulkyWasteCollectionRequestDocument.BulkyWasteCollectionRequest bulkyWasteCollectionRequest = bulkyWasteCollectionRequestDoc.addNewBulkyWasteCollectionRequest();
        super.fillCommonXmlInfo(bulkyWasteCollectionRequest);
        int i = 0;
        
        i = 0;
        if (getBulkyWasteType() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] bulkyWasteTypeTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getBulkyWasteType().size()];
            for (LocalReferentialData object : getBulkyWasteType()) {
              bulkyWasteTypeTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            bulkyWasteCollectionRequest.setBulkyWasteTypeArray(bulkyWasteTypeTypeTab);
        }
      
        if (getCollectionAddress() != null)
            bulkyWasteCollectionRequest.setCollectionAddress(Address.modelToXml(getCollectionAddress()));
      
        bulkyWasteCollectionRequest.setOtherWaste(getOtherWaste());
      
        return bulkyWasteCollectionRequestDoc;
    }

    @Override
    public final BulkyWasteCollectionRequestDocument.BulkyWasteCollectionRequest modelToXmlRequest() {
        return modelToXml().getBulkyWasteCollectionRequest();
    }

    public static BulkyWasteCollectionRequest xmlToModel(BulkyWasteCollectionRequestDocument bulkyWasteCollectionRequestDoc) {
        BulkyWasteCollectionRequestDocument.BulkyWasteCollectionRequest bulkyWasteCollectionRequestXml = bulkyWasteCollectionRequestDoc.getBulkyWasteCollectionRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        BulkyWasteCollectionRequest bulkyWasteCollectionRequest = new BulkyWasteCollectionRequest();
        bulkyWasteCollectionRequest.fillCommonModelInfo(bulkyWasteCollectionRequest, bulkyWasteCollectionRequestXml);
        
        List<org.libredemat.business.request.LocalReferentialData> bulkyWasteTypeList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(bulkyWasteCollectionRequestXml.sizeOfBulkyWasteTypeArray());
        for (LocalReferentialDataType object : bulkyWasteCollectionRequestXml.getBulkyWasteTypeArray()) {
            bulkyWasteTypeList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        bulkyWasteCollectionRequest.setBulkyWasteType(bulkyWasteTypeList);
      
        if (bulkyWasteCollectionRequestXml.getCollectionAddress() != null)
            bulkyWasteCollectionRequest.setCollectionAddress(Address.xmlToModel(bulkyWasteCollectionRequestXml.getCollectionAddress()));
      
        bulkyWasteCollectionRequest.setOtherWaste(bulkyWasteCollectionRequestXml.getOtherWaste());
      
        return bulkyWasteCollectionRequest;
    }

    @Override
    public BulkyWasteCollectionRequest clone() {
        BulkyWasteCollectionRequest clone = new BulkyWasteCollectionRequest(getRequestData().clone(), bulkyWasteCollectionRequestData.clone());
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

  
    public final void setBulkyWasteType(final List<org.libredemat.business.request.LocalReferentialData> bulkyWasteType) {
        bulkyWasteCollectionRequestData.setBulkyWasteType(bulkyWasteType);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getBulkyWasteType() {
        return bulkyWasteCollectionRequestData.getBulkyWasteType();
    }
  
    public final void setCollectionAddress(final org.libredemat.business.users.Address collectionAddress) {
        bulkyWasteCollectionRequestData.setCollectionAddress(collectionAddress);
    }

    
    public final org.libredemat.business.users.Address getCollectionAddress() {
        return bulkyWasteCollectionRequestData.getCollectionAddress();
    }
  
    public final void setOtherWaste(final String otherWaste) {
        bulkyWasteCollectionRequestData.setOtherWaste(otherWaste);
    }

    
    public final String getOtherWaste() {
        return bulkyWasteCollectionRequestData.getOtherWaste();
    }
  
}
