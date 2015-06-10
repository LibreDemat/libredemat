package org.libredemat.util.admin;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.request.IRequestSearchService;
import org.libredemat.service.request.IRequestWorkflowService;
import org.libredemat.util.Critere;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class RequestStepAdder {

    private static Logger logger = Logger.getLogger(RequestWorkflowNavigator.class);

    private static IRequestSearchService requestSearchService;
    private static IRequestWorkflowService requestWorkflowService;

    public void addRequestStep(String requestTypeLabel, String stepName, String stepState) {

        System.err.println("adding a step on '" + requestTypeLabel + "' requests for local authority "
                + SecurityContext.getCurrentSite().getName());

        Map<String, Object> step = new HashMap<String, Object>(4);
        step.put("state", stepState);
        step.put("required", false);
        step.put("errorMsg", null);
        step.put("invalidFields", new ArrayList<String>());

        Critere critere = new Critere(Request.SEARCH_BY_REQUEST_TYPE_LABEL, requestTypeLabel, Critere.EQUALS);
        Set<Critere> criteria = new HashSet<Critere>();
        criteria.add(critere);
        try {
            List<Request> requests = requestSearchService.get(criteria, null, null, -1, 0, true);
            System.err.println("got " + requests.size() + " matching requests");
            for (Request request : requests) {
                if (request.getStepStates().get(stepName) != null) {
                    System.err.println("ignoring existing step on request " + request.getId());
                    continue;
                }
                System.err.println("adding step to request " + request.getId());
                request.getStepStates().put(stepName, step);
                requestWorkflowService.modify(request);
            }
        } catch (Exception e) {
            System.err.println("Error while retrieving requests for label " + requestTypeLabel);
            e.printStackTrace();
        }
    }

    // TODO : maybe step state should be adjusted according to request state
    //        (eg draft -> uncomplete, complete+ -> complete, ...)
    // TODO : maybe also make 'required' a parameter
    public static void main(final String[] args) throws Exception {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.OFF);
        logger.setLevel(Level.INFO);
        if (args.length == 0 || args[0].equals("help") || (args.length != 2 && args.length != 3)) {
            printUsageAndExit();
        }

        String requestTypeLabel = args[0];
        String stepName = args[1];
        String stepState = args[2] == null ? "uncomplete" : args[2];

        ClassPathXmlApplicationContext cpxa = SpringApplicationContextLoader.loadContext(null);
        ILocalAuthorityRegistry localAuthorityRegistry = (ILocalAuthorityRegistry) cpxa.getBean("localAuthorityRegistry");
        requestSearchService = (IRequestSearchService) cpxa.getBean("requestSearchService");
        requestWorkflowService = (IRequestWorkflowService) cpxa.getBean("requestWorkflowService");

        RequestStepAdder requestStepAdder = new RequestStepAdder();

        localAuthorityRegistry.browseAndCallback(requestStepAdder, "addRequestStep", new Object[] {
                requestTypeLabel, stepName, stepState
        });

        System.out.println("Exiting.");
        System.exit(0);
    }

    private static void printUsageAndExit() {
        System.out.println(" USAGE -              ./invoke_request_step_adder.sh [REQUEST TYPE LABEL] [STEP NAME] [STEP STATE]");
        System.out.println("  - [REQUEST TYPE LABEL] : The request type to handle");
        System.out.println("  - [STEP NAME] : The name of the step to add");
        System.out.println("  - [STEP STATE] : The state of the step to add (uncomplete by default)");
        System.exit(0);
    }
}
