package org.libredemat.service.users.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.libredemat.business.document.Document;
import org.libredemat.business.document.DocumentType;
import org.libredemat.business.users.GlobalHomeFolderConfiguration;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.dao.document.IDocumentDAO;
import org.libredemat.dao.document.IDocumentTypeDAO;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.users.IHomeFolderDAO;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextPrivilege;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.users.IHomeFolderDocumentService;


public class HomeFolderDocumentService implements IHomeFolderDocumentService {

    private IHomeFolderDAO homeFolderDAO;
    private IDocumentDAO documentDAO;
    private IGenericDAO genericDAO;
    private IDocumentTypeDAO documentTypeDAO;

    public void setHomeFolderDAO(IHomeFolderDAO homeFolderDAO) {
        this.homeFolderDAO = homeFolderDAO;
    }

    public void setDocumentDAO(IDocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public void setGenericDAO(IGenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public void setDocumentTypeDAO(IDocumentTypeDAO documentTypeDAO) {
        this.documentTypeDAO = documentTypeDAO;
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void link(Long homeFolderId, Long documentId) {
        link(homeFolderDAO.findById(homeFolderId), documentId);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void link(HomeFolder homeFolder, Long documentId) {
        Document document = documentDAO.findById(documentId);
        if (document != null && homeFolder != null) {
            if (document.getLinkedHomeFolder() != homeFolder) {
                document.setLinkedHomeFolder(homeFolder);
            }
            documentDAO.update(document);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void unlink(Long homeFolderId, Long documentId) {
        unlink(homeFolderDAO.findById(homeFolderId), documentId);
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.WRITE)
    public void unlink(HomeFolder homeFolder, Long documentId) {
        Document document = documentDAO.findById(documentId);
        if (document != null && homeFolder != null) {
            document.setLinkedHomeFolder(null);
            documentDAO.update(document);
        }
    }

    @Override
    @Context(types = {ContextType.ECITIZEN, ContextType.AGENT}, privilege = ContextPrivilege.READ)
    public List<Document> documentsLinkedToHomeFolder(Long homeFolderId, DocumentType documentType) {
        if (homeFolderId == null)
            return new ArrayList<Document>();
        return documentDAO.linkedToHomeFolder(homeFolderDAO.findById(homeFolderId), documentType);
    }

    private GlobalHomeFolderConfiguration getGlobalHomeFolderConfiguration() {
        GlobalHomeFolderConfiguration conf =
            genericDAO.simpleSelect(GlobalHomeFolderConfiguration.class).unique();

        return (conf != null) ? conf : new GlobalHomeFolderConfiguration();
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    public void wish(Long documentTypeId) {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();

        Set<DocumentType> wished = conf.getWishedDocumentTypes();
        wished.add(documentTypeDAO.findById(documentTypeId));
        conf.setWishedDocumentTypes(wished);

        genericDAO.update(conf);
    }

    @Override
    @Context(types = {ContextType.AGENT}, privilege = ContextPrivilege.MANAGE)
    public void unwish(Long documentTypeId) {
        GlobalHomeFolderConfiguration conf = getGlobalHomeFolderConfiguration();

        Set<DocumentType> wished = conf.getWishedDocumentTypes();
        wished.remove(documentTypeDAO.findById(documentTypeId));
        conf.setWishedDocumentTypes(wished);

        genericDAO.update(conf);
    }

    @Override
    public Set<DocumentType> wishedDocumentTypes() {
        return getGlobalHomeFolderConfiguration().getWishedDocumentTypes();
    }
}
