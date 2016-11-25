package com.yingu.poc.service;

import java.math.BigDecimal;
import java.util.List;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.service.BaseService;
import com.yingu.poc.pojo.MonthlyRepayment;

/**
 * 
 * 
 *
 * Description: 月还服务层
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月7日上午9:48:05    tianye       V1.0        
 * </pre>
 */
public interface MonthlyRepaymentService extends BaseService<MonthlyRepayment>{
	
	/**
	 * 
	* @Author tianye
	* @Description: 同步月还数据 
	* @return void
	* @throws
	 */
	public void insertOrUpdate(MonthlyRepayment repayment);

	/**
	 * 
	* @Author tianye
	* @Description: 推送至支付平台队列
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public void doRepayment(MonthlyRepayment repayment);
	
	/**
	 * 
	* @Author tianye
	* @Description: 推送至支付平台队列 批量
	* @return void
	* @throws
	 */
	public void doBatchRepayment(List<MonthlyRepayment> repaymentList);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询可进行月还的数据 
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public List<MonthlyRepayment> findForRepayment(String date);
	
	/**
	 * 
	* @Author tianye
	* @Description: 代扣回调
	* @return void
	* @throws
	 */
	public void doCallBackForRepayment(String jsonStr);
	
	/**
	 * 
	* @Author tianye 
	* @Description: 批量更新或者插入
	* @return void
	* @throws
	 */
	public void batchInsertOrUpdate(List<MonthlyRepayment> list);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询分页数据
	* @return void
	* @throws
	 */
	public PageModel findListByPage(MonthlyRepayment repayment, PageModel page);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询报表数据
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public List<MonthlyRepayment> findListForExport(MonthlyRepayment repayment);
	
	/**
	 * 
	* @Author tianye
	* @Description: 总金额
	* @return Long
	* @throws
	 */
	public BigDecimal totalMoneySum(MonthlyRepayment repayment);
	
}
