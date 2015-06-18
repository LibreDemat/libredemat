package org.libredemat.generator.plugins.pdf;

import org.apache.commons.lang.StringUtils;
import org.libredemat.generator.ElementTypeClass;
import org.libredemat.generator.common.ConditionListener;
import org.libredemat.generator.common.ElementSpecific;
import org.libredemat.generator.common.Step;


/**
 * @author rdj@zenexity.fr
 */
public class ElementPdf extends ElementSpecific<ElementPdf> {

    private String label;
    private String name;
    private String javaFieldName;
    private String modelNamespace;
    
    private String type;
    private boolean mandatory = true;
    
    private String i18nPrefixCode;
    private String i18nPrefixContent;
    private String htmlClass;
    private String widget;
    private String[] enumValues;

    private ElementTypeClass typeClass;
 
    private boolean display;

    private String elementToDisplay;
    private String after;
    private String modifier;

    private Step step;
    private ConditionListener conditionListener;
    
    public ElementPdf(String name, String requestAcronym) {
        this.name = name;
        this.javaFieldName = StringUtils.uncapitalize(name);
        this.i18nPrefixCode = requestAcronym + ".property." + this.javaFieldName;
        display = false;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.javaFieldName = StringUtils.uncapitalize(name);
    }
    
    public String getJavaFieldName() {
        return javaFieldName;
    }

    public String getModelNamespace() {
        return modelNamespace;
    }

    public void setModelNamespace(String modelNamespace) {
        this.modelNamespace = modelNamespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getEnumValuesAsString() {
        if (enumValues == null )
            return null;
        String s = "[";
        for (int i = 0; i < enumValues.length; i++) {
            s += "'" + enumValues[i] + "'";
            if (i < enumValues.length - 1)
                s += ",";
        }
        s += "]";
        return s;
    }

    public void setEnumValues(String[] enumValues) {
        this.enumValues = enumValues;
    }

    public String getQualifiedType() {
        return modelNamespace + "." + type;
    }

    public String getI18nPrefixCode() {
        return i18nPrefixCode;
    }

    // TODO - refactor 'htmlClass' and 'conditionClass' members in respectively 'labelClass' 'formFieldClass'
    // TODO - add jsRegExp validation feature
    public String getHtmlClass() {
        if (htmlClass == null)
            setHtmlClass();
        return htmlClass;
    }

    private void setHtmlClass() {
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String type) {
        if (widget != null)
            return;
        this.widget = StringUtils.uncapitalize(StringUtils.removeEnd(type, "Type"));
    }

    public String getTypeClass() {
        return typeClass.toString();
    }

    public void setTypeClass(ElementTypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory || (conditionListener!= null && conditionListener.isRequired());
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    // TODO - finish to implement <fo element="" /> attribute feature
    public String getElementToDisplay() {
        return elementToDisplay;
    }

    public void setElementToDisplay(String elementToDisplay) {
        this.elementToDisplay = elementToDisplay;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    
    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setConditionListener(ConditionListener conditionListener) {
        this.conditionListener = conditionListener;
    }

    public String getI18nPrefixContent() {
        return i18nPrefixContent;
    }

    public void setI18nPrefixContent(String i18nPrefixContent) {
        this.i18nPrefixContent = i18nPrefixContent;
    }
}
