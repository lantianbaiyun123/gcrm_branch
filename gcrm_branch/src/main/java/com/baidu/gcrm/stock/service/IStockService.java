package com.baidu.gcrm.stock.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.occupation1.model.PositionDate;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.vo.DateStock;

public interface IStockService {

    void save(Stock stock);

    /**
     * 根据当前日期后未来一周的库存，创建新增的位置日期当天库存并初始化
     * 
     * @param newPosDates 新的位置日期
     * @param posIdMap 相关的位置列表
     */
    void createNewStocks(List<PositionDate> newPosDates, Map<Long, Position> posIdMap);

    void updateStocksTimer();
    
    /**
     * 当位置的轮播数属性修改时，更新该位置的CPT总库存、占用库存和CPM的占用库存
     * @param position 修改轮播数的位置
     */
    void updateTotalStocks(Position position);
    
    /**
     * 根据条件查询库存情况
     */
    List<DateStock> queryDateStocks(Long billingModelId, List<Long> positionDateIds);
    
    List<Stock> findAll();
    
    Map<String, DateStock> getDateStockMap(Long billingModelId, Long positionId, Collection<Date> dates);
    
    List<Stock> findByPositionDateIdInAndBillingModelId(Long billingModelId, List<Long> positionDateIds);
    
    Long findMaxOccupiedCPTStock(Long positionId);
}
