package org.libredemat.plugins.externalservices.cardfive.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.jdom.Document;
import org.libredemat.plugins.externalservices.cardfive.ftp.SendByFtp;
import org.libredemat.business.payment.ExternalAccountItem;
import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.PurchaseItem;
import org.libredemat.xml.request.parking.ParkCardRequestDocument.ParkCardRequest;
import org.libredemat.exception.CvqConfigurationException;
import org.libredemat.exception.CvqException;
import org.libredemat.external.ExternalServiceBean;
import org.libredemat.external.impl.ExternalProviderServiceAdapter;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
//import org.libredemat.business.external.TraceStatusEnum;
//import org.libredemat.xml.request.parking.ParkCardRequestDocument.ParkCardRequest;

public class CardFiveService extends ExternalProviderServiceAdapter
{
	private static Logger logger = Logger.getLogger(CardFiveService.class);
	private String label;
	private static final String FTP_SERVER = "FtpServer";
	private static final String FTP_PORT = "FtpPort";
	private static final String FTP_USER_NAME = "FtpUserName";
	private static final String FTP_PASSWORD = "FtpPassword";
	private static final String FTP_FOLDER = "FtpFolder";
	private static final String SERVER_NAME = "ServerName";
	private static final String ACCOUNT_HOLDER_TRACE_SUBKEY = "accountHolder";
	private String ftpServer;
	private String ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private String ftpFolder;
	private String serverName;
	private ILocalAuthorityRegistry localAuthorityRegistry;
	
	@Override
	public String sendRequest(XmlObject requestXml) throws CvqException
	{
		String message = null;
		if (requestXml instanceof ParkCardRequest)
		{
			String path = localAuthorityRegistry.getAssetsBase()
					+ localAuthorityRegistry.getLocalAuthorityByServerName(serverName).getName() + "/temp/";
			ParkCardRequest pcr = (ParkCardRequest) requestXml;
			logger.debug("CardFive sendRequest n° : " + pcr.getId());
			Long requestId = pcr.getId();
			SendByFtp sbftp = new SendByFtp(ftpServer, ftpUserName, ftpPassword, ftpFolder, ftpPort, requestXml, requestId, path);
			message = sbftp.getSendIt();
		}
		else 
		{
			return "NC";
		}
		return message;
	}
	
	@Override
	public Map<Date, String> getConsumptions(Long key, Date dateFrom, Date dateTo) throws CvqException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, List<ExternalAccountItem>> getAccountsByHomeFolder(Long homeFolderId,
			String externalHomeFolderId, String externalId) throws CvqException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void loadDepositAccountDetails(ExternalDepositAccountItem edai) throws CvqException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void loadInvoiceDetails(ExternalInvoiceItem eii) throws CvqException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void creditHomeFolderAccounts(Collection<PurchaseItem> purchaseItems, String cvqReference,
			String bankReference, Long homeFolderId, String externalHomeFolderId, String externalId, Date validationDate)
			throws CvqException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void checkConfiguration(ExternalServiceBean externalServiceBean, String localAuthorityName)
			throws CvqConfigurationException
	{
		setFtpServer((String) externalServiceBean.getProperty(FTP_SERVER));
		setFtpPort((String) externalServiceBean.getProperty(FTP_PORT));
		setFtpUserName((String) externalServiceBean.getProperty(FTP_USER_NAME));
		setFtpPassword((String) externalServiceBean.getProperty(FTP_PASSWORD));
		setFtpFolder((String) externalServiceBean.getProperty(FTP_FOLDER));
		setServerName((String) externalServiceBean.getProperty(SERVER_NAME));
		if (ftpServer == null) throw new CvqConfigurationException("Missing " + FTP_SERVER + " configuration parameter");
	}
	
	@Override
	public String helloWorld() throws CvqException
	{
		return null;
	}
	
	@Override
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	@Override
	public boolean supportsConsumptions()
	{
		return false;
	}
	
	@Override
	public boolean handlesTraces()
	{
		return false;
	}
	
	@Override
	public List<String> checkExternalReferential(XmlObject requestXml)
	{
		return Collections.emptyList();
	}
	
	@Override
	public Map<String, Object> loadExternalInformations(XmlObject requestXml) throws CvqException
	{
		return Collections.emptyMap();
	}
	
	public String getFtpServer()
	{
		return ftpServer;
	}
	
	public void setFtpServer(String ftpServer)
	{
		this.ftpServer = ftpServer;
	}
	
	public String getFtpPort()
	{
		return ftpPort;
	}
	
	public void setFtpPort(String ftpPort)
	{
		this.ftpPort = ftpPort;
	}
	
	public String getFtpUserName()
	{
		return ftpUserName;
	}
	
	public void setFtpUserName(String ftpUserName)
	{
		this.ftpUserName = ftpUserName;
	}
	
	public String getFtpPassword()
	{
		return ftpPassword;
	}
	
	public void setFtpPassword(String ftpPassword)
	{
		this.ftpPassword = ftpPassword;
	}
	
	public String getFtpFolder()
	{
		return ftpFolder;
	}
	
	public void setFtpFolder(String ftpFolder)
	{
		this.ftpFolder = ftpFolder;
	}
	
	public String getServerName()
	{
		return serverName;
	}
	
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	
	public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry)
	{
		this.localAuthorityRegistry = localAuthorityRegistry;
	}
}
