package com.baidu.gcrm.ad.statistic.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import com.baidu.gcrm.ad.statistic.dao.IAdContentPublishDataRepositoryCustom;
import com.baidu.gcrm.ad.statistic.web.vo.PublishAdContentVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickListVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishClickStatDataVO;
import com.baidu.gcrm.ad.statistic.web.vo.PublishListVO;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;

public class IAdContentPublishDataRepositoryImpl implements IAdContentPublishDataRepositoryCustom {
   
    private static Logger logger = LoggerFactory.getLogger(IAdContentPublishDataRepositoryImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final int DETAIL_AMOUNT_LIMIT = 4000;
    
    private static final int PAGE_SIZE_LIMIT = 500;
    private static final int PAGE_SIZE_EXPORT_BY_CUSTOMER_NUMBER_MAX = 20000;//TODO
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PublishClickStatDataVO> findByCustomerNumberAndLocale(Long customerNumber, LocaleConstants locale) {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(new Date());
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        
        Calendar lastWeek = Calendar.getInstance();
        lastWeek.setTime(new Date());
        lastWeek.add(Calendar.DAY_OF_MONTH, -8);
        
        StringBuilder sql = new StringBuilder();
        sql.append("select i.key_extra_value,p.click,p.uv,p.site_code,p.publish_time,p.position_id ")
            .append(" from  g_adcontent_publish_data p,g_i18n i,g_advertise_solution_content c ")
            .append(" where i.key_name=concat('g_position.',p.position_id) and p.ad_content_number=c.number ")
            .append(" and i.locale=?")
            .append(" and (p.publish_time>=? and p.publish_time<=?) ");
        
        List<Object> paramList = new LinkedList<Object> ();
        paramList.add(locale.name());
        paramList.add(simpleDateFormat.format(lastWeek.getTime()));
        paramList.add(simpleDateFormat.format(yesterday.getTime()));
        if (customerNumber != null) {
            sql.append(" and (p.customer_number=? or c.advertiser_id=?)");
            paramList.add(customerNumber);
            paramList.add(customerNumber);
        }
        
        sql.append(" order by p.publish_time desc ");
        Query query = prepareQuery(sql.toString(), paramList);
        List<Object[]> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        
        return processPublishStatData(result, simpleDateFormat);
    }
    
    private List<PublishClickStatDataVO> processPublishStatData(List<Object[]> result, SimpleDateFormat format) {
        List<PublishClickStatDataVO> resulList = new LinkedList<PublishClickStatDataVO> ();
        for (Object[] resultArray : result) {
            PublishClickStatDataVO temPublishDataVO = new PublishClickStatDataVO();
            Object fullPositionName = resultArray[0];
            if (fullPositionName != null 
                    && !fullPositionName.toString().trim().equals("")) {
                String[] nameArray = fullPositionName.toString().split(InfoDecorator.SPLIT_FLAG);
                if (nameArray.length > 4) {
                    temPublishDataVO.setAdPlatformName(nameArray[0]);
                    temPublishDataVO.setSiteName(nameArray[1]);
                    StringBuilder currPositionName = new StringBuilder()
                                                .append(nameArray[2]).append(InfoDecorator.SPLIT_FLAG)
                                                .append(nameArray[3]).append(InfoDecorator.SPLIT_FLAG)
                                                .append(nameArray[4]);
                    temPublishDataVO.setPositionName(currPositionName.toString());
                }
            }
            
            temPublishDataVO.setClick(Long.valueOf(resultArray[1].toString()));
            temPublishDataVO.setUv(Long.valueOf(resultArray[2].toString()));
            
            Object siteCodeObj = resultArray[3];
            if (siteCodeObj != null) {
                temPublishDataVO.setSiteCode(siteCodeObj.toString());
            }
            
            Object timeObj = resultArray[4];
            if (timeObj != null) {
                try {
                    temPublishDataVO.setDate(format.parse(timeObj.toString()));
                } catch (ParseException e) {
                }
            }
            
            temPublishDataVO.setPositionId(Long.valueOf(resultArray[5].toString()));
            resulList.add(temPublishDataVO);
        }
        
        return resulList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdPlatform> findAdPlatformByCustomerNumber(Long customerNumber, Date planEndDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct p.ad_platform_id from g_publish pb, g_position p,g_advertise_solution_content c ")
            .append(" where pb.position_id=p.id and pb.ac_number=c.number ");
        
        List<Object> paramList = new LinkedList<Object> ();
        if (customerNumber != null) {
            sql.append(" and (pb.c_number=? or c.advertiser_id=?)");
            paramList.add(customerNumber);
            paramList.add(customerNumber);
        }
        
        Query query = prepareQuery(sql.toString(), paramList);
        List<BigInteger> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        List<AdPlatform> resultList = new LinkedList<AdPlatform> ();
        for (BigInteger adPlatformId : result) {
            AdPlatform temAdPlatform = new AdPlatform();
            temAdPlatform.setId(Long.valueOf(adPlatformId.longValue()));
            resultList.add(temAdPlatform);
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PublishListVO findListByCustomerNumber(Long customerNumber, Date planEndDate, PublishListVO vo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select c.number,c.advertisers,c.period_description,c.description, ")
        .append("c.material_url,c.position_id,pb.status,pb.number as publushNumber, ")
        .append("(select IF(pd.status<>'ongoing',0,1) from g_publish_date pd where pb.number=pd.p_number and pd.status='ongoing' limit 1) as currStatus, ")
        .append(" gaq.budget as budget, gaq.customer_quote as customer_quote, gaq.billing_model_id as billing_model_id, gaq.ratio_mine as ratio_mine ")
        .append(" from g_advertise_solution_content c,g_publish pb, g_advertise_quotation gaq ")
        .append(" where c.id=pb.ad_content_id and gaq.advertise_solution_content_id = c.id")
        .append(" and c.product_id=? ");
        
        List<Object> paramList = new LinkedList<Object> ();
        paramList.add(vo.getAdPlatformId());
        if (customerNumber != null) {
            sql.append(" and (pb.c_number=? or c.advertiser_id=?)");
            paramList.add(customerNumber);
            paramList.add(customerNumber);
        }
        
        sql.append(" order by currStatus desc ,pb.status asc,c.create_time desc ");
        int totalCount = countList(sql.toString(), paramList);
        if (totalCount < 1) {
            return vo;
        }
        
        int currPageSize = vo.getPageSize();
        if (currPageSize > PAGE_SIZE_LIMIT) {
            logger.warn("findListByCustomerNumber enable attack protect,page size {} was reseted,use security page size {}",
                    currPageSize, PAGE_SIZE_LIMIT);
            currPageSize = PAGE_SIZE_LIMIT;
        }
        if(currPageSize < 0){//针对 某广告主的全部数据导出下载
            currPageSize = PAGE_SIZE_EXPORT_BY_CUSTOMER_NUMBER_MAX;
        }
        int realPageNum = vo.getPageNumber();
        if (realPageNum < 0) {
            realPageNum = 0;
        } else {
            realPageNum = realPageNum - 1;
        }
        PageRequest page = new PageRequest(realPageNum, currPageSize);
        vo.setTotalCount(totalCount);
        vo.setPageSize(page.getPageSize());
        
        Query query = prepareQuery(sql.toString(), paramList);
        query.setFirstResult(page.getOffset());
        query.setMaxResults(page.getPageSize());
        List<Object[]> result = query.getResultList();
        vo.setAdDatas(processListResult(result));
        return vo;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public PublishAdContentVO findAdContentByContentNumber(String contentNumber, Long customerNumber) {
        StringBuilder sql = new StringBuilder();
        sql.append("select c.advertisers,c.material_url,c.position_id ")
            .append(" from g_advertise_solution_content c, g_advertise_solution s ")
            .append(" where c.advertise_solution_id=s.id and c.number=? ");
        List<Object> paramList = new LinkedList<Object> ();
        paramList.add(contentNumber);
        if (customerNumber != null) {
            sql.append(" and (s.customer_number=? or c.advertiser_id=?) ");
            paramList.add(customerNumber);
            paramList.add(customerNumber);
        }
        
        
        Query query = prepareQuery(sql.toString(), paramList);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<Object[]> result = query.getResultList();
        if (result == null || result.size() < 1) {
            return null;
        }
        Object[] objectArray = result.get(0);
        PublishAdContentVO vo = new PublishAdContentVO();
        vo.setAdvertiser(objectArray[0].toString());
        Object urlObj = objectArray[1];
        if (urlObj != null) {
            vo.setUrl(urlObj.toString());
        }
        vo.setPositionId(Long.valueOf(objectArray[2].toString()));
        return vo;
    }
    
    private List<PublishAdContentVO> processListResult(List<Object[]> result) {
        if (result == null || result.size() < 1) {
            return null;
        }
        List<PublishAdContentVO> tempResultList = new LinkedList<PublishAdContentVO> ();
        for (Object[] resultArray : result) {
            PublishAdContentVO temPublishAdDataVO = new PublishAdContentVO();
            temPublishAdDataVO.setAdContentNumber(resultArray[0].toString());
            temPublishAdDataVO.setAdvertiser(resultArray[1].toString());
            temPublishAdDataVO.setOccupationTime(resultArray[2].toString());
            temPublishAdDataVO.setDesc(resultArray[3].toString());
            if (resultArray[4] != null) {
                temPublishAdDataVO.setUrl(resultArray[4].toString());
            }
            
            temPublishAdDataVO.setPositionId(Long.valueOf(resultArray[5].toString()));
            temPublishAdDataVO.setPublishStatus(resultArray[6].toString());
            temPublishAdDataVO.setPublishNumber(resultArray[7].toString());
            if (resultArray[8] != null) {
                temPublishAdDataVO.setOnlineStatus(resultArray[8].toString());
            }
            if(resultArray[9] != null){
                temPublishAdDataVO.setBudget(Double.valueOf(resultArray[9].toString()));
            }
            if(resultArray[10] != null){
                temPublishAdDataVO.setCustomerQuoteExcludeCPS(Double.valueOf(resultArray[10].toString()));//去掉小数点后没意义的。0
            }
            if(resultArray[11] != null){
                temPublishAdDataVO.setBillingModelId(resultArray[11].toString());
            }            
            if(resultArray[12] != null){
                temPublishAdDataVO.setRatioMine(Double.valueOf(resultArray[12].toString()));
            }
            
            tempResultList.add(temPublishAdDataVO);
        }
        return tempResultList;
    }
    
    

    @SuppressWarnings("unchecked")
    @Override
    public PublishClickListVO findClickListByDateRangeAndAdContentNumber(PublishClickListVO vo, Long customerNumber) {
        StringBuilder sql = new StringBuilder();
        sql.append("select d.publish_time,d.click,d.uv  from g_adcontent_publish_data d,g_advertise_solution_content c ")
            .append(" where d.ad_content_number=c.number and  d.ad_content_number=? ")
            .append(" and d.publish_time>=? and d.publish_time<=? ");
        List<Object> paramList = new ArrayList<Object> (3);
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        paramList.add(vo.getAdContentNumber());
        paramList.add(simpleDateFormat.format(vo.getFrom()));
        paramList.add(simpleDateFormat.format(vo.getTo()));
        
        if (customerNumber != null) {
            sql.append(" and (d.customer_number=? or c.advertiser_id=?)");
            paramList.add(customerNumber);
            paramList.add(customerNumber);
        }
        
        sql.append(" order by d.publish_time ");
        
        int totalCount = countList(sql.toString(), paramList);
        if (totalCount < 1) {
            return vo;
        } else {
            vo.setTotalCount(totalCount);
        }
        
        Query query = prepareQuery(sql.toString(), paramList);
        //attack protect
        if (totalCount > DETAIL_AMOUNT_LIMIT) {
            query.setFirstResult(0);
            query.setMaxResults(DETAIL_AMOUNT_LIMIT);
            logger.warn("findClickListByDateRangeAndAdContentNumber enable attack protect,total count {} was reseted,use security amount {}",
                    totalCount, DETAIL_AMOUNT_LIMIT);
        }

        List<Object[]> result = query.getResultList();
        if (result != null && result.size() > 0) {
            SimpleDateFormat tempSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<PublishClickStatDataVO> adClickDatas = new LinkedList<PublishClickStatDataVO> ();
            for (Object[] resultObj : result) {
                if (resultObj == null || resultObj.length < 3) {
                    continue;
                }
                
                PublishClickStatDataVO temPublishClickDataVO = new PublishClickStatDataVO();
                try {
                    Date publishDate = tempSimpleDateFormat.parse(resultObj[0].toString());
                    temPublishClickDataVO.setDate(publishDate);
                } catch (ParseException e) {
                    logger.error("findClickListByDateRangeAndAdContendId parse date error");
                }
                temPublishClickDataVO.setClick(Long.valueOf(resultObj[1].toString()));
                temPublishClickDataVO.setUv(Long.valueOf(resultObj[2].toString()));
                adClickDatas.add(temPublishClickDataVO);
            }
            
            vo.setAdClickDatas(adClickDatas);
        }
        
        return vo;
        
    }
    
    
    private int countList(String sql, List<Object> paramList) {
        StringBuilder countStr = new StringBuilder();
        String simpleSql = "";
        String lowercaseSql = sql.toLowerCase();
        if (lowercaseSql.indexOf("order") != -1) {
            simpleSql = lowercaseSql.substring(0, lowercaseSql.indexOf("order by"));
        } else {
            simpleSql = lowercaseSql;
        }
        countStr.append("select count(*) from (")
            .append(simpleSql)
            .append(" ) t ");
        Query query = prepareQuery(countStr.toString(), paramList);
        Object totalObj = query.getSingleResult();
        if (totalObj == null) {
            return 0;
        }
        return Integer.parseInt(totalObj.toString());
    }
    
    private Query prepareQuery(String sql, List<? extends Object> paramList) {
        Query query = entityManager.createNativeQuery(sql.toString());
        if (paramList == null || paramList.size() < 1) {
            return query;
        }
        for (int i = 0; i < paramList.size(); i++) {
            query.setParameter(i+1, paramList.get(i));
        }
        
        return query;
    }

    /**
     * return map key=>publishNumber
     */
    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    public Map<String,String> findSuspendByPublishNumber(
            List<String> publishNumberList) {
        StringBuilder sql = new StringBuilder()
                                .append("select distinct p_number from g_publish_date where p_number in (");
        int i = 0;
        for (String publishNumber : publishNumberList) {
            if (i > 0) {
                sql.append(",?");
            } else {
                sql.append("?");
            }
            i++;
        }
        sql.append(") and status = ? and plan_end >=? ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        publishNumberList.add(PublishPeriodStatus.not_start.name());
        publishNumberList.add(simpleDateFormat.format(new Date()));
        
        Query query = prepareQuery(sql.toString(), publishNumberList);
        List<String> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        Map<String,String> statusMap = new HashMap<String,String> ();
        for (String numberStr : result) {
            statusMap.put(numberStr, "");
        }
        
        return statusMap;
    }
    
}
