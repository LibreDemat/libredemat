package org.libredemat.business.request.workflow.event;

import org.libredemat.service.request.IRequestWorkflowService;

public interface IWorkflowPostAction {

    String getExecutor();

    void execute(IRequestWorkflowService requestWorkflowService);
}
