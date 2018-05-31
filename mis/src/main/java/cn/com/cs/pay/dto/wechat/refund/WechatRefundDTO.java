package cn.com.cs.pay.dto.wechat.refund;

import java.math.BigDecimal;

/**
 * 微信支付回调参数
 * @author billy
 */
public class WechatRefundDTO {
	
	/**交易流水号*/
	public String transactionNo;
	
	/**退款单号*/
	public String refundNo;

	/** 支付金额，单位：元 */
	public BigDecimal totalAmount;
	
	/**退款金额*/
	public BigDecimal refundAmount;

	/** 订单号 */
	public String outTradeNo;

	/** 发起支付请求的IP地址 */
	public String ipAddress;

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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
