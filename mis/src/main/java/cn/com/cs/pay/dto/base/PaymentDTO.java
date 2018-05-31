package cn.com.cs.pay.dto.base;

import java.math.BigDecimal;

/**
 * 支付相关参数
 *
 */
public class PaymentDTO {

	/**商品名称	 */
	public String body;
	
	/**回调地址*/
	public String notifyUrl;
	
	/**支付金额，单位：元*/
	public BigDecimal totalAmount;
	
	/**订单号*/
	public String outTradeNo;
	
	/**发起支付请求的IP地址*/
	public String ipAddress;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
