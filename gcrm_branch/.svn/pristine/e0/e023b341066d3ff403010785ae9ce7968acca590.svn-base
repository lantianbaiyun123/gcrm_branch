package com.baidu.gcrm.common.page;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class HqlPageQuery<T> implements IHqlPageQuery<T> {

	private long executeCountQuery(EntityManager entityManager, String countStr,
			List<?> paramList) {
		Query countQuery = entityManager.createQuery(countStr);
        setParameter(countQuery,paramList);
        Long totals = (Long)countQuery.getSingleResult();
        
        long total = 0L;
        total += totals == null ? 0 : totals.longValue();
        
        return total;
	}

	private void setParameter(Query query,List<?> paramList){
        Assert.notNull(query);
        if(paramList != null){
            for(int i = 1; i <= paramList.size(); i++){
                query.setParameter(i, paramList.get(i-1));
            }
        }
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, StringBuilder countStr, List<?> paramList,
			PageWrapper<T> page) {
		if(page.getPageNumber() < 0){
            return;
        }
        long total = executeCountQuery(entityManager,countStr.toString(),paramList);
        List<T> resultList = null;
        if(total > page.getOffset()){
            Query query = entityManager.createQuery(sqlStr.toString());
            query.setFirstResult(page.getOffset());
            query.setMaxResults(page.getPageSize());
            setParameter(query,paramList);
            resultList = query.getResultList();
        }else{
            resultList = Collections.emptyList();
        }
        page.setContent(resultList);
        page.setTotal(total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, StringBuilder countStr,
			Map<String, Object> params, PageWrapper<T> page) {
		if(page.getPageNumber() < 0){
            return;
        }
        long total = executeCountQuery(entityManager,countStr.toString(),params);
        List<T> resultList = null;
        if(total > page.getOffset()){
            Query query = entityManager.createQuery(sqlStr.toString());
            query.setFirstResult(page.getOffset());
            query.setMaxResults(page.getPageSize());
            setParameter(query,params);
            resultList = query.getResultList();
        }else{
            resultList = Collections.emptyList();
        }
        page.setContent(resultList);
        page.setTotal(total);
	}
	
	private void setParameter(Query query,Map<String, Object> params){
        Assert.notNull(query);
        if(params != null){
        	Iterator<String> it = params.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                query.setParameter(key, params.get(key));
            }
        }
    }
	
	private long executeCountQuery(EntityManager entityManager, String countStr,
			Map<String, Object> params) {
		Query countQuery = entityManager.createQuery(countStr);
        setParameter(countQuery,params);
        Long totals = (Long)countQuery.getSingleResult();
        
        long total = 0L;
        total += totals == null ? 0 : totals.longValue();
        
        return total;
	}
}
