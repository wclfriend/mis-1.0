package cn.com.cs.common.enums;

/***
 *  0 : 面聊
 *  1 : 语音电话
 */
public class ServiceType extends CommonEnum{

	public final static ServiceType meet = new ServiceType("0", "面聊");
	
	public final static ServiceType voice = new ServiceType("1", "语音电话");
	
	public ServiceType(String value, String name) {
		super(value, name);
	}
}
