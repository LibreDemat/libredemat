package org.libredemat.external.endpoint;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Test;
import org.libredemat.business.request.RequestState;
import org.libredemat.business.request.external.RequestExternalAction;
import org.libredemat.business.users.external.HomeFolderMapping;
import org.libredemat.business.users.external.IndividualMapping;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.external.ExternalServiceTestCase;
import org.libredemat.external.endpoint.AckRequestServiceEndpoint;
import org.libredemat.external.endpoint.HomeFolderMappingServiceEndpoint;
import org.libredemat.external.endpoint.RequestServiceEndpoint;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.request.IRequestExportService;
import org.libredemat.service.request.external.IRequestExternalService;
import org.libredemat.util.Critere;
import org.libredemat.util.DateUtils;
import org.libredemat.xml.common.RequestStateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.xmlbeans.XmlBeansMarshaller;

import org.libredemat.AckRequestType;
import org.libredemat.AckRequestsRequestDocument;
import org.libredemat.GetRequestsRequestDocument;
import org.libredemat.HomeFolderMappingRequestDocument;
import org.libredemat.HomeFolderMappingType;
import org.libredemat.IndividualMappingType;
import org.libredemat.AckRequestsRequestDocument.AckRequestsRequest;
import org.libredemat.AckRequestsResponseDocument.AckRequestsResponse;
import org.libredemat.GetRequestsRequestDocument.GetRequestsRequest;
import org.libredemat.GetRequestsResponseDocument.GetRequestsResponse;
import org.libredemat.HomeFolderMappingRequestDocument.HomeFolderMappingRequest;

public class RequestServiceEndpointTest extends ExternalServiceTestCase {

    @Autowired
    private IRequestExternalService requestExternalService;
    @Autowired
    private IRequestExportService requestExportService;

    @Test
    public void testGetAndAckFlow() throws Exception {

        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithManageRoles);
        
        /* Initialize internal variables */
        AckRequestServiceEndpoint endpoint1 = gimmeAckRequestServiceEndpoint();
        RequestServiceEndpoint endpoint2 = gimmeRequestServiceEndpoint();

        try {
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -1);
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            GetRequestsRequest getRequest = GetRequestsRequest.Factory.newInstance();
            AckRequestsRequest ackRequest = AckRequestsRequest.Factory.newInstance();
            GetRequestsRequestDocument requestDocument = 
                GetRequestsRequestDocument.Factory.newInstance();
            
            getRequest.setDateFrom(calendar);
            getRequest.setState(RequestStateType.Enum.forString(RequestState.PENDING.toString()));
            requestDocument.setGetRequestsRequest(getRequest);
            
            // test we get our request
            GetRequestsResponse getResponse = 
                (GetRequestsResponse) endpoint2.invokeInternal(requestDocument);
            int getCountBefore = getResponse.getRequestArray().length;
            assertEquals(1, getCountBefore); 
            
            continueWithNewTransaction();

            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);

            Set<Critere> criteriaSet = new HashSet<Critere>();
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_DATE,
                DateUtils.parseDate("13/07/2007"), Critere.GT));
            assertEquals(1, requestExternalActionService.getTracesCount(criteriaSet).longValue());
            
            /* Create acknowledgement response */
            AckRequestType[] types = new AckRequestType[1];
            AckRequestType type = AckRequestType.Factory.newInstance();
            type.setRequestId(request.getId());
            type.setErroneous(false);
            types[0] = type;
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            ackRequest.setAckElementsArray(types);
            AckRequestsRequestDocument ackRequestDocument = 
                AckRequestsRequestDocument.Factory.newInstance();
            ackRequestDocument.setAckRequestsRequest(ackRequest);
            AckRequestsResponse ackResponse = 
                (AckRequestsResponse) endpoint1.invokeInternal(ackRequestDocument);
            assertNotNull(ackResponse);
            assertTrue(ackResponse.getAccomplished());
            
            continueWithNewTransaction();
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);

            assertEquals(2, requestExternalActionService.getTracesCount(criteriaSet).longValue());
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            getResponse = (GetRequestsResponse) endpoint2.invokeInternal(requestDocument);
            assertEquals(0, getResponse.getRequestArray().length);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unwaited exception trown : " + e.getMessage());
        } finally {
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
        }
    }
    
    @Test
    public void testAckServiceEndpoint() throws Exception {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithManageRoles);
        
        /* Initialize internal variables */
        AckRequestServiceEndpoint endpoint1 = gimmeAckRequestServiceEndpoint();
        RequestServiceEndpoint endpoint2 = gimmeRequestServiceEndpoint();
        Long[] requestIDs = new Long[3];
        try {
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            
            GetRequestsRequest getRequest = GetRequestsRequest.Factory.newInstance();
            AckRequestsRequest ackRequest = AckRequestsRequest.Factory.newInstance();
            GetRequestsRequestDocument requestDocument = 
                GetRequestsRequestDocument.Factory.newInstance();
            
            getRequest.setDateFrom(calendar);
            getRequest.setState(RequestStateType.Enum.forString(RequestState.PENDING.toString()));
            requestDocument.setGetRequestsRequest(getRequest);
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());
            
            /* Create sent traces */
            GetRequestsResponse getResponse = 
                (GetRequestsResponse) endpoint2.invokeInternal(requestDocument);
            int getCountBefore = getResponse.getRequestArray().length;
            
            assertEquals(1, getCountBefore);
            
            getRequest.setRequestTypeLabel(tirLabel);
            requestDocument.setGetRequestsRequest(getRequest);

            getResponse = (GetRequestsResponse) endpoint2.invokeInternal(requestDocument);
            getCountBefore = getResponse.getRequestArray().length;
            
            assertEquals(1, getCountBefore);
            
            continueWithNewTransaction();

            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            
            Set<Critere> criteriaSet = new HashSet<Critere>();
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_DATE,
                DateUtils.parseDate("13/07/2007"), Critere.GT));
            long tracesCount = requestExternalActionService.getTracesCount(criteriaSet);
            assertTrue("Should have found at least one trace", tracesCount > 0);
            
            /* Create acknowledged traces */
            AckRequestType[] types = new AckRequestType[3];
            AckRequestType type = AckRequestType.Factory.newInstance();
            requestIDs[0] = createRequest().getId();
            type.setRequestId(requestIDs[0]);
            type.setErroneous(false);
            types[0] = type;
            
            type = AckRequestType.Factory.newInstance();
            requestIDs[1] = createRequest().getId();
            type.setRequestId(requestIDs[1]);
            type.setErroneous(false);
            types[1] = type;
            
            type = AckRequestType.Factory.newInstance();
            requestIDs[2] = createRequest().getId();
            type.setRequestId(requestIDs[2]);
            type.setErroneous(true);
            types[2] = type;
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());
            
            ackRequest.setAckElementsArray(types);
            AckRequestsRequestDocument ackRequestDocument = AckRequestsRequestDocument.Factory.newInstance();
            ackRequestDocument.setAckRequestsRequest(ackRequest);
            AckRequestsResponse ackResponse = (AckRequestsResponse) endpoint1.invokeInternal(ackRequestDocument);
            assertNotNull(ackResponse);

            continueWithNewTransaction();
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            
            assertEquals(tracesCount+3, requestExternalActionService.getTracesCount(criteriaSet).longValue());
            
            criteriaSet = new HashSet<Critere>();
            criteriaSet.add(new Critere(RequestExternalAction.SEARCH_BY_STATUS,
                RequestExternalAction.Status.ERROR, Critere.EQUALS));
            RequestExternalAction trace = requestExternalActionService.getTraces(criteriaSet, null, null, 1, 0).get(0);
            
            assertEquals(trace.getKey(), requestIDs[2].toString());
            assertEquals(trace.getKeyOwner(),"libredemat");
            assertEquals(trace.getStatus(), RequestExternalAction.Status.ERROR);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unwaited exception trown : " + e.getMessage());
        } finally {
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
            for (Long id : requestIDs) requestWorkflowService.delete(id);
        }
        
    }
    
    @Test
    public void testSecuredEndpoint() throws Exception {
        try {

            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.ADMIN_CONTEXT);
            
            RequestServiceEndpoint endpoint = gimmeRequestServiceEndpoint();

            GetRequestsRequestDocument pendedRequestDocument = 
                GetRequestsRequestDocument.Factory.newInstance();
            pendedRequestDocument.addNewGetRequestsRequest();
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());
            
            endpoint.invokeInternal(pendedRequestDocument);

        } catch (CvqObjectNotFoundException e) {
            fail("Unwaited exception raised");
        } catch (CvqException e) {
            fail();
        }
    }
    
    @Test
    public void testAccessPermissions() {
        try {
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            RequestServiceEndpoint endpoint = gimmeRequestServiceEndpoint();

            GetRequestsRequestDocument pendedRequestDocument = 
                GetRequestsRequestDocument.Factory.newInstance();
            GetRequestsRequest pendedRequest = GetRequestsRequest.Factory.newInstance();
            pendedRequest.setRequestTypeLabel("Dude Registration");
            
            pendedRequestDocument.setGetRequestsRequest(pendedRequest);
            GetRequestsResponse getResponse = 
                (GetRequestsResponse) endpoint.invokeInternal(pendedRequestDocument);
            assertNotNull(getResponse.getError());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unwaited exception raised");
        }
    }
    
    @Test
    public void testRequestServiceEndpoint() throws Exception {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
        SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());
        Long[] requestIDs = new Long[2];
        try {
            int completeCountBefore = 0;

            /* Initialize internal variables */
            RequestServiceEndpoint endpoint = gimmeRequestServiceEndpoint();
            AckRequestServiceEndpoint endpoint2 = gimmeAckRequestServiceEndpoint();

            GetRequestsRequestDocument pendedRequestDocument = GetRequestsRequestDocument.Factory.newInstance();
            GetRequestsRequest pendedRequest = GetRequestsRequest.Factory.newInstance();
            
            /*
             * Retrieve requests that has pending status and has fromDate
             * equals to current date
             */
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            pendedRequest.setDateFrom(calendar);
            pendedRequest.setState(RequestStateType.Enum.forString(RequestState.PENDING.toString()));
            
            pendedRequestDocument.setGetRequestsRequest(pendedRequest);
            GetRequestsResponse pendedResponse = (GetRequestsResponse) endpoint.invokeInternal(pendedRequestDocument);
            int pendedCountBefore = pendedResponse.getRequestArray().length;
            
            /* Retrieving requests with an another state */
            GetRequestsRequestDocument completeRequestDocument = GetRequestsRequestDocument.Factory.newInstance();
            GetRequestsRequest completeRequest = GetRequestsRequest.Factory.newInstance();
            completeRequest.setDateFrom(pendedRequest.getDateFrom());
            completeRequest.setState(RequestStateType.Enum.forString(RequestState.COMPLETE.toString()));
            
            completeRequestDocument.setGetRequestsRequest(completeRequest);
            GetRequestsResponse completeResponse = (GetRequestsResponse) endpoint.invokeInternal(completeRequestDocument);
            completeCountBefore = completeResponse.getRequestArray().length;
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
            requestIDs[0] = createRequest().getId();
            requestIDs[1] = createRequest().getId();
            
            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            pendedResponse = (GetRequestsResponse) endpoint.invokeInternal(pendedRequestDocument);
            completeResponse = (GetRequestsResponse) endpoint.invokeInternal(completeRequestDocument);
            int pendedCountAfter = pendedResponse.getRequestArray().length;
            int completeCountAfter = completeResponse.getRequestArray().length;
            
            assertEquals("Pended request counts don't match", 
                    pendedCountBefore + 2, pendedCountAfter);
            assertEquals("Complete request counts don't match",
                    completeCountBefore, completeCountAfter);
            
            /*
             * Retrieve a request by its id
             */
            GetRequestsRequest getRequestById = GetRequestsRequest.Factory.newInstance();
            getRequestById.setId(request.getId());
            pendedRequestDocument.setGetRequestsRequest(getRequestById);
            GetRequestsResponse getRequestByIdResponse = 
                (GetRequestsResponse) endpoint.invokeInternal(pendedRequestDocument);
            assertEquals(1, getRequestByIdResponse.getRequestArray().length);
            
            /*
             * Ack it and check we still get it when asked by id 
             */
            AckRequestsRequest ackRequest = AckRequestsRequest.Factory.newInstance();
            AckRequestType[] types = new AckRequestType[1];
            AckRequestType type = AckRequestType.Factory.newInstance();
            type.setRequestId(request.getId());
            type.setErroneous(false);
            types[0] = type;
            ackRequest.setAckElementsArray(types);

            AckRequestsRequestDocument ackRequestDocument = 
                AckRequestsRequestDocument.Factory.newInstance();
            ackRequestDocument.setAckRequestsRequest(ackRequest);
            AckRequestsResponse ackResponse = 
                (AckRequestsResponse) endpoint2.invokeInternal(ackRequestDocument);
            assertNotNull(ackResponse);

            getRequestByIdResponse = 
                (GetRequestsResponse) endpoint.invokeInternal(pendedRequestDocument);
            assertEquals(1, getRequestByIdResponse.getRequestArray().length);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unwaited exception trown : " + e.getMessage());
        } finally {
            SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
            for (Long id : requestIDs) requestWorkflowService.delete(id);
        }
    }
    
    @Test
    public void testHomeFolderMapping() throws CvqException {
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.BACK_OFFICE_CONTEXT);
        
        try {

            /* Initialize internal variables */
            XmlBeansMarshaller xmlBeansMarshaller = new XmlBeansMarshaller();
            HomeFolderMappingServiceEndpoint endpoint = new HomeFolderMappingServiceEndpoint(xmlBeansMarshaller);
            endpoint.setExternalHomeFolderService(externalHomeFolderService);

            HomeFolderMappingRequestDocument homeFolderMappingRequestDocument =
                HomeFolderMappingRequestDocument.Factory.newInstance();
            HomeFolderMappingRequest homeFolderMappingRequest =
                homeFolderMappingRequestDocument.addNewHomeFolderMappingRequest();
            homeFolderMappingRequest.setLocalAuthority(localAuthorityName);

            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            
            externalHomeFolderService.addHomeFolderMapping(fakeExternalService.getLabel(), fake.id, "OriginalHomeFolderId");
            continueWithNewTransaction();
            externalHomeFolderService.setExternalId(fakeExternalService.getLabel(), fake.id, fake.responsibleId, "OriginalIndividualId");
            continueWithNewTransaction();
            
            HomeFolderMapping homeFolderMapping =
                externalHomeFolderService.getHomeFolderMapping(fakeExternalService.getLabel(), fake.id);
            HomeFolderMappingType homeFolderMappingType = homeFolderMappingRequest.addNewHomeFolderMapping();
            homeFolderMappingType.setExternalLibreDematId(homeFolderMapping.getExternalLibreDematId());
            homeFolderMappingType.setExternalId("ExternalHomeFolderId");
            
            IndividualMappingType individualMappingType = homeFolderMappingRequest.addNewIndividualMapping();
            String externalLibreDematId = null;
            for (IndividualMapping indMapping : homeFolderMapping.getIndividualsMappings()) {
                if (indMapping.getExternalId().equals("OriginalIndividualId")) {
                    externalLibreDematId = indMapping.getExternalLibreDematId();
                    individualMappingType.setExternalLibreDematId(indMapping.getExternalLibreDematId());
                    individualMappingType.setExternalId("ExternalIndividualId");
                    break;
                }
            }

            SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.EXTERNAL_SERVICE_CONTEXT);
            SecurityContext.setCurrentExternalService(fakeExternalService.getLabel());

            endpoint.invokeInternal(homeFolderMappingRequestDocument);
            
            continueWithNewTransaction();
            homeFolderMapping = externalHomeFolderService.getHomeFolderMapping(fakeExternalService.getLabel(), fake.id);
            assertEquals("ExternalHomeFolderId", homeFolderMapping.getExternalId());
            for (IndividualMapping indMapping : homeFolderMapping.getIndividualsMappings()) {
                if (indMapping.getExternalLibreDematId().equals(externalLibreDematId)) {
                    assertEquals("ExternalIndividualId", indMapping.getExternalId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unwaited exception trown : " + e.getMessage());
        } finally {
            SecurityContext.setCurrentContext(SecurityContext.BACK_OFFICE_CONTEXT);
            SecurityContext.setCurrentAgent(agentNameWithManageRoles);
            for (RequestExternalAction trace :
                requestExternalActionService.getTraces(new HashSet<Critere>(), null, null, 0, 0)) {
                HibernateUtil.getSession().delete(trace);
            }
        }
    }
    
    private RequestServiceEndpoint gimmeRequestServiceEndpoint() {
        RequestServiceEndpoint endpoint = new RequestServiceEndpoint(new XmlBeansMarshaller());
        endpoint.setRequestSearchService(requestSearchService);
        endpoint.setRequestExternalActionService(requestExternalActionService);
        endpoint.setRequestDAO((IRequestDAO)getApplicationBean("requestDAO"));
        endpoint.setRequestExternalService(requestExternalService);
        endpoint.setRequestExportService(requestExportService);
        
        return endpoint;
    }

    private AckRequestServiceEndpoint gimmeAckRequestServiceEndpoint() {
        AckRequestServiceEndpoint endpoint = new AckRequestServiceEndpoint(new XmlBeansMarshaller());
        endpoint.setRequestExternalActionService(requestExternalActionService);
        
        return endpoint;
    }
}
