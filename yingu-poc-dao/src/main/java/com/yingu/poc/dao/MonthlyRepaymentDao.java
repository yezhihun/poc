package com.yingu.poc.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.dao.BaseDao;
import com.yingu.poc.pojo.MonthlyRepayment;

@Repository
public interface MonthlyRepaymentDao extends BaseDao<MonthlyRepayment>{

	/**
	 * 
	* @Author tianye
	* @Description: 根据状态查询 
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public List<MonthlyRepayment> findByDeductStatusAndRepayDate(Integer status, Date repayDate);
	
	/**
	 * 
	* @Author tianye
	* @Description: 更新状态 
	* @return void
	* @throws
	 */
	public void updateStatus(MonthlyRepayment repayment);
	
	/**
	 * 
	* @Author tianye
	* @Description: 根据correlation_id 查询 
	* @return MonthlyRepayment
	* @throws
	 */
//	public MonthlyRepayment findByCorrelationId(String correlationId);
	
	/**
	 * 
	* @Author tianye
	* @Description: 
	* @return MonthlyRepayment
	* @throws
	 */
	public MonthlyRepayment findByReId(String reId);

	/**
	 *
	 * @param reId 用","分割的id
	 * @return
	 */
	public List<MonthlyRepayment> findByReIds(String reId);

	/**
	 * 
	* @Author tianye
	* @Description: 查询分页数据
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public List<MonthlyRepayment> findListByPage(MonthlyRepayment repayment, PageModel page);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询数据总数
	* @return long
	* @throws
	 */
	public long findCountByPage(MonthlyRepayment repayment);
	
	/**
	 * 
	* @Author tianye
	* @Description: 查询导出数据
	* @return List<MonthlyRepayment>
	* @throws
	 */
	public List<MonthlyRepayment> findListForExport(MonthlyRepayment repayment);
	
	/**
	 * 
	* @Author Administrator
	* @Description: 根据ID查询
	* @param id
	* @return
	* @return MonthlyRepayment
	* @throws
	 */
	//public MonthlyRepayment findById(String id);
	
	/**
	 * 
	* @Author Administrator
	* @Description: 总金额
	* @param repayment
	* @return
	* @return Long
	* @throws
	 */
	public BigDecimal totalMoneySum(MonthlyRepayment repayment);

	/**
	 * 根据globalId 查询数据
	 * @param globalId
	 * @return
	 */
	MonthlyRepayment findByGlobalId(String globalId);
}
