package org.libredemat.dao.request.hibernate;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.libredemat.business.request.parking.config.StreetBorderReferential;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.request.IParkCardDAO;

public class ParkCardDAO extends JpaTemplate<StreetBorderReferential, Long> implements
		IParkCardDAO
{
	@Override
	public StreetBorderReferential findByStreetLabel(String label)
	{
		Query query = HibernateUtil
				.getSession()
				.createQuery(
						"from StreetBorderReferential as sbr where sbr.streetLabel = :label ")
						.setString("label", label);
		return (StreetBorderReferential) query.uniqueResult();
	}

	public void deleteAll(Session session)
	{
		session.createQuery("delete from StreetBorderReferential").executeUpdate();
	}
	
	@Override
	public List<StreetBorderReferential> listAllStreets()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("from StreetBorderReferential as sbr").append(
				" order by sbr.streetLabel asc ");
		return HibernateUtil.getSession().createQuery(sb.toString()).list();
	}
	
	@Override
	public StreetBorderReferential findById(Long id)
	{
		Query query = HibernateUtil.getSession()
				.createQuery("from StreetBorderReferential as sbr where sbr.id = :id ")
				.setLong("id", id);
		return (StreetBorderReferential) query.uniqueResult();
	}
}
