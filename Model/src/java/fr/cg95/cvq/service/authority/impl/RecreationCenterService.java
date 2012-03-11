package fr.cg95.cvq.service.authority.impl;

import java.util.List;

import org.apache.log4j.Logger;

import fr.cg95.cvq.business.authority.RecreationCenter;
import fr.cg95.cvq.dao.authority.IRecreationCenterDAO;
import fr.cg95.cvq.exception.CvqModelException;
import fr.cg95.cvq.security.annotation.Context;
import fr.cg95.cvq.security.annotation.ContextPrivilege;
import fr.cg95.cvq.security.annotation.ContextType;
import fr.cg95.cvq.service.authority.IRecreationCenterService;

/**
 * Implementation of the recreation center service.
 *
 * @author bor@zenexity.fr
 */
public final class RecreationCenterService implements IRecreationCenterService {

    private static Logger logger = Logger.getLogger(RecreationCenterService.class);

    private IRecreationCenterDAO recreationCenterDAO;

    @Override
    public RecreationCenter getByName(String name) {
        return recreationCenterDAO.findByName(name);
    }

    @Override
    public RecreationCenter getById(final Long id) {
        return recreationCenterDAO.findById(id);
    }

    @Override
    public List<RecreationCenter> getAll() {
        return recreationCenterDAO.listAll();
    }

    @Override
    public List<RecreationCenter> getActive() {
        return recreationCenterDAO.getActive();
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.WRITE)
    public Long create(final RecreationCenter recreationCenter) throws CvqModelException {
        Long recreationCenterId = null;

        if (recreationCenter != null) {
            if (exists(recreationCenter.getName(), recreationCenter.getId())) {
                logger.error("create() there is already a recreation center with name : " + recreationCenter.getName());
                throw new CvqModelException("recreationCenter.error.nameAlreadyExists");
            }
            recreationCenterId = recreationCenterDAO.create(recreationCenter).getId();
        }
        logger.debug("Created recreation center object with id : "
            + recreationCenterId);
        return recreationCenterId;
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.WRITE)
    public void modify(final RecreationCenter recreationCenter)
        throws CvqModelException {

        if (recreationCenter != null) {
            if (exists(recreationCenter.getName(), recreationCenter.getId())) {
                logger.error("modify() there is already a recreation center with name : " + recreationCenter.getName());
                throw new CvqModelException("recreationCenter.error.nameAlreadyExists");
            }
            recreationCenterDAO.update(recreationCenter);
        }   
    }

    public void setDAO(IRecreationCenterDAO recreationCenterDAO) {
        this.recreationCenterDAO = recreationCenterDAO;
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.WRITE)
    public void delete(final RecreationCenter recreationCenter) {

        if (recreationCenter != null)
            recreationCenterDAO.delete(recreationCenter);
    }

    public boolean exists(String name, Long id) {
        return recreationCenterDAO.exists(name, id);
    }
}
