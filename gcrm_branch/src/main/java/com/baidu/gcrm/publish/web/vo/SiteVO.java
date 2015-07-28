package com.baidu.gcrm.publish.web.vo;

import java.util.Map;

public class SiteVO {
	private String name;
	private Integer total;
	 
	private Map<String,ChannelVO> channel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Map<String, ChannelVO> getChannel() {
		return channel;
	}

	public void setChannel(Map<String, ChannelVO> channel) {
		this.channel = channel;
	}
}
