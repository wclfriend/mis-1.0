package cn.com.cs.common.constant;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ServiceTimeMap {
	public static Map<String, String> timesMap = null;
	static {
		timesMap = new TreeMap<String, String>(new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				// 降序排序
				return obj1.compareTo(obj2);
			}
		});
		timesMap.put("0", "6:00-8:00");
		timesMap.put("1", "8:00-10:00");
		timesMap.put("2", "10:00-12:00");
		timesMap.put("3", "12:00-14:00");
		timesMap.put("4", "14:00-16:00");
		timesMap.put("5", "16:00-18:00");
		timesMap.put("6", "18:00-20:00");
		timesMap.put("7", "20:00-22:00");
	}
	public static void main(String[] args) {
		System.out.println(ServiceTimeMap.timesMap.get("0"));;
	}
}
