package org.libredemat.plugins.externalservices.cardfive.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.XmlObject;
import org.libredemat.exception.CvqException;
import org.libredemat.xml.request.parking.ParkCardRequestDocument.ParkCardRequest;
import org.libredemat.xml.request.parking.ParkImmatriculationType;

public class SendByFtp
{
	private static Logger logger = Logger.getLogger(SendByFtp.class);
	private static String server;
	private static String userName;
	private static String password;
	private static String folder;
	private static String port;
	private static XmlObject request;
	private static Long requestId;
	private static String path;

	public SendByFtp(String server, String userName, String password, String folder, String port, XmlObject request,
			Long requestId, String path)
	{
		super();
		this.server = server;
		this.userName = userName;
		this.password = password;
		this.folder = folder;
		this.port = port;
		this.request = request;
		this.requestId = requestId;
		this.path = path;
	}

	// method to send file on ftp - specific ParkCard
	public String getSendIt() throws CvqException
	{
		// chaine retournée qui représente l'externalId - exploité par le
		// service appelant et le met à jour s'il est retourné.
		// Dans notre cas, ce n'est pa sutile. Il n'y a pas d'identifiant pour
		// le ParkCard.
		String externalId = "";
		try
		{
			FTPClient ftp = new FTPClient();
			if (port.equals(null))
			{
				ftp.connect(server);
			}
			else
			{
				ftp.connect(server, Integer.valueOf(port));
			}
			ftp.login(userName, password);
			logger.debug("We are trying to connect to : " + server + " and it response is : "
					+ ftp.getReplyStrings().toString());
			if (folder != null && !folder.trim().equals("")) ftp.changeWorkingDirectory(folder);
			ParkCardRequest pcr = (ParkCardRequest) request;
			String name = "ParkCard_" + String.valueOf(requestId) + ".xls"; // desing
																			// name
																			// of
																			// xsl
			String filePath = path + name;
			Workbook wb = new HSSFWorkbook(); // create workbook
			Sheet sh = wb.createSheet("Carte");
			CreationHelper createHelper = wb.getCreationHelper();
			createRow(sh, 0, "TYPE DE CARTE", "NOM", "PRENOM", "ADRESSE", "N° de la demande", "N° IMMATRICULATION",
					"DATE DE VALIDITE", "DATE DE LA DEMANDE", createHelper);
			Integer i = 1;
			SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, String> cardType = new HashMap<String, String>();
			cardType.put("borderresident", "Riverain");
			cardType.put("cityresident", "Résident");
			cardType.put("foreigner", "Hors commune");
			String address = "NC";
			if (pcr.getSubjectAddress() != null)
			{
				address = pcr.getSubjectAddress().getStreetNumber() + " " + pcr.getSubjectAddress().getStreetName();
			}
			for (ParkImmatriculationType pi : pcr.getParkImatriculationArray())
			{
				createRow(sh, i, cardType.get(pcr.getParkResident()), pcr.getSubject().getAdult().getLastName(), pcr
						.getSubject().getAdult().getFirstName(), address, String.valueOf(pcr.getId()),
						pi.getImmatriculation(), getValidityDate(),
						simpleFormat.format(pcr.getCreationDate().getTime()), createHelper);
				i++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			wb.write(fileOutputStream);
			// File file = new File(filePath);
			// FileInputStream fileInputStream = new FileInputStream(file);
			InputStream theRequest = new FileInputStream(filePath);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// ftp.storeFileStream(filePath);
			ftp.storeFile(name, theRequest);
			fileOutputStream.close();
			fileOutputStream.flush();
			// close connection
			ftp.logout();
			ftp.disconnect();
		}
		catch (Exception e)
		{
			logger.error("There is a probleme when sending file to ftp");
			logger.error(e.getMessage());
			throw new CvqException("FTP - Une erreur s'est produite : " + e.getMessage());
		}
		return externalId;
	}

	private static void createRow(Sheet sh, Integer u, String col1, String col2, String col3, String col4, String col5,
			String col6, String col7, String col8, CreationHelper createHelper)
	{
		Row titleRow = sh.createRow(u);
		titleRow.createCell(0).setCellValue(createHelper.createRichTextString(col1));
		titleRow.createCell(1).setCellValue(createHelper.createRichTextString(col2));
		titleRow.createCell(2).setCellValue(createHelper.createRichTextString(col3));
		titleRow.createCell(3).setCellValue(createHelper.createRichTextString(col4));
		titleRow.createCell(4).setCellValue(createHelper.createRichTextString(col5));
		titleRow.createCell(5).setCellValue(createHelper.createRichTextString(col6));
		titleRow.createCell(6).setCellValue(createHelper.createRichTextString(col7));
		titleRow.createCell(7).setCellValue(createHelper.createRichTextString(col8));
	}

    @SuppressWarnings("deprecation")
	private static String getValidityDate()
	{
		// String dateValidity = "30/09/2014";
		Date date = new Date(); // 21/08/2013
		Calendar cal = Calendar.getInstance();
		cal.set(date.getYear() + 1900, 7, 1); // 1/07/2013
		Date dateValidity = cal.getTime();
		if (date.after(dateValidity))
		{
			cal.set(date.getYear() + 1901, 8, 30); // 30/09/2014
			dateValidity = cal.getTime();
		}
		else
		{
			cal.set(date.getYear() + 1900, 8, 30); // 30/09/2013
			dateValidity = cal.getTime();
		}
		return new SimpleDateFormat("dd/MM/yyyy").format(dateValidity);
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public static String getPath()
	{
		return path;
	}

	public static void setPath(String path)
	{
		SendByFtp.path = path;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFolder()
	{
		return folder;
	}

	public void setFolder(String folder)
	{
		this.folder = folder;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public XmlObject getRequest()
	{
		return request;
	}

	public void setRequest(XmlObject request)
	{
		this.request = request;
	}

	public Long getRequestId()
	{
		return requestId;
	}

	public void setRequestId(Long requestId)
	{
		this.requestId = requestId;
	}
}
