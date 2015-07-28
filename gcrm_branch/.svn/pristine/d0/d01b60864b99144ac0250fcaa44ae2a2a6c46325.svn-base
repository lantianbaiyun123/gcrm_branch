package com.baidu.gcrm.ad.statistic.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.ad.statistic.dao.IAdContentPublishDataRepository;
import com.baidu.gcrm.ad.statistic.model.AdContentPublishData;
import com.baidu.gcrm.ad.statistic.service.IAdContentStatisticService;
import com.baidu.gcrm.ad.statistic.service.helper.Hao123DataJsonVO;
import com.baidu.gcrm.ad.statistic.service.helper.Hao123DataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishAdContentVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickListVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickStatDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishListVO;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;

@Service("adContentStatisticServiceImpl")
public class AdContentStatisticServiceImpl implements IAdContentStatisticService{
    
    private static Logger logger = LoggerFactory.getLogger(AdContentStatisticServiceImpl.class);
    
    private static final int HAO123_NOT_FINISH_NUM = -2;
    private static final int HAO123_SUCCESS_NUM = 0;
    private static final String HAO123_TRAFFIC_TYPE = "1";
    private static final int TREND_GREATER = 1;
    private static final int TREND_EQUAL = 0;
    private static final int TREND_LESSER = -1;
    private static final String CURR_ONLINE_STATUS = "1";
    
    @Autowired
    IAdContentPublishDataRepository publishDataRepository;
    
    @Resource(name="taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    I18nKVService i18nKVService;
    
    @Override
    public void syncHao123AdContentPublishData() {
        syncHao123AdContentPublishData(null, null);
    }

    @Override
    public void syncHao123AdContentPublishData(String dayFrom, String dayTo) {
        try {
            logger.info("syncHao123AdContentPublishData start");
            String connectionTimeout = GcrmConfig.getConfigValueByKey("http.connect.timeout");
            String requestPageSize = GcrmConfig.getConfigValueByKey("http.page.size");
            int retryCountLimit = Integer.parseInt(GcrmConfig.getConfigValueByKey("http.fail.retry"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1);
            String yesterdayStr = simpleDateFormat.format(c.getTime());
            
            if (dayFrom == null || dayFrom.trim().equals("")) {
                dayFrom = yesterdayStr;
            }
            if (dayTo == null || dayTo.trim().equals("")) {
                dayTo = yesterdayStr;
            }
            
            String baseUrl = new StringBuilder()
                                .append(GcrmConfig.getConfigValueByKey("hao123.ad.publish.data.url"))
                                .append(dayFrom)
                                .append("/").append(dayTo).toString();
            
            int currPageNum = 1;
            int timeoutNumber = Integer.parseInt(connectionTimeout);
            
            boolean hasNextPage = getAndSaveHao123Data(baseUrl, currPageNum,
                    requestPageSize, timeoutNumber, retryCountLimit);
            while (hasNextPage) {
                currPageNum++;
                hasNextPage = getAndSaveHao123Data(baseUrl, currPageNum,
                        requestPageSize, timeoutNumber, retryCountLimit);
            }
            
            //update other info
            publishDataRepository.updatePublishDataInfo(PublishStatus.release);
            
            logger.info("syncHao123AdContentPublishData end");
            
        } catch (Exception e) {
            logger.error("syncHao123AdContentPublishData failed", e);
            final String errorMessage = e.getMessage();
            //send email
            taskExecutor.execute(new Runnable(){
                @Override
                public void run() {
                    try {
                        MailHelper.sendSystemMail("[ERROR]sync hao123 publish data failed", errorMessage);
                    } catch (Exception e) {
                        logger.error("taskExecutor system mail failed!",e);
                    }
                }
            });
        }
    }
    
    /**
     * 
     * @param baseUrl
     * @param currPageNum
     * @param requestPageSize
     * @param timeout
     * @return boolean has next page
     * @throws Exception
     */
    private boolean getAndSaveHao123Data(String baseUrl, int currPageNum,
            String requestPageSize, int connectionTimeout, int retryCountLimit) throws Exception{
        
        StringBuilder fullRequestUrl = new StringBuilder()
                                        .append(baseUrl)
                                        .append("/").append(currPageNum)
                                        .append("/").append(requestPageSize);
        String responseContent = null;
        //add retry 
        int currRetryCount = 0;
        boolean needRequest= true;
        while (needRequest &&  currRetryCount < retryCountLimit) {
            try {
                responseContent = Request.Get(fullRequestUrl.toString())
                        .socketTimeout(connectionTimeout)
                        .connectTimeout(connectionTimeout)
                        .execute()
                        .returnContent().asString();
                
                needRequest = false;
            } catch (Exception e) {
                logger.error("request hao123 remote data failed", e);
                currRetryCount++;
                needRequest = true;
                if (currRetryCount >= retryCountLimit) {
                    throw new Exception("request hao123 remote data failed");
                }
            }
        }
        
        //determin need request next page
        if (!PatternUtil.isBlank(responseContent)) {
            Hao123DataJsonVO jsonData = JSON.parseObject(responseContent, Hao123DataJsonVO.class);
            int currErrorNo = jsonData.getErrno().intValue();
            if (currErrorNo == HAO123_NOT_FINISH_NUM) {
                return false;
            } else if (currErrorNo != HAO123_SUCCESS_NUM) {
                throw new Exception("request hao123 remote data failed");
            }
            
            List<Hao123DataVO> resultList = jsonData.getResult();
            if (!CollectionUtils.isEmpty(resultList)) {
                List<AdContentPublishData> adContentPublishDataList = convertToModel(resultList);
                publishDataRepository.save(adContentPublishDataList);
                if (resultList.size() < Integer.parseInt(requestPageSize)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
            
        } else {
            return false;
        }
    }
    
    private List<AdContentPublishData> convertToModel(List<Hao123DataVO> list) {
        List<AdContentPublishData> modelList = new LinkedList<AdContentPublishData> ();;
        if (CollectionUtils.isEmpty(list)) {
            return modelList;
        }
        Date createDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Hao123DataVO temHao123DataVO : list) {
            
            if(!HAO123_TRAFFIC_TYPE.equals(temHao123DataVO.getType())) {
                continue;
            }
            
            AdContentPublishData temModel = new AdContentPublishData();
            temModel.setCreateTime(createDate);
            temModel.setPublishNumber(temHao123DataVO.getAd_id());
            String ctime = temHao123DataVO.getCtime();
            if (!PatternUtil.isBlank(ctime)) {
                try {
                    temModel.setPublishTime(simpleDateFormat.parse(ctime));
                } catch (ParseException e) {
                }
            }
            temModel.setSiteUrl(temHao123DataVO.getHost());
            temModel.setClick(temHao123DataVO.getClick_num());
            temModel.setUv(temHao123DataVO.getClick_uv());
            modelList.add(temModel);
        }
        
        return modelList;
    }

    @Override
    public List<PublishClickDataVO>  findByCustomerNumberAndLocale(Long customerNumber, LocaleConstants locale) {
        List<PublishClickStatDataVO> adStatList = publishDataRepository.findByCustomerNumberAndLocale(customerNumber, locale);
        if (adStatList == null || adStatList.size() < 1) {
            return Collections.emptyList();
        }
        List<PublishClickDataVO> clickList = new LinkedList<PublishClickDataVO> ();
        Calendar lastweek = Calendar.getInstance();
        lastweek.setTime(new Date());
        lastweek.add(Calendar.DAY_OF_MONTH, -7);
        Date lastWeekDate = lastweek.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatLastDateStr = simpleDateFormat.format(lastWeekDate);
        Date formatedLastWeekDate = lastWeekDate;
        try {
            formatedLastWeekDate = simpleDateFormat.parse(formatLastDateStr);
        } catch (ParseException e) {
        }
        //key=>time
        Map<String,PublishClickDataVO> clickMap = new HashMap<String,PublishClickDataVO> ();
        //key=>time+positionId
        Map<String,PublishClickStatDataVO> compareClickMap = new HashMap<String,PublishClickStatDataVO> ();
        for (PublishClickStatDataVO temPublishClickStatDataVO : adStatList) {
            Long positionId = temPublishClickStatDataVO.getPositionId();
            Date currDate = temPublishClickStatDataVO.getDate();
            Long time = currDate.getTime();
            String timeKeyStr = simpleDateFormat.format(currDate);
            PublishClickDataVO clickDataVO = clickMap.get(timeKeyStr);
            if (clickDataVO == null) {
                clickDataVO = new PublishClickDataVO();
                clickDataVO.setTime(time);
                clickMap.put(timeKeyStr, clickDataVO);
                if (!currDate.before(formatedLastWeekDate)) {
                    clickList.add(clickDataVO);
                }
            }
            String compareKey = new StringBuilder().append(timeKeyStr)
                                    .append("-")
                                    .append(positionId.toString()).toString();
            
            if (compareClickMap.get(compareKey) == null) {
                compareClickMap.put(compareKey, temPublishClickStatDataVO);
            }
            
            List<PublishClickStatDataVO> clickData = clickDataVO.getAdStats();
            if (clickData == null) {
                clickData = new LinkedList<PublishClickStatDataVO> ();
                clickDataVO.setAdStats(clickData);
            }
            clickData.add(temPublishClickStatDataVO);
        }
        
        
        if (clickList.size() < 1) {
            return clickList;
        }
        //generate trend
        for (PublishClickDataVO currPublishClickDataVO : clickList) {
            List<PublishClickStatDataVO> currClickData = currPublishClickDataVO.getAdStats();
            if (currClickData != null && currClickData.size() > 0) {
                for (PublishClickStatDataVO currPublishClickStatDataVO : currClickData) {
                    Date time = currPublishClickStatDataVO.getDate();
                    Calendar lastDay = Calendar.getInstance();
                    lastDay.setTime(time);
                    lastDay.add(Calendar.DAY_OF_MONTH, -1); 
                    Date lastDayDate = lastDay.getTime();
                    String lastDayKeyStr = simpleDateFormat.format(lastDayDate);
                    
                    Long positionId = currPublishClickStatDataVO.getPositionId();
                    String lastKey = new StringBuilder()
                                        .append(lastDayKeyStr.toString())
                                        .append("-")
                                        .append(positionId.toString()).toString();
                    PublishClickStatDataVO lastPublishClickStatDataVO = compareClickMap.get(lastKey);
                    if (lastPublishClickStatDataVO != null) {
                        long currClick = currPublishClickStatDataVO.getClick().longValue();
                        long currentUV = currPublishClickStatDataVO.getUv().longValue();
                        
                        long lastClick = lastPublishClickStatDataVO.getClick().longValue();
                        long lastUV= lastPublishClickStatDataVO.getUv().longValue();
                        currPublishClickStatDataVO.setClickTrend(compareTrend(currClick, lastClick));
                        currPublishClickStatDataVO.setUvTrend(compareTrend(currentUV, lastUV));
                    }
                    
                }
            }
            
        }
        
        return clickList;
    }
    
    private Integer compareTrend(long curr, long last) {
        int trend = 0;
        if (curr > last) {
            trend = TREND_GREATER;
        } else if (curr < last) {
            trend = TREND_LESSER;
        } else {
            trend = TREND_EQUAL;
        }
        
        return Integer.valueOf(trend);
    }

    @Override
    public List<AdPlatform> findAdPlatformByCustomerNumberAndLocale(
            Long customerNumber, LocaleConstants locale) {
        List<AdPlatform> adPlatformList = publishDataRepository.findAdPlatformByCustomerNumber(customerNumber, new Date());
        i18nKVService.fillI18nInfo(adPlatformList, locale);
        return adPlatformList;
    }

    @Override
    public PublishListVO findListByCustomerNumberAndLocale(Long customerNumber, Date planEndDate,
            LocaleConstants locale, PublishListVO vo) {
        PublishListVO publishListVO = publishDataRepository.findListByCustomerNumber(customerNumber, planEndDate, vo);
        List<PublishAdContentVO> publishAdDataVOList = publishListVO.getAdDatas();
        
        String billingModelTable = "g_billing_model";
        List<I18nKV> billingModelI18nKVList = i18nKVService.findByKeyNameLikeAndLocale(billingModelTable, locale);
        Map<String, String> billModeId2NameMap = new HashMap<String, String>();
        if(CollectionUtils.isNotEmpty(billingModelI18nKVList)){
            for(I18nKV i18nKV : billingModelI18nKVList){//(格式为g_billing_model.x)，截取长度从billingModelTable.length() + “。”。length()出开始
                billModeId2NameMap.put(i18nKV.getKey().substring(billingModelTable.length() + 1), i18nKV.getValue());
            }
        }
        
        if (publishAdDataVOList != null && publishAdDataVOList.size() > 0){
            Map<String,String> suspendPublishMap = getSuspendPublishStatus(publishAdDataVOList);
            
            for (PublishAdContentVO temPublishAdDataVO : publishAdDataVOList) {
                String publishNumber = temPublishAdDataVO.getPublishNumber();
                String publishStatsu = temPublishAdDataVO.getPublishStatus();
                if (String.valueOf(PublishStatus.unpublish.ordinal()).equals(publishStatsu)
                        || String.valueOf(PublishStatus.publishing.ordinal()).equals(publishStatsu)) {
                    if (CURR_ONLINE_STATUS.equals(temPublishAdDataVO.getOnlineStatus())){
                        temPublishAdDataVO.setPublishStatus(PublishPeriodStatus.ongoing.name());
                    } else if (suspendPublishMap != null && suspendPublishMap.get(publishNumber) != null) {
                        temPublishAdDataVO.setPublishStatus(PublishPeriodStatus.not_start.name());
                    }
                     
                } else {
                    temPublishAdDataVO.setPublishStatus(null);
                }
                
                String modelId = temPublishAdDataVO.getBillingModelId();
                if("2".equals(modelId)){//根据需要，如果是cps的话 单价信息显示成我方分成,CPS.id=2不会变
                    NumberFormat nt = DecimalFormat.getPercentInstance();
                    nt.setMinimumFractionDigits(2);
                    Double ratioMine = temPublishAdDataVO.getRatioMine();
                    if(ratioMine != null){
                        temPublishAdDataVO.setCustomerQuote(nt.format(ratioMine.doubleValue() / 100d));//12.00%的格式，小数点后需要2个0
                    }
                    else{
                        temPublishAdDataVO.setCustomerQuote("");
                    }
                }
                else{
                    NumberFormat nt = DecimalFormat.getNumberInstance();
                    Double quoteExcludeCPS = temPublishAdDataVO.getCustomerQuoteExcludeCPS();//小数点后去掉多余的0
                    temPublishAdDataVO.setCustomerQuote(quoteExcludeCPS != null ? nt.format(quoteExcludeCPS) : "");
                }
                temPublishAdDataVO.setBillingModelName(billModeId2NameMap.get(modelId));//把billingModelId转换成name 如CPS CPA CPT
                
                processPositionName(temPublishAdDataVO, locale);
            }
        }
        return publishListVO;
    }
    
    private Map<String,String> getSuspendPublishStatus(List<PublishAdContentVO> publishAdDataVOList) {
        Map<String,String> statusMap = new HashMap<String,String> ();
        List<String> publishNumberList = new LinkedList<String> ();
        for (PublishAdContentVO temPublishAdDataVO : publishAdDataVOList) {
            String currPublishStatus = temPublishAdDataVO.getPublishStatus();
            if (String.valueOf(PublishStatus.unpublish.ordinal()).equals(currPublishStatus)
                    || String.valueOf(PublishStatus.publishing.ordinal()).equals(currPublishStatus)) {
                publishNumberList.add(temPublishAdDataVO.getPublishNumber());
            }
        }
        if (publishNumberList.size() < 1) {
            return statusMap;
        }
        statusMap = publishDataRepository.findSuspendByPublishNumber(publishNumberList);
        return statusMap;
    }
    
    private void processPositionName(PublishAdContentVO temPublishAdDataVO, LocaleConstants locale) {
        Long positionId = temPublishAdDataVO.getPositionId();
        I18nKV i18nKV = i18nKVService.getAndLoadI18Info(Position.class, positionId, locale);
        if (i18nKV == null) {
            return;
        }
        String fullPositionName = i18nKV.getExtraValue();
        if (fullPositionName != null && !fullPositionName.trim().equals("")) {
            String[] nameArray = fullPositionName.toString().split(InfoDecorator.SPLIT_FLAG);
            if (nameArray.length > 4) {
                temPublishAdDataVO.setAdPlatformName(nameArray[0]);
                temPublishAdDataVO.setSiteName(nameArray[1]);
                StringBuilder currPositionName = new StringBuilder()
                                            .append(nameArray[2]).append(InfoDecorator.SPLIT_FLAG)
                                            .append(nameArray[3]).append(InfoDecorator.SPLIT_FLAG)
                                            .append(nameArray[4]);
                temPublishAdDataVO.setPositionName(currPositionName.toString());
            }
        }
    }

    @Override
    public PublishClickListVO findClickListByDateRangeAndAdContentNumber(PublishClickListVO vo, Long customerNumber) {
        return publishDataRepository.findClickListByDateRangeAndAdContentNumber(vo, customerNumber);
    }

    @Override
    public void updatePublishDataInfo() {
        publishDataRepository.updatePublishDataInfo(PublishStatus.release);
    }

    @Override
    public PublishAdContentVO findByAdContentNumber(String adContentNumber,
            Long customerNumber, LocaleConstants locale) {
        PublishAdContentVO currPublishAdContentVO =  publishDataRepository.findAdContentByContentNumber(adContentNumber, customerNumber);
        processPositionName(currPublishAdContentVO, locale);
        return currPublishAdContentVO;
    }
    
    public void setPublishDataRepository(IAdContentPublishDataRepository publishDataRepository) {
        this.publishDataRepository = publishDataRepository;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setI18nKVService(I18nKVService i18nKVService) {
        this.i18nKVService = i18nKVService;
    }
}
