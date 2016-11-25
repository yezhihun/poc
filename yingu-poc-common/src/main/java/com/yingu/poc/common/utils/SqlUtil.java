package com.yingu.poc.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * 
 *
 * Description:
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月13日上午11:11:53    tianye       V1.0        
 * </pre>
 */
public class SqlUtil {

	/**
	 * 
	* @Author tianye
	* @Description: 通过反射进行新老数据合并
	* @return T
	* @throws
	 */
	public static <T> T unionObj(T oldObj, T newObj) {
		if(!newObj.getClass().equals(oldObj.getClass())){
			Exception e = new Exception("类型不一致！");
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		Class<?> demo = newObj.getClass();
		Field[] field = demo.getDeclaredFields();
		for(int i=0;i<field.length;i++){
			Class<?> type = field[i].getType();
			String fieldName = field[i].getName();
			fieldName = fieldName.replaceFirst(fieldName.charAt(0)+"", (fieldName.charAt(0)+"").toUpperCase());
			
			Object newVal = getter(newObj,fieldName);
			Object oldVal = getter(oldObj,fieldName);
			if(newVal==null ){
				continue;
			}
			if(!newVal.equals( oldVal )){
				setter(oldObj, fieldName, newVal, type);
			}
		}
		
		return oldObj;
	}
	
	public static Object getter(Object obj, String att){
		try {
			Method method = obj.getClass().getMethod( "get" + att );
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setter(Object obj, String att, Object value, Class<?> type){
		
		try {
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
