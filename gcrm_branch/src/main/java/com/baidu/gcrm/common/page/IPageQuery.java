package com.baidu.gcrm.common.page;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * 翻页原生sql查询接口
 * @param <T>
 * @param <T>
 * @param <T>
 * 
 *
 */
public interface IPageQuery {
    
    <T> void executePageQuery(EntityManager entityManager, StringBuilder sqlStr,
            List<?> paramList, PageWrapper<T> page);
    
    long executeCountQuery(EntityManager entityManager, String sqlStr, List<?> paramList);

    <T> void executePageQuery(EntityManager entityManager, StringBuilder sqlStr,
           Map<String,Object> params, Page<T> page);
    
    long executeCountQuery(EntityManager entityManager, String sqlStr, Map<String,Object> params);
}
