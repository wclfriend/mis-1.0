package cn.com.cs.pay.dto.wechat;

import cn.com.cs.pay.dto.base.PaymentDTO;

/**
 * 微信支付参数
 * @author billy
 *
 */
public class WechatPaymentDTO extends PaymentDTO {
	
	private String openId;//微信用户对一个公众号唯一

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
