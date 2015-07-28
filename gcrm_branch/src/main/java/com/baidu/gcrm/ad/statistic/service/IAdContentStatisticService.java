package com.baidu.gcrm.ad.statistic.service;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.ad.statistic.web.vo.PublishAdContentVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickListVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishListVO;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;

public interface IAdContentStatisticService {
    
    void syncHao123AdContentPublishData();
    
    void updatePublishDataInfo();
    
    void syncHao123AdContentPublishData(String dayFrom, String dayTo);
    
    List<PublishClickDataVO> findByCustomerNumberAndLocale(Long customerNumber, LocaleConstants locale);
    
    List<AdPlatform> findAdPlatformByCustomerNumberAndLocale(Long customerNumber, LocaleConstants locale);
    
    PublishListVO findListByCustomerNumberAndLocale(Long customerNumber, Date planEndDate,
            LocaleConstants locale, PublishListVO vo);

    PublishClickListVO findClickListByDateRangeAndAdContentNumber(PublishClickListVO vo, Long customerNumber);
    
    PublishAdContentVO findByAdContentNumber(String adContentNumber, Long customerNumber, LocaleConstants locale);
}
