package cn.com.cs.wx.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.user.pojo.FortuneTellerInfo;

public interface FortuneTellerInfoService extends BaseService {

	Map<String, Set<String>> getUsedTime(String fortuneId);

	List<FortuneTellerInfo> fortuneList(HttpServletRequest request, Page page);

	boolean hasOrderForms(String id);


}
