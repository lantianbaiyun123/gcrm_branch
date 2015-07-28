package com.baidu.gcrm.publish.web.vo;

import java.util.List;

import com.baidu.gcrm.user.web.vo.UserVO;

public class PublishOwnerListVO {
	private Long id;
    private String channelName;
    private String siteName;
    private List<UserVO> user;
    private Long channelId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public List<UserVO> getUser() {
		return user;
	}
	public void setUser(List<UserVO> user) {
		this.user = user;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
}
