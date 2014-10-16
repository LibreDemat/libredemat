package org.libredemat.plugins.externalservices.cirilnetenfance.util;

import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import enfanceServicesEnfance.ExternalDocumentReponseDocument;
import enfanceServicesEnfance.GetExternalDocumentDocument1;
import enfanceServicesEnfance.GetExternalDocumentDocument1.GetExternalDocument;

public class TimeOut
{
	Thread interrupter;
	Thread target;
	long timeout;
	boolean success = true;
	boolean forceStop;
	private WebServiceTemplate cirilClientService;
	private String endPoint;
	public boolean running = false;

	/**
	 * 
	 * @param target
	 *            The Runnable target to be executed
	 * @param timeout
	 *            The time in milliseconds before target will be interrupted or
	 *            stopped
	 * @param forceStop
	 *            If true, will Thread.stop() this target instead of just
	 *            interrupt()
	 */
	public TimeOut(String endPoint, WebServiceTemplate cirilClientService, long timeout, boolean forceStop)
	{
		this.endPoint = endPoint;
		this.cirilClientService = cirilClientService;
		this.timeout = timeout;
		this.forceStop = forceStop;
		this.target = new Thread(new ServerStarted());
		this.interrupter = new Thread(new Interrupter());
	}

	public boolean execute() throws InterruptedException
	{
		// Start target and interrupter
		interrupter.start();
		Thread.sleep(200);
		target.start();
		Thread.sleep(200);
		while (running && success)
		{
			Thread.sleep(200);
		}
		interrupter.interrupt();
		System.out.println("End of ws return : " + success);
		return success; // status is set in the Interrupter inner class
	}

	private class ServerStarted implements Runnable
	{
		ServerStarted()
		{
		}

		@Override
		public void run()
		{
			try
			{
				System.out.println("Test call to ciril : " + endPoint);
				running = true;
				cirilClientService.setDefaultUri(endPoint);
				GetExternalDocumentDocument1 gedd = GetExternalDocumentDocument1.Factory.newInstance();
				GetExternalDocument ged = gedd.addNewGetExternalDocument();
				ged.setIdFolder(Long.valueOf(100L));
				ged.setExternalHomeFolderId(Long.valueOf("100") + "");
				ExternalDocumentReponseDocument result = (ExternalDocumentReponseDocument) cirilClientService
						.marshalSendAndReceive(gedd);
				if (result != null)
				{
					success = true;
				}
				else
				{
					success = false;
				}
				running = false;
			}
			catch (XmlMappingException e)
			{
				success = true;
				running = false;
			}
			catch (WebServiceClientException e)
			{
				success = true;
				running = false;
			}
		}
	}

	private class Interrupter implements Runnable
	{
		Interrupter()
		{
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run()
		{
			try
			{
				System.out.println("Start timeout of ws");
				Thread.sleep(timeout);
				target.interrupt();
				if (forceStop) target.stop();
				System.out.println("done the timeout of ws");
				success = false;
			}
			catch (InterruptedException e)
			{
				System.out.println("done the timeout of ws " + e.getMessage());
				success = false;
			}
		}
	}
}
