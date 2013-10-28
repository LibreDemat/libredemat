package org.libredemat.dao.users;

import java.util.List;

import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.UserAction;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author bor@zenexity.fr
 */
public interface IHomeFolderDAO extends IJpaTemplate<HomeFolder,Long> {

    /**
     * Return the list of all known home folders.
     */
    List<HomeFolder> listAll(boolean filterArchived, boolean filterTemporary);

    /**
     * Return the list of all UserAction with type WAITING_NOTIFICATION
     */
    List<UserAction> waitingNotification();
}
