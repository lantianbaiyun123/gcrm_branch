package com.baidu.gcrm.customer.dao;

import java.util.ArrayList;
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

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerCondition;
import com.baidu.gcrm.customer.web.helper.CustomerDataState;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IPageQuery mysqlPageQuery;

    @Override
    public void updateCustomerNumber(Long oldId, Long newId) {
        Query query = entityManager.createNativeQuery("update g_customer set id = ? where id = ?");
        query.setParameter(1, newId);
        query.setParameter(2, oldId);

        query.executeUpdate();
    }

    @Override
    public Page<CustomerListBean> findByCondition(CustomerCondition condition) {
        Map<String, Object> params = new java.util.HashMap<String, Object>();
        StringBuilder rempSqlConditionStr = processCondition(condition, params);
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select c.id,c.customer_number,c.company_name,c.customer_type,c.business_type,c.country,")
                .append("c.agenct_regional,c.company_status,c.agenct_type,c.belongs_sales,c.approval_status,c.company_status,")
                .append("a.company_name as agentCompanyName ,").append("gu.realname AS belongSalesName ").append(", c.task_info as taskInfor ")
                .append(",c.create_time,c.create_operator ")
                .append(" from g_customer c left join g_customer a on  c.agenct_company=a.id ")
                .append(" left join g_user gu on c.belongs_sales=gu.ucid where 1=1 ").append(rempSqlConditionStr)
                .append(" order by c.company_status,c.approval_status,c.last_update_time desc ");

        mysqlPageQuery.executePageQuery(entityManager, sqlStr, params, condition);

        return condition;
    }

    private StringBuilder processCondition(CustomerCondition condition, Map<String, Object> params) {
        StringBuilder sqlConditionStr = new StringBuilder();

        Integer agentRegional = condition.getAgentRegional();
        if (agentRegional != null) {
            sqlConditionStr.append(" and c.agenct_regional=:agentRegional");
            params.put("agentRegional", agentRegional);
        }
        if (condition.getApprovalStatus() != null) {
            Integer approvalStatus = condition.getApprovalStatus().ordinal();
            if (approvalStatus != null) {
                sqlConditionStr.append(" and c.approval_status=:approvalStatus ");
                params.put("approvalStatus", approvalStatus);
            }
        }
        Integer country = condition.getCountry();
        if (country != null) {
            sqlConditionStr.append(" and c.country=:country ");
            params.put("country", country);
        }

        String businessType = condition.getBusinessType();
        if (businessType != null) {
            sqlConditionStr.append(" and c.business_type like :businessType ");
            params.put("businessType", "%" + businessType + "%");
        }
        if (condition.getCustomerType() != null) {

            Integer customerType = condition.getCustomerType().ordinal();

            if (customerType != null) {
                sqlConditionStr.append(" and c.customer_type =:customerType ");
                params.put("customerType", customerType);
            }
        }
        CustomerCondition.QueryType queryType = condition.getQueryType();
        String queryStr = condition.getQueryStr();
        if (condition.getQueryType() != null && !PatternUtil.isBlank(queryStr)) {
            queryStr = queryStr.trim();
            switch (queryType) {
            case SALES:
                sqlConditionStr.append(" and gu.realname like :queryStr ");
                queryStr = new StringBuilder().append("%").append(queryStr).append("%").toString();
                break;
            case AGENT:
                sqlConditionStr.append(" and c.agenct_company= :queryStr ");
                break;
            case COMPANY:
                sqlConditionStr.append(" and c.company_name like :queryStr ");
                queryStr = new StringBuilder().append("%").append(queryStr).append("%").toString();
                break;
            case AGENT_CUSTOMER_NUMBER:
//                if (queryStr.length() < 10) {
//                    queryStr = "-1";
//                }
                queryStr = new StringBuilder().append("%").append(queryStr).append("%").toString();

                sqlConditionStr.append(" and c.customer_number like :queryStr ");
                break;
            }
            params.put("queryStr", queryStr);
        }

        if (condition.getCreateOperator() != null && CollectionUtils.isNotEmpty(condition.getOperatorIdList())) {
            sqlConditionStr.append(" and ( c.create_operator=:createOperator ");
            sqlConditionStr.append(" or (( c.create_operator in ( :operators )");
            sqlConditionStr.append(" or c.belongs_sales in ( :operators )) and c.create_time is not null ))");
            
            params.put("createOperator", condition.getCreateOperator());
            params.put("operators", condition.getOperatorIdList());

        } else {

            if (condition.getCreateOperator() != null) {
                sqlConditionStr.append(" and ( c.create_operator=:createOperator ");
                sqlConditionStr.append(" or (c.belongs_sales=:createOperator and c.create_time is not null ))");

                params.put("createOperator", condition.getCreateOperator());
            }

            if (CollectionUtils.isNotEmpty(condition.getOperatorIdList())) {
                sqlConditionStr.append(" and ( c.create_operator in ( :operators )");
                sqlConditionStr.append(" or c.belongs_sales in ( :operators ))  and c.create_time is not null ");
                params.put("operators", condition.getOperatorIdList());
            }
        }
        
        if(StringUtils.isNotBlank(condition.getCreateStartDate())){
        	sqlConditionStr.append(" AND c.create_time > :createTime");
			params.put("createTime", DateUtils.getString2Date(DateUtils.YYYY_MM_DD, condition.getCreateStartDate()));
		}
        
        if(CollectionUtils.isNotEmpty(condition.getApprovalStatusList())){
        	sqlConditionStr.append(" and c.approval_status in (:approvalStatusList)");
            params.put("approvalStatusList", condition.getApprovalStatusList());
        }
        return sqlConditionStr;
    }

    @Override
    public HashMap<String, Object> findExecutor(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.username,a.email from AdvertiseSolution ad, User a where ad.operator=a.ucid and ad.id=?1");
        Query query = entityManager.createQuery(String.valueOf(sql));
        query.setParameter(1, id);
        Object[] values = (Object[]) query.getSingleResult();
        Map<String, Object> account = new HashMap<String, Object>();
        if (values[0] != null) {
            account.put("name", values[0]);
        } else {
            account.put("name", "");
        }
        if (values[1] != null) {
            account.put("email", values[1]);
        } else {
            account.put("email", "");
        }
        return (HashMap<String, Object>) account;

    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findCustomerCreateBetween(Date startDate, Date endDate,String operateType) {
		List<Customer> result = new ArrayList<Customer>();
		if(StringUtils.isBlank(operateType)){
			return result;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select a from Customer a");
		if("create".equals(operateType)){
			sb.append(" where a.createTime > :startDate AND a.createTime < :endDate ")
			.append(" AND a.dataStatus = ").append(CustomerDataState.create.ordinal());  
		}else if("update".equals(operateType)){
			sb.append(" where a.updateTime > :startDate AND a.updateTime < :endDate ")
			.append(" AND a.dataStatus = ").append(CustomerDataState.update.ordinal());  
		}else if("approved".equals(operateType)){
			sb.append(" where a.updateTime > :startDate AND a.updateTime < :endDate ")
			.append(" and a.approvalStatus =3");  
		}else if("approving".equals(operateType)){
			sb.append(" where a.updateTime > :startDate AND a.updateTime < :endDate ")
			.append(" and a.approvalStatus =2");  
		}
		
		Query query = entityManager.createQuery(sb.toString());
        
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return (List<Customer>) query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findCustomerApprovedBetween(Date startDate,
			Date endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("select a from Customer a WHERE a.approvalStatus =3");
		
		if(startDate != null){
			sb.append(" AND updateTime > :startDate ");
		}
		
		if(endDate != null){
			sb.append(" AND updateTime < :endDate ");
		}
		
		Query query = entityManager.createQuery(sb.toString());
        
		if(startDate != null){
			query.setParameter("startDate", startDate);
		}
		
		if(endDate != null){
			query.setParameter("endDate", endDate);
		}
		
		return (List<Customer>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Long> getIdNumberMap() {
		Map<Long, Long> results = new HashMap<Long, Long>();
		String sql = "select id, customerNumber from Customer";
		Query query = entityManager.createQuery(sql.toString());
		List<Object[]> objects = query.getResultList();
		for (Object[] object : objects) {
			results.put((Long)object[0], (Long)object[1]);
		}
		return results;
	}
}
