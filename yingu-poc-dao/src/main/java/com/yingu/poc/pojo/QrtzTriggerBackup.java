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
@Entity(name = "qrtz_triggers_backup")

public class QrtzTriggerBackup {
	
	@Column(name = "SCHED_NAME") // 调度程序名称
	private String schedName;
	@Id
	@Column(name = "TRIGGER_NAME") // 触发器名称
	private String triggerName;
	@Column(name = "TRIGGER_GROUP") // 触发器组
	private String triggerGroup;
	@Column(name = "JOB_NAME") // 任务名称
	private String jobName;
	@Column(name = "JOB_GROUP") // 任务组
	private String jobGroup;
	@Column(name = "DESCRIPTION") // 描述 
	private String desciption;
	@Column(name = "FIRE_TIME") // 执行时间
	private long fireTime;

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

	public long getFireTime() {
		return fireTime;
	}

	public void setFireTime(long fireTime) {
		this.fireTime = fireTime;
	}

}
