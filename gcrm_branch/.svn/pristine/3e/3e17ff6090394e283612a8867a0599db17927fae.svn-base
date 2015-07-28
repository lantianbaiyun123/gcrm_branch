package com.baidu.gcrm.i18n.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.dao.I18nKVDaoCustom;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

@Repository
public class I18nKVDaoCustomImpl implements I18nKVDaoCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<I18nKV> findExistsValue(String keyPrefix, Long id, List<LocaleVO> values) {
        List<Object> paramList = new ArrayList<Object> ();
        StringBuilder sqlStr = new StringBuilder();
        
        sqlStr.append("from I18nKV where key like ?1 and (");
        paramList.add(new StringBuilder(keyPrefix).append(".%").toString());
        
        StringBuilder valueCondition = new StringBuilder();
        int paramCount = 2;
        for (LocaleVO temLocaleVO : values) {
            if (valueCondition.length() > 0) {
                valueCondition.append(" or ");
            }
            
            valueCondition.append("(")
                .append("value=?")
                .append(paramCount)
                .append(" and locale=?")
                .append(paramCount+1)
                .append(")");
            
            paramList.add(temLocaleVO.getValue());
            paramList.add(temLocaleVO.getLocale());
                
            paramCount += 2;
            
        }
        
        sqlStr.append(valueCondition).append(")");
        
        if (id != null) {
            sqlStr.append(" and key<>?").append(paramCount);
            paramList.add(new StringBuilder(keyPrefix).append(".").append(id).toString());
        }
            
        Query query = entityManager.createQuery(sqlStr.toString());
        for(int i = 0; i < paramList.size(); i++) {
            query.setParameter(i+1, paramList.get(i));
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<I18nKV> findByAdPlatformIdAndSiteId(Long adPlatformId, Long siteId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select v.key_name ,v.key_value,v.locale,p.index_str,v.key_extra_value")
            .append(" from g_i18n v,g_position p where v.key_name=concat('g_position.',p.id) ")
            .append(" and p.ad_platform_id=? and p.site_id=? ");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, adPlatformId);
        query.setParameter(2, siteId);
        
        List<Object[]> resultList = (List<Object[]>)query.getResultList();
        return processI18nListResult(resultList);
    }
    
    @SuppressWarnings("unchecked")
    public List<I18nKV> findByIndexStr(Long excludePositionId, String indexStr) {
        StringBuilder sql = new StringBuilder();
        sql.append("select v.key_name ,v.key_value,v.locale,p.index_str,v.key_extra_value")
            .append(" from g_i18n v,g_position p where v.key_name=concat('g_position.',p.id) ")
            .append(" and p.index_str=? and p.id <> ?");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, indexStr);
        query.setParameter(2, excludePositionId);
        
        List<Object[]> resultList = (List<Object[]>)query.getResultList();
        return processI18nListResult(resultList);
    }
    
    private List<I18nKV> processI18nListResult(List<Object[]> resultList) {
        List<I18nKV> allI18nList = null;
        if (!CollectionUtils.isEmpty(resultList)) {
            allI18nList = new ArrayList<I18nKV> ();
            for (Object[] temResult : resultList) {
                I18nKV temI18nKV = new I18nKV();
                temI18nKV.setKey(temResult[0].toString());
                temI18nKV.setValue(temResult[1].toString());
                temI18nKV.setLocale(LocaleConstants.valueOf(temResult[2].toString()));
                if (temResult.length > 3 && temResult[3] != null) {
                    temI18nKV.setIndexStr(temResult[3].toString());
                }
                
                if (temResult.length > 4 && temResult[4] != null) {
                    temI18nKV.setExtraValue(temResult[4].toString());
                }
                
                allI18nList.add(temI18nKV);
            }
        }
        
        return allI18nList;
    }

    @Override
    public void updateSubPositionExtraName(String indexStr, PositionType type,
            LocaleConstants locale) {
        StringBuilder sql = new StringBuilder();
        sql.append("update g_i18n i, g_position p,g_i18n pi set i.key_extra_value=concat(pi.key_extra_value,'>',i.key_value) ")
            .append(" where pi.key_name=concat('g_position.',p.parent_id) and pi.locale=? ")
            .append(" and i.key_name=concat('g_position.',p.id) and i.locale=?  and p.index_str like ? and p.type=? ");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, locale.name());
        query.setParameter(2, locale.name());
        query.setParameter(3, indexStr);
        query.setParameter(4, type.ordinal());
        query.executeUpdate();
    }
    
}
