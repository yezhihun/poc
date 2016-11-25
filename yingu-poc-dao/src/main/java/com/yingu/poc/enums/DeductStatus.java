package com.yingu.poc.enums;

/**
 * 
 * 
 *
 * Description: 代扣状态
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月8日下午4:30:06    tianye       V1.0        
 * </pre>
 */
public enum DeductStatus {

	WCL(1, "未处理"),
	CLZ(2, "处理中"),
	CLCG(3, "代扣成功"),
	CLSB(4, "代扣失败");
	
	private int val;
	private String desc;
	
	DeductStatus(int val, String desc){
		this.val = val;
		this.desc = desc;
	}
	
	public int getVal(){
		return val;
	}
	
	public String getDesc(){
		return desc;
	}

	public static DeductStatus getEnum(int val){
		for (DeductStatus deductStatus : DeductStatus.values()) {
			if (deductStatus.val == val) {
				return deductStatus;
			}
		}
		return null;
	}
}
