package com.baidu.gcrm.log.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.log.service.ModifyRecordConstant;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;

@Controller
@RequestMapping("/modifyReord")
public class ModifyRecordAction extends ControllerHelper {
	
	
	private static Logger log = LoggerFactory.getLogger(ModifyRecordAction.class);
	
	@Autowired
	private BillingModelServiceImpl billingModelServiceImpl;
	
	@Autowired
    IPositionService positionService;
    
    @Autowired
    AdvertisingPlatformServiceImpl adPlatformService;
    
    @Autowired
    ISiteService siteService;

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IContractService contractServie;
	
	@Autowired
	private ModifyRecordService modifyRecordService;
	
	@Autowired
	IAdSolutionContentService adSolutionContentService;
	
	@Autowired
	IUserService userService;
	
	@RequestMapping("/queryModifyReocrds/{className}/{id}")
	@ResponseBody
	public List<Map<String, Object>> queryModifyReocrds(@PathVariable("className") String className,
			@PathVariable("id") Long id){
		return modifyRecordService.findModifyRecord(className,id, getCurrentLocale());
	}
	
	@RequestMapping("/queryAdModifyReocrds/{id}")
	@ResponseBody
	public JsonBean<List<Map<String, Object>>> queryAdModifyReocrds(@PathVariable("id") Long id,
	        String role, HttpServletRequest request){
	        
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		LocaleConstants locale = getCurrentLocale();
		List<Map<String, Object>> solutionList = modifyRecordService.findModifyRecord(AdvertiseSolution.class.getSimpleName(),
				id, locale);
		convertAdvertiseSolution(solutionList,locale);
		list.addAll(solutionList);
		List<AdSolutionContentView> contents = adSolutionContentService.findByAdSolutionId(id, getCurrentLocale(), request.getContextPath());
		for(AdSolutionContentView content :contents){
			List<Map<String, Object>> contentList = modifyRecordService.findModifyRecord(AdSolutionContent.class.getSimpleName(),
					content.getAdSolutionContent().getId(), locale);
			convertAdvertiseContent(contentList,content,locale);
			list.addAll(contentList);
			if(role!=null && !role.equals(ParticipantConstants.pm.name())){
				List<Map<String, Object>> quotationList = modifyRecordService.findModifyRecord(AdvertiseQuotation.class.getSimpleName(),
						content.getAdvertiseQuotation().getId(), getCurrentLocale());
				convertAdvertiseQuotation(quotationList,content,locale);
				list.addAll(quotationList);
			}
		}
//		List<Map<String, Object>> materialList = modifyRecordService.findModifyRecord(AdvertiseMaterial.class.getSimpleName(),
//				id, getCurrentLocale());
//		list.addAll(materialList);
		return JsonBeanUtil.convertBeanToJsonBean(list);
	}
	
	@RequestMapping("/queryAdContentModifyReocrds/{id}")
	@ResponseBody
	public JsonBean<List<Map<String, Object>>> queryAdContentModifyReocrds(@PathVariable("id") Long id,
	        String role, HttpServletRequest request){
	        
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		LocaleConstants locale = getCurrentLocale();
		List<AdSolutionContentView> contents =  Arrays.asList(
					adSolutionContentService.findByAdSolutionContentId(id, locale, request.getContextPath()));
		for(AdSolutionContentView content :contents){
			List<Map<String, Object>> contentList = modifyRecordService.findModifyRecord(AdSolutionContent.class.getSimpleName(),
					content.getAdSolutionContent().getId(), locale);
			convertAdvertiseContent(contentList,content,locale);
			list.addAll(contentList);
			if(role!=null && !role.equals(ParticipantConstants.pm.name())){
				List<Map<String, Object>> quotationList = modifyRecordService.findModifyRecord(AdvertiseQuotation.class.getSimpleName(),
						content.getAdvertiseQuotation().getId(), getCurrentLocale());
				convertAdvertiseQuotation(quotationList,content,locale);
				list.addAll(quotationList);
			}
		}
//		List<Map<String, Object>> materialList = modifyRecordService.findModifyRecord(AdvertiseMaterial.class.getSimpleName(),
//				id, getCurrentLocale());
//		list.addAll(materialList);
		return JsonBeanUtil.convertBeanToJsonBean(list);
	}
	//转换广告方案编码编码类为名称
	private void convertAdvertiseSolution(List<Map<String, Object>> solutionList,LocaleConstants locale){
		if(solutionList!=null && solutionList.size()>0){
			for(Map<String, Object> solution:solutionList){
				try {
					processOperator(solution);
					Object filed = solution.get(ModifyRecordConstant.TABLEFIELD_KEY);
					if (processEmpty(filed, solution, locale)) {
						// 删除操作设置oldValue，其他操作设置newValue
						if (solution.get(ModifyRecordConstant.OPERATETYPE_KEY).equals("delete")) {
							solution.put(ModifyRecordConstant.OLDVALUE_KEY, solution.get(ModifyRecordConstant.NEWVALUE_KEY) + " " + solution.get(ModifyRecordConstant.OLDVALUE_KEY));
						} else {
							solution.put(ModifyRecordConstant.NEWVALUE_KEY, solution.get(ModifyRecordConstant.NEWVALUE_KEY) + " " + solution.get(ModifyRecordConstant.OLDVALUE_KEY));
						}
						continue;
					}
					String filedName= filed.toString();
					if("customerNumber".equals(filedName)){
						Customer customer = customerService.findByCustomerNumber(
								Long.parseLong((String)solution.get(ModifyRecordConstant.NEWVALUE_KEY)));
						solution.put(ModifyRecordConstant.NEWVALUE_KEY,customer.getCompanyName());
						customer = customerService.findByCustomerNumber(
								Long.parseLong((String)solution.get(ModifyRecordConstant.OLDVALUE_KEY)));
						solution.put(ModifyRecordConstant.OLDVALUE_KEY,customer.getCompanyName());
					}else if("contractNumber".equals(filedName)){
						Object contractNumber = solution.get(ModifyRecordConstant.NEWVALUE_KEY);
						if(contractNumber !=null){
							solution.put(ModifyRecordConstant.NEWVALUE_KEY,
									contractServie.findByNumber(contractNumber.toString()).getSummary());
						}
						contractNumber = solution.get(ModifyRecordConstant.OLDVALUE_KEY);
						if(contractNumber !=null){
							solution.put(ModifyRecordConstant.OLDVALUE_KEY,
									contractServie.findByNumber(contractNumber.toString()).getSummary());
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}	
	}
	
	private void processOperator(Map<String, Object> record){
		Object operator = record.get(ModifyRecordConstant.CREATEOPERATOR_KEY);
		if(operator != null){
			User user = userService.findByUcid(((Number)operator).longValue());
			if(user != null){
				record.put(ModifyRecordConstant.CREATEOPERATOR_KEY, user.getRealname());
			}
			
		}
	}
	
	//转换广告内容编码、ID为名称
	private void convertAdvertiseContent(List<Map<String, Object>> contentList,
			AdSolutionContentView contentView,LocaleConstants locale){
		AdSolutionContent cont = contentView.getAdSolutionContent();
		if (contentList != null && contentList.size() > 0) {
			for (Map<String, Object> content : contentList) {
				try {
					processOperator(content);
					content.put("number", cont.getNumber());
					Object filed = content.get(ModifyRecordConstant.TABLEFIELD_KEY);
					if (processEmpty(filed, content, locale)) {
						// 删除操作设置oldValue，其他操作设置newValue
						if (content.get(ModifyRecordConstant.OPERATETYPE_KEY).equals("delete")) {
							content.put(ModifyRecordConstant.OLDVALUE_KEY, content.get(ModifyRecordConstant.NEWVALUE_KEY) + " " + content.get(ModifyRecordConstant.OLDVALUE_KEY));
						} else {
							content.put(ModifyRecordConstant.NEWVALUE_KEY, content.get(ModifyRecordConstant.NEWVALUE_KEY) + " " + content.get(ModifyRecordConstant.OLDVALUE_KEY));
						}
						continue;
					}
					String filedName= filed.toString();
					if("productId".equals(filedName)){
						Long productId = Long.parseLong((String)content.get(ModifyRecordConstant.NEWVALUE_KEY));
						content.put(ModifyRecordConstant.NEWVALUE_KEY,
								adPlatformService.getByIdAndLocale(productId, locale).getI18nName());
						productId = Long.parseLong((String)content.get(ModifyRecordConstant.OLDVALUE_KEY));
						content.put(ModifyRecordConstant.OLDVALUE_KEY,
								adPlatformService.getByIdAndLocale(productId, locale).getI18nName());
					}else if("siteId".equals(filedName)){
						Long siteId = Long.parseLong((String)content.get(ModifyRecordConstant.NEWVALUE_KEY));
						content.put(ModifyRecordConstant.NEWVALUE_KEY,
								siteService.findSiteAndI18nById(siteId, locale).getI18nName());
						siteId = Long.parseLong((String)content.get(ModifyRecordConstant.OLDVALUE_KEY));
						content.put(ModifyRecordConstant.OLDVALUE_KEY,
								siteService.findSiteAndI18nById(siteId, locale).getI18nName());
					}else if("channelId".equals(filedName)
							||"areaId".equals(filedName)
						||"positionId".equals(filedName)){
						List<Long> ids = new ArrayList<Long>();
						ids.add(Long.parseLong((String)content.get(ModifyRecordConstant.NEWVALUE_KEY)));
						ids.add(Long.parseLong((String)content.get(ModifyRecordConstant.OLDVALUE_KEY)));
						List<PositionVO> pos = positionService.findI18nByPositionIds(ids , locale);
						if(pos != null && pos.size()==2){
							if(pos.get(0).getId()==
									Long.parseLong((String)content.get(ModifyRecordConstant.NEWVALUE_KEY))){
								content.put(ModifyRecordConstant.NEWVALUE_KEY, pos.get(0).getI18nName());
								content.put(ModifyRecordConstant.OLDVALUE_KEY, pos.get(1).getI18nName());
							}else{
								content.put(ModifyRecordConstant.NEWVALUE_KEY, pos.get(1).getI18nName());
								content.put(ModifyRecordConstant.OLDVALUE_KEY, pos.get(0).getI18nName());
							}
						}
					}else if("periodDescription".equals(filedName)){
						String periodDescription = (String)content.get(ModifyRecordConstant.NEWVALUE_KEY);
						if(periodDescription!=null &&periodDescription.indexOf(",")>-1){
							content.put(ModifyRecordConstant.NEWVALUE_KEY,
									periodDescription.replaceAll(",", "~"));
						}
						periodDescription = (String)content.get(ModifyRecordConstant.OLDVALUE_KEY);
						if(periodDescription!=null && periodDescription.indexOf(",")>-1){
							content.put(ModifyRecordConstant.OLDVALUE_KEY,
									periodDescription.replaceAll(",", "~"));
						}
					}else if("materialEmbedCode".equals(filedName)){
						
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	//处理报价国际化等信息
	private void convertAdvertiseQuotation(List<Map<String, Object>> quotationList,
			AdSolutionContentView contentView,LocaleConstants locale){
		if(quotationList!=null && quotationList.size()>0){
			for(Map<String, Object> quotation:quotationList){
				try {
					processOperator(quotation);
					quotation.put("number", contentView.getAdSolutionContent().getNumber());
					Object filed = quotation.get(ModifyRecordConstant.TABLEFIELD_KEY);
					if (processEmpty(filed, quotation, locale)) {
						continue;
					}
					String filedName= filed.toString();
					if("billingModelId".equals(filedName)){
						BillingModel bill = billingModelServiceImpl.getByIdAndLocale(
								quotation.get(ModifyRecordConstant.NEWVALUE_KEY).toString(),locale);
						quotation.put(ModifyRecordConstant.NEWVALUE_KEY, bill.getI18nName());
						bill = billingModelServiceImpl.getByIdAndLocale(
								quotation.get(ModifyRecordConstant.OLDVALUE_KEY).toString(),locale);
						quotation.put(ModifyRecordConstant.OLDVALUE_KEY, bill.getI18nName());
					}else if("priceType".equals(filedName)){
						if(quotation.get(ModifyRecordConstant.NEWVALUE_KEY)!=null){
							String key = ModifyRecordConstant.QUOTATION_PRICETYPE+
									quotation.get(ModifyRecordConstant.NEWVALUE_KEY);
							String price = MessageHelper.getMessage(key, locale);
							quotation.put(ModifyRecordConstant.NEWVALUE_KEY, price);
						}
						if(quotation.get(ModifyRecordConstant.OLDVALUE_KEY)!=null){
							String key = ModifyRecordConstant.QUOTATION_PRICETYPE+
									quotation.get(ModifyRecordConstant.OLDVALUE_KEY);
							String price = MessageHelper.getMessage(key, locale);
							quotation.put(ModifyRecordConstant.OLDVALUE_KEY, price);
						}
					}else if("industryType".equals(filedName)){
						if(quotation.get(ModifyRecordConstant.NEWVALUE_KEY)!=null){
							String key = ModifyRecordConstant.QUOTATION_INDUSTRYTYPE+
									quotation.get(ModifyRecordConstant.NEWVALUE_KEY);
							String industryType = MessageHelper.getMessage(key,locale);
							quotation.put(ModifyRecordConstant.NEWVALUE_KEY, industryType);
						}
						if(quotation.get(ModifyRecordConstant.OLDVALUE_KEY)!=null){
							String key = ModifyRecordConstant.QUOTATION_INDUSTRYTYPE+
									quotation.get(ModifyRecordConstant.OLDVALUE_KEY);
							String industryType = MessageHelper.getMessage(key, locale);
							quotation.put(ModifyRecordConstant.OLDVALUE_KEY, industryType);
						}
					}else if("reachEstimate".equals(filedName) &&
							LocaleConstants.zh_CN.equals(locale)){
						Object value = quotation.get(ModifyRecordConstant.NEWVALUE_KEY);
						if(value !=null && Boolean.valueOf(value.toString())){
							quotation.put(ModifyRecordConstant.NEWVALUE_KEY,"是");
						}else{
							quotation.put(ModifyRecordConstant.NEWVALUE_KEY,"否");
						}
						value = quotation.get(ModifyRecordConstant.OLDVALUE_KEY);
						if(value !=null && Boolean.valueOf(value.toString())){
							quotation.put(ModifyRecordConstant.OLDVALUE_KEY,"是");
						}else{
							quotation.put(ModifyRecordConstant.OLDVALUE_KEY,"否");
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	private boolean processEmpty(Object fieldName, Map<String, Object> record, LocaleConstants locale) {
		if (fieldName == null || "".equals(fieldName)) {
			String key = record.get(ModifyRecordConstant.NEWVALUE_KEY).toString();
			String operateInfoI18n = MessageHelper.getMessage(key, locale);
			record.put(ModifyRecordConstant.NEWVALUE_KEY, operateInfoI18n);
			return true;
		}
		return false;
	}
}
