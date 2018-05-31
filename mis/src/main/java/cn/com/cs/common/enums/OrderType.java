package cn.com.cs.common.enums;

/***
 * 0:免费订单
 * 1:付费订单
 */
public class OrderType extends CommonEnum {
	public final static OrderType free = new OrderType("0", "免费订单");
	
	public final static OrderType pay = new OrderType("1", "付费订单");
	
	public final static OrderType theme = new OrderType("2", "主题订单");
	
	public OrderType(String value, String name) {
		super(value, name);
	}

}
