package com.baidu.gcrm.ad.content.web.helper;

import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView4Material;
import com.baidu.gcrm.common.page.Page;

/**
 * 广告内容查询，自定义查询条件
 *     
 * 项目名称：gcrm    
 * 类名称：AdSolutionContentCondition    
 * 类描述：    
 * 创建人：chenchunhui01    
 * 创建时间：2014年4月19日 下午3:41:33    
 * 修改人：chenchunhui01    
 * 修改时间：2014年4月19日 下午3:41:33    
 * 修改备注：    
 * @version     
 *
 */
public class AdSolutionContentCondition extends Page<AdSolutionContentView4Material> {
    public enum QueryType {
        CONTENTID,
        SOLUTIONID,
        RESOURCEID;
    }
    /**
     * 查詢類型（广告内容ID，广告方案ID,资源位编号)
     */
    private QueryType queryType = QueryType.CONTENTID;
    /**
     * 查询条件 与查询类型映射，使用
     */
    private String queryString;
    /**
     * 投放时间开始
     */
    private String beginThrowTime;
    /**
     * 投放时间 结束
     */
    private String endThrowTime;
   /**
    *  广告主
    */
    private String advertiser;
   
    /**
     * 投放站点ID
     */
    private String siteId;
    /**
     * 投放频道ID;
     */
    private String channelId;
    /**
     * 投放区域ID
     */
    private String areaId;
    /**
     * 投放平台ID(productId)
     */
    private String platformId;
 
    /**
     * 投放位置ID
     */
    private String positionId;
    /**
     * 签约公司名称（方案对应客户信息）
     * suggest(只允许起选择指定公司
     */
    private String customerId;
    
    /**
     * 提交人（首页查询）
     */
    private Long createOperator;
    
    public QueryType getQueryType() {
        return queryType;
    }
    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }
    public String getQueryString() {
        return queryString;
    }
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
    public String getBeginThrowTime() {
        return beginThrowTime;
    }
    public void setBeginThrowTime(String beginThrowTime) {
        this.beginThrowTime = beginThrowTime;
    }
    public String getEndThrowTime() {
        return endThrowTime;
    }
    public void setEndThrowTime(String endThrowTime) {
        this.endThrowTime = endThrowTime;
    }
    public String getSiteId() {
        return siteId;
    }
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public String getChannelId() {
        return channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getPlatformId() {
        return platformId;
    }
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }
    public String getPositionId() {
        return positionId;
    }
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getAdvertiser() {
        return advertiser;
    }
    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }
  
    public Long getCreateOperator() {
        return createOperator;
    }
    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;
    }
    @Override
    public Class<AdSolutionContentView4Material> getResultClass() {
        return AdSolutionContentView4Material.class;
    }
}
