package com.yingu.poc.enums;

/**
 * 
 * 
 *
 * Description:
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月21日下午5:24:04    tianye       V1.0        
 * </pre>
 */
public enum PaymentType {
	DEBIT("代扣"),
	PAY("代付");
	
	private String desc;
	
	private PaymentType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc(){
		return desc;
	}
}
