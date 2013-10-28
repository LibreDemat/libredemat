package org.libredemat.plugins.externalservices.restgenericexternalservice.service;

import org.libredemat.util.web.WS.HttpResponse;

public class RestGenericUtil {

    private RestGenericUtil() {}

    public static String externalIdFromIISString(HttpResponse response) {
        return response.getString().replaceAll("<.*?>", "");
    }

}
