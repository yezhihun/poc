package com.yingu.poc.service;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.SchedulerException;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.service.BaseService;
import com.yingu.poc.pojo.QrtzTrigger;

public interface QrtzTriggerService extends BaseService<QrtzTrigger>{

	/**
	 * 
	* @Author tianye
	* @Description: 定时任务列表 
	* @return List<Map<String,Object>>
	* @throws
	 */
	public List<Map<String,Object>> findJobList();
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询定时任务分页数据 
	* @return PageModel
	* @throws
	 */
	public PageModel findListByPage(Map<String,Object> param, PageModel pageModel);
	
	/**
	 * 
	* @Author tianye
	* @Description: 根据定时任务名称修改 执行时间 
	* @return void
	* @throws
	 */
	public void resetCronByTirggerName(String cron, String triggerName);
	
	/**
	 * 
	* @Author tianye
	* @Description: 开始任务 
	* @return void
	* @throws
	 */
	public void startJob(String triggerName, String jobName) throws Exception;
	
	/**
	 * 
	* @Author tianye
	* @Description: 暂停任务 
	* @return void
	* @throws
	 */
	public void pauseJob(String triggerName, String jobName);
	
	/**
	 * 
	* @Author tianye
	* @Description: 手动创建任务
	* @return void
	* @throws
	 */
	public void createSimpleJob(String desc, String cron,Class<? extends Job> clazz) throws SchedulerException;
	
	/**
	 * 
	* @Author tianye
	* @Description: 删除任务
	* @return void
	* @throws
	 */
	public void discardJob(String jobName);

	/**
	 * 
	* @Author tianye
	* @Description: 手动创建备份任务
	* @return void
	* @throws
	 */
	public void createBackupJob(String jobName);

	/**
	 * 
	* @Author tianye
	* @Description: 手动刪除备份任务
	* @return void
	* @throws
	 */
	public void deleteBackupJob(String jobName);
//	
//	/**
//	 * 
//	* @Author tianye
//	* @Description: 根据定时任务名称修改 备份任务执行时间 
//	* @return void
//	* @throws
//	 */
//	public void updateBackupJob(String date, String triggerName);
}
