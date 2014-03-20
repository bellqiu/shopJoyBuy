package com.spshop.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessResourceFailureException;

import com.spshop.model.Component;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;
public abstract class AbstractBaseDAO<T extends Component, ID extends Serializable> {

	protected final Log log = LogFactory.getLog(getClass());

	// TODO need to make this configurable
	protected final static FlushMode queryFlushMode = FlushMode.COMMIT;

	// protected StopWatch s = new StopWatch();

	private SessionFactory sessionFactory;
	private Class<T> persistentClass;

	// private Session session;

	@SuppressWarnings("unchecked")
	public AbstractBaseDAO() {
		Type type = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.persistentClass = (Class<T>) type;
	}
	
	@SuppressWarnings("rawtypes")
	public List queryByHQL(String hql,int start, int max){
		return getSession().createQuery(hql).setFirstResult(start).setMaxResults(max).list();
	}
	
	public Object queryByHQL(String hql){
	    return getSession().createQuery(hql).list();
	}
	
	public Object queryByHQL(String hql,Map<String,Object> params){
		Query q = getSession().createQuery(hql);
		for (String key : params.keySet()) {
			q.setParameter(key, params.get(key));
		}
	    return q.list();
	}
	
/*	public Object queryByHQL(String hql,Object... params){
		Query q = getSession().createQuery(hql);
		if(null!=params){
			for (int i = 0 ; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
	    return q.list();
	}
	*/
	public Object queryByHQL(String hql,int start, int max,Object... params){
		Query q = getSession().createQuery(hql).setFirstResult(start).setMaxResults(max);
		if(null!=params){
			for (int i = 0 ; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
	    return q.list();
	}
	
	@SuppressWarnings("unchecked")
    public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className){
        Query q = getSession().createQuery(hql);
        if(null!=params){
            for (int i = 0 ; i < params.size(); i++) {
                q.setParameter(i, params.get(i));
            }
        }
        List<Component> qs = q.list();
        int count = qs.size();
        QueryResult<Component> qr = new QueryResult<Component>();
        qr.setRecordCount(count);
        qr.setResult(qs);
        qr.setComponentType(className);
        
        return qr;
    }
	
	@SuppressWarnings("unchecked")
    public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className, int start, int max){
        Query q = getSession().createQuery(hql).setFirstResult(start).setMaxResults(max);
        if(null!=params){
            for (int i = 0 ; i < params.size(); i++) {
                q.setParameter(i, params.get(i));
            }
        }
        List<Component> qs = q.list();
        int count = qs.size();
        QueryResult<Component> qr = new QueryResult<Component>();
        qr.setRecordCount(count);
        qr.setResult(qs);
        qr.setComponentType(className);
        
        return qr;
    }

	//
	// @SuppressWarnings("unchecked")
	// public void setSession(Session s) {
	// this.session = s;
	// // return (DaoImpl) this;
	// }
	//
	// protected Session getSession() {
	// if (session == null)
	// throw new IllegalStateException("Session has not been set on DAO before
	// usage");
	// return session;
	// }

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	/*public List<E> findById(ID id) {
		return findById(id, false);
	}*/

	public T findById(ID id) {
		return findById(id, false);
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) sessionFactory.getCurrentSession().load(
					getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) sessionFactory.getCurrentSession().load(
					getPersistentClass(), id);

		return entity;
	}

	public T fetchById(ID id) {
		return fetchById(id, false);
	}

	@SuppressWarnings("unchecked")
	public T fetchById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) sessionFactory.getCurrentSession().get(
					getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) sessionFactory.getCurrentSession().get(
					getPersistentClass(), id);

		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		return crit.list();
	}

	public List<T> findPageByPage(int firstResult, int maxResults) {
		return findByCriteriaPageByPage(firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public T save(T entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		return entity;
	}

	public void remove(ID id) {
		T entity = findById(id, false);

		remove(entity);
	}

	public void remove(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) {
		return (T) sessionFactory.getCurrentSession().merge(entity);
	}

	public T persist(T entity) {
		sessionFactory.getCurrentSession().persist(entity);

		return entity;
	}

	protected Session getSession() throws DataAccessResourceFailureException,
			IllegalStateException {
		return new FlushModeSession(sessionFactory.getCurrentSession(),
				queryFlushMode);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteriaPageByPage(int firstResult, int maxResults,
			Criterion... criterion) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		return criteria.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	public void evict(T entity) {
		sessionFactory.getCurrentSession().evict(entity);
	}
	
	@SuppressWarnings("unchecked")
	public QueryResult<Component> queryByHQL(QueryCriteria criteria){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM");
		hql.append(" ");
		hql.append(criteria.getType());
		hql.append(" ");
		hql.append("WHERE");
		hql.append(" ");
		hql.append("name");
		hql.append(" ");
		hql.append("LIKE");
		hql.append(" ");
		hql.append(":name");
		
		hql.append(" ");
		if(null!=criteria.getStart()){
			hql.append("AND");
			hql.append(" ");
			hql.append("createDate > :start");
		}
		hql.append(" ");
		if(null!=criteria.getEnd()){
			hql.append("AND");
			hql.append(" ");
			hql.append("createDate < :end");
		}
		if(null!=criteria.getProperties()&&!criteria.getProperties().isEmpty()){
			for(String key : criteria.getProperties().keySet()){
				hql.append(" ");
				hql.append("AND");
				hql.append(" ");
				Object value = criteria.getProperties().get(key);
				if(null!=value){
					hql.append(key +"=:"+key  );
				}else{
					hql.append(key +" IS NULL" );
				}
			}
		}
		
		hql.append(" ");
		String orderBy = criteria.getOrderBy();
		if(null==criteria.getOrderBy()){
			orderBy = "id";
		}
		hql.append("ORDER BY");
		hql.append(" ");
		hql.append(orderBy);
		hql.append(" ");
		hql.append(criteria.isAsc()?"ASC":"DESC");
		
		String countHql = "select count(name) "+hql;
		Query query =  getSession().createQuery(hql.toString()).setMaxResults(criteria.getMaxResult()).setFirstResult(criteria.getStartIndex());
		Query countQuery = getSession().createQuery(countHql);
		if(null!=criteria.getStart()){
			query.setDate("start", criteria.getStart());
			countQuery.setDate("start", criteria.getStart());
		}
		if(null!=criteria.getEnd()){
			query.setDate("end", criteria.getEnd());
			countQuery.setDate("end", criteria.getStart());
		}
		
		query.setString("name", null==criteria.getKey()?"%%":"%"+criteria.getKey()+"%");
		countQuery.setString("name", null==criteria.getKey()?"%%":"%"+criteria.getKey()+"%");
		
		if(null!=criteria.getProperties()&&!criteria.getProperties().isEmpty()){
			for(String key : criteria.getProperties().keySet()){
				Object value = criteria.getProperties().get(key);
				if(null!=value){
					query.setParameter(key,value);
					countQuery.setParameter(key,value);
				}
			}
		}
		
		List<Component> rs =query.list();
		int count = Integer.valueOf(countQuery.list().get(0).toString());
		QueryResult<Component> qs = new QueryResult<Component>();
		qs.setRecordCount(count);
		qs.setResult(rs);
		qs.setComponentType(criteria.getType());
		
		return qs;
	}
	
}
