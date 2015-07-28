package com.baidu.gcrm.ad.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.ad.dao.ContractRepositoryCutom;
import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.ad.web.utils.ContractCondition;
import com.baidu.gcrm.ad.web.utils.ContractListBean;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.log.LoggerHelper;

@Repository
public class ContractRepositoryCutomImpl implements ContractRepositoryCutom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findValidContractAmountBetween(Date startDate, Date endDate) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) as count from Contract").append(" where state = :state");

        if(startDate != null){
			sb.append(" AND beginDate > :startDate ");
		}
		
		if(endDate != null){
			sb.append(" AND beginDate < :endDate ");
		}
        
        Query query = entityManager.createQuery(sb.toString());

        query.setParameter("state", ContractState.VALID);

        if(startDate != null){
        	query.setParameter("startDate", startDate);
		}
		
		if(endDate != null){
			query.setParameter("endDate", endDate);
		}
		
        Long totals = (Long) query.getSingleResult();

        long total = 0L;
        total += totals == null ? 0 : totals.longValue();
        return total;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContractListBean> findContractsByCondition(ContractCondition condition) throws CRMBaseException {
        List<ContractListBean> contractList = new ArrayList<ContractListBean>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            StringBuffer sb = new StringBuffer();
            sb.append("select c.number,summary,c.customer_id,c.category,c.begin_Date,c.end_date,c.state,c.sales  from g_contract c where 1=1");
            sb.append(processCondition(condition, params));

            Query query = entityManager.createNativeQuery(sb.toString(), ContractListBean.class);
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                query.setParameter(key, params.get(key));
            }

            contractList =(List<ContractListBean>) query.getResultList();
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "查询合同列表信息异常", e);
            throw new CRMBaseException(e);
        }
        return contractList;
    }

    private StringBuilder processCondition(ContractCondition condition, Map<String, Object> params) {
        StringBuilder sqlConditionStr = new StringBuilder();

        if (condition.getCustomerId() != null) {
            sqlConditionStr.append(" and c.customer_id=:customerId");
            params.put("customerId", condition.getCustomerId()); 
        }

        if (condition.getContractCategory()!=null) {
            sqlConditionStr.append(" and c.category =:category");
            params.put("category", condition.getContractCategory());
        }

        if (condition.getContractState() != null) {
            sqlConditionStr.append(" and c.state =:state");
            params.put("state", condition.getContractState());
        }

        if (condition.getStartDate() != null) {
            sqlConditionStr.append("  and begin_Date >= :startDate");
            params.put("startDate", condition.getStartDate());
        }

        if (condition.getEndDate() != null) {
            sqlConditionStr.append("  and begin_Date < :endDate");
            params.put("endDate", condition.getEndDate());
        }
        if (condition.getQueryType() != null && StringUtils.isNotBlank(condition.getQueryStr())) {
            ContractCondition.QueryType queryType = condition.getQueryType();
            String queryStr = condition.getQueryStr();
            switch (queryType) {
            case contractNumber:
                sqlConditionStr.append(" and c.number like :queryStr");
                queryStr = new StringBuilder().append("%").append(queryStr).append("%").toString();
                break;
            case belongsSales:
                sqlConditionStr.append(" and c.sales= :queryStr");
                break;
            }
            params.put("queryStr", queryStr);

        }

        return sqlConditionStr;
    }
}
