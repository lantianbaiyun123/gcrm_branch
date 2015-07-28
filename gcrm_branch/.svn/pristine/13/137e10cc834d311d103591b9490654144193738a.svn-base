package com.baidu.gcrm.data.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baidu.gcrm.data.bean.ADContentStatisticsData;
import com.baidu.gcrm.data.dao.IADContentDataSampleRepositoryCustom;

@Repository
public class ADContentDataSampleRepositoryCustomImpl implements IADContentDataSampleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public ADContentStatisticsData caculateDataByAdContentIdList(List<Long> adContentIdList, Long billingModelId) {
        ADContentStatisticsData result = new ADContentStatisticsData();
        
        String sql = "select count(1) as accumulatedDays, ifnull(sum(impressions),0) as impressions, "
                + "ifnull(sum(clicks),0) as clicks, ifnull(sum(uv),0) as uv, ifnull(sum(click_uv),0) as clickUv "
                + "from g_adcontent_data_sample where ad_content_id in (:adContentIdList)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("adContentIdList", adContentIdList);

        Object[] object = (Object[]) query.getSingleResult();
        if (billingModelId.longValue() == 1L) {
            result.setAccumulatedAmount(Long.parseLong(object[2].toString()));
        } else if (billingModelId.longValue() == 4L) {
            result.setAccumulatedAmount(Long.parseLong(object[1].toString()));
        } else {
            result.setAccumulatedAmount(Long.parseLong(object[0].toString()));
        }
        result.setAccumulatedDays(Integer.parseInt(object[0].toString()));
        result.setImpressions(Long.parseLong(object[1].toString()));
        result.setClicks(Long.parseLong(object[2].toString()));
        result.setUv(Long.parseLong(object[3].toString()));
        result.setClickUv(Long.parseLong(object[4].toString()));

        return result;
    }

}
