package com.yingu.poc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.service.impl.AbstractBaseServiceImpl;
import com.yingu.poc.dao.QrtzTriggerDao;
import com.yingu.poc.pojo.QrtzTrigger;
import com.yingu.poc.service.QrtzTriggerService;

@Service
public class QrtzTriggerServiceImpl extends AbstractBaseServiceImpl<QrtzTrigger> implements QrtzTriggerService{

	@Autowired
	private QrtzTriggerDao qrtzTriggerDao;
	
	@Autowired
	private SchedulerFactoryBean scheduler;
	
	@Override
	@PostConstruct
	public void init() {
		this.baseDao = qrtzTriggerDao;
	}
	
	@Override
	public List<Map<String, Object>> findJobList() {
		return changeTriggerTime(qrtzTriggerDao.findList());
	}

	/**
	 * 根据定时任务名称修改 执行时间
	 * @param cron 执行时间 表达式
	 * @param triggerName 任务名称
	 */
	@Override
	public void resetCronByTirggerName(String cron, String triggerName) {
		try{
			if(CronExpression.isValidExpression(cron)){
				Scheduler sch = scheduler.getScheduler();

				TriggerKey triggerKey = new TriggerKey(triggerName);
				CronTrigger trigger = (CronTrigger)sch.getTrigger(triggerKey);
				CronScheduleBuilder schBuilder = CronScheduleBuilder.cronSchedule(cron);
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(schBuilder).startNow().build();
				sch.rescheduleJob(triggerKey, trigger);
				
				TriggerKey key = new TriggerKey(triggerName);
				sch.pauseTrigger(key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void startJob(String triggerName, String jobName) throws Exception {
//		TriggerKey key = new TriggerKey(triggerName);
		Scheduler sch = scheduler.getScheduler();
		
		try {
			/**
			 * resume 会导致任务从当前的nextTime开始执行任务，如果当前时间在nextTime之后则会立即执行一次
			 * 固采用重新build的方式并同时指定startTime，否则startTime为初始时间，nextTime会根据startTime 计算
			 */
			
			TriggerKey triggerKey = new TriggerKey(triggerName);
			CronTrigger trigger = (CronTrigger)sch.getTrigger(triggerKey);
			CronScheduleBuilder schBuilder = CronScheduleBuilder.cronSchedule(trigger.getCronExpression()).withMisfireHandlingInstructionDoNothing();
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(schBuilder).startNow().build();
			sch.rescheduleJob(triggerKey, trigger);
			
//			sch.resumeTrigger(key);
//			sch.resumeJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerException("当前任务已过期，请修改执行时间");
		}
	}

	@Override
	public void pauseJob(String triggerName, String jobName) {
		TriggerKey key = new TriggerKey(triggerName);
		JobKey jobKey = new JobKey(jobName);
		Scheduler sch = scheduler.getScheduler();
		try {
			sch.pauseTrigger(key);
			sch.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createSimpleJob(String desc, String cron, Class<? extends Job> clazz) throws SchedulerException {
		Scheduler sch = scheduler.getScheduler();
		long date = new Date().getTime();
		String triggerName = date +"-trigger";
		String jobName = date+"-job";
		TriggerKey triggerKey = new TriggerKey(triggerName);
		CronScheduleBuilder csb=CronScheduleBuilder.cronSchedule(cron);
	
		JobDetail jobDetail = JobBuilder
				.newJob(clazz) // 绑定执行任务的类
				.withIdentity(jobName)
				.withDescription(desc).storeDurably().build();
		
		CronTrigger trigger = TriggerBuilder.newTrigger().
												withDescription(desc).
												withIdentity(triggerKey).
												withSchedule(csb).forJob(jobDetail).startNow().build();
		
		sch.scheduleJob(jobDetail, trigger);
		
	}

	@Override
	public void discardJob(String jobName) {
		Scheduler sch = scheduler.getScheduler();
		JobKey jobKey = new JobKey(jobName);
		try {
			if(sch.checkExists(jobKey)){
				sch.deleteJob(jobKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageModel findListByPage(Map<String, Object> param,
			PageModel page) {
		List<Map<String,Object>> list1 = qrtzTriggerDao.findListByPage(param, page);
		List<Map<String,Object>> list = changeTriggerTime(list1);
		Long count = qrtzTriggerDao.findCountPage(param);
		page.setRows(list);
		page.setTotal(count);
		return page;
	}
	@Override
	public void createBackupJob(String jobName) {
		qrtzTriggerDao.insertBackupData(jobName);
	}
	
	@Override
	public void deleteBackupJob(String jobName) {
		qrtzTriggerDao.deleteBackupData(jobName);
	}
//
//	@Override
//	public void updateBackupJob(String date, String triggerName) {
//		qrtzTriggerDao.updateBackupData(date, triggerName);
//	}
	
	private List<Map<String, Object>> changeTriggerTime(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			map.replace("TRIGGER_STATE", null, "DONE");
		}
		return list;
	}
}
