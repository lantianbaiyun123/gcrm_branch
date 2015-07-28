package com.baidu.gcrm.ad.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baidu.gcrm.ad.dao.AdvertiseQuotationRepositoryCustom;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;

public class AdvertiseQuotationRepositoryImpl implements AdvertiseQuotationRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertiseQuotation> findByByAdSolutionId(Long id) {
		//this.entityManager.createNativeQuery(sqlString, resultClass)
		Query query = this.entityManager.createQuery("select q from AdvertiseQuotation q, AdSolutionContent c where q.adSolutionContentId=c.id and c.adSolutionId=?1");
		query.setParameter(1, id);
		return query.getResultList();
	}

	@SuppressWarnings("unused")
	@Override
	public void moveToHistory(List<Long> adContentIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into g_advertise_quotation_history (")
			.append("id,advertise_solution_content_id,price_type,billing_model_id,")
			.append("currency_type,publish_price,customer_quote,traffic_amount,")
			.append("click_amount,discount,budget,total_price,product_ratio_mine,")
			.append("product_ratio_customer,product_ratio_third,industry_type,ratio_mine,")
			.append("ratio_customer,ratio_third,ratio_condition,ratio_condition_desc,reach_estimate,")
			.append("daily_amount,total_amount,create_time,create_operator,last_update_time,last_update_operator) ")
			.append(" select id,advertise_solution_content_id,price_type,billing_model_id,")
			.append("currency_type,publish_price,customer_quote,traffic_amount,")
			.append("click_amount,discount,budget,total_price,product_ratio_mine,")
			.append("product_ratio_customer,product_ratio_third,industry_type,ratio_mine,")
			.append("ratio_customer,ratio_third,ratio_condition,ratio_condition_desc,reach_estimate,")
			.append("daily_amount,total_amount,create_time,create_operator,last_update_time,last_update_operator ")
			.append(" from g_advertise_quotation where advertise_solution_content_id in (");
		
		int temParamIndex = 0;
		for(Long adContentId : adContentIds) {
			if (temParamIndex > 0) {
				sql.append(",");
			}
			sql.append("?");
			temParamIndex++;
		}
		sql.append(")");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		int currParamIdex = 1;
		for(Long adContentId : adContentIds) {
			query.setParameter(currParamIdex, adContentId);
			currParamIdex++;
		}
		query.executeUpdate();
		
	}

}
