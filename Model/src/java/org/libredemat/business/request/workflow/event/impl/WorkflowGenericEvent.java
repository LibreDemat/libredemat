package org.libredemat.business.request.workflow.event.impl;

import java.util.ArrayList;
import java.util.List;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.workflow.event.IWorkflowEvent;
import org.libredemat.business.request.workflow.event.IWorkflowEventVisitor;
import org.libredemat.business.request.workflow.event.IWorkflowPostAction;
import org.libredemat.exception.CvqException;


public abstract class WorkflowGenericEvent implements IWorkflowEvent {

    private static final long serialVersionUID = 1L;
    private Request request;
    private List<IWorkflowPostAction> workflowPostActions = new ArrayList<IWorkflowPostAction>();

    @Override
    public void accept(IWorkflowEventVisitor workflowEventVisitor) throws CvqException {}

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public WorkflowGenericEvent(Request request) {
        setRequest(request);
    }

    public List<IWorkflowPostAction> getWorkflowPostActions() {
        return workflowPostActions;
    }

    public void setWorkflowPostAction(IWorkflowPostAction workflowPostAction) {
        workflowPostActions.add(workflowPostAction);
    }
}
