import org.libredemat.security.*
import grails.converters.*

import net.sf.json.groovy.JsonSlurper
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.commons.*
import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.json.parser.JSONParser

import java.lang.reflect.InvocationTargetException
import org.libredemat.security.SecurityContext
import org.libredemat.util.JSONUtils

/**
 * Autocompletion service
 * 
 * @author Inexine : Frederic Fabre
 *
 */
public class ServiceAutocompletionController {

  /** Logger */
  private static Logger logger = Logger.getLogger(ServiceAutocompletionController.class)

  public static boolean wayExistAddressReferential(String cityCodeInsee, String city, String streetName) {

    def token = SecurityContext.getCurrentSite().token ?: ""
    def url = SecurityContext.getCurrentSite().adressesReferentialUrl ?: ""
    def retour = true
    JSON resultat = null

    if(url != null && token != null && url != "" && token != "" && cityCodeInsee!=null && cityCodeInsee!="") {
        if(SecurityContext.getCurrentConfigurationBean().isAddresseReferentialCityRestriction()) {
            if(org.apache.commons.lang3.StringUtils.equals(SecurityContext.getCurrentSite().getDisplayTitle().toLowerCase(), city.toLowerCase())) {
                def http = new HTTPBuilder(url)
                resultat = http.get(path: '/ways', query: [city: cityCodeInsee, search: streetName, token:token]) as JSON
                if (resultat?.toString()?.contains("name") && resultat?.toString()?.contains("\""+streetName+"\"")) { // FIXME : real check ...
                    retour = true
                } else {
                    retour = false
                }
            }
        }
    }
    return retour
  }
}
