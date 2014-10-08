package org.libredemat.exception;

/**
 * Hack inexine : permet de gérer les Exceptions sur le payment
 * 
 * @author pontfort
 * 
 */
public class IXENoBrokerFindException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String i18nKey;
	private String[] i18nArgs;
	
	public IXENoBrokerFindException(final String i18nKey) {
        this(i18nKey, null);
    }
	
	public IXENoBrokerFindException(String i18nKey, String[] i18nArgs)
	{
		super(i18nKey);
		this.i18nKey = i18nKey;
		this.i18nArgs = i18nArgs;
	}
	
	public String getI18nKey()
	{
		return i18nKey;
	}
	
	public void setI18nKey(String key)
	{
		this.i18nKey = key;
	}
	
	public String[] getI18nArgs()
	{
		return this.i18nArgs;
	}
	
	public void setI18nArgs(String[] args)
	{
		this.i18nArgs = args;
	}
}
