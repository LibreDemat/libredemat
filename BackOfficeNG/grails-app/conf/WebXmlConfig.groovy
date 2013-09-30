/**
 * Application configuration file for WebXml plugin.
 */
webxml {
    //========================================
    // Delegating Filter Chain
    //========================================
    // 
    // Add a 'filter chain proxy' delegater as a Filter.  This will allow the application
    // to define a FilterChainProxy bean that can add additional filters, such as
    // an instance of org.acegisecurity.util.FilterChainProxy.
    
    // Set to true to add a filter chain delegator.
    //filterChainProxyDelegator.add = true
    
    // The name of the delegate FilterChainProxy bean.  You must ensure you have added a bean
    // witht his name that implements FilterChainProxy to
    // YOUR-APP/grails-app/conf/spring/resources.groovy.
    //filterChainProxyDelegator.targetBeanName = "filterChainProxyDelegate"
    
    // The URL pattern to which the filter will apply.  Usually set to '/*' to cover all URLs.
    //filterChainProxyDelegator.urlPattern = "/*"

    // Set to true to add Listeners
    listener.add = true
    listener.classNames = ["TicketBookingSessionListener","org.codehaus.groovy.grails.web.util.Log4jConfigListener"]

    //-------------------------------------------------
    // These settings usually do not need to be changed
    //-------------------------------------------------
    
    // The name of the delegating filter.
    //filterChainProxyDelegator.filterName = "filterChainProxyDelegator"
    
    // The delegating filter proxy class.
    //filterChainProxyDelegator.className = "org.springframework.web.filter.DelegatingFilterProxy"

    // ------------------------------------------------
    // Example for context aparameters
    // ------------------------------------------------
    // this example will create the following XML part
    // contextparams = [port: '6001']
    //
    //  <context-param>
    //    <param-name>port</param-name>
    //    <param-value>6001</param-value>
    //  </context-param>

    servlet.add = true
    servlet.names = ['soap-ws':'org.springframework.ws.transport.http.MessageDispatcherServlet']
    servlet.initparams = ['soap-ws':['transformWsdlLocations':'true']]
    servlet.mappings = ['soap-ws':'/service/soap/*']

    filter.add = true
    filter.names = ['SoapOpenSessionInViewFilter':'fr.cg95.cvq.util.web.filter.CvqOpenSessionInViewFilter']
    filter.initparams = ['SoapOpenSessionInViewFilter':['context':'backOffice']]
    filter.mappings = ['SoapOpenSessionInViewFilter':'/service/soap/*']
}
