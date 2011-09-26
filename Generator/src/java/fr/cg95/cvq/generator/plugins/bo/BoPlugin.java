package fr.cg95.cvq.generator.plugins.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import fr.cg95.cvq.generator.ApplicationDocumentation;
import fr.cg95.cvq.generator.ElementProperties;
import fr.cg95.cvq.generator.ElementTypeClass;
import fr.cg95.cvq.generator.IPluginGenerator;
import fr.cg95.cvq.generator.UserDocumentation;
import fr.cg95.cvq.generator.common.ElementStack;
import fr.cg95.cvq.generator.common.RequestCommon;
import fr.cg95.cvq.generator.common.Step;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

/**
 * @author rdj@zenexity.fr
 */
public class BoPlugin implements IPluginGenerator {
    
    private static Logger logger = Logger.getLogger(BoPlugin.class);

    private int depth;
    
    private String outputDir;
    private String editTemplate;
    private String stepsTemplate;
    private String collectionTemplate;
    
    private RequestBo requestBo;
    
    private ElementStack<ElementBo> elementBoStack;
    
    
    public void initialize(Node configurationNode) {
        logger.debug("initialize()");
        try {
            NamedNodeMap childAttributesMap = configurationNode.getFirstChild().getAttributes();
            outputDir = childAttributesMap.getNamedItem("outputdir").getNodeValue();
            editTemplate = childAttributesMap.getNamedItem("edittemplate").getNodeValue();
            stepsTemplate = childAttributesMap.getNamedItem("stepstemplate").getNodeValue();
            collectionTemplate = childAttributesMap.getNamedItem("collectiontemplate").getNodeValue();
        } catch (NullPointerException npe) {
            throw new RuntimeException ("Check bo-plugin.xml <properties outputdir=\"\" groovytemplate=\"\"/> configuration tag");
        }
    }
    
    public void startRequest(String requestName, String targetNamespace) {
        logger.debug("startRequest()");
        depth = 0;
        requestBo = new RequestBo(requestName, targetNamespace);
        elementBoStack = new ElementStack<ElementBo>();
    }
    
    
    public void endRequest(String requestName) {
        logger.debug("endRequest()");
        try {
            String output = outputDir + "/" + requestBo.getName() + "/";
            if (! new File(output).exists())
                new File(output).mkdir();
            
            SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();
            
            // main .../<requestType.name>/_edit.gsp 
            Template template = templateEngine.createTemplate(new File(editTemplate));
            Map<String, Object> bindingMap = new HashMap<String, Object>();
            bindingMap.put("requestBo", requestBo);
            template.make(bindingMap).writeTo(new OutputStreamWriter(new FileOutputStream(output + "_edit.gsp"), "UTF-8"));
            logger.warn("endRequest() - edit.gsp.tpl OK");
            
            // _steps.gsp template
            template = templateEngine.createTemplate(new File(stepsTemplate));
            int bundleIndex = 0;
            if (requestBo.getStepBundles().size() > 1 ) {
                for (List<Step> stepBundle : requestBo.getStepBundles()) {
                    bindingMap.put("stepBundle", stepBundle);
                    template.make(bindingMap).writeTo(new OutputStreamWriter(new FileOutputStream(output + "_steps"+ bundleIndex++ +".gsp" ), "UTF-8"));
                }
            }
            logger.warn("endRequest() - steps.gsp.tpl OK");
            
            // .../<requestType.name>_<collection>.gsp templates
            template = templateEngine.createTemplate(new File(collectionTemplate));
            bindingMap = new HashMap<String, Object>();
            for (ElementBo element: requestBo.getElementsByTypeClass(ElementTypeClass.COLLECTION)) {
                bindingMap.put("element", element);
                template.make(bindingMap).writeTo(new OutputStreamWriter(new FileOutputStream(output + "_" + element.getJavaFieldName() + ".gsp"), "UTF-8"));
            }
            logger.warn("endRequest() - collection.gsp.tpl OK");
            
        } catch (CompilationFailedException cfe) {
            logger.error(cfe.getMessage()); 
        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe.getMessage()); 
        } catch (IOException ioe) {
            logger.error(ioe.getMessage()); 
        }
    }
    
    public void startElement(String elementName, String type) {
        logger.debug("endElement()");
        elementBoStack.push(++depth, new ElementBo(elementName, this.requestBo.getAcronym()));
    }
    
    public void endElement(String elementName) {
        logger.debug("endElement()");

        if (depth <= 1 && elementBoStack.peek(depth).isDisplay())
            requestBo.addElement(elementBoStack.pop(depth));
        else if (elementBoStack.peek(depth).isDisplay())
            elementBoStack.store(depth);
        else
            elementBoStack.pop(depth);
        
        depth--;
    }

    public void startElementProperties(ElementProperties elementProp) {
        logger.debug("startElementProperties()");
        ElementBo elementBo = elementBoStack.peek(depth);
        elementBo.setType(elementProp.getXmlSchemaType());
        elementBo.setMinLength(elementProp.getMinLength());
        elementBo.setMaxLength(elementProp.getMaxLength());
        elementBo.setTypeClass(elementProp.getTypeClass());
        // TODO - define a more robust namespace mapping policy
        if (elementProp.isReferentialType())
            elementBo.setModelNamespace(IPluginGenerator.MODEL_BASE_TARGET_NS + ".users");
        
        if (elementProp.getMinOccurs().compareTo(BigInteger.valueOf(0)) == 0)
            elementBo.setMandatory(false);
        
        if (elementProp.getEnumValues() != null)
            elementBo.setWidget("capdematEnum");
        else
            elementBo.setWidget(elementProp.getXmlSchemaType());
    }
    
    public void endElementProperties() {
        logger.debug("endElementProperties()");
    }

    public void onApplicationInformation(ApplicationDocumentation appDoc) {
        logger.debug("onApplicationInformation()");
        if (depth < 1) {
            requestBo.setSteps(appDoc.getRequestCommon().getSteps());
        } else if (depth >= 1) {
            ElementBo elementBo = elementBoStack.peek(depth);
            elementBo.setStep(appDoc.getRequestCommon().getCurrentElementCommon().getStep());
            elementBo.setConditionListener(appDoc.getRequestCommon().getCurrentElementCommon().getConditionListener());
            elementBo.setTriggeredConditions(appDoc.getRequestCommon().getCurrentElementCommon().getTriggeredConditions());
            elementBo.setModelNamespace(RequestCommon.MODEL_REQUEST_NS + "." + appDoc.getRequestCommon().getNamespace());
            elementBo.setJsRegexp(appDoc.getRequestCommon().getCurrentElementCommon().getJsRegexp());
            elementBo.setDisplay(true);
            
            if (appDoc.getNodeName().equals("bo")) {
                Node node = appDoc.getXmlNode();
                elementBo.setColumn(ApplicationDocumentation.getNodeAttributeValue(node, "column"));
                elementBo.setAfter(ApplicationDocumentation.getNodeAttributeValue(node, "after"));
                
                if (appDoc.hasChildNode("textarea")) {
                    elementBo.setRows(ApplicationDocumentation.getNodeAttributeValue(
                            appDoc.getChildrenNodes("textarea")[0], "rows"));
                    elementBo.setWidget("textarea");
                }
            }
         }
    }
    
    public void onUserInformation(UserDocumentation userDoc) {
        logger.debug("onUserInformation()");
    }
    
    public void shutdown() { }

}