package com.yingu.poc.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.yingu.poc.common.base.PageModel;
import com.yingu.poc.common.base.dao.impl.BaseDaoImpl;
import com.yingu.poc.pojo.QrtzTrigger;
import com.yingu.poc.pojo.QrtzTriggerBackup;

public class QrtzTriggerDaoImpl extends BaseDaoImpl<QrtzTrigger>{
	private static final String SQL_COLUMN = "*";

	@SuppressWarnings({ "unchecked" })
	public List<Map<String,Object>> findList(){
		Query q = entityManager.createNativeQuery(creatSql(SQL_COLUMN) +  " GROUP BY qt.TRIGGER_NAME ");
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = q.getResultList();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> findCronTriggerByName(String name){
		Query q = entityManager.createNativeQuery("select * from qrtz_cron_triggers qt where trigger_name=?1 ");
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setParameter(1, name);
		List<Map<String,Object>> list = q.getResultList();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void updateCronTriggerByName(String cron,String name){
		Query q = entityManager.createQuery("UPDATE qrtz_cron_triggers SET cron_expression=?1 where trigger_name=?2 ");
		q.setParameter(1, name);
		q.setParameter(2, cron);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findListByPage(Map<String,Object> param, PageModel page){
		String sql = creatSql(SQL_COLUMN) + createWhereSql(param) + " GROUP BY qt.TRIGGER_NAME ORDER BY qt.FIRE_TIME";
		Query q = entityManager.createNativeQuery(sql);
		log.info(sql);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setFirstResult(page.getStartIndex());
		q.setMaxResults(page.getPageSize());
		List<Map<String,Object>> list = q.getResultList();
		
		return list;
	}
	
	private String createWhereSql(Map<String,Object> param){
		StringBuilder sb = new StringBuilder(" WHERE 1=1 ");
		try{
			if(param.containsKey("endDate")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(param.get("endDate").toString());
				sb.append(" AND qt.FIRE_TIME <= " + DateUtils.addDays(date, 1).getTime());
			}
			if(param.containsKey("startDate")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(param.get("startDate").toString());
				sb.append(" AND qt.FIRE_TIME >= " + date.getTime());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public long findCountPage(Map<String,Object> param){
		String sql = creatSql("count(DISTINCT qt.TRIGGER_NAME)") + createWhereSql(param);
		Query q = entityManager.createNativeQuery(sql);
		
		long count = Long.valueOf(q.getSingleResult().toString());
		
		return count;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void insertBackupData(String jobName){
		Query q = entityManager.createNativeQuery("SELECT qt.SCHED_NAME,qt.TRIGGER_NAME,qt.JOB_NAME,qt.DESCRIPTION,qt.PREV_FIRE_TIME AS FIRE_TIME,qt.JOB_GROUP,qt.TRIGGER_GROUP "
												+ "FROM qrtz_triggers qt LEFT JOIN qrtz_cron_triggers qct ON qt.SCHED_NAME = qct.SCHED_NAME AND qt.TRIGGER_GROUP= qct.TRIGGER_GROUP AND qt.TRIGGER_NAME = qct.TRIGGER_NAME "
												+ "WHERE qt.JOB_NAME = '" + jobName + "'", QrtzTriggerBackup.class);
		
		List<QrtzTriggerBackup> list = q.getResultList();
		EntityManager em = emf.createEntityManager();
	    EntityTransaction entityTransaction = em.getTransaction();
	    entityTransaction.begin();
		em.merge(list.get(0));
		entityTransaction.commit();
	}
	
	public void deleteBackupData(String jobName){
		Query q = entityManager.createNativeQuery("DELETE FROM qrtz_triggers_backup where JOB_NAME=?1 ");
		q.setParameter(1, jobName);
		q.executeUpdate();
	}
	
	private String creatSql(String param) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT "+ param +" FROM (");
		sb.append("SELECT qt.JOB_NAME, qt.DESCRIPTION, qt.NEXT_FIRE_TIME AS FIRE_TIME, qct.CRON_EXPRESSION, qt.TRIGGER_STATE, qt.TRIGGER_NAME ");
		sb.append("FROM qrtz_triggers qt LEFT JOIN qrtz_cron_triggers qct ON qt.SCHED_NAME = qct.SCHED_NAME AND qt.TRIGGER_GROUP = qct.TRIGGER_GROUP AND qt.TRIGGER_NAME = qct.TRIGGER_NAME ");
		sb.append("UNION ");
		sb.append("SELECT qt.JOB_NAME, qt.DESCRIPTION, qt.FIRE_TIME, qct.CRON_EXPRESSION, qtb.TRIGGER_STATE, qt.TRIGGER_NAME FROM qrtz_triggers_backup qt ");
		sb.append("LEFT JOIN qrtz_cron_triggers qct ON qt.SCHED_NAME = qct.SCHED_NAME AND qt.TRIGGER_GROUP = qct.TRIGGER_GROUP AND qt.TRIGGER_NAME = qct.TRIGGER_NAME ");
		sb.append("LEFT JOIN qrtz_triggers qtb ON qt.SCHED_NAME = qtb.SCHED_NAME AND qt.TRIGGER_GROUP = qtb.TRIGGER_GROUP AND qt.TRIGGER_NAME = qtb.TRIGGER_NAME ) qt");
		return sb.toString();
	}
//	
//	public void updateBackupData(String date,String name){
//		try {
//			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date dt = sdf.parse(date);
//			String sql = "UPDATE qrtz_triggers_backup as qtb SET qtb.NEXT_FIRE_TIME=?1 where qtb.TRIGGER_NAME=?2 ";
//			Query q = entityManager.createNativeQuery(sql);
//			q.setParameter(1, dt.getTime());
//			q.setParameter(2, name);
//			q.executeUpdate();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}
