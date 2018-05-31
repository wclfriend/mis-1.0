package cn.com.cs.common.enums;

/**
 * 0 : 感情
 * 1 : 事业
 * 2 : 财运
 * 3 : 健康
 * 4 : 婚姻
 * */
public class ServiceContent extends CommonEnum{

	public final static ServiceContent love = new ServiceContent("0", "感情");

	public final static ServiceContent career = new ServiceContent("1", "事业");
	
	public final static ServiceContent money = new ServiceContent("2", "财运");
	
	public final static ServiceContent health = new ServiceContent("3", "健康");
	
	public final static ServiceContent Marriage = new ServiceContent("4", "婚姻");
	
	public ServiceContent(String value, String name) {
		super(value, name);
	}
}
