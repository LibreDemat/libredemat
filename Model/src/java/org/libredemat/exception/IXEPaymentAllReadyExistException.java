package org.libredemat.exception;

/**
 * Hack inexine : permet de gérer les Exceptions sur le payment
 * 
 * @author pontfort
 *
 */
public class IXEPaymentAllReadyExistException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IXEPaymentAllReadyExistException(String message)
	{
		super(message);
	}
	
}
