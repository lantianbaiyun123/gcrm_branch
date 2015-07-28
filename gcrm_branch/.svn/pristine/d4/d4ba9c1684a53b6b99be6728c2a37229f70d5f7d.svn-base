package com.baidu.gcrm.occupation1.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baidu.gcrm.occupation1.dao.PositionDateRepositoryCustom;

public class PositionDateRepositoryImpl implements PositionDateRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Date getFarthestDateByPosition(Long positionId) {
        Query query = entityManager
                .createNativeQuery("select max(date) from g_position_date where position_id = ? and status = 1");
        query.setParameter(1, positionId);
        return (Date) query.getSingleResult();

    }
    
    @Override
    public int moveEnabledFromPositionOccupationToPositionDate(Date date) {
        String sql = "insert into g_position_date(id, position_id, date, status)"
                + " select id, position_id, date, status from g_position_occupation where position_id in"
                + " (select id from g_position where status = 1 and type = 2) and date >= :date";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("date", date);
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findByDateFromTo(Date from, Date to) {
        if (from == null && to == null) {
            return new ArrayList<Long>();
        }
        StringBuilder sql = new StringBuilder().append("select id from PositionDate where 1=1");
        List<Object> paramList = new ArrayList<Object>();
        if (from != null) {
            sql.append(" and date >= ?1");
            paramList.add(from);
        }
        if (to != null) {
            sql.append(" and date <= ?2");
            paramList.add(to);
        }
        Query query = entityManager.createQuery(sql.toString());
        int paramIndex = 1;
        for (Object param : paramList) {
            query.setParameter(paramIndex, param);
            paramIndex++;
        }
        return query.getResultList();
    }
}
