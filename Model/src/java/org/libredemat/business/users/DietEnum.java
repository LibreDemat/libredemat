/**
 * 
 */
package org.libredemat.business.users;

import java.util.HashMap;
import java.util.Map;
import org.libredemat.security.SecurityContext;

/**
 * Régimes alimentaires
 * 
 * @author Inexine : Frederic Fabre
 * 
 */
public enum DietEnum
{
	SANS_PORC(""), SANS_VIANDE(""), PRATIQUE_AUTRE(""), SANS_SUCRE(""), SANS_OEUFS(""), SANS_LAIT(""), SANS_ARACHIDE(""), SANS_GLUTEN(
			""), SANS_COQUE(""), SANS_POISSON(""), ALLERGIE_AUTRE(""), SANS_POISS(""), PAI(""), ALLERGIE(""), ALLERGIE_LEGUMINEUX("");
	private String name;

	public String getName()
	{
		return name;
	}

	private DietEnum(String name)
	{
		if (name != null && !name.trim().equals(""))
			this.name = name;
		else this.name = this.name();
	}

	@Override
	public String toString()
	{
		return this.name();
	}

	/** map permettant de récupérer un diet avec son libellé */
	private static final Map<String, DietEnum> mapDiets = new HashMap<String, DietEnum>();
	static
	{
		for (DietEnum diet : values())
		{
			mapDiets.put(diet.toString(), diet);
		}
	}

	/**
	 * private List<DietEnum> values() { List<DietEnum> diets = new
	 * ArrayList<DietEnum>(); if
	 * (SecurityContext.getCurrentConfigurationBean().getDietsEnumeration() !=
	 * null) { HashMap<String, String> fields =
	 * SecurityContext.getCurrentConfigurationBean().getDietsEnumeration(); for
	 * (String field : fields.keySet()) { diets.add(new DietEnum(field,
	 * field.get(field))) } } return diets; }
	 */
	/**
	 * Récupération de l'instance
	 * 
	 * @param dietLibelle
	 *            : libellé du régime par ex : "Sans poisson"
	 * @return retourne l'enum
	 * 
	 */
	public static DietEnum fromDietLibelle(String dietLibelle)
	{
		final DietEnum value = mapDiets.get(dietLibelle);
		if (value != null) { return value; }
		throw new IllegalArgumentException("régime inconnu : " + dietLibelle);
	}

	public static String toDietLibelle(DietEnum dietLibelle)
	{
		Map<String, String> fields = SecurityContext.getCurrentConfigurationBean().getDietsEnumeration();
		for (String field : fields.keySet())
		{
			if (field.equals(dietLibelle.name())) return fields.get(field);
		}
		return null;
	}
}
