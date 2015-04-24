
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
public class ConflansMunicipalWasteRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = ConflansMunicipalWasteRequestData.conditions;

    @AssertValid(message = "")
    private ConflansMunicipalWasteRequestData conflansMunicipalWasteRequestData;

    public ConflansMunicipalWasteRequest(RequestData requestData, ConflansMunicipalWasteRequestData conflansMunicipalWasteRequestData) {
        super(requestData);
        this.conflansMunicipalWasteRequestData = conflansMunicipalWasteRequestData;
    }

    public ConflansMunicipalWasteRequest() {
        super();
        this.conflansMunicipalWasteRequestData = new ConflansMunicipalWasteRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("informations", stepState);
        
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
          getStepStates().put("administration", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public ConflansMunicipalWasteRequestData getSpecificData() {
        return conflansMunicipalWasteRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(ConflansMunicipalWasteRequestData conflansMunicipalWasteRequestData) {
        this.conflansMunicipalWasteRequestData = conflansMunicipalWasteRequestData;
    }

    @Override
    public final String modelToXmlString() {
        ConflansMunicipalWasteRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final ConflansMunicipalWasteRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        ConflansMunicipalWasteRequestDocument conflansMunicipalWasteRequestDoc = ConflansMunicipalWasteRequestDocument.Factory.newInstance();
        ConflansMunicipalWasteRequestDocument.ConflansMunicipalWasteRequest conflansMunicipalWasteRequest = conflansMunicipalWasteRequestDoc.addNewConflansMunicipalWasteRequest();
        super.fillCommonXmlInfo(conflansMunicipalWasteRequest);
        int i = 0;
        
        if (getAdresseOrganisation() != null)
            conflansMunicipalWasteRequest.setAdresseOrganisation(Address.modelToXml(getAdresseOrganisation()));
        CmwrTailleComposteurType cmwrTailleComposteurTypeTailleComposteur = conflansMunicipalWasteRequest.addNewTailleComposteur();
        if (getComposteurGrand() != null)
            cmwrTailleComposteurTypeTailleComposteur.setComposteurGrand(new BigInteger(getComposteurGrand().toString()));
      
        if (getComposteurPetit() != null)
            cmwrTailleComposteurTypeTailleComposteur.setComposteurPetit(new BigInteger(getComposteurPetit().toString()));
      
        i = 0;
        if (getConteneur() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] conteneurTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getConteneur().size()];
            for (LocalReferentialData object : getConteneur()) {
              conteneurTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            conflansMunicipalWasteRequest.setConteneurArray(conteneurTypeTab);
        }
      
        conflansMunicipalWasteRequest.setNomOrganisation(getNomOrganisation());
      
        conflansMunicipalWasteRequest.setNombreResidants(getNombreResidants());
        CmwrVolumesOmType cmwrVolumesOmTypeVolumesOm = conflansMunicipalWasteRequest.addNewVolumesOm();
        if (getOmCentVingtLitres() != null)
            cmwrVolumesOmTypeVolumesOm.setOmCentVingtLitres(new BigInteger(getOmCentVingtLitres().toString()));
      
        if (getOmDeuxCentQuaranteLitres() != null)
            cmwrVolumesOmTypeVolumesOm.setOmDeuxCentQuaranteLitres(new BigInteger(getOmDeuxCentQuaranteLitres().toString()));
      
        if (getOmSixCentSoixanteLitres() != null)
            cmwrVolumesOmTypeVolumesOm.setOmSixCentSoixanteLitres(new BigInteger(getOmSixCentSoixanteLitres().toString()));
      
        if (getOmTroisCentQuaranteLitres() != null)
            cmwrVolumesOmTypeVolumesOm.setOmTroisCentQuaranteLitres(new BigInteger(getOmTroisCentQuaranteLitres().toString()));
      
        conflansMunicipalWasteRequest.setPrecisionsReparation(getPrecisionsReparation());
      
        if (getProfilDemandeur() != null)
            conflansMunicipalWasteRequest.setProfilDemandeur(org.libredemat.xml.request.environment.CmwrProfilDemandeurType.Enum.forString(getProfilDemandeur().getLegacyLabel()));
      
        i = 0;
        if (getQuartier() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] quartierTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getQuartier().size()];
            for (LocalReferentialData object : getQuartier()) {
              quartierTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            conflansMunicipalWasteRequest.setQuartierArray(quartierTypeTab);
        }
        CmwrVolumesTriType cmwrVolumesTriTypeVolumesTri = conflansMunicipalWasteRequest.addNewVolumesTri();
        if (getTriCentVingtLitres() != null)
            cmwrVolumesTriTypeVolumesTri.setTriCentVingtLitres(new BigInteger(getTriCentVingtLitres().toString()));
      
        if (getTriDeuxCentQuaranteLitres() != null)
            cmwrVolumesTriTypeVolumesTri.setTriDeuxCentQuaranteLitres(new BigInteger(getTriDeuxCentQuaranteLitres().toString()));
      
        if (getTriSixCentSoixanteLitres() != null)
            cmwrVolumesTriTypeVolumesTri.setTriSixCentSoixanteLitres(new BigInteger(getTriSixCentSoixanteLitres().toString()));
      
        if (getTriTroisCentQuaranteLitres() != null)
            cmwrVolumesTriTypeVolumesTri.setTriTroisCentQuaranteLitres(new BigInteger(getTriTroisCentQuaranteLitres().toString()));
      
        if (getTypeHabitation() != null)
            conflansMunicipalWasteRequest.setTypeHabitation(org.libredemat.xml.request.environment.CmwrTypeHabitationType.Enum.forString(getTypeHabitation().getLegacyLabel()));
        CmwrVolumesVerreType cmwrVolumesVerreTypeVolumesVerre = conflansMunicipalWasteRequest.addNewVolumesVerre();
        if (getVerreCentVingtLitres() != null)
            cmwrVolumesVerreTypeVolumesVerre.setVerreCentVingtLitres(new BigInteger(getVerreCentVingtLitres().toString()));
      
        if (getVerreDeuxCentQuaranteLitres() != null)
            cmwrVolumesVerreTypeVolumesVerre.setVerreDeuxCentQuaranteLitres(new BigInteger(getVerreDeuxCentQuaranteLitres().toString()));
      
        if (getVerreTrenteCinqLitres() != null)
            cmwrVolumesVerreTypeVolumesVerre.setVerreTrenteCinqLitres(new BigInteger(getVerreTrenteCinqLitres().toString()));
      
        return conflansMunicipalWasteRequestDoc;
    }

    @Override
    public final ConflansMunicipalWasteRequestDocument.ConflansMunicipalWasteRequest modelToXmlRequest() {
        return modelToXml().getConflansMunicipalWasteRequest();
    }

    public static ConflansMunicipalWasteRequest xmlToModel(ConflansMunicipalWasteRequestDocument conflansMunicipalWasteRequestDoc) {
        ConflansMunicipalWasteRequestDocument.ConflansMunicipalWasteRequest conflansMunicipalWasteRequestXml = conflansMunicipalWasteRequestDoc.getConflansMunicipalWasteRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ConflansMunicipalWasteRequest conflansMunicipalWasteRequest = new ConflansMunicipalWasteRequest();
        conflansMunicipalWasteRequest.fillCommonModelInfo(conflansMunicipalWasteRequest, conflansMunicipalWasteRequestXml);
        
        if (conflansMunicipalWasteRequestXml.getAdresseOrganisation() != null)
            conflansMunicipalWasteRequest.setAdresseOrganisation(Address.xmlToModel(conflansMunicipalWasteRequestXml.getAdresseOrganisation()));
      
        conflansMunicipalWasteRequest.setComposteurGrand(conflansMunicipalWasteRequestXml.getTailleComposteur().getComposteurGrand());
      
        conflansMunicipalWasteRequest.setComposteurPetit(conflansMunicipalWasteRequestXml.getTailleComposteur().getComposteurPetit());
      
        List<org.libredemat.business.request.LocalReferentialData> conteneurList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(conflansMunicipalWasteRequestXml.sizeOfConteneurArray());
        for (LocalReferentialDataType object : conflansMunicipalWasteRequestXml.getConteneurArray()) {
            conteneurList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        conflansMunicipalWasteRequest.setConteneur(conteneurList);
      
        conflansMunicipalWasteRequest.setNomOrganisation(conflansMunicipalWasteRequestXml.getNomOrganisation());
      
        conflansMunicipalWasteRequest.setNombreResidants(conflansMunicipalWasteRequestXml.getNombreResidants());
      
        conflansMunicipalWasteRequest.setOmCentVingtLitres(conflansMunicipalWasteRequestXml.getVolumesOm().getOmCentVingtLitres());
      
        conflansMunicipalWasteRequest.setOmDeuxCentQuaranteLitres(conflansMunicipalWasteRequestXml.getVolumesOm().getOmDeuxCentQuaranteLitres());
      
        conflansMunicipalWasteRequest.setOmSixCentSoixanteLitres(conflansMunicipalWasteRequestXml.getVolumesOm().getOmSixCentSoixanteLitres());
      
        conflansMunicipalWasteRequest.setOmTroisCentQuaranteLitres(conflansMunicipalWasteRequestXml.getVolumesOm().getOmTroisCentQuaranteLitres());
      
        conflansMunicipalWasteRequest.setPrecisionsReparation(conflansMunicipalWasteRequestXml.getPrecisionsReparation());
      
        if (conflansMunicipalWasteRequestXml.getProfilDemandeur() != null)
            conflansMunicipalWasteRequest.setProfilDemandeur(org.libredemat.business.request.environment.CmwrProfilDemandeurType.forString(conflansMunicipalWasteRequestXml.getProfilDemandeur().toString()));
        else
            conflansMunicipalWasteRequest.setProfilDemandeur(org.libredemat.business.request.environment.CmwrProfilDemandeurType.getDefaultCmwrProfilDemandeurType());
      
        List<org.libredemat.business.request.LocalReferentialData> quartierList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(conflansMunicipalWasteRequestXml.sizeOfQuartierArray());
        for (LocalReferentialDataType object : conflansMunicipalWasteRequestXml.getQuartierArray()) {
            quartierList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        conflansMunicipalWasteRequest.setQuartier(quartierList);
      
        conflansMunicipalWasteRequest.setTriCentVingtLitres(conflansMunicipalWasteRequestXml.getVolumesTri().getTriCentVingtLitres());
      
        conflansMunicipalWasteRequest.setTriDeuxCentQuaranteLitres(conflansMunicipalWasteRequestXml.getVolumesTri().getTriDeuxCentQuaranteLitres());
      
        conflansMunicipalWasteRequest.setTriSixCentSoixanteLitres(conflansMunicipalWasteRequestXml.getVolumesTri().getTriSixCentSoixanteLitres());
      
        conflansMunicipalWasteRequest.setTriTroisCentQuaranteLitres(conflansMunicipalWasteRequestXml.getVolumesTri().getTriTroisCentQuaranteLitres());
      
        if (conflansMunicipalWasteRequestXml.getTypeHabitation() != null)
            conflansMunicipalWasteRequest.setTypeHabitation(org.libredemat.business.request.environment.CmwrTypeHabitationType.forString(conflansMunicipalWasteRequestXml.getTypeHabitation().toString()));
        else
            conflansMunicipalWasteRequest.setTypeHabitation(org.libredemat.business.request.environment.CmwrTypeHabitationType.getDefaultCmwrTypeHabitationType());
      
        conflansMunicipalWasteRequest.setVerreCentVingtLitres(conflansMunicipalWasteRequestXml.getVolumesVerre().getVerreCentVingtLitres());
      
        conflansMunicipalWasteRequest.setVerreDeuxCentQuaranteLitres(conflansMunicipalWasteRequestXml.getVolumesVerre().getVerreDeuxCentQuaranteLitres());
      
        conflansMunicipalWasteRequest.setVerreTrenteCinqLitres(conflansMunicipalWasteRequestXml.getVolumesVerre().getVerreTrenteCinqLitres());
      
        return conflansMunicipalWasteRequest;
    }

    @Override
    public ConflansMunicipalWasteRequest clone() {
        ConflansMunicipalWasteRequest clone = new ConflansMunicipalWasteRequest(getRequestData().clone(), conflansMunicipalWasteRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("informations", stepState);
        
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
          clone.getStepStates().put("administration", stepState);
        
        return clone;
    }

  
    public final void setAdresseOrganisation(final org.libredemat.business.users.Address adresseOrganisation) {
        conflansMunicipalWasteRequestData.setAdresseOrganisation(adresseOrganisation);
    }

    
    public final org.libredemat.business.users.Address getAdresseOrganisation() {
        return conflansMunicipalWasteRequestData.getAdresseOrganisation();
    }
  
    public final void setComposteurGrand(final java.math.BigInteger composteurGrand) {
        conflansMunicipalWasteRequestData.setComposteurGrand(composteurGrand);
    }

    
    public final java.math.BigInteger getComposteurGrand() {
        return conflansMunicipalWasteRequestData.getComposteurGrand();
    }
  
    public final void setComposteurPetit(final java.math.BigInteger composteurPetit) {
        conflansMunicipalWasteRequestData.setComposteurPetit(composteurPetit);
    }

    
    public final java.math.BigInteger getComposteurPetit() {
        return conflansMunicipalWasteRequestData.getComposteurPetit();
    }
  
    public final void setConteneur(final List<org.libredemat.business.request.LocalReferentialData> conteneur) {
        conflansMunicipalWasteRequestData.setConteneur(conteneur);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getConteneur() {
        return conflansMunicipalWasteRequestData.getConteneur();
    }
  
    public final void setNomOrganisation(final String nomOrganisation) {
        conflansMunicipalWasteRequestData.setNomOrganisation(nomOrganisation);
    }

    
    public final String getNomOrganisation() {
        return conflansMunicipalWasteRequestData.getNomOrganisation();
    }
  
    public final void setNombreResidants(final String nombreResidants) {
        conflansMunicipalWasteRequestData.setNombreResidants(nombreResidants);
    }

    
    public final String getNombreResidants() {
        return conflansMunicipalWasteRequestData.getNombreResidants();
    }
  
    public final void setOmCentVingtLitres(final java.math.BigInteger omCentVingtLitres) {
        conflansMunicipalWasteRequestData.setOmCentVingtLitres(omCentVingtLitres);
    }

    
    public final java.math.BigInteger getOmCentVingtLitres() {
        return conflansMunicipalWasteRequestData.getOmCentVingtLitres();
    }
  
    public final void setOmDeuxCentQuaranteLitres(final java.math.BigInteger omDeuxCentQuaranteLitres) {
        conflansMunicipalWasteRequestData.setOmDeuxCentQuaranteLitres(omDeuxCentQuaranteLitres);
    }

    
    public final java.math.BigInteger getOmDeuxCentQuaranteLitres() {
        return conflansMunicipalWasteRequestData.getOmDeuxCentQuaranteLitres();
    }
  
    public final void setOmSixCentSoixanteLitres(final java.math.BigInteger omSixCentSoixanteLitres) {
        conflansMunicipalWasteRequestData.setOmSixCentSoixanteLitres(omSixCentSoixanteLitres);
    }

    
    public final java.math.BigInteger getOmSixCentSoixanteLitres() {
        return conflansMunicipalWasteRequestData.getOmSixCentSoixanteLitres();
    }
  
    public final void setOmTroisCentQuaranteLitres(final java.math.BigInteger omTroisCentQuaranteLitres) {
        conflansMunicipalWasteRequestData.setOmTroisCentQuaranteLitres(omTroisCentQuaranteLitres);
    }

    
    public final java.math.BigInteger getOmTroisCentQuaranteLitres() {
        return conflansMunicipalWasteRequestData.getOmTroisCentQuaranteLitres();
    }
  
    public final void setPrecisionsReparation(final String precisionsReparation) {
        conflansMunicipalWasteRequestData.setPrecisionsReparation(precisionsReparation);
    }

    
    public final String getPrecisionsReparation() {
        return conflansMunicipalWasteRequestData.getPrecisionsReparation();
    }
  
    public final void setProfilDemandeur(final org.libredemat.business.request.environment.CmwrProfilDemandeurType profilDemandeur) {
        conflansMunicipalWasteRequestData.setProfilDemandeur(profilDemandeur);
    }

    
    public final org.libredemat.business.request.environment.CmwrProfilDemandeurType getProfilDemandeur() {
        return conflansMunicipalWasteRequestData.getProfilDemandeur();
    }
  
    public final void setQuartier(final List<org.libredemat.business.request.LocalReferentialData> quartier) {
        conflansMunicipalWasteRequestData.setQuartier(quartier);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getQuartier() {
        return conflansMunicipalWasteRequestData.getQuartier();
    }
  
    public final void setTriCentVingtLitres(final java.math.BigInteger triCentVingtLitres) {
        conflansMunicipalWasteRequestData.setTriCentVingtLitres(triCentVingtLitres);
    }

    
    public final java.math.BigInteger getTriCentVingtLitres() {
        return conflansMunicipalWasteRequestData.getTriCentVingtLitres();
    }
  
    public final void setTriDeuxCentQuaranteLitres(final java.math.BigInteger triDeuxCentQuaranteLitres) {
        conflansMunicipalWasteRequestData.setTriDeuxCentQuaranteLitres(triDeuxCentQuaranteLitres);
    }

    
    public final java.math.BigInteger getTriDeuxCentQuaranteLitres() {
        return conflansMunicipalWasteRequestData.getTriDeuxCentQuaranteLitres();
    }
  
    public final void setTriSixCentSoixanteLitres(final java.math.BigInteger triSixCentSoixanteLitres) {
        conflansMunicipalWasteRequestData.setTriSixCentSoixanteLitres(triSixCentSoixanteLitres);
    }

    
    public final java.math.BigInteger getTriSixCentSoixanteLitres() {
        return conflansMunicipalWasteRequestData.getTriSixCentSoixanteLitres();
    }
  
    public final void setTriTroisCentQuaranteLitres(final java.math.BigInteger triTroisCentQuaranteLitres) {
        conflansMunicipalWasteRequestData.setTriTroisCentQuaranteLitres(triTroisCentQuaranteLitres);
    }

    
    public final java.math.BigInteger getTriTroisCentQuaranteLitres() {
        return conflansMunicipalWasteRequestData.getTriTroisCentQuaranteLitres();
    }
  
    public final void setTypeHabitation(final org.libredemat.business.request.environment.CmwrTypeHabitationType typeHabitation) {
        conflansMunicipalWasteRequestData.setTypeHabitation(typeHabitation);
    }

    
    public final org.libredemat.business.request.environment.CmwrTypeHabitationType getTypeHabitation() {
        return conflansMunicipalWasteRequestData.getTypeHabitation();
    }
  
    public final void setVerreCentVingtLitres(final java.math.BigInteger verreCentVingtLitres) {
        conflansMunicipalWasteRequestData.setVerreCentVingtLitres(verreCentVingtLitres);
    }

    
    public final java.math.BigInteger getVerreCentVingtLitres() {
        return conflansMunicipalWasteRequestData.getVerreCentVingtLitres();
    }
  
    public final void setVerreDeuxCentQuaranteLitres(final java.math.BigInteger verreDeuxCentQuaranteLitres) {
        conflansMunicipalWasteRequestData.setVerreDeuxCentQuaranteLitres(verreDeuxCentQuaranteLitres);
    }

    
    public final java.math.BigInteger getVerreDeuxCentQuaranteLitres() {
        return conflansMunicipalWasteRequestData.getVerreDeuxCentQuaranteLitres();
    }
  
    public final void setVerreTrenteCinqLitres(final java.math.BigInteger verreTrenteCinqLitres) {
        conflansMunicipalWasteRequestData.setVerreTrenteCinqLitres(verreTrenteCinqLitres);
    }

    
    public final java.math.BigInteger getVerreTrenteCinqLitres() {
        return conflansMunicipalWasteRequestData.getVerreTrenteCinqLitres();
    }
  
}
