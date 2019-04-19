package com.love.family.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Synchronization;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import lombok.Synchronized;

@Repository("myBaseHibernateQueryDao")
public class MyBaseHibernateQueryDao<E extends Serializable> {
	
	@PersistenceContext
	private EntityManager em;
	
	private SessionFactory sf;
	
	protected Class<E> entityClass=null;
	
	@SuppressWarnings("unchecked")
	public MyBaseHibernateQueryDao() {
		if(getClass().getGenericSuperclass() instanceof java.lang.reflect.ParameterizedType) {
			if(!(((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0] instanceof TypeVariable)) {
				entityClass =(Class<E>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			}
		}
	}
	
	private synchronized SessionFactory getSessionFactory() {
		Session session = (Session)em.getDelegate();
		sf = session.getSessionFactory();
		if(sf==null)
			sf=session.getSessionFactory();
		return sf;
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
	
	public void detachAll(Collection<E> entities) {
		for (E entity : entities) {
			em.detach(entity);
		}
	}
	
	public boolean contains(E entity) {
		return em.contains(entity);
	}
	
	public void save(E entity) {
		if(!em.contains(entity)) {
			em.merge(entity);	
		}
	}
	
	public E saveEntity(E entity) {
		if(!em.contains(entity)) {
			return em.merge(entity);
		}
		return null;
	}
	public void save(Collection<E> entities) {
		for (E entity : entities) {
			save(entity);
		}
	}
	
	public void insert(E entity) {
		em.persist(entity);
	}
	
	public void insert(Collection<E> entities) {
		for (E entity:entities) {
			insert(entity);
		}
	}
	
	public <T extends Serializable> void insert(T entity,Class <T> objType) {
		em.persist(entity);
	}
	
	public <T extends Serializable> void insert(Collection<T> entities,Class <T> objType) {
		for (T entity:entities) {
			insert(entity,objType);
		}
	}
	
	public void delete(E entity) {
		em.remove(em.contains(entity)?entity:em.merge(entity));
	}
	
	public <T extends Serializable> void delete(T entity,Class <T> objType) {
		em.remove(em.contains(entity)?entity:em.merge(entity));
	}
	
	public void delete(Object id) {
		delete(load(id));
	}
	
	public E load(Object id) {
		return em.find(entityClass, id);
	}
	
	public <T> T  load(Object id,Class <T> objType) {
		return em.find(objType, id);
	}
	
	protected E findOneEntityObject(final String hql,final Object [] values) {
		return findOneEntityObject(hql,null,values);
	}
	
	protected E findOneEntityObjectByClas(final String hql,Class clazz,final Object [] values) {
		return findOneEntityObject(hql,clazz,null,values);
	}
	
	protected E findOneEntityObject(final String hql,final Map<String,Object> conditionMap) {
		return findOneEntityObject(hql,conditionMap,null);
	}
	
	protected E findOneEntityObjectByClas(final String hql,Class clazz,final Map<String,Object> conditionMap) {
		return findOneEntityObject(hql,clazz,conditionMap,null);
	}
	
	@SuppressWarnings("unchecked")
	private E findOneEntityObject(final String hql,final Map<String,Object> map,final Object [] values) {
		if(hql!=null) {
			Query query = em.createQuery(hql,entityClass);
			setParameters(query, map, values);
			return (E)getSingleResult(query);
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private E findOneEntityObject(final String hql,Class entityClass,final Map<String,Object> map,final Object [] values) {
		if(hql!=null) {
			Query query = em.createQuery(hql,entityClass);
			setParameters(query, map, values);
			return (E)getSingleResult(query);
		}else {
			return null;
		}
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
	 * 更新
	 * 
	 * @param entity
	 */
	public <T> void update(T entity) {
		em.merge(entity);
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
	
	private Object getSingleResult(Query query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
