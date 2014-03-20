package com.spshop.dao.intf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.spshop.model.Component;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;
public interface BaseDAO<T extends Component, ID extends Serializable> {
	T findById(ID id);

	T findById(ID id, boolean lock);
	
	T fetchById(ID id);
	
	com.spshop.model.query.QueryResult<Component> queryByHQL(QueryCriteria criteria);

	T fetchById(ID id, boolean lock);

	List<T> findAll();

	List<T> findPageByPage(int firstResult, int maxResults);

	List<T> findByExample(T exampleInstance, String... excludeProperty);

	T save(T entity);

	void remove(ID id);

	void remove(T entity);

	T merge(T entity);
	
	T persist(T entity);

	void setSessionFactory(SessionFactory s);
	
	void flush();
	
	void evict(T entity);
	
	@SuppressWarnings("rawtypes")
	public List queryByHQL(String hql,int start, int max);
	
	public Object queryByHQL(String hql);
	
	/*public Object queryByHQL(String hql,Object... params);*/
	
	public Object queryByHQL(String hql,int start, int max,Object... params);
	
	public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className);
	
	public Object queryByHQL(String hql,Map<String,Object> params);
	
	public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className, int start, int max);
}
