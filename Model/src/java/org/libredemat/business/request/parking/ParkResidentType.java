package org.libredemat.business.request.parking;

/**
 * Generated class file, do not edit !
 */
public enum ParkResidentType
{
	BORDERRESIDENT("borderresident"), RESIDENT("cityresident"), FOREIGNER("foreigner");
	/**
	 * only for backward use ParkResidentType.values() instead
	 * 
	 * @deprecated only for backward
	 */
	@Deprecated
	public static ParkResidentType[] allParkResidentTypes = ParkResidentType.values();
	private String legacyLabel;

	private ParkResidentType(String legacyLabel)
	{
		this.legacyLabel = legacyLabel;
	}

	public String getLegacyLabel()
	{
		return legacyLabel;
	}

	public static ParkResidentType getDefaultParkResidentType()
	{
		return null;
	}

	/**
	 * @deprecated use valueOf instead. Watchout! you must provid something of
	 *             ParkResidentType.something not the value of the name
	 *             attribut.
	 */
	public static ParkResidentType forString(final String enumAsString)
	{
		return ParkResidentType.valueOf(enumAsString);
	}
}
