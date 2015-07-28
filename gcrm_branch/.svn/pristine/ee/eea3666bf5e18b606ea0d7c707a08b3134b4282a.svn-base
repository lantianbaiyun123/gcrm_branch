/**
 * 
 */
package com.baidu.gcrm.notice.dao.impl;

import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.notice.dao.INoticeRepositoryCustom;
import com.baidu.gcrm.notice.model.Notice;

/**
 * @author shijiwen
 *
 */
@Repository("noticeRepositoryCustomImpl")
public class NoticeRepositoryCustomImpl implements INoticeRepositoryCustom{
    @Autowired
    private IPageQuery mysqlPageQuery;
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Page<Notice> findByCreatorIdList(Page<Notice> noticePage, Set<Long> creatorIds) {
        Map<String, Object> params = new java.util.HashMap<String, Object>();
        StringBuilder sqlFrame = new StringBuilder();
        sqlFrame.append(" SELECT  * from g_notice n ");
        if(CollectionUtils.isNotEmpty(creatorIds)){
            sqlFrame.append("where n.create_operator in ( :operators )");
            params.put("operators", creatorIds);
        }
        sqlFrame.append(" order by n.create_time desc");
        mysqlPageQuery.executePageQuery(entityManager, sqlFrame, params, noticePage);
        return noticePage;
    }

}
