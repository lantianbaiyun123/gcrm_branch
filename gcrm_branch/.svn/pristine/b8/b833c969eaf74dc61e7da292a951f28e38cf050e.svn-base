package com.baidu.gcrm.ad.statistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.statistic.model.AdContentPublishData;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;

public interface IAdContentPublishDataRepository extends JpaRepository<AdContentPublishData, Long>,IAdContentPublishDataRepositoryCustom {
    
    @Modifying
    @Query(value="update g_adcontent_publish_data d,g_publish p, g_position ps,g_site s SET d.customer_number=p.c_number,d.position_id=p.position_id,d.site_code=s.code,d.ad_content_number=p.ac_number  where d.publish_number=p.online_number and p.position_id=ps.id and s.id=ps.site_id and d.customer_number is null and p.status<>?1 ", 
    nativeQuery=true)
    void updatePublishDataInfo(PublishStatus status);
    
}
