package cn.com.cs.pay.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import cn.com.cs.pay.alipay.controller.AlipayConfig;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

@Service
public class AlipayPaymentServiceSpringImpl implements AlipayPaymentService {
	
	@Override
	public String alipayBack(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
		String result = "fail";
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			String out_trade_no = request.getParameter("out_trade_no");//商户订单号
			String trade_no = request.getParameter("trade_no");//支付宝交易号
			String trade_status = request.getParameter("trade_status");//交易状态
			String buyer_logon_id  = request.getParameter("buyer_logon_id ");//买家支付宝账号
			String total_amount = request.getParameter("total_amount");//交易金额
			String receipt_amount = request.getParameter("receipt_amount");//实收金额 
			String buyer_pay_amount = request.getParameter("buyer_pay_amount");//买家付款的金额 
			String point_amount = request.getParameter("point_amount");// 使用积分宝付款的金额 
			String invoice_amount = request.getParameter("invoice_amount");//交易中可给用户开具发票的金额
			String payTime = request.getParameter("gmt_payment");//交易支付时间 
			String card_balance = request.getParameter("card_balance ");//支付宝卡余额 
			
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				result = "success";
			}
			
		}else {//验证失败
			result = "fail";
		
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
		
		//——请在这里编写您的程序（以上代码仅作参考）——
		return result;
	}
	
	@Override
	public AlipayTradePagePayRequest webAlipay(WechatOrderForm order) {
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		final AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setBody("订单描述");//订单描述
		model.setDisablePayChannels("");//禁用渠道
		model.setEnablePayChannels("balance,moneyFund,pcredit,creditCardExpress");//可用渠道
		model.setOutTradeNo(order.getOrderSerialNumber());
		model.setProductCode("FAST_INSTANT_TRADE_PAY");
		//订单标题
		model.setSubject("在线付款");
		//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
		model.setTimeoutExpress("1c");
		//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
		model.setTotalAmount(order.getOrderAmount().setScale(2).toString());
		
		JsonConfig config = new JsonConfig();
		config.registerJsonBeanProcessor(AlipayTradePagePayModel.class, new JsonBeanProcessor() {
		   @Override
		   public JSONObject processBean(Object bean, JsonConfig config) {
			   AlipayTradePagePayModel m = (AlipayTradePagePayModel) model;
			   JSONObject obj = new JSONObject();
			   obj.element("out_trade_no", m.getOutTradeNo());
			   obj.element("product_code", m.getProductCode());
			   obj.element("total_amount", m.getTotalAmount());
			   obj.element("subject", m.getSubject());
			   obj.element("body", m.getBody());
			   obj.element("timeout_express", m.getTimeoutExpress());
			   obj.element("enable_pay_channels", m.getEnablePayChannels());
			   
		      return obj;
		   }
		});
		JSONObject jsonObject = JSONObject.fromObject(model, config);
		
		System.out.println(jsonObject.toString());;
		
		alipayRequest.setBizContent(jsonObject.toString());
		
		/*
		绝对超时时间，格式为yyyy-MM-dd HH:mm
		model.setTimeExpire(null);
		业务扩展参数，花呗分期时使用
		model.setExtendParams(null);
		商品详情
		List<GoodsDetail> goodsDetail = new ArrayList<>();
		GoodsDetail g1 = new GoodsDetail();
		g1.setBody("手机壳");
		goodsDetail.add(g1);
		model.setGoodsDetail(goodsDetail);
		公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
		model.setPassbackParams(URLEncoder.encode(""));
		优惠参数  注：仅与支付宝协商后可用
		model.setPromoParams("");
		分账信息
		RoyaltyInfo royaltyInfo = new RoyaltyInfo();
		royaltyInfo.setRoyaltyType("");
		model.setRoyaltyInfo(royaltyInfo);
		商户门店编号
		model.setStoreId("");*/
		
//		alipayRequest.setBizContent(JsonUtil.entityToJson(model));
//		alipayRequest.setBizContent("{\"out_trade_no\":\""+ "D1213" +"\"," 
//				+ "\"total_amount\":1.01," 
//				+ "\"subject\":\"subject\"," 
//				+ "\"body\":\"body\"," 
//				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//		System.out.println(alipayRequest.getBizContent());
		return alipayRequest;
	}
}
