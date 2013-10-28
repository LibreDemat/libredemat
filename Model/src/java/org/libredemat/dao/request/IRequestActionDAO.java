package org.libredemat.dao.request;

import java.util.List;

import org.libredemat.business.request.RequestAction;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestAdminAction;
import org.libredemat.business.request.RequestState;
import org.libredemat.dao.jpa.IJpaTemplate;


/**
 * @author jsb@zenexity.fr
 */
public interface IRequestActionDAO extends IJpaTemplate<RequestAction, Long> {

    boolean hasAction(final Long requestId, final RequestActionType type);

    RequestAction getAction(final Long requestId, final RequestActionType type,
            final RequestState state, boolean first);

    List<RequestAdminAction> getAdminActions();

    boolean hasArchivesMigrationAction();
}
