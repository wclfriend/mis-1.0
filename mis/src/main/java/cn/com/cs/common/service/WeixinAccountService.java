package cn.com.cs.common.service;

public interface WeixinAccountService extends BaseService {
	public String getAccessToken();

	public String getAccessToken(String accountId);
}
