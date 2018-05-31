package cn.com.cs.common.enums;

/***
0	6:00-8:00
1	8:00-10:00
2	10:00-12:00
3	12:00-14:00
4	14:00-16:00
5	16:00-18:00
6	18:00-20:00
7	20:00-22:00 
 *
 */
public class ServiceTime extends CommonEnum{

	public final static ServiceTime T_6_8 = new ServiceTime("0", "6:00-8:00");

	public final static ServiceTime T_8_10 = new ServiceTime("1", "8:00-10:00");
	
	public final static ServiceTime T_10_12 = new ServiceTime("2", "10:00-12:00");
	
	public final static ServiceTime T_12_14 = new ServiceTime("3", "12:00-14:00");
	
	public final static ServiceTime T_14_16 = new ServiceTime("4", "14:00-16:00");
	
	public final static ServiceTime T_16_18 = new ServiceTime("5", "16:00-18:00");
	
	public final static ServiceTime T_18_20 = new ServiceTime("6", "18:00-20:00");
	
	public final static ServiceTime T_20_22 = new ServiceTime("7", "20:00-22:00");
	
	public ServiceTime(String value, String name) {
		super(value, name);
	}
}
