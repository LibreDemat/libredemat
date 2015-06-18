package org.libredemat.generator.plugins.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.libredemat.generator.ElementTypeClass;
import org.libredemat.generator.common.Condition;
import org.libredemat.generator.common.ConditionListener;
import org.libredemat.generator.common.ElementSpecific;
import org.libredemat.generator.common.Step;
import org.libredemat.generator.common.Condition.RoleType;


/**
 * @author rdj@zenexity.fr
 */
public class ElementBo extends ElementSpecific<ElementBo> {

    private String label;
    private String name;
    private String javaFieldName;
    private String modelNamespace;
    
    private String type;
    private boolean mandatory = true;
    private String jsRegexp;
    
    private int minLength;
    private int maxLength;
    
    private String i18nPrefixCode;
    private String i18nPrefixContent;
    private String htmlClass;
    private String widget;
    private int rows;
    
    private ElementTypeClass typeClass;
 
    private boolean display;
    private boolean disabled = false;

    private int column;
    private String after;
    private String specificEditField;

    private Step step;
    private ConditionListener conditionListener;
    private List<Condition> triggeredConditions;
    
    public ElementBo(String name, String requestAcronym) {
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

    public String getHtmlClass() {
        if (htmlClass == null)
            setHtmlClass();
        return htmlClass;
    }
    
    private void setHtmlClass() {
        this.htmlClass = StringUtils.join(new String[] {
             !this.isDisabled() ? "action-editField" : "", " ", "validate-", (jsRegexp != null ? "regex" : widget)
            ,(mandatory ? " required-true" : "")
            , " " , "i18n-" +i18nPrefixCode
            ,(widget != null && widget.equals("libredematEnum") ? " " + "javatype-" + getQualifiedType() : "" )
            ,(widget != null && widget.equals("textarea") ? " rows-" + rows : "" )
            ,(widget != null && widget.equals("localReferentialData") ? " data-localReferentialData" : "" )
            ,(minLength > 0 ? " minLength-" + minLength : "")
            ,(maxLength > 0 ? " maxLength-" + maxLength : "")
        });
        if (this.specificEditField != null) {
            this.specificEditField = this.specificEditField.replace("AND", "&&");
            this.htmlClass = this.htmlClass.replace("action-editField", this.specificEditField);
        }
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
                throw new RuntimeException("setWidget() - <textarea /> can be only used for types {string, token}");
            return;
        }
        this.widget = StringUtils.uncapitalize(StringUtils.removeEnd(type, "Type"));
    }

    public void setRows(String rows) {
        if (rows == null)
            return;
        try {
            this.rows= Integer.valueOf(rows).intValue();
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("setRows() - rows {"+ rows +"} is not an integer in " + name + "element.");
        }
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

    public int getColumn() {
        return column;
    }

    public void setColumn(String column) {
        if (column == null)
            return;
        try {
            this.column = Integer.valueOf(column).intValue();
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("setColumn() - Column {"+ column +"} is not an integer");
        }
        if (this.column < 1 || this.column > 2)
            throw new RuntimeException("setColumn() - Column {"+ column +"} is not in [1,2]");
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        if (after == null)
            return;
        this.after = after;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    // FIXME - manage condition and mandatory state of the element. separation of concerns ??
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

    public void setConditionListener(ConditionListener conditionListener) {
        this.conditionListener = conditionListener;
    }

    public void setTriggeredConditions(List<Condition> triggeredConditions) {
        this.triggeredConditions = triggeredConditions;
    }

    public String getSpecificEditField() {
        return specificEditField;
    }

    public void setSpecificEditField(String specificEditField) {
        this.specificEditField = specificEditField;
    }

    public String getI18nPrefixContent() {
        return i18nPrefixContent;
    }

    public void setI18nPrefixContent(String i18nPrefixContent) {
        this.i18nPrefixContent = i18nPrefixContent;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
