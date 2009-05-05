package fr.cg95.cvq.service.authority.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

import fr.cg95.cvq.business.authority.LocalAuthority;
import fr.cg95.cvq.business.authority.LocalAuthorityResource;
import fr.cg95.cvq.business.authority.LocalAuthorityResource.Version;
import fr.cg95.cvq.dao.authority.ILocalAuthorityDAO;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.exception.CvqConfigurationException;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;
import fr.cg95.cvq.permission.CvqPermissionException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.authority.ILocalAuthorityLifecycleAware;
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry;
import fr.cg95.cvq.service.authority.LocalAuthorityConfigurationBean;

/**
 * Implementation of the local authority registry.
 *
 * @author bor@zenexity.fr
 */
public class LocalAuthorityRegistry
    implements ILocalAuthorityRegistry, ApplicationContextAware, BeanFactoryAware {

    private static Logger logger = Logger.getLogger(LocalAuthorityRegistry.class);

    /** Used to store the currently deployed local authorities. */
    private Map<String, LocalAuthorityConfigurationBean> configurationBeansMap = 
        new HashMap<String, LocalAuthorityConfigurationBean>();

    /** Mapping between URL server names and local authorities */
    private Map<String, LocalAuthority> serverNameMappings =
        new HashMap<String, LocalAuthority>();

    /** The parent application context in which the application runs. */
    private ApplicationContext parentApplicationContext;

    /** Keep a map of all services interested in local authorities lifecycle */
    protected Collection<ILocalAuthorityLifecycleAware> allListenerServices;

    private ILocalAuthorityDAO localAuthorityDAO;
    
    private ListableBeanFactory beanFactory;

    private Boolean performDbUpdates;
    
    private String referentialBase;
    private String assetsBase;
    private String[] includedLocalAuthorities;
    private String localAuthoritiesListFilename;
    
    public void init() {
        Map services = beanFactory.getBeansOfType(ILocalAuthorityLifecycleAware.class, true, true);
        if (!services.isEmpty()) {
            allListenerServices = services.values();
        }
    }

    public LocalAuthority getLocalAuthorityByServerName(String serverName) {
        return serverNameMappings.get(serverName);
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.WRITE)
    public void addLocalAuthorityServerName(String serverName)
         throws CvqPermissionException {
        LocalAuthority localAuthority = SecurityContext.getCurrentSite();
        localAuthority.getServerNames().add(serverName);
        localAuthorityDAO.update(localAuthority);
        registerLocalAuthorityServerName(serverName);
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.WRITE)
    public void registerLocalAuthorityServerName(String serverName) {
        serverNameMappings.put(serverName, SecurityContext.getCurrentSite());
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.WRITE)
    public void removeLocalAuthorityServerName(String serverName)
        throws CvqPermissionException {
        LocalAuthority localAuthority = SecurityContext.getCurrentSite();
        localAuthority.getServerNames().remove(serverName);
        localAuthorityDAO.update(localAuthority);
        unregisterLocalAuthorityServerName(serverName);
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.WRITE)
    public void unregisterLocalAuthorityServerName(String serverName) {
        serverNameMappings.put(serverName, null);
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.WRITE)
    public void setLocalAuthorityServerNames(TreeSet<String> serverNames)
        throws CvqException {
        // this method iterates twice on serverNames but it is needed
        // to check serverNames availability before erasing previous ones,
        // and avoid concurrent modification exceptions, although it can probably be enhanced
        for (String serverName : serverNames) {
            if (!isAvailableLocalAuthorityServerName(serverName)) {
                throw new CvqException("ServerName " + serverName + " already exists", "localAuthority.existing.url.error", new String[]{serverName});
            }
        }
        LocalAuthority localAuthority = SecurityContext.getCurrentSite();
        for (String serverName : localAuthority.getServerNames()) {
                unregisterLocalAuthorityServerName(serverName);
        }
        localAuthority.getServerNames().clear();
        for (String serverName : serverNames) {
            addLocalAuthorityServerName(serverName);
        }
    }

    @Context(type = ContextType.ADMIN, privilege = ContextPrivilege.READ)
    public boolean isAvailableLocalAuthorityServerName(String serverName) {
        return (serverNameMappings.get(serverName) == null || serverNameMappings.get(serverName).getId().compareTo(SecurityContext.getCurrentSite().getId()) == 0);
    }

    public LocalAuthorityConfigurationBean getLocalAuthorityBeanByName(final String name) {
        if (name == null)
            return null;

        return (LocalAuthorityConfigurationBean) configurationBeansMap.get(name.toLowerCase());
    }

    public LocalAuthorityConfigurationBean getLocalAuthorityBean(final LocalAuthority localAuthority) {
        if (localAuthority == null)
            return null;

        return (LocalAuthorityConfigurationBean)
            configurationBeansMap.get(localAuthority.getName().toLowerCase());
    }

    public LocalAuthority getLocalAuthorityByName(String name) {
        return localAuthorityDAO.findByName(name);
    }

    public Set<String> getAllLocalAuthoritiesNames() {
        return configurationBeansMap.keySet();
    }

    private LocalAuthorityResource getLocalAuthorityResource(String id)
        throws CvqException {
        LocalAuthorityResource resource = LocalAuthorityResource.localAuthorityResources.get(id);
        if (resource == null) {
            logger.error("getLocalAuthorityResource() couldn't find local authority resource");
            throw new CvqException("localAuthority.resource.error.invalid");
        }
        return resource;
    }

    public File getLocalAuthorityResourceFile(String id, boolean fallbackToDefault)
        throws CvqException {
        return getLocalAuthorityResourceFile(id, LocalAuthorityResource.Version.CURRENT, fallbackToDefault);
    }

    public File getLocalAuthorityResourceFile(String id, LocalAuthorityResource.Version version, boolean fallbackToDefault)
        throws CvqException {
        LocalAuthorityResource resource = getLocalAuthorityResource(id);
        String completeFilename = resource.getFilename() + LocalAuthorityResource.versionExtensions.get(version) + resource.getExtension();
        String filePath = assetsBase + SecurityContext.getCurrentSite().getName().toLowerCase() + "/" + resource.getResourceType() + "/";
        File file = new File(filePath + completeFilename);
        if (!file.exists() && fallbackToDefault) {
            logger.warn("getLocalAuthorityResourceFile() did not find " + filePath + completeFilename + ", trying default");
            return getReferentialResource(resource.getResourceType(), completeFilename);
        }
        return file;
    }

    @Deprecated
    private File getAssetsFile(final String resourceType, final String localAuthorityName,
            final String filename, final boolean fallbackToDefault) {

        StringBuffer filePath = new StringBuffer().append(assetsBase)
            .append(localAuthorityName.toLowerCase()).append("/");
        
        // TODO Refactor this part, put it more flexible
        if (resourceType.equals(IMAGE_ASSETS_RESOURCE_TYPE)) {
            filePath.append(IMAGE_ASSETS_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(CSS_ASSETS_RESOURCE_TYPE)) {
            filePath.append(CSS_ASSETS_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(LOCAL_REFERENTIAL_RESOURCE_TYPE)) {
            filePath.append(LOCAL_REFERENTIAL_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(EXTERNAL_REFERENTIAL_RESOURCE_TYPE)) {
            filePath.append(EXTERNAL_REFERENTIAL_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(TXT_ASSETS_RESOURCE_TYPE)) {
            filePath.append(TXT_ASSETS_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(XSL_RESOURCE_TYPE)) {
            filePath.append(XSL_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(HTML_RESOURCE_TYPE)) {
            filePath.append(HTML_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(PDF_ASSETS_RESOURCE_TYPE)) {
            filePath.append(PDF_ASSETS_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(REQUEST_XML_RESOURCE_TYPE)) {
            filePath.append(REQUEST_XML_RESOURCE_TYPE).append("/");
        } else if (resourceType.equals(MAIL_TEMPLATES_TYPE)) {
            filePath.append(MAIL_TEMPLATES_TYPE).append("/");
        } else {
            logger.warn("getAssetsFile() unrecognized resource type : " + resourceType);
            return null;
        }

        filePath.append(filename);
        logger.debug("getAssetsFile() searching file : " + filePath.toString());
        
        File resourceFile = new File(filePath.toString());
        if (!resourceFile.exists() && fallbackToDefault) {
            logger.warn("getAssetsFile() did not find " + filePath.toString() + ", trying default");
            return getReferentialResource(resourceType, filename);
        }

        return resourceFile;
    }

    public File getReferentialResource(final String resourceType, final String filename) {

        StringBuffer filePath = new StringBuffer().append(referentialBase);

        // TODO Refactor this part, put it more flexible
        if (resourceType.equals(XSL_RESOURCE_TYPE)) {
            filePath.append("xsl/");
        } else if (resourceType.equals(LOCAL_REFERENTIAL_RESOURCE_TYPE)) {
            filePath.append("local_referential/");
        } else if (resourceType.equals(HTML_RESOURCE_TYPE)) {
            filePath.append("html/");
        } else if (resourceType.equals(MAIL_TEMPLATES_TYPE)) { 
            filePath.append(MAIL_TEMPLATES_TYPE).append("/");
        } else if (resourceType.equals(EXTERNAL_REFERENTIAL_RESOURCE_TYPE)) {
            filePath.append("external_referential/");
        } else if (resourceType.equals(PDF_ASSETS_RESOURCE_TYPE)) {
            filePath.append(PDF_ASSETS_RESOURCE_TYPE).append("/");
        } else {
            logger.warn("getReferentialResource() unrecognized resource type : " + resourceType);
            return null;
        }

        filePath.append(filename);
        
        logger.debug("getReferentialResource() searching file : " + filePath.toString());
        File resourceFile = new File(filePath.toString());
        if (!resourceFile.exists()) {
            logger.warn("getReferentialResource() did not find resource file : " + filename
                    + " of type " + resourceType);
            return null;
        }
        
        return resourceFile;
    }
    
    @Deprecated
    public File getCurrentLocalAuthorityResource(final String resourceType, final String filename,
            final boolean fallbackToDefault) {

        String currentSiteName = SecurityContext.getCurrentSite().getName();
        return getAssetsFile(resourceType, currentSiteName, filename, fallbackToDefault);
    }

    public File getRequestXmlResource(Long id) {
        return new File(getRequestXmlPath(id));
    }
    
    private String getRequestXmlPath(Long id) {
        return String.format("%1$s/%2$s/%3$s/%4$s.xml", 
                this.getAssetsBase(),
                SecurityContext.getCurrentConfigurationBean().getName(),
                REQUEST_XML_RESOURCE_TYPE,
                id);
    }

    @Deprecated
    public String getBufferedCurrentLocalAuthorityResource(final String resourceType, 
            final String filename, final boolean fallbackToDefault) {

        File resourceFile = 
            getCurrentLocalAuthorityResource(resourceType, filename, fallbackToDefault);
        return getFileContent(resourceFile);
    }
    
    public String getBufferedLocalAuthorityResource(String id, boolean fallbackToDefault)
        throws CvqException {
        return getFileContent(getLocalAuthorityResourceFile(id, fallbackToDefault));
    }

    public Map<String,String> getBufferedCurrentLocalAuthorityRequestHelpMap(final String requestLabel) {
        
        StringBuffer requestTypePath = new StringBuffer().append(assetsBase)
            .append(SecurityContext.getCurrentSite().getName().toLowerCase())
            .append("/").append(HTML_RESOURCE_TYPE).append("/request/").append(requestLabel);
            
        File requestTypeDir = new File(requestTypePath.toString());
        
        File[] helpFiles = requestTypeDir.listFiles(new FilenameFilter() { 
            public boolean accept(File dir, String n) {
                if(n.endsWith(".html"))
                    return true;
                else
                    return false;
            }
        });

        HashMap<String,String> helpMap = new HashMap<String,String>();
        if (helpFiles != null)
            for (File helpFile : helpFiles)
                 helpMap.put(helpFile.getName().replace(".html", ""), getFileContent(helpFile));
        
        return helpMap ;
    }

    public List<String> getLocalAuthorityRules(String requestTypeLabel) {
        StringBuffer requestTypePath = new StringBuffer().append(assetsBase)
            .append(SecurityContext.getCurrentSite().getName().toLowerCase())
            .append("/").append(PDF_ASSETS_RESOURCE_TYPE).append("/").append(requestTypeLabel);
        File requestTypeDir = new File(requestTypePath.toString());
        if (!requestTypeDir.exists()) {
            return new ArrayList<String>(0);
        }
        List<String> result = new ArrayList<String>();
        for (String filename : requestTypeDir.list()) {
            result.add(filename.substring(0, filename.lastIndexOf(".pdf")));
        }
        return result;
    }

    private String getFileContent(File resourceFile) {
        
        if (resourceFile == null || !resourceFile.exists())
            return null;
        
        FileReader fileReader = null;
        String result = null;
        try {
            fileReader = new FileReader(resourceFile);
            char[] data = new char[(int) resourceFile.length()];
            fileReader.read(data);
            result = new String(data);
        } catch (FileNotFoundException e) {
            // unlikely to happen since we already checked just above
        } catch (IOException ioe) {
            logger.error("getBufferedCurrentLocalAuthorityResource() error while reading file " 
                    + resourceFile);
            logger.error("getBufferedCurrentLocalAuthorityResource() error : " + ioe);
            return null;
        } finally {
            if (fileReader != null)
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error("getBufferedCurrentLocalAuthorityResource() failed to close file reader stream !");
                    e.printStackTrace();
                }
        }

        return result;        
    }
    
    @Deprecated
    public File getLocalAuthorityResource(final String localAuthorityName, 
            final String resourceType, final String filename, 
            final boolean fallbackToDefault) {

        return getAssetsFile(resourceType, localAuthorityName, filename, fallbackToDefault);
    }
    
    @Deprecated
    public void saveLocalAuthorityResource(String resourceType, String filename, byte[] data) throws CvqException{
        if (data == null) {
            logger.warn("saveLocalAuthorityResource() received empty data to save");
            return;
        }
        String currentSiteName = SecurityContext.getCurrentSite().getName();
        File assetsFile = getAssetsFile(resourceType, currentSiteName, filename, false);
        try {
            if (!assetsFile.exists())
                assetsFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(assetsFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            logger.error("saveLocalAuthorityResource() failel !" + e.getMessage());
            throw new CvqException(e.getMessage());
        } catch (IOException ioe) {
            logger.error("saveLocalAuthorityResource() failel !" + ioe.getMessage());
            throw new CvqException(ioe.getMessage());
        }
    }
    
    public void saveLocalAuthorityResource(String id, byte[] data)
        throws CvqException {
        if (data == null) {
            logger.warn("saveLocalAuthorityResource() received empty data to save");
            return;
        }
        File assetsFile = getLocalAuthorityResourceFile(id, false);
        try {
            if (!assetsFile.exists())
                assetsFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(assetsFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            logger.error("saveLocalAuthorityResource() failel !" + e.getMessage());
            throw new CvqException(e.getMessage());
        } catch (IOException ioe) {
            logger.error("saveLocalAuthorityResource() failel !" + ioe.getMessage());
            throw new CvqException(ioe.getMessage());
        }
    }

    public void replaceLocalAuthorityResource(String id, byte[] data)
        throws CvqException {
        if (data == null) {
            logger.warn("replaceCurrentLocalAuthorityResource() received empty data to save");
            return;
        }
        if (hasLocalAuthorityResource(id, LocalAuthorityResource.Version.CURRENT)) {
            renameLocalAuthorityResource(id, LocalAuthorityResource.Version.CURRENT, LocalAuthorityResource.Version.TEMP);
        }
        try {
            saveLocalAuthorityResource(id, data);
        } catch (CvqException e) {
            if (hasLocalAuthorityResource(id, LocalAuthorityResource.Version.TEMP)) {
                renameLocalAuthorityResource(id, LocalAuthorityResource.Version.TEMP, LocalAuthorityResource.Version.CURRENT);
            }
            throw e;
        }
        if (hasLocalAuthorityResource(id, LocalAuthorityResource.Version.TEMP)) {
            renameLocalAuthorityResource(id, LocalAuthorityResource.Version.TEMP, LocalAuthorityResource.Version.OLD);
        }
    }

    public void rollbackLocalAuthorityResource(String id)
        throws CvqException {
        renameLocalAuthorityResource(id, LocalAuthorityResource.Version.OLD, LocalAuthorityResource.Version.TEMP);
        renameLocalAuthorityResource(id, LocalAuthorityResource.Version.CURRENT, LocalAuthorityResource.Version.OLD);
        renameLocalAuthorityResource(id, LocalAuthorityResource.Version.TEMP, LocalAuthorityResource.Version.CURRENT);
    }

    public boolean hasLocalAuthorityResource(String id, Version version)
        throws CvqException {
        return getLocalAuthorityResourceFile(id, version, false).exists();
    }

    @Deprecated
    public void renameLocalAuthorityResource(String resourceType, String filename, String newFilename) 
        throws CvqException {
        
        String currentSiteName = SecurityContext.getCurrentSite().getName();
        File assetsFile = getAssetsFile(resourceType, currentSiteName, filename, false);

        if (!assetsFile.exists())
            throw new CvqException("File "+ assetsFile.getPath() + "/" + filename + " do not exists !");
        
       boolean succeed = 
           assetsFile.renameTo(getAssetsFile(resourceType, currentSiteName, newFilename, false));
       if (!succeed)
           throw new CvqException("Can't rename "
                   + assetsFile.getPath() + "/" + assetsFile.getName()
                   + " to "
                   + assetsFile.getPath() + "/" + newFilename);
    }

    public void renameLocalAuthorityResource(String id, Version oldVersion, Version newVersion)
        throws CvqException {
        LocalAuthorityResource resource = getLocalAuthorityResource(id);
        File file = getLocalAuthorityResourceFile(id, oldVersion, false);
        if (!file.exists()) {
            throw new CvqException("File "+ file.getPath() + " does not exist !");
        }
        if (!file.renameTo(new File(file.getParent() + "/" + resource.getFilename() + LocalAuthorityResource.versionExtensions.get(newVersion) + resource.getExtension())))
            throw new CvqException("Can't rename "
                    + resource.getId() + " from version "
                    + oldVersion + " to "
                    + newVersion);
    }
    
    @Deprecated
    public void removeLocalAuthorityResource(String resourceType, String filename) {
        String currentSiteName = SecurityContext.getCurrentSite().getName();
        File assetsFile = getAssetsFile(resourceType, currentSiteName, filename, false);
        if (!assetsFile.exists())
            return;
        if (!assetsFile.delete())
            logger.warn("removeLocalAuthorityResource() can't delete " + getAssetsBase() + filename );
    }

    public void removeLocalAuthorityResource(String id)
        throws CvqException {
        File file;
        for (Version version : LocalAuthorityResource.Version.values()) {
            file = getLocalAuthorityResourceFile(id, version, false);
            if (!file.exists())
                continue;
            if (!file.delete())
                logger.warn("removeLocalAuthorityResource() can't delete " + file.getPath() + file.getName());
        }
    }

    public void registerLocalAuthorities(Resource[] localAuthoritiesFiles)
        throws CvqConfigurationException {

        GenericApplicationContext gac = new GenericApplicationContext(parentApplicationContext);
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(gac);
        if (includedLocalAuthorities == null) {
            xmlBeanDefinitionReader.loadBeanDefinitions(localAuthoritiesFiles);
        } else {
            for (int i = 0; i < localAuthoritiesFiles.length; i++) {
                String localAuthorityName =
                    localAuthoritiesFiles[i].getFilename().replaceFirst("localAuthority-", "")
                    .replaceFirst(".xml", "");
                logger.debug("registerLocalAuthorities() extracted " + localAuthorityName);
                for (int j = 0; j < includedLocalAuthorities.length; j++) {
                    if (includedLocalAuthorities[j].equals(localAuthorityName)) {
                        logger.debug("registerLocalAuthorities() loading " + localAuthorityName);
                        xmlBeanDefinitionReader.loadBeanDefinitions(localAuthoritiesFiles[i]);
                        break;
                    }
                }
            }
        }

        Map beansMap = gac.getBeansOfType(LocalAuthorityConfigurationBean.class, true, true);
        if (beansMap.isEmpty()) {
            logger.warn("registerLocalAuthorities() no local authority configuration bean found !");
            return;
        }

        Iterator localAuthoritiesIt = beansMap.values().iterator();
        while (localAuthoritiesIt.hasNext()) {
            LocalAuthorityConfigurationBean lacb =
                (LocalAuthorityConfigurationBean) localAuthoritiesIt.next();
            logger.debug("registerLocalAuthorities() adding [" + lacb.getName()
                         + "] to the list of known local authorities (" + lacb + ")");
            configurationBeansMap.put(lacb.getName().toLowerCase(), lacb);

            if (performDbUpdates.booleanValue()) {
                callback(lacb.getName(), this, "instantiateLocalAuthority", 
                    new Object[]{lacb.getName()});
                
                String externalReferentialDirBase = assetsBase + lacb.getName() + "/"
                    + EXTERNAL_REFERENTIAL_RESOURCE_TYPE;
                File file = new File(externalReferentialDirBase);
                if (!file.exists())
                    file.mkdir();
                
                // TODO Refactor this part, put it more flexible
                String htmlDirBase = assetsBase + lacb.getName() + "/" + 
                    HTML_RESOURCE_TYPE;
                file = new File(htmlDirBase);
                if (!file.exists())
                    file.mkdir();
                
                String mailTemplates = assetsBase + lacb.getName() + "/" + 
                    MAIL_TEMPLATES_TYPE;
                file = new File(mailTemplates);
                if (!file.exists())
                    file.mkdirs();
                
                String requestXmlDirBase = assetsBase + lacb.getName() + "/"
                    + REQUEST_XML_RESOURCE_TYPE;
                file = new File(requestXmlDirBase);
                if (!file.exists())
                    file.mkdir();                
            }
            
            // notify listener services of the new local authority
            for (ILocalAuthorityLifecycleAware service : allListenerServices) {
                service.addLocalAuthority(lacb.getName().toLowerCase());
            }
        }
        
        generateLocalAuthoritiesList();
    }

    protected void instantiateLocalAuthority(String localAuthorityName) 
        throws CvqConfigurationException, CvqException {

        //String localAuthorityName = SecurityContext.getCurrentSite().getName();
        LocalAuthorityConfigurationBean lacb =
            (LocalAuthorityConfigurationBean) configurationBeansMap.get(localAuthorityName);
        
        LocalAuthority localAuthority = localAuthorityDAO.findByName(lacb.getName());
        // create local authority entry in DB if it does not exist yet
        if (localAuthority == null) {
            logger.debug("instantiateLocalAuthority() creating " + localAuthorityName);
            try {
                localAuthority = new LocalAuthority();
                localAuthority.setName(lacb.getName().toLowerCase());
                localAuthorityDAO.create(localAuthority);
            } catch (CvqPermissionException cpe) {
                // can't happen, we are admin here
            } catch (Exception e) {
                throw new CvqConfigurationException("unable to create local authority " 
                        + localAuthorityName);
            }
        }
        if (localAuthority.getServerNames() == null) {
            localAuthority.setServerNames(new TreeSet<String>());
        } else {
            for (String serverName : localAuthority.getServerNames()) {
                registerLocalAuthorityServerName(serverName);
            }
        }
        if (localAuthority.getServerNames().isEmpty()) {
            try {
                addLocalAuthorityServerName("vosdemarches.ville-" + lacb.getName() + ".fr");
            } catch (CvqPermissionException e) {
                // can't happen, we are admin here
            }
        }
    }

    @Deprecated
    public void updateDraftSettings(Integer liveDuration, Integer notificationBeforeDelete) 
        throws CvqException {
        LocalAuthority localAuthority = SecurityContext.getCurrentSite();
        localAuthority.setDraftLiveDuration(liveDuration);
        localAuthority.setDraftNotificationBeforeDelete(notificationBeforeDelete);
        localAuthorityDAO.saveOrUpdate(localAuthority);
    }
    
    public void callback(String localAuthority, Object object, String callbackMethodName, 
            Object[] args) {
        
        LocalAuthorityConfigurationBean lacb =
            (LocalAuthorityConfigurationBean) configurationBeansMap.get(localAuthority);
        SessionFactory sessionFactory = lacb.getSessionFactory();
        HibernateUtil.setSessionFactory(sessionFactory);
        try {
            SecurityContext.setCurrentSite(lacb.getName(), SecurityContext.ADMIN_CONTEXT);
        } catch (CvqObjectNotFoundException confe) {
            // unlikely to happen
        }

        try {
            
            HibernateUtil.beginTransaction();

            if (args == null || args.length == 0) {
                Method method = 
                    object.getClass().getDeclaredMethod(callbackMethodName);
                method.invoke(object);
            } else {
                Object[] methArgs = new Object[args.length];
                Class[] methDefArgs = new Class[args.length];
                for (int i=0; i < args.length; i++) {
                    methArgs[i] = args[i];
                    methDefArgs[i] = String.class;
                }
                Method method = 
                    object.getClass().getDeclaredMethod(callbackMethodName, methDefArgs);
                method.invoke(object, methArgs);
            }

            HibernateUtil.commitTransaction();

        } catch (Exception e) {
            logger.error("callback() got an exception, rollbacking");
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.closeSession();
            SecurityContext.resetCurrentSite();
        }
    }
    
	public void browseAndCallback(Object object, String callbackMethodName, 
            Object[] methodArgs) {
		for (String localAuthorityName : configurationBeansMap.keySet()) {
            logger.debug("browseAndCallback() calling " + callbackMethodName
                    + " for " + localAuthorityName);
			callback(localAuthorityName, object, callbackMethodName, methodArgs);
        }
	}

    public void generateLocalAuthoritiesList() {
        Set<String> allLocalAuthoriesNames = getAllLocalAuthoritiesNames();

        try {
            String filename = getAssetsBase() + localAuthoritiesListFilename;
            logger.debug("generateLocalAuthoritiesList() writing list in " + filename);
            FileOutputStream fos = new FileOutputStream(filename);
            for (String localAuth : allLocalAuthoriesNames) {
                fos.write(localAuth.getBytes());
                fos.write('\n');
            }
            fos.close();
        } catch (Exception e) {
            logger.error("generateLocalAuthoritiesList() got exception while writing local authorities list");
            e.printStackTrace();
        }
    }

    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.parentApplicationContext = applicationContext;
    }

    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    public void setReferentialBase(final String referentialBase) {
        if (!referentialBase.endsWith("/"))
            this.referentialBase = referentialBase + "/";
        else
            this.referentialBase = referentialBase;
    }
    
    public List<File> getLocalResourceContent(String resourceType) 
        throws CvqException {
        return this.getLocalResourceContent(resourceType, "*");
    }
    public List<File> getLocalResourceContent(String resourceType, final String pattern) 
        throws CvqException {
        StringBuffer path = new StringBuffer();
        if (pattern == null) 
            throw new CvqException("localresources.mask_cannt_be_null");
        
        path.append(assetsBase).append("/")
            .append(SecurityContext.getCurrentSite().getName())
            .append("/").append(resourceType);
        
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().matches(pattern);
            }
        };
        File file = new File(path.toString());
        return Arrays.asList(file.listFiles(filter));
    }

    public String getReferentialBase() {
        return this.referentialBase;
    }

    public String getAssetsBase() {
        return assetsBase;
    }

    public void setAssetsBase(String assetsBase) {
        if (!assetsBase.endsWith("/")) 
            this.assetsBase = assetsBase + "/";
        else
            this.assetsBase = assetsBase;
    }

    public void setLocalAuthorityDAO(ILocalAuthorityDAO localAuthorityDAO) {
        this.localAuthorityDAO = localAuthorityDAO;
    }

    public void setPerformDbUpdates(Boolean performDbUpdates) {
        if (performDbUpdates != null)
            this.performDbUpdates = performDbUpdates;
        else
            this.performDbUpdates = Boolean.FALSE;
    }

    public void setIncludes(final String includes) {
        if (!includes.equals("") && !includes.equals("**"))
            includedLocalAuthorities = includes.split(",");
    }
    
    public void setLocalAuthoritiesListFilename(final String localAuthoritiesListFilename) {
        this.localAuthoritiesListFilename = localAuthoritiesListFilename;
    }
}
