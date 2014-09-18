package org.libredemat.service.users.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.libredemat.business.QoS;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.IndividualRole;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.UserState;
import org.libredemat.dao.users.IAdultDAO;
import org.libredemat.dao.users.IChildDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.users.IUserSearchService;
import org.libredemat.service.users.external.IExternalHomeFolderService;
import org.libredemat.util.Critere;

import org.apache.commons.lang.StringUtils;

public class UserSearchService implements IUserSearchService {

    private IHomeFolderDAO homeFolderDAO;

    private IIndividualDAO individualDAO;

    private IAdultDAO adultDAO;

    private IChildDAO childDAO;

    private IExternalHomeFolderService externalHomeFolderService;

    @Override
    public List<Individual> get(Set<Critere> criterias, Map<String,String> sortParams,
        Integer max, Integer offset, boolean filterPendings) {
        return individualDAO.search(filterPendings ? filterPendingUsers(criterias) : criterias, sortParams, max, offset);
    }

    @Override
    public Integer getCount(Set<Critere> criterias, boolean filterPendings) {
        return individualDAO.searchCount(filterPendings ? filterPendingUsers(criterias) : criterias);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Adult> match(final Set<Critere> criteriaSet) {
//        return adultDAO.match(criteriaSet, new UserState[] { UserState.ARCHIVED });
        return Collections.emptyList();
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.UNAUTH_ECITIZEN, ContextType.EXTERNAL_SERVICE},
            privilege = ContextPrivilege.READ)
    public Individual getById(Long id) {
        return individualDAO.findById(id);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.EXTERNAL_SERVICE},
            privilege = ContextPrivilege.READ)
    public Adult getAdultById(Long id) {
        Individual individual = adultDAO.findById(id);
        return (individual instanceof Adult) ? (Adult) individual : null;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Child getChildById(Long id) {
        Individual individual = childDAO.findById(id);
        return (individual instanceof Child) ? (Child) individual : null;
    }

    @Override
    public Adult getByLogin(String login) {
        return adultDAO.findByLogin(login);
    }

    @Override
    public Individual getByFederationKey(final String federationKey) {
        return individualDAO.findByFederationKey(federationKey);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> listTasks(QoS qoS, int max) {
        return individualDAO.listTasks(qoS, max);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Long countTasks(QoS qoS) {
        return individualDAO.countTasks(qoS);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Long countDuplicates() {
        return adultDAO.countDuplicates();
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Adult> listDuplicates(int max) {
        return adultDAO.listDuplicates(max);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public final List<HomeFolder> getAll(boolean filterArchived, boolean filterTemporary) {
        return homeFolderDAO.listAll(filterArchived, filterTemporary);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.EXTERNAL_SERVICE, ContextType.UNAUTH_ECITIZEN}, privilege = ContextPrivilege.READ)
    public final HomeFolder getHomeFolderById(final Long id) {
        return homeFolderDAO.findById(id);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public final List<Child> getChildren(final Long homeFolderId, UserState... states) {
        return childDAO.listChildrenByHomeFolder(homeFolderId, states);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public final List<Adult> getAdults(final Long homeFolderId, UserState... states) {
        return adultDAO.listAdultsByHomeFolder(homeFolderId, states);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> getIndividuals(Long homeFolderId) {
        return individualDAO.listByHomeFolder(homeFolderId);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.READ)
    public List<Individual> getExternalIndividuals(final Long homeFolderId) {
        Set<Individual> externalIndividuals = new HashSet<Individual>();
        externalIndividuals.addAll(individualDAO.listByHomeFolderRoles(homeFolderId, RoleType.values(), true));
        for (Individual individual : getIndividuals(homeFolderId))
            externalIndividuals.addAll(individualDAO.listBySubjectRoles(individual.getId(), RoleType.values(), true));
        return new ArrayList<Individual>(externalIndividuals);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Adult getHomeFolderResponsible(Long homeFolderId) {
        List<Individual> individuals =
            individualDAO.listByHomeFolderRole(homeFolderId, RoleType.HOME_FOLDER_RESPONSIBLE);
        // here we can make the assumption that we properly enforced the role
        return (Adult) individuals.get(0);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> listByHomeFolderRole(Long homeFolderId, RoleType role) {
        return individualDAO.listByHomeFolderRole(homeFolderId, role);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> listByHomeFolderRoles(Long homeFolderId, RoleType[] roles) {
        return individualDAO.listByHomeFolderRoles(homeFolderId, roles, false);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> listBySubjectRole(Long subjectId, RoleType role) {
        return individualDAO.listBySubjectRole(subjectId, role);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Individual> listBySubjectRoles(Long subjectId, RoleType[] roles) {
        return individualDAO.listBySubjectRoles(subjectId, roles, false);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public Set<Child> havingAsOnlyResponsible(Adult adult) {
        Set<Child> children = new HashSet<Child>();

        for (IndividualRole role : adult.getIndividualRoles()) {
            if(role.getIndividualId()!=null){
                List<Individual> responsibles = listBySubjectRole(role.getIndividualId(), null);
                if (responsibles.size() == 1){
                    children.add((Child)individualDAO.findById(role.getIndividualId()));}
            }
        }

        return children;
    }

    @Override
    public Boolean hasExternalLibredematId(Long uId) {
        return (externalHomeFolderService.getIndividualMappings(uId).size() != 0);
    }

    public void setExternalHomeFolderService(IExternalHomeFolderService externalHomeFolderService) {
        this.externalHomeFolderService = externalHomeFolderService;
    }

    /**
     * Filter users in pending state (email validation pending) from searchs
     * @param criterias
     * @return
     */
    private Set<Critere> filterPendingUsers(final Set<Critere> criterias) {
        criterias.add(new Critere(Adult.SEARCH_BY_USER_STATE, UserState.PENDING.name(), Critere.NEQUALS));
        return criterias;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setIndividualDAO(IIndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }

    public void setAdultDAO(IAdultDAO adultDAO) {
        this.adultDAO = adultDAO;
    }

    public void setChildDAO(IChildDAO childDAO) {
        this.childDAO = childDAO;
    }

    @Override
    public List<Individual> getIndividualsByFirstnameAndLastname(String firstname, String lastname)
    {
        List<Individual> individuals = new ArrayList<Individual>();
        if (StringUtils.isNotBlank(lastname) && StringUtils.isNotBlank(firstname))
        {
            individuals = this.individualDAO.listIndividualsByFirstnameAndLastname(firstname, lastname);
        }
        return individuals;
    }
}
