package com.baidu.gcrm.schedule.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.schedule.dao.IScheduleRepositoryCustom;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.web.helper.ScheduleCondition;
import com.baidu.gcrm.schedule.web.vo.ScheduleListBean;
@Repository
public class Schedule1RepositoryCustomImpl implements IScheduleRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private IPageQuery mysqlPageQuery;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findNumbersByPositionAndStatus(Long positionId, ScheduleStatus status) {
		String sql = "select number from Schedule where positionId =:positionId and status =:status";
		Query query = entityManager.createQuery(sql);
		query.setParameter("positionId", positionId);
		query.setParameter("status", status);
		return (List<String>) query.getResultList();
	}
	
	@Override
	public Map<String, Object> findSchedule(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append ("SELECT s.id,s.number,s.adContentId,s.positionId,s.occupyPeriod,s.status,s.createTime,s.confirmTime,");
		sql.append ("a.advertiser,a.description,s.billingModelId,s.periodDescription,s.insertPeriodDescription,");
		sql.append("a.number,");
		sql.append("(select companyName from Customer where customerNumber=(select customerNumber from AdvertiseSolution where id=a.adSolutionId)),");
		sql.append("g.adPlatformId,a.siteId,a.channelId,a.areaId,a.positionId,a.adSolutionId");
		sql.append(" From Schedule s");
        sql.append(",AdSolutionContent a");
        sql.append(",Position g  where s.adContentId = a.id and s.positionId=g.id ");
        sql.append(" and s.id=?1");
        Query query = entityManager.createQuery(String.valueOf(sql));
        query.setParameter(1, id); 
        Object[] values = new Object[]{};
        try {
        	values = (Object[])query.getSingleResult();
        } catch(Exception e) {
        	return null;
        }
        Map<String,Object> meet = new HashMap<String, Object>();
        meet.put("id", values[0]);
        meet.put("number", values[1]);
        meet.put("adContentId", values[2]);
        meet.put("billingModel",values[10]);
        meet.put("positionId", values[3]);
        meet.put("occupyPeriod", values[4]);
        meet.put("status", "schedule.schedulestatus."+values[5]);
        if(values[6]!=null)
        {
        meet.put("createTime", values[6].toString());
        }
        if(values[7]!=null)
        {
        meet.put("confirmTime", values[7].toString());
        }    
        meet.put("advertiser", values[8]);
        meet.put("description", values[9]);   
        meet.put("periodDescription", values[11]);
        meet.put("insertPeriodDescription", values[12]);
        meet.put("contractnum", values[13]);
        meet.put("companyname", values[14]);
        meet.put("adPlatformId", values[15]);
        meet.put("siteId", values[16]);
        meet.put("channelId", values[17]);
        meet.put("areaId", values[18]);
        meet.put("positionId", values[19]);
        //meet.put("guodai", values[20]);
        meet.put("adSolutionId", values[20]);
   
        return meet;
	}
	
	@Override
	public PageWrapper<ScheduleListBean> findScheduleListByCondition(
			ScheduleCondition scheduleCondition) {
		List<Object> paramList = new ArrayList<Object> ();
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("SELECT a.id,a.period_description,a.number,")
		.append("a.insert_period_description,a.status,")
		.append("b.advertisers as advertisers,b.number as description,a.ad_content_id")
		.append(" FROM g_schedule a,g_advertise_solution_content b,g_position c where a.ad_content_id=b.id")
		.append(" and b.position_id = c.id and a.status is not null and b.description is not null");
		
		sqlStr.append(processCondition(scheduleCondition,paramList));
		
		sqlStr.append(" order by a.create_time desc");
		
		mysqlPageQuery.executePageQuery(entityManager, sqlStr, paramList, scheduleCondition);
		return scheduleCondition;
	}
	
	
	private StringBuilder processCondition(ScheduleCondition scheduleCondition,
			List<Object> paramList) {
		StringBuilder sqlStr = new StringBuilder();
		
		Integer scheduleStatus = scheduleCondition.getScheduleStatus();
		if(scheduleStatus != null){
			sqlStr.append(" and a.status =?");
			paramList.add(scheduleStatus);
		}
		
		Integer platFormId = scheduleCondition.getPlatFormId();
		if(platFormId != null){
			sqlStr.append(" and c.ad_platform_id =?");
			paramList.add(platFormId);
		}
		
		Integer siteId = scheduleCondition.getSiteId();
		if(siteId != null){
			sqlStr.append(" and b.site_id =?");
			paramList.add(siteId);
		}
		
		Integer channelId = scheduleCondition.getChannelId();
		if(channelId != null){
			sqlStr.append(" and b.channel_id =?");
			paramList.add(channelId);
		}
		
		Integer areaId = scheduleCondition.getAreaId();
		if(areaId != null){
			sqlStr.append(" and b.area_id =?");
			paramList.add(areaId);
		}
		
		Integer positionId = scheduleCondition.getPositionId();
		if(positionId != null){
			sqlStr.append(" and b.position_id =?");
			paramList.add(positionId);
		}

//		String curOccupyIds = scheduleCondition.getCurOccupyIds();
//		if(!PatternUtil.isBlank(curOccupyIds)){
//			sqlStr.append(" and SUBSTRING_INDEX(a.occupy_period,',',1) in (").append(curOccupyIds).append(")");
//		}
		
		
		ScheduleCondition.QueryType queryType = scheduleCondition
				.getQueryType();
		String queryStr = scheduleCondition.getQueryStr();

		if (scheduleCondition.getQueryType() != null
				&& !PatternUtil.isBlank(queryStr)) {
			queryStr = queryStr.trim();
			switch (queryType) {
			case advertisers:
				sqlStr.append(" and b.advertisers like ? ");
				queryStr = new StringBuilder("%").append(queryStr).append("%").toString();
				break;
			case schedulenum:
				sqlStr.append(" and a.number=? ");
				break;
			case adcontentnum:
				sqlStr.append(" and b.number =? ");
				break;
			}
			paramList.add(queryStr);
		}

		return sqlStr;
	}

}
