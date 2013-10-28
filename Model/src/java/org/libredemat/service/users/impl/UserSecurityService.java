package org.libredemat.service.users.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.libredemat.business.authority.Agent;
import org.libredemat.business.users.UserSecurityProfile;
import org.libredemat.business.users.UserSecurityRule;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.IAgentService;
import org.libredemat.service.users.IUserSecurityService;


public class UserSecurityService implements IUserSecurityService {

    private IGenericDAO genericDAO;
    private IAgentService agentService;

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public List<Agent> listAgents(boolean withWriteProfile) {
        List<Agent> agents = agentService.getAll();
        if (!withWriteProfile)
            return agents;
        Map<Long, UserSecurityProfile> rules = mapRules();
        Iterator<Agent> it = agents.iterator();
        while (it.hasNext()) {
            Agent agent = (Agent) it.next();
            if (!UserSecurityProfile.writer.contains(rules.get(agent.getId())))
                it.remove();
        }
        return agents;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public Map<Long, UserSecurityProfile> mapRules() {
        List<UserSecurityRule> rules = genericDAO.findAll(UserSecurityRule.class);
        Map<Long, UserSecurityProfile> mapRules = new HashMap<Long, UserSecurityProfile>();
        for (UserSecurityRule rule : rules) {
            mapRules.put(rule.getAgentId(), rule.getProfile());
        }
        return mapRules;
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.ADMIN}, privilege = ContextPrivilege.READ)
    public UserSecurityRule getRule(Long agentId) {
        return genericDAO.simpleSelect(UserSecurityRule.class).and("agentId", agentId).unique();
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void allow(Long agentId, UserSecurityProfile profile) {
        UserSecurityRule rule = getRule(agentId);
        if (rule == null) {
            rule = new UserSecurityRule(agentId, profile);
            genericDAO.create(rule);
        } else {
            rule.setProfile(profile);
            genericDAO.update(rule);
        }
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.NONE)
    public void disallow(Long agentId) {
        UserSecurityRule rule = getRule(agentId);
        if (rule != null)
            genericDAO.delete(rule);
    }

    @Deprecated
    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.NONE)
    public boolean canWrite(Long agentId) {
        UserSecurityRule rule = getRule(agentId);
        if (rule == null)
            return false;
        return UserSecurityProfile.writer.contains(rule.getProfile());
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.NONE)
    public boolean can(Agent agent, ContextPrivilege privilege) {
        if (agent == null)
            return false;
        UserSecurityRule rule = getRule(agent.getId());
        if (rule == null)
            return false;

        switch(privilege) {
            case MANAGE:
                return (UserSecurityProfile.MANAGE.equals(rule.getProfile()));
            case WRITE:
                return (UserSecurityProfile.writer.contains(rule.getProfile()));
            case READ:
                return (UserSecurityProfile.reader.contains(rule.getProfile()));
            default:
                return false;
        }
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public void setAgentService(IAgentService agentService) {
        this.agentService = agentService;
    }

}
