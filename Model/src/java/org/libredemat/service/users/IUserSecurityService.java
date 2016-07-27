package org.libredemat.service.users;

import java.util.List;
import java.util.Map;

import org.libredemat.business.authority.Agent;
import org.libredemat.business.users.UserSecurityProfile;
import org.libredemat.business.users.UserSecurityRule;
import org.libredemat.security.annotation.ContextPrivilege;


public interface IUserSecurityService {

    List<Agent> listAgents(boolean withWriteProfile);

    Map<Long,UserSecurityProfile> mapRules();

    UserSecurityRule getRule(Long agentId);

    void allow(Long agentId, UserSecurityProfile profile);

    void disallow(Long agentId);

    /**
     * @deprecated use can(Agent, ContextPrivilege) instead.
     */
    @Deprecated
    boolean canWrite(Long agentId);

    public boolean can(Agent agent, ContextPrivilege privilege);

    Agent changePermissionAgentPayment(Long agentId, boolean permission);
}
