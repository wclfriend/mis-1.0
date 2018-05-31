package cn.com.cs.common.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public interface BaseDao {

	<T> List<T> queryByHql(String hql, Object... values);

	void save(Object entity);

	void update(Object entity);

	void delete(Object entity);

	<T> T load(Class<?> entityClass, String id);

	<T> T get(Class<?> entityClass, String id);

	void merge(Class<?> entity);

	void evict(Class<?> entity);

	void saveAll(List<?> list);

	void updateAll(String hql);

	<T> List<T> findByProperty(Class<?> entityClass, String propertyName, String value);

	<T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions);

	Session getSession();

	List<?> list(Criteria criteria);

	List<Map<String, Object>> queryForList(String sql, Object[] objects);

	public Map<String, Object> queryForMapSingleRow(String sql, Object[] objects);

	void saveOrUpdate(Object entity);

	void executeSql(String sql);

	void flush();

}