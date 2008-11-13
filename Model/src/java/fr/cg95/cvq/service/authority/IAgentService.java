package fr.cg95.cvq.service.authority;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.cg95.cvq.business.authority.Agent;
import fr.cg95.cvq.business.authority.CategoryProfile;
import fr.cg95.cvq.business.authority.LocalAuthority;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;

/**
 * Service related to the management of agents.
 * 
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public interface IAgentService {

    /** service name used by Spring's application context */
    String SERVICE_NAME = "agentService";

    String SEARCH_BY_SERVICE_ID = "id";
    String SEARCH_BY_CATEGORY_ID = "categoryId";
    String SEARCH_BY_LOGIN = "login";

    Long create(Agent agent)
    		throws CvqException;
    
    void modify(Agent agent)
    		throws CvqException;
    
    void delete(final String agentLogin)
        throws CvqException;
    
    List<Agent> getAll()
        throws CvqException;
    
    List<Agent> get(final Set criteriaSet)
        throws CvqException;

    /**
     * Return agents that have a right (read or write) for the given category.
     * 
     */
    Set<Agent> getAuthorizedForCategory(final Long categoryId)
        throws CvqException;

    /**
     * Modify rights associated to an agent.
     *
     * @param agentId id of agent for which we want to modify rights
     * @param categorysProfiles a map whose key is the category id
     *                         and value the profile
     *
     * @see fr.cg95.cvq.business.authority.CategoryProfile
     */
    void modifyRights(final Long agentId, final Map categorysProfiles)
        throws CvqException, CvqObjectNotFoundException;

    void modifyProfiles(Agent agent, final List newGroups, final List administratorGroups,
            final List agentGroups, final LocalAuthority localAuthority)
        throws CvqException;
    
    void updateUserProfiles(String username, List<String> groups, 
            Map<String, String> informations) throws CvqException;
    
    /**
     * Set or unset (if category profile is {@link CategoryProfile#NONE}) the agent's profile 
     * for the given category.
     *
     * @deprecated
     * @see methods addCategoryRole and removeCategoryRole and modifyCategoryRole
     */
    void setCategoryProfile(final Long agentId, final Long categoryId, 
            final CategoryProfile categoryProfile)
        throws CvqException;

    public void addCategoryRole(final Long agentId, final  Long categoryId, 
            final CategoryProfile categoryProfile ) throws CvqException;
    
    /*
     * Modify or add agent's categoryRole
     */
    public void modifyCategoryRole(final Long agentId, final  Long categoryId, 
            final CategoryProfile categoryProfile ) throws CvqException;
    
    public void removeCategoryRole(final Long agentId, final  Long categoryId) throws CvqException;
    
    /**
     * Retrives a cutoff of agent preferences by its key
     * 
     * @param key 
     * @param agent scope entity
     * @return Cutoff of agent preferences
     */
    Hashtable<String, String> getPreferenceByKey(String key, Agent agent);
    
    /**
     * Modifies a cutoff of agent preferences
     * 
     * @param key
     * @param preference cutoff to replace
     * @param agent scope entity
     * @throws CvqException
     */
    void modifyPreference(String key,Hashtable<String,String> preference,Agent agent) throws CvqException;
    
    /**
     * Get an agent by id.
     */
    Agent getById(final Long id)
        throws CvqException, CvqObjectNotFoundException;

    /**
     * Return whether an agent with the given id exists.
     */
    boolean exists(final Long id)
        throws CvqException;
    
    /**
     * Get an agent by login.
     *
     * The agent's login is returned by the CAS server after the
     * successful authentication of an agent. So you can use this
     * method to get a "real" agent object if needed.
     */
    Agent getByLogin(final String login)
        throws CvqException, CvqObjectNotFoundException;
}
