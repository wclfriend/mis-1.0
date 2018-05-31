package cn.com.cs.pay.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.pay.service.WechatRefundService;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.service.WechatOrderFormService;

@Scope("prototype")
@Path("/refund/wechat")
public class WechatRefundController extends BaseController {
	
	@Autowired
	WechatOrderFormService orderFormService;
	
	@Autowired
	WechatRefundService refundService;
	
	@GET
	@Path("/{orderSerialNumber}")
	public AjaxJson toPay(@PathParam("orderSerialNumber") String orderSerialNumber) {
		AjaxJson json = new AjaxJson();
		List<WechatOrderForm> list = orderFormService.findByProperty(WechatOrderForm.class, "orderSerialNumber", orderSerialNumber);
		System.out.println("list size:" + list.size());
		if (list.size() == 0) {
			json.setSuccess(false);
			json.setMsg("订单号错误");
			return json;
		}
		WechatOrderForm order = list.get(0);
		
		refundService.wechatRefund(order);
		json.setObj("SUCCESS");
		
		return json;
	}
}
