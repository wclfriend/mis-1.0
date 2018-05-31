package cn.com.cs.wx.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.pojo.WechatOrderRefund;

public interface WechatOrderRefundService extends BaseService {

	void wechatRefunxn(WechatOrderForm orderForm);

}
