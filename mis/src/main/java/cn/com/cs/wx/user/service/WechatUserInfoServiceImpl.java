package cn.com.cs.wx.user.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.enums.UserStatus;
import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.pay.util.WeixinUtils;
import cn.com.cs.wx.order.service.WeixinService;
import cn.com.cs.wx.user.pojo.WechatUserInfo;
import net.sf.json.JSONObject;

@Service("wechatUserInfoService")
@Transactional
public class WechatUserInfoServiceImpl extends BaseServiceImpl implements WechatUserInfoService {
	
	@Autowired
	WechatUniqueIdService wechatUniqueService;
	
	@Autowired
	WeixinService weixinService;
	
	
	@Override
	public WechatUserInfo getWechatUserInfo(String openId) {
		List<WechatUserInfo> users = super.findByProperty(WechatUserInfo.class, "openId", openId);
		
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public void saveWechtUserInfo(String openId) {
		if (StringUtils.isBlank(openId))
			return;
		WechatUserInfo userInfo = getWechatUserInfo(openId);
		if (userInfo == null) {
			userInfo = new WechatUserInfo();
			userInfo.setOpenId(openId);
			userInfo.setUserId(wechatUniqueService.getUniqueId("userId"));
			userInfo.setUserStatus(String.valueOf(UserStatus.empty.getValue()));
			
			super.save(userInfo);
		}
	}
	
	@Override
	public WechatUserInfo getWechatUserInfoByMobile(String mobilePhoneNumber) {
		String hql = "from " + WechatUserInfo.class.getSimpleName() + " where mobilePhoneNumber = ?";
		List<WechatUserInfo> list = super.queryByHql(hql, mobilePhoneNumber);
		
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public WechatUserInfo getWechatUserByAnyId(String field, String anyId) {
		String[] ids = {"id","userId","openId"};
		if(!Arrays.asList(ids).contains(field)) 
			return null;
		WechatUserInfo user = super.findUniqueByProperty(WechatUserInfo.class, field, anyId);
		
		return user;
	}
	
	
	/**
	 * 调用微信API，查询客户基本信息
	 * @param fromUserName
	 * @return
	 */
	@Override
	public Map<String, String> getUserInfo(String openId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			//获取关注者基本信息的URL地址
			String userInfo_url = WeixinUtils.userInfo_url;
			String accessTokenCode = weixinService.getAccessToken();
			
			String url = userInfo_url.replace("ACCESS_TOKEN", accessTokenCode).replace("OPENID", openId);
			
			JSONObject jsonObject = WeixinUtils.httpRequest(url, "GET", null);
			
			//没有授权，无法获取客户昵称
			if (!jsonObject.containsKey("nickname")) {
				return map;
			}
			
			//昵称
			map.put("nickname", jsonObject.getString("nickname"));
			//性别。值为1时是男性，值为2时是女性，值为0时是未知 
			map.put("sex", jsonObject.getString("sex"));
			//城市
			map.put("city", jsonObject.getString("city"));
			//国家
			map.put("country", jsonObject.getString("country"));
			//省份
			map.put("province", jsonObject.getString("province"));
			//语言
			map.put("language", jsonObject.getString("language"));
			//头像地址 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 
			map.put("headimgurl", jsonObject.getString("headimgurl"));
			//关注时间
			map.put("subscribe_time", jsonObject.getString("subscribe_time"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public List<WechatUserInfo> userList(HttpServletRequest request, Page page) {
		int totle = totalCount(WechatUserInfo.class);
		page.setRecordsTotal(totle);
		page.setRecordsFiltered(totle);
		
		Criteria criteria = createQuery(request);
		criteria.setMaxResults(page.getLength());
		criteria.setFirstResult(page.getStart());
		
		List<WechatUserInfo> list = criteria.list();
		
		return list;
	}

	private Criteria createQuery(HttpServletRequest request) {
		Criteria criteria = getSession().createCriteria(WechatUserInfo.class);

		Map<String, String[]> map = request.getParameterMap();
		if (map == null) {
			return criteria;
		}
		try {
			String userName = map.get("userName")[0];
			String mobilePhone = map.get("mobilePhone")[0];
			String sex = map.get("sex")[0];
			String userId = map.get("userId")[0];
			String userStatus = map.get("userStatus")[0];
			
			if (StringUtils.isNotBlank(userName)) {
				criteria.add(Restrictions.ge("userName", userName));
			}
			if (StringUtils.isNotBlank(mobilePhone)) {
				criteria.add(Restrictions.eq("mobilePhoneNumber", mobilePhone));
			}
			if (StringUtils.isNotBlank(sex)) {
				criteria.add(Restrictions.eq("sex", sex));
			}
			if (StringUtils.isNotBlank(userId)) {
				criteria.add(Restrictions.eq("userId", userId));
			}
			if (StringUtils.isNotBlank(userStatus)) {
				criteria.add(Restrictions.eq("userStatus", userStatus));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return criteria;
	}
	
}