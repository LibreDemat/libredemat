package org.libredemat.service.request.school.external;

import java.util.Map;
import org.libredemat.exception.CvqException;

public interface IRemoteCirilSchoolsProvider {
    public Map<String, String> loadChildSchools(Map<String, Object> params) throws CvqException;
}
