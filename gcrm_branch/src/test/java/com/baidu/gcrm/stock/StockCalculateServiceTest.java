package com.baidu.gcrm.stock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.service.CalculateStockServiceContext;
import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Ignore
public class StockCalculateServiceTest extends BaseTestContext {
    
    @Autowired
    CalculateStockServiceContext calServiceContext;
    
    @Before
    public void init() throws Exception {
        TestUtils.initDatabase(dataSource, "datas/g_stock.xml");
    }
    
    @Test
    public void cptOccupyTest() {
        Long positionDateId = 1L;
        List<Long> positionDateIds = new ArrayList<Long>();
        positionDateIds.add(positionDateId);
        ICalculateStockService calculateStockService = calServiceContext.getCalculateStockService(BillingModel.CPT_ID);
        calculateStockService.batchOccupy(positionDateIds, 1L);
        
        Stock cptStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPT_ID);
        long cptRemainStock = cptStock.getTotalStock().longValue() - cptStock.getOccupiedStock().longValue();
        Assert.assertTrue(cptRemainStock == 0);
        
        Stock cpmStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPM_ID);
        long cpmRemainStock = cpmStock.getTotalStock().longValue() - cpmStock.getOccupiedStock().longValue();
        Assert.assertTrue(cpmRemainStock == 0);
        boolean changeFlag = false;
        try {
            calculateStockService.batchOccupy(positionDateIds, 1L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CRMRuntimeException);
            changeFlag = true;
        }
        
        Assert.assertTrue(changeFlag);
        calculateStockService.batchRelease(positionDateIds, 1L);
        calculateStockService.batchOccupy(positionDateIds, 1L);
        
        System.out.print("cptOccupyTest pass");
        
    }
    
    @Test
    public void cpmOccupyTest() {
        Long positionDateId = 1L;
        List<Long> positionDateIds = new ArrayList<Long>();
        positionDateIds.add(positionDateId);
        ICalculateStockService calculateStockService = calServiceContext.getCalculateStockService(BillingModel.CPM_ID);
        calculateStockService.batchOccupy(positionDateIds, 500L);
        
        Stock cptStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPT_ID);
        long cptRemainStock = cptStock.getTotalStock().longValue() - cptStock.getOccupiedStock().longValue();
        Assert.assertTrue(cptRemainStock == 0);
        
        Stock cpmStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPM_ID);
        long cpmRemainStock = cpmStock.getTotalStock().longValue() - cpmStock.getOccupiedStock().longValue();
        Assert.assertTrue(cpmRemainStock == 500);
        
        
        boolean changeFlag = false;
        try {
            calculateStockService.batchOccupy(positionDateIds, 1001L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CRMRuntimeException);
            changeFlag = true;
        }
        
        Assert.assertTrue(changeFlag);
        calculateStockService.batchRelease(positionDateIds, 500L);
        calculateStockService.batchOccupy(positionDateIds, 1000L);
        
        System.out.print("cpmOccupyTest pass");
        
    }
    
    @Test
    public void cpmAndCptOccupyTest() {
        Long positionDateId = 2L;
        List<Long> positionDateIds = new ArrayList<Long>();
        positionDateIds.add(positionDateId);
        ICalculateStockService calculateStockService = calServiceContext.getCalculateStockService(BillingModel.CPT_ID);
        calculateStockService.batchOccupy(positionDateIds, 1L);
        
        Stock cptStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPT_ID);
        long cptRemainStock = cptStock.getTotalStock().longValue() - cptStock.getOccupiedStock().longValue();
        Assert.assertTrue(cptRemainStock == 2);
        
        Stock cpmStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPM_ID);
        long cpmRemainStock = cpmStock.getTotalStock().longValue() - cpmStock.getOccupiedStock().longValue();
        Assert.assertTrue(cpmRemainStock == 500);
        
        
        boolean changeFlag = false;
        calculateStockService.batchOccupy(positionDateIds, 1L);
        Assert.assertFalse(changeFlag);
        cpmStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPM_ID);
        cpmRemainStock = cpmStock.getTotalStock().longValue() - cpmStock.getOccupiedStock().longValue();
        Assert.assertTrue(cpmRemainStock == 250);
        
        calculateStockService.batchRelease(positionDateIds, 1L);
        cptStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPT_ID);
        cptRemainStock = cptStock.getTotalStock().longValue() - cptStock.getOccupiedStock().longValue();
        Assert.assertTrue(cptRemainStock == 2);
        
        
        try {
            calculateStockService.batchOccupy(positionDateIds, 3L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CRMRuntimeException);
            changeFlag = true;
        }
        Assert.assertTrue(changeFlag);
        
        calculateStockService.batchOccupy(positionDateIds, 1L);
        calculateStockService = calServiceContext.getCalculateStockService(BillingModel.CPM_ID);
        calculateStockService.batchOccupy(positionDateIds, 250L);
        
        cpmStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPM_ID);
        cpmRemainStock = cpmStock.getTotalStock().longValue() - cpmStock.getOccupiedStock().longValue();
        Assert.assertTrue(cpmRemainStock == 0);
        calculateStockService.batchRelease(positionDateIds, 1L);
        cptStock = calculateStockService.findSingleStock(positionDateId, BillingModel.CPT_ID);
        cptRemainStock = cptStock.getTotalStock().longValue() - cptStock.getOccupiedStock().longValue();
        Assert.assertTrue(cptRemainStock == 0);
        
        System.out.print("cpmAndCptOccupyTest pass");
        
    }

}
