package org.libredemat.dao.authority;

import java.util.List;

import org.libredemat.business.authority.Agent;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author bor@zenexity.fr
 */
public interface IAgentDAO extends IJpaTemplate<Agent,Long> {

    /**
     * Return whether there exists an agent with the given id.
     */
    boolean exists(final Long id);

    /**
     * Return whether there exists an agent with the given login and with a different ID. 
     */
    boolean exists(final String login, final Long id);

    /**
     * Look up an Agent by login.
     */
    Agent findByLogin(final String login);

    /**
     * Return the list of all known agents.
     */
    List<Agent> listAll();
}
