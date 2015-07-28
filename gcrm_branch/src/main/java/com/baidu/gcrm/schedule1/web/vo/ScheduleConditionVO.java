package com.baidu.gcrm.schedule1.web.vo;

import java.util.List;

import com.baidu.gcrm.common.page.PageWrapper;

public class ScheduleConditionVO extends PageWrapper<ScheduleListVO> {

    private static final long serialVersionUID = 9193434942417442698L;
    
    public enum QueryType {
		advertisers,//广告主
		schedulenum,//排期单编号
        adcontentnum;//广告内容编号
    }
	/**
	 * 查询类型
	 */
	private QueryType queryType = QueryType.advertisers;
	/**
	 * 查询字段内容
	 */
	private String queryStr;
	/**
	 * 排期单状态
	 */
	private Integer scheduleStatus;
	/**
	 * 投放开始时间
	 */
	private String startDate;
	/**
	 * 投放结束时间
	 */
	private String endDate;
	/**
	 * 平台
	 */
	private Integer platFormId;
	/**
	 * 国家站点
	 */
	private Integer siteId;
	/**
	 * 页面
	 */
	private Integer channelId;
	/**
	 * 区域
	 */
	private Integer areaId;
	/**
	 * 位置
	 */
	private Integer positionId;
	
	private List<Long> positionDateIds;
	
	
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
	public Integer getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(Integer scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getPlatFormId() {
		return platFormId;
	}
	public void setPlatFormId(Integer platFormId) {
		this.platFormId = platFormId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
    public List<Long> getPositionDateIds() {
        return positionDateIds;
    }
    public void setPositionDateIds(List<Long> positionDateIds) {
        this.positionDateIds = positionDateIds;
    }
}
