package cn.com.cs.common.enums;

/**
 * 农历出生时辰
0	23:00-00:59
1	01:00-02:59
2	03:00-04:59
3	05:00-06:59
4	07:00-08:59
5	09:00-10:59
6	11:00-12:59
7	13:00-14:59
8	15:00-16:59
9	17:00-18:59
10	19:00-20:59
11	21:00-22:59
 */
public class Season extends CommonEnum{

		public final static Season T23_01 = new Season("0", "23:00-00:59");
		
		public final static Season T01_03 = new Season("1", "01:00-02:59");
		
		public final static Season T03_05 = new Season("2", "03:00-04:59");
		
		public final static Season T05_07 = new Season("3", "05:00-06:59");
		
		public final static Season T07_09 = new Season("4", "07:00-08:59");
		
		public final static Season T09_11 = new Season("5", "09:00-10:59");
		
		public final static Season T11_13 = new Season("6", "11:00-12:59");
		
		public final static Season T13_15 = new Season("7", "13:00-14:59");
		
		public final static Season T15_17 = new Season("8", "15:00-16:59");
		
		public final static Season T17_19 = new Season("9", "17:00-18:59");
		
		public final static Season T19_21 = new Season("10", "19:00-20:59");
		
		public final static Season T21_23 = new Season("11", "21:00-22:59");
		
		public Season(String value, String name) {
			super(value, name);
		}
}
