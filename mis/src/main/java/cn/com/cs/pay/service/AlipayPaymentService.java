package cn.com.cs.pay.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;

import cn.com.cs.wx.order.pojo.WechatOrderForm;

public interface AlipayPaymentService {

	String alipayBack(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException;

	AlipayTradePagePayRequest webAlipay(WechatOrderForm order);

}
