package com.yingu.poc.webservice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yingu.poc.common.utils.Constant;
import com.yingu.poc.common.utils.DateUtil;
import com.yingu.poc.common.utils.HttpUtil;
import com.yingu.poc.common.utils.JaxbUtil;
import com.yingu.poc.enums.DeductStatus;
import com.yingu.poc.mq.MessageSender;
import com.yingu.poc.pojo.MonthlyRepayment;
import com.yingu.poc.pojo.MonthlyRepaymentResponse;
import com.yingu.poc.pojo.OmLog;
import com.yingu.poc.service.MonthlyRepaymentService;
import com.yingu.poc.service.OmLogService;
import com.yingu.poc.service.impl.MonthlyRepaymentServiceImpl;
import com.yingu.poc.webservice.CallOmWebService;

@Service
public class CallOmWebServiceImpl implements CallOmWebService{

	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private MonthlyRepaymentService monthlyRepaymentService;
	
	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private OmLogService omLogService;
	
	@Value("${om.allData.url}")
	private String omAllDataUrl;
	
	@Value("${om.repayData.url}")
	private String omRepayData;
	
	@Value("${om.pushData.url}")
	private String omPushData;
	
	@Override
	public void synMonthlyRepaymentData() {
		String date = DateUtil.getRepayDate();
		synMonthlyRepaymentData(date);
	}
	
	@Override
	public void synMonthlyRepaymentData(String date) {
		String xmlData = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		System.out.println("syn data begin--------------"+sdf.format(new Date()));
		log.info("syn data begin--------------"+sdf.format(new Date()));
		try {
			/**
			 * 		1,请求C#，发起月还,同步月还数据
			 */
			xmlData = HttpUtil.sendGet(omAllDataUrl+"/"+date, "");
			System.out.println("syn data end------------"+sdf.format(new Date()));
			log.info("syn data end------------"+sdf.format(new Date()));
		} catch (Exception e) {
			log.error("接口同步数据失败");
			e.printStackTrace();
		}
		
		OmLog omLog = new OmLog();
		boolean flag = true;
		try{
			List<MonthlyRepayment> list = convertData(xmlData);
			System.out.println("convert data --------------"+sdf.format(new Date()));
			log.info("convert data --------------"+sdf.format(new Date()));
			for(MonthlyRepayment repayment :list){
				//储存至数据库
				repayment.setCreateTime(new Date());
				repayment.setGlobalId(Constant.GLOBAL_ID);
				repayment.setDeductStatus(DeductStatus.WCL.getVal());
				
			}
			monthlyRepaymentService.batchInsertOrUpdate(list);
			System.out.println(" MonthlyRepayment insert data count is "+ list.size() + " -----------"+sdf.format(new Date()));
			log.info(" MonthlyRepayment insert data count is "+ list.size() + " -----------"+sdf.format(new Date()));
					
		}catch(Exception e){
			System.out.println("exception------------"+sdf.format(new Date()));
			log.error(e);
			e.printStackTrace();
			flag = false;
		}finally{
			omLog.setData(xmlData);
			omLog.setInterfaceDesc("同步还款数据接口");
			omLog.setOperTime(new Date());
			omLog.setOperStatus(flag?1:0);
			
//			omLogService.insert(omLog);
		}
	}
	
	/**
	 * 
	* @Author tianye
	* @Description: 
	* @return List<MonthlyRepayment>
	* @throws
	 */
	private List<MonthlyRepayment> convertData(String xmlData){
		List<MonthlyRepayment> list = JaxbUtil.converyToJavaBean("ArrayOfRepayData", xmlData, MonthlyRepayment.class);
		return list;
	}

	@Override
	public void endMonthlyRepayment(List<MonthlyRepaymentResponse> list) {
		/**
		 *  1,同步月还结果
		 */
		String xmlStr = JaxbUtil.convertToXml(list, "ArrayOfPushData", MonthlyRepaymentResponse.class);
		
		String listRes = "";
		for(int i=0; i<list.size(); i++){
			listRes = listRes + list.get(i).getReId() + ",";
		}
		log.info("list of reId--------------------"+listRes);
		try{
			String result = HttpUtil.sendPost(omPushData, xmlStr);
			log.info("sentPost result is "+result);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
		}
	}
	
	@Override
	public void doRepayment(){
		String date = DateUtil.getRepayDate();
		doRepayment(date);
	}
	
	@Override
	public void doRepayment(String date) {
		// 推送信息至mq队列
		MonthlyRepaymentServiceImpl.messageCount=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		List<MonthlyRepayment> listRepayment = monthlyRepaymentService.findForRepayment(date);
		System.out.println("doRepayment begin,size is "+listRepayment.size()+" -------------- "+sdf.format(new Date()));
		log.info("doRepayment begin,size is "+listRepayment.size()+" -------------- "+sdf.format(new Date()));
		monthlyRepaymentService.doBatchRepayment(listRepayment);
		log.info("send message begin -------------- "+sdf.format(new Date()));
		System.out.println("send message end -------------- "+sdf.format(new Date()));
		for(int i=0;i<listRepayment.size();i++){
			MonthlyRepayment repayment = listRepayment.get(i);
			messageSender.sendMessage(repayment);
		}
		log.info("send message end -------------- "+sdf.format(new Date()));
		System.out.println("doRepayment end -------------- "+sdf.format(new Date()));
		log.info("doRepayment end -------------- "+sdf.format(new Date()));
	}

}
