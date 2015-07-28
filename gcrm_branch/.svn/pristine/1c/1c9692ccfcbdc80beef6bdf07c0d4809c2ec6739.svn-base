package com.baidu.gcrm.ad.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.dao.AdvertiseQuotationRepository;
import com.baidu.gcrm.ad.dao.AdvertiseQuotationRepositoryCustom;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.web.vo.QuotationRecordVO;
import com.baidu.gcrm.quote.model.PriceType;

/**
 * 广告报价业务服务实现
 * 
 * @author zhangxiaobin
 *
 */
@Service
public class AdvertiseQuotationServiceImpl implements IAdvertiseQuotationService {
	
	@Autowired
	private AdvertiseQuotationRepository quotationDao;
	
	@Autowired
	private AdvertiseQuotationRepositoryCustom adQuotationRepositoryCustom;
	
	@Override
	public void save(AdvertiseQuotation quotation) {
		quotationDao.save(quotation);
	}

	@Override
	public AdvertiseQuotation getAdvertiseQuotationById(Long id) {
		return quotationDao.findOne(id);
	}

	@Override
	public AdvertiseQuotation findByAdSolutionContentId(Long id) {
		return quotationDao.findByAdSolutionContentId(id);
	}
	
	@Override
	public QuotationRecordVO findQuotationRecordByContent(Long contentId){
		return convertToQuotationRecordVO(
				findByAdSolutionContentId(contentId));
	}
	
	public List<QuotationRecordVO> findQuotationRecordBySolution(Long solutionId){
		List<QuotationRecordVO> result = new ArrayList<QuotationRecordVO>();
		List<AdvertiseQuotation> quotations = adQuotationRepositoryCustom.findByByAdSolutionId(solutionId);
		for(AdvertiseQuotation quotation:quotations){
			result.add(convertToQuotationRecordVO(quotation));
		}
		return result;
	}
	
	private QuotationRecordVO convertToQuotationRecordVO(AdvertiseQuotation quotation){
		QuotationRecordVO quotationRecoredVo = null;
		if(quotation != null){
			quotationRecoredVo = new QuotationRecordVO();
			quotationRecoredVo.setContentId(quotation.getAdSolutionContentId());
			if(quotation.getPriceType().equals(PriceType.ratio)
					||quotation.getBillingModelId()==2){
				if(quotation.getProductRatioMine()==null
						||quotation.getProductRatioMine()<0){
					quotationRecoredVo.setRecord(false);
				}else{
					quotationRecoredVo.setRecord(true);
					quotationRecoredVo.setLessProductRatio(
							quotation.getRatioMine()-quotation.getProductRatioMine()<0);
				}
			}else{
				if(quotation.getPublishPrice()==null
						||quotation.getPublishPrice()<0){
					quotationRecoredVo.setRecord(false);
				}else{
					quotationRecoredVo.setRecord(true);
				}
				quotationRecoredVo.setDiscount(quotation.getDiscount());
			}
		}
		return quotationRecoredVo;
	}

    @Override
    public List<AdvertiseQuotation> findQuotationByAdContentIds(List<Long> adContentIds) {
        return quotationDao.findByAdSolutionContentIdIn(adContentIds);
    }

	@Override
	public void moveToHistory(List<Long> adContentIds) {
		adQuotationRepositoryCustom.moveToHistory(adContentIds);
	}

	@Override
	public void deleteByAdSolutionContentId(Long adSolutionContentId) {
		quotationDao.deleteByAdSolutionContentId(adSolutionContentId);
	}

	@Override
	public void deleteByAdSolutionContentIdIn(List<Long> adSolutionContentIds) {
		quotationDao.deleteByAdSolutionContentIdIn(adSolutionContentIds);
	}

}
