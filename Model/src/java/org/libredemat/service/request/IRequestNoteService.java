package org.libredemat.service.request;

import java.util.List;

import org.libredemat.business.request.RequestNote;
import org.libredemat.business.request.RequestNoteType;
import org.libredemat.exception.CvqException;
import org.libredemat.service.request.annotation.IsRequest;


public interface IRequestNoteService {

    /**
     * Get notes related to a given request.
     * Optionnal type parameter, used to filter notes if it is not null.
     * Filters notes that must not be readable
     * (private notes which don't belong to the current context)
     *
     * @return a list of {@link org.libredemat.business.request.RequestNote} objects
     */
    List<RequestNote> getNotes(@IsRequest final Long requestId, final RequestNoteType type)
        throws CvqException;

    /**
     * Get the last readable note (of this type, if not null).
     */
    RequestNote getLastNote(@IsRequest final Long requestId, final RequestNoteType type)
        throws CvqException;

    /**
     * Get the last readable note written by an agent (of this type, if not null).
     */
    RequestNote getLastAgentNote(@IsRequest final Long requestId, final RequestNoteType type)
        throws CvqException;

    /**
     * Add a note to a request.
     *
     * @param requestId the request to which note has to be added
     * @param rnt the type of the note
     * @param note the body of the note itself
     */
    void addNote(@IsRequest final Long requestId, final RequestNoteType rnt, final String note);

    RequestNote getNote(@IsRequest final Long requestId, final Long requestNoteId)
                throws CvqException;

    /**
    * Add a note to a request.
    *
    * @param requestId the request to which note has to be added
    * @param rnt the type of the note
    * @param note the body of the note itself
    */
    void addNote(@IsRequest final Long requestId, final RequestNoteType rnt, final String note,
            byte[] attachment, String attachmentName, Long parentId);

}
