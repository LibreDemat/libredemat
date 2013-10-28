package org.libredemat.dao.authority;

import org.libredemat.business.authority.LocalAuthority;
import org.libredemat.dao.jpa.IJpaTemplate;

/**
 * @author bor@zenexity.fr
 */
public interface ILocalAuthorityDAO extends IJpaTemplate<LocalAuthority,Long> {

    /**
     * Look up a LocalAuthority by name.
     */
    LocalAuthority findByName(final String name);
}
