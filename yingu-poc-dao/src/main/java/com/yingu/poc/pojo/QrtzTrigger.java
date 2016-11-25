package com.yingu.poc.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * 
 *
 * Description: quartz 对应类
 *
 * @author tianye
 * @version V1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年10月14日上午10:28:41    tianye       V1.0
 * </pre>
 */
@Entity(name = "qrtz_triggers")

public class QrtzTrigger {
	@Id
	@Column(name = "id", unique = false, nullable = false)
	private int id;
	
	@Column(name = "SCHED_NAME")
	private String schedName; //
	@Column(name = "TRIGGER_NAME")
	private String triggerName; // 定时器名称
	@Column(name = "TRIGGER_GROUP")
	private String triggerGroup; // 定时器组
	@Column(name = "JOB_NAME")
	private String jobName; // 任务执行时间的lang型 + trigger
	@Column(name = "JOB_GROUP")
	private String jobGroup; // 任务的组
	@Column(name = "DESCRIPTION")
	private String desciption; // 任务详细
	@Column(name = "NEXT_FIRE_TIME")
	private long nextFireTime; // 下次执行时间
	@Column(name = "PREV_FIRE_TIME")
	private long prevFireTime; // 上次执行时间
	@Column(name = "START_TIME")
	private long startTime; // 开始时间
	@Column(name = "PRIORITY")
	private int priority; //
	@Column(name = "TRIGGER_STATE")
	private String triggerStatus; // 定时任务状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public long getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public long getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(String triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

}
