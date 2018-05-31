package cn.com.cs.pay.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.pay.dto.wechat.WechatPaymentDTO;
import cn.com.cs.pay.dto.wechat.WechatPaymentResultDTO;
import cn.com.cs.pay.scan.wx.WXPay;
import cn.com.cs.pay.scan.wx.WXPayConfig;
import cn.com.cs.pay.scan.wx.WXPayUtil;
import cn.com.cs.pay.util.GetWxOrderno;
import cn.com.cs.pay.util.RequestHandler;
import cn.com.cs.pay.util.Sha1Util;
import cn.com.cs.pay.util.TenpayUtil;
import cn.com.cs.pay.util.WeixinUtils;
import cn.com.cs.wx.order.service.WechatOrderFormService;

/**
 * 微信支付Service
 *
 */
@Service("wechatPaymentService")
@Transactional
public class WechatPaymentServiceSpringImpl extends BaseServiceImpl implements WechatPaymentService {
	private static Logger log = LoggerFactory.getLogger(WechatPaymentServiceSpringImpl.class);
	
	@Override
	public Map<String, String> getWxPayParam() {
		Map<String, String> result = new HashMap<String, String>();
		String sql = "select t.codeName,t.code from basic_type t,basic_typegroup g where g.id = t.typegroupid and g.code = 'wxPay'";
		List<Map<String, Object>> list = super.findForJdbc(sql);
		for (Map<String, Object> map : list) {
			result.put(map.get("codeName").toString(), map.get("code").toString());
		}
		
		return result;
	}
	
	@Autowired
	WechatOrderFormService orderFormService;
	
	/**
	 * 封装微信支付的相关参数
	 */
	public Map<String, String> wxPayment(WechatPaymentDTO dto) {
		//微信支付的参数信息
		Map<String, String> map = getWxPayParam();
		String attach = "123";
		// 总金额以分为单位，不带小数点
		String totalFee = dto.getTotalAmount().multiply(new BigDecimal("100")).setScale(0).toString();
		//时间戳
		String noncStr = getNonceStr();

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", map.get("appId"));
		packageParams.put("mch_id", map.get("partner"));
		packageParams.put("nonce_str", noncStr);
		packageParams.put("body", dto.getBody());
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", dto.getOutTradeNo());
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", dto.getIpAddress());
		packageParams.put("notify_url", map.get("notifyurl"));
		packageParams.put("trade_type", "JSAPI");
		packageParams.put("openid", dto.getOpenId());
		
		log.info("wx pay:" + packageParams.toString());
		
		System.out.println("map to xml :" + packageParams.toString());
		
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(map.get("appId"), map.get("appSecret"), map.get("partnerkey"));

		String sign = reqHandler.createSign(packageParams);
		StringBuffer xml = new StringBuffer("<xml>")
					.append("<appid>").append(map.get("appId")).append("</appid>")
					.append("<mch_id>").append(map.get("partner")).append("</mch_id>")
					.append("<nonce_str>").append(noncStr).append("</nonce_str>")
					.append("<sign>").append(sign).append("</sign>")
					.append("<body><![CDATA[").append(dto.getBody()).append("]]></body>")
					.append("<out_trade_no>").append(dto.getOutTradeNo()).append("</out_trade_no>")
					.append("<attach>").append(attach).append("</attach>")
					.append("<total_fee>").append(totalFee).append("</total_fee>")
					.append("<spbill_create_ip>").append(dto.getIpAddress()).append("</spbill_create_ip>")
					.append("<notify_url>").append(map.get("notifyurl")).append("</notify_url>")
					.append("<trade_type>").append("JSAPI").append("</trade_type>")
					.append("<openid>").append(dto.getOpenId()).append("</openid>")
					.append("</xml>");
		
		System.out.println("xml:\n" + xml.toString());
		String prepay_id = GetWxOrderno.getPayNo(WeixinUtils.unifiedorder_url, xml.toString());
		System.out.println("获取到的预支付ID：" + prepay_id);
		
		// 获取prepay_id后，拼接最后请求支付所需要的package
		SortedMap<String, String> result = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id=" + prepay_id;
		result.put("appId", map.get("appId"));
		result.put("timeStamp", timestamp);
		result.put("nonceStr", getNonceStr());
		result.put("package", packages);
		result.put("signType", "MD5");
		//签名
		String finalsign = reqHandler.createSign(result);
		
		result.put("paySign", finalsign);

		return result;
	}
	

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
	
	/**
	 * 微信支付完成之后，签名
	 * @param request
	 * @param response
	 * @return
	 */
	public WechatPaymentResultDTO wxPaymentCallBack(HttpServletRequest request, HttpServletResponse response) {
		log.info("微信支付回调数据开始");
		String inputLine;
		String notityXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("支付成功返回报文信息：" + notityXml);
		WechatPaymentResultDTO wpr = null;
		Element root = null;
		try {
			root = DocumentHelper.parseText(notityXml).getRootElement();
			wpr = new WechatPaymentResultDTO();
			wpr.setAppId(root.selectSingleNode("appid").getStringValue());
			wpr.setBankType(root.selectSingleNode("bank_type").getStringValue());
			wpr.setCashFee(root.selectSingleNode("cash_fee").getStringValue());
			wpr.setFeeType(root.selectSingleNode("fee_type").getStringValue());
			wpr.setIsSubscribe(root.selectSingleNode("is_subscribe").getStringValue());
			wpr.setMchId(root.selectSingleNode("mch_id").getStringValue());
			wpr.setNonceStr(root.selectSingleNode("nonce_str").getStringValue());
			wpr.setOpenid(root.selectSingleNode("openid").getStringValue());
			wpr.setOutTradeNo(root.selectSingleNode("out_trade_no").getStringValue());
			wpr.setResultCode(root.selectSingleNode("result_code").getStringValue());
			wpr.setReturn_code(root.selectSingleNode("return_code").getStringValue());
			wpr.setSign(root.selectSingleNode("sign").getStringValue());
			wpr.setTimeEnd(root.selectSingleNode("time_end").getStringValue());
			wpr.setTotalFee(root.selectSingleNode("total_fee").getStringValue());
			wpr.setTradeType(root.selectSingleNode("trade_type").getStringValue());
			wpr.setTransactionId(root.selectSingleNode("transaction_id").getStringValue());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return wpr;
	}

	@Override
	public boolean wechatScanCode(HttpServletResponse response, WechatPaymentDTO dto) {
		WXPayConfig config = WXPayConfig.getInstance(this.getWxPayParam());
		WXPay wxpay = new WXPay(config);
        
		HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", dto.getBody());
        data.put("out_trade_no", dto.getOutTradeNo());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", dto.getTotalAmount().multiply(new BigDecimal("100")).setScale(0).toString());
        data.put("spbill_create_ip", dto.getIpAddress());
        data.put("notify_url", dto.getNotifyUrl());
        data.put("trade_type", "NATIVE");
        data.put("product_id", dto.getBody());

        try {
            Map<String, String> map = wxpay.unifiedOrder(data);
            System.out.println(map);
            if ("SUCCESS".equals(map.get("return_code"))) {
            	if ("SUCCESS".equals(map.get("result_code"))) {
                	createQRCode(response, map.get("code_url"));
                } else {
                	log.error("生成预订单失败.......result_code:{}.........{}",map.get("result_code"), map.get("err_code_des"));
                }
            } else {
            	log.error("生成预订单失败.......return_code:{}.........{}",map.get("return_code"), map.get("return_msg"));
            }
        } catch (Exception e) {
        	log.error("生成预订单异常................");
            e.printStackTrace();
        }
		return false;
	}
	/**
     * 生成二维码图片
     * @param content
     * @return
     */
    public void createQRCode(HttpServletResponse response, String content) {
        try {
             Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
             hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
             BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
             
             MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public Map<String, String> scanBack(String xml) {
		Map<String, String> result = new HashMap<String, String>();
		
		WXPayConfig config = WXPayConfig.getInstance(this.getWxPayParam());
        WXPay wxpay = new WXPay(config);

		try {
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 转换成map
			if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
	            
	        }
	        else {
	            // 签名错误，如果数据里没有sign字段，也认为是签名错误
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}  

        
	        
	        
//		ScanPayNotifyResData resData = new ScanPayNotifyResData();
//		try {
//			if (Signature.checkIsSignValidFromResponseString(xml)) {
//				//解析xml成map
//				Map<String, Object> map = XMLParser.getMapFromXML(xml);
//				 //支付成功
//				 if("SUCCESS".equals((String)map.get("return_code"))) {
//					 if (((String)map.get("result_code")).equals("SUCCESS")) {
//						log.info("wechat scan pay success.");
//						String mch_id = (String) map.get("mch_id");
//						String openid = (String) map.get("openid");
//						String is_subscribe = (String) map.get("is_subscribe");
//						String out_trade_no = (String) map.get("out_trade_no");
//						String transaction_id = (String)map.get("transaction_id");
//
//						String total_fee = (String) map.get("total_fee");
//				        	
//						log.info("mch_id:" + mch_id);
//						log.info("openid:" + openid);
//						log.info("is_subscribe:" + is_subscribe);
//						log.info("out_trade_no:" + out_trade_no);
//						log.info("total_fee:" + total_fee);
//						
//						result.put("out_trade_no", out_trade_no);
//			        	
//		        		return result;
//					 } else {
//						 log.info("result_code:{}", map.get("result_code"));
//					 }
//				 } else {
//					 log.info("return_code:{}", map.get("return_code"));
//				 }
//			}else{
//				log.info("通知签名验证失败");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			resData.setReturn_code("FAIL");
//    		resData.setReturn_msg(e.getMessage());
//		}
//		
//		if (StringUtils.isBlank(result.get("xml"))) {
//			resData.setReturn_code("FAIL");
//    		resData.setReturn_msg("未知错误");
//    		
//    		result.put("flag", "N");
//    		result.put("xml", XMLUtils.getXmlFromObject(resData, ScanPayNotifyResData.class));
//		}
		
		return result;
	}
	
}
