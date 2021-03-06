package org.libredemat.generator.plugins.i18n;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.libredemat.generator.ApplicationDocumentation;
import org.libredemat.generator.ElementProperties;
import org.libredemat.generator.IPluginGenerator;
import org.libredemat.generator.UserDocumentation;
import org.libredemat.generator.common.ElementStack;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

/**
 * @author rdj@zenexity.fr
 */
public class I18nPlugin implements IPluginGenerator {

 private static Logger logger = Logger.getLogger(I18nPlugin.class);
    
    private int depth;
    
    private String outputDir;
    private String i18nTemplate;
    private String i18nTmpTemplate;
    
    private RequestI18n requestI18n;
    private List<ElementI18n> elementI18ns = new ArrayList<ElementI18n>();
    
    private ElementStack<ElementI18n> elementI18nStack;
    
    public void initialize(Node configurationNode) {
        logger.debug("initialize()");
        try {
            NamedNodeMap childAttributesMap = configurationNode.getFirstChild().getAttributes();
            
            outputDir = childAttributesMap.getNamedItem("outputdir").getNodeValue();
            i18nTemplate = childAttributesMap.getNamedItem("i18ntemplate").getNodeValue();
            i18nTmpTemplate = childAttributesMap.getNamedItem("i18ntmptemplate").getNodeValue();
        } catch (NullPointerException npe) {
            throw new RuntimeException ("Check i18n-plugin.xml " +
            		"<properties outputdir=\"\" i18ntemplate=\"\" i18ntmptemplate=\"\"/> configuration tag");
        }
    }
    
    public void startRequest(String requestName, String targetNamespace) {
        logger.debug("startRequest()");
        depth = 0;
        requestI18n = new RequestI18n(targetNamespace);
        elementI18nStack = new ElementStack<ElementI18n>();
        elementI18ns.clear();
    }
    
    public void endRequest(String requestName) {
        logger.warn("endRequest()");
        try {
            SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();
            Template template = templateEngine.createTemplate(new BufferedReader(new InputStreamReader(new FileInputStream(i18nTemplate), "UTF8")));
            Template template2 = templateEngine.createTemplate(new BufferedReader(new InputStreamReader(new FileInputStream(i18nTmpTemplate), "UTF8")));
            
            for (String lang: requestI18n.getI18nLabels().keySet()) {
                // support is deprecated for langages other than FR
                if (!lang.equals("fr"))
                    continue;

                String output = outputDir + requestI18n.getAcronym();
                String templateOutput = outputDir + requestI18n.getAcronym() + "customized";
                output += ".properties";
                templateOutput += ".properties";
                
                Map<String, Object> bindingMap = new HashMap<String, Object>();
                bindingMap.put("lang", lang);
                bindingMap.put("acronym", requestI18n.getAcronym());
                bindingMap.put("requestI18n", requestI18n.getI18nLabels());
                bindingMap.put("steps", requestI18n.getSteps());
                bindingMap.put("elements", elementI18ns);
                
                template.make(bindingMap).writeTo(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));
                template2.make(bindingMap).writeTo(new OutputStreamWriter(new FileOutputStream(templateOutput + ".tmp"), "UTF-8"));
            }
        } catch (CompilationFailedException cfe) {
            logger.error(cfe.getMessage()); 
        } catch (IOException ioe) {
            logger.error(ioe.getMessage()); 
        }
    }
    
    public void startElement(String elementName, String type) {
        logger.debug("endElement()");
        elementI18nStack.push(++depth, new ElementI18n(elementName, requestI18n.getAcronym()));
    }
    
    public void endElement(String elementName) {
        logger.debug("endElement()");        
        if (depth > 1)
            elementI18nStack.store(depth);
        
        depth--;
    }

    public void startElementProperties(ElementProperties elementProp) {
        logger.debug("startElementProperties()");
        ElementI18n elementI18n = elementI18nStack.peek(depth);
        elementI18n.setTypeClass(elementProp.getTypeClass());
    }
    
    public void endElementProperties() {
        logger.debug("endElementProperties()");
    }

    public void onApplicationInformation(ApplicationDocumentation appDoc) {
        logger.debug("onApplicationInformation()");
        if (depth < 1)
            requestI18n.setSteps(appDoc.getRequestCommon().getSteps());
        else if (depth >= 1) {
            ElementI18n element = elementI18nStack.peek(depth);
            elementI18ns.add(element);
        }
    }
    
    public void onUserInformation(UserDocumentation userDoc) {
        logger.debug("onUserInformation()");
        if (depth == 0) {
            if (userDoc.getSourceUri().equals(IPluginGenerator.SHORT_DESC))
                requestI18n.addI18nLabel(userDoc.getLang(), "short", userDoc.getText());
            if (userDoc.getSourceUri().equals(IPluginGenerator.LONG_DESC))
                requestI18n.addI18nLabel(userDoc.getLang(), "long", userDoc.getText());
        }
        if (elementI18nStack.peek(depth) != null) {
            if (userDoc.getSourceUri().equals(IPluginGenerator.SHORT_DESC))
                elementI18nStack.peek(depth).addi18nUserDocText(userDoc.getLang(), userDoc.getText());
            if (userDoc.getSourceUri().equals(IPluginGenerator.ENUM_TRANS))
                elementI18nStack.peek(depth).addi18nUserDocEnums(userDoc.getLang(), userDoc.getXmlTranslationNodes());
        }
    }
    
    public void shutdown() { }
}
