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

import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepositoryCustom;
@Repository
public class ADPositionDateRelationRepositoryCustomImpl implements IADPositionDateRelationRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAdcontentIdsByPositionOccIdIn(List<Long> positionOccIds) {
		if (CollectionUtils.isEmpty(positionOccIds)) {
			return new ArrayList<Long>();
		}
		String qlString = "select adContentId from AdvertisePositionDateRelation where positionOccId in(:ids)";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("ids", positionOccIds);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findIdlePositionOccIds(Collection<Long> positionOccIds) {
		if (CollectionUtils.isEmpty(positionOccIds)) {
			return new ArrayList<Long>();
		}
		String qlString = "select distinct adr.positionOccId from AdvertisePositionDateRelation adr,Schedule s where adr.adContentId=s.adContentId and adr.positionOccId in(:ids)";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("ids", positionOccIds);
		List<Long> occupiedIds = query.getResultList();
		return (List<Long>) CollectionUtils.subtract(positionOccIds, occupiedIds);
	}
	
	@Override
	public Date getMaxIdleOccupationDate(Long positionId) {
		String qlString = "select max(po.date) from PositionOccupation po where po.id not in " +
				"(select adr.positionOccId from AdvertisePositionDateRelation adr,Schedule s where adr.adContentId=s.adContentId and s.positionId=:positionId) " +
				"and po.positionId=:positionId";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("positionId", positionId);
		return (Date) query.getSingleResult();
	}
	
	@Override
	public Date getMaxOccupiedOccupationDate(Long positionId) {
		String qlString = "select max(po.date) from PositionOccupation po where po.id in " +
				"(select adr.positionOccId from AdvertisePositionDateRelation adr,Schedule s where adr.adContentId=s.adContentId and s.positionId=:positionId) " +
				"and po.positionId=:positionId";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("positionId", positionId);
		return (Date) query.getSingleResult();
	}
	
	@Override
	public Map<Long, Long> getBusyCountGroupByPositionOccId(Collection<Long> positionOccIds) {
		Map<Long, Long> results = new HashMap<Long, Long>();
		if (CollectionUtils.isEmpty(positionOccIds)) {
			return results;
		}
		String qlString = "select adr.positionOccId,count(*) from AdvertisePositionDateRelation adr,Schedule s where adr.adContentId=s.adContentId and adr.positionOccId in(:ids) and s.status <> '3' group by adr.positionOccId";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("ids", positionOccIds);
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long)object[0], (Long)object[1]);
		}
		return results;
	}
	
	@Override
	public Map<Long, Long> getCountGroupByPositionOccId(Collection<Long> positionOccIds) {
		Map<Long, Long> results = new HashMap<Long, Long>();
		if (CollectionUtils.isEmpty(positionOccIds)) {
			return results;
		}
		String qlString = "select positionOccId,count(*) from AdvertisePositionDateRelation where positionOccId in(:ids) group by positionOccId";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("ids", positionOccIds);
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long)object[0], (Long)object[1]);
		}
		return results;
	}
}
