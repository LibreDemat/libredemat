package org.libredemat.service.request.social.impl;

import org.libredemat.business.LibreDematEvent;
import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestEvent;
import org.libredemat.business.request.social.BafaGrantRequest;
import org.libredemat.business.users.Adult;
import org.libredemat.business.users.Individual;
import org.libredemat.service.request.condition.EqualityChecker;
import org.libredemat.service.request.impl.RequestService;

/**
 * @author Jean-Sébastien Bour (jsb@zenexity.fr)
 *
 */
public class BafaGrantRequestService extends RequestService {

    @Override
    public void init() {
        BafaGrantRequest.conditions.put("isSubjectAccountHolder", new EqualityChecker("true"));
    }

    @Override
    public boolean accept(Request request) {
        return request instanceof BafaGrantRequest;
    }

    @Override
    public Request getSkeletonRequest() {
        return new BafaGrantRequest();
    }

    @Override
    public void onRequestCompleted(Request request) {
        BafaGrantRequest bgr = (BafaGrantRequest) request;
        Individual subject = (Individual) genericDAO.findById(Individual.class, bgr.getSubjectId());
        subject.setAddress(bgr.getSubjectAddress());
        subject.setBirthCity(bgr.getSubjectBirthCity());
        subject.setBirthDate(bgr.getSubjectBirthDate());
        if (subject instanceof Adult) {
            ((Adult)subject).setEmail(bgr.getSubjectEmail());
        }
    }

    @Override
    public void onApplicationEvent(LibreDematEvent e) {
        if (e instanceof RequestEvent) {
            RequestEvent event = (RequestEvent)e;
            if (RequestEvent.EVENT_TYPE.REQUEST_CLONED.equals(event.getEvent())
                && accept(event.getRequest())) {
                BafaGrantRequest request = (BafaGrantRequest) event.getRequest();
                request.setAccountHolderEdemandeId(null);
                request.setEdemandeId(null);
            }
        }
    }
}
