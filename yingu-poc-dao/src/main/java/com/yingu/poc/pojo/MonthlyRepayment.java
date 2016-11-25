package com.yingu.poc.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.yingu.poc.common.base.BaseEntity;
import com.yingu.poc.common.utils.JaxbDateSerializer;

/**
 * 
 * 
 *
 * Description: 月还数据
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description
 * ------------------------------------------------------------------ 
 * 2016年9月7日上午9:26:06    tianye       V1.0        
 * </pre>
 */
@XmlRootElement(name = "RepayData")

@Entity(name="monthly_repayment")
public class MonthlyRepayment extends BaseEntity{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	/**
	 * 全局标示
	 */
	@Column(name="global_id")
	private String globalId;
	
	@Column(name="application_id")
	private String applicationId;
	
	/**
	 * 运营平台ID
	 */
	@Column(name="re_id")
	private String reId;
	
	/**
	 * 订单号
	 */
	@Column(name="order_num")
	private String orderNum;
	
	/**
	 * 客户姓名
	 */
	@Column(name="user_name")
	private String userName;
	
	/**
	 * 证件号码
	 */
	@Column(name="user_card")
	private String userCard;
	
	/**
	 * 手机号
	 */
	@Column(name="user_mobile")
	private String userMobile;
	
	/**
	 * 银行编号
	 */
	@Column(name="bank_code")
	private String bankCode;
	
	/**
	 * 银行名称
	 */
	@Column(name="bank_name")
	private String bankName;
	
	/**
	 * 支行
	 */
	@Column(name="bank_detail")
	private String bankDetailed;
	
	/**
	 * 银行卡号
	 */
	@Column(name="bank_card")
	private String bankCard;
	
	/**
	 * 支付渠道
	 */
	@Column(name="pay_channel")
	private String payChannel;

	/**
	 * 交易类型
	 */
	@Column(name="trans_type")
	private String transType;
	
	/**
	 * 金额
	 */
	@Column(name="pay_amounts")
	private BigDecimal payAmounts;
	
	/**
	 * 证件类型
	 */
	@Column(name="id_type")
	private String idType;
	
	/**
	 * 时间
	 */
	@Column(name="repay_date")
	private Date repayDate;
	
	/**
	 * 代扣状态
	 */
	@Column(name="deduct_status")
	private Integer deductStatus;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Date createTime;
	
	/**
	 * 扣款时间
	 */
	@Column(name="deduct_time")
	private Date deductTime;
	
	/**
	 * 贷款期限
	 */
	@Column(name="loan_deadline")
	private String loanDeadline;
	
	/**
	 * 交易状态信息
	 */
	@Column(name="transaction_status_msg")
	private String transactionStatusMsg;
	
	/**
	 * 门店名称
	 */
	@Column(name="store_name")
	private String storeName;
	
	/**
	 * 门店所在城市
	 */
	@Column(name="store_city")
	private String storeCity;
	
	/**
	 * 门店区域
	 */
	@Column(name="store_area")
	private String storeArea;
	
	/**
	 * 流水号
	 */
	@Column(name="request_id")
	private String requestId;
	

	
	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	
	@XmlTransient
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@XmlElement(name="ReId")
	public String getReId() {
		return reId;
	}

	@XmlElement(name="OrderNum")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@XmlElement(name="UserName")
	public String getUserName() {
		return userName;
	}

	//不进行实例化
	@XmlTransient
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@XmlElement(name="ProcessTime")
	public Date getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(Date deductTime) {
		this.deductTime = deductTime;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name="UserCard")
	public String getUserCard() {
		return userCard;
	}

	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}

	@XmlElement(name="UserMobile")
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@XmlElement(name="BankCode")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@XmlElement(name="BankName")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@XmlElement(name="BankDetailed")
	public String getBankDetailed() {
		return bankDetailed;
	}

	public void setBankDetailed(String bankDetailed) {
		this.bankDetailed = bankDetailed;
	}

	@XmlElement(name="BankCard")
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	@XmlElement(name="PayChannel")
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@XmlElement(name="TransType")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	@XmlElement(name="PayAmounts")
	public BigDecimal getPayAmounts() {
		return payAmounts;
	}

	public void setPayAmounts(BigDecimal payAmounts) {
		this.payAmounts = payAmounts;
	}

	@XmlElement(name="IdType")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@XmlElement(name="RepayDate")
	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	@XmlElement(name="RepaymentStatus")
	public Integer getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(Integer deductStatus) {
		this.deductStatus = deductStatus;
	}
	
	@XmlElement(name="XDExtendTime")
	public String getLoanDeadline() {
		return loanDeadline;
	}

	public void setLoanDeadline(String loanDeadline) {
		this.loanDeadline = loanDeadline;
	}

	@XmlElement(name="transactionStatusMsg")
	public String getTransactionStatusMsg() {
		return transactionStatusMsg;
	}

	public void setTransactionStatusMsg(String transactionStatusMsg) {
		this.transactionStatusMsg = transactionStatusMsg;
	}

	@XmlElement(name="StoreName")
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@XmlElement(name="CityName")
	public String getStoreCity() {
		return storeCity;
	}

	public void setStoreCity(String storeCity) {
		this.storeCity = storeCity;
	}

	@XmlElement(name="AreaName")
	public String getStoreArea() {
		return storeArea;
	}

	public void setStoreArea(String storeArea) {
		this.storeArea = storeArea;
	}
	
	@XmlElement(name="request_id")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "MonthlyRepayment2 [id=" + id + ", globalId=" + globalId + ", applicationId=" + applicationId + ", reId="
				+ reId + ", orderNum=" + orderNum + ", userName=" + userName + ", userCard=" + userCard
				+ ", userMobile=" + userMobile + ", bankCode=" + bankCode + ", bankName=" + bankName + ", bankDetailed="
				+ bankDetailed + ", bankCard=" + bankCard + ", payChannel=" + payChannel + ", transType="
				+ transType + ", payAmounts=" + payAmounts + ", idType=" + idType + ", repayDate=" + repayDate
				+ ", deductStatus=" + deductStatus + ", createTime=" + createTime + ", deductTime=" + deductTime
				+ ", loanDeadline=" + loanDeadline + ", transactionStatusMsg=" + transactionStatusMsg + ", storeName="
				+ storeName + ", storeCity=" + storeCity + ", storeArea=" + storeArea + "requestId" +"]";
	}
}
