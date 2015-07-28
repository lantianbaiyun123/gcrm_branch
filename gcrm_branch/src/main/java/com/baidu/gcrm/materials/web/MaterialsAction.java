package com.baidu.gcrm.materials.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.CustomerBean;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.materials.service.MatericalsService;

/**
 * 资质认证材料添加
 * @author weichengke
 *
 */
@Controller
@RequestMapping("/materials")
public class MaterialsAction extends ControllerHelper {
	private static Logger log = LoggerFactory.getLogger(MaterialsAction.class);

	@Autowired
	private MatericalsService matericalsService;
	
	@Autowired
    private ICustomerService customerService;
	
	@Autowired
	private IFileUploadService fileUploadService;

	/**
	 * 跳转到上传界面
	 * 
	 * @param cunstomerId
	 * @param model
	 * @return
	 */
	@RequestMapping("/gotoUpload")
	public String gotoUpload(
			@RequestParam(value = "customerNumber", defaultValue = "-1") Long cunstomerId,
			Model model) {

		return "customer/new-customer-materials";
	}
	
	
	/**
	 * 文件上传
	 * 
	 * @return
	 */
	@RequestMapping("/doUploadFile")
	@ResponseBody
	public  Object doUploadFile(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) {
		Iterator<String> it = multipartRequest.getFileNames();
		MultipartFile mpf = null;
		while (it.hasNext()) {
			String fileName = it.next();
			mpf = multipartRequest.getFile(fileName);
			log.debug("===" + fileName+"upload to local sever");
		}		
		
		//设置默认值，防止属性绑定失败
		Attachment attachment = new Attachment();
		attachment.setFieldName(mpf.getName());
		attachment.setName(mpf.getOriginalFilename());
		attachment.setCustomerNumber(-1L);
		attachment.setId(-1L);
		attachment.setTempUrl("");
		//attachment.setType(-1);
		attachment.setUrl("");
	//	attachment.setExit(false);
		try {
	        attachment.setBytes(mpf.getBytes());
        } catch (IOException e) {
	        log.error("=====文件上传出错："+e.getMessage());
	        attachment.setMessage("failed");
	        return attachment;
        }
		
		if(!StringUtils.isEmpty(mpf.getOriginalFilename())){
			if(mpf.getOriginalFilename().endsWith(".exe")){
				attachment.setMessage("materials.extension.error");//设置I18N消息code
			}else{
				if(matericalsService.uploadFile(attachment)){
					attachment.setMessage("success");
				}else{
					attachment.setMessage("failed");
				} 	
			}
		}
		
		//浏览器兼容
		String userAgent = multipartRequest.getHeader("user-agent").toLowerCase();
		if (userAgent.indexOf("msie 6") != -1 || userAgent.indexOf("msie 7") != -1 || 
				userAgent.indexOf("msie 8") != -1 || userAgent.indexOf("msie 9") != -1) {
			return JSONObject.toJSONString(attachment);
			
		}
		return attachment;
	}

	/**
	 * 开发小页面用的action
	 * @param customerBean
	 * @param model
	 * @param result
	 * @return
	 */
	@RequestMapping("/doSaveMaterials")
	public String doSaveMaterials(CustomerBean customerBean, Model model,
			BindingResult result) {
		List<Attachment> attachments = customerBean.getAttachments();

		if (attachments == null ) {
			return "customer/new-customer-materials";
		}

		Long customerId = customerBean.getCustomer().getId();
		for(Attachment attachment : attachments){
			attachment.setCustomerNumber(customerBean.getCustomer().getCustomerNumber());
		}
		
//		matericalsService.saveAttachments(attachments);
		updateCustomerRelated(customerService.findById(customerId));

		return "customer/new-customer-materials";
	}


	
	/**
	 * 跳转到详情页面
	 * @param customerNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("/gotoMaterialsDetail/{customerNumber}")
	public String gotoMaterialsDetail(@PathVariable("customerNumber")Long customerNumber,Model model){

		String[] attachmentTypes = {
				"materials.type.business.license",
				"materials.type.credit.reference",
				"materials.type.advertising.cooperation.prove",
				"materials.type.customer.agent.certificate"};
		
		List<Attachment> attachments = matericalsService.findByCustomerNumber(customerNumber);
		Customer customer = customerService.findByCustomerNumber(customerNumber);
		model.addAttribute("approvalStatus",customer.getApprovalStatus());
		model.addAttribute("attachments", attachments);
		model.addAttribute("attachmentTypes", attachmentTypes);
		if(attachments!=null&&attachments.size()>0){
			return "/widget/detailMaterials";
		}else{
			return "/widget/detailMaterialsEmpty";
		}
	}

	@RequestMapping("/gotoEditMaterials/{customerNumber}")
	public String gotoEditQualification(@PathVariable("customerNumber")Long customerNumber,  HttpServletRequest request,Model model){
				
		String[] attachmentTypes = {
				"materials.type.business.license",
				"materials.type.credit.reference",
				"materials.type.advertising.cooperation.prove",
				"materials.type.customer.agent.certificate"};
		List<Attachment> attachments = matericalsService.findByCustomerNumber(customerNumber); 

		for(Attachment attachment : attachments){
		//	attachment.setExit(true);
		}
		

		model.addAttribute("attachments", attachments);
		model.addAttribute("attachmentTypes", attachmentTypes);
		
		return "widget/editMaterials";
	}
	
//	@RequestMapping("doEditMaterials/{customerNumber}")
//	@ResponseBody
//	public AjaxResult doEditMaterials(@Valid CustomerBean customerBean,@PathVariable("customerNumber") Long customerNumber){
//		List<Attachment> attachments = customerBean.getAttachments();
//		AjaxResult ajaxResult = new AjaxResult();
//		
//		try {
//			
//			if (attachments != null ) {
//				for(Attachment attachment : attachments){
//					attachment.setCustomerNumber(customerNumber);
//				}
//				boolean isChanged = matericalsService.saveAttachments(attachments);
//				if (isChanged) {
//					updateCustomerRelated(customerService.findByCustomerNumber(customerNumber));
//				}
//			}
//			
//	        ajaxResult.setSuccess(true);
//        } catch (Exception e) {
//        	ajaxResult.setSuccess(false);
//        }
//		return ajaxResult;
//	}
//	
	@RequestMapping(value = "/download/{id}/{name}")
	public void downloadFile(HttpServletResponse response,@PathVariable Long id){
		Attachment attachment = matericalsService.findById(id);
		try {
	        FileCopyUtils.copy(fileUploadService.download(attachment.getTempUrl()), response.getOutputStream());
        } catch (IOException e) {
	        log.error("=====下载文件出错"+e.getMessage());
        }
	}
	
	private void updateCustomerRelated(Customer customer) {
		generatePropertyForUpdate(customer);
		customerService.updateCustomer(customer);
	}
	
}
