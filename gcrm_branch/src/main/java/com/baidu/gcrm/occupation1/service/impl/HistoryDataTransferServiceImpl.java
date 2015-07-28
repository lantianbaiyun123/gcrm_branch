package com.baidu.gcrm.occupation1.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.dao.AdvertiseQuotationRepository;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;
import com.baidu.gcrm.occupation1.dao.PositionDateRelationRepository;
import com.baidu.gcrm.occupation1.dao.PositionDateRelationRepositoryCustom;
import com.baidu.gcrm.occupation1.dao.PositionDateRepository;
import com.baidu.gcrm.occupation1.service.IHistoryDataTransferService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.stock.service.IStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service
public class HistoryDataTransferServiceImpl implements IHistoryDataTransferService {

    @Autowired
    IPositionService positionService;

    @Autowired
    PositionDateRepository positionDateDao;

    @Autowired
    IStockService stockService;

    @Autowired
    PositionDateRelationRepositoryCustom relationCustomDao;

    @Autowired
    AdvertiseQuotationRepository quotationDao;

    @Autowired
    PositionDateRelationRepository relationDao;

    @Autowired
    @Qualifier("CPTCalculateStockService")
    ICalculateStockService cptCalculateStockService;

    @Autowired
    @Qualifier("CPMCalculateStockService")
    ICalculateStockService cpmCalculateStockService;

    @Override
    public void initFromHistory() {
        long start = System.currentTimeMillis();
        Date date = DateUtils.getCurrentDateOfZero();
        int moveCount = positionDateDao.moveEnabledFromPositionOccupationToPositionDate(date);
        LoggerHelper.info(getClass(), "move {} records to g_position_date.", moveCount);
        List<Position> positions = positionService.findByStatusAndType(PositionStatus.enable, PositionType.position);
        // add position occupation
        long createNewStocksStart = System.currentTimeMillis();
        stockService.createNewStocks(positionDateDao.findAll(), getPositionMap(positions));
        LoggerHelper.info(getClass(), "createNewStocks cost={}", (System.currentTimeMillis() - createNewStocksStart));
        List<AdvertiseQuotation> quotations = quotationDao.findByBillingModelAndContentStatus();
        List<Long> cptContentIds = new ArrayList<Long>();
        Map<Long, Long> dailyAmountMap = new HashMap<Long, Long>();
        for (AdvertiseQuotation quotation : quotations) {
            Long billingModelId = quotation.getBillingModelId();
            if (billingModelId == null) {
                continue;
            }
            if (billingModelId.longValue() == BillingModel.CPM_ID) {
                dailyAmountMap.put(quotation.getAdSolutionContentId(), quotation.getDailyAmount());
            } else {
                cptContentIds.add(quotation.getAdSolutionContentId());
            }
        }
        LoggerHelper.info(getClass(), "there are {} cpm contents and {} cpt contents related", dailyAmountMap.size(),
                cptContentIds.size());
        long cpmStart = System.currentTimeMillis();
        occupyCPMStock(dailyAmountMap);
        LoggerHelper.info(getClass(), "occupyCPMStock cost={}", (System.currentTimeMillis() - cpmStart));
        cpmStart = System.currentTimeMillis();
        occupyCPTStock(cptContentIds);
        LoggerHelper.info(getClass(), "occupyCPTStock cost={}", (System.currentTimeMillis() - cpmStart));
        LoggerHelper.info(getClass(), "initFromHistory complete cost={}", (System.currentTimeMillis() - start));
    }

    private Map<Long, Position> getPositionMap(List<Position> positions) {
        Map<Long, Position> positionMap = new HashMap<Long, Position>();
        for (Position position : positions) {
            positionMap.put(position.getId(), position);
        }
        return positionMap;
    }

    private void occupyCPMStock(Map<Long, Long> dailyAmountMap) {
        if (CollectionUtils.isEmpty(dailyAmountMap.keySet())) {
            LoggerHelper.info(getClass(), "no cpm stocks.");
            return;
        }
        List<AdvertisePositionDateRelation> relations = relationDao.findByAdContentIdInAndDateFrom(
                dailyAmountMap.keySet(), DateUtils.getCurrentDateOfZero());
        long cur = System.currentTimeMillis();
        LoggerHelper.info(getClass(), "need to update {} cpm stocks.", relations.size());
        for (AdvertisePositionDateRelation relation : relations) {
            Long dailyAmount = dailyAmountMap.get(relation.getAdContentId());
            if (dailyAmount == null) {
                continue;
            }
            List<Long> ids = new ArrayList<Long>();
            ids.add(relation.getPositionOccId());
            cpmCalculateStockService.batchOccupy(ids, dailyAmount);
        }
        LoggerHelper.info(getClass(), "need to update {} cpm stocks. cost={}", relations.size(),(System.currentTimeMillis()-cur));
    }

    private void occupyCPTStock(List<Long> cptContentIds) {
        Map<Long, Long> cptCountGroupByOcc = relationCustomDao.getCountGroupByAdContentIdInAndDateFrom(cptContentIds,
                DateUtils.getCurrentDateOfZero());
        long cur = System.currentTimeMillis();
        LoggerHelper.info(getClass(), "need to update {} cpt stocks", cptCountGroupByOcc.keySet().size());
        for (Long occId : cptCountGroupByOcc.keySet()) {
            List<Long> ids = new ArrayList<Long>();
            ids.add(occId);
            cptCalculateStockService.batchOccupy(ids, cptCountGroupByOcc.get(occId));
        }
        LoggerHelper.info(getClass(), "need to update {} cpt stocks, cost={}", cptCountGroupByOcc.keySet().size(),(System.currentTimeMillis()-cur));
    }

}
