package cn.com.cs.common.enums;


/**
0	免费预测
1	免费测算完成
2	已支付
3	未支付
4	付费测算已完成
5	付费预测延期
6	付费预测取消
 * 
 * */
public class OrderStatus extends CommonEnum {

	public final static OrderStatus free = new OrderStatus("0", "免费预测");
	
	public final static OrderStatus freeFinished = new OrderStatus("1", "免费测算完成");
	
	public final static OrderStatus paid = new OrderStatus("2", "已支付");
	
	public final static OrderStatus unpaid = new OrderStatus("3", "未支付");
	
	public final static OrderStatus paidFinished = new OrderStatus("4", "付费测算已完成");
	
	public final static OrderStatus paidModified = new OrderStatus("5", "付费预测延期");
	
	public final static OrderStatus close = new OrderStatus("6", "付费预测取消");

	
    private OrderStatus(String value, String name){
    	super(value, name);
    }
    
}
