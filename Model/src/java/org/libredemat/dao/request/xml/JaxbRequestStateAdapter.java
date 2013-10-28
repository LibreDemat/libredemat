package org.libredemat.dao.request.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.libredemat.business.request.RequestState;


public class JaxbRequestStateAdapter extends XmlAdapter<String, RequestState> {

    @Override
    public RequestState unmarshal(String state) {
        return RequestState.forString(state);
    }

    @Override
    public String marshal(RequestState state) {
        return state.toString();
    }
}
