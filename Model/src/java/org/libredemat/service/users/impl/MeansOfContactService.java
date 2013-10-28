package org.libredemat.service.users.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.MeansOfContact;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.dao.users.IMeansOfContactDAO;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.users.IMeansOfContactService;


/**
 * Implementation of the {@link IMeansOfContactService} service.
 *
 * @author rdj@zenexity.fr
 */
public class MeansOfContactService implements IMeansOfContactService, ILocalAuthorityLifecycleAware {

    private static Logger logger = Logger.getLogger(MeansOfContactService.class);

    private IMeansOfContactDAO meansOfContactDAO;

    @Override
    public MeansOfContact getById(Long id) {
        return meansOfContactDAO.findById(id);
    }

    /* BE CAREFUL :
     *  - MeansOfContact removing not yet implement ...
     *  - EMAIL MeansOfContact is enabled by default
     */
    @Context(types = {ContextType.SUPER_ADMIN})
    private void initAvalaibleMeansOfContact() {
        logger.debug("initAvalaibleMeansOfContact() init for "
            + SecurityContext.getCurrentSite().getName());
        List<MeansOfContact> mocList = meansOfContactDAO.listAll();
        initLoop : for (MeansOfContactEnum mocEnum : MeansOfContactEnum.allMeansOfContactEnums) {
            for (MeansOfContact moc : mocList) {
                if (moc.getType().equals(mocEnum)) {
                    continue initLoop;
                }
            }
            MeansOfContact meansOfContact = new MeansOfContact(mocEnum);
            if (meansOfContact.getType().equals(MeansOfContactEnum.EMAIL))
                meansOfContact.setEnabled(true);
            meansOfContactDAO.create(meansOfContact);
        }
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN})
    public void addLocalAuthority(String localAuthorityName) {
        initAvalaibleMeansOfContact();
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN})
    public void removeLocalAuthority(String localAuthorityName) {
    }

    @Override
    public MeansOfContact getMeansOfContactByType(MeansOfContactEnum type) {
        return meansOfContactDAO.findByType(type);
    }

    private boolean canDisableMeansOfContact(MeansOfContact meansOfContact) {
        List<MeansOfContact> enableMocList = meansOfContactDAO.listAllEnabled();
        return enableMocList.size() > 1 || !enableMocList.get(0).equals(meansOfContact);
    }

    @Override
    public void disableMeansOfContact(Long mocId)
        throws CvqModelException {
        disableMeansOfContact(getById(mocId));
    }

    @Override
    public void disableMeansOfContact(MeansOfContact meansOfContact)
        throws CvqModelException {
        if (!canDisableMeansOfContact(meansOfContact))
            throw new CvqModelException("meansOfContact.message.mustHaveOneEnabled");
        meansOfContact.setEnabled(false);
        meansOfContactDAO.update(meansOfContact);
    }

    @Override
    public void enableMeansOfContact(MeansOfContact meansOfContact) {
        meansOfContact.setEnabled(true);
        meansOfContactDAO.update(meansOfContact);
    }

    @Override
    public List<MeansOfContact> getAvailableMeansOfContact() {
        List<MeansOfContact> results = meansOfContactDAO.listAll();
        Collections.sort(results);
        return results;
    }

    @Override
    public List<MeansOfContact> getEnabledMeansOfContact() {
        return meansOfContactDAO.listAllEnabled();
    }

    private MeansOfContact getFromListByType(List<MeansOfContact> mocList, MeansOfContactEnum type){
        for (MeansOfContact moc : mocList){
            if (moc.getType().equals(type))
                return moc;
        }
        return null;
    }

    @Override
    public List<MeansOfContact> getCurrentEcitizenEnabledMeansOfContact() {
        return getAdultEnabledMeansOfContact(SecurityContext.getCurrentEcitizen());
    }

    @Override
    public List<MeansOfContact> getAdultEnabledMeansOfContact(Adult adult) {
        List<MeansOfContact> enableMocList = meansOfContactDAO.listAllEnabled();
        List<MeansOfContact> individualEnableMocList = new ArrayList<MeansOfContact>();

        if (adult != null) {
            MeansOfContact moc = getFromListByType(enableMocList, MeansOfContactEnum.MAIL);
            if(adult.getAddress() != null &&  moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.EMAIL);
            if(adult.getEmail() != null &&  adult.getEmail().length() > 0 && moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.HOME_PHONE);
            if(adult.getHomePhone() != null &&  adult.getHomePhone().length() > 0 && moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.OFFICE_PHONE);
            if(adult.getOfficePhone() != null && adult.getOfficePhone().length() > 0 && moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.MOBILE_PHONE);
            if(adult.getMobilePhone() != null && adult.getMobilePhone().length() > 0 && moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.SMS);
            if(adult.getMobilePhone() != null && adult.getMobilePhone().length() > 0 &&  moc != null)
                individualEnableMocList.add(moc);

            moc = getFromListByType(enableMocList, MeansOfContactEnum.LOCAL_AUTHORITY_OFFICE);
            if(moc != null)
                individualEnableMocList.add(moc);
        }
        return individualEnableMocList;
    }

    @Override
    public boolean isAvailable(MeansOfContactEnum type, Adult adult) {
        return getAdultEnabledMeansOfContact(adult).contains(getMeansOfContactByType(type));
    }

    public void setMeansOfContactDAO(IMeansOfContactDAO meansOfContactDAO) {
        this.meansOfContactDAO = meansOfContactDAO;
    }
}
