package cn.com.cs.common.enums;

/**
 * 0	未测算
 * 1	免费测算
 * 2	免费测算完成
 * 3	付费测算
 * 4	付费测算完成
 * */
public class UserStatus extends CommonEnum{

	public final static UserStatus empty = new UserStatus("0", "未测算");
	
	public final static UserStatus free = new UserStatus("1", "免费测算");
	
	public final static UserStatus freeFinished = new UserStatus("2", "免费测算完成");
	
	public final static UserStatus pay = new UserStatus("3", "付费测算");

	public final static UserStatus payFinished = new UserStatus("4", "付费测算完成");
	
	public final static UserStatus test = new UserStatus("5", "付费测算完成", "test");

	public UserStatus(String value, String name) {
		super(value, name);
	}
	public UserStatus(String value, String name, String coreValue) {
		super(value, name, coreValue);
	}
}
