package com.yingu.poc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.dao.BaseDao;
import com.yingu.poc.pojo.QrtzTrigger;

/**
 * 
 * 
 *
 * Description: 定时任务
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年10月20日下午2:55:21    tianye       V1.0        
 * </pre>
 */
@Repository
public interface QrtzTriggerDao extends BaseDao<QrtzTrigger>{

	/**
	 * 
	* @Author tianye
	* @Description: 查询全部定时任务
	* @return List<Map<String,Object>>
	* @throws
	 */
	public List<Map<String,Object>> findList();
	
	/**
	 * 
	* @Author tianye
	* @Description: 根据触发器名称查询 
	* @return Map<String,Object>
	* @throws
	 */
	public Map<String,Object> findCronTriggerByName(String name);
	
	/**
	 * 
	* @Author tianye
	* @Description: 
	* @return void
	* @throws
	 */
	public void updateCronTriggerByName(String cron,String name);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询分页数据 
	* @return List<Map<String,Object>>
	* @throws
	 */
	public List<Map<String,Object>> findListByPage(Map<String,Object> param, PageModel pageModel);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询总条数 
	* @return long
	* @throws
	 */
	public long findCountPage(Map<String,Object> param);

	/**
	 * 
	* @Author tianye
	* @Description: 插入备份定时任务
	* @throws
	 */
	public void insertBackupData(String triggerName);
	
	/**
	 * 
	* @Author tianye
	* @Description: 刪除备份定时任务
	* @throws
	 */
	public void deleteBackupData(String jobName);
//
//	
//	/**
//	 * 
//	* @Author tianye
//	* @Description: 修改备份定时任务时间
//	* @throws
//	 */
//	public void updateBackupData(String date,String name);
}
