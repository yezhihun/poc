package com.yingu.poc.common.base.service;

import java.util.List;

/**
 * 
 * 
 *
 * Description: 服务层基础接口
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年8月29日下午3:07:04    Administrator       V1.0        
 * </pre>
 */
public interface BaseService<T> {

	public void deleteByPrimaryKey(Integer id);

    public void insert(T t);

    public T selectByPrimaryKey(Integer id);

    //TODO update 实现 待定
//    public int updateByPrimaryKeySelective(T t);

//    public int updateByPrimaryKey(T t);
    
    public void init();
    
    public List<T> findAll();
    
    public void batchInsert(List<T> list);
}
