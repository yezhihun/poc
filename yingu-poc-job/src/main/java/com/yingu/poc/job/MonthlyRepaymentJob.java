package com.yingu.poc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 *
 * Description: 月还任务
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月7日上午10:27:30    tianye       V1.0        
 * </pre>
 */

@Component
public class MonthlyRepaymentJob extends QuartzJobBean{
	

//	@Autowired
//	private CallOmWebService callOmWebService;
	@Autowired
	private Job monthlyRepaymentJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	
	/**
	 * 
	* @Author tianye
	* @Description: 执行任务
	* @return void
	* @throws
	 */
	@Override
	public void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			jobLauncher.run(monthlyRepaymentJob, new JobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
