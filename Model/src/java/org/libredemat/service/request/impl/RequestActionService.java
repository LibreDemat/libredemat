package org.libredemat.service.request.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestAction;
import org.libredemat.business.request.RequestActionType;
import org.libredemat.business.request.RequestAdminAction;
import org.libredemat.business.request.RequestState;
import org.libredemat.dao.request.IRequestActionDAO;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.request.IRequestActionService;
import org.libredemat.util.logging.impl.Log;


/**
 *
 * @author bor@zenexity.fr
 */
public class RequestActionService implements IRequestActionService {

    private IRequestActionDAO requestActionDAO;
    private IRequestDAO requestDAO;

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public RequestAction getAction(final Long requestId, final Long id)
        throws CvqObjectNotFoundException {
        // do not directly use requestActionDAO to enforce request access rights
        Request request = requestDAO.findById(requestId, false);
        for (RequestAction action : request.getActions()) {
            if (action.getId().equals(id))
                return action;
        }
        throw new CvqObjectNotFoundException();
    }

    @Override
    public RequestAction getLastAction(Long requestId, RequestActionType type, RequestState state) {
        return requestActionDAO.getAction(requestId, type, state, false);
    }


    @Override
    public RequestAction getFirstAction(Long requestId, RequestActionType type, RequestState state) {
        return requestActionDAO.getAction(requestId, type, state, true);
    }


    @Override
    public boolean hasAction(final Long requestId, final RequestActionType type)
        throws CvqException {
        return requestActionDAO.hasAction(requestId, type);
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.WRITE)
    public void addAction(final Long requestId, final RequestActionType type,
        final String message, final String note, final byte[] pdfData, String filename) {

        addActionTrace(type, message, note, new Date(), null, requestId, pdfData, filename, null, null, null);
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.WRITE)
    public void addSystemAction(final Long requestId,
        final RequestActionType type) {

        addActionTrace(type, null, null, new Date(), null, requestId, null, null, null, null, null);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void addDraftCreationAction(Long requestId, Date date) {
        addActionTrace(RequestActionType.CREATION, null, null, date,
            RequestState.DRAFT, requestId, null, null, null, null, null);
    }

    @Override
    public void addCreationAction(Long requestId, Date date, byte[] pdfData, String note) {
        addActionTrace(RequestActionType.CREATION, null, note, date,
            RequestState.PENDING, requestId, pdfData, null, null, null, null);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT, ContextType.EXTERNAL_SERVICE},
            privilege = ContextPrivilege.WRITE)
    public void addWorfklowAction(final Long requestId, final String note, final Date date,
            final RequestState resultingState, final byte[] pdfData) {

        addActionTrace(RequestActionType.STATE_CHANGE, null, note, date,
            resultingState, requestId, pdfData, null, null, null, null);

        Log.logger(SecurityContext.getCurrentSite().getName()).info("CHANGE STATE : " 
                + "[" + requestId + "]" 
                + " to " + resultingState.name());
    }

    @Override
    @Context(types = { ContextType.AGENT, ContextType.EXTERNAL_SERVICE }, privilege = ContextPrivilege.WRITE)
    public void addAction(final Long requestId, final RequestActionType type, final String message,
            final String note, final byte[] pdfData, String filename, byte[] attachment,
            String attachmentName) {
        addActionTrace(type, message, note, new Date(), null, requestId, pdfData, filename, attachment, attachmentName, null);
    }

    private void addActionTrace(final RequestActionType type, final String message,
        final String note, final Date date, final RequestState resultingState,
        final Long requestId, final byte[] pdfData, String filename, byte[] attachment, String attachmentName, final Long replyParentId) {

        Request request = requestDAO.findById(requestId);

        // retrieve user or agent id according to context
        Long userId = SecurityContext.getCurrentUserId();
        RequestAction requestAction = new RequestAction();
        requestAction.setAgentId(userId);
        requestAction.setExternalService(SecurityContext.getCurrentExternalService());
        requestAction.setType(type);
        requestAction.setMessage(message);
        requestAction.setNote(note);
        requestAction.setDate(date);
        requestAction.setResultingState(resultingState);
        requestAction.setFile(pdfData);
        requestAction.setFilename(filename);
        requestAction.setAttachment(attachment);
        requestAction.setAttachmentName(attachmentName);
        requestAction.setReplyParentId(replyParentId);

        if (request.getActions() == null) {
            Set<RequestAction> actionsSet = new HashSet<RequestAction>();
            actionsSet.add(requestAction);
            request.setActions(actionsSet);
        } else {
            request.getActions().add(requestAction);
        }

        requestDAO.update(request);
    }

    @Override
    @Context(types = {ContextType.ADMIN}, privilege = ContextPrivilege.READ)
    public List<RequestAdminAction> getAdminActions() {
        return requestActionDAO.getAdminActions();
    }

    @Override
    @Context(types = {ContextType.AGENT, ContextType.EXTERNAL_SERVICE}, privilege = ContextPrivilege.WRITE)
    public void addAction(final Long requestId, final RequestActionType type, final String message, final String note, final byte[] pdfData, String filename, final Long replyParentId) {

        addActionTrace(type, message, note, new Date(), null, requestId, pdfData, filename, null, null, replyParentId);
    }

    public void setRequestActionDAO(IRequestActionDAO requestActionDAO) {
        this.requestActionDAO = requestActionDAO;
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

}
