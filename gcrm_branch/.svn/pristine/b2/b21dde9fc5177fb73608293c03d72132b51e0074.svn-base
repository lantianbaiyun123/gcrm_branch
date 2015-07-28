package com.baidu.gcrm.common.page;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MysqlPageQuery implements IPageQuery{
    

    @SuppressWarnings("unchecked")
    @Override
    public <T> void executePageQuery(EntityManager entityManager,
            StringBuilder sqlStr, List<?> paramList, PageWrapper<T> page) {
        boolean isQqueryAll = page.isQueryAll();
        if(!isQqueryAll && page.getPageNumber() < 0){
            return;
        }
        long total = executeCountQuery(entityManager, sqlStr.toString(), paramList);
        List<T> resultList = null;
        if(total >0 && total > page.getOffset()){
            if (!isQqueryAll) {
                String pageLimitStr =  String.format(" limit %1$d,%2$d ", page.getOffset(), page.getPageSize());
                sqlStr.append(pageLimitStr);
            }
            
            Query query = entityManager.createNativeQuery(sqlStr.toString(), page.getResultClass());
            setParameter(query,paramList);
            resultList = query.getResultList();
        } else {
            resultList = Collections.emptyList();
        }
        page.setContent(resultList);
        page.setTotal(total);

    }

    @Override
    public long executeCountQuery(EntityManager entityManager, String sqlStr,
            List<?> paramList) {
        Query countQuery = entityManager.createNativeQuery(getCountSql(sqlStr));
        setParameter(countQuery,paramList);
        Object totals = countQuery.getSingleResult();
        if (totals == null) {
            return 0;
        }
        return Long.parseLong(totals.toString());
    }

    private void setParameter(Query query, List<?> paramList) {
        Assert.notNull(query);
        if (paramList != null) {
            for (int i = 1; i <= paramList.size(); i++) {
                query.setParameter(i, paramList.get(i - 1));
            }
        }
    }

    private void setParameter(Query query, Map<String, Object> params) {
        Assert.notNull(query);
        if (params != null) {
            for (String key : params.keySet())
                query.setParameter(key, params.get(key));
        }
    }

    private static String getCountSql(String realSql) {
        StringBuilder countSql = new StringBuilder();
        String lowerCaseSql = realSql.toLowerCase();
        countSql.append("select count(*) ");
        String otherSectionSql = "";
        if (realSql.indexOf("order by") != -1) {
            otherSectionSql = realSql.substring(lowerCaseSql.indexOf("from"), lowerCaseSql.indexOf("order by"));
        } else {
            otherSectionSql = realSql.substring(lowerCaseSql.indexOf("from"));
        }

        countSql.append(otherSectionSql);
        return StringUtils.trim(countSql.toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void executePageQuery(EntityManager entityManager, StringBuilder sqlStr, Map<String, Object> params,
            Page<T> page) {
        if (page.getPageNumber() < 0) {
            return;
        }
        long total = executeCountQuery(entityManager, sqlStr.toString(), params);
        int pageSize = page.getPageSize();
        List<T> resultList = null;
        if (total > page.getOffset()) {
            Query query = entityManager.createNativeQuery(sqlStr.toString(), page.getResultClass());
            query.setFirstResult(page.getOffset());
            query.setMaxResults(pageSize);
            setParameter(query, params);
            resultList = query.getResultList();
        } else {
            resultList = Collections.emptyList();
        }
        page.setContent(resultList);
        page.setTotal(total);
        page.setTotalCount(total);
   //     page.setTotalPages(total/pageSize +1);

    }

    @Override
    public long executeCountQuery(EntityManager entityManager, String sqlStr, Map<String, Object> params) {
        Query countQuery = entityManager.createNativeQuery(getCountSql(sqlStr));
        setParameter(countQuery, params);
        Object totals = countQuery.getSingleResult();
        if (totals == null) {
            return 0;
        }
        return Long.parseLong(totals.toString());
    }

}
