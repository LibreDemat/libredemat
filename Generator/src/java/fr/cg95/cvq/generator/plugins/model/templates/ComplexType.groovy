<%
  import org.apache.commons.lang.StringUtils
  import fr.cg95.cvq.generator.common.Condition.RoleType
  import fr.cg95.cvq.generator.plugins.model.ModelPluginUtils
  fr.cg95.cvq.generator.plugins.model.ElementModelProperties.metaClass.type = {
    if (delegate.simpleType) {
        if (delegate.enumValues) {
            return delegate.javaPackageName + delegate.xmlSchemaType
        } else {
            return delegate.javaType
        }
    } else {
        if (delegate.maxOccurs == 1) {
            return delegate.javaPackageName + StringUtils.removeEnd(delegate.xmlSchemaType, "Type")
        } else {
            return "List<${delegate.javaPackageName + delegate.modelClassName}>"
        }
    }
  }
  def returnInstance = StringUtils.uncapitalize(className)
  def displayModelToXmlWidget = { element, wrapper ->
    def widgets = [
      "long" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(this.${element.nameAsParam}.longValue());
      """,
      "double" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(this.${element.nameAsParam}.doubleValue());
      """,
      "short" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(this.${element.nameAsParam});
      """,
      "string" : """
        ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(this.${element.nameAsParam});
      """,
      "enum" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(${element.xmlBeansPackageName}.${element.xmlSchemaType}.Enum.forString(this.${element.nameAsParam}.toString()));
      """,
      "date" : """
        date = this.${element.nameAsParam};
        if (date != null) {
            calendar.setTime(date);
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(calendar);
        }
      """,
      "time" : """
        localTime = this.${element.nameAsParam};
        if (localTime != null) {
            calendar.set(Calendar.HOUR_OF_DAY,localTime.getHourOfDay());
            calendar.set(Calendar.MINUTE,localTime.getMinuteOfHour());
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(calendar);
        }
      """,
      "boolean" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(this.${element.nameAsParam}.booleanValue());
      """,
      "positiveInteger" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(new BigInteger(this.${element.nameAsParam}.toString()));
      """,
      "complex" : """
        if (this.${element.nameAsParam} != null)
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}(${element.modelClassName}.modelToXml(this.${element.nameAsParam}));
      """,
      "referentialList" : """
        i = 0;
        if (this.${element.nameAsParam} != null) {
            ${element.xmlBeansPackageName}.${element.xmlSchemaType}[] ${element.nameAsParam}TypeTab = new ${element.xmlBeansPackageName}.${element.xmlSchemaType}[${element.nameAsParam}.size()];
            for (${element.modelClassName} object : ${element.nameAsParam}) {
              ${element.nameAsParam}TypeTab[i++] = ${element.modelClassName}.modelToXml(object);
            }
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}Array(${element.nameAsParam}TypeTab);
        }
      """,
      "complexList" : """
        i = 0;
        if (${element.nameAsParam} != null) {
            ${element.xmlBeansPackageName}.${element.xmlSchemaType}[] ${element.nameAsParam}TypeTab = new ${element.xmlBeansPackageName}.${element.xmlSchemaType}[${element.nameAsParam}.size()];
            for (${element.modelClassName} object : ${element.nameAsParam}) {
              ${element.nameAsParam}TypeTab[i++] = object.modelToXml();
            }
            ${wrapper}.set${StringUtils.capitalize(element.nameAsParam)}Array(${element.nameAsParam}TypeTab);
        }
      """
    ]
    widgets["referential"] = widgets["complex"]
    def output = widgets[element.widget]
    if (output != null) print output
  }
  def displayXmlToModelWidget = { element, wrapper ->
    def widgets = [
      "long" : """
        if (${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}() != 0)
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(new Long(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}()));
      """,
      "double" : """
        ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(new Double(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}()));
      """,
      "short" : """
        if (${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}() != null)
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}());
      """,
      "string" : """
        ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}());
      """,
      "enum" : """
        if (${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}() != null)
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${element.javaPackageName}${element.xmlSchemaType}.forString(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}().toString()));
        else
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${element.javaPackageName}${element.xmlSchemaType}.getDefault${element.xmlSchemaType}());
      """,
      "date" : """
        calendar = ${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}();
        if (calendar != null) {
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(calendar.getTime());
        }
      """,
      "time" : """
        calendar = ${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}();
        if (calendar != null) {
            localTime = new LocalTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(localTime);
        }
      """,
      "boolean" : """
        ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(Boolean.valueOf(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}()));
      """,
      "positiveInteger" : """
        ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}());
      """,
      "complex" : """
        if (${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}() != null)
            ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${element.modelClassName}.xmlToModel(${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}()));
      """,
      "complexList" : """
        List<${element.javaPackageName}${element.modelClassName}> ${element.nameAsParam}List = new ArrayList<${element.javaPackageName}${element.modelClassName}>(${wrapper}.sizeOf${StringUtils.capitalize(element.nameAsParam)}Array());
        for (${element.modelClassName}Type object : ${wrapper}.get${StringUtils.capitalize(element.nameAsParam)}Array()) {
            ${element.nameAsParam}List.add(${element.javaPackageName}${element.modelClassName}.xmlToModel(object));
        }
        ${returnInstance}.set${StringUtils.capitalize(element.nameAsParam)}(${element.nameAsParam}List);
      """
    ]
    widgets["referential"] = widgets["complex"]
    widgets["referentialList"] = widgets["complexList"]
    def output = widgets[element.widget]
    if (output != null) print output
  }
  def displayAnnotation = { element ->
    def sqlName = ModelPluginUtils.getSQLName(StringUtils.capitalize(element.nameAsParam))
    def widgets = [
      "simple" : """
    @Column(name="${sqlName}" ${element.maxLength > 0 ? ', length=' + element.maxLength : element.length > 0 ? ', length=' + element.length : ""} )
      """,
      "positiveInteger" : """
    @Column(name="${sqlName}" ${element.maxLength > 0 ? ', length=' + element.maxLength : element.length > 0 ? ', length=' + element.length : ""}, columnDefinition="bytea" )
    @Type(type="serializable") //Hack see http://capdemat.capwebct.fr/ticket/338
      """,
      "enum" : """
    @Enumerated(EnumType.STRING)
    @Column(name="${sqlName}" ${element.maxLength > 0 ? ', length=' + element.maxLength : element.length > 0 ? ', length=' + element.length : ""} )
      """,
      "localTime" : """
    @Column(name="${sqlName}" ${element.maxLength > 0 ? ', length=' + element.maxLength : element.length > 0 ? ', length=' + element.length : ""} )
      """,
      "one-to-many" : """
    @OneToMany(cascade=CascadeType.ALL)
    @OrderColumn(name="${sqlName}_index")
    @JoinColumn(name="${sqlName}_id")
      """,
      "many-to-one" : """
    @ManyToOne${element.tiedToRequest ? '(cascade=CascadeType.ALL)' : ""}
    @JoinColumn(name="${sqlName}_id")
      """,
      "many-to-many" : """
    @ManyToMany${element.tiedToRequest ? '(cascade=CascadeType.ALL)' : ""}
    @JoinTable(name="${sqlName}")
    @JoinColumn(name="${sqlName}_id")
    @OrderColumn(name="${sqlName}_index")
      """
    ]
    widgets["long"] = widgets["simple"]
    widgets["double"] = widgets["simple"]
    widgets["short"] = widgets["simple"]
    widgets["string"] = widgets["simple"]
    widgets["date"] = widgets["simple"]
    widgets["time"] = widgets["localTime"]
    widgets["boolean"] = widgets["simple"]
    widgets["referential"] = widgets["many-to-one"]
    widgets["referentialList"] = widgets["many-to-many"]
    widgets["complex"] = widgets["one-to-many"]
    widgets["complexList"] = widgets["one-to-many"]
    def output = widgets[element.widget]
    if (output != null) print output
  }
  def displayCloneWidget = { element ->
    def widgets = [
      "simple" : """
        result.set${StringUtils.capitalize(element.nameAsParam)}(${element.nameAsParam});
      """,
      "enum" : """
        if (${element.nameAsParam} != null)
            result.set${StringUtils.capitalize(element.nameAsParam)}(${element.nameAsParam});
        else
            result.set${StringUtils.capitalize(element.nameAsParam)}(${element.javaPackageName}${element.xmlSchemaType}.getDefault${element.xmlSchemaType}());
      """,
      "complex" : """
        if (${element.nameAsParam} != null)
            result.set${StringUtils.capitalize(element.nameAsParam)}(${element.nameAsParam}.clone());
      """,
      "complexList" : """
        List<${element.javaPackageName}${element.modelClassName}> ${element.nameAsParam}List = new ArrayList<${element.javaPackageName}${element.modelClassName}>();
        for (${element.modelClassName} object : ${element.nameAsParam}) {
            ${element.nameAsParam}List.add(object.clone());
        }
        result.set${StringUtils.capitalize(element.nameAsParam)}(${element.nameAsParam}List);
      """
    ]
    widgets["long"] = widgets["simple"]
    widgets["double"] = widgets["simple"]
    widgets["short"] = widgets["simple"]
    widgets["string"] = widgets["simple"]
    widgets["date"] = widgets["simple"]
    widgets["time"] = widgets["simple"]
    widgets["boolean"] = widgets["simple"]
    widgets["positiveInteger"] = widgets["simple"]
    widgets["referential"] = widgets["complex"]
    widgets["referentialList"] = widgets["complexList"]
    def output = widgets[element.widget]
    if (output != null) print output
  }
%>
package ${baseNS}.request.${lastParticle};

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalTime;

import net.sf.oval.constraint.*;
import org.apache.xmlbeans.XmlOptions;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.request.*;
import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.xml.common.RequestType;
import ${XMLBeansBaseNS}.common.*;
import ${XMLBeansBaseNS}.request.${lastParticle}.*;
import fr.cg95.cvq.service.request.LocalReferential;
import fr.cg95.cvq.service.request.condition.IConditionChecker;
import javax.persistence.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * Generated class file, do not edit !
 */
@Entity
@Table(name="${sqlName}")
public class ${className} implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        ${requestName}.conditions;

    public ${className}() {
        super();
      <% constructorAttributes.each { %>
        ${it.key} = ${it.value};
      <% } %>
    }

    public final String modelToXmlString() {
        ${className}Type object = (${className}Type) this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    public final ${className}Type modelToXml() {
        <% def localComplexTypesSet = new HashSet<String>() %>
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        LocalTime localTime = new LocalTime();
        ${className}Type ${returnInstance} = ${className}Type.Factory.newInstance();
        int i = 0;
    <%
      elements.each {
        def container
        if (it.complexContainerElementName) {
          complexTypeName = it.complexContainerElementTypeName
          complexTypeInstanceName = StringUtils.uncapitalize(complexTypeName) + it.complexContainerElementName
          if (!localComplexTypesSet.contains(complexTypeInstanceName)) {
              localComplexTypesSet.add(complexTypeInstanceName)
              print "    $complexTypeName $complexTypeInstanceName = ${returnInstance}.addNew${it.complexContainerElementName}();"
          }
          container = complexTypeInstanceName
        } else {
          container = returnInstance
        }
        displayModelToXmlWidget(it, container)
      }
    %>
        return ${returnInstance};
    }

    public static ${className} xmlToModel(${className}Type ${returnInstance}Doc) {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        ${className} ${returnInstance} = new ${className}();
    <%
      elements.each {
        def container = "${returnInstance}Doc"
        if (it.complexContainerElementName) {
            container += ".get${it.complexContainerElementName}()"
        }
        displayXmlToModelWidget(it, container)
      }
    %>
        return ${returnInstance};
    }

    @Override
    public ${className} clone() {
        ${className} result = new ${className}();
        <% elements.each { element -> %>
          <% if (["RecreationCenterType", "SchoolType"].contains(element.xmlSchemaType)) { %>
            result.set${element.elementName}(${element.nameAsParam});
          <% } else { %>
            <% displayCloneWidget(element) %>
          <% } %>
        <% } %>
        return result;
    }

    private Long id;

    public final void setId(final Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    public final Long getId() {
        return this.id;
    }

  <% elements.each { element -> %>
    <% element.validationAnnotations.each { %>
      @${it.key}(
        <% it.value.each { %>
          ${it.key} = ${it.value},
        <% } %>
        <%
          if (element.elementCommon.conditionListener != null
            || element.complexContainerConditionListener != null) {
        %>
          when = "groovy:def active = true;" +
          <%
            [element.elementCommon.conditionListener, element.complexContainerConditionListener].each { listener ->
              if (listener != null) {
                def trigger = complexType.getElementModelProperties(listener.condition.trigger.name)
                if ("LocalReferentialData".equals(trigger.modelClassName)) {
          %>
            "if (_this.${trigger.nameAsParam} == null || _this.${trigger.nameAsParam}.isEmpty()) return false; _this.${trigger.nameAsParam}.each { active &= <% if (RoleType.unfilled.equals(listener.role)) { %>!<% } %>_this.conditions['${StringUtils.uncapitalize(className)}.${trigger.nameAsParam}'].test(it.name) };" +
                <% } else { %>
            "active &= <% if (RoleType.unfilled.equals(listener.role)) { %>!<% } %>_this.conditions['${StringUtils.uncapitalize(className)}.${trigger.nameAsParam}'].test(_this.${trigger.nameAsParam}.toString());" +
                <% } %>
              <% } %>
            <% } %>
            "return active",
        <% } %>
        profiles = {"${complexType.properties.elementCommon.step.name}"},
        message = "${element.nameAsParam}"
      )
    <% } %>
    private ${element.type()} ${element.nameAsParam};

    public void set${StringUtils.capitalize(element.nameAsParam)}(final ${element.type()} ${element.nameAsParam}) {
        this.${element.nameAsParam} = ${element.nameAsParam};
    }

<% displayAnnotation(element) %>
    public ${element.type()} get${StringUtils.capitalize(element.nameAsParam)}() {
        return this.${element.nameAsParam};
    }
  <% } %>
}
