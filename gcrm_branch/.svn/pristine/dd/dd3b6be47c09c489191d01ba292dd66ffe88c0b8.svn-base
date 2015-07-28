package com.baidu.gcrm.quote.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.rights.service.impl.UserRightsServiceImpl;
import com.baidu.gcrm.ad.content.web.helper.BaseI18nModelComparator;
import com.baidu.gcrm.ad.web.utils.AutoSuggestBean;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.datarole.IGCrmDataRoleService;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.customer.web.helper.AjaxResult;
import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;
import com.baidu.gcrm.quote.approval.record.service.IQtApprovalRecordService;
import com.baidu.gcrm.quote.dao.QuotationCondition;
import com.baidu.gcrm.quote.material.model.QuotationMaterial;
import com.baidu.gcrm.quote.material.model.QuotationMaterialVO;
import com.baidu.gcrm.quote.material.service.QuotationMaterialService;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationBusinessType;
import com.baidu.gcrm.quote.model.QuotationIndustryType;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.service.IQuotationModifyRecordService;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.quote.service.QuotationService;
import com.baidu.gcrm.quote.web.utils.AdvertisingPlatformComparator;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;
import com.baidu.gcrm.quote.web.validator.QuotationMainViewValidator;
import com.baidu.gcrm.quote.web.validator.QuotationValidator;
import com.baidu.gcrm.quote.web.vo.PriceTypeCps;
import com.baidu.gcrm.quote.web.vo.PriceTypeOther;
import com.baidu.gcrm.quote.web.vo.QuotationAddVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainAddVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainConflict;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainView;
import com.baidu.gcrm.quote.web.vo.QuotationView;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CodeCacheServiceImpl;
@Controller
@RequestMapping("quote")
public class QuotationAction extends ControllerHelper{
	private static Logger log = LoggerFactory.getLogger(QuotationAction.class);
	private String processDefineId = GcrmConfig.getConfigValueByKey("quote.process.defineId");

	@Autowired
	private QuotationService quoteService;
	@Autowired
	private QuotationMainService quotationMainService;
	
	@Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
	
	@Autowired
	private CodeCacheServiceImpl codeServiceImpl;
    
	@Autowired
	private AbstractValuelistCacheService<AgentRegional> agentRegionalService;
    
	@Autowired
	private ISiteService siteService;
	
	@Resource(name="advertisingPlatformServiceImpl")
	private AbstractValuelistCacheService<AdvertisingPlatform> advertisingPlatformService;
	
	@Resource(name="billingModelServiceImpl")
	private AbstractValuelistCacheService<BillingModel> billingModelService;
	
	@Autowired
	private IFileUploadService fileUploadService;
	
	@Autowired
	private QuotationMaterialService quotationMaterialService;
	
	@Autowired
	private IUserDataRightService userDataRightService;
	
	@Autowired
	private IUserService userService;
	@Autowired 
	IQtApprovalRecordService qtApprovalRecordService;
	
	@Autowired
	private IQuotationModifyRecordService quotationModifyRecordService;
	
	@Autowired
	private IGCrmDataRoleService gcrmDataRoleService;
	@Autowired
    IUserRightsService userRightsService;
	@RequestMapping("preQuery")
	public ModelAndView list(){
		ModelAndView model = new ModelAndView("/quote/list");
		init(model);
		
		return model;
	}
	
	@RequestMapping("query")
	public ModelAndView query(@ModelAttribute("quoteCondition") QuotationCondition condition, 
			@RequestParam(defaultValue="1")Integer number, @RequestParam(defaultValue="5")Integer size){
		ModelAndView model = new ModelAndView("/quote/listTable");
		init(model);
		
		Page<Quotation> page = quoteService.findByCondition(number-1, size,condition);
		for(Quotation quote : page.getContent()){
			prepareView(quote);
		}
		model.addObject("page", page);
		return model;
	}
	
	@RequestMapping("preSave")
	public ModelAndView preSave(){
		ModelAndView model = new ModelAndView("/quote/new");
		init(model);
		return model;
	}

	@RequestMapping("submit")
	public ModelAndView submit(@Valid @ModelAttribute("quote") Quotation quote){
		ModelAndView model = new ModelAndView("/quote/new");
		if(quote == null){
			return model;
		}
		init(model);
		try{
			List<Quotation> dbQuote = quoteService.findByAdvertisingPlatformIdAndSiteIdAndBillingModelId(
					quote.getAdvertisingPlatformId(), quote.getSiteId(), quote.getBillingModelId());
			if(dbQuote != null && dbQuote.size() != 0){
				model.addObject("message", "quote.exist");
			}else{
				model.addObject("message", "quote.save.success");
				prepSave(quote);
				quoteService.addQuote(quote);
			}
		}catch(Exception e){
			log.info("=====新增报价信息失败"+e.getMessage());
			model.addObject("message", "quote.save.faild");
		}
		
		
		return model;
	}
	
	@RequestMapping("preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Long id){
		ModelAndView model = new ModelAndView("/quote/update");
		Quotation quote = quoteService.findById(id);
		prepareView(quote);
		
		model.addObject("quote", quote);
		return model;
	}

	@RequestMapping("update")
	public ModelAndView update(@Valid @ModelAttribute("quote") Quotation quote){
		ModelAndView model = null;
		if(quote == null || quote.getId() == null){
			return model;
		}
		model = new ModelAndView("/quote/update");
		prepSave(quote);
		
		try{
			List<Quotation> dbQuote = quoteService.findByAdvertisingPlatformIdAndSiteIdAndBillingModelId(
					quote.getAdvertisingPlatformId(), quote.getSiteId(), quote.getBillingModelId());
			if(dbQuote != null && dbQuote.size() !=0 ){
				if(dbQuote.get(0).getId().longValue() != quote.getId().longValue()){
					model.addObject("message", "quote.save.faild");
					log.info("=====更新报价信息失败：id和后台不匹配:"+dbQuote.get(0).getId()+"==="+quote.getId());
					prepareView(quote);	
					return model;
				}
			}
			
			quoteService.updateQuote(quote);
			model.addObject("message", "quote.save.success");
		}catch(Exception e){
			log.info("=====更新报价信息失败"+e.getMessage());
			model.addObject("message", "quote.save.faild");
		}
		prepareView(quote);	
		return model;
	}

	private void prepSave(Quotation quote) {
		if(quote.getRatioMine() != null){
			quote.setRatioMine(quote.getRatioMine()/100);
		}
		if(quote.getRatioThird() != null){
			quote.setRatioThird(quote.getRatioThird()/100);
		}
	}
	
	@RequestMapping("deleteById/{id}")
	public ModelAndView deleteById(@PathVariable("id") Long id){
		ModelAndView model = new ModelAndView("redirect:/quote/preQuery");
		
		if(id != null){
			quoteService.delete(id);
		}
		
		return model;
	}
	
	@RequestMapping("querySites/{adPlatFormId}")
	@ResponseBody
	public AjaxResult querySites(@PathVariable("adPlatFormId")Long adPlatFormId){
		LocaleConstants currentLocale = LocaleConstants.zh_CN;
		AjaxResult result = new AjaxResult();
		
		try{
			List<Site> siteList = siteService.findSiteByAdPlatform(adPlatFormId,currentLocale);
			result.setRetBean(siteList);
			result.setSuccess(true);
		}catch(Exception e){
			Map<String,String> errors = new HashMap<String, String>();
			errors.put("querySites.faild","quote.querySites.faild");
			result.setSuccess(false);
		}
		
		return result;	
	}
	
	private void init(ModelAndView model) {
	    List<AdvertisingPlatform> advertisingPlatforms = advertisingPlatformService.getAll();
		List<BillingModel> billingModels = billingModelService.getAll();
		model.addObject("advertisingPlatforms", advertisingPlatforms);
		model.addObject("billingModels", billingModels);
    }
	
	/**
	 * 加载id对应的数据
	 * @param quote
	 */
	private void prepareView(Quotation quote) {
		
		if(quote.getAdvertisingPlatformId() != null){
			AdvertisingPlatform advertisingPlatform = advertisingPlatformService.getById(String.valueOf(quote.getAdvertisingPlatformId()));
			if(advertisingPlatform != null){
				quote.setAdvertisingPlatformName(advertisingPlatform.getName());
			}
		}
		
		if(quote.getRatioMine() != null){
			BigDecimal b = new BigDecimal(quote.getRatioMine()*100); 
			quote.setRatioMine(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		if(quote.getRatioThird() != null){
			BigDecimal b = new BigDecimal(quote.getRatioThird()*100); 
			quote.setRatioThird(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		
		Site site = siteService.findSiteAndI18nById(quote.getSiteId(), LocaleConstants.zh_CN);
		if(site != null){
			quote.setSiteName(site.getI18nName());
		}

		if(quote.getBillingModelId() != null){
			BillingModel billingModel = billingModelService.getById(String.valueOf(quote.getBillingModelId()));
			if(billingModel != null){
				quote.setBillingModelName(billingModel.getName());
				quote.setBillingModelType(billingModel.getType());
			}
		}
		
    }
	
	@InitBinder("quote")
	protected void saveQuote(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(new QuotationValidator());
	}
	/**
	* 功能描述：   提交标杆价申请
	* 创建人：yudajun    
	* 创建时间：2014-4-14 上午8:51:48   
	* 修改人：yudajun
	* 修改时间：2014-4-14 上午8:51:48   
	* 修改备注：   
	* 参数： @param quotationMainView
	* 参数： @param bindingResult
	* 参数： @return
	* @version
	 */
	@RequestMapping("/submitQuotation")
	@ResponseBody
	public JsonBean<List<String>> submitQuotation(@RequestBody QuotationMainAddVO quotationAdd,BindingResult bindingResult){
		QuotationMainView quotationMainView = processQuotationAdd(quotationAdd);
		List<String> result = new ArrayList<String>();
		//记录当前操作人
		quotationMainView.setUpdateOperator(this.getUserId());
		quotationMainView.setOperatorName(getUserName());
		ValidationUtils.invokeValidator(new QuotationMainViewValidator(), 
				quotationMainView, bindingResult);
        if (bindingResult.hasErrors()) {
        	return JsonBeanUtil.convertBeanToJsonBean(result,
	                super.collectErrors(bindingResult), JsonBeanUtil.CODE_ERROR);
        }
        try {
            result = quotationMainService.submitQuotation(quotationMainView);
        } catch (Exception e) {
        	log.error("提交标杆价异常" + e.getMessage());
            String message = MessageHelper.getMessage("activity.complete.error", this.currentLocale);
            return JsonBeanUtil.convertBeanToJsonBean(null, message);
        }
    	return JsonBeanUtil.convertBeanToJsonBean(result);
	}
	/**
	* 功能描述：   处理前端传入的数据，生成N个标杆价
	* 创建人：yudajun    
	* 创建时间：2014-4-15 下午7:49:23   
	* 修改人：yudajun
	* 修改时间：2014-4-15 下午7:49:23   
	* 修改备注：   
	* 参数： @param quotationAdd
	* 参数： @return
	* @version
	 */
	private QuotationMainView processQuotationAdd(QuotationMainAddVO quotationAdd){
		QuotationMainView quotationMainView = new QuotationMainView();
		QuotationMain main = quotationAdd.getQuotationMain();
		List<BillingModel> billmodelList = billingModelService.getAll();
		Map<String,Long> billModeMap = new HashMap<String, Long>();
		for(BillingModel model : billmodelList){
			billModeMap.put(model.getName().toLowerCase(), model.getId());
		}
		if(main == null ){
			return quotationMainView;
		}
		quotationMainView.setQuotationMain(main);
		List<QuotationView> quotationViewList = new ArrayList<QuotationView>();
		List<QuotationAddVO> addList = quotationAdd.getQuotationList();
		for(QuotationAddVO vo:addList){
			if(vo.getSiteId() != null){
				QuotationView view  = new QuotationView();
				view.setSiteId(vo.getSiteId());
				view.setQuotationMaterialList(vo.getQuotationMaterialList());
				
				List<Quotation> quotationList = new ArrayList<Quotation>();
				
				if(PriceType.ratio.equals(main.getPriceType())){
					if(StringUtils.isNotBlank(vo.getRatioMine()) 
							&& Double.valueOf(vo.getRatioMine()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						
						BigDecimal value = BigDecimal.valueOf(Double.valueOf(vo.getRatioMine())).divide(BigDecimal.valueOf(100));
						DecimalFormat df2 = new DecimalFormat("#.0000");
						quotation.setRatioMine(Double.valueOf(df2.format(value.doubleValue())));
						
						value = BigDecimal.valueOf(Double.valueOf(vo.getRatioCustomer())).divide(BigDecimal.valueOf(100));
						quotation.setRatioCustomer(Double.valueOf(df2.format(value.doubleValue())));
						if(StringUtils.isNotBlank(vo.getRatioThird())){
							value = BigDecimal.valueOf(Double.valueOf(vo.getRatioThird())).divide(BigDecimal.valueOf(100));
							quotation.setRatioThird(Double.valueOf(df2.format(value.doubleValue())));
						}
						quotation.setId(vo.getId());
						quotationList.add(quotation);
					}
				}
				
				if(PriceType.rebate.equals(main.getPriceType())){
					if(StringUtils.isNotBlank(vo.getRatioRebate())
							&& Double.valueOf(vo.getRatioRebate()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						
						BigDecimal value = BigDecimal.valueOf(Double.valueOf(vo.getRatioRebate())).divide(BigDecimal.valueOf(100));
						DecimalFormat df2 = new DecimalFormat("#.0000");
						quotation.setRatioRebate(Double.valueOf(df2.format(value.doubleValue())));
						quotation.setId(vo.getId());
						quotationList.add(quotation);
					}
				}
				if(PriceType.unit.equals(main.getPriceType())){
					if(vo.getCpc() != null && StringUtils.isNotBlank(vo.getCpc().getValue())
							&& Double.valueOf(vo.getCpc().getValue()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setBillingModelId(billModeMap.get("cpc"));
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						quotation.setPublishPrice(Double.valueOf(vo.getCpc().getValue()));
						quotation.setId(vo.getCpc().getId());
						quotationList.add(quotation);
					}
					if(vo.getCpa() != null && StringUtils.isNotBlank(vo.getCpa().getValue())
							&& Double.valueOf(vo.getCpa().getValue()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setBillingModelId(billModeMap.get("cpa"));
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						quotation.setPublishPrice(Double.valueOf(vo.getCpa().getValue()));
						quotation.setId(vo.getCpa().getId());
						quotationList.add(quotation);
					}
					
					if(vo.getCpt() != null && StringUtils.isNotBlank(vo.getCpt().getValue())
							&& Double.valueOf(vo.getCpt().getValue()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setBillingModelId(billModeMap.get("cpt"));
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						quotation.setPublishPrice(Double.valueOf(vo.getCpt().getValue()));
						quotation.setId(vo.getCpt().getId());
						quotationList.add(quotation);
					}
					
					if(vo.getCpm() != null && StringUtils.isNotBlank(vo.getCpm().getValue())
							&& Double.valueOf(vo.getCpm().getValue()).doubleValue() > 0){
						Quotation quotation = new Quotation();
						quotation.setPriceType(main.getPriceType());
						quotation.setBillingModelId(billModeMap.get("cpm"));
						quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
						quotation.setPublishPrice(Double.valueOf(vo.getCpm().getValue()));
						quotation.setId(vo.getCpm().getId());
						quotationList.add(quotation);
					}
					
					List<PriceTypeCps> cpsList = vo.getCps();
					if(cpsList != null && cpsList.size() > 0){
						for (PriceTypeCps cps : cpsList) {
							if (cps != null && StringUtils.isNotBlank(cps.getValue())
									&& Double.valueOf(cps.getValue()).doubleValue() > 0) {
								Quotation quotation = new Quotation();
								quotation.setPriceType(main.getPriceType());
								quotation.setBillingModelId(billModeMap.get("cps"));
								if(cps.getIndustryId() != null){
									quotation.setIndustryId(cps.getIndustryId().intValue());
								}
								quotation.setAdvertisingPlatformId(main.getAdvertisingPlatformId());
								BigDecimal cpsValue = BigDecimal.valueOf(Double.valueOf(cps.getValue())).divide(BigDecimal.valueOf(100));
								DecimalFormat df2 = new DecimalFormat("#.0000");
								quotation.setRatioMine(Double.valueOf(df2.format(cpsValue.doubleValue())));
								quotation.setId(cps.getId());
								quotationList.add(quotation);
							}
						}
					}
				}
				view.setQuotationList(quotationList);
				quotationViewList.add(view);
			}
		}
		quotationMainView.setQuotationViewList(quotationViewList);
		return quotationMainView;
	}
	
	
	/**
	* 功能描述：   检验是否有冲突
	* 创建人：yudajun    
	* 创建时间：2014-4-14 上午8:52:05   
	* 修改人：yudajun
	* 修改时间：2014-4-14 上午8:52:05   
	* 修改备注：   
	* 参数： @param quotationMainView
	* 参数： @param bindingResult
	* 参数： @return
	* @version
	 */
	@RequestMapping("/checkConflict")
	@ResponseBody
    public JsonBean<List<QuotationMainConflict>> checkConflict(@RequestBody QuotationMainAddVO quotationAdd,BindingResult bindingResult){
		QuotationMainView quotationMainView = processQuotationAdd(quotationAdd);
		
		List<QuotationMainConflict> list = quotationMainService
				.checkConflict(quotationMainView, currentLocale);
		return JsonBeanUtil.convertBeanToJsonBean(list);
	}
	
	/**
	* 功能描述：   查询定价的行业类型
	* 创建人：yudajun    
	* 创建时间：2014-4-15 下午8:10:57   
	* 修改人：yudajun
	* 修改时间：2014-4-15 下午8:10:57   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findIndustryType")
	@ResponseBody
	public JsonBean<List<QuotationIndustryType>> findIndustryType(){
		List<Code> all = codeServiceImpl.getAllByCodeTypeAndLoacleAndCodeParent("quotationMain.industry",this.currentLocale.name(),null);
		List<QuotationIndustryType> list = new ArrayList<QuotationIndustryType>();
		list = processIndustry(all);
		return JsonBeanUtil.convertBeanToJsonBean(list);
	}
	
	private List<QuotationIndustryType> processIndustry(List<Code> all){
		List<QuotationIndustryType> list = new ArrayList<QuotationIndustryType>();
		if(CollectionUtils.isNotEmpty(all)){
			for (Code code : all) {
				QuotationIndustryType type = new QuotationIndustryType();
				type.setId(Long.valueOf(code.getCodeValue()));
				type.setIndustryTypeName(code.getI18nName());
				
				List<Code> subList = codeServiceImpl.getAllByCodeTypeAndLoacleAndCodeParent("quotationMain.industry",this.currentLocale.name(),code.getCodeValue());
				if(CollectionUtils.isNotEmpty(subList)){
					type.setSubIndustryType(processIndustry(subList));
				}
				
				list.add(type);
			}
		}
		return list;
	}
	/**
	* 功能描述：   查询业务类型
	* 创建人：yudajun    
	* 创建时间：2014-6-12 下午5:55:14   
	* 修改人：yudajun
	* 修改时间：2014-6-12 下午5:55:14   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findBusinessType")
	@ResponseBody
	public JsonBean<List<BusinessType>> findBusinessType(){
		List<BusinessType> list = new ArrayList<BusinessType>();
		if(getUserId() != null){
			List<Integer> rightList = userDataRightService.getBusinessTypeByUcId(this.getUserId());
			if(CollectionUtils.isNotEmpty(rightList)){
				for(Integer id : rightList){
					if(id != null){
						list.add(BusinessType.valueOf(id));
					}
				}
			}
		}
		return JsonBeanUtil.convertBeanToJsonBean(list);
	}
	
	
	/**
	* 功能描述：   标杆价列表
	* 创建人：yudajun    
	* 创建时间：2014-4-10 下午5:04:18   
	* 修改人：yudajun
	* 修改时间：2014-4-10 下午5:04:18   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findQuotationMainPage")
	@ResponseBody
	public JsonBean<com.baidu.gcrm.common.page.Page<QuotationMainVO>> findQuotationMainPage(@RequestBody QuotationMainCondition condition){
		//加数据权限控制
		List<Long> ucidList = gcrmDataRoleService.findFeasiblityUserIdList(getUserId());
		condition.setOperatorIdList(ucidList);
		
		if(StringUtils.isBlank(condition.getAdvertisingPlatformId())){
			condition.setPlatIdList(findPlatformIdList(getUserId()));
		}
		
		if(StringUtils.isBlank(condition.getSiteId())){
			condition.setAgentIdList(findRegionalIdList(getUserId()));
			condition.setSiteIdList(findSiteIdList(getUserId()));
		}
		
		com.baidu.gcrm.common.page.Page<QuotationMainVO> page = 
		quotationMainService.findQuotationMainPage(condition, currentLocale);
    	return JsonBeanUtil.convertPageBeanToJsonBean(page);
	}
	
	private List<Long> findPlatformIdList(Long ucid){
		List<Long> platformIdList = new ArrayList<Long>();
		List<AdvertisingPlatform> platformList = userDataRightService.getPlatformListByUcId(ucid);
		if(CollectionUtils.isNotEmpty(platformList)){
			for(AdvertisingPlatform plat : platformList){
				platformIdList.add(plat.getId());
			}
		}
		return platformIdList;
	}
	
	private List<Long> findSiteIdList(Long ucid){
		List<Long> siteIdList = new ArrayList<Long>();
		List<Site> siteList = userDataRightService.getSiteListByUcId(ucid,currentLocale);
		if(CollectionUtils.isNotEmpty(siteList)){
			for(Site site : siteList){
				siteIdList.add(site.getId());
			}
		}
		return siteIdList;
	}
	
	private List<Long> findRegionalIdList(Long ucid){
		List<Long> regionalIdList = new ArrayList<Long>();
		List<AgentRegional> agentList = agentRegionalService.getAllByLocale(currentLocale);
		if(CollectionUtils.isNotEmpty(agentList)){
			for(AgentRegional obj : agentList){
				regionalIdList.add(obj.getId());
			}
		}
		return regionalIdList;
	}
	
	/**
	* 功能描述：   根据平台id查询站点或者区域
	* 创建人：yudajun    
	* 创建时间：2014-4-14 上午8:51:13   
	* 修改人：yudajun
	* 修改时间：2014-4-14 上午8:51:13   
	* 修改备注：   
	* 参数： @param platformId
	* 参数： @return
	* @version
	 */
	@RequestMapping("/siteSuggest")
	@ResponseBody
	public JsonBean<List<AutoSuggestBean<Long>>> siteSuggest(@RequestParam("platformId")String platformId){
		List<AutoSuggestBean<Long>> suggests = new ArrayList<AutoSuggestBean<Long>>();
		if(StringUtils.isBlank(platformId)){
			return JsonBeanUtil.convertBeanToJsonBean(suggests);
		}
		AdvertisingPlatform platform = adPlatformService.getById(platformId);
		if(BusinessType.CASH.ordinal() == platform.getBusinessType()){//变现
			List<Long> siteIdList = findSiteIdList(this.getUserId());
			List<Site> siteList = siteService.findSiteByAdPlatform(Long.valueOf(platformId),currentLocale);
			for(Site site:siteList){
				if(siteIdList.contains(site.getId())){
					suggests.add(new AutoSuggestBean<Long>(site.getI18nName(), site.getId()));
				}
			}
		}else{//销售
			List<AgentRegional> agentList = agentRegionalService.getAllByLocale(currentLocale);
			for(AgentRegional agent:agentList){
				suggests.add(new AutoSuggestBean<Long>(agent.getI18nName(), agent.getId()));
			}
		}
		return JsonBeanUtil.convertBeanToJsonBean(suggests);
	}
	
	/**
	* 功能描述：   标杆价详情
	* 创建人：yudajun    
	* 创建时间：2014-4-14 上午8:50:56   
	* 修改人：yudajun
	* 修改时间：2014-4-14 上午8:50:56   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findQuotationVOById")
	@ResponseBody 
	public JsonBean<QuotationMainView> findQuotationVOById(@RequestParam("quotationMainId") String quotationMainId,HttpServletRequest request){
		QuotationMainView view = new QuotationMainView();
		if(StringUtils.isNotBlank(quotationMainId)){
			view = quotationMainService.findQuotationVOById(Long.valueOf(quotationMainId), currentLocale,request);
		}
		if(view == null || StringUtils.isBlank(quotationMainId)){
			String message = MessageHelper.getMessage("quotaition.not.found", currentLocale);
			return JsonBeanUtil.convertBeanToJsonBean(null, message);
		}
		return JsonBeanUtil.convertBeanToJsonBean(view);
	}
	/**
	* 功能描述：   上传举证材料
	* 创建人：yudajun    
	* 创建时间：2014-4-14 上午9:00:20   
	* 修改人：yudajun
	* 修改时间：2014-4-14 上午9:00:20   
	* 修改备注：   
	* 参数： @param quotationMaterial
	* 参数： @param resp
	* @version
	 */
	@RequestMapping("/uploadQuotationMaterial")
    @ResponseBody
    public void uploadQuotationMaterial(QuotationMaterial quotationMaterial, HttpServletResponse resp) {
	    String jsonStr = null;
	    PrintWriter writer = null;
	    resp.setContentType("text/html; charset=UTF-8");
	    try {
	        writer = resp.getWriter();
            MultipartFile file = quotationMaterial.getMaterialFile();
            if (file != null && !file.isEmpty()) {
                String uploadFileName = new StringBuilder().append("/").append(UUID.randomUUID().toString()).toString();
                String fileUrl = fileUploadService.upload(uploadFileName, file.getBytes());
                QuotationMaterialVO vo = new QuotationMaterialVO();
                vo.setFileUrl(fileUrl);
                vo.setUploadFileName(uploadFileName);
                vo.setDownloadFileName(file.getOriginalFilename());
                jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(vo));
            }
            
        } catch (IOException e) {
            jsonStr = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null, "upload fail"));
        }
	    if (writer != null) {
	        writer.println(jsonStr);
	    }
	    
    }
    /**
    * 功能描述：   删除举证材料
    * 创建人：yudajun    
    * 创建时间：2014-4-14 上午9:00:31   
    * 修改人：yudajun
    * 修改时间：2014-4-14 上午9:00:31   
    * 修改备注：   
    * 参数： @param materialId
    * 参数： @return
    * @version
     */
    @RequestMapping("/deleteMaterial/{materialId}")
    @ResponseBody
	public JsonBean<QuotationMaterial> deleteQuotationMaterial(
			@PathVariable("materialId") Long materialId) {
    	QuotationMaterial existsQuotationMaterial = null;
		try {
			if (materialId != null) {
				    existsQuotationMaterial = quotationMaterialService
						.findById(materialId);
				try {
					fileUploadService.deleteFile(existsQuotationMaterial
							.getUploadFileName());
				} catch (Exception e) {
					log.error("删除云端文件出错" + e.getMessage());
				}
				quotationMaterialService.deleteQuotationMaterial(materialId);
			}

		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, "delete fail");
		}
		return JsonBeanUtil.convertBeanToJsonBean(existsQuotationMaterial);
	}
    
    @RequestMapping("downloadQuoteMaterial")
	public void downloadQuoteMaterial(HttpServletResponse response,HttpServletRequest request,
			@RequestParam("quoteMaterialId") Long quoteMaterialId) {
        if(quoteMaterialId == null){
        	return;
        }
		QuotationMaterial material = quotationMaterialService.findById(quoteMaterialId);
		if(material == null){
			return;
		}
		String fileName = material.getDownloadFileName();
		if (!PatternUtil.isBlank(fileName) && fileName.contains(".")) {
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if(!fileType.endsWith("jpg") &&
					!fileType.endsWith("png") && 
					!fileType.endsWith("gif") && 
					!fileType.endsWith("jpeg") && 
					!fileType.endsWith("bmp") ){
				try {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
				} catch (UnsupportedEncodingException e) {
					log.error("=====download quotation material fail===="
							+ e.getMessage());
				}
			}else{
				String userAgent = request.getHeader("user-agent").toLowerCase();
				if (userAgent.contains("msie")) {
					response.setContentType("application/octet-stream");
				}else{
					response.setContentType("image/" + fileType);
				}
			}
		}

		try {
			FileCopyUtils.copy(fileUploadService.download(material.getUploadFileName()),
					response.getOutputStream());
		} catch (Exception e) {
			log.error("=====download quotation material fail===="
					+ e.getMessage());
		}
	}

	/**
	* 功能描述：  投放平台，根据当前登录人及业务类型获取  
	* 创建人：yudajun    
	* 创建时间：2014-4-16 上午9:31:57   
	* 修改人：yudajun
	* 修改时间：2014-4-16 上午9:31:57   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findPlatformByCurrUser")
	@ResponseBody
	public JsonBean<List<AdvertisingPlatform>> findPlatformByCurrUser(@RequestBody QuotationMainCondition condition) {
		List<AdvertisingPlatform> platformList = userDataRightService.getPlatformListByUcId(this.getUserId());
		List<AdvertisingPlatform> result = new ArrayList<AdvertisingPlatform>();
		for(AdvertisingPlatform form : platformList){
			if(form != null && form.getBusinessType() == condition.getBusinessType().ordinal()){
				result.add(adPlatformService.getByIdAndLocale(form.getId(), currentLocale));
			}
		}
		
        Collections.sort(result, new AdvertisingPlatformComparator());
		return JsonBeanUtil.convertBeanToJsonBean(result);
	}
	/**
	* 功能描述：当前标杆价   
	* 创建人：yudajun    
	* 创建时间：2014-4-16 上午10:44:21   
	* 修改人：yudajun
	* 修改时间：2014-4-16 上午10:44:21   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findCurrentQuotation")
	@ResponseBody
	public JsonBean<QuotationMainAddVO> findCurrentQuotation(@RequestBody QuotationMainCondition condition) {
		List<Long> siteIdList = new ArrayList<Long>();
		if(BusinessType.CASH.ordinal() == condition.getBusinessType().ordinal()){//变现
			List<Site> siteList = userDataRightService.getSiteListByUcId(this.getUserId(),currentLocale);
			List<Site> siteListNew = new ArrayList<Site>();
			String currAdPlatformId = condition.getAdvertisingPlatformId();
			if (currAdPlatformId != null) {
			    List<Site> existsSiteList = siteService.findSiteByAdPlatform(Long.valueOf(currAdPlatformId), null);
			    Map<Long,String> existsSiteMap = new HashMap<Long,String> ();
			    for (Site tempExistsSite : existsSiteList) {
			        existsSiteMap.put(tempExistsSite.getId(), "");
			    }
			    for(Site site : siteList){
	                if(existsSiteMap.get(site.getId()) != null){
	                    siteListNew.add(site);
	                    siteIdList.add(site.getId());
	                }
	            }
			}
			
			
			condition.setSiteList(siteListNew);
			condition.setSiteOrAgentIdList(siteIdList);
		}else{//销售
			List<String> agentIds = userDataRightService.getRegionalIdListByUcId(getUserId());
			List<AgentRegional> agentList = new ArrayList<AgentRegional>();
			
			if(CollectionUtils.isEmpty(agentIds)){
				List<AgentRegional> ls = agentRegionalService.getAllByLocale(currentLocale);
				for(AgentRegional agent : ls){
					agentList.add(agent);
					siteIdList.add(agent.getId());
				}
			}
			
			if(CollectionUtils.isNotEmpty(agentIds)){
				for(String id :agentIds){
					if(StringUtils.isNotBlank(id)){
						AgentRegional agent = agentRegionalService.getByIdAndLocale(id, this.currentLocale);
						agentList.add(agent);
						siteIdList.add(agent.getId());
					}
				}
			}
			condition.setSiteOrAgentIdList(siteIdList);
			condition.setAgentList(agentList);
		}
		
		com.baidu.gcrm.user.model.User currentUser = userService.findByUcid(this.getUserId());
		if(currentUser != null && ParticipantConstants.countryAgent.name().equals(currentUser.getRole())){//如果是国代，加上提交人的条件进行查询
			List<Long> createOperatorList = new ArrayList<Long>();
			List<User> userList = userDataRightService.getSubUserListByUcId(this.getUserId());
			for(User user:userList){
				createOperatorList.add(user.getUcid());
			}
			condition.setCreateOperatorList(createOperatorList);
		}
		condition.setStatus(QuotationStatus.VALID);
		List<QuotationView> quotationList = quotationMainService.findCurrentQuotation(condition, currentLocale);
		
		return processCurrentQuotation(quotationList);
	}

	
	private JsonBean<QuotationMainAddVO> processCurrentQuotation(List<QuotationView> quotationViewList){
		QuotationMainAddVO current = new QuotationMainAddVO();
		List<QuotationAddVO> result = new ArrayList<QuotationAddVO>();
		
		List<BillingModel> billmodelList = billingModelService.getAll();
		Map<String,Long> billModeMap = new HashMap<String, Long>();
		for(BillingModel model : billmodelList){
			billModeMap.put(model.getName().toLowerCase(), model.getId());
		}
		
		for(QuotationView view : quotationViewList){
			QuotationAddVO vo = new QuotationAddVO();
			vo.setSiteIi8nName(view.getSiteName());
			
			List<Quotation> quotationList = view.getQuotationList();
			
			if(quotationList != null && quotationList.size()>0){
				List<PriceTypeCps> cpsList = new ArrayList<PriceTypeCps>();
				for(Quotation quotation : quotationList){
					DecimalFormat df2 = new DecimalFormat("#.###");
					if(quotation.getPriceType().equals(PriceType.rebate)){//返点
						if(quotation.getRatioRebate() != null && quotation.getRatioRebate().doubleValue() > 0){
							vo.setRatioRebate(df2.format(quotation.getRatioRebate()));
							current.setShowRebate(true);
						}
					}
					
					if(quotation.getPriceType().equals(PriceType.ratio)){//分成
						if(quotation.getRatioMine() != null && quotation.getRatioMine().doubleValue() > 0){
							vo.setRatioMine(df2.format(quotation.getRatioMine()));
							current.setShowRatio(true);
						}
						
						if(quotation.getRatioCustomer() != null && quotation.getRatioCustomer().doubleValue() > 0){
							vo.setRatioCustomer(df2.format(quotation.getRatioCustomer()));
							current.setShowRatio(true);
						}
						
						if(quotation.getRatioThird() != null && quotation.getRatioThird().doubleValue() > 0){
							vo.setRatioThird(df2.format(quotation.getRatioThird()));
							current.setShowRatio(true);
						}
					}
					
					if(quotation.getPriceType().equals(PriceType.unit)){//单价
						if(quotation.getBillingModelId().equals(billModeMap.get("cpc"))){
							if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
								PriceTypeOther o = new PriceTypeOther();
								o.setValue(df2.format(quotation.getPublishPrice()));
								vo.setCpc(o);
								current.setShowUnit(true);
							}
						}
						
						if(quotation.getBillingModelId().equals(billModeMap.get("cpa"))){
							if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
								PriceTypeOther o = new PriceTypeOther();
								o.setValue(df2.format(quotation.getPublishPrice()));
								vo.setCpa(o);
								current.setShowUnit(true);
							}
						}
						
						if(quotation.getBillingModelId().equals(billModeMap.get("cpt"))){
							if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
								PriceTypeOther o = new PriceTypeOther();
								o.setValue(df2.format(quotation.getPublishPrice()));
								vo.setCpt(o);
								current.setShowUnit(true);
							}
						}
						
						if(quotation.getBillingModelId().equals(billModeMap.get("cpm"))){
							if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
								PriceTypeOther o = new PriceTypeOther();
								o.setValue(df2.format(quotation.getPublishPrice()));
								vo.setCpm(o);
								current.setShowUnit(true);
							}
						}
						
						if(quotation.getBillingModelId().equals(billModeMap.get("cps"))){
							PriceTypeCps cps = new PriceTypeCps();
							if(quotation.getIndustryId() != null){
								Map<String,Code> codeMap = codeServiceImpl.getCodeValueMap("quotationMain.industry",this.currentLocale.name());
								Code code = codeMap.get(String.valueOf(quotation.getIndustryId()));
								if(code != null){
									cps.setIndustryName(code.getI18nName());
									cps.setIndustryId(quotation.getIndustryId().longValue());
								}
							}
							if(quotation.getRatioMine() != null && quotation.getRatioMine().doubleValue() > 0){
								cps.setValue(df2.format(quotation.getRatioMine()));
								cpsList.add(cps);
								current.setShowUnit(true);
							}
						}
					}
				}
			vo.setCps(cpsList);
			}
			result.add(vo);
		}
		current.setQuotationList(result);
		return JsonBeanUtil.convertBeanToJsonBean(current);
	}
   
    @RequestMapping("/approve/{quoteMaimId}")
	public ModelAndView approve(@PathVariable("quoteMaimId") Long quoteMaimId){
		
		int i=0;
		getUserName();
		try{
		QuotationMain main=quotationMainService.findById(quoteMaimId);
    	quotationMainService.submitProcess(main, i, getUserName());
    	System.out.println ("审批记录共"+qtApprovalRecordService.findRecordByQuoteMainId(quoteMaimId).size());
		}
		catch (Exception e) {
			System.out.println("错误"+e.getMessage());
		}
    	return null;
	}
  
	/**
	* 功能描述：   根据当前人员，查询业务类型
	* 创建人：yudajun    
	* 创建时间：2014-4-16 下午5:58:07   
	* 修改人：yudajun
	* 修改时间：2014-4-16 下午5:58:07   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	@RequestMapping("/findBusinessTypeByCurrentUser")
	@ResponseBody
	public JsonBean<List<QuotationBusinessType>> findBusinessTypeByCurrentUser(){
        List<QuotationBusinessType> businessTypeList = new ArrayList<QuotationBusinessType>();
		BusinessType[] typeArray = BusinessType.values();
		for(int i = 0; i < typeArray.length; i++){
			BusinessType typeOld = typeArray[i];
			QuotationBusinessType type = new QuotationBusinessType();
			type.setId(Long.valueOf(typeOld.ordinal()));
			type.setBusinessTypeName(typeOld.name());
			
			List<AdvertisingPlatform> platformList = userDataRightService.getPlatformListByUcId(this.getUserId());
			List<AdvertisingPlatform> result = new ArrayList<AdvertisingPlatform>();
			for(AdvertisingPlatform form : platformList){
				if(form != null && form.getBusinessType() == typeOld.ordinal()){
					result.add(adPlatformService.getByIdAndLocale(form.getId(), currentLocale));
				}
			}
	        Collections.sort(result, new AdvertisingPlatformComparator());
	        if(result != null && result.size() > 0){
	        	type.setPlatformList(result);
	        	businessTypeList.add(type);
	        }
		}		
		return JsonBeanUtil.convertBeanToJsonBean(businessTypeList);
	}
	
	/**
	* 功能描述：   作废标杆价
	* 创建人：yudajun    
	* 创建时间：2014-4-18 上午9:08:58   
	* 修改人：yudajun
	* 修改时间：2014-4-18 上午9:08:58   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @return
	* @version
	 */
	@RequestMapping("/cancelQuotation")
	@ResponseBody 
	public Object cancelQuotation(String quotationMainId,String returnType,HttpServletRequest request){
		if(StringUtils.isNotBlank(quotationMainId)){
			QuotationMain main = quotationMainService.findById(Long.valueOf(quotationMainId));
			main.setStatus(QuotationStatus.CANCEL);//作废
			main.setUpdateOperator(getUserId());
			main.setUpdateTime(new Date());
			quotationMainService.saveAndFlush(main);
		}
		if(StringUtils.isNotBlank(returnType)){
			return findQuotationVOById(quotationMainId,request);
		}else{
			//如果returnType为空，则返回空的一个结果，由前端重新发起查询列表的请求
			return JsonBeanUtil.convertBeanToJsonBean(null);
		}
	}
	
	/**
	* 功能描述：   撤销标杆价申请
	* 创建人：yudajun    
	* 创建时间：2014-4-18 上午10:09:53   
	* 修改人：yudajun
	* 修改时间：2014-4-18 上午10:09:53   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @param returnType
	* 参数： @return
	* @version
	 */
	@RequestMapping("/cancelQuotationApprove")
	@ResponseBody 
	public Object cancelQuotationApprove(String quotationMainId,String returnType,HttpServletRequest request){
		if(StringUtils.isNotBlank(quotationMainId)){
			QuotationMain main = quotationMainService.findById(Long.valueOf(quotationMainId));
			quotationMainService.cancelQuotationApprove(main);
		}
		if(StringUtils.isNotBlank(returnType)){
			return findQuotationVOById(quotationMainId,request);
		}else{
			//如果returnType为空，则返回空的一个结果，由前端重新发起查询列表的请求
			return JsonBeanUtil.convertBeanToJsonBean(null);
		}
	}
	@RequestMapping("/findApproveRecord")
	@ResponseBody
	public Object findApproveRecord(@RequestParam("quotationMainId") Long quotationMainId){
		List<QtApprovalRecord> recordList = new ArrayList<QtApprovalRecord>();
		if(quotationMainId!=null){
			recordList = qtApprovalRecordService.findByQuoteMainId(quotationMainId, processDefineId, getCurrentLocale());
			if(CollectionUtils.isNotEmpty(recordList)){
				for(QtApprovalRecord rd : recordList){
					if(rd.getCreateOperator() != null){
						rd.setCreater(userService.getAccountByUcId(rd.getCreateOperator()).getName());
					}
				}
			}
		}
		return JsonBeanUtil.convertBeanToJsonBean(recordList);
	}
	/**
	* 功能描述：   
	* 创建人：yudajun   查看修改记录 
	* 创建时间：2014-4-24 下午1:56:17   
	* 修改人：yudajun
	* 修改时间：2014-4-24 下午1:56:17   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @return
	* @version
	 */
	@RequestMapping("/modifyRecord")
	@ResponseBody
	public Object findQuotationModifyRecord(@RequestParam("quotationMainId") Long quotationMainId){
		return JsonBeanUtil.convertBeanToJsonBean(quotationModifyRecordService.findQuotationModifyRecord(quotationMainId, this.currentLocale));
	}
	
	@RequestMapping("/queryPlatform")
    @ResponseBody
    public JsonBean<List<AdvertisingPlatform>> queryPlatform() {
		List<AdvertisingPlatform> adPlatformList = userDataRightService.getPlatformListByUcId(this.getUserId(),currentLocale);
        Collections.sort(adPlatformList, new BaseI18nModelComparator());
        Iterator<AdvertisingPlatform> it = adPlatformList.iterator();
        while(it.hasNext()){
        	AdvertisingPlatform form = it.next();
        	if(form.getStatus().intValue() != AdPlatformStatus.enable.ordinal()){
        		it.remove();
        	}
        }
        return JsonBeanUtil.convertBeanToJsonBean(adPlatformList);
    }
	@RequestMapping("/remind/{quoteMainId}")
    @ResponseBody
    public JsonBean<String> remind(@PathVariable("quoteMainId") Long quoteMainId) {
		try {
			quotationMainService.remindQuote(quoteMainId);
		} catch (Exception e) {
			return JsonBeanUtil.convertBeanToJsonBean(null, e.getMessage());
		}
		return JsonBeanUtil.convertBeanToJsonBean("success");
	}
	@RequestMapping("/findleader/{ucid}")
    @ResponseBody
    public JsonBean<List<String>> findleader(@PathVariable("ucid") Long ucid) {
		List<User> users=userRightsService.findPosUserByRoleTag(ParticipantConstants.dept_manager,ucid);
		List<String>  countrys=new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(users)){
			 for(int i1=0;i1<users.size();i1++){
					countrys.add(users.get(i1).getUuapName());
			 }
		 }	
		return JsonBeanUtil.convertBeanToJsonBean(countrys);
	}
}
