package cn.com.cs.common.enums;

/**
 * 0:否 1:是
 */
public class DelayFlag extends CommonEnum {
	// NO, YES
	public final static DelayFlag NO = new DelayFlag("0", "否");
	
	public final static DelayFlag YES = new DelayFlag("1", "是");

	public DelayFlag(String value, String name) {
		super(value, name);
	}
}
