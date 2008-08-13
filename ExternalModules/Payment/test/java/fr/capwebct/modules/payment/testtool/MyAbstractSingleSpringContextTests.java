package fr.capwebct.modules.payment.testtool;


import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.AbstractSpringContextTests;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * A rewrite of the Spring's MyAbstractSingleSpringContextTests to have
 * an application context that can be shared by multiple instances of a test case. 
 * Used in load tests where we want to simulate a web application behavior.
 */
public abstract class MyAbstractSingleSpringContextTests extends AbstractSpringContextTests {

    /** Application context this test will run against */
    protected ConfigurableApplicationContext applicationContext;
    private static Object syncObject = new Object();

    private int loadCount = 0;

    /**
     * Default constructor for AbstractDependencyInjectionSpringContextTests.
     */
    public MyAbstractSingleSpringContextTests() {
    }

    /**
     * Constructor for AbstractDependencyInjectionSpringContextTests with a JUnit name.
     * @param name the name of this text fixture
     */
    public MyAbstractSingleSpringContextTests(String name) {
        super(name);
    }


    /**
     * This implementation is final.
     * Override <code>onSetUp</code> for custom behavior.
     * @see #onSetUp()
     */
    protected final void setUp() throws Exception {
        synchronized (syncObject) {
            this.applicationContext = getContext(contextKey());
            prepareTestInstance();
            onSetUp();
        }
    }

    /**
     * Prepare this test instance, for example populating its fields.
     * The context has already been loaded at the time of this callback.
     * <p>The default implementation does nothing.
     * @throws Exception in case of preparation failure
     */
    protected void prepareTestInstance() throws Exception {
    }

    /**
     * Subclasses can override this method in place of the
     * <code>setUp()</code> method, which is final in this class.
     * This implementation does nothing.
     * @throws Exception simply let any exception propagate
     */
    protected void onSetUp() throws Exception {
    }

    /**
     * Called to say that the "applicationContext" instance variable is dirty and
     * should be reloaded. We need to do this if a test has modified the context
     * (for example, by replacing a bean definition).
     */
    protected void setDirty() {
        setDirty(contextKey());
    }

    /**
     * This implementation is final.
     * Override <code>onTearDown</code> for custom behavior.
     * @see #onTearDown()
     */
    protected final void tearDown() throws Exception {
        onTearDown();
    }

    /**
     * Subclasses can override this to add custom behavior on teardown.
     * @throws Exception simply let any exception propagate
     */
    protected void onTearDown() throws Exception {
    }


    /**
     * Return a key for this context. Default is the config location array
     * as determined by {@link #getConfigLocations()}.
     * <p>If you override this method, you will typically have to override
     * {@link #loadContext(Object)} as well, being able to handle the key type
     * that this method returns.
     * @return the context key
     * @see #getConfigLocations()
     */
    protected Object contextKey() {
        return getConfigLocations();
    }

    /**
     * This implementation assumes a key of type String array and loads
     * a context from the given locations.
     * <p>If you override {@link #contextKey()}, you will typically have to
     * override this method as well, being able to handle the key type
     * that <code>contextKey()</code> returns.
     * @see #getConfigLocations()
     */
    protected ConfigurableApplicationContext loadContext(Object key) throws Exception {
        return loadContextLocations((String[]) key);
    }

    /**
     * Load a Spring ApplicationContext from the given config locations.
     * <p>The default implementation creates a standard
     * {@link #createApplicationContext GenericApplicationContext},
     * allowing for customizing the internal bean factory through
     * {@link #customizeBeanFactory}.
     * @param locations the config locations (as Spring resource locations,
     * e.g. full classpath locations or any kind of URL)
     * @return the corresponding ApplicationContext instance (potentially cached)
     * @throws Exception if context loading failed
     * @see #createApplicationContext
     * @see #customizeBeanFactory
     */
    protected ConfigurableApplicationContext loadContextLocations(String[] locations) throws Exception {
        ++this.loadCount;
        if (logger.isInfoEnabled()) {
            logger.info("Loading context for locations: " + StringUtils.arrayToCommaDelimitedString(locations));
        }
        return createApplicationContext(locations);
    }

    /**
     * Modified to support multiple context refreshing.
     * 
     * Create a Spring ApplicationContext for use by this test.
     * <p>The default implementation creates a standard GenericApplicationContext
     * instance, populates it from the specified config locations through a
     * {@link org.springframework.beans.factory.xml.XmlBeanDefinitionReader},
     * and calls {@link #customizeBeanFactory} to allow for customizing the
     * context's DefaultListableBeanFactory.
     * @param locations the config locations (as Spring resource locations,
     * e.g. full classpath locations or any kind of URL)
     * @return the GenericApplicationContext instance
     * @see #loadContextLocations
     * @see #customizeBeanFactory
     */
    protected ConfigurableApplicationContext createApplicationContext(String[] locations) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(locations);
        return context;
    }

    /**
     * Customize the internal bean factory of the ApplicationContext used by this test.
     * <p>The default implementation is empty. Can be overridden in subclasses
     * to customize DefaultListableBeanFactory's standard settings.
     * @param beanFactory the newly created bean factory for this context
     * @see #loadContextLocations
     * @see #createApplicationContext
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowEagerClassLoading
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowCircularReferences
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowRawInjectionDespiteWrapping
     */
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
    }


    /**
     * Subclasses can override this method to return the locations of their
     * config files, unless they override {@link #contextKey()} and
     * {@link #loadContext(Object)} instead.
     * <p>A plain path will be treated as class path location, e.g.:
     * "org/springframework/whatever/foo.xml". Note however that you may prefix path
     * locations with standard Spring resource prefixes. Therefore, a config location
     * path prefixed with "classpath:" with behave the same as a plain path, but a
     * config location such as "file:/some/path/path/location/appContext.xml" will
     * be treated as a filesystem location.
     * <p>The default implementation builds config locations for the config paths
     * specified through {@link #getConfigPaths()}.
     * @return an array of config locations
     * @see #getConfigPaths()
     * @see org.springframework.core.io.ResourceLoader#getResource(String)
     */
    protected String[] getConfigLocations() {
        String[] paths = getConfigPaths();
        String[] locations = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (path.startsWith("/")) {
                locations[i] = ResourceUtils.CLASSPATH_URL_PREFIX + path;
            }
            else {
                locations[i] = ResourceUtils.CLASSPATH_URL_PREFIX +
                        StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()) + "/" + path);
            }
        }
        return locations;
    }

    /**
     * Subclasses can override this method to return paths to their
     * config files, relative to the concrete test class.
     * <p>A plain path, e.g. "context.xml", will be loaded as classpath resource
     * from the same package that the concrete test class is defined in.
     * A path starting with a slash is treated as fully qualified class path
     * location, e.g.: "/org/springframework/whatever/foo.xml".
     * <p>The default implementation builds an array for the config path
     * specified through {@link #getConfigPath()}.
     * @return an array of config locations
     * @see #getConfigPath()
     * @see java.lang.Class#getResource(String)
     */
    protected String[] getConfigPaths() {
        String path = getConfigPath();
        return (path != null ? new String[] {path} : new String[0]);
    }

    /**
     * Subclasses can override this method to return a single path to a
     * config file, relative to the concrete test class.
     * <p>A plain path, e.g. "context.xml", will be loaded as classpath resource
     * from the same package that the concrete test class is defined in.
     * A path starting with a slash is treated as fully qualified class path
     * location, e.g.: "/org/springframework/whatever/foo.xml".
     * <p>The default implementation simply returns <code>null</code>.
     * @return an array of config locations
     * @see #getConfigPath()
     * @see java.lang.Class#getResource(String)
     */
    protected String getConfigPath() {
        return null;
    }


    /**
     * Return the ApplicationContext that this base class manages.
     */
    public final ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * Return the current number of context load attempts.
     */
    public final int getLoadCount() {
        return this.loadCount;
    }

}