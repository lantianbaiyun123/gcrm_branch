package com.baidu.gcrm.common.page;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface IHqlPageQuery<T> {
	void executePageQuery(EntityManager entityManager, StringBuilder sqlStr,StringBuilder countStr,
            List<?> paramList, PageWrapper<T> page);
	
	void executePageQuery(EntityManager entityManager, StringBuilder sqlStr,StringBuilder countStr,
            Map<String,Object> params, PageWrapper<T> page);
}
