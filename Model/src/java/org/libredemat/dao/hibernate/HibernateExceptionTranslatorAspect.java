package org.libredemat.dao.hibernate;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.HibernateException;
import org.libredemat.dao.CvqDaoException;


@Aspect
public class HibernateExceptionTranslatorAspect {

    @AfterThrowing(pointcut="org.libredemat.SystemArchitecture.dataAccessOperation()",
            throwing="hibernateEx")
    public void translateException(HibernateException hibernateEx) {
        throw new CvqDaoException("Unexpected DAO exception", hibernateEx);
    }
}
