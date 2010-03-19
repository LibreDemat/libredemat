package fr.capwebct.capdemat.plugins.externalservices.cirilnetenfance.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.xpath.XPath;
import org.springframework.ws.soap.SoapElement;


import fr.cg95.cvq.exception.CvqException;



public class Registration {

    private static Logger logger = Logger.getLogger(Registration.class);
    private String endPoint;
    private XmlObject modelToXml;
    private String registrationType;
    
    public Registration() {
    }
    

    public Registration(String endPoint, XmlObject modelToXml, String registrationType) {
        super();
        this.endPoint = endPoint;
        this.modelToXml = modelToXml;
        this.registrationType = registrationType;
    }

    public HashMap<String, Object> getReturnRegistration() throws CvqException  {
        HashMap<String, Object> demandReturn = new HashMap<String, Object>();
        logger.debug("getReturnRegistration() endPoint : " + endPoint);
        try {
            URL url = new URL(endPoint);
            
            // creating connection object
            SOAPConnectionFactory scFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection con = scFactory.createConnection();
            
            // creating soap message
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope enveloppe = soapPart.getEnvelope();
            
            SOAPHeader header = enveloppe.getHeader();
            header.detachNode();
            // creating body of soap message
            org.w3c.dom.Document document = (org.w3c.dom.Document) modelToXml.newDomNode();
            
            DOMBuilder builder = new DOMBuilder();
            Document jdom = (Document) builder.build(document);
            Element racine = (Element) jdom.getRootElement();
                        
            SOAPBody body = enveloppe.getBody();
            Name bodyName = enveloppe.createName(registrationType,"q0","Ciril:Enfance:ServicesEnfance");
            SOAPElement gtl = body.addChildElement(bodyName);
            Name sRacine = enveloppe.createName(racine.getName());
            SOAPElement root = gtl.addChildElement(sRacine);
            
            listElements(racine, enveloppe, root);
            
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            message.writeTo(outStream);
            String envoi = outStream.toString("UTF-8");
            
            logger.debug("getReturnRegistration() message envoy� : " + envoi);            
            
            SOAPMessage response = con.call(message, url);
            
            
            // get r�ponse
            ByteArrayOutputStream outStream2 = new ByteArrayOutputStream();
            response.writeTo(outStream2);
            String reponse = outStream2.toString("UTF-8");
            logger.debug("Registration() message re�u : " + reponse);
            
            SOAPPart sp = response.getSOAPPart();
            SOAPEnvelope env = sp.getEnvelope();
            SOAPBody sb = env.getBody();
          
            // stack message response in Dom Document
            org.w3c.dom.Document result = sb.extractContentAsDocument();

            // close connection
            con.close();
            
            // convert Dom in JDOM because this API is better
            DOMBuilder builder2 = new DOMBuilder();
            Document repDoc = (Document) builder.build(result);
            
            XPath xpathError = XPath.newInstance("//message");
            Element nodeError = (Element) xpathError.selectSingleNode(repDoc);
            
            List<Attribute> attr = nodeError.getAttributes();
            String messageError = "";
            for(Attribute at : attr){
                if (at.getName().equals("state")){
                    // get state attribute equal error to stop process and catch error type
                    if (at.getValue().equals("error")){
                        messageError= nodeError.getChild("Error").getChild("Message").getText();
                    }
                }
            }                        
            if ( messageError.equals("")){
                if (registrationType.equals("SchoolRegistration")){
                    XPath xpath = XPath.newInstance("//SchoolName");
                    Element node = (Element) xpath.selectSingleNode(repDoc);
                    demandReturn.put("school", node.getText());
                }
                //  adding the externalid
                XPath xpathExtern = XPath.newInstance("//HomeFolderMapping");
                Element hfe = (Element) xpathExtern.selectSingleNode(repDoc);
                
                XPath xpathIndividuMap = XPath.newInstance("//IndividualMapping");
                List<Element>listIndividualMap = (List<Element>) xpathIndividuMap.selectNodes(repDoc);
                
                if( hfe != null){
                    logger.debug("homefoldermapping response : " +hfe.getChild("ExternalId").getText()); 
                    demandReturn.put("homeFolderMapping", hfe.getChild("ExternalId").getText());
                    HashMap<String, String> IndivdualMapping = new HashMap<String, String>();
                    logger.debug("individual mapping size : " + listIndividualMap);
                    if(listIndividualMap.size() > 0) { 
                        for (Element indMap : listIndividualMap){
                                IndivdualMapping.put(indMap.getChild("CapDematId").getText(), indMap.getChild("ExternalId").getText());
                        }                       
                        demandReturn.put("indMapp", IndivdualMapping);
                    }
                }
            } else {
                demandReturn.put("error", messageError);
            }
            
           
        } catch (JDOMException e) {
            throw new CvqException();
        } catch (MalformedURLException e) {
            throw new CvqException();
        } catch (SOAPException esoap) {
            esoap.printStackTrace();
            logger.debug(esoap.getMessage());
            throw new CvqException();
        } catch (IOException e) {
            throw new CvqException();
        }
        return demandReturn;
    }
    

    private void listElements(Element element, SOAPEnvelope SoapEnveloppe, SOAPElement soapElement) throws SOAPException {
        List<Element>  elements = (List<Element>) element.getChildren();
        for(Element elem : elements) {
            if(elem.getName().equals("Individuals")){
                Name child = SoapEnveloppe.createName(elem.getName());
                SOAPElement se = soapElement.addChildElement(child);
                setAttributes(elem.getAttributes(), SoapEnveloppe, se);
                se.addTextNode(elem.getText());
                List<Attribute> atts = (List<Attribute>) elem.getAttributes();
                Name child1 = null;
                Name attResp = null;
                String value = "";
               
                for(Attribute at : atts) {
                    if (at.getName().equals("type")) {
                        if (at.getValue().equals("com:ChildType")){
                            child1 = SoapEnveloppe.createName("Child");
                            
                        } else {
                            child1 = SoapEnveloppe.createName("Adult");
                            
                        }                            
                    }
                    if (at.getName().equals("isHomeFolderResponsible")){
                        attResp = SoapEnveloppe.createName(at.getName());
                        value = at.getValue();
                    }
                }
               
                SOAPElement ses = se.addChildElement(child1);
                if (attResp != null) {
                    ses.addAttribute(attResp, value);
                }
                
                listElements(elem, SoapEnveloppe, ses);
            } else {
                Name child = SoapEnveloppe.createName(elem.getName());
                SOAPElement se = soapElement.addChildElement(child);
                setAttributes(elem.getAttributes(), SoapEnveloppe, se);
                se.addTextNode(elem.getText());
                listElements(elem, SoapEnveloppe, se);
            }
        }
    }


    private void setAttributes(List<Attribute> attributes, SOAPEnvelope enveloppe, SOAPElement element) throws SOAPException {
        for(Attribute attribut : attributes){
            Name att = enveloppe.createName(attribut.getName());
            element.addAttribute(att, attribut.getValue());
        }
    }


    public final String getEndPoint() {
        return endPoint;
    }


    public final void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }


    public final XmlObject getModelToXml() {
        return modelToXml;
    }


    public final void setModelToXml(XmlObject modelToXml) {
        this.modelToXml = modelToXml;
    }
    
    
    public final String getRegistrationType() {
        return registrationType;
    }
    
    
    public final void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }
}
