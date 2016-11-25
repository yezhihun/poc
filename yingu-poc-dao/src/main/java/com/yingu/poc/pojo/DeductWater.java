package com.yingu.poc.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "deduct_water")
public class DeductWater {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="global_id")
	private String globalId;

	@Column(name="application_id")
	private String applicationId;
	
	@Column(name="order_num")
	private String orderNum;
	
	@Column(name="deduct_status")
	private Integer deductStatus;
	
	@Column(name="payment_type")
	private String paymentType;
	
	@Column(name="deduct_time")
	private Date deductTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="pay_channel")
	private String payChannel;
	
	@Column(name="request_id")
	private String requestId;
	
	public DeductWater(){
		
	}

	/*public DeductWater(PaymentTransferInfoResponse response){
		this.applicationId = response.getApplicationId();
		this.orderNum = response.getOrderNum();
		this.createTime = new Date();
		this.deductStatus = response.getPaymentStatus();
		this.paymentType = response.getPaymentType();
		this.globalId = response.getGlobalId();
		this.payChannel = response.getPayChannel();
		this.requestId = response.getRequestId();
	}*/
	
	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(Integer deductStatus) {
		this.deductStatus = deductStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(Date deductTime) {
		this.deductTime = deductTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
