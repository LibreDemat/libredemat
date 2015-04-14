package org.libredemat.generator.plugins.fo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.libredemat.generator.ElementTypeClass;
import org.libredemat.generator.common.Autofill;
import org.libredemat.generator.common.Condition;
import org.libredemat.generator.common.ConditionListener;
import org.libredemat.generator.common.ElementSpecific;
import org.libredemat.generator.common.Step;
import org.libredemat.generator.common.Autofill.AutofillType;
import org.libredemat.generator.common.Condition.RoleType;
import org.libredemat.generator.plugins.tool.GeneratorUtils;


/**
 * @author rdj@zenexity.fr
 */
public class ElementFo extends ElementSpecific<ElementFo> {

    private String label;
    private String name;
    private String javaFieldName;
    private String modelNamespace;
    
    private String type;
    private boolean mandatory = true;
    private String jsRegexp;
    
    private Integer minLength = 0;
    private Integer maxLength = 0;
    
    private String i18nPrefixCode;
    private String i18nPrefixContent;
    private String style = "width:99%";

    private String htmlClass;
    private String widget;
    private String[] enumValues;

    private ElementTypeClass typeClass;
 
    private boolean display;
    private boolean disabled = false;

    private String elementToDisplay;
    private String after;
    private String modifier;
    private int rows;
    private String collectionSpecific;

    private Step step;
    private ConditionListener conditionListener;
    private List<Condition> triggeredConditions;
    private Autofill autofill;
    private String specificTestCodeToAddItemInCollection = "true";
    
    public ElementFo(String name, String requestAcronym) {
        this.name = name;
        this.javaFieldName = StringUtils.uncapitalize(name);
        this.i18nPrefixCode = requestAcronym + ".property." + this.javaFieldName;
        display = false;
        triggeredConditions = new ArrayList<Condition>();
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
        String[] enums = new String[enumValues.length];
        int i = 0;
        for (String enumValue : enumValues){
            enums[i] = GeneratorUtils.getEnumStaticName(enumValue);
            i++;
        }
        this.enumValues = enums;
    }

    public String getQualifiedType() {
        return modelNamespace + "." + type;
    }

    public String getI18nPrefixCode() {
        return i18nPrefixCode;
    }

    public String getJsRegexp() {
        String s = "";
        if (jsRegexp != null)
            s += "regex=\"" + jsRegexp + "\""; 
        return s;
    }

    public void setJsRegexp(String jsRegexp) {
        this.jsRegexp = jsRegexp;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    // FIXME - avoid to generate attributeName="value" in java side
    // FIXME - minlength is not a HTML 4.01 valid attribute
    public String getLengthLimits() {
        String limits = "";
        if (maxLength != 0)
            limits += "maxlength=\"" + maxLength + "\""; 
        if (minLength != 0)
            limits += " minlength=\"" + minLength + "\""; 
        return limits;
    }

    // TODO - refactor 'htmlClass' and 'conditionClass' members in respectively 'labelClass' 'formFieldClass'
    // TODO - add jsRegExp validation feature
    public String getHtmlClass() {
        if (htmlClass == null)
            setHtmlClass();
        return htmlClass;
    }
    
    private void setHtmlClass() {
        this.htmlClass = getConditionsClass() + " " + getAutofillClass() + " ";
        if (jsRegexp != null)
            this.htmlClass += "validate-regex";
        else if (widget == null)
            return;
        else if (widget.equals("select") && mandatory)
            this.htmlClass += "validate-not-first";
        else if (widget.equals("radio"))
            this.htmlClass += "validate-one-required";
        else if (widget.equals("boolean"))
            this.htmlClass += "validate-one-required boolean";
        else if ((widget.equals("checkbox") || widget.equals("acceptance"))) {
            // Checks acceptance only if mandatory.
            if (mandatory)
                this.htmlClass += "validate-acceptance";
        } else
            this.htmlClass += "validate-" + widget;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String type) {
        if (widget != null) {
            // TODO - how to process element without xmlschema 'type' attribute
            if (type == null)
                return;
            if (widget.equals("textarea") && !(type.equals("string") || type.equals("token")))
                throw new RuntimeException("setWidget() - " +
                        "<textarea /> can only be used for types {string, token}. [element: " + this.name + "]");
            if (widget.equals("checkbox") && !type.equals("AcceptanceType"))
                throw new RuntimeException("setWidget() - " +
                        "<checkbox /> can only be used for acceptance type. [element: " + this.name + "]");
            return;
        }
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

    public String getRows() {
        if (rows == 0)
            return "";
        return Integer.valueOf(rows).toString();
    }

    public void setRows(String rows) {
        if (rows == null)
            return;
        try {
            this.rows = Integer.valueOf(rows).intValue();
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("setRows() - rows {"+ rows +"} is not an integer in element : " + name);
        }
    }

    public Step getStep() {
        return step;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    // FIXME - manage condition and mandatory state of the element. separation of concerns ??
    // TODO - refactor 'htmlClass' and 'conditionClass' members in respectively 'labelClass' 'formFieldClass'
    public String getConditionsClass() {
        StringBuffer sb = new StringBuffer();
        sb.append(mandatory ? "required " : "");
        if (conditionListener != null)
            sb.append("condition-" + conditionListener.getCondition().getName()
                + "-" + conditionListener.getRole() + " ");
        for (Condition c : triggeredConditions)
            sb.append("condition-" + c.getName() + "-" + RoleType.trigger + " ");
        return sb.toString().trim();
    }
    
    public String getListenerConditionsClass() {
        StringBuffer sb = new StringBuffer();
        sb.append(mandatory ? "required " : "");
        if (conditionListener != null)
            sb.append("condition-" + conditionListener.getCondition().getName()
                + "-" + conditionListener.getRole() + " ");
        return sb.toString().trim();
    }

    public void setConditionListener(ConditionListener conditionListener) {
        this.conditionListener = conditionListener;
    }

    public void setTriggeredConditions(List<Condition> triggeredConditions) {
        this.triggeredConditions = triggeredConditions;
    }

    public Autofill getAutofill() {
        return autofill;
    }

    public void setAutofill(Autofill autofill) {
        this.autofill = autofill;
    }

    public String getAutofillClass() {
        StringBuffer sb = new StringBuffer();
        if (autofill != null) {
            sb.append("autofill-" + autofill.getName() + "-" + autofill.getType().name().toLowerCase());
            if (autofill.getType().equals(AutofillType.LISTENER)) {
                sb.append("-" + autofill.getField());
            }
        }
        return sb.toString();
    }

    public String getCollectionSpecific() {
        return collectionSpecific;
    }

    public void setCollectionSpecific(String collectionSpecific) {
        this.collectionSpecific = collectionSpecific;
    }

    public String getI18nPrefixContent() {
        return i18nPrefixContent;
    }

    public void setI18nPrefixContent(String i18nPrefixContent) {
        this.i18nPrefixContent = i18nPrefixContent;
    }
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSpecificTestCodeToAddItemInCollection() {
        return specificTestCodeToAddItemInCollection;
    }

    public void setSpecificTestCodeToAddItemInCollection(String specificTestCodeToAddItemInCollection) {
        this.specificTestCodeToAddItemInCollection = specificTestCodeToAddItemInCollection;
    }
}
