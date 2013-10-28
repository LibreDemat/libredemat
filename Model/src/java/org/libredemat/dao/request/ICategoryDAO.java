package org.libredemat.dao.request;

import java.util.List;

import org.libredemat.business.request.Category;
import org.libredemat.business.request.CategoryProfile;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author bor@zenexity.fr
 */
public interface ICategoryDAO extends IJpaTemplate<Category,Long> {

    /**
     * Return the list of all known categories.
     */
    List<Category> listAll();

    /**
     * Return the list of category for which agent has a role.
     */
    List<Category> listByAgent(final Long agentId, final CategoryProfile categoryProfile);

    /**
     * Get a category by name.
     */
    Category findByName(final String name);
}
