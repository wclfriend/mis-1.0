package cn.com.cs.pay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.pay.dto.wechat.WechatPaymentDTO;
import cn.com.cs.pay.dto.wechat.WechatPaymentResultDTO;

public interface WechatPaymentService extends BaseService {

	Map<String, String> wxPayment(WechatPaymentDTO parameterDTO);

	WechatPaymentResultDTO wxPaymentCallBack(HttpServletRequest request,
			HttpServletResponse response);

	Map<String, String> getWxPayParam();

	boolean wechatScanCode(HttpServletResponse response, WechatPaymentDTO dto);

	Map<String, String> scanBack(String xml);
	
}
