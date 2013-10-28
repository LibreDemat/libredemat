package org.libredemat.business.request.workflow.event;

import org.libredemat.business.request.workflow.event.impl.WorkflowArchivedEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowCancelledEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowClosedEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowCompleteEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowDraftEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowExtInProgressEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowInProgressEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowNotifiedEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowPendingEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowRectifiedEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowRejectedEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowUncompleteEvent;
import org.libredemat.business.request.workflow.event.impl.WorkflowValidatedEvent;
import org.libredemat.exception.CvqException;

public interface IWorkflowEventVisitor {
    void visit(final WorkflowDraftEvent wfEvent) throws CvqException;

    void visit(final WorkflowPendingEvent wfEvent) throws CvqException;

    void visit(final WorkflowInProgressEvent wfEvent) throws CvqException;

    void visit(final WorkflowExtInProgressEvent wfEvent) throws CvqException;

    void visit(final WorkflowCompleteEvent wfEvent) throws CvqException;

    void visit(final WorkflowUncompleteEvent wfEvent) throws CvqException;

    void visit(final WorkflowRectifiedEvent wfEvent) throws CvqException;

    void visit(final WorkflowRejectedEvent wfEvent) throws CvqException;

    void visit(final WorkflowCancelledEvent wfEvent) throws CvqException;

    void visit(final WorkflowValidatedEvent wfEvent) throws CvqException;

    void visit(final WorkflowNotifiedEvent wfEvent) throws CvqException;

    void visit(final WorkflowClosedEvent wfEvent) throws CvqException;

    void visit(final WorkflowArchivedEvent wfEvent) throws CvqException;
}
