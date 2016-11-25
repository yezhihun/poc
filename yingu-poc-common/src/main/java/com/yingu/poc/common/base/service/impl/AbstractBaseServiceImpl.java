package com.yingu.poc.common.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yingu.poc.common.base.dao.BaseDao;
import com.yingu.poc.common.base.service.BaseService;

/**
 * 
 * 
 *
 * Description:服务层基础实现
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年8月29日下午3:07:28    Administrator       V1.0        
 * </pre>
 */
public abstract class AbstractBaseServiceImpl<T> implements BaseService<T>{

	protected BaseDao<T> baseDao;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public void deleteByPrimaryKey(Integer id){
		baseDao.delete(id);
	}

    public void insert(T t){
    	try{
    		baseDao.save(t);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }

    public T selectByPrimaryKey(Integer id){
    	return baseDao.findOne(id);
    }

    public List<T> findAll(){
    	return baseDao.findAll();
    }
    
    public void batchInsert(List<T> list){
    	baseDao.batchSave(list);
    }
}
