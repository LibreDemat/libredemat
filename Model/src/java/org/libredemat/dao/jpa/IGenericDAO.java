package org.libredemat.dao.jpa;

import java.util.List;

import org.libredemat.business.users.GlobalUserConfiguration;
import org.libredemat.dao.hibernate.SimpleQuery;



/**
 * Generic Data Access Object for cases where we don't need specific search parameters.
 *
 * @author bor@zenexity.fr
 */
public interface IGenericDAO {

    SimpleQuery simpleSelect(final Class<?> clazz);

    SimpleQuery simpleSelect();

    /**
     * Create a persistent object in DB.
     * @param Object object
     */
    Object create(final Object object);

    /**
     * Save or update a persistent object in DB.
     *
     * @deprecated only for backward, use create or update instead
     * @param Object object to save or update
     * @return updated object
     */
    Object saveOrUpdate(final Object object);

    /**
     * Update a persistent object in DB.
     * @param Object object
     */
    void update(final Object object);

    /**
     * Delete a persistent object from DB.
     * @param Object object
     */
    void delete(final Object object);

    @SuppressWarnings("rawtypes")
    public List find(Class<?> clazz, String query, Object... params);

    public Object findById(Class<?> clazz, Long id);

    /**
     * @return list of Object
     */
    @SuppressWarnings("rawtypes")
    public List findAll(Class<?> clazz);

    public Long count(Class<?> clazz);
}
