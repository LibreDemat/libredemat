package org.libredemat.service.authority;

import static org.junit.Assert.*;

import org.junit.Test;
import org.libredemat.business.authority.Agent;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.security.SecurityContext;
import org.libredemat.testtool.ServiceTestCase;


/**
 * The tests for the agent service.
 *
 * @author bor@zenexity.fr
 */
public class AgentServiceTest extends ServiceTestCase {

    @Test
    public void testCreate() throws CvqException {
        
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);
        
        Agent agent = new Agent();
        agent.setFirstName("agentFirstName");
        agent.setLastName("agentLastName");
        agent.setLogin("agentLogin");
        agentService.create(agent);
        
        continueWithNewTransaction();
        
        Agent fetchAgent = null;
        try {
            fetchAgent = agentService.getByLogin("agentLogin");
        } catch (CvqObjectNotFoundException confe) {
            fail("should not have happened");
        }
        assertEquals("agentFirstName", fetchAgent.getFirstName());
        assertEquals("agentLastName", fetchAgent.getLastName());
        assertEquals("agentLogin", fetchAgent.getLogin());

        agentService.delete("agentLogin");

        continueWithNewTransaction();
        
        try {
            fetchAgent = agentService.getByLogin("agentLogin");
            fail("should have thrown an exception");
        } catch (CvqObjectNotFoundException confe) {
            // that was expected
        }        
    }
}
