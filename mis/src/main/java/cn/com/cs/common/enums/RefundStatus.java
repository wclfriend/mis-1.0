package cn.com.cs.common.enums;

/**
 * 退款状态
 *
 */
public class RefundStatus extends CommonEnum {
	public final static RefundStatus SUCCESS = new RefundStatus("0", "成功");

	public final static RefundStatus FAILED = new RefundStatus("1", "失败");
	
	public final static RefundStatus PROCESSING = new RefundStatus("2", "处理中");

	public RefundStatus(String value, String name) {
		super(value, name);
	}
}
