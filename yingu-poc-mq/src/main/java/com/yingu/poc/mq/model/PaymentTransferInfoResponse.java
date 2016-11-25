package com.yingu.poc.mq.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"globalId", // 与支付接口的ID 原字段 applicationId
	"applicationId", // 原来的globalId
    "orderNum",
    "paymentStatus",
    "paymentType",
    "returnCode",
    "returnReason",
    "requestId",
//    "plantformType"
    "payChannel"
})
@XmlRootElement(name = "paymentReponse")
public class PaymentTransferInfoResponse {
	@XmlElement(required = true)
    protected String globalId;
    @XmlElement(required = true)
    protected String applicationId;
    @XmlElement(required = true)
    protected String orderNum;
    @XmlElement(required = true)
    protected int paymentStatus;
    @XmlElement(required = true)
    protected String paymentType;
    @XmlElement(required = true)
    protected String returnCode;
    @XmlElement(required = true)
    protected String returnReason;
    @XmlElement(required = true)
    protected String requestId;
//    @XmlElement(required = true)
//    protected String plantformType;
    @XmlElement(required = true)
    protected String payChannel;

    public String getApplicationId() {
		return applicationId;
	}
    
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


    /**
     * Gets the value of the globalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalId() {
        return globalId;
    }

    /**
     * Sets the value of the globalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalId(String value) {
        this.globalId = value;
    }

    /**
     * Gets the value of the orderNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * Sets the value of the orderNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNum(String value) {
        this.orderNum = value;
    }

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

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
}
