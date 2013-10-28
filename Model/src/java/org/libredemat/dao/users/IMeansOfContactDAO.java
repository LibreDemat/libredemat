package org.libredemat.dao.users;

import java.util.List;

import org.libredemat.business.users.MeansOfContact;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.dao.jpa.IJpaTemplate;


public interface IMeansOfContactDAO extends IJpaTemplate<MeansOfContact,Long> {

    MeansOfContact findByType(MeansOfContactEnum type);

    List<MeansOfContact> listAllEnabled();

    List<MeansOfContact> listAll();
}
