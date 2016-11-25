package com.yingu.poc.common.base;

import java.util.List;

@SuppressWarnings("rawtypes")
public class DataMsg {

	//总记录数
	private Long total;
	
	//结果集
	private List rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
