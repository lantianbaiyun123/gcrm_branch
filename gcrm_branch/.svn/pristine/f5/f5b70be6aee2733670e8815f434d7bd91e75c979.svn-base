package com.baidu.gcrm.stock.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.occupation1.model.PositionDate;
import com.baidu.gcrm.occupation1.service.IHistoryDataTransferService;
import com.baidu.gcrm.occupation1.service.IPositionDateService;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.service.IStockService;

@Controller
@RequestMapping("/stock")
public class StockAction extends ControllerHelper {
    
    @Autowired
    IStockService stockService;
    
    @Autowired
    IPositionDateService posDateService;
    
    @Autowired
    IHistoryDataTransferService hisDataTransService;
    
    @RequestMapping("/initPositionDate")
    public void initPositionDate() {
        posDateService.initPositionDateTimer();
    }
    
    @RequestMapping("/updateStock")
    public void updateStock() {
        stockService.updateStocksTimer();
    }

    @RequestMapping("/initFromHistory")
    @ResponseBody
    public JsonBean<String> initFromHistory() {
        List<PositionDate> positionDates = posDateService.findAll();
        List<Stock> stocks = stockService.findAll();
        if (CollectionUtils.isNotEmpty(positionDates) || CollectionUtils.isNotEmpty(stocks)) {
            return JsonBeanUtil.convertBeanToJsonBean(null, "You must empty tables g_position_date and g_stock first");
        }
        hisDataTransService.initFromHistory();
        return JsonBeanUtil.convertBeanToJsonBean("success");
    }
}
