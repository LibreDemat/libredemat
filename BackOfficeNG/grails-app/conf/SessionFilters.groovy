import javax.persistence.EntityManagerFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import zdb.core.Store
import edu.yale.its.tp.cas.client.CASAuthenticationException
import edu.yale.its.tp.cas.client.CASReceipt
import edu.yale.its.tp.cas.client.ProxyTicketValidator
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import org.libredemat.authentication.IAuthenticationService
import org.libredemat.business.authority.LocalAuthority
import org.libredemat.dao.jpa.JpaUtil
import org.libredemat.exception.CvqException
import org.libredemat.exception.CvqObjectNotFoundException
import org.libredemat.oauth2.IOAuth2Service
import org.libredemat.oauth2.OAuth2Exception
import org.libredemat.oauth2.model.AccessToken
import org.libredemat.security.SecurityContext
import org.libredemat.security.annotation.ContextType
import org.libredemat.service.authority.IAgentService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.service.authority.LocalAuthorityConfigurationBean
import org.libredemat.service.request.ICategoryService
import org.libredemat.util.web.filter.CASFilter
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib
import org.libredemat.service.users.IUserSearchService

import org.apache.log4j.MDC

class SessionFilters {

    def securityService
    
    IAgentService agentService
    ICategoryService categoryService
    ILocalAuthorityRegistry localAuthorityRegistry
    IOAuth2Service oauth2Service
    IAuthenticationService authenticationService
    IUserSearchService userSearchService

    static filters = {
        
        logBefore(controller: '*', action: '*') {
            before = {
                log.debug(
                    session.currentEcitizenId
                    + " " + session.currentCredentialBean
                    + " " + request.method.toUpperCase()
                    + " /" + controllerName
                    + "/" + actionName)
            }
        }
        
        monitoring(controller:'system', action:'monitoring') {
            before = {
                return false
            }
        }

        // filter used for local authority resources (images, css, pdf, ...)
        // that do not need a BD connection
        openSiteContextOnly(controller:'localAuthorityResource') {
            before = {
                try {
                    LocalAuthority la = localAuthorityRegistry.getLocalAuthorityByServerName(request.serverName)
                    if (la == null) {
                        log.error "No local authority found for domain : ${request.serverName}"
                        response.setStatus(500)
                        render "No local authority found for domain : ${request.serverName}"
                        return false
                    }
                    LocalAuthorityConfigurationBean lacb =
                            localAuthorityRegistry.getLocalAuthorityBeanByName(la.name)
                    if (lacb == null) {
                        log.error "No LACB found for local authority : ${la.name}"
                        response.setStatus(500)
                        render "No LACB found for local authority : ${la.name}"
                        return false
                    }
                    MDC.put("local_authority", la.getName())
                    SecurityContext.setCurrentSite(la, SecurityContext.BACK_OFFICE_CONTEXT)
                    
                } catch (Throwable t) {
                    log.error "Unexpected error while setting local authority context : ${t.message}"
                    response.setStatus(500)
                    render "Unexpected error while setting local authority context : ${t.message}"
                    t.printStackTrace()
                    return false
                }
            }
            afterView = {
                SecurityContext.resetCurrentSite();
            }
        }
        
        openSessionInView(controller:'localAuthorityResource', invert:'true') {
            before = {
                try {
                    LocalAuthority la
                    LocalAuthorityConfigurationBean lacb
                    if (controllerName != "serviceProvisioning") {
	                    la = localAuthorityRegistry.getLocalAuthorityByServerName(request.serverName)
	                    if (la == null) {
	                        log.error "No local authority found for domain : ${request.serverName}"
	                        response.setStatus(500)
	                        render "No local authority found for domain : ${request.serverName}"
	                        return false
	                    }
	                    lacb = localAuthorityRegistry.getLocalAuthorityBeanByName(la.name)
	                    if (lacb == null) {
	                        log.error "No LACB found for local authority : ${la.name}"
	                        response.setStatus(500)
	                        render "No LACB found for local authority : ${la.name}"
	                        return false
	                    }
                    } else {
                        lacb = localAuthorityRegistry.getLocalAuthorityBeanByName(params.localAuthority)
                    }

                    EntityManagerFactory entityManagerFactory = lacb.getEntityManagerFactory()
                    if (flash.redirect) {
                        log.debug "In filters redirect, rollbacking current transaction"
                        JpaUtil.init(entityManagerFactory)
                    } else if (controllerName == "system" && actionName == "error") {
                        JpaUtil.eventualInit(entityManagerFactory)
                    } else {
                        JpaUtil.init(entityManagerFactory)
                    }

		    // this one requires having a DB connection opened
                    if (controllerName == "serviceProvisioning") {
                        la = localAuthorityRegistry.getLocalAuthorityByName(lacb.name)
                    }

                    Store.init(new File(localAuthorityRegistry.assetsBase + la.name, "zdb"))
                    SecurityContext.setCurrentSite(la, SecurityContext.BACK_OFFICE_CONTEXT)
                    SecurityContext.setCurrentLocale(Locale.FRENCH)
                    MDC.put("local_authority", la.getName())
                    session.setAttribute("currentSiteName", la.name.toLowerCase())
                    session.setAttribute("currentSiteDisplayTitle", la.displayTitle)
                    session.setAttribute("additionalTabs", lacb.getAdditionalTabs())
                    session.setAttribute("doRollback", false)
                    def findIt = lacb.externalServices.find{it.key.label == "CirilNetEnfance"}
                    if(findIt != null){
                      session.setAttribute("supportsReservationTab", true)
                    } else {
                      session.setAttribute("supportsReservationTab", false)
                    }


                } catch (Throwable t) {
                	log.error "Unexpected error while setting local authority context : ${t.message}"
                	response.setStatus(500)
                	render "Unexpected error while setting local authority context : ${t.message}"
                    t.printStackTrace()
					return false
                }
            }
            afterView = {
                def doRollback = session.getAttribute("doRollback")
                JpaUtil.close(doRollback)
                Store.release();
            }
        }

        enableAddressesReferential(controller: '*', action: '*') {
            before = {
                if (SecurityContext.currentSite.token != null
                    && SecurityContext.currentSite.token != "") {
                    flash.put("addressesReferentialEnabled", !!SecurityContext.currentSite.token)
                }
            }
        }

        includeGoogleAnalytics(uri: '/frontoffice/**') {
            before = {
                if (SecurityContext.currentSite.googleAnalyticsId != null
                && SecurityContext.currentSite.googleAnalyticsId != "") {
                    session.setAttribute("googleAnalyticsId", SecurityContext.currentSite.googleAnalyticsId)
                }
            }
        }

        setupFrontUser(uri: '/frontoffice/**') {
            before = {
                // Avoid looping in this filter, go out if we detect that we are dealing with an error
                // Still could not figure out how we entered in ...
                if (controllerName == 'system' && actionName == 'error')
                    return true

                if (params.owner) {
                    session.setAttribute(
                        'templatePath',
                        'templates/fo/' + params.owner + '/' + (params.template?: 'default'))
                }
                def callbackURI = request.forwardURI+(request.queryString ? "?"+request.queryString : "")
                def point =
                    securityService.defineAccessPoint(session.frontContext,
                        SecurityContext.FRONT_OFFICE_CONTEXT, controllerName,
                        actionName)
                log.debug "Security service returned point : ${point}"
                try {
                    SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT)
                    if (session.currentAgentId != null && session.currentEcitizenId == null && session.startAgentSpoofEcitizenProcess == null) {
                        redirect(controller: 'backofficeLogin', action: 'logout')
                        return false
                    } else if (session.frontContext == ContextType.AGENT && session.currentEcitizenId != null) {
                        SecurityContext.setProxyAgent(session.currentUser)
                        session.proxyAgent = SecurityContext.proxyAgent
                    } else {
                        SecurityContext.resetProxyAgent()
                        session.proxyAgent = null
                    }
                    if ((point.controller == controllerName && point.action != actionName) || 
                        (point.controller != controllerName)) {
                        if(point.action) redirect(controller: point.controller, action: point.action,params : ["callback" : callbackURI])
                        else redirect(controller: point.controller)
                        flash.redirect = true
                        return false
                    } else if (session.currentEcitizenId) {
                        SecurityContext.setCurrentEcitizen(session.currentEcitizenId)
                        session.setAttribute("currentCredentialBean", SecurityContext.currentCredentialBean)
                        if(session.additionalTabs.contains("Planning") && !userSearchService.hasExternalLibredematId(session.currentEcitizenId))
                            session.additionalTabs.remove("Planning")
                    }
                } catch (CvqObjectNotFoundException ce) {
                    log.error "Object not found : ${ce}"
                    securityService.logout(session)
                    redirect(controller: 'frontofficeHome', action: 'login')
                    flash.redirect = true
                    return false
                } catch (CvqException ce) {
                    securityService.logout(session)
                	log.error "Unexpected error while setting current ecitizen : ${ce.message}"
                	response.setStatus(500)
                	render "Unexpected error while setting current ecitizen : ${ce.message}"
					return false
                }
            }
        }

        emailCheck(controller: 'frontofficeHomeFolder|frontofficeHome', action: 'editImportedAccount|logout', invert: true) {
            before = {
                if (!SecurityContext.isFrontOfficeContext())
                    return true
                def ecitizen = SecurityContext.getCurrentEcitizen()
                def userId = SecurityContext.getCurrentUserId()

                if (userId != null && ecitizen != null &&
                    ecitizen.homeFolder.isImportedAndNotInitialized() && !agentService.exists(userId)) {
                    redirect(controller: "frontofficeHomeFolder", action: "editImportedAccount")
                    flash.redirect = true
                    return false
                }
            }
        }

        casAuth(uri: '/backoffice/**') {
            before = {
            		
                if (authenticationService.getAuthenticationMethod() != "cas") {
                    return true
                }

                if (CH.config.cas_mocking == 'true') {
            		if (session.getAttribute(CASFilter.CAS_FILTER_USER) == null) {
            			response.sendRedirect('/LibreDemat/cas.gsp')
                        flash.redirect = true
            			return false
            		} else {
            			return true
            		}
            	}
            	
                CASReceipt receipt = (CASReceipt) session.getAttribute(CASFilter.CAS_FILTER_RECEIPT);
                String ticket = request.getParameter("ticket")

                if (receipt == null && ticket == null) {
                	// TODO : did gateway support
                	
                   	def redirectUrl =
                   	  "${CH.config.cas_login_url}?localAuthority=${session.getAttribute('currentSiteName')}&service=https://${request.serverName}${request.forwardURI}"
                   	response.sendRedirect(redirectUrl)
                    flash.redirect = true
                   	return false
                }

                if (ticket != null) {
                	try {
                		ProxyTicketValidator pv = null;
                		pv = new ProxyTicketValidator()
                		pv.setCasValidateUrl(CH.config.cas_validate_url)
                		pv.setServiceTicket(ticket)
                		pv.setService("https://${request.serverName}${request.forwardURI}")
                		pv.setRenew(false)
                		receipt = CASReceipt.getReceipt(pv)

                		session.setAttribute(CASFilter.CAS_FILTER_USER, receipt.getUserName())
                		session.setAttribute(CASFilter.CAS_FILTER_RECEIPT, receipt)

                	} catch (CASAuthenticationException e) {
                    	log.error "Unable to validate proxy ticket : ${e.message}"
                    	response.setStatus(500)
                    	render "Unable to validate proxy ticket : ${e.message}"
                        e.printStackTrace()
    					return false
                	}
                }
            }
        }
        

        oauth2Auth(uri: '/backoffice/**') {
          before = {
            if (authenticationService.getAuthenticationMethod() != "oauth2") {
              return true
            }
            try {
              SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
              if (!session.currentUser) {
                  if (controllerName == 'backofficeLogin') {
                    return true
                  } else {
                    def callback = params.callback ?: new ApplicationTagLib().createLink(controller:'backofficeHome').toString()
                    def url = oauth2Service.authorizationRequestUri(callback, true)
                    response.sendRedirect(url)
                    return false
                  }
              } else {
                  SecurityContext.setCurrentAgent(session.currentUser)
                  session.setAttribute("currentCredentialBean", SecurityContext.currentCredentialBean)

                  def point = securityService.defineAccessPoint(
                    session.currentCredentialBean.hasSiteAdminRole() ?
                    ContextType.ADMIN : ContextType.AGENT,
                    SecurityContext.BACK_OFFICE_CONTEXT,
                    controllerName, actionName)
                  if (point.controller != controllerName || point.action != actionName) {
                    redirect(controller: point.controller, action: point.action)
                    return false
                  }
              }
            } catch (CvqObjectNotFoundException ce) {
              session.currentUser = null
              redirect(controller: 'backofficeLogin')
              return false
            } catch (CvqException ce) {
              if (session.currentUser) session.currentUser = null
              log.error "Unexpected error while setting current agent : ${ce.message}"
              response.setStatus(500)
              render "Unexpected error while setting current agent : ${ce.message}"
              ce.printStackTrace()
              return false
            }
          }
        }

        builtinAuth(uri: '/backoffice/**') {
            before = {
                if (authenticationService.getAuthenticationMethod() != "builtin") {
                    return true
                }
                try {
                    SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
                    if (!session.currentUser) {
                        if (controllerName != 'backofficeLogin') {
                            redirect(controller: 'backofficeLogin')
                            return false
                        } else {
                            return true
                        }
                    } else {

                        SecurityContext.setCurrentAgent(session.currentUser)
                        session.setAttribute("currentCredentialBean", SecurityContext.currentCredentialBean)

                        def point = securityService.defineAccessPoint(
                        	session.currentCredentialBean.hasSiteAdminRole() ?
                        		ContextType.ADMIN : ContextType.AGENT,
                        	SecurityContext.BACK_OFFICE_CONTEXT,
                        	controllerName, actionName)
                        if (point.controller != controllerName || point.action != actionName) {
                        	redirect(controller: point.controller, action: point.action)
                        	return false
                        }
        	        }
        	    } catch (CvqObjectNotFoundException ce) {
        	        session.currentUser = null
        	        redirect(controller: 'backofficeLogin')
        	        return false
        	    } catch (CvqException ce) {
        	        if (session.currentUser) session.currentUser = null
                	log.error "Unexpected error while setting current agent : ${ce.message}"
                	response.setStatus(500)
                	render "Unexpected error while setting current agent : ${ce.message}"
                    ce.printStackTrace()
					return false
        	    }
        	}
        }

        setupBackUser(uri: '/backoffice/**') {
        	before = {
               if (authenticationService.getAuthenticationMethod() != "cas") {
                   return true
               }

                String user = (String) session.getAttribute(CASFilter.CAS_FILTER_USER)
                if (user != null && user.indexOf(";") != -1) {
                    // we are receiving a chain with user and groups information
                    Map<String, ArrayList<String>> id = new HashMap<String, ArrayList<String>>();
                    String[] splitted = user.split(";");
                    for (int i = 0; i < splitted.length; i++) {
                        String token = splitted[i];
                        if (token.indexOf("=") != -1) {
                            String[] keyVal = token.split("=");
                            String key = keyVal[0];
                            String value = keyVal[1];
                            if (!id.containsKey(key))
                                id.put(key, new ArrayList<String>());
                            id.get(key).add(value);
                        }
                    }
                    if (!id.containsKey("username")) {
                    	log.error "No username parameter found"
                    	response.setStatus(500)
                    	render "No username parameter found"
    					return false
                    }
                    user = id.get("username").get(0);

                    if (id.get("localAuthority") != null) {
                        String authorizedLocalAuthority = id.get("localAuthority").get(0).toLowerCase()
                        String currentLocalAuthority = SecurityContext.getCurrentConfigurationBean().getName()
                        if (!authorizedLocalAuthority.equals(currentLocalAuthority)) {
                        	log.error "User is not authorized to access local authority : ${currentLocalAuthority}"
                        	response.setStatus(500)
                        	render "User is not authorized to access local authority : ${currentLocalAuthority}"
        					return false
                        }
                    } else {
                    	log.error "No local authority found in CAS information, got : ${user}"
                    	response.setStatus(500)
                    	render "No local authority found in CAS information, got : ${user}"
    					return false
                    }

                    List groups = (List) id.get("group")
                    if (groups == null || !SecurityContext.isAuthorizedGroup(groups)) {
                    	log.error "User ${user} is not authorized to access this resource"
                    	response.setStatus(500)
                    	render "User ${user} is not authorized to access this resource"
    					return false
                    }
                    Map<String, String> userInformations = new HashMap<String, String>()
                    if (id.get("firstName") != null)
                        userInformations.put("firstName", id.get("firstName").get(0))
                    if (id.get("lastName") != null)
                        userInformations.put("lastName", id.get("lastName").get(0))
                    if (id.get("email") != null)
                        userInformations.put("email", id.get("email").get(0))
                    try {
                        SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
                        agentService.updateUserProfiles(user, groups, userInformations)
                        JpaUtil.closeAndReOpen(false)
                        SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT)
                        SecurityContext.setCurrentAgent(user)
                        session.setAttribute("currentUser", user)
                        session.setAttribute("currentCredentialBean", SecurityContext.currentCredentialBean)
                        log.debug("setting " + user + " on attribute " + CASFilter.CAS_FILTER_USER)
                        session.setAttribute(CASFilter.CAS_FILTER_USER, user)
                    } catch (CvqException e) {
                    	log.error "Unexpected error while setting agent in security context (${e.message})"
                    	response.setStatus(500)
                    	render "Unexpected error while setting agent in security context (${e.message})"
                        e.printStackTrace()
    					return false
                    }
                } else {
                    // set current user in security context for him to be available for using webapps
                    try {
                        SecurityContext.setCurrentAgent(user)
                        session.setAttribute("currentCredentialBean", SecurityContext.currentCredentialBean)
                    } catch (CvqException e) {
                    	log.error "Unexpected error while setting agent in security context"
                    	response.setStatus(500)
                    	render "Unexpected error while setting agent in security context"
                        e.printStackTrace()
						JpaUtil.close(true)
						SecurityContext.resetCurrentSite()
    					return false
                    }
                }
                def point = securityService.defineAccessPoint(
                    session.currentCredentialBean.hasSiteAdminRole() ?
                        ContextType.ADMIN : ContextType.AGENT,
                    SecurityContext.BACK_OFFICE_CONTEXT,
                    controllerName, actionName)
                if (point.controller != controllerName || point.action != actionName) {
                    redirect(controller: point.controller, action: point.action)
                    flash.redirect = true
                    return false
                }
        	}
        }

		    setBackOfficeAgentForRequests(uri: '/backoffice/**') {
			    before = {
			        if (SecurityContext.currentAgent) {
			            if (!categoryService.getManaged().isEmpty()) {
			                session['isACategoryManager'] = true
			            }
			            else {
			                session['isACategoryManager'] = false
			            }
			        }
			        return true
			    }
		    }

        setProvisioningContext(uri: '/service/provisioning/**') {
            before = {
                SecurityContext.setCurrentContext(SecurityContext.ADMIN_CONTEXT)
            }
        }

        oauth2(uri: '/service/oauth/**') {
            before = {
                try {
                    String authHeader = ((HttpServletRequest) request).getHeader("Authorization");
                    log.debug("Authorization header : " + authHeader);

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        log.debug("OAuth2 token is missing.");
                        throw new OAuth2Exception();
                    }

                    String [] bearer = authHeader.split(" ", 2);
                    AccessToken token = oauth2Service.valide(bearer[1]);
                    request.setAttribute("accessToken", token);

                    String [] scopeExternalServiceLabel = token?.getScope().split("esl:");
                    if (scopeExternalServiceLabel?.length == 2) {
                        String externalServiceLabel = scopeExternalServiceLabel[1].split(" ")[0].replaceAll("_", " ")
                        SecurityContext.setCurrentContext(SecurityContext.EXTERNAL_SERVICE_CONTEXT);
                        SecurityContext.setCurrentExternalService(externalServiceLabel);
                    }
                } catch (OAuth2Exception e) {
                    log.info(e.getMessage(), e);
                    response.setStatus(e.getHttpErrorCode())
                    render e.getJson()
                    return false;
                } catch (Exception e) {
                    log.error("Unexpected authorization exception.", e);
                    response.setStatus(500)
                    render "{\"error\":\"unexpected_exception.\", \"error_description\": \"Unexpected exception\"}"
                    return false;
                }
            }
        }
    }
}
