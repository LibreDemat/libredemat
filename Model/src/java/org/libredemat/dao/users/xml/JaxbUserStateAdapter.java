package org.libredemat.dao.users.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.libredemat.business.users.UserState;


public class JaxbUserStateAdapter extends XmlAdapter<String, UserState> {

    @Override
    public UserState unmarshal(String state) {
        return UserState.forString(state);
    }

    @Override
    public String marshal(UserState state) {
        return state.toString();
    }
}
