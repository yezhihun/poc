package com.yingu.poc.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobLaunch {
	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring/spring-application.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("monthlyRepaymentJob");
        String date = "2016-10-30";
        try {
            /* 运行Job */
            JobExecution result = launcher.run(job, new JobParametersBuilder().addString("date", date).toJobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
