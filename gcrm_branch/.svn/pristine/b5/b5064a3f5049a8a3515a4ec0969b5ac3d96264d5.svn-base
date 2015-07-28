package com.baidu.gcrm.ad.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.service.IAdModifyRecordsService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.log.service.ModifyRecordService;

@Service
public class AdModifyRecordsServiceImpl implements IAdModifyRecordsService {

	private final static String MODIFY_RECORD_ADD_ADSOLUTIONCONTENT="modify.record.add.adsolutioncontent";
	private final static String MODIFY_RECORD_DEL_ADSOLUTIONCONTENT="modify.record.del.adsolutioncontent";
	
	@Autowired
	private ModifyRecordService modifyRecordService;
	@Autowired
	IApprovalRecordService approvalRecordService;
	@Autowired
    private IAdvertiseSolutionService adSolutionService;
	
	@Override
	public boolean isSaveModifyRecords(Long adSolutionId) {
		List<ApprovalRecord> list = approvalRecordService.findByAdSolutionId(adSolutionId, null, null);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public void saveAdSolutionModifyRecords(AdvertiseSolution solution) {
		modifyRecordService.saveModifyRecord(solution);
	}

	@Override
	public void saveAdContentModifyRecords(AdSolutionContentView contentView) {
		try {
			AdSolutionContent content = contentView.getAdSolutionContent();
			if(content!=null && content.getAdSolutionId()!=null ){
				Long oldSolutionId=adSolutionService.findById(content.getAdSolutionId()).getOldSolutionId();
                if(isSaveModifyRecords(content.getAdSolutionId()) || (oldSolutionId!=null && isSaveModifyRecords(oldSolutionId))){
					if(content.getId()==null){
						modifyRecordService.insertRecord(AdvertiseSolution.class, content.getAdSolutionId(),
								MODIFY_RECORD_ADD_ADSOLUTIONCONTENT,content.getDescription());
					}else{
						modifyRecordService.saveModifyRecord(content);
						modifyRecordService.saveModifyRecord(contentView.getAdvertiseQuotation());
					}
				}
			}
		} catch (Exception e) {
			LoggerHelper.err(this.getClass(), e.getMessage(), e);
		}
	}
	
    public List<ModifyRecord> saveAdContentModifyRecords(AdSolutionContentView newContentView,
            AdSolutionContentView oldContentView) {
        List<ModifyRecord> modifyRecords = new ArrayList<ModifyRecord>();
        try {
            modifyRecords.addAll(modifyRecordService.saveModifyRecordAndReturn(AdSolutionContent.class,
                    newContentView.getAdSolutionContent(), oldContentView.getAdSolutionContent()));
            modifyRecords.addAll(modifyRecordService.saveModifyRecordAndReturn(AdvertiseQuotation.class,
                    newContentView.getAdvertiseQuotation(), oldContentView.getAdvertiseQuotation()));
        } catch (Exception e) {
            LoggerHelper.err(this.getClass(), e.getMessage(), e);
        }
        return modifyRecords;
    }

	@Override
	public void removeAdContentModifyRecords(AdSolutionContent content) {
		try {
			if(isSaveModifyRecords(content.getAdSolutionId())){
				modifyRecordService.deleteRecord(AdvertiseSolution.class, content.getAdSolutionId(),
						MODIFY_RECORD_DEL_ADSOLUTIONCONTENT,content.getDescription());
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
