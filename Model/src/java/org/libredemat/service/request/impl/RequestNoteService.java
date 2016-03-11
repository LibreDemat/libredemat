package org.libredemat.service.request.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.RequestNote;
import org.libredemat.business.request.RequestNoteType;
import org.libredemat.business.request.RequestEvent.COMP_DATA;
import org.libredemat.business.request.RequestEvent.EVENT_TYPE;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.dao.request.IRequestNoteDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.IAgentService;
import org.libredemat.service.request.IRequestNoteService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class RequestNoteService implements IRequestNoteService, ApplicationContextAware {

    private IRequestNoteDAO requestNoteDAO;
    private IRequestDAO requestDAO;
    
    private IAgentService agentService;
    
    private ApplicationContext applicationContext;
    
    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<RequestNote> getNotes(final Long requestId, RequestNoteType type)
        throws CvqException {

        // filter private notes one is not allowed to see
        // (agent private notes when ecitizen, and vice-versa)
        // TODO refactor this security filtering which doesn't look very robust
        List<RequestNote> result = new ArrayList<RequestNote>();
        List<RequestNote> notes = requestNoteDAO.listByRequestAndType(requestId, type);
        if (notes == null)
            return result;
        boolean isAgentNote;
        for (RequestNote note : notes) {
            isAgentNote = agentService.exists(note.getUserId());
            if (!note.getType().equals(RequestNoteType.INTERNAL)
                || (isAgentNote
                    && SecurityContext.BACK_OFFICE_CONTEXT.equals(SecurityContext.getCurrentContext()))
                || (!isAgentNote
                    && SecurityContext.FRONT_OFFICE_CONTEXT.equals(SecurityContext.getCurrentContext()))) {
                    result.add(note);
            }
        }
        return result;
    }

    public RequestNote getLastNote(final Long requestId, RequestNoteType type)
        throws CvqException {
        List<RequestNote> notes = getNotes(requestId, type);
        if (notes == null || notes.isEmpty()) return null;
        return notes.get(notes.size() -1);
    }

    public RequestNote getLastAgentNote(final Long requestId, RequestNoteType type)
        throws CvqException {
        List<RequestNote> notes = getNotes(requestId, type);
        if (notes == null || notes.isEmpty()) return null;
        Collections.reverse(notes);
        for (RequestNote note : notes) {
            if (agentService.exists(note.getUserId())) {
                return note;
            }
        }
        return null;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void addNote(final Long requestId, final RequestNoteType rtn, final String note) {


        Long userId = SecurityContext.getCurrentUserId();

        RequestNote requestNote = new RequestNote();
        requestNote.setType(rtn);
        requestNote.setNote(note);
        requestNote.setUserId(userId);
        requestNote.setDate(new Date());

        Request request = requestDAO.findById(requestId);
        if (request.getNotes() == null) {
            Set<RequestNote> notes = new HashSet<RequestNote>();
            notes.add(requestNote);
            request.setNotes(notes);
        } else {
            request.getNotes().add(requestNote);
        }

        updateLastModificationInformation(request);
        if (agentService.exists(userId)) {
            RequestEvent requestEvent =
                new RequestEvent(this, EVENT_TYPE.NOTE_ADDED, request);
            requestEvent.addComplementaryData(COMP_DATA.REQUEST_NOTE, requestNote);
            applicationContext.publishEvent(requestEvent);
        } else {
            RequestEvent requestEvent = new RequestEvent(this, EVENT_TYPE.USER_NOTE_ADDED, request);
            requestEvent.addComplementaryData(COMP_DATA.REQUEST_NOTE, requestNote);
            applicationContext.publishEvent(requestEvent);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void addNote(Long requestId, RequestNoteType rnt, String note, byte[] attachment,
            String attachmentName, Long parentId) {
        Long userId = SecurityContext.getCurrentUserId();

        RequestNote requestNote = new RequestNote();
        requestNote.setType(rnt);
        requestNote.setNote(note);
        requestNote.setUserId(userId);
        requestNote.setDate(new Date());
        requestNote.setAttachment(attachment);
        requestNote.setAttachmentName(attachmentName);
        requestNote.setParentId(parentId);

        Request request = requestDAO.findById(requestId);
        if (request.getNotes() == null) {
            Set<RequestNote> notes = new HashSet<RequestNote>();
            notes.add(requestNote);
            request.setNotes(notes);
        } else {
            request.getNotes().add(requestNote);
        }

        updateLastModificationInformation(request);
        if (agentService.exists(userId)) {
            RequestEvent requestEvent =
                new RequestEvent(this, EVENT_TYPE.NOTE_ADDED, request);
            requestEvent.addComplementaryData(COMP_DATA.REQUEST_NOTE, requestNote);
            applicationContext.publishEvent(requestEvent);
        } else {
            RequestEvent requestEvent =
                    new RequestEvent(this, EVENT_TYPE.USER_NOTE_ADDED, request);
                requestEvent.addComplementaryData(COMP_DATA.REQUEST_NOTE, requestNote);
                applicationContext.publishEvent(requestEvent);
         }
     }

    @Override
    public RequestNote getNote(Long requestId, Long requestNoteId) throws CvqException {
        return requestNoteDAO.getNote(requestId, requestNoteId);
    }

    private void updateLastModificationInformation(Request request) {

        request.setLastModificationDate(new Date());
        request.setLastInterveningUserId(SecurityContext.getCurrentUserId());

        requestDAO.update(request);
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setRequestNoteDAO(IRequestNoteDAO requestNoteDAO) {
        this.requestNoteDAO = requestNoteDAO;
    }

    public void setAgentService(IAgentService agentService) {
        this.agentService = agentService;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }
}
