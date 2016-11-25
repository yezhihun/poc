package com.yingu.poc.job;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yingu.poc.service.QrtzTriggerService;

/**
 * 
 * 
 *
 * Description: 全局监听
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年11月2日上午10:25:17    tianye       V1.0        
 * </pre>
 */
@Component
public class TriggerListenerForBackup implements TriggerListener{
    @Autowired
    private QrtzTriggerService qrtzTriggerService;

	@Override
	public String getName() {
		return "TriggerListenerForBackupName";
	}

	/**
	 * 开始执行
	 */
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		System.out.println("triggerFired");
		try{
			qrtzTriggerService.createBackupJob(trigger.getJobKey().getName());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("vetoJobExecution");
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		System.out.println("triggerMisfired");
		
	}

	/**
	 * 执行完成
	 */
	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		System.out.println("triggerComplete");
		
	}

}
