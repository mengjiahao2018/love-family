package com.love.family.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("myBaseHibernateQueryDao")
public class MyBaseHibernateQueryDao<E extends Serializable> {
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 新增
	 * 
	 * @param entity
	 */
	public <T> void insert(T entity) {
		em.persist(entity);
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public <T> void delete(T entity) {
		em.remove(entity);
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public <T> void update(T entity) {
		em.merge(entity);
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeSqlQuery(String sql,
			Map<String, Object> params) {
		Query query = em.createNativeQuery(sql);	
		
		List<Map<String, Object>> list = null;
		setParameters(query,params,null);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.getResultList();
		return list;
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int executeSqlQueryCount(String sql, Map<String, Object> params) {
		Query query = em.createNativeQuery(sql);
		List<Map<String, Object>> list = null;
		setParameters(query,params,null);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.getResultList();
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		return list.size();
	}

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 * @param params
	 * @param classz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> executeHqlQuery(String hql, Map<String, Object> params,
			Class<T> classz) throws Exception {
		TypedQuery<T> createQuery = em.createQuery(hql, classz);
		if (params != null) {
			for (Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if ("start".equals(key)
						&& StringUtils.isNotEmpty(value.toString())) {
					createQuery.setFirstResult(Integer.parseInt(value
							.toString()));
				} else if ("end".equals(key)
						&& StringUtils.isNotEmpty(value.toString())) {
					createQuery
							.setMaxResults(Integer.parseInt(value.toString()));
				} else if (hql.contains(":" + key)) {
					createQuery.setParameter(key, value);
				}
			}
		}
		List<T> resultList = createQuery.getResultList();
		return resultList;
	}

	/**
	 * 执行原生SQL的更新(更新、删除语句)
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSqlUpdate(String sql, Map<String, Object> params) {
		Query query = em.createNativeQuery(sql);
		setParameters(query,params,null);
		int executeUpdate = query.executeUpdate();
		return executeUpdate;
	}

	private void setParameters(Query query, Map<String, Object> map,
			Object[] values) {
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		} else if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
	}
}
