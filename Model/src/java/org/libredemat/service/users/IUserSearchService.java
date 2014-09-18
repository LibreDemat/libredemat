package org.libredemat.service.users;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.libredemat.business.QoS;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Child;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.business.users.RoleType;
import org.libredemat.business.users.UserState;
import org.libredemat.security.annotation.IsUser;
import org.libredemat.service.request.IAutofillTriggerService;
import org.libredemat.util.Critere;


public interface IUserSearchService extends IAutofillTriggerService {

    List<Individual> get(Set<Critere> criterias, Map<String,String> sortParams, Integer max, Integer offset, boolean filterPendings);

    Integer getCount(Set<Critere> criterias, boolean filterPendings);

    List<Adult> match(final Set<Critere> criteriaSet);

    @Override
    Individual getById(@IsUser Long id);

    Adult getAdultById(@IsUser Long id);

    Child getChildById(@IsUser Long id);

    Adult getByLogin(@IsUser String login);

    Individual getByFederationKey(String federationKey);

    List<Individual> listTasks(QoS qoS, int max);

    Long countTasks(QoS qoS);

    List<Adult> listDuplicates(int max);

    Long countDuplicates();

    Adult getHomeFolderResponsible(@IsUser Long homeFolderId);

    List<Individual> listByHomeFolderRole(@IsUser Long homeFolderId, RoleType role);

    List<Individual> listByHomeFolderRoles(@IsUser Long homeFolderId, RoleType[] roles);

    List<Individual> listBySubjectRole(@IsUser Long subjectId, RoleType role);

    List<Individual> listBySubjectRoles(@IsUser Long subjectId, RoleType[] roles);

    List<HomeFolder> getAll(boolean filterArchived, boolean filterTemporary);

    HomeFolder getHomeFolderById(@IsUser Long id);

    List<Child> getChildren(@IsUser Long homeFolderId, UserState... states);

    List<Adult> getAdults(@IsUser Long homeFolderId, UserState... states);

    List<Individual> getIndividuals(@IsUser Long homeFolderId);

    /**
     * Return the list of children having only one responsible: the adult passed in param.
     */
    Set<Child> havingAsOnlyResponsible(@IsUser Adult adult);

    /**
     * Get a list of individuals who have a role in the home folder but are not part of it.
     */
    List<Individual> getExternalIndividuals(@IsUser Long homeFolderId);

    Boolean hasExternalLibredematId(@IsUser Long uId);

    List<Individual> getIndividualsByFirstnameAndLastname(String firstname, String lastname);
}
