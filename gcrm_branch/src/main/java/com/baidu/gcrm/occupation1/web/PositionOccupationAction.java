package com.baidu.gcrm.occupation1.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.occupation1.service.IPositionDateService;
import com.baidu.gcrm.occupation1.web.vo.CombinePeriodsCondition;
import com.baidu.gcrm.occupation1.web.vo.DateOccupation;
import com.baidu.gcrm.occupation1.web.vo.DateOccupationQueryCondition;
import com.baidu.gcrm.occupation1.web.vo.Periods;
import com.baidu.gcrm.occupation1.web.vo.PositionOccupationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.RotationType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.schedule1.service.ISchedulesService;
import com.baidu.gcrm.stock.helper.CalculatorHelper;
import com.baidu.gcrm.stock.service.IStockService;
import com.baidu.gcrm.stock.vo.AdContentBriefDTO;
import com.baidu.gcrm.stock.vo.DateStock;
import com.baidu.gcrm.stock.vo.PositionOccupationDetail;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Controller
@RequestMapping("/occupation")
public class PositionOccupationAction {

    @Autowired
    IAdSolutionContentService contentService;
    
    @Autowired
    IPositionService positionService;
    
    @Autowired
    IPositionDateService posDateService;
    
    @Autowired
    IStockService stockService;
    
    @Autowired
    ISchedulesService scheduleService;
    
    @Autowired
    IPositionDateRelationService relationService;
    
    @Autowired
    IAdvertiseQuotationService quotationService;
    
    @RequestMapping("/{positionId}")
    @ResponseBody
    public JsonBean<PositionOccupationDetail> findOccupationDetails(@PathVariable("positionId") Long positionId) {
        PositionOccupationDetail detail = new PositionOccupationDetail();
        Position position = positionService.findById(positionId);
        if (position == null) {
            return JsonBeanUtil.convertBeanToJsonBean(null, "position.not.exists");
        }
        List<AdContentBriefDTO> contentBriefDTOs = contentService.findContentsHoldingPosition(positionId);
        detail.setSchedules(contentBriefDTOs);
        detail.setPositionId(positionId);
        detail.setRotation(RotationType.yes.equals(position.getRotationType()));
        detail.setTotalCount(position.getSalesAmount());
        
        return JsonBeanUtil.convertBeanToJsonBean(detail);
    }

    @RequestMapping("/maxDate/{positionId}")
    @ResponseBody
    public JsonBean<Date> maxDate(@PathVariable("positionId") Long positionId) {
        Date maxDate = posDateService.getFarthestDateByPosition(positionId);
        if (maxDate == null) {
            return JsonBeanUtil.convertBeanToJsonBean(null, "occupation.error.100");
        }
        return JsonBeanUtil.convertBeanToJsonBean(maxDate);
    }
    
    /**
     * 合并时间段；如果是CPM或CPT方式，去掉时间范围内库存为0的时间；如果是CPM返回时间段内的最小库存
     * <p>
     * eg, 时间段是2014-01-01,2014-01-08;2014-01-05,2014-01-10;其中2014-01-03,2014-01-05库存为0
     * 选择CPM或CPT计费方式，则返回2014-01-01,2014-01-02;2014-01-06,2014-01-10</p>
     */
    @RequestMapping("/combinePeriods")
    @ResponseBody
    public JsonBean<Periods> combinePeriods(@RequestBody CombinePeriodsCondition condition) {
        Periods combinedPeriods = new Periods();
        Long billingModelId = condition.getBillingModelId();
        if (!BillingModel.CPM_ID.equals(billingModelId) && !BillingModel.CPT_ID.equals(billingModelId)) {
            List<DatePeriod> combined = DatePeriodHelper.combineAndGetDatePeriods(condition.getDatePeriod());
            combinedPeriods.setDatePeriod(combined);
            combinedPeriods.setTotalDays(DatePeriodHelper.getTotalDays(combined));
            return JsonBeanUtil.convertBeanToJsonBean(combinedPeriods);
        }
        getMinStockOfCPM(condition, combinedPeriods);
        return JsonBeanUtil.convertBeanToJsonBean(combinedPeriods);
    }

    private void getMinStockOfCPM(CombinePeriodsCondition condition, Periods combinedPeriods) {
        DateOccupationQueryCondition queryCondition = transToQueryCondition(condition);
        List<DateOccupation> dateOccs = posDateService.queryDateOccupations(queryCondition);
        if (condition.getOldContentId() != null && CollectionUtils.isNotEmpty(dateOccs)) {
            AdSolutionContent content = contentService.findOne(condition.getOldContentId());
            // 如果旧内容的排期单都已释放，代表库存早已释放，无需再单独减掉库存；如果有确认或锁定排期单，需要单独减掉库存
            if (content != null && !ApprovalStatus.saving.equals(content.getApprovalStatus())
                    && !scheduleService.isSchedulesAllReleased(condition.getOldContentId())) {
                removeStockOfOldContent(queryCondition, dateOccs, content);
            }
        }
        Long minStock = null;;
        Set<Date> selectedDates = DatePeriodHelper.getAllDates(condition.getDatePeriod());
        List<Date> minStockDates = new ArrayList<Date>();
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
        for (DateOccupation dateOcc : dateOccs) {
            Date date = DateUtils.getString2Date(format.format(dateOcc.getDate()));
            if (selectedDates.contains(date) && dateOcc.isFull()) {
                selectedDates.remove(date);
                continue;
            }
            if (!selectedDates.contains(date)) {
                continue;
            }
            if (minStock == null || minStock.longValue() > dateOcc.getRemained()) {
                minStock = dateOcc.getRemained();
                minStockDates.clear();
                minStockDates.add(date);
            } else if (minStock.longValue() == dateOcc.getRemained()) {
                minStockDates.add(date);
            }
        }
        List<Date> sortedDates = new ArrayList<Date>(selectedDates);
        Collections.sort(sortedDates);
        List<DatePeriod> combined = DatePeriodHelper.combineDates(sortedDates);
        combinedPeriods.setDatePeriod(combined);
        combinedPeriods.setTotalDays(DatePeriodHelper.getTotalDays(combined));
        combinedPeriods.setMinStock(minStock);
        Collections.sort(minStockDates);
        combinedPeriods.setMinStockPeriods(DatePeriodHelper.getDatePeriodStr(minStockDates));
    }

    private DateOccupationQueryCondition transToQueryCondition(CombinePeriodsCondition condition) {
        DateOccupationQueryCondition queryCondition = new DateOccupationQueryCondition();
        queryCondition.setBillingModelId(condition.getBillingModelId());
        queryCondition.setOldContentId(condition.getOldContentId());
        queryCondition.setPositionId(condition.getPositionId());
        queryCondition.setPeriod(DatePeriodHelper.getMaxRange(condition.getDatePeriod()));
        return queryCondition;
    }
    
    @RequestMapping("/queryDateOccupation")
    @ResponseBody
    public JsonBean<PositionOccupationVO> queryDateOccupation(@RequestBody DateOccupationQueryCondition condition) {
        Position position = positionService.findById(condition.getPositionId());
        if (position == null) {
            return JsonBeanUtil.convertBeanToJsonBean(null, "position.not.exists");
        }
        Date from = condition.getPeriod().getFrom();
        if (from != null && from.before(DateUtils.getCurrentDateOfZero())) {
            from = DateUtils.getCurrentDateOfZero();
            condition.getPeriod().setFrom(from);
        }
        PositionOccupationVO vo = new PositionOccupationVO();
        vo.setBillingModelId(condition.getBillingModelId());
        vo.setPositionId(condition.getPositionId());
        vo.setFrom(from);
        vo.setTo(condition.getPeriod().getTo());
        vo.setRotation(RotationType.yes.equals(position.getRotationType()));
        vo.setTotalCount(position.getSalesAmount());
        List<DateOccupation> dateOccs = posDateService.queryDateOccupations(condition);
        vo.setOccupations(dateOccs);
        if (condition.getOldContentId() == null || CollectionUtils.isEmpty(dateOccs)) {
            return JsonBeanUtil.convertBeanToJsonBean(vo);
        }
        AdSolutionContent content = contentService.findOne(condition.getOldContentId());
        // 如果旧内容的排期单都已释放，代表库存早已释放，无需再单独减掉库存；如果有确认或锁定排期单，需要单独减掉库存
        if (content != null && !ApprovalStatus.saving.equals(content.getApprovalStatus())
                && !scheduleService.isSchedulesAllReleased(condition.getOldContentId())) {
            removeStockOfOldContent(condition, dateOccs, content);
        }
        vo.setOccupations(dateOccs);
        return JsonBeanUtil.convertBeanToJsonBean(vo);
    }
    
    @SuppressWarnings("unchecked")
    private void removeStockOfOldContent(DateOccupationQueryCondition condition, List<DateOccupation> dateOccs,
            AdSolutionContent oldContent) {
        Long positionId = oldContent.getPositionId();
        if (!positionId.equals(condition.getPositionId())) { // not the same position
            return;
        }
        AdvertiseQuotation quotation = quotationService.findByAdSolutionContentId(condition.getOldContentId());
        if (quotation == null || quotation.getBillingModelId() == null) {
            return;
        }
        Long billingModelId = quotation.getBillingModelId();
        // 除了cpm和cpt其他计费方式对库存没有影响
        if (!BillingModel.CPM_ID.equals(billingModelId) && !BillingModel.CPT_ID.equals(billingModelId)) {
            return;
        }
        
        List<Date> queryDates = new ArrayList<Date>();
        for (DateOccupation dateOcc : dateOccs) {
            queryDates.add(dateOcc.getDate());
        }
        List<Date> oldDates = relationService.getDatesByContentIdAndDateBetween(oldContent.getId(), condition
                .getPeriod().getFrom(), condition.getPeriod().getTo());
        
        Collection<Date> intersectDates = CollectionUtils.intersection(queryDates, oldDates);
        if (CollectionUtils.isEmpty(intersectDates)) {
            return;
        }
        Long removeStock = quotation.getDailyAmount();;
        if (BillingModel.CPT_ID.equals(billingModelId)) {
            removeStock = 1L;
        } 
        if (removeStock == null) {
            return;
        }
        if (billingModelId.equals(condition.getBillingModelId())) {
            for (DateOccupation dateOcc : dateOccs) {
                if (!intersectDates.contains(dateOcc.getDate())) {
                    continue;
                }
                dateOcc.setOccupied(dateOcc.getOccupied() - removeStock);
                dateOcc.setRemained(dateOcc.getRemained() + removeStock);
                dateOcc.setFull(dateOcc.getRemained() <= 0L);
                dateOcc.setBiddingCount(dateOcc.getBiddingCount() - 1);
            }
        } else if (BillingModel.CPT_ID.equals(condition.getBillingModelId())
                || BillingModel.CPM_ID.equals(condition.getBillingModelId())) { 
            Map<String, DateStock> otherDateStockMap = stockService.getDateStockMap(billingModelId, positionId,
                    intersectDates);
            Map<String, DateStock> queryDateStockMap = stockService.getDateStockMap(condition.getBillingModelId(),
                    positionId, intersectDates);
            for (DateOccupation dateOcc : dateOccs) {
                if (!intersectDates.contains(dateOcc.getDate())) {
                    continue;
                }
                String date = DateUtils.getDate2ShortString(dateOcc.getDate());
                DateStock oDateStock = otherDateStockMap.get(date);
                DateStock qDateStock = queryDateStockMap.get(date);
                if (oDateStock == null || qDateStock == null) {
                    continue;
                }
                dateOcc.setOccupied(qDateStock.getRealOccupiedStock()
                        + CalculatorHelper.cal(oDateStock.getRealOccupiedStock() - removeStock,
                                oDateStock.getTotalStock(), qDateStock.getTotalStock()));
                dateOcc.setRemained(qDateStock.getTotalStock() - dateOcc.getOccupied());
                dateOcc.setFull(dateOcc.getRemained() <= 0L);
                dateOcc.setBiddingCount(dateOcc.getBiddingCount() - 1);
            }
        } else { // query CPC\CPA\CPS
            Map<String, DateStock> otherDateStockMap = stockService.getDateStockMap(billingModelId, positionId,
                    intersectDates);
            for (DateOccupation dateOcc : dateOccs) {
                if (!intersectDates.contains(dateOcc.getDate())) {
                    continue;
                }
                String date = DateUtils.getDate2ShortString(dateOcc.getDate());
                DateStock oDateStock = otherDateStockMap.get(date);
                if (oDateStock == null) {
                    continue;
                }
                long remained = oDateStock.getTotalStock() - (oDateStock.getOccupiedStock() - removeStock);
                dateOcc.setOccupied(oDateStock.getOccupiedStock() - removeStock);
                dateOcc.setRemained(remained);
                dateOcc.setFull(remained <= 0L);
                dateOcc.setBiddingCount(dateOcc.getBiddingCount() - 1);
            }
        }
    }
}
