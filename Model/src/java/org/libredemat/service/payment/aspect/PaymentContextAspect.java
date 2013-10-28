package org.libredemat.service.payment.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.dao.payment.IPaymentDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.dao.users.IIndividualDAO;
import org.libredemat.security.GenericAccessManager;
import org.libredemat.security.PermissionException;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.security.annotation.IsUser;
import org.libredemat.service.payment.annotation.IsPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;


@Aspect
public class PaymentContextAspect implements Ordered {

    private Logger logger = Logger.getLogger(PaymentContextAspect.class);

    @Autowired
    private IPaymentDAO paymentDAO;
    @Autowired
    private IHomeFolderDAO homeFolderDAO;
    @Autowired
    private IIndividualDAO individualDAO;

    @Before("org.libredemat.SystemArchitecture.businessService() && @annotation(context) && within(org.libredemat.service.payment..*)")
    public void contextAnnotatedMethod(JoinPoint joinPoint, Context context) {
        
        if (!ArrayUtils.contains(context.types(), ContextType.ECITIZEN)
            && !ArrayUtils.contains(context.types(), ContextType.AGENT))
            return;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        if (context.privilege().equals(ContextPrivilege.NONE)) {
            logger.debug("contextAnnotatedMethod() no special privilege asked"
                    + " on method " + signature.getMethod().getName() + ", returning");
            return;
        }

        Method method = signature.getMethod();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        Object[] arguments = joinPoint.getArgs();
        Long homeFolderId = null;
        Long individualId = null;
        int i = 0;
        for (Object argument : arguments) {
            if (parametersAnnotations[i] != null && parametersAnnotations[i].length > 0) {
                Annotation parameterAnnotation = parametersAnnotations[i][0];
                if (parameterAnnotation.annotationType().equals(IsUser.class)) {
                    if (argument instanceof Long) {
                        Long id = (Long) argument;
                        if (individualDAO.findById(id) == null) {
                            if (homeFolderDAO.findById(id) != null);
                                homeFolderId = id;
                        } else {
                            individualId = id;
                        }
                    } else if (argument instanceof Individual) {
                        individualId = ((Individual) argument).getId();
                    } else if (argument instanceof HomeFolder) {
                        homeFolderId = ((HomeFolder)argument).getId();
                    }
                } else if (parameterAnnotation.annotationType().equals(IsPayment.class)) {
                    if (argument instanceof Long) {
                        Object payment = paymentDAO.findById((Long)argument);
                        if (payment != null) {
                            homeFolderId = ((Payment) payment).getHomeFolderId();
                            individualId = ((Payment) payment).getRequesterId();
                        } else {
                            // FIXME throw PermissionException?
                            logger.error("no payment match the given id: " + (Long) argument);
                        }
                    } else if (argument instanceof Payment) {
                        homeFolderId = ((Payment) argument).getHomeFolderId();
                        individualId= ((Payment) argument).getRequesterId();
                    }
                }
            }
            i++;
        }

        if (!GenericAccessManager.performPermissionCheck(homeFolderId, individualId, context))
            throw new PermissionException(joinPoint.getSignature().getDeclaringType(), 
                    joinPoint.getSignature().getName(), context.types(), context.privilege(),
                    "access denied on home folder " + homeFolderId 
                        + " / individual " + individualId);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public IPaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public void setPaymentDAO(IPaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setIndividualDAO(IIndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }
}
