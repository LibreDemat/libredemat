package fr.cg95.cvq.dao.document.hibernate;

import java.util.List;

import org.hibernate.Criteria;

import fr.cg95.cvq.business.document.DocumentType;
import fr.cg95.cvq.dao.document.IDocumentTypeDAO;
import fr.cg95.cvq.dao.jpa.JpaTemplate;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.util.Critere;
import java.math.BigInteger;
import fr.cg95.cvq.dao.jpa.JpaUtil;


/**
 * Implementation of the {@link IDocumentTypeDAO} interface.
 * 
 * @author bor@zenexity.fr
 */
public class DocumentTypeDAO extends JpaTemplate<DocumentType,Long> implements IDocumentTypeDAO {

    public DocumentType findByType(final Integer typeId) {
        Criteria crit = HibernateUtil.getSession().createCriteria(DocumentType.class);
        crit.add(Critere.compose("type", typeId, Critere.EQUALS));
        return (DocumentType) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<DocumentType> listAll() {
        return (List<DocumentType>)HibernateUtil.getSession()
            .createQuery("from DocumentType as documentType").list();
    }

    public Integer getNextTypeId() {
        String sql ="SELECT max(type)+1 FROM document_type";
        return (Integer)HibernateUtil.getSession().createSQLQuery(sql).uniqueResult();
    }

    public Boolean isRequiredByARequest(Long documentTypeId) {
        return ((BigInteger)JpaUtil.getEntityManager()
                .createNativeQuery("SELECT count(*) FROM requirement WHERE document_type_id = :doc_id")
                .setParameter("doc_id", documentTypeId)
                .getSingleResult()).intValue() != 0;
    }

    public Boolean isUsedInARequest(Long documentTypeId) {
        return ((BigInteger)JpaUtil.getEntityManager()
                .createNativeQuery("SELECT count(*) FROM document WHERE document_type_id = :doc_id")
                .setParameter("doc_id", documentTypeId)
                .getSingleResult()).intValue() != 0;
    }

    public Boolean nameAlreadyInUse(String name) {
        return ((BigInteger)JpaUtil.getEntityManager()
                .createNativeQuery("SELECT count(document_type.name) FROM document_type WHERE lower(name) = lower(:name)")
                .setParameter("name", name)
                .getSingleResult()).intValue() != 0;
    }
}
