package cn.com.cs.wx.user.service;

import cn.com.cs.wx.user.pojo.WechatUniqueId;

public interface WechatUniqueIdService {
	WechatUniqueId getWechatUniqueId(String id);

	String getUniqueId(String idType);

}
