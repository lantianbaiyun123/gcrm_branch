package com.baidu.gcrm.stock.service;

import java.util.Map;

import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.service.ICalculateStockService.StockOperateType;

public interface IStockUpdateService {
    
    /**
     * 
     * @param stockOperateType
     * @param stock
     * @param stockMap key ==> positionDateId + "-" + billingModelId
     */
    void changeOtherStock(StockOperateType stockOperateType,
            Stock stock, Map<String, Stock> stockMap);

}
