package cn.com.cs.common.enums;

/**
 * 0:否
 * 1:是
 * */
public class AnalyseResult extends CommonEnum {
	// NO, YES
	public final static AnalyseResult NO = new AnalyseResult("0", "否");
	
	public final static AnalyseResult YES = new AnalyseResult("1", "是");

	public AnalyseResult(String value, String name) {
		super(value, name);
	}

}
