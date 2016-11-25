package com.yingu.poc.enums;

/**
 * 
 * 
 *
 * Description: 渠道
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月30日上午9:42:20    tianye       V1.0        
 * </pre>
 */
public enum PlantformType {

	YL(1,"银联"),
	FY(2,"富有"),
	YQZL(3,"银企直连"),
	PAYEASE(4,"首信易"),
	YSB(5,"银生宝"),
	YJF(6,"一九付"),
	FF(7,"丰付"),
	CPCNPAY(8,"中金"),
	CHANPAY(9, "畅捷"),
	ATOBOPAY(10, "金运通");
	
	private int value;
	private String desc;
	
	private PlantformType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static PlantformType createByValue(String value){
		for (PlantformType plantformType : PlantformType.values()) {
			if (plantformType.toString().equals(value)) {
				return plantformType;
			}
		}
		return null;
	}
}
