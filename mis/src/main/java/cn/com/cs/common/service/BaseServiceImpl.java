package cn.com.cs.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.com.cs.common.dao.BaseDao;
import cn.com.cs.common.util.IpUtil;
import cn.com.cs.core.hibernate.CriteriaQuery;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.core.security.SpringSecurityUtils;
import cn.com.cs.system.pojo.base.log.SystemLog;
import cn.com.cs.wx.order.pojo.WechatOrderForm;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	public BaseDao dao;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Override
	public void saveLog(HttpServletRequest request, String remark) {
		SystemLog log = new SystemLog();
		log.setOperator(SpringSecurityUtils.getCurrentUserName());
		log.setIp(IpUtil.getIpAddr(request));
		log.setRemark(remark);
		log.setCreateTime(new Date());
		
		save(log);
	}
	
	@Override
	public <T> void delete(Class<T> entity,String id) {
		dao.delete(get(entity, id));
	}

	@Override
	public <T> List<T> queryByHql(String hql, Object... values) {
		return dao.queryByHql(hql, values);
	}
	
	@Override
	public void saveOrUpdate(Object entity) {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void save(Object entity) {
		dao.save(entity);
	}

	@Override
	public void update(Object entity) {
		dao.update(entity);
	}

	@Override
	public void delete(Object entity) {
		dao.delete(entity);
	}
	
	@Override
	public Object load(Class<?> entityClass, String id) {
		return dao.load(entityClass, id);
	}
	
	@Override
	public <T> T get(Class<T> entityClass, String id) {
		return dao.get(entityClass, id);
	}

	@Override
	public void merge(Class<?> entity) {
		dao.merge(entity);
	}
	
	@Override
	public void evict(Class<?> entity) {
		dao.evict(entity);
	}
	
	@Override
	public void saveAll(List<?> list) {
		dao.saveAll(list);
	}
	
	@Override
	public void updateAll(String hql) {
		dao.updateAll(hql);
	}

	@Override
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, String value) {
		return dao.findByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions) {
		return dao.createCriteria(entityClass, isAsc, criterions);
	}
	
	@Override
	public Session getSession() {
		return dao.getSession();
	}
	
	@Override
	public List<?> list(Criteria criteria) {
		return dao.list(criteria);
	}
	
	/**
	 * 根据实体名获取对象
	 */
	public <T> T getEntity(Class entityName, String id) {
		return dao.get(entityName, id);
	}
	
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql, objs);
	}
	
	@Override
	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql, param);
	}
	
	@Override
	public Integer executeSql(String sql, Map<String, Object> param) {
		// return this.namedParameterJdbcTemplate.update(sql, param);
		return null;
	}
	
	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}
	
	/**
	 * 创建Criteria对象带属性比较
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	@Override
	public <T> List<T> getList(Class<?> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}
	
	public List<WechatOrderForm> getListByCriteriaQuery(CriteriaQuery cq, boolean ispage) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());
		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		if (ispage)
			criteria.setMaxResults(cq.getPageSize());
		return criteria.list();
	}
	
	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... objects) {
		return dao.queryForList(sql, objects);
	}
	
	@Override
	public Map<String, Object> queryForMapSingleRow(String sql, Object...objects) {
		return dao.queryForMapSingleRow(sql, objects);
	}
	
	@Override
	public Map<String, String> getBasicCodeByTypeName(String typeName) {
		Map<String, String> result = new HashMap<String, String>();
		String sql = "select t.code,t.codeName from basic_type t where exists (select 1 from basic_typegroup g where t.typegroupid = g.id and g.code = ?)";
		List<Map<String, Object>> list = queryForList(sql, typeName);
		for (Map<String, Object> map : list) {
			result.put(map.get("code").toString(), map.get("codeName").toString());
		}
		
		System.out.println(result.toString());
		return result;
	}
	
	@Override
	public <T> List<T> list(Class<T> c, Page page){
		Criteria criteria = getSession().createCriteria(c);
		
		int totle = criteria.list().size();
		page.setRecordsTotal(totle);
		page.setRecordsFiltered(totle);
		
		criteria.setMaxResults(page.getLength());
		criteria.setFirstResult(page.getStart());
		criteria.addOrder(Order.asc("createTime"));
		
		List<T> list = criteria.list();
		
		return list;
	}
	
	@Override
	public Integer totalCount(Class c) {
		String hql = "select count(*) from " + c.getSimpleName();
		
		List<Object> list = this.queryByHql(hql);
		
		return Integer.valueOf(list.get(0).toString());
	}

	@Override
	public void flush() {
		dao.flush();
	}

}
