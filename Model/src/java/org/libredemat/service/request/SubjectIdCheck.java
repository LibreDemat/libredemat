package org.libredemat.service.request;

import java.math.BigInteger;

import org.libredemat.business.request.RequestData;
import org.libredemat.business.request.RequestState;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.exception.CvqException;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

public class SubjectIdCheck extends AbstractAnnotationCheck<SubjectId> {

    private static final long serialVersionUID = 1L;

    private static IRequestServiceRegistry requestServiceRegistry;

    private static IRequestWorkflowService requestWorkflowService;

    @Override
    public boolean isSatisfied(Object validatedObject, Object valueToValidate, OValContext context,
        Validator validator) throws OValException {
        RequestData requestData = (RequestData)validatedObject;
        if (requestData.getId() != null) {
            BigInteger subjectId = (BigInteger)HibernateUtil.getSession()
                .createSQLQuery("select subject_id from request where id = :id")
                    .setLong("id", requestData.getId()).uniqueResult();
            if (subjectId != null) {
                if (Long.valueOf(subjectId.longValue()).equals(valueToValidate)) {
                    return true;
                } else if (!RequestState.DRAFT.equals(requestData.getState())) {
                    return false;
                }
            }
        }

        if (requestServiceRegistry.getRequestService(requestData.getRequestType().getLabel()).getSubjectPolicy() != IRequestWorkflowService.SUBJECT_POLICY_NONE) {
            HibernateUtil.getSession().evict(requestData);
            try {
                requestWorkflowService.checkSubjectPolicy(
                        (Long)valueToValidate,
                        requestData.getHomeFolderId(),
                        requestServiceRegistry.getRequestService(requestData.getRequestType().getLabel())
                        .getSubjectPolicy(),
                        requestData.getRequestType()
                );
                return true;
            } catch (CvqException e) {
                return false;
            } finally {
                HibernateUtil.getSession().merge(requestData);
            }
        } else {
            return true;
        }
    }

    public static void setRequestServiceRegistry(IRequestServiceRegistry requestServiceRegistry) {
        SubjectIdCheck.requestServiceRegistry = requestServiceRegistry;
    }

    public static void setRequestWorkflowService(IRequestWorkflowService requestWorkflowService) {
        SubjectIdCheck.requestWorkflowService = requestWorkflowService;
    }
}
