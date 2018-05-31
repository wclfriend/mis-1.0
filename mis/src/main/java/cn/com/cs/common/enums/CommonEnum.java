package cn.com.cs.common.enums;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class CommonEnum {

	private String name;

	private String value;

	private String coreValue;

	public CommonEnum(String value, String name, String coreValue) {
		this.value = value;
		this.name = name;
		this.coreValue = coreValue;
	}

	public CommonEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCoreValue() {
		return coreValue;
	}

	public void setCoreValue(String coreValue) {
		this.coreValue = coreValue;
	}
	
	public static CommonEnum getEnumByCoreValue(Class<?> enumClass, String coreValue) {
		Assert.notNull(coreValue, "coreValue must not be null");
		try {
			Field[] list = enumClass.getFields();
			for (int i = 0; i < list.length; i++) {
				Field t = list[i];
				CommonEnum u = (CommonEnum) t.get(enumClass);
				if (coreValue.equals(u.getCoreValue())) {
					return u;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	public static CommonEnum getEnumByValue(Class<?> enumClass, String value) {
		Assert.notNull(value, "value must not be null");
		try {
			Field[] list = enumClass.getFields();
			for (int i = 0; i < list.length; i++) {
				Field t = list[i];
				CommonEnum u = (CommonEnum) t.get(enumClass);
				if (value.equals(u.getValue())) {
					return u;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	public static CommonEnum getEnumByName(Class<?> enumClass, String name) {
		Assert.notNull(name, "name must not be null");
		try {
			Field[] list = enumClass.getFields();
			for (int i = 0; i < list.length; i++) {
				Field t = list[i];
				CommonEnum u = (CommonEnum) t.get(enumClass);
				if (name.equals(u.getName())) {
					return u;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	public boolean containsValue(Class<?> enumClass, String value) {
		if (StringUtils.isBlank(value) || enumClass == null) {
			return false;
		}
		CommonEnum val = getEnumByValue(enumClass, value);
		
		return val == null ? false : true;
	}

	public static void main(String[] args) {
		UserStatus s = (UserStatus) UserStatus.getEnumByValue(UserStatus.class, "1");
		System.out.println(s.getName());
		
		UserStatus ss = (UserStatus) UserStatus.getEnumByCoreValue(UserStatus.class, "test");
		System.out.println(ss.getName());
	}
	
}
