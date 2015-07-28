package com.baidu.gcrm.stock.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.stock.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>, StockRepositoryCustom {

    @Query("select pd, s, p.salesAmount from Stock s, PositionDate pd, Position p where pd.positionId = p.id"
            + " and s.positionDateId = pd.id and p.status = 1 and pd.date >= ?1 and s.billingModelId = ?2"
            + " and pd.positionId in(?3)")
    List<Object[]> findStocks(Date fromDate, Long billingModelId, Collection<Long> positionIds);
	
	List<Stock> findByPositionDateIdIn(Collection<Long> positionDateIds);
	
    List<Stock> findByPositionDateIdAndBillingModelId(Long positionDateId, Long billingModelId);
    
    List<Stock> findByPositionDateIdInAndBillingModelId(Collection<Long> positionDateIds, Long billingModelId);
    
    @Query("select pd, s, p.salesAmount from Stock s, PositionDate pd, Position p where pd.positionId = p.id"
            + " and s.positionDateId = pd.id and p.status = 1 and pd.date >= ?1 and weekday(pd.date) = ?2"
            + " and s.billingModelId = ?3 and pd.positionId in(?4)")
    List<Object[]> findStocksOfWeekday(Date fromDate, int weekday, Long billingModelId, Collection<Long> positionIds);
    
    @Query("select max(s.occupiedStock) from Stock s,PositionDate pd where s.positionDateId=pd.id and s.billingModelId=?3 "
            + " and pd.date >= ?1 and pd.positionId = ?2 ")
    Long findMaxOccupiedCPTStock(Date fromDate, Long positionId, Long billingModelId);
}
