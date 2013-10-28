<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd">

<beans>

  <bean id="${pluginBeanName}" parent="externalProviderServiceAdapter"
    class="org.libredemat.plugins.${pluginType.toLowerCase()}.${pluginName.toLowerCase()}.service.${pluginName}Service"
    init-method="init">
    <property name="label" value="${pluginName}"></property>
    <!-- Insert services you need here -->
  </bean>

  <% if (pluginType == 'CsvImporters') { %>
  <bean class="org.libredemat.platform.PluginBeanFactoryPostProcessor">
    <property name="extensionBeanName" value="${extensionBeanName}" />
    <property name="propertyName" value="${propertyName}" />
    <property name="pluginBeanName" value="${pluginBeanName}" />
  </bean>
  <% } %>
  
</beans>
