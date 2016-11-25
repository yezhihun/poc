package com.yingu.poc.enums;

public enum TriggerStatus {

	WAITING("等待"),
	PAUSED("暂停"),
	ACQUIRED("正常执行"),
	BLOCKED("阻塞"),
	ERROR("错误");
	
	private String desc;
	TriggerStatus(String desc){
		this.desc = desc;
	}
	
	public String getDesc(){
		return this.desc;
	}
}
