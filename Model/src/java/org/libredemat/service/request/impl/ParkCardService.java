package org.libredemat.service.request.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import au.com.bytecode.opencsv.CSVReader;
import org.libredemat.business.request.parking.config.StreetBorderReferential;
import org.libredemat.dao.jpa.JpaUtil;
import org.libredemat.dao.request.IParkCardDAO;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.exception.CvqParkCardException;
import org.libredemat.service.request.IParkCardService;

public class ParkCardService implements IParkCardService
{
	private IParkCardDAO parkCardDAO;
	private static Logger logger = Logger.getLogger(ParkCardService.class);
	
	@Override
	public StreetBorderReferential createStreetBorderReferential(StreetBorderReferential streetBorderReferential)
			throws CvqException
	{
		if (streetBorderReferential == null) throw new CvqException("There is no street to add");
		return parkCardDAO.create(streetBorderReferential);
	}
	
	@Override
	public void updateStreetBorderReferential(StreetBorderReferential streetBorderReferential)
	{
		parkCardDAO.update(streetBorderReferential);
	}
	
	@Override
	public void deleteStreetBorderReferential(Long id) throws CvqObjectNotFoundException
	{
		parkCardDAO.delete(getById(id));
	}
	
	@Override
	public List<StreetBorderReferential> getAllStreets() throws CvqException
	{
		return parkCardDAO.listAllStreets();
	}
	
	@Override
	public boolean isThereStreets() throws CvqException
	{
		boolean result = false;
		if (getAllStreets().isEmpty()) result = true;
		return result;
	}
	
	@Override
	public StreetBorderReferential getById(Long id) throws CvqObjectNotFoundException
	{
		return (StreetBorderReferential) parkCardDAO.findById(id);
	}
	
	@Override
	public Map<String, Integer> importStreets(byte[] streets) throws CvqParkCardException
	{
		Map<String, Integer> report = new HashMap<String, Integer>();
		report.put("street", 0);
		Session hibernateSession = ((HibernateEntityManager) JpaUtil.getEntityManager()).getSession();
		try
		{
			// if (streets == null)
			CSVReader csvReader = new CSVReader(new StringReader(new String(streets)), ';');
			if (csvReader != null) parkCardDAO.deleteAll(hibernateSession);
			String[] line;
			while ((line = csvReader.readNext()) != null)
			{
				if (!line[0].trim().equals("") && !line[1].trim().equals("") && !line[2].trim().equals(""))
				{
					StreetBorderReferential streetBorderReferential = new StreetBorderReferential(line[0].trim(), line[1].trim(),
							line[2].trim());
					parkCardDAO.create(streetBorderReferential);
					hibernateSession.flush();
				}
			}
			csvReader.close();
		}
		catch (IOException io)
		{
			logger.error(io.getMessage());
			hibernateSession.getTransaction().rollback();
			throw new CvqParkCardException("requestType.configuration.parkCard.error.importstreetfailled");
		}
		catch (NumberFormatException nfe)
		{
			logger.error(nfe.getMessage());
			hibernateSession.getTransaction().rollback();
			throw new CvqParkCardException("requestType.configuration.parkCard.error.importstreetfailled");
		}
		catch (IndexOutOfBoundsException iobe)
		{
			logger.error(iobe.getMessage());
			hibernateSession.getTransaction().rollback();
			throw new CvqParkCardException("requestType.configuration.parkCard.error.importstreetfailled");
		}
		return report;
	}
	
	public void setParkCardDAO(IParkCardDAO parkCardDAO)
	{
		this.parkCardDAO = parkCardDAO;
	}
}
