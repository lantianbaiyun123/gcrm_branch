package com.baidu.gcrm.personalpage.web.vo;

public class ChannelOperationVO {
	private String date;
	private String platName;
	private String siteName;
	private Long platformId;
	private Long siteId;
	private Long channelId;
    private String channelName;
    private String salesAmount;
    private String soldAmount;
    private String ratio;
    private String indexStr;
    
	public String getIndexStr() {
		return indexStr;
	}
	public void setIndexStr(String indexStr) {
		this.indexStr = indexStr;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(String salesAmount) {
		this.salesAmount = salesAmount;
	}
	public String getSoldAmount() {
		return soldAmount;
	}
	public void setSoldAmount(String soldAmount) {
		this.soldAmount = soldAmount;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
    public String getPlatName() {
		return platName;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
}
