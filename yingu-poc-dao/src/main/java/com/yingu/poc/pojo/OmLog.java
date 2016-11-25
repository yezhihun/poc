package com.yingu.poc.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yingu.poc.common.base.BaseEntity;

@Entity(name="om_log")
public class OmLog extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 接口数据
	 */
	private String data;
	
	/**
	 * 接口描述
	 */
	@Column(name="interface_desc")
	private String interfaceDesc;
	
	/**
	 * 时间
	 */
	@Column(name="oper_time")
	private Date operTime;
	
	/**
	 * 接口状态
	 */
	@Column(name="oper_status")
	private Integer operStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getInterfaceDesc() {
		return interfaceDesc;
	}

	public void setInterfaceDesc(String interfaceDesc) {
		this.interfaceDesc = interfaceDesc;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	
}
