package cn.com.cs.common.enums.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;


@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumData implements Comparable, Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -966557831549601089L;

	/**
     * An empty <code>Map</code>, as JDK1.2 didn't have an empty map.
     */
	private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
    
    /**
     * <code>Map</code>, key of class name, value of <code>Entry</code>.
     */
    private static Map cEnumClasses
        // LANG-334: To avoid exposing a mutating map,
        // we copy it each time we add to it. This is cheaper than
        // using a synchronized map since we are almost entirely reads
        = new WeakHashMap();
    
    /**
     * The string representation of the Enum.
     */
    private final String iName;
    
    /**
     * The hashcode representation of the Enum.
     */
    private transient final int iHashCode;
    
    /**
     * The toString representation of the Enum.
     * @since 2.0
     */
    protected transient String iToString = null;
    
    /**
     * <p>Enable the iterator to retain the source code order.</p>
     */
    protected static class Entry {
        /**
         * Map of Enum name to Enum.
         */
        final Map map = new HashMap();
        /**
         * Map of Enum name to Enum.
         */
        final Map unmodifiableMap = Collections.unmodifiableMap(map);
        /**
         * List of Enums in source code order.
         */
        final List list = new ArrayList(25);
        /**
         * Map of Enum name to Enum.
         */
        final List unmodifiableList = Collections.unmodifiableList(list);

        /**
         * <p>Restrictive constructor.</p>
         */
        protected Entry() {
            super();
        }
    }

    /**
     * <p>Constructor to add a new named item to the enumeration.</p>
     *
     * @param name  the name of the enum object,
     *  must not be empty or <code>null</code>
     * @throws IllegalArgumentException if the name is <code>null</code>
     *  or an empty string
     * @throws IllegalArgumentException if the getEnumClass() method returns
     *  a null or invalid Class
     */
    protected EnumData(String name) {
        super();
        init(name);
        iName = name;
        iHashCode = 7 + getEnumClass().hashCode() + 3 * name.hashCode();
        // cannot create toString here as subclasses may want to include other data
    }
    

	protected EnumData() {
		super();
		iName = "";
        iHashCode = 7 + getEnumClass().hashCode() + 3 * iName.hashCode();
	}



	/**
     * Initializes the enumeration.
     * 
     * @param name  the enum name
     * @throws IllegalArgumentException if the name is null or empty or duplicate
     * @throws IllegalArgumentException if the enumClass is null or invalid
     */
	private void init(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("The Enum name must not be empty or null");
        }
        
        Class enumClass = getEnumClass();
        if (enumClass == null) {
            throw new IllegalArgumentException("getEnumClass() must not be null");
        }
        Class cls = getClass();
        boolean ok = false;
        while (cls != null && cls != Enum.class && cls != ValuedEnumData.class) {
            if (cls == enumClass) {
                ok = true;
                break;
            }
            cls = cls.getSuperclass();
        }
        if (ok == false) {
            throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
        }

        Entry entry;
        synchronized( Enum.class ) { // LANG-334
            // create entry
            entry = (Entry) cEnumClasses.get(enumClass);
            if (entry == null) {
                entry = createEntry(enumClass);
                Map myMap = new WeakHashMap( ); // we avoid the (Map) constructor to achieve JDK 1.2 support
                myMap.putAll( cEnumClasses );
                myMap.put(enumClass, entry);
                cEnumClasses = myMap;
            }
        }
        if (entry.map.containsKey(name)) {
            throw new IllegalArgumentException("The Enum name must be unique, '" + name + "' has already been added");
        }
        entry.map.put(name, this);
        entry.list.add(this);
    }

    /**
     * <p>Handle the deserialization of the class to ensure that multiple
     * copies are not wastefully created, or illegal enum types created.</p>
     *
     * @return the resolved object
     */
    protected Object readResolve() {
        Entry entry = (Entry) cEnumClasses.get(getEnumClass());
        if (entry == null) {
            return null;
        }
        return entry.map.get(getName());
    }
    
    //--------------------------------------------------------------------------------

    /**
     * <p>Gets an <code>Enum</code> object by class and name.</p>
     * 
     * @param enumClass  the class of the Enum to get, must not
     *  be <code>null</code>
     * @param name  the name of the <code>Enum</code> to get,
     *  may be <code>null</code>
     * @return the enum object, or <code>null</code> if the enum does not exist
     * @throws IllegalArgumentException if the enum class
     *  is <code>null</code>
     */
    protected static EnumData getEnum(Class enumClass, String name) {
        Entry entry = getEntry(enumClass);
        if (entry == null) {
            return null;
        }
        return (EnumData) entry.map.get(name);
    }

    /**
     * <p>Gets the <code>Map</code> of <code>Enum</code> objects by
     * name using the <code>Enum</code> class.</p>
     *
     * <p>If the requested class has no enum objects an empty
     * <code>Map</code> is returned.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get,
     *  must not be <code>null</code>
     * @return the enum object Map
     * @throws IllegalArgumentException if the enum class is <code>null</code>
     * @throws IllegalArgumentException if the enum class is not a subclass of Enum
     */
    protected static Map getEnumMap(Class enumClass) {
        Entry entry = getEntry(enumClass);
        if (entry == null) {
            return EMPTY_MAP;
        }
        return entry.unmodifiableMap;
    }

    /**
     * <p>Gets the <code>List</code> of <code>Enum</code> objects using the
     * <code>Enum</code> class.</p>
     *
     * <p>The list is in the order that the objects were created (source code order).
     * If the requested class has no enum objects an empty <code>List</code> is
     * returned.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get,
     *  must not be <code>null</code>
     * @return the enum object Map
     * @throws IllegalArgumentException if the enum class is <code>null</code>
     * @throws IllegalArgumentException if the enum class is not a subclass of Enum
     */
    protected static List getEnumList(Class enumClass) {
        Entry entry = getEntry(enumClass);
        if (entry == null) {
            return Collections.EMPTY_LIST;
        }
        return entry.unmodifiableList;
    }

    /**
     * <p>Gets an <code>Iterator</code> over the <code>Enum</code> objects in
     * an <code>Enum</code> class.</p>
     *
     * <p>The <code>Iterator</code> is in the order that the objects were
     * created (source code order). If the requested class has no enum
     * objects an empty <code>Iterator</code> is returned.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get,
     *  must not be <code>null</code>
     * @return an iterator of the Enum objects
     * @throws IllegalArgumentException if the enum class is <code>null</code>
     * @throws IllegalArgumentException if the enum class is not a subclass of Enum
     */
    protected static Iterator iterator(Class enumClass) {
        return EnumData.getEnumList(enumClass).iterator();
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Gets an <code>Entry</code> from the map of Enums.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get
     * @return the enum entry
     */
    private static Entry getEntry(Class enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        if (EnumData.class.isAssignableFrom(enumClass) == false) {
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        }
        Entry entry = (Entry) cEnumClasses.get(enumClass);

        if (entry == null) {
            try {
                // LANG-76 - try to force class initialization for JDK 1.5+
                Class.forName(enumClass.getName(), true, enumClass.getClassLoader());
                entry = (Entry) cEnumClasses.get(enumClass);
            } catch (Exception e) {
                // Ignore
            }
        }

        return entry;
    }
    
    /**
     * <p>Creates an <code>Entry</code> for storing the Enums.</p>
     *
     * <p>This accounts for subclassed Enums.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get
     * @return the enum entry
     */
    private static Entry createEntry(Class enumClass) {
        Entry entry = new Entry();
        Class cls = enumClass.getSuperclass();
        while (cls != null && cls != Enum.class && cls != ValuedEnumData.class) {
            Entry loopEntry = (Entry) cEnumClasses.get(cls);
            if (loopEntry != null) {
                entry.list.addAll(loopEntry.list);
                entry.map.putAll(loopEntry.map);
                break;  // stop here, as this will already have had superclasses added
            }
            cls = cls.getSuperclass();
        }
        return entry;
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Retrieve the name of this Enum item, set in the constructor.</p>
     * 
     * @return the <code>String</code> name of this Enum item
     */
    public final String getName() {
        return iName;
    }

    /**
     * <p>Retrieves the Class of this Enum item, set in the constructor.</p>
     * 
     * <p>This is normally the same as <code>getClass()</code>, but for
     * advanced Enums may be different. If overridden, it must return a
     * constant value.</p>
     * 
     * @return the <code>Class</code> of the enum
     * @since 2.0
     */
    public Class getEnumClass() {
        return getClass();
    }

    /**
     * <p>Tests for equality.</p>
     *
     * <p>Two Enum objects are considered equal
     * if they have the same class names and the same names.
     * Identity is tested for first, so this method usually runs fast.</p>
     * 
     * <p>If the parameter is in a different class loader than this instance,
     * reflection is used to compare the names.</p>
     *
     * @param other  the other object to compare for equality
     * @return <code>true</code> if the Enums are equal
     */
    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getClass() == this.getClass()) {
            // Ok to do a class cast to Enum here since the test above
            // guarantee both
            // classes are in the same class loader.
            return iName.equals(((EnumData) other).iName);
        } else {
            // This and other are in different class loaders, we must check indirectly
            if (other.getClass().getName().equals(this.getClass().getName()) == false) {
                return false;
            }
            return iName.equals( getNameInOtherClassLoader(other) );
        }
    }
    
    /**
     * <p>Returns a suitable hashCode for the enumeration.</p>
     *
     * @return a hashcode based on the name
     */
    public final int hashCode() {
        return iHashCode;
    }

    /**
     * <p>Tests for order.</p>
     *
     * <p>The default ordering is alphabetic by name, but this
     * can be overridden by subclasses.</p>
     * 
     * <p>If the parameter is in a different class loader than this instance,
     * reflection is used to compare the names.</p>
     *
     * @see java.lang.Comparable#compareTo(Object)
     * @param other  the other object to compare to
     * @return -ve if this is less than the other object, +ve if greater
     *  than, <code>0</code> of equal
     * @throws ClassCastException if other is not an Enum
     * @throws NullPointerException if other is <code>null</code>
     */
    public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (other.getClass() != this.getClass()) {
            if (other.getClass().getName().equals(this.getClass().getName())) {
                return iName.compareTo( getNameInOtherClassLoader(other) );
            }
            throw new ClassCastException(
                    "Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        }
        return iName.compareTo(((EnumData) other).iName);
    }

    /**
     * <p>Use reflection to return an objects class name.</p>
     *
     * @param other The object to determine the class name for
     * @return The class name
     */
    private String getNameInOtherClassLoader(Object other) {
        try {
        	Class<?>[] parameterTypes = {};
        	Object[] invokeObj = {};
            Method mth = other.getClass().getMethod("getName", parameterTypes);
            String name = (String) mth.invoke(other, invokeObj);
            return name;
        } catch (NoSuchMethodException e) {
            // ignore - should never happen
        } catch (IllegalAccessException e) {
            // ignore - should never happen
        } catch (InvocationTargetException e) {
            // ignore - should never happen
        }
        throw new IllegalStateException("This should not happen");
    }

    /**
     * <p>Human readable description of this Enum item.</p>
     * 
     * @return String in the form <code>type[name]</code>, for example:
     * <code>Color[Red]</code>. Note that the package name is stripped from
     * the type name.
     */
    public String toString() {
        if (iToString == null) {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            iToString = shortName + "[" + getName() + "]";
        }
        return iToString;
    }

}
