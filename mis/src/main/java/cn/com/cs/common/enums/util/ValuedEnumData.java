package cn.com.cs.common.enums.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

@SuppressWarnings({"rawtypes"})
public class ValuedEnumData extends EnumData {

	 /**
     * Required for serialization support.
     * 
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = -7129650521543789085L;
    
    /**
     * The value contained in enum.
     */
    private final int iValue;

    /**
     * Constructor for enum item.
     *
     * @param name  the name of enum item
     * @param value  the value of enum item
     */
    protected ValuedEnumData(String name, int value) {
        super(name);
        iValue = value;
    }

    /**
     * <p>Gets an <code>Enum</code> object by class and value.</p>
     *
     * <p>This method loops through the list of <code>Enum</code>,
     * thus if there are many <code>Enum</code>s this will be
     * slow.</p>
     * 
     * @param enumClass  the class of the <code>Enum</code> to get
     * @param value  the value of the <code>Enum</code> to get
     * @return the enum object, or null if the enum does not exist
     * @throws IllegalArgumentException if the enum class is <code>null</code>
     */
    protected static EnumData getEnum(Class enumClass, int value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        List list = EnumData.getEnumList(enumClass);
        for (Iterator it = list.iterator(); it.hasNext();) {
        	ValuedEnumData enumeration = (ValuedEnumData) it.next();
            if (enumeration.getValue() == value) {
                return enumeration;
            }
        }
        return null;
    }

    /**
     * <p>Get value of enum item.</p>
     *
     * @return the enum item's value.
     */
    public final int getValue() {
        return iValue;
    }

    /**
     * <p>Tests for order.</p>
     *
     * <p>The default ordering is numeric by value, but this
     * can be overridden by subclasses.</p>
     *
     * <p>NOTE: From v2.2 the enums must be of the same type.
     * If the parameter is in a different class loader than this instance,
     * reflection is used to compare the values.</p>
     *
     * @see java.lang.Comparable#compareTo(Object)
     * @param other  the other object to compare to
     * @return -ve if this is less than the other object, +ve if greater than,
     *  <code>0</code> of equal
     * @throws ClassCastException if other is not an <code>Enum</code>
     * @throws NullPointerException if other is <code>null</code>
     */
    public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (other.getClass() != this.getClass()) {
            if (other.getClass().getName().equals(this.getClass().getName())) {
                return iValue - getValueInOtherClassLoader(other);
            }
            throw new ClassCastException(
                    "Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        }
        return iValue - ((ValuedEnumData) other).iValue;
    }

    /**
     * <p>Use reflection to return an objects value.</p>
     *
     * @param other  the object to determine the value for
     * @return the value
     */
    private int getValueInOtherClassLoader(Object other) {
        try {
        	Class<?>[] parameterTypes = {};
        	Object[] invokeObj = {};
            Method mth = other.getClass().getMethod("getValue", parameterTypes);
            Integer value = (Integer) mth.invoke(other, invokeObj);
            return value.intValue();
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
     * <p>Human readable description of this <code>Enum</code> item.</p>
     *
     * @return String in the form <code>type[name=value]</code>, for example:
     *  <code>JavaVersion[Java 1.0=100]</code>. Note that the package name is
     *  stripped from the type name.
     */
    public String toString() {
        if (iToString == null) {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            iToString = shortName + "[" + getName() + "=" + getValue() + "]";
        }
        return iToString;
    }
	
}
