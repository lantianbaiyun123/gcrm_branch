package com.baidu.gcrm.qualification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.qualification.service.IQualificationService;

@Controller
@RequestMapping("/qualification")
public class QualificationAction extends ControllerHelper {

	@Autowired
	private IQualificationService qualificationService;
	
	@Autowired
    private ICustomerService customerService;

//	@RequestMapping
//	public String index() {
//		return "customer/customer-list";
//	}
//
//	@RequestMapping("/gotoSaveCustomerQualification")
//	public String gotoSaveCustomerQualication(
//			@RequestParam(value = "customerNumber", defaultValue = "-1") Long customerNumber,
//			Model model) {
//		Qualification qualification = new Qualification();
//		qualification = qualificationService
//				.findByCustomerNumber(customerNumber);
//
//		model.addAttribute("qualification", qualification);
//		model.addAttribute("customerNumber", customerNumber);
//
//		return "customer/new-customer-qualification";
//	}
//
//	@RequestMapping("/doSaveCustomerQualication")
//	public String doSaveCustomerQualication(@Valid CustomerBean customerBean,
//			BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			Map<String, String> errors = new HashMap<String, String>();
//
//			for (FieldError fieldError : result.getFieldErrors()) {
//				String[] codes = fieldError.getCodes();
//				if (fieldError.getCode().equals("typeMismatch")) {
//					errors.put(fieldError.getField(), codes[0]);
//				} else {
//					errors.put(fieldError.getField(), fieldError.getCode());
//				}
//			}
//			model.addAttribute("errors", errors);
//			model.addAttribute("qualification",customerBean.getQualification());
//			model.addAttribute("customerNumber", customerBean.getCustomer().getCustomerNumber());
//			return "customer/new-customer-qualification";
//			//return "redirect:/qualification/gotoSaveCustomerQualification";
//		}
//		
//		generatePropertyForCreate(customerBean.getQualification());
//		saveQualification(customerBean);
//
//		MessageHelper.addSuccessAttribute(model, "创建客户资历成功!");
//		return "redirect:/qualification/gotoSaveCustomerQualification?customerNumber="
//				+ customerBean.getCustomer().getCustomerNumber();
//	}
//	
//	/**
//	 * 跳转到详情界面
//	 * 
//	 * @param cunstomerId
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/gotoQualificationDetail/{customerNumber}")
//	public String gotoQualificationDetail(@PathVariable("customerNumber")Long customerNumber,Model model){
//
//		Qualification qualification = qualificationService.findByCustomerNumber(customerNumber);
//		
//		model.addAttribute("qualification",qualification);
//		model.addAttribute("customerNumber", customerNumber);
//		if(qualification==null){
//			return "/widget/detailAgentQualificationEmpty";
//		}else{
//			return "/widget/detailAgentQualification";
//		}
//	}
//
//	@RequestMapping("/gotoEditQualification/{customerNumber}")
//	public String gotoEditQualification(@PathVariable("customerNumber")Long customerNumber,  HttpServletRequest request,Model model){
//				
//		Qualification qualification = qualificationService.findByCustomerNumber(customerNumber);
//		model.addAttribute("qualification", qualification);
//		
//		return "widget/editAgentQualification";
//	}
//	
//	@RequestMapping("doEditQualification/{customerNumber}")
//	@ResponseBody
//	public AjaxResult doEditQualification(@Valid @ModelAttribute("qualification")Qualification qualification, @PathVariable("customerNumber") Long customerNumber,
//			BindingResult result,HttpServletRequest request,Model model){
//		AjaxResult ajaxResult = new AjaxResult();
//		Map<String,String> errors = new HashMap<String, String>();
//		if(result.hasErrors()){
//			ajaxResult.setErrors(super.collectErrors(result));
//			ajaxResult.setSuccess(false);
//			return ajaxResult;
//		}
//		try {
//			Customer dbCustomer = customerService.findByCustomerNumber(customerNumber);
//			if(dbCustomer.getBusinessType()==null || dbCustomer.getCustomerType().ordinal()!=CustomerType.offline.getValue()){
//				errors.put("businesstype.error", "qualification.save.faild.businesstype.error");
//	        	ajaxResult.setErrors(errors);
//	        	ajaxResult.setSuccess(false);
//	        	return ajaxResult;
//			}
//			qualification.setCustomerNumber(customerNumber);
//			qualification.setCreateOperator((this.getUserId()));
//			generatePropertyForUpdate(qualification);
//	        qualificationService.saveQualification(qualification);
//	        
//	        if(isQualificationBlank(qualification)){
//	        	qualificationService.deleteByCustomerNumber(qualification.getCustomerNumber());
//	        }
//	        updateCustomerRelated(customerService.findByCustomerNumber(customerNumber));
//
//	        ajaxResult.setSuccess(true);
//        } catch (Exception e) {
//        	errors.put("message", "update faild");
//        	ajaxResult.setErrors(errors);
//        	ajaxResult.setSuccess(false);
//        }
//		return ajaxResult;
//	}
//	
//	private void saveQualification(CustomerBean customerBean) {
//		Qualification qualificationNew = customerBean.getQualification();
//
//		Long customerId = customerBean.getCustomer().getId();
//		Qualification qualificationOld = qualificationService
//				.findByCustomerNumber(customerBean.getCustomer().getCustomerNumber());
//
//		if (qualificationOld != null) {
//			qualificationNew.setId(qualificationOld.getId());
//		}
//		
//		customerBean.getQualification().setCustomerNumber(
//				customerBean.getCustomer().getCustomerNumber());
//
//		qualificationService.saveQualification(customerBean.getQualification());
//		updateCustomerRelated(customerService.findById(customerId));
//	}
//	
//	private void updateCustomerRelated(Customer customer) {
//		generatePropertyForUpdate(customer);
//		customerService.updateCustomer(customer);
//	}
//
//	@InitBinder("customerBean")
//	protected void initBinder(HttpServletRequest request,
//			ServletRequestDataBinder binder) throws Exception {
//
//		binder.setValidator(new CustomerQualificationValidator());
//	}
//	
//	@InitBinder("qualification")
//	protected void initBinderQualification(HttpServletRequest request,
//			ServletRequestDataBinder binder) throws Exception {
//		binder.setValidator(new QualificationValidator());
//	}
//	
//	private boolean isQualificationBlank(Qualification qualification){
//		boolean isQualificationEmpty = true; 
//		isQualificationEmpty = isQualificationEmpty && StringUtils.isEmpty(qualification.getParterTop1())
//				&& StringUtils.isEmpty(qualification.getParterTop2())
//				&& StringUtils.isEmpty(qualification.getParterTop3())
//				&& StringUtils.isEmpty(qualification.getPerformanceHighlights());
//		
//		boolean isResourceEmpty = true;
//		List<CustomerResource> resources = qualification.getCustomerResources();
//		for(CustomerResource resource : resources){
//			isResourceEmpty = isResourceEmpty && StringUtils.isEmpty(resource.getAdvertisersCompany1())
//					&& StringUtils.isEmpty(resource.getAdvertisersCompany2())
//					&& StringUtils.isEmpty(resource.getAdvertisersCompany3())
//					&& StringUtils.isEmpty(resource.getIndustry());
//			if(isResourceEmpty==false){
//				break;
//			}
//		}
//		
//		return isQualificationEmpty&&isResourceEmpty;
//	}
//	
}
