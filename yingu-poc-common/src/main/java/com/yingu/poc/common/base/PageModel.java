package com.yingu.poc.common.base;

import java.util.List;

/**
 * 
 * 
 *
 * Description: 分页组件
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月23日上午10:09:47    tianye       V1.0        
 * </pre>
 */
@SuppressWarnings("rawtypes")
public class PageModel {

	// 总记录数
	private Long total;

	// 结果集
	private List rows;

	// 当前页
	private int pageNo;

	// 每页显示多少条
	private int pageSize;

	public Long getTotal() {
		return total;
	}

	/**
	 * 取得总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return (total.intValue() + pageSize - 1) / pageSize;
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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: 取得第一页
	 * @return int
	 * @throws
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: 取得上一页
	 * @return int
	 * @throws
	 */
	public int getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: 取得下一页
	 * @return int
	 * @throws
	 */
	public int getNextPageNo() {
		if (pageNo >= getTotalPages()) {
			return getTotalPages() == 0 ? 1 : getTotalPages();
		}
		return pageNo + 1;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: 取得最后一页
	 * @return int
	 * @throws
	 */
	public int getBottomPageNo() {
		return getTotalPages() == 0 ? 1 : getTotalPages();
	}

	/**
	 * 
	 * @Author tianye
	 * @Description:每一页的起始索引
	 * @return int
	 * @throws
	 */
	public int getStartIndex() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description:每一页的结束索引
	 * @return int
	 * @throws
	 */
	public int getEndIndex() {
		return (pageNo - 1) * pageSize + pageSize;
	}
}
