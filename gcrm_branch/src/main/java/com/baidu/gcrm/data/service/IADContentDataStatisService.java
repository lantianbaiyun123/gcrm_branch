package com.baidu.gcrm.data.service;

import java.util.Collection;
import java.util.List;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.data.model.ADContentStatistics;

/**
 * 处理锁定的广告内容，抽取数据到表g_adcontent_data_statistics,产生报表数据
 * 
 * @author yangjianguo
 * 
 */
public interface IADContentDataStatisService {
    ADContentStatistics findByAdContentId(Long id);

    /**
     * 目前只处理国际hao123平台的广告内容
     * 
     * @param content
     */
    public void insertData4AdContent(AdSolutionContent content);

    /**
     * 根据广告内容ID列表查询广告内容统计表记录
     * 
     * @author gaofuchun
     * @param adContendIdList
     * @return 广告内容统计对象列表
     */
    List<ADContentStatistics> findByAdContentIdIn(Collection<Long> adContentIds);

    /**
     * 更新广告内容统计数据
     * 
     * @author gaofuchun
     * @param contentSamples
     */
    void updateADContentStatistics(List<ADContentDataSample> contentSamples);
}
