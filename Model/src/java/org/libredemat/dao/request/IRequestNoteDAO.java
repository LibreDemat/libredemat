package org.libredemat.dao.request;


import java.util.List;

import org.libredemat.business.request.RequestNote;
import org.libredemat.business.request.RequestNoteType;
import org.libredemat.dao.jpa.IJpaTemplate;

/**
 * @author bor@zenexity.fr
 */
public interface IRequestNoteDAO extends IJpaTemplate<RequestNote, Long> {

    /**
     * Return the list of notes related to a given request,
     * filtered with the specified type if it is not null.
     */
    List<RequestNote> listByRequestAndType(final Long requestId, final RequestNoteType type);
}
