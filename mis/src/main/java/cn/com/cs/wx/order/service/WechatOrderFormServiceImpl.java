package cn.com.cs.wx.order.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.common.service.WeixinAccountService;
import cn.com.cs.common.util.JsonUtil;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.pay.util.WeixinUtils;
import cn.com.cs.wx.message.model.ReqInitiativeMes;
import cn.com.cs.wx.message.model.ReqTextMsg;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.user.pojo.FortuneTellerInfo;
import cn.com.cs.wx.user.pojo.WechatUserInfo;
import cn.com.cs.wx.user.service.FortuneTellerInfoService;
import net.sf.json.JSONObject;

@Service("wechatOrderFormService")
@Transactional
public class WechatOrderFormServiceImpl extends BaseServiceImpl implements WechatOrderFormService {
	
	@Autowired
	WeixinAccountService weixinAccountService;
	
	@Autowired
	FortuneTellerInfoService fortuneTellerService;
	
	@SuppressWarnings("unchecked")
	private Criteria createQuery(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		
		Criteria criteria = getSession().createCriteria(WechatOrderForm.class);
		if (map == null)
			return criteria;
		try {
			String orderType = map.get("orderType")[0];
			String serviceType = map.get("serviceType")[0];
			String serviceContent = map.get("serviceContent")[0];
			String orderStatus = map.get("orderStatus")[0];
			String startDate = map.get("startDate")[0];
			String endDate = map.get("endDate")[0];
			String userName = map.get("userName")[0];
			String userId = map.get("userId")[0];
			String mobilePhoneNumber = map.get("mobilePhoneNumber")[0];
			String orderSerialNumber = map.get("orderSerialNumber")[0];
			String transactionSerialNumber = map.get("transactionSerialNumber")[0];
			
			if (StringUtils.isNotBlank(startDate)) {
				criteria.add(Restrictions.ge("createTime", DateUtils.parseDate(startDate, "yyyy-MM-dd")));
			}
			if (StringUtils.isNotBlank(endDate)) {
				criteria.add(Restrictions.le("createTime", DateUtils.parseDate(endDate, "yyyy-MM-dd")));
			}
			if (StringUtils.isNotBlank(orderType)) {
				criteria.add(Restrictions.eq("orderType", orderType));
			}
			if (StringUtils.isNotBlank(serviceType)) {
				criteria.add(Restrictions.eq("serviceType", serviceType));
			}
			if (StringUtils.isNotBlank(serviceContent)) {
				criteria.add(Restrictions.eq("serviceContent", serviceContent));
			}
			if (StringUtils.isNotBlank(orderStatus)) {
				criteria.add(Restrictions.eq("orderStatus", orderStatus));
			}
			if (StringUtils.isNotBlank(orderSerialNumber)) {
				criteria.add(Restrictions.eq("orderSerialNumber", orderSerialNumber));
			}
			if (StringUtils.isNotBlank(transactionSerialNumber)) {
				criteria.add(Restrictions.eq("transactionSerialNumber", transactionSerialNumber));
			}
			criteria.createAlias("wechatUserInfo", "user");
			if (StringUtils.isNotBlank(mobilePhoneNumber)) {
				criteria.add(Restrictions.eq("user.mobilePhoneNumber", mobilePhoneNumber));
			}
			if (StringUtils.isNotBlank(userName)) {
				criteria.add(Restrictions.eq("user.userName", userName));
			}
			if (StringUtils.isNotBlank(userId)) {
				criteria.add(Restrictions.eq("user.userId", userId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return criteria;
	}

	@Override
	public WechatOrderForm getOrderByAnyId(String field, String anyId) {
		String[] ids = {"id","orderSerialNumber","refundNumber"};
		if(!Arrays.asList(ids).contains(field)) 
			return null;
		return super.findUniqueByProperty(WechatOrderForm.class, field, anyId);
	}
	
	/**
	 * 根据openId主动向客户推送文本消息
	 * @param openId
	 */
	@Override
	public void sendMessage(String openId, String content) {
		try {
			//获取关注者基本信息的URL地址
			String sendMessageUrl = WeixinUtils.SEND_MESSAGE_URL;
			
			String url = sendMessageUrl.replace("ACCESS_TOKEN", weixinAccountService.getAccessToken());
			
			ReqInitiativeMes reqInitiativeMes = new ReqInitiativeMes();
			reqInitiativeMes.setTouser(openId);
			reqInitiativeMes.setMsgtype("text");
			ReqTextMsg text = new ReqTextMsg();
			text.setContent(content);
			reqInitiativeMes.setText(text);
			String param = JsonUtil.entityToJson(reqInitiativeMes);
			
			JSONObject jsonObject = WeixinUtils.httpRequest(url, "POST", param);
			System.out.println("login success.send message to user.result:" + jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 根据openId主动向客户推送文本消息
	 * @param openId
	 */
	@Override
	public void sendMessageByOpendId(WechatOrderForm order, String content) {
		String openId = getOpenId(order);
		
		sendMessage(openId, content);
	}

	private String getOpenId(WechatOrderForm order) {
		String mobilePhoneNumber = null;
		List<FortuneTellerInfo> list = fortuneTellerService.getList(FortuneTellerInfo.class);
		WechatUserInfo userInfo = super.findUniqueByProperty(WechatUserInfo.class, "mobilePhoneNumber", mobilePhoneNumber);
		
		return userInfo.getOpenId();
	}
	

	public void uploadFile(WechatOrderForm wechatOrderForm, HttpServletRequest request) {
	}
}
