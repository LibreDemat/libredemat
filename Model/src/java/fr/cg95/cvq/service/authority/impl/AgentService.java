package fr.cg95.cvq.service.authority.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import fr.cg95.cvq.authentication.IAuthenticationService;
import fr.cg95.cvq.business.authority.Agent;
import fr.cg95.cvq.business.authority.SiteProfile;
import fr.cg95.cvq.business.authority.SiteRoles;
import fr.cg95.cvq.dao.authority.IAgentDAO;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqModelException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.authority.IAgentService;
import fr.cg95.cvq.service.authority.ILocalAuthorityLifecycleAware;
import fr.cg95.cvq.util.DateUtils;
import fr.cg95.cvq.util.mail.IMailService;
import fr.cg95.cvq.util.translation.ITranslationService;

/**
 * Implementation of the agent service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public final class AgentService implements IAgentService, ILocalAuthorityLifecycleAware {

    private static Logger logger = Logger.getLogger(AgentService.class);

    private IAgentDAO agentDAO;
    private IAuthenticationService authenticationService;
    private IMailService mailService;
    private ITranslationService translationService;

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public Long create(Agent agent) throws CvqException {
        
        if (agent == null)
            throw new CvqException("No agent object provided");

        if (exists(agent.getLogin(), agent.getId())) {
            logger.error("create() there is already an agent with login : " + agent.getLogin());
            throw new CvqModelException("agent.error.loginAlreadyExists");
        }
        //FIXME empty passwords on application launch
        if (agent.getPassword() == null || agent.getPassword().isEmpty()) {
            agent.setPassword("aaaaaaaa");
        }
        agent.setPassword(authenticationService.encryptPassword(agent.getPassword()));
        if (agent.getSitesRoles() == null) {
            SiteRoles siteRoles = new SiteRoles(SiteProfile.AGENT, agent);
            Set<SiteRoles> siteRolesSet = new HashSet<SiteRoles>(1);
            siteRolesSet.add(siteRoles);
            agent.setSitesRoles(siteRolesSet);
        }
        Long agentId = agentDAO.create(agent).getId();
        logger.debug("Created agent object with id : " + agentId);
        return agentId;
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void modify(final Agent agent) throws CvqException {
        if (agent != null) {
            if (exists(agent.getLogin(), agent.getId())) {
                logger.error("modify() there is already an agent with login : " + agent.getLogin());
                throw new CvqModelException("agent.error.loginAlreadyExists");
            }
            agentDAO.update(agent);
        }
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void delete(final String agentLogin) {
        Agent agent = agentDAO.findByLogin(agentLogin);
        if (agent != null)
            agentDAO.delete(agent);
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public List<Agent> getAll() {
        return agentDAO.listAll();
    }

    @Override
    public boolean exists(Long id) {
        return agentDAO.exists(id);
    }

    public boolean exists(String login, Long id) {
        return agentDAO.exists(login, id);
    }

    @Override
    @Context(types={ContextType.ADMIN},privilege=ContextPrivilege.NONE)
    public boolean isAgent(Agent agent) {

        if (agent != null) {
            for (SiteRoles siteRoles : agent.getSitesRoles()) {
                if (siteRoles.getProfile().equals(SiteProfile.AGENT)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Context(types={ContextType.ADMIN},privilege=ContextPrivilege.NONE)
    public boolean isAdmin(Agent agent) {

        if (agent != null) {
            for (SiteRoles siteRoles : agent.getSitesRoles()) {
                if (siteRoles.getProfile().equals(SiteProfile.ADMIN)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Agent getById(final Long id) {
        if (id.longValue() == -1) {
            return null;
        }
        return agentDAO.findById(id);
    }

    // Since this function is used before an agent logs in,
    // we can't do an access control check
    @Override
    public Agent getByLogin(final String login)
        throws CvqObjectNotFoundException {

        Agent agent = agentDAO.findByLogin(login);
        if (agent == null)
            throw new CvqObjectNotFoundException("agent.error.agentNotFound");

        return agent;
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void updateUserProfiles(String username, List<String> groups,
            Map<String, String> informations) throws CvqException {

        Agent agent = null;
        try {
            agent = getByLogin(username);
        } catch (CvqObjectNotFoundException confe) {
            agent = new Agent();
            agent.setActive(Boolean.TRUE);
            agent.setLogin(username);
            Set<SiteRoles> agentSiteRoles = new HashSet<SiteRoles>();
            SiteRoles defaultSiteRole = new SiteRoles();
            defaultSiteRole.setProfile(SiteProfile.AGENT);
            defaultSiteRole.setAgent(agent);
            agentSiteRoles.add(defaultSiteRole);
            agent.setSitesRoles(agentSiteRoles);

            create(agent);
        }

        if (informations.get("firstName") != null)
            agent.setFirstName(informations.get("firstName"));
        if (informations.get("lastName") != null)
            agent.setLastName(informations.get("lastName"));
        if (informations.get("email") != null)
            agent.setEmail(informations.get("email"));
        modify(agent);

        modifyProfiles(agent, groups, SecurityContext.getAdministratorGroups(),
                SecurityContext.getAgentGroups());
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void modifyProfiles(Agent agent, final List<String> newGroups, 
            final List<String> administratorGroups,
            final List<String> agentGroups) {
        
        // check if user became administrator
        for (String newGroup : newGroups) {
            if (administratorGroups.contains(newGroup)) {
                boolean alreadyAdmin = false;
                for (SiteRoles siteRoles : agent.getSitesRoles()) {
                    if (siteRoles.getProfile().equals(SiteProfile.ADMIN)) {
                        alreadyAdmin = true;
                        break;
                    }
                }

                if (!alreadyAdmin) {
                    SiteRoles adminSiteRoles = new SiteRoles();
                    adminSiteRoles.setProfile(SiteProfile.ADMIN);
                    adminSiteRoles.setAgent(agent);
                    agent.getSitesRoles().clear();
                    agent.getSitesRoles().add(adminSiteRoles);
                    agentDAO.update(agent);
                }

                return;
            }
        }
        
        // check if user is no longer administrator
        Set<SiteRoles> agentSiteRoles = agent.getSitesRoles();
        boolean wasAdmin = false;
        for (SiteRoles siteRoles : agentSiteRoles) {
            if (siteRoles.getProfile().equals(SiteProfile.ADMIN)) {
                wasAdmin = true;
                break;
            }
        }
        if (wasAdmin) {
            boolean isAlwaysAdmin = false;
            for (String administratorGroup : administratorGroups) {
                if (newGroups.contains(administratorGroup)) {
                    isAlwaysAdmin = true;
                    break;
                }
            }
            if (!isAlwaysAdmin) {
                SiteRoles defaultSiteRoles = new SiteRoles();
                defaultSiteRoles.setProfile(SiteProfile.AGENT);
                defaultSiteRoles.setAgent(agent);
                agent.getSitesRoles().clear();
                agent.getSitesRoles().add(defaultSiteRoles);
                agentDAO.update(agent);
            }
        }
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void setProfiles(Agent agent, final List<SiteProfile> siteProfiles)
        throws CvqException {

        if (agent == null)
            throw new CvqException("No agent object provided");

        agent.getSitesRoles().clear();
        for (SiteProfile siteProfile : siteProfiles) {
            agent.getSitesRoles().add(new SiteRoles(siteProfile, agent));
        }
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.NONE)
    public Hashtable<String, String> getPreferenceByKey(String key) {
        Agent agent = SecurityContext.getCurrentAgent();
        if (agent.getPreferences() == null) return null;
        else return agent.getPreferences().get(key);
    }
    
    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.NONE)
    public void modifyPreference(String key,
        Hashtable<String,String> preference) {
        Agent agent = SecurityContext.getCurrentAgent();
        if (agent.getPreferences() == null)
            agent.setPreferences(new Hashtable<String, Hashtable<String,String>>());
        agent.getPreferences().put(key, preference);
        agentDAO.update(agent);
    }

    @Override
    public void addLocalAuthority(String localAuthorityName) {
        String agentLogin = "admin." + SecurityContext.getCurrentSite().getName();
        if (authenticationService.getAuthenticationMethod().equals("builtin")
                && agentDAO.findByLogin(agentLogin) == null) {
            logger.debug("addLocalAuthority() in builtin auth mode, creating default admin");
            Agent agent = new Agent();
            agent.setActive(Boolean.TRUE);
            agent.setLogin(agentLogin);
            agent.setFirstName("admin");
            agent.setLastName(SecurityContext.getCurrentSite().getName());
            agent.setPassword(authenticationService.encryptPassword(agentLogin));
            SiteRoles siteRoles = new SiteRoles();
            siteRoles.setAgent(agent);
            siteRoles.setProfile(SiteProfile.ADMIN);
            Set<SiteRoles> siteRolesSet = new HashSet<SiteRoles>();
            siteRolesSet.add(siteRoles);
            agent.setSitesRoles(siteRolesSet);
            agentDAO.create(agent);
        }
    }

    @Override
    public void sendResetPasswordEmail(final Agent agent) throws CvqException {
        agent.assignRandomValidationCode();
        agent.setValidationCodeExpiration(DateUtils.getShiftedDate(Calendar.DAY_OF_YEAR, 3));
        agentDAO.update(agent);

        String urlReset = String.format("backoffice/login/newPassword?login=%s&key=%s",
            agent.getLogin(),
            agent.getValidationCode());

        String subject = translationService.translate("agent.reset.email.subject",
            new Object[] { SecurityContext.getCurrentSite().getDisplayTitle() });

        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("link", urlReset);
        variables.put("login", agent.getLogin());
        variables.put("firstName", agent.getFirstName());
        variables.put("lastName", agent.getLastName());

        mailService.send(
            SecurityContext.getCurrentSite().getAdminEmail(),
            agent.getEmail(),
            null,
            subject,
            mailService.prepareBodyFromAsset("ResetPasswordAgent", variables));
    }

    @Override
    public void assignNewPassword(final Agent agent, final String password) {
        agent.setPassword(authenticationService.encryptPassword(password));
        agent.setValidationCode("");
        agent.setValidationCodeExpiration(null);
        agentDAO.update(agent);
    }

    @Override
    public boolean checkResetPasswordLink(String login, String key) {
        Agent agent = agentDAO.findByLogin(login);
        return isCodeVerificationValid(agent, key) && agent.getValidationCodeExpiration().after(new Date());
    }

    private boolean isCodeVerificationValid(Agent agent, String code) {
        return agent != null && code != null && code.equals(agent.getValidationCode());
    }

    @Override
    public void removeLocalAuthority(String localAuthorityName) {
    }

    public void setAgentDAO(IAgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }

    public void setAuthenticationService(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setTranslationService(ITranslationService translationService) {
        this.translationService = translationService;
    }

    public void setMailService(IMailService mailService) {
        this.mailService = mailService;
    }
}
