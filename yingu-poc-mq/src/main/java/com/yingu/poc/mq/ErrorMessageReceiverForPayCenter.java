package com.yingu.poc.mq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yingu.poc.common.utils.JaxbUtil;
import com.yingu.poc.mq.model.PaymentTransferInfoResponse;
import com.yingu.poc.service.MonthlyRepaymentService;

@Component("errorMessageReceiverForPayCenter")
public class ErrorMessageReceiverForPayCenter implements MessageListener{

	private static Logger log = Logger.getLogger(ErrorMessageReceiverForPayCenter.class);
	
	@Autowired
	private MonthlyRepaymentService monthlyRepaymentService;
	
	private List<PaymentTransferInfoResponse> convertMessage(byte[] byteRecord){
		try{
			List<PaymentTransferInfoResponse> list = JaxbUtil.converyToJavaBean("paymentTransferInfoResponse", new String(byteRecord), PaymentTransferInfoResponse.class);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void onMessage(Message message) {
		byte[] byteRecord = message.getBody();
		try{
			log.info("receive error:"+new String(byteRecord));
			//将消息转换为PaymentTransferInfoResponse
			List<PaymentTransferInfoResponse> list = convertMessage(byteRecord);
			if(list != null){
				new Thread(new Runnable() {
					@Override
					public void run() {
//						monthlyRepaymentService.doCallBackForRepayment(list);
						String jsonStr = JSON.toJSONString(list);
						monthlyRepaymentService.doCallBackForRepayment(jsonStr);
					}
				}).start();
			}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
	}
}
