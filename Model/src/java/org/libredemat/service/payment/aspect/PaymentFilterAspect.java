package org.libredemat.service.payment.aspect;

import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.libredemat.business.payment.Payment;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.payment.annotation.PaymentFilter;
import org.libredemat.util.Critere;
import org.springframework.core.Ordered;


/**
 * @author jsb@zenexity.fr
 */
@Aspect
public class PaymentFilterAspect implements Ordered {

    @SuppressWarnings("unchecked")
    @Before("org.libredemat.SystemArchitecture.businessService() && @annotation(paymentFilter) && within(org.libredemat.service.payment..*)")
    public void contextAnnotatedMethod(JoinPoint joinPoint, PaymentFilter paymentFilter) {
        Object[] arguments = joinPoint.getArgs();
        Set<Critere> criteriaSet = (Set<Critere>) arguments[0];
        if (SecurityContext.isFrontOfficeContext()) {
            criteriaSet.add(new Critere(Payment.SEARCH_BY_HOME_FOLDER_ID,
                SecurityContext.getCurrentEcitizen().getHomeFolder().getId(),
                Critere.EQUALS));
        }
    }

    public int getOrder() {
        return 2;
    }
}
