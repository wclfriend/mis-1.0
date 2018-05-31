package cn.com.cs.wx.order.service;

public interface WeixinService {


	String getUserOpenId(String code);

	String getAccessToken();

}
