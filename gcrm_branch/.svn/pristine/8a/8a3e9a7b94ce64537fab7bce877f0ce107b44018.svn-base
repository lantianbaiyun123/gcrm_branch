package com.baidu.gcrm.data.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.GcrmFileUtils;
import com.baidu.gcrm.data.bean.ADContentStatisticsData;
import com.baidu.gcrm.data.dao.IADContentDataSampleRepository;
import com.baidu.gcrm.data.dao.IADContentDataSampleRepositoryCustom;
import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.data.service.IADContentDataSampleService;
import com.baidu.gcrm.data.service.IADContentDataStatisService;
import com.baidu.gcrm.data.service.IFileReadRecordService;
import com.baidu.gcrm.data.service.IPublishDateStatisticsService;

@Service
public class ADContentDataSampleService implements IADContentDataSampleService {
    @Autowired
    IADContentDataSampleRepository contentSampleDao;

    @Autowired
    IADContentDataSampleRepositoryCustom contentSampleCustomDao;

    @Autowired
    IADContentDataStatisService adContentDataStatisService;

    @Autowired
    IPublishDateStatisticsService publishDateStatisticsService;

    @Autowired
    IFileReadRecordService fileRecordService;

    private final static int ADCONTENT_COLUMN_COUNT = 6;

    private static Map<Date, Set<Long>> cachedReadRecords = Collections.synchronizedMap(new HashMap<Date, Set<Long>>());

    private static Set<Long> cachedAdContentIds = Collections.synchronizedSet(new HashSet<Long>());

    @Override
    public void save(List<ADContentDataSample> contentSamples) {
        contentSampleDao.save(contentSamples);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int readFromFileAndSave(File file) {
        List<ADContentDataSample> contentSamples = new ArrayList<ADContentDataSample>();
        try {
            List<String[]> datas = GcrmFileUtils.analyzeFile(file, ADCONTENT_COLUMN_COUNT);
            for (String[] data : datas) {
                contentSamples.add(generateADContentDataSample(data));
            }
            //removeDuplicatedRecords(contentSamples);
            save(contentSamples);
            adContentDataStatisService.updateADContentStatistics(contentSamples);
            publishDateStatisticsService.updateStatisticsFromSamples(contentSamples);
            fileRecordService.saveReadFileRecord(StringUtils.trimToEmpty(file.getName()));
        } catch (Exception e) {
            //clearCached();
            LoggerHelper.err(getClass(), "读取广告统计文件并保存时出错，错误原因：{}，文件：{}", e.getMessage(), file.getName());
            throw new CRMRuntimeException(e);
        }
        return contentSamples.size();
    }

    private void clearCached() {
        cachedAdContentIds.clear();
        cachedReadRecords.clear();
    }

    /**
     * 对北京时间的每一天(零点到24点之间)，若读取到的ecom同步过来的文件中有重复的广告内容，则日志输入警告信息，并remove重复的，以广告内容id为准
     * 
     * @param contentSamples
     */
    private void removeDuplicatedRecords(List<ADContentDataSample> contentSamples) {
        if (CollectionUtils.isEmpty(contentSamples)) {
            return;
        }
        Date today = DateUtils.getCurrentDateOfZero();
        List<ADContentDataSample> exists = new ArrayList<ADContentDataSample>();
        for (ADContentDataSample sample : contentSamples) {
            if (cachedReadRecords.size() == 0) {
                cachedAdContentIds.add(sample.getAdContentId());
                cachedReadRecords.put(today, cachedAdContentIds);
            } else if (cachedReadRecords.size() == 1) {
                if (cachedReadRecords.get(today) != null) {
                    if (!cachedReadRecords.get(today).add(sample.getAdContentId())) {
                        exists.add(sample);
                        LoggerHelper.warn(getClass(), "ecom同步给gcrm的文件中，同一天中出现了重复的广告内容，adContentId={}",
                                sample.getAdContentId());
                    }

                } else {
                    LoggerHelper.info(getClass(), "时间{}:读取到的ecom同步给gcrm的所有广告内容为id={}", cachedReadRecords.keySet()
                            .toArray(), cachedAdContentIds.toArray());
                    clearCached();
                    cachedAdContentIds.add(sample.getAdContentId());
                    cachedReadRecords.put(today, cachedAdContentIds);
                }
            }
        }
        if (exists.size() > 0) {
            contentSamples.removeAll(exists);
            LoggerHelper.warn(getClass(), "已成功去掉时间{}：读取到的ecom同步给gcrm中重复的广告内容{}，将以第一个为准更新gcrm", today, exists.toArray());
        }
    }

    private ADContentDataSample generateADContentDataSample(String[] data) {
        ADContentDataSample contentSample = new ADContentDataSample();
        contentSample.setDate(data[0]);
        contentSample.setAdContentId(Long.valueOf(data[1]));
        contentSample.setImpressions(Long.valueOf(data[2]));
        contentSample.setClicks(Long.valueOf(data[3]));
        contentSample.setUv(Long.valueOf(data[4]));
        contentSample.setClickUv(Long.valueOf(data[5]));
        return contentSample;
    }

    @Override
    public ADContentStatisticsData caculateDataByAdContentIdList(List<Long> adContentIdList, Long billingModelId) {
        return contentSampleCustomDao.caculateDataByAdContentIdList(adContentIdList, billingModelId);
    }
}
