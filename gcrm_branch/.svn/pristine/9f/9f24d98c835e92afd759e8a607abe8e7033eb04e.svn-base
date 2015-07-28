package com.baidu.gcrm.publish.web.utils;

import java.util.List;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.publish.web.vo.PublishDoneListVO;

@SuppressWarnings("rawtypes")
public class PublishDoneCondition extends Page<PublishDoneListVO>{
	private static final long serialVersionUID = 2354683576213542825L;
	/**
	 * 查询类型
	 */
	private QueryType queryType = QueryType.adSolutionNumber;
	private String queryStr;//查询字段内容
	private Long productId;//平台
	private String channelId;//频道
	private Long areaId;//区域
	private Long positionId;//位置
	private Long siteId;//站点
	private String number;//申请单编号
	private List<Long> currentOperChannelIds;//当前登录人负责的频道
    private List<Long> currentOperPlatIds;//当前登录人负责的平台
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public QueryType getQueryType() {
		return queryType;
	}
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
    public List<Long> getCurrentOperChannelIds() {
        return currentOperChannelIds;
    }
    public void setCurrentOperChannelIds(List<Long> currentOperChannelIds) {
        this.currentOperChannelIds = currentOperChannelIds;
    }
    public List<Long> getCurrentOperPlatIds() {
        return currentOperPlatIds;
    }
    public void setCurrentOperPlatIds(List<Long> currentOperPlatIds) {
        this.currentOperPlatIds = currentOperPlatIds;
    }
    @Override
    public Class<PublishDoneListVO> getResultClass() {
        return PublishDoneListVO.class;
    }
}
