package cn.com.cs.common.enums.util;

/**
 * 
 * @author lovxxk
 *
 */
public class EnumUtils {

	/**
	 * 返回枚举类型是否包含字符串
	 * @param cls 枚举类
	 * @param str 字符串
	 * @return
	 */
	public static <T> boolean contains(Class<T> cls, String str) {
		if (cls.isEnum()) {
			T[] ts = cls.getEnumConstants();
			for (T t : ts) {
				Enum<?> tempEnum = (Enum<?>) t;
				if (String.valueOf(tempEnum.ordinal()).equals(str) 
						|| String.valueOf(tempEnum.name()).equals(str)) {
					return true;
				}
			}
		}
		return false;
	}
	
}
