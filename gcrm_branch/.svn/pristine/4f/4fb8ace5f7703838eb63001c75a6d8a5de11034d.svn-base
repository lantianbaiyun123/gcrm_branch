package com.baidu.gcrm.quote.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.page.HqlPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.web.helper.BusinessType;
import com.baidu.gcrm.quote.dao.IQuotationMainRepositoryCustom;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;
@Repository
public class QuotationMainRepositoryCustomImpl implements
		IQuotationMainRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private HqlPageQuery hqlPageQuery;
	
	@SuppressWarnings("unchecked")
	public Page<QuotationMain> findQuotationMainPage(QuotationMainCondition condition){
		Page<QuotationMain> page = new Page<QuotationMain>();
		
		StringBuilder sqlStr = new StringBuilder();
		
		sqlStr.append("select a from QuotationMain a where 1=1");
		
		StringBuilder countStr = new StringBuilder();
		countStr.append("select count(*) as count from QuotationMain a where 1=1");
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuilder conditionStr = new StringBuilder();
		
		conditionStr = processConditionForPageQuery(condition,params);
		//拼接条件
		sqlStr.append(conditionStr);
		countStr.append(conditionStr);
		
		sqlStr.append(" order by a.createTime desc");
		
		hqlPageQuery.executePageQuery(entityManager, sqlStr,countStr, params,condition);
		
		page.setResult(condition.getContent());
	//	page.setPageNo(condition.getPageNumber());
		page.setPageSize(condition.getPageSize());
		page.setResultClass(condition.getResultClass());
    	page.setTotalCount(condition.getTotal());
		return page;
	}
	
	private StringBuilder processConditionForPageQuery(QuotationMainCondition condition,Map<String,Object> params){
		StringBuilder sqlStr = new StringBuilder();
		if(StringUtils.isNotBlank(condition.getAdvertisingPlatformId())){
			sqlStr.append(" AND a.advertisingPlatformId = :advertisingPlatformId ");
			params.put("advertisingPlatformId", Long.valueOf(condition.getAdvertisingPlatformId()));
		}
		
		if(StringUtils.isNotBlank(condition.getSiteId())){
			sqlStr.append(" AND a.siteId = :siteId ");
			params.put("siteId", Long.valueOf(condition.getSiteId()));
		}
		
		if(condition.getApproveStatus() != null){
			sqlStr.append(" AND a.approveStatus = :approveStatus ");
			params.put("approveStatus", condition.getApproveStatus());
		}
		
		if(condition.getStatus() != null){
			sqlStr.append(" AND a.status = :status ");
			params.put("status", condition.getStatus());
		}
		
		if(StringUtils.isNotBlank(condition.getCreateOperator())){
			sqlStr.append(" AND a.createOperator = :createOperator ");
			params.put("createOperator", Long.valueOf(condition.getCreateOperator()));
		}
		
		if(condition.getBusinessType() != null){
			sqlStr.append(" AND a.businessType = :businessType ");
			params.put("businessType", condition.getBusinessType());
		}
		
		if(StringUtils.isNotBlank(condition.getStartDate())){
			sqlStr.append(" AND a.startTime >= :startTime ");
			params.put("startTime", DateUtils.getString2Date(DateUtils.YYYY_MM_DD, condition.getStartDate()));
		}
		if(StringUtils.isNotBlank(condition.getEndDate())){
			sqlStr.append(" AND a.startTime <= :endTime");
			params.put("endTime", DateUtils.getString2Date(DateUtils.YYYY_MM_DD, condition.getEndDate()));
		}
		
		if(StringUtils.isNotBlank(condition.getCreateStartDate())){
			sqlStr.append(" AND a.createTime > :createTime ");
			params.put("createTime", DateUtils.getString2Date(DateUtils.YYYY_MM_DD, condition.getCreateStartDate()));
		}
		
		if(CollectionUtils.isNotEmpty(condition.getOperatorIdList())){
			if(condition.getOperatorIdList().size()  == 1){
				sqlStr.append(" AND a.createOperator = :operator ");
				params.put("operator", RequestThreadLocal.getLoginUserId());
			}else if(condition.getOperatorIdList().size()  > 1){
				sqlStr.append(" AND (a.createOperator = :operator");
				sqlStr.append(" OR a.createOperator in (:operatorIdList))");
				params.put("operator", RequestThreadLocal.getLoginUserId());
				params.put("operatorIdList", condition.getOperatorIdList());
			}
		}
		
		if(CollectionUtils.isNotEmpty(condition.getPlatIdList())){
			sqlStr.append(" AND a.advertisingPlatformId in (:platIdList)");
			params.put("platIdList",condition.getPlatIdList());
		}
		
		if(CollectionUtils.isNotEmpty(condition.getSiteIdList()) && CollectionUtils.isNotEmpty(condition.getAgentIdList())){
			sqlStr.append(" AND ((a.siteId in (:siteIdList) and a.businessType = :businessTypeCash)");
			sqlStr.append(" OR (a.siteId in (:agentIdList) and a.businessType = :businessTypeSale))");
			
			params.put("siteIdList",condition.getSiteIdList());
			params.put("agentIdList",condition.getAgentIdList());
			params.put("businessTypeCash", com.baidu.gcrm.quote.model.BusinessType.CASH);
			params.put("businessTypeSale", com.baidu.gcrm.quote.model.BusinessType.SALE);
		}else{
			if(CollectionUtils.isNotEmpty(condition.getSiteIdList())){
				sqlStr.append(" AND a.siteId in (:siteIdList) AND a.businessType = :businessTypeCash");
				params.put("siteIdList",condition.getSiteIdList());
				params.put("businessTypeCash", com.baidu.gcrm.quote.model.BusinessType.CASH);
			}
			
			if(CollectionUtils.isNotEmpty(condition.getAgentIdList())){
				sqlStr.append(" AND a.siteId in (:agentIdList) AND a.businessType = :businessTypeSale");
				params.put("agentIdList",condition.getAgentIdList());
				params.put("businessTypeSale", com.baidu.gcrm.quote.model.BusinessType.SALE);
			}
		}
		
		return sqlStr;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Quotation> findByQuotationMainCondition(
			QuotationMainCondition condition) {
        StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select a from Quotation a,QuotationMain b where a.quotationMainId=b.id");
		
		if(StringUtils.isNotBlank(condition.getAdvertisingPlatformId())){
			sqlStr.append(" AND b.advertisingPlatformId = :advertisingPlatformId");
		}
		
		if(condition.getBusinessType() != null){
			sqlStr.append(" AND b.businessType = :businessType");
		}
		
		if(CollectionUtils.isNotEmpty(condition.getSiteOrAgentIdList())){
			sqlStr.append(" AND b.siteId in (:siteIdList)");
		}
		
		if(StringUtils.isNotBlank(condition.getSiteId())){
			sqlStr.append(" AND b.siteId = :siteId");
		}
		
		if(CollectionUtils.isNotEmpty(condition.getCreateOperatorList())){
			sqlStr.append(" AND b.createOperator in (:createOperatorList)");
		}
		
		if(condition.getStatus() != null){
			sqlStr.append(" AND b.status = :status");
		}
		if(condition.getMainId() != null){
			sqlStr.append(" AND b.id = :id");
		}
		
		Query query = entityManager.createQuery(sqlStr.toString());
		if(StringUtils.isNotBlank(condition.getAdvertisingPlatformId())){
			query.setParameter("advertisingPlatformId", Long.valueOf(condition.getAdvertisingPlatformId()));
		}
		
		if(condition.getBusinessType() != null){
			query.setParameter("businessType", condition.getBusinessType());
		}
		
		if(CollectionUtils.isNotEmpty(condition.getSiteOrAgentIdList())){
			query.setParameter("siteIdList", condition.getSiteOrAgentIdList());
		}
		
		if(CollectionUtils.isNotEmpty(condition.getCreateOperatorList())){
			query.setParameter("createOperatorList", condition.getCreateOperatorList());
		}
		
		if(StringUtils.isNotBlank(condition.getSiteId())){
			query.setParameter("siteId", Long.valueOf(condition.getSiteId()));
		}
		
		if(condition.getStatus() != null){
			query.setParameter("status", condition.getStatus());
		}
		if(condition.getMainId() != null){
			query.setParameter("id", condition.getMainId());
		}
		return (List<Quotation>) query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<QuotationMain> findForValid(Long advertisingPlatformId,
			Long siteId, QuotationApproveStatus approveStaus,
			QuotationStatus status) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select a from QuotationMain a where 1=1");
		
		if(status != null){
			sqlStr.append(" AND a.status < :status");
		}
		
		if(approveStaus != null){
			sqlStr.append(" AND a.approveStatus = :approveStatus");
		}
		
		if(advertisingPlatformId != null){
			sqlStr.append(" AND a.advertisingPlatformId = :advertisingPlatformId");
		}
		
		if(siteId != null){
			sqlStr.append(" AND a.siteId = :siteId");
		}
		
		Query query = entityManager.createQuery(sqlStr.toString());
		
		if(status != null){
			query.setParameter("status", status);
		}
		
		if(approveStaus != null){
			query.setParameter("approveStatus", approveStaus);
		}
		
		if(advertisingPlatformId != null){
			query.setParameter("advertisingPlatformId", advertisingPlatformId);
		}
		
		if(siteId != null){
			query.setParameter("siteId", siteId);
		}
		return (List<QuotationMain>) query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<QuotationMain> findQuotationMainListCreateDateBetween(
			Date startDate, Date endDate) {
        StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select a from QuotationMain a where createTime > :startDate AND createTime < :endDate");
		
        Query query = entityManager.createQuery(sqlStr.toString());
        
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
		return (List<QuotationMain>) query.getResultList();
	}
	
	@Override
	public Map<Long, String> getIdNumberMap() {
		Map<Long, String> results = new HashMap<Long, String>();
		String sql = "select id, quoteCode from QuotationMain";
		Query query = entityManager.createQuery(sql.toString());
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long) object[0], (String) object[1]);
		}
		return results;
	}
}
