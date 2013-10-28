package org.libredemat.service.request;

import org.apache.xmlbeans.XmlObject;
import org.libredemat.business.request.Request;
import org.libredemat.exception.CvqException;


public interface IRequestExportService {
    
    XmlObject fillRequestXml(Request request) throws CvqException;
}
