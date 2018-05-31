package cn.com.cs.wx.order.service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.service.WeixinAccountService;
import cn.com.cs.common.util.ResourceUtil;
import cn.com.cs.pay.util.WeixinUtils;
import cn.com.cs.wx.user.service.WechatUniqueIdService;
import cn.com.cs.wx.user.service.WechatUserInfoService;
import net.sf.json.JSONObject;

@Service
@Transactional
public class WeixinServiceSpringImpl implements WeixinService {
	
	private static final Logger log = LogManager.getLogger(WeixinServiceSpringImpl.class);
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	WechatUserInfoService wechatUserInfoService;
	
	@Autowired
	WechatUniqueIdService wechatUniqueService;
	
//	@Autowired
//	WechatPaymentService wechatPaymentService;
	
	@Autowired
	WeixinAccountService weixinAccountService;
	
	/**
	 * 获取AccessToken Code，已考虑失效情况
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAccessToken() {
		return weixinAccountService.getAccessToken();
	}
	
	
	/**
	 * 微信高级接口获取OpenId
	 * @param code
	 * @return
	 */
	@Override
	public String getUserOpenId(String code) {
		log.info("start get user openId.");
		String openId = null;
		try {
			String appid = ResourceUtil.getConfigByName("wxPay_AppId");
			String appsecret = ResourceUtil.getConfigByName("wxPay_APPKEY");;
			
			//获取关注者基本信息的URL地址
			String getOpenIdUrl = ResourceUtil.getConfigByName("get_openId_url");
			getOpenIdUrl = MessageFormat.format(getOpenIdUrl, appid, appsecret, code);
			log.info("get user openId ,http request url:" + getOpenIdUrl);
			
			JSONObject jsonObject = WeixinUtils.httpRequest(getOpenIdUrl, "GET", null);
			log.info("get user info.process result json:" + jsonObject);
			
			if (!jsonObject.containsKey("openid")) {
				log.error("error code.");
				return null;
			}
			openId = jsonObject.getString("openid");
			
		} catch (Exception e) {
			log.error("get user openId failed." + e);
		}
		
		return openId;
	}
	


	/**
	 * 生成微信分享需要的参数
	 * @return
	 */
	public Map<String, String> wechatShare(String url) {
		Map<String, String> map = new HashMap<String, String>();
		
//		String noncestr = CommonUtil.CreateNoncestr();
//		String timestamp = create_timestamp();
//		String appId = WeixinUtils.getContentFromProperties("appid");
//		String uri = WeixinUtils.getContentFromProperties("url");
//		
//
//		//注意这里参数名必须全部小写
//		StringBuffer string1 = new StringBuffer("jsapi_ticket=").append(getTicketCode())
//								.append("&noncestr=").append(noncestr)
//								.append("&timestamp=").append(timestamp)
//								.append("&url=").append(url);
//		
//		String signature = SHA1Util.Sha1(string1.toString());
//
//		map.put("uri", uri);
//		map.put("appId", appId);
//		map.put("timestamp", timestamp);
//		map.put("nonceStr", noncestr);
//		map.put("signature", signature);
		return map;
	}
	
	/**
	 * 高级接口获取用户OpenId时使用
	 * @return
	 */
//	private String getTicketCode() {
//		//查询数据库中保存的历时数据
//		OAuthAccessToken ticket = oAuthAccessTokenService.getTicketId();
//		//失效日期大于当前日期,说明该ticketCode还可以用
//		if (ticket != null && ticket.getExpirationDate().after(new Date())) {
//			return ticket.getAccessTokenCode();
//		}
//		
//		String accessTokenCode = getAccessToken();
//		
//		String uri = WeixinUtil.JSAPI_TICKET_URL.replaceAll("ACCESS_TOKEN", accessTokenCode);
//		JSONObject obj = WeixinUtil.httpRequest(uri, "GET", "");
//		log.info(obj);
//		String result = obj.get("ticket") != null ? obj.get("ticket").toString() : "";
//		if (ticket == null) {
//			ticket = new OAuthAccessToken();
//			ticket.setName("ticket");
//		}
//		ticket.setAccessTokenCode(result);
//		ticket.setEffectiveDate(new Date());
//		try {
//			ticket.setExpirationDate(DateUtils.addSeconds(ticket.getEffectiveDate(), obj.getInt("expires_in")));
//		} catch (Exception e) {
//			ticket.setExpirationDate(new Date());
//			e.printStackTrace();
//		}
//		ticket.setRemark("JS-SDK使用");
//		ticket.setTokenType(2);
//		ticket.setUpdateTime(new Date());
//		
//		oAuthAccessTokenService.saveOrUpdateAccessToken(ticket);
//		
//		System.out.println("jsapi_ticket=" + result);
//		
//		return result;
//	}
	
	private static String create_timestamp() {
	    return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 提交订单，确认支付，获取微信支付需要的相关参数
	 * @param productName 产品名称
	 * @param orderSerialNumber 订单号
	 * @param orderAmount 支付金额
	 * @return
	 */
	public Map<String, String> wxPayment(String productName, String orderSerialNumber, BigDecimal orderAmount, String uri) {
		
		return null;
	}

}








