package com.baidu.gcrm.ad.material.vo;

import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
/**
* 项目名称：gcrm     
* 类描述：   首页查询用
* 创建人：yudajun   
* 创建时间：2014-5-16 下午7:38:45   
* 修改人：yudajun   
* 修改时间：2014-5-16 下午7:38:45   
* 修改备注：   
* @version    
*/
public class MaterialApplyContentVO {
	private Long applyId;
	private String number;
	//首页增加属性 start
	private Long adSolutionId;//方案id
	private Long adContentId;//内容id
	private String advertiser;//广告主
	private String description;//内容描述
	private String contentNumber;//内容单号
	private String taskInfor;//任务到达
	private MaterialApplyState applyState;//审核状态
	
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Long getAdSolutionId() {
		return adSolutionId;
	}
	public void setAdSolutionId(Long adSolutionId) {
		this.adSolutionId = adSolutionId;
	}
	public Long getAdContentId() {
		return adContentId;
	}
	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}
	public String getAdvertiser() {
		return advertiser;
	}
	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentNumber() {
		return contentNumber;
	}
	public void setContentNumber(String contentNumber) {
		this.contentNumber = contentNumber;
	}
	public String getTaskInfor() {
		return taskInfor;
	}
	public void setTaskInfor(String taskInfor) {
		this.taskInfor = taskInfor;
	}
	public MaterialApplyState getApplyState() {
		return applyState;
	}
	public void setApplyState(MaterialApplyState applyState) {
		this.applyState = applyState;
	}
}
