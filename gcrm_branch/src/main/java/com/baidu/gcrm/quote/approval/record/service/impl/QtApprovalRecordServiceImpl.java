package com.baidu.gcrm.quote.approval.record.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.CompleteQuoteActivityReq;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.QuoteCompleteContent;
import com.baidu.gcrm.mail.QuoteFinishContent;
import com.baidu.gcrm.quote.approval.record.dao.IQtApprovalRecordRepository;
import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;
import com.baidu.gcrm.quote.approval.record.service.IQtApprovalRecordService;
import com.baidu.gcrm.quote.approval.record.service.IQtProcessEndUpWordService;
import com.baidu.gcrm.quote.approval.record.web.vo.QtprovalRecordVO;
import com.baidu.gcrm.quote.dao.QuotationMainRepository;
import com.baidu.gcrm.quote.material.dao.QuotationMaterialRepository;
import com.baidu.gcrm.quote.material.model.QuotationMaterial;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.quote.service.impl.QuotationMainServiceImpl;
import com.baidu.gcrm.quote.web.vo.PriceTypeCps;
import com.baidu.gcrm.quote.web.vo.QuotationAddVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gwfp.ws.exception.GWFPException;

@Service
public class QtApprovalRecordServiceImpl implements IQtApprovalRecordService{
	Logger logger = LoggerFactory.getLogger(QuotationMainServiceImpl.class);
	
    @Autowired
    IAdSolutionContentRepository contentRepository;   
    @Autowired
    IQtApprovalRecordRepository qtApprovalRecordRepository;
    @Autowired
    IBpmProcessService processService;   
    @Autowired
    IUserService userService;   
    @Autowired
    @Qualifier("quoteProcessServiceImpl")
    IBpmProcessStartService quoteProcessService;
    @Autowired
    QuotationMainRepository quotationMainRepository;
    @Autowired
    QuotationMainService quotationMainService;
    @Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
	@Autowired
	private AbstractValuelistCacheService<AgentRegional> agentRegionalService;
	@Autowired
	private QuotationMaterialRepository quotationMaterialRepository;
    @Autowired
	private I18nKVService i18nKVService;
    @Autowired
   	private IAccountService accountService;
    @Autowired
    IQtProcessEndUpWordService qtProcessEndUpWordService;
    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;
    @Override
    public void saveApprovalRecordVO(QtprovalRecordVO vo) {
    	QtApprovalRecord approvalRecord = vo.getRecord();
        approvalRecord.setActivityId(vo.getActDefId());
        qtApprovalRecordRepository.save(approvalRecord);

    }
    
    @Override
    public List<QtApprovalRecord> findByQuoteMainId(Long id, String processDefId,
            LocaleConstants currentLocale) {
        List<QtApprovalRecord> approvalRecordList = qtApprovalRecordRepository.findByQuoteMainId(id);
        if (currentLocale != null && !CollectionUtils.isEmpty(approvalRecordList)) {
            processTaskName(processDefId, approvalRecordList, currentLocale);
        }
        return approvalRecordList;
    }
    
    private void processTaskName(String processDefId, List<QtApprovalRecord> approvalRecordList, LocaleConstants locale) {
        List<String> activityIds = new ArrayList<String> ();
        for (QtApprovalRecord temApprovalRecord : approvalRecordList) {
            String temActivityId = temApprovalRecord.getActivityId();
            if (!PatternUtil.isBlank(temActivityId)) {
                activityIds.add(temActivityId);
            }
        }
        if (CollectionUtils.isEmpty(activityIds)) {
            return;
        }        
        //query task name
        List<ActivityNameI18n> activityNameI18nList = processService.findActivityNameI18n(processDefId,
                activityIds, locale);
        Map<String,String> activityNameMap = new HashMap<String,String> ();
        for (ActivityNameI18n temActivityNameI18n : activityNameI18nList) {
            String activityId = temActivityNameI18n.getActivityId();
            if (activityNameMap.get(activityId) == null) {
                activityNameMap.put(activityId, temActivityNameI18n.getActivityName());
            }
        }        
        for (QtApprovalRecord temApprovalRecord : approvalRecordList) {
            String temActivityId = temApprovalRecord.getActivityId();
            temApprovalRecord.setTaskName(activityNameMap.get(temActivityId));
        }
    }
	
	@Override
	public QtApprovalRecord findByTaskId(String taskId){
		return qtApprovalRecordRepository.findByTaskId(taskId);
	}
	
	@Override
	public void saveAndCompleteApproval(QtprovalRecordVO approvalRecordVO,Long id,LocaleConstants currentLocale) {
		saveApprovalRecordVO(approvalRecordVO);
		boolean refused = false;
		QtApprovalRecord record = approvalRecordVO.getRecord();
		Integer approvalStatus = record.getApprovalStatus();
		if (isRefused(approvalStatus)) {
			changeToRefuseStatus(record);
			refused = true;
		}
		String excuteUsername = RequestThreadLocal.getLoginUsername();
		if(refused)
		{
			String processId = approvalRecordVO.getProcessId();
			try {
				processService.terminateProcess(processId, excuteUsername, record.getApprovalSuggestion());
				return;
			} catch (GWFPException e) {
				LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", processId,
						approvalRecordVO.getActivityId(), excuteUsername);
				throw new CRMRuntimeException("activity.complete.error");
			}						
		}
		CompleteQuoteActivityReq req = generateCompleteActivityReq(approvalRecordVO);		
		CompleteActivityResponse response = quoteProcessService.completeActivity(req);
		List<Activity> acts = response.getActivities();
        String info = gcrmTaskInfoService.getTaskInfo(acts,"quote.main.approval.task");
        if(info != null && !refused){
        	QuotationMain main=quotationMainRepository.findOne(approvalRecordVO.getRecord().getQuoteMainId());
        	main.setTaskInfo(info);
        	if (response.isProcessFinish())
        	{	approvalRecordVO.setFinish(1);
        		List<QuoteCompleteContent> quoteList = null;
                try {
                    quoteList = quotationMainService.processQuotationAfterApproved(main.getId(), currentLocale, id);
                } catch (CRMBaseException e1) {
                    throw new CRMRuntimeException("activity.complete.error");
                }
        		main.setApproveStatus(QuotationApproveStatus.APPROVED);
        		if(quoteList.size()>0)
        		{
        			for(QuoteCompleteContent content:quoteList)
        			{   try{
        				MailHelper.sendQuoteClashMail(content);
        				}
        				catch (Exception e) {
        				logger.error("邮件发送失败"+e.getMessage());
						}
        			}
        		}       			        		
        	}
        	quotationMainRepository.save(main);
        	
        }
	}

	private boolean isRefused(Integer approvalStatus) {
		return approvalStatus == 0;
	}

	private void changeToRefuseStatus(QtApprovalRecord record) {
		Long quoteMainId = record.getQuoteMainId();
		// update ad solution to refuse status
		//查看标杆价详情
		QuotationMain main=quotationMainRepository.findOne(record.getQuoteMainId());
		//审批驳回
		main.setApproveStatus(QuotationApproveStatus.CANCEL); 
    	User user = userService.findByUcid(record.getCreateOperator());
    	String userName;
    	if(user!=null){
    	    userName=user.getRealname();
    	}else{
    	    userName=record.getCreateOperator().toString();
    	}

    	//保存标杆价对应的taskinfo
		List<String>otherInfos = new ArrayList<String>();
		otherInfos.add(record.getApprovalSuggestion());
	//	sb = gcrmTaskInfoService.getTaskInfo(userName, "quote.main.approval.refuse", otherInfos);
		main.setTaskInfo(gcrmTaskInfoService.getTaskInfo(userName, "quote.main.approval.refuse", otherInfos));
		quotationMainRepository.save(main);	
	}
	
	private CompleteQuoteActivityReq generateCompleteActivityReq(QtprovalRecordVO approvalRecordVO) {
		CompleteQuoteActivityReq req = new CompleteQuoteActivityReq();
    	req.setActivityId(approvalRecordVO.getActivityId());
    	req.setProcessId(approvalRecordVO.getProcessId());
    	req.setPerformer(RequestThreadLocal.getLoginUser().getUuapName());
    	QtApprovalRecord record = approvalRecordVO.getRecord();
    	req.setApproved(record.getApprovalStatus());
    	req.setReason(record.getApprovalSuggestion());
		return req;
	}

    @Override
    public void saveApprovalRecord(QtApprovalRecord record) {
    	qtApprovalRecordRepository.save(record);
    }
	@Override
	public void withDrawApproval(Long id) {
		QuotationMain main = quotationMainRepository.findOne(id);
		main.setApproveStatus(QuotationApproveStatus.SAVING);
		main.setStatus(QuotationStatus.INVALID);
		Long ucid = RequestThreadLocal.getLoginUserId();
		String username = StringUtils.EMPTY;
		String realName = StringUtils.EMPTY;
		if(ucid!=null){
	    	User user = userService.findByUcid(ucid);
	    	if(user != null ){
	    		realName = user.getRealname();
				username = user.getUuapName();
	    	}
	    	main.setUpdateOperator(ucid);
	    	main.setUpdateTime(new Date());
	    	}
		main.setTaskInfo(gcrmTaskInfoService.getTaskInfo(realName, "quote.main.approval.cancel"));
		quotationMainRepository.save(main);	
		List<QtApprovalRecord> records=qtApprovalRecordRepository.findByQuoteMainId(main.getId());
		withdraw(records,username);
	}
	
	private void withdraw(List<QtApprovalRecord> records, String username) {
		if (CollectionUtils.isEmpty(records)) {
			throw new CRMRuntimeException("quote.not.approved");
		}
		
		String processId = records.get(records.size() - 1).getProcessId();
		processService.withdrawAndTerminateProcess(processId, username, StringUtils.EMPTY);
	}

	@Override
	public List<QtApprovalRecord> findRecordByQuoteMainId(Long quoteMainId) {
		return qtApprovalRecordRepository.findByQuoteMainId(quoteMainId);
	}

	@Override
	public void findMail(Long id, LocaleConstants currentLocale) throws IOException  {	
		QuotationMain main=quotationMainRepository.findOne(id);
		//获取classse路径
		
		 String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		 String newfile="";
		 String platFormName=adPlatformService.getByIdAndLocale(
				 main.getAdvertisingPlatformId(), currentLocale).getI18nName();
		 QuoteFinishContent content =new QuoteFinishContent();
		 content.setPlatFormName(platFormName);
		 content.setPriceMessage(main.getDescreption()); 
		 if(main.getPriceType().equals(PriceType.ratio))
		 {
			 
			 content.setPriceType(MessageHelper.getMessage("advertise.content.quotation.priceType.ratio", currentLocale)); 
		 }
		 else if(main.getPriceType().equals(PriceType.unit))
		 {
			 content.setPriceType(MessageHelper.getMessage("advertise.content.quotation.priceType.unit", currentLocale));
		 }
		 else if(main.getPriceType().equals(PriceType.rebate))
		 {
			 content.setPriceType(MessageHelper.getMessage("advertise.content.quotation.priceType.rebate", currentLocale));
		 }
		 BusinessType bussinessType = main.getBusinessType();			
			if(BusinessType.CASH.equals(bussinessType)){//变现
				content.setSiteName(i18nKVService.getI18Info(
		 				Site.class,main.getSiteId(),currentLocale));
			}			
			if (BusinessType.SALE.equals(bussinessType)) {// 销售
				content.setSiteName(agentRegionalService.getByIdAndLocale(
						main.getSiteId(), currentLocale).getI18nName());
			}
	     List<Map> url=new ArrayList<Map>();
		 Map<String,String> materurl=new HashMap<String,String>();
		 List<QuotationMaterial> materList=quotationMaterialRepository.findByQuotationMainId(main.getId()); 
		 for(QuotationMaterial mater:materList)
	      {  materurl.put(mater.getDownloadFileName(), GcrmConfig.getConfigValueByKey("app.url")+"/quote/downloadQuoteMaterial?quoteMaterialId="+mater.getId());
	    	url.add(materurl);
	      }
	     content.setUrl(url);
	     User account =  userService.findByUcid(main.getCreateOperator());
	     Set<String> sendTo = new HashSet<String>();
	     sendTo.add(account.getEmail());
	     content.setSendTo(sendTo);
	     content.setOperator(account.getRealname());
	     content.setValidStartTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getStartTime()));	     
	     content.setValidEndTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, main.getEndTime()));
	     content.setLocale(currentLocale);
	     content.setSubmitTer(account.getRealname());
	     content.setSubmitTime(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,main.getCreateTime()));
	     
	     content.setQuoteUrl(GcrmConfig.getConfigValueByKey("app.url")+"views/main.jsp#/benchmarkPriceManagement/detail?id="+main.getId());
	    
	     StringBuffer priceType=new StringBuffer();
	     QuotationAddVO vo=quotationMainService.findluge(main.getId(), currentLocale);
	     if(main.getPriceType().equals(PriceType.unit))
	     {
	    	 StringBuffer cpses=new StringBuffer();
	    	 if(vo.getCpc()!=null){
	    		 content.setCpc(vo.getCpc().getValue()==null?"无":vo.getCpc().getValue());
	    	 }else{
	    		 content.setCpc("");	 
	    	 }
	    	 if(vo.getCpa()!=null){
	    		 content.setCpa(vo.getCpa().getValue()==null?"无":vo.getCpa().getValue());
	    	 }else{
	    		 content.setCpa("");
	    	 }
	    	 if(vo.getCpm()!=null){
	    		 content.setCpm(vo.getCpm().getValue()==null?"无":vo.getCpm().getValue());
	    	 }else{
	    		 content.setCpm("");
	    	 }
	    	 if(vo.getCpt()!=null){
	    		 content.setCpt(vo.getCpt().getValue()==null?"无":vo.getCpt().getValue());
	    	 }else{
	    		 content.setCpt("");
	    	 }
	    	 priceType.append("CPC:"+content.getCpc()+""+"CPA:"+content.getCpa()
	    			 +""+"CPM:"+content.getCpm()+""+"CPT:"+content.getCpt()+"");
	    	 if(vo.getCps()!=null&&CollectionUtils.isNotEmpty(vo.getCps()))
	    	 {
	    		 for(int i=0;i<vo.getCps().size();i++)
	      		 {		PriceTypeCps cps=vo.getCps().get(i);
	      		        if(cps.getIndustryName()==null){
	      		        	cps.setIndustryName("统一分成比例");
	      		        }
	      		 		cpses.append(cps.getIndustryName()+":"+cps.getValue()+"%  ");
	      		 		
	    		 }
	    		 content.setCps1(cpses.toString());
	    		 priceType.append(content.getCps1());
	    		 }
	    	 }
	     else if(main.getPriceType().equals(PriceType.ratio)) {
	    	 content.setRatioCustomer(vo.getRatioCustomer());
	    	 content.setRatioMine(vo.getRatioMine());
	    	 content.setRatioThird(vo.getRatioThird()==null?"0":vo.getRatioThird());
	    	 
	    	 priceType.append("我方:"+content.getRatioMine()+"%"+"  客户方："+content.getRatioCustomer()+"%"+"   第三方："+content.getRatioThird()+"%"); 
	     }
	     else if(main.getPriceType().equals(PriceType.rebate)){
	    	 content.setRatioRebate(vo.getRatioRebate());
	    	 priceType.append("返点："+content.getRatioRebate()+"%");
	     }
	     
	     String processDefineId = GcrmConfig.getConfigValueByKey("quote.process.defineId");
	     List<QtApprovalRecord> recordList=findByQuoteMainId(main.getId(),processDefineId,currentLocale);
	     StringBuffer record=new StringBuffer();
	     if(recordList!=null)
	     {  
	    	 String isagree="";
	    	 for(QtApprovalRecord records:recordList)
	    	 {
	    		 if(records.getApprovalStatus()==null&&records.getActivityId().equals("act1"))
	    		 {
	    			 isagree="发起流程";
	    		 }
	    		 else if(records.getApprovalStatus()==1)
	    		 {
	    			 isagree="审批通过";
	    		 }
	    		 else if(records.getApprovalStatus()==0)
	    		 {
	    			 isagree="审批驳回";
	    		 }
	    		 record.append("节点："+records.getTaskName()
	    				 +"   操作人："+userService.getAccountByUcId(records.getCreateOperator()).getName()
	    				 +"   审批状态："+isagree+"   意见："+(records.getApprovalSuggestion()==null?"无审批意见":records.getApprovalSuggestion())+"") ;
	    	 }
	    	 content.setRecordList(record.toString());
	    	
	     }
	     try {
	    	  if (System.getProperty("os.name").contains("Windows")) {
	    		  path=path.substring(1, path.length());
    		  }	    	
	    	 newfile="标杆价"+main.getId()+".doc";
	    	 String  source =path+GcrmConfig.getConfigValueByKey("doc.url");
	    	 byte[] bytes=qtProcessEndUpWordService.poiWordTableReplace(source, content,priceType);
	    	 ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
	    	 if(inStream!=null){
	    	 content.setInstream(inStream);
	    	 content.setwordName(newfile);
	        
	    	 }
	     
	     } catch (Exception e) {
			
	    	 logger.error("附件添加失败"+e.getMessage());
		}
        
	     	MailHelper.sendQuoteFinishMail(content);
	}


}
