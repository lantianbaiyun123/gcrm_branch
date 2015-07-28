package com.baidu.gcrm.quote.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.log.dao.ModifyRecordRepository;
import com.baidu.gcrm.log.dao.ModifyTableInfoRepository;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.log.model.ModifyRecord.OperateType;
import com.baidu.gcrm.log.model.ModifyTableInfo;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QutationModifyRecord;
import com.baidu.gcrm.quote.service.IQuotationModifyRecordService;
import com.baidu.gcrm.quote.service.QuotationMainService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CodeCacheServiceImpl;

@Service
public class QuotationModifyRecordServiceImpl implements
		IQuotationModifyRecordService {
	private final static String MODIFY_RECORD_TABLE_NAME="QuotationMain";
	private final static String MODIFY_RECORD_FIELD_BUSSINESSTYPE="bussinessType";
	private final static String MODIFY_RECORD_FIELD_PLATFORM="platform";
	private final static String MODIFY_RECORD_FIELD_PRICETYPE="priceType";
	private final static String MODIFY_RECORD_FIELD_VALIDDATE="validDate";
	private final static String MODIFY_RECORD_FIELD_SITE="site";
	private final static String MODIFY_RECORD_FIELD_CPA="cpa";
	private final static String MODIFY_RECORD_FIELD_CPC="cpc";
	private final static String MODIFY_RECORD_FIELD_CPT="cpt";
	private final static String MODIFY_RECORD_FIELD_CPM="cpm";
	private final static String MODIFY_RECORD_FIELD_CPS="cps";
	private final static String MODIFY_RECORD_FIELD_RATIOMINE="ratioMine";
	private final static String MODIFY_RECORD_FIELD_RATIOCUS="ratioCustomer";
	private final static String MODIFY_RECORD_FIELD_RATIO3TH="ratioThird";
	private final static String MODIFY_RECORD_FIELD_RATIOREBATE="ratioRebate";
	
	@Autowired
	private ModifyRecordRepository modifyRecordRepository;
	
	@Resource(name="billingModelServiceImpl")
	private AbstractValuelistCacheService<BillingModel> billingModelService;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ModifyTableInfoRepository modifyTableInfoRepository;
	
	@Autowired
	private CodeCacheServiceImpl codeServiceImpl;
	
	@Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
	
	@Autowired
	private ISiteService siteService;
	
	@Autowired
	private QuotationMainService quotationMainService;
	
	@Autowired
	private AbstractValuelistCacheService<AgentRegional> agentRegionalService;
	
	@Autowired
	IUserService userService;
	
	/**
	* 功能描述：  保存Main的修改记录 
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午5:36:00   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午5:36:00   
	* 修改备注：   
	* 参数： 
	* @version
	 */
	public boolean saveQuotationMainModifyRecord(QuotationMain oldObject,QuotationMain newObject){
		boolean result =  false;
		List<ModifyRecord> recordList = new ArrayList<ModifyRecord>();
		if(oldObject != null && newObject != null 
				&& newObject.getId() !=null
				&& newObject.getId().equals(oldObject.getId())){
			if(!newObject.getBusinessType().equals(oldObject.getBusinessType())){
				ModifyRecord record = getNewModifyRecord(newObject.getId());
				record.setOperateType(OperateType.modify);
				record.setModifyField(MODIFY_RECORD_FIELD_BUSSINESSTYPE);
				record.setOldValue(String.valueOf(oldObject.getBusinessType().ordinal()));
				record.setNewValue(String.valueOf(newObject.getBusinessType().ordinal()));
				recordList.add(record);
			}else{
				if(!newObject.getAdvertisingPlatformId().equals(oldObject.getAdvertisingPlatformId())){
					ModifyRecord record = getNewModifyRecord(newObject.getId());
					record.setOperateType(OperateType.modify);
					record.setModifyField(MODIFY_RECORD_FIELD_PLATFORM);
					record.setOldValue(String.valueOf(oldObject.getAdvertisingPlatformId()));
					record.setNewValue(String.valueOf(newObject.getAdvertisingPlatformId()));
					recordList.add(record);
				}else{
					if(!newObject.getPriceType().equals(oldObject.getPriceType())){
						ModifyRecord record = getNewModifyRecord(newObject.getId());
						record.setOperateType(OperateType.modify);
						record.setModifyField(MODIFY_RECORD_FIELD_PRICETYPE);
						record.setOldValue(String.valueOf(oldObject.getPriceType().ordinal()));
						record.setNewValue(String.valueOf(newObject.getPriceType().ordinal()));
						recordList.add(record);
					}else{
						if(!oldObject.getSiteId().equals(newObject.getSiteId())){
							ModifyRecord record = getNewModifyRecord(newObject.getId());
							record.setOperateType(OperateType.modify);
							record.setModifyField(MODIFY_RECORD_FIELD_SITE);
							record.setOldValue(oldObject.getSiteId().toString());
							record.setNewValue(newObject.getSiteId().toString());
							recordList.add(record);
						}else{
							if(DateUtils.compareDate(newObject.getStartTime(), oldObject.getStartTime(), DateUtils.YYYY_MM_DD)!=0 ||
									DateUtils.compareDate(newObject.getEndTime(), oldObject.getEndTime(), DateUtils.YYYY_MM_DD)!=0){
								ModifyRecord record = getNewModifyRecord(newObject.getId());
								record.setOperateType(OperateType.modify);
								record.setModifyField(MODIFY_RECORD_FIELD_VALIDDATE);
								record.setOldValue(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,oldObject.getStartTime())
										+ "~" + DateUtils.getDate2String(DateUtils.YYYY_MM_DD,oldObject.getEndTime()));
								record.setNewValue(DateUtils.getDate2String(DateUtils.YYYY_MM_DD,newObject.getStartTime())
										+ "~" + DateUtils.getDate2String(DateUtils.YYYY_MM_DD,newObject.getEndTime()));
								recordList.add(record);
							}
							result =  true;//如果仅修改时间
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(recordList)){
			modifyRecordRepository.save(recordList);
		}
		return result;
	}
	
	private ModifyRecord getNewModifyRecord(Long mainId){
		ModifyRecord record = new ModifyRecord();
		record.setModifiedDataId(mainId);
		record.setTableName(MODIFY_RECORD_TABLE_NAME);
		record.setCreateOperator(RequestThreadLocal.getLoginUserId());
		record.setCreateTime(new Date());
		record.setUpdateOperator(RequestThreadLocal.getLoginUserId());
		record.setUpdateTime(new Date());
		return record;
	}

	/**
	* 功能描述：   保存子表修改记录
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午5:38:04   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午5:38:04   
	* 修改备注：   
	* 参数： @param oldObject
	* 参数： @param newObject
	* @version
	 */
    public void saveQuotationModifyRecord(List<Quotation> oldList,List<Quotation> newList,Long mainId){
    	List<ModifyRecord> recordList = new ArrayList<ModifyRecord>();
    	Map<Long,Quotation> oldMap = new HashMap<Long, Quotation>();
    	for(int i = 0; i < oldList.size(); i++){
    		Quotation old = new Quotation();
    		old = oldList.get(i).clone();
    		oldMap.put(old.getId(), old);
    	}
    	
    	List<BillingModel> billmodelList = billingModelService.getAll();
		Map<String,Long> billModeMap = new HashMap<String, Long>();
		for(BillingModel model : billmodelList){
			billModeMap.put(model.getName().toLowerCase(), model.getId());
		}
    	
    	List<Quotation> addList = new ArrayList<Quotation>();
    	
    	for( int i = 0; i < newList.size(); i++){
    		Quotation newObject = newList.get(i);
    		if(oldMap.containsKey(newObject.getId())){//
    			Quotation oldObject = oldMap.get(newObject.getId()).clone();
    			if(oldObject != null && PriceType.ratio.equals(oldObject.getPriceType())){//分成
    				if(oldObject.getRatioMine() != null && newObject.getRatioMine() != null &&
    						oldObject.getRatioMine().compareTo(newObject.getRatioMine()) != 0){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					record.setModifyField(MODIFY_RECORD_FIELD_RATIOMINE);
    					record.setOldValue(String.valueOf(oldObject.getRatioMine().doubleValue()));
    					record.setNewValue(String.valueOf(newObject.getRatioMine().doubleValue()));
    					recordList.add(record);
    				}
    				
    				if(oldObject.getRatioCustomer() != null && newObject.getRatioCustomer() != null &&
    						oldObject.getRatioCustomer().compareTo(newObject.getRatioCustomer()) != 0){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					record.setModifyField(MODIFY_RECORD_FIELD_RATIOCUS);
    					record.setOldValue(String.valueOf(oldObject.getRatioCustomer().doubleValue()));
    					record.setNewValue(String.valueOf(newObject.getRatioCustomer().doubleValue()));
    					recordList.add(record);
    				}
    				
    				if(oldObject.getRatioThird() != null && newObject.getRatioThird() != null &&
    						oldObject.getRatioThird().compareTo(newObject.getRatioThird()) != 0){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					record.setModifyField(MODIFY_RECORD_FIELD_RATIO3TH);
    					record.setOldValue(String.valueOf(oldObject.getRatioThird().doubleValue()));
    					record.setNewValue(String.valueOf(newObject.getRatioThird().doubleValue()));
    					recordList.add(record);
    				}
    				
    				oldMap.remove(newObject.getId());
    				continue;
    			}
    			
    			if(oldObject != null && PriceType.rebate.equals(oldObject.getPriceType())){//返点
    				if(oldObject.getRatioRebate() != null && newObject.getRatioRebate() != null &&
    						oldObject.getRatioRebate().compareTo(newObject.getRatioRebate()) != 0){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					record.setModifyField(MODIFY_RECORD_FIELD_RATIOREBATE);
    					record.setOldValue(String.valueOf(oldObject.getRatioRebate().doubleValue()));
    					record.setNewValue(String.valueOf(newObject.getRatioRebate().doubleValue()));
    					recordList.add(record);
    				}
    				oldMap.remove(newObject.getId());
    				continue;
    			}
    			
    			if(oldObject != null && PriceType.unit.equals(oldObject.getPriceType())){//单价
    				if(billModeMap.get("cps").equals(oldObject.getBillingModelId())){
    					if(oldObject.getRatioMine() != null && newObject.getRatioMine() != null &&
        						oldObject.getRatioMine().compareTo(newObject.getRatioMine()) != 0 ){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					
						record.setModifyField(MODIFY_RECORD_FIELD_CPS + "." + oldObject.getIndustryId());
						record.setNewValue(String.valueOf(newObject.getRatioMine().doubleValue()));
						record.setOldValue(String.valueOf(oldObject.getRatioMine().doubleValue()));
						
						if(newObject.getId() == null){//如果没有id，认为是新增的
        					addList.add(newObject);
        	    			continue;
        	    	    }else{
        	    			oldMap.remove(newObject.getId());
					    }
						recordList.add(record);
    					}
					}else if(oldObject.getPublishPrice() != null && newObject.getPublishPrice() != null &&
    						oldObject.getPublishPrice().compareTo(newObject.getPublishPrice()) != 0 ){
    					ModifyRecord record = getNewModifyRecord(newObject.getQuotationMainId());
    					record.setOperateType(OperateType.modify);
    					if(billModeMap.get("cpa").equals(oldObject.getBillingModelId())){
    						record.setModifyField(MODIFY_RECORD_FIELD_CPA);
    					}
    					
    					if(billModeMap.get("cpc").equals(oldObject.getBillingModelId())){
    						record.setModifyField(MODIFY_RECORD_FIELD_CPC);
    					}
    					
    					if(billModeMap.get("cpt").equals(oldObject.getBillingModelId())){
    						record.setModifyField(MODIFY_RECORD_FIELD_CPT);
    					}
    					
    					if(billModeMap.get("cpm").equals(oldObject.getBillingModelId())){
    						record.setModifyField(MODIFY_RECORD_FIELD_CPM);
    					}
    					
    					record.setOldValue(String.valueOf(oldObject.getPublishPrice().doubleValue()));
        				record.setNewValue(String.valueOf(newObject.getPublishPrice().doubleValue()));
        					
        				if(newObject.getId() == null){//如果没有id，认为是新增的
            					addList.add(newObject);
            	    			continue;
            	    	}else{
            	    			oldMap.remove(newObject.getId());
    					}
    					
    					recordList.add(record);
    				}
    			}
    			oldMap.remove(newObject.getId());
    		}else{
    			addList.add(newObject);
    		}
    	}
    	
    	if(CollectionUtils.isNotEmpty(addList)){
			for(Quotation add : addList){
				ModifyRecord record = getNewModifyRecord(add.getQuotationMainId());
				record.setOperateType(OperateType.insert);
				if(add != null && PriceType.unit.equals(add.getPriceType())){//单价
					if(billModeMap.get("cps").equals(add.getBillingModelId())){
						record.setModifyField(MODIFY_RECORD_FIELD_CPS + "." +  add.getIndustryId());
						record.setNewValue(String.valueOf(add.getRatioMine().doubleValue()));
					}else{
						if(billModeMap.get("cpa").equals(add.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPA);
						}
						
						if(billModeMap.get("cpc").equals(add.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPC);
						}
						
						if(billModeMap.get("cpt").equals(add.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPT);
						}
						
						if(billModeMap.get("cpm").equals(add.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPM);
						}
						record.setNewValue(String.valueOf(add.getPublishPrice().doubleValue()));
					}
				}
				
				if(add != null && PriceType.rebate.equals(add.getPriceType())){//返点
					record.setModifyField(MODIFY_RECORD_FIELD_RATIOREBATE);
					record.setNewValue(String.valueOf(add.getRatioRebate()
							.doubleValue()));
				}
				
				if(add != null && PriceType.ratio.equals(add.getPriceType())){//分成
					record.setModifyField(MODIFY_RECORD_FIELD_RATIOREBATE);
					record.setNewValue(String.valueOf(add.getRatioMine()
							.doubleValue()));
				}
				
				recordList.add(record);
			}
		}
    	
		if (!oldMap.isEmpty()) {
			for (Long key : oldMap.keySet()) {
				Quotation delete = oldMap.get(key);
				ModifyRecord record = getNewModifyRecord(delete
						.getQuotationMainId());
				if(delete != null && PriceType.unit.equals(delete.getPriceType())){//单价
					if(billModeMap.get("cps").equals(delete.getBillingModelId())){
						record.setModifyField(MODIFY_RECORD_FIELD_CPS + "." +  delete.getIndustryId());
						record.setOldValue(String.valueOf(delete.getRatioMine()
								.doubleValue()));
					}else{
						if(billModeMap.get("cpa").equals(delete.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPA);
						}
						
						if(billModeMap.get("cpc").equals(delete.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPC);
						}
						
						if(billModeMap.get("cpt").equals(delete.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPT);
						}
						
						if(billModeMap.get("cpm").equals(delete.getBillingModelId())){
							record.setModifyField(MODIFY_RECORD_FIELD_CPM);
						}
						record.setOldValue(String.valueOf(delete.getPublishPrice()
								.doubleValue()));
					}
				}
				if(delete != null && PriceType.rebate.equals(delete.getPriceType())){//返点
					record.setModifyField(MODIFY_RECORD_FIELD_RATIOREBATE);
					record.setOldValue(String.valueOf(delete.getRatioRebate()
							.doubleValue()));
				}
				
				if(delete != null && PriceType.ratio.equals(delete.getPriceType())){//分成
					record.setModifyField(MODIFY_RECORD_FIELD_RATIOREBATE);
					record.setOldValue(String.valueOf(delete.getRatioMine()
							.doubleValue()));
				}
				
				record.setOperateType(OperateType.delete);
				recordList.add(record);
			}
		}
    	
    	if(CollectionUtils.isNotEmpty(recordList)){
			modifyRecordRepository.save(recordList);
		}
	}
    
    public List<QutationModifyRecord> findQuotationModifyRecord(Long quotationMainId,LocaleConstants locale){
    	QuotationMain main = quotationMainService.findById(quotationMainId);
    	
        List<ModifyRecord> list =modifyRecordRepository.findByTableNameAndModifiedDataIdOrderByCreateTimeDesc(MODIFY_RECORD_TABLE_NAME, quotationMainId);
        List<QutationModifyRecord> result =  new ArrayList<QutationModifyRecord>();
        for(ModifyRecord record : list){
        	QutationModifyRecord res = convertQuotationRecord(record,locale,main.getBusinessType());
        	result.add(res);
        }
        return result;
    }
    
    private QutationModifyRecord convertQuotationRecord(ModifyRecord record,LocaleConstants locale,BusinessType businessType){
    	QutationModifyRecord res = new QutationModifyRecord();
    	res.setId(record.getId());
    	res.setCreateOperator(record.getCreateOperator());
    	res.setModifiedDataId(record.getModifiedDataId());
    	res.setModifyField(record.getModifyField());
    	res.setNewValue(record.getNewValue());
    	res.setOldValue(record.getOldValue());
    	res.setOperateType(record.getOperateType());
    	res.setTableName(record.getTableName());
    	res.setCreateTime(record.getCreateTime());
    	if(res.getCreateOperator() != null){
    		Account acc = userService.getAccountByUcId(res.getCreateOperator());
    		if(acc != null){
    			res.setOperatorName(acc.getName());
    		}
    	}
    	
    	List<Code> priceTypeAll = codeServiceImpl.getAllByCodeTypeAndLoacle("quotationMain.priceType",locale.name());
    	Map<String,Code> priceTypeMap = new HashMap<String, Code>();
		if(CollectionUtils.isNotEmpty(priceTypeAll)){
			for (Code code : priceTypeAll) {
				priceTypeMap.put(code.getCodeValue(), code);
			}
		}
		
		List<Code> businessTypeAll = codeServiceImpl.getAllByCodeTypeAndLoacle("quotationMain.businessType",locale.name());
    	Map<String,Code> businessTypeMap = new HashMap<String, Code>();
		if(CollectionUtils.isNotEmpty(businessTypeAll)){
			for (Code code : businessTypeAll) {
				businessTypeMap.put(code.getCodeValue(), code);
			}
		}
		
    	if(StringUtils.isNotBlank(res.getModifyField())){
    		DecimalFormat df2 = new DecimalFormat("#.###");
    		
    		if (res.getModifyField().contains(MODIFY_RECORD_FIELD_CPS)) {//cps
				List<ModifyTableInfo> table = modifyTableInfoRepository
						.findByTableNameAndTableFieldAndLocal(
								MODIFY_RECORD_TABLE_NAME,
								MODIFY_RECORD_FIELD_CPS, locale.name());
				String field = "";
				if (table != null && table.size() == 1) {
					field = table.get(0).getFieldName();
				}
				
				String[] arr = res.getModifyField().split("\\.");
				if(arr.length == 2){
					String industryId = arr[1];
					Code code = codeServiceImpl.getCodeByTypeAndValueAndLocale("quotationMain.industry",industryId,locale.name());
					if(code != null){
						field = field + "_" + code.getI18nName();
					}
				}
				
				if(StringUtils.isNotBlank(field)){
					res.setFieldName(field);
				}
				
				if(StringUtils.isNotBlank(res.getNewValue())){
					res.setNewValue(df2.format(Double.valueOf(res.getNewValue()) * 100) + "%");
				}
				
				if(StringUtils.isNotBlank(res.getOldValue())){
					res.setOldValue(df2.format(Double.valueOf(res.getOldValue()) * 100) + "%");
				}
				
    	    }else{
    	    	List<ModifyTableInfo> table = modifyTableInfoRepository
						.findByTableNameAndTableFieldAndLocal(MODIFY_RECORD_TABLE_NAME,
								res.getModifyField(),
								 locale.name());
				if (table != null && table.size() == 1) {
					res.setFieldName(table.get(0).getFieldName());
				}
				
				if(MODIFY_RECORD_FIELD_RATIOREBATE.equals(res.getModifyField())
						|| MODIFY_RECORD_FIELD_RATIOMINE.equals(res.getModifyField())
						|| MODIFY_RECORD_FIELD_RATIOCUS.equals(res.getModifyField())
						|| MODIFY_RECORD_FIELD_RATIO3TH.equals(res.getModifyField())){//涉及到比例的类型
					if(StringUtils.isNotBlank(res.getNewValue())){
						res.setNewValue(df2.format(Double.valueOf(res.getNewValue()) * 100) + "%");
					}
					
					if(StringUtils.isNotBlank(res.getOldValue())){
						res.setOldValue(df2.format(Double.valueOf(res.getOldValue()) * 100) + "%");
					}
				}
				//业务类型
				if(MODIFY_RECORD_FIELD_BUSSINESSTYPE.equals(res.getModifyField())){
					if(StringUtils.isNotBlank(res.getNewValue())){
						res.setNewValue(businessTypeMap.get(res.getNewValue()).getI18nName());
					}
					
					if(StringUtils.isNotBlank(res.getOldValue())){
						res.setOldValue(businessTypeMap.get(res.getOldValue()).getI18nName());
					}
				}
				
				//价格种类
				if(MODIFY_RECORD_FIELD_PRICETYPE.equals(res.getModifyField())){
					if(StringUtils.isNotBlank(res.getNewValue())){
						res.setNewValue(priceTypeMap.get(res.getNewValue()).getI18nName());
					}
					
					if(StringUtils.isNotBlank(res.getOldValue())){
						res.setOldValue(priceTypeMap.get(res.getOldValue()).getI18nName());
					}
				}
				
				//平台
				if(MODIFY_RECORD_FIELD_PLATFORM.equals(res.getModifyField())){
					if(StringUtils.isNotBlank(res.getNewValue())){
						AdvertisingPlatform platform = adPlatformService.getByIdAndLocale(res.getNewValue(), locale);
						if(platform != null){
							res.setNewValue(platform.getI18nName());
						}
					}
					
					if(StringUtils.isNotBlank(res.getOldValue())){
						AdvertisingPlatform platform = adPlatformService.getByIdAndLocale(res.getOldValue(), locale);
						if(platform != null){
							res.setOldValue(platform.getI18nName());
						}
					}
				}
				
				//站点区域
				if(MODIFY_RECORD_FIELD_SITE.equals(res.getModifyField())){
					if(StringUtils.isNotBlank(res.getNewValue())){
						if(BusinessType.CASH.equals(businessType)){//变现
							Site site = siteService.findSiteAndI18nById(Long.valueOf(res.getNewValue()), locale);
							if(site != null){
								res.setNewValue(site.getI18nName());
							}
						}
						
                        if(BusinessType.SALE.equals(businessType)){//销售
                            AgentRegional agent = agentRegionalService.getByIdAndLocale(res.getNewValue(), locale);
                        	if(agent != null){
                        		res.setNewValue(agent.getI18nName());
							}
						}
						
					}
					
					if(StringUtils.isNotBlank(res.getOldValue())){
						if(BusinessType.CASH.equals(businessType)){//变现
							Site site = siteService.findSiteAndI18nById(Long.valueOf(res.getOldValue()), locale);
							if(site != null){
								res.setOldValue(site.getI18nName());
							}
						}
						
                        if(BusinessType.SALE.equals(businessType)){//销售
                        	AgentRegional agent = agentRegionalService.getByIdAndLocale(res.getOldValue(), locale);
                        	if(agent != null){
                        		res.setOldValue(agent.getI18nName());
							}
                        	
						}
					}
				}
    	    }
    	}
    	return res;
    }
}
