package com.baidu.gcrm.schedule1.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.schedule1.dao.ScheduleRepositoryCustom;
import com.baidu.gcrm.schedule1.model.ScheduleVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;
import com.baidu.gcrm.schedule1.web.vo.ScheduleConditionVO;
import com.baidu.gcrm.schedule1.web.vo.ScheduleListVO;
@Repository
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private IPageQuery mysqlPageQuery;
	
    @Override
    public ScheduleVO findSchedule(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append ("select s.id,s.number,s.ad_content_id,s.period_description,")
                .append("s.status,s.create_time,s.lock_time,a.advertisers,")
                .append("a.description,s.billing_model_id,so.contract_number,")
                .append("(select c.company_name from g_customer c ")
                .append("where c.customer_number=so.customer_number) as company_name,")
                .append("a.product_id as ad_platform_id,a.site_id,a.channel_id,")
                .append("a.area_id,a.position_id,so.id as ad_solution_id ")
                .append(" from g_schedules s,g_advertise_solution_content a,g_advertise_solution so ")
                .append(" where s.ad_content_id = a.id and a.advertise_solution_id=so.id ")
                .append(" and s.id=?1");
        
        Query query = entityManager.createNativeQuery(sql.toString(), ScheduleVO.class);
        query.setParameter(1, id); 
        ScheduleVO result = null;
        try {
            result = (ScheduleVO)query.getSingleResult();
        } catch(Exception e) {
            return null;
        }
   
        return result;
    }
	
	
	@Override
	public PageWrapper<ScheduleListVO> findScheduleListByCondition(
			ScheduleConditionVO scheduleConditionVO) {
		List<Object> paramList = new ArrayList<Object> ();
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select s.id,s.period_description,s.number,s.status,")
        		.append("c.advertisers,c.number as description,s.ad_content_id")
        		.append(" from g_schedules s,g_advertise_solution_content c ")
        		.append(" where s.ad_content_id=c.id  ")
        		.append(" and s.status is not null and c.description is not null");
		sqlStr.append(processCondition(scheduleConditionVO,paramList));
		sqlStr.append(" order by s.create_time desc");
		
		mysqlPageQuery.executePageQuery(entityManager, sqlStr, paramList, scheduleConditionVO);
		return scheduleConditionVO;
	}
	
	
	private StringBuilder processCondition(ScheduleConditionVO scheduleConditionVO,
			List<Object> paramList) {
		StringBuilder sqlStr = new StringBuilder();
		Integer scheduleStatus = scheduleConditionVO.getScheduleStatus();
		if (scheduleStatus != null) {
			sqlStr.append(" and s.status =?");
			paramList.add(scheduleStatus);
		}
		
		Integer platFormId = scheduleConditionVO.getPlatFormId();
		if (platFormId != null) {
			sqlStr.append(" and c.product_id =?");
			paramList.add(platFormId);
		}
		
		Integer siteId = scheduleConditionVO.getSiteId();
		if (siteId != null) {
			sqlStr.append(" and c.site_id =?");
			paramList.add(siteId);
		}
		
		Integer channelId = scheduleConditionVO.getChannelId();
		if (channelId != null) {
			sqlStr.append(" and c.channel_id =?");
			paramList.add(channelId);
		}
		
		Integer areaId = scheduleConditionVO.getAreaId();
		if (areaId != null) {
			sqlStr.append(" and c.area_id =?");
			paramList.add(areaId);
		}
		
		Integer positionId = scheduleConditionVO.getPositionId();
		if(positionId != null){
			sqlStr.append(" and c.position_id =?");
			paramList.add(positionId);
		}

		List<Long> positionDateIds = scheduleConditionVO.getPositionDateIds();
		if(!CollectionUtils.isEmpty(positionDateIds)){
		    StringBuilder idParams = new StringBuilder();
		    int i = 0;
		    for (Long positionDateId : positionDateIds) {
		        if (i > 0) {
		            idParams.append(",");
		        }
		        idParams.append(positionDateId);
		        i++;
		    }
			sqlStr.append(" and c.id in ")
			        .append("(select r.ad_content_id from g_adcontent_position_date_relation r  where")
			        .append(" r.position_occ_id in (").append(idParams).append(") group by r.ad_content_id)");
		}
		
		ScheduleConditionVO.QueryType queryType = scheduleConditionVO.getQueryType();
		String queryStr = scheduleConditionVO.getQueryStr();
		if (scheduleConditionVO.getQueryType() != null
				&& !PatternUtil.isBlank(queryStr)) {
			queryStr = queryStr.trim();
			switch (queryType) {
    			case advertisers :
    				sqlStr.append(" and c.advertisers like ? ");
    				queryStr = new StringBuilder("%").append(queryStr).append("%").toString();
    				break;
    			case schedulenum :
    				sqlStr.append(" and s.number=? ");
    				break;
    			case adcontentnum :
    				sqlStr.append(" and c.number =? ");
    				break;
			}
			paramList.add(queryStr);
		}
		return sqlStr;
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<Schedules> findLatestScheduleByAdContentId(Long adContentId) {
	    String qlString = "from Schedules where adContentId = :adContentId order by createTime desc";
        Query query = entityManager.createQuery(qlString);
	    query.setParameter("adContentId", adContentId);
	    return query.getResultList();
	}
	
	@Override
	public void releaseScheduleByAdContentId(Long adContentId) {
	    String sql = "update Schedules set status = :status, releaseTime = now() where adContentId = :adContentId";
        Query query = entityManager.createQuery(sql);
	    query.setParameter("status", ScheduleStatus.released);
	    query.setParameter("adContentId", adContentId);
	    query.executeUpdate();
	}
}
