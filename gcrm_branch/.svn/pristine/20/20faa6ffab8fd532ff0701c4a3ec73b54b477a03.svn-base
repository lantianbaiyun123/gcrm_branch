package com.baidu.gcrm.stock.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baidu.gcrm.stock.model.PositionDateStock;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.vo.DateStock;

public interface StockRepositoryCustom {
    List<PositionDateStock> findPositionDateStocks(Collection<Long> positionIds, Date fromDate, Date toDate,
            Long billingModelId);

    List<DateStock> findDateStockByBillingModelIdAndPositionDateIdIn(Long billingModelId, List<Long> positionDateIds);

    List<DateStock> findDateStockByBillingModelIdAndPositionIdAndDateIn(Long billingModelId, Long positionId,
            Collection<Date> dates);
    
    int updateOccupiedStock(Stock oldStock, Long occupiedStock, Long oldOccupiedStock);
    
    int updateOccupiedStock(Stock oldStock, Long occupiedStock,
            Long realOccupiedStock, Long oldOccupiedStock);
}
