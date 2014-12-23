package org.libredemat.service.request.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.libredemat.business.request.RequestLock;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IRequestDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.security.PermissionException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.request.IRequestLockService;
import org.libredemat.service.request.IRequestTypeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;


public class RequestLockService implements IRequestLockService, BeanFactoryAware {

    private IRequestDAO requestDAO;
    private IGenericDAO genericDAO;
    private IRequestTypeService requestTypeService;
    private ListableBeanFactory beanFactory;

    @Override
    @Context(types = {ContextType.ECITIZEN}, privilege = ContextPrivilege.WRITE)
    public void lock(final Long id)
        throws CvqException {
        applyLock(id);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public void tryToLock(final Long id) {
        try {
            // FIXME JSB : hack to avoid bypassing aspect security
            ((IRequestLockService)beanFactory.getBean("requestLockService")).applyLock(id);
        } catch (PermissionException e) {
            // couldn't lock request : we only have READ privilege
        } catch (CvqException e) {
            // couldn't lock request : it is probably already locked
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void applyLock(final Long requestId)
        throws CvqException {
        synchronized(this) {
            RequestLock lock = getRequestLock(requestId);
            if (lock == null) {
                // no lock, we can acquire a new one
                lock = new RequestLock(requestId, SecurityContext.getCurrentUserId());
            }
            else if (lock.getUserId().equals(SecurityContext.getCurrentUserId())
                    || new DateTime(lock.getDate().getTime()).plusMinutes(
                        requestTypeService.getGlobalRequestTypeConfiguration().getRequestLockMaxDelay())
                            .isBeforeNow()) {
                // current user owns the lock,
                // or the lock is old enough to be overriden
                lock.setDate(new Date());
                lock.setUserId(SecurityContext.getCurrentUserId());
            } else {
                throw new CvqException("request.lock.error.alreadyLocked");
            }
            genericDAO.saveOrUpdate(lock);
            HibernateUtil.getSession().flush();
            JpaUtil.getEntityManager().flush();
            JpaUtil.closeAndReOpen(false);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public RequestLock getRequestLock(final Long requestId) {
        synchronized(this) {
            return requestDAO.getRequestLock(requestId);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public boolean isLocked(final Long requestId) {
        RequestLock lock = getRequestLock(requestId);
        return (lock != null
                && !lock.getUserId().equals(SecurityContext.getCurrentUserId())
                && !new DateTime(lock.getDate().getTime()).plusMinutes(
                    requestTypeService.getGlobalRequestTypeConfiguration().getRequestLockMaxDelay())
                        .isBeforeNow());
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public boolean isLockedByCurrentUser(final Long requestId) {
        RequestLock lock = getRequestLock(requestId);
        return (lock != null
                && lock.getUserId().equals(SecurityContext.getCurrentUserId())
                && !new DateTime(lock.getDate().getTime()).plusMinutes(
                    requestTypeService.getGlobalRequestTypeConfiguration().getRequestLockMaxDelay())
                        .isBeforeNow());
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void release(final Long requestId) {
        synchronized(this) {
            RequestLock lock = getRequestLock(requestId);
            if (lock == null || !lock.getUserId().equals(SecurityContext.getCurrentUserId())) {
                return;
            }
            genericDAO.delete(lock);
            HibernateUtil.getSession().flush();
        }
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN}, privilege = ContextPrivilege.NONE)
    public void cleanRequestLocks() {
        synchronized(this) {
            requestDAO.cleanRequestLocks(
                requestTypeService.getGlobalRequestTypeConfiguration().getRequestLockMaxDelay());
            HibernateUtil.getSession().flush();
        }
    }

    public void setRequestDAO(IRequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        this.beanFactory = (ListableBeanFactory) arg0;
    }

    public void setRequestTypeService(IRequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }
}
