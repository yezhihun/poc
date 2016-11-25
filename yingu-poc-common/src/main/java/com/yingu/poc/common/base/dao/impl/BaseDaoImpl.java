package com.yingu.poc.common.base.dao.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yingu.poc.common.utils.SqlUtil;


/**
 * 
 * 
 *
 * Description: 数据层基础实现类
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月13日上午9:43:50    tianye       V1.0        
 * </pre>
 */
public abstract class BaseDaoImpl<T> {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@PersistenceUnit
	protected EntityManagerFactory emf;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public void update(T t){
		Query q = null;
		try {
			q = createSqlForUpdate(t);
			q.executeUpdate();
		} catch (Exception e) {
			log.error("创建SQL失败:"+e);
			e.printStackTrace();
		}
	}
	
	public void update(T oldObj, T newObj){
		Query q = null;
		try {
			q = createMustSqlForUpdate(oldObj, newObj);
			q.executeUpdate();
		} catch (Exception e) {
			log.error("创建SQL失败:"+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	* @Author tianye
	* @Description: 自动创建UPDATE语句，已主键为条件 
	* @return String
	* @throws
	 */
	private Query createMustSqlForUpdate(T oldObj, T newObj) throws Exception{
		String tableName = "";
		Class<?> demo = oldObj.getClass();
		Entity[] e = demo.getAnnotationsByType(Entity.class);
		for(Entity en : e){
			tableName = en.name();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder(" UPDATE " + tableName + " SET ");
		StringBuilder setter = new StringBuilder();
		StringBuilder where = new StringBuilder(" WHERE ");
		Field[] field = demo.getDeclaredFields();
		for(int i=0;i<field.length;i++){
			Field f = field[i];
			String fieldName = field[i].getName();
			
			String methodFieldName = fieldName.replaceFirst(fieldName.charAt(0)+"", (fieldName.charAt(0)+"").toUpperCase());
			
			Object oldVal = SqlUtil.getter(oldObj,methodFieldName);
			Object newVal = SqlUtil.getter(newObj,methodFieldName);
			if(f.isAnnotationPresent(Id.class)){
				if(oldVal==null){
					throw new Exception("主键不可为空！");
				}
				where.append( fieldName + "=" + oldVal);
			}else{
				if(newVal==null ){
					continue;
				}
				setter.append(fieldName + "=:" + fieldName + ",");
				map.put(fieldName, newVal);
			}
			
		}
		if(StringUtils.isBlank(setter.toString())){
			throw new Exception("数据项不可全部为空!");
		}
		
		sql.append(setter.substring(0, setter.lastIndexOf(","))).append(where);
		Query q = entityManager.createQuery(sql.toString());
		
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, Object> key = it.next();
			q.setParameter(key.getKey(), key.getValue());
		}
		return q;
	}
	
	/**
	 * 
	* @Author tianye
	* @Description: 自动创建UPDATE语句，已主键为条件
	* @return String
	* @throws
	 */
	private Query createSqlForUpdate(T t) throws Exception{
		String tableName = "";
		Class<?> demo = t.getClass();
		Entity[] e = demo.getAnnotationsByType(Entity.class);
		for(Entity en : e){
			tableName = en.name();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder(" UPDATE " + tableName + " SET ");
		StringBuilder setter = new StringBuilder();
		StringBuilder where = new StringBuilder(" WHERE ");
		Field[] field = demo.getDeclaredFields();
		for(int i=0;i<field.length;i++){
			Field f = field[i];
			String fieldName = field[i].getName();
			
			String methodFieldName = fieldName.replaceFirst(fieldName.charAt(0)+"", (fieldName.charAt(0)+"").toUpperCase());
			
			Object val = SqlUtil.getter(t,methodFieldName);
			if(f.isAnnotationPresent(Id.class)){
				if(val==null){
					throw new Exception("主键不可为空！");
				}
				where.append( fieldName + "=" + val);
			}else{
				if(val==null ){
					continue;
				}
				setter.append(fieldName + "=:" + fieldName + ",");
				map.put(fieldName, val);
			}
			
		}
		if(StringUtils.isBlank(setter.toString())){
			throw new Exception("数据项不可全部为空!");
		}
		
		sql.append(setter.substring(0, setter.lastIndexOf(","))).append(where);
		Query q = entityManager.createQuery(sql.toString());
		
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, Object> key = it.next();
			q.setParameter(key.getKey(), key.getValue());
		}
		return q;
	}
	
	public void batchSaveOrUpdate(List<T> list){
		int size = list.size();
		for(int i=0;i<size;i++){
			T t = list.get(i);
			Class<?> demo = t.getClass();
			Field[] field = demo.getDeclaredFields();
			int length = field.length;
			for(int j=0;j<length;i++){
				Field f = field[j];
				String fieldName = field[j].getName();
				
				String methodFieldName = fieldName.replaceFirst(fieldName.charAt(0)+"", (fieldName.charAt(0)+"").toUpperCase());
				
				Object val = SqlUtil.getter(t,methodFieldName);
				if(f.isAnnotationPresent(Id.class)){
					if(val==null){
						entityManager.persist(t);
					}else{
						entityManager.merge(t);
					}
					break;
				}
			}
			if(i%1000 == 0){
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.flush();
		entityManager.clear();
	}
	
	public void batchSave(List<T> list){
		for(int i=0;i<list.size();i++){
			entityManager.persist(list.get(i));
			if(i%100 == 0){
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.flush();
		entityManager.clear();
	}
	
	public void batchUpdate(List<T> list){
		for(int i=0;i<list.size();i++){
			entityManager.merge(list.get(i));
			if(i%1000 == 0){
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.flush();
		entityManager.clear();
	}
}
