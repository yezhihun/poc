package com.yingu.poc.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PushData")
public class MonthlyRepaymentResponse {

	private String orderNum;
	
	private Integer status;
	
	private BigDecimal deductionAmount;
	
	private Date payTime;
	
	private String reId;
	
//	private String plantformType;
	private String payChannel;

	private String requestId;
	
	public MonthlyRepaymentResponse(){
		
	}
	
	public MonthlyRepaymentResponse(MonthlyRepayment repayment){
		this.orderNum = repayment.getOrderNum();
		this.status = repayment.getDeductStatus();
		this.deductionAmount = repayment.getPayAmounts();
		this.payTime = repayment.getDeductTime();
		this.reId = repayment.getReId();
		this.payChannel = repayment.getPayChannel();
		this.requestId = repayment.getRequestId();
	}

	@XmlElement(name="OrderNum")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@XmlElement(name="Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@XmlElement(name="DeductionAmount")
	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	@XmlElement(name="PayTime")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@XmlElement(name="ReId")
	public String getReId() {
		return reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}

	@XmlElement(name="payChannel")
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

//	public String getPayChannel() {
//		return plantformType;
//	}
//
//	public void setPayChannel(String plantformType) {
//		this.plantformType = plantformType;
//	}

	@XmlElement(name="requestId")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
