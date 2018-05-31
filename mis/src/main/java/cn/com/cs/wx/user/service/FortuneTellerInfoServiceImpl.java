package cn.com.cs.wx.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.common.enums.OrderType;
import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.core.hibernate.CriteriaQuery;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.user.pojo.FortuneTellerInfo;

@Service("fortuneTellerInfoService")
@Transactional
public class FortuneTellerInfoServiceImpl extends BaseServiceImpl implements FortuneTellerInfoService {
	

	/***
	 * @param fortuneId  测算师ID
	 * 
	 */
	@Override
	public Map<String, Set<String>> getUsedTime(String fortuneId) {
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		CriteriaQuery cq = new CriteriaQuery(WechatOrderForm.class);
		cq.eq("fortuneTellerInfo.id", fortuneId);
		cq.eq("orderType", String.valueOf(OrderType.pay.getValue()));
		cq.notEq("orderStatus", OrderStatus.paidFinished.getValue());
		cq.notEq("orderStatus", OrderStatus.close.getValue());
		cq.add();
		List<WechatOrderForm> fortuneList = super.getListByCriteriaQuery(cq, false);
//		String serviceDate = null,serviceTime = null;
//		Set<String> set = null;
//		for (WechatOrderForm order : fortuneList) {
//			//未选择预约时间
//			if (order.getServiceDate() == null)
//				continue;
//			serviceDate = DateUtils.formatDate(order.getServiceDate(), "yyyyMMdd");
//			serviceTime = order.getServiceTime();
//			if (StringUtils.isNotBlank(order.getDelayflag()) && order.getDelayflag().equals(String.valueOf(DelayFlag.YES.getValue()))) {
//				serviceDate = DateUtils.formatDate(order.getModifiedServiceDate(), "yyyyMMdd");
//				serviceTime = order.getModifiedServiceTime();
//			}
//			set = result.get(serviceDate);
//			if (set == null) {
//				set = new HashSet<String>();
//				result.put(serviceDate, set);
//			}
//			set.add(serviceTime);
//		}
		
		return result;
	}

	@Override
	public List<FortuneTellerInfo> fortuneList(HttpServletRequest request, Page page) {
		int totle = totalCount(FortuneTellerInfo.class);
		page.setRecordsTotal(totle);
		page.setRecordsFiltered(totle);
		
		Criteria criteria = getSession().createCriteria(FortuneTellerInfo.class);
		criteria.setMaxResults(page.getLength());
		criteria.setFirstResult(page.getStart());
		List<FortuneTellerInfo> list = criteria.list();
		
		return list;
	}

	@Override
	public boolean hasOrderForms(String id) {
		String hql = "select count(*) c from wx_orderForm where fortuneTellerInfoId = ?";
		List<Map<String, Object>> result = super.queryForList(hql, id);
		
		String count = result.get(0).get("c").toString();
		
		return Integer.valueOf(count) > 0 ? true : false;
	}
}