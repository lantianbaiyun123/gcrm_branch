package com.baidu.gcrm.occupation.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepository;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepositoryCustom;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;
import com.baidu.gcrm.occupation.service.IADPositionDateRelationService;
import com.baidu.gcrm.occupation1.dao.PositionDateRepository;
import com.baidu.gcrm.occupation1.model.PositionDate;
import com.baidu.gcrm.stock.service.CalculateStockServiceContext;
import com.baidu.gcrm.stock.service.ICalculateStockService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

@Service
public class ADPositionDateRelationServiceImpl implements IADPositionDateRelationService {
	
	@Autowired
	PositionDateRepository positionDateRepository;
	@Autowired
	private IADPositionDateRelationRepository relationDao;
	@Autowired
	private IAdSolutionContentService adContentService;
	
	@Autowired
	IAdvertiseQuotationService advertiseQuotationService;
	
	@Autowired
    CalculateStockServiceContext calculateStockServiceContext;
	
	@Autowired
    private IADPositionDateRelationRepositoryCustom relationCustomDao;

	@Override
	public int buildRelationshipAndOccypyStock(Long adContentId, Long positionId, Set<Date> dates) {
	    AdvertiseQuotation advertiseQuotation = advertiseQuotationService.findByAdSolutionContentId(adContentId);
	    
		List<AdvertisePositionDateRelation> relations = new ArrayList<AdvertisePositionDateRelation>();
		List<PositionDate> positionDates = positionDateRepository.findByPositionIdAndDateIn(positionId, dates);
		for (PositionDate positionDate : positionDates) {
			AdvertisePositionDateRelation relation = new AdvertisePositionDateRelation();
			relation.setAdContentId(adContentId);
			relation.setPositionOccId(positionDate.getId());
			relations.add(relation);
			
			if (advertiseQuotation == null 
			        || (BillingModel.CPM_ID.longValue() !=  advertiseQuotation.getBillingModelId()
			        && BillingModel.CPT_ID.longValue() !=  advertiseQuotation.getBillingModelId())) {
			    continue;
			}
		    Long billingModelId = advertiseQuotation.getBillingModelId();
		    Long occupyCount = Long.valueOf(-1);
		    if (BillingModel.CPM_ID.longValue() == billingModelId.longValue()) {
		        occupyCount = advertiseQuotation.getDailyAmount();
		    } else if (BillingModel.CPT_ID.longValue() == billingModelId.longValue()) {
		        occupyCount = Long.valueOf(1);
		    }
//		    ICalculateStockService calculateStockService = calculateStockServiceContext.getCalculateStockService(billingModelId);
//		    if (calculateStockService != null) {
//		        calculateStockService.occupy(positionDate.getId(), occupyCount);
//		    }
		}
		List<AdvertisePositionDateRelation> saved = relationDao.save(relations);
		return saved.size();
	}
	
	@Override
	public int removeRelationships(Long adContentId) {
	    releaseStock(adContentId);
		return relationDao.deleteByAdContentId(adContentId);
	}
	
	@Override
	public int removeRelationships(List<Long> adContentIds) {
	    for (Long temAdContentId : adContentIds) {
	        releaseStock(temAdContentId);
	    }
		return relationDao.deleteByAdContentIds(adContentIds);
	}
	
	/**
	 * 释放库存
	 * @param adContentId
	 */
	private void releaseStock(Long adContentId) {
	    AdvertiseQuotation advertiseQuotation = advertiseQuotationService.findByAdSolutionContentId(adContentId);
	    if (advertiseQuotation == null 
                || (BillingModel.CPM_ID.longValue() !=  advertiseQuotation.getBillingModelId()
                && BillingModel.CPT_ID.longValue() !=  advertiseQuotation.getBillingModelId())) {
            return;
        }
	    List<AdvertisePositionDateRelation> relations = relationDao.findByAdContentId(adContentId);
	    if (CollectionUtils.isEmpty(relations)) {
	        return;
	    }
	    Long billingModelId = advertiseQuotation.getBillingModelId();
        Long occupyCount = Long.valueOf(-1);
        if (BillingModel.CPM_ID.longValue() == billingModelId.longValue()) {
            occupyCount = advertiseQuotation.getDailyAmount();
        } else if (BillingModel.CPT_ID.longValue() == billingModelId.longValue()) {
            occupyCount = Long.valueOf(1);
        }
        
	    ICalculateStockService calculateStockService = calculateStockServiceContext.getCalculateStockService(billingModelId);
	    if (calculateStockService == null) {
	        return;
	    }
//	    for (AdvertisePositionDateRelation temRelations : relations) {
//            calculateStockService.release(temRelations.getPositionOccId(), occupyCount);
//	    }
	}

    @Override
    public List<AdvertisePositionDateRelation> findByOccupations(
            Collection<Long> occupationIds) {
        return relationDao.findByOccupations(occupationIds);
    }

    @Override
    public int deleteByAdContentIdAndPositionOccId(Long adContentId,
            List<Long> positionOccIds) {
        return relationDao.deleteByAdContentIdAndPositionOccId(adContentId, positionOccIds);
    }

    @Override
    public void save(List<AdvertisePositionDateRelation> entities) {
        relationDao.save(entities);
    }

    @Override
    public Long findCountsByAdContentIdAndPositionOccId(Long adContentId,
            List<Long> positionOccIds) {
        return relationDao.findCountsByAdContentIdAndPositionOccId(adContentId, positionOccIds);
    }

    @Override
    public List<Long> findAdContentIdByOccupation(Collection<Long> occupationIds) {
        return relationDao.findAdContentIdByOccupation(occupationIds);
    }

    @Override
    public List<AdvertisePositionDateRelation> findByAdContentIdAndOccIds(Long adContentId,
            Collection<Long> occupationIds) {
        return relationDao.findByAdContentIdAndOccIds(adContentId, occupationIds);
    }
    
    @Override
    public List<AdvertisePositionDateRelation> findRelationsWithScheduleByOccupations(Collection<Long> occupationIds) {
    	return relationDao.findRelationsWithScheduleByOccupations(occupationIds);
    }
    
    @Override
    public int buildRelationByAdContentId(Long adContentId) {
    	AdSolutionContent content = adContentService.findOne(adContentId);
    	Set<Date> allDates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
    	return buildRelationshipAndOccypyStock(adContentId, content.getPositionId(), allDates);
    }
    
    @Override
    public int buildRelationByAdContent(AdSolutionContent content) {
    	Set<Date> allDates = DatePeriodHelper.getAllDates(content.getPeriodDescription());
    	return buildRelationshipAndOccypyStock(content.getId(), content.getPositionId(), allDates);
    }

    @Override
    public Map<Long, Long> getCountGroupByPositionDateId(Collection<Long> positionDateIds) {
        return relationCustomDao.getCountGroupByPositionOccId(positionDateIds);
    }
}
