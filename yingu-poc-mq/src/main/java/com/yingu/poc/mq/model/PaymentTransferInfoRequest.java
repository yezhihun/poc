
package com.yingu.poc.mq.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.yingu.poc.enums.PaymentType;
import com.yingu.poc.pojo.MonthlyRepayment;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="globalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signingName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userMobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankDetailed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="monthMount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="repayDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "globalId",
    "correlationId",
    "orderNum",
    "signingName",
    "userCard",
    "userMobile",
    "bankCode",
    "bankName",
    "bankDetailed",
    "bankCard",
    "monthMount",
    "paymentType",
//    "payChannel",
    "repayDate"
})
@XmlRootElement(name = "paymentTransferInfoRequest")
public class PaymentTransferInfoRequest {

    @XmlElement(required = true)
    protected String globalId;
    @XmlElement(required = true)
    protected String orderNum;
    @XmlElement(required = true)
    protected String signingName;
    @XmlElement(required = true)
    protected String userCard;
    @XmlElement(required = true)
    protected String userMobile;
    @XmlElement(required = true)
    protected String bankCode;
    @XmlElement(required = true)
    protected String bankName;
    @XmlElement(required = true)
    protected String bankDetailed;
    @XmlElement(required = true)
    protected String bankCard;
    @XmlElement(required = true)
    protected BigDecimal monthMount;
    @XmlElement(required = true)
    protected String repayDate;
    @XmlElement(required = true)
    protected String correlationId;
//    @XmlElement(required = true)
//    protected String payChannel;
    @XmlElement(required = true)
    protected String paymentType;
    
    public PaymentTransferInfoRequest(){
    	
    }
    
    public PaymentTransferInfoRequest(MonthlyRepayment repayment) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.bankCard = repayment.getBankCard();
		this.bankCode = repayment.getBankCode();
		this.bankDetailed = repayment.getBankDetailed();
		this.bankName = repayment.getBankName();
		this.globalId = repayment.getGlobalId();
		this.monthMount = repayment.getPayAmounts();
		this.orderNum = repayment.getOrderNum();
		this.repayDate = sdf.format(repayment.getRepayDate());
		this.signingName = repayment.getUserName();
		this.userCard = repayment.getUserCard();
		this.userMobile = repayment.getUserMobile();
		this.correlationId = repayment.getId()+"";
//		this.payChannel = repayment.getPlantformType();
		this.paymentType = PaymentType.DEBIT.toString();
	}
    
//    public String getPayChannel() {
//		return payChannel;
//	}
//
//	public void setPayChannel(String payChannel) {
//		this.payChannel = payChannel;
//	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getCorrelationId(){
    	return correlationId;
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

    /**
     * Gets the value of the signingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigningName() {
        return signingName;
    }

    /**
     * Sets the value of the signingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigningName(String value) {
        this.signingName = value;
    }

    /**
     * Gets the value of the userCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCard() {
        return userCard;
    }

    /**
     * Sets the value of the userCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCard(String value) {
        this.userCard = value;
    }

    /**
     * Gets the value of the userMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * Sets the value of the userMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserMobile(String value) {
        this.userMobile = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the bankDetailed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankDetailed() {
        return bankDetailed;
    }

    /**
     * Sets the value of the bankDetailed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankDetailed(String value) {
        this.bankDetailed = value;
    }

    /**
     * Gets the value of the bankCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * Sets the value of the bankCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCard(String value) {
        this.bankCard = value;
    }

    /**
     * Gets the value of the monthMount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthMount() {
        return monthMount;
    }

    /**
     * Sets the value of the monthMount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthMount(BigDecimal value) {
        this.monthMount = value;
    }

    /**
     * Gets the value of the repayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepayDate() {
        return repayDate;
    }

    /**
     * Sets the value of the repayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepayDate(String value) {
        this.repayDate = value;
    }

}
