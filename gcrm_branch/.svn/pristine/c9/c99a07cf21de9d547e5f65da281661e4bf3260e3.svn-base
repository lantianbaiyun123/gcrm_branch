package com.baidu.gcrm.data.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.data.bean.ADContentStatisticsData;
import com.baidu.gcrm.data.dao.IADContentDataStatisRepository;
import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.data.model.ADContentStatistics;
import com.baidu.gcrm.data.service.IADContentDataSampleService;
import com.baidu.gcrm.data.service.IADContentDataStatisService;

@Service
public class AdContentDataStatisServiceImpl implements IADContentDataStatisService {
    @Autowired
    private IADContentDataStatisRepository statisRepository;

    @Autowired
    IAdSolutionContentService adSolutionContentService;

    @Autowired
    IADContentDataSampleService adContentDataSampleService;

    private final static int FETCH_LIST_SIZE = 1000;

    @Override
    public ADContentStatistics findByAdContentId(Long id) {
        return statisRepository.findByAdContentId(id);
    }

    @Override
    public void insertData4AdContent(AdSolutionContent content) {
        if (content == null) {
            return;
        }
        // 目前只处理国际hao123平台的数据，以供报表使用
        if (content.getProductId() == Constants.PLATFORM_HAO123_ID) {
            statisRepository.saveAdContentStatisData(content.getId());
        }

    }

    @Override
    public List<ADContentStatistics> findByAdContentIdIn(Collection<Long> adContentIds) {
        List<ADContentStatistics> list = new ArrayList<ADContentStatistics>();

        if (CollectionUtils.isNotEmpty(adContentIds)) {
            list = statisRepository.findByAdContentIdIn(adContentIds);
        }

        return list;
    }

    @Override
    public void updateADContentStatistics(List<ADContentDataSample> contentSamples) {
        List<ADContentStatistics> adContentStatisticsList = new ArrayList<ADContentStatistics>();
        List<AdSolutionContent> adSolutionContentList = new ArrayList<AdSolutionContent>();
        List<Long> tempAdContentIdList = new ArrayList<Long>();
        Map<Long, Long> adContentIdMap = new HashMap<Long, Long>();

        if (!CollectionUtils.isEmpty(contentSamples)) {
            for (ADContentDataSample contentSample : contentSamples) {
                tempAdContentIdList.add(contentSample.getAdContentId());
                if (tempAdContentIdList.size() == FETCH_LIST_SIZE) {
                    adContentStatisticsList.addAll(statisRepository.findByAdContentIdIn(tempAdContentIdList));
                    adSolutionContentList.addAll(adSolutionContentService.findByAdContentIds(tempAdContentIdList));
                    tempAdContentIdList.clear();
                }
            }

            if (tempAdContentIdList.size() > 0) {
                adContentStatisticsList.addAll(statisRepository.findByAdContentIdIn(tempAdContentIdList));
                adSolutionContentList.addAll(adSolutionContentService.findByAdContentIds(tempAdContentIdList));
            }

            for (AdSolutionContent adSolutionContent : adSolutionContentList) {
                if (adSolutionContent.getOldContentId() != null) {
                    adContentIdMap.put(adSolutionContent.getId(), adSolutionContent.getOldContentId());
                }
            }
        }

        caculateADContentStatisticsData(adContentStatisticsList, adContentIdMap);
        statisRepository.save(adContentStatisticsList);
    }

    private void caculateADContentStatisticsData(List<ADContentStatistics> adContentStatisticsList,
            Map<Long, Long> adContentIdMap) {
        if (!CollectionUtils.isEmpty(adContentStatisticsList)) {
            for (ADContentStatistics adContentStatistics : adContentStatisticsList) {
                List<Long> adContentIdList = new ArrayList<Long>();
                Long adContentId = adContentStatistics.getAdContentId();
                adContentIdList.add(adContentId);
                if (adContentIdMap.get(adContentId) != null) {
                    adContentIdList.add(adContentIdMap.get(adContentId));
                    adContentIdList.addAll(findOldAdContentIdList(adContentIdMap.get(adContentId)));
                }
                ADContentStatisticsData data =
                        adContentDataSampleService.caculateDataByAdContentIdList(adContentIdList,
                                adContentStatistics.getBillingModelId());
                adContentStatistics.setAccumulatedAmount(data.getAccumulatedAmount());
                adContentStatistics.setAccumulatedDays(data.getAccumulatedDays());
                adContentStatistics.setImpressions(data.getImpressions());
                adContentStatistics.setClicks(data.getClicks());
                adContentStatistics.setUv(data.getUv());
                adContentStatistics.setClickUV(data.getClickUv());
            }
        }
    }

    private List<Long> findOldAdContentIdList(Long adContentId) {
        List<Long> oldAdContentIdList = new ArrayList<Long>();

        AdSolutionContent tempAdSolutionContent = adSolutionContentService.findOne(adContentId);
        while (tempAdSolutionContent != null && tempAdSolutionContent.getOldContentId() != null) {
            oldAdContentIdList.add(tempAdSolutionContent.getOldContentId());
            tempAdSolutionContent =
                    adSolutionContentService.findOne(tempAdSolutionContent.getOldContentId());
        }

        return oldAdContentIdList;
    }
}
