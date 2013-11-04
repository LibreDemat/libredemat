package org.libredemat.plugins.externalservices.edemande.service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.keyvalue.UnmodifiableMapEntry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.libredemat.business.document.Document;
import org.libredemat.business.document.DocumentBinary;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestDocument;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.business.request.school.StudyGrantRequest;
import org.libredemat.business.request.social.BafaGrantRequest;
import org.libredemat.business.users.FrenchRIB;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqInvalidTransitionException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.service.document.IDocumentService;
import org.libredemat.service.request.IRequestDocumentService;
import org.libredemat.service.request.IRequestSearchService;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.service.request.external.IRequestExternalActionService;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere;
import org.libredemat.util.translation.ITranslationService;
import org.libredemat.xml.common.AddressType;
import org.libredemat.xml.request.school.StudyGrantRequestDocument;
import org.libredemat.xml.request.school.impl.StudyGrantRequestDocumentImpl.StudyGrantRequestImpl;
import org.libredemat.xml.request.social.BafaGrantRequestDocument;
import org.libredemat.xml.request.social.impl.BafaGrantRequestDocumentImpl.BafaGrantRequestImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.unilog.gda.edem.service.EnregistrerValiderFormulaireResponseDocument;
import com.unilog.gda.glob.service.GestionCompteResponseDocument;

import org.libredemat.plugins.externalservices.edemande.adapters.BafaGrantEdemandeRequest;
import org.libredemat.plugins.externalservices.edemande.adapters.EdemandeRequest;
import org.libredemat.plugins.externalservices.edemande.adapters.StudyGrantEdemandeRequest;
import org.libredemat.plugins.externalservices.edemande.webservice.client.IEdemandeClient;

/**
 * @deprecated 'til updated to fit the new request workflow.
 *
 * The main problem is with updateRequestState() which should only be called from a
 * {@link IWorkflowPostAction workflow post action}.
 * As it, this service can't be mixed with an other external service for managing a request workflow.
 */
public class EdemandeService extends ExternalProviderServiceAdapter {

    private static Logger logger = Logger.getLogger(EdemandeService.class);
    
    private String label;
    private IEdemandeClient edemandeClient;
    private IUserSearchService userSearchService;
    private IRequestExternalActionService requestExternalActionService;
    private IRequestSearchService requestSearchService;
    private IRequestDocumentService requestDocumentService;
    private IDocumentService documentService;
    private IRequestWorkflowService requestWorkflowService;
    private ITranslationService translationService;
    private IExternalHomeFolderService externalHomeFolderService;
    private IGenericDAO genericDAO;
    private EdemandeUploader uploader;

    private static final String SUBJECT_TRACE_SUBKEY = "subject";
    private static final String ACCOUNT_HOLDER_TRACE_SUBKEY = "accountHolder";
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private List<String> documentTypesToSend = Collections.emptyList();
    private int fileMaxSize = 4*1024*1024;

    @Override
    public String sendRequest(XmlObject requestXml) {
        EdemandeRequest request = adapt(requestXml);
        if (request == null) return null;
        String psCodeTiersAH = null;
        if (!request.isSubjectAccountHolder()) {
            psCodeTiersAH = request.getAccountHolderEdemandeId();
            if (psCodeTiersAH == null || psCodeTiersAH.trim().isEmpty()) {
              //Search the account holder : psCodeTiersAH is the external ID
                psCodeTiersAH = searchCases(request, request.getAccountHolderFirstName(),
                        request.getAccountHolderLastName(), request.getAccountHolderBirthDate(),
                        ACCOUNT_HOLDER_TRACE_SUBKEY);
                if (psCodeTiersAH == null || psCodeTiersAH.trim().isEmpty()) {
                    if (mustCreateAccountHolder(request)) {
                        createAccountHolder(request);
                    } else if (psCodeTiersAH != null) {
                        addTrace(request.getId(), ACCOUNT_HOLDER_TRACE_SUBKEY,
                            RequestExternalAction.Status.IN_PROGRESS,
                            "Le tiers viré n'est pas encore créé");
                    }
                    return null;
                } else {
                    request.setAccountHolderEdemandeId(psCodeTiersAH);
                    setAccountHolderEdemandeId(request, psCodeTiersAH);
                }
            }
        }
        String psCodeTiersS = request.getSubjectEdemandeId();
        if (psCodeTiersS == null || psCodeTiersS.trim().isEmpty()) {
            // external id (code tiers) not known locally : 
            //     either check if tiers has been created in eDemande
            //     either ask for its creation in eDemande
            psCodeTiersS = searchCases(request, request.getSubjectFirstName(),
                    request.getSubjectLastName(), request.getSubjectBirthDate(), SUBJECT_TRACE_SUBKEY);
            
            // add a "hack" condition when psCodeTiersS == psCodeTiersAH
            // to handle homonyms until individual search accepts birth date etc.
            if (psCodeTiersS == null || psCodeTiersS.trim().isEmpty() || psCodeTiersS.trim().equals(psCodeTiersAH)) {
                // tiers has not been created in eDemande ...
                if (mustCreateSubject(request)) {
                    // ... and no request in progress so ask for its creation
                    createSubject(request);
                } else if (psCodeTiersS != null) {
                    // eDemande answered since psCodeTiers is not null,
                    // and that means psCodeTiers is empty, so tiers
                    // has not been created yet.
                    // If psCodeTiers was null, that would mean searchIndividual
                    // caught an exception while contacting eDemande, and
                    // has already added a NOT_SENT trace.
                    // FIXME BOR : is this trace really needed ?
                    addTrace(request.getId(), SUBJECT_TRACE_SUBKEY,
                        RequestExternalAction.Status.IN_PROGRESS,
                        "Le tiers sujet n'est pas encore créé");
                }
                return null;
            } else {
                // tiers has been created in eDemande, store its code locally
                request.setSubjectEdemandeId(psCodeTiersS);
                externalHomeFolderService.setExternalId(label, request.getHomeFolderId(),
                    request.getSubjectId(), psCodeTiersS);
            }
        }
        
        // reaching this code means we have valid psCodeTiers (either because
        // they were already set since it is not the subject and account holder's first request, or because
        // searchIndividual returned the newly created tiers' psCodeTiers)
        // Try to get the external ID if we don't already know it
        String psCodeDemande = request.getEdemandeId();
        if (psCodeDemande == null || psCodeDemande.trim().isEmpty()) {
            psCodeDemande = searchRequest(request, psCodeTiersS);
            if (psCodeDemande != null && !psCodeDemande.trim().isEmpty() && !"-1".equals(psCodeDemande)) {
                request.setEdemandeId(psCodeDemande);
                setEdemandeId(request, psCodeDemande);
            }
        }
        // (Re)send request if needed
        if (mustSendNewRequest(request)) {
            submitRequest(request, psCodeTiersS, true);
        } else if (mustResendRequest(request)) {
            submitRequest(request, psCodeTiersS, false);
        }
        // check request status
        String msStatut = getRequestStatus(request, psCodeTiersS);
        if (msStatut == null) {
            // got an exception while contacting Edemande
            return null;
        }
        if (msStatut.trim().isEmpty()) {
            addTrace(request.getId(), null, RequestExternalAction.Status.NOT_SENT, 
                "La demande n'a pas encore été reçue");
            return null;
        }
        if ("En attente de réception par la collectivité".equals(msStatut)) {
            return null;
        } else if ("A compléter ou corriger".equals(msStatut) ||
            "A compléter".equals(msStatut) ||
            "En erreur".equals(msStatut)) {
            addTrace(request.getId(), null, RequestExternalAction.Status.ERROR, msStatut);
        } else if ("En cours d'analyse".equals(msStatut) ||
            "En attente d'avis externe".equals(msStatut) ||
            "En cours d'instruction".equals(msStatut)) {
            addTrace(request.getId(), null, RequestExternalAction.Status.ACKNOWLEDGED, msStatut);
        } else if ("Accepté".equals(msStatut) ||
            "En cours de paiement".equals(msStatut) ||
            "Payé partiellement".equals(msStatut) ||
            "Terminé".equals(msStatut)) {
            addTrace(request.getId(), null, RequestExternalAction.Status.ACCEPTED, msStatut);
            if ("Accepté".equals(msStatut))
                try {
                    requestWorkflowService.updateRequestState(request.getId(), RequestState.VALIDATED, null);
                } catch (CvqInvalidTransitionException e) {
                    logger.error("sendRequest() could not validate request " + request.getId() 
                            + " (" + e.getMessage() + ")"); 
                } catch (CvqObjectNotFoundException e) {
                    // unlikely to happen
                } catch (CvqException e) {
                    logger.error("sendRequest() unexpected error while validating request " + request.getId() 
                            + " (" + e.getMessage() + ")");
                }
        } else if ("Refusé".equals(msStatut)) {
            addTrace(request.getId(), null, RequestExternalAction.Status.REJECTED, msStatut);
            try {
                requestWorkflowService.updateRequestState(request.getId(), RequestState.REJECTED, null);
            } catch (CvqInvalidTransitionException e) {
                logger.error("sendRequest() could not reject request " + request.getId() 
                        + " (" + e.getMessage() + ")"); 
            } catch (CvqObjectNotFoundException e) {
                // unlikely to happen
            } catch (CvqException e) {
                logger.error("sendRequest() unexpected error while rejecting request " + request.getId() 
                        + " (" + e.getMessage() + ")");
            }
        }
        return null;
    }

    private void addTrace(Long requestId, String subkey, RequestExternalAction.Status status,
        String message) {
        Set<Critere> criteriaSet = new HashSet<Critere>(4);
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(requestId), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
            label, Critere.EQUALS));
        List<RequestExternalAction> actions = requestExternalActionService.getTraces(
            criteriaSet, RequestExternalAction.SEARCH_BY_DATE, "desc", 1, 0);
        RequestExternalAction est = actions.size() > 0 ? actions.get(0) : null;
        if (est != null && est.getStatus().equals(status)
            && StringUtils.equals(subkey, (String)est.getComplementaryData().get("individual"))) {
            est.setDate(new Date());
            est.setMessage(message);
            Integer count = (Integer)est.getComplementaryData().get("count");
            if (count == null) count = 2; else count++;
            est.getComplementaryData().put("count", count);
            genericDAO.update(est);
        } else {
            est = new RequestExternalAction();
            est.setDate(new Date());
            est.setKey(requestId);
            est.setKeyOwner("libredemat");
            est.setMessage(message);
            est.setName(label);
            est.setStatus(status);
            est.getComplementaryData().put("individual", subkey);
            requestExternalActionService.addTrace(est);
        }
        if (RequestExternalAction.Status.ERROR.equals(status)) {
            try {
                requestWorkflowService.updateRequestState(requestId, RequestState.UNCOMPLETE,
                    message);
            } catch (CvqObjectNotFoundException e) {
                // TODO
            } catch (CvqInvalidTransitionException e) {
                // TODO
            } catch (CvqException e) {
                // TODO
            }
        }
    }

    /**
     * Search for this request's individual in eDemande.
     * 
     * @return the individual's code in eDemande, an empty string if the individual is not found,
     * or null if there is an error while contacting eDemande.
     */
    private String searchIndividual(EdemandeRequest request, Map<String, Object> model, String filter) throws CvqException {
        String searchResults;
        int resultsNumber;
        //Launch client search
        searchResults =
                edemandeClient.rechercherTiers(escapeStrings(model))
                .getRechercherTiersResponse().getReturn();
        resultsNumber = parseDatas(searchResults,
                "//resultatRechTiers/listeTiers/tiers/codeTiers").size();
        
        //Return "" if no results
        if (resultsNumber == 0) {
            return "";
        }
        
        //Apply filter to check and get the right one
        for (int i = 1; i <= resultsNumber; i++) {
            try {
                //Get code tier
                String code =
                    parseData(searchResults,
                        "//resultatRechTiers/listeTiers/tiers[" + i
                        + "]/codeTiers");
                //Client call to get infos about tier 
                String informations = 
                    edemandeClient.initialiserFormulaire(request.getConfig().name, code)
                    .getInitialiserFormulaireResponse().getReturn();
               
                if (this.testData(informations, filter)) {
                    return code;
                }
            } catch (CvqException e) {
                continue;
            }
        }
        return "";
    }
    /**
     * Search by IBAN - Search by RIB - Search by LastName/FirstName/postalCode
     * @param request
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param subkey
     * @return Tiers exteranl ID
     */
    private String searchCases(EdemandeRequest request, String firstName, String lastName,
            Calendar birthDate, String subkey) {
        
        try {
            //Bank account is mondatory
            if (request.getFrBankAccount().getIBAN() == null) {
                addTrace(request.getId(), subkey, RequestExternalAction.Status.NOT_SENT, "Coordonnées bancaires non renseignées");
                return null;
            }
            //Search by IBAN
            Map<String, Object> modelIBAN = new HashMap<String, Object>();
            modelIBAN.put("numero", request.getFrBankAccount().getIBAN());
            modelIBAN.put("type", "IBAN");
            modelIBAN.put("lastName", "");
            modelIBAN.put("firstName", "");
            modelIBAN.put("postalCode", "");
            String filterNP = new StringBuffer("(upper-case(/CBdosInitFormulaireBean/moTierInit/msPrenom)='")
            .append(StringUtils.upperCase(firstName))
            .append("' or upper-case(/CBdosInitFormulaireBean/moTierInit/msPrenom)='")
            .append(StringUtils.upperCase(this.escapeFirstName(firstName)))
            .append("') and (upper-case(/CBdosInitFormulaireBean/moTierInit/msNom)='")
            .append(StringUtils.upperCase(lastName))
            .append("' or upper-case(/CBdosInitFormulaireBean/moTierInit/msNom)='")
            .append(StringUtils.upperCase(this.escapeLastName(lastName)))
            .append("')").toString();
            String resultIBAN = this.searchIndividual(request, modelIBAN, filterNP);
            if(!"".equals(resultIBAN)) return resultIBAN;
            //Search by RIB
            Map<String, Object> modelRIB = new HashMap<String, Object>();
            modelRIB.put("numero", FrenchRIB.xmlToModel(request.getFrenchRIB()).format(" "));
            modelRIB.put("type", "RIB");
            modelRIB.put("lastName", "");
            modelRIB.put("firstName", "");
            modelRIB.put("postalCode", "");
            String resultRIB = this.searchIndividual(request, modelRIB, filterNP);
            if(!"".equals(resultRIB)) return resultRIB;
            //Search by lastName/firtName/postalCode
            if(SUBJECT_TRACE_SUBKEY.equals(subkey)) {
                Map<String, Object> modelNPC = new HashMap<String, Object>();
                modelNPC.put("numero", "");
                modelNPC.put("type", "");
                modelNPC.put("lastName", StringUtils.upperCase(lastName));
                modelNPC.put("firstName", firstName);
                modelNPC.put("postalCode", request.getSubjectAddress().getPostalCode());
                String filterBD = "/CBdosInitFormulaireBean/moTierInit/mdtDateNaissance='"+new SimpleDateFormat("yyyy-MM-dd").format(birthDate.getTime())+"'";
                String resultNPC = this.searchIndividual(request, modelNPC, filterBD);
                if(!"".equals(resultNPC)) return resultNPC;
                //Search by lastName/escaped(firtName)/postalCode
                if(!firstName.equalsIgnoreCase(this.escapeFirstName(firstName))) {
                    Map<String, Object> modelNPC_ = new HashMap<String, Object>();
                    modelNPC_.put("numero", "");
                    modelNPC_.put("type", "");
                    modelNPC_.put("lastName", StringUtils.upperCase(lastName));
                    modelNPC_.put("firstName", this.escapeFirstName(firstName));
                    modelNPC_.put("postalCode", "");
                    String resultNPC_ = this.searchIndividual(request, modelNPC_, filterBD);
                    if(!"".equals(resultNPC_)) return resultNPC_;
                }
            }
            
        } catch (CvqException e) {
            addTrace(request.getId(), subkey, RequestExternalAction.Status.NOT_SENT, e.getMessage());
            return null;
        }
        return "";
        
    }
    

    private void createSubject(EdemandeRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("lastName", StringUtils.upperCase(request.getSubjectLastName()));
        model.put("address", request.getSubjectAddress());
        if (!StringUtils.isBlank(request.getSubjectPhone())) {
            model.put("phone", request.getSubjectPhone());
        }
        model.put("title",
            translationService.translate("homeFolder.adult.title."
                + request.getSubjectTitle().toString().toLowerCase(), Locale.FRANCE));
        model.put("firstName", StringUtils.capitalize(request.getSubjectFirstName()));
        model.put("birthPlace", StringUtils.defaultString(request.getSubjectBirthCity()));
        model.put("birthDate", formatDate(request.getSubjectBirthDate()));
        model.put("iban", request.getFrBankAccount().getIBAN());
        model.put("bic", request.getFrBankAccount().getBIC());
        try {
            model.put("email", StringUtils.defaultIfEmpty(request.getSubjectEmail(),
                userSearchService.getHomeFolderResponsible(request.getHomeFolderId()).getEmail()));
            GestionCompteResponseDocument response =
                edemandeClient.creerTiers(escapeStrings(model));
            if (!"0".equals(parseData(response.getGestionCompteResponse().getReturn(), "//Retour/codeRetour"))) {
                addTrace(request.getId(), SUBJECT_TRACE_SUBKEY, RequestExternalAction.Status.ERROR,
                    parseData(response.getGestionCompteResponse().getReturn(),
                        "//Retour/messageRetour"));
            } else {
                addTrace(request.getId(), SUBJECT_TRACE_SUBKEY,
                    RequestExternalAction.Status.IN_PROGRESS, "Demande de création du tiers sujet");
            }
        } catch (CvqException e) {
            e.printStackTrace();
            addTrace(request.getId(), SUBJECT_TRACE_SUBKEY, RequestExternalAction.Status.NOT_SENT,
                e.getMessage());
        }
    }

    private void createAccountHolder(EdemandeRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", translationService.translate("homeFolder.adult.title."
            + request.getAccountHolderTitle().toString().toLowerCase(), Locale.FRANCE));
        model.put("lastName", StringUtils.upperCase(request.getAccountHolderLastName()));
        //FIXME placeholders; are these really needed ?
        model.put("address", request.getSubjectAddress());
        model.put("phone", "");
        model.put("birthPlace", "");
        //ENDFIXME
        model.put("firstName", StringUtils.capitalize(request.getAccountHolderFirstName()));
        model.put("birthDate", formatDate(request.getAccountHolderBirthDate()));
        model.put("iban", request.getFrBankAccount().getIBAN());
        model.put("bic", request.getFrBankAccount().getBIC());
      
        try {
            //FIXME placeholder
            model.put("email",
                userSearchService.getHomeFolderResponsible(request.getHomeFolderId()).getEmail());
            GestionCompteResponseDocument response =
                edemandeClient.creerTiers(escapeStrings(model));
            if (!"0".equals(parseData(response.getGestionCompteResponse().getReturn(), "//Retour/codeRetour"))) {
                addTrace(request.getId(), ACCOUNT_HOLDER_TRACE_SUBKEY,
                    RequestExternalAction.Status.ERROR,
                    parseData(response.getGestionCompteResponse().getReturn(),
                        "//Retour/messageRetour"));
            } else {
                addTrace(request.getId(), ACCOUNT_HOLDER_TRACE_SUBKEY,
                    RequestExternalAction.Status.IN_PROGRESS, "Demande de création du tiers viré");
            }
        } catch (CvqException e) {
            e.printStackTrace();
            addTrace(request.getId(), ACCOUNT_HOLDER_TRACE_SUBKEY,
                RequestExternalAction.Status.NOT_SENT, e.getMessage());
        }
    }

    private void submitRequest(EdemandeRequest request, String psCodeTiers, boolean firstSending) {
        Map<String, Object> model = new HashMap<String, Object>();
        String requestData = null;
        if (!firstSending) {
            try {
                requestData = edemandeClient.chargerDemande(psCodeTiers, request.getEdemandeId()).getChargerDemandeResponse().getReturn();
            } catch (CvqException e) {
                e.printStackTrace();
                addTrace(request.getId(), null, RequestExternalAction.Status.NOT_SENT,
                    e.getMessage());
            }
        }
        model.put("externalRequestId", buildExternalRequestId(request));
        model.put("psCodeTiers", psCodeTiers);
        model.put("psCodeDemande",
            StringUtils.defaultIfEmpty(request.getEdemandeId(), "-1"));
        model.put("etatCourant", firstSending ? 2 : 1);
        model.put("firstName", StringUtils.capitalize(request.getSubjectFirstName()));
        model.put("lastName", StringUtils.upperCase(request.getSubjectLastName()));
        model.put("address", request.getSubjectAddress());
        if (!StringUtils.isBlank(request.getSubjectPhone())) {
            model.put("phone", request.getSubjectPhone());
        }
        model.put("iban", request.getFrBankAccount().getIBAN());
        model.put("bic", request.getFrBankAccount().getBIC());
        model.put("frenchRIB", FrenchRIB.xmlToModel(request.getFrenchRIB()).format(" "));
        model.put("creationDate", formatDate(request.getCreationDate()));
        List<Map<String, String>> documents = new ArrayList<Map<String, String>>();
        model.put("documents", documents);
        try {
            for (RequestDocument requestDoc : requestDocumentService.getAssociatedDocuments(request.getId())) {
                Document document = documentService.getById(requestDoc.getDocumentId());
                for (String documentTypeToSend : documentTypesToSend) {
                    if (documentTypeToSend.equals(document.getDocumentType().getType().toString())) {
                        int i = 1;
                        for (DocumentBinary documentBinary : document.getDatas()) {
                            if (documentBinary.getData().length > fileMaxSize) {
                                continue;
                            }
                            Map<String, String> doc = new HashMap<String, String>();
                            documents.add(doc);
                            String filename = org.springframework.util.StringUtils.arrayToDelimitedString(
                                new String[] {
                                    "LibreDemat", document.getDocumentType().getName(),
                                    String.valueOf(request.getId()), String.valueOf(i++)
                                }, "-");
                            doc.put("filename", filename);
                            doc.put("label", "documentType." +
                                document.getDocumentType().getName().replaceAll(" ", "")
                                    .toLowerCase());
                            try {
                                doc.put("remotePath", uploader.upload(filename, documentBinary.getData()));
                            } catch (JSchException e) {
                                addTrace(request.getId(), null, RequestExternalAction.Status.ERROR,
                                    "Erreur à l'envoi d'une pièce jointe");
                            } catch (SftpException e) {
                                addTrace(request.getId(), null, RequestExternalAction.Status.ERROR,
                                    "Erreur à l'envoi d'une pièce jointe");
                            }
                        }
                        break;
                    }
                }
            }
            model.put("email", StringUtils.defaultIfEmpty(request.getSubjectEmail(),
                userSearchService.getHomeFolderResponsible(request.getHomeFolderId()).getEmail()));
            model.put("msStatut", firstSending ? "" :
                getRequestStatus(request, psCodeTiers));
            model.put("millesime", firstSending ? "" :
                parseData(requestData, "//donneesDemande/Demande/miMillesime"));
            model.put("msCodext", firstSending ? "" :
                parseData(requestData, "//donneesDemande/Demande/msCodext"));
            model.put("requestTypeCode",
                parseData(edemandeClient.chargerTypeDemande(request.getConfig().name).getChargerTypeDemandeResponse().getReturn(), "//typeDemande/code"));
            model.put("config", request.getConfig());
            model.putAll(request.getSpecificFields(this));
            EnregistrerValiderFormulaireResponseDocument
                enregistrerValiderFormulaireResponseDocument =
                    edemandeClient.enregistrerValiderFormulaire(escapeStrings(model));
            if (!"0".equals(parseData(enregistrerValiderFormulaireResponseDocument.getEnregistrerValiderFormulaireResponse().getReturn(), "//Retour/codeRetour"))) {
                addTrace(request.getId(), null, RequestExternalAction.Status.ERROR, parseData(
                    enregistrerValiderFormulaireResponseDocument
                        .getEnregistrerValiderFormulaireResponse().getReturn(),
                    "//Retour/messageRetour"));
            } else {
                addTrace(request.getId(), null, RequestExternalAction.Status.SENT,
                    "Demande transmise");
            }
        } catch (CvqException e) {
            e.printStackTrace();
            addTrace(request.getId(), null, RequestExternalAction.Status.NOT_SENT, e.getMessage());
        }
    }

    private String searchRequest(EdemandeRequest request, String psCodeTiers) {
        try {
            return parseData(edemandeClient.rechercheDemandesTiers(psCodeTiers)
                .getRechercheDemandesTiersResponse().getReturn(),
                "//resultatRechDemandes/listeDemandes/Demande/moOrigineApsect[msIdentifiant ='"
                + buildExternalRequestId(request) + "']/../miCode");
        } catch (CvqException e) {
            addTrace(request.getId(), null, RequestExternalAction.Status.NOT_SENT, e.getMessage());
            return null;
        }
    }

    private String getRequestStatus(EdemandeRequest request, String psCodeTiers) {
        try {
            if (request.getEdemandeId() == null || request.getEdemandeId().trim().isEmpty()) {
                return parseData(edemandeClient.rechercheDemandesTiers(psCodeTiers)
                    .getRechercheDemandesTiersResponse().getReturn(),
                    "//resultatRechDemandes/listeDemandes/Demande/moOrigineApsect[msIdentifiant ='"
                        + buildExternalRequestId(request) + "']/../msStatut");
            } else {
                return parseData(edemandeClient.chargerDemande(psCodeTiers, request.getEdemandeId())
                        .getChargerDemandeResponse().getReturn(),
                        "//donneesDemande/Demande/msStatut");
            }
        } catch (CvqException e) {
            addTrace(request.getId(), null, RequestExternalAction.Status.NOT_SENT, e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> checkExternalReferential(final XmlObject requestXml) {
        EdemandeRequest request = adapt(requestXml);
        List<String> result = new ArrayList<String>();
        try {
            String postalCodeAndCityCheck = edemandeClient.existenceCommunePostale(request.getSubjectAddress().getPostalCode(), request.getSubjectAddress().getCity()).getExistenceCommunePostaleResponse().getReturn();
            if (!"0".equals(parseData(postalCodeAndCityCheck, "//FluxWebService/msCodeRet"))) {
                result.add(parseData(postalCodeAndCityCheck, "//FluxWebService/erreur/message"));
            }
            String bankInformationsCheck = edemandeClient.verifierRIB(request.getFrenchRIB()).getVerifierRIBResponse().getReturn();
            if (!"0".equals(parseData(bankInformationsCheck, "//FluxWebService/msCodeRet"))) {
                result.add(parseData(bankInformationsCheck, "//FluxWebService/erreur/message"));
            }
        } catch (CvqException e) {
            result.add("Impossible de contacter Edemande");
        }
        return result;
    }

    @Override
    public Map<String, Object> loadExternalInformations(XmlObject requestXml)
        throws CvqException {
        EdemandeRequest rqt = adapt(requestXml);
        if (StringUtils.isBlank(rqt.getSubjectEdemandeId())
            || StringUtils.isBlank(rqt.getEdemandeId())) {
            return Collections.emptyMap();
        }
        Map<String, Object> informations = new TreeMap<String, Object>();
        String request =
            edemandeClient.chargerDemande(rqt.getSubjectEdemandeId(), rqt.getEdemandeId())
                .getChargerDemandeResponse().getReturn();
        String status = getRequestStatus(rqt, rqt.getSubjectEdemandeId());
        if (status != null && !status.trim().isEmpty()) {
            informations.put("sgr.property.externalStatus", status);
        }
        String grantedAmount = parseData(request, "//donneesDemande/Demande/mdMtAccorde");
        if (grantedAmount != null && !grantedAmount.trim().isEmpty()) {
            informations.put("sgr.property.grantedAmount",
                new DecimalFormat(translationService.translate("format.currency"))
                .format(new BigDecimal(grantedAmount)));
        }
        String paidAmount = parseData(request, "//donneesDemande/Demande/mdMtRealise");
        if (paidAmount != null && !paidAmount.trim().isEmpty()) {
            informations.put("sgr.property.paidAmount",
                new DecimalFormat(translationService.translate("format.currency"))
                .format(new BigDecimal(paidAmount)));
        }
        return informations;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setEdemandeClient(IEdemandeClient edemandeClient) {
        this.edemandeClient = edemandeClient;
    }

    @Override
    public boolean supportsConsumptions() {
        return false;
    }

    @Override
    public boolean handlesTraces() {
        return true;
    }

    private String parseData(String returnElement, String path)
        throws CvqException {
        try {
            return new DOMXPath(path)
                .stringValueOf(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(returnElement)))
                        .getDocumentElement());
        } catch (JaxenException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (SAXException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        }
    }
    
    
    private boolean testData(String returnElement, String path)
            throws CvqException {
            try {   
                return new DOMXPath(path)
                    .booleanValueOf(
                        DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .parse(new InputSource(new StringReader(returnElement)))
                            .getDocumentElement());
            } catch (JaxenException e) {
                e.printStackTrace();
                throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
            } catch (SAXException e) {
                e.printStackTrace();
                throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
            } catch (IOException e) {
                e.printStackTrace();
                throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
            }
        }

    /**
     * Same as {@link #parseData(String, String)}
     * but selects all matching elements
     */
    private List<?> parseDatas(String returnElement, String path)
        throws CvqException {
        try {
            return new DOMXPath(path)
                .selectNodes(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(returnElement)))
                        .getDocumentElement());
        } catch (JaxenException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (SAXException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new CvqException("Erreur lors de la lecture de la réponse du service externe");
        }
    }

    private String buildExternalRequestId(EdemandeRequest request) {
        return org.springframework.util.StringUtils.arrayToDelimitedString(
            new String[] {
                "LibreDemat",
                new SimpleDateFormat("yyyyMMdd").format(new Date(request.getCreationDate().getTimeInMillis())),
                String.valueOf(request.getId())
            },
            "-");
    }

    /**
     * Whether or not we have to send the request.
     * 
     * @return true if the request has no SENT trace (it has never been successfully sent)
     * or it has an error trace and no Edemande ID (it was sent and received, but rejected and must be sent as new)
     */
    private boolean mustSendNewRequest(EdemandeRequest request) {
        Set<Critere> criteriaSet = new HashSet<Critere>(3);
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
            label, Critere.EQUALS));
        criteriaSet.add(new Critere(
            RequestExternalAction.SEARCH_BY_STATUS, RequestExternalAction.Status.SENT,
            Critere.EQUALS));
        if (requestExternalActionService.getTracesCount(criteriaSet) == 0)
            return true;
        criteriaSet.clear();
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
            label, Critere.EQUALS));
        criteriaSet.add(new Critere(
            RequestExternalAction.SEARCH_BY_STATUS, RequestExternalAction.Status.ERROR,
            Critere.EQUALS));
        return (requestExternalActionService.getTracesCount(criteriaSet) != 0
            && (request.getEdemandeId() == null || request.getEdemandeId().trim().isEmpty()));
    }

    /**
     * Whether or not we have to resend the request.
     * 
     * @return true if the request has an Edemande ID (so it was already sent),
     * and an ERROR trace not followed by a SENT trace
     */
    private boolean mustResendRequest(EdemandeRequest request) {
        Set<Critere> criteriaSet = new HashSet<Critere>(3);
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
            label, Critere.EQUALS));
        criteriaSet.add(new Critere(
            RequestExternalAction.SEARCH_BY_STATUS, RequestExternalAction.Status.ERROR,
            Critere.EQUALS));
        if (requestExternalActionService.getTracesCount(criteriaSet) == 0
            || request.getEdemandeId() == null || request.getEdemandeId().trim().isEmpty()) {
            return false;
        }
        Set<Critere> criteres = new HashSet<Critere>();
        criteres.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteres.add(new Critere(RequestExternalAction.SEARCH_BY_NAME, label,
            Critere.EQUALS));
        for (RequestExternalAction est : requestExternalActionService.getTraces(criteres,
            RequestExternalAction.SEARCH_BY_DATE, "desc", 0, 0)) {
            if (RequestExternalAction.Status.SENT.equals(est.getStatus())) {
                return false;
            } else if (RequestExternalAction.Status.ERROR.equals(est.getStatus())) {
                return true;
            }
        }
        // we should never execute the next line :
        // the above loop should have found a SENT trace and returned false,
        // or found the ERROR trace that IS in the list
        // (otherwise the first test would have succeded)
        // however, for compilation issues (and an hypothetic concurrent traces deletion)
        // we return false, to do nothing rather than doing something wrong
        return false;
    }

    /**
     * Determines if we must send an individual creation request for the request's subject
     * or account holder when this individual has no psCodeTiers yet.
     */
    private boolean mustCreateIndividual(EdemandeRequest request, String subkey) {
        Set<Critere> criteriaSet = new HashSet<Critere>(4);
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_COMPLEMENTARY_DATA,
            new UnmodifiableMapEntry("individual", subkey), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_NAME,
            label, Critere.EQUALS));
        criteriaSet.add(new Critere(
            RequestExternalAction.SEARCH_BY_STATUS, RequestExternalAction.Status.IN_PROGRESS,
            Critere.EQUALS));
        if (requestExternalActionService.getTracesCount(criteriaSet) == 0) {
            return true;
        }
        Set<Critere> criteres = new HashSet<Critere>();
        criteres.add(new Critere(RequestExternalAction.SEARCH_BY_KEY,
            String.valueOf(request.getId()), Critere.EQUALS));
        criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_COMPLEMENTARY_DATA,
            new UnmodifiableMapEntry("individual", subkey), Critere.EQUALS));
        criteres.add(new Critere(RequestExternalAction.SEARCH_BY_NAME, label,
            Critere.EQUALS));
        for (RequestExternalAction est : requestExternalActionService.getTraces(criteres,
            RequestExternalAction.SEARCH_BY_DATE, "desc", 0, 0)) {
            if (RequestExternalAction.Status.IN_PROGRESS.equals(est.getStatus())) {
                return false;
            } else if (RequestExternalAction.Status.ERROR.equals(est.getStatus())) {
                return true;
            }
        }
        return false;
    }

    public String formatDate(Calendar calendar) {
        if (calendar == null) return "";
        return formatter.format(new Date(calendar.getTimeInMillis()));
    }

    public String translate(String code) {
        return translationService.translate(code, Locale.FRANCE);
    }

    private boolean mustCreateAccountHolder(EdemandeRequest request) {
        return mustCreateIndividual(request, ACCOUNT_HOLDER_TRACE_SUBKEY);
    }

    private boolean mustCreateSubject(EdemandeRequest request) {
        return mustCreateIndividual(request, SUBJECT_TRACE_SUBKEY);
    }

    private Map<String, Object> escapeStrings(Map<String, Object> model) {
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (entry.getValue() instanceof String) {
                entry.setValue(
                    StringEscapeUtils.escapeXml((String)entry.getValue()));
            } else if (entry.getValue() instanceof AddressType) {
                AddressType addressType = (AddressType)entry.getValue();
                Map<String, String> address = new HashMap<String, String>();
                address.put("additionalDeliveryInformation", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getAdditionalDeliveryInformation())));
                address.put("additionalGeographicalInformation", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(
                        addressType.getAdditionalGeographicalInformation())));
                address.put("city", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getCity())));
                address.put("countryName", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getCountryName())));
                address.put("placeNameOrService", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getPlaceNameOrService())));
                address.put("postalCode", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getPostalCode())));
                address.put("streetName", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getStreetName())));
                address.put("streetNumber", StringUtils.defaultString(
                    StringEscapeUtils.escapeXml(addressType.getStreetNumber())));
                entry.setValue(address);
            }
        }
        return model;
    }

    private String escapeName(String name) {
        return Normalizer.normalize(name.replaceAll("-", " ").replaceAll("'", ""),
            Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]","");
    }

    private String escapeFirstName(String firstName) {
        return escapeName(WordUtils.capitalizeFully(firstName, new char[]{' ', '-'}));
    }

    private String escapeLastName(String lastName) {
        return StringUtils.upperCase(escapeName(lastName));
    }

    private EdemandeRequest adapt(XmlObject requestXml) {
        if (requestXml instanceof StudyGrantRequestDocument) {
            return new StudyGrantEdemandeRequest((StudyGrantRequestImpl)((StudyGrantRequestDocument)requestXml).getStudyGrantRequest());
        } else if (requestXml instanceof StudyGrantRequestImpl) {
            return new StudyGrantEdemandeRequest((StudyGrantRequestImpl) requestXml);
        } else if (requestXml instanceof BafaGrantRequestDocument) {
            return new BafaGrantEdemandeRequest((BafaGrantRequestImpl)((BafaGrantRequestDocument)requestXml).getBafaGrantRequest());
        } else if (requestXml instanceof BafaGrantRequestImpl) {
            return new BafaGrantEdemandeRequest((BafaGrantRequestImpl) requestXml);
        } else {
            return null;
        }
    }

    private void setEdemandeId(EdemandeRequest request, String psCodeDemande) {
        Request rqt = requestSearchService.getById(request.getId(), true);
        if (request instanceof StudyGrantEdemandeRequest) {
            ((StudyGrantRequest)rqt).setEdemandeId(psCodeDemande);
        } else if (request instanceof BafaGrantEdemandeRequest) {
            ((BafaGrantRequest)rqt).setEdemandeId(psCodeDemande);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setAccountHolderEdemandeId(EdemandeRequest request, String psCodeTiersAH) {
        Request rqt = requestSearchService.getById(request.getId(), true);
        if (request instanceof StudyGrantEdemandeRequest) {
            ((StudyGrantRequest)rqt).setAccountHolderEdemandeId(psCodeTiersAH);
        } else if (request instanceof BafaGrantEdemandeRequest) {
            ((BafaGrantRequest)rqt).setAccountHolderEdemandeId(psCodeTiersAH);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    

    @Override
    public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
        throws CvqConfigurationException {
        List<String> documentTypesToSend =
            (List<String>)externalServiceBean.getProperty("documentTypesToSend");
        if (documentTypesToSend != null) {
            this.documentTypesToSend = documentTypesToSend;
        }
        try {
            fileMaxSize = Integer.parseInt((String)externalServiceBean.getProperty("fileMaxSize"));
        } catch (NumberFormatException e) {
            // nothing to do
        }
    }

    @Override
    public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems,
            String cvqReference, String bankReference, Long homeFolderId,
            String externalHomeFolderId, String externalId, Date validationDate)
            throws CvqException {
    }

    @Override
    public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId,
            String externalHomeFolderId, String externalId) throws CvqException {
        return null;
    }

    @Override
    public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo)
            throws CvqException {
        return null;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String helloWorld() throws CvqException {
        return null;
    }

    @Override
    public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException {
    }

    @Override
    public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException {
    }

    public void setRequestSearchService(IRequestSearchService requestSearchService) {
        this.requestSearchService = requestSearchService;
    }

    public void setDocumentService(IDocumentService documentService) {
        this.documentService = documentService;
    }

    public void setUserSearchService(IUserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public void setRequestExternalActionService(IRequestExternalActionService requestExternalActionService) {
        this.requestExternalActionService = requestExternalActionService;
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }

    public void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        this.requestWorkflowService = requestWorkflowService;
    }

    public void setRequestDocumentService(IRequestDocumentService requestDocumentService) {
        this.requestDocumentService = requestDocumentService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setUploader(EdemandeUploader uploader) {
        this.uploader = uploader;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
