package cn.com.cs.wx.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.pojo.WechatOrderRefund;

@Service("wechatOrderRefundService")
@Transactional
public class WechatOrderRefundServiceImpl extends BaseServiceImpl implements WechatOrderRefundService {
	@Override
	public void wechatRefunxn(WechatOrderForm orderForm) {
		
	}

	@SuppressWarnings("unchecked")
	private Criteria createQuery(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		
		Criteria criteria = getSession().createCriteria(WechatOrderRefund.class);
		if (map == null)
			return criteria;
		try {
			String orderSerialNumber = map.get("orderSerialNumber")[0];
			String transactionSerialNumber = map.get("transactionSerialNumber")[0];
			String refundStatus = map.get("refundStatus")[0];
			String refundNumber = map.get("refundNumber")[0];
			String transactionRefundNumber = map.get("transactionRefundNumber")[0];
			String refundAmount = map.get("refundAmount")[0];
			
			if (StringUtils.isNotBlank(orderSerialNumber)) {
				criteria.add(Restrictions.eq("orderSerialNumber", orderSerialNumber));
			}
			if (StringUtils.isNotBlank(transactionSerialNumber)) {
				criteria.add(Restrictions.eq("transactionSerialNumber", transactionSerialNumber));
			}
			if (StringUtils.isNotBlank(refundStatus)) {
				criteria.add(Restrictions.eq("refundStatus", refundStatus));
			}
			if (StringUtils.isNotBlank(refundNumber)) {
				criteria.add(Restrictions.eq("refundNumber", refundNumber));
			}
			if (StringUtils.isNotBlank(transactionRefundNumber)) {
				criteria.add(Restrictions.eq("transactionRefundNumber", transactionRefundNumber));
			}
			if (StringUtils.isNotBlank(refundAmount)) {
				criteria.add(Restrictions.eq("refundAmount", refundAmount));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return criteria;
	}
}