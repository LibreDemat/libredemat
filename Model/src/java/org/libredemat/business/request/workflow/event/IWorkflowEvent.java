package org.libredemat.business.request.workflow.event;

import org.libredemat.business.request.workflow.event.IWorkflowEventVisitor;
import org.libredemat.exception.CvqException;

public interface IWorkflowEvent {
    void accept(IWorkflowEventVisitor workflowEventVisitor) throws CvqException;
}
