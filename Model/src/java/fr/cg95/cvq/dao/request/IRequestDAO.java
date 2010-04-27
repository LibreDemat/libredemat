package fr.cg95.cvq.dao.request;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.cg95.cvq.business.request.Request;
import fr.cg95.cvq.business.request.RequestActionType;
import fr.cg95.cvq.business.request.RequestLock;
import fr.cg95.cvq.business.request.RequestState;
import fr.cg95.cvq.dao.IGenericDAO;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;
import fr.cg95.cvq.util.Critere;

/**
 * @author bor@zenexity.fr
 */
public interface IRequestDAO extends IGenericDAO {

    /**
     * Look up requests given a set of search criteria.
     *
     * @param criteria a set of {@link fr.cg95.cvq.util.Critere} criteria
     * @param sort an optional string to sort results on
     * @param dir sort direction (asc or desc)
     * @param recordsReturned number of records to return (-1 for all)
     * @param startIndex start index of the records to return
     */
    List<Request> search(final Set<Critere> criteria, final String sort, String dir, 
        int recordsReturned, int startIndex, final boolean full);

    /**
     * Return the number of requests that match a set of search criteria.
     */
    Long count(final Set<Critere> criteria);

    /**
     * Return the list of all requests whose requester has the given id.
     */
    List<Request> listByRequester(final Long requesterId, final boolean full);

    /**
     * Return the list of all requests whose subject, if any, has the given id.
     */
    List<Request> listBySubject(final Long subjectId, final boolean full);

    /**
     * Return the list of requests with the given label whose associated subject has
     * the given id.
     *
     * @param label label identifier as used internally by all requests-related services
     */
    List<Request> listBySubjectAndLabel(final Long subjectId, final String label, 
        final RequestState[] excludedStates, final boolean full);

    /**
     * Return the list of all requests whose associated home folder has the given id.
     */
    List<Request> listByHomeFolder(final Long homeFolderId, final boolean full);

    /**
     * Return the list of requests with the given label whose associated home folder has
     * the given id.
     *
     * @param label label identifier as used internally by all requests-related services
     */
    List<Request> listByHomeFolderAndLabel(final Long homeFolderId, final String label, 
        final RequestState[] excludedStates, final boolean full);

    /**
     * Return the list of all requests who are in one of the given states.
     */
    List<Request> listByStates(final Set<RequestState> states, final boolean full);
    
    /**
     * Return the list of all requests of the given type who are in one of the given states.
     */
    List<Request> listByStatesAndType(final Set<RequestState> states, final String requestTypeLabel,
        final boolean full);
    
    /**
     * Return the list of requests which do not have the given action type.
     */
    List<Request> listByNotMatchingActionLabel(final RequestActionType type, final boolean full);

    /**
     * Specialized method for the RequestService endpoint.
     */
    List<Request> listRequestsToExport(final String resultingState,
            final Date startDate, final Date endDate,
            final List<String> requestTypesLabel);
    
    /**
     * Retrieve drafted requests created before given date that don't have the 
     * given action trace label yet.
     */
    List<Request> listDraftedByNotificationAndDate(RequestActionType type, Date date,
        final boolean full);

    List<Long> listHomeFolderSubjectIds(Long homeFolderId, String label, 
            RequestState[] excludedStates);

    RequestLock getRequestLock(Long requestId);

    List<Long> cleanRequestLocks(int maxDelay);

    Request findById(Long id, final boolean full)
        throws CvqObjectNotFoundException;

    /**
     * Delete specific data
     */
    public void empty(Request request)
        throws CvqException;
}
