package cn.com.cs.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import cn.com.cs.core.hibernate.Page;

public interface BaseService {

	<T> List<T> queryByHql(String hql, Object... values);

	void save(Object entity);

	void update(Object entity);

	void delete(Object entity);

	Object load(Class<?> entityClass, String id);

	<T> T get(Class<T> entityClass, String id);

	void merge(Class<?> entity);

	void evict(Class<?> entity);

	void saveAll(List<?> list);

	void updateAll(String hql);

	<T> List<T> findByProperty(Class<T> entityClass, String propertyName, String value);

	<T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions);

	Session getSession();

	List<?> list(Criteria criteria);


	public <T> T getEntity(Class entityName, String id);

	List<Map<String, Object>> findForJdbc(String sql, Object[] objs);

	Integer executeSql(String sql, Object[] param);

	Integer executeSql(String sql, Map<String, Object> param);


	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value);
	

	<T> List<T> getList(Class<?> entityClass);

	List<Map<String, Object>> queryForList(String sql, Object[] objects);

	Map<String, String> getBasicCodeByTypeName(String typeName);

	Map<String, Object> queryForMapSingleRow(String sql, Object[] objects);

	 <T> List<T> list(Class<T> c, Page page);

	void saveOrUpdate(Object entity);

	Integer totalCount(Class<T> c);

	void saveLog(HttpServletRequest request, String remark);

	<T> void delete(Class<T> entity, String id);


	public void flush();
}