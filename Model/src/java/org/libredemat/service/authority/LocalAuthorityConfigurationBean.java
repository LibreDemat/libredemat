package org.libredemat.service.authority;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.ExternalServiceConfigurationBean;
import org.libredemat.external.IExternalProviderService;
import org.libredemat.oauth2.OAuth2ConfigurationBean;
import org.libredemat.service.payment.IPaymentProviderService;
import org.libredemat.service.payment.PaymentServiceBean;


/**
 * A bean used to store configuration specific to a local authority.
 *
 * This bean can be retrieved via the local authority registry.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 * @see ILocalAuthorityRegistry#getCurrentLocalAuthorityBean()
 */
public final class LocalAuthorityConfigurationBean {

    private static Logger logger = Logger.getLogger(LocalAuthorityConfigurationBean.class);

    private String name;
    private String defaultServerName;
    private String defaultEmail;
    private String authenticationMethod;
    private String authenticationMethodFront;
    private boolean accountValidationRequired = false;

    private Map<String, String> authorizations;
    private Map<IPaymentProviderService, PaymentServiceBean> paymentServices;
    private Map<String, String> externalApplicationProperties;
    private ExternalServiceConfigurationBean escb;

    private Map<String, Map<String, String>> agentNotifications;
    private Map<String, Map<String, String>> paymentNotifications;
    private Properties jpaConfigurations;
    private EntityManagerFactory entityManagerFactory;
    private OAuth2ConfigurationBean oauth2ConfigurationBean;
    private Map<String, String> dietsEnumeration;

    private boolean informationSheetRequired = false;
    private boolean informationSheetDisplayed = true;
    private boolean viewDocumentInPanelInBO = true;
    private boolean autotransition = false;
    private boolean addresseReferentialCityRestriction = false;

    public LocalAuthorityConfigurationBean() {
        paymentServices =
            new HashMap<IPaymentProviderService, PaymentServiceBean>();
        escb = new ExternalServiceConfigurationBean();
    }

    public OAuth2ConfigurationBean getOauth2ConfigurationBean(){
        return oauth2ConfigurationBean;
    }

    public void setOauth2ConfigurationBean(OAuth2ConfigurationBean oauth2ConfigurationBean) {
        this.oauth2ConfigurationBean = oauth2ConfigurationBean;
    }

    /**
     * Get the list of payment services objects for the current local authority.
     */
    public Set<IPaymentProviderService> getPaymentServicesObjects() {
        if (paymentServices == null || paymentServices.isEmpty())
            return null;

        return paymentServices.keySet();
    }

    /**
     * Return whether the given key has agent notification enabled for the local
     * authority associated to this bean.
     */

    public boolean hasAgentNotification(final String agentNotificationKey) {
        if (agentNotifications== null)
            return false;

        if (agentNotifications.get(agentNotificationKey) != null)
            return true;

        return false;
    }

    /**
     * Return configuration data associated to the given data key for the given 
     * agent's notification key
     */
    public String getAgentNotificationData(final String agentNotificationKey,
            final String dataKey) {
        if (agentNotifications== null)
            return null;
        Map<String, String> data = agentNotifications.get(agentNotificationKey);
        if (data == null)
            return null;
        return data.get(dataKey);
    }

    /**
     * Return whether the given key has agent notification enabled for the local
     * authority associated to this bean.
     */
    public boolean hasPaymentNotification(final String paymentNotificationKey) {
        if (paymentNotifications == null)
            return false;

        if (paymentNotifications.get(paymentNotificationKey) != null)
            return true;

        return false;
    }

    /**
     * Return configuration data associated to the given data key for the given 
     * agent's notification key
     */
    public String getPaymentNotificationData(final String paymentNotificationKey,
            final String dataKey) {
        if (paymentNotifications == null)
            return null;
        Map<String, String> data =
            paymentNotifications.get(paymentNotificationKey);
        if (data == null)
            return null;
        return data.get(dataKey);
    }

    public void init() throws CvqConfigurationException {

        // FIXME : this should be done by the payment service
        if (paymentServices != null && paymentServices.size() > 0) {
            for (IPaymentProviderService service : paymentServices.keySet()) {
                logger.debug("init() Looking at " + service.getClass());
                service.checkConfiguration(paymentServices.get(service));
            }
        }
    }

    /**
     * Extract email information ("Send to", "Subject", "Body") from the notifications properties
     * of the current local authority.
     *
     * @param hasNotificationMethodName name of method that check email existence
     * @param getNotificationDataMethodName name of method that get email field
     * @param mailKey key of the email to extract
     */
    public Map<String, String> getMailAsMap(String hasNotificationMethodName, 
            String getNotificationDataMethodName, String mailKey) {

		try {
			Method hasNotificationMethod = 
				getClass().getDeclaredMethod(hasNotificationMethodName, 
                        new Class[] { String.class });
			Method getNotificationDataMethod = 
				getClass().getDeclaredMethod(getNotificationDataMethodName, 
                        new Class[] { String.class, String.class });

			if ((Boolean) hasNotificationMethod.invoke(this, new Object[] {mailKey}))  {
                Map<String, String> mailMap = new HashMap<String, String>();

				String mailSubject = 
                    (String) getNotificationDataMethod.invoke(this, new Object[] {mailKey, "mailSubject"});
				mailMap.put("mailSubject", mailSubject);
				String mailSendTo = 
                    (String) getNotificationDataMethod.invoke(this, new Object[] {mailKey, "mailSendTo"});
				mailMap.put("mailSendTo", mailSendTo);
				String mailData = 
                    (String) getNotificationDataMethod.invoke(this, new Object[] {mailKey, "mailData"});
				mailMap.put("mailData", mailData);

                return mailMap;
			}

            return null;
		} catch(Exception exception) {
			logger.error("getMailAsMap() reflection method call exception");
			exception.printStackTrace();
			return null;
		}
	}

    public List<String> getAdditionalTabs() {
        List<String> tabs = new ArrayList<String>();
        if (paymentServices != null && !paymentServices.isEmpty()) {
            tabs.add("Payments");
        }
        if (escb.supportsActivitiesTab()) {
            tabs.add("Activities");
        }
        if (!getExternalApplicationProperty("booker.url").isEmpty()) {
            tabs.add("Planning");
        }
        return tabs;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDefaultServerName() {
        return defaultServerName;
    }

    public void setDefaultServerName(String defaultServerName) {
        this.defaultServerName = defaultServerName;
    }

    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getAuthenticationMethodFront() {
        return authenticationMethodFront;
    }

    public void setAuthenticationMethodFront(String authenticationMethodFront) {
        this.authenticationMethodFront = authenticationMethodFront;
    }

    public Properties getJpaConfigurations() {
        return jpaConfigurations;
    }

    public void setJpaConfigurations(Properties jpaConfigurations) {
        this.jpaConfigurations = jpaConfigurations;
    }

    public void setPaymentServices(final Map<IPaymentProviderService, PaymentServiceBean> paymentServices) {
        this.paymentServices = paymentServices;
    }

    public final Map<IPaymentProviderService, PaymentServiceBean> getPaymentServices() {
        return paymentServices;
    }

    public void setExternalServices(final Map<IExternalProviderService, ExternalServiceBean> externalProviderServices) 
        throws CvqConfigurationException {
        escb.setExternalServices(externalProviderServices, name);
    }

    public Map<IExternalProviderService, ExternalServiceBean> getExternalServices() {
        return escb.getExternalServices();
    }
    
    public ExternalServiceConfigurationBean getExternalServiceConfigurationBean() {
        return escb;
    }

    public void registerExternalService(IExternalProviderService service, ExternalServiceBean esb) 
        throws CvqConfigurationException {
        escb.registerExternalService(service, esb, name);
    }

    public ExternalServiceBean getBeanForExternalService(String externalServiceLabel) {
        return escb.getBeanForExternalService(externalServiceLabel);
    }

    public void unregisterExternalService(IExternalProviderService service) {
        escb.unregisterExternalService(service);
    }

	public void setAgentNotifications(Map<String, Map<String, String>> agentNotifications) {
		this.agentNotifications = agentNotifications;
	}

	public void setPaymentNotifications(Map<String, Map<String, String>> paymentNotifications) {
		this.paymentNotifications = paymentNotifications;
	}

    public synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("libredematPersistenceUnit", getJpaConfigurations());
        }
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public boolean isAutotransition() {
        return autotransition;
    }

    public void setAutotransition(boolean autotransition) {
        this.autotransition = autotransition;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public void setDefaultEmail(String defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public Map<String, String> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Map<String, String> authorizations) {
        this.authorizations = authorizations;
    }


    public String getExternalApplicationProperty(String key) {
        if (externalApplicationProperties != null) {
            return externalApplicationProperties.get(key);
        } else {
            return "";
        }
    }
    /**
     * @return the informationSheetRequired
     */
    public boolean isInformationSheetRequired()
    {
        if (!isInformationSheetDisplayed()) return false;
        return informationSheetRequired;
    }

    /**
     * @param informationSheetRequired
     *            the informationSheetRequired to set
     */
    public void setInformationSheetRequired(boolean informationSheetRequired)
    {
        this.informationSheetRequired = informationSheetRequired;
    }

    /**
     * @return the dietsEnumeration
     */
    public Map<String, String> getDietsEnumeration()
    {
        return dietsEnumeration;
    }

    /**
     * @param dietsEnumeration
     *            the dietsEnumeration to set
     */
    public void setDietsEnumeration(Map<String, String> dietsEnumeration)
    {
        this.dietsEnumeration = dietsEnumeration;
    }

    public void setViewDocumentInPanelInBO(boolean viewDocumentInPanelInBO)
    {
        this.viewDocumentInPanelInBO = viewDocumentInPanelInBO;
    }

    public boolean isViewDocumentInPanelInBO()
    {
        return viewDocumentInPanelInBO;
    }

    public void setInformationSheetDisplayed(boolean informationSheetDisplayed)
    {
        this.informationSheetDisplayed = informationSheetDisplayed;
    }

    public boolean isInformationSheetDisplayed()
    {
        return informationSheetDisplayed;
    }

    public void setExternalApplicationProperties(Map<String, String> externalApplicationProperties) {
        this.externalApplicationProperties = externalApplicationProperties;
    }

    public boolean isAccountValidationRequired() {
        return accountValidationRequired;
    }

    public void setAccountValidationRequired(boolean accountValidationRequired) {
        this.accountValidationRequired = accountValidationRequired;
    }

    public boolean isAddresseReferentialCityRestriction() {
        return addresseReferentialCityRestriction;
    }
    public void setAddresseReferentialCityRestriction(boolean addresseReferentialCityRestriction) {
        this.addresseReferentialCityRestriction = addresseReferentialCityRestriction;
    }
}
