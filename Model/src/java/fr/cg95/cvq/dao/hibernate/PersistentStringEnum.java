package fr.cg95.cvq.dao.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;


/**
 * Provides a base class for persistable, type-safe, comparable,
 * and serializable enums persisted as strings.
 *
 * <p>Create a subclass of this class implementing the enumeration:
 * <pre>package com.foo;
 *
 * public final class Gender extends PersistentCharacterEnum {
 *  public static final Gender MALE = new Gender("male");
 *  public static final Gender FEMALE = new Gender("female");
 *  public static final Gender UNDETERMINED = new Gender("undetermined");
 *
 *  public Gender() {}
 *
 *  private Gender(String name) {
 *   super(name);
 *  }
 * }
 * </pre>
 * Note that a no-op default constructor must be provided.</p>
 *
 * <p>Use this enumeration in your mapping file as:
 * <pre>&lt;property name="gender" type="com.foo.Gender"&gt;</pre></p>
 *
 * <p><code>
 * $Id: PersistentStringEnum.java 3557 2007-01-11 12:04:00Z borihuela $
 * </pre></p>
 *
 * @version $Revision: 3557 $
 * @author &Oslash;rjan Nygaard Austvold
 */
public abstract class PersistentStringEnum extends PersistentEnum {
    /**
     * Default constructor.  Hibernate need the default constructor
     * to retrieve an instance of the enum from a JDBC resultset.
     * The instance will be resolved to the correct enum instance
     * in {@link #nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)}.
     */
    protected PersistentStringEnum() {
        // no-op -- instance will be tossed away once the equivalent enum is found.
    }


    /**
     * Constructs an enum with name as the persistent representation.
     *
     * @param name name of the enum.
     */
    protected PersistentStringEnum(String name) {
        super(name, name);
    }


    /**
     * Constructs an enum with the given name and persistent representation.
     *
     * @param name name of enum.
     * @param persistentString persistent representation of the enum.
     */
    protected PersistentStringEnum(String name, String persistentString) {
        super(name, persistentString);
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (!(other instanceof PersistentStringEnum))
        		return -1;
        String valueToCompareWith = ((String) ((PersistentStringEnum) other).getEnumCode());
        return ((String) getEnumCode()).compareTo(valueToCompareWith);
    }


    /**
     * @see PersistentEnum#getNullableType()
     */
    protected NullableType getNullableType() {
        return Hibernate.STRING;
    }
}
