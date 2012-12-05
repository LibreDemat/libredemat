<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd">

<beans>

  <bean id="configurationBean_blainville"
    class="fr.cg95.cvq.service.authority.LocalAuthorityConfigurationBean"
    init-method="init">
    <property name="name" value="blainville"/>
    <property name="authorizations">
      <map>
        <entry key="login" value="password"/>
      </map>
    </property>
    <property name="defaultServerName" value="localhost"/>
    <property name="defaultEmail" value="sansmail@blainville.fr"/>
    <property name="autotransition" value="true"/>
    <property name="authenticationMethod" value="oauth2"/>
    <property name="jpaConfigurations">
        <props>
            <prop key="hibernate.show_sql">false</prop>
            <prop key="hibernate.format_sql">false</prop>
            <prop key="hibernate.connection.driver_class">org.postgresql.Driver</prop>
            <prop key="hibernate.connection.url">jdbc:postgresql://localhost:5432/capdemat_blainville_${branch}</prop>
            <prop key="hibernate.connection.username">capdemat</prop>
            <prop key="hibernate.connection.password">capdematpass</prop>
            <prop key="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</prop>
            <prop key="acquireIncrement">3</prop>
            <prop key="initialPoolSize">0</prop>
            <prop key="minPoolSize">0</prop>
            <prop key="maxStatements">24</prop>
            <prop key="maxIdleTime">300</prop>
        </props>
    </property>
    <property name="paymentServices">
      <map>
        <entry>
          <key>
            <ref bean="fakePaymentProviderService" />
          </key>
          <bean class="fr.cg95.cvq.service.payment.PaymentServiceBean">
            <property name="broker" value="Régie démo de Blainville"></property>
            <property name="friendlyLabel" value="Services autres que restauration scolaire"></property>
            <property name="requestTypes">
              <list>
                <value>Ticket Booking</value>
                <value>Perischool Activity Registration</value>
              </list>
            </property>
          </bean>
        </entry>
      </map>
    </property>
    <property name="externalServices">
      <map>
        <entry>
          <key>
            <ref bean="fakeExternalService" />
          </key>
          <bean class="fr.cg95.cvq.external.ExternalServiceBean">
            <property name="requestTypes">
              <list>
                <value>School Canteen Registration</value>
                <value>Perischool Activity Registration</value>
                <value>Recreation Activity Registration</value>
                <value>School Transport Registration</value>
                <value>Global School Registration</value>
                <value>Leisure Center Registration</value>
                <value>Holiday Camp Registration</value>
              </list>
            </property>
            <property name="serviceProperties">
              <map>
                <entry>
                  <key><value>sendHomeFolderCreation</value></key>
                  <value>true</value>
                </entry>
              </map>
            </property>
          </bean>
        </entry>
        <entry>
          <key>
            <ref bean="fakePointExternalService" />
          </key>
          <bean class="fr.cg95.cvq.external.ExternalServiceBean">
            <property name="requestTypes">
              <list>
                <value>Technical Intervention</value>
                <value>School Canteen Registration</value>
              </list>
            </property>
           <property name="password" value="abcd"/>
          </bean>
        </entry>
        <entry>
          <key>
            <ref bean="restExternalService" />
          </key>
          <bean class="fr.cg95.cvq.external.ExternalServiceBean">
            <property name="requestTypes">
              <list>
                <value>Compostable Waste Collection</value>
              </list>
            </property>
           <property name="login" value="BlainVilleRest"/>
           <property name="password" value="abcd"/>
          </bean>
        </entry>
        <entry>
          <key>
            <ref bean="externalApplicationProviderService" />
          </key>
          <bean class="fr.cg95.cvq.external.ExternalServiceBean">
            <property name="requestTypes">
              <list>
                <value>School Canteen Registration</value>
                <value>Perischool Activity Registration</value>
                <value>Recreation Activity Registration</value>
              </list>
            </property>
          </bean>
        </entry>
      </map>
    </property>
     <property name="agentNotifications">
      <map>
        <entry>
          <key><value>NotCommitPaymentAlert</value></key>
          <map>
            <entry key="mailSendTo" value="capdemat-dev@zenexity.fr"/>
            <entry key="mailSubject" value="[CapDémat] Alerte Paiement"/>
            <entry key="mailData" value="NotCommitedPaymentsAlert"/>
          </map>
        </entry>
      </map>
    </property>
    <property name="paymentNotifications">
      <map>
        <entry>
          <key><value>CommitPaymentConfirmation</value></key>
          <map>
            <entry key="mailSubject" value="[CapDémat] Confirmation de paiement"/>
            <entry key="mailData" value="CommitPaymentNotification"/>
          </map>
        </entry>
      </map>
    </property>
    <property name="externalApplicationProperties">
      <map>
        <entry key="booker.url" value="http://booker:9001/blainville/"/>
        <entry key="booker.logouturl" value="http://booker:9001/logout"/>
      </map>
    </property>
    <property name="oauth2ConfigurationBean">
      <bean class="fr.cg95.cvq.oauth2.OAuth2ConfigurationBean">
        <constructor-arg index="0" value="MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAl8j1Vw4sNiNKF/6Qg91q+MYko8zJiXJP SI95Nh7SjR4rGT6meschgKwhS0tna7PoSe56gxSxsOJVWnZUfws0iSmha7bDsYe1HG98GoS9OkhW 8QK53lsAXAOm5LLCkJCFPZ5gHIsVznYQZIn/kica0kWzC7looTiC7M0GrA5jBrx7IxRgasLBzpqh XUIET6w4KijfPzLncz71+9KtbQYCUeekVevIJiwUI9s+E+CLmKlsGq/ugOu4yfzDuqI2Xn2mFhSp 2xtPYEuQTyya48whNtnLJGjhcjuEr1fTmrTxL4yLNE7B2vm8Fze2LmdXenMFgOce+kzTOgr+oRPR s8x6BsW+BtjYvf5zrqiYkchKQ9UDnRZHRo9tFoT+AWIVy8P1ICF9Ikw440+5tYR7wtR0HwBb7FKh hWN7/RCu71K6KLUN8LbOJ2A6wrBjvRfo6+VaVEPuF/zyZYwIRBAvaFISo+yq/nijSKcoPyq54nTN rmfIzSM2k0N1bKyL6c8ij2p3ibkytXTF8FFra/3fiLd4L57pF0lG56nQ4WUrrmOpKZS4UP/eSStQ o2XX5qT42n0uPsDhRbMyXlTABXrY3+YytvSojfkLGyTZiz5HfuQjZQYXbuygob5tmsQEAgg/ToBK SyJ1yORzbggcWTs/kGbiezO5okczcj9hMPrBuED8pX0CAwEAAQ=="/>
        <property name="authorizationUri" value="http://swarm:9000/oauth/authorize"/>
        <property name="tokenUri" value="http://swarm:9000/oauth/accessToken"/>
        <property name="logoutUri" value="http://swarm:9000/logout"/>
        <property name="resourceServerName" value="blainville"/>
        <property name="clientId" value="capdemat"/>
        <property name="password" value="aaaaaaaa"/>
        <property name="redirectionUri" value="http://localhost:8080/CapDemat/OAuth2/login"/>
        <property name="identificationScope" value="ident"/>
      </bean>
    </property>
  </bean>

</beans>

