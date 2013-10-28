package org.libredemat.service.authority;

import java.util.List;

import org.libredemat.business.authority.School;
import org.libredemat.exception.CvqModelException;


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
