package cn.com.cs.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.pojo.WeixinAccountEntity;
import cn.com.cs.common.util.DateUtils;
import cn.com.cs.common.util.ResourceUtil;
import cn.com.cs.pay.util.WeixinUtils;
import net.sf.json.JSONObject;

@Service("weixinAccountService")
@Transactional
public class WeixinAccountServiceImpl extends BaseServiceImpl implements WeixinAccountService {

	private static Logger log = LoggerFactory.getLogger(WeixinAccountServiceImpl.class);
	
	public WeixinAccountEntity findLoginWeixinAccount() {
		String userName = ResourceUtil.getConfigByName("loginUserName");
		List<WeixinAccountEntity> acclst = findByProperty(WeixinAccountEntity.class, "userName", userName);
		WeixinAccountEntity weixinAccountEntity = acclst.size() != 0 ? acclst.get(0) : null;
		if (weixinAccountEntity != null) {
			return weixinAccountEntity;
		} else {
			weixinAccountEntity = new WeixinAccountEntity();
			// 返回个临时对象，防止空指针
			weixinAccountEntity.setWeixin_accountid("-1");
			weixinAccountEntity.setId("-1");
			return weixinAccountEntity;
		}
	}
	

	@Override
	public String getAccessToken() {
		WeixinAccountEntity account = findLoginWeixinAccount();
		String token = account.getAccountaccesstoken();
		Date start = account.getAddtoekntime();
		if (StringUtils.isBlank(token) || DateUtils.addHours(start, 1).before(new Date())) {
			// 失效 重新获取
			String requestUrl = WeixinUtils.access_token_url.replace("APPID", account.getAccountappid()).replace("APPSECRET", account.getAccountappsecret());
			JSONObject jsonObject = WeixinUtils.httpRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					account.setAccountaccesstoken(token);
					// 重置事件
					account.setAddtoekntime(new Date());
					this.update(account);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInt("errcode")
							+ jsonObject.getString("errmsg");
					log.info(wrongMessage);
				}
			}
		}
		return token;
	}
	

	@Override
	public String getAccessToken(String accountId) {
		
		return null;
	}
}