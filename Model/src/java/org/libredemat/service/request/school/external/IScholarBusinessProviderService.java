package org.libredemat.service.request.school.external;

import java.util.Map;

import org.libredemat.business.request.Request;
import org.libredemat.business.users.Child;


public interface IScholarBusinessProviderService {

    Map<String, Map<String, String>> getSchools(Child child);

    Map<String, String> getHolidayCamps(Request request, Child child);

    Map<String, String> getLeisureCenters(Request request, Child child);

    Map<String, String> getTransportLines(Request request, Child child);

    Map<String, String> getTransportStops(Request request, Child child, String lineId);

    Map<String, String> getLeisureCenterTransportLines(Request request, Child child);

    Map<String, String> getLeisureCenterTransportStops(Request request, Child child, String lineId);
}
