package org.libredemat.service.users;

import java.io.IOException;
import java.util.List;

import org.libredemat.business.users.HomeFolder;
import org.libredemat.business.users.Individual;
import org.libredemat.exception.CvqException;

public interface IUserSynchronisationService {
    public void synchroniseAll(List<String> servicesLabel, String email) throws CvqException, IOException;

    public void synchronise(List<String> servicesLabel, Individual individual) throws CvqException;
}
