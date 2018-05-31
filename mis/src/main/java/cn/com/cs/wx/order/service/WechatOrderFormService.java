package cn.com.cs.wx.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.pojo.WechatOrderForm;

public interface WechatOrderFormService extends BaseService {
	
	WechatOrderForm getOrderByAnyId(String field, String anyId);

	void sendMessageByOpendId(WechatOrderForm order, String content);

	void sendMessage(String openId, String content);

	void uploadFile(WechatOrderForm wechatOrderForm, HttpServletRequest request);

}
