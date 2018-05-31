package cn.com.cs.common.enums.util;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * POJO类DataDictionary
 */
public class EnumDictionary extends EnumData {

	private static final long serialVersionUID = 1L;


	/** 属性枚举名称 */
	private String dataCode;
	
	/** 属性数据名称 */
	private String dataName;

	/** 属性数据英文名称 */
	private String dataENName;
	
	/** 属性数据类型 */
	private Integer dataType;
	
	/** 属性整型数据值 */
	private Integer value;

	/** 属性字符串数据值 */
	private String stringValue;

	/** 属性数据等级 */
	private Integer dataLevel;

	/** 属性显示顺序 */
	private Integer seqIndex;
	
	/** 属性间距 */
	private Date dateValue;
	
	/** 属性最小整型数据值 */
	private Integer minIntValue;

	/** 属性最大整型数据值 */
	private Integer maxIntValue;

	/** 属性最小日期数据值 */
	private Date minDateValue;

	/** 属性最大日期数据值 */
	private Date maxDateValue;

	/** 属性操作员 */
	private String operatorID;
	
	/** 属性核心值 */
	private String coreValue;
	
	/** 属性商家值 */
	private String merchantValue;
	
	/** 属性对应枚举值 */
	private EnumDictionary  correspond;
	
	/** 属性父节点 */
	private EnumDictionary parent;
	
	/** 属性创建时间 */
	private Date createTime;

	/** 属性更新时间 */
	private Date updateTime = new Date();
	
	public EnumDictionary() {
	}
	
	public EnumDictionary(String enumName, Integer value) {
		super(enumName);
		this.dataCode = enumName;
		this.value = value;
	}
	
	public EnumDictionary(String enumName, String dataName, Integer value) {
		super(enumName);
		this.dataName = dataName;
		this.dataCode = enumName;
		this.value = value;
	}
	
	public EnumDictionary(String enumName, String dataName, String stringValue) {
		super(enumName);
		this.dataName = dataName;
		this.dataCode = enumName;
		this.stringValue = stringValue;
	}
	
	public EnumDictionary(String enumName, String dataName, String dataENName,
			Integer value) {
		super(enumName);
		this.dataName = dataName;
		this.dataENName = dataENName;
		this.dataCode = enumName;
		this.value = value;
	}
	
	public EnumDictionary(String enumName, String dataName,
			Integer value, String coreValue) {
		super(enumName);
		this.dataCode = enumName;
		this.dataName = dataName;
		this.value = value;
		this.coreValue = coreValue;
	}
	
	public EnumDictionary(String enumName, String dataName,
			String dataENName, Integer value, String coreValue) {
		super(enumName);
		this.dataCode = enumName;
		this.dataName = dataName;
		this.dataENName = dataENName;
		this.value = value;
		this.coreValue = coreValue;
	}
	
	public EnumDictionary(String name, String dataCode, String dataName,
			Integer value, String coreValue, String merchantValue,
			EnumDictionary correspond) {
		super(name);
		this.dataCode = dataCode;
		this.dataName = dataName;
		this.value = value;
		this.coreValue = coreValue;
		this.merchantValue = merchantValue;
		this.correspond = correspond;
	}

	public EnumDictionary(String enumName, String dataName,
			Integer value, String coreValue, String merchantValue) {
		super(enumName);
		this.dataCode = enumName;
		this.dataName = dataName;
		this.value = value;
		this.coreValue = coreValue;
		this.merchantValue = merchantValue;
	}
	
	
	public EnumDictionary(String name, String dataCode, String dataName,
			String dataENName, Integer value, String coreValue,
			String merchantValue) {
		super(name);
		this.dataCode = dataCode;
		this.dataName = dataName;
		this.dataENName = dataENName;
		this.value = value;
		this.coreValue = coreValue;
		this.merchantValue = merchantValue;
	}
	
	public EnumDictionary(String name, String dataCode, String dataName,
			Integer value, String coreValue, String merchantValue) {
		super(name);
		this.dataCode = dataCode;
		this.dataName = dataName;
		this.value = value;
		this.coreValue = coreValue;
		this.merchantValue = merchantValue;
	}

	@SuppressWarnings("rawtypes")
	public static EnumData getEnum(Class enumClass, int value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        List list = EnumData.getEnumList(enumClass);
        for (Iterator it = list.iterator(); it.hasNext();) {
        	EnumDictionary enumeration = (EnumDictionary) it.next();
            if (enumeration.getValue() == value) {
                return enumeration;
            }
        }
        return null;
    }
	 
	@SuppressWarnings("rawtypes")
	public static EnumData getEnumByCoreValue(Class enumClass, String coreValue) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        List list = EnumData.getEnumList(enumClass);
        for (Iterator it = list.iterator(); it.hasNext();) {
        	EnumDictionary enumeration = (EnumDictionary) it.next();
        	if (StringUtils.isNotBlank(coreValue) && StringUtils.isNotBlank(enumeration.getCoreValue())) {
        		 if (coreValue.equalsIgnoreCase(enumeration.getCoreValue())) {
                     return enumeration;
                 }
        	}
           
        }
        return null;
    }
	
	@SuppressWarnings("rawtypes")
	public static EnumData getEnumByMerchantValue(Class enumClass, String merchantValue) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        List list = EnumData.getEnumList(enumClass);
        for (Iterator it = list.iterator(); it.hasNext();) {
        	EnumDictionary enumeration = (EnumDictionary) it.next();
        	if (StringUtils.isNotBlank(merchantValue) && StringUtils.isNotBlank(enumeration.getMerchantValue())) {
        		 if (merchantValue.equalsIgnoreCase(enumeration.getMerchantValue())) {
                     return enumeration;
                 }
        	}
           
        }
        return null;
    }
	
	public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (other.getClass() != this.getClass()) {
            if (other.getClass().getName().equals(this.getClass().getName())) {
                return value - getValueInOtherClassLoader(other);
            }
            throw new ClassCastException(
                    "Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        }
        return value - ((EnumDictionary) other).value;
    }
	
	private int getValueInOtherClassLoader(Object other) {
        try {
        	Class<?>[] parameterTypes = {};
        	Object[] invokeObj = {};
            Method mth = other.getClass().getMethod("getValue", parameterTypes);
            Integer value = (Integer) mth.invoke(other, invokeObj);
            return value.intValue();
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        throw new IllegalStateException("This should not happen");
    }
	
	 public String toString() {
	        if (iToString == null) {
	            String shortName = ClassUtils.getShortClassName(getEnumClass());
	            iToString = shortName + "[" + getName() + "=" + getValue() + "]";
	        }
	        return iToString;
    }
	 
	/**

	/**
	 * 属性数据类型的getter方法
	 */

	public Integer getDataType() {
		return this.dataType;
	}

	/**
	 * 属性数据类型的setter方法
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/**
	 * 属性数据名称的getter方法
	 */
	public String getDataName() {
		return this.dataName;
	}

	/**
	 * 属性数据名称的setter方法
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * 属性数据英文名称的getter方法
	 */
	public String getDataENName() {
		return this.dataENName;
	}

	/**
	 * 属性数据英文名称的setter方法
	 */
	public void setDataENName(String dataENName) {
		this.dataENName = dataENName;
	}

	/**
	 * 属性枚举名称的getter方法
	 */

	public String getDataCode() {
		return this.dataCode;
	}

	/**
	 * 属性枚举名称的setter方法
	 */
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	/**
	 * 属性整型数据值的getter方法
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * 属性整型数据值的setter方法
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * 属性字符串数据值的getter方法
	 */
	public String getStringValue() {
		return this.stringValue;
	}

	/**
	 * 属性字符串数据值的setter方法
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * 属性数据等级的getter方法
	 */
	public Integer getDataLevel() {
		return this.dataLevel;
	}

	/**
	 * 属性数据等级的setter方法
	 */
	public void setDataLevel(Integer dataLevel) {
		this.dataLevel = dataLevel;
	}

	/**
	 * 属性显示顺序的getter方法
	 */
	public Integer getSeqIndex() {
		return this.seqIndex;
	}

	/**
	 * 属性显示顺序的setter方法
	 */
	public void setSeqIndex(Integer seqIndex) {
		this.seqIndex = seqIndex;
	}
	
	/**
	 * 属性间距的getter方法
	 */
	public Date getDateValue() {
		return dateValue;
	}
	
	/**
	 * 属性间距的setter方法
	 */
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	/**
	 * 属性最小整型数据值的getter方法
	 */
	public Integer getMinIntValue() {
		return this.minIntValue;
	}

	/**
	 * 属性最小整型数据值的setter方法
	 */
	public void setMinIntValue(Integer minIntValue) {
		this.minIntValue = minIntValue;
	}

	/**
	 * 属性最大整型数据值的getter方法
	 */
	public Integer getMaxIntValue() {
		return this.maxIntValue;
	}

	/**
	 * 属性最大整型数据值的setter方法
	 */
	public void setMaxIntValue(Integer maxIntValue) {
		this.maxIntValue = maxIntValue;
	}

	/**
	 * 属性最小日期数据值的getter方法
	 */
	public Date getMinDateValue() {
		return this.minDateValue;
	}

	/**
	 * 属性最小日期数据值的setter方法
	 */
	public void setMinDateValue(Date minDateValue) {
		this.minDateValue = minDateValue;
	}

	/**
	 * 属性最大日期数据值的getter方法
	 */
	public Date getMaxDateValue() {
		return this.maxDateValue;
	}

	/**
	 * 属性最大日期数据值的setter方法
	 */
	public void setMaxDateValue(Date maxDateValue) {
		this.maxDateValue = maxDateValue;
	}
	
	/** 
	 * 属性核心值的getter方法
	 */
	public String getCoreValue() {
		return coreValue;
	}
	
	/** 
	 * 属性核心值的setter方法
	 */
	public void setCoreValue(String coreValue) {
		this.coreValue = coreValue;
	}

	/** 
	 * 属性商家值的setter方法
	 */
	public String getMerchantValue() {
		return merchantValue;
	}
	
	/** 
	 * 属性商家值的getter方法
	 */
	public void setMerchantValue(String merchantValue) {
		this.merchantValue = merchantValue;
	}

	

	/**
	 * 属性操作员的getter方法
	 */
	public String getOperatorID() {
		return this.operatorID;
	}

	/**
	 * 属性操作员的setter方法
	 */
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	
	/**
	 * 属性对应枚举值的getter方法
	 */
	public EnumDictionary getCorrespond() {
		return correspond;
	}
	
	/**
	 * 属性对应枚举值的setter方法
	 */
	public void setCorrespond(EnumDictionary correspond) {
		this.correspond = correspond;
	}
	
	/**
	 * 属性父节点的getter方法
	 */
	public EnumDictionary getParent() {
		return parent;
	}
	
	/**
	 * 属性父节点的setter方法
	 */
	public void setParent(EnumDictionary parent) {
		this.parent = parent;
	}

	/**
	 * 属性创建时间的getter方法
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 属性创建时间的setter方法
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 属性更新时间的getter方法
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 属性更新时间的setter方法
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
