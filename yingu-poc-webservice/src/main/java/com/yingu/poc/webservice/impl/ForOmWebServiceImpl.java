package com.yingu.poc.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yingu.poc.pojo.MonthlyRepayment;
import com.yingu.poc.service.MonthlyRepaymentService;
import com.yingu.poc.webservice.ForOmWebService;

@Component
@Path("/forOm")
public class ForOmWebServiceImpl implements ForOmWebService{

	@Autowired
	private MonthlyRepaymentService monthlyRepaymentService;
	
	@GET
	@Path("/sayHello")
	public String sayHello(){
		return "hello";
	}
	
	@Override
	public String syncRepaymentData(String xmlData) {
		/**
		 * TODO 1,接收月还数据
		 * 2,更新至数据库
		 */
		//接收参数为xml格式，转换为entity
		List<MonthlyRepayment> list = convertData(xmlData);
		for(MonthlyRepayment repayment :list){
			//储存至数据库
			monthlyRepaymentService.insert(repayment);
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 推送信息至mq队列
//				List<MonthlyRepayment> list = monthlyRepaymentService.findForRepayment();
//				for(MonthlyRepayment repayment : list){
//					monthlyRepaymentService.doRepayment(repayment);
//				}
				
			}
		}).start();
		return null;
	}
	
	/**
	 * 
	* @Author tianye
	* @Description: 
	* @return List<MonthlyRepayment>
	* @throws
	 */
	private List<MonthlyRepayment> convertData(String xmlData){
		List<MonthlyRepayment> list = new ArrayList<MonthlyRepayment>();
		//TODO 数据转换
		return list;
	}

	@Override
	public String reRepayment() {
		/**
		 * TODO 1,查询需要代扣的操作
		 * 2,整理推送至支付平台
		 */
		return null;
	}

}
