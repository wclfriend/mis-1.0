package cn.com.cs.pay.alipay.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.pay.service.AlipayPaymentService;
import cn.com.cs.pay.service.WechatPaymentService;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.service.WechatOrderFormService;

@RestController
@Scope("prototype")
@RequestMapping("/pay/alipay")
public class AliPayController extends BaseController {

	public Logger log = LoggerFactory.getLogger(AliPayController.class);

	@Autowired
	WechatOrderFormService orderFormService;

	@Autowired
	WechatPaymentService wechatPaymentService;

	@Autowired
	AlipayPaymentService alipayService;

	/**
	 * 支付宝网站付款
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/{orderNo}")
	public ModelAndView toPay(@PathVariable("orderNo") String orderNo) {
		ModelAndView mav = new ModelAndView("/web/pay/alipay/mess");
		WechatOrderForm order = orderFormService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", orderNo);
		if (order == null) {
			mav.addObject("mess", "订单号错误");
			return mav;
		}
		if (order.getOrderStatus().equals(OrderStatus.paid.getValue())) {
			mav.addObject("mess", "已支付成功");
			return mav;
		}

		mav.addObject("alipayRequest", alipayService.webAlipay(order)).setViewName("/web/pay/alipay/alipay");

		return mav;
	}

	/**
	 * 网站支付回调
	 */
	@RequestMapping(value = "/return", method = { RequestMethod.GET })
	public ModelAndView returnBack(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("return................................................................................");
			try {
				System.out.println(alipayService.alipayBack(request));;
			} catch (Exception e) {
				e.printStackTrace();
			}
		ModelAndView mav = new ModelAndView("web/pay/alipay/result");
		
		return mav;
	}

	/**
	 * 网站支付回调
	 */
	@RequestMapping(value = "/nofity", method = { RequestMethod.POST })
	public void nofityBack(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("return======================================================================================");
		try {
			ServletOutputStream out = response.getOutputStream();
			out.println(alipayService.alipayBack(request));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
