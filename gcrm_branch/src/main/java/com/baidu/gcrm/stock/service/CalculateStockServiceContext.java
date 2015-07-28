package com.baidu.gcrm.stock.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service
public class CalculateStockServiceContext {
    
    @Resource(name = "CPMCalculateStockService")
    ICalculateStockService cpmCalculateStockService;
    
    @Resource(name = "CPTCalculateStockService")
    ICalculateStockService cptCalculateStockService;
    
    private Map<String, ICalculateStockService> serviceMap = null;
    
    @PostConstruct
    public void initServiceMap() {
        serviceMap = new HashMap<String, ICalculateStockService>();
        serviceMap.put(BillingModel.CPM_ID.toString(), cpmCalculateStockService);
        serviceMap.put(BillingModel.CPT_ID.toString(), cptCalculateStockService);
    }
    
    public ICalculateStockService getCalculateStockService(Long billingModelId) {
        return serviceMap.get(String.valueOf(billingModelId));
    }

}
