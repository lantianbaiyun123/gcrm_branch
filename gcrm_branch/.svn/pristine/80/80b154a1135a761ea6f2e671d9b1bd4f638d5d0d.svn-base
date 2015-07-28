package com.baidu.gcrm.stock.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service("CPTCalculateStockService")
public class CPTCalculateStockServiceImpl extends AbstractCalculateStockService
    implements ICalculateStockService {
    
    @Override
    public List<Long> batchOccupy(Collection<Long> positionDateIds, Long count) {
        return calculateOccupation(BillingModel.CPT_ID, positionDateIds,
                count, StockOperateType.CPT_OCCUPY);
    }

    @Override
    public void batchRelease(Collection<Long> positionDateIds, Long count) {
        calculateRelease(BillingModel.CPT_ID, positionDateIds,
                count, StockOperateType.CPT_RELEASE);
    }

}
