package com.baidu.gcrm.quote.approval.record.web;


import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.quote.approval.record.service.IQtApprovalRecordService;
import com.baidu.gcrm.quote.approval.record.web.vo.QtprovalRecordVO;
import com.baidu.gcrm.quote.dao.QuotationMainRepository;
import com.baidu.gcrm.quote.service.QuotationMainService;

@Controller
@RequestMapping("quote/approval")
public class QtApprovalRecordAction extends ControllerHelper{
    
    @Autowired
    IQtApprovalRecordService qtApprovalRecordService;
    @Autowired
    QuotationMainRepository quotationMainRepository;
    @Autowired
    QuotationMainService quotationMainService;
    private String processDefineId = GcrmConfig.getConfigValueByKey("quote.process.defineId");
    
    @RequestMapping("/saveApprovalRecord")
    @ResponseBody
    public JsonBean<QtprovalRecordVO> saveApprovalRecord(@RequestBody QtprovalRecordVO qtprovalRecordVO) {
        try {
            generatePropertyForCreate(qtprovalRecordVO.getRecord());
            qtApprovalRecordService.saveAndCompleteApproval(qtprovalRecordVO,getUserId(),currentLocale);
            
        } catch(Exception e) {
        	return JsonBeanUtil.convertBeanToJsonBean(null, "activity.complete.error");
        }
        if(qtprovalRecordVO.getFinish()==1){
        	
        	try {
				qtApprovalRecordService.findMail(qtprovalRecordVO.getRecord().getQuoteMainId(), LocaleConstants.zh_CN);
			} catch (IOException e) {
			}
        }
        return JsonBeanUtil.convertBeanToJsonBean(qtprovalRecordVO);
    }
        
	
}
