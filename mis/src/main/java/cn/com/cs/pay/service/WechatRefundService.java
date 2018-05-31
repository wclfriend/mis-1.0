package cn.com.cs.pay.service;

import cn.com.cs.wx.order.pojo.WechatOrderForm;

public interface WechatRefundService {

	void wechatRefund(WechatOrderForm orderForm);

}
