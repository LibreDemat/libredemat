package org.libredemat.dao.users;

import java.util.List;
import java.util.Map;

import org.libredemat.business.users.Child;
import org.libredemat.business.users.UserState;


/**
 * @author bor@zenexity.fr
 */
public interface IChildDAO extends IIndividualDAO {

    /**
     * Return the list of {@link Child children} belonging to a given home folder.
     */
    List<Child> listChildrenByHomeFolder(final Long homeFolderId, UserState... states);

    List<Child> findDuplicates(Map<String, Object> parameters);
}
