package org.libredemat.service.document.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.libredemat.business.document.DocumentType;
import org.libredemat.dao.document.IDocumentTypeDAO;
import org.libredemat.security.SecurityContext;
import org.libredemat.security.annotation.Context;
import org.libredemat.security.annotation.ContextType;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.document.IDocumentTypeService;


public class DocumentTypeService
    implements IDocumentTypeService, ILocalAuthorityLifecycleAware {

    static Logger logger = Logger.getLogger(DocumentTypeService.class);

    protected IDocumentTypeDAO documentTypeDAO;

    private DocumentBootstrapper documentBootstrapper;

    @Context(types = {ContextType.SUPER_ADMIN})
    private void initSampleDocumentTypes() {
        logger.debug("initSampleDocumentTypes() init for " 
            + SecurityContext.getCurrentSite().getName());
        documentBootstrapper.bootstrapForCurrentLocalAuthority();
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN})
    public void addLocalAuthority(String localAuthorityName) {
        initSampleDocumentTypes();
    }

    @Override
    @Context(types = {ContextType.SUPER_ADMIN})
    public void removeLocalAuthority(String localAuthorityName) {
    }

    @Override
    public DocumentType getDocumentTypeByType(final Integer id) {
        return documentTypeDAO.findByType(id);
    }

    @Override
    public DocumentType getDocumentTypeById(Long id) {
        return documentTypeDAO.findById(id);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeDAO.listAll();
    }

    public void setDocumentTypeDAO(final IDocumentTypeDAO documentTypeDAO) {
        this.documentTypeDAO = documentTypeDAO;
    }
    
    public void setDocumentBootstrapper(DocumentBootstrapper documentBootstrapper) {
        this.documentBootstrapper = documentBootstrapper;
    }

    @Override
    public DocumentType create(DocumentType type) {
        return this.documentTypeDAO.create(type);
    }

    @Override
    public void update(DocumentType type) {
        this.documentTypeDAO.update(type);
    }

    @Override
    public void delete(DocumentType type) {
        this.documentTypeDAO.delete(type);
    }

    @Override
    public Integer getNextTypeId() {
        return this.documentTypeDAO.getNextTypeId();
    }

    @Override
    public Boolean isRequiredByARequest(Long documentTypeId) {
        return this.documentTypeDAO.isRequiredByARequest(documentTypeId);
    }
    @Override
    public Boolean isUsedInARequest(Long documentTypeId) {
        return this.documentTypeDAO.isUsedInARequest(documentTypeId);
    }

    @Override
    public Boolean nameAlreadyInUse(String name) {
        return this.documentTypeDAO.nameAlreadyInUse(name);
    }
}
