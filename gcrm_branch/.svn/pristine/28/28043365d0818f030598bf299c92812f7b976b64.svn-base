package com.baidu.gcrm.personalpage.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.material.vo.MaterialApplyContentVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.ad.web.utils.AdvertiseSolutionCondition;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionListView;
import com.baidu.gcrm.bpm.dao.IActivityNameI18nRepository;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.TaskInfoModel;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.CustomerApproveState;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.personalpage.dao.IOperateReportRepository;
import com.baidu.gcrm.personalpage.model.OperateReport;
import com.baidu.gcrm.personalpage.service.IPersonalPageService;
import com.baidu.gcrm.personalpage.web.vo.AdSolutionOperationVO;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.personalpage.web.vo.OperateReportVO;
import com.baidu.gcrm.personalpage.web.vo.OperationImageVO;
import com.baidu.gcrm.personalpage.web.vo.OperationListVO;
import com.baidu.gcrm.personalpage.web.vo.PositionOperationVO;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.quote.web.utils.AdvertisingPlatformComparator;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gson.JsonParseException;
import com.baidu.gson.JsonParser;
import com.baidu.tianlu.common.utils.JsonUtils;

@Service
public class PersonalPageServiceImpl implements IPersonalPageService {

	@Autowired
	private QuotationMainService quotationMainService;
	
	@Autowired
	private IAdvertiseSolutionService adService;
	
	@Autowired
	private IAdSolutionContentService adSolutionContentService;
	
    @Autowired
    private IPositionService positionService;
	
	@Autowired
	private IUserDataRightService userDataRightService;
	
	@Autowired
	private ISiteService siteService;
	
	@Autowired
	private IOperateReportRepository operateReportDao;
	
	@Autowired
	private IContractService contractService;
	
    @Autowired
    private ICustomerService customerService;
	
    @Autowired
    IActivityNameI18nRepository activityNameI18nRepository;
	/**
	 * 折线图的点个数  -4 到0  5个点
	 */
	private static final int IMAGE_COUNT = -9;
	
	private static final String QUOTATIONMAIN = "QuotationMain";
	private static final String CUSTOMER = "Customer";
	private static final String ADSOLUTION = "AdvertiseSolution";
	private static final String POSITION = "Position";
	
	@Autowired
	IUserService userService;
	/**
	 * 查询个人一个月以来提交的标杆价
	 */
	public List<QuotationMainVO> findPersonalQuotation(LocaleConstants currentLocale){
		QuotationMainCondition condition =  new QuotationMainCondition();
		condition.setPageSize(Integer.MAX_VALUE);
		condition.setCreateStartDate(DateUtils.getLastNMonthEndDate(-1));
		condition.setCreateOperator(RequestThreadLocal.getLoginUserId().toString());
		List<QuotationMainVO> quotationMainList = quotationMainService.findQuotationMainVOList(condition, currentLocale);
		processQuotationTaskInfor(quotationMainList,currentLocale);
		return quotationMainList;
	}
	
	private void processQuotationTaskInfor(List<QuotationMainVO> result,LocaleConstants currentLocale) {
		if (CollectionUtils.isNotEmpty(result)) {
			for (QuotationMainVO bean : result) {
				String taskInfor = bean.getQuotationMain().getTaskInfo();
				if (StringUtils.isNotBlank(taskInfor)) {
					bean.setTaskInfoMessage(getActivityNamesByTaskInfo(RemindType.quote.getProcessDefineId(), currentLocale, taskInfor));
				}
			}
		}
	}
	
	/**
	* 功能描述：   查询近一个月来提交的广告方案
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午5:43:10   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午5:43:10   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<AdvertiseSolutionListView> findAdSolution(LocaleConstants locale){
		AdvertiseSolutionCondition adSolutionCondition = new AdvertiseSolutionCondition();
		adSolutionCondition.setPageSize(Integer.MAX_VALUE);
		adSolutionCondition.setCreateStartDate(DateUtils.getLastNMonthEndDate(-1));
		adSolutionCondition.setCreateOperator(RequestThreadLocal.getLoginUserId());
		List<AdvertiseSolutionApproveState> solutionStatusList = new ArrayList<AdvertiseSolutionApproveState>();
		solutionStatusList.add(AdvertiseSolutionApproveState.approved);
		solutionStatusList.add(AdvertiseSolutionApproveState.approving);
		solutionStatusList.add(AdvertiseSolutionApproveState.effective);
		solutionStatusList.add(AdvertiseSolutionApproveState.refused);
		adSolutionCondition.setSolutionStatusList(solutionStatusList);
		
		Page<AdvertiseSolutionListView> page = adService.findAdSolutionPage(adSolutionCondition);
		List<AdvertiseSolutionListView> res = page.getResult();
		for(AdvertiseSolutionListView view :  res){
			if(AdvertiseSolutionApproveState.approving.name().equals(view.getApproval_status())){
				view.setTaskInfoMessage(getActivityNamesByTaskInfo(RemindType.advertise.getProcessDefineId(), locale, view.getTaskInfo()));
			}
		}
		return res;
	}
	/**
	* 功能描述：   查询近一个月来提交的物料单
	* 创建人：yudajun    
	* 创建时间：2014-5-16 下午8:24:06   
	* 修改人：yudajun
	* 修改时间：2014-5-16 下午8:24:06   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<MaterialApplyContentVO> findMaterialApplyContent(LocaleConstants locale){
		AdvertiseSolutionCondition adSolutionCondition = new AdvertiseSolutionCondition();
		adSolutionCondition.setCreateStartDate(DateUtils.getLastNMonthEndDate(-1));
		adSolutionCondition.setCreateOperator(RequestThreadLocal.getLoginUserId());
		List<MaterialApplyState> materialApplyStateList = new ArrayList<MaterialApplyState>();
		materialApplyStateList.add(MaterialApplyState.submit);
		materialApplyStateList.add(MaterialApplyState.confirm);
		materialApplyStateList.add(MaterialApplyState.refused);
		adSolutionCondition.setMaterialApplyStateList(materialApplyStateList);
		
		List<MaterialApplyContentVO> res = adSolutionContentService.findMaterialApplyContent(adSolutionCondition);
		for(MaterialApplyContentVO vo : res){
			vo.setTaskInfor(getActivityNamesByTaskInfo(RemindType.material.getProcessDefineId(), locale,vo.getTaskInfor()));
		}
		return res;
	}
	
	/**
	 * 查询最近一个月的客户信息
	 */
	@Override
	public List<CustomerListBean> findCustomer(LocaleConstants locale) {
		CustomerCondition condition = new CustomerCondition();
		condition.setPageSize(Integer.MAX_VALUE);
		condition.setCreateStartDate(DateUtils.getLastNMonthEndDate(-1));
		condition.setCreateOperator(RequestThreadLocal.getLoginUserId());
		List<Integer> approvalStatusList = new ArrayList<Integer>();
		approvalStatusList.add(CustomerApproveState.approving.ordinal());
		approvalStatusList.add(CustomerApproveState.approved.ordinal());
		approvalStatusList.add(CustomerApproveState.refused.ordinal());
		condition.setApprovalStatusList(approvalStatusList);
		
		Page<CustomerListBean> resultPage = customerService.findByCondition(condition, locale);
		List<CustomerListBean> result = resultPage.getContent();
		processCustomerTaskInfor(result,locale);
		return result;
	}
	
	/**
	* 功能描述：   方案及合同运营情况
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午3:41:59   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午3:41:59   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public AdSolutionOperationVO findSolutionOperation(){
		AdSolutionOperationVO vo = new AdSolutionOperationVO();
		vo.setOperationList(getAdOperationList());
		vo.setOperationImage(getAdOperationImage());
		return vo;
	}
	
	private List<OperationListVO> getAdOperationList() {
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<OperationListVO> operationList = new ArrayList<OperationListVO>();
		// 上周
		Date endDate = DateUtils.getTheDayOfWeek(0, Calendar.MONDAY);
		
		if(DateUtils.getWeekDay() == Calendar.SUNDAY){
			endDate = DateUtils.getTheDayOfWeek(-1, Calendar.MONDAY);
		}
		
		// 上周的方案数量
		Long lastWeek = adService.findSolutionCountCreatAndApproved(null,
				endDate);
		Long lastWeekContract = contractService.findValidContractAmountBetween(null, endDate);
		// 本周
		endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		
		if(DateUtils.getWeekDay() == Calendar.SUNDAY){
			endDate = DateUtils.getTheDayOfWeek(0, Calendar.MONDAY);
		}
		
		// 本周方案数量
		Long thisWeek = adService.findSolutionCountCreatAndApproved(null,
				endDate);
		Long thisWeekContract = contractService.findValidContractAmountBetween(null, endDate);

		OperationListVO solution = new OperationListVO();
		solution.setType("solution");
		solution.setLastWeek(df2.format(lastWeek));
		solution.setThisWeek(df2.format(thisWeek));

		if (lastWeek.intValue() == 0) {
			solution.setRatio("0");
		} else {
			solution.setRatio(df2.format((thisWeek - lastWeek) * 100.00
					/ lastWeek));
		}

		OperationListVO contract = new OperationListVO();
		contract.setType("contract");
		contract.setLastWeek(df2.format(lastWeekContract));
		contract.setThisWeek(df2.format(thisWeekContract));

		if (lastWeekContract.intValue() == 0) {
			contract.setRatio("0");
		} else {
			contract.setRatio(df2.format((thisWeekContract - lastWeekContract)
					* 100.00 / lastWeekContract));
		}

		OperationListVO total = new OperationListVO();
		total.setType("total");
		total.setLastWeek(df2.format(lastWeek + lastWeekContract));
		total.setThisWeek(df2.format(thisWeek + thisWeekContract));

		if ((lastWeek + lastWeekContract) != 0) {
			Double ratio = (thisWeek + thisWeekContract - lastWeek - lastWeekContract)
					* 100.00 / (lastWeek + lastWeekContract);
			total.setRatio(df2.format(ratio));
		}

		operationList.add(solution);
		operationList.add(contract);
		operationList.add(total);
		return operationList;
	}
	
	private List<OperationImageVO> getAdOperationImage() {
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<OperationImageVO> operationImage = new ArrayList<OperationImageVO>();

		for (int i = IMAGE_COUNT; i <= 0; i++) {
			Date endDate = DateUtils.getTheDayOfWeek(i + 1, Calendar.MONDAY);
			
			Date startShowDate = DateUtils.getTheDayOfWeek(i, Calendar.MONDAY);
			Date endShowDate = DateUtils.getTheDayOfWeek(i + 1, Calendar.SUNDAY);
			
			if(DateUtils.getWeekDay() == Calendar.SUNDAY){
				endDate = DateUtils.getTheDayOfWeek(i, Calendar.MONDAY);
				
				startShowDate = DateUtils.getTheDayOfWeek(i -1, Calendar.MONDAY);
				endShowDate = DateUtils.getTheDayOfWeek(i, Calendar.SUNDAY);
			}
			
			Long solution = adService.findSolutionCountCreatAndApproved(null,
					endDate);
			Long contract = contractService.findValidContractAmountBetween(null, endDate);
			
			OperationImageVO vo = new OperationImageVO();
			
			String st = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_, startShowDate);
			String ed = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_, endShowDate);
			
			vo.setPeriod(st.substring(5, st.length())
					+ "-" + ed.substring(5, ed.length()));
			vo.setSolution(df2.format(solution));
			vo.setContract(df2.format(contract));
			operationImage.add(vo);
		}
		
		return operationImage;
	}
	/**
	 * 客户运营情况
	 */
	public AdSolutionOperationVO findCustomerOperation(){
		AdSolutionOperationVO vo = new AdSolutionOperationVO();
		vo.setOperationList(getCustomerOperationList());
		vo.setOperationImage(getCustomerOperationImage());
		return vo;
	}
	
	private List<OperationListVO> getCustomerOperationList() {
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<OperationListVO> operationList = new ArrayList<OperationListVO>();
		// 上周
		Date endDate = DateUtils.getTheDayOfWeek(0, Calendar.MONDAY);
		if(DateUtils.getWeekDay() == Calendar.SUNDAY){
			endDate = DateUtils.getTheDayOfWeek(-1, Calendar.MONDAY);
		}
		
		List<Customer> list = customerService.findCustomerApprovedBetween(null, endDate);
		
		Long lastWeekAgent = 0L;
		Long lastWeekAdvertiser = 0L;
		Long lastWeekOther = 0L;
		for(Customer obj : list){
			if(CustomerType.offline.ordinal() == obj.getCustomerType().ordinal()){//代理
				lastWeekAgent = lastWeekAgent + 1;
			}else if(CustomerType.union.ordinal() == obj.getCustomerType().ordinal()){
				lastWeekOther = lastWeekOther + 1;
			}else{
				lastWeekAdvertiser = lastWeekAdvertiser + 1;
			}
		}
		
		// 本周
		endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		if(DateUtils.getWeekDay() == Calendar.SUNDAY){
			endDate = DateUtils.getTheDayOfWeek(0, Calendar.MONDAY);
		}
		list = customerService.findCustomerApprovedBetween(null, endDate);
		
		Long thisWeekAgent = 0L;
		Long thisWeekAdvertiser = 0L;
		Long thisWeekOther = 0L;
		for(Customer obj : list){
			if(CustomerType.offline.ordinal() == obj.getCustomerType().ordinal()){//代理
				thisWeekAgent = thisWeekAgent + 1;
			}else if(CustomerType.union.ordinal() == obj.getCustomerType().ordinal()){
				thisWeekOther = thisWeekOther + 1;
			}else{
				thisWeekAdvertiser = thisWeekAdvertiser + 1;
			}
		}
		
		OperationListVO agent = new OperationListVO();
		agent.setType("agent");
		agent.setLastWeek(df2.format(lastWeekAgent));
		agent.setThisWeek(df2.format(thisWeekAgent));

		if (lastWeekAgent.intValue() == 0) {
			agent.setRatio("0");
		} else {
			agent.setRatio(df2.format((thisWeekAgent - lastWeekAgent) * 100.00
					/ lastWeekAgent));
		}

		OperationListVO advertiser = new OperationListVO();
		advertiser.setType("advertiser");
		advertiser.setLastWeek(df2.format(lastWeekAdvertiser));
		advertiser.setThisWeek(df2.format(thisWeekAdvertiser));

		if (lastWeekAgent.intValue() == 0) {
			advertiser.setRatio("0");
		} else {
			advertiser.setRatio(df2.format((thisWeekAdvertiser - lastWeekAdvertiser) * 100.00
					/ lastWeekAdvertiser));
		}
		
		OperationListVO other = new OperationListVO();
		other.setType("other");
		other.setLastWeek(df2.format(lastWeekOther));
		other.setThisWeek(df2.format(thisWeekOther));

		if (lastWeekOther.intValue() == 0) {
			other.setRatio("0");
		} else {
			other.setRatio(df2.format((thisWeekOther - lastWeekOther)
					* 100.00 / lastWeekOther));
		}

		OperationListVO total = new OperationListVO();
		total.setType("total");
		total.setLastWeek(df2.format(lastWeekAgent + lastWeekOther + lastWeekAdvertiser));
		total.setThisWeek(df2.format(thisWeekAgent + thisWeekOther + thisWeekAdvertiser));

		if ((lastWeekAgent + lastWeekOther) != 0) {
			Double ratio = (thisWeekAgent + thisWeekOther + thisWeekAdvertiser - lastWeekAgent - lastWeekOther - lastWeekAdvertiser)
					* 100.00 / (lastWeekAgent + lastWeekOther + lastWeekAdvertiser);
			total.setRatio(df2.format(ratio));
		}

		operationList.add(agent);
		operationList.add(advertiser);
		operationList.add(other);
		operationList.add(total);
		return operationList;
	}
	
	private List<OperationImageVO> getCustomerOperationImage() {
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<OperationImageVO> operationImage = new ArrayList<OperationImageVO>();

		for (int i = IMAGE_COUNT; i <= 0; i++) {
			Date endDate = DateUtils.getTheDayOfWeek(i + 1, Calendar.MONDAY);
			
			Date startShowDate = DateUtils.getTheDayOfWeek(i, Calendar.MONDAY);
			Date endShowDate = DateUtils.getTheDayOfWeek(i + 1, Calendar.SUNDAY);
			
			if(DateUtils.getWeekDay() == Calendar.SUNDAY){
				endDate = DateUtils.getTheDayOfWeek(i, Calendar.MONDAY);
				
				startShowDate = DateUtils.getTheDayOfWeek(i -1, Calendar.MONDAY);
				endShowDate = DateUtils.getTheDayOfWeek(i, Calendar.SUNDAY);
			}
			
			List<Customer> list = customerService.findCustomerApprovedBetween(null, endDate);
			
			Long agent = 0L;
			Long advertiser = 0L;
			Long other = 0L;
			for(Customer obj : list){
				if(CustomerType.offline.ordinal() == obj.getCustomerType().ordinal()){//代理
					agent = agent + 1;
				}else if(CustomerType.union.ordinal() == obj.getCustomerType().ordinal()){
					other = other + 1;
				}else{
					advertiser = advertiser + 1;
				}
			}
			
			OperationImageVO vo = new OperationImageVO();
			String st = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_, startShowDate);
			String ed = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_, endShowDate);
			
			vo.setPeriod(st.substring(5, st.length())
					+ "-" + ed.substring(5, ed.length()));
			vo.setAgent(df2.format(agent));
			vo.setOther(df2.format(other));
			vo.setAdvertiser(df2.format(advertiser));
			operationImage.add(vo);
		}
		
		return operationImage;
	}
	
	/**
	* 功能描述：   查询当前登录人拥有的平台
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午6:05:46   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午6:05:46   
	* 修改备注：   
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<AdvertisingPlatform> findPlatformByCurrUser(LocaleConstants locale) {
		List<AdvertisingPlatform> platformList = userDataRightService.getPlatformListByUcId(RequestThreadLocal.getLoginUserId(),locale);
		List<AdvertisingPlatform> result = new ArrayList<AdvertisingPlatform>();
		for(int i = 0; i < platformList.size(); i++){
			AdvertisingPlatform plat = platformList.get(i);
			if(plat.getStatus() == AdPlatformStatus.enable.ordinal()
					&& Constants.Advertising_Platform_Realized==plat.getBusinessType()){
				result.add(plat);
			}
		}
        Collections.sort(result, new AdvertisingPlatformComparator());
		return result;
	}
	
	/**
	 * 根据平台id查询站点的投放情况
	 */
	public List<PositionOperationVO> findSiteOperationByPlatformId(
			Long platformId, LocaleConstants locale) {
		DecimalFormat df2 = new DecimalFormat("#.##");
		List<Site> siteList = siteService.findSiteByAdPlatform(platformId, locale);
		List<PositionOperationVO> result = new ArrayList<PositionOperationVO>();
		for(Site site : siteList){
			PositionOperationVO sop = new PositionOperationVO();
			sop.setSiteId(site.getId());
			sop.setSiteName(site.getI18nName());
			List<ChannelOperationVO> channelList = new ArrayList<ChannelOperationVO>();
			List<PositionVO> resList = positionService.findChannelBySiteIdAndStatus(platformId, site.getId(), locale, PositionStatus.enable);
		    for(PositionVO channel : resList){
		    	ChannelOperationVO cop = new ChannelOperationVO();
		    	cop.setChannelId(channel.getId());
		    	cop.setChannelName(channel.getI18nName());
		    	
		    	List<Position> positionList = positionService.findByIndexStrLikeAndTypeAndStatus(channel.getId(),channel.getIndexStr(),PositionType.position,PositionStatus.enable);
		    	if(CollectionUtils.isEmpty(positionList)){
		    		continue;
		    	}
		    	List<Long> positionIdList = new ArrayList<Long>();
		    	for(Position position : positionList){
		    		positionIdList.add(position.getId());
		    	}
		    	Long salesAmount = Long.valueOf(positionIdList.size());
		    	Long soldAmount = positionService.findPositionOperation(positionIdList, DateUtils.getCurrentDateOfZero());
		    
		        if(salesAmount.intValue() == 0){
		        	cop.setRatio("0");
		        }else{
		        	Double ratio = soldAmount * 100.00/salesAmount;
		        	cop.setRatio(df2.format(ratio));
		        }
		        cop.setSalesAmount(salesAmount.toString());
		        cop.setSoldAmount(soldAmount.toString());
		        channelList.add(cop);
		    }
		    if(CollectionUtils.isEmpty(channelList)){
		    	continue;
		    }
		    sop.setChannelList(channelList);
		    result.add(sop);
		}
		return result;
	}
	
	public OperateReportVO findOperateReport(){
		List<OperateReport> quotationList = new ArrayList<OperateReport>();
		List<OperateReport> customerList = new ArrayList<OperateReport>();
		List<OperateReport> solutionList = new ArrayList<OperateReport>();
		List<OperateReport> positionList = new ArrayList<OperateReport>();
		
		List<OperateReport> reportList = operateReportDao.findAll();
		for(OperateReport report : reportList){
			if(QUOTATIONMAIN.equals(report.getOperateType())){
				quotationList.add(report);
				continue;
			}
			if(CUSTOMER.equals(report.getOperateType())){
				customerList.add(report);
				continue;
			}
			if(ADSOLUTION.equals(report.getOperateType())){
				solutionList.add(report);
				continue;
			}
			if(POSITION.equals(report.getOperateType())){
				positionList.add(report);
				continue;
			}
		}
		OperateReportVO vo = new OperateReportVO();
		vo.setQuotationList(quotationList);
		vo.setCustomerList(customerList);
		vo.setSolutionList(solutionList);
		vo.setPositionList(positionList);
		return vo;
	}
	
	private static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	} 
	
	private void processCustomerTaskInfor(List<CustomerListBean> result,LocaleConstants locale) {
		if (CollectionUtils.isNotEmpty(result)) {
			for (CustomerListBean bean : result) {
				bean.setTaskInfor(getActivityNamesByTaskInfo(RemindType.customer.getProcessDefineId(),locale,bean.getTaskInfor()));
			}
		}
	}
	 
	private String getActivityNamesByTaskInfo(String processDefineId, LocaleConstants locale,String taskInfo){
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(taskInfo) && isGoodJson(taskInfo)){
			try{
			 TaskInfoModel taskInfoModel = JsonUtils.jsonToObj(taskInfo, TaskInfoModel.class);
			 String users[]=taskInfoModel.getValueMap().get(1).split(";");
			 for(int i=0;i<users.length;i++){
				 if(i>0){
					 sb.append(",");
				 }
				 if(users[i].split(":").length>1){
				 sb.append(activityNameI18nRepository
							.findByProcessDefIdAndLocaleAndActivityId(GcrmConfig.getConfigValueByKey(processDefineId),locale,users[i].split(":")[0]).getActivityName());
			 }}
			}catch(Exception exp){
				 LoggerHelper.err(getClass(), exp.getMessage(), exp);
			}
			 
		}
		
		return sb.toString();
	}
	@Override
	public void analysisOperateReport() {
		//分析标杆价
		analysisQuotationMain();
		//分析客户
		analysisCustom();
		//分析广告方案
		analysisAdSolution();
		//分析资源位
		analysisPosition();
	}
	
	private void analysisQuotationMain(){
		// 本周
		Date startDate = DateUtils.getTheDayOfWeek(0, Calendar.SUNDAY);
		Date endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		List<QuotationMain> list = quotationMainService.findQuotationMainListCreateDateBetween(startDate, endDate);
		
		Long approving = 0l;
		Long approved = 0l;
		
		for(QuotationMain obj : list){
			if(QuotationApproveStatus.APPROVING.ordinal() == obj.getApproveStatus().ordinal()){
				approving = approving + 1;
				continue;
			}
			
			if(QuotationApproveStatus.APPROVED.ordinal() == obj.getApproveStatus().ordinal()){
				approved = approved + 1;
				continue;
			}
		}
		
		OperateReport aping = operateReportDao.findByOperateTypeAndReportType(QUOTATIONMAIN, "APPROVING");
		aping.setAmount(approving);
		
		OperateReport aped = operateReportDao.findByOperateTypeAndReportType(QUOTATIONMAIN, "APPROVED");
		aped.setAmount(approved);
		
		operateReportDao.saveAndFlush(aping);
		operateReportDao.saveAndFlush(aped);
	}
	
	private void analysisCustom(){
		Date startDate = DateUtils.getTheDayOfWeek(0, Calendar.SUNDAY);
		Date endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		List<Customer> list = customerService.findCustomerCreateBetween(startDate, endDate,"create");
		Long create = Long.valueOf(list.size());
		
		list = customerService.findCustomerCreateBetween(startDate, endDate,"update");
		Long update = Long.valueOf(list.size());
		
		list = customerService.findCustomerCreateBetween(startDate, endDate,"approving");
		Long approving = Long.valueOf(list.size());
		
		list = customerService.findCustomerCreateBetween(startDate, endDate,"approved");
		Long approved = Long.valueOf(list.size());
		
		OperateReport cre = operateReportDao.findByOperateTypeAndReportType(CUSTOMER, "CREATE");
		cre.setAmount(create);
		
		OperateReport upd = operateReportDao.findByOperateTypeAndReportType(CUSTOMER, "UPDATE");
		upd.setAmount(update);
		
		OperateReport aping = operateReportDao.findByOperateTypeAndReportType(CUSTOMER, "APPROVING");
		aping.setAmount(approving);
		
		OperateReport aped = operateReportDao.findByOperateTypeAndReportType(CUSTOMER, "APPROVED");
		aped.setAmount(approved);
		
		operateReportDao.saveAndFlush(cre);
		operateReportDao.saveAndFlush(upd);
		operateReportDao.saveAndFlush(aping);
		operateReportDao.saveAndFlush(aped);
	}

	private void analysisAdSolution(){
		Date startDate = DateUtils.getTheDayOfWeek(0, Calendar.SUNDAY);
		Date endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		// 本周方案数量
		List<AdvertiseSolution> list = adService.findSolutionCount(startDate,
				endDate, "create");
		Long create = Long.valueOf(list.size());
		
		list = adService.findSolutionCount(startDate, endDate, "update");
		Long update = Long.valueOf(list.size());
		
		list = adService.findSolutionCount(startDate, endDate, "approve");
		Long approving = 0l;
		Long approved = 0l;
		for(AdvertiseSolution obj : list){
			if(AdvertiseSolutionApproveState.approving.ordinal() == obj.getApprovalStatus().ordinal()){
				approving = approving + 1;
			}else if(AdvertiseSolutionApproveState.approved.ordinal() == obj.getApprovalStatus().ordinal()){
				approved = approved + 1;
			}
		}
		
		list = adService.findSolutionCount(startDate, endDate, "confirmed");
		Long confirmed = Long.valueOf(list.size());
		
		list = adService.findSolutionCount(startDate, endDate, "unconfirmed");
		Long unconfirmed = Long.valueOf(list.size());
		
		OperateReport aping = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "APPROVING");
		aping.setAmount(approving);
		
		OperateReport aped = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "APPROVED");
		aped.setAmount(approved);
		
		OperateReport cre = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "CREATE");
		cre.setAmount(create);
		
		OperateReport up = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "UPDATE");
		up.setAmount(update);
		
		OperateReport con = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "UNCONFIRMED");
		con.setAmount(confirmed);
		
		OperateReport uncon = operateReportDao.findByOperateTypeAndReportType(ADSOLUTION, "CONFIRMED");
		uncon.setAmount(unconfirmed);
		
		operateReportDao.saveAndFlush(aping);
		operateReportDao.saveAndFlush(aped);
		operateReportDao.saveAndFlush(cre);
		operateReportDao.saveAndFlush(up);
		operateReportDao.saveAndFlush(con);
		operateReportDao.saveAndFlush(uncon);
	}
	
	private void analysisPosition(){
		Date startDate = DateUtils.getTheDayOfWeek(0, Calendar.SUNDAY);
		Date endDate = DateUtils.getTheDayOfWeek(1, Calendar.MONDAY);
		List<Position> createlist = positionService.findListBetweenDate(startDate, endDate, "create", PositionType.position);
		List<Position> enabledlist = positionService.findListBetweenDate(startDate, endDate, "enabled", PositionType.position);
		
		OperateReport create = operateReportDao.findByOperateTypeAndReportType(POSITION, "CREATE");
		create.setAmount(Long.valueOf(createlist.size()));
		
		OperateReport enabled = operateReportDao.findByOperateTypeAndReportType(POSITION, "ENABLED");
		enabled.setAmount(Long.valueOf(enabledlist.size()));
		
		operateReportDao.saveAndFlush(create);
		operateReportDao.saveAndFlush(enabled);
	}
}
