package com.baidu.gcrm.quote.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.QuoteProcessStartBean;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.QuoteCompleteContent;
import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;
import com.baidu.gcrm.quote.approval.record.service.IQtApprovalRecordService;
import com.baidu.gcrm.quote.dao.IQuotationMainRepositoryCustom;
import com.baidu.gcrm.quote.dao.QuotationMainRepository;
import com.baidu.gcrm.quote.dao.QuotationRepository;
import com.baidu.gcrm.quote.material.dao.QuotationMaterialRepository;
import com.baidu.gcrm.quote.material.model.QuotationMaterial;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.model.QuoteClashType;
import com.baidu.gcrm.quote.service.IQuotationModifyRecordService;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.quote.web.utils.QuotationCodeUtil;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;
import com.baidu.gcrm.quote.web.validator.QuotationValidator;
import com.baidu.gcrm.quote.web.vo.PriceTypeCps;
import com.baidu.gcrm.quote.web.vo.PriceTypeOther;
import com.baidu.gcrm.quote.web.vo.QuotationAddVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainConflict;
import com.baidu.gcrm.quote.web.vo.QuotationMainConflictMsg;
import com.baidu.gcrm.quote.web.vo.QuotationMainConflictVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainView;
import com.baidu.gcrm.quote.web.vo.QuotationView;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CodeCacheServiceImpl;

@Service
public class QuotationMainServiceImpl implements QuotationMainService {
	Logger logger = LoggerFactory.getLogger(QuotationMainServiceImpl.class);
	@Autowired
	private QuotationMainRepository quotationMainRepository;
	@Autowired
	private QuotationRepository quotationRepository;
	@Autowired
	private QuotationMaterialRepository quotationMaterialRepository;
	
	@Autowired
	private IQuotationMainRepositoryCustom qutotionMainRepositoryCustom;
	
	@Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
    
	@Autowired
	private AbstractValuelistCacheService<AgentRegional> agentRegionalService;
    
    @Autowired
	private I18nKVService i18nKVService;
	@Autowired
	private IBpmProcessService bpmProcessService;

    @Autowired
    @Qualifier("quoteProcessServiceImpl")
    IBpmProcessStartService quoteProcessService;
	@Autowired
	private IProcessNameI18nRepository processNameDao;
	@Autowired
	IUserService userService;
	@Autowired
	IQtApprovalRecordService qtApprovalRecordService;
	
	@Resource(name="billingModelServiceImpl")
	private AbstractValuelistCacheService<BillingModel> billingModelService;
	
	@Autowired
	private IQuotationModifyRecordService quotationModifyRecordService;
	
	@Autowired
	private CodeCacheServiceImpl codeServiceImpl;
	@Autowired
    IUserRightsService userRightsService;
    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;
	@Autowired
	private IParticipantService participantService;
	@Override
	public QuotationMain findById(Long id) {
		return quotationMainRepository.findOne(id);
	}

	@Override
	public QuotationMain findByQuoteCode(String quoteCode) {
		return quotationMainRepository.findByQuoteCode(quoteCode);
	}
	

	@Override
	public List<String> submitQuotation(QuotationMainView quotationMainView) throws Exception{
		List<QuotationView> quotationViewList = quotationMainView.getQuotationViewList();
		List<String> result = new ArrayList<String>();
		for(QuotationView quotationView:quotationViewList){
			//如果有站点或代理区域，按站点或区域生成主表
			if(quotationView.getSiteId() !=null){
				boolean recordModify = false;
				//标杆价主表对象属性暂存
				QuotationMain quotationMain = new QuotationMain();
				quotationMain = quotationMainView.getQuotationMain().clone();
				quotationMain.setSiteId(quotationView.getSiteId());
				
				quotationMain.setUpdateOperator(quotationMainView.getUpdateOperator());
				quotationMain.setUpdateTime(new Date());
				if(quotationMain.getId() == null){
					quotationMain.setCreateOperator(quotationMainView.getUpdateOperator());
					quotationMain.setCreateTime(new Date());
					
					//获取quoteCode
					String quoteCode = QuotationCodeUtil.getQuotationCode();
					QuotationMain quotationMainTemp = findByQuoteCode(quoteCode);
					while(quotationMainTemp != null){
						quoteCode = QuotationCodeUtil.getQuotationCode();
						quotationMainTemp = findByQuoteCode(quoteCode);
					}
					quotationMain.setQuoteCode(quoteCode);
				}else{
                    QuotationMain oldMain = quotationMainRepository.findOne(quotationMain.getId());
                    recordModify = quotationModifyRecordService.saveQuotationMainModifyRecord(oldMain, quotationMain);
				}
				quotationMain.setApproveStatus(QuotationApproveStatus.SAVING);
				quotationMain.setStatus(QuotationStatus.INVALID);
				quotationMainRepository.save(quotationMain);
				//处理子表
				List<Quotation> quotationList = quotationView.getQuotationList();
				for(Quotation quotation:quotationList){
					copyQuotationMainToQuotation(quotation,quotationMainView.getQuotationMain());
					quotation.setQuotationMainId(quotationMain.getId());
					quotation.setSiteId(quotationView.getSiteId());
				}
				
				if (quotationMain.getId() != null) {
					List<Quotation> oldQutationList = quotationRepository
							.findByQuotationMainId(quotationMain.getId());
					
					if(recordModify){
						quotationModifyRecordService.saveQuotationModifyRecord(oldQutationList, quotationList, quotationMain.getId());
					}
					
					if (CollectionUtils.isNotEmpty(oldQutationList)) {
						for (int m = 0; m < oldQutationList.size(); m++) {
							if (oldQutationList.get(m).getId() != null) {
								quotationRepository.delete(oldQutationList.get(m)
										.getId());
							}
						}
					}
				}
				
				quotationRepository.save(quotationList);
				
				//处理举证材料
				List<QuotationMaterial> quotationMaterialListOld = quotationMaterialRepository.findByQuotationMainId(quotationMain.getId());
				if (CollectionUtils.isNotEmpty(quotationMaterialListOld)) {
					for (int i = 0; i < quotationMaterialListOld.size(); i++) {
						quotationMaterialRepository.delete(quotationMaterialListOld.get(i));
					}
				}
				
				List<QuotationMaterial> quotationMaterialList = quotationView.getQuotationMaterialList();
				if(CollectionUtils.isNotEmpty(quotationMaterialList)){
					for(QuotationMaterial quotationMaterial : quotationMaterialList){
						quotationMaterial.setQuotationMainId(quotationMain.getId());
						if(quotationMaterial.getId() == null){
							quotationMaterial.setCreateOperator(quotationMainView.getUpdateOperator());
							quotationMaterial.setCreateTime(new Date());
						}
						quotationMaterial.setUpdateOperator(quotationMainView.getUpdateOperator());
						quotationMaterial.setUpdateTime(new Date());
					}
					quotationMaterialRepository.save(quotationMaterialList);
				}
				
				submitProcess(quotationMain,quotationMain.getBusinessType().ordinal(),quotationMainView.getOperatorName());
				
				result.add(quotationMain.getQuoteCode());
			}
		}
		
		return result;
	}
	
	protected void copyQuotationMainToQuotation(Quotation quotation,QuotationMain quotationMain){
		quotation.setBusinessType(quotationMain.getBusinessType());
		quotation.setAdvertisingPlatformId(quotationMain.getAdvertisingPlatformId());
		quotation.setPriceType(quotationMain.getPriceType());
		quotation.setStartTime(quotationMain.getStartTime());
		quotation.setEndTime(quotationMain.getEndTime());
	}
	
	protected void copyQuotationMainToQuotationMain(QuotationMain dest,QuotationMain src){
		dest.setBusinessType(src.getBusinessType());
		dest.setAdvertisingPlatformId(src.getAdvertisingPlatformId());
		dest.setPriceType(src.getPriceType());
		dest.setStartTime(src.getStartTime());
		dest.setEndTime(src.getEndTime());
		dest.setDescreption(src.getDescreption());
		dest.setId(src.getId());
	}
	
	protected void processValidator(Quotation quotation,
    		BindingResult bindingResult) {
		ValidationUtils.invokeValidator(new QuotationValidator(),quotation,bindingResult);
	}

	/**
	 *  标杆价列表
	 */
	@Override
	public Page<QuotationMainVO> findQuotationMainPage(
			QuotationMainCondition condition,LocaleConstants locale) {
		Page<QuotationMainVO> mainPage = new Page<QuotationMainVO>();
		
		Page<QuotationMain> page = qutotionMainRepositoryCustom.findQuotationMainPage(condition);
		List<QuotationMain> resList = page.getResult();
		
		List<QuotationMainVO> voList = new ArrayList<QuotationMainVO>();
		
		//mainPage.setPageNo(page.getPageNumber());
		mainPage.setPageSize(page.getPageSize());
		mainPage.setTotal(page.getTotalCount());
		List<User> accList = userService.findAll();
		Map<Long,User> accMap = new HashMap<Long, User>();
		for(User user : accList){
			if(user.getUcid() != null){
				accMap.put(user.getUcid(),user);
			}
		}
		
		for(QuotationMain main:resList){
			QuotationMainVO vo =  processQuotationMainVO(main,locale);
			if(main.getCreateOperator() != null){
				User account = accMap.get(main.getCreateOperator());
				if(account != null){
					vo.setCreaterName(account.getRealname());
				}
			}
			
			voList.add(vo);
		}
		
		mainPage.setContent(voList);
		return mainPage;
	}
	
	
	private QuotationMainVO processQuotationMainVO(QuotationMain quotationMain,LocaleConstants locale){
		QuotationMainVO vo =  new QuotationMainVO();
		vo.setQuotationMain(quotationMain);
		vo.setPlatformName(adPlatformService.getByIdAndLocale(
    			quotationMain.getAdvertisingPlatformId(), locale).getI18nName());
		
		BusinessType bussinessType = quotationMain.getBusinessType();
		
		if(BusinessType.CASH.equals(bussinessType)){//变现
			vo.setSiteName(i18nKVService.getI18Info(
	 				Site.class,quotationMain.getSiteId(),locale));
		}
		
		if (BusinessType.SALE.equals(bussinessType)) {// 销售
			vo.setSiteName(agentRegionalService.getByIdAndLocale(
					quotationMain.getSiteId(), locale).getI18nName());
		}
		
		Long loginUcId = RequestThreadLocal.getLoginUserId();
		if (null != quotationMain.getCreateOperator() && quotationMain.getCreateOperator().equals(loginUcId)) {
			vo.setIsOwner(true);
		}else {
			vo.setIsOwner(false);
		}
	    return vo;
    }
	/**
	 * 检查标杆价冲突
	 */
	public List<QuotationMainConflict> checkConflict(QuotationMainView quotationMainView,LocaleConstants locale){
		List<QuotationView> quotationViewList = quotationMainView.getQuotationViewList();
		QuotationMain quotationMain = quotationMainView.getQuotationMain();
		
		List<QuotationMainConflict> conflictList = new ArrayList<QuotationMainConflict>();
		//第一种情况
		List<QuotationMainConflictVO> quotationMainConflictVO1List = new ArrayList<QuotationMainConflictVO>();
		//第二种情况
		List<QuotationMainConflictVO> quotationMainConflictVO2List = new ArrayList<QuotationMainConflictVO>();
		//第三种情况
		List<QuotationMainConflictVO> quotationMainConflictVO3List = new ArrayList<QuotationMainConflictVO>();
		//第四种情况
		List<QuotationMainConflictVO> quotationMainConflictVO4List = new ArrayList<QuotationMainConflictVO>();
		
		for(QuotationView quotationView:quotationViewList){
			//如果有站点或代理区域，按站点或区域生成主表
			if(quotationView.getSiteId() !=null){
				Long siteId = quotationView.getSiteId();
				BusinessType businessType = quotationMain.getBusinessType();
                QuotationStatus status = QuotationStatus.OVERDUE_INVALID;
                QuotationApproveStatus quotationApproveStatus = QuotationApproveStatus.APPROVED;
                PriceType priceType = quotationMain.getPriceType();
                Long platformId = quotationMain.getAdvertisingPlatformId();
                
				List<QuotationMain> mainList = quotationMainRepository
						.findBySiteIdAndBusinessTypeAndStatusLessThanAndApproveStatusAndPriceTypeAndAdvertisingPlatformId(siteId,
								businessType, status,quotationApproveStatus,priceType,platformId);
				
				if(mainList.size() > 0){//有旧的生效的标杆价才判断冲突信息
					for (int i = 0; i < mainList.size(); i++) {
						QuotationMain main = mainList.get(i);
						QuotationMainVO oldVO = processQuotationMainVO(main,locale);
						
						if (DateUtils.compareDate(quotationMain.getStartTime(),
								main.getEndTime(), DateUtils.YYYY_MM_DD) <= 0
								&& DateUtils.compareDate(
										quotationMain.getStartTime(),
										main.getStartTime(),
										DateUtils.YYYY_MM_DD) == 1
								&& DateUtils
										.compareDate(
												quotationMain.getEndTime(),
												main.getEndTime(),
												DateUtils.YYYY_MM_DD) >= 0) {// 情况1  其实有且仅有一个
							QuotationMainConflictVO conflictVO = new QuotationMainConflictVO();//发生冲突的旧标杆价 以及 设置截肢后的标杆价对应表
							conflictVO.setOldQuotationMainVO(oldVO);//设置旧的标杆价
							
							//处理成新的标杆价列表，情况1为一对一
							List<QuotationMainVO> newQuotationMainVOList = new ArrayList<QuotationMainVO>();
							QuotationMainVO newVO = new QuotationMainVO();
							newVO = oldVO.clone();//先克隆一个
							
							QuotationMain newMain = main.clone();
							newMain.setEndTime(DateUtils.getNDayFromDate(quotationMain.getStartTime(),-1));//设置为新标杆价的开始日期的上一天
							newVO.setQuotationMain(newMain);
							newQuotationMainVOList.add(newVO);
							
							conflictVO.setNewQuotationMainVOList(newQuotationMainVOList);
							quotationMainConflictVO1List.add(conflictVO);
						}
						
						if (DateUtils.compareDate(quotationMain.getStartTime(),
								main.getStartTime(), DateUtils.YYYY_MM_DD) <= 0
								&& DateUtils.compareDate(
										quotationMain.getEndTime(),
										main.getStartTime(),
										DateUtils.YYYY_MM_DD) >= 0
								&& DateUtils
										.compareDate(
												quotationMain.getEndTime(),
												main.getEndTime(),
												DateUtils.YYYY_MM_DD) == -1) {// 情况2   其实有且仅有一个
							
							QuotationMainConflictVO conflictVO = new QuotationMainConflictVO();//发生冲突的旧标杆价 以及 设置截肢后的标杆价对应表
							conflictVO.setOldQuotationMainVO(oldVO);//设置旧的标杆价
							
							//处理成新的标杆价列表，情况2为一对一
							List<QuotationMainVO> newQuotationMainVOList = new ArrayList<QuotationMainVO>();
							QuotationMainVO newVO = new QuotationMainVO();
							newVO = oldVO.clone();//先克隆一个
							
							QuotationMain newMain = main.clone();
							newMain.setStartTime(DateUtils.getNDayFromDate(quotationMain.getEndTime(),1));//设置为新标杆价的结束日期的后一天
							newVO.setQuotationMain(newMain);
							newQuotationMainVOList.add(newVO);
							
							conflictVO.setNewQuotationMainVOList(newQuotationMainVOList);
							quotationMainConflictVO2List.add(conflictVO);
						}
						
						if (DateUtils.compareDate(quotationMain.getStartTime(),
								main.getStartTime(), DateUtils.YYYY_MM_DD) == 1
								&& DateUtils
										.compareDate(
												quotationMain.getStartTime(),
												main.getEndTime(),
												DateUtils.YYYY_MM_DD) == -1
								&& DateUtils.compareDate(
										quotationMain.getEndTime(),
										main.getStartTime(),
										DateUtils.YYYY_MM_DD) == 1
								&& DateUtils
										.compareDate(
												quotationMain.getEndTime(),
												main.getEndTime(),
												DateUtils.YYYY_MM_DD) == -1) {// 情况3 其实有且仅有一个
							QuotationMainConflictVO conflictVO = new QuotationMainConflictVO();//发生冲突的旧标杆价 以及 设置截肢后的标杆价对应表
							conflictVO.setOldQuotationMainVO(oldVO);//设置旧的标杆价
							
							//处理成新的标杆价列表，情况3为一对多
							List<QuotationMainVO> newQuotationMainVOList = new ArrayList<QuotationMainVO>();
							QuotationMainVO newVO = new QuotationMainVO();
							newVO = oldVO.clone();//先克隆一个
							
							QuotationMain newMain = new QuotationMain();
							newMain = main.clone();
							newMain.setEndTime(DateUtils.getNDayFromDate(quotationMain.getStartTime(),-1));//设置为新标杆价的开始日期的前一天
							newVO.setQuotationMain(newMain);
							
							
							QuotationMainVO newVO1 = new QuotationMainVO();
							newVO1 = oldVO.clone();//再克隆一个
							
							QuotationMain newMain1 = new QuotationMain();
							newMain1 = main.clone();
							newMain1.setStartTime(DateUtils.getNDayFromDate(quotationMain.getEndTime(),1));//设置为新标杆价的结束日期的后一天
							newMain1.setQuoteCode("");
							newMain1.setId(null);
							newMain1.setCreateTime(new Date());
							newVO1.setQuotationMain(newMain1);
							
							newQuotationMainVOList.add(newVO);
							newQuotationMainVOList.add(newVO1);
							
							conflictVO.setNewQuotationMainVOList(newQuotationMainVOList);
							quotationMainConflictVO3List.add(conflictVO);
						}
						
						if (DateUtils.compareDate(quotationMain.getStartTime(),
								main.getStartTime(), DateUtils.YYYY_MM_DD) <= 0
								&& DateUtils
										.compareDate(
												quotationMain.getEndTime(),
												main.getEndTime(),
												DateUtils.YYYY_MM_DD) >= 0) {// 情况4  可以有多个
							QuotationMainConflictVO conflictVO = new QuotationMainConflictVO();//发生冲突的旧标杆价 以及 设置截肢后的标杆价对应表
							conflictVO.setOldQuotationMainVO(oldVO);//设置旧的标杆价
							quotationMainConflictVO4List.add(conflictVO);
						}
					}
				}
			}
		}
		
		if(quotationMainConflictVO1List.size() > 0){
			QuotationMainConflict conflict = new QuotationMainConflict();
			conflict.setConflictType("1");
			conflict.setNewPeriods(DateUtils.getDate2String(
					DateUtils.YYYY_MM_DD, DateUtils
					.getNDayFromDate(quotationMain
							.getStartTime(), -1)));
			List<QuotationMainConflictMsg> msgList = new ArrayList<QuotationMainConflictMsg>();
			for(QuotationMainConflictVO vo :quotationMainConflictVO1List){
				QuotationMainConflictMsg msg = new QuotationMainConflictMsg();
				msg.setSiteName(vo.getOldQuotationMainVO().getSiteName());
				msg.setPlatformName(vo.getOldQuotationMainVO().getPlatformName());
				msg.setEndTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getEndTime()));
				msg.setStartTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getStartTime()));
				msg.setQuoteCode(vo.getOldQuotationMainVO().getQuotationMain().getQuoteCode());
				
				msgList.add(msg);
			}
			
			conflict.setMsgs(msgList);
			conflictList.add(conflict);
		}
		
		if(quotationMainConflictVO2List.size() > 0){
			QuotationMainConflict conflict = new QuotationMainConflict();
			conflict.setConflictType("2");
			conflict.setNewPeriods(DateUtils.getDate2String(
					DateUtils.YYYY_MM_DD, DateUtils
					.getNDayFromDate(quotationMain
							.getEndTime(), 1)));
			List<QuotationMainConflictMsg> msgList = new ArrayList<QuotationMainConflictMsg>();
			for(QuotationMainConflictVO vo :quotationMainConflictVO2List){
				QuotationMainConflictMsg msg = new QuotationMainConflictMsg();
				msg.setSiteName(vo.getOldQuotationMainVO().getSiteName());
				msg.setPlatformName(vo.getOldQuotationMainVO().getPlatformName());
				msg.setEndTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getEndTime()));
				msg.setStartTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getStartTime()));
				msg.setQuoteCode(vo.getOldQuotationMainVO().getQuotationMain().getQuoteCode());
				
				msgList.add(msg);
			}
			
			conflict.setMsgs(msgList);
			conflictList.add(conflict);
		}
		
		if(quotationMainConflictVO3List.size() > 0){
			QuotationMainConflict conflict = new QuotationMainConflict();
			conflict.setConflictType("3");
			
			List<QuotationMainConflictMsg> msgList = new ArrayList<QuotationMainConflictMsg>();
			for(int j = 0; j < quotationMainConflictVO3List.size(); j++){
				QuotationMainConflictVO vo = quotationMainConflictVO3List.get(j);
				QuotationMainConflictMsg msg = new QuotationMainConflictMsg();
				msg.setSiteName(vo.getOldQuotationMainVO().getSiteName());
				msg.setPlatformName(vo.getOldQuotationMainVO().getPlatformName());
				msg.setEndTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getEndTime()));
				msg.setStartTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getStartTime()));
				msg.setQuoteCode(vo.getOldQuotationMainVO().getQuotationMain().getQuoteCode());
				
				List<QuotationMainVO> newQuotationMainVOList = quotationMainConflictVO3List.get(j).getNewQuotationMainVOList();
				List<String> periods = new ArrayList<String>();
				for(int i = 0; i < newQuotationMainVOList.size(); i++){
					QuotationMainVO newVO = newQuotationMainVOList.get(i);
					StringBuilder sb = new StringBuilder();
					sb.append(DateUtils.getDate2String(
								DateUtils.YYYY_MM_DD,newVO.getQuotationMain().getStartTime()))
					  .append("~")
					  .append(DateUtils.getDate2String(
								DateUtils.YYYY_MM_DD,newVO.getQuotationMain().getEndTime()));
					periods.add(sb.toString());
				}
				msg.setMsgPeriods(periods);
				msgList.add(msg);
			}
			conflict.setMsgs(msgList);
			conflictList.add(conflict);
		}
		
		if(quotationMainConflictVO4List.size() > 0){
			QuotationMainConflict conflict = new QuotationMainConflict();
			conflict.setConflictType("4");
			List<QuotationMainConflictMsg> msgList = new ArrayList<QuotationMainConflictMsg>();
			for(QuotationMainConflictVO vo :quotationMainConflictVO4List){
				QuotationMainConflictMsg msg = new QuotationMainConflictMsg();
				msg.setSiteName(vo.getOldQuotationMainVO().getSiteName());
				msg.setPlatformName(vo.getOldQuotationMainVO().getPlatformName());
				msg.setEndTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getEndTime()));
				msg.setStartTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,
						vo.getOldQuotationMainVO().getQuotationMain().getStartTime()));
				msg.setQuoteCode(vo.getOldQuotationMainVO().getQuotationMain().getQuoteCode());
				msgList.add(msg);
			}
			conflict.setMsgs(msgList);
			conflictList.add(conflict);
		}
		return conflictList;
	}
	
	public List<QuoteCompleteContent> processQuotationAfterApproved(Long quotationMainId,LocaleConstants locale,Long operator) throws CRMBaseException{
		QuotationMain quotationMain = quotationMainRepository.findOne(quotationMainId);
		Long siteId = quotationMain.getSiteId();
		BusinessType businessType = quotationMain.getBusinessType();
        QuotationStatus status = QuotationStatus.OVERDUE_INVALID;
        QuotationApproveStatus quotationApproveStatus = QuotationApproveStatus.APPROVED;
        PriceType priceType = quotationMain.getPriceType();
        Long platformId = quotationMain.getAdvertisingPlatformId();
        
        //获取该站点或区域目前有效的标杆价
        List<QuotationMain> mainList = quotationMainRepository
				.findBySiteIdAndBusinessTypeAndStatusLessThanAndApproveStatusAndPriceTypeAndAdvertisingPlatformId(siteId,
						businessType, status,quotationApproveStatus,priceType,platformId);
        List<QuoteCompleteContent> mailContentList = new ArrayList<QuoteCompleteContent>();
        
        //审批通过时间大于结束时间，这事不应该存在
        if(DateUtils.compareDate(quotationMain.getEndTime(), new Date(), DateUtils.YYYY_MM_DD) == -1){
        	quotationMain.setApproveStatus(QuotationApproveStatus.APPROVED);
			quotationMain.setUpdateTime(new Date());
			quotationMain.setUpdateOperator(operator);
        	
			quotationMain.setStatus(QuotationStatus.OVERDUE_INVALID);//超期失效
			
			quotationMainRepository.save(quotationMain);
        	return mailContentList;
        }
        //审批通过的时间 大于开始时间，那么开始时间应该设置为审批通过当天
        if(quotationMain.getStartTime().before(new Date())){
        	quotationMain.setStartTime(new Date());
        }
        
        quotationMain.setApproveStatus(QuotationApproveStatus.APPROVED);
		quotationMain.setUpdateTime(new Date());
		quotationMain.setUpdateOperator(operator);
        
		if(mainList.size() > 0){//有旧的生效的标杆价才判断冲突信息
			for (int i = 0; i < mainList.size(); i++) {
				QuotationMain main = mainList.get(i);
				if(DateUtils.compareDate(quotationMain.getStartTime(),
						main.getEndTime(), DateUtils.YYYY_MM_DD) <= 0
						&& DateUtils.compareDate(
								quotationMain.getStartTime(),
								main.getStartTime(),
								DateUtils.YYYY_MM_DD) == 1
						&& DateUtils
								.compareDate(
										quotationMain.getEndTime(),
										main.getEndTime(),
										DateUtils.YYYY_MM_DD) >= 0){//情况1
					
					main.setEndTime(DateUtils.getNDayFromDate(quotationMain.getStartTime(),-1));//设置为新标杆价的开始日期的上一天
					main.setUpdateTime(new Date());
					main.setUpdateOperator(quotationMain.getCreateOperator());
					
					List<Quotation> qutationList = quotationRepository.findByQuotationMainId(main.getId());
					
					for(Quotation quotation:qutationList){
						quotation.setEndTime(main.getEndTime());
					}
					
					quotationRepository.save(qutationList);
					quotationMainRepository.saveAndFlush(main);
					
					User account =  userService.findByUcid(main.getCreateOperator());
					Set<String> sendTo = new HashSet<String>();
					sendTo.add(account.getEmail());
					
					
					
					QuoteCompleteContent mailContent = new QuoteCompleteContent();
					mailContent.setSendTo(sendTo);
					mailContent.setOperator(account.getRealname());
					mailContent.setLocale(locale);
					mailContent.setOldQuoteCode(main.getQuoteCode());
					mailContent.setNewQuoteCode(quotationMain.getQuoteCode());
					mailContent.setOldQuoteCreateDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getCreateTime()));
					mailContent.setType(QuoteClashType.Expand);
					mailContent.setOldEditValidDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getStartTime())
							+ "~"
							+ DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getEndTime()));
					if(quotationMain.getCreateOperator() != null){
						User subEr = userService.findByUcid(quotationMain.getCreateOperator());
						if(subEr != null){
							mailContent.setSubmitTer(subEr.getRealname());
						}
					}
					
					mailContentList.add(mailContent);
				}
				
				if(DateUtils.compareDate(quotationMain.getStartTime(),
						main.getStartTime(), DateUtils.YYYY_MM_DD) <= 0
						&& DateUtils.compareDate(
								quotationMain.getEndTime(),
								main.getStartTime(),
								DateUtils.YYYY_MM_DD) >= 0
						&& DateUtils
								.compareDate(
										quotationMain.getEndTime(),
										main.getEndTime(),
										DateUtils.YYYY_MM_DD) == -1){//情况2
					main.setStartTime(DateUtils.getNDayFromDate(quotationMain.getEndTime(),1));//设置为新标杆价的结束日期的后一天
					main.setUpdateTime(new Date());
					main.setUpdateOperator(quotationMain.getCreateOperator());
					
					List<Quotation> qutationList = quotationRepository.findByQuotationMainId(main.getId());
					
					for(Quotation quotation:qutationList){
						quotation.setStartTime(main.getStartTime());
					}
					
					quotationRepository.save(qutationList);
					quotationMainRepository.saveAndFlush(main);
					
					User account =  userService.findByUcid(main.getCreateOperator());
					Set<String> sendTo = new HashSet<String>();
					sendTo.add(account.getEmail());
					
					QuoteCompleteContent mailContent = new QuoteCompleteContent();
					mailContent.setSendTo(sendTo);
					mailContent.setOperator(account.getRealname());
					mailContent.setLocale(locale);
					mailContent.setOldQuoteCode(main.getQuoteCode());
					mailContent.setNewQuoteCode(quotationMain.getQuoteCode());
					mailContent.setOldQuoteCreateDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getCreateTime()));
					mailContent.setType(QuoteClashType.Expand);
					mailContent.setOldEditValidDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getStartTime())
							+ "~"
							+ DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getEndTime()));
					if(quotationMain.getCreateOperator() != null){
						User subEr = userService.findByUcid(quotationMain.getCreateOperator());
						if(subEr != null){
							mailContent.setSubmitTer(subEr.getRealname());
						}
					}
					mailContentList.add(mailContent);
				}
				
				if(DateUtils.compareDate(quotationMain.getStartTime(),
						main.getStartTime(), DateUtils.YYYY_MM_DD) == 1
						&& DateUtils
								.compareDate(
										quotationMain.getStartTime(),
										main.getEndTime(),
										DateUtils.YYYY_MM_DD) == -1
						&& DateUtils.compareDate(
								quotationMain.getEndTime(),
								main.getStartTime(),
								DateUtils.YYYY_MM_DD) == 1
						&& DateUtils
								.compareDate(
										quotationMain.getEndTime(),
										main.getEndTime(),
										DateUtils.YYYY_MM_DD) == -1){//情况3
					QuotationMain mainAdd = new QuotationMain();
					mainAdd = main.clone();//克隆一个新的出来
					mainAdd.setId(null);
					mainAdd.setCreateOperator(quotationMain.getCreateOperator());
					mainAdd.setCreateTime(new Date());
					
					//获取quoteCode
					String quoteCode = QuotationCodeUtil.getQuotationCode();
					QuotationMain quotationMainTemp = findByQuoteCode(quoteCode);
					while(quotationMainTemp != null){
						quoteCode = QuotationCodeUtil.getQuotationCode();
						quotationMainTemp = findByQuoteCode(quoteCode);
					}
					
					mainAdd.setQuoteCode(quoteCode);
					mainAdd.setStartTime(DateUtils.getNDayFromDate(quotationMain.getEndTime(),1));//设置为新标杆价的结束日期的后一天
					mainAdd.setUpdateTime(new Date());
					mainAdd.setUpdateOperator(quotationMain.getCreateOperator());
					
					quotationMainRepository.saveAndFlush(mainAdd);
					
					main.setEndTime(DateUtils.getNDayFromDate(quotationMain.getStartTime(),-1));//设置为新标杆价的开始日期的上一天
					main.setUpdateTime(new Date());
					main.setUpdateOperator(quotationMain.getCreateOperator());
					
					quotationMainRepository.saveAndFlush(main);
					
					List<Quotation> qutationList = quotationRepository.findByQuotationMainId(main.getId());
					
					List<Quotation> qutationAddList = new ArrayList<Quotation>();
					
					for(Quotation quotation:qutationList){
						Quotation quotationAdd = new Quotation();
						quotationAdd = quotation.clone();
						
						quotation.setEndTime(main.getEndTime());
						
						quotationAdd.setStartTime(mainAdd.getStartTime());
						quotationAdd.setId(null);
						quotationAdd.setQuotationMainId(mainAdd.getId());
						
						qutationAddList.add(quotationAdd);
					}
					
					List<QuotationMaterial> quotationMaterialList = 
							quotationMaterialRepository.findByQuotationMainId(main.getId());
					List<QuotationMaterial> quotationMaterialAddList = new ArrayList<QuotationMaterial>();
					for(QuotationMaterial me:quotationMaterialList){
						QuotationMaterial addMe = new QuotationMaterial();
						addMe = me.clone();
						addMe.setId(null);
						addMe.setCreateOperator(quotationMain.getCreateOperator());
						addMe.setCreateTime(new Date());
						addMe.setQuotationMainId(mainAdd.getId());
						addMe.setUpdateOperator(quotationMain.getCreateOperator());
						addMe.setUpdateTime(new Date());
						quotationMaterialAddList.add(addMe);
					}
					
					quotationMaterialRepository.save(quotationMaterialAddList);
					quotationRepository.save(qutationList);
					quotationRepository.save(qutationAddList);
					
					User account =  userService.findByUcid(main.getCreateOperator());
					Set<String> sendTo = new HashSet<String>();
					sendTo.add(account.getEmail());
					
					QuoteCompleteContent mailContent = new QuoteCompleteContent();
					mailContent.setSendTo(sendTo);
					mailContent.setOperator(account.getRealname());
					mailContent.setLocale(locale);
					mailContent.setOldQuoteCode(main.getQuoteCode());
					mailContent.setNewQuoteCode(quotationMain.getQuoteCode());
					mailContent.setOldQuoteCreateDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getCreateTime()));
					mailContent.setType(QuoteClashType.Split);
					mailContent.setOldEditValidDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getStartTime())
							+ "~"
							+ DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getEndTime()));
					
					mailContent.setAddQuoteCode(mainAdd.getQuoteCode());
					mailContent.setAddValidDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, mainAdd.getStartTime())
							+ "~"
							+ DateUtils.getDate2String(DateUtils.YYYY_MM_DD, mainAdd.getEndTime()));
					if(quotationMain.getCreateOperator() != null){
						User subEr = userService.findByUcid(quotationMain.getCreateOperator());
						if(subEr != null){
							mailContent.setSubmitTer(subEr.getRealname());
						}
					}
					mailContentList.add(mailContent);
				}
				
				if(DateUtils.compareDate(quotationMain.getStartTime(),
						main.getStartTime(), DateUtils.YYYY_MM_DD) <= 0
						&& DateUtils
								.compareDate(
										quotationMain.getEndTime(),
										main.getEndTime(),
										DateUtils.YYYY_MM_DD) >= 0
						&& !quotationMain.getId().equals(main.getId())){//情况4 可以有多个
					//旧标杆价作废
					main.setUpdateTime(new Date());
					main.setUpdateOperator(operator);
					main.setStatus(QuotationStatus.CANCEL);
					main.setCancelOperator(quotationMain.getCreateOperator());
					main.setCancelTime(new Date());
					
					quotationMainRepository.saveAndFlush(main);
					
					User account =  userService.findByUcid(main.getCreateOperator());
					Set<String> sendTo = new HashSet<String>();
					sendTo.add(account.getEmail());
					
					QuoteCompleteContent mailContent = new QuoteCompleteContent();
					mailContent.setSendTo(sendTo);
					mailContent.setOperator(account.getRealname());
					mailContent.setLocale(locale);
					mailContent.setOldQuoteCode(main.getQuoteCode());
					mailContent.setType(QuoteClashType.Cancel);
					mailContent.setOldQuoteCreateDate(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getCreateTime()));
					mailContent.setNewQuoteCode(quotationMain.getQuoteCode());
					if(quotationMain.getCreateOperator() != null){
						User subEr = userService.findByUcid(quotationMain.getCreateOperator());
						if(subEr != null){
							mailContent.setSubmitTer(subEr.getRealname());
						}
					}
					
					mailContentList.add(mailContent);
				}
			}
		}
		quotationMainRepository.saveAndFlush(quotationMain);
		validQuotation(quotationMain.getAdvertisingPlatformId(), siteId);//设置标杆价有效或超期失效
		return mailContentList;
	}

	@Override
	public QuotationMainView findQuotationVOById(Long quotationMainId,LocaleConstants localeConstants,HttpServletRequest request) {
		QuotationMainView detail = new QuotationMainView();
		QuotationMain main = quotationMainRepository.findOne(quotationMainId);
		if(main != null){
	
			String taskInfoMessage = gcrmTaskInfoService.convertTaskInfo(RemindType.quote,main.getTaskInfo(),localeConstants);
			QuotationMainVO mainVO = processQuotationMainVO(main,localeConstants);
			mainVO.setTaskInfoMessage(taskInfoMessage);
			if(main.getCreateOperator() != null){
				User acc = userService.findByUcid(main.getCreateOperator());
				mainVO.setCreaterName(acc.getRealname());
			}
			detail.setQuotationMainVO(mainVO);
			
			QuotationView view = new QuotationView();
			
			List<Quotation> qutationList = quotationRepository.findByQuotationMainId(main.getId());
			
			processQutationList(qutationList);
			
			List<QuotationMaterial> quotationMaterialList = 
					quotationMaterialRepository.findByQuotationMainId(main.getId());
			if(CollectionUtils.isNotEmpty(quotationMaterialList)){
				for(QuotationMaterial me : quotationMaterialList){
					me.setDownLoadUrl(getDownlaodURL(new StringBuffer(request.getRequestURL()),
					me.getId()));
				}
			}
			
			view.setQuotationList(qutationList);
			view.setQuotationMaterialList(quotationMaterialList);
			
			List<QuotationView> quotationViewList = new ArrayList<QuotationView>();
			quotationViewList.add(view);
			
			detail.setQuotationViewList(quotationViewList);
		}else{
			return null;
		}
		return detail;
	}
	
	private String getDownlaodURL(StringBuffer requestURL,Long id){
		int urlLength = requestURL.indexOf("findQuotationVOById");
		urlLength = urlLength>=0?urlLength:requestURL.indexOf("cancelQuotation");
		urlLength = urlLength>=0?urlLength:requestURL.indexOf("cancelQuotationApprove");
		urlLength = urlLength>=0?urlLength:requestURL.indexOf("view");
		if(urlLength>=0){
			requestURL.replace(urlLength, urlLength + requestURL.length(), "downloadQuoteMaterial");
			return requestURL.append("?quoteMaterialId=" + id).toString().replace("qouteapprovalinfo", "quote");
		}
		return "";
	}
	
	private void processQutationList(List<Quotation> qutationList){
		if(qutationList != null && qutationList.size() > 0){
			List<BillingModel> billmodelList = billingModelService.getAll();
			Map<String,Long> billModeMap = new HashMap<String, Long>();
			for(BillingModel model : billmodelList){
				billModeMap.put(model.getName().toLowerCase(), model.getId());
			}
			for(Quotation quotation : qutationList){
				DecimalFormat df2 = new DecimalFormat("#.00");
				if (quotation.getRatioMine() != null) {
					BigDecimal value = BigDecimal.valueOf(
							Double.valueOf(quotation.getRatioMine())).multiply(
							BigDecimal.valueOf(100));
					quotation.setRatioMine(Double.valueOf(df2.format(value
							.doubleValue())));
				}

				if (quotation.getRatioCustomer() != null) {
					BigDecimal value = BigDecimal.valueOf(
							Double.valueOf(quotation.getRatioCustomer()))
							.multiply(BigDecimal.valueOf(100));
					quotation.setRatioCustomer(Double.valueOf(df2.format(value
							.doubleValue())));
				}

				if (quotation.getRatioThird() != null) {
					BigDecimal value = BigDecimal.valueOf(
							Double.valueOf(quotation.getRatioThird()))
							.multiply(BigDecimal.valueOf(100));
					quotation.setRatioThird(Double.valueOf(df2.format(value
							.doubleValue())));
				}

				if (quotation.getRatioRebate() != null) {
					BigDecimal value = BigDecimal.valueOf(
							Double.valueOf(quotation.getRatioRebate()))
							.multiply(BigDecimal.valueOf(100));
					quotation.setRatioRebate(Double.valueOf(df2.format(value
							.doubleValue())));
				}
			}
		}
	}

	

	//启动标杆价审批
	@Override
	public StartProcessResponse submitProcess(QuotationMain main,int i, String username) throws Exception {
		QuoteProcessStartBean startBean = new QuoteProcessStartBean();
		startBean.setStartUser(username);
		prepare(startBean, main,i);
		StartProcessResponse respone = quoteProcessService.startProcess(startBean);		
		saveQuoteTaskInfo(respone,main.getId());		
		QtApprovalRecord record = generateApprovalRecord(main, respone);
		qtApprovalRecordService.saveApprovalRecord(record);
		return respone;
	}
	//启动前的准备
	private void prepare(QuoteProcessStartBean startBean, QuotationMain main, int i) {
		String processDefId = GcrmConfig.getConfigValueByKey("quote.process.defineId");
		startBean.setPackageId(GcrmConfig.getConfigValueByKey("quote.package.id"));
		startBean.setProcessDefineId(processDefId);
		ProcessNameI18n processNameI18n = processNameDao.findByProcessDefIdAndLocale(processDefId,
				LocaleConstants.en_US.name());
		if (processNameI18n != null) {
			startBean.setProcessName(processNameI18n.getProcessName());
		}
		startBean.setQuoteId(String.valueOf(main.getId()));
		if (i == 0) {
			startBean.setBuType("Realization");

		} else {
			startBean.setBuType("Sales");

		}
		//设置变现主管 
		List<ParticipantBean> participants = new ArrayList<ParticipantBean>();
		ParticipantBean participantCash = new ParticipantBean(); 	
		List<String> cashs = new ArrayList<String>();
		participantCash.setParticipantId(ParticipantConstants.cash_leader.name());
		Participant partcash=participantService.findByKeyAndDescAndParticId(main.getAdvertisingPlatformId().toString(), DescriptionType.platform, ParticipantConstants.cash_leader.toString()); 
		if(partcash!=null){
			User usercash=userService.findByUsername(partcash.getUsername());
			if(usercash!=null){	
				cashs.add(usercash.getUuapName());
				participantCash.setUsernames(cashs);
				participants.add(participantCash);
			}
		}
		//设置部门总监
		ParticipantBean participantCountry = new ParticipantBean(); 
		participantCountry.setParticipantId(ParticipantConstants.dept_manager.name()); 		    
		List<String> countrys = new ArrayList<String>();
		if(startBean.getBuType().equals("Sales")){
			List<User> users=userRightsService.findUserByPosTag("gcrm_area_manager");
			if (CollectionUtils.isNotEmpty(users)){
				 for(int i1=0;i1<users.size();i1++){
						countrys.add(users.get(i1).getUuapName());
				 }
					participantCountry.setUsernames(countrys);
					participants.add(participantCountry);
			 }	
		}else{
		Participant partcountrys=participantService.findByKeyAndDescAndParticId(main.getAdvertisingPlatformId().toString(), DescriptionType.platform, ParticipantConstants.dept_manager.toString()); 
		if(partcountrys!=null){
			User userdepart=userService.findByUsername(partcountrys.getUsername());
			if(userdepart!=null){
				countrys.add(userdepart.getUuapName());
				participantCountry.setUsernames(countrys);
				participants.add(participantCountry);
			}
		}
		}
		startBean.setParticipants(participants);
	}
	
	private void saveQuoteTaskInfo(StartProcessResponse respone,Long quoteId){
		List<Activity> acts = respone.getActivities();
		if(acts != null && acts.size()>0){
			QuotationMain quoteMain = quotationMainRepository.findOne(quoteId);
        	quoteMain.setTaskInfo(gcrmTaskInfoService.getTaskInfo(acts, "quote.main.approval.task"));
        	quoteMain.setApproveStatus(QuotationApproveStatus.APPROVING);
        	quotationMainRepository.save(quoteMain);
        }
	}
	
	//写入审批记录
	private QtApprovalRecord generateApprovalRecord(QuotationMain quoteMain, StartProcessResponse processResponse) {
		QtApprovalRecord record = new QtApprovalRecord();
		record.setQuoteMainId(quoteMain.getId());
		record.setProcessId(processResponse.getProcessId());
		record.setTaskId(processResponse.getFirstActivityId());
		record.setActivityId(processResponse.getActDefId());
		record.setCreateOperator(quoteMain.getCreateOperator());
		record.setCreateTime(processResponse.getProcessStartTime());
		return record;
	}


	@Override
	public List<QuotationView> findCurrentQuotation(
			QuotationMainCondition condition, LocaleConstants locale) {
		List<QuotationView> quotationList = new ArrayList<QuotationView>();
		
		if(BusinessType.CASH.ordinal() == condition.getBusinessType().ordinal()){//变现
			List<Site> siteList = condition.getSiteList();
			if(CollectionUtils.isNotEmpty(siteList)){
				for(Site site : siteList){
					QuotationView view = new QuotationView();
					view.setSiteId(site.getId());
					view.setSiteName(site.getI18nName());
					condition.setSiteId(String.valueOf(site.getId()));
					List<Quotation> list = qutotionMainRepositoryCustom.findByQuotationMainCondition(condition);
					processQutationList(list);
					view.setQuotationList(list);
					quotationList.add(view);
				}
			}
		}else{//销售
			List<AgentRegional> agentList = condition.getAgentList();
			if(CollectionUtils.isNotEmpty(agentList)){
				for(AgentRegional agent : agentList){
					QuotationView view = new QuotationView();
					view.setSiteId(agent.getId());
					view.setSiteName(agent.getI18nName());
					condition.setSiteId(String.valueOf(agent.getId()));
					List<Quotation> list = qutotionMainRepositoryCustom.findByQuotationMainCondition(condition);
					processQutationList(list);
					view.setQuotationList(list);
					quotationList.add(view);
				}
			}
		}
		
		return quotationList;
	}
	
	@Override
	public QuotationAddVO findluge(
			Long mainId, LocaleConstants locale) {
		QuotationMain quoteMain = quotationMainRepository.findOne(mainId);
		QuotationMainCondition condition = new QuotationMainCondition();
		condition.setMainId(mainId);
		
		QuotationView vo = new QuotationView();
		
		if(BusinessType.CASH.equals(quoteMain.getBusinessType())){//变现
			vo.setSiteName(i18nKVService.getI18Info(
	 				Site.class,quoteMain.getSiteId(),locale));
		}
		
		if (BusinessType.SALE.equals(quoteMain.getBusinessType())) {// 销售
			vo.setSiteName(agentRegionalService.getByIdAndLocale(
					quoteMain.getSiteId(), locale).getI18nName());
		}
		
		List<Quotation> list = qutotionMainRepositoryCustom.findByQuotationMainCondition(condition);
		processQutationList(list);
		vo.setQuotationList(list);
		QuotationAddVO res = process(vo,locale);
		return res;
	}

	private QuotationAddVO process(QuotationView view,LocaleConstants locale){
		QuotationAddVO vo = new QuotationAddVO();
		vo.setSiteIi8nName(view.getSiteName());
		
		List<BillingModel> billmodelList = billingModelService.getAll();
		Map<String,Long> billModeMap = new HashMap<String, Long>();
		for(BillingModel model : billmodelList){
			billModeMap.put(model.getName().toLowerCase(), model.getId());
		}
		
		List<Quotation> quotationList = view.getQuotationList();
		
		if(quotationList != null && quotationList.size()>0){
			List<PriceTypeCps> cpsList = new ArrayList<PriceTypeCps>();
			for(Quotation quotation : quotationList){
				DecimalFormat df2 = new DecimalFormat("#.###");
				if(quotation.getPriceType().equals(PriceType.rebate)){//返点
					if(quotation.getRatioRebate() != null && quotation.getRatioRebate().doubleValue() > 0){
						vo.setRatioRebate(df2.format(quotation.getRatioRebate()));
					}
				}
				
				if(quotation.getPriceType().equals(PriceType.ratio)){//分成
					if(quotation.getRatioMine() != null && quotation.getRatioMine().doubleValue() > 0){
						vo.setRatioMine(df2.format(quotation.getRatioMine()));
					}
					
					if(quotation.getRatioCustomer() != null && quotation.getRatioCustomer().doubleValue() > 0){
						vo.setRatioCustomer(df2.format(quotation.getRatioCustomer()));
					}
					
					if(quotation.getRatioThird() != null && quotation.getRatioThird().doubleValue() > 0){
						vo.setRatioThird(df2.format(quotation.getRatioThird()));
					}
				}
				
				if(quotation.getPriceType().equals(PriceType.unit)){//单价
					if(quotation.getBillingModelId().equals(billModeMap.get("cpc"))){
						if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
							PriceTypeOther o = new PriceTypeOther();
							o.setValue(df2.format(quotation.getPublishPrice()));
							vo.setCpc(o);
						}
					}
					
					if(quotation.getBillingModelId().equals(billModeMap.get("cpa"))){
						if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
							PriceTypeOther o = new PriceTypeOther();
							o.setValue(df2.format(quotation.getPublishPrice()));
							vo.setCpa(o);
						}
					}
					
					if(quotation.getBillingModelId().equals(billModeMap.get("cpt"))){
						if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
							PriceTypeOther o = new PriceTypeOther();
							o.setValue(df2.format(quotation.getPublishPrice()));
							vo.setCpt(o);
						}
					}
					
					if(quotation.getBillingModelId().equals(billModeMap.get("cpm"))){
						if(quotation.getPublishPrice() != null && quotation.getPublishPrice().doubleValue() > 0){
							PriceTypeOther o = new PriceTypeOther();
							o.setValue(df2.format(quotation.getPublishPrice()));
							vo.setCpm(o);
						}
					}
					
					if(quotation.getBillingModelId().equals(billModeMap.get("cps"))){
						PriceTypeCps cps = new PriceTypeCps();
						if(quotation.getIndustryId() != null){
							Map<String,Code> codeMap = codeServiceImpl.getCodeValueMap("quotationMain.industry",locale.name());
							Code code = codeMap.get(String.valueOf(quotation.getIndustryId()));
							if(code != null){
								cps.setIndustryName(code.getI18nName());
							}
						}
						if(quotation.getRatioMine() != null && quotation.getRatioMine().doubleValue() > 0){
							cps.setValue(df2.format(quotation.getRatioMine()));
							cpsList.add(cps);
						}
					}
				}
			}
		vo.setCps(cpsList);
		
		}
		return vo;
	}
	
	@Override
	public QuotationMain saveAndFlush(QuotationMain quotationMain) {
		return quotationMainRepository.saveAndFlush(quotationMain);
	}
    /**
     * 撤销标杆价申请
     */
	@Override
	public QuotationMain cancelQuotationApprove(QuotationMain quotationMain) {
		//调用撤销流程的方法
		qtApprovalRecordService.withDrawApproval(quotationMain.getId());
		return quotationMainRepository.findOne(quotationMain.getId());
	}

    /**
    * 功能描述：   
    * 创建人：yudajun    
    * 创建时间：2014-4-22 下午2:57:59   
    * 修改人：yudajun
    * 修改时间：2014-4-22 下午2:57:59   
    * 修改备注：   
    * 参数： @param advertisingPlatformId
    * 参数： @param siteId
    * @version
     */
	public void validQuotation(Long advertisingPlatformId,Long siteId){
		List<QuotationMain> mainList = qutotionMainRepositoryCustom
				.findForValid(advertisingPlatformId, siteId,
						QuotationApproveStatus.APPROVED,
						QuotationStatus.OVERDUE_INVALID);
		Date currentDate = new Date();
		for(QuotationMain main : mainList){
			if(DateUtils.compareDate(main.getStartTime(),currentDate, DateUtils.YYYY_MM_DD) <= 0
					&& DateUtils.compareDate(main.getEndTime(),currentDate, DateUtils.YYYY_MM_DD) >= 0){
				main.setStatus(QuotationStatus.VALID);
			}
			
			if(DateUtils.compareDate(main.getEndTime(),currentDate, DateUtils.YYYY_MM_DD) == -1){
				main.setStatus(QuotationStatus.OVERDUE_INVALID);
			}
			
			if(DateUtils.compareDate(main.getStartTime(),currentDate, DateUtils.YYYY_MM_DD) == 1
					&& DateUtils.compareDate(main.getEndTime(),currentDate, DateUtils.YYYY_MM_DD) == 1){
				main.setStatus(QuotationStatus.INVALID);
			}
		}
		
		quotationMainRepository.save(mainList);
	}

	@Override
	public List<QuotationMainVO> findQuotationMainVOList(
			QuotationMainCondition condition, LocaleConstants localeConstants) {
		Page<QuotationMain> page = qutotionMainRepositoryCustom.findQuotationMainPage(condition);
		List<QuotationMain> resList = page.getResult();
		List<QuotationMainVO> voList = new ArrayList<QuotationMainVO>();
		
		List<User> accList = userService.findAll();
		Map<Long,User> accMap = new HashMap<Long, User>();
		for(User user : accList){
			if(user.getUcid() != null){
				accMap.put(user.getUcid(),user);
			}
		}
		
		for(QuotationMain main:resList){
			String taskInfoMessage = gcrmTaskInfoService.convertTaskInfo(RemindType.quote,main.getTaskInfo(),localeConstants);
			QuotationMainVO mainVO = processQuotationMainVO(main,localeConstants);
			mainVO.setTaskInfoMessage(taskInfoMessage);
			
			if(main.getCreateOperator() != null){
				User account = accMap.get(main.getCreateOperator());
				if(account != null){
					mainVO.setCreaterName(account.getRealname());
				}
			}
			
			voList.add(mainVO);
		}
		return voList;
	}

	@Override
	public List<QuotationMain> findQuotationMainListCreateDateBetween(
			Date startDate, Date endDate) {
		return qutotionMainRepositoryCustom.findQuotationMainListCreateDateBetween(startDate, endDate);
	}

	@Override
	public void remindQuote(Long quoteMainId) {
		RemindRequest request;
		QuotationMain main=quotationMainRepository.findOne(quoteMainId);
		QuotationApproveStatus status=main.getApproveStatus();
		switch (status){
		case APPROVING:
			String key=main.getId().toString();
			request = generateRemindRequest(RemindType.quote, key);
			bpmProcessService.remindByForeignKey(request);
			break;
	        default :
			throw new CRMRuntimeException("remind.forbidden");
		}
	}
	
	private RemindRequest generateRemindRequest(RemindType type, String key) {
		int email = 0;
		RemindRequest request = new RemindRequest();
		request.setReminder(RequestThreadLocal.getLoginUuapName());
		request.setNotifyType(email);
		request.setType(type);
		request.setKey(key);
		return request;
	}
}
