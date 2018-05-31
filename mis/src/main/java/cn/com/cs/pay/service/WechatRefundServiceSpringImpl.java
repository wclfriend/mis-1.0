package cn.com.cs.pay.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.common.enums.RefundStatus;
import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.pay.dto.wechat.refund.WechatRefundDTO;
import cn.com.cs.pay.util.MD5Util;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.pojo.WechatOrderRefund;
import cn.com.cs.wx.order.service.WechatOrderFormService;
import cn.com.cs.wx.user.service.WechatUniqueIdService;
 
@Service
@Transactional
public class WechatRefundServiceSpringImpl extends BaseServiceImpl implements WechatRefundService{
	
	@Autowired
	WechatOrderFormService orderFormService;
	
	@Autowired
	WechatUniqueIdService w_uniqueIdService;
	
	WechatPaymentService paymentService;
	
	@Override
	public void wechatRefund(WechatOrderForm order) {
		WechatOrderRefund refund = new WechatOrderRefund();
		refund.setTransactionSerialNumber(order.getTransactionSerialNumber());
		refund.setOrderSerialNumber(order.getOrderSerialNumber());
		refund.setRefundNumber(w_uniqueIdService.getUniqueId("order"));
		refund.setOrderAmount(order.getOrderAmount());
		refund.setRefundAmount(order.getOrderAmount());
		
		WechatRefundDTO dto = new WechatRefundDTO();
		dto.setOutTradeNo(order.getOrderSerialNumber());
		dto.setTransactionNo(order.getTransactionSerialNumber());
		dto.setRefundNo(order.getRefundNumber());
		dto.setTotalAmount(order.getOrderAmount());
		dto.setRefundAmount(order.getOrderAmount());
		
		//调用退款接口
		String result = wxPayRefund(dto);
		
		try {
			Element root = DocumentHelper.parseText(result.toString()).getRootElement();
			//状态码
			String returnCode = root.selectSingleNode("return_code").getStringValue();
			if (StringUtils.isNotBlank(returnCode) && returnCode.equals("SUCCESS")) {
				//业务结果
				String resultCode = root.selectSingleNode("result_code").getStringValue();
				if (StringUtils.isNotBlank(resultCode) && resultCode.equals("SUCCESS")) {
					//交易状态 
					String tradeState = root.selectSingleNode("trade_state").getStringValue();
					if (StringUtils.isNotBlank(tradeState) && tradeState.equals("SUCCESS")) {
						refund.setRefundStatus(RefundStatus.SUCCESS.getValue());
						refund.setRemark(root.selectSingleNode("return_msg").getStringValue());
						//退款流水号
						refund.setTransactionRefundNumber(root.selectSingleNode("refund_id").getStringValue());	
					} else {
						refund.setRefundStatus(RefundStatus.FAILED.getValue());
						refund.setRemark(root.selectSingleNode("trade_state_desc").getStringValue());
					}
				} else {
					refund.setRefundStatus(RefundStatus.FAILED.getValue());
					refund.setRemark(root.selectSingleNode("err_code_des").getStringValue());
				}
			} else {
				refund.setRefundStatus(RefundStatus.FAILED.getValue());
				refund.setRemark(root.selectSingleNode("return_msg").getStringValue());
			}
		} catch (DocumentException e) {
			refund.setRefundStatus(RefundStatus.FAILED.getValue());
			refund.setRemark(e.getMessage());
			e.printStackTrace();
		}
		
		refund.setRefundTime(new Date());
		refund.setModifyTime(new Date());
		refund.setOrderForm(order);
		saveOrUpdate(refund);
		
		order.setOrderStatus(OrderStatus.close.getValue());
		order.setModifyTime(new Date());
		
		orderFormService.update(order);
	}
	
	public String wxPayRefund(WechatRefundDTO dto) {
		StringBuffer result = new StringBuffer();
		//微信支付的参数信息
		Map<String, String> map = paymentService.getWxPayParam();
		String MCH_ID = map.get("partner");//1460618702
		String APPID = map.get("appId");//wxc9ffe978b2468290
		try {
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", APPID);// appid
			parameters.put("mch_id", MCH_ID);// 商户号
			parameters.put("nonce_str", CreateNoncestr());
			// 在notify_url中解析微信返回的信息获取到 transaction_id，此项不是必填，详细请看上图文档
			 parameters.put("transaction_id", dto.getTransactionNo());
			parameters.put("out_trade_no", dto.getOutTradeNo());// 订单号
			//退款单号
			parameters.put("out_refund_no", dto.getRefundNo());// 我们自己设定的退款申请号，约束为UK
			parameters.put("total_fee", dto.getTotalAmount().multiply(new BigDecimal("100")).setScale(0).toString());// 单位为分
			parameters.put("refund_fee", dto.getRefundAmount().multiply(new BigDecimal("100")).setScale(0).toString());// 单位为分
			parameters.put("op_user_id", MCH_ID);// 操作人员,默认为商户账号
			String sign = createSign("utf-8", parameters, map);
			parameters.put("sign", sign);

			String reuqestXml = getRequestXml(parameters);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(this.getClass().getResource("/").getPath() + "/config/apiclient_cert.p12"));// 放退款证书的路径
			try {
				keyStore.load(instream, MCH_ID.toCharArray());
			} finally {
				instream.close();
			}

			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");// 退款接口

				System.out.println("executing request" + httpPost.getRequestLine());
				StringEntity reqEntity = new StringEntity(reuqestXml);
				// 设置类型
				reqEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(reqEntity);
				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					HttpEntity entity = response.getEntity();

					System.out.println("----------------------------------------");
					System.out.println(response.getStatusLine());
					if (entity != null) {
						System.out.println("Response content length: " + entity.getContentLength());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
						String text;
						while ((text = bufferedReader.readLine()) != null) {
							result.append(text);
						}
					}
					System.out.println(result.toString());
					EntityUtils.consume(entity);
				} finally {
					response.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String createSign(String charSet, SortedMap<Object, Object> parameters, Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + map.get("partnerkey"));
		String sign = MD5Util.MD5Encode(sb.toString(), charSet).toUpperCase();
		return sign;
	}

	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
