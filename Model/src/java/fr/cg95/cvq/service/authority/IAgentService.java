package fr.cg95.cvq.service.authority;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fr.cg95.cvq.business.authority.Agent;
import fr.cg95.cvq.business.authority.SiteProfile;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;

/**
 * Service related to the management of agents.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public interface IAgentService {

    /** minimal password length for agent and admin accounts **/
    int passwordMinLength = fr.cg95.cvq.authentication.IAuthenticationService.passwordMinLength;

    Long create(Agent agent)
        throws CvqException;
    
    void modify(Agent agent) throws CvqException;
    
    void delete(final String agentLogin);
    
    List<Agent> getAll();
    
    /**
     * Get an agent by id.
     */
    Agent getById(final Long id);

    /**
     * Get an agent by login.
     *
     * The agent's login is returned by the CAS server after the
     * successful authentication of an agent. So you can use this
     * method to get a "real" agent object if needed.
     */
    Agent getByLogin(final String login)
        throws CvqObjectNotFoundException;
    
    /**
     * Sets the new profiles of an agent
     *
     * @param agent the agent to modify
     * @param siteProfiles the new profiles
     */    
    void setProfiles(Agent agent, final List<SiteProfile> siteProfiles)
        throws CvqException;

    /**
     * Return whether an agent with the given id exists.
     * Return agents that have a right (read or write) for the given category.
     */
    boolean exists(final Long id);

    /**
     * Return whether an agent with the given login exists,
     * and is not the same agent (with the same ID).
     */
    boolean exists(final String login, final Long id);    

    void modifyProfiles(Agent agent, final List<String> newGroups, 
        final List<String> administratorGroups,
        final List<String> agentGroups);
    
    void updateUserProfiles(String username, List<String> groups, 
        Map<String, String> informations) throws CvqException;

    /**
     * Retrieve current agent's preferences by key.
     * 
     * @return Cutoff of agent preferences
     */
    Hashtable<String, String> getPreferenceByKey(String key);

    /**
     * Return whether an agent has the Agent profile.
     */
    boolean isAgent(final Agent agent);
    
    /**
     * Return whether an agent has the Admin profile.
     */
    boolean isAdmin(final Agent agent);

    /**
     * Modify current agent preferences for the given key.
     * 
     * @param preference cutoff to replace
     */
    void modifyPreference(String key, Hashtable<String,String> preference);

    /**
     * Send email to agent to reset its password
     * @param agent
     * @throws CvqException
     */
    void sendResetPasswordEmail(final Agent agent) throws CvqException;

    /**
     * Check if validation key is valid (value and expiration)
     * @param login
     * @param key
     * @return
     */
    boolean checkResetPasswordLink(String login, String key);

    /**
     * Assign new password to agent and invalidate 'validation code'
     * @param agent
     * @param password
     */
    void assignNewPassword(final Agent agent, final String password);
}
