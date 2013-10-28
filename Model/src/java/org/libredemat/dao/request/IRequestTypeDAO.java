package org.libredemat.dao.request;

import java.util.List;
import java.util.Set;

import org.libredemat.business.request.DisplayGroup;
import org.libredemat.business.request.GlobalRequestTypeConfiguration;
import org.libredemat.business.request.RequestType;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.IJpaTemplate;
import org.libredemat.util.Critere;


/**
 * @author bor@zenexity.fr
 */
public interface IRequestTypeDAO extends IJpaTemplate<RequestType, Long> {

    /**
     * Look up a request type by label.
     */
    RequestType findByLabel(final String requestTypeLabel);

    /**
     * Return the list of all known requests types.
     */
    List<RequestType> listAll();

    /**
     * Return the list of requests types in the given activation state.
     */
    List<RequestType> listByCategoryAndState(Set<Critere> criteriaSet);

    List<DisplayGroup> listAllDisplayGroup();

    GlobalRequestTypeConfiguration getGlobalRequestTypeConfiguration();
}
