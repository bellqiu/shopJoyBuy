package com.spshop.service.intf;

import java.io.Serializable;
import java.util.List;

import com.spshop.dao.intf.BaseDAO;
import com.spshop.model.Component;

public interface BaseService<T extends Component, D extends BaseDAO<T, ID>, ID extends Serializable> {

    T findById(ID id);

    T fetchById(ID id);

    List<T> findAll();

    List<T> findPageByPage(int firstResult, int maxResults);

    List<T> findByExample(T exampleInstance, String... excludeProperty);

    T save(T entity);
    
    T merge(T entity);
    
    T persist(T entity);

    void delete(T entity);

    void delete(ID id);
    
    void flush();
    
    void evict(T entity);

}
