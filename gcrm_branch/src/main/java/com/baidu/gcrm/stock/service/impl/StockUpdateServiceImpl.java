package com.baidu.gcrm.stock.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.stock.helper.CalculatorHelper;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.stock.service.ICalculateStockService.StockOperateType;
import com.baidu.gcrm.stock.service.IStockUpdateService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service
public class StockUpdateServiceImpl implements IStockUpdateService {
    
    @Resource(name = "CPMCalculateStockService")
    ICalculateStockService cpmCalculateStockService;
    
    @Resource(name = "CPTCalculateStockService")
    ICalculateStockService cptCalculateStockService;

    @Override
    public void changeOtherStock(StockOperateType stockOperateType, Stock stock,
            Map<String, Stock> stockMap) {
        Long currentTotalStock = stock.getTotalStock();
        Long positionDateId = stock.getPositionDateId();
        Long currentRealOccupiedStock  = stock.getRealOccupiedStock();
        
        if (currentTotalStock == null || currentTotalStock.longValue() < 1) {
            throw new CRMRuntimeException("stock.occupy.count.error");
        }
        Stock otherStock = null;
        ICalculateStockService calculateStockService = null;
        if (StockOperateType.CPM_OCCUPY == stockOperateType
                || StockOperateType.CPM_RELEASE == stockOperateType) {
            calculateStockService = cptCalculateStockService;
            otherStock = calculateStockService.loadStock(positionDateId, BillingModel.CPT_ID, stockMap);
        } else if (StockOperateType.CPT_OCCUPY == stockOperateType
                || StockOperateType.CPT_RELEASE == stockOperateType) {
            calculateStockService = cpmCalculateStockService;
            otherStock = calculateStockService.loadStock(positionDateId, BillingModel.CPM_ID, stockMap);
        }
        if (otherStock == null) {
            return;
        }
        
        Long oldOccupiedStock = otherStock.getOccupiedStock();
        Long changeOoccupiedStock = CalculatorHelper.cal(currentRealOccupiedStock, currentTotalStock,
                otherStock.getTotalStock());
        Long newOccupiedStock = otherStock.getRealOccupiedStock() + changeOoccupiedStock;
        int changeCount = calculateStockService.updateOccupiedStock(otherStock, newOccupiedStock, oldOccupiedStock);
        if (changeCount < 1) {
            throw new CRMRuntimeException("stock.change.retry");
        }
    }

}
