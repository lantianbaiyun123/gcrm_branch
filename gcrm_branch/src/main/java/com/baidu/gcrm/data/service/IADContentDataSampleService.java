package com.baidu.gcrm.data.service;

import java.io.File;
import java.util.List;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.data.bean.ADContentStatisticsData;
import com.baidu.gcrm.data.model.ADContentDataSample;

public interface IADContentDataSampleService {
    void save(List<ADContentDataSample> contentSamples);

    /**
     * 读取文件数据并保存
     * 
     * @param file
     * @return 成功保存记录个数
     */
    int readFromFileAndSave(File file) throws CRMBaseException;

    /**
     * 计算广告内容累积统计数据
     * 
     * @param adContentIdList
     * @param billingModelId
     * @return 广告内容累积统计数据
     */
    ADContentStatisticsData caculateDataByAdContentIdList(List<Long> adContentIdList, Long billingModelId);

}
