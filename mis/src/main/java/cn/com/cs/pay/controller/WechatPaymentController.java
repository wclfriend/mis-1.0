package cn.com.cs.pay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.util.IpUtil;
import cn.com.cs.pay.dto.wechat.WechatPaymentDTO;
import cn.com.cs.pay.dto.wechat.WechatPaymentResultDTO;
import cn.com.cs.pay.service.WechatPaymentService;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.service.WechatOrderFormService;

@Scope("prototype")
@Path("/pay/wechat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class WechatPaymentController extends BaseController {
	
	public Logger log = LoggerFactory.getLogger(WechatPaymentController.class);
	
	@Autowired
	WechatOrderFormService orderFormService;
	
	@Autowired
	WechatPaymentService wechatPaymentService;
	
	/**
	 * 公众号支付
	 * @param orderSerialNumber
	 * @return
	 */
	@GET
	@Path("/{orderNo}")
	public AjaxJson toPay(@PathParam("orderNo") String orderNo) {
		AjaxJson json = new AjaxJson();
		WechatOrderForm order = orderFormService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", orderNo);
		if (order == null) {
			json.setSuccess(false);
			json.setMsg("订单号错误");
			return json;
		}
		String openId = order.getWechatUserInfo().getOpenId();
		if (StringUtils.isBlank(openId)) {
			json.setSuccess(false);
			json.setMsg("客户openId为空");
			return json;
		}
		if (order.getOrderStatus().equals(OrderStatus.paid.getValue())) {
			json.setSuccess(false);
			json.setMsg("已支付");
			return json;
		}
		WechatPaymentDTO dto = new WechatPaymentDTO();
		
		try {
			dto.setIpAddress(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setIpAddress("127.0.0.1");
		}
		dto.setTotalAmount(order.getOrderAmount());
		dto.setOutTradeNo(order.getOrderSerialNumber());
		dto.setBody("测算");
//		dto.setOpenId(openId);
		
		Map<String, String> map = wechatPaymentService.wxPayment(dto);
		json.setObj(map);
		
		return json;
	}

	/**
	 * 公众号支付回调
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/payCallBack")
	public String payCallBack(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		System.out.println("开始回调.....................................................");
		String responseXml = "<xml><return_code><![CDATA[{0}]]></return_code><return_msg><![CDATA[{1}]]></return_msg></xml> ";
		
		WechatPaymentResultDTO result = wechatPaymentService.wxPaymentCallBack(request, response);
		if (result == null) {
			return MessageFormat.format(responseXml, "FALSE", "通知异常");
		}
		
		WechatOrderForm order = orderFormService.getOrderByAnyId("orderSerialNumber", result.getOutTradeNo());
		if (order == null) {
			return MessageFormat.format(responseXml, "FALSE", "订单号错误");
		}
		
		if (order.getOrderStatus().equals(OrderStatus.paid.getValue())) {
			return MessageFormat.format(responseXml, "SUCCESS", "已回调成功");
		}
		
		order.setOrderStatus(OrderStatus.paidFinished.getValue());
		order.setModifyTime(new Date());
		
		orderFormService.update(order);
		
		return MessageFormat.format(responseXml, "SUCCESS", "OK");
	}
	
	
	
	/**
	 * 扫码支付
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/qr/{orderNo}")
	public void scanCode(@PathParam("orderNo") String orderNo, @Context HttpServletRequest request,@Context HttpServletResponse response) {
		WechatOrderForm orderForm = orderFormService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", orderNo);
		if (orderForm == null)
			return;
		
		WechatPaymentDTO dto = new WechatPaymentDTO();
		//产品名称
		dto.setBody("body");
		//IP
		dto.setIpAddress(IpUtil.getIpAddr(request));
		//订单金额
		dto.setTotalAmount(orderForm.getOrderAmount());
		//商户订单号
		dto.setOutTradeNo(orderForm.getOrderSerialNumber());
		//回调地址
		dto.setNotifyUrl(wechatPaymentService.getWxPayParam().get("notifyurl") + "/pay/wechat/back.do");
		
		boolean flag = wechatPaymentService.wechatScanCode(response, dto);
		log.info("wechat scan code: {}", flag);
	}
	
	@GET
	@Path("/qr/back")
	public String notifyCallBack(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		log.info("........................................................");
		try {
			StringBuffer sb = new StringBuffer();
			//读取参数
			InputStream inputStream = request.getInputStream();
			String str = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
			inputStream.close();
			
			log.info("wechat pay result:{}", sb.toString());
			Map<String, String> map = wechatPaymentService.scanBack(sb.toString());
			String xml = map.get("xml");
			log.info("wechat pay notify xml:{}", xml);
			if (StringUtils.isNotBlank(xml)) {
				return xml;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
