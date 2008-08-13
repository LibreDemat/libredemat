package fr.cg95.cvq.service.authority.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import fr.cg95.cvq.business.authority.School;
import fr.cg95.cvq.dao.authority.ISchoolDAO;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.service.authority.ISchoolService;

/**
 * This class provides School related business logic and
 * services to other layers
 *
 * @author bor@zenexity.fr
 *
 */
public class SchoolService implements ISchoolService {

    static Logger logger = Logger.getLogger(SchoolService.class);

    private ISchoolDAO schoolDAO;

    public SchoolService() {
        super();
    }

    public School getByName(final String schoolName)
        throws CvqException {
        return schoolDAO.findByName(schoolName);
    }

    public Set getAll() throws CvqException {
        List schools = null;
        schools = schoolDAO.listAll();
        return new LinkedHashSet(schools);
    }

    public Long create(final School school)
        throws CvqException {
        Long schoolId = null;

        // do some business logic
        if (school != null)
            schoolId = schoolDAO.create(school);

        logger.debug("Created school object with id : " + schoolId);

        return schoolId;
    }

    public void modify(final School school)
        throws CvqException {
        // do some business logic
        if (school != null)
                schoolDAO.update(school);
    }
    
    public void setDAO(ISchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }
}

