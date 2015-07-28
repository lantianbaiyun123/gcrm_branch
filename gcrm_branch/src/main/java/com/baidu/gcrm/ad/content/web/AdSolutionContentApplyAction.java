package com.baidu.gcrm.ad.content.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.content.web.validator.AdSolutionContentApplyValidator;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentApplyVo;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.web.utils.AutoSuggestBean;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;

/**
 * 
 * @author yangjianguo
 * 提前上线申请相关mapping
 */
@Controller
@RequestMapping("/adcontent/applyOnline")
public class AdSolutionContentApplyAction extends ControllerHelper {
	@Autowired
	private IAdSolutionContentApplyService adSolutionContentApplyService;
	@Autowired
	private IFileUploadService uploadService;
	
	
	 @RequestMapping("/submit")
	 @ResponseBody
	 public JsonBean<AdSolutionContentApply> saveAdContentApply(@RequestBody AdSolutionContentApplyVo adContentApplyVo,BindingResult result){
		 if(adContentApplyVo.getAdSolutionContentApply()!=null){ 
		  ValidationUtils.invokeValidator(new AdSolutionContentApplyValidator(),adContentApplyVo.getAdSolutionContentApply(),result);
		  if(result.hasFieldErrors()){
			 return JsonBeanUtil.convertBeanToJsonBean(adContentApplyVo.getAdSolutionContentApply(), super.collectErrors(result));
		  }
		 }
        try{
        	AdSolutionContentApply apply=adSolutionContentApplyService.saveAdContentApply(adContentApplyVo);
        	return JsonBeanUtil.convertBeanToJsonBean(apply);
        }catch (Exception e) {
	            LoggerHelper.err(getClass(), "提交上线申请单出现异常", e);
	            return JsonBeanUtil.convertBeanToErrorJsonBean(null, e.getMessage());
	        }
	 }
	  
	    @RequestMapping("/approve")
	    @ResponseBody
	    public JsonBean<String> approve(@RequestBody AdContentApplyApprovalRecord adContentApplyApprovalRecord) {
	        try {
	        	adSolutionContentApplyService.processApproveRequest(adContentApplyApprovalRecord, RequestThreadLocal.getLoginUser(), currentLocale);
	        } catch (Exception e) {
	            LoggerHelper.err(getClass(), "提前上线审批出现异常", e);
	            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
	        }
	        return JsonBeanUtil.convertBeanToJsonBean("Success");
	    }
	    
	    @RequestMapping("/viewOnlineApply/{applyId}")
		@ResponseBody
		public JsonBean<AdSolutionContentApplyVo> viewApplyDetail(@PathVariable("applyId") Long applyId){
		
	    	AdSolutionContentApplyVo adSolutionContentApplyVo=adSolutionContentApplyService.findAdSolutionContentApplDetial(applyId, currentLocale);
			
			return JsonBeanUtil.convertBeanToJsonBean(adSolutionContentApplyVo);
		}
		
	    
		@RequestMapping("/contractSuggest")
		@ResponseBody
		public JsonBean<List<AutoSuggestBean<Contract>>> contractSuggest(@RequestParam("customerId") Long customerId){
		
			List<AutoSuggestBean<Contract>> suggests = new ArrayList<AutoSuggestBean<Contract>>();
			if(customerId == null){
				return JsonBeanUtil.convertBeanToJsonBean(null, "advertise.solution.baseinfo.customer.number.null");
			}
			List<Contract> contracts = adSolutionContentApplyService.findImmeContractsFromCMS(customerId);
			for(Contract contract : contracts){
				suggests.add(new AutoSuggestBean<Contract>(contract.getNumber(),contract));
			}
			return JsonBeanUtil.convertBeanToJsonBean(suggests);
		}
		
		@RequestMapping("/doUploadFile")
	    @ResponseBody
	    public void doUploadFile(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {

	        PrintWriter writer = null;
	        response.setContentType("text/html; charset=UTF-8");

	        Iterator<String> it = multipartRequest.getFileNames();
	        MultipartFile mpf = null;
	        while (it.hasNext()) {
	            String fileName = it.next();
	            mpf = multipartRequest.getFile(fileName);
	            // log.debug("===" + fileName+"upload to local sever");
	        }

	        // 设置默认值，防止属性绑定失败
	        AttachmentModel attachment = new AttachmentModel();
	        attachment.setFieldName(mpf.getName());
	        attachment.setName(mpf.getOriginalFilename());
	        attachment.setTransactionRecordId(-1L);
	        attachment.setTempUrl("");
	        attachment.setUrl("");
	        attachment.setModuleName(ModuleNameWithAtta.adcontentonlineapply);
	        try {
	            writer = response.getWriter();
	            attachment.setBytes(mpf.getBytes());
	        } catch (IOException e) {
	            attachment.setMessage("failed");
	            writer.write(JSON.toJSONString(JsonBeanUtil.convertBeanToErrorJsonBean(attachment, e.getMessage())));
	            return;
	        }

	        if (!StringUtils.isEmpty(mpf.getOriginalFilename())) {
	            if (mpf.getOriginalFilename().endsWith(".exe")) {
	                attachment.setMessage("fileupload.extension.error");// 设置I18N消息code
	            } else {
	                if (uploadService.uploadFile(attachment)) {
	                    attachment.setMessage("success");
	                } else {
	                    attachment.setMessage("failed");
	                }
	            }
	        }
	        attachment.setBytes(null);

	        writer.write(JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(attachment)));
	    }
	    @RequestMapping("/reminders/{applyId}")
	    @ResponseBody
	    public JsonBean<String> remindApplier(@PathVariable("applyId") Long applyId) {
	    	try{
	    	adSolutionContentApplyService.remindersContentByMail(applyId, currentLocale);
	        return JsonBeanUtil.convertBeanToJsonBean("success");
	    	}catch(Exception e){
	    		 return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
	    	}

	    }
	    
	    @RequestMapping("/withdrawnOnlineApply/{applyId}")
	    @ResponseBody
	    public JsonBean<String> withdrawnOnlineApply(@PathVariable("applyId") Long applyId) {
	        if (StringUtils.isEmpty(String.valueOf(applyId))) {
	        	 return JsonBeanUtil.convertBeanToErrorJsonBean("error", "the onlineApplyId can not be null");
	        }
	        try {
	             adSolutionContentApplyService.withdrawOnlineApplyById(applyId,  currentLocale);
	        } catch (Exception e) {
	            LoggerHelper.err(this.getClass(), e.getMessage(), e);
	            return JsonBeanUtil.convertBeanToErrorJsonBean("error", e.getMessage());
	        }
	        return JsonBeanUtil.convertBeanToJsonBean("success");
	    }
	    @RequestMapping("/findApproveRecords/{applyId}")
	    @ResponseBody
	    public JsonBean<List<AdContentApplyApprovalRecord>> findOnlineApplyApproveRecords(@PathVariable("applyId") Long applyId) {
	       
	        try {
	             List<AdContentApplyApprovalRecord> records=adSolutionContentApplyService.findApprovalRecordsByOnlineApplyId(applyId,  currentLocale);
	             return JsonBeanUtil.convertBeanToJsonBean(records);
	        } catch (Exception e) {
	            LoggerHelper.err(this.getClass(), e.getMessage(), e);
	            return JsonBeanUtil.convertBeanToErrorJsonBean(null, e.getMessage());
	        }
	    }
}