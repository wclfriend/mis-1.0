package cn.com.cs.common.enums;

/**
 * 0:男
 * 1:女
 * 2:未知
 * */
public class Sex extends CommonEnum{

	public final static Sex MALE = new Sex("0", "男");
	
	public final static Sex FEMALE = new Sex("1", "女");
	
	public final static Sex UNKNOW = new Sex("2", "未知");
	
	public Sex(String value, String name) {
		super(value, name);
	}
}
