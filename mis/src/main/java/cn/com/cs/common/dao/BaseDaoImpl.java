package cn.com.cs.common.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Repository
@Transactional
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	
	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);  
    } 

	@Override
	public List<Map<String, Object>> queryForList(String sql,Object...objects) {
		List<Map<String, Object>> ss = jdbcTemplate.queryForList(sql, objects);
		return ss;
	}
	
	public Map<String, Object> queryForMapSingleRow(String sql,Object...objects) {
		
		return jdbcTemplate.queryForMap(sql, objects);
	}
	

	public <T> List<T> queryByHql(String hql, Object... values) {
		Assert.notNull(hql, "hql is null");
		
		return (List<T>)hibernateTemplate.find(hql, values);
	}

	public void save(Object entity) {
		hibernateTemplate.save(entity);
	}

	public void update(Object entity) {
		hibernateTemplate.update(entity);
	}

	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
	}

	public <T> T load(Class<?> entityClass, String id) {
		return (T) hibernateTemplate.load(entityClass, id);
	}

	public <T> T get(Class<?> entityClass, String id) {
		if (StringUtils.isBlank(id))
			return null;
		return (T) hibernateTemplate.get(entityClass, id);
	}

	public void merge(Class<?> entity) {
		hibernateTemplate.merge(entity);
	}

	public void evict(Class<?> entity) {
		hibernateTemplate.evict(entity);
	}
	
	public void saveAll(List<?> list)  {
	}

	@Override
	public void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}
	
	public void updateAll(String hql) {
		jdbcTemplate.batchUpdate(hql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

			}

			@Override
			public int getBatchSize() {
				return 0;
			}
		});
	}

	public <T> List<T> findByProperty(Class<?> entityClass, String propertyName, String value) {
		return createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	public <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	public List<?> list(Criteria criteria) {
		return criteria.list();
	}
	
	@Override
	public void saveOrUpdate(Object entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	@Override
	public void flush() {
		hibernateTemplate.flush();
	}

}
