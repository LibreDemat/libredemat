package fr.cg95.cvq.service.authority;

import java.util.List;

import fr.cg95.cvq.business.authority.School;
import fr.cg95.cvq.exception.CvqModelException;

/**
 * @author bor@zenexity.fr
 *
 */
public interface ISchoolService {

    Long create(final School school) throws CvqModelException;

    void modify(final School school) throws CvqModelException;

    List<School> getAll();

    List<School> getActive();

    School getByName(final String schoolName);

    void delete(final School school);

    School getById(final Long id);

    boolean exists(final String name, final Long id);
}
