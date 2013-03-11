package fr.cg95.cvq.dao.authority;

import java.util.List;

import fr.cg95.cvq.business.authority.School;
import fr.cg95.cvq.dao.jpa.IJpaTemplate;

/**
 * @author bor@zenexity.fr
 */
public interface ISchoolDAO extends IJpaTemplate<School,Long> {

    /**
     * Look up a school by name.
     */
    School findByName(final String name);

    /**
     * Return whether there exists a school with the given name and with a different ID.
     */
    boolean exists(final String name, final Long id);

    /**
     * Return the list of all known schools.
     */
    List<School> listAll();

    /**
     * @return The active schools
     */
    List<School> getActive();
}
