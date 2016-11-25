package com.yingu.poc.mq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yingu.poc.common.utils.JaxbUtil;
import com.yingu.poc.mq.model.PaymentTransferInfoRequest;
import com.yingu.poc.pojo.MonthlyRepayment;

/**
 * 
 * 
 *
 * Description: mq消息发送端
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月7日上午9:57:45    tianye       V1.0        
 * </pre>
 */
@Component
public class MessageSender {

	@Autowired
	private AmqpTemplate transferJavaTemplate;
	
	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * 
	* @Author tianye
	* @Description: 消息推送 
	* @return void
	* @throws
	 */
	public void sendMessage(PaymentTransferInfoRequest request){
//		String xmlStr = JaxbUtil.convertToXml(request);
		log.info("send Info:"+JaxbUtil.convertToXml(request));
		transferJavaTemplate.convertAndSend(request);
	}
	
	public void sendMessage(List<MonthlyRepayment> list){
		for(MonthlyRepayment repayment : list){
			this.sendMessage(payBatchToRequestInfo(repayment));
		}
	}
	
	public void sendMessage(MonthlyRepayment repayment){
		this.sendMessage(payBatchToRequestInfo(repayment));
	}
	
	private PaymentTransferInfoRequest payBatchToRequestInfo(MonthlyRepayment repayment){
		PaymentTransferInfoRequest request = new PaymentTransferInfoRequest(repayment);
		return request;
	}
}
