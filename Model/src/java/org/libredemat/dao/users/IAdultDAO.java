package org.libredemat.dao.users;

import java.util.List;
import java.util.Map;

import org.libredemat.business.users.Adult;
import org.libredemat.business.users.UserState;


/**
 * @author bor@zenexity.fr
 */
public interface IAdultDAO extends IIndividualDAO {

    Adult findByLogin(String login);

    /**
     * Return the list of {@link Adult} objects belonging to a given home folder.
     */
    List<Adult> listAdultsByHomeFolder(final Long homeFolderId, UserState... states);

    List<Adult> matchAdults (Map<String, String> parameters);

    List<Adult> findDuplicates(Map<String, String> parameters);

    Long countDuplicates();

    List<Adult> listDuplicates(Map<String,String> sortParams, Integer max, Integer offset);

    List<Adult> findResponsibleDuplicates(Map<String, String> parameters);
}
