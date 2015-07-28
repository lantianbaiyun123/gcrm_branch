package com.baidu.gcrm.stock.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service("CPMCalculateStockService")
public class CPMCalculateStockServiceImpl extends AbstractCalculateStockService
    implements ICalculateStockService {
    
    @Override
    public List<Long> batchOccupy(Collection<Long> positionDateIds, Long count) {
        return calculateOccupation(BillingModel.CPM_ID, positionDateIds,
                count, StockOperateType.CPM_OCCUPY);
    }

    @Override
    public void batchRelease(Collection<Long> positionDateIds, Long count) {
        calculateRelease(BillingModel.CPM_ID, positionDateIds,
                count, StockOperateType.CPM_RELEASE);
    }
}
