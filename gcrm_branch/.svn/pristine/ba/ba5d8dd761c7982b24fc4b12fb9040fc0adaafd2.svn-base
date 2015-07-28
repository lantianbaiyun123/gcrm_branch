package com.baidu.gcrm.occupation.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
@Repository
public class PositionOccupationRepositoryCustomImpl implements IPositionOccupationRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Date getFarthestDateByPosition(Long positionId) {
		Query query = entityManager.createNativeQuery("select max(date) from g_position_occupation where position_id = ? and status = 1");
		query.setParameter(1, positionId);
		return (Date) query.getSingleResult();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getReservedDateBetween(Long positionId, Date from, Date to) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct o.date from g_position_occupation o,g_adcontent_position_date_relation adr,");
		sql.append("g_schedule s where o.id=adr.position_occ_id and adr.ad_content_id=s.ad_content_id");
		sql.append(" and o.position_id=? and o.date between ? and ? and s.status=?");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, positionId);
		query.setParameter(2, from);
		query.setParameter(3, to);
		query.setParameter(4, ScheduleStatus.reserved.ordinal());
		return (List<Date>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Long> getReservedPositionCountBetween(Long positionId, Date from, Date to) {
		Map<Long, Long> results = new HashMap<Long, Long>();
		StringBuilder sql = new StringBuilder();
		sql.append("select adr.positionOccId, count(*) from PositionOccupation o,AdvertisePositionDateRelation adr,");
		sql.append("Schedule s where o.id=adr.positionOccId and adr.adContentId=s.adContentId");
		sql.append(" and o.positionId=:positionId and o.date between :from and :to and s.status=:status group by adr.positionOccId");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("positionId", positionId);
		query.setParameter("from", from);
		query.setParameter("to", to);
		query.setParameter("status", ScheduleStatus.reserved);
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long)object[0], (Long)object[1]);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public Map<Long, Date> getDateMapByDateBetween(Long positionId, Date from, Date to) {
		Map<Long, Date> results = new HashMap<Long, Date>();
		String sql = "select po.id,po.date from PositionOccupation po where po.positionId = ? and (po.date between ? and ?)";
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter(1, positionId);
		query.setParameter(2, from);
		query.setParameter(3, to);
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long)object[0], (Date)object[1]);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<PositionOccupation> findByCurScheduleNumberFazzyLike(String scheduleNumber) {
		String sql = "select po from PositionOccupation po where po.curScheduleNumber like :scheduleNumber";
		Query query = entityManager.createQuery(sql);
		query.setParameter("scheduleNumber", "%," + scheduleNumber + ",%");
		return (List<PositionOccupation>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PositionOccupation> findByCurScheduleNumberFazzyLikeFrom(String scheduleNumber, Date from) {
		String sql = "select po from PositionOccupation po where po.curScheduleNumber like :scheduleNumber and po.date >= :from";
		Query query = entityManager.createQuery(sql);
		query.setParameter("scheduleNumber", "%," + scheduleNumber + ",%");
		query.setParameter("from", from);
		return (List<PositionOccupation>) query.getResultList();
	}
	
	@Override
	public void batchUpdateAfterConfirm(List<PositionOccupation> occupations, List<String> conditions) {
		for (int i = 0; i < occupations.size(); i++) {
			StringBuilder sql = new StringBuilder("update g_position_occupation set cur_schedule_number = ?1, sold_amount = ?2 where id = ?3");
			String con = conditions.get(i);
			if(con == null){
				sql.append(" and cur_schedule_number is null");
			}else{
				sql.append(" and cur_schedule_number=?4");
			}
			PositionOccupation occupation = occupations.get(i);
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, occupation.getCurScheduleNumber());
			query.setParameter(2, occupation.getSoldAmount());
			query.setParameter(3, occupation.getId());
			if(con != null){
				query.setParameter(4, con);
			}
			int result = query.executeUpdate();
			if (result == 0) {
				throw new CRMRuntimeException("update error");
			}
		}
		
	}
	
	@Override
	public void batchUpdateAfterRelease(List<PositionOccupation> occupations, List<String> conditions) {
		String sql = "update g_position_occupation set cur_schedule_number = ?1, sold_amount = ?2, history_schedules=?3 where id = ?4 and cur_schedule_number = ?5";
		for (int i = 0; i < occupations.size(); i++) {
			PositionOccupation occupation = occupations.get(i);
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter(1, occupation.getCurScheduleNumber());
			query.setParameter(2, occupation.getSoldAmount());
			query.setParameter(3, occupation.getHistorySchedules());
			query.setParameter(4, occupation.getId());
			query.setParameter(5, conditions.get(i));
			int result = query.executeUpdate();
			if (result == 0) {
				throw new CRMRuntimeException("update error");
			}
		}
		
	}
	
	/**
	 * 按时间段查询位置投放记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PositionOccupation> findByDateFromTo(Date from, Date to) {
		if(from == null && to == null){
			return new ArrayList<PositionOccupation>();
		}
		
		String sql = "select po from PositionOccupation po where 1=1";
		if(from != null){
			sql = sql + " and po.date >= :from";
		}
		if(to != null){
			sql = sql + " and po.date <= :to";
		}
		Query query = entityManager.createQuery(sql);
		
		if(from != null){
			query.setParameter("from", from);
		}
		if(to != null){
			query.setParameter("to", to);
		}
		
		return (List<PositionOccupation>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<PositionOccupation> findByHistoryScheduleNumberFazzyLike(String scheduleNumber) {
		String sql = "select po from PositionOccupation po where po.historySchedules like :scheduleNumber";
		Query query = entityManager.createQuery(sql);
		query.setParameter("scheduleNumber", "%," + scheduleNumber + ",%");
		return (List<PositionOccupation>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public Map<String, Long> getDateOccIdMapFrom(Long positionId, Date from) {
		String sql = "select po from PositionOccupation po where po.positionId = :positionId and po.date >= :from";
		Query query = entityManager.createQuery(sql);
		query.setParameter("positionId", positionId);
		query.setParameter("from", from);
		List<PositionOccupation> occupations = query.getResultList();
		Map<String, Long> dateOccIdMap = new HashMap<String, Long>();
		for (PositionOccupation occ : occupations) {
			dateOccIdMap.put(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, occ.getDate()), occ.getId());
		}
		return dateOccIdMap;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public Map<Long, Date> getOccIdDateMapFrom(Long positionId, Date from) {
		String sql = "select po from PositionOccupation po where po.positionId = :positionId and po.date >= :from";
		Query query = entityManager.createQuery(sql);
		query.setParameter("positionId", positionId);
		query.setParameter("from", from);
		List<PositionOccupation> occupations = query.getResultList();
		Map<Long, Date> dateOccIdMap = new HashMap<Long, Date>();
		for (PositionOccupation occ : occupations) {
			dateOccIdMap.put(occ.getId(), occ.getDate());
		}
		return dateOccIdMap;
	}

    @Override
    public Integer findMaxUsedCountByPositionId(Long positionId,
            Collection<ScheduleStatus> scheduleStatus) {
        StringBuilder sql = new StringBuilder()
                        .append("select max(p.soldAmount) from Schedule s,AdvertisePositionDateRelation r,PositionOccupation p where ")
                        .append(" s.adContentId=r.adContentId and r.positionOccId=p.id ")
                        .append(" and s.positionId=?1 and s.status in (?2)");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter(1, positionId);
        query.setParameter(2, scheduleStatus);
        Object object = query.getSingleResult();
        if (object == null || object.toString().trim().equals("")) {
            return Integer.valueOf(0);
        } else {
            return Integer.valueOf(object.toString());
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, Date> getOccIdDateMapInOccIds(List<Long> occIds) {
    	String sql = "select po from PositionOccupation po where po.id in :occIds";
		Query query = entityManager.createQuery(sql);
		query.setParameter("occIds", occIds);
		List<PositionOccupation> occupations = query.getResultList();
		Map<Long, Date> dateOccIdMap = new HashMap<Long, Date>();
		for (PositionOccupation occ : occupations) {
			dateOccIdMap.put(occ.getId(), occ.getDate());
		}
		return dateOccIdMap;
    }
    
    @Override
    public int updateOccupationTotalAmount(Position position) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" update g_position_occupation po,g_position p set po.total_amount=?1  ")
            .append("where po.position_id=p.id and po.date>=?3 and ");
        
        PositionType type = position.getType();
        if (PositionType.area == type) {
            sql.append(" p.parent_id=?2");
        } else {
            sql.append(" p.id=?2");
        }
        String dateStr = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, new Date());
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, position.getSalesAmount());
        query.setParameter(2, position.getId());
        query.setParameter(3, DateUtils.getString2Date(DateUtils.YYYY_MM_DD, dateStr));
        return query.executeUpdate();
        
    }

	@Override
	public List<ChannelOperationVO> getPositionFull() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.date, COUNT(b.position_id) as total_amount,");
		sql.append("IF (a.rotation_type = 1,SUM(IF(b.sold_amount > 0, 1, 0)),	SUM(b.sold_amount)) AS sold_amount,");
		sql.append("a.ad_platform_id, a.site_id,(SELECT c.parent_id from g_position c where c.id=a.parent_id) as channelId,(SELECT c.index_str from g_position c where c.id=channelId) as indexStr ");
		sql.append(" FROM g_position_occupation b LEFT JOIN g_position a   ON  b.position_id=a.id ");
		sql.append(" WHERE	a.type = 2 AND a. STATUS = 1  and a.rotation_type is not null and b.status=1");
		sql.append(" And b.date>= DATE_SUB(curdate(), INTERVAL 7 DAY) and b.date <=date(curdate()) ");
		sql.append(" GROUP BY channelId,date ORDER BY date ,a.ad_platform_id,a.site_id,channelId");
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> valueList =query.getResultList();
		List<ChannelOperationVO> voList= new ArrayList<ChannelOperationVO>();
		if (CollectionUtils.isNotEmpty(valueList)) {
			for (Object[] values : valueList) {
				ChannelOperationVO vo=new ChannelOperationVO();
				if (values[0] != null) {
					vo.setDate(values[0].toString());
				}
				if (values[1] != null) {
					vo.setSalesAmount(values[1].toString());
				}
				if (values[2] != null) {
					vo.setSoldAmount(values[2].toString());
				}
				if (values[3] != null) {
					vo.setPlatformId(Long.valueOf(values[3].toString()));
				}
				if (values[4] != null) {
					vo.setSiteId(Long.valueOf(values[4].toString()));
				}
				if (values[5] != null) {
					vo.setChannelId(Long.valueOf(values[5].toString()));
				}
				if (values[6] != null) {
					vo.setIndexStr(values[6].toString());
				}
				voList.add(vo);
			}
			
			
		}
		return voList;
	}
	
	
	
}
