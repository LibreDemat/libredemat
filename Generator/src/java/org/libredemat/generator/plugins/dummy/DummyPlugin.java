package org.libredemat.generator.plugins.dummy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.libredemat.generator.ApplicationDocumentation;
import org.libredemat.generator.ElementProperties;
import org.libredemat.generator.IPluginGenerator;
import org.libredemat.generator.UserDocumentation;
import org.w3c.dom.Node;


/**
 * A dummy plugin that just prints out data received from the code
 * generator
 *
 * @author bor@zenexity.fr
 */
public class DummyPlugin implements IPluginGenerator {

    private static Logger logger =
        Logger.getLogger(DummyPlugin.class);

    private int depth = 0;

    public void initialize(Node configurationNode) {
        logger.debug("initialize() Configuration Node : "+ configurationNode.toString());
    }

    public void shutdown() {
        logger.debug("shutdown()");
    }

    public void startRequest(String requestName, String targetNamespace) {

        logger.debug("startRequest() Name : " + requestName);
    }

    public void endRequest(String requestName) {

        logger.debug("endRequest() Name : " + requestName);
    }

    public void startElement(String elementName, String type) {

        depth++;
        logger.debug(getIndent() + "startElement() Name : " + elementName);
        logger.debug(getIndent() + "startElement() Type : " + type);
    }

    public void endElement(String elementName) {

        logger.debug(getIndent() + "endElement() Name : " + elementName + "\n");
        depth--;
    }

    public void startElementProperties(ElementProperties elementProperties) {

        logger.debug(getIndent() + "startElementProperties()");
        logger.debug(getIndent() + "startElementProperties() Min Occurs : " + elementProperties.getMinOccurs());
        logger.debug(getIndent() + "startElementProperties() Max Occurs : " + elementProperties.getMaxOccurs());
        logger.debug(getIndent() + "startElementProperties() Max Length : " + elementProperties.getMaxLength());
        logger.debug(getIndent() + "startElementProperties() List Length : " + elementProperties.getListLength());
        String[] patterns = elementProperties.getPatterns();
        for (int i = 0; i < patterns.length; i++) {
            logger.debug(getIndent() + "startElementProperties() Pattern : " + patterns[i]);
        }
        String[] enumValues = elementProperties.getEnumValues();
        if (enumValues != null) {
            for (int i = 0; i < enumValues.length; i++) {
                logger.debug(getIndent() + "startElementProperties() Enum value : " + enumValues[i]);
            }
        }
        logger.debug(getIndent() + "startElementProperties() Default value : " + elementProperties.getDefaultValue());

        logger.debug(getIndent() + "startElementProperties() XML Schema Type : " + elementProperties.getXmlSchemaType());
        logger.debug(getIndent() + "startElementProperties() XML Bean Type : " + elementProperties.getXmlBeanType());
        logger.debug(getIndent() + "startElementProperties() Java Type : " + elementProperties.getJavaType());

        logger.debug(getIndent() + "startElementProperties() Is referential type : " + elementProperties.isReferentialType());
        logger.debug(getIndent() + "startElementProperties() Is local type : " + elementProperties.isRequestType());
        logger.debug(getIndent() + "startElementProperties() Is primitive type : " + elementProperties.isPrimitiveType());
        logger.debug(getIndent() + "startElementProperties() Is complex type : " + elementProperties.isComplexType());
        logger.debug(getIndent() + "startElementProperties() Is part of choice : " + elementProperties.isChoiceElement());
        logger.debug(getIndent() + "startElementProperties() Is simple type : " + elementProperties.isSimpleType());
        logger.debug(getIndent() + "startElementProperties() Is inherited : " + elementProperties.isInherited());
        logger.debug(getIndent() + "startElementProperties() Is list type : " + elementProperties.isListType());
    }

    public void endElementProperties() {
        logger.debug(getIndent() + "endElementProperties()");
    }

    public void onUserInformation(UserDocumentation userDocumentation) {
        logger.debug(getIndent() + "onUserInformation() " + userDocumentation.getSourceUri() + " / " + userDocumentation.getLang());
        if (userDocumentation.getXmlTranslationNodes() != null) {
            for (Map.Entry<String, String> translation :
                userDocumentation.getXmlTranslationNodes().entrySet())
                logger.debug(getIndent()
                    + "onUserInformation() Translation from "
                    + translation.getKey() + " is " + translation.getValue());
        } else if (userDocumentation.getText() != null) {
                logger.debug(getIndent() + "onUserInformation() Text : " + userDocumentation.getText());
        }
    }

    public void onApplicationInformation(ApplicationDocumentation applicationDocumentation) {
        logger.debug(getIndent() + "onApplicationInformation() " + applicationDocumentation.getNodeName() + " / " + applicationDocumentation.getXmlString());
        
        if (depth < 1)
            logger.warn(getIndent() + "onApplicationInformation - applicationDocumentation.getRequestCommon()=[" +
                    "namespace: " + applicationDocumentation.getRequestCommon().getNamespace() +
                    ", steps.size: " + applicationDocumentation.getRequestCommon().getSteps().size() +
                    "]");
        else if (applicationDocumentation.getRequestCommon().getCurrentElementCommon() != null)
            logger.warn(getIndent() + "onApplicationInformation() - currentElementCommom= [" +
                    "step: [name:" + applicationDocumentation.getRequestCommon().getCurrentElementCommon().getStep().getName() +
                    "] conditionListener:" + applicationDocumentation.getRequestCommon().getCurrentElementCommon().getConditionListener() +
                    ", triggeredConditions.size:" + applicationDocumentation.getRequestCommon().getCurrentElementCommon().getTriggeredConditions().size());
        else 
            logger.warn(getIndent() + "onApplicationInformation() - currentElementCommom= [null]");
    }

    private String getIndent() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < depth; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}
