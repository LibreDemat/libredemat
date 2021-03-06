<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd">

<beans>

    <bean id="configurationBean_blainville"
          class="org.libredemat.service.authority.LocalAuthorityConfigurationBean"
          init-method="init">
        <property name="name" value="blainville"/>
        <property name="authorizations">
            <map>
                <entry key="login" value="password"/>
            </map>
        </property>
        <property name="defaultServerName" value="localhost"/>
        <property name="defaultEmail" value="libredemat-dev@zengularity.com"/>
        <property name="defaultPassword" value="bienvenue"/>
        <property name="superAdminPassword" value="superAdminPassword"/>
        <property name="autotransition" value="true"/>
        <property name="authenticationMethod" value="builtin"/>
        <!-- builtin,cas -->
        <property name="authenticationMethodFront" value="builtin"/>
        <!-- builtin,oauth2-->
        <property name="accountValidationRequired" value="false"/>
        <property name="viewDocumentInPanelInBO" value="false"/>
        <property name="informationSheetDisplayed" value="true"/>
        <property name="informationSheetRequired" value="true"/>
        <property name="informationSheetRequiredFieldsActived" value="true"/>
        <property name="addresseReferentialCityRestriction" value="true"/>
        <property name="activeCocheAllInReservationPlaningMonth" value="true"/>
        <property name="activeGlobalReservation" value="false"/>
        <property name="synchroniseUserOnChangeStateToArchived" value="false"/>
        <property name="synchroniseUserOnChangeState" value="true"/>
        <property name="jpaConfigurations">
            <props>
                <prop key="hibernate.show_sql">false</prop>
                <prop key="hibernate.format_sql">false</prop>
                <prop key="hibernate.connection.driver_class">org.postgresql.Driver</prop>
                <prop key="hibernate.connection.url">jdbc:postgresql://localhost:5432/libredemat_blainville_${branch}
                </prop>
                <prop key="hibernate.connection.username">libredemat</prop>
                <prop key="hibernate.connection.password">libredematpass</prop>
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
                        <ref bean="payBoxSystemV6PaymentProvider"/>
                    </key>
                    <bean class="org.libredemat.service.payment.PaymentServiceBean">
                        <property name="broker" value="REGIE MULTI ACTIVITES"></property>
                        <property name="friendlyLabel" value="Facturation scolaire"></property>
                        <property name="requestTypes">
                            <list>
                                <value>School Canteen Registration</value>
                                <value>Perischool Activity Registration</value>
                                <value>Recreation Activity Registration</value>
                                <value>Recreation Activity Poly Registration</value>
                                <value>Child Care Center Registration</value>
                            </list>
                        </property>
                        <property name="serviceProperties">
                            <map>
                                <entry key="pbxSite" value="2351706"/>
                                <entry key="pbxRang" value="01"/>
                                <entry key="pbxIdentifiant" value="150633506"/>
                                <entry key="pbxRetour" value="montant:M;ref:R;auto:A;trans:T;erreur:E;transNum:S"/>
                                <entry key="pbxHmac" value="506AD00872128806A9EF12020EDFF0AA6F20F083DD3A3FEA4EC0E0A7CE54EF28A8D7E4EA01B49D096D481EFF106455A62BD6672C762FB7D86A6AFF937A9A30DD" />

                                <entry key="pbxEffectue"
                                       value="https://conflans-demociril.zengularity.com/frontoffice/payment/status"/>
                                <entry key="pbxRefuse"
                                       value="https://conflans-demociril.zengularity.com/frontoffice/payment/status"/>
                                <entry key="pbxAnnule"
                                       value="https://conflans-demociril.zengularity.com/service/ciril/negativePayment"/>
                                <entry key="pbxAttente"
                                       value="https://conflans-demociril.zengularity.com/frontoffice/payment/status"/>

                                <entry key="pbxRepondreA"
                                       value="https://conflans-demociril.zengularity.com/service/paybox/index"/>
                            </map>
                        </property>
                    </bean>
                </entry>
                <entry>
                    <key>
                        <ref bean="fakePaymentProviderService"/>
                    </key>
                    <bean class="org.libredemat.service.payment.PaymentServiceBean">
                        <property name="broker" value="Régie démo de LibreDémat"></property>
                        <property name="friendlyLabel" value="Services autres que restauration scolaire"></property>
                        <property name="requestTypes">
                            <list>
                                <value>Park Card</value>
                                <value>Parking Permit Temporary Relocation</value>
                                <value>Ticket Booking</value>
                                <value>Perischool Activity Registration</value>
                                <value>Child Care Registration</value>
                                <value>Parking Permit Temporary Work</value>
                            </list>
                        </property>
                    </bean>
                </entry>
                <!--
                <entry>
                  <key>
                    <ref bean="tipiPaymentProvider" />
                  </key>
                  <bean class="org.libredemat.service.payment.PaymentServiceBean">
                    <property name="broker" value="Tipi"></property>
                    <property name="requestTypes">
                      <list>
                         <value>Perischool Activity Registration</value>
                       </list>
                    </property>
                    <property name="serviceProperties">
                      <map>
                        <entry>
                          <key><value>numcli</value></key>
                          <value>001340</value>
                        </entry>
                        <entry>
                          <key><value>saisie</value></key>
                          <value>T</value>
                        </entry>
                      </map>
                    </property>
                   </bean>
                </entry>
                -->
            </map>
        </property>
        <property name="externalServices">
            <map>
                <!--
                <entry>
                <key>
                <ref bean="cardFiveExternalService" />
                </key>
                <bean class="org.libredemat.external.ExternalServiceBean">
                <property name="requestTypes">
                <list>
                <value>Park Card</value>
                </list>
                </property>

                <property name="serviceProperties">
                <map>
                <entry key="FtpServer" value="192.168.1.193" />
                <entry key="FtpPort" value="21" />
                <entry key="FtpUserName" value="ponpon" />
                <entry key="FtpPassword" value="vazyponponcbon" />
                <entry key="FtpFolder" value="parkCard" />
                <entry key="ServerName" value="blainville.inexine.net" />
                </map>
                </property>
                </bean>
                </entry>
                -->
                <!-- Ciril Net Enfance plugin -->
                <entry>
                    <key>
                        <ref bean="cirilNetEnfanceExternalService"/>
                    </key>
                    <bean class="org.libredemat.external.ExternalServiceBean">
                        <property name="requestTypes">
                            <list>
                                <value>School Registration With Remote Cirilnetenfance</value>
                                <value>Child Care Registration</value>
                            </list>
                        </property>
                        <property name="serviceProperties">
                            <map>
                                <entry key="EndPointRegistration"
                                       value="http://195.42.148.3:89/cgi-bin/ws.exe/cgi-bin/enf_cdv4_6_teleservices.exe?ws"/>
                                <entry key="EndPointReservation"
                                       value="http://195.42.148.3:89/cgi-bin/ws.exe/cgi-bin/enf_cdv4_6_reservations.exe?ws"/>
                                <entry key="EndPointSchool"
                                       value="http://195.42.148.3:89/cgi-bin/ws.exe/cgi-bin/enf_ts_soap.exe?ws"/>

                                <entry key="isActiveSendMailInValidNoPay" value="False"/>
                                <entry key="mailFrom" value="garmetta@ciril.net"/>
                                <!-- ARTUNG BOUBLONS IN CIVIL <entry key="sendHomeFolderCreation" value="createCompteOnSynchronise" /> -->
                            </map>
                        </property>
                    </bean>
                </entry>
                <!-- DIGITECH - City -->
                <!--
                <entry>
                  <key>
                    <ref bean="digitechExternalService" />
                  </key>
                  <bean class="org.libredemat.external.ExternalServiceBean">
                    <property name="requestTypes">
                      <list>
                        <value>Birth Details</value>
                        <value>Marriage Details</value>
                        <value>Death Details</value>
                      </list>
                    </property>
                    <property name="serviceProperties">
                      <map>
                        <entry key="EndPoint" value="http://195.42.148.3:8282/cityws/infoliveServices/" />
                      </map>
                    </property>
                  </bean>
                </entry>
                -->
                <!--
                <entry>
                  <key>
                    <ref bean="cleverExternalService" />
                  </key>
                  <bean class="org.libredemat.external.ExternalServiceBean">
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
                          <key><value>endportpath</value></key>
                          <value>http://services.clever.fr/cleversms/ws</value>
                        </entry>
                        <entry>
                          <key><value>username</value></key>
                          <value>toto</value>
                        </entry>
                        <entry>
                          <key><value>password</value></key>
                          <value>titi</value>
                        </entry>
                      </map>
                    </property>
                  </bean>
                </entry>
                -->
                <entry>
                    <key>
                        <ref bean="fakeExternalService"/>
                    </key>
                    <bean class="org.libredemat.external.ExternalServiceBean">
                        <property name="requestTypes">
                            <list>
                                <value>Technical Intervention</value>
                                <value>School Canteen Registration</value>
                            </list>
                        </property>
                        <property name="login" value="fakePointExternalService"/>
                        <property name="password" value="abcd"/>
                    </bean>
                </entry>
                <!--
                <entry>
                    <key>
                        <ref bean="restExternalService"/>
                    </key>
                    <bean class="org.libredemat.external.ExternalServiceBean">
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
                        <ref bean="externalApplicationProviderService"/>
                    </key>
                    <bean class="org.libredemat.external.ExternalServiceBean">
                        <property name="requestTypes">
                            <list>
                                <value>School Canteen Registration</value>
                                <value>Perischool Activity Registration</value>
                                <value>Recreation Activity Registration</value>
                            </list>
                        </property>
                        <property name="login" value="externalApplicationProviderService"/>
                        <property name="password" value="abcd"/>
                    </bean>
                </entry>
                -->
            </map>
        </property>
        <property name="agentNotifications">
            <map>
                <entry>
                    <key>
                        <value>NotCommitPaymentAlert</value>
                    </key>
                    <map>
                        <entry key="mailSendTo" value="libredemat-dev@zengularity.com"/>
                        <entry key="mailSubject" value="[LibreDémat] Alerte Paiement"/>
                        <entry key="mailData" value="NotCommitedPaymentsAlert"/>
                    </map>
                </entry>
            </map>
        </property>
        <property name="paymentNotifications">
            <map>
                <entry>
                    <key>
                        <value>CommitPaymentConfirmation</value>
                    </key>
                    <map>
                        <entry key="mailSubject" value="[LibreDémat] Confirmation de paiement"/>
                        <entry key="mailData" value="CommitPaymentNotification"/>
                    </map>
                </entry>
                <entry>
                    <key>
                        <value>AskPaymentNotification</value>
                    </key>
                    <map>
                        <entry key="mailSubject" value="[CapDémat] Demande de paiement"/>
                        <entry key="mailData" value="AskPaymentNotification"/>
                    </map>
                </entry>
                <entry>
                    <key>
                        <value>CancelPaymentNotification</value>
                    </key>
                    <map>
                        <entry key="mailSubject" value="[CapDémat] Annulation de paiement"/>
                        <entry key="mailData" value="CancelPaymentNotification"/>
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
            <bean class="org.libredemat.oauth2.OAuth2ConfigurationBean">
                <constructor-arg index="0"
                                 value="MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAl8j1Vw4sNiNKF/6Qg91q+MYko8zJiXJP SI95Nh7SjR4rGT6meschgKwhS0tna7PoSe56gxSxsOJVWnZUfws0iSmha7bDsYe1HG98GoS9OkhW 8QK53lsAXAOm5LLCkJCFPZ5gHIsVznYQZIn/kica0kWzC7looTiC7M0GrA5jBrx7IxRgasLBzpqh XUIET6w4KijfPzLncz71+9KtbQYCUeekVevIJiwUI9s+E+CLmKlsGq/ugOu4yfzDuqI2Xn2mFhSp 2xtPYEuQTyya48whNtnLJGjhcjuEr1fTmrTxL4yLNE7B2vm8Fze2LmdXenMFgOce+kzTOgr+oRPR s8x6BsW+BtjYvf5zrqiYkchKQ9UDnRZHRo9tFoT+AWIVy8P1ICF9Ikw440+5tYR7wtR0HwBb7FKh hWN7/RCu71K6KLUN8LbOJ2A6wrBjvRfo6+VaVEPuF/zyZYwIRBAvaFISo+yq/nijSKcoPyq54nTN rmfIzSM2k0N1bKyL6c8ij2p3ibkytXTF8FFra/3fiLd4L57pF0lG56nQ4WUrrmOpKZS4UP/eSStQ o2XX5qT42n0uPsDhRbMyXlTABXrY3+YytvSojfkLGyTZiz5HfuQjZQYXbuygob5tmsQEAgg/ToBK SyJ1yORzbggcWTs/kGbiezO5okczcj9hMPrBuED8pX0CAwEAAQ=="/>
                <property name="authorizationUri" value="http://swarm:9000/oauth/authorize"/>
                <property name="tokenUri" value="http://swarm:9000/oauth/accessToken"/>
                <property name="logoutUri" value="http://swarm:9000/logout"/>
                <property name="resourceServerName" value="blainville"/>
                <property name="clientId" value="libredemat"/>
                <property name="password" value="aaaaaaaa"/>
                <property name="redirectionUri" value="http://localhost:8080/LibreDemat/OAuth2/login"/>
                <property name="identificationScope" value="ident"/>
                <property name="agentScope" value="agent"/>
                <property name="cssNameFront" value="cssFrontLoginOauth"/>
                <property name="cssNameBack" value="cssBackLoginOauth"/>
            </bean>
        </property>
        <property name="dietsEnumeration">
            <map>
                <entry key="SANS_PORC" value="Sans porc"/>
                <entry key="SANS_POISS" value="Sans poisson"/>
                <entry key="PAI" value="PAI"/>
                <entry key="ALLERGIE" value="Sans allergène"/>
            </map>
        </property>


    </bean>

</beans>
