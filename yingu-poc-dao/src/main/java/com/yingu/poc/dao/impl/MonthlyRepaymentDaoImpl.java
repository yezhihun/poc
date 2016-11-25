package com.yingu.poc.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.dao.impl.BaseDaoImpl;
import com.yingu.poc.pojo.MonthlyRepayment;

/**
 * 
 * 
 *
 * Description: 月还数据层
 *
 * @author tianye
 * @version V1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月13日上午9:35:08    tianye       V1.0        
 * </pre>
 */
public class MonthlyRepaymentDaoImpl extends BaseDaoImpl<MonthlyRepayment>{

	public void updateStatus(MonthlyRepayment repayment){
		Query q = entityManager.createQuery("update monthly_repayment p set p.deductStatus=?1, p.deductTime=?2, p.payChannel=?3, p.requestId=?4 WHERE id=?5");
		q.setParameter(1, repayment.getDeductStatus());
		q.setParameter(2, repayment.getDeductTime());
		q.setParameter(3, repayment.getPayChannel());
		q.setParameter(4, repayment.getRequestId());
		q.setParameter(5, repayment.getId());
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<MonthlyRepayment> findByDeductStatusAndRepayDate(Integer status, Date repayDate){
		Query q = entityManager.createQuery("select mr from monthly_repayment mr where deductStatus=?1 AND repayDate=?2");
		q.setParameter(1, status);
		q.setParameter(2, repayDate);
		List<MonthlyRepayment> list = (List<MonthlyRepayment>)q.getResultList();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public MonthlyRepayment findByReId(String reId){
		Query q = entityManager.createQuery("select mr from monthly_repayment mr where reId=?1");
		q.setParameter(1, reId);
		List<MonthlyRepayment> list = (List<MonthlyRepayment>)q.getResultList();
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

	/**
	 *
	 * @param reId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MonthlyRepayment> findByReIds(String reId){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * ");
		sb.append(" FROM monthly_repayment mr ");
		sb.append(" WHERE mr.re_id IN ( ");
		sb.append(reId);
		sb.append(" )");
		Query query = entityManager.createNativeQuery(sb.toString(), MonthlyRepayment.class);
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public List<MonthlyRepayment> findListByPage(MonthlyRepayment repayment, PageModel page){
		String sql = "SELECT mr FROM monthly_repayment mr WHERE 1=1 " + createPageSqlForWhere(repayment) + " ORDER BY mr.id";
		Query q = entityManager.createQuery(sql);
		
		q.setFirstResult(page.getStartIndex());
		q.setMaxResults(page.getPageSize());
		List<MonthlyRepayment> list = (List<MonthlyRepayment>)q.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MonthlyRepayment> findListForExport(MonthlyRepayment repayment){
		String sql = "SELECT mr FROM monthly_repayment mr WHERE 1=1 " + createPageSqlForWhere(repayment) + " ORDER BY mr.id";
		Query q = entityManager.createQuery(sql);
		
		List<MonthlyRepayment> list = (List<MonthlyRepayment>)q.getResultList();
		return list;
	}
	
	public long findCountByPage(MonthlyRepayment repayment){
		String sql = "SELECT count(mr) FROM monthly_repayment mr WHERE 1=1 " + createPageSqlForWhere(repayment);
		Query q = entityManager.createQuery(sql);
		long count = Long.valueOf(q.getSingleResult().toString());
		
		return count;
		
	}
	
	private String createPageSqlForWhere(MonthlyRepayment repayment){
		String sql = "";
		if(repayment.getDeductStatus()!=null){
			sql += " AND deductStatus=" + repayment.getDeductStatus();
		}
		if(repayment.getPayChannel()!=null){
			sql += " AND payChannel=" + "'" + repayment.getPayChannel() + "'" ;
		}
		if(repayment.getRepayDate()!=null){
			sql += " AND repayDate=" + "'" + repayment.getRepayDate() + "'";
		}
		if(repayment.getOrderNum()!=null){
			sql += " AND orderNum=" + "'" + repayment.getOrderNum() + "'" ;
		}
		return sql;
	}
	
	/**
	 * @param repayment
	 * @return BigDecimal
	 */
	public BigDecimal totalMoneySum(MonthlyRepayment repayment){
		Query q = entityManager.createQuery("SELECT SUM(mr.payAmounts) FROM monthly_repayment mr WHERE 1=1" + createPageSqlForWhere(repayment));
		Object o = q.getSingleResult();
		BigDecimal totalMoneySum = new BigDecimal(o==null?"0":(o.toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(null != totalMoneySum && !"".equals(totalMoneySum)){
			return totalMoneySum;
		} else {
			return null;
		}
	}

	/**
	 *
	 * @param globalId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MonthlyRepayment findByGlobalId(String globalId){
		String sql = "SELECT * FROM monthly_repayment mr WHERE global_id = '" + globalId + "'";
		Query q = entityManager.createNativeQuery(sql, MonthlyRepayment.class);
		List<MonthlyRepayment> list = q.getResultList();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
