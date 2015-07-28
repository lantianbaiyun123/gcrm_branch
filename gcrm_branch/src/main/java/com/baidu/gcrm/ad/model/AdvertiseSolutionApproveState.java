package com.baidu.gcrm.ad.model;


public enum AdvertiseSolutionApproveState {
	/** 待提交 */
	saving,
	/** 审核驳回 */
	refused,
	/** 审核中 */
	approving,
	/** 审核完成 */
	approved,
    /** 已生效 */
    effective,
	/** 作废 */
	cancel;
	public static AdvertiseSolutionApproveState valueOf(Integer value){
		if(value == null){
			return null;
		}
		AdvertiseSolutionApproveState[] values = AdvertiseSolutionApproveState.values(); 
		for(AdvertiseSolutionApproveState status : values){
			if(status.ordinal() == value){
				return status;
			}
		}
		return null;
	}
}
