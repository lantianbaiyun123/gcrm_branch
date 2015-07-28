package com.baidu.gcrm.ad.statistic.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.statistic.web.vo.PublishAdContentVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickListVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickStatDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishListVO;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;

public interface IAdContentPublishDataRepositoryCustom {
    
    List<PublishClickStatDataVO> findByCustomerNumberAndLocale(Long customerNumber, LocaleConstants locale);
    
    List<AdPlatform> findAdPlatformByCustomerNumber(Long customerNumber, Date planEndDate);
    
    PublishListVO findListByCustomerNumber(Long customerNumber, Date planEndDate, PublishListVO vo);
    
    Map<String,String> findSuspendByPublishNumber(List<String> publishNumberList);
    
    PublishClickListVO findClickListByDateRangeAndAdContentNumber(PublishClickListVO vo, Long customerNumber);
    
    PublishAdContentVO findAdContentByContentNumber(String contentNumber, Long customerNumber);
}
