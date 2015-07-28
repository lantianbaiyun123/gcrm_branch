package com.baidu.gcrm.ad.approval.record.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalInsertRecordJsonVO;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalRecordVO;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;

@Controller
@RequestMapping("/ad/approval")
public class ApprovalRecordAction extends ControllerHelper{
    
    @Autowired
    IApprovalRecordService approvalRecordService;
    
    private String processDefineId = GcrmConfig.getConfigValueByKey("ad.process.defineId");
    
    @RequestMapping("/save")
    @ResponseBody
    public JsonBean<ApprovalRecordVO> saveApprovalRecord(@RequestBody ApprovalRecordVO approvalRecordVO) {
        try {
        	if (!approvalRecordVO.getActivityId().equals(approvalRecordVO.getRecord().getTaskId())) {
        		LoggerHelper.err(getClass(), "activity {} and {} id not equal", approvalRecordVO.getActivityId(), approvalRecordVO.getRecord().getTaskId());
        		throw new CRMRuntimeException("activity not equal");
        	}
            generatePropertyForCreate(approvalRecordVO.getRecord());
            generatePropertysForCreate(approvalRecordVO.getInsertRecords());
        	approvalRecordService.saveAndCompleteApproval(approvalRecordVO);
        } catch(Exception e) {
        	return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.error");
        }
        return JsonBeanUtil.convertBeanToJsonBean(approvalRecordVO);
    }
    
	@RequestMapping("/queryRecord/{adSolutionId}")
    @ResponseBody
    public JsonBean<List<ApprovalRecord>> queryApprovalRecord(@PathVariable("adSolutionId") Long adSolutionId) {
        List<ApprovalRecord> list = approvalRecordService.findByAdSolutionId(adSolutionId, processDefineId, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean(list);
    }
	
	@RequestMapping("/queryContentRecord/{adContentId}")
    @ResponseBody
	public JsonBean<List<ApprovalRecord>> queryContentApprovalRecord(@PathVariable("adContentId") Long adContentId) {
        List<ApprovalRecord> list = approvalRecordService.findByContentId(adContentId, processDefineId, currentLocale);
        return JsonBeanUtil.convertBeanToJsonBean(list);
    }
    
    @RequestMapping("/queryInsertedRecord/{recordId}")
    @ResponseBody
    public JsonBean<Collection<ApprovalInsertRecordJsonVO>> queryApprovalInsertedRecord(@PathVariable("recordId") Long recordId) {
        Collection<ApprovalInsertRecordJsonVO> list = approvalRecordService.findByApprovalRecordId(recordId);
        return JsonBeanUtil.convertBeanToJsonBean(list);
    }
    
}
