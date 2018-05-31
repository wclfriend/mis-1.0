package cn.com.cs.common.enums;


/**
0	否
1	是
 * */
public class Active extends CommonEnum{

	public final static Active FALSE = new Active("0", "否");
	
	public final static Active TRUE = new Active("1", "是");
	
	public Active(String value, String name) {
		super(value, name);
	}
    
}
