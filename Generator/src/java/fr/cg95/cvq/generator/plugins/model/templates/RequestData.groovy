
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
  def returnInstance = StringUtils.uncapitalize(requestName)
  def displayAnnotation = { element, wrapper ->
    def sqlName = ModelPluginUtils.getSQLName(element.elementName)
    def wrapperSQLName = ModelPluginUtils.getSQLName(wrapper)
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
      "one-to-many" : """
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @OrderColumn(name="${sqlName}_index")
    @JoinColumn(name="${wrapperSQLName}_id")
      """,
      "many-to-one" : """
    @ManyToOne(fetch=FetchType.EAGER${element.tiedToRequest ? ', cascade=CascadeType.ALL)' : ")"}
    @JoinColumn(name="${sqlName}_id")
      """,
      "many-to-many" : """
    @ManyToMany(fetch=FetchType.EAGER${element.tiedToRequest ? ', cascade=CascadeType.ALL)' : ")"}
    @JoinTable(name="${wrapperSQLName}_${sqlName}",
            joinColumns=
                @JoinColumn(name="${wrapperSQLName}_id"),
            inverseJoinColumns=
                @JoinColumn(name="${sqlName}_id"))
    @OrderColumn(name="${sqlName}_index")
      """
    ]
    widgets["long"] = widgets["simple"]
    widgets["double"] = widgets["simple"]
    widgets["short"] = widgets["simple"]
    widgets["string"] = widgets["simple"]
    widgets["date"] = widgets["simple"]
    widgets["time"] = widgets["simple"]
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.oval.constraint.*;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.request.*;
import fr.cg95.cvq.business.users.*;
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
public class ${requestName}Data implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions =
        new HashMap<String, IConditionChecker>(RequestData.conditions);

    private Long id;

    public ${requestName}Data() {
      <% constructorAttributes.each { %>
        ${it.key} = ${it.value};
      <% } %>
    }

    @Override
    public ${requestName}Data clone() {
        ${requestName}Data result = new ${requestName}Data();
        <% elements.each { element -> %>
          <% if (["RecreationCenterType", "SchoolType"].contains(element.xmlSchemaType)) { %>
            result.set${element.elementName}(${element.nameAsParam});
          <% } else { %>
            <% displayCloneWidget(element) %>
          <% } %>
        <% } %>
        return result;
    }

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
                def trigger = request.getField(listener.condition.trigger.name)
                def prefix = listener.listenAMultiTrigger() ? '\''+listener.condition.name+'=\'+' : ''
                if ("LocalReferentialData".equals(trigger.modelClassName)) {
          %>
            "if (_this.${trigger.nameAsParam} == null || _this.${trigger.nameAsParam}.isEmpty()) return false; _this.${trigger.nameAsParam}.each { active &= <% if (RoleType.unfilled.equals(listener.role)) { %>!<% } %>_this.conditions['${trigger.nameAsParam}'].test(${!prefix.isEmpty() ? prefix : ''}it.name) };" +
                <% } else { %>
            "active &= <% if (RoleType.unfilled.equals(listener.role)) { %>!<% } %>_this.conditions['${trigger.nameAsParam}'].test(${!prefix.isEmpty() ? prefix : ''}_this.${trigger.nameAsParam}.toString());" +
                <% } %>
              <% } %>
            <% } %>
            "return active",
        <% } %>
        profiles = {"${element.elementCommon.step.name}"},
        message = "${element.nameAsParam}"
      )
    <% } %>
    private ${element.type()} ${element.nameAsParam};

    public void set${element.elementName}(final ${element.type()} ${element.nameAsParam}) {
        this.${element.nameAsParam} = ${element.nameAsParam};
    }

 <% displayAnnotation(element, requestName) %>
    public ${element.type()} get${element.elementName}() {
        return this.${element.nameAsParam};
    }
  <% } %>
}
