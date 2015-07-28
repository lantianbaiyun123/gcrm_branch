package com.baidu.gcrm.occupation1.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;
import com.baidu.gcrm.occupation1.dao.PositionDateRelationRepository;
import com.baidu.gcrm.occupation1.dao.PositionDateRelationRepositoryCustom;
import com.baidu.gcrm.occupation1.model.PositionDate;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.occupation1.service.IPositionDateService;
import com.baidu.gcrm.stock.model.Stock;
import com.baidu.gcrm.stock.service.CalculateStockServiceContext;
import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.stock.service.IStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.google.common.collect.Multimap;

@Service
public class PositionDateRelationServiceImpl implements IPositionDateRelationService {
	
    @Autowired
    private PositionDateRelationRepository relationDao;
    
    @Autowired
    PositionDateRelationRepositoryCustom relationCustomDao;
    
	@Autowired
	IPositionDateService positionDateService;
	
	@Autowired
	IAdvertiseSolutionService adSolutionService;
	
	@Autowired
	private IAdSolutionContentService adContentService;
	
	@Autowired
	IAdvertiseQuotationService advertiseQuotationService;
	
	@Autowired
    CalculateStockServiceContext calculateStockServiceContext;
	
	@Autowired
	IStockService stockService;

	@Override
	public void buildRelationshipAndOccypyStock(AdSolutionContent content, Set<Date> dates) {
	    Long adContentId = content.getId();
	    AdvertiseQuotation advertiseQuotation = advertiseQuotationService.findByAdSolutionContentId(adContentId);
	    if (advertiseQuotation == null) {
	        throw new CRMRuntimeException("advertise.content.quotation.null");
	    }
	    Long billingModelId = advertiseQuotation.getBillingModelId();
	    boolean isCPT = BillingModel.CPT_ID.equals(billingModelId);
        boolean stockNotOccupied = !(BillingModel.CPM_ID.equals(billingModelId) || isCPT);
	    
		List<AdvertisePositionDateRelation> relations = new ArrayList<AdvertisePositionDateRelation>();
		List<PositionDate> positionDates = positionDateService.findByPositionIdAndDateIn(content.getPositionId(), dates);
		Map<Long, PositionDate> positionDateMap = new HashMap<Long, PositionDate>();
		for (PositionDate positionDate : positionDates) {
			AdvertisePositionDateRelation relation = new AdvertisePositionDateRelation();
			relation.setAdContentId(adContentId);
			relation.setPositionOccId(positionDate.getId());
			relations.add(relation);
			positionDateMap.put(positionDate.getId(), positionDate);
		}
		relationDao.save(relations);
		if (stockNotOccupied) {
		    return;
		}
		Long occupyCount = advertiseQuotation.getDailyAmount();
		if (isCPT) {
		    occupyCount = Long.valueOf(1);
		}
		if (occupyCount == null) {
		    throw new CRMRuntimeException("advertise.content.quotation.null");
		}
		
        ICalculateStockService calculateStockService = calculateStockServiceContext
                .getCalculateStockService(billingModelId);
		if (calculateStockService == null) {
		    return;
		}
		
		List<Long> fullPositionDateIds = calculateStockService.batchOccupy(positionDateMap.keySet(), occupyCount);
		
		notifyIfNecessary(content, isCPT, positionDateMap, fullPositionDateIds);
	}

    private void notifyIfNecessary(AdSolutionContent content, boolean isCPT, Map<Long, PositionDate> positionDateMap,
            List<Long> fullPositionDateIds) {
        if (CollectionUtils.isEmpty(fullPositionDateIds)) {
            return;
        }
        Set<PositionDate> fullPositionDates = new HashSet<PositionDate>();
		if (isCPT) {
            List<Stock> stocks = stockService.findByPositionDateIdInAndBillingModelId(BillingModel.CPM_ID,
                    fullPositionDateIds);
		    for (Stock stock : stocks) {
		        if (stock != null && stock.getTotalStock() <= stock.getOccupiedStock()) {
	                fullPositionDates.add(positionDateMap.get(stock.getPositionDateId()));
	            }
            }
		} else {
		    for (Long positionDateId : fullPositionDateIds) {
		        fullPositionDates.add(positionDateMap.get(positionDateId));
	        }
		}
		
		if (!CollectionUtils.isEmpty(fullPositionDates)) {
		    adSolutionService.notifyInfluencedAdSolution(fullPositionDates, content, null);
		}
    }
	
	@Override
    public void buildRelationByAdContentId(Long adContentId) {
        AdSolutionContent content = adContentService.findOne(adContentId);
        Set<Date> allDates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
        buildRelationshipAndOccypyStock(content, allDates);
    }
    
    @Override
    public void buildRelationByAdContent(AdSolutionContent content) {
        Set<Date> allDates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
        buildRelationshipAndOccypyStock(content, allDates);
    }
    
    @Override
    public void removeRelationshipsAndReleaseStock(List<AdvertisePositionDateRelation> relations, Long adContentId) {
        releaseStock(relations, adContentId);
        relationDao.delete(relations);
    }
	
	@Override
	public void removeRelationshipsAndReleaseStock(Long adContentId) {
	    List<AdvertisePositionDateRelation> relations = relationDao.findByAdContentId(adContentId);
	    removeRelationshipsAndReleaseStock(relations, adContentId);
	}
	
	@Override
	public void removeRelationshipsAndReleaseStock(List<Long> adContentIds) {
	    List<AdvertisePositionDateRelation> allRelations = new ArrayList<AdvertisePositionDateRelation>();
	    for (Long temAdContentId : adContentIds) {
	        List<AdvertisePositionDateRelation> relations = relationDao.findByAdContentId(temAdContentId);
	        releaseStock(relations, temAdContentId);
	        allRelations.addAll(relations);
	    }
	    if (CollectionUtils.isEmpty(allRelations)) {
	        return;
	    }
	    relationDao.delete(allRelations);
	}
	
	@Override
	public void removeRelationsAndReleaseStocksFromDate(Long adContentId, Date from) {
	    List<AdvertisePositionDateRelation> relations = relationDao.findByAdContentIdAndDateFrom(adContentId, from);
	    releaseStock(relations, adContentId);
	    relationDao.delete(relations);
	}
	
	@Override
	public void removeRelationsAndReleaseStocksByDateIn(Long adContentId, Collection<Date> dates) {
	    List<AdvertisePositionDateRelation> relations = findByAdContentIdAndDatesIn(adContentId, dates);
	    removeRelationshipsAndReleaseStock(relations, adContentId);
	}
	
	/**
	 * 释放库存
	 * @param adContentId
	 */
	private void releaseStock(List<AdvertisePositionDateRelation> relations, Long adContentId) {
	    if (CollectionUtils.isEmpty(relations)) {
            return;
        }
	    AdvertiseQuotation advertiseQuotation = advertiseQuotationService.findByAdSolutionContentId(adContentId);
	    if (advertiseQuotation == null) {
	        throw new CRMRuntimeException("advertise.content.quotation.null");
        }
	    if (BillingModel.CPM_ID.longValue() !=  advertiseQuotation.getBillingModelId()
                && BillingModel.CPT_ID.longValue() !=  advertiseQuotation.getBillingModelId()) {
	        return;
        }
        Long billingModelId = advertiseQuotation.getBillingModelId();
        Long occupyCount = Long.valueOf(-1);
        if (BillingModel.CPM_ID.longValue() == billingModelId.longValue()) {
            occupyCount = advertiseQuotation.getDailyAmount();
        } else if (BillingModel.CPT_ID.longValue() == billingModelId.longValue()) {
            occupyCount = Long.valueOf(1);
        }
        
        ICalculateStockService calculateStockService = calculateStockServiceContext
                .getCalculateStockService(billingModelId);
        if (calculateStockService == null) {
            return;
        }
        List<Long> positionDateIds = new ArrayList<Long>();
        for (AdvertisePositionDateRelation temRelations : relations) {
            positionDateIds.add(temRelations.getPositionOccId());
        }
        calculateStockService.batchRelease(positionDateIds, occupyCount);
	}
	
	@Override
	public int replaceAdContentIdInRelationship(Long oldContentId, Long replaceContentId) {
	    return relationCustomDao.replaceAdContentId(oldContentId, replaceContentId);
	}

    @Override
    public Map<Long, Long> getCountGroupByPositionDateId(Collection<Long> positionDateIds) {
        return relationCustomDao.getCountGroupByPositionOccId(positionDateIds);
    }
    
    @Override
    public Multimap<Long, String> getContentIdDateMap(Collection<Long> adContentIds, Date fromDate) {
        return relationCustomDao.getContentIdDateMap(adContentIds, fromDate);
    }
    
    @Override
    public List<Date> getDatesByContentIdAndDateBetween(Long adContentId, Date fromDate, Date toDate) {
        return relationDao.getDatesByContentIdAndDateBetween(adContentId, fromDate, toDate);
    }
    
    @Override
    public List<AdvertisePositionDateRelation> findByAdContentIdAndDatesIn(Long adContentId, Collection<Date> dates) {
        return relationDao.findByAdContentIdAndDateIn(adContentId, dates);
    }
}
