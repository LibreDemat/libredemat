package org.libredemat.util.admin;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.libredemat.business.document.ContentType;
import org.libredemat.business.document.DocumentBinary;
import org.libredemat.dao.document.IDocumentDAO;
import org.libredemat.dao.jpa.IGenericDAO;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.service.document.IDocumentService;
import org.libredemat.service.document.impl.DocumentService;
import org.libredemat.service.document.impl.MimeUtil;
import org.springframework.aop.framework.Advised;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zdb.core.Store;

public class DocumentToZDBExtractor {

    public ILocalAuthorityRegistry localAuthorityRegistry;

    public IDocumentService documentService;
    public IDocumentDAO documentDAO;
    public IGenericDAO genericDAO;

    public static void main(final String[] args) {
        Logger.getRootLogger().setLevel(Level.OFF);
        ClassPathXmlApplicationContext cpxa = SpringApplicationContextLoader.loadContext(args[0]);
        DocumentToZDBExtractor extractor = new DocumentToZDBExtractor();
        extractor.localAuthorityRegistry = cpxa.getBean("localAuthorityRegistry", ILocalAuthorityRegistry.class);
        extractor.documentService = cpxa.getBean("documentService", IDocumentService.class);
        extractor.documentDAO = cpxa.getBean("documentDAO", IDocumentDAO.class);
        extractor.genericDAO = cpxa.getBean("genericDAO", IGenericDAO.class);
        extractor.localAuthorityRegistry.browseAndCallback(extractor, "extract", null);
        System.exit(0);
    }

    public void extract()
        throws NoSuchMethodException {
        Method method = DocumentService.class.getDeclaredMethod("createPreview", DocumentBinary.class);
        method.setAccessible(true);
        Store.init(new File(localAuthorityRegistry.getAssetsBase() + SecurityContext.getCurrentSite().getName(), "zdb"));
        DocumentBinary docBin;
        byte[] data;
        for (Long id : (List<Long>) JpaUtil.getEntityManager().createQuery("select id from DocumentBinary").getResultList()) {
            docBin = (DocumentBinary) genericDAO.findById(DocumentBinary.class, id.longValue());
            data = (byte[]) JpaUtil.getEntityManager()
                .createNativeQuery("select data from document_binary where id = :id")
                    .setParameter("id", id).getSingleResult();
            if (data == null) {
                docBin.setContentType(ContentType.OCTET_STREAM);
            } else {
                docBin.setContentType(ContentType.forString(
                    MimeUtil.getMostSpecificMimeType(new MimeUtil().getMimeTypes(data)).toString()));
            }
            docBin.setData(data);
            try {
                method.invoke(((Advised)documentService).getTargetSource().getTarget(), docBin);
            } catch (Exception e) {
                // whatever...
            }
            genericDAO.update(docBin);
            JpaUtil.closeAndReOpen(false);
        }
    }
}
