package com.love.family.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("baseHibernateQueryDao")
public class BaseHibernate4QueryDao<E extends Serializable> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;
	private static SessionFactory sf;
	protected Class<E> entityClass = null;

	@SuppressWarnings("unchecked")
	public BaseHibernate4QueryDao() {
		if (getClass().getGenericSuperclass() instanceof java.lang.reflect.ParameterizedType) {
			if (!(((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0] instanceof TypeVariable)) {
				entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
			}
		}
	}

	public void flush() {
		em.flush();
	}

	public void clear() {
		em.flush();
		em.clear();
	}

	public void detach(E entity) {
		em.detach(entity);
	}

	/**
	 * 将entity从Session中脱离
	 * 
	 * @param entities
	 */
	public void detachAll(Collection<E> entities) {
		for (E entity : entities) {
			this.detach(entity);
		}
	}

	public boolean contains(E entity) {
		return em.contains(entity);
	}

	/**
	 * 不确定entity的状态
	 * 
	 * @param entity
	 */
	public void save(E entity) {
		if (!em.contains(entity)) {
			em.merge(entity);
		}
	}

	public E saveEntity(E entity) {
		if (!em.contains(entity)) {
			return em.merge(entity);
		}
		return null;
	}

	/**
	 * 不确定entity的状态
	 * 
	 * @param entities
	 */
	public void save(Collection<E> entities) {
		for (E entity : entities) {
			save(entity);
		}
	}

	/**
	 * 已确定entity为New状态，直接插入记录到数据库
	 * 
	 * @param entity
	 */
	public void insert(E entity) {
		em.persist(entity);
	}

	/**
	 * 已确定entity为New状态，直接插入记录到数据库
	 * 
	 * @param entities
	 */
	public void insert(Collection<E> entities) {
		for (E entity : entities) {
			insert(entity);
		}
	}

	
	public void delete(Object id) {
		delete(load(id));
	}
	
	public <T extends Serializable> void delete(T entity,Class<T> objType) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}
	
	public void delete(E entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	public E load(Object id) {
		return em.find(entityClass, id);
	}

	/**
	 * HQL查询语句返回单个Entity对象，使用位置参数（positional parameter）
	 */
	protected E findOneEntityObject(final String hql, final Object[] values) {
		return findOneEntityObject(hql, null, values);
	}

	/**
	 * 根据HQL查询语句返回单个Entity对象，使用命名参数（named parameter）
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	protected E findOneEntityObject(final String hql, final Map<String, Object> conditionMap) {
		return findOneEntityObject(hql, conditionMap, null);
	}

	@SuppressWarnings("unchecked")
	private E findOneEntityObject(final String hql, final Map<String, Object> map, final Object[] values) {
		if (hql != null) {
			Query query = em.createQuery(hql, entityClass);
			setParameters(query, map, values);
			return (E) getSingleResult(query);
		} else {
			return null;
		}
	}

	/**
	 * HQL查询语句返回Entity对象列表，使用位置参数（positional parameter）
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	protected List<E> findEntityObjects(final String hql, final Object[] values) {
		return findAllEntityObjects(hql, null, values);
	}

	/**
	 * HQL查询语句返回Entity对象列表，使用命名参数（named parameter）
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	protected List<E> findEntityObjects(final String hql, final Map<String, Object> conditionMap) {
		return findAllEntityObjects(hql, conditionMap, null);
	}

	@SuppressWarnings("unchecked")
	private List<E> findAllEntityObjects(final String hql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (hql != null) {
			Query query = em.createQuery(hql, entityClass);
			setParameters(query, conditionMap, values);
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * HQL查询语句返回查询结果对象，对象为非Entity对象，若多个字段的情况下，返回Object[]，使用位置参数（positional
	 * parameter）
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	protected Object findOneResultObject(final String hql, final Object[] values) {
		return findOneResultObject(hql, null, values);
	}

	/**
	 * HQL查询语句返回查询结果对象，对象为非Entity对象，若多个字段的情况下，返回Object[]，使用命名参数（named parameter）
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	protected Object findOneResultObject(final String hql, final Map<String, Object> conditionMap) {
		return findOneResultObject(hql, conditionMap, null);
	}

	private Object findOneResultObject(final String hql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (hql != null) {
			Query query = em.createQuery(hql);
			setParameters(query, conditionMap, values);
			return getSingleResult(query);
		} else {
			return null;
		}
	}

	/**
	 * HQL查询语句返回查询结果对象列表，列表中的对象为非Entity对象，若多个字段的情况下，返回Object[]的列表，使用位置参数（positional
	 * parameter）
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	protected List<?> findResultObjects(final String hql, final Object[] values) {
		return findAllResultObjects(hql, null, values);
	}

	/**
	 * HQL查询语句返回查询结果对象列表，列表中的对象为非Entity对象，若多个字段的情况下，返回Object[]的列表，使用命名参数（named
	 * parameter）
	 * 
	 * @param hql
	 * @param conditionMap
	 * @return
	 */
	protected List<?> findResultObjects(final String hql, final Map<String, Object> conditionMap) {
		return findAllResultObjects(hql, conditionMap, null);
	}

	private List<?> findAllResultObjects(final String hql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (hql != null) {
			Query query = em.createQuery(hql);
			setParameters(query, conditionMap, values);
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * HQL查询语句返回分页的Entity对象列表， 使用位置参数（positional parameter）
	 */
	protected Page<E> findEntityObjects(final String hql, final Object[] values, final Pageable pageRequest) {
		return findEntityObjects(hql, null, values, pageRequest);
	}

	/**
	 * HQL查询语句返回分页的Entity对象列表， 使用命名参数（named parameter）
	 */
	protected Page<E> findEntityObjects(final String hql, final Map<String, Object> conditionMap,
			final Pageable pageRequest) {
		return findEntityObjects(hql, conditionMap, null, pageRequest);
	}

	private Page<E> findEntityObjects(final String hql, final Map<String, Object> conditionMap, final Object[] values,
			final Pageable pageRequest) {
		if (hql != null && pageRequest.getOffset() >= 0 && pageRequest.getPageSize() > 0) {
			long total = findCount(hql, conditionMap, values);
			Query query = em.createQuery(hql, entityClass);
			setParameters(query, conditionMap, values);
			Long offset =pageRequest.getOffset();
			query.setFirstResult(offset.intValue());
			int pageSize  =pageRequest.getPageSize();
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<E> content = query.getResultList();

			return new PageImpl<E>(content, pageRequest, total);
		} else {
			return null;
		}
	}

	/**
	 * HQL查询语句返回分页的查询结果对象列表，使用位置参数（positional parameter）
	 */
	protected Page<Object[]> findResultObjects(final String hql, final Object[] values, final Pageable pageRequest) {
		return findResultObjects(hql, null, values, pageRequest);
	}

	/**
	 * HQL查询语句返回分页的查询结果对象列表，使用命名参数（named parameter）
	 */
	protected Page<Object[]> findResultObjects(final String hql, final Map<String, Object> conditionMap,
			final Pageable pageRequest) {
		return findResultObjects(hql, conditionMap, null, pageRequest);
	}

	private Page<Object[]> findResultObjects(final String hql, final Map<String, Object> conditionMap,
			final Object[] values, final Pageable pageRequest) {
		if (hql != null && pageRequest.getOffset() >= 0 && pageRequest.getPageSize() > 0) {
			long total = findCount(hql, conditionMap, values);
			Query query = em.createQuery(hql);
			setParameters(query, conditionMap, values);
			Long offset =pageRequest.getOffset();
			query.setFirstResult(offset.intValue());
			int pageSize  =pageRequest.getPageSize();
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<Object[]> content = query.getResultList();

			return new PageImpl<Object[]>(content, pageRequest, total);
		} else {
			return null;
		}
	}

	/**
	 * 通用的hql查询总行数的方法
	 */
	private long findCount(final String hql, Map<String, Object> conditionMap, final Object[] values) {
		String sql = hqlToSql(hql);
		// hqlToSql(String)方法在转换SQL时，会把HQL中的命名参数转成位置参数，所以该地方调用时传入的是位置参数
		if (conditionMap != null && !conditionMap.isEmpty()) {
			Object[] paras = mapToArray(hql, conditionMap);
			sql = improveSql(sql);
			return findCountBySql(sql, null, paras);
		} else {
			return findCountBySql(sql, null, values);
		}
	}

	/**
	 * 通用的sql查询语句返回单个Entity对象，使用位置参数（positional parameter）
	 */
	protected E findOneEntityObjectBySql(final String sql, final Object[] values) {
		return findOneEntityObjectBySql(sql, null, values);
	}

	/**
	 * 通用的sql查询语句返回单个Entity对象，使用命名参数（named parameter）
	 */
	protected E findOneEntityObjectBySql(final String sql, final Map<String, Object> conditionMap) {
		return findOneEntityObjectBySql(sql, conditionMap, null);
	}

	@SuppressWarnings("unchecked")
	private E findOneEntityObjectBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (sql != null) {
			Query query = em.createNativeQuery(sql, entityClass);
			setParameters(query, conditionMap, values);
			return (E) getSingleResult(query);
		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回Entity对象列表，使用位置参数（positional parameter）
	 */
	protected List<E> findEntityObjectsBySql(final String sql, final Object[] values) {
		return findAllEntityObjectsBySql(sql, null, values);
	}

	/**
	 * 通用的sql查询语句返回Entity对象列表，使用命名参数（named parameter）
	 */
	protected List<E> findEntityObjectsBySql(final String sql, final Map<String, Object> conditionMap) {
		return findAllEntityObjectsBySql(sql, conditionMap, null);
	}

	@SuppressWarnings("unchecked")
	private List<E> findAllEntityObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (sql != null) {
			Query query = em.createNativeQuery(sql, entityClass);
			setParameters(query, conditionMap, values);
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回单个查询结果，使用位置参数（positional parameter）
	 */
	protected Object findOneResutlObjectBySql(final String sql, final Object[] values) {
		return findOneResutlObjectBySql(sql, null, values);
	}

	/**
	 * 通用的sql查询语句返回单个查询结果，使用命名参数（named parameter）
	 */
	protected Object findOneResutlObjectBySql(final String sql, final Map<String, Object> conditionMap) {
		return findOneResutlObjectBySql(sql, conditionMap, null);
	}

	private Object findOneResutlObjectBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (sql != null) {
			Query query = em.createNativeQuery(sql);
			setParameters(query, conditionMap, values);
			return getSingleResult(query);
		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回查询结果对象列表，使用位置参数（positional parameter）
	 */
	protected List<?> findResutlObjectsBySql(final String sql, final Object[] values) {
		return findAllResutlObjectsBySql(sql, null, values);
	}

	/**
	 * 通用的sql查询语句返回查询结果对象列表，使用命名参数（named parameter）
	 */
	protected List<?> findResutlObjectsBySql(final String sql, final Map<String, Object> conditionMap) {
		return findAllResutlObjectsBySql(sql, conditionMap, null);
	}

	private List<?> findAllResutlObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values) {
		if (sql != null) {
			Query query = em.createNativeQuery(sql);
			setParameters(query, conditionMap, values);
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回分页Entity对象列表，使用位置参数（positional parameter）
	 */
	protected Page<E> findEntityObjectsBySql(final String sql, final Object[] values, final Pageable pageRequest) {
		return findEntityObjectsBySql(sql, null, values, pageRequest);
	}

	/**
	 * 通用的sql查询语句返回分页Entity对象列表，使用命名参数（named parameter）
	 */
	protected Page<E> findEntityObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Pageable pageRequest) {
		return findEntityObjectsBySql(sql, conditionMap, null, pageRequest);
	}

	private Page<E> findEntityObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values, final Pageable pageRequest) {
		if (pageRequest.getOffset() >= 0 && pageRequest.getPageSize() > 0 && sql != null) {
			long total = findCountBySql(sql, conditionMap, values);
			Query query = em.createNativeQuery(sql, entityClass);
			setParameters(query, conditionMap, values);
			Long offset =pageRequest.getOffset();
			query.setFirstResult(offset.intValue());
			int pageSize  =pageRequest.getPageSize();
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<E> content = query.getResultList();
			return new PageImpl<E>(content, pageRequest, total);

		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回分页查询结果列表，使用位置参数（positional parameter）
	 */
	protected Page<Object[]> findResutlObjectsBySql(final String sql, final Object[] values,
			final Pageable pageRequest) {
		return findResutlObjectsBySql(sql, null, values, pageRequest);
	}

	/**
	 * 通用的sql查询语句返回分页查询结果列表，使用命名参数（named parameter）
	 */
	protected Page<Object[]> findResutlObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Pageable pageRequest) {
		return findResutlObjectsBySql(sql, conditionMap, null, pageRequest);
	}

	private Page<Object[]> findResutlObjectsBySql(final String sql, final Map<String, Object> conditionMap,
			final Object[] values, final Pageable pageRequest) {
		if (pageRequest.getOffset() >= 0 && pageRequest.getPageSize() > 0 && sql != null) {
			long total = findCountBySql(sql, conditionMap, values);
			Query query = em.createNativeQuery(sql);
			setParameters(query, conditionMap, values);
			Long offset =pageRequest.getOffset();
			query.setFirstResult(offset.intValue());
			int pageSize  =pageRequest.getPageSize();
			query.setMaxResults(pageSize);
			@SuppressWarnings("unchecked")
			List<Object[]> content = query.getResultList();

			return new PageImpl<Object[]>(content, pageRequest, total);
		} else {
			return null;
		}
	}

	/**
	 * 通用的sql查询语句返回结果数的实现，sql以from开头
	 */
	private Integer findCountBySql(final String sql, final Map<String, Object> conditionMap, final Object[] values) {
		if (sql != null) {
			Object o = findOneResutlObjectBySql("select count(*) from ( " + sql + " ) generateSql", conditionMap,
					values);
			if (Objects.nonNull(o)) {
				return Integer.parseInt(o.toString());
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 更新或者删除语句hql 使用位置参数（positional parameter）
	 */
	protected int bulkUpdate(final String hql, final Object[] values) {
		return bulkUpdate(hql, null, values);
	}

	/**
	 * 更新或者删除语句hql 使用命名参数（named parameter）
	 */
	protected int bulkUpdate(final String hql, final Map<String, Object> conditionMap) {
		return bulkUpdate(hql, conditionMap, null);
	}

	private int bulkUpdate(final String hql, final Map<String, Object> conditionMap, final Object[] values) {
		if (hql != null) {
			Query query = em.createQuery(hql);
			setParameters(query, conditionMap, values);
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	/**
	 * 更新或者删除语句sql，使用命名参数（named parameter）使用位置参数（positional parameter）
	 */
	protected int bulkUpdateSql(final String sql, final Object[] values) {
		return bulkUpdateSql(sql, null, values);
	}

	/**
	 * 更新或者删除语句sql，使用命名参数（named parameter）使用位置参数（positional parameter）
	 */
	protected int bulkUpdateSql(final String sql, final Map<String, Object> conditionMap) {
		return bulkUpdateSql(sql, conditionMap, null);
	}

	private int bulkUpdateSql(final String sql, final Map<String, Object> conditionMap, final Object[] values) {
		if (sql != null) {
			Query query = em.createNativeQuery(sql);
			setParameters(query, conditionMap, values);
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	private void setParameters(Query query, Map<String, Object> map, Object[] values) {
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		} else if (null != values && values.length > 0) {
			for (int i = 0; i < values.length; ++i) {
				query.setParameter(i + 1, values[i]);
			}
		}
	}

	/**
	 * 该方法不支持JPA2.0规范，依赖于Hibernate实现， 并且使用了Hibernate内部API，Hibernate后继版本不保证兼容
	 *
	 */
	private String hqlToSql(final String hql) {
		if (StringUtils.isEmpty(hql)) {
			return hql;
		}
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP,
				(SessionFactoryImplementor) getSessionFactory());
		queryTranslator.compile(Collections.EMPTY_MAP, true);
		String sql = queryTranslator.getSQLString();
		return sql;
	}

	private SessionFactory getSessionFactory() {
		Session session = (Session) em.getDelegate();
		sf = session.getSessionFactory();
		if (sf == null)
			sf = session.getSessionFactory();
		return sf;
	}

	/**
	 * 截取分析HQL，把conditionMap的参数值，按照命名参数出现的顺序转换成Object[]
	 *
	 */
	private Object[] mapToArray(final String hql, final Map<String, Object> conditionMap) {
		String tmp = " " + hql + " ";
		Object[] param = new Object[conditionMap.size()];
		int i = 0;
		while (true) {
			int start = tmp.indexOf(':');
			if (start < 0)
				break;
			tmp = tmp.substring(start + 1);
			int end = tmp.indexOf(' ');
			String key;
			if (end < 0) {
				key = tmp.substring(0, tmp.length());
			} else {
				key = tmp.substring(0, end);
			}
			int m = key.indexOf(')');
			if (m > 0) {
				key = key.substring(0, m);
			}
			param[i++] = conditionMap.get(key);
		}
		return param;
	}

	/**
	 * 对SQL中的"?"的后面加上序号,比如?1,?2,?3
	 *
	 */
	private String improveSql(String sql) {
		sql = " " + sql + " ";
		String[] split = sql.split("[?]");
		for (int i = 0; i < split.length; i++) {
			if (i != split.length - 1)
				split[i] += " ?" + (i + 1) + " ";
		}
		return toStr(split);
	}

	/**
	 * 数组转成String，数组中的元素仅仅进行平铺，不进行任何分割
	 *
	 */
	private String toStr(Object[] param) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < param.length; i++) {
			sb.append(param[i].toString());
		}
		return sb.toString();
	}

	private Object getSingleResult(Query query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
