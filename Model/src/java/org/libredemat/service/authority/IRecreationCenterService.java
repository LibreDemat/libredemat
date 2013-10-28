package org.libredemat.service.authority;

import java.util.List;

import org.libredemat.business.authority.RecreationCenter;
import org.libredemat.exception.CvqModelException;


/**
 * @author bor@zenexity.fr
 */
public interface IRecreationCenterService {

    Long create(final RecreationCenter recreationCenter) throws CvqModelException;

    List<RecreationCenter> getAll();

    List<RecreationCenter> getActive();

    RecreationCenter getById(final Long id);

    RecreationCenter getByName(String name);

    void modify(final RecreationCenter recreationCenter)
        throws CvqModelException;

    void delete(final RecreationCenter recreationCenter);

    boolean exists(final String name, final Long id);
}
