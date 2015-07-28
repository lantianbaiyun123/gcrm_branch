package com.baidu.gcrm.ad.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.dao.AdvertiseMaterialApplyRepository;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialApplyService;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;

@Service
public class AdvertiseMaterialApplyServiceImpl implements
		IAdvertiseMaterialApplyService {
	
	@Autowired
	AdvertiseMaterialApplyRepository adMaterialApplyRepository;
	
	@Autowired
	IRandomStringService randomService;
	
	@Override
	public AdvertiseMaterialApply save(Long contentId,Long scheduleId) throws CRMBaseException{
		AdvertiseMaterialApply adMaterialApply = new AdvertiseMaterialApply();
    	adMaterialApply.setAdSolutionContentId(contentId);
    	adMaterialApply.setScheduleId(scheduleId);
    	adMaterialApply.setApplyState(MaterialApplyState.create);
    	adMaterialApply.setNumber(genrateMaterialApplyNumber());
    	adMaterialApply.setCreateTime(new Date());
    	adMaterialApply.setUpdateTime(new Date());
    	Long ucId = RequestThreadLocal.getLoginUserId();
    	adMaterialApply.setCreateOperator(ucId);
    	adMaterialApply.setUpdateOperator(ucId);
		adMaterialApplyRepository.save(adMaterialApply);
		return adMaterialApply;
	}

	@Override
	public void save(AdvertiseMaterialApply adMaterialApply) {
		adMaterialApplyRepository.save(adMaterialApply);
	}

	@Override
	public List<AdvertiseMaterialApply> findByAdSolutionContentId(Long id) {
		return adMaterialApplyRepository.findByAdSolutionContentId(id);
	}
	
	@Override
	public List<AdvertiseMaterialApply> findMaterialApplyByContentIdAndState(
			Long contentId, MaterialApplyState state) {
		return adMaterialApplyRepository.findMaterialApplyByContentIdAndState(contentId, state);
	}

	private String genrateMaterialApplyNumber() throws CRMBaseException {
	    return randomService.random(8, RandomType.random_material_apply, new IRandomCheckCallback(){
            @Override
            public boolean exists(String randomStr) {
                AdvertiseMaterialApply exists = adMaterialApplyRepository.findByNumber(randomStr);
                if (exists != null) {
                    return true;
                } else {
                    return false;
                }
            }
	        
	    });
    }
	
	public List<AdvertiseMaterialApply> findByAdSolutionContentIdAndApplyStateNot(Long contentId, MaterialApplyState state){
	    return adMaterialApplyRepository.findByAdSolutionContentIdAndApplyStateNot(contentId, state);
	}
}
