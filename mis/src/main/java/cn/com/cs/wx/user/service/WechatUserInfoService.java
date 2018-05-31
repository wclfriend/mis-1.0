package cn.com.cs.wx.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.user.pojo.WechatUserInfo;

public interface WechatUserInfoService extends BaseService {

	WechatUserInfo getWechatUserInfo(String openId);

	void saveWechtUserInfo(String openId);

	/**
	 * 
	 * @param field id, userId, openId
	 * @param anyId id
	 * @return
	 */
	WechatUserInfo getWechatUserByAnyId(String field, String anyId);

	Map<String, String> getUserInfo(String openId);

	WechatUserInfo getWechatUserInfoByMobile(String mobilePhoneNumber);

	List<WechatUserInfo> userList(HttpServletRequest request, Page page);

}
