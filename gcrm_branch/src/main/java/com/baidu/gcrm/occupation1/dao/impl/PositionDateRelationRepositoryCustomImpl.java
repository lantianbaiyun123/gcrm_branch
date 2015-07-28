package com.baidu.gcrm.occupation1.dao.impl;

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
import com.baidu.gcrm.occupation1.dao.PositionDateRelationRepositoryCustom;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Repository
public class PositionDateRelationRepositoryCustomImpl implements PositionDateRelationRepositoryCustom{
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, Long> getCountGroupByPositionOccId(Collection<Long> positionOccIds) {
        Map<Long, Long> results = new HashMap<Long, Long>();
        if (CollectionUtils.isEmpty(positionOccIds)) {
            return results;
        }
        String sqlString = "select positionOccId, count(*) from AdvertisePositionDateRelation "
                + "where positionOccId in(:ids) group by positionOccId";
        Query query = entityManager.createQuery(sqlString);
        query.setParameter("ids", positionOccIds);
        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            results.put((Long)object[0], (Long)object[1]);
        }
        return results;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, Long> getCountGroupByAdContentIdInAndDateFrom(Collection<Long> adContentIds, Date fromDate) {
        Map<Long, Long> results = new HashMap<Long, Long>();
        if (CollectionUtils.isEmpty(adContentIds)) {
            return results;
        }
        String sql = "select r.positionOccId, count(*) from AdvertisePositionDateRelation r, PositionDate pd where "
                + "r.positionOccId = pd.id and r.adContentId in(:ids) and pd.date >= :date group by r.positionOccId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("ids", adContentIds);
        query.setParameter("date", fromDate);
        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            results.put((Long) object[0], (Long) object[1]);
        }
        return results;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Multimap<Long, String> getContentIdDateMap(Collection<Long> adContentIds, Date fromDate) {
        Multimap<Long, String> contentIdDateMap = ArrayListMultimap.create();
        if (CollectionUtils.isEmpty(adContentIds)) {
            return contentIdDateMap;
        }
        String sql = "select r.adContentId, pd.date from AdvertisePositionDateRelation r, PositionDate pd "
                + "where r.positionOccId = pd.id and r.adContentId in(:ids) and pd.date >= :date";
        Query query = entityManager.createQuery(sql);
        query.setParameter("ids", adContentIds);
        query.setParameter("date", fromDate);
        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            Date date = (Date) object[1];
            contentIdDateMap.put((Long) object[0], DateUtils.getDate2ShortString(date));
        }
        return contentIdDateMap;
    }
    
    @Override
    public int replaceAdContentId(Long oldContentId, Long replaceContentId) {
        String sql = "update AdvertisePositionDateRelation set adContentId = :replaceContentId" 
                + "where adContentId = :oldContentId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("oldContentId", oldContentId);
        query.setParameter("replaceContentId", replaceContentId);
        return query.executeUpdate();
    }
}
