package com.baidu.gcrm.stock.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baidu.gcrm.stock.dao.StockRepositoryCustom;
import com.baidu.gcrm.stock.model.PositionDateStock;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.vo.DateStock;

public class StockRepositoryImpl implements StockRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<PositionDateStock> findPositionDateStocks(Collection<Long> positionIds, Date fromDate, Date toDate,
            Long billingModelId) {
        String sql = "select pd.id, pd.position_id, pd.date, s.total_stock, s.billing_model_id from"
                + " g_position_date pd, g_stock s where pd.id = s.position_date_id and pd.position_id in(:positionIds)"
                + " and s.billing_model_id = :billingModelId and pd.date between :fromDate and :toDate";
        Query query = entityManager.createNativeQuery(sql, PositionDateStock.class);
        query.setParameter("positionIds", positionIds);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("billingModelId", billingModelId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DateStock> findDateStockByBillingModelIdAndPositionDateIdIn(Long billingModelId,
            List<Long> positionDateIds) {
        String sql = "select s.id, s.position_date_id, s.total_stock, s.occupied_stock, s.real_occupied_stock, pd.date"
                + " from g_position_date pd, g_stock s where pd.id = s.position_date_id "
                + "and s.position_date_id in (:positionDateIds) and s.billing_model_id = :billingModelId";
        Query query = entityManager.createNativeQuery(sql, DateStock.class);
        query.setParameter("positionDateIds", positionDateIds);
        query.setParameter("billingModelId", billingModelId);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DateStock> findDateStockByBillingModelIdAndPositionIdAndDateIn(Long billingModelId, Long positionId,
            Collection<Date> dates) {
        String sql = "select s.id, s.real_occupied_stock, s.total_stock, s.occupied_stock, pd.date, s.position_date_id"
                + " from g_position_date pd, g_stock s where pd.id = s.position_date_id "
                + "and s.billing_model_id = :billingModelId and pd.position_id = :positionId and pd.date in(:dates)";
        Query query = entityManager.createNativeQuery(sql, DateStock.class);
        query.setParameter("positionId", positionId);
        query.setParameter("billingModelId", billingModelId);
        query.setParameter("dates", dates);
        return query.getResultList();
    }
    
    
    @Override
    public int updateOccupiedStock(Stock oldStock, Long occupiedStock, Long oldOccupiedStock) {
        String sql = "update Stock set occupiedStock = ?1 where id = ?2 and occupiedStock = ?3";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, occupiedStock);
        query.setParameter(2, oldStock.getId());
        query.setParameter(3, oldOccupiedStock);
        int updatedNumber = query.executeUpdate();
        entityManager.detach(oldStock);
        return updatedNumber;
    }

    @Override
    public int updateOccupiedStock(Stock oldStock, Long occupiedStock,
            Long realOccupiedStock, Long oldOccupiedStock) {
        String sql = "update Stock set occupiedStock = ?1,realOccupiedStock = ?2 where id = ?3 and occupiedStock = ?4";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, occupiedStock);
        query.setParameter(2, realOccupiedStock);
        query.setParameter(3, oldStock.getId());
        query.setParameter(4, oldOccupiedStock);
        int updatedNumber = query.executeUpdate();
        entityManager.detach(oldStock);
        return updatedNumber;
    }
}
